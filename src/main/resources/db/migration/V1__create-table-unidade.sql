create table unidade(
    id bigint not null auto_increment,
    nome varchar(50) not null unique,
    abreviacao varchar(15),
    constraint unidadepk primary key(id)
);