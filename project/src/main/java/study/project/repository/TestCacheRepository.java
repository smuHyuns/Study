package study.project.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.UUID;

/**
 * Redis의 여러 데이터 조회 방식 학습
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class TestCacheRepository {
    private final StringRedisTemplate redisTemplate;

    /**
     * 방법 1. HashOperation 활용
     * 캐시에서 데이터를 조회할 때 다수의 컬럼이 존재할 경우 모든 값을 가져와서 사용하는 것은 어려운 일입니다.
     * 하지만, HashOperations을 활용하여 데이터를 조회할 경우, 엔티티를 생성하지 않고도 특정 컬럼에 접근할 수 있습니다.
     * 단, 이 경우 데이터를 삽입할 시 hash를 활용하여 삽입을 해야만 합니다.
     */
    public void setUpHash(String username) {
        String key = getHashKey(username);
        log.info("username : {}", username);

        if (Boolean.TRUE.equals(redisTemplate.hasKey(key))) {
            redisTemplate.delete(key);
            log.info("기존 키 delete : {}", key);
        }

        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();

        Map<String, String> map = Map.ofEntries(
                Map.entry("id", UUID.randomUUID().toString()),
                Map.entry("name", username),
                Map.entry("age", "28"),
                Map.entry("gender", username.length() % 2 == 1 ? "male" : "female"),
                Map.entry("email", username + "@example.com")
        );

        hashOperations.putAll(key, map);
        log.info("데이터 저장 완료");
    }


    public String findColumn(String username, String column) {
        String key = getHashKey(username);
        //logging
        log.info("전달된 data - username : {}, column : {}", username, column);
        log.info("사용되는 key : {}", key);

        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();
        Map<String, String> hashMap = hashOperations.entries(key);

        if (hashMap.isEmpty() || !StringUtils.hasText(hashMap.get(column)))
            return String.format("\"%s\" 는 정보를 가지고 있지 않은 회원입니다.", username);

        return hashMap.get(column);
    }

    public String getHashKey(String user) {
        return String.format("hash:user:%s", user);
    }
}
