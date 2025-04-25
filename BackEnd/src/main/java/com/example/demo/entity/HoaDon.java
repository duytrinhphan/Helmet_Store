package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Entity
@Table(name = "hoa_don")
public class HoaDon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @Column(name = "ma")
    private String ma;

    @Column(name = "tong_tien", precision = 20, scale = 0)
    private BigDecimal tongTien;

    @Column(name = "tien_khach_dua", precision = 20, scale = 0)
    private BigDecimal tienKhachDua;

    @Column(name = "ten_nguoi_nhan")
    private String tenNguoiNhan;

    @Column(name = "ngay_nhan")
    private LocalDateTime ngayNhan;

    @Column(name = "tien_ship", precision = 20, scale = 0)
    private BigDecimal tienShip;

    @Column(name = "tien_sau_khi_giam_gia", precision = 20, scale = 0)
    private BigDecimal tienSauKhiGiam;

    @Column(name = "ly_do")
    private String lyDo;

//    @UpdateTimestamp
    @Column(name = "ngay_sua")
    private LocalDateTime ngaySua;

//    @CreationTimestamp
    @Column(name = "ngay_tao")
    private LocalDateTime ngayTao;

    @Column(name = "trang_thai")
    private Integer trangThai;

    @Column(name = "ngay_thanh_toan")
    private LocalDateTime ngayThanhToan;

    @Column(name = "ngay_ship")
    private LocalDateTime ngayShip;

    @Column(name = "hinh_thuc_giao_hang")
    private Integer hinhThucGiaoHang;

    @Column(name = "mo_ta")
    private String moTa;

    @ManyToOne
    @JoinColumn(name = "id_dia_chi_sdt")
    private DiaChi diaChi;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

    @ManyToOne
    @JoinColumn(name = "id_nguoi_tao")
    private User nguoiTao;

    @ManyToOne
    @JoinColumn(name = "id_phuong_thuc_thanh_toan")
    private PhuongThucThanhToan phuongThucThanhToan;

    @ManyToOne
    @JoinColumn(name = "id_voucher")
    private Voucher voucher;

    @JsonIgnore
    @OneToMany(mappedBy = "hoaDon", fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<HoaDonChiTiet> hoaDonChiTietList;
}
