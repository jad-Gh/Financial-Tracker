package com.example.financialtracker.FinancialAccount;

import com.example.financialtracker.AppUser.AppUser;
import com.example.financialtracker.AppUser.AppUserRepository;
import com.example.financialtracker.FinancialCategory.FinancialCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Data
@AllArgsConstructor
@Slf4j
public class FinancialAccountService {
    private final FinancialAccountRepository financialAccountRepository;
    private final AppUserRepository appUserRepository;

    public List<FinancialAccount> getAccounts(){
        try{
            log.info("Get Accounts successful");
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            AppUser appUser = appUserRepository.findAppUserByUsername(auth.getName())
                    .orElseThrow(()->new UsernameNotFoundException("User not found"));
            return appUser.getAccountList();
        }catch(Exception e){
            log.error("Error getting data: " + e.getMessage());
            throw new RuntimeException(e.getLocalizedMessage());
        }

    }

    public void addAccount(FinancialAccount account){
        try{
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            AppUser appUser = appUserRepository.findAppUserByUsername(auth.getName())
                    .orElseThrow(()->new UsernameNotFoundException("User not found"));

            account.setCreatedAt(LocalDateTime.now());
            account.setAccountOwner(appUser);
            account.setBalance(0);

            financialAccountRepository.save(account);
        }catch(Exception e){
            log.error("Error adding account: " + e.getMessage());
            throw new RuntimeException(e.getMessage());
        }


    }

    public void updateAccount(FinancialAccount updated){

        FinancialAccount account = financialAccountRepository.findById(updated.getId())
                .orElseThrow(()->new RuntimeException("Account not found"));

        account.setName(updated.getName());

        financialAccountRepository.save(account);

    }

    public void deleteAccount(Long id){
        financialAccountRepository.deleteById(id);
    }


}
