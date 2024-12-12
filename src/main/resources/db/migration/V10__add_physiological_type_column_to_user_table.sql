alter table is_course_user
    add physiological_type_id bigint not null references is_course_physiological_type on update cascade on delete restrict default 1;