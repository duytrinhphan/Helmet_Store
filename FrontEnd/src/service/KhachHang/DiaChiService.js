import { defineStore } from 'pinia';
import axios from 'axios';

const api = `${import.meta.env.VITE_BASE_API_ENDPOINT}/khach-hang/dia-chi`;
const baseUrl = 'https://online-gateway.ghn.vn/shiip/public-api/master-data/';
const headers = {
    Token: 'fc6bed6e-7bd8-11ee-af43-6ead57e9219a'
};
export const useDiaChi = defineStore('diaChi', {
    state: () => ({
        user: [],
        diaChi: [],
        data: [],
        diaChiMacDinh: [],
        diaChiThanhToan: []
    }),
    actions: {
        async fetchData(id) {
            try {
                const response = await axios.get(api + '/find-all?token=' + id);
                this.diaChi = response.data;
            } catch (error) {
                console.error('Error fetching users:', error);
            }
        },
        async findDiaChiByIdUserAndTrangThai(token) {
            try {
                const response = await axios.get(api + '/find-dia-chi-mac-dinh?token=' + token);
                this.diaChiMacDinh = response.data;
            } catch (error) {
                console.error('Error fetching users:', error);
            }
        },

        async fetchDiaChiThanhToan(id) {
            try {
                const response = await axios.get(api + '/thanh-toan/' + id);
                this.diaChiThanhToan = response.data;
            } catch (error) {
                console.error('Error fetching users:', error);
            }
        },

        async fetchDiaChiThanhToanModal(id, token) {
            try {
                const response = await axios.put(api + '/thiet-lap-mac-dinh/' + id + `?token=${token}`);
                // this.diaChiMacDinh = response.data;
                return response.data;
            } catch (error) {
                console.error('Error fetching users:', error);
            }
        },

        async fetchAllDiaChi(idUser) {
            try {
                const response = await axios.get(api + `/${idUser}`); // Thay đổi URL dựa trên API của bạn
                this.diaChi = response.data;
                //  console.log('mau: ',   this.mauSacs  );
                // return this.diaChi;
            } catch (error) {
                console.error('Lỗi khi lấy danh sách địa chỉ:', error);
            }
        },
        createDiaChi(form, token) {
            axios
                .post(api + `/add?token=${token}`, form)
                .then((response) => {
                    this.diaChi.unshift(response.data);
                    if(response.data.trangThai === 1){
                        this.diaChiMacDinh = response.data;
                    }
                })
                .catch((error) => {
                    // console.error('Lỗi khi tạo:', error);
                });
        },
        deleteDiaChi(id) {
            axios
                .put(api + `/${id}/delete`)
                .then((response) => {
                    // console.log("Đã xóa thành công");
                })
                .catch((error) => {
                    // Xử lý lỗi nếu có
                    // console.error("Lỗi khi xóa địa chỉ: ", error);
                });
        },

        async updateDiaChi(id, form, token) {
            await axios.put(api + '/update/' + id + `?token=${token}`, form);
            for (let i = 0; i < this.diaChi.length; i++) {
                if (id == this.diaChi[i].id) {
                    this.diaChi[i].idTinhThanh = form.idTinhThanh;
                    this.diaChi[i].tenTinhThanh = form.tinhThanh;
                    this.diaChi[i].idQuanHuyen = form.idQuanHuyen;
                    this.diaChi[i].tenQuanHuyen = form.quanHuyen;
                    this.diaChi[i].idPhuongXa = form.idPhuongXa;
                    this.diaChi[i].tenphuongXa = form.phuongXa;
                    this.diaChi[i].diaChi = form.diaChi;
                    if(this.diaChi[i].trangThai === 1){
                     
                        this.diaChiMacDinh.idTinhThanh = form.idTinhThanh;
                        this.diaChiMacDinh.tenTinhThanh = form.tinhThanh;
                        this.diaChiMacDinh.idQuanHuyen = form.idQuanHuyen;
                        this.diaChiMacDinh.tenQuanHuyen = form.quanHuyen;
                        this.diaChiMacDinh.idPhuongXa = form.idPhuongXa;
                        this.diaChiMacDinh.tenphuongXa = form.phuongXa;
                        this.diaChiMacDinh.diaChi = form.diaChi;
                    }
                }
            }

        },
        async fetchTinhThanh() {
            try {
                const response = await axios.get(baseUrl + 'province', { headers });
                // console.log('API Response:', response.data);
                this.tinhThanhList = response.data.data;
                return this.tinhThanhList;
            } catch (error) {
                console.error('Lỗi khi lấy danh sách tỉnh/thành phố:', error);
            }
        },

        async fetchQuanHuyen(tinhThanhId) {
            try {
                const response = await axios.get(baseUrl + `district?province_id=${tinhThanhId}`, { headers });
                return response.data.data;
            } catch (error) {
                console.error('Lỗi khi lấy danh sách quận/huyện:', error);
            }
        },

        async fetchPhuongXa(quanHuyenId) {
            try {
                const response = await axios.get(baseUrl + `ward?district_id=${quanHuyenId}`, { headers });
                return response.data.data;
            } catch (error) {
                console.error('Lỗi khi lấy danh sách phường/xã:', error);
            }
        }
    }
});
