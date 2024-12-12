CREATE TABLE flight_status
(
    id          BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name        VARCHAR(100) UNIQUE                     NOT NULL,
    output_name VARCHAR(100)                            NOT NULL,
    CONSTRAINT pk_flight_status PRIMARY KEY (id)
);

CREATE TABLE is_course_air_type
(
    id          BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name        VARCHAR(100) UNIQUE                     NOT NULL,
    output_name VARCHAR(100)                            NOT NULL,
    CONSTRAINT pk_is_course_air_type PRIMARY KEY (id)
);

CREATE TABLE is_course_biological_type
(
    id          BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name        VARCHAR(100) UNIQUE                     NOT NULL,
    output_name VARCHAR(100),
    CONSTRAINT pk_is_course_biological_type PRIMARY KEY (id)
);

CREATE TABLE is_course_cargo
(
    id                   BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name                 VARCHAR(100)                            NOT NULL,
    recipient_id         BIGINT                                  NOT NULL,
    sender_id            BIGINT                                  NOT NULL,
    weight               INTEGER CHECK (weight > 0)              NOT NULL,
    insurance_program_id BIGINT,
    ship_id              BIGINT,
    cargo_condition_id   BIGINT                                  NOT NULL,
    CONSTRAINT pk_is_course_cargo PRIMARY KEY (id)
);

CREATE TABLE is_course_cargo_condition
(
    id                  BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    air_type_id         BIGINT                                  NOT NULL,
    habitat_id          BIGINT                                  NOT NULL,
    temperature_type_id BIGINT                                  NOT NULL,
    CONSTRAINT pk_is_course_cargo_condition PRIMARY KEY (id)
);

CREATE TABLE is_course_chat
(
    id                BIGINT GENERATED BY DEFAULT AS IDENTITY   NOT NULL,
    user_first_id     BIGINT                                    NOT NULL,
    user_second_id    BIGINT                                    NOT NULL,
    creation_datetime TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW() NOT NULL,
    CONSTRAINT pk_is_course_chat PRIMARY KEY (id)
);

CREATE TABLE is_course_flight
(
    id                 BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name               VARCHAR(100) UNIQUE                     NOT NULL,
    ship_id            BIGINT                                  NOT NULL,
    flight_status_id   BIGINT                                  NOT NULL,
    departure_datetime TIMESTAMP WITHOUT TIME ZONE             NOT NULL,
    arrival_datetime   TIMESTAMP WITHOUT TIME ZONE             NOT NULL CHECK (arrival_datetime > is_course_flight.departure_datetime),
    CONSTRAINT pk_is_course_flight PRIMARY KEY (id)
);

CREATE TABLE is_course_flight_schedule
(
    id                  BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    flight_id           BIGINT                                  NOT NULL,
    planet_departure_id BIGINT                                  NOT NULL,
    planet_arrival_id   BIGINT                                  NOT NULL,
    departure_datetime  TIMESTAMP WITHOUT TIME ZONE             NOT NULL,
    arrival_datetime    TIMESTAMP WITHOUT TIME ZONE             NOT NULL,
    schedule_status_id  BIGINT                                  NOT NULL,
    CONSTRAINT pk_is_course_flight_schedule PRIMARY KEY (id)
);

CREATE TABLE is_course_flight_workers
(
    flight_id BIGINT NOT NULL,
    worker_id BIGINT NOT NULL,
    CONSTRAINT pk_is_course_flight_workers PRIMARY KEY (flight_id, worker_id)
);

CREATE TABLE is_course_galaxy
(
    id          BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name        VARCHAR(100) UNIQUE                     NOT NULL,
    output_name VARCHAR(100)                            NOT NULL,
    CONSTRAINT pk_is_course_galaxy PRIMARY KEY (id)
);

CREATE TABLE is_course_habitat
(
    id          BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name        VARCHAR(100) UNIQUE                     NOT NULL,
    output_name VARCHAR(100)                            NOT NULL,
    CONSTRAINT pk_is_course_habitat PRIMARY KEY (id)
);

CREATE TABLE is_course_insurance_issued
(
    id                   BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    passenger_id         BIGINT,
    cargo_id             BIGINT,
    total_cost           INTEGER CHECK ( total_cost >= 0 )       NOT NULL,
    insurance_program_id BIGINT                                  NOT NULL,
    recipient_id         BIGINT                                  NOT NULL,
    flight_id            BIGINT                                  NOT NULL,
    CONSTRAINT pk_is_course_insurance_issued PRIMARY KEY (id)
);

CREATE TABLE is_course_insurance_program
(
    id             BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    rank           INTEGER                                 NOT NULL,
    min_cost       INTEGER CHECK (min_cost >= 0)           NOT NULL,
    refund_amount  INTEGER CHECK ( refund_amount >= 0 )    NOT NULL,
    active         BOOLEAN DEFAULT FALSE                   NOT NULL,
    start_datetime TIMESTAMP WITHOUT TIME ZONE             NOT NULL,
    end_datetime   TIMESTAMP WITHOUT TIME ZONE             NOT NULL CHECK (end_datetime > start_datetime),
    CONSTRAINT pk_is_course_insurance_program PRIMARY KEY (id)
);

CREATE TABLE is_course_message
(
    id                BIGINT GENERATED BY DEFAULT AS IDENTITY   NOT NULL,
    chat_id           BIGINT                                    NOT NULL,
    text              VARCHAR(100)                              NOT NULL,
    creation_datetime TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW() NOT NULL,
    CONSTRAINT pk_is_course_message PRIMARY KEY (id)
);

CREATE TABLE is_course_passenger
(
    id               BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    user_doc_id      BIGINT                                  NOT NULL,
    flight_id        BIGINT                                  NOT NULL,
    service_class_id BIGINT                                  NOT NULL,
    user_id          BIGINT                                  NOT NULL,
    CONSTRAINT pk_is_course_passenger PRIMARY KEY (id)
);

CREATE TABLE is_course_physiological_type
(
    id                  BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name                VARCHAR(100) UNIQUE                     NOT NULL,
    output_name         VARCHAR(100)                            NOT NULL,
    temperature_type_id BIGINT                                  NOT NULL,
    habitat_id          BIGINT                                  NOT NULL,
    air_type_id         BIGINT                                  NOT NULL,
    CONSTRAINT pk_is_course_physiological_type PRIMARY KEY (id)
);

CREATE TABLE is_course_planet
(
    id             BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name           VARCHAR(255)                            NOT NULL,
    galaxy_id      BIGINT                                  NOT NULL,
    population     INTEGER                                 NOT NULL,
    planet_type_id BIGINT                                  NOT NULL,
    CONSTRAINT pk_is_course_planet PRIMARY KEY (id)
);

CREATE TABLE is_course_planet_type
(
    id                  BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name                VARCHAR(100) UNIQUE                     NOT NULL,
    output_name         VARCHAR(100)                            NOT NULL,
    temperature_type_id BIGINT                                  NOT NULL,
    habitat_id          BIGINT                                  NOT NULL,
    air_type_id         BIGINT                                  NOT NULL,
    CONSTRAINT pk_is_course_planet_type PRIMARY KEY (id)
);

CREATE TABLE is_course_schedule_status
(
    id          BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name        VARCHAR(100) UNIQUE                     NOT NULL,
    output_name VARCHAR(100)                            NOT NULL,
    CONSTRAINT pk_is_course_schedule_status PRIMARY KEY (id)
);

CREATE TABLE is_course_service_class
(
    id          BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name        VARCHAR(100) UNIQUE                     NOT NULL,
    output_name VARCHAR(100)                            NOT NULL,
    CONSTRAINT pk_is_course_service_class PRIMARY KEY (id)
);

CREATE TABLE is_course_ship
(
    id                    BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name                  VARCHAR(100) UNIQUE                     NOT NULL,
    ship_type_id          BIGINT                                  NOT NULL,
    number                VARCHAR(50) UNIQUE                      NOT NULL,
    registration_datetime TIMESTAMP WITHOUT TIME ZONE             NOT NULL,
    photo                 VARCHAR(255),
    CONSTRAINT pk_is_course_ship PRIMARY KEY (id)
);

CREATE TABLE is_course_ship_service_classes
(
    service_class_id BIGINT NOT NULL,
    ship_id          BIGINT NOT NULL,
    CONSTRAINT pk_is_course_ship_service_classes PRIMARY KEY (service_class_id, ship_id)
);

CREATE TABLE is_course_ship_type
(
    id                 BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name               VARCHAR(100) UNIQUE                     NOT NULL,
    output_name        VARCHAR(100)                            NOT NULL,
    load_capacity      INTEGER CHECK (load_capacity >= 0)      NOT NULL,
    passenger_capacity INTEGER CHECK (passenger_capacity >= 0) NOT NULL,
    CONSTRAINT pk_is_course_ship_type PRIMARY KEY (id)
);

CREATE TABLE is_course_role
(
    id                  BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name                VARCHAR(100)                            NOT NULL,
    flight_id           BIGINT,
    planet_id           BIGINT,
    active              BOOLEAN                                 NOT NULL,
    expiration_datetime TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_is_course_role PRIMARY KEY (id)
);

CREATE TABLE is_course_temperature_type
(
    id              BIGINT GENERATED BY DEFAULT AS IDENTITY                     NOT NULL,
    name            VARCHAR(100) UNIQUE                                         NOT NULL,
    output_name     VARCHAR(100)                                                NOT NULL,
    min_temperature DOUBLE PRECISION                                            NOT NULL,
    max_temperature DOUBLE PRECISION CHECK (max_temperature >= min_temperature) NOT NULL,
    CONSTRAINT pk_is_course_temperature_type PRIMARY KEY (id)
);

CREATE TABLE is_course_user
(
    id            BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    login         VARCHAR(100) UNIQUE                     NOT NULL,
    password      VARCHAR(255)                            NOT NULL,
    first_name    VARCHAR(100)                            NOT NULL,
    last_name     VARCHAR(100)                            NOT NULL,
    surname       VARCHAR(100)                            NOT NULL,
    date_of_birth date                                    NOT NULL,
    email         VARCHAR(255) UNIQUE                     NOT NULL,
    CONSTRAINT pk_is_course_user PRIMARY KEY (id)
);

CREATE TABLE is_course_user_data
(
    id                    BIGINT GENERATED BY DEFAULT AS IDENTITY             NOT NULL,
    biological_type_id    BIGINT                                              NOT NULL,
    physiological_type_id BIGINT                                              NOT NULL,
    contact               VARCHAR(255) CHECK (contact ~ '^\+?[1-9]\d{1,14}$') NOT NULL,
    CONSTRAINT pk_is_course_user_data PRIMARY KEY (id)
);

CREATE TABLE is_course_user_doc
(
    id               BIGINT GENERATED BY DEFAULT AS IDENTITY   NOT NULL,
    user_doc_type_id BIGINT                                    NOT NULL,
    series           VARCHAR(50)                               NOT NULL,
    number           VARCHAR(50)                               NOT NULL,
    issue_date       date                                      NOT NULL,
    expiration_date  date CHECK (expiration_date > issue_date) NOT NULL,
    CONSTRAINT pk_is_course_user_doc PRIMARY KEY (id)
);

CREATE TABLE is_course_user_doc_type
(
    id          BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name        VARCHAR(100) UNIQUE                     NOT NULL,
    output_name VARCHAR(100)                            NOT NULL,
    CONSTRAINT pk_is_course_user_doc_type PRIMARY KEY (id)
);

CREATE TABLE is_course_user_roles
(
    roles_id BIGINT NOT NULL,
    user_id  BIGINT NOT NULL,
    CONSTRAINT pk_is_course_user_roles PRIMARY KEY (roles_id, user_id)
);

CREATE TABLE is_course_worker
(
    id                  BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    user_id             BIGINT                                  NOT NULL,
    role_id             BIGINT                                  NOT NULL,
    contacts            VARCHAR(255)                            NOT NULL,
    qualification       VARCHAR(255)                            NOT NULL,
    expiration_datetime TIMESTAMP WITHOUT TIME ZONE             NOT NULL,
    CONSTRAINT pk_is_course_worker PRIMARY KEY (id)
);

ALTER TABLE is_course_cargo_condition
    ADD CONSTRAINT FK_IS_COURSE_CARGO_CONDITION_ON_AIR_TYPE FOREIGN KEY (air_type_id) REFERENCES is_course_air_type (id);

ALTER TABLE is_course_cargo_condition
    ADD CONSTRAINT FK_IS_COURSE_CARGO_CONDITION_ON_HABITAT FOREIGN KEY (habitat_id) REFERENCES is_course_habitat (id);

ALTER TABLE is_course_cargo_condition
    ADD CONSTRAINT FK_IS_COURSE_CARGO_CONDITION_ON_TEMPERATURE_TYPE FOREIGN KEY (temperature_type_id) REFERENCES is_course_temperature_type (id);

ALTER TABLE is_course_cargo
    ADD CONSTRAINT FK_IS_COURSE_CARGO_ON_CARGO_CONDITION FOREIGN KEY (cargo_condition_id) REFERENCES is_course_cargo_condition (id);

ALTER TABLE is_course_cargo
    ADD CONSTRAINT FK_IS_COURSE_CARGO_ON_INSURANCE_PROGRAM FOREIGN KEY (insurance_program_id) REFERENCES is_course_insurance_program (id);

ALTER TABLE is_course_cargo
    ADD CONSTRAINT FK_IS_COURSE_CARGO_ON_RECIPIENT FOREIGN KEY (recipient_id) REFERENCES is_course_user (id);

ALTER TABLE is_course_cargo
    ADD CONSTRAINT FK_IS_COURSE_CARGO_ON_SENDER FOREIGN KEY (sender_id) REFERENCES is_course_user (id);

ALTER TABLE is_course_cargo
    ADD CONSTRAINT FK_IS_COURSE_CARGO_ON_SHIP FOREIGN KEY (ship_id) REFERENCES is_course_ship (id);

ALTER TABLE is_course_chat
    ADD CONSTRAINT FK_IS_COURSE_CHAT_ON_USER_FIRST FOREIGN KEY (user_first_id) REFERENCES is_course_user (id);

ALTER TABLE is_course_chat
    ADD CONSTRAINT FK_IS_COURSE_CHAT_ON_USER_SECOND FOREIGN KEY (user_second_id) REFERENCES is_course_user (id);

ALTER TABLE is_course_flight
    ADD CONSTRAINT FK_IS_COURSE_FLIGHT_ON_FLIGHT_STATUS FOREIGN KEY (flight_status_id) REFERENCES flight_status (id);

ALTER TABLE is_course_flight
    ADD CONSTRAINT FK_IS_COURSE_FLIGHT_ON_SHIP FOREIGN KEY (ship_id) REFERENCES is_course_ship (id);

ALTER TABLE is_course_flight_schedule
    ADD CONSTRAINT FK_IS_COURSE_FLIGHT_SCHEDULE_ON_FLIGHT FOREIGN KEY (flight_id) REFERENCES is_course_flight (id);

ALTER TABLE is_course_flight_schedule
    ADD CONSTRAINT FK_IS_COURSE_FLIGHT_SCHEDULE_ON_PLANET_ARRIVAL FOREIGN KEY (planet_arrival_id) REFERENCES is_course_planet (id);

ALTER TABLE is_course_flight_schedule
    ADD CONSTRAINT FK_IS_COURSE_FLIGHT_SCHEDULE_ON_PLANET_DEPARTURE FOREIGN KEY (planet_departure_id) REFERENCES is_course_planet (id);

ALTER TABLE is_course_flight_schedule
    ADD CONSTRAINT FK_IS_COURSE_FLIGHT_SCHEDULE_ON_SCHEDULE_STATUS FOREIGN KEY (schedule_status_id) REFERENCES is_course_schedule_status (id);

ALTER TABLE is_course_insurance_issued
    ADD CONSTRAINT FK_IS_COURSE_INSURANCE_ISSUED_ON_CARGO FOREIGN KEY (cargo_id) REFERENCES is_course_cargo (id);

ALTER TABLE is_course_insurance_issued
    ADD CONSTRAINT FK_IS_COURSE_INSURANCE_ISSUED_ON_FLIGHT FOREIGN KEY (flight_id) REFERENCES is_course_flight (id);

ALTER TABLE is_course_insurance_issued
    ADD CONSTRAINT FK_IS_COURSE_INSURANCE_ISSUED_ON_INSURANCE_PROGRAM FOREIGN KEY (insurance_program_id) REFERENCES is_course_insurance_program (id);

ALTER TABLE is_course_insurance_issued
    ADD CONSTRAINT FK_IS_COURSE_INSURANCE_ISSUED_ON_PASSENGER FOREIGN KEY (passenger_id) REFERENCES is_course_passenger (id);

ALTER TABLE is_course_insurance_issued
    ADD CONSTRAINT FK_IS_COURSE_INSURANCE_ISSUED_ON_RECIPIENT FOREIGN KEY (recipient_id) REFERENCES is_course_user (id);

ALTER TABLE is_course_message
    ADD CONSTRAINT FK_IS_COURSE_MESSAGE_ON_CHAT FOREIGN KEY (chat_id) REFERENCES is_course_chat (id);

ALTER TABLE is_course_passenger
    ADD CONSTRAINT FK_IS_COURSE_PASSENGER_ON_FLIGHT FOREIGN KEY (flight_id) REFERENCES is_course_flight (id);

ALTER TABLE is_course_passenger
    ADD CONSTRAINT FK_IS_COURSE_PASSENGER_ON_SERVICE_CLASS FOREIGN KEY (service_class_id) REFERENCES is_course_service_class (id);

ALTER TABLE is_course_passenger
    ADD CONSTRAINT FK_IS_COURSE_PASSENGER_ON_USER FOREIGN KEY (user_id) REFERENCES is_course_user (id);

ALTER TABLE is_course_passenger
    ADD CONSTRAINT FK_IS_COURSE_PASSENGER_ON_USER_DOC FOREIGN KEY (user_doc_id) REFERENCES is_course_user_doc (id);

ALTER TABLE is_course_physiological_type
    ADD CONSTRAINT FK_IS_COURSE_PHYSIOLOGICAL_TYPE_ON_AIR_TYPE FOREIGN KEY (air_type_id) REFERENCES is_course_air_type (id);

ALTER TABLE is_course_physiological_type
    ADD CONSTRAINT FK_IS_COURSE_PHYSIOLOGICAL_TYPE_ON_HABITAT FOREIGN KEY (habitat_id) REFERENCES is_course_habitat (id);

ALTER TABLE is_course_physiological_type
    ADD CONSTRAINT FK_IS_COURSE_PHYSIOLOGICAL_TYPE_ON_TEMPERATURE_TYPE FOREIGN KEY (temperature_type_id) REFERENCES is_course_temperature_type (id);

ALTER TABLE is_course_planet
    ADD CONSTRAINT FK_IS_COURSE_PLANET_ON_GALAXY FOREIGN KEY (galaxy_id) REFERENCES is_course_galaxy (id);

ALTER TABLE is_course_planet
    ADD CONSTRAINT FK_IS_COURSE_PLANET_ON_PLANET_TYPE FOREIGN KEY (planet_type_id) REFERENCES is_course_planet_type (id);

ALTER TABLE is_course_planet_type
    ADD CONSTRAINT FK_IS_COURSE_PLANET_TYPE_ON_AIR_TYPE FOREIGN KEY (air_type_id) REFERENCES is_course_air_type (id);

ALTER TABLE is_course_planet_type
    ADD CONSTRAINT FK_IS_COURSE_PLANET_TYPE_ON_HABITAT FOREIGN KEY (habitat_id) REFERENCES is_course_habitat (id);

ALTER TABLE is_course_planet_type
    ADD CONSTRAINT FK_IS_COURSE_PLANET_TYPE_ON_TEMPERATURE_TYPE FOREIGN KEY (temperature_type_id) REFERENCES is_course_temperature_type (id);

ALTER TABLE is_course_ship
    ADD CONSTRAINT FK_IS_COURSE_SHIP_ON_SHIP_TYPE FOREIGN KEY (ship_type_id) REFERENCES is_course_ship_type (id);

ALTER TABLE is_course_role
    ADD CONSTRAINT FK_is_course_role_ON_FLIGHT FOREIGN KEY (flight_id) REFERENCES is_course_flight (id);

ALTER TABLE is_course_role
    ADD CONSTRAINT FK_is_course_role_ON_PLANET FOREIGN KEY (planet_id) REFERENCES is_course_planet (id);

ALTER TABLE is_course_user_data
    ADD CONSTRAINT FK_IS_COURSE_USER_DATA_ON_BIOLOGICAL_TYPE FOREIGN KEY (biological_type_id) REFERENCES is_course_biological_type (id);

ALTER TABLE is_course_user_data
    ADD CONSTRAINT FK_IS_COURSE_USER_DATA_ON_PHYSIOLOGICAL_TYPE FOREIGN KEY (physiological_type_id) REFERENCES is_course_physiological_type (id);

ALTER TABLE is_course_user_doc
    ADD CONSTRAINT FK_IS_COURSE_USER_DOC_ON_USER_DOC_TYPE FOREIGN KEY (user_doc_type_id) REFERENCES is_course_user_doc_type (id);

ALTER TABLE is_course_worker
    ADD CONSTRAINT FK_IS_COURSE_WORKER_ON_ROLE FOREIGN KEY (role_id) REFERENCES is_course_role (id);

ALTER TABLE is_course_worker
    ADD CONSTRAINT FK_IS_COURSE_WORKER_ON_USER FOREIGN KEY (user_id) REFERENCES is_course_user (id);

ALTER TABLE is_course_flight_workers
    ADD CONSTRAINT fk_iscoufliwor_on_flight FOREIGN KEY (flight_id) REFERENCES is_course_flight (id);

ALTER TABLE is_course_flight_workers
    ADD CONSTRAINT fk_iscoufliwor_on_worker FOREIGN KEY (worker_id) REFERENCES is_course_worker (id);

ALTER TABLE is_course_ship_service_classes
    ADD CONSTRAINT fk_iscoushisercla_on_service_class FOREIGN KEY (service_class_id) REFERENCES is_course_service_class (id);

ALTER TABLE is_course_ship_service_classes
    ADD CONSTRAINT fk_iscoushisercla_on_ship FOREIGN KEY (ship_id) REFERENCES is_course_ship (id);

ALTER TABLE is_course_user_roles
    ADD CONSTRAINT fk_iscouuserol_on_role FOREIGN KEY (roles_id) REFERENCES is_course_role (id);

ALTER TABLE is_course_user_roles
    ADD CONSTRAINT fk_iscouuserol_on_user FOREIGN KEY (user_id) REFERENCES is_course_user (id);