package com.example.financialtracker.FinancialRecord;

import com.example.financialtracker.FinancialAccount.FinancialAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FinancialRecordRepository extends JpaRepository<FinancialRecord,Long> {

    Page<FinancialRecord> findAllByAccountRef(FinancialAccount accountRef, Pageable pageable);

}
