package com.example.ModaMint_Backend.repository;

import com.example.ModaMint_Backend.entity.Role;
import com.example.ModaMint_Backend.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;


public interface RoleRepository extends JpaRepository<Role, String> {
    <T> Optional<T> findByName(RoleName name);
}
