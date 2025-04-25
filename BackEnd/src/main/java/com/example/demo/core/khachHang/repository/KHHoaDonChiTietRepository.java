package com.example.demo.core.khachHang.repository;

import com.example.demo.core.khachHang.model.response.KHHoaDonChiTietResponse;
import com.example.demo.core.khachHang.model.response.KhHoaDonTraHangResponse;
import com.example.demo.entity.HoaDonChiTiet;
import com.example.demo.reponsitory.HoaDonChiTietReponsitory;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface KHHoaDonChiTietRepository extends HoaDonChiTietReponsitory {
    @Query(value = """
            SELECT ROW_NUMBER() OVER(ORDER BY hd.id DESC) AS stt,
            u.email as email ,u.sdt,hd.hinh_thuc_giao_hang as hinhThucGiaoHang, u.ten as nguoiTao,
            hd.ma as maHD, hd.ngay_nhan as ngayNhan, hd.ngay_thanh_toan as ngayThanhToan, hd.ngay_sua as ngaySua,
            hd.ngay_ship as ngayShip, hd.ngay_tao as ngayTao, hd.ten_nguoi_nhan as tenNguoiNhan, hd.tien_sau_khi_giam_gia as tienSauKhiGiam,
            hd.tien_ship as tienShip, hd.tong_tien as tongTien, hdct.trang_thai as trangThai,
            hd.id as idHD, spct.gia_ban as giaBan, spct.gia_sau_giam as giaSPSauGiam, sp.ma as maSP, hd.id_dia_chi_sdt as idDiaChi,hdct.id as idHoaDonCT,
            hd.id_user as idUser ,sp.ten as tenSP, dc.dia_chi as diaChiCuThe, dc.id_phuong_xa as idPhuongXa, dc.ten_phuong_xa as tenPhuongXa,
            dc.id_quan_huyen as idQuanHuyen, dc.ten_quan_huyen as tenQuanHuyen, dc.id_tinh_thanh as idTinhThanh, hdct.ma as maHDCT,
            dc.ten_tinh_thanh as tenTinhThanh, pttt.ten as tenPTTT, hdct.don_gia as donGia, hdct.so_luong as soLuong,
            hdct.ly_do as lyDo, spct.anh as anh, (select ms.ten from datn.mau_sac ms where ms.id = spct.id_mau_sac) as mauSac,
             (select ms.id from datn.mau_sac ms where ms.id = spct.id_mau_sac) as idMauSac,
             (select s.id from datn.size s where s.id = spct.id_size) as idSize,
            (select s.ten from datn.size s where s.id = spct.id_size) as size, hdct.trang_thai as trangThaiHdct,spct.so_luong_ton as soLuongTon,spct.id as idSPCT,
            (select t.value from datn.trong_luong t where t.id = spct.id_trong_luong) as trongLuong
            FROM datn.hoa_don_chi_tiet hdct join datn.hoa_don hd on hdct.id_hoa_don = hd.id
            join datn.san_pham_chi_tiet spct on hdct.id_san_pham_chi_tiet = spct.id
            join datn.san_pham sp on sp.id = spct.id_san_pham
            left join datn.dia_chi dc on dc.id = hd.id_dia_chi_sdt
            join datn.phuong_thuc_thanh_toan pttt on pttt.id = hd.id_phuong_thuc_thanh_toan
            join datn.user u on u.id = hd.id_user
            join datn.trong_luong tl on tl.id = spct.id_trong_luong
            	where hd.id = :id""", nativeQuery = true)
    List<KHHoaDonChiTietResponse> findHDCTByIDHD(@Param("id") Integer id);



    @Query(value = """
            SELECT ROW_NUMBER() OVER(ORDER BY hd.id DESC) AS stt,
            u.email as email ,u.sdt,hd.hinh_thuc_giao_hang as hinhThucGiaoHang, u.ten as nguoiTao,
            hd.ma as maHD, hd.ngay_nhan as ngayNhan, hd.ngay_thanh_toan as ngayThanhToan, hd.ngay_sua as ngaySua,
            hd.ngay_ship as ngayShip, hd.ngay_tao as ngayTao, hd.ten_nguoi_nhan as tenNguoiNhan, hd.tien_sau_khi_giam_gia as tienSauKhiGiam,
            hd.tien_ship as tienShip, hd.tong_tien as tongTien, hdct.trang_thai as trangThai,
            hd.id as idHD, spct.gia_ban as giaBan, spct.gia_sau_giam as giaSPSauGiam, sp.ma as maSP, hd.id_dia_chi_sdt as idDiaChi,hdct.id as idHoaDonCT,
            hd.id_user as idUser ,sp.ten as tenSP, dc.dia_chi as diaChiCuThe, dc.id_phuong_xa as idPhuongXa, dc.ten_phuong_xa as tenPhuongXa,
            dc.id_quan_huyen as idQuanHuyen, dc.ten_quan_huyen as tenQuanHuyen, dc.id_tinh_thanh as idTinhThanh, hdct.ma as maHDCT,
            dc.ten_tinh_thanh as tenTinhThanh, pttt.ten as tenPTTT, hdct.don_gia as donGia, hdct.so_luong as soLuong,
            hdct.ly_do as lyDo, spct.anh as anh, (select ms.ten from datn.mau_sac ms where ms.id = spct.id_mau_sac) as mauSac,
             (select ms.id from datn.mau_sac ms where ms.id = spct.id_mau_sac) as idMauSac,
             (select s.id from datn.size s where s.id = spct.id_size) as idSize,
            (select s.ten from datn.size s where s.id = spct.id_size) as size, hdct.trang_thai as trangThaiHdct,spct.so_luong_ton as soLuongTon,spct.id as idSPCT,
            (select t.value from datn.trong_luong t where t.id = spct.id_trong_luong) as trongLuong
            FROM datn.hoa_don_chi_tiet hdct join datn.hoa_don hd on hdct.id_hoa_don = hd.id
            join datn.san_pham_chi_tiet spct on hdct.id_san_pham_chi_tiet = spct.id
            join datn.san_pham sp on sp.id = spct.id_san_pham
            left join datn.dia_chi dc on dc.id = hd.id_dia_chi_sdt
            join datn.phuong_thuc_thanh_toan pttt on pttt.id = hd.id_phuong_thuc_thanh_toan
            join datn.user u on u.id = hd.id_user
            join datn.trong_luong tl on tl.id = spct.id_trong_luong
            	where hd.ma = :ma""", nativeQuery = true)
    List<KHHoaDonChiTietResponse> findHDCTByMaHD(@Param("ma") String ma);



    @Query(value = "select hdct from HoaDonChiTiet hdct where hdct.hoaDon.id = :idHd")
    List<HoaDonChiTiet> findByIdHoaDon(@Param("idHd") Integer idHd, Sort sort);


    @Query(value = """
                    SELECT ROW_NUMBER() OVER(ORDER BY hd.id DESC) AS stt,
                    hdct.trang_thai as trangThaihdct,
                     hd.id as idHD,hd.ma as maHD, hdct.so_luong as soLuong, hdct.don_gia as donGia
                     FROM  datn.hoa_don hd  join datn.hoa_don_chi_tiet hdct on hdct.id_hoa_don = hd.id
                     join datn.user u on u.id = hd.id_user where u.id =:idUser and (hdct.trang_thai=7 or hdct.trang_thai=8 or hdct.trang_thai=9)
            """, nativeQuery = true)
    List<KhHoaDonTraHangResponse> getHoaDonDoiTraTrangThai(Integer idUser);
}
