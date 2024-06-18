package com.fastcampus.springsecurity.Controller;

import com.fastcampus.springsecurity.model.user.User;
import com.fastcampus.springsecurity.model.user.UserSignUpRequestBody;
import com.fastcampus.springsecurity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/users")
public class UserController {
    @Autowired
    UserService userService;

    public ResponseEntity<User> singUp(@RequestBody UserSignUpRequestBody userSignUpRequestBody){
        var user = userService.signUp(userSignUpRequestBody.username(), userSignUpRequestBody.password());
        return ResponseEntity.ok(user);

    }
}
