package com.sd.onlinebankingsystemassignment.services;

import com.sd.onlinebankingsystemassignment.dto.users.RoleCreateDto;
import com.sd.onlinebankingsystemassignment.dto.users.RoleDto;
import com.sd.onlinebankingsystemassignment.dto.users.RoleResponseDto;
import com.sd.onlinebankingsystemassignment.dto.users.RoleUpdateDto;
import com.sd.onlinebankingsystemassignment.models.Role;
import org.springframework.data.domain.Page;

public interface RoleService {
    RoleResponseDto createRole(RoleCreateDto req);
    RoleResponseDto updateRole(Long id, RoleUpdateDto req);
    Role getRoleDetail(Long id);
    Page<RoleResponseDto> getRoleList(String query, int page, int size);
    void deleteRole(Long id);
}
