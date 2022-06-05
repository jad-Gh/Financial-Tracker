package com.example.financialtracker.FinancialCategory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/category")
@AllArgsConstructor
public class FinancialCategoryController {

    private final FinancialCategoryService financialCategoryService;

    @GetMapping()
    public ResponseEntity<List<FinancialCategory>> getCategories (){
        List<FinancialCategory> list = financialCategoryService.getCategories();
        return ResponseEntity.ok().body(list);
    }

    @PostMapping()
    public ResponseEntity<String> addCategory( @RequestBody() CategoryBody categoryBody){
        financialCategoryService.addCategory(categoryBody.getName());
        return ResponseEntity.ok().body("Category added successfully");
    }

    @PutMapping()
    public ResponseEntity<String> editCategory(@RequestBody() CategoryBody categoryBody){
        financialCategoryService.editCategory(categoryBody.getName(),categoryBody.getIdToGet());
        return ResponseEntity.ok().body("Category updated successfully");
    }

    @DeleteMapping(path="/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable(name = "id") Long id){
        financialCategoryService.deleteCategory(id);
        return ResponseEntity.ok().body("Category deleted successfully");
    }
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class CategoryBody{
    private String name;
    private Long idToGet;
}
