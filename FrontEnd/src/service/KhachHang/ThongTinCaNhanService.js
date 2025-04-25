import {defineStore} from 'pinia';
import axios from 'axios';
import httpAxios from '@/service/Authentication/http.js';

const api = `${import.meta.env.VITE_BASE_API_ENDPOINT}/khach-hang/thong-tin-ca-nhan`;

export const useThongTin = defineStore('thongTin', {
    id: 'user',
    state: () => ({
        user: [],
        diaChi: [],
        data: [],
    }),
    actions: {
        async fetchData(id) {
            try {
                const response = await axios.get(api + '/find-all?token=' + id);
                this.data = response.data;
            } catch (error) {
                console.error('Error fetching users:', error);
            }
        },
        async fetchProductById(idUser) {
            try {
                const response = await axios.get(api + `/getOne/${idUser}`);
                this.user = response.data;
            } catch (error) {
                console.error('Lỗi khi lấy user:', error);
                throw error;
            }
        },
        async fetchAllDiaChi(idProduct) {
            try {
                const response = await axios.get(api + `/${idProduct}/dia-chi`); // Thay đổi URL dựa trên API của bạn
                this.diaChi = response.data;
                //  console.log('mau: ',   this.mauSacs  );
                return this.diaChi;
            } catch (error) {
                console.error('Lỗi khi lấy danh sách địa chỉ:', error);
            }
        },
        updateUser(id, form) {
            axios.put(api + '/' + id, form).then((response) => {
                for (let i = 0; i < this.data.length; i++) {
                    if (id == this.data[i].id) {
                        this.data[i].ten = form.ten;
                        this.data[i].email = form.email;
                        this.data[i].sdt = form.sdt;
                        this.data[i].gioiTinh = form.gioiTinh;
                        this.data[i].ngaySinh = form.ngaySinh;
                        this.data[i].image = form.image;
                    }
                }
            });
        },
        async updatesdt(id, sdt) {
            try {
                const response = await axios.patch(`${import.meta.env.VITE_BASE_API_ENDPOINT}/khach-hang/user/updateSDT/` + id + `?sdt=${sdt}`);
                this.data = response.data;
            } catch (error) {
                console.error('Lỗi khi lấy danh sách địa chỉ:', error);
            }
        },
        async doiMatKhau(userId, oldPassword, newPassword) {
            await httpAxios.put(`${import.meta.env.VITE_BASE_API_ENDPOINT}/khach-hang/user/${userId}/doi-mat-khau`, {oldPassword, newPassword});
        }
    }
});