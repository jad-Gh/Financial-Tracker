package com.example.financialtracker.FinancialCategory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FinancialCategoryRepository extends JpaRepository<FinancialCategory,Long> {

}
