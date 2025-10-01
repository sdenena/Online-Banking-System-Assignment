package com.sd.onlinebankingsystemassignment.dto.users;

import com.sd.onlinebankingsystemassignment.models.Role;
import com.sd.onlinebankingsystemassignment.models.Users;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateDto {
    private Long id;
    private String firstName;
    private String lastName;
    @NotBlank(message = "Email is required")
    private String email;
    @NotBlank(message = "Username is required")
    private String username;
    private String password;
    @NotNull(message = "At least one role is required")
    private List<Long> roles;

    public Users toUser() {
        var roleObjs = roles.stream().map(Role::new).collect(Collectors.toSet());
        return new Users(id, firstName, lastName, email, username, password, roleObjs);
    }
}
