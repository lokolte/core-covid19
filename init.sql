INSERT INTO public.role(id, name) VALUES
    (1, 'CIVIL'),
	(2, 'PROFESIONAL_MEDICO'),
	(3, 'ADMIN'),
    (4, 'COORDINADOR');

INSERT INTO public.status(id, name) VALUES
    (1, 'HEALTHY'),
	(2, 'SUSPECT'),
	(3, 'INFECTED'),
	(4, 'RECOVERED');


INSERT INTO public.location(id, latitude, longitude)
	VALUES (1, -25.326650, -57.559925);

INSERT INTO public.person
    (id, address, birth_date, document, lastname, name, phone, sex, location, province, status) VALUES
    (1, 'Juan de Garay 1634', now(), '4204613', 'Aguilar', 'Jesus', '0982912326', 'MASCULINO', 1, NULL, 1);

INSERT INTO public.account(id, email, password, verify, person_id)
	VALUES (1, 'jaaguilarmeza@gmail.com', 'l0k0lte.', true, 1);

INSERT INTO public.role_account(
    account_id, role_id)
VALUES (1, 3);


INSERT INTO public.location(id, latitude, longitude)
VALUES (2, -25.328537, -57.609223);

INSERT INTO public.person
(id, address, birth_date, document, lastname, name, phone, sex, location, province, status) VALUES
(2, 'De las Nieves 2060, Lambare', now(), '797148', 'Legal', 'Horacio', '0971336246', 'MASCULINO', 2, NULL, 1);

INSERT INTO public.account(id, email, password, verify, person_id)
VALUES (2, 'horacio.legal@gmail.com', '.h0r4c10.', true, 2);

INSERT INTO public.role_account(
    account_id, role_id)
VALUES (2, 3);


INSERT INTO public.location(id, latitude, longitude)
VALUES (3, -25.328637, -57.609223);

INSERT INTO public.person
(id, address, birth_date, document, lastname, name, phone, sex, location, province, status) VALUES
(3, 'Avenida siempre viva', now(), '123456', 'Villalba', 'Cynthia', '0981123456', 'FEMENINO', 3, NULL, 1);

INSERT INTO public.account(id, email, password, verify, person_id)
VALUES (3, 'cvillalba@pol.una.py', '.cynth14.', true, 3);

INSERT INTO public.role_account(
    account_id, role_id)
VALUES (3, 3);


INSERT INTO public.location(id, latitude, longitude)
VALUES (4, -25.3265581,-57.5792112);

INSERT INTO public.person
(id, address, birth_date, document, lastname, name, phone, sex, location, province, status) VALUES
(4, 'Yporã N° 1014', now(), '4697163', 'Derlis', 'Gómez', '0994289789', 'MASCULINO', 4, NULL, 1);

INSERT INTO public.account(id, email, password, verify, person_id)
VALUES (4, 'derlisrgomez@gmail.com', '.d3rl1s.', true, 4);

INSERT INTO public.role_account(
    account_id, role_id)
VALUES (4, 3);




INSERT INTO public.item(
	id, subtitle, title, type, order_level)
	VALUES (1, 'Marquelo si posee actualmente temperatura superior a 38 grados.', '¿Tiene fiebre?', 'CHECK', 1);

INSERT INTO public.item(
	id, subtitle, title, type, order_level)
	VALUES (2, 'Marquelo si no puede parar de toser.', '¿Tiene tos?', 'CHECK', 2);
	
INSERT INTO public.item(
	id, subtitle, title, type, order_level)
	VALUES (3, 'Sensación de falta de aire o de respiración incómoda.', '¿Tiene dificultad para respirar?', 'CHECK', 3);
	
INSERT INTO public.item(
	id, subtitle, title, type, order_level)
	VALUES (4, 'Marquelo si tiene dolor de garganta.', '¿Tiene dolor de garganta?', 'CHECK', 4);

INSERT INTO public.item(
	id, subtitle, title, type, order_level)
	VALUES (5, 'Marquelo si ha perdido el olfato.', '¿Ha perdido el olfato?', 'CHECK', 5);
	
INSERT INTO public.item(
	id, subtitle, title, type, order_level)
	VALUES (6, 'Marquelo si ha tenido ojos rojos.', '¿Ha tenido ojos rojos?', 'CHECK', 6);
	
INSERT INTO public.item(
	id, subtitle, title, type, order_level)
	VALUES (7, 'Marquelo si ha tenido diarrea.', '¿Ha tenido diarrea?', 'CHECK', 7);
	
INSERT INTO public.item(
	id, subtitle, title, type, order_level)
	VALUES (8, 'Marquelo si ha tenido erupciones en las manos o pies.', '¿Ha tenido erupciones en las manos o pies?', 'CHECK', 8);
	
INSERT INTO public.item(
	id, subtitle, title, type, order_level)
	VALUES (9, 'Marquelo si tiene dolor de cabeza.', '¿Tiene dolor de cabeza?', 'CHECK', 9);
	
INSERT INTO public.item(
	id, subtitle, title, type, order_level)
	VALUES (10, 'Marquelo si tiene dolores de huesos y músculos.', '¿Tiene dolores de huesos y músculos?', 'CHECK', 10);
	
INSERT INTO public.item(
	id, subtitle, title, type, order_level)
	VALUES (11, 'Marquelo si tiene dolor detrás de los oídos.', '¿Tiene dolor detrás de los oídos?', 'CHECK', 11);
	
INSERT INTO public.item(
	id, subtitle, title, type, order_level)
	VALUES (12, 'Marquelo si tiene alteración del gusto.', '¿Tiene alteración del gusto?', 'CHECK', 12);

INSERT INTO public.item(
	id, subtitle, title, type, order_level)
	VALUES (13, 'Sólo se consideran viajes fuera del país.', '¿Viajó recientemente al exterior?', 'CHECK', 13);

INSERT INTO public.item(
	id, subtitle, title, type, order_level)
	VALUES (14, 'Considere el contacto cercano, como: a 2 metros de distancia, la misma habitación o área (por ejemplo un avión), cuidar, vivir o visitar a una persona, incluso en una sala de atención médica, sin uso de equipo de protección personal.', '¿Tuvo contacto cercano con un caso sospechoso de COVID-19 14 días antes de los síntomas?', 'CHECK', 14);

INSERT INTO public.item(
	id, subtitle, title, type, order_level)
	VALUES (15, 'Considere el contacto cercano, como: a 2 metros de distancia, la misma habitación o área (por ejemplo un avión), cuidar, vivir o visitar a una persona, incluso en una sala de atención médica, sin uso de equipo de protección personal.', '¿Tuvo contacto cercano con un caso CONFIRMADO de COVID-19 14 días antes de los síntomas?', 'CHECK', 15);

INSERT INTO public.item(
	id, subtitle, title, type, order_level)
	VALUES (16, 'Debe marcar la opción sólo si se ha realizado el hisopado.', '¿Se ha hecho la prueba del COVID-19?', 'CHECK', 16);
	
INSERT INTO public.item(
	id, subtitle, title, type, order_level)
	VALUES (17, '¿Hace cuanto tiempo?', '¿Tuvo COVID-19?', 'INPUT_TEXT', 17);

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

INSERT INTO public.form_items(
	form_id, item_id)
	VALUES (1, 8);

INSERT INTO public.form_items(
	form_id, item_id)
	VALUES (1, 9);
	
INSERT INTO public.form_items(
	form_id, item_id)
	VALUES (1, 10);
	
INSERT INTO public.form_items(
	form_id, item_id)
	VALUES (1, 11);
	
INSERT INTO public.form_items(
	form_id, item_id)
	VALUES (1, 12);
	
INSERT INTO public.form_items(
	form_id, item_id)
	VALUES (1, 13);
	
INSERT INTO public.form_items(
	form_id, item_id)
	VALUES (1, 14);
		
INSERT INTO public.form_items(
	form_id, item_id)
	VALUES (1, 15);
	
INSERT INTO public.form_items(
	form_id, item_id)
	VALUES (1, 16);
	
INSERT INTO public.form_items(
	form_id, item_id)
	VALUES (1, 17);
	
INSERT INTO public.person_forms(
	person_id, form_id)
	VALUES (1, 1);





INSERT INTO public.item(
	id, subtitle, title, type, order_level)
	VALUES (18, '¿Usted sufre de esta enfermedad? (en caso afirmativo, seleccione y complete la medicación y dósis que utiliza).', 'Problema del corazón', 'INPUT_TEXT', 1);

INSERT INTO public.item(
	id, subtitle, title, type, order_level)
	VALUES (19, '¿Usted sufre de esta enfermedad? (en caso afirmativo, seleccione y complete la medicación y dósis que utiliza).', 'Problema renal', 'INPUT_TEXT', 2);

INSERT INTO public.item(
	id, subtitle, title, type, order_level)
	VALUES (20, '¿Usted sufre de esta enfermedad? (en caso afirmativo, seleccione y complete la medicación y dósis que utiliza).', 'Hipertensión arterial', 'INPUT_TEXT', 3);

INSERT INTO public.item(
	id, subtitle, title, type, order_level)
	VALUES (21, '¿Usted sufre de esta enfermedad? (en caso afirmativo, seleccione y complete la medicación y dósis que utiliza).', 'Problema de tiroides', 'INPUT_TEXT', 4);

INSERT INTO public.item(
	id, subtitle, title, type, order_level)
	VALUES (22, '¿Usted sufre de esta enfermedad? (en caso afirmativo, seleccione y complete la medicación y dósis que utiliza).', 'Diabetes tipo 1', 'INPUT_TEXT', 5);

INSERT INTO public.item(
	id, subtitle, title, type, order_level)
	VALUES (23, '¿Usted sufre de esta enfermedad? (en caso afirmativo, seleccione y complete la medicación y dósis que utiliza).', 'Diabetes tipo 2', 'INPUT_TEXT', 6);

INSERT INTO public.item(
	id, subtitle, title, type, order_level)
	VALUES (24, '¿Usted sufre de esta enfermedad? (en caso afirmativo, seleccione y complete la medicación y dósis que utiliza).', 'Diabetes gestacional', 'INPUT_TEXT', 7);

INSERT INTO public.item(
	id, subtitle, title, type, order_level)
	VALUES (25, '¿Usted sufre de esta enfermedad? (en caso afirmativo, seleccione y complete la medicación y dósis que utiliza).', 'Problemas pulmonares', 'INPUT_TEXT', 8);

INSERT INTO public.item(
	id, subtitle, title, type, order_level)
	VALUES (26, '¿Usted sufre de esta enfermedad? (en caso afirmativo, seleccione y complete la medicación y dósis que utiliza).', 'Asma / Rinitis', 'INPUT_TEXT', 9);

INSERT INTO public.item(
	id, subtitle, title, type, order_level)
	VALUES (27, '¿Usted sufre de esta enfermedad? (en caso afirmativo, seleccione y complete la medicación y dósis que utiliza).', 'Problemas de autoinmunidad', 'INPUT_TEXT', 10);

INSERT INTO public.item(
	id, subtitle, title, type, order_level)
	VALUES (28, '¿Usted sufre de esta enfermedad? (en caso afirmativo, seleccione y complete la medicación y dósis que utiliza).', 'HIV / Sida', 'INPUT_TEXT', 11);

INSERT INTO public.item(
	id, subtitle, title, type, order_level)
	VALUES (29, '¿Usted sufre de esta enfermedad? (en caso afirmativo, seleccione y complete la medicación y dósis que utiliza).', 'Cáncer', 'INPUT_TEXT', 12);

INSERT INTO public.item(
	id, subtitle, title, type, order_level)
	VALUES (30, '¿Usted sufre de esta enfermedad? (en caso afirmativo, seleccione y complete la medicación y dósis que utiliza).', 'Otros (especificar)', 'INPUT_TEXT', 13);
	

INSERT INTO public.form(
	id, subtitle, title, order_level)
	VALUES (2, 'Debe ingresar las enfermedades base que posee.', 'Formulario de Enfermedades Base', 2);
	
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
	
INSERT INTO public.form_items(
	form_id, item_id)
	VALUES (2, 22);
	
INSERT INTO public.form_items(
	form_id, item_id)
	VALUES (2, 23);
	
INSERT INTO public.form_items(
	form_id, item_id)
	VALUES (2, 24);
	
INSERT INTO public.form_items(
	form_id, item_id)
	VALUES (2, 25);
	
INSERT INTO public.form_items(
	form_id, item_id)
	VALUES (2, 26);
	
INSERT INTO public.form_items(
	form_id, item_id)
	VALUES (2, 27);
	
INSERT INTO public.form_items(
	form_id, item_id)
	VALUES (2, 28);
	
INSERT INTO public.form_items(
	form_id, item_id)
	VALUES (2, 29);
	
INSERT INTO public.form_items(
	form_id, item_id)
	VALUES (2, 30);
	
INSERT INTO public.person_forms(
	person_id, form_id)
	VALUES (1, 2);
	
	
	
	

INSERT INTO public.item(
	id, subtitle, title, type, order_level)
	VALUES (31, 'En caso afirmativo, especifique donde y la medicación otorgada en dicha consulta.', '¿Ha consultado con algún médico?', 'INPUT_TEXT', 1);

INSERT INTO public.item(
	id, subtitle, title, type, order_level)
	VALUES (32, 'En caso afirmativo, especifique donde y la medicación.', '¿Retira medicamentos de algún lugar habitualmente?', 'INPUT_TEXT', 2);

INSERT INTO public.item(
	id, subtitle, title, type, order_level)
	VALUES (33, 'Especifique.', '¿Cómo se llama el Hospital público, Centro de Salud o Atención Primaria de Salud que le queda más cerca de su casa?', 'INPUT_TEXT', 3);

INSERT INTO public.form(
	id, subtitle, title, order_level)
	VALUES (3, 'Debe ingresar los datos hospitalarios.', 'Formulario Logístico', 3);

INSERT INTO public.form_items(
	form_id, item_id)
	VALUES (3, 31);
	
INSERT INTO public.form_items(
	form_id, item_id)
	VALUES (3, 32);
	
INSERT INTO public.form_items(
	form_id, item_id)
	VALUES (3, 33);

	
INSERT INTO public.person_forms(
	person_id, form_id)
	VALUES (1, 3);
