package com.vuducminh.stylash.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Integer> {

  Optional<User> findByEmail(String email);
  int countByRole(Role role);
  List<User> findByRoleAndLastnameContainingIgnoreCase(Role role, String lastName);
  List<User> findByRole(Role role);

}
