import { defineStore } from 'pinia';
import axios from 'axios';

const api = `${import.meta.env.VITE_BASE_API_ENDPOINT}/khach-hang/thong-bao`;

export const KHThongBaoStore = defineStore('KHthongbao', {
    state: () => ({
        data: [],
    }),
    actions: {
        async fetchData(token) {
            try {
                const response = await axios.get(api +  `?token=${token}`);
                response.data.sort((a, b) => b.id - a.id);
                this.data = response.data;
            } catch (error) {
                console.error('Error fetching users:', error);
            }
        },

        async fetchdem(token) {
            try {
                const response = await axios.get(api + `/dem?token=${token}`);
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