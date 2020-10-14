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
	
INSERT INTO public.person(id, document, name, lastname, phone, sex, location, status)
	VALUES (1, '4204613', 'Jesus', 'Aguilar', '0982912326', 'MASCULINO', 1, 1);

INSERT INTO public.account(id, email, password, person_id, role_id)
	VALUES (1, 'jaaguilarmeza@gmail.com', 'l0k0lte.', 1, 3);
	

INSERT INTO public.location(id, latitude, longitude)
	VALUES (2, -25.342138, -57.511109);
	
INSERT INTO public.person(id, document, name, lastname, phone, sex, location, status)
	VALUES (2, '4653346', 'Juan', 'Duarte', '0961849365', 'FEMENINO', 2, 1);

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
	id, subtitle, title, type)
	VALUES (1, 'Marquelo si posee actualmente temperatura superior a 38 grados.', '¿Tienes fiebre?', 'CHECK');

INSERT INTO public.item(
	id, subtitle, title, type)
	VALUES (2, 'Marquelo si no puede parar de toser.', '¿Tiene tos?', 'CHECK');
	
INSERT INTO public.item(
	id, subtitle, title, type)
	VALUES (3, 'Sensación de falta de aire o de respiración incómoda.', '¿Tiene dificultad para respirar?', 'CHECK');
	
INSERT INTO public.item(
	id, subtitle, title, type)
	VALUES (4, 'Sensación de falta de aire o de respiración incómoda.', '¿Tiene dificultad para respirar?', 'CHECK');
	
INSERT INTO public.item(
	id, subtitle, title, type)
	VALUES (5, 'Sólo se consideran viajes.', '¿Viajó a un lugar con transición local o comunitaria 14 dias antes de presentar síntomas?', 'CHECK');

INSERT INTO public.item(
	id, subtitle, title, type)
	VALUES (6, 'Considere el contacto cercano, como: a 2 metros de distancia, la misma habitación o área (por ejemplo un avión), cuidar, vivir o visitar a una persona, incluso en una sala de atención médica, sin uso de equipo de protección personal.', '¿Tuvo contacto cercano con un caso sospechoso de COVID-19 14 días antes de los síntomas?', 'CHECK');

INSERT INTO public.item(
	id, subtitle, title, type)
	VALUES (7, 'Considere el contacto cercano, como: a 2 metros de distancia, la misma habitación o área (por ejemplo un avión), cuidar, vivir o visitar a una persona, incluso en una sala de atención médica, sin uso de equipo de protección personal.', '¿Tuvo contacto cercano con un caso CONFIRMADO de COVID-19 14 días antes de los síntomas?', 'CHECK');


INSERT INTO public.form(
	id, subtitle, title)
	VALUES (1, 'Debe ingresar los síntomas de las últimas 24hs.', 'Formulario de síntomas');
	
	
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
	id, subtitle, title, type)
	VALUES (8, '¿Usted sufre de esta enfermedad? (en caso afirmativo, seleccione y complete la medicación y dósis que utiliza).', 'Problema del corazón', 'INPUT_TEXT');

INSERT INTO public.item(
	id, subtitle, title, type)
	VALUES (9, '¿Usted sufre de esta enfermedad? (en caso afirmativo, seleccione y complete la medicación y dósis que utiliza).', 'Problema renal', 'INPUT_TEXT');

INSERT INTO public.item(
	id, subtitle, title, type)
	VALUES (10, '¿Usted sufre de esta enfermedad? (en caso afirmativo, seleccione y complete la medicación y dósis que utiliza).', 'Hipertensión arterial', 'INPUT_TEXT');

INSERT INTO public.item(
	id, subtitle, title, type)
	VALUES (11, '¿Usted sufre de esta enfermedad? (en caso afirmativo, seleccione y complete la medicación y dósis que utiliza).', 'Problema de tiroides', 'INPUT_TEXT');

INSERT INTO public.item(
	id, subtitle, title, type)
	VALUES (12, '¿Usted sufre de esta enfermedad? (en caso afirmativo, seleccione y complete la medicación y dósis que utiliza).', 'Diabetes tipo 1', 'INPUT_TEXT');

INSERT INTO public.item(
	id, subtitle, title, type)
	VALUES (13, '¿Usted sufre de esta enfermedad? (en caso afirmativo, seleccione y complete la medicación y dósis que utiliza).', 'Diabetes tipo 2', 'INPUT_TEXT');

INSERT INTO public.item(
	id, subtitle, title, type)
	VALUES (14, '¿Usted sufre de esta enfermedad? (en caso afirmativo, seleccione y complete la medicación y dósis que utiliza).', 'Diabetes gestacional', 'INPUT_TEXT');

INSERT INTO public.item(
	id, subtitle, title, type)
	VALUES (15, '¿Usted sufre de esta enfermedad? (en caso afirmativo, seleccione y complete la medicación y dósis que utiliza).', 'Problemas pulmonares', 'INPUT_TEXT');

INSERT INTO public.item(
	id, subtitle, title, type)
	VALUES (16, '¿Usted sufre de esta enfermedad? (en caso afirmativo, seleccione y complete la medicación y dósis que utiliza).', 'Asma / Rinitis', 'INPUT_TEXT');

INSERT INTO public.item(
	id, subtitle, title, type)
	VALUES (17, '¿Usted sufre de esta enfermedad? (en caso afirmativo, seleccione y complete la medicación y dósis que utiliza).', 'Problemas reumatólogos', 'INPUT_TEXT');

INSERT INTO public.item(
	id, subtitle, title, type)
	VALUES (18, '¿Usted sufre de esta enfermedad? (en caso afirmativo, seleccione y complete la medicación y dósis que utiliza).', 'Problemas de autoinmunidad', 'INPUT_TEXT');

INSERT INTO public.item(
	id, subtitle, title, type)
	VALUES (19, '¿Usted sufre de esta enfermedad? (en caso afirmativo, seleccione y complete la medicación y dósis que utiliza).', 'HIV / Sida', 'INPUT_TEXT');

INSERT INTO public.item(
	id, subtitle, title, type)
	VALUES (20, '¿Usted sufre de esta enfermedad? (en caso afirmativo, seleccione y complete la medicación y dósis que utiliza).', 'Cáncer', 'INPUT_TEXT');

INSERT INTO public.item(
	id, subtitle, title, type)
	VALUES (21, '¿Usted sufre de esta enfermedad? (en caso afirmativo, seleccione y complete la medicación y dósis que utiliza).', 'Otros (especificar)', 'INPUT_TEXT');
	

INSERT INTO public.form(
	id, subtitle, title)
	VALUES (2, 'Debe ingresar las enfermedades base que posee.', 'Formulario de Enfermedades Base');
	
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
