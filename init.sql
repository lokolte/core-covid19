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

INSERT INTO public.account(id, email, password, person_id, role_id)
	VALUES (1, 'jaaguilarmeza@gmail.com', 'bG9rb2x0ZQ==', NULL, 3);
	
INSERT INTO public.location(id, latitude, longitude)
	VALUES (1, -25.326650, -57.559925);

INSERT INTO public.person(id, document, name, lastname, phone, sex, location, status)
	VALUES (1, '4204613', 'Jesus', 'Aguilar', '0982912326', 'MASCULINO', 1, 1);
	
UPDATE public.account
	SET person_id=(SELECT id from public.person where document='4204613')
	WHERE id=(SELECT id from public.account where email='jaaguilarmeza@gmail.com');

INSERT INTO public.account(id, email, password, person_id, role_id)
	VALUES (2, 'juanber2.0@gmail.com', 'anVhbmJlcg==', NULL, 3);

INSERT INTO public.location(id, latitude, longitude)
	VALUES (2, -25.342138, -57.511109);

INSERT INTO public.person(id, document, name, lastname, phone, sex, location, status)
	VALUES (2, '4653346', 'Juan', 'Duarte', '0961849365', 'MASCULINO', 2, 1);
	
UPDATE public.account
	SET person_id=(SELECT id from public.person where document='4653346')
	WHERE id=(SELECT id from public.account where email='juanber2.0@gmail.com');

INSERT INTO public.location(id, latitude, longitude)
	VALUES (3, -25.295729, -57.625436);

INSERT INTO public.contact(
	id, contact_date, person_id1, person_id2, contact_location)
	VALUES (1, now(), 1, 2, 3);