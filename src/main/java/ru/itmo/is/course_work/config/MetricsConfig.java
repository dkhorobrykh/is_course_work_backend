package ru.itmo.is.course_work.config;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.MeterBinder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MetricsConfig {

    @Bean
    public Counter flightBookingCounter(MeterRegistry registry) {
        return Counter.builder("flight.bookings.total")
                .description("Общее количество успешных бронирований рейсов")
                .register(registry);
    }

    @Bean
    public Counter bookingErrorCounter(MeterRegistry registry) {
        return Counter.builder("flight.bookings.errors")
                .description("Количество неудачных попыток бронирования")
                .register(registry);
    }

    @Bean
    public Counter flightStatusChangeCounter(MeterRegistry registry) {
        return Counter.builder("flight.status.changes")
                .description("Количество изменений статусов рейсов")
                .register(registry);
    }

    @Bean
    public Counter cargoStatusChangeCounter(MeterRegistry registry) {
        return Counter.builder("cargo.status.changes")
                .description("Количество изменений статусов грузов")
                .register(registry);
    }

    @Bean
    public Counter userBalanceTopupCounter(MeterRegistry registry) {
        return Counter.builder("user.balance.topup")
                .description("Общая сумма пополнений баланса пользователей")
                .baseUnit("currency") // рубль/доллар
                .register(registry);
    }
}