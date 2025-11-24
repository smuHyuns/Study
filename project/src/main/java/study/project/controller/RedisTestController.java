package study.project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class RedisTestController {

    @GetMapping()
    ResponseEntity<String> testRedis() {
        return ResponseEntity.ok("test");
    }
}
