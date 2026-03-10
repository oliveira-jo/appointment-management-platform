package com.devjoliveira.appointmentmanagementapi.service;

import java.util.List;
import java.util.UUID;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devjoliveira.appointmentmanagementapi.domain.User;
import com.devjoliveira.appointmentmanagementapi.dto.UserDTO;
import com.devjoliveira.appointmentmanagementapi.dto.UserMinDTO;
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
  public List<UserMinDTO> findAll() {
    return userRepository.findAll().stream().map(UserMinDTO::new).toList();
  }

  @Transactional(readOnly = true)
  public List<UserMinDTO> findAllProfessionals() {
    return userRepository.findByRole(UserRole.ROLE_PROFESSIONAL).get().stream().map(UserMinDTO::new).toList();
  }

  @Transactional(readOnly = true)
  public List<UserMinDTO> findAllCustomers() {
    return userRepository.findByRole(UserRole.ROLE_CUSTOMER).get().stream().map(UserMinDTO::new).toList();
  }

  @Transactional(readOnly = true)
  public UserDTO findByEmail(String email) {
    return userRepository.findByEmail(email).map(UserDTO::new).orElseThrow(
        () -> new ResourceNotFoundException("User not found with email: " + email));
  }

  @Transactional(readOnly = true)
  public UserDTO findById(UUID id) {
    return userRepository.findById(id).map(UserDTO::new).orElseThrow(
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
  public UserDTO save(UserMinDTO request, UserRole role) {
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
    return new UserDTO(fromDB);
  }

  @Transactional
  public UserDTO change(UUID id, UserMinDTO request) {

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
    return new UserDTO(updatedCustomer);
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
