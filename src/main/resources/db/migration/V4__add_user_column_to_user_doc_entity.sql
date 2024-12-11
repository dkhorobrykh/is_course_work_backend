alter table is_course_user_doc
    add user_id bigint not null references is_course_user on update cascade on delete restrict default 1;