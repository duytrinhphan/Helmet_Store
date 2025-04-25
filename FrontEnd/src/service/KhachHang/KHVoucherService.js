import { defineStore } from 'pinia';
import axios from 'axios';

const apiVoucher = `${import.meta.env.VITE_BASE_API_ENDPOINT}/khach-hang/voucher`;



export const voucherStore = defineStore('voucher', {
    state: () => ({
        data: []
    }),
    actions: {
        async getListVoucher(id) {
            try {
                const response = await axios.get(apiVoucher +`/${id}`);
                this.data = response.data;
            } catch (error) {
                console.error('Lỗi khi lấy chi tiết sản phẩm:', error);
                throw error;
            }
        }
        ,
        async getVoucherByUser(id) {
            try {
                const response = await axios.get(apiVoucher +`/${id}`);
                this.data = response.data;
            } catch (error) {
                console.error('Lỗi khi lấy chi tiết sản phẩm:', error);
                throw error;
            }
        }
    }
})