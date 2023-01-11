package com.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChangePassForm {
    private String oldPassword;
    private String newPassword;
}
