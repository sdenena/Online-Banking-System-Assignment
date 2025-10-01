package com.sd.onlinebankingsystemassignment.dto.users;

import com.sd.onlinebankingsystemassignment.models.Permission;
import com.sd.onlinebankingsystemassignment.models.Role;
import com.sd.onlinebankingsystemassignment.models.Users;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleUpdateDto {
    private Long id = null;

    @NotBlank(message = "Name is required")
    private String name = null;

    private Boolean admin = false;

    private List<Long> permissions;

    public Role updateRole(Role r) {
        var permissionObjs = permissions.stream().map(Permission::new).collect(Collectors.toSet());
        r.setRoleName(name);
        r.setAdmin(admin);
        r.getPermissions().clear();
        r.getPermissions().addAll(permissionObjs);

        return r;
    }
}
