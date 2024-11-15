create database testeimagem;

use testeimagem;

create table usuario (
	id bigint auto_increment primary key,
	nome text,
	email text,
	senha text,
	caminho_foto text
);