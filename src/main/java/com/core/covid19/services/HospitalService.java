package com.core.covid19.services;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import com.core.covid19.models.entities.*;
import com.core.covid19.repos.*;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core.covid19.models.entities.location.HospitalDistance;
import com.core.covid19.models.responses.HospitalsResponse;
import org.springframework.web.multipart.MultipartFile;

@Service
public class HospitalService {

	@Autowired
	private AccountRepo accountRepo;

	@Autowired
	private HospitalRepo hospitalRepo;

	@Autowired
	private ProvinceRepo provinceRepo;

	@Autowired
	private LocationRepo locationRepo;

	@Autowired
	private DistrictRepo districtRepo;

	private Integer maxHospitals = 10;

	public List<Hospital> getAll(){
		return hospitalRepo.findAll();
	}

	public HospitalsResponse getTenCloser(String email) {
		Account account = accountRepo.findByEmail(email);

		if(account.getPerson() == null) return null;

		List<Hospital> hospitals = hospitalRepo.findAll().stream().filter(hospital -> hospital.getLocation() != null).collect(Collectors.toList());

		List<HospitalDistance> hospitalsDistance = new ArrayList<HospitalDistance>();

		for(Hospital hospital : hospitals)
			hospitalsDistance.add(new HospitalDistance(distanceOf(account.getPerson().getLocation(), hospital.getLocation()), hospital));

		hospitalsDistance = hospitalsDistance.stream().sorted().collect(Collectors.toList());

		return new HospitalsResponse(account.getPerson(), tenClosers(hospitalsDistance));
	}

	public void cargar(MultipartFile file) throws IOException, InvalidFormatException {

		Workbook workbook = WorkbookFactory.create(file.getInputStream());
		Sheet sheet = workbook.getSheetAt(0);
		DataFormatter dataFormatter = new DataFormatter();
		Iterator<Row> rowIterator = sheet.rowIterator();

		HashMap<Integer, Province> provinciasCargadas = new HashMap<>();
		Set<Integer> provinces = new HashSet<>();
		HashMap<Integer, District> distritosCargados = new HashMap<>();
		Set<Integer> districts = new HashSet<>();
		int rowNumber = 0;

		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			if (rowNumber == 0) {
				rowNumber++;
			} else {
				Iterator<Cell> cellIterator = row.cellIterator();
				int col = 0;
				Integer idProvince = 0;
				String nameProvince = "";
				Integer idDistrict = 0;
				String nameDistrict = "";
				String code = "";
				String address = "";
				String type = "";
				String area = "";
				Boolean state = null;
				String phone = "";
				String director = "";
				Double latitude = null;
				Double longitude = null;

				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					String cellValue = dataFormatter.formatCellValue(cell);
					if (col == 0) idProvince = new Integer(cellValue);
					else if (col == 1) nameProvince = cellValue;
					else if (col == 2) idDistrict = new Integer(cellValue);
					else if (col == 3) nameDistrict = cellValue;
					else if (col == 5) code = cellValue;
					else if (col == 6) type = cellValue;
					else if (col == 7) area = cellValue;
					else if (col == 8) {
						if (cellValue != null) {
							if (cellValue.equalsIgnoreCase("Si"))
								state = true;
							else
								state = false;
						}
					}
					else if (col == 9) phone = cellValue;
					else if (col == 10) address = cellValue;
					else if (col == 11) director = cellValue;
					else if (col == 12)
						try {
							longitude = new Double(cellValue);
						} catch (Exception e) {}
					else if (col == 13)
						try {
							latitude = new Double(cellValue);
						} catch (Exception e) {}
					col++;
				}
				// Cargar provincia si aun no existe
				if (!provinces.contains(idProvince)) {
					provinces.add(idProvince);
					Province p = new Province();
					p.setId(idProvince);
					p.setName(nameProvince);
					provinceRepo.save(p);
					provinciasCargadas.put(idProvince, p);
					distritosCargados.clear();
					distritosCargados = new HashMap<>();
					districts.clear();
					districts = new HashSet<>();
				}
				Province p = provinciasCargadas.get(idProvince);
				// Cargar distrito si aun no existe
				if (!districts.contains(idDistrict)) {
					districts.add(idDistrict);
					District d = new District();
					d.setName(nameDistrict);
					d.setProvince(p);
					districtRepo.save(d);
					distritosCargados.put(idDistrict, d);
				}
				District d = distritosCargados.get(idDistrict);
				Hospital h = new Hospital();
				h.setName(type);
				h.setArea(area);
				h.setCode(code);
				h.setType(type);
				h.setAddress(address);
				h.setDirector(director);
				h.setPhone(phone);
				h.setState(state);
				h.setDistrict(d);
				if (latitude != null && longitude != null) {
					Location loc = new Location();
					loc.setLatitude(latitude);
					loc.setLongitude(longitude);
					locationRepo.save(loc);
					h.setLocation(loc);
				}
				hospitalRepo.save(h);
			}
		}
	}

	private List<Hospital> tenClosers(List<HospitalDistance> closers) {
		List<Hospital> tenHospitalClosers = new ArrayList<Hospital>();
		for(HospitalDistance hospitalDistance : closers)
			if(tenHospitalClosers.size() < maxHospitals)
				tenHospitalClosers.add(hospitalDistance.getHospital());
			else break;

		return tenHospitalClosers;
	}

	private Double distanceOf(Location a, Location b) {
		// TODO para que no explote
		if (a == null || b == null)
			return 0.0;
		return Math.sqrt(square(a.getLatitude() - b.getLatitude()) + square(a.getLongitude() - b.getLongitude()));
	}

	private double square(double n) {
		return n*n;
	}
}