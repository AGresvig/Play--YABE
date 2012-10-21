# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table post (
  id                        bigint not null,
  date_posted               timestamp,
  title                     varchar(255),
  content                   clob,
  constraint pk_post primary key (id))
;

create table user (
  id                        bigint not null,
  first_name                varchar(255),
  last_name                 varchar(255),
  dateof_birth              timestamp,
  email                     varchar(255),
  password                  varchar(255),
  is_admin                  boolean,
  constraint pk_user primary key (id))
;

create sequence post_seq;

create sequence user_seq;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists post;

drop table if exists user;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists post_seq;

drop sequence if exists user_seq;

