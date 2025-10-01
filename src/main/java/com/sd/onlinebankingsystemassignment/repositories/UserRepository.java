package com.sd.onlinebankingsystemassignment.repositories;

import com.sd.onlinebankingsystemassignment.base.repository.BaseRepository;
import com.sd.onlinebankingsystemassignment.models.Users;
import java.util.Optional;

public interface UserRepository extends BaseRepository<Users> {
    Optional<Users> findByUsernameIgnoreCaseOrEmailIgnoreCaseAndStatusTrue(String username, String email);
    Optional<Users> findByUsernameIgnoreCaseAndStatusTrue(String username);
}
