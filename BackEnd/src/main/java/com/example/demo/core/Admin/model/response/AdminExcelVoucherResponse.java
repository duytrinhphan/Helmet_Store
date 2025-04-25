package com.example.demo.core.Admin.model.response;

import com.example.demo.infrastructure.status.ImportStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Getter
@Setter
public class AdminExcelVoucherResponse {

    private String ten;

    private LocalDateTime thoiGianBatDau;

    private LocalDateTime thoiGianKetThuc;

    private String moTa;

    private BigDecimal giamToiDa;

    private Integer soLuong;

    private Integer giaTriGiam;

    private String importMessageTen;

    private String importMessageThoiGianBatDau;

    private String importMessageThoiGianKetThuc;

    private String importMessageMoTa;

    private String importMessageGiamToiDa;

    private String importMessageSoLuong;

    private String importMessageGiaTriGiam;


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
