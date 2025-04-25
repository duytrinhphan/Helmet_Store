package com.example.demo.tool;

import com.example.demo.core.Admin.repository.AdChiTietSanPhamReponsitory;
import com.example.demo.core.Admin.repository.AdHoaDonChiTietReponsitory;
import com.example.demo.core.Admin.repository.AdHoaDonReponsitory;
import com.example.demo.core.Admin.repository.AdUserRepository;
import com.example.demo.core.khachHang.repository.KHGioHangChiTietRepository;
import com.example.demo.core.khachHang.repository.KHGioHangRepository;
import com.example.demo.entity.*;
import com.example.demo.infrastructure.status.HoaDonStatus;
import com.example.demo.util.DatetimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@EnableJpaRepositories(
        basePackages = "com.example.demo"
)
public class DBGenetator implements CommandLineRunner {

    @Autowired
    private AdHoaDonReponsitory hoaDonReponsitory;

    @Autowired
    private AdChiTietSanPhamReponsitory chiTietSanPhamReponsitory;

    @Autowired
    private AdHoaDonChiTietReponsitory hoaDonChiTietReponsitory;

    @Autowired
    private AdUserRepository userReponsitory;

    @Autowired
    private KHGioHangRepository khGioHangRepository;

    @Autowired
    private KHGioHangChiTietRepository khGioHangChiTietRepository;

    @Override
    public void run(String... args) throws Exception {
        for (int i = 1; i <=5 ; i++) {
            SanPhamChiTiet sanPhamChiTiet = chiTietSanPhamReponsitory.findById(1).get();
            User user = userReponsitory.findById(3).get();
            HoaDon hoaDon = new HoaDon();
            hoaDon.setUser(user);
            hoaDon.setTenNguoiNhan(user.getTen());
            hoaDon.setTongTien(BigDecimal.valueOf(300000L));
            hoaDon.setNgayNhan(LocalDateTime.of(2023, 10, 16, 12, 30, 0));
            hoaDon.setNgayShip(LocalDateTime.of(2023, 10, 12, 12, 30, 0));
            hoaDon.setDiaChi(DiaChi.builder().id(1).build());
            hoaDon.setTienShip(BigDecimal.valueOf(20000L));
            hoaDon.setHinhThucGiaoHang(1);
            hoaDon.setNgayTao(DatetimeUtil.getCurrentDateAndTimeLocal());
            hoaDon.setPhuongThucThanhToan(PhuongThucThanhToan.builder().id(1).build());
            hoaDon.setTrangThai(HoaDonStatus.YEU_CAU_XAC_NHAN);
            HoaDon hoaDon1 = hoaDonReponsitory.save(hoaDon);
            hoaDon1.setMa("HD " + hoaDon1.getId());
            HoaDon hoaDon2 =   hoaDonReponsitory.save(hoaDon1);

           HoaDonChiTiet hoaDonChiTiet = new HoaDonChiTiet();
            hoaDonChiTiet.setSanPhamChiTiet(sanPhamChiTiet);
            hoaDonChiTiet.setDonGia(sanPhamChiTiet.getGiaBan());
            hoaDonChiTiet.setSoLuong(2);
            hoaDonChiTiet.setNgayTao(DatetimeUtil.getCurrentDate());
            hoaDonChiTiet.setTrangThai(HoaDonStatus.YEU_CAU_XAC_NHAN);
            hoaDonChiTiet.setHoaDon(hoaDon2);
            HoaDonChiTiet donChiTiet = hoaDonChiTietReponsitory.save(hoaDonChiTiet);
            donChiTiet.setMa("HDCT "+donChiTiet.getId());
            hoaDonChiTietReponsitory.save(donChiTiet);
        }

//        for (int i = 1; i <=20 ; i++) {
//            SanPhamChiTiet sanPhamChiTiet = chiTietSanPhamReponsitory.findById(1).get();
//            User user = userReponsitory.findById(3).get();
//           GioHang gioHang  = new GioHang();
//
//           gioHang.setUser(user);
//          GioHang newGioHang =  khGioHangRepository.save(gioHang);
//
//            GioHangChiTiet gioHangChiTiet = new GioHangChiTiet();
//            gioHangChiTiet.setGioHang(newGioHang);
//            gioHangChiTiet.setDonGia(sanPhamChiTiet.getGiaBan());
//            gioHangChiTiet.setSanPhamChiTiet(sanPhamChiTiet);
//            gioHangChiTiet.setSoLuong(2);
//            gioHangChiTiet.setNgayTao(DatetimeUtil.getCurrentDate());
//            khGioHangChiTietRepository.save(gioHangChiTiet);
//
//        }

    }

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(DBGenetator.class);
        context.close();
    }
}
