alter table is_course_flight
    add cargo_status_id bigint not null default 1 references is_course_cargo_status on update cascade on delete restrict;