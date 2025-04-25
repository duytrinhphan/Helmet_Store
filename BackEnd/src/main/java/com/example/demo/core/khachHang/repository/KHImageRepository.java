package com.example.demo.core.khachHang.repository;

import com.example.demo.entity.Image;
import com.example.demo.reponsitory.ImageReponsitory;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface KHImageRepository extends ImageReponsitory {

    @Query("select im from  Image im where im.sanPham.id =:id")
    List<Image> findBySanPhamId(Integer id);
}
