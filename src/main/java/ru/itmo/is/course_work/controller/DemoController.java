package ru.itmo.is.course_work.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("demo")
@RequiredArgsConstructor
public class DemoController {
    @GetMapping("{testParam}")
    public ResponseEntity<?> getDemo(@PathVariable String testParam) {
        return ResponseEntity.ok(List.of("string", "test", testParam));
    }
}
