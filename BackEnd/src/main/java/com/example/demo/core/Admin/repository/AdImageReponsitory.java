package com.example.demo.core.Admin.repository;

import com.example.demo.core.Admin.model.response.AdminImageResponse;
import com.example.demo.entity.Image;
import com.example.demo.reponsitory.ImageReponsitory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdImageReponsitory extends ImageReponsitory {

    @Query("select im from  Image im where im.sanPham.id =:id")
    List<Image> findBySanPhamId(Integer id);

    @Query(value = """
            select im.anh as anh, im.id as id from  datn.image im where im.id_san_pham =:id
             """, nativeQuery = true)
    List<AdminImageResponse> findBySanPhamIds(Integer id);

    @Query(value = """
            select im.anh , im.id as id from  datn.image im where im.id =:id
             """, nativeQuery = true)
    AdminImageResponse findByIds(Integer id);

    @Query("select im from Image im where im.sanPham.id = :id and im.anh like :anh")
    Image findBySanPhamIdAndAnh(@Param("id") Integer id, @Param("anh") String anh);

}
