package com.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
@AllArgsConstructor
public class RegisterForm {
    private String email;
    private String username;
    private String password;
    private String repassword;
}
