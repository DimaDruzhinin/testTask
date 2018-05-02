create table if not exists user (
  id bigint primary key auto_increment,
  name char(20) not null
);
# todo:индексы
# create unique index user_id_index on user (id);
# create unique index user_name_uindex on user (name);
