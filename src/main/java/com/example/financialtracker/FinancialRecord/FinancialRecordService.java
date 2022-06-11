package com.example.financialtracker.FinancialRecord;

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

    public void addRecord(FinancialRecord financialRecord){

        FinancialCategory category = financialCategoryRepository.findById(financialRecord.getFinancialCategory().getId())
                .orElseThrow(()->new UsernameNotFoundException("Category Not found"));

        financialRecord.setFinancialCategory(category);
        financialRecord.setCreatedAt(LocalDateTime.now());

        financialRecordRepository.save(financialRecord);
    }

    public void updateRecord(FinancialRecord financialRecord){

        FinancialRecord recordToUpdate = financialRecordRepository.findById(financialRecord.getId())
                .orElseThrow(()->new UsernameNotFoundException("Record Not found"));

        FinancialCategory category = financialCategoryRepository.findById(financialRecord.getFinancialCategory().getId())
                .orElseThrow(()->new UsernameNotFoundException("Category Not found"));

        financialRecord.setFinancialCategory(category);

        financialRecordRepository.save(recordToUpdate);
    }

    public void deleteRecord(Long id){
        financialRecordRepository.deleteById(id);
    }

    public List<FinancialRecord> getRecords(){
    return financialRecordRepository.findAll();
    }
}
