package com.example.autologin.controllers;


import com.example.autologin.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.example.autologin.entity.User;
import com.example.autologin.model.UserDTO;
import com.example.autologin.payload.request.LoginRequest;
import com.example.autologin.payload.response.JwtResponse;

import com.example.autologin.repository.UserRepository;
import com.example.autologin.security.jwt.JwtUtils;


import javax.validation.Valid;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@RestController
@Slf4j
@RequestMapping(value = "/api/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private UserService userService;


    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) throws IOException {

        User user = userRepository.findUserByEmail(loginRequest.getEmail());
        UserDTO userDTO = new UserDTO();
        userDTO = userService.mapToDTO(user, userDTO);
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        
        try {

            
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = jwtUtils.generateJwtToken(authentication);

            return ResponseEntity.ok(new JwtResponse(userDTO,jwt));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: "+e.toString());
        }
    }

    @PostMapping(value = "/register")
    @ResponseBody
    public ResponseEntity<String> register(
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

        return ResponseEntity.status(HttpStatus.CREATED).body(id.toString());


    }
}