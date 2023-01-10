package com.controller;

import com.model.jwt.AppUser;
import com.service.role.IRoleService;
import com.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin("*")
public class UserController {
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
    public ResponseEntity <AppUser> lockAccountByIdUser(@PathVariable Long id){
        Optional<AppUser> user = userService.findById(id);
        if(user.isPresent()) {
            userService.lockAccountById(id);
            user.get().setStatus("0");
        }
        return new ResponseEntity<>(user.get(),HttpStatus.OK);
    }

    @PutMapping("/admin/open/{id}")
    public ResponseEntity <AppUser> openAccountByIdUser(@PathVariable Long id){
        Optional<AppUser> user = userService.findById(id);
        if(user.isPresent()) {
            userService.openAccountById(id);
            user.get().setStatus("1");
        }
        return new ResponseEntity<>(user.get(),HttpStatus.OK);
    }

//    @PutMapping("admin/changeManager")
//    public ResponseEntity <Optional <AppUser>> changeManager(@RequestParam("name") String name){
//        Optional<AppUser> user=userService.findByUser(name);
//
//        if(!user.isPresent()){
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        }
//        userService.changeManager(name);
//        return new ResponseEntity<>(user,HttpStatus.OK);
//    }
//    @PutMapping("admin/changeUser")
//    public ResponseEntity <Optional <AppUser>> changeUser(@RequestParam("name") String name){
//        Optional<AppUser> user=userService.findByUser(name);
//        if(!user.isPresent()){
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        }
//        userService.changeUser(name);
//        return new ResponseEntity<>(user,HttpStatus.OK);
//    }
}
