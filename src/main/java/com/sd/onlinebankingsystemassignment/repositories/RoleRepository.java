package com.sd.onlinebankingsystemassignment.repositories;

import com.sd.onlinebankingsystemassignment.base.repository.BaseRepository;
import com.sd.onlinebankingsystemassignment.models.Permission;
import com.sd.onlinebankingsystemassignment.models.Role;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoleRepository extends BaseRepository<Role> {
    @Query(value = """
        SELECT DISTINCT p
        FROM Users u
        JOIN u.roles r
        JOIN r.permissions p
        WHERE u.id = :userId
    """)
   List<Permission> getRolePermissionByUserId(Long userId);
}
