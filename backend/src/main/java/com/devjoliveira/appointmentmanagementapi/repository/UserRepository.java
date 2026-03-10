package com.devjoliveira.appointmentmanagementapi.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devjoliveira.appointmentmanagementapi.domain.User;
import com.devjoliveira.appointmentmanagementapi.enums.UserRole;

public interface UserRepository extends JpaRepository<User, UUID> {

	Optional<User> findByEmail(String email);

	Optional<List<User>> findByRole(UserRole role);

	User findByName(String username);

}