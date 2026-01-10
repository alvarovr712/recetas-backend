package com.recetasAPI.model.dtos;

import com.recetasAPI.model.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDTO {

    private String username;
    private Role role;
}
