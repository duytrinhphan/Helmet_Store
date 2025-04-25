package com.example.demo.infrastructure.status;

public class HoaDonStatus {
    public static final Integer CHO_THANH_TOAN = 1;
    public static final Integer DA_HUY = 0;
    public static final Integer YEU_CAU_XAC_NHAN = 2;               // -> dang chuan bi hang, nhan vien va khach hang co the huy hoa don
    public static final Integer HOAN_THANH = 3;                     // -> khach hang yeu cau doi tra
    public static final Integer DANG_CHUAN_BI_HANG = 4;             // -> giao cho don vi van chuyen, khach hang co the huy hoa don
    public static final Integer GIAO_CHO_DON_VI_VAN_CHUYEN = 5;     // -> hoan thanh
    public static final Integer DA_GIAO_CHO_DON_VI_VAN_CHUYEN = 6;  // xoa
    public static final Integer YEU_CAU_DOI_TRA = 7;                // Yêu cầu trả hàng
    public static final Integer XAC_NHAN_DOI_TRA = 8;               // Xác nhận trả hàng
    public static final Integer HUY_DOI_TRA = 9;                // Huỷ đổi trả
    public static final Integer HOAN_THANH_DOI_TRA = 10;               // Đổi trả Hoàn thành
}
