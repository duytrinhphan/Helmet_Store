package com.example.demo.reponsitory;

import com.example.demo.entity.MauSac;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MauSacReponsitory extends JpaRepository<MauSac, Integer> {
    @Query("select pot from  MauSac pot where pot.ten like :keyword or pot.ma like :keyword")
    Page<MauSac> search(String keyword, Pageable pageable);
    List<MauSac> findAllByTrangThai(Integer trangThai, Sort sort);

}
