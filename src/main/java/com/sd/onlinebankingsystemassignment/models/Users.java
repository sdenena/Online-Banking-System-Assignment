package com.sd.onlinebankingsystemassignment.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sd.onlinebankingsystemassignment.base.entity.BaseEntity;
import com.sd.onlinebankingsystemassignment.dto.account.AccountResponseDto;
import com.sd.onlinebankingsystemassignment.dto.users.RoleDto;
import com.sd.onlinebankingsystemassignment.dto.users.UserResponseDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;

import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "mas_user")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(value = {"password", "roles"}, allowSetters = true)
public class Users extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;

    @BatchSize(size = 10)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    // Convert Entity to Response DTO
    public UserResponseDto toResponseDto() {
        Set<RoleDto> roleDto = roles.stream()
                .map(Role::toRoleDto)
                .collect(Collectors.toSet());

        return new UserResponseDto(id, firstName, lastName, email, username, roleDto);
    }
}
