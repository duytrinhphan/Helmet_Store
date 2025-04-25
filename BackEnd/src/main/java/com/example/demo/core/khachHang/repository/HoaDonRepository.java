package com.example.demo.core.khachHang.repository;

import com.example.demo.entity.HoaDon;
import com.example.demo.reponsitory.HoaDonReponsitory;

import java.util.Optional;

public interface HoaDonRepository extends HoaDonReponsitory {

    Optional<HoaDon> findById(Integer id);

    HoaDon findAllById(Integer id);
}
