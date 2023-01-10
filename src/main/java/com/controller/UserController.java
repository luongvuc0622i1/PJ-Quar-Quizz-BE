package com.controller;

import com.model.jwt.AppUser;
import com.model.jwt.Role;
import com.service.role.IRoleService;
import com.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RestController
@CrossOrigin("*")
public class UserController {
    @Autowired
    private IRoleService roleService;
    @Autowired
    private IUserService userService;

    @GetMapping("/manager/users")
    public ResponseEntity<Iterable<AppUser>> managerGetAll() {
        Iterable<AppUser> userList = userService.managerFindAll();
        System.out.println(userList);
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    @GetMapping("/admin/users")
    public ResponseEntity<Iterable<AppUser>> adminGetAll() {
        Iterable<AppUser> userList = userService.adminFindAll();
        System.out.println(userList);
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

//    @GetMapping("/users/{id}")
//    public ResponseEntity<AppUser> findById(@PathVariable Long id) {
//        Optional<AppUser> optional = userService.findById(id);
//        if (!optional.isPresent()) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//        return new ResponseEntity<>(optional.get(), HttpStatus.OK);
//    }

    @PutMapping("/admin/lock/{id}")
    public ResponseEntity<AppUser> lockAccountByIdUser(@PathVariable Long id) {
        Optional<AppUser> user = userService.findById(id);
        if (user.isPresent()) {
            userService.lockAccountById(id);
            user.get().setStatus("0");
        }
        return new ResponseEntity<>(user.get(), HttpStatus.OK);
    }

    @PutMapping("/admin/open/{id}")
    public ResponseEntity<AppUser> openAccountByIdUser(@PathVariable Long id) {
        Optional<AppUser> user = userService.findById(id);
        if (user.isPresent()) {
            userService.openAccountById(id);
            user.get().setStatus("1");
        }
        return new ResponseEntity<>(user.get(), HttpStatus.OK);
    }

    @PutMapping("/admin/changeToManager/{id}")
    public ResponseEntity <Optional <AppUser>> changeToManager(@PathVariable Long id){
        Optional<AppUser> user = userService.findById(id);
        if(!user.isPresent()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        Set<Role> roles = new HashSet<>();
        roles.add(roleService.findById(2L).get());
        user.get().setRoles(roles);
        userService.changeToManager(id);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @PutMapping("/admin/changeToUser/{id}")
    public ResponseEntity <Optional <AppUser>> changeUser(@PathVariable Long id){
        Optional<AppUser> user = userService.findById(id);
        if(!user.isPresent()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        Set<Role> roles = new HashSet<>();
        roles.add(roleService.findById(3L).get());
        user.get().setRoles(roles);
        userService.changeToUser(id);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }
}
