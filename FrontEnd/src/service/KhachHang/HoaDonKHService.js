import { defineStore } from 'pinia';
import axios from 'axios';

const apiHD = `${import.meta.env.VITE_BASE_API_ENDPOINT}/khach-hang/hoa-don`;
const apiHDCT = `${import.meta.env.VITE_BASE_API_ENDPOINT}/khach-hang/hoa-don-chi-tiet`;

export const HDKHStore = defineStore('hoaDonKH', {
    state: () => ({
        data: [],
        dataAll: [],
        dataChoThanhToan: [], //1
        dataDaHoanTra: [], //8
        dataDaHuy: [], //0
        dataDangChuanBi: [], //4
        dataDangGiao: [], //5
        dataHoanThanh: [], //3
        dataHoanTraHoanTien: [], //7
        //nếu đang ở load tất cả thì là 0
        check: 0,

       dataSP:[],
       dataHD:[], 
    }),
    actions: {
        //load tất cả data
        async fetchData(token) {
            this.check = 0;
            try {
                const response = await axios.get(apiHD + '/find-all?token=' + token);
                const hoaDonList = response.data;
                for (const [key, product] of hoaDonList.entries()) {
                    hoaDonList[key]['sanPhamChiTiet'] = null;
                    const mau = await this.findHdctByIdHd(product.idHD);
                    // mau.sort((a, b) => b.id - a.id);
                    hoaDonList[key]['sanPhamChiTiet'] = mau;
                }
                this.dataAll = hoaDonList;
            } catch (error) {
                console.error('Error fetching users:', error);
            }
        },
        //load data hd theo trạng thái
        async fetchDataByStatus(token, status, status2, status3 , status4) {
            this.check = 1;
            try {
                const response = await axios.get(apiHD + '/find-all-by-trang-thai?token=' + token + '&trangThai=' + status + '&trangThai2=' + status2 + '&trangThai3=' + status3 + '&trangThai4=' + status4);
                if (status == 1) {
                    const hoaDonList = response.data;
                    for (const [key, product] of hoaDonList.entries()) {
                        hoaDonList[key]['sanPhamChiTiet'] = null;
                        const mau = await this.findHdctByIdHd(product.idHD);
                        // mau.sort((a, b) => b.id - a.id);
                        hoaDonList[key]['sanPhamChiTiet'] = mau;
                    }
                    this.dataChoThanhToan = hoaDonList;
                }
                if (status == 2) {
                    const hoaDonList = response.data;
                    for (const [key, product] of hoaDonList.entries()) {
                        hoaDonList[key]['sanPhamChiTiet'] = null;
                        const mau = await this.findHdctByIdHd(product.idHD);
                        // mau.sort((a, b) => b.id - a.id);
                        hoaDonList[key]['sanPhamChiTiet'] = mau;
                    }
                    this.dataDangChuanBi = hoaDonList;
                }
                if (status == 5) {
                    const hoaDonList = response.data;
                    for (const [key, product] of hoaDonList.entries()) {
                        hoaDonList[key]['sanPhamChiTiet'] = null;
                        const mau = await this.findHdctByIdHd(product.idHD);
                        // mau.sort((a, b) => b.id - a.id);
                        hoaDonList[key]['sanPhamChiTiet'] = mau;
                    }
                    this.dataDangGiao = hoaDonList;
                }
                if (status == 3) {
                    const hoaDonList = response.data;
                    for (const [key, product] of hoaDonList.entries()) {
                        hoaDonList[key]['sanPhamChiTiet'] = null;
                        const mau = await this.findHdctByIdHd(product.idHD);
                        // mau.sort((a, b) => b.id - a.id);
                        hoaDonList[key]['sanPhamChiTiet'] = mau;
                    }
                    this.dataHoanThanh = hoaDonList;
                }
                if (status == 7 || status2 == 8 || status3 == 9 || status4 == 10) {
                    const hoaDonList = response.data;
                    for (const [key, product] of hoaDonList.entries()) {
                        hoaDonList[key]['sanPhamChiTiet'] = null;
                        const mau = await this.findHdctByIdHd(product.idHD);
                        // mau.sort((a, b) => b.id - a.id);
                        hoaDonList[key]['sanPhamChiTiet'] = mau;
                    }
                    this.dataHoanTraHoanTien = hoaDonList;
                }
                if (status == 0) {
                    const hoaDonList = response.data;
                    for (const [key, product] of hoaDonList.entries()) {
                        hoaDonList[key]['sanPhamChiTiet'] = null;
                        const mau = await this.findHdctByIdHd(product.idHD);
                        // mau.sort((a, b) => b.id - a.id);
                        hoaDonList[key]['sanPhamChiTiet'] = mau;
                    }
                    this.dataDaHuy = hoaDonList;
                }
            } catch (error) {
                console.error('Error fetching users:', error);
            }
        },

        async findHdct(token) {
            try {
                const response = await axios.get(apiHDCT + '/find-by-id-hd-tra?token=' + token);
                const hoaDonList = response.data;
                for (const [key, product] of hoaDonList.entries()) {
                    hoaDonList[key]['sanPhamChiTiet'] = null;
                    const mau = await this.findHdctByIdHd(product.idHD);
                  
                    const doiTuong = mau.filter(item => item.trangThaiHDCT === 7 ||  item.trangThaiHDCT === 8  ||  item.trangThaiHDCT === 9);
                   
                    // mau.sort((a, b) => b.id - a.id);
                    hoaDonList[key]['sanPhamChiTiet'] = doiTuong;
                }
                this.dataHoanTraHoanTien = hoaDonList;
            } catch (error) {
                console.error('Error fetching users:', error);
            }
        },

        //lấy sp theo id hoá đơn
        async findHdByIdHd(idHD) {
            try {
                const response = await axios.get(apiHD + '/find-by-id/' + idHD);
                this.dataHD = response.data;
      //          console.log('HD: ',this.dataHD )
                return response.data;
            } catch (error) {
                console.error('Error fetching users:', error);
            }
        },

        async findHdByMaHd(maHD) {
            try {
                const response = await axios.get(apiHD + '/find-by-ma/' + maHD);
                return response.data;
            } catch (error) {
                console.error('Error fetching users:', error);
            }
        },

         //lấy sp theo id hoá đơn
         async findHdById(idHD) {
            try {
                const response = await axios.get(apiHD + '/find-by-id/' + idHD);
                this.data = response.data;
            } catch (error) {
                console.error('Error fetching users:', error);
            }
        },

        //lấy sp theo id hoá đơn
        async findHdctByIdHd(idHD) {
          
            try {
                const response = await axios.get(apiHDCT + '/find-by-id-hd/' + idHD);
                this.dataSP = response.data;
              //  console.log('SP: ',this.dataSP  )
                return response.data;
            } catch (error) {
                console.error('Error fetching users:', error);
            }
        },

        async findHdctByMaHd(maHD) {
            try {
                const response = await axios.get(apiHDCT + '/find-by-ma-hd/' + maHD);
                return response.data;
            } catch (error) {
                console.error('Error fetching users:', error);
            }
        },

        async huyHoaDons(id, lyDo) {
            try {
                const response = await axios.put(apiHD + '/huy-hoa-don/' + id + '?lyDo=' + lyDo); // Thay đổi URL và dữ liệu updatedProduct tùy theo API của bạn
                const index = this.dataDangChuanBi.findIndex(product => product.idHD === id);
                if (index !== -1) {
                    let newProductData = this.dataDangChuanBi[index];
                    newProductData = response.data;
                    newProductData['sanPhamChiTiet'] = null;
                    const mau = await this.findHdctByIdHd(id);
                    // mau.sort((a, b) => b.id - a.id);
                    newProductData['sanPhamChiTiet'] = mau;
                
                    for (let i = 0; i <  this.dataAll.length; i++){
                     if(this.dataAll[i].idHD == id){
                        // console.log(this.dataAll[i].id);
                        this.dataAll[i].trangThai = 0;
                     }
                    }
                    this.dataDaHuy.unshift(newProductData);
                    this.dataDangChuanBi.splice(index, 1);
                }
            } catch (error) {
                console.error('Lỗi khi sửa sản phẩm:', error);
            }
        },

        //search date
        async searchDate(startDate, endDate, cbbValue) {
            // console.log(cbbValue);
            try {
                const response = await axios.get(apiHD + '/search-date?startDate=' + startDate + '&endDate=' + endDate + '&comboBoxValue=' + cbbValue);
                this.dataAll = response.data;
                return this.dataAll;
            } catch (error) {
                console.error('Error fetching users:', error);
            }
        },
        //search date theo trạng thái
        async searchDateByTrangThai(startDate, endDate, cbbValue, trangThai) {
            try {
                const response = await axios.get(apiHD + '/search-date-by-trang-thai?startDate=' + startDate + '&endDate=' + endDate + '&comboBoxValue=' + cbbValue + '&trangThai=' + trangThai);
                if (trangThai == 2) {
                    this.dataChoXacNhan = response.data;
                    return this.dataChoXacNhan;
                }
                if (trangThai == 8) {
                    this.dataDaHoanTra = response.data;
                    return this.dataDaHoanTra;
                }
                if (trangThai == 0) {
                    this.dataDaHuy = response.data;
                    return this.dataDaHuy;
                }
                if (trangThai == 4) {
                    this.dataDangChuanBi = response.data;
                    return this.dataDangChuanBi;
                }
                if (trangThai == 5) {
                    this.dataDangGiao = response.data;
                    return this.dataDangGiao;
                }
                if (trangThai == 3) {
                    this.dataHoanThanh = response.data;
                    return this.dataHoanThanh;
                }
                if (trangThai == 7) {
                    this.dataHoanTraHoanTien = response.data;
                    return this.dataHoanTraHoanTien;
                }
            } catch (error) {
                console.error('Error fetching users:', error);
            }
        },

        async doiTra(token, newHoaDon) {
            try {
                const response = await axios.post(apiHD + `/doi-tra?token=${token}`, newHoaDon);
             //   console.log(response.data)
                this.dataDaHoanTra.unshift(response.data);
            } catch (error) {
                console.error('Error fetching users:', error);
            }
        },

        async huydoiTra(id) {
            try {
                const response = await axios.delete(apiHD + `/huy-doi-tra/${id}`);
             //   console.log(response.data)
                this.dataDaHoanTra.slice(response.data);
            } catch (error) {
                console.error('Error fetching users:', error);
            }
        },
    }
});
