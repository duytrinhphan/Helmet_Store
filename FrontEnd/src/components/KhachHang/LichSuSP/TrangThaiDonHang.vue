<script setup>
import { format, parse } from 'date-fns';
import { ref, onMounted, computed, watch } from 'vue';
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
import { useConfirm } from "primevue/useconfirm";

const productStore = useDetailProductStore();
const toast = useToast();
const confirm = useConfirm();
const gioHangService = gioHangStore();
const router = useRoute();
const routers = useRouter();
const useHD = HDKHStore();
const idHD = router.params.id;
const dataSP = computed(() => useHD.dataSP);
const dataHD = computed(() => useHD.dataHD);

const schema = yup.object().shape({
    lyDo: yup.string().required('Vui lòng chọn lý do'),
    //   moTa: yup.string().required('Vui lòng điền mô tả ').min(10, 'Mô tả sản phẩm phải có ít nhất 10 ký tự'),
    soLuong: yup.number().required('số lượng không được để trống').typeError('Số lượng phải là một số').min(1, 'Số lượng phải lớn hơn hoặc bằng 1').nullable()
});
const { handleSubmit, resetForm } = useForm({
    validationSchema: schema
});

const { value: lyDo, errorMessage: lyDoSacError } = useField('lyDo');
const { value: moTa, errorMessage: MoTaSacError } = useField('moTa');
const { value: soluong, errorMessage: soLuongError } = useField('soLuong');
onMounted(() => {
    loadData();
    loadDataHD();
    openSocketConnection();
});
const tongTienHang = ref();
const bien = ref(2);
const loadData = async () => {
    await useHD.findHdctByIdHd(idHD);
};


const loadDataHD = async () => {
    await useHD.findHdByIdHd(idHD);
    //   console.log(dataHD.value)
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

const doiTra = (idhdct, userId, diaChiId, soluong, idSPCT) => {

    const t = dataSP.value.find(x => x.idSPCT == idSPCT && (x.trangThaiHDCT == 7))
    //    console.log(t)
    if (t != null) {
        soLuongHang.value = parseInt(soluong) - parseInt(t.soLuong);
    } else {
        soLuongHang.value = soluong;
    }
    for (const key of dataSP.value) {
        if (key.trangThaiHDCT == 7 || key.trangThaiHDCT == 8) {
            if (key.idSPCT == idSPCT && soluong <= 0) {
                toast.add({ severity: 'warn', summary: 'lỗi', detail: 'lỗi', life: 3000 });
                return;
            }
        }
    }
    idHDCT.value = idhdct;
    idUser.value = userId;
    idDiaChi.value = diaChiId;
    doiTraDialog.value = true;
};
const tatDoiTra = () => {
    doiTraDialog.value = false;
};

const onSubmit = handleSubmit(async (values) => {
    if (soLuongHang.value < values.soLuong) {
        toast.add({ severity: 'warn', summary: 'lỗi', detail: 'số lượng trả hàng lớn hơn số lượng mua', life: 3000 });
        return;
    }
    const form = {
        tienShip: dataHD.value.tienShip,
        idUser: idUser.value,
        idHDCT: idHDCT.value,
        idDiaChi: idDiaChi.value,
        lyDo: values.lyDo,
        moTa: values.moTa,
        soLuong: values.soLuong
    };
    const token = localStorage.getItem('token');
    await useHD.doiTra(token, form);
    resetForm();
    loadData();
    loadDataHD();
    sendMessage();
    doiTraDialog.value = false;
});

const huyTraHang = async (id) => {
    await useHD.huydoiTra(id);
    loadData();
    loadDataHD();
}

const requireConfirmation = (id) => {
    confirm.require({
        group: 'headless',
        header: 'bạn có chắc muốn hủy trả hàng không ?',
        message: 'Vui lòng xác nhận để tiếp tục.',
        accept: () => {
            huyTraHang(id);
            toast.add({ severity: 'info', summary: 'Confirmed', detail: 'You have accepted', life: 3000 });
        },
        reject: () => {
            toast.add({ severity: 'error', summary: 'Rejected', detail: 'You have rejected', life: 3000 });
        }
    });
};

const dataLyDo = ref([{ value: 'Không đúng màu sắc' }, { value: 'Không đúng size' }, { value: 'Mũ bị hỏng ' }, { value: 'Mũ không giống như mô tả' }, { value: 'Khác' }]);
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
    routers.push({ name: 'gio-hang' });

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
</script>
<template>
    <div class="container">
        <div class="card">
            <div>
                <div class="flex">
                    <div>
                        <h3>Trạng thái đơn hàng</h3>
                    </div>
                    <div style="margin-left: 600px; font-size: 17px">
                        <label for="">Mã đơn hàng: <span> {{ dataHD.maHD }} </span></label>
                        <span> | </span>
                        <label for="" style="color: red">{{ hienThiTrangThai(dataHD.trangThai).text }}</label>
                    </div>
                    <!-- <div style="margin-left: 10px">
                            <span> | </span>
                            <label for="" style="color: red; margin-left: 10px">{{ hienThiTrangThai(dataHD.trangThai).text }}</label>
                        </div> -->
                </div>
                <div>
                    <Timeline :value="events" layout="horizontal" align="bottom" class="customized-timeline"
                        v-if="hienTimeLine(dataHD.trangThai)">
                        <template #marker="slotProps">
                            <span
                                class="flex w-2rem h-2rem align-items-center justify-content-center text-white border-circle z-1 shadow-1"
                                :style="{ backgroundColor: slotProps.item.color }">
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
                <div class="flex">
                    <div>
                        <p>Cảm ơn bạn đã mua sắm tại VNK!</p>
                    </div>
                    <div style="margin-left: 600px">
                        <Button type="button" label="Liên hệ" icon="pi pi-users" badgeClass="p-badge-danger"
                            style="width: 200px; margin-left: 20px" outlined />
                    </div>
                </div>
                <Divider />
                <div v-for="(hdct, index) in dataSP" :key="index">
                    <div class="flex">
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

                                <p v-if="hdct.trangThaiHDCT == 8">
                                    <span style="color: red">Chú ý: </span> Hãy dùng mã <span style="color: red">{{
                                        hdct.maHDCT }} </span> ghi lên kiện hàng bạn gửi cho shop nhé !
                                </p>
                                <p v-if="hdct.trangThaiHDCT == 9">
                                    Rất tiếc đơn hàng đổi trả của bạn đã bị hủy !<br />
                                    <span style="color: rgb(241, 50, 50)">Lý do: </span>{{ dataHD.moTa }}
                                </p>
                                <p v-if="hdct.trangThaiHDCT == 7">
                                    Đang trong quá trình xác nhận trả hàng !<br />
                                    <span style="color: rgb(241, 50, 50)" v-if="hdct.lyDo != null">Lý do: </span>{{
                                        hdct.lyDo }}
                                </p>
                                <div class="price">
                                    <ConfirmDialog group="headless">
                                        <template #container="{ message, acceptCallback, rejectCallback }">
                                            <div
                                                class="flex flex-column align-items-center p-5 surface-overlay border-round">
                                                <div
                                                    class="border-circle bg-primary inline-flex justify-content-center align-items-center h-6rem w-6rem -mt-8">
                                                    <i class="pi pi-question text-5xl"></i>
                                                </div>
                                                <span class="font-bold text-2xl block mb-2 mt-4">{{ message.header }}</span>
                                                <p class="mb-0">{{ message.message }}</p>
                                                <div class="flex align-items-center gap-2 mt-4">
                                                  
                                                    <Button label="Không" outlined @click="rejectCallback"></Button>
                                                    <Button label="Có" @click="acceptCallback"></Button>
                                                </div>
                                            </div>
                                        </template>
                                    </ConfirmDialog>
                                    <h6 style="color: red">{{ formatCurrency(hdct.donGia) }}</h6>
                                    <Button type="button" label="Mua lại" style="width: 100px; margin-right: 10px"
                                        @click="addCart(hdct.soLuong, hdct.idSPCT, hdct.idSize, hdct.idMau)"
                                        :disabled="dataHD.trangThai == 7 || dataHD.trangThai == 2" />
                                    <Button v-if="checks(hdct.trangThai, hdct.soLuong)" severity="secondary"
                                        label="Trả Hàng" style="width: 100px"
                                        @click="doiTra(hdct.idHDCT, hdct.idUser, hdct.idDiaChi, hdct.soLuong, hdct.idSPCT)" />
                                    <Button v-if="hdct.trangThai == 7" severity="danger" label="Hủy trả"
                                        style="width: 100px" @click="requireConfirmation(hdct.idHDCT)" />
                                    
                                    <p v-if="hdct.trangThaiHDCT == 9" style="margin-top: 10px">yêu cầu Trả sản phẩm thất bại
                                    </p>
                                    <p v-if="hdct.trangThaiHDCT == 8" style="margin-top: 10px">yêu cầu Trả sản phẩm thành
                                        công</p>
                                    <p v-if="hdct.trangThaiHDCT == 10" style="margin-top: 10px">Trả sản phẩm thành công</p>
                                    <p v-if="hdct.trangThaiHDCT == 7" style="margin-top: 10px">sản phẩm đang yêu cầu trả
                                        hàng</p>
                                </div>
                            </div>
                        </div>
                    </div>
                    <Divider />
                </div>

                <div class="flex">
                    <div>
                        <h4>Địa chỉ nhận hàng</h4>
                        <p>{{ dataHD.tenNguoiNhan }}</p>
                        <p>{{ dataHD.sdt }}</p>
                        <p>{{ dataHD.diaChiCuThe }}, {{ dataHD.tenPhuongXa }}, {{ dataHD.tenQuanHuyen }}, {{
                            dataHD.tenTinhThanh }}</p>
                    </div>
                    <div class="c2" style="margin-left: 500px">
                        <p>Tổng tiền hàng</p>
                        <p>Phí vận chuyển</p>
                        <p>Tiền giảm</p>
                        <p>Thành tiền</p>
                    </div>
                    <div class="c2">
                        <p>{{ formatCurrency(dataHD.tongTien) }}</p>
                        <p>{{ formatCurrency(dataHD.tienShip) }}</p>
                        <p style="color: red" v-if="dataHD.idVoucher !== null">- {{
                            formatCurrency(parseInt(dataHD.tongTien) + parseInt(dataHD.tienShip) -
                                parseInt(dataHD.tienSauKhiGiam)) }}</p>
                        <p style="color: red" v-else>0</p>
                        <p style="font-weight: bold; color: red">{{ formatCurrency(tinhTongTien(dataHD.tongTien,
                            dataHD.tienShip, dataHD.tienSauKhiGiam, dataHD.idVoucher)) }}</p>
                    </div>
                </div>
            </div>
        </div>

        <Dialog v-model:visible="doiTraDialog" :style="{ width: '450px' }" header="Đổi trả Sản phẩm" :modal="true">
            <form @submit="onSubmit">
                <div class="Field col-12 md:col-12" style="margin-bottom: 0px">
                    <div class="" style="margin-bottom: 10px; margin-top: 20px; display: flex">
                        <span class="p-float-label">
                            <InputNumber id="soluong" v-model="soluong" :class="{ 'p-invalid': soLuongError }">
                            </InputNumber>
                            <label for="SoLuongTon">Số lượng trả hàng</label>
                        </span>


                        <p style="margin-left: 10px;margin-top: 10px;">số lượng sản phẩm: {{ soLuongHang }}</p>

                    </div>
                    <small class="p-error">{{ soLuongError }}</small>
                    <div style="display: flex">

                        <span class="p-float-label" style="width: 500px; margin-top: 20px">
                            <Dropdown id="dropdown" style="width: 370px" :options="dataLyDo" v-model="selectLyDo"
                                optionLabel="value" :class="{ 'p-invalid': lyDoSacError }" @change="onTrongLuongChange">
                            </Dropdown>

                            <label for="dropdown">Lý do</label>
                        </span>
                        <TableTrongLuong :tableId="'TableTrongLuong'" :rightGhId="'right_ghTrongLuong'"
                            :tableClass="'TableTrongLuong'" :rightGhClass="'right_ghTrongLuong'" />
                    </div>

                    <small class="p-error">{{ lyDoSacError }}</small>

                    <div class="" style="margin-bottom: 0px; margin-top: 20px">
                        <label for="address">Mô tả</label>
                        <Textarea id="address" rows="4" v-model="moTa" :class="{ 'p-invalid': MoTaSacError }"
                            style="width: 370px"></Textarea>
                        <small class="p-error">{{ MoTaSacError }}</small>
                    </div>
                </div>
                <div style="width: 400px; text-align: center">
                    <Button class="p-button-outlined" outlined severity="secondary"
                        style="width: 100px; height: auto; margin: 10px" @click="doiTraDialog = false" label="Hủy"></Button>
                    <Button type="submit" :disabled="soluong > soLuongHang" class="p-button-outlined"
                        style="width: 100px; height: auto; margin: 10px" label="Xác nhận"></Button>
                </div>
            </form>
        </Dialog>
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
}</style>
