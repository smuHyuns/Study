package study.project.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;
import study.project.dto.ValueOpsDto;

/**
 * Redis의 여러 데이터 조회 방식 학습
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class TestCacheRepository2 {
    private final StringRedisTemplate redisTemplate;
    private final ObjectMapper objectMapper;

    /**
     * 방법 2.ValueOperation
     * ValueOperations는 Redis의 String 타입을 사용하여
     * 한 개의 Key에 직렬화된 객체(JSON 등)를 그대로 저장하는 방식입니다.
     * 장점
     * 1) GET key 한 번으로 객체 전체를 조회할 수 있어 사용이 단순합니다.
     * 2) 객체 단위 캐시에 적합하며, TTL 역시 Key 단위로 간단히 설정할 수 있습니다.
     * <p>
     * 단점
     * 1) 특정 필드만 수정하고 싶어도, 전체 객체를 가져와서 수정 후 다시 저장해야 하므로 부분 업데이트에 비효율적입니다.
     * 2) 특정 필드만 조회하더라도 전체 객체를 역직렬화해야 하므로,
     * 일부 필드만 자주 사용하는 경우에는 Hash 타입보다 비효율적일 수 있습니다.
     * <p>
     * HashOperation은 필드 단위의 TTL 설정이 불가능하다는 단점을 가지고 있습니다.
     * 또한, List혹은 Array형식의 객체를 다루기에는 적합하지 않습니다.
     * <p>
     * 하지만, ValueOperation을 활용한다면 필드단위의 TTL설정이 가능하며,
     * GET key하나로 데이터를 조회가능하므로 읽기의 성능이 좋습니다.
     * 그러나 부분업데이트를 실시하고 싶을 시 전체 객체를 업로드해야하므로, 해당 부분에 있어서는 단점을 가지고 있습니다.
     */

    public ValueOpsDto.SearchResponse find(String username) {
        log.info("들어온 데이터 : {}", username);

        // NULL POINTER 에러 조심!
        if (!StringUtils.hasText(username)) return null;
        String key = getKey(username);
        // 데이터를 찾습니다.
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        String json = ops.get(key);
        if (!StringUtils.hasText(json))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "존재하지 않는 KEY입니다 : " + key);
        // 찾은 데이터를 직렬화하여 객체로 변환합니다.
        ValueOpsDto.SavedData data;
        try {
            data = objectMapper.readValue(json, ValueOpsDto.SavedData.class);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "존재하지 않는 KEY입니다 : " + key);
        }
        // 원하는 응답으로 가공합니다.
        return ValueOpsDto.SearchResponse.builder()
                .isEqual(data.getUsername().equals(username))
                .saveData(data)
                .build();
    }


    /**
     * 값을 저장합니다.
     */
    public void setValueOperation(ValueOpsDto.SaveRequest req) {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        String valueKey = getKey(req.getUsername());
        String saveFormat = """
                {
                    "username" : "%s",
                    "password" : "%s"
                }
                """;
        ops.set(valueKey, String.format(saveFormat, req.getUsername(), req.getPassword()));

        // or
        // String saveTarget = objectMapper.writeValueAsString(타겟);
        // ops.set(valueKey, saveTarget);
    }

    private String getKey(String username) {
        return String.format("study:value:%s", username);
    }
}
