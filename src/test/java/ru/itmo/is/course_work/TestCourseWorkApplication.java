package ru.itmo.is.course_work;

import org.springframework.boot.SpringApplication;

public class TestCourseWorkApplication {

    public static void main(String[] args) {
        SpringApplication.from(CourseWorkApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
