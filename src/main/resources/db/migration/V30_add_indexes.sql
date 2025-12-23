CREATE INDEX idx_flight_status_name ON is_course_flight_status (name);
CREATE INDEX idx_flight_schedule_departure ON is_course_flight_schedule (planet_departure_id);
CREATE INDEX idx_flight_schedule_arrival ON is_course_flight_schedule (planet_arrival_id);
CREATE INDEX idx_flight_status_id ON is_course_flight (flight_status_id);
CREATE INDEX idx_passenger_user ON is_course_passenger (user_id);
CREATE INDEX idx_cargo_flight ON is_course_cargo (flight_id);