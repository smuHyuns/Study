package study.project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import study.project.dto.ValueOpsDto;
import study.project.repository.TestCacheRepository;
import study.project.repository.TestCacheRepository2;


@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class RedisTestController {
    private final TestCacheRepository hashCacheRepository;
    private final TestCacheRepository2 valueCacheRepository;

    @GetMapping("/hash")
    ResponseEntity<String> testRedis(@RequestParam("username") String username, @RequestParam("column") String column) {
        String columnValue = hashCacheRepository.findColumn(username, column);
        return ResponseEntity.ok("column : " + columnValue);
    }

    @PostMapping("/hash/save")
    void saveHashTestData(@RequestBody String username) {
        hashCacheRepository.setUpHash(username);
    }

    @GetMapping("/value")
    ResponseEntity<ValueOpsDto.SearchResponse> getValueOpsRedis(@RequestParam("username") String username) {
        return ResponseEntity.ok(valueCacheRepository.find(username));
    }

    @PostMapping("/value/save")
    void saveValueOpsRedis(@RequestBody ValueOpsDto.SaveRequest request) {
        valueCacheRepository.setValueOperation(request);
    }
}
