package com.example.autologin.controllers;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import com.example.autologin.model.RoleDTO;
import com.example.autologin.services.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/api/roles", produces = MediaType.APPLICATION_JSON_VALUE)
public class RoleResource {

    @Autowired
    private RoleService roleService;

    @GetMapping
  //  @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<List<RoleDTO>> getAllRoles() {
        return ResponseEntity.ok(roleService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleDTO> getRole(@PathVariable(name = "id") final String id) {
        return ResponseEntity.ok(roleService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<String> createRole(@RequestBody @Valid final RoleDTO roleDTO) {
        final String createdId = roleService.create(roleDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateRole(@PathVariable(name = "id") final String id,
            @RequestBody @Valid final RoleDTO roleDTO) {
        roleService.update(id, roleDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteRole(@PathVariable(name = "id") final String id) {
        roleService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
