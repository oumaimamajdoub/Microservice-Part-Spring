package com.example.autologin.services;

import org.springframework.beans.factory.annotation.Autowired;
import com.example.autologin.entity.Role;
import com.example.autologin.entity.User;
import com.example.autologin.model.UserDTO;
import com.example.autologin.repository.RoleRepository;
import com.example.autologin.repository.UserRepository;
import com.example.autologin.util.NotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;


    public List<UserDTO> findAll() {
        final List<User> users = userRepository.findAll(Sort.by("id"));
        return users.stream()
                .map(user -> mapToDTO(user, new UserDTO()))
                .collect(Collectors.toList());
    }

    public UserDTO get(final Long id) {
        return userRepository.findById(id)
                .map(user -> mapToDTO(user, new UserDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final UserDTO userDTO) {
        final User user = new User();
        mapToEntity(userDTO, user);
        return userRepository.save(user).getId();
    }

    public void update(final Long id, final UserDTO userDTO) {
        final User user = userRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(userDTO, user);
        userRepository.save(user);
    }

    public void delete(final Long id) {
        User u = userRepository.findById(id).orElse(null);
        if (u != null){
            userRepository.deleteById(id);
        }
    }

    public UserDTO mapToDTO(final User user, final UserDTO userDTO) {
        userDTO.setId(user.getId());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setAdress(user.getAdress());
        userDTO.setRegion(user.getRegion());
        userDTO.setCity(user.getCity());
        userDTO.setZip(user.getZip());
        Set<String> roles = new HashSet<>();
        user.getRoles().forEach(role->{
            roles.add(role.getRoleName());
        });
        userDTO.setRoles(roles);
        return userDTO;
    }

    public User mapToEntity(final UserDTO userDTO, final User user) {
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setAdress(userDTO.getAdress());
        user.setRegion(userDTO.getRegion());
        user.setCity(userDTO.getCity());
        user.setZip(userDTO.getZip());
        Set<Role> userRoles = new HashSet<>();
        userDTO.getRoles().forEach(role->{
            Role userDTOrole = roleRepository.findById(role).get();
            userRoles.add(userDTOrole);
        });
        user.setRoles(userRoles);

        return user;
    }

}
