package com.controller;

import com.model.dto.Mail;
import com.model.dto.RegisterForm;
import com.model.jwt.AppUser;
import com.model.jwt.MessageResponse;
import com.service.mail.MailService;
import com.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class MailController {
    @Autowired
    private MailService mailService;

    @Autowired
    private UserService userService;

    @PutMapping("/sendOtp")
    public ResponseEntity<?> forgetPassword(@Valid @RequestBody AppUser appUser) {
        Optional<AppUser> userOptional = userService.getUserByEmail(appUser.getEmail());
        AppUser user = userOptional.get();
        if (!userOptional.isPresent()) {
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }
        double randomDouble = Math.random();
        randomDouble = randomDouble * 1000000 + 1;
        int OTP = (int) randomDouble;
        user.setOtp(String.valueOf(OTP));
        userService.save(user);
        Mail mail = new Mail();
        mail.setMailTo(user.getEmail());
        mail.setMailFrom("quarquizteam@gmail.com");
        mail.setMailSubject("Password Reset");
        mail.setMailContent("Hi " + user.getUsername() + ",\n\n" +
                "OTP code " + OTP + " is valid for 1 time to change password, used to authenticate the password change request at Quar Quizz website. " +
                "For security reasons, do not share this OTP with anyone! \n\n" +
                "Best, \n" +
                "Quarquizzteam");
        mailService.sendEmail(mail);
        return new ResponseEntity<>(userOptional.get(), HttpStatus.OK);
    }

    @PutMapping("/confirmOtp")
    public ResponseEntity<?> confirmOtp(@Valid @RequestBody AppUser appUser) {
        Optional<AppUser> userOptional = userService.getUserByEmail(appUser.getEmail());
        AppUser user = userOptional.get();
        if (appUser.getOtp().equals(user.getOtp())) {
            user.setPassword(userService.randomPassword());
            user.setOtp(null);
            userService.save(user);
            Mail mail = new Mail();
            mail.setMailTo(user.getEmail());
            mail.setMailFrom("quarquizteam@gmail.com");
            mail.setMailSubject("Reset Password Successfully");
            mail.setMailContent("Hello " + user.getUsername() + ",\n\n" +
                    "We are sorry about your problem!\n" +
                    "New Password: " + user.getPassword() + "\n" +
                    "Please change Your password! \n\n" +
                    "Best,\n" +
                    "Quarquizzteam");
            mailService.sendEmail(mail);
            return new ResponseEntity<>(userOptional.get(), HttpStatus.OK);
        } else {
            return ResponseEntity.badRequest().body(new MessageResponse("otp not match"));
        }
    }
}
