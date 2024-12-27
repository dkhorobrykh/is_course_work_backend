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
    IF (SELECT expiration_date FROM is_course_user_doc WHERE id = NEW.user_doc_id) < flight_departure_date THEN
        RAISE EXCEPTION 'The document is expired or will expire before the flight departure date.';
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;