package com.sd.onlinebankingsystemassignment.configuration;

import com.sd.onlinebankingsystemassignment.models.Users;
import com.sd.onlinebankingsystemassignment.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("auditorProvider")
@RequiredArgsConstructor
public class AuditorAwareImpl implements AuditorAware<Long> {
    private static final Long SYSTEM_USER_ID = 0L;
    private final UserRepository userRepository;

    @Override
    public Optional<Long> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Debug logging
        System.out.println("Authentication: " + authentication);
        System.out.println("Is Authenticated: " + (authentication != null && authentication.isAuthenticated()));

        if (authentication == null || !authentication.isAuthenticated()
                || authentication instanceof AnonymousAuthenticationToken) {
            System.out.println("Returning SYSTEM_USER_ID - no authentication");
            return Optional.of(SYSTEM_USER_ID);
        }

        Object principal = authentication.getPrincipal();
        System.out.println("Principal class: " + principal.getClass().getName());
        System.out.println("Principal: " + principal);

        // Principal is a String (username)
        if (principal instanceof String username) {
            return userRepository.findByUsernameIgnoreCaseOrEmailIgnoreCaseAndStatusTrue(username, username)
                    .map(Users::getId) // Replace 'User' with your actual entity class
                    .or(() -> Optional.of(SYSTEM_USER_ID));
        }

        System.out.println("SYSTEM_USER_ID: " + SYSTEM_USER_ID);

        return Optional.of(SYSTEM_USER_ID);
    }
}
