package com.sd.onlinebankingsystemassignment.services;

import com.sd.onlinebankingsystemassignment.base.response.ResponseMessage;
import com.sd.onlinebankingsystemassignment.base.response.ResponseObj;
import com.sd.onlinebankingsystemassignment.dto.LoginRequestDto;
import com.sd.onlinebankingsystemassignment.dto.LoginResponseDto;
import com.sd.onlinebankingsystemassignment.dto.UserUpdateDto;
import com.sd.onlinebankingsystemassignment.models.Users;

public interface UserService {
    ResponseObj<LoginResponseDto> login(LoginRequestDto loginRequestDto);

    ResponseMessage registerUser(Users request);

    ResponseMessage updateUser(Long id, UserUpdateDto request);
}
