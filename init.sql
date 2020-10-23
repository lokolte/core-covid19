INSERT INTO public.role(id, name)
	VALUES (1, 'CIVIL');
INSERT INTO public.role(id, name)
	VALUES (2, 'PROFESIONAL_MEDICO');
INSERT INTO public.role(id, name)
	VALUES (3, 'ADMIN');

INSERT INTO public.status(id, name)
	VALUES (1, 'HEALTHY');
INSERT INTO public.status(id, name)
	VALUES (2, 'SUSPECT');
INSERT INTO public.status(id, name)
	VALUES (3, 'INFECTED');
INSERT INTO public.status(id, name)
	VALUES (4, 'RECOVERED');


INSERT INTO public.location(id, latitude, longitude)
	VALUES (1, -25.326650, -57.559925);
	
INSERT INTO public.person(id, document, name, lastname, birth_date, phone, sex, address, location, status)
	VALUES (1, '4204613', 'Jesus', 'Aguilar', now(), '0982912326', 'MASCULINO', 'Juan de Garay 1634', 1, 1);

INSERT INTO public.account(id, email, password, person_id, role_id)
	VALUES (1, 'jaaguilarmeza@gmail.com', 'l0k0lte.', 1, 3);
	

INSERT INTO public.location(id, latitude, longitude)
	VALUES (2, -25.342138, -57.511109);
	
INSERT INTO public.person(id, document, name, lastname, birth_date, phone, sex, address, location, status)
	VALUES (2, '4653346', 'Veronica', 'Gayoso', now(), '0981719893', 'FEMENINO', 'Juan de Garay 1634', 2, 1);

INSERT INTO public.account(id, email, password, person_id, role_id)
	VALUES (2, 'vritogayoso@gmail.com', 'vrito', 2, 1);


INSERT INTO public.location(id, latitude, longitude)
	VALUES (3, -25.295729, -57.625436);

INSERT INTO public.contact(
	id, contact_date, person_id1, person_id2, contact_location)
	VALUES (1, now(), 1, 2, 3);
	
///// para formularios
// FORMULARIO DE SINTOMAS
INSERT INTO public.item(
	id, subtitle, title, type, order_level)
	VALUES (1, 'Marquelo si posee actualmente temperatura superior a 38 grados.', '¿Tienes fiebre?', 'CHECK', 1);

INSERT INTO public.item(
	id, subtitle, title, type, order_level)
	VALUES (2, 'Marquelo si no puede parar de toser.', '¿Tiene tos?', 'CHECK', 2);
	
INSERT INTO public.item(
	id, subtitle, title, type, order_level)
	VALUES (3, 'Sensación de falta de aire o de respiración incómoda.', '¿Tiene dificultad para respirar?', 'CHECK', 3);
	
INSERT INTO public.item(
	id, subtitle, title, type, order_level)
	VALUES (4, 'Sólo se consideran viajes.', '¿Viajó a un lugar con transición local o comunitaria 14 dias antes de presentar síntomas?', 'CHECK', 4);

INSERT INTO public.item(
	id, subtitle, title, type, order_level)
	VALUES (5, 'Considere el contacto cercano, como: a 2 metros de distancia, la misma habitación o área (por ejemplo un avión), cuidar, vivir o visitar a una persona, incluso en una sala de atención médica, sin uso de equipo de protección personal.', '¿Tuvo contacto cercano con un caso sospechoso de COVID-19 14 días antes de los síntomas?', 'CHECK', 5);

INSERT INTO public.item(
	id, subtitle, title, type, order_level)
	VALUES (6, 'Considere el contacto cercano, como: a 2 metros de distancia, la misma habitación o área (por ejemplo un avión), cuidar, vivir o visitar a una persona, incluso en una sala de atención médica, sin uso de equipo de protección personal.', '¿Tuvo contacto cercano con un caso CONFIRMADO de COVID-19 14 días antes de los síntomas?', 'CHECK', 6);

INSERT INTO public.item(
	id, subtitle, title, type, order_level)
	VALUES (7, 'Debe marcar la opción sólo si se ha realizado el hisopado.', '¿Se ha hecho la prueba del COVID-19?', 'CHECK', 7);


INSERT INTO public.form(
	id, subtitle, title, order_level)
	VALUES (1, 'Debe ingresar los síntomas de las últimas 24hs.', 'Formulario de síntomas', 1);
	
INSERT INTO public.form_items(
	form_id, item_id)
	VALUES (1, 1);

INSERT INTO public.form_items(
	form_id, item_id)
	VALUES (1, 2);
	
INSERT INTO public.form_items(
	form_id, item_id)
	VALUES (1, 3);
	
INSERT INTO public.form_items(
	form_id, item_id)
	VALUES (1, 4);
	
INSERT INTO public.form_items(
	form_id, item_id)
	VALUES (1, 5);
	
INSERT INTO public.form_items(
	form_id, item_id)
	VALUES (1, 6);
	
INSERT INTO public.form_items(
	form_id, item_id)
	VALUES (1, 7);
	
	
INSERT INTO public.person_forms(
	person_id, form_id)
	VALUES (1, 1);

// FORMULARIO PARA ENFERMEDADES BASE
INSERT INTO public.item(
	id, subtitle, title, type, order_level)
	VALUES (8, '¿Usted sufre de esta enfermedad? (en caso afirmativo, seleccione y complete la medicación y dósis que utiliza).', 'Problema del corazón', 'INPUT_TEXT', 1);

INSERT INTO public.item(
	id, subtitle, title, type, order_level)
	VALUES (9, '¿Usted sufre de esta enfermedad? (en caso afirmativo, seleccione y complete la medicación y dósis que utiliza).', 'Problema renal', 'INPUT_TEXT', 2);

INSERT INTO public.item(
	id, subtitle, title, type, order_level)
	VALUES (10, '¿Usted sufre de esta enfermedad? (en caso afirmativo, seleccione y complete la medicación y dósis que utiliza).', 'Hipertensión arterial', 'INPUT_TEXT', 3);

INSERT INTO public.item(
	id, subtitle, title, type, order_level)
	VALUES (11, '¿Usted sufre de esta enfermedad? (en caso afirmativo, seleccione y complete la medicación y dósis que utiliza).', 'Problema de tiroides', 'INPUT_TEXT', 4);

INSERT INTO public.item(
	id, subtitle, title, type, order_level)
	VALUES (12, '¿Usted sufre de esta enfermedad? (en caso afirmativo, seleccione y complete la medicación y dósis que utiliza).', 'Diabetes tipo 1', 'INPUT_TEXT', 5);

INSERT INTO public.item(
	id, subtitle, title, type, order_level)
	VALUES (13, '¿Usted sufre de esta enfermedad? (en caso afirmativo, seleccione y complete la medicación y dósis que utiliza).', 'Diabetes tipo 2', 'INPUT_TEXT', 6);

INSERT INTO public.item(
	id, subtitle, title, type, order_level)
	VALUES (14, '¿Usted sufre de esta enfermedad? (en caso afirmativo, seleccione y complete la medicación y dósis que utiliza).', 'Diabetes gestacional', 'INPUT_TEXT', 7);

INSERT INTO public.item(
	id, subtitle, title, type, order_level)
	VALUES (15, '¿Usted sufre de esta enfermedad? (en caso afirmativo, seleccione y complete la medicación y dósis que utiliza).', 'Problemas pulmonares', 'INPUT_TEXT', 8);

INSERT INTO public.item(
	id, subtitle, title, type, order_level)
	VALUES (16, '¿Usted sufre de esta enfermedad? (en caso afirmativo, seleccione y complete la medicación y dósis que utiliza).', 'Asma / Rinitis', 'INPUT_TEXT', 9);

INSERT INTO public.item(
	id, subtitle, title, type, order_level)
	VALUES (17, '¿Usted sufre de esta enfermedad? (en caso afirmativo, seleccione y complete la medicación y dósis que utiliza).', 'Problemas reumatólogos', 'INPUT_TEXT', 10);

INSERT INTO public.item(
	id, subtitle, title, type, order_level)
	VALUES (18, '¿Usted sufre de esta enfermedad? (en caso afirmativo, seleccione y complete la medicación y dósis que utiliza).', 'Problemas de autoinmunidad', 'INPUT_TEXT', 11);

INSERT INTO public.item(
	id, subtitle, title, type, order_level)
	VALUES (19, '¿Usted sufre de esta enfermedad? (en caso afirmativo, seleccione y complete la medicación y dósis que utiliza).', 'HIV / Sida', 'INPUT_TEXT', 12);

INSERT INTO public.item(
	id, subtitle, title, type, order_level)
	VALUES (20, '¿Usted sufre de esta enfermedad? (en caso afirmativo, seleccione y complete la medicación y dósis que utiliza).', 'Cáncer', 'INPUT_TEXT', 13);

INSERT INTO public.item(
	id, subtitle, title, type, order_level)
	VALUES (21, '¿Usted sufre de esta enfermedad? (en caso afirmativo, seleccione y complete la medicación y dósis que utiliza).', 'Otros (especificar)', 'INPUT_TEXT', 14);
	

INSERT INTO public.form(
	id, subtitle, title, order_level)
	VALUES (2, 'Debe ingresar las enfermedades base que posee.', 'Formulario de Enfermedades Base', 2);
	
INSERT INTO public.form_items(
	form_id, item_id)
	VALUES (2, 8);

INSERT INTO public.form_items(
	form_id, item_id)
	VALUES (2, 9);
	
INSERT INTO public.form_items(
	form_id, item_id)
	VALUES (2, 10);
	
INSERT INTO public.form_items(
	form_id, item_id)
	VALUES (2, 11);
	
INSERT INTO public.form_items(
	form_id, item_id)
	VALUES (2, 12);
	
INSERT INTO public.form_items(
	form_id, item_id)
	VALUES (2, 13);
	
INSERT INTO public.form_items(
	form_id, item_id)
	VALUES (2, 14);
	
INSERT INTO public.form_items(
	form_id, item_id)
	VALUES (2, 15);
	
INSERT INTO public.form_items(
	form_id, item_id)
	VALUES (2, 16);
	
INSERT INTO public.form_items(
	form_id, item_id)
	VALUES (2, 17);
	
INSERT INTO public.form_items(
	form_id, item_id)
	VALUES (2, 18);
	
INSERT INTO public.form_items(
	form_id, item_id)
	VALUES (2, 19);
	
INSERT INTO public.form_items(
	form_id, item_id)
	VALUES (2, 20);
	
INSERT INTO public.form_items(
	form_id, item_id)
	VALUES (2, 21);
	
	
INSERT INTO public.person_forms(
	person_id, form_id)
	VALUES (1, 2);
	

// FORMULARIO HOSPITALARIO
INSERT INTO public.item(
	id, subtitle, title, type, order_level)
	VALUES (22, 'En caso afirmativo, especifique donde y la medicación otorgada en dicha consulta.', '¿Ha consultado con algún médico?', 'INPUT_TEXT', 1);

INSERT INTO public.item(
	id, subtitle, title, type, order_level)
	VALUES (23, 'En caso afirmativo, especifique donde y la medicación.', '¿Retira medicamentos de algún lugar habitualmente?', 'INPUT_TEXT', 2);

INSERT INTO public.item(
	id, subtitle, title, type, order_level)
	VALUES (24, 'Especifique.', '¿Cómo se llama el Hospital público, Centro de Salud o Atención Primaria de Salud que le queda más cerca de su casa?', 'INPUT_TEXT', 3);

INSERT INTO public.form(
	id, subtitle, title, order_level)
	VALUES (3, 'Debe ingresar los datos hospitalarios.', 'Formulario Hospitalario', 3);

INSERT INTO public.form_items(
	form_id, item_id)
	VALUES (3, 22);
	
INSERT INTO public.form_items(
	form_id, item_id)
	VALUES (3, 23);
	
INSERT INTO public.form_items(
	form_id, item_id)
	VALUES (3, 24);

	
INSERT INTO public.person_forms(
	person_id, form_id)
	VALUES (1, 3);
	
	