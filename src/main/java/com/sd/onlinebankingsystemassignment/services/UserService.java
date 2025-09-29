package com.sd.onlinebankingsystemassignment.services;

import com.sd.onlinebankingsystemassignment.base.response.ResponseMessage;
import com.sd.onlinebankingsystemassignment.base.response.ResponseObj;
import com.sd.onlinebankingsystemassignment.dto.login.LoginRequestDto;
import com.sd.onlinebankingsystemassignment.dto.login.LoginResponseDto;
import com.sd.onlinebankingsystemassignment.dto.users.UserUpdateDto;
import com.sd.onlinebankingsystemassignment.models.Users;

public interface UserService {
    ResponseObj<LoginResponseDto> login(LoginRequestDto loginRequestDto);

    ResponseMessage registerUser(Users request);

    ResponseMessage updateUser(Long id, UserUpdateDto request);
}
