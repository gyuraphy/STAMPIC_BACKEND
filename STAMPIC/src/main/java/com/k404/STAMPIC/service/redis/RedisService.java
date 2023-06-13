package com.k404.STAMPIC.service.redis;

import com.k404.STAMPIC.config.redis.RedisDao;
import com.k404.STAMPIC.domain.user.User;
import com.k404.STAMPIC.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;

import static com.k404.STAMPIC.enumeration.Result.REDIS_VALUE_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class RedisService {

    private final RedisDao redisDao;

    public Boolean setValues(String key, String value) {
        redisDao.setValues(key, value);
        return true;
    }

    public String getValues(String key) {
        String values = redisDao.getValues(key);

        if (values.isBlank()) {
            throw new CustomException(REDIS_VALUE_NOT_FOUND);
        }

        return values;
    }

    public Boolean logoutFromRedis(String email, String accessToken, Long accessTokenExpiration) {
        redisDao.deleteValues(email);
        redisDao.setValues(accessToken, "logout", Duration.ofMillis(accessTokenExpiration));
        return true;
    }

}
