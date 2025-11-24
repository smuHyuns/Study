package study.project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import study.project.repository.TestCacheRepository;


@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class RedisTestController {
    private final TestCacheRepository testCacheRepository;

    @GetMapping("/hash")
    ResponseEntity<String> testRedis(@RequestParam("username") String username, @RequestParam("column") String column) {
        String columnValue = testCacheRepository.findColumn(username, column);
        return ResponseEntity.ok("column : " + columnValue);
    }

    @PostMapping("/hash/save")
    void saveHashTestData(@RequestBody String username) {
        testCacheRepository.setUpHash(username);
    }
}
