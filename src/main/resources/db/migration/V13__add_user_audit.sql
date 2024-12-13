create table is_course_user_audit
(
    id bigserial not null ,
    revision_id int4 not null references is_course_revision_info,
    revision_type int2 not null references is_course_revision_types,
    login text,
    password text,
    first_name text,
    last_name text,
    surname text,
    date_of_birth date,
    email text,
    physiological_type_id bigint,
    primary key (id, revision_id)
);