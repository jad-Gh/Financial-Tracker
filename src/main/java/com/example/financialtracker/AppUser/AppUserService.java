package com.example.financialtracker.AppUser;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
@Slf4j
public class AppUserService {

    private final AppUserRepository appUserRepository;

    public void createUser(AppUser appUser){
        try {
            appUser.setCreatedAt(LocalDateTime.now());
            appUser.setActive(false);
            appUser.setPassword(new BCryptPasswordEncoder().encode(appUser.getPassword()));
            appUserRepository.save(appUser);
            log.info("User: %s : created successfully",appUser.getFullname());

        }catch (Exception e){
            log.error("failed to create user: %s : Error: %e",appUser.getFullname(),e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    public Map<String, Object> getUsers(int page,int size){
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "createdAt"));
            Page<AppUser> users = appUserRepository.findAll(pageable);
            Map<String, Object> result = new HashMap<>();

            result.put("Current Page", users.getNumber());
            result.put("Total Pages", users.getTotalPages());
            result.put("Total Elements", users.getTotalElements());
            result.put("Elements in Page", users.getNumberOfElements());
            result.put("Data", users.getContent());

            return result;
        }catch(Exception e){
            log.error("failed to return users : Error: %e",e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    public void updateUser(AppUser appUser){
        try {
            AppUser oldUser = appUserRepository.findById(appUser.getId()).orElseThrow(()-> new UsernameNotFoundException("User not found"));
            if (!appUser.getPassword().isEmpty()){
                appUser.setPassword(new BCryptPasswordEncoder().encode(appUser.getPassword()));
            }
            appUserRepository.save(appUser);
            log.info("User: %s : updated successfully",appUser.getFullname());
        }catch (Exception e){
            log.error("failed to update user: %s : Error: %e",appUser.getFullname(),e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    public void deleteUser(Long id){
        try{
            appUserRepository.deleteById(id);
            log.info("Deleted user with ID: %i successfully",id);
        }catch (Exception e){
            log.error("Failed to delete user: Error: %e",e.getMessage());
        }
    }


}
