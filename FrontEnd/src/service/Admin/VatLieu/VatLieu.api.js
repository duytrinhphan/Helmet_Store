import { defineStore } from 'pinia';
import axios from '@/service/Authentication/http.js';

export const VatLieuStore = defineStore('vatLieu', {
    state: () => ({
        data: [],
        dataByStatus1: [],
        //nếu đang ở load tất cả thì là 0
        check: 0
    }),
    actions: {
        async fetchAll() {
            this.check = 0;
            try {
                const response = await axios.get(`${import.meta.env.VITE_BASE_API_ENDPOINT}/admin/vat-lieu`); // Thay đổi URL dựa trên API của bạn
                this.data = response.data.data;
            } catch (error) {
                console.error('Lỗi khi lấy danh sách:', error);
            }
        },
        async fetchDataByStatus(status) {
            this.check = 1;
            try {
                const response = await axios.get(`${import.meta.env.VITE_BASE_API_ENDPOINT}/admin/vat-lieu` + '/trang-thai?trangThai=' + status);
                if (status === 1) {
                    this.dataByStatus1 = response.data.data;
                }
                this.data = response.data.data;
            } catch (error) {
                console.error('Error fetching users:', error);
            }
        },
        createVatLieu(form) {
            axios.post(`${import.meta.env.VITE_BASE_API_ENDPOINT}/admin/vat-lieu` + '/add', form).then((response) => {
                if (this.check == 0) {
                    this.data.unshift(response.data.data);
                    if (response.data.data.trangThai == 1) this.dataByStatus1.unshift(response.data.data);
                } else {
                    if (this.data[0].trangThai == response.data.data.trangThai) this.data.unshift(response.data.data);
                    if (response.data.data.trangThai == 1) this.dataByStatus1.unshift(response.data.data);
                }
            });
        },
        updateVatLieu(id, form) {
            axios.put(`${import.meta.env.VITE_BASE_API_ENDPOINT}/admin/vat-lieu` + '/update/' + id, form).then((response) => {
                for (let i = 0; i < this.data.length; i++) {
                    if (id == this.data[i].id) {
                        this.data[i].ten = form.ten;
                        this.data[i].moTa = form.moTa;
                    }
                }
                for (let i = 0; i < this.dataByStatus1.length; i++) {
                    if (id == this.dataByStatus1[i].id) {
                        this.dataByStatus1[i].ten = form.ten;
                        this.dataByStatus1[i].moTa = form.moTa;
                    }
                }
            });
        },
        deleteVatLieu(id) {
            axios.put(`${import.meta.env.VITE_BASE_API_ENDPOINT}/admin/vat-lieu` + '/delete/' + id).then((response) => {
                if (this.check == 0) {
                    for (let i = 0; i < this.data.length; i++) {
                        if (id == this.data[i].id) {
                            this.data[i].trangThai = 0;
                        }
                    }
                    let index = -1;
                    for (let i = 0; i < this.dataByStatus1.length; i++) {
                        if (id == this.dataByStatus1[i].id) {
                            index = i;
                        }
                    }
                    this.dataByStatus1.splice(index, 1);
                } else {
                    if (this.data[0].trangThai != response.data.data.trangThai) {
                        let index = -1;
                        for (let i = 0; i < this.data.length; i++) {
                            if (id == this.data[i].id) {
                                index = i;
                            }
                        }
                        this.data.splice(index, 1);
                        let index2 = -1;
                        for (let i = 0; i < this.dataByStatus1.length; i++) {
                            if (id == this.dataByStatus1[i].id) {
                                index2 = i;
                            }
                        }
                        this.dataByStatus1.splice(index2, 1);
                    }
                }
            });
        }
    }
});
