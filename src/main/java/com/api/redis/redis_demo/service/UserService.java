package com.api.redis.redis_demo.service;

import com.api.redis.redis_demo.dao.UserRepository;
import com.api.redis.redis_demo.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User saveUser(User user) throws Exception {
        user.setId(UUID.randomUUID().toString());
        return userRepository.save(user);
    }

    public User getById(String id) throws Exception {
        return userRepository.findById(id);
    }

    public List<User> getAllUsers() throws Exception {
        return userRepository.findAll();
    }

    public User updateUser(String id,User user) throws Exception {
        return userRepository.update(id,user);
    }

    public String deleteUser(String id) throws Exception {
        return userRepository.delete(id);
    }
}
