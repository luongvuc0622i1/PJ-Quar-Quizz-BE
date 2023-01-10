package com.controller;

import com.model.dto.LoginForm;
import com.model.dto.Mail;
import com.model.dto.RegisterForm;
import com.model.jwt.AppUser;
import com.model.jwt.MessageResponse;
import com.model.jwt.Role;
import com.service.jwt.JwtService;
import com.service.mail.IMailService;
import com.service.role.IRoleService;
import com.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@RestController
@CrossOrigin("*")
public class LoginController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private IUserService userService;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private IMailService mailService;

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

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterForm registerForm) {
        if (userService.existsByEmail(registerForm.getEmail()) && userService.existsByUsername(registerForm.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("email & username are exist in db"));
        } else if (userService.existsByEmail(registerForm.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("email is exist in db"));
        } else if (userService.existsByUsername(registerForm.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("user is exist in db"));
        } else if (!registerForm.getPassword().equals(registerForm.getRepassword())) {
            return ResponseEntity.badRequest().body(new MessageResponse("repassword not match"));
        } else {
            AppUser user = new AppUser();
            user.setEmail(registerForm.getEmail());
            user.setUsername(registerForm.getUsername());
            user.setPassword(registerForm.getPassword());
            Set<Role> roles = new HashSet<>();
            roles.add(roleService.findById(3L).get());
            user.setRoles(roles);
            user.setStatus("1");
            AppUser appUser = userService.save(user);
            Mail mail = new Mail();
            mail.setMailTo(user.getEmail());
            mail.setMailFrom("quarquizteam@gmail.com");
            mail.setMailSubject("Thanks for signing up.");
            mail.setMailContent("Hello " + user.getUsername() + "," + "\n\nThank you for signing up for our team!" +
                    "We are looking forward to seeing you there.\n\n" +
                    "Best, \n" +
                    "Quarquizzteam");
            mailService.sendEmail(mail);
            return new ResponseEntity<>(appUser, HttpStatus.OK);
        }
    }
}