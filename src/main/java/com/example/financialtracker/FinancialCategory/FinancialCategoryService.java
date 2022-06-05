package com.example.financialtracker.FinancialCategory;

import com.example.financialtracker.AppUser.AppUser;
import com.example.financialtracker.AppUser.AppUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FinancialCategoryService {
    private final FinancialCategoryRepository financialCategoryRepository;
    private final AppUserRepository appUserRepository;

    public void addCategory (String name){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        AppUser appUser = appUserRepository.findAppUserByUsername(auth.getName())
                .orElseThrow(()->new UsernameNotFoundException("User not found"));
        FinancialCategory financialCategory = new FinancialCategory();
        financialCategory.setName(name);
        financialCategory.setCategoryOwner(appUser);
        financialCategoryRepository.save(financialCategory);
    }

    public void editCategory (String name,Long id){
        FinancialCategory financialCategory =  financialCategoryRepository.findById(id)
                .orElseThrow(()->new UsernameNotFoundException("Category does not exist"));

        financialCategory.setName(name);
        financialCategoryRepository.save(financialCategory);
    }

    public void deleteCategory (Long id){
        financialCategoryRepository.deleteById(id);
    }

    public List<FinancialCategory> getCategories (){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        AppUser appUser = appUserRepository.findAppUserByUsername(auth.getName())
                .orElseThrow(()->new UsernameNotFoundException("User not found"));

        return appUser.getCategoryList();
    }
}
