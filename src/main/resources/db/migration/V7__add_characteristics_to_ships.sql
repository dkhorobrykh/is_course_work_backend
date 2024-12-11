alter table is_course_ship
    add habitat_id          bigint not null default 1 references is_course_habitat on update cascade on delete restrict,
    add temperature_type_id bigint not null default 1 references is_course_temperature_type on update cascade on delete restrict,
    add air_type_id         bigint not null default 1 references is_course_air_type on update cascade on delete restrict;