<!-- eslint-disable no-unused-vars -->
<script setup>
import { FilterMatchMode, FilterOperator } from 'primevue/api';
import { ref, onBeforeMount, onMounted, watch } from 'vue';
import { useToast } from 'primevue/usetoast';
import { HDKHStore } from '@/service/KhachHang/HoaDonKHService';
import DetailHoaDon from './TrangThaiDonHang.vue';
import { useRouter } from 'vue-router';
import { gioHangStore } from '@/service/KhachHang/Giohang/GiohangCTService';
import { useDetailProductStore } from '@/service/KhachHang/DetailService';

const productStore = useDetailProductStore();
const router = useRouter();
const gioHangService = gioHangStore();
const redirectToTrangThaiDonHang = (id) => {
    // Chuyển hướng đến trang trang-thai-don-hang và truyền ID của hóa đơn qua URL
    router.push({ name: 'trang-thai-don-hang', params: { id: id } });
};
const toast = useToast();
const useHD = HDKHStore();
const filters1 = ref(null);
const idHD = ref(null);
const data = ref([]);
// confirm xác nhận
const addProductDialog = ref(false);

//hiện confirm
const confirmAddProduct = (id) => {
    idHD.value = id;
    addProductDialog.value = true;
};

watch(addProductDialog, (newVal) => {
    if (addProductDialog.value == false) {
        idHD.value = null;
    }
});
const loadData = async () => {
    const token = localStorage.getItem('token');
    if (token.length > 0 || token != null) {
        await useHD.fetchDataByStatus(token, 5, "", "","");
        data.value = useHD.dataDangGiao;
    }
};
//chạy cái hiện data luôn
onMounted(() => {
    loadData();
});

const hienThiTrangThai = (trangThai) => {
    if (trangThai == 0) {
        return { text: 'Đã hủy', severity: 'danger' };
    } else if (trangThai == 1) {
        return { text: 'Chờ thanh toán', severity: 'secondary' };
    } else if (trangThai == 2) {
        return { text: 'Yêu cầu xác nhận', severity: 'success' };
    } else if (trangThai == 3) {
        return { text: 'Hoàn thành', severity: 'info' };
    } else if (trangThai == 4) {
        return { text: 'Đang chuẩn bị hàng', severity: 'success' };
    } else if (trangThai == 5) {
        return { text: 'Đang giao', severity: 'help' };
    } else if (trangThai == 6) {
        return { text: 'Yêu cầu đổi trả', severity: 'warning' };
    } else {
        return { text: 'Xác nhận đổi trả', severity: 'success' };
    }
};

const btnXacNhan = () => {
    useHD.hoanThanh(idHD.value);
    toast.add({ severity: 'success', summary: 'Thông báo', detail: 'Xác nhận thành công', life: 3000 });
    addProductDialog.value = false;
};

const columns = ref([
    { field: 'maHD', header: 'Mã hoá đơn' },
    { field: 'tenNguoiNhan', header: 'Tên người nhận' },
    { field: 'tongTien', header: 'Tổng tiền' },
    { field: 'tienSauKhiGiam', header: 'Tiền sau giảm' }
]);
const startDate = ref(null);
const endDate = ref([null]);
const typeSearchDate = ref(null);

const searchDate = async () => {
    if (typeSearchDate.value == null) {
        const respone = await useHD.searchDateByTrangThai(startDate.value, endDate.value, 'ngayTao', 5);
        data.value = respone;
    } else {
        const respone = await useHD.searchDateByTrangThai(startDate.value, endDate.value, typeSearchDate.value.value, 5);
        data.value = respone;
    }
};

const selectedColumns = ref(columns.value);

const onToggle = (val) => {
    selectedColumns.value = columns.value.filter((col) => val.includes(col));
};


onBeforeMount(() => {

    initFilters1();
});

const initFilters1 = () => {
    filters1.value = {
        global: { value: null, matchMode: FilterMatchMode.CONTAINS }
    };
};

const formatCurrency = (value) => {
    return parseInt(value).toLocaleString('vi-VN', { style: 'currency', currency: 'VND' });
};
const formatDate = (value) => {
    return value.toLocaleDateString('en-US', {
        day: '2-digit',
        month: '2-digit',
        year: 'numeric'
    });
};

const muaLaiDialog = ref(false);
const dataSP = ref([]);
const showDialogMuaLai = async (idHD) => {
    dataSP.value = await useHD.findHdctByIdHd(idHD);
    muaLaiDialog.value = true;
};

const dataGHCT = ref([]);
const soLuongGH = ref(0);
const dataListSPCT = ref([]);
const loaddataListSPCT = async (idProduct, idSize, idMau) => {
    await productStore.fetchIdSPCT(idProduct, idSize, idMau);
    dataListSPCT.value = productStore.products;
};
const addCart = async (soLuong, idCTSP, idSize, idMau) => {
    const cartItem = {
        soLuong: soLuong,
        sanPhamChiTiet: idCTSP
    };
    const token = localStorage.getItem('token');
    await loaddataListSPCT(idCTSP, idSize, idMau);

    await gioHangService.getGHCTByIdctsp(token, idCTSP)
    dataGHCT.value = gioHangService.data;

    soLuongGH.value = parseInt(soLuong) + parseInt(dataGHCT.value.soLuong)


        if (soLuongGH.value > dataListSPCT.value.soLuongTon) {
            toast.add({ severity: 'warn', summary: '', detail: 'Số lượng bạn chọn đã đạt mức tối đa của sản phẩm', life: 5000 });
            return;
        }
    await gioHangService.addToCart(cartItem, token);
    router.push({ name: 'gio-hang' });

};
const tinhTongTien = (tienShip, tongTien, tienSauGiam,idVoucher) => {
    if (idVoucher === '' || idVoucher === null) {
        return parseInt(tongTien) + parseInt(tienShip);
    } else {
        return parseInt(tienSauGiam);
    }
};

</script>
<template>
         <div v-if="! useHD.dataDangGiao ||  useHD.dataDangGiao.length===0" style="text-align: center; margin-top: 100px; "  > 
                                  
                                  <svg  width="100px" height="100px" viewBox="0 0 16 16" xmlns="http://www.w3.org/2000/svg" fill="#000000" class="bi bi-file-earmark-x">
<path d="M6.854 7.146a.5.5 0 1 0-.708.708L7.293 9l-1.147 1.146a.5.5 0 0 0 .708.708L8 9.707l1.146 1.147a.5.5 0 0 0 .708-.708L8.707 9l1.147-1.146a.5.5 0 0 0-.708-.708L8 8.293 6.854 7.146z"/>
<path d="M14 14V4.5L9.5 0H4a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h8a2 2 0 0 0 2-2zM9.5 3A1.5 1.5 0 0 0 11 4.5h2V14a1 1 0 0 1-1 1H4a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1h5.5v2z"/>
</svg>
                       
                            
<h4  style="text-align: center;">Chưa có Đơn hàng !</h4>
                          </div>
    <div v-for="(hd, index) in  useHD.dataDangGiao" :key="index">
        <div style="width: 1060px; background: rgb(255, 255, 255); ">

            <div style="width: 1060px; background: rgb(252, 246, 246);  ">
                <div class="flex">
                    <div style="margin-top: 10px; display: flex;">
                        <svg width="17" height="16" viewBox="0 0 17 16" class="_0RxYUS"
                            style="margin-top: 2px; margin-right: 10px; margin-left: 10px;">
                            <title>Shop Icon</title>
                            <path
                                d="M1.95 6.6c.156.804.7 1.867 1.357 1.867.654 0 1.43 0 1.43-.933h.932s0 .933 1.155.933c1.176 0 1.15-.933 1.15-.933h.984s-.027.933 1.148.933c1.157 0 1.15-.933 1.15-.933h.94s0 .933 1.43.933c1.368 0 1.356-1.867 1.356-1.867H1.95zm11.49-4.666H3.493L2.248 5.667h12.437L13.44 1.934zM2.853 14.066h11.22l-.01-4.782c-.148.02-.295.042-.465.042-.7 0-1.436-.324-1.866-.86-.376.53-.88.86-1.622.86-.667 0-1.255-.417-1.64-.86-.39.443-.976.86-1.643.86-.74 0-1.246-.33-1.623-.86-.43.536-1.195.86-1.895.86-.152 0-.297-.02-.436-.05l-.018 4.79zM14.996 12.2v.933L14.984 15H1.94l-.002-1.867V8.84C1.355 8.306 1.003 7.456 1 6.6L2.87 1h11.193l1.866 5.6c0 .943-.225 1.876-.934 2.39v3.21z"
                                stroke-width=".3" stroke="#333" fill="#333" fill-rule="evenodd"></path>
                        </svg>
                        <h5 style="font-weight: 700;margin-top: -2px;">cửa hàng VNK</h5>
                    </div>
                    <div style="margin-left: 100px; font-size: 15px;margin-top: 10px; margin-right: 50px;">
                        <label for="">Mã đơn hàng: <span>{{ hd.maHD }} </span></label>
                        <span> | </span>
                        <label for="" style="color: red">{{ hienThiTrangThai(hd.trangThai).text }}</label>
                    </div>
                    <!-- <div style="margin-left: 10px">
                        <span> | </span>
                        <label for="" style="color: red; margin-left: 10px">{{ hienThiTrangThai(dataHD.trangThai).text }}</label>
                    </div> -->
                </div>

            </div>
            <Divider />
            <div v-for="(sp, index) in hd.sanPhamChiTiet" :key="index">
                <div style="width: 1060px; background: rgb(255, 255, 255); height: 120px; margin-top: 10px;">
                    <div class="flex">
                        <div style="margin-left: 20px; margin-top: 20px;">
                            <Image :src="sp.anh" alt="Image" width="90" preview />
                        </div>
                        <div class="product-details" style="margin-top: 10px; margin-left: 20px;">
                            <h5 class="flex details">{{ sp.tenSP }}</h5>
                            <div class="flex details">
                                <div>
                                    <p>
                                        Phân loại: <span>{{ sp.tenMauSac }}</span> <span
                                            v-if="sp.tenSize !== '' || sp.tenSize !== null">,{{ sp.tenSize }}</span>
                                    </p>
                                    <p>
                                    </p>
                                    <p>
                                        Số lượng: <span>{{ sp.soLuong }}</span>
                                    </p>
                                </div>
                                <div class="price">
                                    <h4 style="color: rgb(7, 6, 6); margin-left: -130px; margin-top: -20px;">{{
                                        formatCurrency(sp.donGia) }}
                                    </h4>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <Divider />
            <div style="width: 1060px; background: rgb(29, 23, 23);">
                <div style="display: flex; width: 100%; background: rgb(255, 255, 255);">
                    <div style="background: rgb(255, 255, 255);width: 30%; height: 100px; margin-top: ;">
                        <h5 style="color: rgb(253, 1, 1);margin-top: 30px;margin-left: -50px; margin-bottom: 20px;">Thành
                            tiền: <span>{{ formatCurrency(tinhTongTien(hd.tongTien, hd.tienShip, hd.tienSauKhiGiam,hd.idVoucher)) }}</span>
                        </h5>
                    </div>

                    <div style="display: flex;justify-content: flex-end; width: 70%;">

                        <div style=" height: 100%; margin-top: 30px;">

                            <Button type="button" label="Mua lại" style="width: 100px;margin-right: 10px; "
                                @click="showDialogMuaLai(hd.idHD)" />
                            <Button severity="secondary" label="Xem chi tiết" style="width: 150px"
                                @click="redirectToTrangThaiDonHang(hd.idHD)" />
                        </div>

                    </div>
                </div>




            </div>
        </div>
    </div>
    <Dialog v-model:visible="muaLaiDialog" :style="{ width: '550px' }" header="Thêm vào giỏ hàng" :modal="true">
        <div class="card">


            <div v-for="(sp, index) in dataSP" :key="index">
                <div style="width: 300px; background: rgb(255, 255, 255); height: 120px; margin-top: 0px;">
                    <div class="flex" style="width: 450px;  background: rgb(255, 255, 255);">
                        <div style="margin-left: 20px; margin-top: 20px;">
                            <Image :src="sp.anh" alt="Image" width="90" preview />
                        </div>
                        <div class="product-details"
                            style="margin-top: 10px; margin-left: 20px; background: rgb(255, 255, 255);">
                            <h5 class="flex details">{{ sp.tenSP }}</h5>
                            <div class="flex details" style=" width: 250px;">
                                <div style="display: flex;">
                                    <p style="margin-right: 0px;">
                                        Phân loại: <span>{{ sp.tenMauSac }}</span> <span
                                            v-if="sp.tenSize !== '' || sp.tenSize !== null">,{{ sp.tenSize }}</span>
                                    </p>
                                    <p style="margin-left: 40px;" v-if="sp.soLuongTon < sp.soLuong">
                                        Hết hàng
                                    </p>
                                    <p style="margin-left: 40px;" v-if="sp.soLuongTon > sp.soLuong">
                                        x <span>{{ sp.soLuong }}</span>
                                    </p>
                                </div>

                            </div>

                        </div>
                        <Button icon="pi pi-shopping-cart" class="p-button-rounded p-button-warning mt-2" style=""
                            @click="addCart(sp.soLuong, sp.idSPCT, sp.idSize, sp.idMau)" :disabled="sp.soLuongTon < sp.soLuong" />
                    </div>
                </div>
            </div>

        </div>
        <template #footer>
            <Button label="Đóng" icon="pi pi-times" class="p-button-text" @click="muaLaiDialog = false" />
        </template>
    </Dialog>
    <Dialog v-model:visible="addProductDialog" :style="{ width: '450px' }" header="Confirm" :modal="true">
        <div class="flex align-items-center justify-content-center">
            <i class="pi pi-exclamation-triangle mr-3" style="font-size: 2rem" />
            <span>Bạn có chắc chắn muốn hoàn thành không ?</span>
        </div>
        <template #footer>
            <Button label="No" icon="pi pi-times" class="p-button-text" @click="addProductDialog = false" />
            <Button label="Yes" icon="pi pi-check" class="p-button-text" @click="btnXacNhan()" />
        </template>
    </Dialog>
</template>
<style setup>
.flex {
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.product-details {
    flex: 1;
}
</style>