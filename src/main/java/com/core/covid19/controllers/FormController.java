package com.core.covid19.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.core.covid19.models.responses.*;
import com.core.covid19.authentication.util.JwtUtil;
import com.core.covid19.services.FormService;

@RestController
@RequestMapping("/forms")
public class FormController {

	@Autowired
	private FormService formService;

	@Autowired
	private JwtUtil jwtUtil;

	/**
	 * Obtiene la lista de formularios
	 * @return
	 */
	@GetMapping
	public PersonFormsResponse listAll(@RequestParam(value = "idForm", required = false) Integer idForm) {
		if (idForm != null) {
			return formService.get(idForm);
		}
		return formService.findAll();
	}

	/**
	 * Guarda los datos del Formulario
	 * @param authorization
	 * @param data
	 */
	@PostMapping(value="/save")
	public void save(@RequestHeader("Authorization") String authorization,
					   @RequestBody FormItemResponse data) {
		formService.save(jwtUtil.getEmailFromJwtToken(authorization), data);
	}

	/**
	 * Lista todos los items
	 * @return
	 */
	@GetMapping(value="/items")
	public List<QuestionResponse> getItems(@RequestParam(value = "idForm", required = false) Integer idForm) {
		if (idForm != null) {
			return formService.getItems(idForm);
		}
		return formService.getItems();
	}

	/**
	 * Obtiene los datos de un item
	 * @param id
	 * @return
	 */
	@GetMapping(value="/items/{id}")
	public QuestionResponse getItem(@PathVariable("id") Integer id) {
		return formService.getItem(id);
	}

	/**
	 * Crea un nuevo Item
	 * @param authorization
	 * @param data
	 */
	@PostMapping(value="/items")
	public void saveItem(@RequestHeader("Authorization") String authorization,
						 @RequestBody QuestionResponse data) {
		formService.saveItem(jwtUtil.getEmailFromJwtToken(authorization), data);
	}

	/**
	 * Actualiza los datos del item
	 * @param authorization
	 * @param id
	 * @param data
	 */
	@PutMapping(value="/items/{id}")
	public void saveItem(@RequestHeader("Authorization") String authorization,
						 @PathVariable("id") Integer id,
						 @RequestBody QuestionResponse data) {
		formService.modifyItem(jwtUtil.getEmailFromJwtToken(authorization), id, data);
	}

	/**
	 * Obtiene los items del formulario
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/{id}/questions")
	public List<QuestionResponse> getAnswer(@PathVariable("id") Integer id) {
		return formService.getAnswer(id);
	}

	/**
	 * Obtiene los formularios del usuario logueado
	 * @param authorization
	 * @return
	 */
	@GetMapping(value="/my")
	public PersonFormsResponse listAllByEmail(@RequestHeader("Authorization") String authorization) {
		return formService.findAllByPersonEmail(jwtUtil.getEmailFromJwtToken(authorization));
	}

	/**
	 * Obtiene los formularios del usuario
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/{id}")
	public PersonFormsResponse listAllById(@PathVariable("id") Integer id) {
		return formService.findAllById(id);
	}

	@PostMapping
	public void asign(@RequestHeader("Authorization") String authorization) {
		formService.addDefaultFormsToPerson(jwtUtil.getEmailFromJwtToken(authorization));
	}

}
