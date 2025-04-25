package com.example.demo.reponsitory;

import com.example.demo.entity.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoucherReponsitory extends JpaRepository<Voucher, Integer> {
}
