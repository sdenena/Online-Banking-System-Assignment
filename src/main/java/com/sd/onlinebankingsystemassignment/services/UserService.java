package com.sd.onlinebankingsystemassignment.services;

import com.sd.onlinebankingsystemassignment.base.response.ResponseMessage;
import com.sd.onlinebankingsystemassignment.base.response.ResponseObj;
import com.sd.onlinebankingsystemassignment.dto.login.LoginRequestDto;
import com.sd.onlinebankingsystemassignment.dto.login.LoginResponseDto;
import com.sd.onlinebankingsystemassignment.dto.users.UserResponseDto;
import com.sd.onlinebankingsystemassignment.dto.users.UserUpdateDto;
import com.sd.onlinebankingsystemassignment.models.Users;
import org.springframework.data.domain.Page;

public interface UserService {
    ResponseObj<LoginResponseDto> login(LoginRequestDto loginRequestDto);

    ResponseMessage registerUser(Users request);

    ResponseMessage updateUser(Long id, UserUpdateDto request);

    Page<UserResponseDto> getUserList(String query, int page, int size);

    UserResponseDto getUserById(Long id);
}
