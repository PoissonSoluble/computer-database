drop schema if exists `computer-database-db`;
  create schema if not exists `computer-database-db`;
  use `computer-database-db`;

  drop table if exists computer;
  drop table if exists company;

  create table company (
    ca_id                        bigint not null auto_increment,
    ca_name                      varchar(255) not null,
    ca_picture                   varchar(1024) null,
    constraint pk_company primary key (ca_id))
  ;

  create table computer (
    cu_id                        bigint not null auto_increment,
    cu_name                      varchar(255) not null,
    cu_introduced                datetime NULL,
    cu_discontinued              datetime NULL,
    ca_id                        bigint default NULL,
    constraint pk_computer primary key (cu_id))
  ;

  create table user (
    us_id                        bigint not null auto_increment,
    us_login                     varchar(255) not null unique,
    us_password                  varchar(255) not null,
    ur_id                        bigint not null,
    constraint pk_user primary key (us_id))
  ;

  create table user_role (
    ur_id                        bigint not null auto_increment,
    ur_label                     varchar(255) not null,
    constraint pk_user primary key (ur_id))
  ;

  alter table computer add constraint fk_computer_company_1 foreign key (ca_id) references company (ca_id) on delete restrict on update restrict;
  alter table user add constraint fk_user_role_1 foreign key (ur_id) references user_role (ur_id) on delete restrict on update restrict;
  create index ix_computer_company_1 on computer (ca_id);
