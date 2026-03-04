package com.devjoliveira.appointmentmanagementapi.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devjoliveira.appointmentmanagementapi.domain.User;

public interface UserRepository extends JpaRepository<User, UUID> {

	// @Query(nativeQuery = true, value = """
	// SELECT tb_user.email AS username, tb_user.password, tb_role.id AS roleId,
	// tb_role.authority
	// FROM tb_user
	// INNER JOIN tb_user_role ON tb_user.id = tb_user_role.user_id
	// INNER JOIN tb_role ON tb_role.id = tb_user_role.role_id
	// WHERE tb_user.email = :email
	// """)
	// List<UserDetailsProjection> searchUserAndRolesByEmail(String email);

	Optional<User> findByEmail(String email);

}