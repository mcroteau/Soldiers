create table account (
	id bigint PRIMARY KEY AUTO_INCREMENT,
	name character varying(55),
	age character varying(155),
	location character varying(155),
	image_uri character varying(75),
	username character varying(55) NOT NULL,
	password character varying(155) NOT NULL,
	disabled boolean,
	date_disabled bigint,
	uuid character varying(55)
);

create table role (
	id bigint PRIMARY KEY AUTO_INCREMENT,
	name character varying(55) NOT NULL UNIQUE
);

create table account_permissions (
	account_id bigint REFERENCES account(id),
	permission character varying(55)
);

create table account_roles (
	role_id bigint NOT NULL REFERENCES role(id),
	account_id bigint NOT NULL REFERENCES account(id)
);
