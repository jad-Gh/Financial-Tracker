package com.example.financialtracker.FinancialAccount;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/account")
@AllArgsConstructor
public class FinancialAccountController {
    private final FinancialAccountService financialAccountService;

    @PostMapping()
    public ResponseEntity<String> addAccount(@RequestBody FinancialAccount account){
        financialAccountService.addAccount(account);
        return ResponseEntity.ok().body("Account created successfully");
    }

    @PutMapping()
    public ResponseEntity<String> updateAccount(@RequestBody FinancialAccount account){
        financialAccountService.updateAccount(account);
        return ResponseEntity.ok().body("Account updated successfully");
    }

    @GetMapping()
    public ResponseEntity<List<FinancialAccount>> getAccounts(){
        List<FinancialAccount> accounts = financialAccountService.getAccounts();
        return ResponseEntity.ok().body(accounts);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable("id") Long id){
        financialAccountService.deleteAccount(id);
        return ResponseEntity.ok().body("Account deleted successfully");
    }


}
