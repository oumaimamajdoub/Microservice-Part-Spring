package com.example.autologin.repository;

import com.example.autologin.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RoleRepository extends JpaRepository<Role, String> {
}
