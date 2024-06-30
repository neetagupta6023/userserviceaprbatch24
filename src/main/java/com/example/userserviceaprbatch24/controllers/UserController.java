package com.example.userserviceaprbatch24.controllers;

import com.example.userserviceaprbatch24.dtos.LoginRequestDto;
import com.example.userserviceaprbatch24.dtos.SignUpRequestDto;
import com.example.userserviceaprbatch24.dtos.UserDto;
import com.example.userserviceaprbatch24.dtos.ValidateRequestDto;
import com.example.userserviceaprbatch24.models.Token;
import com.example.userserviceaprbatch24.models.User;
import com.example.userserviceaprbatch24.services.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private UserService userService;
    public UserController(UserService userService)
    {
        this.userService = userService;
    }
    @PostMapping("/signup")
    public UserDto SignUp(@RequestBody SignUpRequestDto request)
    {
        User user=
                userService.signUp
                        (request.getEmail(),request.getName(),request.getPassword());
        return UserDto.from(user);
    }
    @PostMapping("/login")
    public Token Login(@RequestBody LoginRequestDto request)
    {
        Token token=
                userService.login(request.getEmail(),request.getPassword());
        return token;
    }

    @PostMapping("/validate/{token}")
    public UserDto validate(@PathVariable String token)
    {   try
        {
            return   UserDto.from(userService.validatetoken(token));
        }
        catch(Exception e)
        {
            return null;
        }


    }
}
