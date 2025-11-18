package com.example.controller;

import com.example.domain.Company;
import com.example.domain.Permission;
import com.example.domain.response.ResultPaginantionDTO;
import com.example.service.PermissionService;
import com.example.util.annotation.ApiMessage;
import com.example.util.error.IdInvalidException;
import com.turkraft.springfilter.boot.Filter;
import jakarta.validation.Valid;
import lombok.Getter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class PermissionController {
    private final PermissionService permissionService;

    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @PostMapping("/permissions")
    @ApiMessage("Create Permission")
    public ResponseEntity<Permission> create(@Valid @RequestBody Permission permission) throws IdInvalidException {
        if (this.permissionService.isPermissionExist(permission))
            throw new IdInvalidException("Permission already exists");
        return ResponseEntity.status(HttpStatus.CREATED).body(this.permissionService.create(permission));
    }

    @PutMapping("/permissions")
    @ApiMessage("Update Permission")
    public ResponseEntity<Permission> update(@Valid @RequestBody Permission permission) throws IdInvalidException {
        if (this.permissionService.fetchById(permission.getId()) == null)
            throw new IdInvalidException("Permission not found");

        if (this.permissionService.isPermissionExist(permission))
            throw new IdInvalidException("Permission already exists");

        return ResponseEntity.ok().body(this.permissionService.update(permission));
    }

    @DeleteMapping("/permissions/{id}")
    @ApiMessage("Delete Permission")
    public ResponseEntity<Void> delete(@PathVariable long id) throws IdInvalidException {
        if (this.permissionService.fetchById(id) == null)
            throw new IdInvalidException("Permission not found");

        this.permissionService.delete(id);
        return ResponseEntity.ok().body(null);
    }

    @GetMapping("/permissions")
    @ApiMessage("Fetch Permission by ID")
    public ResponseEntity<ResultPaginantionDTO> getPermissions(
            @Filter Specification<Permission> spec,
            Pageable pageable
    ) throws IdInvalidException {
        return ResponseEntity.ok().body(this.permissionService.getPermissions(spec, pageable));
    }
}
