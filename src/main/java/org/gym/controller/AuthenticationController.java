package org.gym.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import org.gym.aspect.Authenticated;
import org.gym.exception.UserNotFoundException;
import org.gym.model.User;
import org.gym.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.Map;


@RestController
@RequestMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(value = "Authentication Controller", description = "Endpoints for user authentication")
public class AuthenticationController {


    private final UserService userService;

    @Autowired
    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/sign_in")
    @ApiOperation(value = "Login in to profile ")
    public ResponseEntity<?> login(@RequestBody Map<String, String> cred) {
        String username = cred.get("username");
        String password = cred.get("password");
        userService.authenticated(username, password);
        return ResponseEntity.ok().build();

    }

    @ApiOperation(value = "Change")
    @PutMapping(value = "/changeLogin", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> changeLogin(@RequestBody Map<String, String> cred) {
        String username = cred.get("username");
        String password = cred.get("password");
        userService.authenticated(username, password);
        userService.changePassword(username, cred.get("newPassword"));
        return ResponseEntity.ok().build();
    }
}


