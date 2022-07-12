package com.example.financialtracker.FinancialRecord;

import com.example.financialtracker.FinancialAccount.FinancialAccount;
import com.example.financialtracker.FinancialAccount.FinancialAccountRepository;
import com.example.financialtracker.FinancialCategory.FinancialCategory;
import com.example.financialtracker.FinancialCategory.FinancialCategoryRepository;
import com.example.financialtracker.FinancialCategory.FinancialCategoryService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class FinancialRecordService {
    private final FinancialRecordRepository financialRecordRepository;
    private final FinancialCategoryRepository financialCategoryRepository;
    private final FinancialAccountRepository financialAccountRepository;

    public void addRecord(FinancialRecord financialRecord){

        FinancialCategory category = financialCategoryRepository.findById(financialRecord.getFinancialCategory().getId())
                .orElseThrow(()->new UsernameNotFoundException("Category Not found"));

        FinancialAccount account = financialAccountRepository.findById(financialRecord.getAccountRef().getId())
                .orElseThrow(()->new UsernameNotFoundException("Account Not found"));

        account.setBalance(financialRecord.getType().equals("Income") ? (account.getBalance()+ financialRecord.getValue()):(account.getBalance() - financialRecord.getValue()));

        financialRecord.setFinancialCategory(category);
        financialRecord.setAccountRef(account);
        financialRecord.setCreatedAt(LocalDateTime.now());

        financialAccountRepository.save(account);
        financialRecordRepository.save(financialRecord);
    }

    public void updateRecord(FinancialRecord financialRecord){

        FinancialRecord recordToUpdate = financialRecordRepository.findById(financialRecord.getId())
                .orElseThrow(()->new UsernameNotFoundException("Record Not found"));

        FinancialCategory category = financialCategoryRepository.findById(financialRecord.getFinancialCategory().getId())
                .orElseThrow(()->new UsernameNotFoundException("Category Not found"));

        FinancialAccount account = financialAccountRepository.findById(financialRecord.getAccountRef().getId())
                .orElseThrow(()->new UsernameNotFoundException("Account Not found"));

        financialRecord.setFinancialCategory(category);
        financialRecord.setAccountRef(account);

        financialRecordRepository.save(financialRecord);
    }

    public void deleteRecord(Long id){

        financialRecordRepository.deleteById(id);
    }

    public List<FinancialRecord> getRecords(){
    return financialRecordRepository.findAll();
    }
}
