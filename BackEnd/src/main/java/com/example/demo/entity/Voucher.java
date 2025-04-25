package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "voucher")
@Builder
public class Voucher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ten")
    private String ten;

    @Column(name = "thoi_gian_bat_dau")
    private LocalDateTime thoiGianBatDau;

    @Column(name = "thoi_gian_ket_thuc")
    private LocalDateTime thoiGianKetThuc;

    @Column(length = 10000)
    private String moTa;

    @Column(precision = 20, scale = 0)
    private BigDecimal giamToiDa;

    @Column(name = "trang_thai")
    private Integer trangThai;

    @Column(name = "so_luong")
    private Integer soLuong;

    @Column(name = "gia_tri_giam")
    private Integer giaTriGiam;

    @JsonIgnore
    @OneToMany(mappedBy = "voucher")
    private List<HoaDon> hoaDonList;
}
