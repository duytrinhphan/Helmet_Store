package com.example.demo.core.Admin.repository;

import com.example.demo.entity.ThuongHieu;
import com.example.demo.reponsitory.ThuongHieuReponsitory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdThuongHieuReponsitory extends ThuongHieuReponsitory {
    @Query("select  pot from  ThuongHieu  pot where pot.ten like :keyword or pot.ma like :keyword")
    Page<ThuongHieu> search(String keyword, Pageable pageable);

    List<ThuongHieu> findAllByTrangThai(Integer trangThai, Sort sort);

    ThuongHieu findByTen(String ten);

}
