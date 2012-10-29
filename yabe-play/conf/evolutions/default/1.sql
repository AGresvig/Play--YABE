# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table category (
  name                      varchar(255) not null,
  constraint pk_category primary key (name))
;

create table post (
  title_string              varchar(255) not null,
  title                     varchar(255),
  content                   clob,
  category_name             varchar(255),
  constraint pk_post primary key (title_string))
;

create table tag (
  name                      varchar(255) not null,
  constraint pk_tag primary key (name))
;

create table user (
  email                     varchar(255) not null,
  first_name                varchar(255),
  last_name                 varchar(255),
  password                  varchar(255),
  is_admin                  boolean,
  constraint pk_user primary key (email))
;


create table post_tag (
  post_title_string              varchar(255) not null,
  tag_name                       varchar(255) not null,
  constraint pk_post_tag primary key (post_title_string, tag_name))
;
create sequence category_seq;

create sequence post_seq;

create sequence tag_seq;

create sequence user_seq;

alter table post add constraint fk_post_category_1 foreign key (category_name) references category (name) on delete restrict on update restrict;
create index ix_post_category_1 on post (category_name);



alter table post_tag add constraint fk_post_tag_post_01 foreign key (post_title_string) references post (title_string) on delete restrict on update restrict;

alter table post_tag add constraint fk_post_tag_tag_02 foreign key (tag_name) references tag (name) on delete restrict on update restrict;

# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists category;

drop table if exists post;

drop table if exists post_tag;

drop table if exists tag;

drop table if exists user;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists category_seq;

drop sequence if exists post_seq;

drop sequence if exists tag_seq;

drop sequence if exists user_seq;

