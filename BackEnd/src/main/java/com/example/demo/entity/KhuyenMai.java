package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Entity
@Table(name = "Khuyen_mai")
public class KhuyenMai {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "ten")
    private String ten;

    @Column(name = "ma")
    private String ma;

    @UpdateTimestamp
    @Column(name = "ngay_sua")
    private LocalDateTime ngaySua;

    @CreationTimestamp
    @Column(name = "ngay_tao")
    private LocalDateTime ngayTao;

    @Column(name = "mo_ta", length = 10000)
    private String moTa;

    @Column(name = "trang_thai")
    private Integer trangThai;

    @Column(name = "thoi_gian_bat_dau")
    private LocalDateTime thoiGianBatDau;

    @Column(name = "thoi_gian_ket_thuc")
    private LocalDateTime thoiGianKetThuc;

    @Column(name = "so_luong")
    private Integer soLuong;

    @Column(precision = 20, scale = 0)
    private BigDecimal dieuKien;

    @Enumerated(EnumType.STRING)
    private KieuGiamGia kieuGiamGia;

    @Column(name = "gia_tri_giam",precision = 20, scale = 0)
    private Integer giaTriGiam;

    @Column(precision = 20, scale = 0)
    private BigDecimal giamToiDa;
}
