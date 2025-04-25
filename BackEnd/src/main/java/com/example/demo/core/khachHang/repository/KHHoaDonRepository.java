package com.example.demo.core.khachHang.repository;

import com.example.demo.core.khachHang.model.response.KHHoaDonResponse;
import com.example.demo.reponsitory.HoaDonReponsitory;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface KHHoaDonRepository extends HoaDonReponsitory {
    @Query(value = """
                    SELECT ROW_NUMBER() OVER(ORDER BY hd.id DESC) AS stt,
                           u.email as email ,u.sdt,hd.hinh_thuc_giao_hang as hinhThucGiaoHang, hd.ly_do as lyDo,
                           hd.ma as maHD, u.ten as nguoiTao, hd.ngay_nhan as ngayNhan, hd.ngay_ship as ngayShip, hd.ngay_tao as ngayTao,\s
                           hd.ngay_sua as ngaySua, hd.ten_nguoi_nhan as tenNguoiNhan, hd.tien_sau_khi_giam_gia as tienSauKhiGiam,
                           hd.tien_ship as tienShip, hd.tong_tien as tongTien, hd.trang_thai as trangThai,
                           hd.id as idHD,hd.mo_ta as moTa,vou.id as idVoucher, vou.ten as tenVoucher,
                           dc.dia_chi as diaChiCuThe, dc.id_tinh_thanh as idTinhThanh,\s
                           dc.ten_tinh_thanh as tenTinhThanh, dc.id_quan_huyen as idQuanHuyen, dc.ten_quan_huyen as tenQuanHuyen,\s
                           dc.id_phuong_xa as idPhuongXa, dc.ten_phuong_xa as tenPhuongXa,
                           pttt.ten as tenPTTT, hd.ngay_thanh_toan  as ngayThanhToan
                    FROM  datn.hoa_don hd    left join datn.dia_chi dc on dc.id = hd.id_dia_chi_sdt\s
                                             join datn.phuong_thuc_thanh_toan pttt on pttt.id = hd.id_phuong_thuc_thanh_toan\s
                                               left  join voucher vou  on hd.id_voucher = vou.id
                                             join datn.user u on u.id = hd.id_user where u.id =:id
            """, nativeQuery = true)
    List<KHHoaDonResponse> getHoaDonByIdUser(Integer id);

    @Query(value = """
                    SELECT ROW_NUMBER() OVER(ORDER BY hd.id DESC) AS stt,
                           u.email as email ,u.sdt,hd.hinh_thuc_giao_hang as hinhThucGiaoHang, hd.ly_do as lyDo,
                           hd.ma as maHD, u.ten as nguoiTao, hd.ngay_nhan as ngayNhan, hd.ngay_ship as ngayShip, hd.ngay_tao as ngayTao,
                           hd.ngay_sua as ngaySua, hd.ten_nguoi_nhan as tenNguoiNhan, hd.tien_sau_khi_giam_gia as tienSauKhiGiam,
                           hd.tien_ship as tienShip, hd.tong_tien as tongTien, hd.trang_thai as trangThai,
                           hd.id as idHD,hd.mo_ta as moTa,vou.id as idVoucher, vou.ten as tenVoucher,
                           dc.dia_chi as diaChiCuThe, dc.id_tinh_thanh as idTinhThanh,
                           dc.ten_tinh_thanh as tenTinhThanh, dc.id_quan_huyen as idQuanHuyen, dc.ten_quan_huyen as tenQuanHuyen,
                           dc.id_phuong_xa as idPhuongXa, dc.ten_phuong_xa as tenPhuongXa,
                           pttt.ten as tenPTTT, hd.ngay_thanh_toan  as ngayThanhToan
                    FROM  datn.hoa_don hd  left  join datn.dia_chi dc on dc.id = hd.id_dia_chi_sdt
                                             join datn.phuong_thuc_thanh_toan pttt on pttt.id = hd.id_phuong_thuc_thanh_toan
                                               left  join voucher vou  on hd.id_voucher = vou.id
                                             join datn.user u on u.id = hd.id_user where u.id = :id and (hd.trang_thai =:trangThai 
                                               or hd.trang_thai =:trangThai2
                                              or hd.trang_thai =:trangThai3 or hd.trang_thai =:trangThai4)
            """, nativeQuery = true)
    List<KHHoaDonResponse> getHoaDonTrangThai(Integer id, Integer trangThai, Integer trangThai2, Integer trangThai3, Integer trangThai4);


    @Query(value = """
                     SELECT ROW_NUMBER() OVER(ORDER BY hd.id DESC) AS stt,
                           u.email as email ,u.sdt,hd.hinh_thuc_giao_hang as hinhThucGiaoHang, hd.ly_do as lyDo,
                           hd.ma as maHD, u.ten as nguoiTao, hd.ngay_nhan as ngayNhan, hd.ngay_ship as ngayShip, hd.ngay_tao as ngayTao,\s
                           hd.ngay_sua as ngaySua, hd.ten_nguoi_nhan as tenNguoiNhan, hd.tien_sau_khi_giam_gia as tienSauKhiGiam,
                           hd.tien_ship as tienShip, hd.tong_tien as tongTien, hd.trang_thai as trangThai,
                           hd.id as idHD, hd.mo_ta as moTa,vou.id as idVoucher, vou.ten as tenVoucher,
                           dc.dia_chi as diaChiCuThe, dc.id_tinh_thanh as idTinhThanh,\s
                           dc.ten_tinh_thanh as tenTinhThanh, dc.id_quan_huyen as idQuanHuyen, dc.ten_quan_huyen as tenQuanHuyen,\s
                           dc.id_phuong_xa as idPhuongXa, dc.ten_phuong_xa as tenPhuongXa,
                           pttt.ten as tenPTTT, hd.ngay_thanh_toan  as ngayThanhToan
                    FROM  datn.hoa_don hd    left join datn.dia_chi dc on dc.id = hd.id_dia_chi_sdt\s
                                             join datn.phuong_thuc_thanh_toan pttt on pttt.id = hd.id_phuong_thuc_thanh_toan\s
                                               left  join voucher vou  on hd.id_voucher = vou.id
                                             join datn.user u on u.id = hd.id_user
            	   WHERE  hd.id=:id
            """, nativeQuery = true)
    KHHoaDonResponse getByIds(Integer id);



    @Query(value = """
                     SELECT ROW_NUMBER() OVER(ORDER BY hd.id DESC) AS stt,
                           u.email as email ,u.sdt,hd.hinh_thuc_giao_hang as hinhThucGiaoHang, hd.ly_do as lyDo,
                           hd.ma as maHD, u.ten as nguoiTao, hd.ngay_nhan as ngayNhan, hd.ngay_ship as ngayShip, hd.ngay_tao as ngayTao,\s
                           hd.ngay_sua as ngaySua, hd.ten_nguoi_nhan as tenNguoiNhan, hd.tien_sau_khi_giam_gia as tienSauKhiGiam,
                           hd.tien_ship as tienShip, hd.tong_tien as tongTien, hd.trang_thai as trangThai,
                           hd.id as idHD, hd.mo_ta as moTa,vou.id as idVoucher, vou.ten as tenVoucher,
                           dc.dia_chi as diaChiCuThe, dc.id_tinh_thanh as idTinhThanh,\s
                           dc.ten_tinh_thanh as tenTinhThanh, dc.id_quan_huyen as idQuanHuyen, dc.ten_quan_huyen as tenQuanHuyen,\s
                           dc.id_phuong_xa as idPhuongXa, dc.ten_phuong_xa as tenPhuongXa,
                           pttt.ten as tenPTTT, hd.ngay_thanh_toan  as ngayThanhToan
                    FROM  datn.hoa_don hd    left join datn.dia_chi dc on dc.id = hd.id_dia_chi_sdt\s
                                             join datn.phuong_thuc_thanh_toan pttt on pttt.id = hd.id_phuong_thuc_thanh_toan\s
                                               left  join voucher vou  on hd.id_voucher = vou.id
                                             join datn.user u on u.id = hd.id_user
            	   WHERE  hd.ma=:ma
            """, nativeQuery = true)
    KHHoaDonResponse getByMaHD(String ma);
}
