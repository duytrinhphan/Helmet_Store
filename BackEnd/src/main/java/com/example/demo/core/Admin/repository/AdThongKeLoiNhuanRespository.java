package com.example.demo.core.Admin.repository;

import com.example.demo.core.Admin.model.response.AdminThongKeLoiNhuanHoaDonResponse;
import com.example.demo.core.Admin.model.response.AdminThongKeLoiNhuanSanPhamResponse;
import com.example.demo.core.Admin.model.response.AdminThongKeSanPhamCaoResponse;
import com.example.demo.core.Admin.model.response.AdminThongKeSanPhamThapResponse;
import com.example.demo.reponsitory.HoaDonReponsitory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdThongKeLoiNhuanRespository extends HoaDonReponsitory {
    @Query(value = """
            SELECT  SUM( hd.tong_tien-hdct.don_gia ) as loiNhuan\s
           FROM  datn.
            hoa_don hd  JOIN datn.hoa_don_chi_tiet hdct ON  hd.id = hdct.id_hoa_don
           where hd.trang_thai in(3) and  YEAR(hd.ngay_tao) =:year or (hd.ngay_tao BETWEEN :startDate AND :endDate) 
            """, nativeQuery = true)
    Integer tongLoiNhuan(String year, String startDate, String endDate );


    @Query(value = """
           WITH DoanhThuSanPham AS (
             SELECT\s
               sp.ma as ma,
               sp.ten as ten,
               spct.gia_ban as giaBan,
               spct.gia_nhap as giaNhap,
               sp.anh as anh,
               spct.trang_thai as trangThai,
               SUM(hdct.don_gia - spct.gia_nhap) as loiNhuan
             FROM datn.san_pham_chi_tiet spct
             JOIN datn.san_pham sp ON spct.id_san_pham = sp.id
             JOIN datn.hoa_don_chi_tiet hdct ON hdct.id_san_pham_chi_tiet = spct.id
             JOIN datn.hoa_don hd ON hd.id = hdct.id_hoa_don
             WHERE hd.trang_thai IN (3)  and  YEAR(hd.ngay_tao) =:year or (hd.ngay_tao BETWEEN :startDate AND :endDate)
             GROUP BY sp.ma, sp.ten,spct.gia_ban,spct.gia_nhap,sp.anh, spct.trang_thai
           )
           SELECT * FROM DoanhThuSanPham order by loiNhuan desc
              """, nativeQuery = true)
    List<AdminThongKeLoiNhuanSanPhamResponse> lstSanPhamLoiNhuan(String year, String startDate, String endDate );

    @Query(value = """
           WITH DoanhThuSanPham AS (
             SELECT\s
             hd.id as idHD,
             hd.ma as maHD,
             hd.ngay_tao as ngayTao,
             hd.tong_tien as tongTien,
             hd.tien_sau_khi_giam_gia as tienSauGiam,
             pt.ten as tenPhuongThucThanhToan,
             hd.tien_ship as tienShip,
             hd.trang_thai as trangThai,
              dc.dia_chi as diaChiCuThe, dc.id_tinh_thanh as idTinhThanh,\s
              dc.ten_tinh_thanh as tenTinhThanh, dc.id_quan_huyen as idQuanHuyen, dc.ten_quan_huyen as tenQuanHuyen,\s
              dc.id_phuong_xa as idPhuongXa, dc.ten_phuong_xa as tenPhuongXa,
              hd.tong_tien- SUM(hdct.don_gia ) as loiNhuan,
               (select  sum(v.don_gia) from datn.hoa_don_chi_tiet v where v.trang_thai = 8 and v.id_hoa_don = hd.id)  as hoanTien
             FROM datn.san_pham_chi_tiet spct
             JOIN datn.san_pham sp ON spct.id_san_pham = sp.id
             JOIN datn.hoa_don_chi_tiet hdct ON hdct.id_san_pham_chi_tiet = spct.id
             JOIN datn.hoa_don hd ON hd.id = hdct.id_hoa_don
          left   join datn.dia_chi dc on dc.id = hd.id_dia_chi_sdt
            left join datn.phuong_thuc_thanh_toan pt on hd.id_phuong_thuc_thanh_toan = pt.id
             WHERE hd.trang_thai IN (3)  and   YEAR(hd.ngay_tao) =:year or (hd.ngay_tao BETWEEN :startDate AND :endDate)
             GROUP BY hd.id,hd.ma,hd.ngay_tao,hd.tong_tien,hd.tien_sau_khi_giam_gia, hd.trang_thai,pt.ten,hd.tien_ship
           )
           SELECT * FROM DoanhThuSanPham
              """, nativeQuery = true)
    List<AdminThongKeLoiNhuanHoaDonResponse> lstHoaDonLoiNhuan(String year, String startDate, String endDate );



    @Query(value = """
            SELECT count(hd.id) FROM datn.hoa_don hd where hd.trang_thai=3 and   YEAR(hd.ngay_tao) =:year or (hd.ngay_tao BETWEEN :startDate AND :endDate)
             """, nativeQuery = true)
    Integer tongDonhangHoanThanh(String year, String startDate, String endDate );

    @Query(value = """
           select  sum(v.don_gia) from datn.hoa_don_chi_tiet v join datn.hoa_don hd on hd.id = v.id_hoa_don where v.trang_thai = 8  and   YEAR(hd.ngay_tao) =:year or (hd.ngay_tao BETWEEN :startDate AND :endDate)
            """, nativeQuery = true)
    Integer tongDonhangDangGiao(String year, String startDate, String endDate );

    @Query(value = """
           SELECT count(hd.id) FROM datn.hoa_don hd where hd.trang_thai=0 and   YEAR(hd.ngay_tao) =:year or (hd.ngay_tao BETWEEN :startDate AND :endDate)
            """, nativeQuery = true)
    Integer tongDonhangHuy(String year, String startDate, String endDate );


    @Query(value = """
            SELECT  SUM( hd.tong_tien-hdct.don_gia ) as loiNhuan\s
           FROM  datn.
            hoa_don hd  JOIN datn.hoa_don_chi_tiet hdct ON  hd.id = hdct.id_hoa_don
           where hd.trang_thai in(3) and hd.hinh_thuc_giao_hang =:idPhuongThuc
            """, nativeQuery = true)
    Integer tongLoiNhuanByHinhThuc(Integer idPhuongThuc);


    @Query(value = """
           WITH DoanhThuSanPham AS (
             SELECT\s
               sp.ma as ma,
               sp.ten as ten,
               spct.gia_ban as giaBan,
               spct.gia_nhap as giaNhap,
               sp.anh as anh,
               spct.trang_thai as trangThai,
               SUM(hdct.don_gia - spct.gia_nhap) as loiNhuan
             FROM datn.san_pham_chi_tiet spct
             JOIN datn.san_pham sp ON spct.id_san_pham = sp.id
             JOIN datn.hoa_don_chi_tiet hdct ON hdct.id_san_pham_chi_tiet = spct.id
             JOIN datn.hoa_don hd ON hd.id = hdct.id_hoa_don
             WHERE hd.trang_thai IN (3)  and hd.hinh_thuc_giao_hang =:idPhuongThuc
             GROUP BY sp.ma, sp.ten,spct.gia_ban,spct.gia_nhap,sp.anh, spct.trang_thai
           )
           SELECT * FROM DoanhThuSanPham order by loiNhuan desc
              """, nativeQuery = true)
    List<AdminThongKeLoiNhuanSanPhamResponse> lstSanPhamLoiNhuanByHinhThuc(Integer idPhuongThuc);

    @Query(value = """
           WITH DoanhThuSanPham AS (
             SELECT\s
             hd.id as idHD,
             hd.ma as maHD,
             hd.ngay_tao as ngayTao,
             hd.tong_tien as tongTien,
             hd.tien_sau_khi_giam_gia as tienSauGiam,
             pt.ten as tenPhuongThucThanhToan,
             hd.tien_ship as tienShip,
             hd.trang_thai as trangThai,
              dc.dia_chi as diaChiCuThe, dc.id_tinh_thanh as idTinhThanh,\s
              dc.ten_tinh_thanh as tenTinhThanh, dc.id_quan_huyen as idQuanHuyen, dc.ten_quan_huyen as tenQuanHuyen,\s
              dc.id_phuong_xa as idPhuongXa, dc.ten_phuong_xa as tenPhuongXa,
              hd.tong_tien- SUM(hdct.don_gia ) as loiNhuan,
                (select  sum(v.don_gia) from datn.hoa_don_chi_tiet v where v.trang_thai = 8 and v.id_hoa_don = hd.id)  as hoanTien
             FROM datn.san_pham_chi_tiet spct
             JOIN datn.san_pham sp ON spct.id_san_pham = sp.id
             JOIN datn.hoa_don_chi_tiet hdct ON hdct.id_san_pham_chi_tiet = spct.id
             JOIN datn.hoa_don hd ON hd.id = hdct.id_hoa_don
          left   join datn.dia_chi dc on dc.id = hd.id_dia_chi_sdt
           left  join datn.phuong_thuc_thanh_toan pt on hd.id_phuong_thuc_thanh_toan = pt.id
             WHERE hd.trang_thai IN (3) and hd.hinh_thuc_giao_hang =:idPhuongThuc
             GROUP BY hd.id,hd.ma,hd.ngay_tao,hd.tong_tien,hd.tien_sau_khi_giam_gia, hd.trang_thai,pt.ten,hd.tien_ship
           )
           SELECT * FROM DoanhThuSanPham
              """, nativeQuery = true)
    List<AdminThongKeLoiNhuanHoaDonResponse> lstHoaDonLoiNhuanByHinhThuc(Integer idPhuongThuc);



    @Query(value = """
            SELECT count(hd.id) FROM datn.hoa_don hd where hd.trang_thai=3 and hd.hinh_thuc_giao_hang =:idPhuongThuc
               """, nativeQuery = true)
    Integer tongDonhangHoanThanhByHinhThuc(Integer idPhuongThuc);

    @Query(value = """
            select  sum(v.don_gia) from datn.hoa_don_chi_tiet v join datn.hoa_don hd on hd.id = v.id_hoa_don where v.trang_thai = 8 and hd.hinh_thuc_giao_hang =:idPhuongThuc
                """, nativeQuery = true)
    Integer tongDonhangDangGiaoByHinhThuc(Integer idPhuongThuc);

    @Query(value = """
           SELECT count(hd.id) FROM datn.hoa_don hd where hd.trang_thai=0 and hd.hinh_thuc_giao_hang =:idPhuongThuc
            """, nativeQuery = true)
    Integer tongDonhangHuyByHinhThuc(Integer idPhuongThuc);


    @Query(value = """
            SELECT count(hd.id) FROM datn.hoa_don hd
            """, nativeQuery = true)
    Integer tongDonhang();

    @Query(value = """
           SELECT count(hd.id) FROM datn.hoa_don hd where hd.trang_thai=8;
            """, nativeQuery = true)
    Integer tongDonhangHoanTra();
}
