package com.sd.onlinebankingsystemassignment.services.implementations;

import com.sd.onlinebankingsystemassignment.base.response.ResponseMessage;
import com.sd.onlinebankingsystemassignment.base.response.ResponseObj;
import com.sd.onlinebankingsystemassignment.dto.LoginRequestDto;
import com.sd.onlinebankingsystemassignment.dto.LoginResponseDto;
import com.sd.onlinebankingsystemassignment.dto.UserUpdateDto;
import com.sd.onlinebankingsystemassignment.exception.ApiErrorException;
import com.sd.onlinebankingsystemassignment.models.Users;
import com.sd.onlinebankingsystemassignment.repositories.UserRepository;
import com.sd.onlinebankingsystemassignment.security.UserPrinciple;
import com.sd.onlinebankingsystemassignment.services.UserService;
import com.sd.onlinebankingsystemassignment.utils.JwtTokenUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;

    @Override
    public ResponseObj<LoginResponseDto> login(LoginRequestDto loginRequestDto) {
        final var user = userRepository.findByUsernameIgnoreCaseOrEmailIgnoreCaseAndStatusTrue(loginRequestDto.username(), loginRequestDto.username()).orElseThrow(() ->
                new ApiErrorException(401, "Invalid username or password"));
        if (!passwordEncoder.matches(loginRequestDto.password(), user.getPassword())) {
            throw new ApiErrorException(401, "Invalid username or password");
        }
        final var userAuthorities = user.getRoles().stream().map(r -> new SimpleGrantedAuthority(r.getRoleName())).toList();
        final var userDetail = new UserPrinciple(
                user.getId(), user.getUsername(), user.getPassword(), userAuthorities
        );
        final var token = jwtTokenUtil.generateToken(userDetail);
        return new ResponseObj<>(new LoginResponseDto(token));
    }

    @Override
    public ResponseMessage registerUser(Users request) {
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(request);
        return new ResponseMessage();
    }

    @Override
    public ResponseMessage updateUser(Long id, UserUpdateDto request) {
        final var user = userRepository.findByIdAndStatusTrue(id).orElseThrow(() -> new ApiErrorException(404, "User not found"));
        var userUpdated = request.updateUser(user);
        userRepository.save(userUpdated);
        return new ResponseMessage();
    }
}
