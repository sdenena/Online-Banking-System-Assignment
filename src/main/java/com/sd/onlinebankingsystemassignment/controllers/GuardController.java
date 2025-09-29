package com.sd.onlinebankingsystemassignment.controllers;

import com.sd.onlinebankingsystemassignment.base.response.ResponseObj;
import com.sd.onlinebankingsystemassignment.dto.login.LoginRequestDto;
import com.sd.onlinebankingsystemassignment.dto.login.LoginResponseDto;
import com.sd.onlinebankingsystemassignment.services.UserService;
import com.sd.onlinebankingsystemassignment.utils.Constant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(Constant.MAIN_PATH + "/guards")
@RequiredArgsConstructor
public class GuardController {
    private final UserService userService;

    @PostMapping("/log-in")
    public ResponseObj<LoginResponseDto> logIn(@RequestBody LoginRequestDto loginRequestDto) {
        return userService.login(loginRequestDto);
    }
}
