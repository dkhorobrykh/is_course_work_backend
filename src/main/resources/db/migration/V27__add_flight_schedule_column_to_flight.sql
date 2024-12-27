alter table is_course_flight
    add flight_schedule_id bigint references is_course_flight_schedule on update cascade on delete restrict;