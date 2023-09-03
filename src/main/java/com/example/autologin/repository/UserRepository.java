package com.example.autologin.repository;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;
import com.example.autologin.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


@EnableJpaRepositories
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    public User findUserByEmail(String email);
}
