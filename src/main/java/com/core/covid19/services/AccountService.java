package com.core.covid19.services;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.core.covid19.models.entities.*;
import com.core.covid19.models.requests.ChangePasswordRequest;
import com.core.covid19.models.requests.DoctorResponse;
import com.core.covid19.models.responses.PersonResponse;
import com.core.covid19.repos.*;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core.covid19.models.enums.Roles;
import com.core.covid19.models.requests.AccountRequest;
import org.springframework.web.multipart.MultipartFile;

@Service
public class AccountService {

	@Autowired
	private RoleRepo roleRepo;

	@Autowired
	private AccountRepo accountRepo;

	@Autowired
	private PersonRepo personRepo;

	@Autowired
	private StatusRepo statusRepo;

	@Autowired
	private ProvinceRepo provinceRepo;

	@Autowired
	private LocationRepo locationRepo;

	@Autowired
	HospitalDoctorRepo hospitalDoctorRepo;

	public Account insert(AccountRequest accountRequest) {
		Role role = null;

		if(accountRequest.getRole() != null)
			role = roleRepo.findByName(accountRequest.getRole().getName());
		else role = roleRepo.findByName(Roles.CIVIL.toString());

		Account account = new Account();
		account.setEmail(accountRequest.getEmail());
		account.setPassword(accountRequest.getPassword());
		account.setRole(role);

		return accountRepo.save(account);
	}

	public List<Account> findAll(){
		return accountRepo.findAll();
	}

	public Optional<Account> findById(Integer id) {
		return accountRepo.findById(id);
	}

	public Account modify(AccountRequest accountRequest){
		Role role = roleRepo.findByName(accountRequest.getRole().getName());

		Account account = new Account();
		account.setEmail(accountRequest.getEmail());
		account.setPassword(accountRequest.getPassword());
		account.setRole(role);

		return accountRepo.save(account);
	}

	public void deleteById(String email) {
		accountRepo.delete(accountRepo.findByEmail(email));
	}

	public List<PersonResponse> getDoctors() {

		Role role = roleRepo.findByName(Roles.PROFESIONAL_MEDICO.toString());
		List<Person> persons = personRepo.getDoctors(role.getId());
		List<PersonResponse> list = new ArrayList<>();
		for (Person p : persons) list.add(new PersonResponse(p));
		return list;
	}

	public DoctorResponse getDoctor(int id) {

		Optional<Person> person = personRepo.findById(id);
		if (person != null && person.isPresent())
			return new DoctorResponse(person.get());
		return null;
	}

	public List<Hospital> getHospitalsDoctor(int id) {

		Optional<Person> person = personRepo.findById(id);
		if (person != null && person.isPresent()) {
			Person p = person.get();
			return hospitalDoctorRepo.getHospitalsDoctor(p.getId());
		}
		return new ArrayList<>();
	}

	public void saveHospitalsDoctor(int id, List<Hospital> hospitalList) {

		Optional<Person> person = personRepo.findById(id);
		if (person != null && person.isPresent()) {
			Person p = person.get();
			List<HospitalDoctor> hospitals = hospitalDoctorRepo.getAsignados(p.getId());
			for (HospitalDoctor h : hospitals) {
				hospitalDoctorRepo.delete(h);
			}
			for (Hospital h : hospitalList) {
				HospitalDoctor hd = new HospitalDoctor(new HospitalDoctorPk(id, h.getId()));
				hospitalDoctorRepo.save(hd);
			}
		}
	}

	public void changePassword(ChangePasswordRequest data, String email) throws Exception {

		Account account = accountRepo.findByEmail(email);
		if (account != null) {
			String passwordActual = account.getPassword();
			if (!data.getPassword().equals(passwordActual)) {
				throw new Exception("Password incorrecto");
			}
			account.setPassword(data.getNewpassword());
			accountRepo.save(account);
		}
	}

	public void loadData(MultipartFile file) throws IOException, InvalidFormatException {

		Workbook workbook = WorkbookFactory.create(file.getInputStream());
		Sheet sheet = workbook.getSheetAt(0);
		DataFormatter dataFormatter = new DataFormatter();
		Iterator<Row> rowIterator = sheet.rowIterator();
		int rowNumber = 0;

		String document = "";
		String name = "";
		String lastname = "";
		String phone = "";
		String sex = "";
		String province = "";
		String address = "";
		String birthDate = "";
		String email = "";
		String password = "";
		String status = "";
		Double latitude = null;
		Double longitude = null;

		// iterate rows
		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			if (rowNumber == 0) {
				rowNumber++;
			} else {
				Iterator<Cell> cellIterator = row.cellIterator();
				int col = 0;
				// iterate columns
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					String cellValue = dataFormatter.formatCellValue(cell);
					if (col == 0) document = cellValue;
					else if (col == 1) name = cellValue;
					else if (col == 2) lastname = cellValue;
					else if (col == 3) sex = cellValue;
					else if (col == 4) birthDate = cellValue;
					else if (col == 5) phone = cellValue;
					else if (col == 6) province = cellValue;
					else if (col == 7) address = cellValue;
					else if (col == 8) email = cellValue;
					else if (col == 9) password = cellValue;
					else if (col == 10) status = cellValue;
					else if (col == 11) {
						try {
							longitude = new Double(cellValue);
						} catch (Exception e) { }
					} else if (col == 12) {
						try {
							latitude = new Double(cellValue);
						} catch (Exception e) { }
					}
					col++;
				}

				Person p = personRepo.findByDocument(document);
				Status s = statusRepo.findByName(status);
				Province pro = provinceRepo.findByName(province);
				boolean isNew = false;
				if (p == null) {
					p = new Person();
					isNew = true;
				}

				// Location
				Location l = new Location(latitude, longitude);
				Location loc = null;
				if (isNew) {
					loc = locationRepo.save(l);
				} else {
					if (p.getLocation() != null) {
						Optional<Location> data = locationRepo.findById(p.getLocation().getId());
						if (data != null && data.isPresent()) {
							loc = data.get();
							if (latitude != loc.getLatitude() || longitude != loc.getLongitude()) {
								loc.setLatitude(latitude);
								loc.setLongitude(longitude);
								locationRepo.save(loc);
							}
						} else {
							loc = locationRepo.save(l);
						}
					}
				}

				// Save Person
				p.setDocument(document);
				p.setName(name);
				p.setLastname(lastname);
				p.setSex(sex);
				p.setPhone(phone);
				p.setAddress(address);
				p.setStatus(s);
				p.setProvince(pro);
				p.setLocation(loc);
				p.setBirthDate(getFecha(birthDate));
				p = personRepo.save(p);

				// Account
				Account a = new Account();
				if (!isNew) {
					if (p.getAccounts() != null && p.getAccounts().size() > 0) {
						List<Account> accounts = new ArrayList<>();
						accounts.addAll(p.getAccounts());
						a = accounts.get(0);
					}
				}
				a.setEmail(email);
				a.setPassword(password);
				a.setPerson(p);
				Role role = roleRepo.findByName(Roles.PROFESIONAL_MEDICO.toString());
				a.setRole(role);
				accountRepo.save(a);
			}
		}
	}

	public ByteArrayOutputStream export() throws Exception {

		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("Doctores");
		sheet.setDefaultColumnWidth(20);
		List<PersonResponse> doctors = getDoctors();
		int rowCount = 0;

		// Header
		Row rowHeader = sheet.createRow(rowCount++);
		rowHeader.createCell(0).setCellValue("Nro. Documento");
		rowHeader.createCell(1).setCellValue("Nombre completo");
		rowHeader.createCell(2).setCellValue("Nro. de telefono");

		for (PersonResponse doctor : doctors) {
			Row row = sheet.createRow(rowCount++);
			row.createCell(0).setCellValue(doctor.getDocument());
			row.createCell(1).setCellValue(doctor.getName());
			row.createCell(2).setCellValue(doctor.getPhone());
		}

		ByteArrayOutputStream res = new ByteArrayOutputStream();
		try {
			workbook.write(res);
		} catch (IOException e) {
			e.printStackTrace();
			throw new Exception("Error al generar planilla");
		} finally {
			workbook.close();
		}
		return res;
	}

	private Timestamp getFecha(String birthDate) {

		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		try {
			Date d = format.parse(birthDate);
			if (d != null)
				return new Timestamp(d.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

}