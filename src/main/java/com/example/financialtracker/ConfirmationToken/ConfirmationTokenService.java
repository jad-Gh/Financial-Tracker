package com.example.financialtracker.ConfirmationToken;

import com.example.financialtracker.AppUser.AppUser;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ConfirmationTokenService {
    private final ConfirmationTokenRepository confirmationTokenRepository;

    public void createToken(AppUser user){
        ConfirmationToken token = new ConfirmationToken();
        token.setExpiresAt(LocalDateTime.now().plusMinutes(10));
        token.setUuid(UUID.randomUUID().toString());
        token.setUser(user);

        confirmationTokenRepository.save(token);
    }

    public void deleteToken(Long id){
        ConfirmationToken token = confirmationTokenRepository.findById(id).orElseThrow(()->new RuntimeException("Token not found"));

        confirmationTokenRepository.deleteById(id);
    }

    public ConfirmationToken findToken(String uuid){
        ConfirmationToken token = confirmationTokenRepository.findByUUID(uuid).orElseThrow(()->new RuntimeException("Token not found"));
        return token;
    }

    public boolean isTokenNotExpired(ConfirmationToken token){
        if (token.getExpiresAt().isBefore(LocalDateTime.now())){
            this.deleteToken(token.getId());
            return false;
        } else {
            return true;
        }
    }
}
