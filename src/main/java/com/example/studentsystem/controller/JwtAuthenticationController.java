package com.example.studentsystem.controller;

import com.example.studentsystem.dto.UserDto;
import com.example.studentsystem.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.studentsystem.servicesImpl.JwtUserDetailsServiceImpl;
import com.example.studentsystem.config.JwtTokenUtil;
import com.example.studentsystem.dto.JwtRequestDto;
import com.example.studentsystem.dto.JwtResponseDto;

@RestController
@CrossOrigin
public class JwtAuthenticationController {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationController.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUserDetailsServiceImpl userDetailsService;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequestDto authenticationRequest) throws Exception {
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponseDto(token));

    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> saveUser(@RequestBody UserDto userDto) throws Exception {
        UserDto user =  userService.getByUsername(userDto.getUsername());

        if (user != null) {
            return new ResponseEntity<String>("Created", HttpStatus.CREATED);
        }

        try {
            Integer userId = userDetailsService.save(userDto);
            return new ResponseEntity<String>(userId.toString(), HttpStatus.CREATED);
        } catch (Exception e) {
            LOGGER.error("Error found: {}", e.getMessage(), e);
            return new ResponseEntity<String>("Failed saving", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void authenticate(String username, String password) throws Exception {

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }

    }

}