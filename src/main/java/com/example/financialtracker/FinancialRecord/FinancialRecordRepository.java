package com.example.financialtracker.FinancialRecord;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FinancialRecordRepository extends JpaRepository<FinancialRecord,Long> {

}
