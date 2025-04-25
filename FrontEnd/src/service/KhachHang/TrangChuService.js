import { defineStore } from 'pinia';
import axios from 'axios';
import { te } from 'date-fns/locale';

const apiTrangChu = `${import.meta.env.VITE_BASE_API_ENDPOINT}/khach-hang/trang-chu`;

export const TrangChuStore = defineStore('trangChu', {
    state: () => ({
        dataHangMoi: [],
        dataSPBanChay: [],
        data3Phan4: [],
        dataTreEm: [],
        dataFullface: []
    }),
    actions: {
        //load sp mớI
        async fetchDataHangMoi() {
            try {
                const response = await axios.get(apiTrangChu + '/get-all');
                this.dataHangMoi = response.data;
            } catch (error) {
                console.error('Error fetching users:', error);
            }
        },
        //load sp bán chạy
        async fetchDataBanChay() {
            try {
                const response = await axios.get(apiTrangChu + '/get-san-pham-ban-chay');
                this.dataSPBanChay = response.data;
            } catch (error) {
                console.error('Error fetching users:', error);
            }
        },
        //load tất cả data
        async fetchDataByTenLoai(tenLoai) {
            try {
                const response = await axios.get(apiTrangChu + '/get-all-by-ten-loai/' + tenLoai);
                if (tenLoai == 5) this.dataTreEm = response.data;
                if (tenLoai == 2) this.dataFullface = response.data;
            } catch (error) {
                console.error('Error fetching users:', error);
            }
        },
        async fetchDataByTreEm(tenLoai) {
            try {
                const response = await axios.get(apiTrangChu + '/loai?tenLoai=' + tenLoai);
                this.dataTreEm = response.data.data;
            } catch (error) {
                console.error('Error fetching users:', error);
            }
        },
        async fetchDataByNgayTao() {
            this.check = 0;
            try {
                const response = await axios.get(apiTrangChu + '/ngay-tao');
                this.dataHangMoi = response.data.data;
            } catch (error) {
                console.error('Error fetching users:', error);
            }
        }
    }
});
