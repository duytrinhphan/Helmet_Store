package com.example.demo.core.Admin.service.impl;

import com.example.demo.core.Admin.model.response.AdminExcelVoucherBO;
import com.example.demo.core.Admin.model.response.AdminExcelVoucherResponse;
import com.example.demo.core.Admin.repository.AdVoucherReponsitory;
import com.example.demo.entity.Voucher;
import com.example.demo.util.DataUltil;
import com.example.demo.util.ExcelUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class AdExcelVoucherServiceImpl {


    @Autowired
    private AdVoucherReponsitory repository;

    public AdminExcelVoucherBO previewDataImportExcel(MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        Workbook workbook = new XSSFWorkbook(inputStream);

        Sheet sheet = workbook.getSheetAt(0);

        List<AdminExcelVoucherResponse> list = StreamSupport.stream(sheet.spliterator(), false)
                .skip(1) // Bỏ qua 2 dòng đầu tiên
                .filter(row -> !ExcelUtils.checkNullLCells(row, 1))
                .map(row -> {
                    try {
                        return processRow(row);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                })
                .collect(Collectors.toList());


        Map<Boolean, Long> importStatusCounts = list.stream()
                .collect(Collectors.groupingBy(AdminExcelVoucherResponse::isError, Collectors.counting()));

        // set tổng số bản ghi lỗi, tổng số bản ghi thành công, tổng số bản ghi
        AdminExcelVoucherBO adminExcelVoucherBO = new AdminExcelVoucherBO();
        adminExcelVoucherBO.setResponseList(list);
        adminExcelVoucherBO.setTotal(Long.parseLong(String.valueOf(list.size())));
        this.savaData(adminExcelVoucherBO);
        adminExcelVoucherBO.setTotalError(importStatusCounts.getOrDefault(true, 0L));
        adminExcelVoucherBO.setTotalSuccess(importStatusCounts.getOrDefault(false, 0L));

        return adminExcelVoucherBO;
    }

    public AdminExcelVoucherResponse processRow(Row row) throws Exception {
        AdminExcelVoucherResponse userDTO = new AdminExcelVoucherResponse();

        Long stt = ExcelUtils.getCellLong(row.getCell(0));
        String ten = ExcelUtils.getCellString(row.getCell(1));
        Long thoiGianBatDau = ExcelUtils.getCellDatetime(row.getCell(2));
        Long thoiGianKetThuc = ExcelUtils.getCellDatetime(row.getCell(3));
        Long giamToiDa = ExcelUtils.getCellLong(row.getCell(4));
        Long giaTriGiam = ExcelUtils.getCellLong(row.getCell(5));
        Long soLuong = ExcelUtils.getCellLong(row.getCell(6));
        String moTa = ExcelUtils.getCellString(row.getCell(7));


        LocalDateTime batDauDateTime = Instant.ofEpochMilli(thoiGianBatDau)
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        LocalDateTime ketThucDateTime = Instant.ofEpochMilli(thoiGianKetThuc)
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();


        LocalDateTime ngayHienTai = LocalDateTime.now();

        Voucher voucher = repository.getVoucherByTen(ten);

        if (DataUltil.isNullObject(ten)) {
            userDTO.setImportMessageTen("Tên voucher không được để trống tại vị trí: " + stt);
            userDTO.setError(true);

        } else if (!DataUltil.isNullObject(voucher)) {
            userDTO.setImportMessageTen("Voucher đã tồn tại, tại vị trí: " + stt);
            userDTO.setError(true);

        } else if (batDauDateTime.isBefore(ngayHienTai)) {
            userDTO.setImportMessageThoiGianBatDau("Thời gian bắt đầu đã qua.");
            userDTO.setError(true);

        } else if (giaTriGiam <= 0) {
            userDTO.setImportMessageThoiGianBatDau("giá trị giảm phải lớn hơn 0");
            userDTO.setError(true);

        } else if (giaTriGiam >= 100) {
            userDTO.setImportMessageThoiGianBatDau("giá trị giảm phải nhỏ hơn 100");
            userDTO.setError(true);

        } else if (giamToiDa <= 0) {
            userDTO.setImportMessageThoiGianBatDau("Tiền giảm tối đa phải lớn hơn 0");
            userDTO.setError(true);

        } else if (batDauDateTime.isBefore(ngayHienTai)) {
            userDTO.setImportMessageThoiGianBatDau("Thời gian bắt đầu đã qua.");
            userDTO.setError(true);

        } else if (!batDauDateTime.isBefore(ketThucDateTime)) {
            userDTO.setImportMessageThoiGianBatDau("Thời gian bắt đầu trước thời gian kết thúc.");
            userDTO.setError(true);

        } else if (batDauDateTime.isEqual(ketThucDateTime)) {
            userDTO.setImportMessageThoiGianBatDau("Thời gian bắt đầu và kết thúc bằng nhau.");
            userDTO.setError(true);

        } else if (DataUltil.isNullObject(thoiGianBatDau)) {
            userDTO.setImportMessageThoiGianBatDau("Thời gian bắt đầu không được để trống tại vị trí: " + stt);
            userDTO.setError(true);

        } else if (DataUltil.isNullObject(thoiGianKetThuc)) {
            userDTO.setImportMessageThoiGianKetThuc("Thời gian kết thúc không được để trống tại vị trí: " + stt);
            userDTO.setError(true);

        } else if (DataUltil.isNullObject(giamToiDa)) {
            userDTO.setImportMessageGiamToiDa("Giảm tối đa không được để trống tại vị trí: " + stt);
            userDTO.setError(true);

        } else if (DataUltil.isNullObject(giaTriGiam)) {
            userDTO.setImportMessageGiaTriGiam("Giá trị giảm không được để trống tại ví trí: " + stt);
            userDTO.setError(true);

        } else if (DataUltil.isNullObject(soLuong)) {
            userDTO.setImportMessageSoLuong("Số lượng không được để trống tại vị trí: " + stt);
            userDTO.setError(true);

        } else if (soLuong <= 0) {
            userDTO.setImportMessageSoLuong("Số lượng phải lớn hơn 0 tại vị trí: " + stt);
            userDTO.setError(true);

        } else {
            userDTO.setTen(ten);
            userDTO.setImportMessageTen("SUCCESS");
            userDTO.setThoiGianBatDau(batDauDateTime);
            userDTO.setImportMessageThoiGianBatDau("SUCCESS");
            userDTO.setThoiGianKetThuc(ketThucDateTime);
            userDTO.setImportMessageThoiGianKetThuc("SUCCESS");
            userDTO.setGiamToiDa(BigDecimal.valueOf(giamToiDa));
            userDTO.setImportMessageGiamToiDa("SUCCESS");
            userDTO.setGiaTriGiam((int) giaTriGiam.longValue());
            userDTO.setImportMessageGiaTriGiam("SUCCESS");
            userDTO.setSoLuong((int) soLuong.longValue());
            userDTO.setImportMessageSoLuong("SUCCESS");
            if (DataUltil.isNullObject(moTa)) {
                userDTO.setMoTa(null);
                userDTO.setImportMessageMoTa("SUCCESS");
            } else {
                userDTO.setMoTa(moTa);
                userDTO.setImportMessageMoTa("SUCCESS");
            }
            userDTO.setError(false);

        }
        return userDTO;
    }

    public AdminExcelVoucherBO savaData(AdminExcelVoucherBO adminExcelAddSanPhamBO) {

        try {
            for (AdminExcelVoucherResponse o : adminExcelAddSanPhamBO.getResponseList()) {
                if (o.getImportStatus().equals("FAIL")) {
                    return null;
                }
            }
            if (adminExcelAddSanPhamBO.getTotalError() != null && Integer.valueOf(adminExcelAddSanPhamBO.getTotalError().toString()) != 0)
                return null;

            this.saveAllVoucher(adminExcelAddSanPhamBO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return adminExcelAddSanPhamBO;
    }


    public List<Voucher> saveAllVoucher(AdminExcelVoucherBO khuyenMaiBO) {
        List<Voucher> lstVoucher = new ArrayList<>();

        for (AdminExcelVoucherResponse o : khuyenMaiBO.getResponseList()) {
            Voucher voucher = new Voucher();
            voucher.setTen(o.getTen());
            voucher.setThoiGianBatDau(o.getThoiGianBatDau());
            voucher.setThoiGianKetThuc(o.getThoiGianKetThuc());
            voucher.setSoLuong(o.getSoLuong());
            voucher.setMoTa(o.getMoTa());
            voucher.setGiaTriGiam(o.getGiaTriGiam());
            voucher.setGiamToiDa(o.getGiamToiDa());
            LocalDateTime thoiGianHienTai = LocalDateTime.now();
            if (voucher.getThoiGianKetThuc().isBefore(thoiGianHienTai)) {
                voucher.setTrangThai(1);
            } else if (voucher.getThoiGianBatDau().isAfter(thoiGianHienTai)) {
                voucher.setTrangThai(3);
            } else {
                voucher.setTrangThai(0);
            }
            // Thêm  vào danh sách
            lstVoucher.add(voucher);

            // Lưu mỗi đối tượng  riêng lẻ vào database
            repository.save(voucher);
        }

        return lstVoucher;
    }

}
