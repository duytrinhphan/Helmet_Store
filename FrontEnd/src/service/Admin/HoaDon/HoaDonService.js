import { defineStore } from 'pinia';
// import axios from 'axios';
import axios from '@/service/Authentication/http.js';
import { pt } from 'date-fns/locale';
const apiHD = `${import.meta.env.VITE_BASE_API_ENDPOINT}/admin/hoaDon`;
const apiHDCT = `${import.meta.env.VITE_BASE_API_ENDPOINT}/admin/hoa-don-chi-tiet`;

export const HDStore = defineStore('hoaDon', {
    state: () => ({
        dataAll: [],
        dataChoXacNhan: [], //2
        dataDaHoanTra: [], //10
        dataDaHuy: [], //0
        dataDangChuanBi: [], //4
        dataDangGiao: [], //5
        dataHoanThanh: [], //3
        dataHoanTraHoanTien: [], //7
        dataXacNhanHoanTraHoanTien: [], //8
        dataDaHuyDoiTra: [], //9

        //nếu đang ở load tất cả thì là 0
        check: 0
        //theo doi xem đang ở màn nào
    }),
    actions: {
        //từ chờ xác nhận -> chuẩn bị
        choXacNhan(id) {
            axios.put(apiHD + '/XacNhan/' + id).then((response) => {
                if (this.check == 1) {
                    if (this.dataChoXacNhan[0].trangThai == '2') {
                        let index = -1;
                        for (let i = 0; i < this.dataChoXacNhan.length; i++) {
                            if (id == this.dataChoXacNhan[i].idHD) {
                                index = i;
                            }
                        }
                        this.dataChoXacNhan[index].trangThai = 4;
                        this.dataDangChuanBi.unshift(this.dataChoXacNhan[index]);
                        this.dataChoXacNhan.splice(index, 1);
                    }
                }
            });
        },
        //từ chờ đang chuẩn bị -> đang giao
        async dangChuanBi(id, ngayShip, tongTien, tienShip) {
            const respone = await axios.put(apiHD + '/XacNhanGiaoHang/' + id + '?ngayShip=' + ngayShip + `&tongTien=` + tongTien + `&tienShip=` + tienShip).then((response) => {
                if (this.check == 1) {
                    if (this.dataChoXacNhan[0].trangThai == '2') {
                        let index = -1;
                        for (let i = 0; i < this.dataChoXacNhan.length; i++) {
                            if (id == this.dataChoXacNhan[i].idHD) {
                                index = i;
                            }
                        }
                        this.dataChoXacNhan[index].trangThai = 5;
                        this.dataDangGiao.unshift(response.data);
                        this.dataChoXacNhan.splice(index, 1);
                        return response.data;
                    }
                }
            });
            return respone;
        },

        //từ đang giao -> hoàn thành
        hoanThanh(id) {
            axios.put(apiHD + '/hoan-thanh/' + id).then((response) => {
                if (this.check == 1) {
                    if (this.dataDangGiao[0].trangThai == '5') {
                        let index = -1;
                        for (let i = 0; i < this.dataDangGiao.length; i++) {
                            if (id == this.dataDangGiao[i].idHD) {
                                index = i;
                            }
                        }
                        // this.dataDangGiao[index].trangThai = 3;
                        this.dataHoanThanh.unshift(response.data);
                        this.dataDangGiao.splice(index, 1);
                    }
                }
            });
        },

        //từ Xác nhận đổi tra -> hoàn thành đổi trả
        hoanThanhDoiTra(id) {
            axios.put(apiHD + '/hoan-thanh-doi-tra/' + id).then((response) => {
                if (this.check == 1) {
                    if (this.dataXacNhanHoanTraHoanTien[0].trangThai == '8') {
                        let index = -1;
                        for (let i = 0; i < this.dataXacNhanHoanTraHoanTien.length; i++) {
                            if (id == this.dataXacNhanHoanTraHoanTien[i].idHD) {
                                index = i;
                            }
                        }
                        this.dataXacNhanHoanTraHoanTien[index].trangThai = 10;
                        this.dataDaHoanTra.unshift(this.dataXacNhanHoanTraHoanTien[index]);
                        this.dataXacNhanHoanTraHoanTien.splice(index, 1);
                    }
                }
            });
        },

        //từ Xác nhận đổi tra -> hoàn thành đổi trả không cộng lại số lượng
        hoanThanhDoiTraKhongCongSoLuong(id) {
            axios.put(apiHD + '/hoan-thanh-tra/' + id).then((response) => {
                if (this.check == 1) {
                    if (this.dataXacNhanHoanTraHoanTien[0].trangThai == '8') {
                        let index = -1;
                        for (let i = 0; i < this.dataXacNhanHoanTraHoanTien.length; i++) {
                            if (id == this.dataXacNhanHoanTraHoanTien[i].idHD) {
                                index = i;
                            }
                        }
                        this.dataXacNhanHoanTraHoanTien[index].trangThai = 10;
                        this.dataDaHoanTra.unshift(this.dataXacNhanHoanTraHoanTien[index]);
                        this.dataXacNhanHoanTraHoanTien.splice(index, 1);
                    }
                }
            });
        },

        //từ trả hàng -> xác nhận trả hàng
        traHang(id, idSPCT) {
            axios.put(apiHD + '/xac-nhan-doi-tra/' + id + '?idSPCT=' + idSPCT).then((response) => {
                if (this.check == 1) {
                    if (parseInt(this.dataHoanTraHoanTien[0].trangThai) == 7) {
                        let index = -1;
                        for (let i = 0; i < this.dataHoanTraHoanTien.length; i++) {
                            if (id == this.dataHoanTraHoanTien[i].idHD) {
                                index = i;
                            }
                        }
                        // this.dataHoanTraHoanTien[index].trangThai = 8;
                        this.dataXacNhanHoanTraHoanTien.unshift(response.data);
                        this.dataHoanTraHoanTien.splice(index, 1);
                    }
                }
            });
        },

        //huỷ hoá đơn
        huyHoaDon(id, lyDo, man) {
            axios.put(apiHD + '/huyXacNhan/' + id + '?lyDo=' + lyDo).then((response) => {
                if (this.check == 1) {
                    if (this.dataChoXacNhan.length > 0 && man == 2) {
                        if (this.dataChoXacNhan[0].trangThai == '2') {
                            let index = -1;
                            for (let i = 0; i < this.dataChoXacNhan.length; i++) {
                                if (id == this.dataChoXacNhan[i].idHD) {
                                    index = i;
                                }
                            }
                            this.dataChoXacNhan[index].trangThai = 0;
                            this.dataDaHuy.unshift(response.data);
                            this.dataChoXacNhan.splice(index, 1);
                        }
                    }
                    if (this.dataDangChuanBi.length > 0 && man == 4) {
                        if (this.dataDangChuanBi[0].trangThai == '4') {
                            let index = -1;
                            for (let i = 0; i < this.dataDangChuanBi.length; i++) {
                                if (id == this.dataDangChuanBi[i].idHD) {
                                    index = i;
                                }
                            }
                            this.dataDangChuanBi[index].trangThai = 0;
                            this.dataDaHuy.unshift(response.data);
                            this.dataDangChuanBi.splice(index, 1);
                        }
                    }
                    if (this.dataDangGiao.length > 0 && man == 5) {
                        if (this.dataDangGiao[0].trangThai == '5') {
                            let index = -1;
                            for (let i = 0; i < this.dataDangGiao.length; i++) {
                                if (id == this.dataDangGiao[i].idHD) {
                                    index = i;
                                }
                            }
                            this.dataDangGiao[index].trangThai = 0;
                            this.dataDaHuy.unshift(response.data);
                            this.dataDangGiao.splice(index, 1);
                        }
                    }
                }
            });
        },

        //huỷ hoá đơn đổi trả
        huyHoaDonDoiTra(id, lyDo, man, idSPCT) {
            axios.put(apiHD + '/huy-doi-tra/' + id + '?lyDo=' + lyDo + '&idSPCT=' + idSPCT).then((response) => {
                if (this.check == 1) {
                    if (this.dataHoanTraHoanTien.length > 0 && man == 7) {
                        if (this.dataHoanTraHoanTien[0].trangThai == '7') {
                            let index = -1;
                            for (let i = 0; i < this.dataHoanTraHoanTien.length; i++) {
                                if (id == this.dataHoanTraHoanTien[i].idHD) {
                                    index = i;
                                }
                            }
                            this.dataHoanTraHoanTien[index].trangThai = 9;
                            this.dataHoanTraHoanTien[index].soLuong = response.data.soLuong;
                            this.dataDaHuyDoiTra.unshift(this.dataHoanTraHoanTien[index]);
                            this.dataHoanTraHoanTien.splice(index, 1);
                        }
                    }
                }
            });
        },

        //gửi cho giao hàng nhanh
        async giaoHangNhanh(danhSachSP, hoaDon, formGHN) {
            const danhSachItem = [];
            for (let i = 0; i < danhSachSP.length; i++) {
                const form2 = {
                    name: danhSachSP[i].tenSP,
                    code: danhSachSP[i].maSP,
                    quantity: parseInt(danhSachSP[i].soLuong),
                    price: parseInt(danhSachSP[i].donGia),
                    length: 12,
                    width: 12,
                    height: 12,
                    weight: danhSachSP[i].trongLuong,
                    category: {
                        level1: 'Mũ'
                    }
                };
                danhSachItem.push(form2);
            }
            //api thật
            const form1 = {
                payment_type_id: 2,
                note: 'Tintest 123',
                required_note: 'KHONGCHOXEMHANG',
                from_name: 'Nguyễn Đức Dụng',
                from_phone: '0339927992',
                from_address: 'Số 29 ngõ 143',
                from_ward_name: 'Xuân Phương',
                from_district_name: 'Nam Từ Liêm',
                from_province_name: 'HN',
                return_phone: '0332190444',
                return_address: '39 NTT',
                return_district_id: 3440,
                return_ward_code: '13010',
                client_order_code: '',
                to_name: hoaDon.tenNguoiNhan,
                to_phone: hoaDon.sdt,
                to_address: hoaDon.diaChiCuThe,
                to_ward_code: hoaDon.idPhuongXa,
                to_district_id: parseInt(hoaDon.idQuanHuyen),
                cod_amount: 200000,
                content: hoaDon.maHD,
                weight: parseInt(formGHN.trongLuong),
                length: parseInt(formGHN.cao),
                width: parseInt(formGHN.dai),
                height: parseInt(formGHN.rong),
                pick_station_id: 1444,
                deliver_station_id: null,
                insurance_value: 500000,
                service_id: 0,
                service_type_id: 2,
                coupon: null,
                pick_shift: [2],
                items: danhSachItem
            };
            // api xem trước
            const form2 = {
                payment_type_id: 2,
                note: 'Tintest 123',
                required_note: 'KHONGCHOXEMHANG',
                return_phone: '0339927992',
                return_address: '39 NTT',
                return_district_id: null,
                return_ward_code: '',
                client_order_code: '',
                to_name: hoaDon.tenNguoiNhan,
                to_phone: hoaDon.sdt,
                to_address: hoaDon.diaChiCuThe,
                to_ward_code: hoaDon.idPhuongXa,
                to_district_id: parseInt(hoaDon.idQuanHuyen),
                cod_amount: 300000,
                content: hoaDon.maHD,
                weight: parseInt(formGHN.trongLuong),
                length: parseInt(formGHN.cao),
                width: parseInt(formGHN.dai),
                height: parseInt(formGHN.rong),
                pick_station_id: 0,
                insurance_value: 500000,
                service_id: 0,
                service_type_id: 2,
                coupon: null,
                pick_shift: [2],
                items: danhSachItem
            };
            // console.log(form1);
            try {
                const response = await axios.post('https://online-gateway.ghn.vn/shiip/public-api/v2/shipping-order/create', form1, {
                    headers: {
                        // Thêm headers vào yêu cầu POST ở đây
                        token: 'fc6bed6e-7bd8-11ee-af43-6ead57e9219a',
                        ShopId: '4677532'
                    }
                });
                // console.log(response);
                return response;
            } catch (error) {
                console.log('Error fetching users:', error);
            }
        },
        //lấy sp theo id hoá đơn
        async findHdctByIdHd(idHD) {
            try {
                const response = await axios.get(apiHDCT + '/find-by-id-hd/' + idHD);
                return response.data;
            } catch (error) {
                console.error('Error fetching users:', error);
            }
        },

        //load tất cả data
        async fetchData() {
            this.check = 0;
            try {
                const response = await axios.get(apiHD);
                this.dataAll = response.data;
                return this.dataAll;
            } catch (error) {
                console.error('Error fetching users:', error);
            }
        },

        //load tất cả data theo pttt
        async fetchDataByPttt(pttt) {
            this.check = 0;
            try {
                const response = await axios.get(apiHD + '/find-tat-ca-by-pttt?pttt=' + pttt);
                this.dataAll = response.data;
                return this.dataAll;
            } catch (error) {
                console.error('Error fetching users:', error);
            }
        },

        //load tất cả data theo hình thức giao
        async fetchDataByHinhThucGiao(hinhThucGiao) {
            this.check = 0;
            try {
                const response = await axios.get(apiHD + '/find-tat-ca-by-hinh-thuc-giao?hinhThucGiao=' + hinhThucGiao);
                this.dataAll = response.data;
                return this.dataAll;
            } catch (error) {
                console.error('Error fetching users:', error);
            }
        },

        //load tất cả data theo hình thức giao and pttt
        async fetchDataByHinhThucGiaoAndPttt(hinhThucGiao, pttt) {
            this.check = 0;
            try {
                const response = await axios.get(apiHD + '/find-tat-ca-by-hinh-thuc-giao-and_pttt?hinhThucGiao=' + hinhThucGiao + '&pttt=' + pttt);
                this.dataAll = response.data;
                return this.dataAll;
            } catch (error) {
                console.error('Error fetching users:', error);
            }
        },
        //load data hd theo trạng thái
        async fetchDataByStatus(status) {
            this.check = 1;
            try {
                const response = await axios.get(apiHD + '/hoaDonTrangThai/' + status);
                if (status == 2) this.dataChoXacNhan = response.data;
                if (status == 0) this.dataDaHuy = response.data;
                if (status == 4) this.dataDangChuanBi = response.data;
                if (status == 5) this.dataDangGiao = response.data;
                if (status == 3) this.dataHoanThanh = response.data;
            } catch (error) {
                console.error('Error fetching users:', error);
            }
        },

        //load data hd theo trạng thái and htgd
        async fetchDataByStatusAndHinhThucGiao(status, hinhThucGiao) {
            this.check = 1;
            try {
                const response = await axios.get(apiHD + '/hoaDonTrangThai/hinh-thuc-giao/' + status + '?hinhThucGiao=' + hinhThucGiao);
                if (status == 2) this.dataChoXacNhan = response.data;
                if (status == 0) this.dataDaHuy = response.data;
                if (status == 4) this.dataDangChuanBi = response.data;
                if (status == 5) this.dataDangGiao = response.data;
                if (status == 3) this.dataHoanThanh = response.data;
            } catch (error) {
                console.error('Error fetching users:', error);
            }
        },

        //load data hd theo trạng thái and pttt
        async fetchDataByStatusAndPttt(status, pttt) {
            this.check = 1;
            try {
                const response = await axios.get(apiHD + '/hoaDonTrangThai/pttt/' + status + '?pttt=' + pttt);
                if (status == 2) this.dataChoXacNhan = response.data;
                if (status == 0) this.dataDaHuy = response.data;
                if (status == 4) this.dataDangChuanBi = response.data;
                if (status == 5) this.dataDangGiao = response.data;
                if (status == 3) this.dataHoanThanh = response.data;
            } catch (error) {
                console.error('Error fetching users:', error);
            }
        },

        //load data hd theo trạng thái and pttt
        async fetchDataByStatusAndPtttAndHtgh(status, pttt, htgh) {
            this.check = 1;
            try {
                const response = await axios.get(apiHD + '/hoaDonTrangThai/pttt-and-htgh/' + status + '?pttt=' + pttt + '&htgh=' + htgh);
                if (status == 2) this.dataChoXacNhan = response.data;
                if (status == 0) this.dataDaHuy = response.data;
                if (status == 4) this.dataDangChuanBi = response.data;
                if (status == 5) this.dataDangGiao = response.data;
                if (status == 3) this.dataHoanThanh = response.data;
            } catch (error) {
                console.error('Error fetching users:', error);
            }
        },
        //load data hd trả theo trạng thái
        async fetchDataHDCTByStatus(status) {
            this.check = 1;
            try {
                const response = await axios.get(apiHDCT + '/find-by-trang-thai/' + status);
                if (status == 8) this.dataXacNhanHoanTraHoanTien = response.data;
                if (status == 7) this.dataHoanTraHoanTien = response.data;
                if (status == 9) this.dataDaHuyDoiTra = response.data;
            } catch (error) {
                console.error('Error fetching users:', error);
            }
        },
        //search date
        async searchDate(startDate, endDate, cbbValue) {
            try {
                const response = await axios.get(apiHD + '/search-date?startDate=' + startDate + '&endDate=' + endDate + '&comboBoxValue=' + cbbValue);
                this.dataAll = response.data;
                return this.dataAll;
            } catch (error) {
                console.error('Error fetching users:', error);
            }
        },

        //search date by hình thức giao
        async searchDateByHinhThucGiao(startDate, endDate, cbbValue, hinhThucGiao) {
            try {
                const response = await axios.get(apiHD + '/search-date-by-hinh-thuc-giao?startDate=' + startDate + '&endDate=' + endDate + '&comboBoxValue=' + cbbValue + '&hinhThucGiao=' + hinhThucGiao);
                this.dataAll = response.data;
                return this.dataAll;
            } catch (error) {
                console.error('Error fetching users:', error);
            }
        },

        //search date by pttt
        async searchDateByPttt(startDate, endDate, cbbValue, pttt) {
            try {
                const response = await axios.get(apiHD + '/search-date-by-pttt?startDate=' + startDate + '&endDate=' + endDate + '&comboBoxValue=' + cbbValue + '&pttt=' + pttt);
                this.dataAll = response.data;
                return this.dataAll;
            } catch (error) {
                console.error('Error fetching users:', error);
            }
        },

        //search date by pttt and htgh
        async searchDateByPtttAndHtgh(startDate, endDate, cbbValue, pttt, htgh) {
            try {
                const response = await axios.get(apiHD + '/search-date-by-pttt-and-htgh?startDate=' + startDate + '&endDate=' + endDate + '&comboBoxValue=' + cbbValue + '&pttt=' + pttt + '&htgh=' + htgh);
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
                if (trangThai == 10) {
                    this.dataDaHoanTra = response.data;
                    return this.dataDaHoanTra;
                }
                if (trangThai == 8) {
                    this.dataXacNhanHoanTraHoanTien = response.data;
                    return this.dataXacNhanHoanTraHoanTien;
                }
                if (trangThai == 9) {
                    this.dataDaHuyDoiTra = response.data;
                    return this.dataDaHuyDoiTra;
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
        //search date theo trạng thái
        async searchDateByTrangThaiAndHinhThucGiao(startDate, endDate, cbbValue, trangThai, hinhThucGiao) {
            try {
                const response = await axios.get(apiHD + '/search-date-by-trang-thai-and-hinh-thuc-giao?startDate=' + startDate + '&endDate=' + endDate + '&comboBoxValue=' + cbbValue + '&trangThai=' + trangThai + '&hinhThucGiao=' + hinhThucGiao);
                if (trangThai == 2) {
                    this.dataChoXacNhan = response.data;
                    return this.dataChoXacNhan;
                }
                if (trangThai == 10) {
                    this.dataDaHoanTra = response.data;
                    return this.dataDaHoanTra;
                }
                if (trangThai == 8) {
                    this.dataXacNhanHoanTraHoanTien = response.data;
                    return this.dataXacNhanHoanTraHoanTien;
                }
                if (trangThai == 9) {
                    this.dataDaHuyDoiTra = response.data;
                    return this.dataDaHuyDoiTra;
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

        //search date theo trạng thái
        async searchDateByTrangThaiAndPttt(startDate, endDate, cbbValue, trangThai, pttt) {
            try {
                const response = await axios.get(apiHD + '/search-date-by-trang-thai-and-pttt?startDate=' + startDate + '&endDate=' + endDate + '&comboBoxValue=' + cbbValue + '&trangThai=' + trangThai + '&pttt=' + pttt);
                if (trangThai == 2) {
                    this.dataChoXacNhan = response.data;
                    return this.dataChoXacNhan;
                }
                if (trangThai == 10) {
                    this.dataDaHoanTra = response.data;
                    return this.dataDaHoanTra;
                }
                if (trangThai == 8) {
                    this.dataXacNhanHoanTraHoanTien = response.data;
                    return this.dataXacNhanHoanTraHoanTien;
                }
                if (trangThai == 9) {
                    this.dataDaHuyDoiTra = response.data;
                    return this.dataDaHuyDoiTra;
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
        async searchDateByTrangThaiAndPtttAndHtgh(startDate, endDate, cbbValue, trangThai, pttt, htgh) {
            try {
                const response = await axios.get(apiHD + '/search-date-by-trang-thai-and-pttt-and-htgh?startDate=' + startDate + '&endDate=' + endDate + '&comboBoxValue=' + cbbValue + '&trangThai=' + trangThai + '&pttt=' + pttt + '&htgh=' + htgh);
                if (trangThai == 2) {
                    this.dataChoXacNhan = response.data;
                    return this.dataChoXacNhan;
                }
                if (trangThai == 10) {
                    this.dataDaHoanTra = response.data;
                    return this.dataDaHoanTra;
                }
                if (trangThai == 8) {
                    this.dataXacNhanHoanTraHoanTien = response.data;
                    return this.dataXacNhanHoanTraHoanTien;
                }
                if (trangThai == 9) {
                    this.dataDaHuyDoiTra = response.data;
                    return this.dataDaHuyDoiTra;
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
        }
    }
});
