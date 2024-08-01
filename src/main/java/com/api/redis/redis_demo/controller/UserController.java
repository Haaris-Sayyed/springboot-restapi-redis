package com.api.redis.redis_demo.controller;

import com.api.redis.redis_demo.model.User;
import com.api.redis.redis_demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public User saveUser(@RequestBody User user) throws Exception {
        return userService.saveUser(user);
    }

    @GetMapping
    public List<User> getAllUsers() throws Exception {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getById(@PathVariable String id) throws Exception {
        return userService.getById(id);
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable String id, @RequestBody User user) throws Exception {
        return userService.updateUser(id,user);
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable String id) throws Exception {
        return userService.deleteUser(id);
    }
}
