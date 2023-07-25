package com.vuducminh.stylash.controller;

import com.vuducminh.stylash.controller.dto.UserDto;
import com.vuducminh.stylash.service.EmailService;
import com.vuducminh.stylash.service.UserService;
import com.vuducminh.stylash.user.User;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

//    @GetMapping("/me")
//    public ResponseEntity<User> getAllUsers() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String username = authentication.getName();
//        User user = userService.getUserByEmail("mvu7179@gmail.com");
//        return ResponseEntity.ok(user);
//    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Integer userId) {
        User user = userService.getUserById(userId);
        return ResponseEntity.ok(user);
    }
    @GetMapping("/countUsers")
    public ResponseEntity<Integer> countUsers() {
        return ResponseEntity.ok(userService.countManagers());
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @PutMapping("/{userId}")
    public User updateUser(@PathVariable Integer userId, @RequestBody User user) {
        user.setId(userId);
        return userService.updateUser(user);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Integer userId) {
        userService.deleteUser(userId);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam("email") String email) {

        System.out.println(email);

        String newPassword = generateRandomPassword();
        sendPasswordEmail(email, newPassword);

        User user = userService.validateAndGetUserByUsername(email);

        if (user == null) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userService.updateUser(user);

        return new ResponseEntity<>("An email with the new password has been sent to the user.", HttpStatus.OK);
    }


    private String generateRandomPassword() {
        int password = (int) (Math.random() * 900000) + 100000;
        return String.valueOf(password);
    }

    private void sendPasswordEmail(String email, String password) {
        String to = email;
        String subject = "Your password has been reset, please use this password to login";
        String text = password;

        emailService.sendEmail(to, subject, text);
    }
}

