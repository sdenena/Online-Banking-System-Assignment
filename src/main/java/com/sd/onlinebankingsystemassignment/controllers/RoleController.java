package com.sd.onlinebankingsystemassignment.controllers;

import com.sd.onlinebankingsystemassignment.aop.AuditFilter;
import com.sd.onlinebankingsystemassignment.base.response.ResponseMessage;
import com.sd.onlinebankingsystemassignment.base.response.ResponseObj;
import com.sd.onlinebankingsystemassignment.base.response.ResponsePage;
import com.sd.onlinebankingsystemassignment.dto.users.RoleDto;
import com.sd.onlinebankingsystemassignment.models.Role;
import com.sd.onlinebankingsystemassignment.services.RoleService;
import com.sd.onlinebankingsystemassignment.utils.Constant;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Constant.MAIN_PATH + "/role")
@RequiredArgsConstructor
public class RoleController {
    private  final RoleService roleService;

    @AuditFilter
    @PostMapping
    public ResponseObj<Role> createAccount(@Valid @RequestBody RoleDto roleRequestDto) {
        return new ResponseObj<>(roleService.createRole(roleRequestDto.toRole()));
    }

    @AuditFilter()
    @GetMapping("/{id}")
    public ResponseObj<RoleDto> getRoleById(@PathVariable Long id) {
        return new ResponseObj<>(roleService.getRoleDetail(id).toRoleDto());
    }

    @AuditFilter()
    @PutMapping("/{id}")
    public ResponseObj<Role> updateRole(@PathVariable Long id, @Valid @RequestBody RoleDto roleDto) {
        return new ResponseObj<>(roleService.updateRole(id, roleDto));
    }

    @AuditFilter()
    @DeleteMapping("/{id}")
    public ResponseMessage deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
        return new ResponseMessage();
    }

    @AuditFilter()
    @GetMapping("/list")
    public ResponsePage<RoleDto> getRoleListPage(
            @RequestParam(required = false) String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        var listPage = roleService.getRoleList(query, page, size);
        var roleList = listPage.getContent().stream().map(Role::toRoleDto).toList();
        return new ResponsePage<>(roleList, listPage.getTotalElements());
    }
}
