package com.example.demo.core.Admin.service.impl;

import com.example.demo.core.Admin.model.request.AdminVatLieuRequest;
import com.example.demo.core.Admin.repository.AdVatLieuReponsitory;
import com.example.demo.core.Admin.service.AdVatLieuServcie;
import com.example.demo.entity.VatLieu;
import com.example.demo.util.DataUltil;
import com.example.demo.util.DatetimeUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class VatLieuServiceImpl implements AdVatLieuServcie {
    @Autowired
    private AdVatLieuReponsitory repository;

    @Override
    public Page<VatLieu> getAll(Integer page) {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(page, 5, sort);
        return repository.findAll(pageable);
    }

    @Override
    public List<VatLieu> findAll() {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        return repository.findAll(sort);
    }

    @Override
    public List<VatLieu> getAllByTrangThai(Integer trangThai) {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        return repository.findAllByTrangThai(trangThai, sort);
    }

    @Override
    public VatLieu getById(Integer id) {
        Optional<VatLieu> optional = this.repository.findById(id);
        return optional.isPresent() ? optional.get() : null;
    }

    @Override
    public Page<VatLieu> search(String keyword, Integer page) {
        return null;
    }

    @Override
    public HashMap<String, Object> add(AdminVatLieuRequest dto) {
        dto.setTrangThai(1);
        dto.setNgayTao(DatetimeUtil.getCurrentDate());
        VatLieu vatLieu = dto.dtoToEntity(new VatLieu());
        try {
            VatLieu vatLieu1 = repository.save(vatLieu);
            vatLieu1.setMa("VL" + vatLieu1.getId());
            vatLieu1.setTrangThai(1);
            return DataUltil.setData("success", repository.save(vatLieu1));
        } catch (Exception e) {
            return DataUltil.setData("error", "error");
        }
    }

    @Override
    public HashMap<String, Object> update(AdminVatLieuRequest dto, Integer id) {
        Optional<VatLieu> optional = repository.findById(id);
        if (optional.isPresent()) {
            VatLieu vatLieu = optional.get();
            vatLieu.setMa(vatLieu.getMa());
            vatLieu.setTen(dto.getTen());
            vatLieu.setMoTa(dto.getMoTa());
            vatLieu.setNgayTao(vatLieu.getNgayTao());
            vatLieu.setNgaySua(DatetimeUtil.getCurrentDate());
            try {
                return DataUltil.setData("success", repository.save(vatLieu));
            } catch (Exception e) {
                return DataUltil.setData("error", "error");
            }
        } else {
            return DataUltil.setData("error", "không tìm thấy vật liệu để sửa");
        }
    }

    @Override
    public HashMap<String, Object> delete(Integer id) {
        Optional<VatLieu> optional = repository.findById(id);
        if (optional.isPresent()) {
            VatLieu vatLieu = optional.get();
            vatLieu.setTrangThai(0);
            try {
                return DataUltil.setData("success", repository.save(vatLieu));
            } catch (Exception e) {
                return DataUltil.setData("error", "error");
            }
        } else {
            return DataUltil.setData("error", "không tìm thấy vật liệu để xóa");
        }
    }

    @Override
    public void saveExcel(MultipartFile file) {
        if (this.isValidExcelFile(file)) {
            try {
                List<VatLieu> vatLieus = this.getCustomersDataFromExcel(file.getInputStream());
                List<VatLieu> saveVatLieu = this.repository.saveAll(vatLieus);

                for (int i = 0; i < vatLieus.size(); i++) {
                    VatLieu vatLieu = vatLieus.get(i);
                    vatLieu.setMa("VL" + saveVatLieu.get(i).getId());
                    vatLieu.setNgayTao(DatetimeUtil.getCurrentDate());
                }

                this.repository.saveAll(vatLieus);
            } catch (IOException e) {
                throw new IllegalArgumentException("The file is not a valid excel file");
            }
        }

    }
    public static boolean isValidExcelFile(MultipartFile file) {

        return Objects.equals(file.getContentType(), "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    }

    public List<VatLieu> getCustomersDataFromExcel(InputStream inputStream) {
        List<VatLieu> vatLieuList = new ArrayList<>();
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = workbook.getSheetAt(2);

            if (workbook != null) {
//                System.out.println("Workbook co ton tai");
                if (sheet != null) {
//                    System.out.println("sheet ton tai");
                    int rowIndex = 0;
                    for (Row row : sheet) {
                        if (rowIndex == 0) {
                            rowIndex++;
                            continue;
                        }
                        Iterator<Cell> cellIterator = row.iterator();
                        int cellIndex = 0;
                        VatLieu vatLieu = new VatLieu();
                        while (cellIterator.hasNext()) {
                            Cell cell = cellIterator.next();
                            switch (cellIndex) {
                                case 0 -> vatLieu.setTen(cell.getStringCellValue());
                                case 1 -> vatLieu.setTrangThai((int) cell.getNumericCellValue());

                                default -> {
                                }
                            }
                            cellIndex++;
                        }
                        vatLieuList.add(vatLieu);
                    }
                } else {
                    System.out.println("sheet ko ton tai.");
                }
            } else {
                System.out.println("Workbook is null. Không thể đọc dữ liệu từ Excel.");
            }

        } catch (IOException e) {
            e.getStackTrace();
        }
        return vatLieuList;
    }
}
