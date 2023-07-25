package com.vuducminh.stylash.auth;

import com.vuducminh.stylash.user.Role;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequest {

  private String avatar;
  private String firstname;
  private String lastname;
  private String email;
  private String address;
  private String phone_number;
  private String password;
  private Role role;
}
