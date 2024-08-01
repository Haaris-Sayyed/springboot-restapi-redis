package com.api.redis.redis_demo.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.Map;

public class RedisHelper {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static Map<String, Object> convertObjectToMap(Object obj) throws Exception {
        return objectMapper.convertValue(obj, new TypeReference<Map<String, Object>>() {});
    }

    public static <T> T convertMapToObject(Map<String, Object> map, Class<T> type) throws Exception {
        return objectMapper.convertValue(map, type);
    }
}
