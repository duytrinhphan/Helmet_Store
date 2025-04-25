package com.example.demo.core.Admin.service;

import com.example.demo.core.Admin.model.response.AdminExcelAddSanPhamBO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface AdExcelAddSanPhamService {
    AdminExcelAddSanPhamBO previewDataImportExcel(MultipartFile file) throws IOException;
}
