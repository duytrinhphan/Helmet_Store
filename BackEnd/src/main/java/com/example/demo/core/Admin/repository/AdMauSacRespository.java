package com.example.demo.core.Admin.repository;

import com.example.demo.entity.MauSac;
import com.example.demo.reponsitory.MauSacReponsitory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AdMauSacRespository extends MauSacReponsitory {

    @Query("select pt from MauSac pt where pt.ten =:ten")
    MauSac findByTenMauSacExcel(String ten);
}
