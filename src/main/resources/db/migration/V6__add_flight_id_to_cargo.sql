alter table is_course_cargo
    add flight_id bigint references is_course_flight on update cascade on delete restrict;