package com.example.demo.core.Admin.repository;

import com.example.demo.core.Admin.model.response.*;
import com.example.demo.reponsitory.HoaDonReponsitory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdThongKeResponsitory extends HoaDonReponsitory {

    @Query(value = """
            SELECT SUM(hd.tong_tien) as tongTien
            FROM  datn.hoa_don hd
            where hd.trang_thai in (3,10) 
            """, nativeQuery = true)
    Integer tongDoanhThu(); //


    @Query(value = """
           WITH DoanhThuSanPham AS (
           SELECT
                sp.ma as ma,
                sp.ten as ten,
                SUM(hd.tong_tien) AS tongTien
           FROM datn.san_pham_chi_tiet spct
            JOIN datn.san_pham sp ON spct.id_san_pham = sp.id
            JOIN datn.hoa_don_chi_tiet hdct ON hdct.id_san_pham_chi_tiet = spct.id
             JOIN datn.hoa_don hd ON hd.id = hdct.id_hoa_don
             WHERE hd.trang_thai IN (3)
             GROUP BY sp.ma, sp.ten
           )
           SELECT * FROM DoanhThuSanPham ORDER BY tongTien desc LIMIT 5;
                                                                         
              """, nativeQuery = true)
    List<AdminThongKeSanPhamCaoResponse> sanPhamDoanhThuCaoNhat(); //

    @Query(value = """
            WITH DoanhThuSanPham AS (
              SELECT
                sp.ma as ma,
                sp.ten as ten,
                SUM(hd.tong_tien) AS tongTien
              FROM datn.san_pham_chi_tiet spct
              JOIN datn.san_pham sp ON spct.id_san_pham = sp.id
              JOIN datn.hoa_don_chi_tiet hdct ON hdct.id_san_pham_chi_tiet = spct.id
              JOIN datn.hoa_don hd ON hd.id = hdct.id_hoa_don
              WHERE hd.trang_thai IN  (3,10) 
       GROUP BY sp.ma, sp.ten       )
            SELECT * FROM DoanhThuSanPham ORDER BY tongTien  LIMIT 5;
              """, nativeQuery = true)
    List<AdminThongKeSanPhamThapResponse> sanPhamDoanhThuThapNhat();//

    @Query(value = """
            SELECT  MONTH(hd.ngay_tao) AS thang, SUM(hd.tong_tien) AS tongTien,YEAR(CURDATE()) as nam
            FROM datn.hoa_don hd  where year(hd.ngay_tao)=YEAR(CURDATE()) and hd.trang_thai IN  (3,10) 
            GROUP BY MONTH(hd.ngay_tao) ORDER BY MONTH(hd.ngay_tao) ;
              """, nativeQuery = true)
    List<AdminThongKeThangResponse> doanhThuTheoThang();//

    @Query(value = """
            SELECT  MONTH(hd.ngay_tao) AS thang, SUM(hd.tong_tien) AS tongTien,YEAR(CURDATE()) -1 as nam
            FROM datn.hoa_don hd \s
            WHERE YEAR(hd.ngay_tao) = YEAR(CURDATE()) -1  and hd.trang_thai IN  (3,10) 
            GROUP BY MONTH(hd.ngay_tao)\s
            ORDER BY MONTH(hd.ngay_tao);
              """, nativeQuery = true)
    List<AdminThongKeThangNamTruocResponse> doanhThuTheoThangNamTruoc();//

    @Query(value = """
            WITH DoanhThuThuongHieu AS (
               SELECT
                  th.ten AS ten,
                   th.ma as ma,
                  SUM(hd.tong_tien) AS tongTien
               FROM datn.san_pham_chi_tiet spct
               JOIN datn.san_pham sp ON spct.id_san_pham = sp.id
               JOIN datn.thuong_hieu th ON sp.id_thuong_hieu = th.id
               JOIN datn.hoa_don_chi_tiet hdct ON hdct.id_san_pham_chi_tiet = spct.id
               JOIN datn.hoa_don hd ON hd.id = hdct.id_hoa_don
               WHERE hd.trang_thai IN  (3,10) 
               GROUP BY   th.ten,  th.ma
            )
            SELECT ten,ma, SUM(tongTien) AS tongTien
            FROM DoanhThuThuongHieu GROUP BY ten,ma ORDER BY tongTien DESC;
              """, nativeQuery = true)
    List<AdminThongKeThuongHieuResponse> doanhThuTheoThuongHieu();//

    @Query(value = """
            WITH DoanhThuLoai AS (
              SELECT
                th.ten AS ten,
                th.ma as ma,
                SUM(hd.tong_tien) AS tongTien
              FROM datn.san_pham_chi_tiet spct
              JOIN datn.san_pham sp ON spct.id_san_pham = sp.id
              JOIN datn.loai th ON sp.id_loai = th.id
              JOIN datn.hoa_don_chi_tiet hdct ON hdct.id_san_pham_chi_tiet = spct.id
              JOIN datn.hoa_don hd ON hd.id = hdct.id_hoa_don
              WHERE hd.trang_thai IN  (3,10) 
              GROUP BY   th.ten,  th.ma
            )
            SELECT ten,ma, SUM(tongTien) AS tongTien
            FROM DoanhThuLoai GROUP BY ten,ma ORDER BY tongTien DESC;
                """, nativeQuery = true)
    List<AdminThongKeLoaiResponse> doanhThuTheoLoai();//


    @Query(value = """
            SELECT SUM(hd.tong_tien) as tongTien
            FROM  datn.hoa_don hd
            where hd.trang_thai in (3,10)  and hd.hinh_thuc_giao_hang =:idPhuongThuc
            """, nativeQuery = true)
    Integer tongDoanhThuByPhuongThuc(Integer idPhuongThuc); //


    @Query(value = """
            WITH DoanhThuSanPham AS (
              SELECT
                sp.ma as ma,
                sp.ten as ten,
                SUM(hd.tong_tien) AS tongTien
              FROM datn.san_pham_chi_tiet spct
              JOIN datn.san_pham sp ON spct.id_san_pham = sp.id
              JOIN datn.hoa_don_chi_tiet hdct ON hdct.id_san_pham_chi_tiet = spct.id
              JOIN datn.hoa_don hd ON hd.id = hdct.id_hoa_don
              WHERE hd.trang_thai IN (3,10) and hd.hinh_thuc_giao_hang =:idPhuongThuc
             GROUP BY sp.ma, sp.ten  )
            SELECT * FROM DoanhThuSanPham ORDER BY tongTien DESC LIMIT 5;
              """, nativeQuery = true)
    List<AdminThongKeSanPhamCaoResponse> sanPhamDoanhThuCaoNhatByPhuongThuc(Integer idPhuongThuc); //

    @Query(value = """
            WITH DoanhThuSanPham AS (
              SELECT
                sp.ma as ma,
                sp.ten as ten,
                SUM(hd.tong_tien) AS tongTien
              FROM datn.san_pham_chi_tiet spct
              JOIN datn.san_pham sp ON spct.id_san_pham = sp.id
              JOIN datn.hoa_don_chi_tiet hdct ON hdct.id_san_pham_chi_tiet = spct.id
              JOIN datn.hoa_don hd ON hd.id = hdct.id_hoa_don
              WHERE hd.trang_thai IN  (3,10)  and hd.hinh_thuc_giao_hang =:idPhuongThuc
            GROUP BY sp.ma, sp.ten )
            SELECT * FROM DoanhThuSanPham ORDER BY tongTien  LIMIT 5;
              """, nativeQuery = true)
    List<AdminThongKeSanPhamThapResponse> sanPhamDoanhThuThapNhatByPhuongThuc(Integer idPhuongThuc);//

    @Query(value = """
            SELECT  MONTH(hd.ngay_tao) AS thang, SUM(hd.tong_tien) AS tongTien,YEAR(CURDATE()) as nam
            FROM datn.hoa_don hd  where year(hd.ngay_tao)=YEAR(CURDATE()) and hd.trang_thai IN  (3,10) and hd.hinh_thuc_giao_hang =:idPhuongThuc
            GROUP BY MONTH(hd.ngay_tao) ORDER BY MONTH(hd.ngay_tao) ;
              """, nativeQuery = true)
    List<AdminThongKeThangResponse> doanhThuTheoThangByPhuongThuc(Integer idPhuongThuc);//

    @Query(value = """
            SELECT  MONTH(hd.ngay_tao) AS thang, SUM(hd.tong_tien) AS tongTien,YEAR(CURDATE()) -1 as nam
            FROM datn.hoa_don hd \s
            WHERE YEAR(hd.ngay_tao) = YEAR(CURDATE()) -1  and hd.trang_thai IN  (3,10) and hd.hinh_thuc_giao_hang =:idPhuongThuc
            GROUP BY MONTH(hd.ngay_tao)\s
            ORDER BY MONTH(hd.ngay_tao);
              """, nativeQuery = true)
    List<AdminThongKeThangNamTruocResponse> doanhThuTheoThangNamTruocByPhuongThuc(Integer idPhuongThuc);//

    @Query(value = """
            WITH DoanhThuThuongHieu AS (
               SELECT
                  th.ten AS ten,
                   th.ma as ma,
                  SUM(hd.tong_tien) AS tongTien
               FROM datn.san_pham_chi_tiet spct
               JOIN datn.san_pham sp ON spct.id_san_pham = sp.id
               JOIN datn.thuong_hieu th ON sp.id_thuong_hieu = th.id
               JOIN datn.hoa_don_chi_tiet hdct ON hdct.id_san_pham_chi_tiet = spct.id
               JOIN datn.hoa_don hd ON hd.id = hdct.id_hoa_don
               WHERE hd.trang_thai IN  (3,10) and hd.hinh_thuc_giao_hang =:idPhuongThuc
               GROUP BY   th.ten,  th.ma
            )
            SELECT ten,ma, SUM(tongTien) AS tongTien
            FROM DoanhThuThuongHieu GROUP BY ten,ma ORDER BY tongTien DESC;
              """, nativeQuery = true)
    List<AdminThongKeThuongHieuResponse> doanhThuTheoThuongHieuByPhuongThuc(Integer idPhuongThuc);//

    @Query(value = """
            WITH DoanhThuLoai AS (
              SELECT
                th.ten AS ten,
                th.ma as ma,
                SUM(hd.tong_tien) AS tongTien
              FROM datn.san_pham_chi_tiet spct
              JOIN datn.san_pham sp ON spct.id_san_pham = sp.id
              JOIN datn.loai th ON sp.id_loai = th.id
              JOIN datn.hoa_don_chi_tiet hdct ON hdct.id_san_pham_chi_tiet = spct.id
              JOIN datn.hoa_don hd ON hd.id = hdct.id_hoa_don
              WHERE hd.trang_thai IN  (3,10) and hd.hinh_thuc_giao_hang =:idPhuongThuc
              GROUP BY   th.ten,  th.ma
            )
            SELECT ten,ma, SUM(tongTien) AS tongTien
            FROM DoanhThuLoai GROUP BY ten,ma ORDER BY tongTien DESC;
                """, nativeQuery = true)
    List<AdminThongKeLoaiResponse> doanhThuTheoLoaiByPhuongThuc(Integer idPhuongThuc);//


    // tổng doanh thu theo sản phẩm
    @Query(value = """
            SELECT SUM(hd.tong_tien) as tongTien
                                            FROM datn.san_pham_chi_tiet spct
                                            JOIN datn.san_pham sp ON spct.id_san_pham = sp.id
                                            JOIN datn.hoa_don_chi_tiet hdct ON hdct.id_san_pham_chi_tiet = spct.id
                                            JOIN datn.hoa_don hd ON hd.id = hdct.id_hoa_don
                                            WHERE hd.trang_thai IN  (3,10) AND spct.id =:id AND YEAR(hd.ngay_tao) =:year 
              """, nativeQuery = true)
    Integer tongDoanhThuBySanPham(Integer id, String year);

    // bảng top 5 sp có doanh thu cao và thấp nhất
    @Query(value = """
            WITH DoanhThuSanPham AS (
              SELECT
                sp.ma as ma,
                sp.ten as ten,
                SUM(hd.tong_tien) AS tongTien
              FROM datn.san_pham_chi_tiet spct
              JOIN datn.san_pham sp ON spct.id_san_pham = sp.id
              JOIN datn.hoa_don_chi_tiet hdct ON hdct.id_san_pham_chi_tiet = spct.id
              JOIN datn.hoa_don hd ON hd.id = hdct.id_hoa_don
              WHERE hd.trang_thai IN  (3,10) AND spct.id =:id AND YEAR(hd.ngay_tao) =:year  
             GROUP BY sp.ma, sp.ten )
            SELECT * FROM DoanhThuSanPham ORDER BY tongTien DESC LIMIT 5;
              """, nativeQuery = true)
    List<AdminThongKeSanPhamCaoResponse> sanPhamDoanhThuCaoNhatBySanPham(Integer id, String year);

    @Query(value = """
            WITH DoanhThuSanPham AS (
              SELECT
                sp.ma as ma,
                sp.ten as ten,
                SUM(hd.tong_tien) AS tongTien
              FROM datn.san_pham_chi_tiet spct
              JOIN datn.san_pham sp ON spct.id_san_pham = sp.id
              JOIN datn.hoa_don_chi_tiet hdct ON hdct.id_san_pham_chi_tiet = spct.id
              JOIN datn.hoa_don hd ON hd.id = hdct.id_hoa_don
              WHERE hd.trang_thai IN  (3,10)  AND spct.id =:id AND YEAR(hd.ngay_tao) =:year  
           GROUP BY sp.ma, sp.ten  )
            SELECT * FROM DoanhThuSanPham ORDER BY tongTien  LIMIT 5;
              """, nativeQuery = true)
    List<AdminThongKeSanPhamThapResponse> sanPhamDoanhThuThapNhatBySanPham(Integer id, String year);

    @Query(value = """
            SELECT  MONTH(hd.ngay_tao) AS thang, SUM(hd.tong_tien) AS tongTien,YEAR(hd.ngay_tao) as nam
            FROM datn.san_pham_chi_tiet spct
            JOIN datn.san_pham sp ON spct.id_san_pham = sp.id
            JOIN datn.hoa_don_chi_tiet hdct ON hdct.id_san_pham_chi_tiet = spct.id
            JOIN datn.hoa_don hd ON hd.id = hdct.id_hoa_don\s
             WHERE spct.id =:id and hd.trang_thai IN  (3,10) AND YEAR(hd.ngay_tao) =:year  
            GROUP BY MONTH(hd.ngay_tao),YEAR(hd.ngay_tao) ORDER BY MONTH(hd.ngay_tao)
                """, nativeQuery = true)
    List<AdminThongKeThangResponse> doanhThuTheoThangBySanPham(Integer id, String year);

    @Query(value = """
                        
             SELECT  MONTH(hd.ngay_tao) AS thang, SUM(hd.tong_tien) AS tongTien,YEAR(CURDATE())-1 as nam
            FROM datn.san_pham_chi_tiet spct
            JOIN datn.san_pham sp ON spct.id_san_pham = sp.id
            JOIN datn.hoa_don_chi_tiet hdct ON hdct.id_san_pham_chi_tiet = spct.id
            JOIN datn.hoa_don hd ON hd.id = hdct.id_hoa_don
            WHERE spct.id =:id and YEAR(hd.ngay_tao) = YEAR(CURDATE()) -1  and hd.trang_thai IN  (3,10) 
            GROUP BY MONTH(hd.ngay_tao) ORDER BY MONTH(hd.ngay_tao)
                
              """, nativeQuery = true)
    List<AdminThongKeThangNamTruocResponse> doanhThuTheoThangNamTruocBySanPham(Integer id);

    @Query(value = """
            WITH DoanhThuThuongHieu AS (
               SELECT
                  th.ten AS ten,
                   th.ma as ma,
                  SUM(hd.tong_tien) AS tongTien
               FROM datn.san_pham_chi_tiet spct
               JOIN datn.san_pham sp ON spct.id_san_pham = sp.id
               JOIN datn.thuong_hieu th ON sp.id_thuong_hieu = th.id
               JOIN datn.hoa_don_chi_tiet hdct ON hdct.id_san_pham_chi_tiet = spct.id
               JOIN datn.hoa_don hd ON hd.id = hdct.id_hoa_don
               WHERE hd.trang_thai IN  (3,10)  AND spct.id =:id AND YEAR(hd.ngay_tao) =:year  
               GROUP BY   th.ten,th.ma
            )
            SELECT ten,ma, SUM(tongTien) AS tongTien
            FROM DoanhThuThuongHieu GROUP BY ten ORDER BY tongTien DESC;
              """, nativeQuery = true)
    List<AdminThongKeThuongHieuResponse> doanhThuTheoThuongHieuBySanPham(Integer id, String year);

    @Query(value = """
            WITH DoanhThuLoai AS (
              SELECT\s
                th.ten AS ten,
                th.ma as ma,
                SUM(hd.tong_tien) AS tongTien
              FROM datn.san_pham_chi_tiet spct
              JOIN datn.san_pham sp ON spct.id_san_pham = sp.id
              JOIN datn.loai th ON sp.id_loai = th.id
              JOIN datn.hoa_don_chi_tiet hdct ON hdct.id_san_pham_chi_tiet = spct.id
              JOIN datn.hoa_don hd ON hd.id = hdct.id_hoa_don
              WHERE hd.trang_thai IN  (3,10)  AND spct.id =:id AND YEAR(hd.ngay_tao) =:year 
              GROUP BY   th.ten,th.ma
            )
            SELECT ten,ma, SUM(tongTien) AS tongTien
            FROM DoanhThuLoai GROUP BY ten ORDER BY tongTien DESC;
                """, nativeQuery = true)
    List<AdminThongKeLoaiResponse> doanhThuTheoLoaiBySanPham(Integer id, String year);


    // tổng doanh thu theo thương hiệu
    @Query(value = """
            SELECT SUM(hd.tong_tien) as tongTien
                                    FROM datn.san_pham_chi_tiet spct
                                    JOIN datn.san_pham sp ON spct.id_san_pham = sp.id
                                    JOIN datn.thuong_hieu th ON sp.id_thuong_hieu = th.id
                                    JOIN datn.hoa_don_chi_tiet hdct ON hdct.id_san_pham_chi_tiet = spct.id
                                    JOIN datn.hoa_don hd ON hd.id = hdct.id_hoa_don
                                    WHERE hd.trang_thai IN  (3,10) AND th.id =:id AND YEAR(hd.ngay_tao) =:year 
              """, nativeQuery = true)
    Integer tongDoanhThuByThuongHieu(@Param("id") Integer id, String year);

    // bảng top 5 sp có doanh thu cao và thấp nhất
    @Query(value = """
            WITH DoanhThuSanPham AS (
              SELECT
                sp.ma as ma,
                sp.ten as ten,
                SUM(hd.tong_tien) AS tongTien
              FROM datn.san_pham_chi_tiet spct
              JOIN datn.san_pham sp ON spct.id_san_pham = sp.id
              JOIN datn.thuong_hieu th ON sp.id_thuong_hieu = th.id
              JOIN datn.hoa_don_chi_tiet hdct ON hdct.id_san_pham_chi_tiet = spct.id
              JOIN datn.hoa_don hd ON hd.id = hdct.id_hoa_don
              WHERE hd.trang_thai IN  (3,10)  AND th.id =:id AND YEAR(hd.ngay_tao) =:year 
            GROUP BY sp.ma, sp.ten )
            SELECT * FROM DoanhThuSanPham ORDER BY tongTien DESC LIMIT 5;
              """, nativeQuery = true)
    List<AdminThongKeSanPhamCaoResponse> sanPhamDoanhThuCaoNhatByThuongHieu(Integer id, String year);

    @Query(value = """
            WITH DoanhThuSanPham AS (
              SELECT
                sp.ma as ma,
                sp.ten as ten,
                SUM(hd.tong_tien) AS tongTien
              FROM datn.san_pham_chi_tiet spct
              JOIN datn.san_pham sp ON spct.id_san_pham = sp.id
              JOIN datn.thuong_hieu th ON sp.id_thuong_hieu = th.id
              JOIN datn.hoa_don_chi_tiet hdct ON hdct.id_san_pham_chi_tiet = spct.id
              JOIN datn.hoa_don hd ON hd.id = hdct.id_hoa_don
              WHERE hd.trang_thai IN  (3,10)   AND th.id =:id AND YEAR(hd.ngay_tao) =:year 
           GROUP BY sp.ma, sp.ten )
            SELECT * FROM DoanhThuSanPham ORDER BY tongTien  LIMIT 5;
              """, nativeQuery = true)
    List<AdminThongKeSanPhamThapResponse> sanPhamDoanhThuThapNhatByThuongHieu(Integer id, String year);

    @Query(value = """
            SELECT  MONTH(hd.ngay_tao) AS thang, SUM(hd.tong_tien) AS tongTien,YEAR(hd.ngay_tao) as nam
            FROM datn.san_pham_chi_tiet spct
            JOIN datn.san_pham sp ON spct.id_san_pham = sp.id
            JOIN datn.thuong_hieu th ON sp.id_thuong_hieu = th.id
            JOIN datn.hoa_don_chi_tiet hdct ON hdct.id_san_pham_chi_tiet = spct.id
            JOIN datn.hoa_don hd ON hd.id = hdct.id_hoa_don\s
            WHERE th.id =:id and hd.trang_thai IN  (3,10) AND YEAR(hd.ngay_tao) =:year 
            GROUP BY MONTH(hd.ngay_tao),YEAR(hd.ngay_tao) ORDER BY MONTH(hd.ngay_tao)
               """, nativeQuery = true)
    List<AdminThongKeThangResponse> doanhThuTheoThangByThuongHieu(Integer id, String year);

    @Query(value = """
                        
             SELECT  MONTH(hd.ngay_tao) AS thang, SUM(hd.tong_tien) AS tongTien,YEAR(CURDATE())-1 as nam
            FROM datn.san_pham_chi_tiet spct
            JOIN datn.san_pham sp ON spct.id_san_pham = sp.id
            JOIN datn.thuong_hieu th ON sp.id_thuong_hieu = th.id
            JOIN datn.hoa_don_chi_tiet hdct ON hdct.id_san_pham_chi_tiet = spct.id
            JOIN datn.hoa_don hd ON hd.id = hdct.id_hoa_don
            WHERE th.id =:id and YEAR(hd.ngay_tao) = YEAR(CURDATE())-1  and hd.trang_thai IN (3,10)
            GROUP BY MONTH(hd.ngay_tao) ORDER BY MONTH(hd.ngay_tao)
                
              """, nativeQuery = true)
    List<AdminThongKeThangNamTruocResponse> doanhThuTheoThangNamTruocByThuongHieu(Integer id);

    @Query(value = """
            WITH DoanhThuThuongHieu AS (
               SELECT
                  th.ten AS ten,
                   th.ma as ma,
                  SUM(hd.tong_tien) AS tongTien
               FROM datn.san_pham_chi_tiet spct
               JOIN datn.san_pham sp ON spct.id_san_pham = sp.id
               JOIN datn.thuong_hieu th ON sp.id_thuong_hieu = th.id
               JOIN datn.hoa_don_chi_tiet hdct ON hdct.id_san_pham_chi_tiet = spct.id
               JOIN datn.hoa_don hd ON hd.id = hdct.id_hoa_don
               WHERE hd.trang_thai IN  (3,10)  AND th.id =:id  AND YEAR(hd.ngay_tao) =:year 
               GROUP BY   th.ten,th.ma
            )
            SELECT ten,ma, SUM(tongTien) AS tongTien
            FROM DoanhThuThuongHieu GROUP BY ten,ma ORDER BY tongTien DESC;
              """, nativeQuery = true)
    List<AdminThongKeThuongHieuResponse> doanhThuTheoThuongHieuByThuongHieu(Integer id, String year);

    @Query(value = """
            WITH DoanhThuLoai AS (
                                    SELECT
                                         l.ten AS ten,
                                         l.ma as ma,
                                         SUM(hd.tong_tien) AS tongTien
                                    FROM datn.san_pham_chi_tiet spct
                                    JOIN datn.san_pham sp ON spct.id_san_pham = sp.id
                                    JOIN datn.thuong_hieu th ON sp.id_thuong_hieu = th.id
                                    JOIN datn.loai l ON sp.id_loai = l.id
                                    JOIN datn.hoa_don_chi_tiet hdct ON hdct.id_san_pham_chi_tiet = spct.id
                                    JOIN datn.hoa_don hd ON hd.id = hdct.id_hoa_don
                                    WHERE hd.trang_thai IN  (3,10)  AND th.id =:id AND YEAR(hd.ngay_tao) =:year 
                                    GROUP BY   l.ten,l.ma
                                  )
            SELECT ten,ma, SUM(tongTien) AS tongTien
            FROM DoanhThuLoai GROUP BY ten,ma ORDER BY tongTien DESC;
               """, nativeQuery = true)
    List<AdminThongKeLoaiResponse> doanhThuTheoLoaiByThuongHieu(Integer id, String year);


    // tổng doanh thu theo loai
    @Query(value = """
            SELECT SUM(hd.tong_tien) as tongTien
                                    FROM datn.san_pham_chi_tiet spct
                                    JOIN datn.san_pham sp ON spct.id_san_pham = sp.id
                                    JOIN datn.loai th ON sp.id_loai = th.id
                                    JOIN datn.hoa_don_chi_tiet hdct ON hdct.id_san_pham_chi_tiet = spct.id
                                    JOIN datn.hoa_don hd ON hd.id = hdct.id_hoa_don
                                    WHERE hd.trang_thai IN  (3,10) AND th.id =:id AND YEAR(hd.ngay_tao) =:year
              """, nativeQuery = true)
    Integer tongDoanhThuByLoai(@Param("id") Integer id, String year);

    // bảng top 5 sp có doanh thu cao và thấp nhất
    @Query(value = """
            WITH DoanhThuSanPham AS (
              SELECT
                sp.ma as ma,
                sp.ten as ten,
                SUM(hd.tong_tien) AS tongTien
              FROM datn.san_pham_chi_tiet spct
              JOIN datn.san_pham sp ON spct.id_san_pham = sp.id
               JOIN datn.loai th ON sp.id_loai = th.id
              JOIN datn.hoa_don_chi_tiet hdct ON hdct.id_san_pham_chi_tiet = spct.id
              JOIN datn.hoa_don hd ON hd.id = hdct.id_hoa_don
              WHERE hd.trang_thai IN  (3,10) AND th.id =:id AND YEAR(hd.ngay_tao) =:year
           GROUP BY sp.ma, sp.ten   )
            SELECT * FROM DoanhThuSanPham ORDER BY tongTien DESC LIMIT 5;
              """, nativeQuery = true)
    List<AdminThongKeSanPhamCaoResponse> sanPhamDoanhThuCaoNhatByLoai(Integer id, String year);

    @Query(value = """
            WITH DoanhThuSanPham AS (
              SELECT
                sp.ma as ma,
                sp.ten as ten,
                SUM(hd.tong_tien) AS tongTien
              FROM datn.san_pham_chi_tiet spct
              JOIN datn.san_pham sp ON spct.id_san_pham = sp.id
              JOIN datn.loai th ON sp.id_loai = th.id
              JOIN datn.hoa_don_chi_tiet hdct ON hdct.id_san_pham_chi_tiet = spct.id
              JOIN datn.hoa_don hd ON hd.id = hdct.id_hoa_don
              WHERE hd.trang_thai IN  (3,10)   AND th.id =:id AND YEAR(hd.ngay_tao) =:year
            GROUP BY sp.ma, sp.ten  )
            SELECT * FROM DoanhThuSanPham ORDER BY tongTien  LIMIT 5;
              """, nativeQuery = true)
    List<AdminThongKeSanPhamThapResponse> sanPhamDoanhThuThapNhatByLoai(Integer id, String year);

    @Query(value = """
            SELECT  MONTH(hd.ngay_tao) AS thang, SUM(hd.tong_tien) AS tongTien,YEAR(hd.ngay_tao) as nam
            FROM datn.san_pham_chi_tiet spct
            JOIN datn.san_pham sp ON spct.id_san_pham = sp.id
            JOIN datn.loai th ON sp.id_loai = th.id
            JOIN datn.hoa_don_chi_tiet hdct ON hdct.id_san_pham_chi_tiet = spct.id
            JOIN datn.hoa_don hd ON hd.id = hdct.id_hoa_don\s
            WHERE th.id =:id and hd.trang_thai IN  (3,10) AND YEAR(hd.ngay_tao) =:year
            GROUP BY MONTH(hd.ngay_tao),YEAR(hd.ngay_tao) ORDER BY MONTH(hd.ngay_tao)
               """, nativeQuery = true)
    List<AdminThongKeThangResponse> doanhThuTheoThangByLoai(Integer id, String year);

    @Query(value = """
                        
             SELECT  MONTH(hd.ngay_tao) AS thang, SUM(hd.tong_tien) AS tongTien,YEAR(CURDATE()) -1 as nam
            FROM datn.san_pham_chi_tiet spct
            JOIN datn.san_pham sp ON spct.id_san_pham = sp.id
            JOIN datn.loai th ON sp.id_loai = th.id
            JOIN datn.hoa_don_chi_tiet hdct ON hdct.id_san_pham_chi_tiet = spct.id
            JOIN datn.hoa_don hd ON hd.id = hdct.id_hoa_don
            WHERE th.id =:id and YEAR(hd.ngay_tao) = YEAR(CURDATE()) -1  and hd.trang_thai IN  (3,10)
            GROUP BY MONTH(hd.ngay_tao) ORDER BY MONTH(hd.ngay_tao)
                
              """, nativeQuery = true)
    List<AdminThongKeThangNamTruocResponse> doanhThuTheoThangNamTruocByLoai(Integer id);

    @Query(value = """
            WITH DoanhThuThuongHieu AS (
               SELECT
                  th.ten AS ten,
                   th.ma as ma,
                  SUM(hd.tong_tien) AS tongTien
               FROM datn.san_pham_chi_tiet spct
               JOIN datn.san_pham sp ON spct.id_san_pham = sp.id
               JOIN datn.thuong_hieu th ON sp.id_thuong_hieu = th.id
               JOIN datn.loai l ON sp.id_loai = l.id
               JOIN datn.hoa_don_chi_tiet hdct ON hdct.id_san_pham_chi_tiet = spct.id
               JOIN datn.hoa_don hd ON hd.id = hdct.id_hoa_don
               WHERE hd.trang_thai IN  (3,10)  AND l.id =:id AND YEAR(hd.ngay_tao) =:year
               GROUP BY   th.ten,th.ma
            )
            SELECT ten,ma, SUM(tongTien) AS tongTien
            FROM DoanhThuThuongHieu GROUP BY ten,ma ORDER BY tongTien DESC;
              """, nativeQuery = true)
    List<AdminThongKeThuongHieuResponse> doanhThuTheoThuongHieuByLoai(Integer id, String year);

    @Query(value = """
            WITH DoanhThuThuongHieu AS (
                                        SELECT
                                            l.ten AS ten,
                                            l.ma as ma,
                                            SUM(hd.tong_tien) AS tongTien
                                        FROM datn.san_pham_chi_tiet spct
                                        JOIN datn.san_pham sp ON spct.id_san_pham = sp.id
                                        JOIN datn.thuong_hieu th ON sp.id_thuong_hieu = th.id
                                        JOIN datn.loai l ON sp.id_loai = l.id
                                        JOIN datn.hoa_don_chi_tiet hdct ON hdct.id_san_pham_chi_tiet = spct.id
                                        JOIN datn.hoa_don hd ON hd.id = hdct.id_hoa_don
                                        WHERE hd.trang_thai IN  (3,10)  AND l.id =:id AND YEAR(hd.ngay_tao) =:year\s
                                        GROUP BY   l.ten,l.ma
                                        )
            SELECT ten,ma, SUM(tongTien) AS tongTien
            FROM DoanhThuThuongHieu GROUP BY ten,ma ORDER BY tongTien DESC;
                """, nativeQuery = true)
    List<AdminThongKeLoaiResponse> doanhThuTheoLoaiByLoai(Integer id, String year);

    // doanh số theo năm

    @Query(value = """
            SELECT SUM(hd.tong_tien) as tongTien
            FROM  datn.hoa_don hd
            where hd.trang_thai in (3,10) AND YEAR(hd.ngay_tao) =:year
            """, nativeQuery = true)
    Integer tongDoanhThuByNam(String year);

    @Query(value = """
            WITH DoanhThuSanPham AS (
                                       SELECT\s
                                            sp.ma as ma,
                                            sp.ten as ten,
                                            SUM(hd.tong_tien) AS tongTien
                                       FROM datn.san_pham_chi_tiet spct
                                       JOIN datn.san_pham sp ON spct.id_san_pham = sp.id
                                       JOIN datn.hoa_don_chi_tiet hdct ON hdct.id_san_pham_chi_tiet = spct.id
                                       JOIN datn.hoa_don hd ON hd.id = hdct.id_hoa_don
                                       WHERE hd.trang_thai IN  (3,10) AND  YEAR(hd.ngay_tao)  =:year
                                      GROUP BY sp.ma, sp.ten )
            SELECT * FROM DoanhThuSanPham ORDER BY tongTien DESC LIMIT 5;
             """, nativeQuery = true)
    List<AdminThongKeSanPhamCaoResponse> sanPhamDoanhThuCaoNhatByNam(String year);

    @Query(value = """
            WITH DoanhThuSanPham AS (
                                       SELECT\s
                                            sp.ma as ma,
                                            sp.ten as ten,
                                            SUM(hd.tong_tien) AS tongTien
                                       FROM datn.san_pham_chi_tiet spct
                                       JOIN datn.san_pham sp ON spct.id_san_pham = sp.id
                                       JOIN datn.hoa_don_chi_tiet hdct ON hdct.id_san_pham_chi_tiet = spct.id
                                       JOIN datn.hoa_don hd ON hd.id = hdct.id_hoa_don
                                       WHERE hd.trang_thai IN  (3,10) AND  YEAR(hd.ngay_tao)  =:year
                                     GROUP BY sp.ma, sp.ten  )
            SELECT * FROM DoanhThuSanPham ORDER BY tongTien DESC LIMIT 5;
             """, nativeQuery = true)
    List<AdminThongKeSanPhamThapResponse> sanPhamDoanhThuThapNhatByNam(String year);

    @Query(value = """
            SELECT    MONTH(hd.ngay_tao) AS thang,  SUM(hd.tong_tien) AS tongTien,YEAR(hd.ngay_tao) as nam
            FROM datn.hoa_don hd  WHERE	  YEAR(hd.ngay_tao) =:year and hd.trang_thai IN  (3,10)
            GROUP BY MONTH(hd.ngay_tao), YEAR(hd.ngay_tao) ORDER BY MONTH(hd.ngay_tao) ;
              """, nativeQuery = true)
    List<AdminThongKeThangResponse> doanhThuTheoThangByNam(String year);

    @Query(value = """
                        
             SELECT  MONTH(hd.ngay_tao) AS thang, SUM(hd.tong_tien) AS tongTien,YEAR(hd.ngay_tao) as nam
            FROM datn.hoa_don hd  WHERE	  YEAR(hd.ngay_tao) =:year and hd.trang_thai IN  (3,10)
            GROUP BY MONTH(hd.ngay_tao),YEAR(hd.ngay_tao) ORDER BY MONTH(hd.ngay_tao) ;
                    
              """, nativeQuery = true)
    List<AdminThongKeThangNamTruocResponse> doanhThuTheoThangNamHienTaiByNam(String year);

    @Query(value = """
            WITH DoanhThuSanPham AS (
                                      SELECT\s
                                           th.ten AS ten,
                                           th.ma AS ma,
                                           SUM(hd.tong_tien) AS tongTien
                                      FROM datn.san_pham_chi_tiet spct
                                      JOIN datn.san_pham sp ON spct.id_san_pham = sp.id
                                      JOIN datn.loai th ON sp.id_loai = th.id
                                      JOIN datn.hoa_don_chi_tiet hdct ON hdct.id_san_pham_chi_tiet = spct.id
                                      JOIN datn.hoa_don hd ON hd.id = hdct.id_hoa_don
                                      WHERE hd.trang_thai IN  (3,10) AND  YEAR(hd.ngay_tao) =:year
                                      GROUP BY   th.ten,th.ma
                                     )
            SELECT ten,ma, SUM(tongTien) AS tongTien
            FROM DoanhThuSanPham GROUP BY ten,ma ORDER BY tongTien DESC;
              """, nativeQuery = true)
    List<AdminThongKeLoaiResponse> doanhThuTheoLoaiByNam(String year);

    @Query(value = """
            WITH DoanhThuSanPham AS (
                                      SELECT\s
                                           th.ten AS ten,
                                           th.ma AS ma,
                                           SUM(hd.tong_tien) AS tongTien
                                      FROM datn.san_pham_chi_tiet spct
                                      JOIN datn.san_pham sp ON spct.id_san_pham = sp.id
                                      JOIN datn.thuong_hieu th ON sp.id_thuong_hieu = th.id
                                      JOIN datn.hoa_don_chi_tiet hdct ON hdct.id_san_pham_chi_tiet = spct.id
                                      JOIN datn.hoa_don hd ON hd.id = hdct.id_hoa_don
                                      WHERE hd.trang_thai IN  (3,10) AND  YEAR(hd.ngay_tao) =:year
                                      GROUP BY   th.ten,th.ma
                                     )
            SELECT ten,ma, SUM(tongTien) AS tongTien
            FROM DoanhThuSanPham GROUP BY ten,ma ORDER BY tongTien DESC;
              """, nativeQuery = true)
    List<AdminThongKeThuongHieuResponse> doanhThuTheoThuongHieuByNam(String year);


    // tổng doanh thu theo tháng
    @Query(value = """
            SELECT SUM(hd.tong_tien) as tongTien
            FROM datn.hoa_don hd
            WHERE hd.trang_thai IN  (3,10) AND hd.ngay_tao BETWEEN :startDate AND :endDate
                        """, nativeQuery = true)
    Integer tongDoanhThuTheoThang(String startDate, String endDate);


    @Query(value = """
            WITH DoanhThuSanPham AS (
              SELECT\s
                sp.ma as ma,
                sp.ten as ten,
                SUM(hd.tong_tien) AS tongTien
              FROM datn.san_pham_chi_tiet spct
              JOIN datn.san_pham sp ON spct.id_san_pham = sp.id
              JOIN datn.hoa_don_chi_tiet hdct ON hdct.id_san_pham_chi_tiet = spct.id
              JOIN datn.hoa_don hd ON hd.id = hdct.id_hoa_don
              WHERE hd.trang_thai IN  (3,10) AND hd.ngay_tao BETWEEN :startDate AND :endDate
            GROUP BY sp.ma, sp.ten )
            SELECT * FROM DoanhThuSanPham ORDER BY tongTien DESC LIMIT 5;                      
                       """, nativeQuery = true)
    List<AdminThongKeSanPhamCaoResponse> sanPhamDoanhThuCaoNhatByThang(String startDate, String endDate);

    @Query(value = """
            WITH DoanhThuSanPham AS (
              SELECT\s
                sp.ma as ma,
                sp.ten as ten,
                SUM(hd.tong_tien) AS tongTien
              FROM datn.san_pham_chi_tiet spct
              JOIN datn.san_pham sp ON spct.id_san_pham = sp.id
              JOIN datn.hoa_don_chi_tiet hdct ON hdct.id_san_pham_chi_tiet = spct.id
              JOIN datn.hoa_don hd ON hd.id = hdct.id_hoa_don
              WHERE hd.trang_thai IN  (3,10) AND hd.ngay_tao BETWEEN :startDate AND :endDate
             GROUP BY sp.ma, sp.ten)
            SELECT * FROM DoanhThuSanPham ORDER BY tongTien  LIMIT 5;                      
                       """, nativeQuery = true)
    List<AdminThongKeSanPhamThapResponse> sanPhamDoanhThuThapNhatByThang(String startDate, String endDate);

    @Query(value = """
            WITH DoanhThuSanPham AS (
              SELECT\s
                th.ten AS ten,
                th.ma as ma,
                SUM(hd.tong_tien) AS tongTien
              FROM datn.san_pham_chi_tiet spct
              JOIN datn.san_pham sp ON spct.id_san_pham = sp.id
              JOIN datn.loai th ON sp.id_loai = th.id
              JOIN datn.hoa_don_chi_tiet hdct ON hdct.id_san_pham_chi_tiet = spct.id
              JOIN datn.hoa_don hd ON hd.id = hdct.id_hoa_don
              WHERE hd.trang_thai IN  (3,10) AND hd.ngay_tao BETWEEN :startDate AND :endDate
              GROUP BY   th.ten , th.ma\s
            )
            SELECT ten,ma,SUM(tongTien) AS tongTien
            FROM DoanhThuSanPham GROUP BY ten,ma ORDER BY tongTien DESC;                      
                       """, nativeQuery = true)
    List<AdminThongKeLoaiResponse> doanhThuTheoLoaiByThang(String startDate, String endDate);

    @Query(value = """
            WITH DoanhThuSanPham AS (
              SELECT\s
                th.ten AS ten,
                th.ma as ma,
                SUM(hd.tong_tien) AS tongTien
              FROM datn.san_pham_chi_tiet spct
              JOIN datn.san_pham sp ON spct.id_san_pham = sp.id
              JOIN datn.thuong_hieu th ON sp.id_thuong_hieu = th.id
              JOIN datn.hoa_don_chi_tiet hdct ON hdct.id_san_pham_chi_tiet = spct.id
              JOIN datn.hoa_don hd ON hd.id = hdct.id_hoa_don
              WHERE hd.trang_thai IN  (3,10) AND hd.ngay_tao BETWEEN :startDate AND :endDate
              GROUP BY   th.ten ,th.ma\s
            )
            SELECT ten,ma, SUM(tongTien) AS tongTien
            FROM DoanhThuSanPham GROUP BY ten,ma ORDER BY tongTien DESC;                     
                      """, nativeQuery = true)
    List<AdminThongKeThuongHieuResponse> doanhThuTheoThuongHieuByThang(String startDate, String endDate);

    @Query(value = """
            SELECT    MONTH(hd.ngay_tao) AS thang,  SUM(hd.tong_tien) AS tongTien,YEAR(hd.ngay_tao ) as nam
            FROM datn.hoa_don hd  WHERE	 hd.ngay_tao BETWEEN :startDate AND :endDate and hd.trang_thai IN  (3,10)
            GROUP BY MONTH(hd.ngay_tao),YEAR(hd.ngay_tao ) ORDER BY MONTH(hd.ngay_tao) ;                   
                    """, nativeQuery = true)
    List<AdminThongKeThangResponse> doanhThuTheoThangByThang(String startDate, String endDate);

    // hoan tien
    @Query(value = """
            select  sum(v.don_gia) from datn.hoa_don_chi_tiet v 
            join datn.hoa_don hd on hd.id = v.id_hoa_don 
            where v.trang_thai = 8  and   YEAR(hd.ngay_tao) =:year or (hd.ngay_tao BETWEEN :startDate AND :endDate)
             """, nativeQuery = true)
    Integer tongHoanTienByThangNam(String year, String startDate, String endDate);

    @Query(value = """
            select  sum(v.don_gia) from datn.hoa_don_chi_tiet v 
            join datn.hoa_don hd on hd.id = v.id_hoa_don where v.trang_thai = 8 
             """, nativeQuery = true)
    Integer tongHoanTien();

    @Query(value = """
            SELECT SUM(hdct.don_gia) as hoanTien
            FROM  datn.hoa_don hd
            join datn.hoa_don_chi_tiet hdct on hd.id = hdct.id_hoa_don
            where hdct.trang_thai in (8)  and hd.hinh_thuc_giao_hang =:idPhuongThuc
            """, nativeQuery = true)
    Integer tongHoanTienByPhuongThuc(Integer idPhuongThuc);

    @Query(value = """
            SELECT SUM(hdct.don_gia) as hoanTien
                                            FROM datn.san_pham_chi_tiet spct
                                            JOIN datn.san_pham sp ON spct.id_san_pham = sp.id
                                            JOIN datn.hoa_don_chi_tiet hdct ON hdct.id_san_pham_chi_tiet = spct.id
                                            JOIN datn.hoa_don hd ON hd.id = hdct.id_hoa_don
                                            WHERE hdct.trang_thai IN  (8) AND spct.id =:id AND YEAR(hd.ngay_tao) =:year 
              """, nativeQuery = true)
    Integer tongHoanTienBySanPham(Integer id, String year);

    @Query(value = """
            SELECT SUM(hdct.don_gia) as tongTien
                                    FROM datn.san_pham_chi_tiet spct
                                    JOIN datn.san_pham sp ON spct.id_san_pham = sp.id
                                    JOIN datn.thuong_hieu th ON sp.id_thuong_hieu = th.id
                                    JOIN datn.hoa_don_chi_tiet hdct ON hdct.id_san_pham_chi_tiet = spct.id
                                    JOIN datn.hoa_don hd ON hd.id = hdct.id_hoa_don
                                    WHERE hdct.trang_thai IN  (8) AND th.id =:id AND YEAR(hd.ngay_tao) =:year 
              """, nativeQuery = true)
    Integer tongHoanTienByThuongHieu(@Param("id") Integer id, String year);

    @Query(value = """
            SELECT SUM(hdct.don_gia) as tongTien
                                    FROM datn.san_pham_chi_tiet spct
                                    JOIN datn.san_pham sp ON spct.id_san_pham = sp.id
                                    JOIN datn.loai th ON sp.id_loai = th.id
                                    JOIN datn.hoa_don_chi_tiet hdct ON hdct.id_san_pham_chi_tiet = spct.id
                                    JOIN datn.hoa_don hd ON hd.id = hdct.id_hoa_don
                                    WHERE hdct.trang_thai IN  (8) AND th.id =:id AND YEAR(hd.ngay_tao) =:year
              """, nativeQuery = true)
    Integer tongHoanTienByLoai(@Param("id") Integer id, String year);


    // chiet khau
    @Query(value = """
            select  Sum(tong_tien + tien_ship - tien_sau_khi_giam_gia) as chietKhau
             from datn.hoa_don_chi_tiet v join datn.hoa_don hd on hd.id = v.id_hoa_don where hd.trang_thai in (3)  and   YEAR(hd.ngay_tao) =:year or (hd.ngay_tao BETWEEN :startDate AND :endDate)
             """, nativeQuery = true)
    Integer tongChietKhauByThangNam(String year, String startDate, String endDate);

    @Query(value = """
            select  Sum(tong_tien + tien_ship - tien_sau_khi_giam_gia) as chietKhau
             from datn.hoa_don_chi_tiet v join datn.hoa_don hd on hd.id = v.id_hoa_don where  hd.trang_thai in (3) 
             """, nativeQuery = true)
    Integer tongChietKhau();

    @Query(value = """
            SELECT Sum(tong_tien + tien_ship - tien_sau_khi_giam_gia) as chietKhau
            FROM  datn.hoa_don hd
            join datn.hoa_don_chi_tiet hdct on hd.id = hdct.id_hoa_don
            where  hd.trang_thai in (3)   and hd.hinh_thuc_giao_hang =:idPhuongThuc
            """, nativeQuery = true)
    Integer tongChietKhauByPhuongThuc(Integer idPhuongThuc);

    @Query(value = """
            SELECT Sum(tong_tien + tien_ship - tien_sau_khi_giam_gia) as chietKhau
                                            FROM datn.san_pham_chi_tiet spct
                                            JOIN datn.san_pham sp ON spct.id_san_pham = sp.id
                                            JOIN datn.hoa_don_chi_tiet hdct ON hdct.id_san_pham_chi_tiet = spct.id
                                            JOIN datn.hoa_don hd ON hd.id = hdct.id_hoa_don
                                            WHERE  hd.trang_thai in (3)  AND spct.id =:id AND YEAR(hd.ngay_tao) =:year 
              """, nativeQuery = true)
    Integer tongChietKhauBySanPham(Integer id, String year);

    @Query(value = """
            SELECT Sum(tong_tien + tien_ship - tien_sau_khi_giam_gia) as chietKhau
                                    FROM datn.san_pham_chi_tiet spct
                                    JOIN datn.san_pham sp ON spct.id_san_pham = sp.id
                                    JOIN datn.thuong_hieu th ON sp.id_thuong_hieu = th.id
                                    JOIN datn.hoa_don_chi_tiet hdct ON hdct.id_san_pham_chi_tiet = spct.id
                                    JOIN datn.hoa_don hd ON hd.id = hdct.id_hoa_don
                                    WHERE  hd.trang_thai in (3)  AND th.id =:id AND YEAR(hd.ngay_tao) =:year 
              """, nativeQuery = true)
    Integer tongChietKhauByThuongHieu(@Param("id") Integer id, String year);

    @Query(value = """
            SELECT Sum(tong_tien + tien_ship - tien_sau_khi_giam_gia) as chietKhau
                                    FROM datn.san_pham_chi_tiet spct
                                    JOIN datn.san_pham sp ON spct.id_san_pham = sp.id
                                    JOIN datn.loai th ON sp.id_loai = th.id
                                    JOIN datn.hoa_don_chi_tiet hdct ON hdct.id_san_pham_chi_tiet = spct.id
                                    JOIN datn.hoa_don hd ON hd.id = hdct.id_hoa_don
                                    WHERE  hd.trang_thai in (3)  AND th.id =:id AND YEAR(hd.ngay_tao) =:year
              """, nativeQuery = true)
    Integer tongChietKhaunByLoai(@Param("id") Integer id, String year);


    @Query(value = """
            SELECT count(hd.id) FROM datn.hoa_don hd where hd.trang_thai=3 and   YEAR(hd.ngay_tao) =:year or (hd.ngay_tao BETWEEN :startDate AND :endDate)
             """, nativeQuery = true)
    Integer tongDonhangHoanThanhByNamThang(String year, String startDate, String endDate);

    @Query(value = """
            SELECT count(hd.id) FROM datn.hoa_don hd where hd.trang_thai=3 
             """, nativeQuery = true)
    Integer tongDonhangHoanThanh();

    @Query(value = """
            SELECT count(hd.id)
            FROM  datn.hoa_don hd
            join datn.hoa_don_chi_tiet hdct on hd.id = hdct.id_hoa_don
            where  hd.trang_thai in (3)   and hd.hinh_thuc_giao_hang =:idPhuongThuc
            """, nativeQuery = true)
    Integer tongDonhangHoanThanhByPhuongThuc(Integer idPhuongThuc);

    @Query(value = """
            SELECT count(hd.id)
                                            FROM datn.san_pham_chi_tiet spct
                                            JOIN datn.san_pham sp ON spct.id_san_pham = sp.id
                                            JOIN datn.hoa_don_chi_tiet hdct ON hdct.id_san_pham_chi_tiet = spct.id
                                            JOIN datn.hoa_don hd ON hd.id = hdct.id_hoa_don
                                            WHERE  hd.trang_thai in (3)  AND spct.id =:id AND YEAR(hd.ngay_tao) =:year 
              """, nativeQuery = true)
    Integer tongDonhangHoanThanhBySanPham(Integer id, String year);

    @Query(value = """
            SELECT count(hd.id)
                                    FROM datn.san_pham_chi_tiet spct
                                    JOIN datn.san_pham sp ON spct.id_san_pham = sp.id
                                    JOIN datn.thuong_hieu th ON sp.id_thuong_hieu = th.id
                                    JOIN datn.hoa_don_chi_tiet hdct ON hdct.id_san_pham_chi_tiet = spct.id
                                    JOIN datn.hoa_don hd ON hd.id = hdct.id_hoa_don
                                    WHERE  hd.trang_thai in (3)  AND th.id =:id AND YEAR(hd.ngay_tao) =:year 
              """, nativeQuery = true)
    Integer tongDonhangHoanThanhByThuongHieu(@Param("id") Integer id, String year);

    @Query(value = """
            SELECT count(hd.id)
                                    FROM datn.san_pham_chi_tiet spct
                                    JOIN datn.san_pham sp ON spct.id_san_pham = sp.id
                                    JOIN datn.loai th ON sp.id_loai = th.id
                                    JOIN datn.hoa_don_chi_tiet hdct ON hdct.id_san_pham_chi_tiet = spct.id
                                    JOIN datn.hoa_don hd ON hd.id = hdct.id_hoa_don
                                    WHERE  hd.trang_thai in (3)  AND th.id =:id AND YEAR(hd.ngay_tao) =:year
              """, nativeQuery = true)
    Integer tongDonhangHoanThanhByLoai(@Param("id") Integer id, String year);

    @Query(value = """
            SELECT SUM(hd.tong_tien) as tongTien
            FROM  datn.hoa_don hd
            where hd.trang_thai in (3)  and DATE(hd.ngay_tao) = CURDATE();
              """, nativeQuery = true)
    Integer tongDoanhThuByDayNow();

    @Query(value = """
            SELECT SUM(hd.tong_tien) as tongTien
            FROM  datn.hoa_don hd
            where hd.trang_thai in (3)  and hd.hinh_thuc_giao_hang in (1,2) and hd.id_nguoi_tao is not null and DATE(hd.ngay_tao) = CURDATE();
            """, nativeQuery = true)
    Integer tongDoanhThuByDayNowTaiQuay();

    @Query(value = """
            SELECT SUM(hd.tong_tien) as tongTien
            FROM  datn.hoa_don hd
            where hd.trang_thai in (3)  and hd.hinh_thuc_giao_hang =2 and hd.id_nguoi_tao is null and DATE(hd.ngay_tao) = CURDATE();
            """, nativeQuery = true)
    Integer tongDoanhThuByDayOnline();

    @Query(value = """
            SELECT count(hd.id) FROM datn.hoa_don hd where hd.trang_thai=3 and DATE(hd.ngay_tao) = CURDATE();
             """, nativeQuery = true)
    Integer tongDonhangHoanThanhByDay();

    @Query(value = """
            SELECT count(hd.id) FROM datn.hoa_don_chi_tiet hd where hd.trang_thai=8 and DATE(hd.ngay_tao) = CURDATE();
             """, nativeQuery = true)
    Integer tongDonhangTraByDay();

    @Query(value = """
            SELECT count(hd.id) FROM datn.hoa_don hd where hd.trang_thai=0 and DATE(hd.ngay_tao) = CURDATE();
             """, nativeQuery = true)
    Integer tongDonhangHuyByDay();

    @Query(value = """
            SELECT
                months.month AS thang,
                COALESCE(SUM(hoa_don.tong_tien), 0) AS tong_tien_thang
            FROM
                (SELECT 1 AS month UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5 UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9 UNION SELECT 10 UNION SELECT 11 UNION SELECT 12) AS months
            LEFT JOIN
                hoa_don ON MONTH(hoa_don.ngay_tao) = months.month AND YEAR(hoa_don.ngay_tao) = YEAR(CURRENT_DATE) AND hoa_don.trang_thai IN (3, 10)
            GROUP BY
                months.month
            ORDER BY
                months.month;
            """, nativeQuery = true)
    public List<xuatBaoCaoThongKeRespone> xuatBaoCaoThongKe();
}
