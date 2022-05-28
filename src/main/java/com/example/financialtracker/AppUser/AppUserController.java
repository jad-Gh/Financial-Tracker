package com.example.financialtracker.AppUser;

import com.example.financialtracker.RegestrationService.RegestrationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(path = "/user")
@AllArgsConstructor
public class AppUserController {
    private final AppUserService appUserService;
    private final RegestrationService regestrationService;

    @PostMapping("/create")
    public ResponseEntity<String> createUser(@RequestBody AppUser appUser){
        regestrationService.registerUser(appUser);
        return ResponseEntity.ok().body("User created successfully");
    }

    @PutMapping
    public ResponseEntity<String> editUser(@RequestBody AppUser appUser){
        appUserService.updateUser(appUser);
        return ResponseEntity.ok().body("User updated successfully");
    }

    @GetMapping
    public ResponseEntity<Map<String,Object>> getUsers(@RequestParam(name = "page",defaultValue = "0") int page,
                                                       @RequestParam(name="size",defaultValue = "10") int size){
        return ResponseEntity.ok().body(appUserService.getUsers(page,size));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable(name = "id") Long id){
        appUserService.deleteUser(id);
        return ResponseEntity.ok().body("User deleted successfully");
    }
}
