package com.example.financialtracker.AppUser;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/user")
@AllArgsConstructor
public class AppUserController {
    private final AppUserService appUserService;

    public ResponseEntity<String> createUser(@RequestBody AppUser appUser){
        appUserService.createUser(appUser);
        return ResponseEntity.ok().body("User created successfully");
    }
}
