package com.example.demo.core.Admin.model.response;

import com.example.demo.infrastructure.status.ImportStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class AdminExcelAddSanPhamResponse {

    private String tenSanPham;

    private Integer idVatLieu;

    private String tenVatLieu;

    private Integer valueTrongLuong;

    private Integer idTrongLuong;

    private List<String> giaBan;

  //  private List<String> giaNhap;


    private List<String> tenMau;

    private List<String> tenSize;

    private String moTa;

    private String demLot;

    private String quaiDeo;

    private String tenThuongHieu;

    private String tenLoai;

    private Integer idLoai;

    private Integer idThuongHieu;

    private String anhChinh;

    private List<String> soLuongSize;

    private List<String> soLuongMauSac;

    private List<String> idMauSac;

    private List<String> idSize;

    private List<String> imgMauSac;

    private List<String> imagesProduct;

    private String importMessageSanPham;

    private String importMessageVatLieu;

    private String importMessageTrongLuong;

    private String importMessageGiaBan;

  //  private String importMessageGiaNhap;

    private String importMessageImageMau;

    private String importMessageSoLuongMau;

    private String importMessageSize;

    private String importMessageMauSac;

    private String importMessageLoai;

    private String importMessageThuongHieu;

    private String importMessageSoLuongSize;

    private String importMessageAnhChinh;

    private String importMessageQuaiDeo;

    private String importMessageDemLot;


    private boolean isError;

    private ImportStatus importStatus;

    public ImportStatus getImportStatus() {
        if (isError) {
            importStatus = ImportStatus.FAIL;
        } else {
            importStatus = ImportStatus.SUCCESS;
        }
        return importStatus;
    }
}
