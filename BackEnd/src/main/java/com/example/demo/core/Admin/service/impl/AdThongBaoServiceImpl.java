package com.example.demo.core.Admin.service.impl;

import com.example.demo.core.Admin.repository.AdHoaDonReponsitory;
import com.example.demo.core.Admin.repository.AdminThongBaoRepository;
import com.example.demo.entity.HoaDon;
import com.example.demo.entity.ThongBao;
import com.example.demo.entity.User;
import com.example.demo.infrastructure.status.ThongBaoStatus;
import com.example.demo.infrastructure.status.ThongBaoType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdThongBaoServiceImpl {

    @Autowired
    private AdminThongBaoRepository adminThongBaoRepository;

    @Autowired
    private AdHoaDonReponsitory hoaDonReponsitory;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public List<ThongBao> getAll() {
        List<ThongBao> lst = adminThongBaoRepository.findAll();
        return lst;
    }

    public Integer dem() {
        Integer lst = adminThongBaoRepository.dem();
        return lst;
    }

    public ThongBao daXem(Integer id) {
        ThongBao thongBao = adminThongBaoRepository.findById(id).get();
        if (thongBao != null) {
            thongBao.setTrangThai(ThongBaoStatus.DA_XEM);
            return adminThongBaoRepository.save(thongBao);
        }
        return null;
    }

    public void huyHoaDon(Integer idHD) {
        HoaDon hoaDon = hoaDonReponsitory.findById(idHD).get();
        ThongBao thongBao = new ThongBao();
        thongBao.setType(ThongBaoType.HUY_DON_HANG);
        thongBao.setUser(User.builder().id(hoaDon.getUser().getId()).build());
        thongBao.setContent("Hóa đơn mã " + hoaDon.getMa() + " đã bị hủy");
        thongBao.setTrangThai(ThongBaoStatus.CHUA_XEM);
        adminThongBaoRepository.save(thongBao);
        messagingTemplate.convertAndSend("/topic/hoa-don/"+hoaDon.getUser().getId(),thongBao);
    }

    public void xacNhanHoaDon(Integer idHD) {
        HoaDon hoaDon = hoaDonReponsitory.findById(idHD).get();
        ThongBao thongBao = new ThongBao();
        thongBao.setType(ThongBaoType.XAC_NHAN_DON_HANG);
        thongBao.setUser(User.builder().id(hoaDon.getUser().getId()).build());
        thongBao.setContent("Hóa đơn mã " + hoaDon.getMa() + " đã được xác nhận");
        thongBao.setTrangThai(ThongBaoStatus.CHUA_XEM);
        adminThongBaoRepository.save(thongBao);
        messagingTemplate.convertAndSend("/topic/hoa-don/"+hoaDon.getUser().getId(),thongBao);
    }

    public void xacNhanDoiTra(Integer idHD) {
        HoaDon hoaDon = hoaDonReponsitory.findById(idHD).get();
        ThongBao thongBao = new ThongBao();
        thongBao.setType(ThongBaoType.XAC_NHAN_DOI_TRA);
        thongBao.setUser(User.builder().id(hoaDon.getUser().getId()).build());
        thongBao.setContent("Hóa đơn mã " + hoaDon.getMa() + " đã đồng ý đổi trả");
        thongBao.setTrangThai(ThongBaoStatus.CHUA_XEM);
        adminThongBaoRepository.save(thongBao);
        messagingTemplate.convertAndSend("/topic/hoa-don/"+hoaDon.getUser().getId(),thongBao);
    }

    public void HuyDoiTra(Integer idHD) {
        HoaDon hoaDon = hoaDonReponsitory.findById(idHD).get();
        ThongBao thongBao = new ThongBao();
        thongBao.setType(ThongBaoType.HUY_DOI_TRA);
        thongBao.setUser(User.builder().id(hoaDon.getUser().getId()).build());
        thongBao.setContent("Hóa đơn mã " + hoaDon.getMa() + " đã bị hủy đổi trả");
        thongBao.setTrangThai(ThongBaoStatus.CHUA_XEM);
        adminThongBaoRepository.save(thongBao);
        messagingTemplate.convertAndSend("/topic/hoa-don/"+hoaDon.getUser().getId(),thongBao);
    }

    public void hoanThanhDoiTra(Integer idHD) {
        HoaDon hoaDon = hoaDonReponsitory.findById(idHD).get();
        ThongBao thongBao = new ThongBao();
        thongBao.setType(ThongBaoType.HOAN_THANH_DOI_TRA);
        thongBao.setUser(User.builder().id(hoaDon.getUser().getId()).build());
        thongBao.setContent("Hóa đơn mã " + hoaDon.getMa() + " đã hoàn thành đổi trả");
        thongBao.setTrangThai(ThongBaoStatus.CHUA_XEM);
        adminThongBaoRepository.save(thongBao);
        messagingTemplate.convertAndSend("/topic/hoa-don/"+hoaDon.getUser().getId(),thongBao);
    }

    public void hoanThanh(Integer idHD) {
        HoaDon hoaDon = hoaDonReponsitory.findById(idHD).get();
        ThongBao thongBao = new ThongBao();
        thongBao.setType(ThongBaoType.HOAN_THANH);
        thongBao.setUser(User.builder().id(hoaDon.getUser().getId()).build());
        thongBao.setContent("Hóa đơn mã " + hoaDon.getMa() + " đã hoàn thành");
        thongBao.setTrangThai(ThongBaoStatus.CHUA_XEM);
        adminThongBaoRepository.save(thongBao);
        messagingTemplate.convertAndSend("/topic/hoa-don/"+hoaDon.getUser().getId(),thongBao);
    }

}
