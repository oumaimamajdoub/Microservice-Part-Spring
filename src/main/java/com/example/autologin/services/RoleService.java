package com.example.autologin.services;

import org.springframework.beans.factory.annotation.Autowired;
import com.example.autologin.entity.Role;
import com.example.autologin.model.RoleDTO;
import com.example.autologin.repository.RoleRepository;
import com.example.autologin.util.NotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;


    public List<RoleDTO> findAll() {
        final List<Role> roles = roleRepository.findAll();
        return roles.stream()
                .map(role -> mapToDTO(role, new RoleDTO()))
                .collect(Collectors.toList());
    }

    public RoleDTO get(final String id) {
        return roleRepository.findById(id)
                .map(role -> mapToDTO(role, new RoleDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public String create(final RoleDTO roleDTO) {
        final Role role = new Role();
        mapToEntity(roleDTO, role);
        return roleRepository.save(role).getRoleName();
    }

    public void update(final String id, final RoleDTO roleDTO) {
        final Role role = roleRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(roleDTO, role);
        roleRepository.save(role);
    }

    public void delete(final String id) {
        roleRepository.deleteById(id);
    }

    private RoleDTO mapToDTO(final Role role, final RoleDTO roleDTO) {
        roleDTO.setRole(role.getRoleName());
        return roleDTO;
    }

    private Role mapToEntity(final RoleDTO roleDTO, final Role role) {
        role.setRoleName(roleDTO.getRole());
        return role;
    }

}
