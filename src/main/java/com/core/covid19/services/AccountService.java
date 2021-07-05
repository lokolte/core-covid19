package com.core.covid19.services;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import com.core.covid19.authentication.util.JwtUtil;
import com.core.covid19.models.entities.*;
import com.core.covid19.models.requests.ChangePasswordRequest;
import com.core.covid19.models.requests.DoctorRequest;
import com.core.covid19.models.requests.DoctorResponse;
import com.core.covid19.models.responses.PersonResponse;
import com.core.covid19.models.responses.PersonsResponse;
import com.core.covid19.repos.*;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.core.covid19.models.enums.Roles;
import com.core.covid19.models.requests.AccountRequest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class AccountService extends BaseService {

	@Autowired
	private RoleRepo roleRepo;

	@Autowired
	private RoleAccountRepo roleAccountRepo;

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

	@Autowired
	PatientDoctorRepo patientDoctorRepo;

	@Autowired
	EmailSender emailSender;

	@Autowired
	private JwtUtil jwtTokenUtil;

	@Autowired
	private CustomUserDetailService customUserDetailService;

	@Value("${url.backend}")
	private String urlBackend;

	@Value("${url.frontend}")
	private String urlFrontend;

	public Account insert(AccountRequest accountRequest) {

		Role role = null;
		if(accountRequest.getRole() != null)
			role = roleRepo.findByName(accountRequest.getRole().getName());
		else role = roleRepo.findByName(Roles.CIVIL.toString());

		Account account = new Account();
		account.setEmail(accountRequest.getEmail());
		account.setPassword(accountRequest.getPassword());
		Account a = accountRepo.save(account);

		RoleAccountPk pk = new RoleAccountPk(a.getId(), role.getId());
		RoleAccount roleAccount = new RoleAccount(pk);
		roleAccountRepo.save(roleAccount);

		String mensaje = getMessage(accountRequest.getEmail(), accountRequest.getPassword());
		emailSender.send(accountRequest.getEmail(), "Validar correo", mensaje);

		return a;
	}

	@Transactional
	public Account insertDoctor(DoctorRequest data) throws Exception {
		return create(data, Roles.PROFESIONAL_MEDICO.toString());
	}

	@Transactional
	public Account insertCoordinador(DoctorRequest data) throws Exception {
		return create(data, Roles.COORDINADOR.toString());
	}

	private Account create(DoctorRequest data, String rol) throws Exception {

		if (!data.getPassword().equals(data.getPassword2()))
			throw new Exception("Los passwords no coinciden");

		Status status = statusRepo.findByName("HEALTHY");
		Location location = new Location(data.getLatitude(), data.getLongitude());
		locationRepo.save(location);
		Person person = new Person(data, location, status);
		personRepo.save(person);

		Account account = new Account();
		account.setEmail(data.getEmail());
		account.setPerson(person);
		account.setPassword(data.getPassword());
		Account a = accountRepo.save(account);

		Role role = roleRepo.findByName(rol);
		RoleAccountPk pk = new RoleAccountPk(a.getId(), role.getId());
		RoleAccount roleAccount = new RoleAccount(pk);
		roleAccountRepo.save(roleAccount);

		String mensaje = getMessage(data.getEmail(), data.getPassword());
		emailSender.send(data.getEmail(), "Validar correo", mensaje);
		return a;
	}

	public List<Account> findAll(){
		return accountRepo.findAll();
	}

	public Optional<Account> findById(Integer id) {
		return accountRepo.findById(id);
	}

	@Transactional
	public void delete(int id) {
		Optional<Person> person = personRepo.findById(id);
		if (person != null && person.isPresent()) {
			Person p = person.get();
			Account a = accountRepo.getAccountByPersonId(p.getId());
			accountRepo.delete(a);
			personRepo.delete(p);
		}
	}

	public Account modify(AccountRequest accountRequest){
		Role role = roleRepo.findByName(accountRequest.getRole().getName());

		Account account = new Account();
		account.setEmail(accountRequest.getEmail());
		account.setPassword(accountRequest.getPassword());
		Account a = accountRepo.save(account);

		RoleAccountPk pk = new RoleAccountPk(a.getId(), role.getId());
		RoleAccount roleAccount = new RoleAccount(pk);
		roleAccountRepo.save(roleAccount);

		return a;
	}

	public void deleteById(String email) {
		accountRepo.delete(accountRepo.findByEmail(email));
	}

	/**
	 * Obtiene la lista de medicos de acuerdo al rol del usuario
	 */
	public List<PersonResponse> getDoctors(String email) {

		// Obtener datos del usuario logueado
		List<PersonResponse> list = new ArrayList<>();
		List<Person> persons = new ArrayList<>();
		Account account = accountRepo.findByEmail(email);
		if (account.getPerson() == null) return list;

		if (isAdmin(account.getRoles())) {
			// Si es admin, obtenemos todos los medicos
			Role role = roleRepo.findByName(Roles.PROFESIONAL_MEDICO.toString());
			List<Account> accounts = accountRepo.getAllByRole(role.getId());
			persons = accounts.stream().map(Account::getPerson).collect(Collectors.toList());

		} else if (isCoordinator(account.getRoles())) {
			// Si es coordinador, obtenemos los medicos de su Region
			Person person = account.getPerson();
			if (person.getProvince() == null) return list;
			Role role = roleRepo.findByName(Roles.PROFESIONAL_MEDICO.toString());
			List<Account> accounts = accountRepo.getAllByRoleAndProvince(role.getId(), person.getProvince().getId());
			persons = accounts.stream().map(Account::getPerson).collect(Collectors.toList());

		} else {
			return list;
		}

		for (Person p : persons) list.add(new PersonResponse(p));
		return list;
	}

	public List<Person> listDoctors() {

		Role role = roleRepo.findByName(Roles.PROFESIONAL_MEDICO.toString());
		List<Account> accounts = personRepo.getAccounts();
		List<Person> persons = new ArrayList<>();
		for (Account a : accounts) {
			for (Role r : a.getRoles()) {
				if (r.getId() == role.getId())
					persons.add(a.getPerson());
			}
		}
		return persons;
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

	/**
	 * Se guardan los hospitales asignados a un medico
	 * @param id				Id del doctor
	 * @param hospitalList		Lista de hospitales
	 */
	public void saveHospitalsDoctor(int id, List<Hospital> hospitalList) {

		Optional<Person> person = personRepo.findById(id);
		if (person != null && person.isPresent()) {
			List<HospitalDoctor> hospitalesDesasignados = new ArrayList<>();
			List<Integer> hospitalesAsginados = hospitalList.stream().map(Hospital::getId).collect(Collectors.toList());;
			Person p = person.get();
			List<HospitalDoctor> hospitals = hospitalDoctorRepo.getAsignados(p.getId());
			for (HospitalDoctor h : hospitals) {
				if (!hospitalesAsginados.contains(h.getId())) {
					hospitalesDesasignados.add(h);
				}
				hospitalDoctorRepo.delete(h);
			}
			for (Hospital h : hospitalList) {
				HospitalDoctor hd = new HospitalDoctor(new HospitalDoctorPk(id, h.getId()));
				hospitalDoctorRepo.save(hd);
			}
			// Desasignar los pacientes de los hospitales desvinculados al medico
			if (hospitalesDesasignados.size() > 0) {
				for (HospitalDoctor hd : hospitalesDesasignados) {
					List<Integer> patients = patientDoctorRepo.getPatientsAsigns(id, hd.getId().getHospital());
					for (Integer patient : patients) {
						PatientDoctor pd = patientDoctorRepo.getDoctor(patient);
						patientDoctorRepo.delete(pd);
						reasignarDoctor(patient, hd.getId().getHospital());
					}
				}
			}
		}
	}

	private void reasignarDoctor(int patient, int hospital) {

		List<Integer> doctors = hospitalDoctorRepo.getDoctorsByHospital(hospital);
		int idDoctor = doctors.get(0);
		List<Object[]> data = patientDoctorRepo.getDoctorByCountPatients(doctors);
		if (data != null && data.size() > 0) {
			Object[] d = data.get(0);
			idDoctor = (int) d[0];
		}
		PatientDoctorPk patientDoctorPk = new PatientDoctorPk(patient, idDoctor);
		PatientDoctor patientDoctor = new PatientDoctor(patientDoctorPk);
		patientDoctorRepo.save(patientDoctor);
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

	public void sendEmail(String email) throws Exception {

		final UserDetails userDetails = customUserDetailService.loadUserByUsername(email);
		final String jwt = jwtTokenUtil.generateToken(userDetails);

		StringBuilder mensaje = new StringBuilder();
		String url = urlFrontend + "reset-password/" + jwt;
		mensaje.append("<p>Para cambiar la contraseña, haga click en el siguiente ");
		mensaje.append("<a href=\"" + url + "\" target=\"_blank\">link</a></p>");
		emailSender.sendEmail(email, "Recuperar contraseña", mensaje.toString());
	}

	public void resetPassword(String email, String password, String password2) throws Exception {

		if (!password.equals(password2)) throw new Exception("Las contraseñas no coinciden");
		Account account = accountRepo.findByEmail(email);
		account.setPassword(password);
		accountRepo.save(account);
	}

	public String verify(String jwt) throws Exception {

		String email = jwtTokenUtil.getEmailFromJwtToken("Bearer " + jwt);
		Account account = accountRepo.findByEmail(email);
		if (account == null) {
			return "El correo electronico no es valido";
		}
		account.setVerify(true);
		accountRepo.save(account);
		return "Correo validado exitosamente";
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
							latitude = new Double(cellValue);
						} catch (Exception e) { }
					} else if (col == 12) {
						try {
							longitude = new Double(cellValue);
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
				a.setVerify(false);
				a.setPerson(p);
				Account account = accountRepo.save(a);

				Role role = roleRepo.findByName(Roles.PROFESIONAL_MEDICO.toString());
				RoleAccountPk pk = new RoleAccountPk(account.getId(), role.getId());
				RoleAccount roleAccount = new RoleAccount(pk);
				roleAccountRepo.save(roleAccount);

				String mensaje = getMessage(email, password);
				emailSender.send(email, "Validar correo", mensaje);
			}
		}
	}

	private String getMessage(String email, String password) {

		final UserDetails userDetails = customUserDetailService.loadUserByUsername(email);
		final String jwt = jwtTokenUtil.generateToken(userDetails);
		String verificarEmail = urlBackend + "/accounts/verify?jwt=" + jwt;
		StringBuilder mensaje = new StringBuilder();
		mensaje.append("<p>Se creo una cuenta con su correo en ");
		mensaje.append("<a href=\"" + urlFrontend + "\" target=\"_blank\">CroniWeb</a>");
		mensaje.append("</p><p>Verifique su email ");
		mensaje.append("<a href=\"" + verificarEmail + "\" target=\"_blank\">CroniWeb</a></p>");
		mensaje.append("<p>Su password es : " + password + "</p>");
		return mensaje.toString();
	}

	public ByteArrayOutputStream export() throws Exception {

		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("Doctores");
		sheet.setDefaultColumnWidth(20);
		List<Person> doctors = listDoctors();
		int rowCount = 0;

		// Header
		Row rowHeader = sheet.createRow(rowCount++);
		rowHeader.createCell(0).setCellValue("Nro. Documento");
		rowHeader.createCell(1).setCellValue("Nombre");
		rowHeader.createCell(2).setCellValue("Apellido");
		rowHeader.createCell(3).setCellValue("Sexo");
		rowHeader.createCell(4).setCellValue("Fecha de nacimiento");
		rowHeader.createCell(5).setCellValue("Telefono");
		rowHeader.createCell(6).setCellValue("Provincia");
		rowHeader.createCell(7).setCellValue("Direccion");
		rowHeader.createCell(8).setCellValue("Email");
		rowHeader.createCell(9).setCellValue("Password");
		rowHeader.createCell(10).setCellValue("Status");
		rowHeader.createCell(11).setCellValue("Latitud");
		rowHeader.createCell(12).setCellValue("Longitud");

		for (Person doctor : doctors) {
			Row row = sheet.createRow(rowCount++);
			row.createCell(0).setCellValue(doctor.getDocument());
			row.createCell(1).setCellValue(doctor.getName());
			row.createCell(2).setCellValue(doctor.getLastname());

			row.createCell(3).setCellValue(doctor.getSex());
			if (doctor.getBirthDate() != null) {
				SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
				String birthDate = format.format(doctor.getBirthDate());
				row.createCell(4).setCellValue(birthDate);
			}
			row.createCell(5).setCellValue(doctor.getPhone());
			if (doctor.getProvince() != null) {
				row.createCell(6).setCellValue(doctor.getProvince().getName());
			}
			row.createCell(7).setCellValue(doctor.getAddress());
			if (doctor.getAccounts() != null && doctor.getAccounts().size() > 0) {
				Iterator<Account> iterator = doctor.getAccounts().iterator();
				Account account = iterator.next();
				row.createCell(8).setCellValue(account.getEmail());
				row.createCell(9).setCellValue(account.getPassword());
			}
			if (doctor.getStatus() != null) {
				row.createCell(10).setCellValue(doctor.getStatus().getName());
			}
			Location loc = doctor.getLocation();
			if (loc != null) {
				row.createCell(11).setCellValue(loc.getLatitude());
				row.createCell(12).setCellValue(loc.getLongitude());
			}
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