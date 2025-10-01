package com.sd.onlinebankingsystemassignment.services.implementations;

import com.sd.onlinebankingsystemassignment.dto.users.RoleCreateDto;
import com.sd.onlinebankingsystemassignment.dto.users.RoleResponseDto;
import com.sd.onlinebankingsystemassignment.dto.users.RoleUpdateDto;
import com.sd.onlinebankingsystemassignment.exception.CustomException;
import com.sd.onlinebankingsystemassignment.models.Role;
import com.sd.onlinebankingsystemassignment.repositories.RoleRepository;
import com.sd.onlinebankingsystemassignment.services.RoleService;
import jakarta.persistence.criteria.Predicate;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public RoleResponseDto createRole(RoleCreateDto req) {
        logger.info("createRole: {}", req);
        RoleResponseDto roleResponseDto = new RoleResponseDto();
        Role role = new Role();

        BeanUtils.copyProperties(req, role);
        var roleSave = roleRepository.save(role);

        BeanUtils.copyProperties(roleSave, roleResponseDto);

        return roleResponseDto;
    }

    @Override
    public RoleResponseDto updateRole(Long id, RoleUpdateDto req) {
        logger.info("updateRole - id: {}, req: {}", id, req.toString());
        RoleResponseDto roleResponseDto = new RoleResponseDto();

        Role roleObj = getRoleDetail(id);
        Role updatedRole = req.updateRole(roleObj);
        var roleUpdate = roleRepository.save(updatedRole);

        BeanUtils.copyProperties(roleUpdate, roleResponseDto);

        return roleResponseDto;
    }

    @Override
    public Role getRoleDetail(Long id) {
        logger.info("getRoleDetail - id: {}", id);
        return roleRepository.findByIdAndStatusTrue(id).orElseThrow(() -> new CustomException(404, "Role not found"));
    }

    @Override
    public Page<RoleResponseDto> getRoleList(String query, int page, int size) {
        logger.info("getRoleList - query: {}, page: {}, size: {}", query, page, size);
        var rolePage = roleRepository.findAll((root, cq, cb) -> {
            ArrayList<Predicate> predicates = new ArrayList<>();

            if (query != null) {
                var searchRoleName = cb.like(cb.upper(root.get("roleName")), "%" + query.toUpperCase() + "%");
                predicates.add(cb.or(searchRoleName));
            }

            predicates.add(cb.isTrue(root.get("status")));
            Objects.requireNonNull(cq).orderBy(cb.desc(root.get("id")));
            return cb.and(predicates.toArray(new Predicate[0]));
        }, PageRequest.of(page, size));

        return rolePage.map(Role::toRoleResponseDto);
    }

    @Override
    public void deleteRole(Long id) {
        Role roleObj = getRoleDetail(id);
        roleObj.setStatus(false);

        roleRepository.save(roleObj);
    }
}
