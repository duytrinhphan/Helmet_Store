package com.example.demo.core.Admin.service.impl;

import com.example.demo.core.Admin.model.response.AdminExcelKhuyenMaiBO;
import com.example.demo.core.Admin.model.response.AdminExcelKhuyenMaiResponse;
import com.example.demo.core.Admin.repository.AdKhuyenMaiReponsitory;
import com.example.demo.entity.KhuyenMai;
import com.example.demo.entity.KieuGiamGia;
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
public class AdExcelKhuyenMaiServiceImpl {

    @Autowired
    private AdKhuyenMaiReponsitory adKhuyenMaiReponsitory;

    public AdminExcelKhuyenMaiBO previewDataImportExcel(MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        Workbook workbook = new XSSFWorkbook(inputStream);

        Sheet sheet = workbook.getSheetAt(0);

        List<AdminExcelKhuyenMaiResponse> list = StreamSupport.stream(sheet.spliterator(), false)
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
                .collect(Collectors.groupingBy(AdminExcelKhuyenMaiResponse::isError, Collectors.counting()));

        // set tổng số bản ghi lỗi, tổng số bản ghi thành công, tổng số bản ghi
        AdminExcelKhuyenMaiBO adminExcelKhuyenMaiBO = new AdminExcelKhuyenMaiBO();
        adminExcelKhuyenMaiBO.setResponseList(list);
        adminExcelKhuyenMaiBO.setTotal(Long.parseLong(String.valueOf(list.size())));
        this.savaData(adminExcelKhuyenMaiBO);
        adminExcelKhuyenMaiBO.setTotalError(importStatusCounts.getOrDefault(true, 0L));
        adminExcelKhuyenMaiBO.setTotalSuccess(importStatusCounts.getOrDefault(false, 0L));

        return adminExcelKhuyenMaiBO;
    }

    public AdminExcelKhuyenMaiResponse processRow(Row row) throws Exception {
        AdminExcelKhuyenMaiResponse userDTO = new AdminExcelKhuyenMaiResponse();

        Long stt = ExcelUtils.getCellLong(row.getCell(0));
        String ten = ExcelUtils.getCellString(row.getCell(1));
        Long thoiGianBatDau = ExcelUtils.getCellDatetime(row.getCell(2));
        Long thoiGianKetThuc = ExcelUtils.getCellDatetime(row.getCell(3));
        Long giamToiDa = ExcelUtils.getCellLong(row.getCell(4));
        Long giaTriGiam = ExcelUtils.getCellLong(row.getCell(5));
        String moTa = ExcelUtils.getCellString(row.getCell(6));
       // String kieuGiamGia = ExcelUtils.getCellString(row.getCell(6));


        LocalDateTime batDauDateTime = Instant.ofEpochMilli(thoiGianBatDau)
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        LocalDateTime ketThucDateTime = Instant.ofEpochMilli(thoiGianKetThuc)
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        LocalDateTime ngayHienTai = LocalDateTime.now();

        KhuyenMai khuyenMai = adKhuyenMaiReponsitory.findKhuyenMaiByTen(ten);

        if (DataUltil.isNullObject(ten)) {
            userDTO.setImportMessageTen("Tên khuyến mại không được để trống tại vị trí: " + stt);
            userDTO.setError(true);

        } else if (!DataUltil.isNullObject(khuyenMai)) {
            userDTO.setImportMessageTen("khuyến mại đã tồn tại, tại vị trí: " + stt);
            userDTO.setError(true);

        } else if (batDauDateTime.isBefore(ngayHienTai)) {
            userDTO.setImportMessageThoiGianBatDau("Thời gian bắt đầu đã qua.");
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

        } else {

                if (giaTriGiam <= 0) {
                    userDTO.setImportMessageGiaTriGiam("giá trị giảm phải lớn hơn 0");
                    userDTO.setError(true);

                } else if (giaTriGiam >= 100) {
                    userDTO.setImportMessageGiaTriGiam("giá trị giảm phải nhỏ hơn 100");
                    userDTO.setError(true);

                } else if (giamToiDa <= 0) {
                    userDTO.setImportMessageGiamToiDa("Tiền giảm tối đa phải lớn hơn 0");
                    userDTO.setError(true);

                } else {
                    userDTO.setKieuGiamGia(KieuGiamGia.GIAM_THEO_PHAN_TRAM);
                    userDTO.setImportMessageKieuGiamGia("SUCCESS");
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
                    if (DataUltil.isNullObject(moTa)) {
                        userDTO.setMoTa(null);
                        userDTO.setImportMessageMoTa("SUCCESS");
                    } else {
                        userDTO.setMoTa(moTa);
                        userDTO.setImportMessageMoTa("SUCCESS");
                    }
                    userDTO.setError(false);
                }

        }
        return userDTO;
    }

    public AdminExcelKhuyenMaiBO savaData(AdminExcelKhuyenMaiBO adminExcelAddSanPhamBO) {

        try {
            for (AdminExcelKhuyenMaiResponse o : adminExcelAddSanPhamBO.getResponseList()) {
                if (o.getImportStatus().equals("FAIL")) {
                    return null;
                }
            }
            if (adminExcelAddSanPhamBO.getTotalError() != null && Integer.valueOf(adminExcelAddSanPhamBO.getTotalError().toString()) != 0)
                return null;

            this.saveAllKhuyenMai(adminExcelAddSanPhamBO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return adminExcelAddSanPhamBO;
    }


    public List<KhuyenMai> saveAllKhuyenMai(AdminExcelKhuyenMaiBO khuyenMaiBO) {
        List<KhuyenMai> khuyenMaiList = new ArrayList<>();

        for (AdminExcelKhuyenMaiResponse o : khuyenMaiBO.getResponseList()) {
            KhuyenMai khuyenMai = new KhuyenMai();
            khuyenMai.setTen(o.getTen());
            khuyenMai.setThoiGianBatDau(o.getThoiGianBatDau());
            khuyenMai.setThoiGianKetThuc(o.getThoiGianKetThuc());
            khuyenMai.setMoTa(o.getMoTa());
            khuyenMai.setGiaTriGiam(o.getGiaTriGiam());
            khuyenMai.setGiamToiDa(o.getGiamToiDa());
            khuyenMai.setKieuGiamGia(o.getKieuGiamGia());
            LocalDateTime thoiGianHienTai = LocalDateTime.now();

            if (khuyenMai.getThoiGianKetThuc().isBefore(thoiGianHienTai)) {
                khuyenMai.setTrangThai(1);
            } else if (khuyenMai.getThoiGianBatDau().isAfter(thoiGianHienTai)) {
                khuyenMai.setTrangThai(3);
            } else {
                khuyenMai.setTrangThai(0);
            }

            // Thêm khuyenMai vào danh sách
            khuyenMaiList.add(khuyenMai);

            // Lưu mỗi đối tượng khuyenMai riêng lẻ vào database
            adKhuyenMaiReponsitory.save(khuyenMai);
        }

        return khuyenMaiList;
    }

}
