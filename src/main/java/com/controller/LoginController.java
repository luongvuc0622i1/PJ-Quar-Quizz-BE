package com.controller;

import com.model.dto.LoginForm;
import com.model.jwt.AppUser;
import com.model.jwt.MessageResponse;
import com.model.jwt.Role;
import com.service.jwt.JwtService;
import com.service.role.IRoleService;
import com.service.role.RoleService;
import com.service.user.IUserService;
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
    public ResponseEntity<?> register(@Valid @RequestBody AppUser user) {
        if (userService.getUserByUsername(user.getUsername()) == null) {
            Set<Role> roles = new HashSet<>();
            roles.add(roleService.findById(3L).get());
            user.setRoles(roles);
            AppUser appUser = userService.save(user);
//            Mail mail = new Mail();
//            mail.setMailTo(user.getEmail());
//            mail.setMailFrom("quarquizteam@gmail.com");
//            mail.setMailSubject("Thanks for signing up.");
//            mail.setMailContent("Hello " + user.getUsername() + "," + "\n\nThank you for signing up for our team!" +
//                    "We are looking forward to seeing you there.\n\n" +
//                    "Best, \n" +
//                    "Quarquizzteam");
//            mailService.sendEmail(mail);
            return new ResponseEntity<>(appUser, HttpStatus.OK);
        }
        if (userService.existsByUsername(user.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("nouser"));
        }
        if (userService.existsByEmail(user.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("noemail"));
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping("/allu")
    public ResponseEntity<Iterable<AppUser>> getAll() {
        Iterable<AppUser> userList = userService.findAll();
        System.out.println(userList);
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }
}