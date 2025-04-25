package com.example.demo.core.Admin.repository;

import com.example.demo.core.Admin.model.response.AdminHoaDonResponse;
import com.example.demo.entity.HoaDon;
import com.example.demo.reponsitory.HoaDonReponsitory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AdHoaDonReponsitory extends HoaDonReponsitory {

    @Query(value = """
                    SELECT ROW_NUMBER() OVER(ORDER BY hd.id DESC) AS stt,
                           u.email as email ,u.sdt,hd.hinh_thuc_giao_hang as hinhThucGiaoHang, hd.ly_do as lyDo,
                           hd.ma as maHD, u.ten as nguoiTao, hd.ngay_nhan as ngayNhan, hd.ngay_ship as ngayShip, hd.ngay_tao as ngayTao,\s
                           hd.ngay_sua as ngaySua, hd.ten_nguoi_nhan as tenNguoiNhan, hd.tien_sau_khi_giam_gia as tienSauKhiGiam,
                           hd.tien_ship as tienShip, hd.tong_tien as tongTien, hd.trang_thai as trangThai,
                           hd.id as idHD,vou.id as idVoucher, vou.ten as tenVoucher,
                           dc.dia_chi as diaChiCuThe, dc.id_tinh_thanh as idTinhThanh,\s
                           dc.ten_tinh_thanh as tenTinhThanh, dc.id_quan_huyen as idQuanHuyen, dc.ten_quan_huyen as tenQuanHuyen,\s
                           dc.id_phuong_xa as idPhuongXa, dc.ten_phuong_xa as tenPhuongXa,
                           pttt.ten as tenPTTT, hd.ngay_thanh_toan  as ngayThanhToan, hd.mo_ta as moTa, u.id as idUser
                    FROM  datn.hoa_don hd  left  join datn.dia_chi dc on dc.id = hd.id_dia_chi_sdt\s
                                             join datn.phuong_thuc_thanh_toan pttt on pttt.id = hd.id_phuong_thuc_thanh_toan\s
                                               left  join voucher vou  on hd.id_voucher = vou.id
                                             join datn.user u on u.id = hd.id_user
            """, nativeQuery = true)
    List<AdminHoaDonResponse> getAll();

    @Query(value = """
                    SELECT ROW_NUMBER() OVER(ORDER BY hd.id DESC) AS stt,
                           u.email as email ,u.sdt,hd.hinh_thuc_giao_hang as hinhThucGiaoHang, hd.ly_do as lyDo,
                           hd.ma as maHD, u.ten as nguoiTao, hd.ngay_nhan as ngayNhan, hd.ngay_ship as ngayShip, hd.ngay_tao as ngayTao,\s
                           hd.ngay_sua as ngaySua, hd.ten_nguoi_nhan as tenNguoiNhan, hd.tien_sau_khi_giam_gia as tienSauKhiGiam,
                           hd.tien_ship as tienShip, hd.tong_tien as tongTien, hd.trang_thai as trangThai,
                           hd.id as idHD,vou.id as idVoucher, vou.ten as tenVoucher,
                           dc.dia_chi as diaChiCuThe, dc.id_tinh_thanh as idTinhThanh,\s
                           dc.ten_tinh_thanh as tenTinhThanh, dc.id_quan_huyen as idQuanHuyen, dc.ten_quan_huyen as tenQuanHuyen,\s
                           dc.id_phuong_xa as idPhuongXa, dc.ten_phuong_xa as tenPhuongXa,
                           pttt.ten as tenPTTT, hd.ngay_thanh_toan  as ngayThanhToan, hd.mo_ta as moTa, u.id as idUser
                    FROM  datn.hoa_don hd  left  join datn.dia_chi dc on dc.id = hd.id_dia_chi_sdt\s
                                             join datn.phuong_thuc_thanh_toan pttt on pttt.id = hd.id_phuong_thuc_thanh_toan\s
                                             join datn.user u on u.id = hd.id_user
                    where pttt.id = :phuongThucThanhToan
            """, nativeQuery = true)
    List<AdminHoaDonResponse> getAllByPttt(@Param("phuongThucThanhToan") Integer pttt);

    @Query(value = """
                    SELECT ROW_NUMBER() OVER(ORDER BY hd.id DESC) AS stt,
                           u.email as email ,u.sdt,hd.hinh_thuc_giao_hang as hinhThucGiaoHang, hd.ly_do as lyDo,
                           hd.ma as maHD, u.ten as nguoiTao, hd.ngay_nhan as ngayNhan, hd.ngay_ship as ngayShip, hd.ngay_tao as ngayTao,\s
                           hd.ngay_sua as ngaySua, hd.ten_nguoi_nhan as tenNguoiNhan, hd.tien_sau_khi_giam_gia as tienSauKhiGiam,
                           hd.tien_ship as tienShip, hd.tong_tien as tongTien, hd.trang_thai as trangThai,
                           hd.id as idHD,vou.id as idVoucher, vou.ten as tenVoucher,
                           dc.dia_chi as diaChiCuThe, dc.id_tinh_thanh as idTinhThanh,\s
                           dc.ten_tinh_thanh as tenTinhThanh, dc.id_quan_huyen as idQuanHuyen, dc.ten_quan_huyen as tenQuanHuyen,\s
                           dc.id_phuong_xa as idPhuongXa, dc.ten_phuong_xa as tenPhuongXa,
                           pttt.ten as tenPTTT, hd.ngay_thanh_toan  as ngayThanhToan, hd.mo_ta as moTa, u.id as idUser
                    FROM  datn.hoa_don hd  left  join datn.dia_chi dc on dc.id = hd.id_dia_chi_sdt\s
                                             join datn.phuong_thuc_thanh_toan pttt on pttt.id = hd.id_phuong_thuc_thanh_toan\s
                                             join datn.user u on u.id = hd.id_user
                    where hd.hinh_thuc_giao_hang = :hinhThucGiao
            """, nativeQuery = true)
    List<AdminHoaDonResponse> getAllByHinhThucGiao(@Param("hinhThucGiao") Integer hinhThucGiao);


    @Query(value = """
                    SELECT ROW_NUMBER() OVER(ORDER BY hd.id DESC) AS stt,
                           u.email as email ,u.sdt,hd.hinh_thuc_giao_hang as hinhThucGiaoHang, hd.ly_do as lyDo,
                           hd.ma as maHD, u.ten as nguoiTao, hd.ngay_nhan as ngayNhan, hd.ngay_ship as ngayShip, hd.ngay_tao as ngayTao,\s
                           hd.ngay_sua as ngaySua, hd.ten_nguoi_nhan as tenNguoiNhan, hd.tien_sau_khi_giam_gia as tienSauKhiGiam,
                           hd.tien_ship as tienShip, hd.tong_tien as tongTien, hd.trang_thai as trangThai,
                           hd.id as idHD,vou.id as idVoucher, vou.ten as tenVoucher,
                           dc.dia_chi as diaChiCuThe, dc.id_tinh_thanh as idTinhThanh,\s
                           dc.ten_tinh_thanh as tenTinhThanh, dc.id_quan_huyen as idQuanHuyen, dc.ten_quan_huyen as tenQuanHuyen,\s
                           dc.id_phuong_xa as idPhuongXa, dc.ten_phuong_xa as tenPhuongXa,
                           pttt.ten as tenPTTT, hd.ngay_thanh_toan  as ngayThanhToan, hd.mo_ta as moTa, u.id as idUser
                    FROM  datn.hoa_don hd  left  join datn.dia_chi dc on dc.id = hd.id_dia_chi_sdt\s
                                             join datn.phuong_thuc_thanh_toan pttt on pttt.id = hd.id_phuong_thuc_thanh_toan\s
                                               left  join voucher vou  on hd.id_voucher = vou.id
                                             join datn.user u on u.id = hd.id_user
                    where hd.hinh_thuc_giao_hang = :hinhThucGiao and pttt.id = :phuongThucThanhToan
            """, nativeQuery = true)
    List<AdminHoaDonResponse> getAllByHinhThucGiaoAndPttt(@Param("hinhThucGiao") Integer hinhThucGiao, @Param("phuongThucThanhToan") Integer pttt);

    @Query(value = """
                     SELECT ROW_NUMBER() OVER(ORDER BY hd.id DESC) AS stt,
                           u.email as email ,u.sdt,hd.hinh_thuc_giao_hang as hinhThucGiaoHang, hd.ly_do as lyDo,
                           hd.ma as maHD, u.ten as nguoiTao, hd.ngay_nhan as ngayNhan, hd.ngay_ship as ngayShip, hd.ngay_tao as ngayTao,\s
                           hd.ngay_sua as ngaySua, hd.ten_nguoi_nhan as tenNguoiNhan, hd.tien_sau_khi_giam_gia as tienSauKhiGiam,
                           hd.tien_ship as tienShip, hd.tong_tien as tongTien, hd.trang_thai as trangThai,
                           hd.id as idHD,vou.id as idVoucher, vou.ten as tenVoucher,
                           dc.dia_chi as diaChiCuThe, dc.id_tinh_thanh as idTinhThanh,\s
                           dc.ten_tinh_thanh as tenTinhThanh, dc.id_quan_huyen as idQuanHuyen, dc.ten_quan_huyen as tenQuanHuyen,\s
                           dc.id_phuong_xa as idPhuongXa, dc.ten_phuong_xa as tenPhuongXa,
                           pttt.ten as tenPTTT, hd.ngay_thanh_toan  as ngayThanhToan, hd.mo_ta as moTa, u.id as idUser
                    FROM  datn.hoa_don hd   left join datn.dia_chi dc on dc.id = hd.id_dia_chi_sdt\s
                                             join datn.phuong_thuc_thanh_toan pttt on pttt.id = hd.id_phuong_thuc_thanh_toan\s
                                               left  join voucher vou  on hd.id_voucher = vou.id
                                             join datn.user u on u.id = hd.id_user
            	   WHERE  hd.id=:id
            """, nativeQuery = true)
    AdminHoaDonResponse getByIds(Integer id);

    @Query(value = """
                    SELECT ROW_NUMBER() OVER(ORDER BY hd.id DESC) AS stt,
                           u.email as email ,u.sdt,hd.hinh_thuc_giao_hang as hinhThucGiaoHang, hd.ly_do as lyDo,
                           hd.ma as maHD, u.ten as nguoiTao, hd.ngay_nhan as ngayNhan, hd.ngay_ship as ngayShip, hd.ngay_tao as ngayTao,\s
                           hd.ngay_sua as ngaySua, hd.ten_nguoi_nhan as tenNguoiNhan, hd.tien_sau_khi_giam_gia as tienSauKhiGiam,
                           hd.tien_ship as tienShip, hd.tong_tien as tongTien, hd.trang_thai as trangThai,
                           hd.id as idHD,vou.id as idVoucher, vou.ten as tenVoucher,
                           dc.dia_chi as diaChiCuThe, dc.id_tinh_thanh as idTinhThanh,\s
                           dc.ten_tinh_thanh as tenTinhThanh, dc.id_quan_huyen as idQuanHuyen, dc.ten_quan_huyen as tenQuanHuyen,\s
                           dc.id_phuong_xa as idPhuongXa, dc.ten_phuong_xa as tenPhuongXa,
                           pttt.ten as tenPTTT, hd.ngay_thanh_toan  as ngayThanhToan, hd.mo_ta as moTa, u.id as idUser
                    FROM  datn.hoa_don hd   left join datn.dia_chi dc on dc.id = hd.id_dia_chi_sdt\s
                                             join datn.phuong_thuc_thanh_toan pttt on pttt.id = hd.id_phuong_thuc_thanh_toan\s
                                               left  join voucher vou  on hd.id_voucher = vou.id
                                             join datn.user u on u.id = hd.id_user where hd.trang_thai =:trangThai
            """, nativeQuery = true)
    List<AdminHoaDonResponse> getHoaDonTrangThai(Integer trangThai);

    @Query(value = """
                    SELECT ROW_NUMBER() OVER(ORDER BY hd.id DESC) AS stt,
                           u.email as email ,u.sdt,hd.hinh_thuc_giao_hang as hinhThucGiaoHang, hd.ly_do as lyDo,
                           hd.ma as maHD, u.ten as nguoiTao, hd.ngay_nhan as ngayNhan, hd.ngay_ship as ngayShip, hd.ngay_tao as ngayTao,\s
                           hd.ngay_sua as ngaySua, hd.ten_nguoi_nhan as tenNguoiNhan, hd.tien_sau_khi_giam_gia as tienSauKhiGiam,
                           hd.tien_ship as tienShip, hd.tong_tien as tongTien, hd.trang_thai as trangThai,
                           hd.id as idHD,vou.id as idVoucher, vou.ten as tenVoucher,
                           dc.dia_chi as diaChiCuThe, dc.id_tinh_thanh as idTinhThanh,\s
                           dc.ten_tinh_thanh as tenTinhThanh, dc.id_quan_huyen as idQuanHuyen, dc.ten_quan_huyen as tenQuanHuyen,\s
                           dc.id_phuong_xa as idPhuongXa, dc.ten_phuong_xa as tenPhuongXa,
                           pttt.ten as tenPTTT, hd.ngay_thanh_toan  as ngayThanhToan, hd.mo_ta as moTa, u.id as idUser
                    FROM  datn.hoa_don hd   left join datn.dia_chi dc on dc.id = hd.id_dia_chi_sdt\s
                                             join datn.phuong_thuc_thanh_toan pttt on pttt.id = hd.id_phuong_thuc_thanh_toan\s
                                               left  join voucher vou  on hd.id_voucher = vou.id
                                             join datn.user u on u.id = hd.id_user where hd.trang_thai =:trangThai and hd.hinh_thuc_giao_hang = :hinhThucGiao
            """, nativeQuery = true)
    List<AdminHoaDonResponse> getHoaDonTrangThaiAndHinhThucGiao(@Param("trangThai") Integer trangThai, @Param("hinhThucGiao") Integer hinhThucGiao);

    @Query(value = """
                    SELECT ROW_NUMBER() OVER(ORDER BY hd.id DESC) AS stt,
                           u.email as email ,u.sdt,hd.hinh_thuc_giao_hang as hinhThucGiaoHang, hd.ly_do as lyDo,
                           hd.ma as maHD, u.ten as nguoiTao, hd.ngay_nhan as ngayNhan, hd.ngay_ship as ngayShip, hd.ngay_tao as ngayTao,\s
                           hd.ngay_sua as ngaySua, hd.ten_nguoi_nhan as tenNguoiNhan, hd.tien_sau_khi_giam_gia as tienSauKhiGiam,
                           hd.tien_ship as tienShip, hd.tong_tien as tongTien, hd.trang_thai as trangThai,
                           hd.id as idHD,vou.id as idVoucher, vou.ten as tenVoucher,
                           dc.dia_chi as diaChiCuThe, dc.id_tinh_thanh as idTinhThanh,\s
                           dc.ten_tinh_thanh as tenTinhThanh, dc.id_quan_huyen as idQuanHuyen, dc.ten_quan_huyen as tenQuanHuyen,\s
                           dc.id_phuong_xa as idPhuongXa, dc.ten_phuong_xa as tenPhuongXa,
                           pttt.ten as tenPTTT, hd.ngay_thanh_toan  as ngayThanhToan, hd.mo_ta as moTa, u.id as idUser
                    FROM  datn.hoa_don hd   left join datn.dia_chi dc on dc.id = hd.id_dia_chi_sdt\s
                                             join datn.phuong_thuc_thanh_toan pttt on pttt.id = hd.id_phuong_thuc_thanh_toan\s
                                               left  join voucher vou  on hd.id_voucher = vou.id
                                             join datn.user u on u.id = hd.id_user 
                    where hd.trang_thai =:trangThai and pttt.id = :phuongThucThanhToan and hd.hinh_thuc_giao_hang = :hinhThucGiao
            """, nativeQuery = true)
    List<AdminHoaDonResponse> getHoaDonTrangThaiAndPtttAndHtgh(@Param("trangThai") Integer trangThai, @Param("phuongThucThanhToan") Integer phuongThucThanhToan, @Param("hinhThucGiao") Integer hinhThucGiao);

    @Query(value = """
                    SELECT ROW_NUMBER() OVER(ORDER BY hd.id DESC) AS stt,
                           u.email as email ,u.sdt,hd.hinh_thuc_giao_hang as hinhThucGiaoHang, hd.ly_do as lyDo,
                           hd.ma as maHD, u.ten as nguoiTao, hd.ngay_nhan as ngayNhan, hd.ngay_ship as ngayShip, hd.ngay_tao as ngayTao,\s
                           hd.ngay_sua as ngaySua, hd.ten_nguoi_nhan as tenNguoiNhan, hd.tien_sau_khi_giam_gia as tienSauKhiGiam,
                           hd.tien_ship as tienShip, hd.tong_tien as tongTien, hd.trang_thai as trangThai,
                           hd.id as idHD,vou.id as idVoucher, vou.ten as tenVoucher,
                           dc.dia_chi as diaChiCuThe, dc.id_tinh_thanh as idTinhThanh,\s
                           dc.ten_tinh_thanh as tenTinhThanh, dc.id_quan_huyen as idQuanHuyen, dc.ten_quan_huyen as tenQuanHuyen,\s
                           dc.id_phuong_xa as idPhuongXa, dc.ten_phuong_xa as tenPhuongXa,
                           pttt.ten as tenPTTT, hd.ngay_thanh_toan  as ngayThanhToan, hd.mo_ta as moTa, u.id as idUser
                    FROM  datn.hoa_don hd   left join datn.dia_chi dc on dc.id = hd.id_dia_chi_sdt\s
                                             join datn.phuong_thuc_thanh_toan pttt on pttt.id = hd.id_phuong_thuc_thanh_toan\s
                                               left  join voucher vou  on hd.id_voucher = vou.id
                                             join datn.user u on u.id = hd.id_user 
                    where hd.trang_thai =:trangThai and pttt.id = :phuongThucThanhToan
            """, nativeQuery = true)
    List<AdminHoaDonResponse> getHoaDonTrangThaiAndPttt(@Param("trangThai") Integer trangThai, @Param("phuongThucThanhToan") Integer phuongThucThanhToan);

    @Query(value = """
                   SELECT ROW_NUMBER() OVER(ORDER BY hd.id DESC) AS stt,
                           u.email as email ,u.sdt,hd.hinh_thuc_giao_hang as hinhThucGiaoHang, hd.ly_do as lyDo,
                           hd.ma as maHD, u.ten as nguoiTao, hd.ngay_nhan as ngayNhan, hd.ngay_ship as ngayShip, hd.ngay_tao as ngayTao,\s
                           hd.ngay_sua as ngaySua, hd.ten_nguoi_nhan as tenNguoiNhan, hd.tien_sau_khi_giam_gia as tienSauKhiGiam,
                           hd.tien_ship as tienShip, hd.tong_tien as tongTien, hd.trang_thai as trangThai,
                           hd.id as idHD,vou.id as idVoucher, vou.ten as tenVoucher,
                           dc.dia_chi as diaChiCuThe, dc.id_tinh_thanh as idTinhThanh,\s
                           dc.ten_tinh_thanh as tenTinhThanh, dc.id_quan_huyen as idQuanHuyen, dc.ten_quan_huyen as tenQuanHuyen,\s
                           dc.id_phuong_xa as idPhuongXa, dc.ten_phuong_xa as tenPhuongXa,
                           pttt.ten as tenPTTT, hd.ngay_thanh_toan  as ngayThanhToan, hd.mo_ta as moTa, u.id as idUser
                    FROM  datn.hoa_don hd  left  join datn.dia_chi dc on dc.id = hd.id_dia_chi_sdt\s
                                             join datn.phuong_thuc_thanh_toan pttt on pttt.id = hd.id_phuong_thuc_thanh_toan\s
                                               left  join voucher vou  on hd.id_voucher = vou.id
                                             join datn.user u on u.id = hd.id_user
            	   where (CASE
                                   WHEN :comboBoxValue = 'ngayTao' THEN  hd.ngay_tao >= :startDate and hd.ngay_tao <= :endDate
                                   WHEN :comboBoxValue = 'ngaySua' THEN  hd.ngay_sua >= :startDate and hd.ngay_sua <= :endDate
                                   WHEN :comboBoxValue = 'ngayThanhToan' THEN  hd.ngay_thanh_toan >= :startDate and hd.ngay_thanh_toan <= :endDate
                                   WHEN :comboBoxValue = 'ngayShip' THEN  hd.ngay_ship >= :startDate and hd.ngay_ship <= :endDate
                                   WHEN :comboBoxValue = 'ngayNhan' THEN  hd.ngay_nhan >= :startDate and hd.ngay_nhan <= :endDate
                                   END)  
            	  
            """, nativeQuery = true)
    List<AdminHoaDonResponse> getHoaDonByDate(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, @Param("comboBoxValue") String comboBoxValue);

    @Query(value = """
                   SELECT ROW_NUMBER() OVER(ORDER BY hd.id DESC) AS stt,
                           u.email as email ,u.sdt,hd.hinh_thuc_giao_hang as hinhThucGiaoHang, hd.ly_do as lyDo,
                           hd.ma as maHD, u.ten as nguoiTao, hd.ngay_nhan as ngayNhan, hd.ngay_ship as ngayShip, hd.ngay_tao as ngayTao,\s
                           hd.ngay_sua as ngaySua, hd.ten_nguoi_nhan as tenNguoiNhan, hd.tien_sau_khi_giam_gia as tienSauKhiGiam,
                           hd.tien_ship as tienShip, hd.tong_tien as tongTien, hd.trang_thai as trangThai,
                           hd.id as idHD,vou.id as idVoucher, vou.ten as tenVoucher,
                           dc.dia_chi as diaChiCuThe, dc.id_tinh_thanh as idTinhThanh,\s
                           dc.ten_tinh_thanh as tenTinhThanh, dc.id_quan_huyen as idQuanHuyen, dc.ten_quan_huyen as tenQuanHuyen,\s
                           dc.id_phuong_xa as idPhuongXa, dc.ten_phuong_xa as tenPhuongXa,
                           pttt.ten as tenPTTT, hd.ngay_thanh_toan  as ngayThanhToan, hd.mo_ta as moTa, u.id as idUser
                    FROM  datn.hoa_don hd  left  join datn.dia_chi dc on dc.id = hd.id_dia_chi_sdt\s
                                             join datn.phuong_thuc_thanh_toan pttt on pttt.id = hd.id_phuong_thuc_thanh_toan\s
                                               left  join voucher vou  on hd.id_voucher = vou.id
                                             join datn.user u on u.id = hd.id_user
            	   where hd.hinh_thuc_giao_hang = :hinhThucGiao and (CASE
                                   WHEN :comboBoxValue = 'ngayTao' THEN  hd.ngay_tao >= :startDate and hd.ngay_tao <= :endDate 
                                   WHEN :comboBoxValue = 'ngaySua' THEN  hd.ngay_sua >= :startDate and hd.ngay_sua <= :endDate 
                                   WHEN :comboBoxValue = 'ngayThanhToan' THEN  hd.ngay_thanh_toan >= :startDate and hd.ngay_thanh_toan <= :endDate 
                                   WHEN :comboBoxValue = 'ngayShip' THEN  hd.ngay_ship >= :startDate and hd.ngay_ship <= :endDate 
                                   WHEN :comboBoxValue = 'ngayNhan' THEN  hd.ngay_nhan >= :startDate and hd.ngay_nhan <= :endDate 
                                   END)  
            	  
            """, nativeQuery = true)
    List<AdminHoaDonResponse> getHoaDonByDateAndHinhThucGiao(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, @Param("comboBoxValue") String comboBoxValue, @Param("hinhThucGiao") Integer hinhThucGiao);

    @Query(value = """
                   SELECT ROW_NUMBER() OVER(ORDER BY hd.id DESC) AS stt,
                           u.email as email ,u.sdt,hd.hinh_thuc_giao_hang as hinhThucGiaoHang, hd.ly_do as lyDo,
                           hd.ma as maHD, u.ten as nguoiTao, hd.ngay_nhan as ngayNhan, hd.ngay_ship as ngayShip, hd.ngay_tao as ngayTao,\s
                           hd.ngay_sua as ngaySua, hd.ten_nguoi_nhan as tenNguoiNhan, hd.tien_sau_khi_giam_gia as tienSauKhiGiam,
                           hd.tien_ship as tienShip, hd.tong_tien as tongTien, hd.trang_thai as trangThai,
                           hd.id as idHD,vou.id as idVoucher, vou.ten as tenVoucher,
                           dc.dia_chi as diaChiCuThe, dc.id_tinh_thanh as idTinhThanh,\s
                           dc.ten_tinh_thanh as tenTinhThanh, dc.id_quan_huyen as idQuanHuyen, dc.ten_quan_huyen as tenQuanHuyen,\s
                           dc.id_phuong_xa as idPhuongXa, dc.ten_phuong_xa as tenPhuongXa,
                           pttt.ten as tenPTTT, hd.ngay_thanh_toan  as ngayThanhToan, hd.mo_ta as moTa, u.id as idUser
                    FROM  datn.hoa_don hd  left  join datn.dia_chi dc on dc.id = hd.id_dia_chi_sdt\s
                                             join datn.phuong_thuc_thanh_toan pttt on pttt.id = hd.id_phuong_thuc_thanh_toan\s
                                               left  join voucher vou  on hd.id_voucher = vou.id
                                             join datn.user u on u.id = hd.id_user
            	   where pttt.id = :phuongThucThanhToan and (CASE
                                   WHEN :comboBoxValue = 'ngayTao' THEN  hd.ngay_tao >= :startDate and hd.ngay_tao <= :endDate 
                                   WHEN :comboBoxValue = 'ngaySua' THEN  hd.ngay_sua >= :startDate and hd.ngay_sua <= :endDate 
                                   WHEN :comboBoxValue = 'ngayThanhToan' THEN  hd.ngay_thanh_toan >= :startDate and hd.ngay_thanh_toan <= :endDate 
                                   WHEN :comboBoxValue = 'ngayShip' THEN  hd.ngay_ship >= :startDate and hd.ngay_ship <= :endDate 
                                   WHEN :comboBoxValue = 'ngayNhan' THEN  hd.ngay_nhan >= :startDate and hd.ngay_nhan <= :endDate 
                                   END)  
            	  
            """, nativeQuery = true)
    List<AdminHoaDonResponse> getHoaDonByDateAndPttt(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, @Param("comboBoxValue") String comboBoxValue, @Param("phuongThucThanhToan") Integer phuongThucThanhToan);

    @Query(value = """
                   SELECT ROW_NUMBER() OVER(ORDER BY hd.id DESC) AS stt,
                           u.email as email ,u.sdt,hd.hinh_thuc_giao_hang as hinhThucGiaoHang, hd.ly_do as lyDo,
                           hd.ma as maHD, u.ten as nguoiTao, hd.ngay_nhan as ngayNhan, hd.ngay_ship as ngayShip, hd.ngay_tao as ngayTao,\s
                           hd.ngay_sua as ngaySua, hd.ten_nguoi_nhan as tenNguoiNhan, hd.tien_sau_khi_giam_gia as tienSauKhiGiam,
                           hd.tien_ship as tienShip, hd.tong_tien as tongTien, hd.trang_thai as trangThai,
                           hd.id as idHD,vou.id as idVoucher, vou.ten as tenVoucher,
                           dc.dia_chi as diaChiCuThe, dc.id_tinh_thanh as idTinhThanh,\s
                           dc.ten_tinh_thanh as tenTinhThanh, dc.id_quan_huyen as idQuanHuyen, dc.ten_quan_huyen as tenQuanHuyen,\s
                           dc.id_phuong_xa as idPhuongXa, dc.ten_phuong_xa as tenPhuongXa,
                           pttt.ten as tenPTTT, hd.ngay_thanh_toan  as ngayThanhToan, hd.mo_ta as moTa, u.id as idUser
                    FROM  datn.hoa_don hd  left  join datn.dia_chi dc on dc.id = hd.id_dia_chi_sdt\s
                                             join datn.phuong_thuc_thanh_toan pttt on pttt.id = hd.id_phuong_thuc_thanh_toan\s
                                               left  join voucher vou  on hd.id_voucher = vou.id
                                             join datn.user u on u.id = hd.id_user
            	   where hd.hinh_thuc_giao_hang = :hinhThucGiao and pttt.id = :phuongThucThanhToan and (CASE
                                   WHEN :comboBoxValue = 'ngayTao' THEN  hd.ngay_tao >= :startDate and hd.ngay_tao <= :endDate 
                                   WHEN :comboBoxValue = 'ngaySua' THEN  hd.ngay_sua >= :startDate and hd.ngay_sua <= :endDate 
                                   WHEN :comboBoxValue = 'ngayThanhToan' THEN  hd.ngay_thanh_toan >= :startDate and hd.ngay_thanh_toan <= :endDate 
                                   WHEN :comboBoxValue = 'ngayShip' THEN  hd.ngay_ship >= :startDate and hd.ngay_ship <= :endDate 
                                   WHEN :comboBoxValue = 'ngayNhan' THEN  hd.ngay_nhan >= :startDate and hd.ngay_nhan <= :endDate 
                                   END)  
            	  
            """, nativeQuery = true)
    List<AdminHoaDonResponse> getHoaDonByDateAndPtttAndHtgh(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, @Param("comboBoxValue") String comboBoxValue, @Param("phuongThucThanhToan") Integer phuongThucThanhToan, @Param("hinhThucGiao") Integer hinhThucGiao);

    @Query(value = """
                   SELECT ROW_NUMBER() OVER(ORDER BY hd.id DESC) AS stt,
                           u.email as email ,u.sdt,hd.hinh_thuc_giao_hang as hinhThucGiaoHang, hd.ly_do as lyDo,
                           hd.ma as maHD, u.ten as nguoiTao, hd.ngay_nhan as ngayNhan, hd.ngay_ship as ngayShip, hd.ngay_tao as ngayTao,\s
                           hd.ngay_sua as ngaySua, hd.ten_nguoi_nhan as tenNguoiNhan, hd.tien_sau_khi_giam_gia as tienSauKhiGiam,
                           hd.tien_ship as tienShip, hd.tong_tien as tongTien, hd.trang_thai as trangThai,
                           hd.id as idHD,vou.id as idVoucher, vou.ten as tenVoucher,
                           dc.dia_chi as diaChiCuThe, dc.id_tinh_thanh as idTinhThanh,\s
                           dc.ten_tinh_thanh as tenTinhThanh, dc.id_quan_huyen as idQuanHuyen, dc.ten_quan_huyen as tenQuanHuyen,\s
                           dc.id_phuong_xa as idPhuongXa, dc.ten_phuong_xa as tenPhuongXa,
                           pttt.ten as tenPTTT, hd.ngay_thanh_toan  as ngayThanhToan, hd.mo_ta as moTa, u.id as idUser
                    FROM  datn.hoa_don hd  left  join datn.dia_chi dc on dc.id = hd.id_dia_chi_sdt\s
                                             join datn.phuong_thuc_thanh_toan pttt on pttt.id = hd.id_phuong_thuc_thanh_toan\s
                                               left  join voucher vou  on hd.id_voucher = vou.id
                                             join datn.user u on u.id = hd.id_user
            	   where hd.trang_thai = :trangThai and (CASE
                                   WHEN :comboBoxValue = 'ngayTao' THEN  hd.ngay_tao >= :startDate and hd.ngay_tao <= :endDate
                                   WHEN :comboBoxValue = 'ngaySua' THEN  hd.ngay_sua >= :startDate and hd.ngay_sua <= :endDate
                                   WHEN :comboBoxValue = 'ngayThanhToan' THEN  hd.ngay_thanh_toan >= :startDate and hd.ngay_thanh_toan <= :endDate
                                   WHEN :comboBoxValue = 'ngayShip' THEN  hd.ngay_ship >= :startDate and hd.ngay_ship <= :endDate
                                   WHEN :comboBoxValue = 'ngayNhan' THEN  hd.ngay_nhan >= :startDate and hd.ngay_nhan <= :endDate
                                   END)  
            	  
            """, nativeQuery = true)
    List<AdminHoaDonResponse> getHoaDonByDateByTrangThai(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, @Param("comboBoxValue") String comboBoxValue, @Param("trangThai") Integer trangThai);

    @Query(value = """
                   SELECT ROW_NUMBER() OVER(ORDER BY hd.id DESC) AS stt,
                           u.email as email ,u.sdt,hd.hinh_thuc_giao_hang as hinhThucGiaoHang, hd.ly_do as lyDo,
                           hd.ma as maHD, u.ten as nguoiTao, hd.ngay_nhan as ngayNhan, hd.ngay_ship as ngayShip, hd.ngay_tao as ngayTao,\s
                           hd.ngay_sua as ngaySua, hd.ten_nguoi_nhan as tenNguoiNhan, hd.tien_sau_khi_giam_gia as tienSauKhiGiam,
                           hd.tien_ship as tienShip, hd.tong_tien as tongTien, hd.trang_thai as trangThai,
                           hd.id as idHD,vou.id as idVoucher, vou.ten as tenVoucher,
                           dc.dia_chi as diaChiCuThe, dc.id_tinh_thanh as idTinhThanh,\s
                           dc.ten_tinh_thanh as tenTinhThanh, dc.id_quan_huyen as idQuanHuyen, dc.ten_quan_huyen as tenQuanHuyen,\s
                           dc.id_phuong_xa as idPhuongXa, dc.ten_phuong_xa as tenPhuongXa,
                           pttt.ten as tenPTTT, hd.ngay_thanh_toan  as ngayThanhToan, hd.mo_ta as moTa, u.id as idUser
                    FROM  datn.hoa_don hd  left  join datn.dia_chi dc on dc.id = hd.id_dia_chi_sdt\s
                                             join datn.phuong_thuc_thanh_toan pttt on pttt.id = hd.id_phuong_thuc_thanh_toan\s
                                               left  join voucher vou  on hd.id_voucher = vou.id
                                             join datn.user u on u.id = hd.id_user
            	   where hd.hinh_thuc_giao_hang = :hinhThucGiao and hd.trang_thai = :trangThai and (CASE
                                   WHEN :comboBoxValue = 'ngayTao' THEN  hd.ngay_tao >= :startDate and hd.ngay_tao <= :endDate
                                   WHEN :comboBoxValue = 'ngaySua' THEN  hd.ngay_sua >= :startDate and hd.ngay_sua <= :endDate
                                   WHEN :comboBoxValue = 'ngayThanhToan' THEN  hd.ngay_thanh_toan >= :startDate and hd.ngay_thanh_toan <= :endDate
                                   WHEN :comboBoxValue = 'ngayShip' THEN  hd.ngay_ship >= :startDate and hd.ngay_ship <= :endDate
                                   WHEN :comboBoxValue = 'ngayNhan' THEN  hd.ngay_nhan >= :startDate and hd.ngay_nhan <= :endDate
                                   END)  
            	  
            """, nativeQuery = true)
    List<AdminHoaDonResponse> getHoaDonByDateByTrangThaiAndHinhThucGiao(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, @Param("comboBoxValue") String comboBoxValue, @Param("trangThai") Integer trangThai, @Param("hinhThucGiao") Integer hinhThucGiao);

    @Query(value = """
                   SELECT ROW_NUMBER() OVER(ORDER BY hd.id DESC) AS stt,
                           u.email as email ,u.sdt,hd.hinh_thuc_giao_hang as hinhThucGiaoHang, hd.ly_do as lyDo,
                           hd.ma as maHD, u.ten as nguoiTao, hd.ngay_nhan as ngayNhan, hd.ngay_ship as ngayShip, hd.ngay_tao as ngayTao,\s
                           hd.ngay_sua as ngaySua, hd.ten_nguoi_nhan as tenNguoiNhan, hd.tien_sau_khi_giam_gia as tienSauKhiGiam,
                           hd.tien_ship as tienShip, hd.tong_tien as tongTien, hd.trang_thai as trangThai,
                           hd.id as idHD,vou.id as idVoucher, vou.ten as tenVoucher,
                           dc.dia_chi as diaChiCuThe, dc.id_tinh_thanh as idTinhThanh,\s
                           dc.ten_tinh_thanh as tenTinhThanh, dc.id_quan_huyen as idQuanHuyen, dc.ten_quan_huyen as tenQuanHuyen,\s
                           dc.id_phuong_xa as idPhuongXa, dc.ten_phuong_xa as tenPhuongXa,
                           pttt.ten as tenPTTT, hd.ngay_thanh_toan  as ngayThanhToan, hd.mo_ta as moTa, u.id as idUser
                    FROM  datn.hoa_don hd  left  join datn.dia_chi dc on dc.id = hd.id_dia_chi_sdt\s
                                             join datn.phuong_thuc_thanh_toan pttt on pttt.id = hd.id_phuong_thuc_thanh_toan\s
                                               left  join voucher vou  on hd.id_voucher = vou.id
                                             join datn.user u on u.id = hd.id_user
            	   where pttt.id = :phuongThucThanhToan and hd.trang_thai = :trangThai and (CASE
                                   WHEN :comboBoxValue = 'ngayTao' THEN  hd.ngay_tao >= :startDate and hd.ngay_tao <= :endDate
                                   WHEN :comboBoxValue = 'ngaySua' THEN  hd.ngay_sua >= :startDate and hd.ngay_sua <= :endDate
                                   WHEN :comboBoxValue = 'ngayThanhToan' THEN  hd.ngay_thanh_toan >= :startDate and hd.ngay_thanh_toan <= :endDate
                                   WHEN :comboBoxValue = 'ngayShip' THEN  hd.ngay_ship >= :startDate and hd.ngay_ship <= :endDate
                                   WHEN :comboBoxValue = 'ngayNhan' THEN  hd.ngay_nhan >= :startDate and hd.ngay_nhan <= :endDate
                                   END)  
            	  
            """, nativeQuery = true)
    List<AdminHoaDonResponse> getHoaDonByDateByTrangThaiAndPttt(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, @Param("comboBoxValue") String comboBoxValue, @Param("trangThai") Integer trangThai, @Param("phuongThucThanhToan") Integer phuongThucThanhToan);

    @Query(value = """
                   SELECT ROW_NUMBER() OVER(ORDER BY hd.id DESC) AS stt,
                           u.email as email ,u.sdt,hd.hinh_thuc_giao_hang as hinhThucGiaoHang, hd.ly_do as lyDo,
                           hd.ma as maHD, u.ten as nguoiTao, hd.ngay_nhan as ngayNhan, hd.ngay_ship as ngayShip, hd.ngay_tao as ngayTao,\s
                           hd.ngay_sua as ngaySua, hd.ten_nguoi_nhan as tenNguoiNhan, hd.tien_sau_khi_giam_gia as tienSauKhiGiam,
                           hd.tien_ship as tienShip, hd.tong_tien as tongTien, hd.trang_thai as trangThai,
                           hd.id as idHD,vou.id as idVoucher, vou.ten as tenVoucher,
                           dc.dia_chi as diaChiCuThe, dc.id_tinh_thanh as idTinhThanh,\s
                           dc.ten_tinh_thanh as tenTinhThanh, dc.id_quan_huyen as idQuanHuyen, dc.ten_quan_huyen as tenQuanHuyen,\s
                           dc.id_phuong_xa as idPhuongXa, dc.ten_phuong_xa as tenPhuongXa,
                           pttt.ten as tenPTTT, hd.ngay_thanh_toan  as ngayThanhToan, hd.mo_ta as moTa, u.id as idUser
                    FROM  datn.hoa_don hd  left  join datn.dia_chi dc on dc.id = hd.id_dia_chi_sdt\s
                                             join datn.phuong_thuc_thanh_toan pttt on pttt.id = hd.id_phuong_thuc_thanh_toan\s
                                               left  join voucher vou  on hd.id_voucher = vou.id
                                             join datn.user u on u.id = hd.id_user
            	   where hd.hinh_thuc_giao_hang = :hinhThucGiao and pttt.id = :phuongThucThanhToan and hd.trang_thai = :trangThai and (CASE
                                   WHEN :comboBoxValue = 'ngayTao' THEN  hd.ngay_tao >= :startDate and hd.ngay_tao <= :endDate
                                   WHEN :comboBoxValue = 'ngaySua' THEN  hd.ngay_sua >= :startDate and hd.ngay_sua <= :endDate
                                   WHEN :comboBoxValue = 'ngayThanhToan' THEN  hd.ngay_thanh_toan >= :startDate and hd.ngay_thanh_toan <= :endDate
                                   WHEN :comboBoxValue = 'ngayShip' THEN  hd.ngay_ship >= :startDate and hd.ngay_ship <= :endDate
                                   WHEN :comboBoxValue = 'ngayNhan' THEN  hd.ngay_nhan >= :startDate and hd.ngay_nhan <= :endDate
                                   END)  
            	  
            """, nativeQuery = true)
    List<AdminHoaDonResponse> getHoaDonByDateByTrangThaiAndPtttAndHtgh(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, @Param("comboBoxValue") String comboBoxValue, @Param("trangThai") Integer trangThai, @Param("phuongThucThanhToan") Integer phuongThucThanhToan, @Param("hinhThucGiao") Integer hinhThucGiao);


    @Query(value = """
                     SELECT ROW_NUMBER() OVER(ORDER BY hd.id DESC) AS stt,
                           u.email as email ,u.sdt,hd.hinh_thuc_giao_hang as hinhThucGiaoHang, hd.ly_do as lyDo,
                           hd.ma as maHD, u.ten as nguoiTao, hd.ngay_nhan as ngayNhan, hd.ngay_ship as ngayShip, hd.ngay_tao as ngayTao,\s
                           hd.ngay_sua as ngaySua, hd.ten_nguoi_nhan as tenNguoiNhan, hd.tien_sau_khi_giam_gia as tienSauKhiGiam,
                           hd.tien_ship as tienShip, hd.tong_tien as tongTien, hd.trang_thai as trangThai,
                           hd.id as idHD,vou.id as idVoucher, vou.ten as tenVoucher,
                           dc.dia_chi as diaChiCuThe, dc.id_tinh_thanh as idTinhThanh,\s
                           dc.ten_tinh_thanh as tenTinhThanh, dc.id_quan_huyen as idQuanHuyen, dc.ten_quan_huyen as tenQuanHuyen,\s
                           dc.id_phuong_xa as idPhuongXa, dc.ten_phuong_xa as tenPhuongXa,
                           pttt.ten as tenPTTT, hd.ngay_thanh_toan  as ngayThanhToan, hd.mo_ta as moTa, u.id as idUser
                    FROM  datn.hoa_don hd    join datn.dia_chi dc on dc.id = hd.id_dia_chi_sdt\s
                                             join datn.phuong_thuc_thanh_toan pttt on pttt.id = hd.id_phuong_thuc_thanh_toan\s
                                               left  join voucher vou  on hd.id_voucher = vou.id
                                             join datn.user u on u.id = hd.id_user 
            	   WHERE  hd.id_user=:id
            """, nativeQuery = true)
    AdminHoaDonResponse getByIdUser(Integer id);

    @Query(value = """
                    SELECT ROW_NUMBER() OVER(ORDER BY hd.id DESC) AS stt,
                           u.email as email ,u.sdt,hd.hinh_thuc_giao_hang as hinhThucGiaoHang, hd.ly_do as lyDo,
                           hd.ma as maHD, u.ten as nguoiTao, hd.ngay_nhan as ngayNhan, hd.ngay_ship as ngayShip, hd.ngay_tao as ngayTao,\s
                           hd.ngay_sua as ngaySua, hd.ten_nguoi_nhan as tenNguoiNhan, hd.tien_sau_khi_giam_gia as tienSauKhiGiam,
                           hd.tien_ship as tienShip, hd.tong_tien as tongTien, hd.trang_thai as trangThai,
                           hd.id as idHD,vou.id as idVoucher, vou.ten as tenVoucher,
                           dc.dia_chi as diaChiCuThe, dc.id_tinh_thanh as idTinhThanh,\s
                           dc.ten_tinh_thanh as tenTinhThanh, dc.id_quan_huyen as idQuanHuyen, dc.ten_quan_huyen as tenQuanHuyen,\s
                           dc.id_phuong_xa as idPhuongXa, dc.ten_phuong_xa as tenPhuongXa,
                           pttt.ten as tenPTTT, hd.ngay_thanh_toan  as ngayThanhToan, hd.mo_ta as moTa, u.id as idUser
                    FROM  datn.hoa_don hd    join datn.dia_chi dc on dc.id = hd.id_dia_chi_sdt\s
                                             join datn.phuong_thuc_thanh_toan pttt on pttt.id = hd.id_phuong_thuc_thanh_toan\s
                                               left  join voucher vou  on hd.id_voucher = vou.id
                                             join datn.user u on u.id = hd.id_user 
            	   WHERE  hd.id=:id
            """, nativeQuery = true)
    List<AdminHoaDonResponse> getListByIds(Integer id);


    @Query(value = """
            SELECT count(hd.id) FROM datn.hoa_don hd
            """, nativeQuery = true)
    Integer tongDonhang();

    @Query(value = """
            SELECT count(hd.id) FROM datn.hoa_don hd where hd.trang_thai=3;
             """, nativeQuery = true)
    Integer tongDonhangHoanThanh();

    @Query(value = """
            SELECT count(hd.id) FROM datn.hoa_don hd where hd.trang_thai=5;
            """, nativeQuery = true)
    Integer tongDonhangDangGiao();

    @Query(value = """
            SELECT count(hd.id) FROM datn.hoa_don hd where hd.trang_thai=0;
             """, nativeQuery = true)
    Integer tongDonhangHuy();

    @Query(value = """
            SELECT count(hd.id) FROM datn.hoa_don hd where hd.trang_thai=8;
             """, nativeQuery = true)
    Integer tongDonhangHoanTra();

    @Query(value = """
                    SELECT ROW_NUMBER() OVER(ORDER BY hd.tong_tien DESC) AS stt,
                           u.email as email ,u.sdt,hd.hinh_thuc_giao_hang as hinhThucGiaoHang, hd.ly_do as lyDo,
                           hd.ma as maHD, u.ten as nguoiTao, hd.ngay_nhan as ngayNhan, hd.ngay_ship as ngayShip, hd.ngay_tao as ngayTao,\s
                           hd.ngay_sua as ngaySua, hd.ten_nguoi_nhan as tenNguoiNhan, hd.tien_sau_khi_giam_gia as tienSauKhiGiam,
                           hd.tien_ship as tienShip, hd.tong_tien as tongTien, hd.trang_thai as trangThai,
                           hd.id as idHD,\s
                           dc.dia_chi as diaChiCuThe, dc.id_tinh_thanh as idTinhThanh,\s
                           dc.ten_tinh_thanh as tenTinhThanh, dc.id_quan_huyen as idQuanHuyen, dc.ten_quan_huyen as tenQuanHuyen,\s
                           dc.id_phuong_xa as idPhuongXa, dc.ten_phuong_xa as tenPhuongXa,
                           pttt.ten as tenPTTT, hd.ngay_thanh_toan  as ngayThanhToan, hd.mo_ta as moTa, u.id as idUser
                    FROM  datn.hoa_don hd    join datn.dia_chi dc on dc.id = hd.id_dia_chi_sdt\s
                                             join datn.phuong_thuc_thanh_toan pttt on pttt.id = hd.id_phuong_thuc_thanh_toan\s
                                             join datn.user u on u.id = hd.id_user
            """, nativeQuery = true)
    List<AdminHoaDonResponse> getAllThongKe();

    List<HoaDon> findByTrangThaiOrderByNgayTaoDesc(Integer trangThai);
}
