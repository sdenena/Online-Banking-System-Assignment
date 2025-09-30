package com.sd.onlinebankingsystemassignment.services.implementations;

import com.sd.onlinebankingsystemassignment.base.response.ResponseMessage;
import com.sd.onlinebankingsystemassignment.base.response.ResponseObj;
import com.sd.onlinebankingsystemassignment.dto.login.LoginRequestDto;
import com.sd.onlinebankingsystemassignment.dto.login.LoginResponseDto;
import com.sd.onlinebankingsystemassignment.dto.users.UserResponseDto;
import com.sd.onlinebankingsystemassignment.dto.users.UserUpdateDto;
import com.sd.onlinebankingsystemassignment.exception.ApiErrorException;
import com.sd.onlinebankingsystemassignment.exception.CustomException;
import com.sd.onlinebankingsystemassignment.models.Role;
import com.sd.onlinebankingsystemassignment.models.Users;
import com.sd.onlinebankingsystemassignment.repositories.UserRepository;
import com.sd.onlinebankingsystemassignment.security.UserPrinciple;
import com.sd.onlinebankingsystemassignment.services.UserService;
import com.sd.onlinebankingsystemassignment.utils.JwtTokenUtil;
import jakarta.persistence.criteria.Predicate;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;
    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public ResponseObj<LoginResponseDto> login(LoginRequestDto loginRequestDto) {
        final var user = userRepository.findByUsernameIgnoreCaseOrEmailIgnoreCaseAndStatusTrue(loginRequestDto.username(), loginRequestDto.username()).orElseThrow(() ->
                new ApiErrorException(401, "Invalid username or password"));
        if (!passwordEncoder.matches(loginRequestDto.password(), user.getPassword())) {
            throw new ApiErrorException(401, "Invalid username or password");
        }

        List<GrantedAuthority> authorities = new ArrayList<>();

        // Add ADMIN role if any role has admin = true
        boolean isAdmin = user.getRoles().stream()
                .anyMatch(Role::getAdmin);

        authorities.add(isAdmin ? new SimpleGrantedAuthority("ROLE_ADMIN") : new SimpleGrantedAuthority("ROLE_USER"));

        final var userDetail = new UserPrinciple(
                user.getId(), user.getUsername(), user.getPassword(), authorities
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
    public void updateUser(Long id, UserUpdateDto request) {
        final var user = userRepository.findByIdAndStatusTrue(id).orElseThrow(() -> new ApiErrorException(404, "User not found"));
        var userUpdated = request.updateUser(user);
        userRepository.save(userUpdated);
        new ResponseMessage();
    }

    @Override
    public Page<UserResponseDto> getUserList(String query, int page, int size) {
        logger.info("getAccountList - query: {}, page: {}, size: {}", query, page, size);

        Page<Users> accountPage = userRepository.findAll((root, cq, cb) -> {
            ArrayList<Predicate> predicates = new ArrayList<>();

            if (query != null && !query.trim().isEmpty()) {
                var searchFirstName = cb.like(cb.upper(root.get("firstName")), "%" + query.toUpperCase() + "%");
                var searchLastName = cb.like(cb.upper(root.get("lastName")), "%" + query.toUpperCase() + "%");
                var searchUserName = cb.like(cb.upper(root.get("username")), "%" + query.toUpperCase() + "%");
                predicates.add(cb.or(searchFirstName, searchLastName, searchUserName));
            }

            // Fixed: changed "status" to "isActive" to match the entity field
            predicates.add(cb.isTrue(root.get("status")));
            Objects.requireNonNull(cq).orderBy(cb.desc(root.get("id")));
            return cb.and(predicates.toArray(new Predicate[0]));
        }, PageRequest.of(page, size));

        // Convert Page<Account> to Page<AccountResponseDto>
        return accountPage.map(Users::toResponseDto);
    }

    @Override
    public UserResponseDto getUserById(Long id) {
        logger.info("getUserById - id: {}", id);
        return userRepository.findByIdAndStatusTrue(id).orElseThrow(() -> new CustomException(404, "User not found")).toResponseDto();
    }
}
