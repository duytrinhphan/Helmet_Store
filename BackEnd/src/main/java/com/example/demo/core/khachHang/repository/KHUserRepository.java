package com.example.demo.core.khachHang.repository;

import com.example.demo.core.khachHang.model.response.KHUserResponse;
import com.example.demo.entity.User;
import com.example.demo.reponsitory.UserReponsitory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
//@Repository("khUserRepo")
public interface KHUserRepository extends UserReponsitory {

    User findAllByUserName(String userName);

    Optional<User> findAllById(Integer id);

    Optional<User> findAllByEmail(String email);

    User findUserByEmail(String email);

    @Query(value = """
             SELECT ROW_NUMBER() OVER(ORDER BY u.id DESC) AS stt,
                   u.id as id, u.anh as anh, u.email as email,
                   u.gioi_tinh as gioiTinh, u.ma as ma, u.ngay_sinh as ngaySinh,
                   u.password as pass, u.role as role, u.sdt as sdt,
                   u.ten as ten, u.trang_thai as trangThai, u.user_name as userName,
                   COUNT(hd.id) AS soLuongHoaDon
            FROM datn.user u
            LEFT JOIN datn.hoa_don hd ON u.id = hd.id_user
            WHERE u.id =:id
            GROUP BY u.id, u.anh, u.email, u.gioi_tinh, u.ma, u.ngay_sinh, u.password, u.role, u.sdt, u.ten, u.trang_thai, u.user_name;
            """, nativeQuery = true)
    KHUserResponse findUserById(@Param("id") Integer id);

    boolean existsByEmail(String email);
}
