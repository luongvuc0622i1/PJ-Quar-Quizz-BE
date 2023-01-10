package com.controller;

import com.model.dto.JwtResponse;
import com.model.dto.LoginForm;
import com.model.jwt.AppUser;
import com.service.jwt.JwtService;
import com.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin("*")
public class LoginController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginForm loginForm) {
        // Tạo 1 đối tượng authentication
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginForm.getUsername(), loginForm.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // Tạo token mới
        String token = jwtService.createToken(authentication);

        AppUser user = userService.getUserByUsername(loginForm.getUsername());
        user.setToken(token);
        return new ResponseEntity<>(user, HttpStatus.ACCEPTED);
    }

    @GetMapping("/allu")
    public ResponseEntity<Iterable<AppUser>> getAll() {
        Iterable<AppUser> userList = userService.findAll();
        System.out.println(userList);
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }
}