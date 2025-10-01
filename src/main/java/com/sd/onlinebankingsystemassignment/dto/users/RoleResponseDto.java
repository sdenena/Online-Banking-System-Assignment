package com.sd.onlinebankingsystemassignment.dto.users;

import com.sd.onlinebankingsystemassignment.models.Permission;
import com.sd.onlinebankingsystemassignment.models.Role;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleResponseDto {
    private Long id = null;
    private String name = null;
    private Boolean admin = false;
    private Set<Permission> permissions;
}
