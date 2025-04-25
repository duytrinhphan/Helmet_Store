package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Entity
@Table(name = "san_pham_chi_tiet")
public class SanPhamChiTiet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @Column(name = "ma")
    private String ma;

    @Column(name = "ngay_sua")
    private String ngaySua;

    @Column(name = "ngay_tao")
    private String ngayTao;

    @Column(name = "anh")
    private String anh;

    @Column(name = "trang_thai")
    private Integer trangThai;

    @Column(name = "so_luong_ton")
    private Integer soLuongTon;

    @Column(name = "gia_ban", precision = 20, scale = 0)
    private BigDecimal giaBan;

    @Column(name = "gia_nhap", precision = 20, scale = 0)
    private BigDecimal giaNhap;

    @Column(name = "gia_sau_giam", precision = 20, scale = 0)
    private BigDecimal giaSauGiam;

    @ManyToOne
    @JoinColumn(name = "id_san_pham")
    private SanPham sanPham;

    @ManyToOne
    @JoinColumn(name = "id_trong_luong")
    private TrongLuong trongLuong;

    @ManyToOne
    @JoinColumn(name = "id_khuyen_mai")
    private KhuyenMai khuyenMai;

    @ManyToOne
    @JoinColumn(name = "id_mau_sac")
    private MauSac mauSac;

    @ManyToOne
    @JoinColumn(name = "id_size")
    private Size size;

    @JsonIgnore
    @OneToMany(mappedBy = "sanPhamChiTiet", fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<GioHangChiTiet> gioHangList;

    @JsonIgnore
    @OneToMany(mappedBy = "sanPhamChiTiet", fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<HoaDonChiTiet> donChiTietList;

    @JsonIgnore
    @OneToMany(mappedBy = "sanPhamChiTiet", fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<ThongBao> thongBaoList;
}
