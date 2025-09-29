package com.sd.onlinebankingsystemassignment.controllers;

import com.sd.onlinebankingsystemassignment.base.response.ResponseMessage;
import com.sd.onlinebankingsystemassignment.dto.users.UserCreateDto;
import com.sd.onlinebankingsystemassignment.dto.users.UserUpdateDto;
import com.sd.onlinebankingsystemassignment.services.UserService;
import com.sd.onlinebankingsystemassignment.utils.Constant;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Constant.MAIN_PATH + "/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseMessage registerUser(@RequestBody UserCreateDto userDto) {
        return userService.registerUser(userDto.toUser());
    }

    @PutMapping("/{id}")
    public ResponseMessage updateUser(@PathVariable Long id, @RequestBody UserUpdateDto userDto) {
        return userService.updateUser(id, userDto);
    }
}
