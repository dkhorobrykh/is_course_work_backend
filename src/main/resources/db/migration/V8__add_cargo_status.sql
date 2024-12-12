create table is_course_cargo_status
(
    id          bigserial primary key not null,
    name        varchar(40)           not null,
    output_name varchar(40)           not null
);

insert into is_course_cargo_status
values (1, 'WAITING_START', 'Ожидание начала'),
       (2, 'CUSTOMS_CHECK', 'Таможенная проверка'),
       (3, 'LOADING', 'Погрузка'),
       (4, 'READY', 'Готов'),
       (5, 'UNLOADING', 'Выгрузка'),
       (6, 'COMPLETED', 'Завершен');

insert into is_course_flight_status
values (1, 'PLANNED', 'Запланирован'),
       (2, 'APPROVED', 'Подтвержден'),
       (3, 'REGISTRATION', 'Регистрация'),
       (4, 'BOARDING', 'Посадка'),
       (5, 'FLIGHT', 'Полет'),
       (6, 'DISEMBARKATION', 'Высадка'),
       (7, 'COMPLETED', 'Завершен');