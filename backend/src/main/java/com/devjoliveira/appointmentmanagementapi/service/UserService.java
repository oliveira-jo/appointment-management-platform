package com.devjoliveira.appointmentmanagementapi.service;

import java.util.List;
import java.util.UUID;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devjoliveira.appointmentmanagementapi.domain.User;
import com.devjoliveira.appointmentmanagementapi.dto.UserResponseDTO;
import com.devjoliveira.appointmentmanagementapi.dto.UserRequestDTO;
import com.devjoliveira.appointmentmanagementapi.enums.UserRole;
import com.devjoliveira.appointmentmanagementapi.repository.UserRepository;
import com.devjoliveira.appointmentmanagementapi.service.exceptions.DatabaseException;
import com.devjoliveira.appointmentmanagementapi.service.exceptions.ResourceNotFoundException;

@Service
public class UserService {

  private final UserRepository userRepository;

  public UserService(UserRepository customerRepository) {
    this.userRepository = customerRepository;
  }

  @Transactional(readOnly = true)
  public Page<UserRequestDTO> findAllProfessionalsPage(Pageable pageable) {
    Page<User> page = userRepository.findByRole(UserRole.ROLE_PROFESSIONAL, pageable);
    return page.map(UserRequestDTO::new);
  }

  @Transactional(readOnly = true)
  public Page<UserRequestDTO> findAllCustomersPage(Pageable pageable) {
    Page<User> page = userRepository.findByRole(UserRole.ROLE_CUSTOMER, pageable);
    return page.map(UserRequestDTO::new);
  }

  @Transactional(readOnly = true)
  public List<UserRequestDTO> findAllProfessionals() {
    return userRepository.findByRole(UserRole.ROLE_PROFESSIONAL).stream().map(UserRequestDTO::new).toList();
  }

  @Transactional(readOnly = true)
  public List<UserRequestDTO> findAllCustomers() {
    return userRepository.findByRole(UserRole.ROLE_CUSTOMER).stream().map(UserRequestDTO::new).toList();
  }

  @Transactional(readOnly = true)
  public UserResponseDTO findByEmail(String email) {
    return userRepository.findByEmail(email).map(UserResponseDTO::new).orElseThrow(
        () -> new ResourceNotFoundException("User not found with email: " + email));
  }

  @Transactional(readOnly = true)
  public UserResponseDTO findById(UUID id) {
    return userRepository.findById(id).map(UserResponseDTO::new).orElseThrow(
        () -> new ResourceNotFoundException("User not found with id: " + id));
  }

  @Transactional(readOnly = true)
  public User findByName(String name) {
    User user = userRepository.findByName(name);
    if (user == null) {
      throw new ResourceNotFoundException("User not found with name: " + name);
    }
    return user;
  }

  @Transactional
  public UserResponseDTO save(UserRequestDTO request, UserRole role) {
    userRepository.findByEmail(request.email()).ifPresent(customer -> {
      throw new DuplicateKeyException("User with email " + customer.getEmail() + " already exists");
    });

    User entity = new User();
    entity.setName(request.name());
    entity.setPhone(request.phone());
    entity.setEmail(request.email());
    entity.setRole(role);

    if (request.password() == null || request.password().isBlank())
      entity.setPassword(null);
    else
      entity.setPassword(new BCryptPasswordEncoder().encode(request.password()));

    var fromDB = userRepository.save(entity);
    return new UserResponseDTO(fromDB);
  }

  @Transactional
  public UserResponseDTO change(UUID id, UserRequestDTO request) {

    var fromDB = userRepository.findById(id).orElseThrow(
        () -> new ResourceNotFoundException("User with id " + id + " not found"));

    if (!fromDB.getEmail().equals(request.email())) {
      userRepository.findByEmail(request.email()).ifPresent(customer -> {
        throw new DuplicateKeyException("User with email " + customer.getEmail() + " already exists");
      });
    }

    fromDB.setName(request.name());
    fromDB.setPhone(request.phone());
    fromDB.setEmail(request.email());

    var updatedCustomer = userRepository.save(fromDB);
    return new UserResponseDTO(updatedCustomer);
  }

  @Transactional
  public void delete(UUID id) {
    var fromDB = userRepository.findById(id).orElseThrow(
        () -> new ResourceNotFoundException("Customer with id " + id + " not found"));

    try {
      userRepository.delete(fromDB);
    } catch (DataIntegrityViolationException e) {
      throw new DatabaseException("Fail in reference integrity");
    }

  }

}
