package com.example.autologin.controllers;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.example.autologin.entity.User;
import com.example.autologin.model.UserDTO;
import com.example.autologin.repository.UserRepository;
import com.example.autologin.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@RestController
@RequestMapping(value = "/api/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserResource {

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private UserRepository userRepository;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('Admin')")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(userService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<?> createUser(
                                           @RequestParam String firstName,
                                           @RequestParam String lastName,
                                           @RequestParam String email,
                                           @RequestParam String phoneNumber,
                                           @RequestParam String password,
                                           @RequestParam String adress,
                                           @RequestParam String region,
                                           @RequestParam String city,
                                           @RequestParam Long zip,
                                           @RequestParam String role
    ) throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName(firstName);
        userDTO.setLastName(lastName);
        userDTO.setEmail(email);
        userDTO.setPassword(encoder.encode(password));
        userDTO.setPhoneNumber(phoneNumber);
        userDTO.setAdress(adress);
        userDTO.setRegion(region);
        userDTO.setCity(city);
        userDTO.setZip(zip);
      

        Set<String> roles = new HashSet<>();
        roles.add(role);
        userDTO.setRoles(roles);
        if (userDTO.getFirstName() == null || userDTO.getFirstName().matches(".*\\d.*")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Firstname");
        }
        if (userDTO.getLastName() == null || userDTO.getLastName().matches(".*\\d.*")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Lastname");
        }
        if (userDTO.getPhoneNumber() == null || !userDTO.getPhoneNumber().matches("\\d{8}")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Phone Number");
        }
        if (userDTO.getEmail() == null || !userDTO.getEmail().matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{message: Invalid Email Address}");
        }

        Long id = userService.create(userDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    @PutMapping(value="/{id}")
    public ResponseEntity<?> updateUser(@PathVariable(name = "id") final Long id,
                                           
                                           @RequestParam String firstName,
                                           @RequestParam String lastName,
                                           @RequestParam String email,
                                           @RequestParam String phoneNumber,
                                           @RequestParam @Nullable String password,
                                           @RequestParam String adress,
                                           @RequestParam String region,
                                           @RequestParam String city,
                                           @RequestParam Long zip,
                                           @RequestParam String role) throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName(firstName);
        userDTO.setLastName(lastName);
        userDTO.setEmail(email);
        if (password !=null){
            userDTO.setPassword(encoder.encode(password));
        }
        userDTO.setPhoneNumber(phoneNumber);
        userDTO.setAdress(adress);
        userDTO.setRegion(region);
        userDTO.setCity(city);
        userDTO.setZip(zip);
        User u  = userRepository.findById(id).orElse(null);
        
        Set<String> roles = new HashSet<>();
        roles.add(role);
        userDTO.setRoles(roles);
        if (userDTO.getFirstName() == null || userDTO.getFirstName().matches(".*\\d.*")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Firstname");
        }
        if (userDTO.getLastName() == null || userDTO.getLastName().matches(".*\\d.*")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Lastname");
        }
        if (userDTO.getPhoneNumber() == null || !userDTO.getPhoneNumber().matches("\\d{8}")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Phone Number");
        }
        if (userDTO.getEmail() == null || !userDTO.getEmail().matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"message\": \"Invalid Email Address\"}");
        }
        userService.update(id, userDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteUser(@PathVariable(name = "id") final Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
