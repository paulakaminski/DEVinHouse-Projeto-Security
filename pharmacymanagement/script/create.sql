create table usuario (
id serial primary key, 
	email varchar(60) not null, 
	senha varchar(60) not null
);

create table endereco (
id serial primary key,
	cep varchar(9) not null,
	logradouro varchar(60) not null,
	numero int not null,
	bairro varchar(60) not null,
	cidade varchar(60) not null,
	uf char(2) not null,
	complemento varchar(30),
	latitude varchar(30) not null,
	longitude varchar(30) not null
);

create table farmacia (
id serial primary key,
	razao_social varchar(60) not null, 
	cnpj varchar(19) not null, 
	nome_fantasia varchar(60) not null,
	email varchar(60) not null,
	telefone_fixo varchar(16),
	telefone_celular varchar(16) not null,
	endereco_id int references endereco (id)
	on delete cascade
	on update cascade
);

create table medicamento (
id serial primary key,
	nome varchar(60) not null,
	laboratorio varchar(60) not null,
	dosagem varchar(30) not null,
	descricao varchar(500),
	preco_unitario numeric not null,
	tipo varchar(30) not null
);