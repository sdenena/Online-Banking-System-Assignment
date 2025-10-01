package com.sd.onlinebankingsystemassignment.dto.users;

import com.sd.onlinebankingsystemassignment.models.Permission;
import com.sd.onlinebankingsystemassignment.models.Role;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RoleCreateDto {
    private Long id = null;

    @NotBlank(message = "Name is required")
    private String roleName = null;

    private Boolean admin = false;

    private List<Long> permissions;

    public Role toRole() {
        var permissionObjs = permissions.stream().map(Permission::new).collect(Collectors.toSet());
        return new Role(id, roleName, admin, permissionObjs);
    }
}
