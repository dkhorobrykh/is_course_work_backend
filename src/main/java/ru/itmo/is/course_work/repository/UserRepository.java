package ru.itmo.is.course_work.repository;

import jakarta.validation.constraints.NotEmpty;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itmo.is.course_work.model.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByLogin(@NonNull @NotEmpty String login);

    Optional<User> findByEmail(@NotEmpty String email);
}
