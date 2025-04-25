package com.example.demo.core.Admin.model.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdminSanPhamRepuest2 {

    private Integer id;

    @NotBlank(message = "không bỏ trống tên")
    private String ten;

    @NotBlank(message = "không bỏ trống đệm lót")
    private String demLot;

    @NotBlank(message = "không bỏ trống quai đeo")
    private String quaiDeo;

    private Integer thuongHieu;

    private Integer loai;

    @NotBlank(message = "Vui lòng chọn trạng thái")
    private String trangThai;

    @NotBlank(message = "Vui lòng nhập mô tả")
    private String moTa;

    private String anh;

    private Integer khuyenMai;

    @Positive(message = "Không bỏ trống vật liệu")
    private Integer vatLieu;

    @Positive(message = "Không bỏ trống trọng lượng")
    private Integer trongLuong;

    private List<String> giaBan;

  //  private List<String> giaNhap;

    private List<String> soLuongSize;

    private List<String> idMauSac;

    private List<String> idSize;

    private List<String> imgMauSac;

    private List<String> imagesProduct;
}
