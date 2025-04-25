import { defineStore } from 'pinia';
import axios from 'axios';





export const uservoucherStore = defineStore('uservoucher', {
    state: () => ({
        data: []
    }),
    actions: {
        async getUserVoucher(userId, voucherId) {
            try {
                const response = await axios.get(`${import.meta.env.VITE_BASE_API_ENDPOINT}/khach-hang/user-voucher?userid=${userId}&voucherId=${voucherId}`);
                this.data = response.data;
            } catch (error) {
                console.error('Lỗi khi lấy chi tiết sản phẩm:', error);
                throw error;
            }
        }
    }

    })