package com.example.demo.infrastructure.status;

public class ThongBaoType {
    public static final Integer THANH_TOAN = 0;  // kh gửi thông báo xác nhận đơn hàng đến ADMIN
    public static final Integer XAC_NHAN_DON_HANG = 1; // admin gửi thông báo xác nhận đơn hàng đến KHACH HANG
    public static final Integer HUY_DON_HANG = 2;  // admin gửi thông báo huy đơn hàng đến KhachHang
    public static final Integer YEU_CAU_DOI_TRA = 3; // kh gửi thông báo yeu cau doi tra đơn hàng đến ADMIN
    public static final Integer XAC_NHAN_DOI_TRA = 4; // admin gửi thông báo xác nhận đơn hàng đến KHACH HANG
    public static final Integer HUY_DOI_TRA = 5; // admin gửi thông báo huy doi tra đơn hàng đến KHACH HANG
    public static final Integer KH_HUY_DON_HANG = 6; // kh gửi thông báo huy  đơn hàng đến admin
    public static final Integer HOAN_THANH_DOI_TRA = 7; //admin gửi tb đến khách
    public static final Integer HOAN_THANH = 8;        //admin gửi tb đến khách
}
