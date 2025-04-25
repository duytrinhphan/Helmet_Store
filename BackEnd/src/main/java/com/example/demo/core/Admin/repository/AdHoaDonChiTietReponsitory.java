package com.example.demo.core.Admin.repository;

import com.example.demo.core.Admin.model.response.AdminHoaDonChitietResponse;
import com.example.demo.core.Admin.model.response.AdminHoaDonResponse;
import com.example.demo.entity.HoaDonChiTiet;
import com.example.demo.reponsitory.HoaDonChiTietReponsitory;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdHoaDonChiTietReponsitory extends HoaDonChiTietReponsitory {

    @Query(value = """
                   SELECT ROW_NUMBER() OVER(ORDER BY hd.id DESC) AS stt,
                    u.email as email ,u.sdt,hd.hinh_thuc_giao_hang as hinhThucGiaoHang, 
                    hd.ma as maHD, hd.ngay_nhan as ngayNhan, hd.ngay_ship as ngayShip, hd.ngay_tao as ngayTao,
            		hd.ten_nguoi_nhan as tenNguoiNhan, hd.tien_sau_khi_giam_gia as tienSauKhiGiam,
            		hd.tien_ship as tienShip, hd.tong_tien as tongTien, hd.trang_thai as trangThai,
            		hd.id as idHD, spct.gia_ban as giaBan, spct.gia_sau_giam as giaSPSauGiam,
            		sp.ten as tenSP, dc.dia_chi as diaChi, pttt.ten as tenPTTT
                   FROM datn.hoa_don_chi_tiet hdct join datn.hoa_don hd on hdct.id_hoa_don = hd.id 
            										join datn.san_pham_chi_tiet spct on hdct.id_san_pham_chi_tiet = spct.id
                                                    join datn.san_pham sp on sp.id = spct.id_san_pham
            									left	join datn.dia_chi dc on dc.id = hd.id_dia_chi_sdt 
                                                    join datn.phuong_thuc_thanh_toan pttt on pttt.id = hd.id_phuong_thuc_thanh_toan 
            										join datn.user u on u.id = hd.id_user where hd.id =:id
            """, nativeQuery = true)
    List<AdminHoaDonChitietResponse> getHoaDonChiTietByIdHD(Integer id);

    @Query(value = "select hdct from HoaDonChiTiet hdct where hdct.hoaDon.id = :idHd")
    List<HoaDonChiTiet> findByIdHoaDon(@Param("idHd") Integer idHd, Sort sort);

    @Query(value = """
                SELECT ROW_NUMBER() OVER(ORDER BY hd.id DESC) AS stt,
                                                                                                                                                  u.email as email ,u.sdt,hd.hinh_thuc_giao_hang as hinhThucGiaoHang, u.ten as nguoiTao,
                                                                                                                                                  hd.ma as maHD, hd.ngay_nhan as ngayNhan, hd.ngay_thanh_toan as ngayThanhToan, hd.ngay_sua as ngaySua,
                                                                                                                                                  hd.ngay_ship as ngayShip, hd.ngay_tao as ngayTao, hd.ten_nguoi_nhan as tenNguoiNhan, hd.tien_sau_khi_giam_gia as tienSauKhiGiam,
                                                                                                                                          		hd.tien_ship as tienShip, hd.tong_tien as tongTien, hdct.trang_thai as trangThai,
                                                                                                                                          		hd.id as idHD, spct.gia_ban as giaBan, spct.gia_sau_giam as giaSPSauGiam,
                                                                                                                                          		sp.ten as tenSP, dc.dia_chi as diaChiCuThe, dc.id_phuong_xa as idPhuongXa, dc.ten_phuong_xa as tenPhuongXa,
                                                                                                                                                  dc.id_quan_huyen as idQuanHuyen, dc.ten_quan_huyen as tenQuanHuyen, dc.id_tinh_thanh as idTinhThanh,
                                                                                                                                                  dc.ten_tinh_thanh as tenTinhThanh, pttt.ten as tenPTTT, ms.ten as mauSac, s.ten as size,
                                                                                                                                                  hdct.don_gia as donGia, hdct.so_luong as soLuong, hdct.ly_do as lyDo, spct.anh as anh, sp.ma as maSP,
                                                                                                                                                  spct.id as idSPCT,
                                                                                                                                                  (select t.value from datn.trong_luong t where t.id = spct.id_trong_luong) as trongLuong
                                                                                                                                                 FROM datn.hoa_don_chi_tiet hdct join datn.hoa_don hd on hdct.id_hoa_don = hd.id
                                                                                                                                          										join datn.san_pham_chi_tiet spct on hdct.id_san_pham_chi_tiet = spct.id
                                                                                                                                                                                  join datn.san_pham sp on sp.id = spct.id_san_pham
                                                                                                              																	left join datn.dia_chi dc on dc.id = hd.id_dia_chi_sdt
                                                                                                                                                                                   join datn.size s on s.id = spct.id_size
                                                                                                                                                                                   join datn.mau_sac ms on ms.id = spct.id_mau_sac
                                                                                                                                                                                  join datn.phuong_thuc_thanh_toan pttt on pttt.id = hd.id_phuong_thuc_thanh_toan
                                                                                                                                          										join datn.user u on u.id = hd.id_user where hdct.trang_thai = :trangThai  
            """, nativeQuery = true)
    List<AdminHoaDonChitietResponse> findHDCTByTrangThai(@Param("trangThai") Integer trangThai);

    @Query(value = """
            SELECT ROW_NUMBER() OVER(ORDER BY hd.id DESC) AS stt,
            	u.email as email ,u.sdt,hd.hinh_thuc_giao_hang as hinhThucGiaoHang, u.ten as nguoiTao,
            	hd.ma as maHD, hd.ngay_nhan as ngayNhan, hd.ngay_thanh_toan as ngayThanhToan, hd.ngay_sua as ngaySua,
            	hd.ngay_ship as ngayShip, hd.ngay_tao as ngayTao, hd.ten_nguoi_nhan as tenNguoiNhan, hd.tien_sau_khi_giam_gia as tienSauKhiGiam,
            	hd.tien_ship as tienShip, hd.tong_tien as tongTien, hdct.trang_thai as trangThai,
            	hd.id as idHD, spct.gia_ban as giaBan, spct.gia_sau_giam as giaSPSauGiam, sp.ma as maSP,
            	sp.ten as tenSP, dc.dia_chi as diaChiCuThe, dc.id_phuong_xa as idPhuongXa, dc.ten_phuong_xa as tenPhuongXa,
            	dc.id_quan_huyen as idQuanHuyen, dc.ten_quan_huyen as tenQuanHuyen, dc.id_tinh_thanh as idTinhThanh,
            	dc.ten_tinh_thanh as tenTinhThanh, pttt.ten as tenPTTT, hdct.don_gia as donGia, hdct.so_luong as soLuong,
            	hdct.ly_do as lyDo, sp.anh as anh, (select ms.ten from datn.mau_sac ms where ms.id = spct.id_mau_sac) as mauSac,
            	(select s.ten from datn.size s where s.id = spct.id_size) as size,
            	(select t.value from datn.trong_luong t where t.id = spct.id_trong_luong) as trongLuong,
            	spct.anh as anh, sp.ma as maSP, spct.id as idSPCT
            	FROM datn.hoa_don_chi_tiet hdct join datn.hoa_don hd on hdct.id_hoa_don = hd.id
            		join datn.san_pham_chi_tiet spct on hdct.id_san_pham_chi_tiet = spct.id
            		join datn.san_pham sp on sp.id = spct.id_san_pham
            	left	join datn.dia_chi dc on dc.id = hd.id_dia_chi_sdt
            		join datn.phuong_thuc_thanh_toan pttt on pttt.id = hd.id_phuong_thuc_thanh_toan
            		join datn.user u on u.id = hd.id_user
            		join datn.trong_luong tl on tl.id = spct.id_trong_luong
            	where hd.id = :id""", nativeQuery = true)
    List<AdminHoaDonChitietResponse> findHDCTByIDHD(@Param("id") Integer id);


    List<HoaDonChiTiet> findByHoaDonId(Integer idHoaDon);
    Optional<HoaDonChiTiet> findByHoaDonIdAndSanPhamChiTietId(Integer idHoaDon, Integer idCTSP);


    @Query(value = """
            SELECT ROW_NUMBER() OVER(ORDER BY hd.id DESC) AS stt,
                                                u.email as email ,u.sdt,hd.hinh_thuc_giao_hang as hinhThucGiaoHang, u.ten as nguoiTao,
                                                hd.ma as maHD, hd.ngay_nhan as ngayNhan, hd.ngay_thanh_toan as ngayThanhToan, hd.ngay_sua as ngaySua,
                                                hd.ngay_ship as ngayShip, hd.ngay_tao as ngayTao, hd.ten_nguoi_nhan as tenNguoiNhan, hd.tien_sau_khi_giam_gia as tienSauKhiGiam,
                                        		hd.tien_ship as tienShip, hd.tong_tien as tongTien, hdct.trang_thai as trangThai,
                                        		hd.id as idHD, spct.gia_ban as giaBan, spct.gia_sau_giam as giaSPSauGiam,
                                        		sp.ten as tenSP, dc.dia_chi as diaChiCuThe, dc.id_phuong_xa as idPhuongXa, dc.ten_phuong_xa as tenPhuongXa,
                                                dc.id_quan_huyen as idQuanHuyen, dc.ten_quan_huyen as tenQuanHuyen, dc.id_tinh_thanh as idTinhThanh,
                                                dc.ten_tinh_thanh as tenTinhThanh, pttt.ten as tenPTTT, ms.ten as mauSac, s.ten as size,
                                                hdct.don_gia as donGia, hdct.so_luong as soLuong, hdct.ly_do as lyDo, spct.anh as anh,
                                                sp.ma as maSP,spct.id as idSPCT,
                                                (select t.value from datn.trong_luong t where t.id = spct.id_trong_luong) as trongLuong
                                               FROM datn.hoa_don_chi_tiet hdct join datn.hoa_don hd on hdct.id_hoa_don = hd.id
                                        										join datn.san_pham_chi_tiet spct on hdct.id_san_pham_chi_tiet = spct.id
                                                                                join datn.san_pham sp on sp.id = spct.id_san_pham
            																	left join datn.dia_chi dc on dc.id = hd.id_dia_chi_sdt
                                                                                 join datn.size s on s.id = spct.id_size
                                                                                 join datn.mau_sac ms on ms.id = spct.id_mau_sac
                                                                                join datn.phuong_thuc_thanh_toan pttt on pttt.id = hd.id_phuong_thuc_thanh_toan
                                        										join datn.user u on u.id = hd.id_user where hdct.id = :idHDCT
                        """, nativeQuery = true)
    AdminHoaDonChitietResponse findByIdHDCT(@Param("idHDCT") Integer id);


}
