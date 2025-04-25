import { defineStore } from 'pinia';
import axios from '@/service/Authentication/http.js';

export const TrongLuongStore = defineStore('trongLuong', {
    state: () => ({
        data: [],

        dataByStatus1: [],

        check: 0
    }),
    actions: {
        async fetchAll() {
            this.check = 0;
            try {
                const response = await axios.get(`${import.meta.env.VITE_BASE_API_ENDPOINT}/admin/trong-luong`); // Thay đổi URL dựa trên API của bạn
                this.data = response.data.data;
                // console.table(this.data);
            } catch (error) {
                console.error('Lỗi khi lấy danh sách sản phẩm:', error);
            }
        },
        async fetchDataByStatus(status) {
            this.check = 1;
            try {
                const response = await axios.get(`${import.meta.env.VITE_BASE_API_ENDPOINT}/admin/trong-luong` + '/trang-thai?trangThai=' + status);
                if (status === 1) {
                    this.dataByStatus1 = response.data.data;
                }
                this.data = response.data.data;
            } catch (error) {
                console.error('Error fetching users:', error);
            }
        },
        createTrongLuong(form) {
            axios.post(`${import.meta.env.VITE_BASE_API_ENDPOINT}/admin/trong-luong` + '/add', form).then((response) => {
                if (this.check == 0) {
                    this.data.unshift(response.data.data);
                    if (response.data.data.trangThai == 1) this.dataByStatus1.unshift(response.data.data);
                } else {
                    if (this.data[0].trangThai == response.data.data.trangThai) this.data.unshift(response.data.data);
                    if (response.data.data.trangThai == 1) this.dataByStatus1.unshift(response.data.data);
                }
                // console.log(response.data.data);
            });
        },
        updateTrongLuong(id, form) {
            axios.put(`${import.meta.env.VITE_BASE_API_ENDPOINT}/admin/trong-luong` + '/update/' + id, form).then((response) => {
                for (let i = 0; i < this.data.length; i++) {
                    if (id == this.data[i].id) {
                        this.data[i].donVi = form.donVi;
                        this.data[i].value = form.value;

                    }
                }
                for (let i = 0; i < this.dataByStatus1.length; i++) {
                    if (id == this.dataByStatus1[i].id) {
                        this.dataByStatus1[i].ten = form.ten;
                        this.dataByStatus1[i].value = form.value;

                    }
                }
            });
        },
        deleteTrongLuong(id) {
            axios.put(`${import.meta.env.VITE_BASE_API_ENDPOINT}/admin/trong-luong` + '/delete/' + id).then((response) => {
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
