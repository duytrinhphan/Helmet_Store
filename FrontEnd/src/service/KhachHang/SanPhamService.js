import { defineStore } from 'pinia';
import axios from 'axios';

const apiSP = `${import.meta.env.VITE_BASE_API_ENDPOINT}/khach-hang/san-pham`;

export const SanPhamStore = defineStore('SanPham', {
    state: () => ({
        dataSP: []
    }),
    actions: {
        //load sp mớI
        async fetchAll() {
            try {
                const response = await axios.get(apiSP + '/getAll');
                this.dataSP = response.data;
            } catch (error) {
                console.error('Error fetching users:', error);
            }
        },
    }
});


