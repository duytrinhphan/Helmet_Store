import {defineStore} from 'pinia';
import {ref} from 'vue';
import axios from '@/service/Authentication/http.js';

const API_ENDPOINT = `${import.meta.env.VITE_BASE_API_ENDPOINT}/admin/ban-hang-tai-quay`;

export const useBanHangTaiQuayStore = defineStore('banHangTaiQuay', () => {
    const dsHDCho = ref([]);
    const dsHDCT = ref([]);
    const dsSP = ref([]);
    const dsPTTT = ref([]);
    const dsKH = ref([]);

    const loadHDCho = async () => {
        const response = await axios.get(`${API_ENDPOINT}/hoa-don`);
        dsHDCho.value = response.data;
    };

    const taoHDCho = async (idNV) => {
        const response = await axios.post(`${API_ENDPOINT}/hoa-don`, null, {
            params: {
                id_nv: idNV
            }
        });
        dsHDCho.value = response.data;
    }

    const huyHDCho = async (idHoaDon) => {
        const response = await axios.delete(`${API_ENDPOINT}/hoa-don/${idHoaDon}`);
        dsSP.value = response.data;
        dsHDCho.value = dsHDCho.value.filter(hdCho => hdCho.id !== idHoaDon);
    }

    const loadHDCT = async (idHoaDon) => {
        const response = await axios.get(`${API_ENDPOINT}/hoa-don-chi-tiet`, {
            params: {
                id_hd: idHoaDon
            }
        });
        dsHDCT.value = response.data;
    };

    const loadSP = async () => {
        const response = await axios.get(`${API_ENDPOINT}/san-pham`);
        dsSP.value = response.data;
    };

    const loadKH = async () => {
        const response = await axios.get(`${API_ENDPOINT}/khach-hang`);
        dsKH.value = response.data;
    }

    const loadPTTT = async () => {
        const response = await axios.get(`${API_ENDPOINT}/phuong-thuc-thanh-toan`);
        dsPTTT.value = response.data;
    };

    const themSPVaoHDCT = async (idHoaDon, idCTSP, soLuong) => {
        const response = await axios.post(`${API_ENDPOINT}/hoa-don-chi-tiet`, {idCTSP, soLuong}, {
            params: {
                id_hd: idHoaDon
            }
        });
        dsHDCT.value = response.data.allHDCT;
        dsSP.value = response.data.allSP;
    }

    const updateSLSPCuaHDCT = async (idHDCT, soLuong) => {
        const response = await axios.put(`${API_ENDPOINT}/hoa-don-chi-tiet/${idHDCT}`, null, {
            params: {
                so_luong: soLuong
            }
        });
        dsHDCT.value = response.data.allHDCT;
        dsSP.value = response.data.allCTSP;
    }

    const xoaHDCT = async (idHDCT) => {
        const response = await axios.delete(`${API_ENDPOINT}/hoa-don-chi-tiet/${idHDCT}`);
        dsHDCT.value = dsHDCT.value.filter(hdct => hdct.id !== idHDCT);
        dsSP.value = response.data;
    }

    const thanhToanHD = async (idHD, hdModel) => {
        await axios.put(`${API_ENDPOINT}/hoa-don/${idHD}`, hdModel);
        dsHDCho.value = dsHDCho.value.filter(hd => hd.id !== idHD);
    }

    const addOrUpdateKH = async (reqBody) => {
        const response = await axios.post(`${API_ENDPOINT}/khach-hang`, reqBody);
        if (reqBody.id === null) dsKH.value[0] = response.data;
        else dsKH.value = response.data;
    }

    const updateKHForHD = async (idHD, idKH) => {
        const response = await axios.put(`${API_ENDPOINT}/hoa-don/${idHD}/khach-hang`, null, {
            params: {
                id_kh: idKH,
            }
        });
        dsHDCho.value = response.data;
    }

    return {
        dsHDCho,
        dsHDCT,
        dsSP,
        dsPTTT,
        dsKH,
        loadHDCho,
        loadHDCT,
        loadSP,
        loadKH,
        loadPTTT,
        taoHDCho,
        huyHDCho,
        themSPVaoHDCT,
        updateSLSPCuaHDCT,
        xoaHDCT,
        thanhToanHD,
        addOrUpdateKH,
        updateKHForHD
    };
});
