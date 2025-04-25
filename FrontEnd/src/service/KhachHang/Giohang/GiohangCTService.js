import { defineStore } from 'pinia';
import axios from 'axios';

const apiGiohang = `${import.meta.env.VITE_BASE_API_ENDPOINT}/khach-hang/giohang`;

export const gioHangStore = defineStore('gioHang', {
    state: () => ({
        data: [],
        dataSessions: [],
        dataSPDaXem: [],
        voucher: [],
        soLuong: 0,
        fakedata: 'check'
    }),
    // this.data.unshift(response.data.data);
    actions: {
        async addToCart(form, token) {
            const response = await axios.post(`${import.meta.env.VITE_BASE_API_ENDPOINT}/khach-hang/giohang/addGiohang?token=${token}`, form);
            await this.countGHCT(token);
            // this.data.unshift(response.data);
            return response.data;
        },

        async addToCartSesion(form, token) {
            const response = await axios.post(`${import.meta.env.VITE_BASE_API_ENDPOINT}/khach-hang/giohang/addGiohang-session?token=${token}`, form);
            this.dataSessions = response.data;
            this.dataSPDaXem = response.data;
     //       console.log(this.dataSPDaXem);
        },

        async addToCartWhenLogin(form, token) {
            const response = await axios.post(`${import.meta.env.VITE_BASE_API_ENDPOINT}/khach-hang/giohang/addGiohang-when-login?token=${token}`, form);
            await this.countGHCT(token);
            this.dataSessions = response.data;
        },

        async getAllGHCT(token) {
            try {
                const response = await axios.get(apiGiohang + `/getListGioHang?token=${token}`);
                this.data = response.data;
            } catch (error) {}
        },

        async getGHCTByIdctsp(token, idctsp) {
            try {
                const response = await axios.get(apiGiohang + `/getGioHangCTByIdctsp?token=${token}&idctsp=${idctsp}`);
                this.data = response.data;
            } catch (error) {}
        },
        async getListVoucher(token) {
            try {
                const response = await axios.get(apiGiohang + `/get-voucher?token=${token}`);
                this.voucher = response.data;
            } catch (error) {}
        },

        async getListVoucherByTrangThai() {
            try {
                const response = await axios.get(apiGiohang + `/get-voucher-trang-thai`);
                this.voucher = response.data;
            } catch (error) {}
        },

        async getListVoucherByUser(token) {
            try {
                const response = await axios.get(apiGiohang + `/get-voucher-user?token=${token}`);
                this.voucher = response.data;
            } catch (error) {}
        },
        async getAllMSCT() {
            try {
                const response = await axios.get(apiGiohang + '/msct');
                this.data = response.data;
            } catch (error) {}
        },
        async getAllSizeCT() {
            try {
                const response = await axios.get(apiGiohang + '/sizect');
                this.data = response.data;
            } catch (error) {}
        },
        async getGHCT(idGHCT, token) {
            try {
                const response = await axios.get(apiGiohang + '/' + idGHCT + `?token=${token}`);
                this.data = response.data;
            } catch (error) {}
        },

        async congSL(idGHCT, token) {
            try {
                const response = await axios.put(apiGiohang + '/congSL/' + idGHCT + `?token=${token}`);
                const index = this.data.findIndex((hoadon) => hoadon.idGHCT === idGHCT);

                if (response.data === '') {
                    this.fakedata = response.data;
                } else {
                    this.fakedata = response.data;
                    if (index !== -1) {
                        this.data[index] = response.data;
                    }
                }
            } catch (error) {}
        },

        async updateSL(idGHCT, token, sl) {
            try {
                const response = await axios.put(apiGiohang + '/updateSL/' + idGHCT + `?token=${token}&sl=${sl}`);
                const index = this.data.findIndex((hoadon) => hoadon.idGHCT === idGHCT);

                if (response.data === '') {
                    this.fakedata = response.data;
                } else {
                    this.fakedata = response.data;
                    if (index !== -1) {
                        this.data[index] = response.data;
                    }
                }
            } catch (error) {}
        },

        async truSL(idGHCT, token) {
            try {
                const response = await axios.put(apiGiohang + '/truSL/' + idGHCT + `?token=${token}`);
                const index = this.data.findIndex((hoadon) => hoadon.idGHCT === idGHCT);

                if (response.data === null) {
                    if (index !== -1) {
                        this.data[index] = response.data;
                        // this.fakedata = 'check';
                    }
                } else {
                    this.fakedata = response.data;
                    await this.getAllGHCT(token);
                    await this.countGHCT(token);
                }
            } catch (error) {}
        },
        async xoaGHCT(idGHCT, token) {
            try {
                const response = await axios.delete(apiGiohang + '/' + idGHCT + `?token=${token}`);
                await this.countGHCT(token);
                this.data = response.data;
            } catch (error) {}
        },

        async updateMauSacSize(idGHCT, idSPCT, token) {
            try {
                const response = await axios.post(`${import.meta.env.VITE_BASE_API_ENDPOINT}/khach-hang/giohang/updateMauSacSize/${idGHCT}?idSPCT=${idSPCT}&token=${token}`);
                this.data = response.data;
            } catch (error) {}
        },

        async countGHCT(token) {
            try {
                if (token == null || token == '') {
                    let array = JSON.parse(localStorage.getItem('cart'));
                    this.soLuong = array.length;
                    return this.soLuong;
                } else {
                    const response = await axios.get(`${import.meta.env.VITE_BASE_API_ENDPOINT}/khach-hang/giohang/countGHCT?token=${token}`);
                    this.soLuong = parseInt(response.data);
                    return this.soLuong;
                }
            } catch (error) {}
        }
    }
});
