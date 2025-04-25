package com.example.demo.core.Admin.service.impl.SanPham;

import com.example.demo.core.Admin.model.request.AdminAddImageRequest;
import com.example.demo.core.Admin.model.request.AdminSanPhamChiTietRequest;
import com.example.demo.core.Admin.model.request.AdminSanPhamRequest;
import com.example.demo.core.Admin.model.response.AdminImageResponse;
import com.example.demo.core.Admin.model.response.AdminSanPhamChiTiet2Response;
import com.example.demo.core.Admin.model.response.AdminSanPhamResponse;
import com.example.demo.core.Admin.model.response.SanPhamDOT;
import com.example.demo.core.Admin.repository.AdChiTietSanPhamReponsitory;
import com.example.demo.core.Admin.repository.AdImageReponsitory;
import com.example.demo.core.Admin.repository.AdSanPhamReponsitory;
import com.example.demo.core.Admin.service.AdSanPhamService.AdUpdateSanPhamService;
import com.example.demo.entity.*;
import com.example.demo.infrastructure.status.ChiTietSanPhamStatus;
import com.example.demo.reponsitory.KhuyenMaiReponsitory;
import com.example.demo.util.DatetimeUtil;
import com.example.demo.util.ImageToAzureUtil;
import com.microsoft.azure.storage.StorageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.util.Optional;
import java.util.concurrent.ExecutionException;


@Service
public class UpdateSanPhamServiceIpml implements AdUpdateSanPhamService {

    @Autowired
    private AdChiTietSanPhamReponsitory chiTietSanPhamReponsitory;

    @Autowired
    private AdImageReponsitory imageReponsitory;


    @Autowired
    private AdSanPhamReponsitory sanPhamReponsitory;

    @Autowired
    private SanPhamServiceImpl sanPhamService;

    @Autowired
    private KhuyenMaiReponsitory khuyenMaiReponsitory;


    @Autowired
    ImageToAzureUtil getImageToAzureUtil;


    @Override
    public AdminSanPhamChiTiet2Response update(AdminSanPhamChiTietRequest dto, Integer id) throws URISyntaxException, StorageException, InvalidKeyException, IOException, ExecutionException, InterruptedException {
        // Lấy sản phẩm chi tiết từ kho dự trữ
        Optional<SanPhamChiTiet> optionalSanPhamChiTiet = chiTietSanPhamReponsitory.findById(id);
        if (optionalSanPhamChiTiet.isPresent()) {
            SanPhamChiTiet sanPhamChiTiet = optionalSanPhamChiTiet.get();
            sanPhamChiTiet.setNgaySua(DatetimeUtil.getCurrentDate());
            sanPhamChiTiet.setGiaBan(BigDecimal.valueOf(dto.getGiaBan()));
         //   sanPhamChiTiet.setGiaNhap(BigDecimal.valueOf(dto.getGiaNhap()));
            sanPhamChiTiet.setSoLuongTon(Integer.valueOf(dto.getSoLuongTon()));
            sanPhamChiTiet.setTrongLuong(TrongLuong.builder().id(dto.getTrongLuong()).build());

            sanPhamChiTiet.setMauSac(MauSac.builder().id(dto.getMauSac()).build());
            sanPhamChiTiet.setTrangThai(dto.getTrangThai());
            if (sanPhamChiTiet.getAnh().equals(dto.getAnh())) {
                sanPhamChiTiet.setAnh(dto.getAnh());
            } else {
                String linkAnh = getImageToAzureUtil.uploadImageToAzure(dto.getAnh());
                sanPhamChiTiet.setAnh(linkAnh);
            }
            if (dto.getIdKhuyenMai() != null && dto.getIdKhuyenMai() != "") {
                KhuyenMai khuyenMai = khuyenMaiReponsitory.findById(Integer.valueOf(dto.getIdKhuyenMai())).get();
                BigDecimal giaBan = sanPhamChiTiet.getGiaBan();
                BigDecimal phanTram = new BigDecimal(khuyenMai.getGiaTriGiam()).divide(new BigDecimal(100));
                BigDecimal giamGia = giaBan.multiply(phanTram);
                BigDecimal giaBanSauGiam = giaBan.subtract(giamGia);
                sanPhamChiTiet.setKhuyenMai(KhuyenMai.builder().id(Integer.valueOf(dto.getIdKhuyenMai())).build());
                sanPhamChiTiet.setGiaSauGiam(giaBanSauGiam);
            } else {
                sanPhamChiTiet.setKhuyenMai(null);
                sanPhamChiTiet.setGiaSauGiam(null);
            }
            if (dto.getSize() != null) {
                sanPhamChiTiet.setSize(Size.builder().id(dto.getSize()).build());
            } else {
                sanPhamChiTiet.setSize(null);
            }
            // Lưu sản phẩm chi tiết đã cập nhật
            SanPhamChiTiet save = chiTietSanPhamReponsitory.save(sanPhamChiTiet);
            return sanPhamReponsitory.getByid(id);
        }

        return null;
    }

    public Boolean check(Integer idSize, Integer idMau, Integer idSP) {
        SanPhamChiTiet sanPhamChiTiet = chiTietSanPhamReponsitory.check(idSize, idMau, idSP);
        if (sanPhamChiTiet != null) {
            return false;
        }
        return true;
    }


    @Override
    public AdminSanPhamChiTiet2Response saveSanPhamChiTiet(AdminSanPhamChiTietRequest dto) throws IOException, StorageException, InvalidKeyException, URISyntaxException {
        SanPhamChiTiet sanPhamChiTiet = new SanPhamChiTiet();
        sanPhamChiTiet.setNgayTao(DatetimeUtil.getCurrentDate());
        sanPhamChiTiet.setSanPham(SanPham.builder().id(dto.getIdSP()).build());
        sanPhamChiTiet.setSize(Size.builder().id(dto.getSize()).build());
        sanPhamChiTiet.setMauSac(MauSac.builder().id(dto.getMauSac()).build());
        sanPhamChiTiet.setTrangThai(dto.getTrangThai());
        sanPhamChiTiet.setTrongLuong(TrongLuong.builder().id(dto.getTrongLuong()).build());
        sanPhamChiTiet.setSoLuongTon(Integer.valueOf(dto.getSoLuongTon()));
        sanPhamChiTiet.setGiaBan(BigDecimal.valueOf(Long.valueOf(dto.getGiaBan())));
       // sanPhamChiTiet.setGiaNhap(BigDecimal.valueOf(Long.valueOf(dto.getGiaNhap())));
        String linkAnh = getImageToAzureUtil.uploadImageToAzure(dto.getAnh());
        sanPhamChiTiet.setAnh(linkAnh);
        if (dto.getIdKhuyenMai() != null && dto.getIdKhuyenMai() != "") {
            KhuyenMai khuyenMai = khuyenMaiReponsitory.findById(Integer.valueOf(dto.getIdKhuyenMai())).get();
            BigDecimal giaBan = sanPhamChiTiet.getGiaBan();
            BigDecimal phanTram = new BigDecimal(khuyenMai.getGiaTriGiam()).divide(new BigDecimal(100));
            BigDecimal giamGia = giaBan.multiply(phanTram);
            BigDecimal giaBanSauGiam = giaBan.subtract(giamGia);
            sanPhamChiTiet.setKhuyenMai(KhuyenMai.builder().id(Integer.valueOf(dto.getIdKhuyenMai())).build());
            sanPhamChiTiet.setGiaSauGiam(giaBanSauGiam);
        } else {
            sanPhamChiTiet.setKhuyenMai(null);
            sanPhamChiTiet.setGiaSauGiam(null);
        }
        if (dto.getSize() != null) {
            sanPhamChiTiet.setSize(Size.builder().id(dto.getSize()).build());
        } else {
            sanPhamChiTiet.setSize(null);
        }
        SanPhamChiTiet sanPhamSave = chiTietSanPhamReponsitory.save(sanPhamChiTiet);
        return sanPhamReponsitory.getByid(sanPhamSave.getId());
    }


    @Override
    public SanPhamDOT updateSanPham(Integer id, AdminSanPhamRequest dto) throws
            IOException, StorageException, InvalidKeyException, URISyntaxException {
        SanPham sanPham = sanPhamReponsitory.findById(id).get();

        if (sanPham != null) {
            sanPham.setNgaySua(DatetimeUtil.getCurrentDate());
            sanPham.setTen(dto.getTen());
            sanPham.setLoai(Loai.builder().id(dto.getLoai()).build());
            sanPham.setThuongHieu(ThuongHieu.builder().id(dto.getThuongHieu()).build());
            sanPham.setVatLieu(VatLieu.builder().id(dto.getVatLieu()).build());
            sanPham.setDemLot(dto.getDemLot());
            sanPham.setTrangThai(dto.getTrangThai());
            sanPham.setMoTa(dto.getMoTa());
            if (sanPham.getAnh().equals(dto.getAnh())) {
                sanPham.setAnh(dto.getAnh());
            } else {
                String linkAnh = getImageToAzureUtil.uploadImageToAzure(dto.getAnh());
                sanPham.setAnh(linkAnh);
            }
            sanPham.setQuaiDeo(dto.getQuaiDeo());
            sanPhamReponsitory.save(sanPham);
        }
        return this.sanPhamService.findByIdSP(sanPham.getId());
    }

    @Override
    public SanPham saveSanPham(AdminSanPhamRequest request) {
        SanPham sanPham = request.dtoToEntity(new SanPham());
        SanPham sanPhamSave = sanPhamReponsitory.save(sanPham);
        // lưu ma theo dạng SP + id vừa tương ứng
        sanPhamSave.setMa("SP" + sanPhamSave.getId());
        return sanPhamReponsitory.save(sanPhamSave);
    }


    @Override
    public AdminSanPhamChiTiet2Response delete(Integer id) {
        SanPhamChiTiet sanPhamChiTiet = chiTietSanPhamReponsitory.findById(id).get();
        if (sanPhamChiTiet != null) {
            sanPhamChiTiet.setNgaySua(DatetimeUtil.getCurrentDate());
            sanPhamChiTiet.setTrangThai(ChiTietSanPhamStatus.XOA);
            chiTietSanPhamReponsitory.save(sanPhamChiTiet);
        }
        return sanPhamReponsitory.getByid(id);
    }

    @Override
    public AdminSanPhamChiTiet2Response khoiPhuc(Integer id) {
        SanPhamChiTiet sanPhamChiTiet = chiTietSanPhamReponsitory.findById(id).get();
        if (sanPhamChiTiet != null) {
            sanPhamChiTiet.setNgaySua(DatetimeUtil.getCurrentDate());
            sanPhamChiTiet.setTrangThai(ChiTietSanPhamStatus.CON_HANG);
            chiTietSanPhamReponsitory.save(sanPhamChiTiet);
        }
        return sanPhamReponsitory.getByid(id);
    }


    @Override
    public AdminImageResponse deleteImg(Integer id) {
        Image image = imageReponsitory.findById(id).get();
        imageReponsitory.delete(image);
        return imageReponsitory.findByIds(id);
    }

    @Override
    public AdminImageResponse updateImage(Integer id, AdminAddImageRequest dto) throws IOException, StorageException, InvalidKeyException, URISyntaxException {

        Image img = this.imageReponsitory.findById(id).get();
        // Kiểm tra xem có ảnh tồn tại hay không
        if (img != null) {
            // Cập nhật thông tin ảnh nếu cần
            if (img.getAnh().equals(dto.getAnh())) {
                img.setAnh(dto.getAnh());
            } else {
                String linkAnh = getImageToAzureUtil.uploadImageToAzure(dto.getAnh());
                img.setAnh(linkAnh);
            }
            this.imageReponsitory.save(img);
            return imageReponsitory.findByIds(id);
        }
        return null;
    }

    @Override
    public AdminImageResponse saveImage(Integer idSP, AdminAddImageRequest adminAddImageRequest) throws IOException, StorageException, InvalidKeyException, URISyntaxException {
        Image image = new Image();
        String linkAnh = getImageToAzureUtil.uploadImageToAzure(adminAddImageRequest.getAnh());
        image.setAnh(linkAnh);
        image.setSanPham(SanPham.builder().id(idSP).build());
        image.setTrangThai(1);
        image.setNgayTao(DatetimeUtil.getCurrentDate());
        Image images = this.imageReponsitory.save(image);
        image.setMa("IM" + images.getId());
        Image imagess = this.imageReponsitory.save(image);
        return imageReponsitory.findByIds(imagess.getId());
    }

}
