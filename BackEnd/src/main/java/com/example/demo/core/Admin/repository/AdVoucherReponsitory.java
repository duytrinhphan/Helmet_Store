package com.example.demo.core.Admin.repository;

import com.example.demo.core.Admin.model.response.AdminVoucherGetUserResponse;
import com.example.demo.entity.Voucher;
import com.example.demo.reponsitory.VoucherReponsitory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdVoucherReponsitory extends VoucherReponsitory {

    @Query("SELECT i FROM Voucher i WHERE i.thoiGianKetThuc <= CURRENT_DATE ")
    List<Voucher> findVoucherByHetHan();

    @Query("SELECT i FROM Voucher i WHERE i.thoiGianBatDau >= CURRENT_DATE")
    List<Voucher> findVoucherByChuaBatDAu();

    @Query("SELECT i FROM Voucher i WHERE i.thoiGianBatDau <= CURRENT_DATE and i.thoiGianKetThuc>= CURRENT_DATE ")
    List<Voucher> findVoucherByConHan();

    @Query("select  i  from  Voucher  i where i.trangThai =:trangThai")
    Page<Voucher> getbyTrangThai(Integer trangThai, Pageable pageable);

    // lấy danh sách voucher ngoại trừ trạng thái là đã xoá
    @Query("select i from Voucher  i where i.trangThai <> 4 order by  i.id desc ")
    List<Voucher> getVoucherByTrangThai();

    @Query(value = """
            select ROW_NUMBER() OVER(ORDER BY u.ma DESC) AS stt, u.ma as ma, u.ten as ten, u.sdt as sdt, u.email as email,\s
            sum(hd.tong_tien) as tongTien
            from datn.voucher v
            join datn.user_voucher uv on v.id = uv.id_voucher
            join datn.user u on u.id = uv.id_user
            left join datn.hoa_don hd on u.id = hd.id_user
            where v.id = :idVoucher
            group by ma,ten,email,sdt
            """, nativeQuery = true)
    List<AdminVoucherGetUserResponse> getUserByVoucher(@Param("idVoucher") Integer idVoucher);

    @Query("select i from Voucher  i where i.ten=:ten and not i.trangThai=4")
    Voucher getVoucherByTen(String ten);
}
