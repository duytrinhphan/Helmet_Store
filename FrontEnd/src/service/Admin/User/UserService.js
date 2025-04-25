import { defineStore } from 'pinia';
import axios from '@/service/Authentication/http.js';

const apiUser = `${import.meta.env.VITE_BASE_API_ENDPOINT}/admin/user/`;

export const userStore = defineStore('user', {
    state: () => ({
        data: [],
        diaChi: [],
        dataAdmin: [],
        dataNhanVien: [],
        dataUser: [],
        dataByStatus1: [],
        //nếu đang ở load tất cả thì là 0
        check: 1,
    }),
    actions: {
        async fetchDataByStatuss(status) {
            this.check = 0;
            try {
                const response = await axios.get(apiUser + 'trang-thai?trangThai=' + status);
                if (status === 0) {
                    this.dataByStatus1 = response.data.data;
                }
                this.data = response.data.data;
            } catch (error) {
                console.error('Error fetching users:', error);
            }
        },
        async fetchData() {
            this.check = 1;
            try {
                const response = await axios.get(apiUser + 'getAllUser');
                this.data = response.data;
            } catch (error) {
                console.error('Error fetching users:', error);
            }
        },
        deleteUser(id) {
            axios.put(apiUser + id + '/vo-hieu-hoa').then((response) => {
                for (let i = 0; i < this.data.length; i++) {
                    if (id == this.data[i].id) {
                        this.data[i].trangThai = 1;
                    }
                }
                if(response.data.role == 'ADMIN'){
                    // let index = -1;
                    for (let i = 0; i < this.dataAdmin.length; i++) {
                        if (id == this.dataAdmin[i].id) {
                            this.dataAdmin[i].trangThai = 1;
                        }
                    }
                    // this.dataAdmin.splice(index, 1);
                }
                if(response.data.role == 'NHANVIEN'){
                    for (let i = 0; i < this.dataNhanVien.length; i++) {
                        if (id == this.dataNhanVien[i].id) {
                            this.dataNhanVien[i].trangThai = 1;
                        }
                    }
                }
                if(response.data.role == 'USER'){
                    for (let i = 0; i < this.dataUser.length; i++) {
                        if (id == this.dataUser[i].id) {
                            this.dataUser[i].trangThai = 1;
                        }
                    }
                }
            });
        },
        createUser(form) {
            // console.log(form)
            axios.post(`${import.meta.env.VITE_BASE_API_ENDPOINT}/admin/user`, form).then((response) => {
                if(response.data.role == 'ADMIN'){
                    this.dataAdmin.unshift(response.data);
                    this.data.unshift(response.data);
                }
                if(response.data.role == 'NHANVIEN'){
                    this.dataNhanVien.unshift(response.data);
                    this.data.unshift(response.data);
                }
            });
        },

        createDiaChi(form) {
            // console.log(form)
            axios.post(`${import.meta.env.VITE_BASE_API_ENDPOINT}/admin/user/add-dia-chi`, form).then((response) => {
                this.diaChi.unshift(response.data);
            });
        },
        updateUser(id, form) {
            axios.put(apiUser + id, form).then((response) => {
                if(response.data.role == 'NHANVIEN'){
                    for (let i = 0; i < this.data.length; i++) {
                        if (id == this.data[i].id) {
                            this.data[i].ten = form.ten;
                            this.data[i].userName = form.userName;
                            this.data[i].email = form.email;
                            this.data[i].role = form.role;
                            this.data[i].sdt = form.sdt;
                            this.data[i].ngaySinh = form.ngaySinh;
                            this.data[i].gioiTinh = form.gioiTinh;
                            this.data[i].diaChi = form.diaChi;
                            this.data[i].image = form.image;
                        }
                    }
                    for (let i = 0; i < this.dataNhanVien.length; i++) {
                        if (id == this.dataNhanVien[i].id) {
                            this.dataNhanVien[i].ten = form.ten;
                            this.dataNhanVien[i].userName = form.userName;
                            this.dataNhanVien[i].email = form.email;
                            this.dataNhanVien[i].role = form.role;
                            this.dataNhanVien[i].sdt = form.sdt;
                            this.dataNhanVien[i].ngaySinh = form.ngaySinh;
                            this.dataNhanVien[i].gioiTinh = form.gioiTinh;
                            this.dataNhanVien[i].diaChi = form.diaChi;
                            this.dataNhanVien[i].image = form.image;
                        }
                    }
                }
                if(response.data.role =='ADMIN'){
                    for (let i = 0; i < this.data.length; i++) {
                        if (id == this.data[i].id) {
                            this.data[i].ten = form.ten;
                            this.data[i].userName = form.userName;
                            this.data[i].email = form.email;
                            this.data[i].role = form.role;
                            this.data[i].sdt = form.sdt;
                            this.data[i].ngaySinh = form.ngaySinh;
                            this.data[i].gioiTinh = form.gioiTinh;
                            this.data[i].diaChi = form.diaChi;
                            this.data[i].image = form.image;
                        }
                    }
                    for (let i = 0; i < this.dataAdmin.length; i++) {
                        if (id == this.dataAdmin[i].id) {
                            this.dataAdmin[i].ten = form.ten;
                            this.dataAdmin[i].userName = form.userName;
                            this.dataAdmin[i].email = form.email;
                            this.dataAdmin[i].role = form.role;
                            this.dataAdmin[i].sdt = form.sdt;
                            this.dataAdmin[i].ngaySinh = form.ngaySinh;
                            this.dataAdmin[i].gioiTinh = form.gioiTinh;
                            this.dataAdmin[i].diaChi = form.diaChi;
                            this.dataAdmin[i].image = form.image;
                        }
                    }
                }
            });
        },
        async fetchAllDiaChi(idProduct) {
            try {
                const response = await axios.get(apiUser+`${idProduct}/dia-chi`); // Thay đổi URL dựa trên API của bạn
                this.diaChi = response.data;
                //  console.log('mau: ',   this.mauSacs  );
                return this.diaChi;
            } catch (error) {
                console.error('Lỗi khi lấy danh sách sản phẩm:', error);
            }
        },
        async fetchDataRole(role) {
            try {
                const response = await axios.get(apiUser + 'getAllUserByRole?role=' + role);
                if (role == 'ADMIN') this.dataAdmin = response.data;
                if (role == 'NHANVIEN') this.dataNhanVien = response.data;
                if (role == 'USER') this.dataUser = response.data;
            } catch (error) {
                console.error('Error fetching users:', error);
            }
        },
        deleteAllUser(id) {
            axios.put(apiUser + id + '/vo-hieu-hoa').then((response) => {
                if (this.check == 1) {
                    for (let i = 0; i < this.data.length; i++) {
                        if (id == this.data[i].id) {
                            this.data[i].trangThai = 1;
                        }
                    }
                } else {
                    if (this.data[0].trangThai != response.data.data.trangThai) {
                        let index = -1;
                        for (let i = 0; i < this.data.length; i++) {
                            if (id == this.data[i].id) {
                                index = i;
                            }
                        }
                        this.data.splice(index, 1);
                    }
                }
            });
        },
        async sendMail(form) {
            try {
                const response = await axios.post(`${import.meta.env.VITE_BASE_API_ENDPOINT}/admin/mail/send`, form);
                return response.data;
            } catch (error) {
                console.error('Error sending mail:', error);
                throw error; // You can handle the error in the component that calls this action
            }
        },
        async sendOTP(form) {
            try {
                const response = await axios.post(`${import.meta.env.VITE_BASE_API_ENDPOINT}/admin/mail/sendOTP`, form);
                return response.data;
            } catch (error) {
                console.error('Error sending mail:', error);
                throw error; // You can handle the error in the component that calls this action
            }
        },
    }
});
