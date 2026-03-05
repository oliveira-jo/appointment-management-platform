package com.devjoliveira.appointmentmanagementapi.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.devjoliveira.appointmentmanagementapi.domain.User;
import com.devjoliveira.appointmentmanagementapi.dto.UserDTO;
import com.devjoliveira.appointmentmanagementapi.security.CustomUserDetails;
import com.devjoliveira.appointmentmanagementapi.service.exceptions.ResourceNotFoundException;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  private final UserService userService;

  // private final Logger logger;

  public UserDetailsServiceImpl(UserService userService) {
    this.userService = userService;
    // this.logger = LoggerFactory.getLogger(this.getClass());

  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    // logger.info("Getting information for user {}", email);
    UserDTO dto = userService.findByEmail(email);
    if (dto == null) {
      throw new ResourceNotFoundException("Could not find user");
    }
    User user = new User(dto);
    return new CustomUserDetails(user);
  }

}