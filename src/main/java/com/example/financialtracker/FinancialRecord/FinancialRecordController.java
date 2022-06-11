package com.example.financialtracker.FinancialRecord;

import com.example.financialtracker.FinancialCategory.FinancialCategory;
import com.example.financialtracker.FinancialCategory.FinancialCategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/record")
@AllArgsConstructor
public class FinancialRecordController {

    private final FinancialRecordService financialRecordService;

    @GetMapping()
    public ResponseEntity<List<FinancialRecord>> getRecords (){
        List<FinancialRecord> list = financialRecordService.getRecords();
        return ResponseEntity.ok().body(list);
    }

    @PostMapping()
    public ResponseEntity<String> addRecord( @RequestBody() FinancialRecord financialRecord){
        financialRecordService.addRecord(financialRecord);
        return ResponseEntity.ok().body("Record added successfully");
    }

    @PutMapping()
    public ResponseEntity<String> editRecord(@RequestBody() FinancialRecord financialRecord){
        financialRecordService.updateRecord(financialRecord);
        return ResponseEntity.ok().body("Record updated successfully");
    }

    @DeleteMapping(path="/{id}")
    public ResponseEntity<String> deleteRecord(@PathVariable(name = "id") Long id){
        financialRecordService.deleteRecord(id);
        return ResponseEntity.ok().body("Record deleted successfully");
    }
}
