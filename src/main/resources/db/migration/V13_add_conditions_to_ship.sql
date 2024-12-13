alter table is_course_ship
    add column temperature double precision not null default 20.0,
add column atmosphere_type varchar(255) not null default 'earth-like',
add column radiation_protection_level double precision not null default 1.0;
