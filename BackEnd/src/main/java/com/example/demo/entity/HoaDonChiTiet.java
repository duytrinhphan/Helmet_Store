package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Entity
@Table(name = "hoa_don_chi_tiet")
public class HoaDonChiTiet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @Column(name = "ma")
    private String ma;

    @Column(name = "don_gia", precision = 20, scale = 0)
    private BigDecimal donGia;

    @Column(name = "chiet_khau", precision = 20, scale = 0)
    private BigDecimal chietKhau;

    @Column(name = "so_luong")
    private Integer soLuong;

//    @UpdateTimestamp
    @Column(name = "ngay_sua")
    private String ngaySua;

//    @CreationTimestamp
    @Column(name = "ngay_tao")
    private String ngayTao;

    @Column(name = "trang_thai")
    private Integer trangThai;

    @Column(name = "ly_do")
    private String lyDo;

    @ManyToOne
    @JoinColumn(name = "id_san_pham_chi_tiet")
    private SanPhamChiTiet sanPhamChiTiet;

    @ManyToOne
    @JoinColumn(name = "id_hoa_don")
    private HoaDon hoaDon;

}
