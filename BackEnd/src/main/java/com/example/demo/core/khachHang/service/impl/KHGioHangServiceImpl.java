package com.example.demo.core.khachHang.service.impl;

import com.example.demo.core.Admin.repository.AdUserRepository;
import com.example.demo.core.khachHang.model.request.GioHangCTRequest;
import com.example.demo.core.khachHang.model.request.KhGioHangChiTietSessionRequest;
import com.example.demo.core.khachHang.model.response.GioHangCTResponse;
import com.example.demo.core.khachHang.model.response.KhCartBO;
import com.example.demo.core.khachHang.model.response.KhVoucherResponse;
import com.example.demo.core.khachHang.repository.ChiTietSanPhamRepository;
import com.example.demo.core.khachHang.repository.KHGioHangChiTietRepository;
import com.example.demo.core.khachHang.repository.KHGioHangRepository;
import com.example.demo.core.khachHang.repository.KHSanPhamRepository;
import com.example.demo.core.khachHang.service.KHGiohangService;
import com.example.demo.core.token.service.TokenService;
import com.example.demo.entity.*;
import com.example.demo.util.DataUltil;
import com.example.demo.util.DatetimeUtil;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class KHGioHangServiceImpl implements KHGiohangService {
    @Autowired
    KHGioHangChiTietRepository gioHangCTRespon;

    @Autowired
    ChiTietSanPhamRepository productReponstory;

    @Autowired
    KHSanPhamRepository khSanPhamRepository;

    @Autowired
    KHGioHangRepository giohangRepo;

    @Autowired
    TokenService tokenService;

    @Autowired
    AdUserRepository userRepository;


    public HashMap<String, Object> addCart(GioHangCTRequest ghct, String token) {
        Integer idKh;
        if (tokenService.getUserNameByToken(token) == null) {
            return null;
        }
        String userName = tokenService.getUserNameByToken(token);
        User user = userRepository.findByUserName(userName);
        idKh = user.getId();
        GioHangChiTiet map = this.addCartDataBase(ghct, idKh);
        return DataUltil.setData("success", map);

    }

    public GioHangChiTiet addCartDataBase(GioHangCTRequest ghct, Integer idKh) {

        SanPhamChiTiet sanPhamCT = productReponstory.findSanPhamChiTietsById(ghct.getSanPhamChiTiet());
        if (ghct.getSoLuong() > sanPhamCT.getSoLuongTon()) {
//            HashMap<String, Object> map = DataUltil.setData("error", "số lượng sản phẩm không đủ");
//            return map;
            return null;
        }
        if (ghct.getSoLuong() <= 0) {
            return null;
        }
        User kh = User.builder().id(idKh).build();

        // tìm GHCT theo id user, id ctsp
        List<GioHangChiTiet> list = gioHangCTRespon.findById(kh.getId(), sanPhamCT.getId());

        if (list.size() == 0) {
            createNewCart(kh, sanPhamCT, ghct, idKh);
        } else {
            boolean createNewCartDetail = false;
            boolean isExistedCartDetail = false;
            for (GioHangChiTiet gioHangChiTiet : list) {
                gioHangChiTiet.setSoLuong(ghct.getSoLuong() + gioHangChiTiet.getSoLuong());
                gioHangChiTiet.setNgaySua(DatetimeUtil.getCurrentDate());
                gioHangCTRespon.save(gioHangChiTiet);
                createNewCartDetail = true;
                break;
            }
            if (!createNewCartDetail) {
                // Nếu chưa tạo giỏ hàng chi tiết mới trong vòng lặp, thực hiện tạo ở đây
                createNewCart(kh, sanPhamCT, ghct, idKh);
            }
        }
        return null;
    }

    public GioHangChiTiet createNewCart(User kh, SanPhamChiTiet sanPhamCT, GioHangCTRequest ghct, Integer idKh) {

        Random random = new Random();
        int randomNumber = random.nextInt(9000) + 1000;

        GioHang gioHang = giohangRepo.finbyIdKH(idKh);
        if (gioHang == null) {
            GioHang newGioHang = new GioHang();
            newGioHang.setNgayTao(DatetimeUtil.getCurrentDate());
            newGioHang.setMa("GH"+randomNumber);
            newGioHang.setUser(User.builder().id(idKh).build());
            gioHang = giohangRepo.save(newGioHang);
        }
        // thêm mới vào GHCT

        GioHangChiTiet gioHangChiTiet = new GioHangChiTiet();

        if (sanPhamCT.getGiaSauGiam() == null) {
            gioHangChiTiet.setDonGia(sanPhamCT.getGiaBan());
        } else {
            gioHangChiTiet.setDonGia(sanPhamCT.getGiaSauGiam());
        }

        gioHangChiTiet.setMa("GHCT" + randomNumber);
        gioHangChiTiet.setSoLuong(ghct.getSoLuong());
        gioHangChiTiet.setGioHang(gioHang);
        gioHangChiTiet.setSanPhamChiTiet(sanPhamCT);
        gioHangChiTiet.setNgayTao(DatetimeUtil.getCurrentDate());

        gioHangCTRespon.save(gioHangChiTiet);
        HashMap<String, Object> map = DataUltil.setData("success", gioHangCTRespon.save(gioHangChiTiet));
        return gioHangCTRespon.save(gioHangChiTiet);


    }

    public KhGioHangChiTietSessionRequest addCartSession(Integer id, Integer soLuong, HttpSession httpSession) {
        // lấy ctsp từ repo
        Optional<SanPhamChiTiet> chiTietSanPham = productReponstory.findById(id);
         SanPham sanPham = khSanPhamRepository.findById(chiTietSanPham.get().getSanPham().getId()).get();
        // tạo ra giỏ hàng chi tiết
        KhGioHangChiTietSessionRequest gioHangChiTiet = new KhGioHangChiTietSessionRequest();
        Random random = new Random();
        int randomNumber = random.nextInt(9000) + 1000;

        gioHangChiTiet.setGiaBan(chiTietSanPham.get().getGiaBan());
        gioHangChiTiet.setIdGHCT(randomNumber);
        gioHangChiTiet.setSoLuong(soLuong);
        gioHangChiTiet.setIdCTSP(chiTietSanPham.get().getId());
        gioHangChiTiet.setIdSP(chiTietSanPham.get().getSanPham().getId());
        gioHangChiTiet.setTenSP(sanPham.getTen());
        gioHangChiTiet.setAnhMau(chiTietSanPham.get().getAnh());
        gioHangChiTiet.setAnh(sanPham.getAnh());
        gioHangChiTiet.setTenMauSac(chiTietSanPham.get().getMauSac().getTen());
        if(chiTietSanPham.get().getSize() == null){
            gioHangChiTiet.setTenSize(null);
        }else{
            gioHangChiTiet.setTenSize(chiTietSanPham.get().getSize().getTen());
        }
        gioHangChiTiet.setGiaSPSauGiam(chiTietSanPham.get().getGiaSauGiam());
        gioHangChiTiet.setSoLuongTon(chiTietSanPham.get().getSoLuongTon());

        //lấy gior hàng từ session
        KhCartBO cartSesion = (KhCartBO) httpSession.getAttribute("cart");
        // nếu chưa có giỏ hàng
        if (cartSesion == null) {
            KhCartBO cart = new KhCartBO();
            List<KhGioHangChiTietSessionRequest> list = new ArrayList<>();
            list.add(gioHangChiTiet);
            cart.setLstCart(list);
            httpSession.setAttribute("cart", cart);
        } else {
            // nếu có giỏ hàng
            KhCartBO cart = (KhCartBO) httpSession.getAttribute("cart");
            List<KhGioHangChiTietSessionRequest> listItem = cart.getLstCart();
            // kieemr tra sản phẩm đã có trong giỏ hàng chưa
            // nếu có thì tăng số lượng lên theo so luong nhap
            for (KhGioHangChiTietSessionRequest itemTmp : listItem) {
                if (itemTmp.getIdCTSP().equals(id)) {
                    itemTmp.setSoLuong(gioHangChiTiet.getSoLuong() + soLuong);
                    return itemTmp;
                }
            }
            // không có thì thêm sản phẩm vào
            listItem.add(gioHangChiTiet);
        }

        return gioHangChiTiet;
    }



    public GioHangChiTiet addCartWhenLogin(List<KhGioHangChiTietSessionRequest> lstRequest, String token){

        if (tokenService.getUserNameByToken(token) == null) {
            return null;
        }
        String userName = tokenService.getUserNameByToken(token);
        User user = userRepository.findByUserName(userName);

        for (KhGioHangChiTietSessionRequest o: lstRequest) {
            // tìm GHCT theo id user, id ctsp
            GioHangChiTiet gioHangChiTiet = gioHangCTRespon.findByIdByIdCTSP(user.getId(), o.getIdCTSP());
            SanPhamChiTiet sanPhamCT = productReponstory.findSanPhamChiTietsById(o.getIdCTSP());
            if (gioHangChiTiet == null) {
                this.createNewCartWhenLogin(sanPhamCT, o, user.getId());
            } else {
                boolean createNewCartDetail = false;
                boolean isExistedCartDetail = false;

                gioHangChiTiet.setSoLuong(o.getSoLuong() + gioHangChiTiet.getSoLuong());
                gioHangChiTiet.setNgaySua(DatetimeUtil.getCurrentDate());
                gioHangCTRespon.save(gioHangChiTiet);
                createNewCartDetail = true;

                if (!createNewCartDetail) {
                    // Nếu chưa tạo giỏ hàng chi tiết mới trong vòng lặp, thực hiện tạo ở đây
                    this.createNewCartWhenLogin(sanPhamCT, o, user.getId());
                }
            }
        }
        return null;
    }

    public GioHangChiTiet createNewCartWhenLogin( SanPhamChiTiet sanPhamCT, KhGioHangChiTietSessionRequest ghct, Integer idKh) {

        GioHang gioHang = giohangRepo.finbyIdKH(idKh);
        if (gioHang == null) {
            GioHang newGioHang = new GioHang();
            newGioHang.setUser(User.builder().id(idKh).build());
            gioHang = giohangRepo.save(newGioHang);
        }
        // thêm mới vào GHCT
        Random random = new Random();
        int randomNumber = random.nextInt(9000) + 1000;
        GioHangChiTiet gioHangChiTiet = new GioHangChiTiet();

        if (sanPhamCT.getGiaSauGiam() == null) {
            gioHangChiTiet.setDonGia(sanPhamCT.getGiaBan());
        } else {
            gioHangChiTiet.setDonGia(sanPhamCT.getGiaSauGiam());
        }

        gioHangChiTiet.setMa("GHCT" + randomNumber);
        gioHangChiTiet.setSoLuong(ghct.getSoLuong());
        gioHangChiTiet.setGioHang(gioHang);
        gioHangChiTiet.setSanPhamChiTiet(sanPhamCT);
        gioHangChiTiet.setNgayTao(DatetimeUtil.getCurrentDate());

        gioHangCTRespon.save(gioHangChiTiet);
        HashMap<String, Object> map = DataUltil.setData("success", gioHangCTRespon.save(gioHangChiTiet));
        return gioHangCTRespon.save(gioHangChiTiet);


    }

    @Override
    public List<GioHangCTResponse> getListGHCT(String token) {
        Integer idUser;
        String userName = tokenService.getUserNameByToken(token);
        User user = userRepository.findByUserName(userName);
        idUser = user.getId();
        return gioHangCTRespon.getListGHCT(idUser);
    }

    @Override
    public GioHangCTResponse getGHCTByIdCTSP(String token, Integer idctsp) {

        Integer idUser;
        String userName = tokenService.getUserNameByToken(token);
        User user = userRepository.findByUserName(userName);
        idUser = user.getId();
        return gioHangCTRespon.getGHCTByIDCTSP(idUser, idctsp);
    }


    @Override
    public ResponseEntity<HttpStatus> deleteGioHangCT(Integer id) {
        try {
            GioHangChiTiet gioHangChiTiet = gioHangCTRespon.findById(id).get();
            gioHangCTRespon.deleteById(gioHangChiTiet.getId());
            giohangRepo.deleteById(gioHangChiTiet.getGioHang().getId());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




    @Override
    public ResponseEntity<HttpStatus> deleteAllGioHangCT() {
        try {
            gioHangCTRespon.deleteAll();
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public GioHangCTResponse updateCongSoLuong(Integer id, String token) {
        Integer idUser;
        if (tokenService.getUserNameByToken(token) == null) {
            return null;
        }
        String userName = tokenService.getUserNameByToken(token);
        User user = userRepository.findByUserName(userName);
        idUser = user.getId();
        Optional<GioHangChiTiet> tutorialData = gioHangCTRespon.findById(id);

        if (tutorialData.isPresent()) {
            GioHangChiTiet _gioHangChiTiet = tutorialData.get();
            int newSoLuong = _gioHangChiTiet.getSoLuong() + 1;
            if (newSoLuong > _gioHangChiTiet.getSanPhamChiTiet().getSoLuongTon()) {
                return null;
            }
            _gioHangChiTiet.setSoLuong(_gioHangChiTiet.getSoLuong() + 1);
            GioHangChiTiet gioHang = gioHangCTRespon.save(_gioHangChiTiet);
            return gioHangCTRespon.getGHCT(idUser, gioHang.getId());
        }
        return null;
    }

    @Override
    public GioHangCTResponse updateSLGH(Integer id, String token, Integer sl) {
        Integer idUser;
        if (tokenService.getUserNameByToken(token) == null) {
            return null;
        }
        String userName = tokenService.getUserNameByToken(token);
        User user = userRepository.findByUserName(userName);
        idUser = user.getId();
        Optional<GioHangChiTiet> tutorialData = gioHangCTRespon.findById(id);

        if (tutorialData.isPresent()) {

            GioHangChiTiet _gioHangChiTiet = tutorialData.get();
            int newSoLuong = sl;
            if (newSoLuong > _gioHangChiTiet.getSanPhamChiTiet().getSoLuongTon()) {
                return null;
            }

            _gioHangChiTiet.setSoLuong( sl);
            GioHangChiTiet gioHang = gioHangCTRespon.save(_gioHangChiTiet);
            log.info("test",gioHang.getSoLuong());
            return gioHangCTRespon.getGHCT(idUser, gioHang.getId());
        }
        return null;
    }


    @Override
    public GioHangCTResponse updateTruSoLuong(Integer id, String token) {
        Integer idUser;
        if (tokenService.getUserNameByToken(token) == null) {
            return null;
        }
        String userName = tokenService.getUserNameByToken(token);
        User user = userRepository.findByUserName(userName);
        idUser = user.getId();
        Optional<GioHangChiTiet> tutorialData = gioHangCTRespon.findById(id);

        if (tutorialData.isPresent()) {
            GioHangChiTiet _gioHangChiTiet = tutorialData.get();
            _gioHangChiTiet.setSoLuong(_gioHangChiTiet.getSoLuong() - 1);
            if (_gioHangChiTiet.getSoLuong() <= 0) {
                this.deleteGioHangCT(id);
                HashMap<String, Object> map = DataUltil.setData("warning", "sản phẩm không được nhỏ bằng 0");
                return null;
            } else {
                GioHangChiTiet gioHang = gioHangCTRespon.save(_gioHangChiTiet);
                return gioHangCTRespon.getGHCT(idUser, gioHang.getId());
            }

        }
        return null;
    }

    @Override
    public HashMap<String, Object> updateMauSacSize(Integer idghct, Integer idSPCT) {
        Optional<GioHangChiTiet> ghct = gioHangCTRespon.findById(idghct);

        if (ghct.isPresent()) {
            GioHangChiTiet _gioHangChiTiet = ghct.get();
            _gioHangChiTiet.setSanPhamChiTiet(SanPhamChiTiet.builder().id(idSPCT).build());
            gioHangCTRespon.save(_gioHangChiTiet);
            HashMap<String, Object> map = DataUltil.setData("warning", gioHangCTRespon.save(_gioHangChiTiet));
            return map;
        } else {
            HashMap<String, Object> map = DataUltil.setData("warning", "lỗi");
            return map;
        }
    }



    @Override
    public List<KhVoucherResponse> getListVoucher(String token) {
        Integer idUser;
        if (tokenService.getUserNameByToken(token) == null) {
            return null;
        }
        String userName = tokenService.getUserNameByToken(token);
        User user = userRepository.findByUserName(userName);
        idUser = user.getId();
        return gioHangCTRespon.getListVoucher(idUser);
    }

    @Override
    public List<KhVoucherResponse> getListVoucherByUser(String token) {
        Integer idUser;
        if (tokenService.getUserNameByToken(token) == null) {
            return null;
        }
        String userName = tokenService.getUserNameByToken(token);
        User user = userRepository.findByUserName(userName);
        idUser = user.getId();
        return gioHangCTRespon.getListVoucherByUser(idUser);
    }

    @Override
    public List<KhVoucherResponse> getListVoucherByTrangThai() {
        return gioHangCTRespon.getListVoucherByTrangThai();
    }

}
