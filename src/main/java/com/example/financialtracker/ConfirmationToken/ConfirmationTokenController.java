package com.example.financialtracker.ConfirmationToken;

import com.example.financialtracker.AppUser.AppUser;
import com.example.financialtracker.AppUser.AppUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(path = "/validate-token")
@AllArgsConstructor
public class ConfirmationTokenController {

    private final ConfirmationTokenService confirmationTokenService;
    //private final AppUserRepository appUserRepository;

    @GetMapping(path = "/{uuid}")
    @Transactional
    public String validateToken(@PathVariable(name = "uuid") String uuid){
        ConfirmationToken token = confirmationTokenService.findToken(uuid);
        if (confirmationTokenService.isTokenNotExpired(token)){
            AppUser appUser = token.getUser();
            appUser.setActive(true);
            confirmationTokenService.deleteToken(token.getId());
            return "Account Verified Successfully";
        } else {
            return "Token has expired, please retry signing up";
        }
    }

    @GetMapping(path = "/reset/{uuid}")
    @Transactional
    public String resetPassword(@PathVariable(name = "uuid") String uuid){
        ConfirmationToken token = confirmationTokenService.findToken(uuid);
        if (confirmationTokenService.isTokenNotExpired(token)){
            AppUser appUser = token.getUser();
            String newPass = UUID.randomUUID().toString();
            appUser.setPassword( new BCryptPasswordEncoder().encode(newPass));
            confirmationTokenService.deleteToken(token.getId());
            return "Password reset to: " + newPass;
        } else {
            return "Token has expired, please retry resetting password";
        }
    }


}
