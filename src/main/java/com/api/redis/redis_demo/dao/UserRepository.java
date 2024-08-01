package com.api.redis.redis_demo.dao;

import com.api.redis.redis_demo.model.User;
import com.api.redis.redis_demo.util.RedisHelper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final RedisTemplate<String, Object> redisTemplate;

    private final Logger logger = LoggerFactory.getLogger(UserRepository.class);

    private final String HASH_KEY = "users";

    public User save(User user) throws Exception {
        Map<String,Object> userMap = RedisHelper.convertObjectToMap(user);
        String key = HASH_KEY + ":" + user.getId();
        redisTemplate.opsForHash().putAll(key,userMap);
        logger.info("User {} got stored in redis hash with key {}",user,user.getId());
        return findById(user.getId());
    }

    public User findById(String id) throws Exception {
        Map<Object,Object> userMap = redisTemplate.opsForHash().entries(HASH_KEY + ":" + id);
        logger.info("Retrieved user : {}", userMap);
        return RedisHelper.convertMapToObject((Map<String, Object>) (Map<?, ?>) userMap, User.class);
    }

    public List<User> findAll() throws Exception {
        Set<String> keys = redisTemplate.keys(HASH_KEY + ":*");
        List<User> users = new ArrayList<>();
        if (keys != null) {
            for (String key : keys) {
                Map<Object, Object> userMap = redisTemplate.opsForHash().entries(key);
                User user = RedisHelper.convertMapToObject((Map<String, Object>) (Map<?, ?>) userMap, User.class);
                users.add(user);
            }
        }
        logger.info("Retrieved users : {}", users);
        return users;
    }

    public User update(String id,User user) throws Exception {
        user.setId(id);
        save(user); // Since we're using putAll, this will update existing fields and add new ones
        User retrievedUser = findById(HASH_KEY + ":" + user.getId());
        logger.info("Updated user details are : {}", retrievedUser);
        return user;
    }

    public String delete(String id) throws Exception {
        String key = HASH_KEY + ":" + id;
        logger.info("Deleted User : {}",findById(id));
        redisTemplate.delete(key);
        return "User deleted successfully";
    }
}
