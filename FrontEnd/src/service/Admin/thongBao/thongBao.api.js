import { defineStore } from 'pinia';
import axios from '@/service/Authentication/http.js';

const api = `${import.meta.env.VITE_BASE_API_ENDPOINT}/admin/thong-bao`;

export const ThongBaoStore = defineStore('thongbao', {
    state: () => ({
        data: [],
    }),
    actions: {
        async fetchData() {
            try {
                const response = await axios.get(api);
                response.data.sort((a, b) =>  b.id - a.id);
                this.data = response.data;

            } catch (error) {
                console.error('Error fetching users:', error);
            }
        },

        async fetchdem() {
            try {
                const response = await axios.get(api + `/dem`);
                return response.data;
            } catch (error) {
                console.error('Error fetching users:', error);
            }
        },

        async daXem(id) {
            try {
                const response = await axios.put(api + `/da-xem/${id}`);
                const index = this.data.findIndex(product => product.id === id);
                if (index !== -1) {
                    let newProductData = this.data[index];
                    newProductData = response.data;
                    this.data[index] = newProductData;
                }

            } catch (error) {
                console.error('Error fetching users:', error);
            }
        },
    }
});