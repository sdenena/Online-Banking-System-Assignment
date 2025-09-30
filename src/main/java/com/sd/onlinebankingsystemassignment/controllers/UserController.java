package com.sd.onlinebankingsystemassignment.controllers;

import com.sd.onlinebankingsystemassignment.aop.AuditFilter;
import com.sd.onlinebankingsystemassignment.base.response.ResponseMessage;
import com.sd.onlinebankingsystemassignment.base.response.ResponseObj;
import com.sd.onlinebankingsystemassignment.base.response.ResponsePage;
import com.sd.onlinebankingsystemassignment.dto.account.AccountResponseDto;
import com.sd.onlinebankingsystemassignment.dto.users.UserCreateDto;
import com.sd.onlinebankingsystemassignment.dto.users.UserResponseDto;
import com.sd.onlinebankingsystemassignment.dto.users.UserUpdateDto;
import com.sd.onlinebankingsystemassignment.services.UserService;
import com.sd.onlinebankingsystemassignment.utils.Constant;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Constant.MAIN_PATH + "/users")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseMessage registerUser(@RequestBody UserCreateDto userDto) {
        return userService.registerUser(userDto.toUser());
    }

    @PutMapping("/{id}")
    public ResponseMessage updateUser(@PathVariable Long id, @RequestBody UserUpdateDto userDto) {
        userService.updateUser(id, userDto);
        return new ResponseMessage(200, "Successfully updated user");
    }

    @AuditFilter()
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponsePage<UserResponseDto> getAccountListPage(
            @RequestParam(required = false) String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        var listPage = userService.getUserList(query, page, size);

        return new ResponsePage<>(listPage.getContent(), listPage.getTotalElements());
    }

    @AuditFilter()
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseObj<UserResponseDto> getAccountById(@PathVariable Long id) {
        return new ResponseObj<>(userService.getUserById(id));
    }
}
