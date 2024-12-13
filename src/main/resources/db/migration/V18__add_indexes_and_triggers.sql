CREATE OR REPLACE FUNCTION update_role_status()
    RETURNS TRIGGER AS
$$
BEGIN
    IF NEW.expiration_datetime IS NOT NULL AND NEW.expiration_datetime < NOW() THEN
        NEW.active := FALSE;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;
CREATE TRIGGER update_role_status_trigger
    BEFORE INSERT OR UPDATE
    ON is_course_role
    FOR EACH ROW
EXECUTE FUNCTION update_role_status();
CREATE TABLE is_course_user_log
(
    id        SERIAL PRIMARY KEY          NOT NULL,
    user_id   INT                         NOT NULL,
    action    TEXT                        NOT NULL,
    old_value TEXT,
    new_value TEXT,
    timestamp TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW()
);

CREATE OR REPLACE FUNCTION log_user_changes()
    RETURNS TRIGGER AS
$$
BEGIN
    IF TG_OP = 'INSERT' THEN
        INSERT INTO is_course_user_log (user_id, action, new_value)
        VALUES (NEW.id, 'INSERT', row_to_json(NEW)::TEXT);
    ELSIF TG_OP = 'UPDATE' THEN
        INSERT INTO is_course_user_log (user_id, action, old_value, new_value)
        VALUES (NEW.id, 'UPDATE', row_to_json(OLD)::TEXT, row_to_json(NEW)::TEXT);
    ELSIF TG_OP = 'DELETE' THEN
        INSERT INTO is_course_user_log (user_id, action, old_value)
        VALUES (OLD.id, 'DELETE', row_to_json(OLD)::TEXT);
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;
CREATE TRIGGER log_user_changes_trigger
    AFTER INSERT OR UPDATE OR DELETE
    ON is_course_user
    FOR EACH ROW
EXECUTE FUNCTION log_user_changes();

CREATE OR REPLACE FUNCTION check_document_expiration()
    RETURNS TRIGGER AS
$$
DECLARE
    flight_departure_date DATE;
BEGIN
    -- Извлечение даты вылета для рейса, связанного с пассажиром
    SELECT departure_datetime::date
    INTO flight_departure_date
    FROM is_course_flight
    WHERE id = NEW.flight_id;
-- Проверка, что expiration_date документа больше или равна дате вылета
    IF (SELECT expiration_date FROM is_course_user_doc WHERE id = NEW.document_id) < flight_departure_date THEN
        RAISE EXCEPTION 'The document is expired or will expire before the flight departure date.';
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;
CREATE TRIGGER trigger_check_document_expiration
    BEFORE INSERT
    ON is_course_passenger
    FOR EACH ROW
EXECUTE FUNCTION check_document_expiration();


CREATE OR REPLACE FUNCTION welcome_message_trigger()
    RETURNS TRIGGER AS
$$
DECLARE
    user_first_type  text;
    user_second_type text;
    welcome_message  text;
BEGIN
    -- Получаем типы существования пользователей или устанавливаем значение по умолчанию
    SELECT COALESCE((SELECT output_name
                     FROM is_course_physiological_type
                     WHERE id = (SELECT physiological_type_id
                                 FROM is_course_user
                                 WHERE id = NEW.user_first_id)), 'незнакомец')
    INTO user_first_type;

    SELECT COALESCE((SELECT output_name
                     FROM is_course_physiological_type
                     WHERE id = (SELECT physiological_type_id
                                 FROM is_course_user
                                 WHERE id = NEW.user_second_id)), 'незнакомец')
    INTO user_second_type;

    -- Формируем приветственное сообщение
    welcome_message := 'Добро пожаловать в новый чат! Похоже, что здесь собрались ' || user_first_type || ' и ' ||
                       user_second_type || '!';

    -- Добавляем приветственное сообщение в таблицу сообщений
    INSERT INTO is_course_message(chat_id, text, creation_datetime)
    VALUES (NEW.id, welcome_message, NOW());

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;
CREATE TRIGGER chat_welcome_message_trigger
    AFTER INSERT
    ON is_course_chat
    FOR EACH ROW
EXECUTE FUNCTION welcome_message_trigger();

CREATE INDEX idx_user_login ON is_course_user (login);
CREATE INDEX idx_user_email ON is_course_user (email);
CREATE INDEX idx_roles_planet ON is_course_role (planet_id);
CREATE INDEX idx_roles_flight ON is_course_role (flight_id);
CREATE INDEX idx_passenger_flight ON is_course_passenger (flight_id);
CREATE INDEX idx_flight_ship ON is_course_flight (ship_id);
CREATE INDEX idx_flight_departure ON is_course_flight (departure_datetime);
CREATE INDEX idx_cargo_recipient ON is_course_cargo (recipient_id);
CREATE INDEX idx_cargo_sender ON is_course_cargo (sender_id);
CREATE INDEX idx_cargo_ship ON is_course_cargo (ship_id);
CREATE INDEX idx_user_docs_user_id ON is_course_user_doc (user_id);