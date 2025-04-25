package com.example.demo.core.Admin.repository;

import com.example.demo.core.Admin.model.response.AdminUserResponse;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.reponsitory.UserReponsitory;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdUserRepository extends UserReponsitory {

    @Query(value = """
             SELECT ROW_NUMBER() OVER(ORDER BY u.id DESC) AS stt,
                   u.id as id, u.anh as anh, u.email as email,
                   u.gioi_tinh as gioiTinh, u.ma as ma, u.ngay_sinh as ngaySinh,
                   u.password as pass, u.role as role, u.sdt as sdt,
                   u.ten as ten, u.trang_thai as trangThai, u.user_name as userName,
                   COUNT(hd.id) AS soLuongHoaDon
            FROM datn.user u
            LEFT JOIN datn.hoa_don hd ON u.id = hd.id_user
            WHERE u.role =:roles
            GROUP BY u.id, u.anh, u.email, u.gioi_tinh, u.ma, u.ngay_sinh, u.password, u.role, u.sdt, u.ten, u.trang_thai, u.user_name;
            """, nativeQuery = true)
    List<AdminUserResponse> findUserByRole(@Param("roles") String roles);


    @Query(value = """
             SELECT ROW_NUMBER() OVER(ORDER BY u.id DESC) AS stt,
                   u.id as id, u.anh as anh, u.email as email,
                   u.gioi_tinh as gioiTinh, u.ma as ma, u.ngay_sinh as ngaySinh,
                   u.password as pass, u.role as role, u.sdt as sdt,
                   u.ten as ten, u.trang_thai as trangThai, u.user_name as userName,
                   COUNT(hd.id) AS soLuongHoaDon
            FROM datn.user u
            LEFT JOIN datn.hoa_don hd ON u.id = hd.id_user
            GROUP BY u.id, u.anh, u.email, u.gioi_tinh, u.ma, u.ngay_sinh, u.password, u.role, u.sdt, u.ten, u.trang_thai, u.user_name;
            """, nativeQuery = true)
    List<AdminUserResponse> getAllUser();

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
    AdminUserResponse findUserById(@Param("id") Integer id);

    Optional<User> findUsersByUserNameOrEmail(String userNam, String enail);

    User findByUserName(String username);

    Optional<User>  findByEmail(String email);

    List<User> getAllByTrangThai(Integer trangThai, Sort sort);

    List<User> findByRoleAndTrangThaiOrderByNgayTaoDesc(Role role, Integer trangThai);
    Optional<User> findBySdt(String sdt);
}
