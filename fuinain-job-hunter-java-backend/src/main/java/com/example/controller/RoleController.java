package com.example.controller;

import com.example.domain.Permission;
import com.example.domain.Role;
import com.example.domain.response.ResultPaginantionDTO;
import com.example.service.RoleService;
import com.example.util.annotation.ApiMessage;
import com.example.util.error.IdInvalidException;
import com.turkraft.springfilter.boot.Filter;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping("/roles")
    @ApiMessage("Create new role")
    public ResponseEntity<Role> create(@Valid @RequestBody Role role) throws IdInvalidException {
        if (this.roleService.existByName(role.getName()))
            throw new IdInvalidException("Role already exists");
        return ResponseEntity.status(HttpStatus.CREATED).body(this.roleService.create(role));
    }

    @PutMapping("/roles")
    @ApiMessage("Update role")
    public ResponseEntity<Role> update(@Valid @RequestBody Role role) throws IdInvalidException {
        if (this.roleService.fetchById(role.getId()) == null)
            throw new IdInvalidException("Role not found");

        if (this.roleService.existByName(role.getName()))
            throw new IdInvalidException("Role already exists");

        return ResponseEntity.ok().body(this.roleService.update(role));
    }

    @DeleteMapping("/roles/{id}")
    @ApiMessage("Delete role")
    public ResponseEntity<Void> delete(@PathVariable long id) throws IdInvalidException {
        if (this.roleService.fetchById(id) == null)
            throw new IdInvalidException("Role not found");
        this.roleService.delete(id);
        return ResponseEntity.ok().body(null);
    }

    @GetMapping("/roles")
    @ApiMessage("Fetch roles")
    public ResponseEntity<ResultPaginantionDTO> getRole(
            @Filter Specification<Role> spec,
            Pageable pageable
    ) throws IdInvalidException {
        return ResponseEntity.ok().body(this.roleService.getRole(spec, pageable));
    }

}
