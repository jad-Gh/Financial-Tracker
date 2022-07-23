package com.example.financialtracker.FinancialRecord;

import com.example.financialtracker.FinancialAccount.FinancialAccount;
import com.example.financialtracker.FinancialAccount.FinancialAccountRepository;
import com.example.financialtracker.FinancialCategory.FinancialCategory;
import com.example.financialtracker.FinancialCategory.FinancialCategoryRepository;
import com.example.financialtracker.FinancialCategory.FinancialCategoryService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

//        FinancialAccount account = financialAccountRepository.findById(financialRecord.getAccountRef().getId())
//                .orElseThrow(()->new UsernameNotFoundException("Account Not found"));


        financialRecord.setFinancialCategory(category);
//        financialRecord.setAccountRef(account);

        financialRecordRepository.save(financialRecord);
    }

    public void deleteRecord(Long id){

        FinancialRecord recordToDelete = financialRecordRepository.findById(id)
                .orElseThrow(()->new UsernameNotFoundException("Record Not found"));

        FinancialAccount account = recordToDelete.getAccountRef();
        account.setBalance(recordToDelete.getType().equals("Income") ? (account.getBalance() - recordToDelete.getValue()):(account.getBalance() + recordToDelete.getValue()));

        financialAccountRepository.save(account);
        financialRecordRepository.deleteById(id);
    }

    public Map<String,Object> getRecords(Long id,int page,int size){

        FinancialAccount account = financialAccountRepository.findById(id)
        .orElseThrow(()->new UsernameNotFoundException("Account Not found"));

        Pageable pageable = PageRequest.of(page,size, Sort.by(Sort.Direction.ASC,"createdAt"));
        Page<FinancialRecord> result = financialRecordRepository.findAllByAccountRef(account,pageable);

        Map<String,Object> map = new HashMap<>();

        map.put("Page",result.getNumber());
        map.put("Size of Page",result.getNumberOfElements());
        map.put("Total Elements",result.getTotalElements());
        map.put("Total Pages",result.getTotalPages());
        map.put("Data",result.getContent());

        return map;
    }
}
