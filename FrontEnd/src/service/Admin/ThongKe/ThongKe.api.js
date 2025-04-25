import { defineStore } from 'pinia';
import axios from '@/service/Authentication/http.js';

const apiThongKe = `${import.meta.env.VITE_BASE_API_ENDPOINT}/admin/Thong-ke`;
export const ThongKeStore = defineStore('thongKe', {
    state: () => ({
        data: [],
        tongDoanhThu: 0,
        tongHoanTien: 0,
        tongChietKhau: 0,
        tongDonHoanThanh: 0,

        tongLoiNhuan: 0,
        tongDonhangHoanThanh: 0,
        tongDonhangDangGiao: 0,
        tongDonhangHuy: 0,
        lstAdminThongKeLoaiResponses: [],
        lstAdminThongKeSanPhamCaoResponses: [],
        lstAdminThongKeSanPhamThapResponses: [],
        lstAdminThongKeThangResponses: [],
        lstAdminThongKeThuongHieuResponses: [],
        lstAdminThongKeThangNamResponses: [],
        lstAdminThongKeLoiNhuanSanPhamResponse: [],
        lstAdminThongKeLoiNhuanHoaDonResponse: [],

        doanhThu: 0,
        doanhThuTaiQuay: 0,
        doanhThuOnline: 0,
        donMua: 0,
        donTra: 0,
        donHuy: 0
    }),
    actions: {
        async fetchAll() {
            try {
                const response = await axios.get(apiThongKe);
                this.data = response.data;
                this.tongDoanhThu = response.data.tongDoanhThu;
                this.tongHoanTien = response.data.tongHoanTien;
                this.tongChietKhau = response.data.tongChietKhau;
                this.tongDonHoanThanh = response.data.tongDonHoanThanh;
                this.lstAdminThongKeLoaiResponses = response.data.lstAdminThongKeLoaiResponses;
                this.lstAdminThongKeSanPhamCaoResponses = response.data.lstAdminThongKeSanPhamCaoResponses;
                this.lstAdminThongKeSanPhamThapResponses = response.data.lstAdminThongKeSanPhamThapResponses;
                this.lstAdminThongKeThangResponses = response.data.lstAdminThongKeThangResponses;
                this.lstAdminThongKeThuongHieuResponses = response.data.lstAdminThongKeThuongHieuResponses;
                this.lstAdminThongKeThangNamResponses = response.data.lstAdminThongKeThangNamResponses;
            } catch (error) {
                console.error('Lỗi khi get sản phẩm:', error);
            }
        },

        async fetchAllByDay() {
            try {
                const response = await axios.get(apiThongKe + `/day`);

                this.doanhThu = response.data.doanhThu;
                this.doanhThuTaiQuay = response.data.doanhThuTaiQuay;
                this.donMua = response.data.donMua;
                this.doanhThuOnline = response.data.doanhThuOnline;
                this.donTra = response.data.donTra;
                this.donHuy = response.data.donHuy;
            } catch (error) {
                console.error('Lỗi khi get sản phẩm:', error);
            }
        },

        async fetchAllByHinhThucGiaoHang(idPhuongThuc) {
            try {
                const response = await axios.get(apiThongKe + `/by-phuong-thuc?idPhuongThuc=${idPhuongThuc}`);
                this.data = response.data;
                this.tongDoanhThu = response.data.tongDoanhThu;
                this.tongHoanTien = response.data.tongHoanTien;
                this.tongChietKhau = response.data.tongChietKhau;
                this.tongDonHoanThanh = response.data.tongDonHoanThanh;
                this.lstAdminThongKeLoaiResponses = response.data.lstAdminThongKeLoaiResponses;
                this.lstAdminThongKeSanPhamCaoResponses = response.data.lstAdminThongKeSanPhamCaoResponses;
                this.lstAdminThongKeSanPhamThapResponses = response.data.lstAdminThongKeSanPhamThapResponses;
                this.lstAdminThongKeThangResponses = response.data.lstAdminThongKeThangResponses;
                this.lstAdminThongKeThuongHieuResponses = response.data.lstAdminThongKeThuongHieuResponses;
                this.lstAdminThongKeThangNamResponses = response.data.lstAdminThongKeThangNamResponses;
            } catch (error) {
                console.error('Lỗi khi get sản phẩm:', error);
            }
        },

        async fetchAllByLoai(id, year) {
            try {
                const response = await axios.get(apiThongKe + `/loai/${id}?year=${year}`);
                this.data = response.data;
                this.tongDoanhThu = response.data.tongDoanhThu;
                this.tongHoanTien = response.data.tongHoanTien;
                this.tongChietKhau = response.data.tongChietKhau;
                this.tongDonHoanThanh = response.data.tongDonHoanThanh;
                this.lstAdminThongKeLoaiResponses = response.data.lstAdminThongKeLoaiResponses;
                this.lstAdminThongKeSanPhamCaoResponses = response.data.lstAdminThongKeSanPhamCaoResponses;
                this.lstAdminThongKeSanPhamThapResponses = response.data.lstAdminThongKeSanPhamThapResponses;
                this.lstAdminThongKeThangResponses = response.data.lstAdminThongKeThangResponses;
                this.lstAdminThongKeThuongHieuResponses = response.data.lstAdminThongKeThuongHieuResponses;
                this.lstAdminThongKeThangNamResponses = response.data.lstAdminThongKeThangNamResponses;
            } catch (error) {
                console.error('Lỗi khi get sản phẩm:', error);
            }
        },

        async fetchAllByThuongHieu(id, year) {
            try {
                const response = await axios.get(apiThongKe + `/thuong-hieu/${id}?year=${year}`);
                this.data = response.data;
                this.tongDoanhThu = response.data.tongDoanhThu;
                this.tongHoanTien = response.data.tongHoanTien;
                this.tongChietKhau = response.data.tongChietKhau;
                this.tongDonHoanThanh = response.data.tongDonHoanThanh;
                this.lstAdminThongKeLoaiResponses = response.data.lstAdminThongKeLoaiResponses;
                this.lstAdminThongKeSanPhamCaoResponses = response.data.lstAdminThongKeSanPhamCaoResponses;
                this.lstAdminThongKeSanPhamThapResponses = response.data.lstAdminThongKeSanPhamThapResponses;
                this.lstAdminThongKeThangResponses = response.data.lstAdminThongKeThangResponses;
                this.lstAdminThongKeThuongHieuResponses = response.data.lstAdminThongKeThuongHieuResponses;
                this.lstAdminThongKeThangNamResponses = response.data.lstAdminThongKeThangNamResponses;
            } catch (error) {
                console.error('Lỗi khi get sản phẩm:', error);
            }
        },

        async fetchAllBySanPham(id, year) {
            try {
                const response = await axios.get(apiThongKe + `/san-pham/${id}?year=${year}`);
                this.data = response.data;
                // console.log(year)
                this.tongDoanhThu = response.data.tongDoanhThu;
                this.tongHoanTien = response.data.tongHoanTien;
                this.tongChietKhau = response.data.tongChietKhau;
                this.tongDonHoanThanh = response.data.tongDonHoanThanh;
                this.lstAdminThongKeLoaiResponses = response.data.lstAdminThongKeLoaiResponses;
                this.lstAdminThongKeSanPhamCaoResponses = response.data.lstAdminThongKeSanPhamCaoResponses;
                this.lstAdminThongKeSanPhamThapResponses = response.data.lstAdminThongKeSanPhamThapResponses;
                this.lstAdminThongKeThangResponses = response.data.lstAdminThongKeThangResponses;
                this.lstAdminThongKeThuongHieuResponses = response.data.lstAdminThongKeThuongHieuResponses;
                this.lstAdminThongKeThangNamResponses = response.data.lstAdminThongKeThangNamResponses;
            } catch (error) {
                console.error('Lỗi khi get sản phẩm:', error);
            }
        },

        async fetchAllByYear(year) {
            try {
                const response = await axios.get(apiThongKe + `/year/${year}`);
                this.data = response.data;
                // console.log(year)
                this.tongDoanhThu = response.data.tongDoanhThu;
                this.tongHoanTien = response.data.tongHoanTien;
                this.tongChietKhau = response.data.tongChietKhau;
                this.tongDonHoanThanh = response.data.tongDonHoanThanh;
                this.lstAdminThongKeLoaiResponses = response.data.lstAdminThongKeLoaiResponses;
                this.lstAdminThongKeSanPhamCaoResponses = response.data.lstAdminThongKeSanPhamCaoResponses;
                this.lstAdminThongKeSanPhamThapResponses = response.data.lstAdminThongKeSanPhamThapResponses;
                this.lstAdminThongKeThangResponses = response.data.lstAdminThongKeThangResponses;
                this.lstAdminThongKeThuongHieuResponses = response.data.lstAdminThongKeThuongHieuResponses;
                this.lstAdminThongKeThangNamResponses = response.data.lstAdminThongKeThangNamResponses;
            } catch (error) {
                console.error('Lỗi khi get sản phẩm:', error);
            }
        },

        async fetchAllByMonth(startDate, endDate) {
            try {
                const response = await axios.get(apiThongKe + `/month?startDate=${startDate}&endDate=${endDate}`);
                this.data = response.data;
                // console.log(year)
                this.tongDoanhThu = response.data.tongDoanhThu;
                this.tongHoanTien = response.data.tongHoanTien;
                this.tongChietKhau = response.data.tongChietKhau;
                this.tongDonHoanThanh = response.data.tongDonHoanThanh;
                this.lstAdminThongKeLoaiResponses = response.data.lstAdminThongKeLoaiResponses;
                this.lstAdminThongKeSanPhamCaoResponses = response.data.lstAdminThongKeSanPhamCaoResponses;
                this.lstAdminThongKeSanPhamThapResponses = response.data.lstAdminThongKeSanPhamThapResponses;
                this.lstAdminThongKeThangResponses = response.data.lstAdminThongKeThangResponses;
                this.lstAdminThongKeThuongHieuResponses = response.data.lstAdminThongKeThuongHieuResponses;
                this.lstAdminThongKeThangNamResponses = response.data.lstAdminThongKeThangNamResponses;
            } catch (error) {
                console.error('Lỗi khi get sản phẩm:', error);
            }
        },

        async fetchAllLoiNhuan(year, startDate, endDate) {
            try {
                const response = await axios.get(apiThongKe + `/loi-nhuan?year=${year}&startDate=${startDate}&endDate=${endDate}`);
                //  this.data = response.data;
                this.tongLoiNhuan = response.data.tongLoiNhuan;
                this.tongDonhangHoanThanh = response.data.tongDonhangHoanThanh;
                this.tongDonhangDangGiao = response.data.tongDonhangDangGiao;
                this.tongDonhangHuy = response.data.tongDonhangHuy;
                this.lstAdminThongKeLoiNhuanSanPhamResponse = response.data.adminThongKeLoiNhuanSanPhamResponses;
                this.lstAdminThongKeLoiNhuanHoaDonResponse = response.data.adminThongKeLoiNhuanHoaDonResponses;
            } catch (error) {
                console.error('Lỗi khi get sản phẩm:', error);
            }
        },

        async fetchAllLoiNhuanbyHinhThucGiaohang(idHinhThuc) {
            try {
                const response = await axios.get(apiThongKe + `/loi-nhuan-hinh-thuc-giao-hang?idHinhThuc=${idHinhThuc}`);
                //  this.data = response.data;
                this.tongLoiNhuan = response.data.tongLoiNhuan;
                this.tongDonhangHoanThanh = response.data.tongDonhangHoanThanh;
                this.tongDonhangDangGiao = response.data.tongDonhangDangGiao;
                this.tongDonhangHuy = response.data.tongDonhangHuy;
                this.lstAdminThongKeLoiNhuanSanPhamResponse = response.data.adminThongKeLoiNhuanSanPhamResponses;
                this.lstAdminThongKeLoiNhuanHoaDonResponse = response.data.adminThongKeLoiNhuanHoaDonResponses;
            } catch (error) {
                console.error('Lỗi khi get sản phẩm:', error);
            }
        },
        async xuatBaoCao() {
            try {
                const response = await axios.get(apiThongKe + `/xuat-bao-cao`);
                return response.data;
            } catch (error) {
                console.error('Lỗi khi get sản phẩm:', error);
            }
        }
    }
});
