//package com.example.demo.util;
//
//import com.example.demo.entity.SanPhamChiTiet;
//import jakarta.servlet.ServletOutputStream;
//import jakarta.servlet.http.HttpServletResponse;
//import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.usermodel.CellStyle;
//import org.apache.poi.ss.usermodel.HorizontalAlignment;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.ss.util.CellRangeAddress;
//import org.apache.poi.xssf.usermodel.XSSFFont;
//import org.apache.poi.xssf.usermodel.XSSFSheet;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//
//import java.io.IOException;
//import java.util.List;
//import java.util.concurrent.atomic.AtomicInteger;
//
//public class ExcelExportUtils {
//
//    private XSSFWorkbook workbook;
//    private XSSFSheet sheet;
//    private List<SanPhamChiTiet> phamChiTietList;
//
//    public ExcelExportUtils(List<SanPhamChiTiet> phamChiTietList) {
//        this.phamChiTietList = phamChiTietList;
//        workbook = new XSSFWorkbook();
//    }
//
//    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
//        sheet.autoSizeColumn(columnCount);
//        Cell cell = row.createCell(columnCount);
//        if (value instanceof Integer) {
//            cell.setCellValue((Integer) value);
//        } else if (value instanceof Double) {
//            cell.setCellValue((Double) value);
//        } else if (value instanceof Boolean) {
//            cell.setCellValue((Boolean) value);
//        } else if (value instanceof Long) {
//            cell.setCellValue((Long) value);
//        } else {
//            cell.setCellValue((String) value);
//        }
//        cell.setCellStyle(style);
//    }
//
//    private void createHeaderRow() {
//        sheet = workbook.createSheet("list san pham");
//        Row row = sheet.createRow(0);
//        CellStyle style = workbook.createCellStyle();
//        XSSFFont font = workbook.createFont();
//        font.setBold(true);
//        font.setFontHeight(20);
//        style.setFont(font);
//        style.setAlignment(HorizontalAlignment.CENTER);
//        createCell(row, 0, "list san pham", style);
//        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 8));
//        font.setFontHeightInPoints((short) 10);
//
//        row = sheet.createRow(1);
//        font.setBold(true);
//        font.setFontHeight(16);
//        style.setFont(font);
//        createCell(row, 0, "ID", style);
//        createCell(row, 1, "Mã sản phẩm", style);
//        createCell(row, 2, "Tên sản phẩm", style);
//        createCell(row, 3, "Ngày tạo", style);
//        createCell(row, 4, "trạng thái", style);
//        createCell(row, 5, "số lượng", style);
//        createCell(row, 6, "giá bán ", style);
//        createCell(row, 7, "giá nhập", style);
//        createCell(row, 8, "vật liệu", style);
//        createCell(row, 9, "trọng lượng", style);
//        createCell(row, 10, "màu sắc", style);
//        createCell(row, 15, "size", style);
//    }
//
//    private void writeCustomerData() {
//        int rowCount = 2;
//        CellStyle style = workbook.createCellStyle();
//        XSSFFont font = workbook.createFont();
//        font.setFontHeight(10);
//        style.setFont(font);
//
//        for (SanPhamChiTiet sanPhamChiTiet : phamChiTietList) {
//            Row row = sheet.createRow(rowCount++);
//            AtomicInteger columnCount = new AtomicInteger();
//            createCell(row, columnCount.getAndIncrement(), sanPhamChiTiet.getId(), style);
//            createCell(row, columnCount.getAndIncrement(), sanPhamChiTiet.getSanPham().getMa(), style);
//            createCell(row, columnCount.getAndIncrement(), sanPhamChiTiet.getSanPham().getTen(), style);
//            createCell(row, columnCount.getAndIncrement(), sanPhamChiTiet.getNgayTao(), style);
//            createCell(row, columnCount.getAndIncrement(), sanPhamChiTiet.getTrangThai(), style);
//            createCell(row, columnCount.getAndIncrement(), String.valueOf(sanPhamChiTiet.getSoLuongTon()), style);
//            createCell(row, columnCount.getAndIncrement(), String.valueOf(sanPhamChiTiet.getGiaBan()), style);
//            createCell(row, columnCount.getAndIncrement(), String.valueOf(sanPhamChiTiet.getGiaNhap()), style);
//            createCell(row, columnCount.getAndIncrement(), sanPhamChiTiet.getVatLieu().getTen(), style);
//            createCell(row, columnCount.getAndIncrement(), sanPhamChiTiet.getTrongLuong().getDonVi(), style);
//            sanPhamChiTiet.getMauSacChiTiets().forEach(mauSacChiTiet -> createCell(row, columnCount.getAndIncrement(), mauSacChiTiet.getMauSac().getTen(), style));
//            sanPhamChiTiet.getSizeChiTiets().forEach(sizeChiTiet -> createCell(row, columnCount.getAndIncrement(), sizeChiTiet.getSize().getTen(), style));
//        }
//    }
//
//    public void exportDataToExcel(HttpServletResponse response) throws IOException {
//        createHeaderRow();
//        writeCustomerData();
//        ServletOutputStream outputStream = response.getOutputStream();
//        workbook.write(outputStream);
//        workbook.close();
//        outputStream.close();
//    }
//}
