package com.airfrance.userApp.service;

import java.util.List;
import java.util.Optional;

import com.airfrance.userApp.entity.User;


public interface UserService {
	User createUser(User user);

    List<User> getAll();

    Optional<User> getUserById(Long id);
}
