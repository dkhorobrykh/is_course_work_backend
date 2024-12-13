create table is_course_user_roles_audit
(
    id bigserial not null ,
    revision_id int4 not null references is_course_revision_info,
    revision_type int2 not null references is_course_revision_types,
    user_id bigint,
    roles_id bigint,
    primary key (id, revision_id)
);