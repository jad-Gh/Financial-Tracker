package com.example.financialtracker.FinancialRecord;

import com.example.financialtracker.FinancialCategory.FinancialCategory;
import com.example.financialtracker.FinancialCategory.FinancialCategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/record")
@AllArgsConstructor
public class FinancialRecordController {

    private final FinancialRecordService financialRecordService;

    @GetMapping()
    public ResponseEntity<Map<String,Object>> getRecords (
            @RequestParam(name = "accountId") Long id,
            @RequestParam(name="page") int page,
            @RequestParam(name="size") int size
    ){
        Map<String,Object> map = financialRecordService.getRecords(id,page,size);
        return ResponseEntity.ok().body(map);
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
