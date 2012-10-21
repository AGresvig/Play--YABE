# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table category (
  id                        bigint not null,
  name                      varchar(255),
  date_created              timestamp,
  constraint pk_category primary key (id))
;

create table post (
  id                        bigint not null,
  date_posted               timestamp,
  title                     varchar(255),
  content                   clob,
  category_id               bigint,
  constraint pk_post primary key (id))
;

create table tag (
  id                        bigint not null,
  name                      varchar(255),
  date_created              timestamp,
  constraint pk_tag primary key (id))
;

create table user (
  id                        bigint not null,
  first_name                varchar(255),
  last_name                 varchar(255),
  date_of_birth             timestamp,
  email                     varchar(255),
  password                  varchar(255),
  is_admin                  boolean,
  constraint pk_user primary key (id))
;

create sequence category_seq;

create sequence post_seq;

create sequence tag_seq;

create sequence user_seq;

alter table post add constraint fk_post_category_1 foreign key (category_id) references category (id) on delete restrict on update restrict;
create index ix_post_category_1 on post (category_id);



# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists category;

drop table if exists post;

drop table if exists tag;

drop table if exists user;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists category_seq;

drop sequence if exists post_seq;

drop sequence if exists tag_seq;

drop sequence if exists user_seq;

