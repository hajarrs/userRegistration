package com.airfrance.userApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.airfrance.userApp.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
