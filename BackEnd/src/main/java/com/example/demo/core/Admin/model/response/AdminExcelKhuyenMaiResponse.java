package com.example.demo.core.Admin.model.response;

import com.example.demo.entity.KieuGiamGia;
import com.example.demo.infrastructure.status.ImportStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class AdminExcelKhuyenMaiResponse {
    private String ten;

    private String moTa;

    private LocalDateTime thoiGianBatDau;

    private LocalDateTime thoiGianKetThuc;

    private KieuGiamGia kieuGiamGia;

    private Integer giaTriGiam;

    private BigDecimal giamToiDa;

    private String importMessageTen;

    private String importMessageThoiGianBatDau;

    private String importMessageThoiGianKetThuc;

    private String importMessageMoTa;

    private String importMessageGiamToiDa;

    private String importMessageGiaTriGiam;

    private String importMessageKieuGiamGia;


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
