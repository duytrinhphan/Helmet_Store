<script setup>
import { ref, onMounted, watch } from 'vue';
import { useToast } from 'primevue/usetoast';
import Divider from 'primevue/divider';
import { useForm, useField } from 'vee-validate';
import { da } from 'date-fns/locale';
import { HDStore } from '@/service/Admin/HoaDon/HoaDonService';
import jsPDF from 'jspdf';
import html2canvas from 'html2canvas';
import { Client } from '@stomp/stompjs';
import SockJS from 'sockjs-client';

const toast = useToast();
const useHD = HDStore();
const productDialog = ref(false);
const code = ref('');
const dataHDCT = ref([]);
const ship = ref('');
const idHD = ref(null);

const ngayDuKienGiao = ref(null);
const khoiLuong = ref(0);
const inputKhoiLuong = ref(0);
const dai = ref(null);
const rong = ref(null);
const cao = ref(null);
// confirm xác nhận
const addProductDialog = ref(false);

//hiện confirm
const confirmAddProduct = (id) => {
    idHD.value = id;
    addProductDialog.value = true;
};

//nhận dữ liệu từ cha
const props = defineProps({
    myProp: {}
});

const tongTienThanhToan = ref(props.myProp.idVoucher != null ? parseInt(props.myProp.tienSauKhiGiam) : parseInt(props.myProp.tongTien) + parseInt(props.myProp.tienShip == null ? 0 : props.myProp.tienShip));
const tienShip = ref(props.myProp.tienShip == null ? 0 : props.myProp.tienShip);
const editProduct = () => {
    code.value = 'Hoá đơn: ' + props.myProp.maHD;
    ship.value = 'nguoiNhan';
    productDialog.value = true;
    loadDataHDCT(props.myProp.idHD);
    ngayDat.value = props.myProp.ngayTao;
    ngayThanhToan.value = props.myProp.ngayThanhToan;
    ngayGiao.value = props.myProp.ngayShip;
    ngayNhan.value = props.myProp.ngayNhan;
    inputKhoiLuong.value = khoiLuong.value;
};

const ngayDat = ref('');
const ngayThanhToan = ref('');
const ngayGiao = ref('');
const ngayNhan = ref('');

watch(ship, (newVal) => {
    if (ship.value === 'nguoiGui') {
        tienShip.value = 0;
        tongTienThanhToan.value = props.myProp.idVoucher === null ? parseInt(props.myProp.tongTien) : parseInt(props.myProp.tienSauKhiGiam) - parseInt(props.myProp.tienShip);
    } else {
        tienShip.value = props.myProp.tienShip;
        tongTienThanhToan.value = props.myProp.idVoucher === null ? parseInt(props.myProp.tongTien) + parseInt(props.myProp.tienShip == null ? 0 : props.myProp.tienShip) : props.myProp.tienSauKhiGiam;
    }
});

watch(productDialog, (newVal) => {
    if (productDialog.value === false) {
        ngayDuKienGiao.value = null;
        inputKhoiLuong.value = null;
        dai.value = null;
        rong.value = null;
        cao.value = null;
    }
});

onMounted(() => {
    openSocketConnection();
});

const giaoHangNhanh = async (idHD, hoaDon, formGHN) => {
    const responeGHN = await useHD.findHdctByIdHd(idHD);
    useHD.giaoHangNhanh(responeGHN, hoaDon, formGHN);
};

const stompClient = ref(null);
const openSocketConnection = () => {
    stompClient.value = new Client({
        brokerURL: `${import.meta.env.VITE_BASE_WEBSOCKET_ENDPOINT}/ws`
    });

    stompClient.value.activate();
};

const sendMessage = () => {
    stompClient.value.publish({
        destination: '/app/hoa-don/' + props.myProp.idUser,
        body: ''
    });
};

const btnXacNhan = async () => {
    const formGHN = {
        trongLuong: inputKhoiLuong.value,
        dai: dai.value,
        rong: rong.value,
        cao: cao.value,
        tongTien: tongTienThanhToan.value
    };
    const ngayHienTai = new Date();
    const ngayChon = new Date(ngayDuKienGiao.value);
    if (formGHN.trongLuong == null || formGHN.trongLuong.length <= 0) {
        toast.add({ severity: 'error', summary: 'error', detail: 'Khối lượng không được trống', life: 3000 });
        addProductDialog.value = false;
    } else if (formGHN.trongLuong <= 0 || formGHN.trongLuong < khoiLuong.value) {
        toast.add({ severity: 'error', summary: 'error', detail: 'Vui lòng nhập khối lượng phù hợp', life: 3000 });
        addProductDialog.value = false;
    } else if (ngayDuKienGiao.value == null || ngayDuKienGiao.value.length <= 0) {
        toast.add({ severity: 'error', summary: 'error', detail: 'Ngày dự kiến giao không được trống', life: 3000 });
        addProductDialog.value = false;
    } else if (ngayChon < ngayHienTai) {
        toast.add({ severity: 'error', summary: 'error', detail: 'Ngày dự kiến giao không được nhỏ hơn ngày hiện tại', life: 3000 });
        addProductDialog.value = false;
    } else if (formGHN.dai == null || formGHN.dai.length <= 0) {
        toast.add({ severity: 'error', summary: 'error', detail: 'Chiều dài không được trống', life: 3000 });
        addProductDialog.value = false;
    } else if (formGHN.rong == null || formGHN.rong.length <= 0) {
        toast.add({ severity: 'error', summary: 'error', detail: 'Chiều rộng không được trống', life: 3000 });
        addProductDialog.value = false;
    } else if (formGHN.cao == null || formGHN.cao.length <= 0) {
        toast.add({ severity: 'error', summary: 'error', detail: 'Chiều cao không được trống', life: 3000 });
        addProductDialog.value = false;
    } else if (formGHN.cao <= 0 || formGHN.rong <= 0 || formGHN.dai <= 0) {
        toast.add({ severity: 'error', summary: 'error', detail: 'Các kích thước phải lớn hơn 0', life: 3000 });
        addProductDialog.value = false;
    } else {
        const responeDCB = await useHD.dangChuanBi(idHD.value, ngayDuKienGiao.value, tongTienThanhToan.value, tienShip.value);
        sendMessage();
        // console.log(responeDCB);
        giaoHangNhanh(idHD.value, responeDCB, formGHN);
        toast.add({ severity: 'success', summary: 'Thông báo', detail: 'Xác nhận thành công', life: 3000 });
        addProductDialog.value = false;
        productDialog.value = false;
    }
};

const events = ref([
    { status: 'Ngày đã đặt', date: ngayDat, icon: 'pi pi-wallet', color: '#9C27B0' },
    { status: 'Ngày đã thanh toán', date: ngayThanhToan, icon: 'pi pi-money-bill', color: '#673AB7' },
    { status: 'Đã giao cho ĐVVC', date: ngayGiao, icon: 'pi pi-car', color: '#FF9800' },
    { status: 'Đã nhận được hàng', date: ngayNhan, icon: 'pi pi-box', color: '#607D8B' },
    { status: 'Đánh giá', date: '17/10/2020', icon: 'pi pi-star', color: '#F55C3B' }
]);

const loadDataHDCT = async (idHD) => {
    const respone = await useHD.findHdctByIdHd(idHD);
    dataHDCT.value = respone;
    for (let i = 0; i < dataHDCT.value.length; i++) {
        khoiLuong.value += parseInt(dataHDCT.value[i].trongLuong);
        inputKhoiLuong.value += parseInt(dataHDCT.value[i].trongLuong);
    }
};

const formatCurrency = (value) => {
    return parseInt(value).toLocaleString('vi-VN', { style: 'currency', currency: 'VND' });
};

const hienTimeLine = (value) => {
    if (parseInt(value) == 0 || parseInt(value) == 5 || parseInt(value) == 3) {
        return true;
    } else {
        return false;
    }
};

const exportToPDF = () => {
    const content = document.getElementById('pdf-content');
    html2canvas(content).then((canvas) => {
        const imgData = canvas.toDataURL('image/png');
        const pdf = new jsPDF();
        const imgWidth = 208;
        const imgHeight = (canvas.height * imgWidth) / canvas.width;
        pdf.addImage(imgData, 'PNG', 0, 0, imgWidth, imgHeight);
        pdf.save('hoa-don.pdf');
    });
};

//show dialog lý do
const lyDoDialog = ref(false);
// confirm huy
const huyDialog = ref(false);
//hiện dialog lý do
const showDialogLyDo = (id) => {
    idHD.value = id;
    lyDoDialog.value = true;
};
//hiện confirm huy
const confirmHuy = () => {
    huyDialog.value = true;
};

const { value: lyDo, errorMessage: LyDoError } = useField('lyDo');
const btnXacNhanHuy = () => {
    if (lyDo.value == null || lyDo.value.length <= 0) {
        toast.add({ severity: 'success', summary: 'Thông báo', detail: 'Huỷ thất bại', life: 3000 });
        lyDo.value = '';
        huyDialog.value = false;
    } else {
        useHD.huyHoaDon(idHD.value, lyDo.value, 2);
        sendMessage();
        toast.add({ severity: 'success', summary: 'Thông báo', detail: 'Huỷ thành công', life: 3000 });
        lyDo.value = '';
        huyDialog.value = false;
        lyDoDialog.value = false;
        productDialog.value = false;
    }
};

const tinhTongTien = (tienShip, tongTien, tienSauGiam) => {
    if (tienSauGiam == '' || tienSauGiam == null) {
        return parseInt(tongTien) + parseInt(tienShip);
    } else {
        return parseInt(tienSauGiam);
    }
};
</script>
<template>
    <Toast />
    <Button label="Xem" class="p-button-outlined p-button-info mr-2 mb-2" @click="editProduct()" />
    <Dialog v-model:visible="productDialog" :style="{ width: '1500px' }" :header="code" :modal="true" class="p-fluid">
        <div class="flex">
            <div class="p-fluid formgrid grid">
                <div class="Field col-12 md:col-8">
                    <div class="trai">
                        <div class="p-col-12" style="text-align: center">
                            <div class="bills-col">
                                <div id="pdf-content" class="card p-fluid" style="background: #ffffff">
                                    <div>
                                        <h3>Shop...</h3>
                                        <label>139 Cầu Giấy, Phường Quan Hoa, Hà Nội</label>
                                        <h5>Hóa đơn thanh toán</h5>
                                    </div>
                                    <div v-if="hienTimeLine(props.myProp.trangThai)">
                                        <Timeline :value="events" layout="horizontal" align="bottom" class="customized-timeline">
                                            <template #marker="slotProps">
                                                <span class="flex w-2rem h-2rem align-items-center justify-content-center text-white border-circle z-1 shadow-1" :style="{ backgroundColor: slotProps.item.color }">
                                                    <i :class="slotProps.item.icon"></i>
                                                </span>
                                            </template>
                                            <template #opposite="slotProps">
                                                <small class="p-text-secondary">{{ slotProps.item.date }}</small>
                                            </template>
                                            <template #content="slotProps">
                                                {{ slotProps.item.status }}
                                            </template>
                                        </Timeline>
                                    </div>
                                    <hr />
                                    <table>
                                        <tr>
                                            <th>Stt</th>
                                            <th>Ảnh</th>
                                            <th>Tên sản phẩm</th>
                                            <th>Màu săc</th>
                                            <th>Size</th>
                                            <th>Số lượng</th>
                                            <th>Đơn giá</th>
                                            <th>Thành tiền</th>
                                        </tr>
                                        <hr />
                                        <tr v-for="(item, index) in dataHDCT" :key="index">
                                            <td style="width: 30px">{{ index + 1 }}</td>
                                            <td style="width: 20%"><img :src="item.anh" style="width: 50%" alt="HoaDon Image" /></td>
                                            <td>{{ item.tenSP }}</td>
                                            <td>{{ item.tenMauSac }}</td>
                                            <td>{{ item.tenSize == null ? 'Không có' : item.tenSize }}</td>
                                            <td>{{ item.soLuong }}</td>
                                            <td>{{ formatCurrency(item.donGia) }}</td>
                                            <td>{{ formatCurrency(item.soLuong * item.donGia) }}</td>
                                        </tr>
                                    </table>
                                    <hr />

                                    <label>Chúc quý khách vui vẻ! Hẹn gặp lại!</label>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="Field col-12 md:col-4">
                    <div class="phai">
                        <div class="card p-fluid" style="width: 460px">
                            <div class="flex">
                                <div class="p-col-6" style="width: 100%">
                                    <div class="row flex">
                                        <div class="flex" style="min-width: 200px; margin-bottom: 6px">
                                            <p>
                                                Họ và tên người nhận: <span style="margin-left: 5px">{{ props.myProp.tenNguoiNhan }}</span>
                                            </p>
                                        </div>
                                        <div class="flex" style="min-width: 200px; margin-bottom: 6px">
                                            <p>
                                                Số điện thoại: <span style="margin-left: 5px">{{ props.myProp.sdt }}</span>
                                            </p>
                                        </div>

                                        <div class="flex" style="min-width: 200px; margin-bottom: 6px">
                                            <p>
                                                Địa chỉ: <span style="margin-left: 5px">{{ props.myProp.diaChiCuThe }}, {{ props.myProp.tenPhuongXa }}, {{ props.myProp.tenQuanHuyen }}, {{ props.myProp.tenTinhThanh }}</span>
                                            </p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="p-inputgroup flex-1">
                                <p>Ngày dự kiến giao:</p>
                                <input type="datetime-local" v-model="ngayDuKienGiao" style="width: 170px; height: 25px; margin-left: 10px" />
                            </div>
                            <div>
                                <p style="float: left; margin-right: 20px; margin-top: 5px">Khối lượng:</p>
                                <InputText id="ten" name="ten" type="number" v-model.trim="inputKhoiLuong" :class="{ 'p-invalid': tenError }" required="true" style="width: 80px; height: 30px" />
                            </div>
                            <div>
                                <p style="float: left; margin-right: 15px; margin-top: 5px">Kích thước(cm):</p>
                                <InputText id="ten" name="ten" type="number" v-model.trim="dai" :class="{ 'p-invalid': tenError }" required="true" style="width: 60px; height: 30px; float: left; margin-right: 5px" />
                                <p style="float: left; margin-right: 5px; margin-top: 5px">Dài</p>
                                <InputText id="ten" name="ten" type="number" v-model.trim="rong" :class="{ 'p-invalid': tenError }" required="true" style="width: 60px; height: 30px; float: left; margin-right: 5px" />
                                <p style="float: left; margin-right: 5px; margin-top: 5px">Rộng</p>
                                <InputText id="ten" name="ten" type="number" v-model.trim="cao" :class="{ 'p-invalid': tenError }" required="true" style="width: 60px; height: 30px; float: left; margin-right: 5px" />
                                <p style="float: left; margin-right: 5px; margin-top: 5px">Cao</p>
                            </div>
                            <div>
                                <p style="float: left; margin-right: 15px; margin-top: 5px">Phí giao hàng:</p>
                                <div class="flex align-items-center" style="margin-top: 5px">
                                    <RadioButton v-model="ship" inputId="aaaaaaa" name="pizza" value="nguoiGui" />
                                    <label for="ingredient2" class="ml-2" style="margin-right: 50px">Người gửi</label>
                                    <RadioButton v-model="ship" inputId="aaaaaaa" name="pizza" value="nguoiNhan" />
                                    <label for="ingredient2" class="ml-2">Người nhận</label>
                                </div>
                            </div>
                            <p>Tổng tiền các sản phẩm: {{ formatCurrency(props.myProp.tongTien) }}</p>
                            <p>Phí vận chuyển: {{ formatCurrency(tienShip) }}</p>
                            <p>
                                Tiền giảm:
                                <span v-if="props.myProp.idVoucher != null" style="color: red"
                                    >- {{ formatCurrency(parseInt(props.myProp.tongTien) + parseInt(props.myProp.tienShip == null ? 0 : props.myProp.tienShip) - parseInt(props.myProp.tienSauKhiGiam)) }}</span
                                >
                                <span v-else style="color: red"> 0</span>
                            </p>
                            <p>
                                Tổng tiền: <span style="color: #ff3333; font-size: 20px; font-weight: bold">{{ formatCurrency(tongTienThanhToan) }}</span>
                            </p>
                            <Button label="Giao Hàng" severity="success" class="btn-ap-dung" @click="confirmAddProduct(props.myProp.idHD)" style="margin-bottom: 20px" />
                            <Button label="Hủy" class="p-button-outlined p-button-info mr-2 mb-2" @click="showDialogLyDo(props.myProp.idHD)" />
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <Dialog v-model:visible="addProductDialog" :style="{ width: '450px' }" header="Confirm" :modal="true">
            <div class="flex align-items-center justify-content-center">
                <i class="pi pi-exclamation-triangle mr-3" style="font-size: 2rem" />
                <span>Bạn có chắc chắn muốn nhận không ?</span>
            </div>
            <template #footer>
                <Button label="Không" icon="pi pi-times" class="p-button-text" @click="addProductDialog = false" />
                <Button label="Có" icon="pi pi-check" class="p-button-text" @click="btnXacNhan()" />
            </template>
        </Dialog>
        <Dialog v-model:visible="lyDoDialog" :style="{ width: '450px' }" header="Huỷ hoá đơn" :modal="true">
            <div class="card">
                <form @submit="onSubmit">
                    <div class="p-fluid formgrid grid">
                        <div class="field col-12" style="margin-bottom: 30px">
                            <label for="address">Lý do</label>
                            <Textarea id="lyDo" rows="4" v-model.trim="lyDo" :class="{ 'p-invalid': LyDoError }" required="true" autofocus></Textarea>
                            <small class="p-error">{{ LyDoError }}</small>
                        </div>
                    </div>
                </form>
            </div>
            <template #footer>
                <Button label="Không" icon="pi pi-times" class="p-button-text" @click="lyDoDialog = false" />
                <Button label="Có" icon="pi pi-check" class="p-button-text" @click="confirmHuy" />
            </template>
        </Dialog>
        <!-- comfirm huỷ -->
        <Dialog v-model:visible="huyDialog" :style="{ width: '450px' }" header="Confirm" :modal="true">
            <div class="flex align-items-center justify-content-center">
                <i class="pi pi-exclamation-triangle mr-3" style="font-size: 2rem" />
                <span>Bạn có chắc chắn muốn huỷ không ?</span>
            </div>
            <template #footer>
                <Button label="Không" icon="pi pi-times" class="p-button-text" @click="huyDialog = false" />
                <Button label="Có" icon="pi pi-check" class="p-button-text" @click="btnXacNhanHuy()" />
            </template>
        </Dialog>
    </Dialog>
</template>

<style scoped>
.ben-phai {
    text-align: right;
}
</style>
