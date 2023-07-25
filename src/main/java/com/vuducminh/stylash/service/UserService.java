package com.vuducminh.stylash.service;

import com.vuducminh.stylash.user.Role;
import com.vuducminh.stylash.user.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAllUsers();

    List<User> getTopUsersWithHighestTotalAmount(int limit);

    public Optional<User> getAuthenticatedUser();

    User getUserById(Integer userId);
    User validateAndGetUserByUsername(String username);

    List<User> getUsersByRole(Role role);

    List<User> getUsersByRoleAndName(Role role, String name);
    void deleteUser(User user);
    int countManagers();

    Optional<User> getUserByUsername(String username);

    User getUserByEmail(String email);

    User createUser(User user);
    User updateUser(User user);
    void deleteUser(Integer userId);
}

