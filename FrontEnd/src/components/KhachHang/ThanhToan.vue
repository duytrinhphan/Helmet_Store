<script setup>
import { ref, onMounted, computed, watchEffect,watch } from 'vue';
import { useRoute } from 'vue-router';
import { useCartStore } from '@/service/KhachHang/Giohang/useCartStore.js';
import { useDiaChi } from '@/service/KhachHang/DiaChiService.js';
import { phiShipStore } from '@/service/KhachHang/PhiGiaoHangService';
import { checkoutStore } from '@/service/KhachHang/HoaDonService.js';
import { voucherStore } from '@/service/KhachHang/KHVoucherService.js';
import { uservoucherStore } from '@/service/KhachHang/UserVoucherService.js';
import { gioHangStore } from '@/service/KhachHang/Giohang/GiohangCTService.js';
import tokenService from '@/service/Authentication/TokenService.js';
import userKHService from '@/service/KhachHang/UserService.js';
import AddDiaChi from '@/components/KhachHang/DiaChiKhachHang/Add.vue';
import UpdateDiaChi from '@/components/KhachHang/DiaChiKhachHang/Update.vue';
import { vnpayStore } from '@/service/KhachHang/PaymentService.js';
import { useThongTin } from '@/service/KhachHang/ThongTinCaNhanService.js';
import { useToast } from 'primevue/usetoast';
import { format } from 'date-fns';
import { Client } from '@stomp/stompjs';
import SockJS from 'sockjs-client';

// import {userStore} from '@/service/KhachHang/UserService.js';
import { useRouter } from 'vue-router';
const toast = useToast();
const router = useRouter();
const gioHangService = gioHangStore();
const checkoutService = checkoutStore();
const thongTinCaNhanService = useThongTin();
const store = useCartStore();
const voucherService = voucherStore();
const userVoucherService = uservoucherStore();
const diaChiService = useDiaChi();
const phiGiaoHangService = phiShipStore();
const vnpayService = vnpayStore();
const selectedVoucher = ref([]);
const giamGia = ref();
const tongThanhToan = ref();
const tienSauGiam = ref();
// const userKHService = userStore();
const selectedCity = ref();
const diaChi = ref();
const phiShip = ref();
const dataVoucher = ref([]);
const phuongThucThanhToan = ref(1);
const productDialog = ref(false);
const updateSDTDialog = ref(false);
const sdt = ref(null);
// const selectedProducts = ref([]);
// const tongTien = ref(0);
const cities = ref([
    { name: 'New York', code: 'NY' },
    { name: 'Rome', code: 'RM' },
    { name: 'London', code: 'LDN' },
    { name: 'Istanbul', code: 'IST' },
    { name: 'Paris', code: 'PRS' }
]);

const route = useRoute();

const dataGHCT = ref([]);
const loaddataGHCT = () => {
    dataGHCT.value = JSON.parse(localStorage.getItem('gioHang'));
};
onMounted(() => {
    loadUser();
    loadPhiShip();
    loaddataGHCT();
    loadDataVoucher();
    loadDiaChi();
    openSocketConnection();
});

const tinhTongTienChoTungSanPham = (soLuong, giaSauGiam, giaBan) => {
    if (giaSauGiam === '' || giaSauGiam === null) {
        return soLuong * giaBan;
    } else {
        return soLuong * giaSauGiam;
    }
};

const tongTien = ref(0);

// Phương thức tính tổng giá trị
const calculateTotalAmount = () => {
    tongTien.value = dataGHCT.value.reduce((total, item) => {
        return total + tinhTongTienChoTungSanPham(item.soLuong, item.giaSPSauGiam, item.giaBan);
    }, 0);
};

// Theo dõi thay đổi trong dataGHCT và tính tổng lại khi có sự thay đổi
watchEffect(() => {
    calculateTotalAmount();
    tongThanhToan.value = tongTien.value + phiShip.value;
});

const formatCurrency = (value) => {
    if (!value) return '';
    return new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(value);
};

const diaChiMacDinh = computed(() =>  diaChiService.diaChiMacDinh);

watch(diaChiMacDinh, async (newVal) => {
    if (diaChiMacDinh.value == '' || diaChiMacDinh.value == null) {
        phiShip.value = -1;
    } else {
        await phiGiaoHangService.phiShip(diaChiMacDinh.value);
        phiShip.value = phiGiaoHangService.money;
    }
});
const loadUser = async () => {
    const token = localStorage.getItem('token');
    await diaChiService.findDiaChiByIdUserAndTrangThai(token);
    //diaChiMacDinh.value = diaChiService.diaChiMacDinh;

    if (diaChiMacDinh.value == '' || diaChiMacDinh.value == null) {
        phiShip.value = -1;
    } else {
        await phiGiaoHangService.phiShip(diaChiMacDinh.value);
        phiShip.value = phiGiaoHangService.money;
    }

    return diaChi.value;
};

const loadPhiShip = async () => { };

const diaChiDialog = ref(false);

const editDiaChi = () => {
    diaChiDialog.value = true;
};

const saveDiaChi = () => {
    diaChiDialog.value = false;
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
        destination: '/app/admin/hoa-don',
        body: ''
    });
};

const dataHoaDon = ref([]);
const stringVNpay = ref();
const thanhtoan = async () => {
    const token = localStorage.getItem('token');
    const userName = await tokenService.getUserNameByToken(token);

    const user = await userKHService.getUserByUsername(userName);

    if(user.sdt == null) {
    
        updateSDTDialog.value = true;
        return;
    }


    const forms = dataGHCT.value.map((item) => {
        return {
            idCTSP: item.idCTSP,
            soLuong: item.soLuong,
            donGia: tongThanhToan.value
        };
    });

    if (selectedVoucher.value.id == null && selectedVoucher.value.id === '') {
        selectedVoucher.value.id = null;
    }
    const form = {
        tongTien: tongTien.value,
        tienShip: phiShip.value,
        idPayMethod: parseInt(phuongThucThanhToan.value),
        idUser: user.id,
        tienSauGiam: tienSauGiam.value ? tienSauGiam.value : null,
        idVoucher: selectedVoucher.value.id ? selectedVoucher.value.id : null,
        listHDCT: forms
    };

    const formString = JSON.stringify(form);

    if (parseInt(phuongThucThanhToan.value) == 2) {
        const data = {
            vnp_Ammount: tongThanhToan.value,
            vnp_OrderInfo: 'Thanh toan hoa don',
            vnp_OrderType: 'Thanh toan hoa don',
            vnp_TxnRef: '123456',
            hdct: forms
        };

        localStorage.setItem('myForm', formString);

        await vnpayService.thanhToanVnPay(data);
        stringVNpay.value = vnpayService.vnpay;

        // Kiểm tra xem API có trả về URL redirect hay không
        if (stringVNpay.value) {
            // Chuyển hướng người dùng đến trang thanh toán của VnPay

            window.location.href = stringVNpay.value;
        } else {
            console.log('Failed to get redirect URL from VnPay API');
        }

        // const idAccount = sessionStorage.getItem('idAccount');
    } else {
        await checkoutService.checkout(form);
        dataHoaDon.value = checkoutService.checkOut;
        demSLGH(token);
        sendMessage();
        toast.add({ severity: 'success', summary: '', detail: 'Thanh toán thành công', life: 3000 });
        router.push({ name: 'thanh-cong' });
    }
};
const demSLGH = async (token) => {
    await gioHangService.countGHCT(token);
};
const selectedVoucherDialog = ref(false);

const selectVoucher = () => {
    selectedVoucherDialog.value = true;
};

const loadDataVoucher = async () => {
    await gioHangService.getListVoucherByTrangThai();
    dataVoucher.value = gioHangService.voucher;

    //  console.log('voucher', dataVoucher.value);
    // const token = localStorage.getItem('token');
    // const userName = await tokenService.getUserNameByToken(token);
    // const user = await userKHService.getUserByUsername(userName);

    // // await gioHangService. getListVoucherByUser(token);
    // //     dataVoucher.value = gioHangService.voucher;
    // await voucherService.getListVoucher(user.id);
    // dataVoucher.value = voucherService.data;
    // console.log(dataVoucher.value);
};

const formatDate = (dateTime) => {
    return format(new Date(dateTime), 'yyyy/MM/dd');
};

const applyVoucher = () => {
    const phanTram = selectedVoucher.value.giaTriGiam;

    let giam = tongTien.value * (phanTram / 100);
    if (giam > selectedVoucher.value.giamToiDa) {
        giamGia.value = selectedVoucher.value.giamToiDa;
        tongThanhToan.value = tongTien.value + phiShip.value - giamGia.value;
        tienSauGiam.value = tongThanhToan.value;
        toast.add({ severity: 'success', summary: '', detail: 'Áp dụng voucher thành công', life: 3000 });
        selectedVoucherDialog.value = false;
    } else {
        giamGia.value = giam;
        tongThanhToan.value = tongTien.value + phiShip.value - giamGia.value;
        tienSauGiam.value = tongThanhToan.value;
        toast.add({ severity: 'success', summary: '', detail: 'Áp dụng voucher thành công', life: 3000 });
        selectedVoucherDialog.value = false;
    }
};

const hideDialog = () => {
    selectedVoucherDialog.value = false;
};

const thietLapMacDinh = async (id) => {
    const token = localStorage.getItem('token');
    await diaChiService.fetchDiaChiThanhToanModal(id, token);
    loadDiaChi();
    loadUser();
};

const submitted = ref(false);
const product = ref({});
const openNew = () => {
    product.value = {};
    submitted.value = false;
    productDialog.value = true;
};

const userDiaChi = ref([]);
const loadDiaChi = async () => {
    const token = localStorage.getItem('token');
    if (token.length > 0 || token != null) {
        await diaChiService.fetchData(token);
        userDiaChi.value = diaChiService.diaChi;

    }
};


const updateSDT = async (id) => {
   
    await thongTinCaNhanService.updatesdt(id, sdt.value)
    diaChiMacDinh.value.user.sdt = sdt.value;
    toast.add({ severity: 'success', summary: '', detail: 'Cập nhật số điện thoại thành công', life: 3000 });
    updateSDTDialog.value = false;

}

const touched = ref(false);

const handleInput = () => {
  touched.value = true;
};

const isValidPhoneNumber = computed(() => {
  // Kiểm tra xem 'sdt' có phải là số điện thoại hợp lệ không
  const phoneNumberRegex = /^0\d{9}$/;
  return phoneNumberRegex.test(sdt.value);
});

const showError = computed(() => {
  // Chỉ hiển thị lỗi khi 'sdt' không hợp lệ và đã được chạm vào ít nhất một lần
  return touched.value && !isValidPhoneNumber.value;
});


</script>
<template>
    
    <div class="card">
        <div class="grid">
            <div class="flex">
                <!-- Cột trái -->
                <div class="p-col-5">
                    <div class="trai" style="margin-top: 50px">
                        <h5>THÔNG TIN THANH TOÁN</h5>
                        <div style="display: flex">
                            <div>
                                <h4 v-if="phiShip == -1" style="color: red"><i class="pi pi-user"
                                        style="margin-right: 15px"></i>Bạn cần thêm địa chỉ trước khi thanh toán</h4>
                                <h4 v-else style="color: red"><i class="pi pi-user" style="margin-right: 15px"></i> Địa chỉ
                                    nhận hàng</h4>

                                <p style="font-weight: bold">{{ diaChiMacDinh?.user?.ten }} - {{ diaChiMacDinh?.user?.sdt }}
                                </p>
                                <p style="">{{ diaChiMacDinh?.diaChi }}, {{ diaChiMacDinh?.tenphuongXa }}, {{
                                    diaChiMacDinh?.tenQuanHuyen }}, {{ diaChiMacDinh?.tenTinhThanh }}</p>
                            </div>
                            <div>
                                <Button style="margin-left: 30px; margin-top: 50px" label="" icon="pi pi-plus"
                                    class="p-button-outlined p-button-info mr-2 mb-2" @click="openNew" />
                            </div>
                        </div>

                        <Textarea v-model="value" rows="4" cols="87" style="margin-top: 25px"
                            placeholder="Ghi chú về đơn hàng, ví dụ: thời gian hay địa điểm giao hàng chi tiết hơn" />
                    </div>
                </div>
                <!-- cột phải -->
                <div class="p-col-7">
                    <div class="phai">
                        <div class="card">
                            <h5>ĐƠN HÀNG CỦA BẠN</h5>
                            <DataTable :value="dataGHCT" dataKey="id" :filters="filters" paginatorTemplate="FirstPageLink Pr
                            evPageLink PageLinks NextPageLink LastPageLink CurrentPageReport RowsPerPageDropdown">
                                <Column field="code" header="Sản phẩm" headerStyle="width:38%; min-width:14rem;">
                                    <template #body="slotProps">
                                        <img :src="slotProps.data.anhMau" alt="Ảnh sản phẩm" class="shadow-2" width="50" />
                                        <span style="margin-left: 10px">{{ slotProps.data.tenSP }}</span>
                                    </template>
                                </Column>

                                <Column field="code" header="Phân loại hàng" headerStyle="width:10%; min-width:5rem;">
                                    <template #body="slotProps">
                                        <div style="width: 100px">
                                            <!-- <p v-if="slotProps.data.soLuongTon != 0">Phân loại hàng:</p> -->
                                            <p style="font-size: 13px; margin-top: -10px">{{ slotProps.data.tenMauSac }}, {{
                                                slotProps.data.tenSize }}</p>
                                        </div>
                                    </template>
                                </Column>

                                <Column field="tenSP" header="Đơn giá" headerStyle="width:14%; min-width:5rem;">
                                    <template #body="slotProps">
                                        <div style="display: block">
                                            <div>{{ formatCurrency(slotProps.data.giaSPSauGiam) == '' ?
                                                formatCurrency(slotProps.data.giaBan) :
                                                formatCurrency(slotProps.data.giaSPSauGiam) }}</div>
                                        </div>
                                    </template>
                                </Column>
                                <Column field="name" header="Số lượng" headerStyle="width:15%; min-width:5rem;">
                                    <template #body="slotProps">
                                        <div class="quantity">
                                            <span>{{ slotProps.data.soLuong }}</span>
                                        </div>
                                    </template>
                                </Column>
                                <Column field="giaBan" header="Tổng tiền" headerStyle="min-width:5rem;">
                                    <template #body="slotProps">
                                        <span class="p-column-title">Code</span>
                                        <p style="font-size: 15px; color: red">
                                            {{ formatCurrency(tinhTongTienChoTungSanPham(slotProps.data.soLuong,
                                                slotProps.data.giaSPSauGiam, slotProps.data.giaBan)) }}
                                        </p>
                                    </template>
                                </Column>
                            </DataTable>

                            <Divider style="margin-bottom: 10px" />
                            <div class="flex">
                                <div class="p-col-6">
                                    <div class="flex-container">
                                        <h6>Tổng tiền hàng</h6>
                                    </div>
                                </div>
                                <div class="p-col-6" style="margin-left: 470px">
                                    <h6 style="height: 20px; width: 80px">{{ formatCurrency(tongTien) }}</h6>
                                </div>
                            </div>
                            <Divider style="margin-top: -10px" />
                            <h6 style="margin-top: 10px">Giao hàng</h6>
                            <!-- <span style="font-weight: bold; margin-bottom: 15px;"><input type="radio" name="shipping" value="fast" /> Giao hàng miễn phí</span> -->
                            <label style="display: flex; justify-content: space-between;">
                                <span>Phí giao nhanh:</span>
                                <span style="margin-left: 400px; font-weight: bold"> {{ phiShip == -1 ? 'bạn cần thêm địa chỉ':formatCurrency(phiShip) }}</span>
                            </label>
                            <Divider />

                            <div class="flex">
                                <div class="p-col-6">
                                    <div class="flex-container">
                                        <h6>Voucher</h6>
                                    </div>
                                </div>
                                <div class="p-col-6" style="margin-left: 490px; margin-bottom: 10px">
                                    <span style="color: blue" @click="selectVoucher">Chọn voucher</span>
                                </div>
                            </div>

                            <Dialog v-model:visible="selectedVoucherDialog" header="Flex Scroll" :style="{ width: '600px' }"
                                maximizable modal :contentStyle="{ height: '370px' }" class="p-fluid">
                                <template #header>
                                    <div
                                        class="flex flex-column md:flex-row md:justify-content-between md:align-items-center">
                                        <h5 class="m-0">Voucher</h5>
                                    </div>
                                </template>
                                <div v-if="!dataVoucher || dataVoucher.length === 0"
                                    style="text-align: center; margin-top: 100px;">

                                    <svg width="100px" height="100px" viewBox="0 0 16 16" xmlns="http://www.w3.org/2000/svg"
                                        fill="#000000" class="bi bi-file-earmark-x">
                                        <path
                                            d="M6.854 7.146a.5.5 0 1 0-.708.708L7.293 9l-1.147 1.146a.5.5 0 0 0 .708.708L8 9.707l1.146 1.147a.5.5 0 0 0 .708-.708L8.707 9l1.147-1.146a.5.5 0 0 0-.708-.708L8 8.293 6.854 7.146z" />
                                        <path
                                            d="M14 14V4.5L9.5 0H4a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h8a2 2 0 0 0 2-2zM9.5 3A1.5 1.5 0 0 0 11 4.5h2V14a1 1 0 0 1-1 1H4a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1h5.5v2z" />
                                    </svg>


                                    <h4 style="text-align: center;">Không có voucher</h4>
                                </div>

                                <div v-for="(vo, index) in dataVoucher"
                                    style="box-shadow: 0px 0px 15px 5px rgba(173, 172, 172, 0.75); border-radius: 20px 0px 0px 20px; margin-bottom: 20px; margin-top: 20px">
                                    <div
                                        style="width: 100%; background: rgb(165, 27, 27); border-radius: 20px 0px 0px 20px; height: 100px; display: flex">
                                        <div
                                            style="height: 100%; width: 30%; background: rgb(11, 117, 103); text-align: center; border-radius: 20px 0px 0px 20px">
                                            <H5 style="margin-top: 30px; color: white">{{ vo.ten }}</H5>
                                        </div>
                                        <div
                                            style="height: 100%; width: 70%; background: rgb(255, 255, 255); display: flex">
                                            <div style="margin-left: 20px; width: 80%; background: rgb(255, 255, 255)">
                                                <p>giảm tối đa: {{ vo.giamToiDa }}</p>

                                                <p style="margin-top: -10px">giá trị giảm: {{ vo.giaTriGiam }} (%)</p>

                                                <p style="margin-top: -10px">Thời gian kết thúc: {{ vo.thoiGianKetThuc }}
                                                </p>

                                                <p style="margin-top: -10px">Mô tả: {{ vo.moTa }}</p>
                                            </div>
                                            <div
                                                style="width: 10%; display: flex; justify-content: center; padding-top: 40px">
                                                <RadioButton v-model="selectedVoucher" inputId="ingredient1" name="pizza"
                                                    :value="vo"  />
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <template #footer>
                                    <Button label="Bỏ qua" icon="pi pi-times" class="p-button-text" @click="hideDialog" />
                                    <Button label="Áp dụng" icon="pi pi-check" @click="applyVoucher" />
                                </template>
                            </Dialog>

                            <div class="flex">
                                <div class="p-col-6">
                                    <div class="flex-container">
                                        <h6>Giảm giá</h6>
                                    </div>
                                </div>
                                <div class="p-col-6" style="margin-left: 520px">
                                    <h6 style="height: 20px; width: 80px">{{ formatCurrency(giamGia) }} <span></span></h6>
                                </div>
                            </div>

                            <div class="flex">
                                <div class="p-col-6">
                                    <div class="flex-container">
                                        <h6>Tổng thanh toán</h6>
                                    </div>
                                </div>
                                <div class="p-col-6" style="margin-left: 470px">
                                    <h6 style="height: 20px; width: 80px">{{ formatCurrency(tongThanhToan) }} <span></span>
                                    </h6>
                                </div>
                            </div>
                            <Divider style="margin-top: -10px" />
                            <span style="font-weight: bold">
                                <input type="radio" id="chuyenkhoan" value="2" v-model="phuongThucThanhToan" />

                                Payment Credit Card / Visa / Master Card / Amex</span>
                            <br />

                            <Divider style="margin-top: -10px" />
                            <span style="font-weight: bold">
                                <input type="radio" id="tienmat" value="1" v-model="phuongThucThanhToan" />
                                Trả tiền mặt khi nhận hàng</span>
                            <br />
                            <!-- <div class="p-inputgroup flex-1">
                                <InputText placeholder="Mã ưu đãi" />
                                <Button label="Áp dụng" severity="warning" />
                            </div> -->
                            <br />
                            <span> <input type="checkbox" id="agreeCheckbox" /> Tôi đã đọc và đồng ý với điều khoản và điều
                                kiện của website * </span>
                            <br />
                            <Button label="Đặt hàng" severity="danger" :disabled="diaChiMacDinh == ''" @click="thanhtoan" />
                            <br />
                            <label for="">Bộ phận CSKH sẽ liên hệ bạn qua số điện thoại để xác nhận đơn hàng.</label>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <Dialog v-model:visible="productDialog" :style="{ width: '650px' }" header="Địa Chỉ" :modal="true" class="p-fluid">
        <div style="position: relative; width: 80px; margin-left: 500px; margin-bottom: 20px">
            <AddDiaChi style="position: absolute; right: 0; width: 80px; margin-bottom: 5px"></AddDiaChi>
        </div>



        <div class="card">

            <div v-if="!userDiaChi || userDiaChi.length === 0" style="text-align: center; margin-top: 50px;">

                <svg width="100px" height="100px" viewBox="0 0 16 16" xmlns="http://www.w3.org/2000/svg" fill="#000000"
                    class="bi bi-file-earmark-x">
                    <path
                        d="M6.854 7.146a.5.5 0 1 0-.708.708L7.293 9l-1.147 1.146a.5.5 0 0 0 .708.708L8 9.707l1.146 1.147a.5.5 0 0 0 .708-.708L8.707 9l1.147-1.146a.5.5 0 0 0-.708-.708L8 8.293 6.854 7.146z" />
                    <path
                        d="M14 14V4.5L9.5 0H4a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h8a2 2 0 0 0 2-2zM9.5 3A1.5 1.5 0 0 0 11 4.5h2V14a1 1 0 0 1-1 1H4a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1h5.5v2z" />
                </svg>


                <h4 style="text-align: center;">Chưa có địa chỉ</h4>
            </div>
            <div v-for="(hd, index) in userDiaChi">
                <div style="width: 100%; height: 100px; display: flex">
                    <div style="width: 90%; height: 100px; background: rgb(255, 255, 255)">
                        <div class="flex">
                            <label for="" style="margin-right: 10px">
                                <span>{{ hd?.user?.ten }} </span></label>
                            <span style="margin-right: 10px"> | </span>
                            <label for="" style="color: rgb(0, 0, 0)"> {{ hd?.user?.sdt }}</label>
                            <label for="" style="color: rgb(255, 0, 0); margin-left: 10px"> {{ hd.trangThai == 1 ? 'Mặc định' : '' }}</label>
                        </div>
                        <div style="margin-top: 10px">
                            <label for="" style="margin-right: 10px">
                                <span>{{ hd.diaChi }} </span></label>
                        </div>
                        <div style="margin-top: 10px">
                            <label for="" style="margin-right: 10px">
                                <span>{{ hd.tenphuongXa }}, {{ hd.tenQuanHuyen }}, {{ hd.tenTinhThanh }} </span></label>
                        </div>
                    </div>

                    <div style="width: 35%; height: 10px; background: rgb(255, 255, 255); display: flex; margin-top: 30px">
                        <UpdateDiaChi :my-prop="hd"></UpdateDiaChi>

                        <Button style="width: 100px; height: 45px; margin-top: 0px" label="Thiết lập mặc định "
                            class="p-button-outlined p-button-info mr-2 mb-2" @click="thietLapMacDinh(hd.id)" />
                    </div>
                </div>
                <Divider />
            </div>
        </div>
    </Dialog>


    <Dialog v-model:visible="updateSDTDialog" :style="{ width: '450px' }" header="Tài khoản chưa có số điện thoại. Vui lòng cập nhật số điện thoại!" :modal="true">
        <div class="flex align-items-center justify-content-center">
          
            <!-- <span class="p-float-label">
                <InputText id="Số điện thoại" v-model="value" />
                <label for="số điện thoại">Số điện thoại</label>
            </span> -->
            <InputText v-model="sdt" type="text" size="small" placeholder="Số điện thoại" @input="handleInput" />
            <p v-if="showError" style="color: red;">Số điện thoại không hợp lệ!</p>
        </div>
        <template #footer>
            <Button label="Huỷ" icon="pi pi-times" class="p-button-text" @click="deleteDialog = false" />
            <Button label="Cập nhật" icon="pi pi-check" class="p-button-text" @click="updateSDT(diaChiMacDinh?.user?.id)" />
        </template>
    </Dialog>
</template>

<style scoped>
.grid {
    /* margin-top: 70px; */
    /* position: absolute;
    top: 0;
    left: 0;
    right: 0; */
    margin: auto;
    margin-top: 70px;
    margin-bottom: 30px;
    width: 1100px;
    /* background: white; */
    display: flex;
    justify-content: center;
    /* align-items: center; */
}

.trai {
    padding: 20px 20px 30px 20px;
    width: 630px;
    /* height: 300px; */
    background: white;
    margin-right: 20px;
    border-radius: 10px;
}

.phai {
    padding: 20px 20px 30px 20px;
    margin-top: 30px;
    width: 755px;
    background: white;
    /* height: 400px; */
    border-radius: 10px;
}

.flex-container {
    display: flex;
}

span {
    font-weight: bold;
}</style>
