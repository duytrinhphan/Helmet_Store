package com.example.demo.core.Admin.service.impl.SanPham;

import com.example.demo.core.Admin.model.request.AdminSanPhamRepuest2;
import com.example.demo.core.Admin.model.request.AdminSanPhamRequest;
import com.example.demo.core.Admin.model.response.AdminImageResponse;
import com.example.demo.core.Admin.model.response.AdminSanPhamChiTiet2Response;
import com.example.demo.core.Admin.model.response.AdminSanPhamResponse;
import com.example.demo.core.Admin.model.response.SanPhamDOT;
import com.example.demo.core.Admin.repository.AdChiTietSanPhamReponsitory;
import com.example.demo.core.Admin.repository.AdImageReponsitory;
import com.example.demo.core.Admin.repository.AdSanPhamReponsitory;
import com.example.demo.core.Admin.service.AdSanPhamService.AdSanPhamService;
import com.example.demo.entity.*;
import com.example.demo.infrastructure.status.ChiTietSanPhamStatus;
import com.example.demo.util.DatetimeUtil;
import com.example.demo.util.ImageToAzureUtil;
import com.microsoft.azure.storage.StorageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class SanPhamServiceImpl implements AdSanPhamService {


    @Autowired
    private AdSanPhamReponsitory sanPhamReponsitory;

    @Autowired
    private AdChiTietSanPhamReponsitory chiTietSanPhamReponsitory;

    @Autowired
    ImageToAzureUtil getImageToAzureUtil;

    @Autowired
    private AdImageReponsitory imageReponsitory;

    @Override
    public List<AdminSanPhamResponse> getAll() {
        return sanPhamReponsitory.getAll();
    }

    public List<SanPhamDOT> getAlls() {
        List<SanPhamDOT> sanPhamDOTS = new ArrayList<>();
        for (AdminSanPhamResponse o : sanPhamReponsitory.getAll()) {
            List<AdminImageResponse> img = this.getProductImages(o.getId());
            List<AdminSanPhamChiTiet2Response> spct = this.findBySanPhamCT(o.getId());


            sanPhamDOTS.add(new SanPhamDOT(img, spct,
                    o.getId(), o.getTen(), o.getMoTa(), o.getMa(), o.getTrangThai()
                    , o.getNgayTao(), o.getSoLuongTon(), o.getQuaiDeo(), o.getDemLot()
                    , o.getVatLieu(), o.getLoai(), o.getThuongHieu(), o.getAnh(), o.getNgaySua()));
        }

        return sanPhamDOTS;
    }

    @Override
    public SanPhamDOT findByIdSP(Integer id) {
        AdminSanPhamResponse o = sanPhamReponsitory.findByIdSP(id);
        List<AdminImageResponse> img = this.getProductImages(o.getId());
        List<AdminSanPhamChiTiet2Response> spct = this.findBySanPhamCT(o.getId());
        SanPhamDOT sanPhamDOT = new SanPhamDOT(img, spct,
                o.getId(), o.getTen(), o.getMoTa(), o.getMa(), o.getTrangThai()
                , o.getNgayTao(), o.getSoLuongTon(), o.getQuaiDeo(), o.getDemLot()
                , o.getVatLieu(), o.getLoai(), o.getThuongHieu(), o.getAnh(), o.getNgaySua());

        return sanPhamDOT;
    }

    @Override
    public List<AdminSanPhamChiTiet2Response> findBySanPhamCT(Integer id) {
        return sanPhamReponsitory.get(id);
    }

    @Override
    public List<AdminImageResponse> getProductImages(Integer idProduct) {
        return imageReponsitory.findBySanPhamIds(idProduct);
    }

    @Override
    public Boolean findBySanPhamTen(AdminSanPhamRepuest2 request) {
        SanPham chiTiet = sanPhamReponsitory.findBySanPhamTenAndTrangThai(request.getTen());
        if (chiTiet != null) {
            if (chiTiet.getThuongHieu().getId() == request.getThuongHieu() && chiTiet.getLoai().getId() == request.getLoai()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public List<SanPhamDOT> loc(String comboBoxValue) {
        List<SanPhamDOT> sanPhamDOTS = new ArrayList<>();
        for (AdminSanPhamResponse o : sanPhamReponsitory.loc(comboBoxValue)) {
            List<AdminImageResponse> img = this.getProductImages(o.getId());
            List<AdminSanPhamChiTiet2Response> spct = this.findBySanPhamCT(o.getId());


            sanPhamDOTS.add(new SanPhamDOT(img, spct,
                    o.getId(), o.getTen(), o.getMoTa(), o.getMa(), o.getTrangThai()
                    , o.getNgayTao(), o.getSoLuongTon(), o.getQuaiDeo(), o.getDemLot()
                    , o.getVatLieu(), o.getLoai(), o.getThuongHieu(), o.getAnh(), o.getNgaySua()));
        }

        return sanPhamDOTS;
    }


    public List<AdminSanPhamChiTiet2Response> locCTSP(String comboBoxValue) {
        return sanPhamReponsitory.locSPCT(comboBoxValue);
    }

    @Override
    public List<AdminSanPhamResponse> getSanPhamByIdLoai(Integer idloai) {
        return sanPhamReponsitory.getSanPhamByIdLoai(idloai);
    }

    @Override
    public List<AdminSanPhamResponse> getSanPhamByIdThuongHieu(Integer idthuonghieu) {
        return sanPhamReponsitory.getSanPhamByIdThuongHieu(idthuonghieu);
    }

    @Override
    public SanPhamDOT save(AdminSanPhamRepuest2 request) throws IOException, StorageException, InvalidKeyException, URISyntaxException {
        String linkAnh = getImageToAzureUtil.uploadImageToAzure(request.getAnh());
        AdminSanPhamRequest sanPhamRequest = AdminSanPhamRequest.builder()
                .loai(request.getLoai())
                .thuongHieu(request.getThuongHieu())
                .demLot(request.getDemLot())
                .moTa(request.getMoTa())
                .ten(request.getTen())
                .vatLieu(request.getVatLieu())
                .quaiDeo(request.getQuaiDeo())
                .anh(linkAnh)
                .build();
        SanPham sanPham = this.saveSanPham(sanPhamRequest);
        if (request.getImagesProduct() == null || request.getImagesProduct().isEmpty()) {
            this.saveImage(sanPham, request.getImgMauSac());
        } else {
            for (String img : request.getImagesProduct()) {
                request.getImgMauSac().add(img);
            }
            this.saveImage(sanPham, request.getImgMauSac());
        }
        List<SanPhamChiTiet> saveSanPhamChiTiet = this.saveSanPhamChiTiet(request, sanPham);
        return this.findByIdSP(sanPham.getId());
    }

    public SanPham saveSanPham(AdminSanPhamRequest request) {
        SanPham sanPham = request.dtoToEntity(new SanPham());
        SanPham sanPhamSave = sanPhamReponsitory.save(sanPham);
        // lưu ma theo dạng SP + id vừa tương ứng
        sanPhamSave.setMa("SP" + sanPhamSave.getId());
        return sanPhamReponsitory.save(sanPhamSave);
    }

    @Override
    public List<SanPhamChiTiet> saveSanPhamChiTiet(AdminSanPhamRepuest2 repuest2, SanPham sanPham) throws URISyntaxException, StorageException, InvalidKeyException, IOException {

        List<SanPhamChiTiet> lstsanPhamChiTiet = new ArrayList<>();
        if (repuest2.getIdSize() == null || repuest2.getIdSize().isEmpty()) {
            lstsanPhamChiTiet = this.saveSanPhamIfIdSizeNull(lstsanPhamChiTiet, repuest2, sanPham);
        } else {
            lstsanPhamChiTiet = this.saveSanPhamIfIdSizenotNull(lstsanPhamChiTiet, repuest2, sanPham);
        }

        List<SanPhamChiTiet> lstChiTiet = chiTietSanPhamReponsitory.saveAll(lstsanPhamChiTiet);
        return lstsanPhamChiTiet;
    }


    public List<SanPhamChiTiet> saveSanPhamIfIdSizeNull(List<SanPhamChiTiet> lstsanPhamChiTiet, AdminSanPhamRepuest2 repuest2, SanPham sanPham) throws IOException, StorageException, InvalidKeyException, URISyntaxException {
        for (int i = 0; i < repuest2.getIdMauSac().size(); i++) {
            Integer idMau = Integer.valueOf(repuest2.getIdMauSac().get(i));
            String imgMauSacValue = repuest2.getImgMauSac().get(i);
            String soluong = repuest2.getSoLuongSize().get(i);
            BigDecimal giaBan = BigDecimal.valueOf(Long.valueOf(repuest2.getGiaBan().get(i)));
         //   BigDecimal giaNhap = BigDecimal.valueOf(Long.valueOf(repuest2.getGiaNhap().get(i)));

            SanPhamChiTiet chiTiet = new SanPhamChiTiet();
            chiTiet.setTrangThai(ChiTietSanPhamStatus.CON_HANG);
            chiTiet.setSanPham(sanPham);
         //   chiTiet.setGiaNhap(giaNhap);
            chiTiet.setGiaBan(giaBan);
            chiTiet.setSoLuongTon(Integer.valueOf(soluong));
            String linkAnh = getImageToAzureUtil.uploadImageToAzure(imgMauSacValue);
            chiTiet.setAnh(linkAnh);
            chiTiet.setNgayTao(DatetimeUtil.getCurrentDate());
            chiTiet.setSize(null);
            chiTiet.setMauSac(MauSac.builder().id(idMau).build());
            chiTiet.setTrongLuong(TrongLuong.builder().id(repuest2.getTrongLuong()).build());
            lstsanPhamChiTiet.add(chiTiet);
        }
        List<SanPhamChiTiet> lstChiTiet = chiTietSanPhamReponsitory.saveAll(lstsanPhamChiTiet);
        for (int i = 0; i < lstChiTiet.size(); i++) {
            SanPhamChiTiet sanPhamChiTiet = lstChiTiet.get(i);
            sanPhamChiTiet.setMa("CTSP"+sanPhamChiTiet.getId());
            chiTietSanPhamReponsitory.save(sanPhamChiTiet);
        }
        return lstChiTiet;
    }

    public List<SanPhamChiTiet> saveSanPhamIfIdSizenotNull(List<SanPhamChiTiet> lstsanPhamChiTiet, AdminSanPhamRepuest2 repuest2, SanPham sanPham) throws IOException, StorageException, InvalidKeyException, URISyntaxException {
        Set<String> uniqueMauSacIds = new HashSet<>(repuest2.getIdMauSac());
        List<String> sortedMauSacIds = uniqueMauSacIds.stream()
                .sorted((o1, o2) -> Integer.valueOf(o2).compareTo(Integer.valueOf(o1)))
                .collect(Collectors.toList());
        for (String idSize : repuest2.getIdSize()) {
            for (String idMauSac : sortedMauSacIds) {
                Integer idMau = Integer.valueOf(idMauSac);
                SanPhamChiTiet chiTiet = new SanPhamChiTiet();
                chiTiet.setSanPham(sanPham);
                chiTiet.setNgayTao(DatetimeUtil.getCurrentDate());
                chiTiet.setSize(Size.builder().id(Integer.valueOf(idSize)).build());
                chiTiet.setMauSac(MauSac.builder().id(idMau).build());
                chiTiet.setTrongLuong(TrongLuong.builder().id(repuest2.getTrongLuong()).build());
                lstsanPhamChiTiet.add(chiTiet);
            }
        }
        List<SanPhamChiTiet> lstChiTiet = chiTietSanPhamReponsitory.saveAll(lstsanPhamChiTiet);
        for (int i = 0; i < lstChiTiet.size(); i++) {
            SanPhamChiTiet sanPhamChiTiet = lstChiTiet.get(i);
            String imgMauSacValue = repuest2.getImgMauSac().get(i);
            BigDecimal giaBan = BigDecimal.valueOf(Long.valueOf(repuest2.getGiaBan().get(i)));
        //    BigDecimal giaNhap = BigDecimal.valueOf(Long.valueOf(repuest2.getGiaNhap().get(i)));
            sanPhamChiTiet.setTrangThai(ChiTietSanPhamStatus.CON_HANG);
            sanPhamChiTiet.setMa("CTSP"+sanPhamChiTiet.getId());
            sanPhamChiTiet.setGiaBan(giaBan);
            sanPhamChiTiet.setSoLuongTon(Integer.valueOf(repuest2.getSoLuongSize().get(i)));
            String linkAnh = getImageToAzureUtil.uploadImageToAzure(imgMauSacValue);
            sanPhamChiTiet.setAnh(linkAnh);
            chiTietSanPhamReponsitory.save(sanPhamChiTiet);
        }

        return lstChiTiet;
    }


    @Override
    public SanPhamDOT delete(Integer id) {
        SanPham sanPham = sanPhamReponsitory.findById(id).get();
        if (sanPham != null) {
            sanPham.setTrangThai(0);
            sanPhamReponsitory.save(sanPham);
        }
        return this.findByIdSP(id);
    }

    @Override
    public SanPhamDOT khoiPhuc(Integer id) {
        SanPham sanPham = sanPhamReponsitory.findById(id).get();
        if (sanPham != null) {
            sanPham.setTrangThai(3);
            sanPhamReponsitory.save(sanPham);
        }
        return this.findByIdSP(sanPham.getId());
    }

    public List<Image> saveImage(SanPham sanPham, List<String> imgSanPham) throws IOException, StorageException, InvalidKeyException, URISyntaxException {
        HashSet<String> uniqueImgLinks = new HashSet<>(imgSanPham);
        List<Image> imageList = new ArrayList<>();
        for (String img : uniqueImgLinks) {
            Image image = new Image();
            String linkAnh = getImageToAzureUtil.uploadImageToAzure(img);
            image.setAnh(linkAnh);
            image.setSanPham(sanPham);
            image.setTrangThai(1);
            image.setNgayTao(DatetimeUtil.getCurrentDate());
            imageList.add(image);
        }
        List<Image> images = this.imageReponsitory.saveAll(imageList);
        for (int i = 0; i < images.size(); i++) {
            Image image = images.get(i);
            image.setMa("IM" + images.get(i).getId());
        }
        return this.imageReponsitory.saveAll(images);
    }
}
