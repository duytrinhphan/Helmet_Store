package com.example.demo.core.Admin.repository;

import com.example.demo.entity.Loai;
import com.example.demo.reponsitory.LoaiReponsitory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdLoaiReponsitory extends LoaiReponsitory {
    @Query("select  pot from  Loai  pot where pot.ten like :keyword or pot.ma like :keyword")
    Page<Loai> search(String keyword, Pageable pageable);
    List<Loai> findAllByTrangThai(Integer trangThai, Sort sort);
    Optional<Loai> findByTen(String ten);

    @Query("select  pot from  Loai  pot where pot.ten like :ten")
    Loai findByTens(String ten);

}
