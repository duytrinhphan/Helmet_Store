import { defineStore } from 'pinia';
import axios from 'axios';

const apiDangNhap = `${import.meta.env.VITE_BASE_API_ENDPOINT}/khach-hang/user`;

export const dangNhapStore = defineStore('dangNhap', {
    // state: () => ({
    //     dataHangMoi: [],
    //     data3Phan4: [],
    //     dataTreEm: [],
    //     dataFullface: []
    // }),
    actions: {
        //load sp mớI
        async dangNhap(login) {
            try {
                const response = await axios.post(apiDangNhap + '/login', login);
                return response.data;
            } catch (error) {
                console.error('Error fetching users:', error);
            }
        },
        //load sp mớI
        async findByToken(token) {
            try {
                const response = await axios.get(apiDangNhap + '/find-by-token?token=' + token);
                return response.data;
            } catch (error) {
                console.error('Error fetching users:', error);
            }
        }
    }
});
