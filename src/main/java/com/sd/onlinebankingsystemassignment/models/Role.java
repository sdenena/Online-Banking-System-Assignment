package com.sd.onlinebankingsystemassignment.models;

import com.sd.onlinebankingsystemassignment.base.entity.BaseEntity;
import com.sd.onlinebankingsystemassignment.dto.users.RoleDto;
import com.sd.onlinebankingsystemassignment.dto.users.RoleResponseDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import java.util.Set;

@Entity
@Table(name = "mas_role")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String roleName;
    private Boolean admin = false;

    @BatchSize(size = 10)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "role_permission", joinColumns = @JoinColumn(name = "role_id"), inverseJoinColumns = @JoinColumn(name = "permission_id"))
    private Set<Permission> permissions;

    public Role(Long id) {
        this.id = id;
    }

    public RoleResponseDto toRoleResponseDto() {
        return new RoleResponseDto(id, roleName, admin, permissions);
    }
}
