create table is_course_revision_types
(
    revision_type      int2        not null primary key,
    revision_type_name varchar(20) not null
);

insert into is_course_revision_types (revision_type, revision_type_name)
values (0, 'CREATE'),
       (1, 'UPDATE'),
       (2, 'DELETE');

create table is_course_revision_info
(
    revision_id        serial4 primary key not null,
    revision_timestamp bigint              not null,
    user_id            bigint
);

create table is_course_role_audit
(
    id bigserial not null ,
    revision_id int4 not null references is_course_revision_info,
    revision_type int2 not null references is_course_revision_types,
    name text ,
    flight_id bigint,
    planet_id bigint,
    active boolean,
    expiration_datetime timestamp without time zone,
    primary key (id, revision_id)
)