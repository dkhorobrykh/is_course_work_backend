alter table is_course_flight
    alter column departure_datetime drop not null,
    alter column arrival_datetime drop not null;