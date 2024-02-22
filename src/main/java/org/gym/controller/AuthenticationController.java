package org.gym.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import org.gym.aspect.Authenticated;
import org.gym.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {


    private final UserService userService;

    @Autowired
    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }


    @Authenticated
    @GetMapping("/{username}/{password}")
    @ApiOperation(value = "GEt")
    public ResponseEntity<Void> login(@PathVariable String username, @PathVariable String password) {

            return ResponseEntity.ok().build();

    }

    @Authenticated
    @ApiOperation(value = "Change")
    @PutMapping(value = "/changeLogin/{username}/{password}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> changeLogin(@PathVariable String username, @PathVariable String password, @RequestBody Map<String, String> newPassword) {

        userService.changePassword(username, newPassword.get("newPassword"));
        return ResponseEntity.ok().build();
    }

}
