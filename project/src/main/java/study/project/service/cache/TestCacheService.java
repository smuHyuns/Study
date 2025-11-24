package study.project.service.cache;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TestCacheService {
    private final StringRedisTemplate stringRedisTemplate;

    public void setup() {

    }
}
