<script setup>
import { format } from 'date-fns';
import { ref, onMounted, watch } from 'vue';
import Timeline from 'primevue/timeline';
import { useRoute } from 'vue-router';
import { HDKHStore } from '@/service/KhachHang/HoaDonKHService';
import { useForm, useField, defineRule } from 'vee-validate';
import * as yup from 'yup';
import { gioHangStore } from '@/service/KhachHang/Giohang/GiohangCTService';
import { useRouter } from 'vue-router';
import { Client } from '@stomp/stompjs';
import { useToast } from 'primevue/usetoast';
import { useDetailProductStore } from '@/service/KhachHang/DetailService';

const productStore = useDetailProductStore();
const toast = useToast();
const maHD = ref(null);
const gioHangService = gioHangStore();
const router = useRoute();
const routers = useRouter();
const useHD = HDKHStore();
const idHD = router.params.id;
const dataSP = ref([]);
const dataHD = ref({});
const dataHDCT = ref([]);
const data = ref([]);

const schema = yup.object().shape({
    lyDo: yup.string().required('Vui lòng chọn lý do'),
    moTa: yup.string().required('Vui lòng điền mô tả ').min(10, 'Mô tả sản phẩm phải có ít nhất 10 ký tự'),
    soLuong: yup.number().required('số lượng không được để trống').typeError('Số lượng phải là một số').min(1, 'Số lượng phải lớn hơn hoặc bằng 1').nullable()
});
const { handleSubmit, resetForm } = useForm({
    validationSchema: schema
});

const { value: lyDo, errorMessage: lyDoSacError } = useField('lyDo');
const { value: moTa, errorMessage: MoTaSacError } = useField('moTa');
const { value: soluong, errorMessage: soLuongError } = useField('soLuong');
onMounted(() => {
    // loadData();
    // loadDataHD();
    openSocketConnection();
    editProduct();
});
const tongTienHang = ref();
const bien = ref(2);
const loadData = async (idHD) => {
    dataSP.value = await useHD.findHdctByIdHd(idHD);
};

const loadDataHD = async (idHD) => {
    dataHD.value = await useHD.findHdByIdHd(idHD);
};

const ngayDat = ref('');
const ngayThanhToan = ref('');
const ngayGiao = ref('');
const ngayNhan = ref('');
watch(dataHD, (newVal) => {
    ngayDat.value = formatDate(dataHD.value.ngayTao);
    ngayThanhToan.value = formatDate(dataHD.value.ngayThanhToan);
    ngayGiao.value = formatDate(dataHD.value.ngayShip);
    ngayNhan.value = formatDate(dataHD.value.ngayNhan);
});

const events = ref([
    { status: 'Ngày đã đặt', date: ngayDat, icon: 'pi pi-wallet', color: '#9C27B0' },
    { status: 'Ngày đã thanh toán', date: ngayThanhToan, icon: 'pi pi-money-bill', color: '#673AB7' },
    { status: 'Đã giao cho ĐVVC', date: ngayGiao, icon: 'pi pi-car', color: '#FF9800' },
    { status: 'Đã nhận được hàng', date: ngayNhan, icon: 'pi pi-box', color: '#607D8B' },
    { status: 'Đánh giá', date: '17/10/2020', icon: 'pi pi-star', color: '#F55C3B' }
]);

const tinhTongTien = (tienShip, tongTien, tienSauGiam, idVoucher) => {
    if (idVoucher === '' || idVoucher === null) {
        return parseInt(tongTien) + parseInt(tienShip);
    } else {
        return parseInt(tienSauGiam);
    }
};
const formatCurrency = (value) => {
    return parseInt(value).toLocaleString('vi-VN', { style: 'currency', currency: 'VND' });
};

const hienThiTrangThai = (trangThai) => {
    if (parseInt(trangThai) == 0) {
        return { text: 'Đã hủy', severity: 'danger' };
    } else if (parseInt(trangThai) == 1) {
        return { text: 'Chờ thanh toán', severity: 'secondary' };
    } else if (parseInt(trangThai) == 2) {
        return { text: 'Yêu cầu xác nhận', severity: 'success' };
    } else if (parseInt(trangThai) == 3) {
        return { text: 'Hoàn thành', severity: 'info' };
    } else if (parseInt(trangThai) == 4) {
        return { text: 'Đang chuẩn bị hàng', severity: 'success' };
    } else if (parseInt(trangThai) == 5) {
        return { text: 'Đang giao', severity: 'help' };
    } else if (parseInt(trangThai) == 7) {
        return { text: 'Yêu cầu đổi trả ', severity: 'warning' };
    } else if (parseInt(trangThai) == 8) {
        return { text: 'Xác nhận đổi trả thành công', severity: 'success' };
    } else if (parseInt(trangThai) == 9) {
        return { text: 'Yêu cầu đổi trả thất bại', severity: 'success' };
    } else {
        return { text: 'Đổi trả thành công', severity: 'success' };
    }
};

const doiTraDialog = ref(false);
const idHDCT = ref(null);
const idUser = ref(null);
const idDiaChi = ref(null);
const soLuongHang = ref(null);
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

const doiTra = (idhdct, userId, diaChiId, soluong, maSP) => {
    for (const key of dataSP.value) {
        if (key.trangThaiHDCT == 7 || key.trangThaiHDCT == 8) {
            if (key.maSP == maSP && soluong <= 0) {
                toast.add({ severity: 'warn', summary: 'lỗi', detail: 'lỗi', life: 3000 });
                return;
            }
        }
    }
    idHDCT.value = idhdct;
    idUser.value = userId;
    idDiaChi.value = diaChiId;
    doiTraDialog.value = true;
    // let sum = 0;
    // for (const key of dataSP.value) {
    //     if (key.trangThaiHDCT == 7) {
    //         sum += parseInt(key.soLuong);
    //     }
    // }
    soLuongHang.value = soluong;
};
const tatDoiTra = () => {
    doiTraDialog.value = false;
};

const selectLyDo = ref();
const onTrongLuongChange = () => {
    if (selectLyDo.value) {
        lyDo.value = selectLyDo.value.value;
    } else {
        lyDo.value = null;
    }
};

const dataGHCT = ref([]);
const soLuongGH = ref(0);
const dataListSPCT = ref([]);
const loaddataListSPCT = async (idProduct, idSize, idMau) => {
    await productStore.fetchIdSPCT(idProduct, idSize, idMau);
    dataListSPCT.value = productStore.products;
};

const hienTimeLine = (value) => {
    if (parseInt(value) == 7 || parseInt(value) == 8 || parseInt(value) == 9 || parseInt(value) == 10) {
        return false;
    } else {
        return true;
    }
};

const formatDate = (dateTime) => {
    if (dateTime == null || dateTime.length <= 0) {
        return null;
    } else {
        return format(new Date(dateTime), 'yyyy/MM/dd HH:mm:ss');
    }
};

const checks = (trangThai, soLuong) => {
    if (parseInt(trangThai) == 3) {
        return true;
    } else if (parseInt(bien.value) >= parseInt(soLuong) && parseInt(trangThai) == 3) {
        return false;
    }
};

const loadDataHDCT = async (idHD) => {
    const respone = await useHD.findHdByIdHd(idHD);
    dataHDCT.value = respone;
};

const loadDataTraCuu = async (idHD) => {
    await useHD.findHdById(idHD);
    data.value = useHD.data;
};

const showInitialMessage = ref(true);

const handleInput = () => {
  // Ẩn đi phần thông báo khi người dùng nhập vào ô input
  showInitialMessage.value = false;
  maHD.value = maHD.value.replace(/\s/g, '');
    //showInitialMessage.value =  maHD.value.trim() === '';
};

const isEditClicked = ref(false);

const editProduct = async () => {
    //console.log(props.myProp);
    // code.value = 'Hoá đơn: ' + maHD.value;

//     if (maHD.value.trim() === '') {
//         toast.add({ severity: 'warn', summary: 'lỗi', detail: 'Vui lòng nhập đúng dữ liệu', life: 3000 });
//     return;
//   }

    loadDataHDCT(idHD);

    // const maHDValue = maHD.value ? maHD.value.trim() : '';

    const respone = await useHD.findHdByIdHd(idHD);
    dataHDCT.value = respone;
    isEditClicked.value = true;

    ngayDat.value = formatDate(dataHDCT.value.ngayDat);
    ngayThanhToan.value = formatDate(dataHDCT.value.ngayThanhToan);
    ngayGiao.value = formatDate(dataHDCT.value.ngayShip);
    ngayNhan.value = formatDate(dataHDCT.value.ngayNhan);
    // tienGiam.value = dataHDCT.value.tienSauKhiGiam;
    // if(tienGiam.value == null){
    //     tienGiam.value = 0;
    // }else{
    //     tienGiam.value = dataHDCT.value.tienSauKhiGiam;
    // }

    loadData(idHD);
    loadDataHD(idHD);
    // loadDataTraCuu(maHD.value);
};


</script>
<template>
    <div class="container">
        <!-- <div style="margin-bottom: 20px; margin-top: 20px">
            <InputGroup>
                <InputText placeholder="Mã HD" v-model="maHD" />
                <Button icon="pi pi-search" severity="warning" @click="editProduct()" />
            </InputGroup>

        </div> -->

        <!-- <div class="field grid mt-3">
            <label for="name3" class="col-12 mb-2 md:col-2 md:mb-0" style="font-weight: bold;">Tra cứu đơn hàng</label>
            <div class="col-12 md:col-10">
                <InputGroup>
                    <InputText placeholder="Mã HD" v-model="maHD"  @input="handleInput" />
                    <Button icon="pi pi-search" severity="warning" @click="editProduct()" />
                </InputGroup>
            </div>
        </div> -->
        <div class="card">
           

            <div >
                <div class="flex" >
                    <div>
                        <h3>Trạng thái đơn hàng</h3>
                    </div>
                    <div style="margin-left: 600px; font-size: 17px">
                        <label for=""
                            >Mã đơn hàng: <span> {{ dataHD.maHD }} </span></label
                        >
                        <span> | </span>
                        <label for="" style="color: red">{{ hienThiTrangThai(dataHD.trangThai).text }}</label>
                    </div>
                    <!-- <div style="margin-left: 10px">
                            <span> | </span>
                            <label for="" style="color: red; margin-left: 10px">{{ hienThiTrangThai(dataHD.trangThai).text }}</label>
                        </div> -->
                </div>
                <div >
                    <Timeline :value="events" layout="horizontal" align="bottom" class="customized-timeline" v-if="hienTimeLine(dataHD.trangThai)">
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
                <Divider />

                <Divider />
                <div v-for="(hdct, index) in dataSP" :key="index" >
                    <div class="flex" >
                        <div>
                            <Image :src="hdct.anh" alt="Image" width="150" preview />
                        </div>
                        <div class="product-details">
                            <h5 class="flex details">{{ hdct.tenSP }}</h5>
                            <div class="flex details">
                                <div>
                                    <p>
                                        Size: <span>{{ hdct.tenSize }}</span>
                                    </p>
                                    <p>
                                        Màu sắc: <span>{{ hdct.tenMauSac }}</span>
                                    </p>
                                    <p>
                                        Số lượng: <span>{{ hdct.soLuong }}</span>
                                    </p>
                                </div>
                            </div>
                        </div>

                        <div class="flex" >
                            <div class="c2" style="margin-left: 500px">
                                <p>Tổng tiền hàng:</p>
                                <p>Phí vận chuyển:</p>
                                <p>Tiền giảm</p>
                                <p>Thành tiền</p>
                            </div>
                            <div class="c2">
                                <p>{{ formatCurrency(dataHD.tongTien) }}</p>
                                <p>{{ formatCurrency(dataHD.tienShip) }}</p>
                                <p style="color: red" v-if="dataHD.idVoucher !== null">- {{ formatCurrency(parseInt(dataHD.tongTien) + parseInt(dataHD.tienShip) - parseInt(dataHD.tienSauKhiGiam)) }}</p>
                                <p style="color: red" v-else>0</p>
                                <p style="font-weight: bold; color: red">{{ formatCurrency(tinhTongTien(dataHD.tongTien, dataHD.tienShip, dataHD.tienSauKhiGiam, dataHD.idVoucher)) }}</p>
                            </div>
                        </div>
                    </div>
                    <Divider />
                </div>
            </div>


            <div v-if =" dataHDCT.length == 0 " style="text-align: center; margin-top: 100px">
                <svg width="100px" height="100px" viewBox="0 0 16 16" xmlns="http://www.w3.org/2000/svg" fill="#000000" class="bi bi-file-earmark-x">
                    <path d="M6.854 7.146a.5.5 0 1 0-.708.708L7.293 9l-1.147 1.146a.5.5 0 0 0 .708.708L8 9.707l1.146 1.147a.5.5 0 0 0 .708-.708L8.707 9l1.147-1.146a.5.5 0 0 0-.708-.708L8 8.293 6.854 7.146z" />
                    <path d="M14 14V4.5L9.5 0H4a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h8a2 2 0 0 0 2-2zM9.5 3A1.5 1.5 0 0 0 11 4.5h2V14a1 1 0 0 1-1 1H4a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1h5.5v2z" />
                </svg>

                <h4 style="text-align: center">Chưa có Đơn hàng !</h4>
            </div>


        </div>
    </div>
</template>
<style scoped>
.container {
    margin-top: 80px;
    margin-bottom: 100px;
}

.flex {
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.product-details {
    flex: 1;
    /* Để cho phần tử này mở rộng và lấp đầy khoảng trống trong flex container */
}

.details {
    margin-left: 20px;
    /* Khoảng cách giữa thông tin sản phẩm và giá tiền */
}

.price {
    text-align: right;
    /* Đặt giá tiền ở bên phải */
    display: block;
}
</style>
