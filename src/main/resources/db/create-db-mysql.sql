CREATE TABLE account (
	id bigint PRIMARY KEY AUTO_INCREMENT,
	name varchar(55),
	age varchar(155),
	location varchar(155),
	image_uri varchar(75),
	username varchar(55) NOT NULL,
	password varchar(155) NOT NULL,
	disabled boolean,
	date_disabled bigint,
	uuid varchar(55)
);

CREATE TABLE role (
	id bigint PRIMARY KEY AUTO_INCREMENT,
	name varchar(55) NOT NULL UNIQUE
);

CREATE TABLE account_permissions (
	account_id bigint REFERENCES account(id),
	permission varchar(55)
);

CREATE TABLE account_roles (
	role_id bigint NOT NULL REFERENCES role(id),
	account_id bigint NOT NULL REFERENCES account(id)
);

