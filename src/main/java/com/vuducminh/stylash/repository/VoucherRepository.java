package com.vuducminh.stylash.repository;

import com.vuducminh.stylash.model.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher, Long> {

    Voucher findByCode(String code);
    Voucher getById(Long id);
    List<Voucher> findByCodeContaining(String name);
}

