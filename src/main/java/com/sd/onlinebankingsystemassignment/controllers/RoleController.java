package com.sd.onlinebankingsystemassignment.controllers;

import com.sd.onlinebankingsystemassignment.aop.AuditFilter;
import com.sd.onlinebankingsystemassignment.base.response.ResponseMessage;
import com.sd.onlinebankingsystemassignment.base.response.ResponseObj;
import com.sd.onlinebankingsystemassignment.base.response.ResponsePage;
import com.sd.onlinebankingsystemassignment.dto.users.RoleCreateDto;
import com.sd.onlinebankingsystemassignment.dto.users.RoleDto;
import com.sd.onlinebankingsystemassignment.dto.users.RoleResponseDto;
import com.sd.onlinebankingsystemassignment.dto.users.RoleUpdateDto;
import com.sd.onlinebankingsystemassignment.models.Role;
import com.sd.onlinebankingsystemassignment.services.RoleService;
import com.sd.onlinebankingsystemassignment.utils.Constant;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Constant.MAIN_PATH + "/role")
@RequiredArgsConstructor
public class RoleController {
    private  final RoleService roleService;

    @AuditFilter
    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CREATE_USER')")
    public ResponseObj<RoleResponseDto> createRole(@Valid @RequestBody RoleCreateDto roleRequestDto) {
        return new ResponseObj<>(roleService.createRole(roleRequestDto));
    }

    @AuditFilter()
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'VIEW_USER')")
    public ResponseObj<RoleResponseDto> getRoleById(@PathVariable Long id) {
        return new ResponseObj<>(roleService.getRoleDetail(id).toRoleResponseDto());
    }

    @AuditFilter()
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'UPDATE_USER')")
    public ResponseObj<RoleResponseDto> updateRole(@PathVariable Long id, @Valid @RequestBody RoleUpdateDto roleDto) {
        return new ResponseObj<>(roleService.updateRole(id, roleDto));
    }

    @AuditFilter()
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'UPDATE_USER')")
    public ResponseMessage deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
        return new ResponseMessage(200, "Successfully deleted role");
    }

    @AuditFilter()
    @GetMapping("/list")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'VIEW_USER')")
    public ResponsePage<RoleResponseDto> getRoleListPage(
            @RequestParam(required = false) String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        var listPage = roleService.getRoleList(query, page, size);
        return new ResponsePage<>(listPage.getContent(), listPage.getTotalElements());
    }
}
