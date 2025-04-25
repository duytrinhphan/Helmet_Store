package com.example.demo.core.Admin.service.impl;

import com.example.demo.core.Admin.model.request.AdminMauSacRequest;
import com.example.demo.entity.MauSac;
import com.example.demo.reponsitory.MauSacReponsitory;
import com.example.demo.core.Admin.service.AdMauSacService;
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
import java.util.*;
@Service
public class MauSacServiceImpl implements AdMauSacService {
    @Autowired
    private MauSacReponsitory mauSacReponsitory;

    @Override
    public Page<MauSac> getAll(Integer page) {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(page, 5, sort);
        return mauSacReponsitory.findAll(pageable);
    }

    @Override
    public List<MauSac> findAll() {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        return mauSacReponsitory.findAll(sort);
    }

    @Override
    public List<MauSac> getAllByTrangThai(Integer trangThai) {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        return mauSacReponsitory.findAllByTrangThai(trangThai, sort);
    }

    @Override
    public MauSac getById(Integer id) {
        Optional<MauSac> optional = this.mauSacReponsitory.findById(id);
        return optional.isPresent() ? optional.get() : null;
    }

    @Override
    public Page<MauSac> search(String keyword, Integer page) {
        return null;
    }

    @Override
    public HashMap<String, Object> add(AdminMauSacRequest dto) {
        dto.setTrangThai("1");
        dto.setNgayTao(DatetimeUtil.getCurrentDate());
        MauSac mauSac = dto.dtoToEntity(new MauSac());
        try {
            MauSac mauSacs = mauSacReponsitory.save(mauSac);
            mauSacs.setMa("MS" + mauSacs.getId());
            return DataUltil.setData("success", mauSacReponsitory.save(mauSacs));
        } catch (Exception e) {
            return DataUltil.setData("error", "error");
        }
    }

    @Override
    public HashMap<String, Object> update(AdminMauSacRequest dto, Integer id) {
        Optional<MauSac> optional = mauSacReponsitory.findById(id);
        if (optional.isPresent()) {
            MauSac mauSac = optional.get();
            mauSac.setMa(mauSac.getMa());
            mauSac.setTen(dto.getTen());
            mauSac.setMoTa(dto.getMoTa());
            mauSac.setNgayTao(mauSac.getNgayTao());
            mauSac.setNgaySua(DatetimeUtil.getCurrentDate());
            try {
                return DataUltil.setData("success", mauSacReponsitory.save(mauSac));
            } catch (Exception e) {
                return DataUltil.setData("error", "error");
            }
        } else {
            return DataUltil.setData("error", "không tìm thấy size để sửa");
        }
    }

    @Override
    public HashMap<String, Object> delete(Integer id) {
        Optional<MauSac> optional = mauSacReponsitory.findById(id);
        if (optional.isPresent()) {
            MauSac mauSac = optional.get();
            mauSac.setTrangThai(0);
            try {
                return DataUltil.setData("success", mauSacReponsitory.save(mauSac));
            } catch (Exception e) {
                return DataUltil.setData("error", "error");
            }
        } else {
            return DataUltil.setData("error", "không tìm thấy màu sắc để xóa");
        }
    }

    @Override
    public void saveExcel(MultipartFile file) {
        if (this.isValidExcelFile(file)) {
            try {
                List<MauSac> mauSacs = this.getCustomersDataFromExcel(file.getInputStream());
                List<MauSac> saveMauSacs = this.mauSacReponsitory.saveAll(mauSacs);

                for (int i = 0; i < mauSacs.size(); i++) {
                    MauSac mauSac = mauSacs.get(i);
                    mauSac.setMa("MS" + saveMauSacs.get(i).getId());
                    mauSac.setNgayTao(DatetimeUtil.getCurrentDate());
                }

                this.mauSacReponsitory.saveAll(mauSacs);
            } catch (IOException e) {
                throw new IllegalArgumentException("The file is not a valid excel file");
            }
        }

    }
    public static boolean isValidExcelFile(MultipartFile file) {

        return Objects.equals(file.getContentType(), "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    }

    public List<MauSac> getCustomersDataFromExcel(InputStream inputStream) {
        List<MauSac> ListMauSac = new ArrayList<>();
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
                        MauSac mauSac = new MauSac();
                        while (cellIterator.hasNext()) {
                            Cell cell = cellIterator.next();
                            switch (cellIndex) {
                                case 0 -> mauSac.setTen(cell.getStringCellValue());
                                case 1 -> mauSac.setTrangThai((int) cell.getNumericCellValue());

                                default -> {
                                }
                            }
                            cellIndex++;
                        }
                        ListMauSac.add(mauSac);
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
        return ListMauSac;
    }
}
