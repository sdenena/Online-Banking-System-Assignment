package com.sd.onlinebankingsystemassignment.services;

import com.sd.onlinebankingsystemassignment.dto.users.RoleDto;
import com.sd.onlinebankingsystemassignment.models.Role;
import org.springframework.data.domain.Page;

public interface RoleService {
    Role createRole(Role req);
    Role updateRole(Long id, RoleDto req);
    Role getRoleDetail(Long id);
    Page<Role> getRoleList(String query, int page, int size);
    void deleteRole(Long id);
}
