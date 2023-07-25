package com.vuducminh.stylash.service;

import com.vuducminh.stylash.exception.UserNotFoundException;
import com.vuducminh.stylash.user.Role;
import com.vuducminh.stylash.user.User;
import com.vuducminh.stylash.user.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public List<User> getTopUsersByTotalAmount(int limit) {
        String queryStr = "SELECT o.user, SUM(o.totalAmount) AS totalAmount " +
                "FROM Order o " +
                "GROUP BY o.user " +
                "ORDER BY totalAmount DESC";
        TypedQuery<Object[]> query = entityManager.createQuery(queryStr, Object[].class);
        query.setMaxResults(limit);

        List<Object[]> results = query.getResultList();
        List<User> topUsers = results.stream()
                .map(result -> (User) result[0])
                .toList();

        return topUsers;
    }

    @Override
    public List<User> getTopUsersWithHighestTotalAmount(int limit) {
        return this.getTopUsersByTotalAmount(limit);
    }

    public Optional<User> getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userRepository.findByEmail(username);
    }

    @Override
    public List<User> getUsersByRole(Role role) {
        return userRepository.findByRole(role);
    }
    @Override
    public void deleteUser(User user) {
        userRepository.delete(user);
    }
    @Override
    public List<User> getUsersByRoleAndName(Role role, String name) {
        return userRepository.findByRoleAndLastnameContainingIgnoreCase(role, name);
    }

    @Override
    public int countManagers() {
        return userRepository.countByRole(Role.MANAGER);
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByEmail(username);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Integer userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public User validateAndGetUserByUsername(String username) {
        return getUserByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(String.format("User with username %s not found", username)));
    }


    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Integer userId) {
        userRepository.deleteById(userId);
    }
}

