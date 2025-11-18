package com.example.service;

import com.example.domain.Permission;
import com.example.domain.Role;
import com.example.domain.response.ResultPaginantionDTO;
import com.example.repository.PermissionRepository;
import com.example.repository.RoleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    public RoleService (RoleRepository roleRepository, PermissionRepository permissionRepository) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
    }

    public boolean existByName(String name) {
        return this.roleRepository.existsByName(name);
    }

    public Role create(Role role) {
        if (role.getPermissions() != null) {
            List<Long> reqPerissions = role.getPermissions()
                    .stream()
                    .map(Permission::getId)
                    .toList();
            List<Permission> dbPermissions = this.permissionRepository.findByIdIn(reqPerissions);
            role.setPermissions(dbPermissions);
        }

        return this.roleRepository.save(role);
    }

    public Role fetchById(long id) {
        Optional<Role> role = roleRepository.findById(id);
        if (role.isPresent()) return role.get();
        return null;
    }

    public Role update(Role role) {
        Role roleDB = this.fetchById(role.getId());

        if(role.getPermissions() != null) {
            List<Long> reqPerissions = role.getPermissions()
                    .stream()
                    .map(Permission::getId)
                    .toList();
            List<Permission> dbPermissions = this.permissionRepository.findByIdIn(reqPerissions);
            roleDB.setPermissions(dbPermissions);
        }

        roleDB.setName(role.getName());
        roleDB.setDescription(role.getDescription());
        roleDB.setActive(role.isActive());
        roleDB.setPermissions(role.getPermissions());
        roleDB = this.roleRepository.save(roleDB);
        return roleDB;
    }

    public void delete(long id) {
        this.roleRepository.deleteById(id);
    }

    public ResultPaginantionDTO getRole(Specification<Role> spec, Pageable pageable) {
        Page<Role> page = this.roleRepository.findAll(spec, pageable);
        ResultPaginantionDTO rs = new ResultPaginantionDTO();
        ResultPaginantionDTO.Meta mt = new ResultPaginantionDTO.Meta();
        mt.setPage(pageable.getPageNumber() + 1);
        mt.setPageSize(pageable.getPageSize());
        mt.setPages(page.getTotalPages());
        mt.setTotal(page.getTotalElements());
        rs.setMeta(mt);
        return rs;
    }
}
