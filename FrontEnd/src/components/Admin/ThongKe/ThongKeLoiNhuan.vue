<script setup>
import { FilterMatchMode, FilterOperator } from 'primevue/api';

import { ThongKeStore } from "@/service/Admin/ThongKe/ThongKe.api";
import { ref, onMounted, watch,onBeforeMount } from 'vue';
import OverlayPanel from 'primevue/overlaypanel';
import { th } from 'date-fns/locale';
import { useForm, useField, defineRule } from 'vee-validate';
import * as yup from 'yup';
import DetailHoaDon from '@/components/Admin/HoaDon/DetailHoaDon.vue';

const thongKeStore = ThongKeStore();
// biểu đồ
onMounted(() => {
    loadSanPham();
    loadNam.value = nam;
    loadThang.value = thang;
});

const vModelHinhThuc = ref();
const lstHoaDon = ref([]);
const lstSanPham = ref([]);
const tongLoiNhuan = ref(0);
const tongDonhangHoanThanh = ref(0);
const tongDonhangDangGiao = ref(0);
const tongDonhangHuy = ref(0);
const loadSanPham = async () => {
    const currentYear = new Date().getFullYear();
    await thongKeStore.fetchAllLoiNhuan(currentYear, '', '');
    tongLoiNhuan.value = thongKeStore.tongLoiNhuan;
    tongDonhangHoanThanh.value = thongKeStore.tongDonhangHoanThanh;
    tongDonhangDangGiao.value = thongKeStore.tongDonhangDangGiao;
    tongDonhangHuy.value = thongKeStore.tongDonhangHuy;
    lstSanPham.value = thongKeStore.lstAdminThongKeLoiNhuanSanPhamResponse;
    lstHoaDon.value = thongKeStore.lstAdminThongKeLoiNhuanHoaDonResponse;
};

const load = () => {
    loadSanPham();
    resetForm();
    vNam.value = null;
    vModelHinhThuc.value= null;
}
const vNam = ref(null);
const op = ref();
const toggle = (event) => {
    op.value.toggle(event);
};

const op1 = ref();
const toggle1 = (event) => {
    op1.value.toggle(event);
};
const loadNam = ref([]);
const currentYear = new Date().getFullYear();
const startYear = 2018;
const nam = Array.from({ length: currentYear - startYear + 1 }, (_, i) => ({ name: (startYear + i).toString() }));

const loadThang = ref([]);
const thang = [{ name: '01' }, { name: '02' }, { name: '03' }, { name: '04' }, { name: '05' }, { name: '06' }, { name: '07' }, { name: '08' }, { name: '09' }, { name: '10' }, { name: '11' }, { name: '12' }];


const formatCurrency = (value) => {
    return value ? value.toLocaleString('vi-VN', { style: 'currency', currency: 'VND' }) : 0;
};

const onHinhThucGiaoHang = async (id) => {
    await thongKeStore.fetchAllLoiNhuanbyHinhThucGiaohang(id);
    tongLoiNhuan.value = thongKeStore.tongLoiNhuan;
    tongDonhangHoanThanh.value = thongKeStore.tongDonhangHoanThanh;
    tongDonhangDangGiao.value = thongKeStore.tongDonhangDangGiao;
    tongDonhangHuy.value = thongKeStore.tongDonhangHuy;
    lstSanPham.value = thongKeStore.lstAdminThongKeLoiNhuanSanPhamResponse;
    lstHoaDon.value = thongKeStore.lstAdminThongKeLoiNhuanHoaDonResponse;
};
watch(vModelHinhThuc, async (newVal) => {

    if (vModelHinhThuc.value == null || vModelHinhThuc.value == '') {
        return;
    }
    await onHinhThucGiaoHang(vModelHinhThuc.value);
});


watch(vNam, async (newVal) => {
    await thongKeStore.fetchAllLoiNhuan(vNam.value, '', '');
    tongLoiNhuan.value = thongKeStore.tongLoiNhuan;
    tongDonhangHoanThanh.value = thongKeStore.tongDonhangHoanThanh;
    tongDonhangDangGiao.value = thongKeStore.tongDonhangDangGiao;
    tongDonhangHuy.value = thongKeStore.tongDonhangHuy;
    lstSanPham.value = thongKeStore.lstAdminThongKeLoiNhuanSanPhamResponse;
    lstHoaDon.value = thongKeStore.lstAdminThongKeLoiNhuanHoaDonResponse;
});
const schema = yup.object().shape({
    startDate: yup.string().required('vui lòng chọn ngày bắt đầu'),
    endDate: yup.string().required('vui lòng chọn chọn ngày kết thúc')
});

const { handleSubmit, resetForm } = useForm({
    validationSchema: schema
});
const { value: startDate, errorMessage: startDateError } = useField('startDate');
const { value: endDate, errorMessage: endDateError } = useField('endDate');

const onSubmit = handleSubmit(async (values) => {
    await thongKeStore.fetchAllLoiNhuan('', values.startDate, values.endDate);
    tongLoiNhuan.value = thongKeStore.tongLoiNhuan;
    tongDonhangHoanThanh.value = thongKeStore.tongDonhangHoanThanh;
    tongDonhangDangGiao.value = thongKeStore.tongDonhangDangGiao;
    tongDonhangHuy.value = thongKeStore.tongDonhangHuy;
    lstSanPham.value = thongKeStore.lstAdminThongKeLoiNhuanSanPhamResponse;
    lstHoaDon.value = thongKeStore.lstAdminThongKeLoiNhuanHoaDonResponse;
});
const getStatusLabel = (trangThai) => {
    switch (trangThai) {
        case 1:
            return { text: 'Còn Hàng', severity: 'success' };

        case 2:
            return { text: 'hết Hàng', severity: 'danger' };

        case 3:
            return { text: 'tồn kho', severity: 'danger' };;

        default:
            return { text: 'Trạng thái không xác định', severity: 'info' };
    }
};

const filters = ref({});
onBeforeMount(() => {
    initFilters();
    initFiltersSP();
});
const initFilters = () => {
    filters.value = {
        global: { value: null, matchMode: FilterMatchMode.CONTAINS }
    };
};

const filtersSP = ref({});

const initFiltersSP = () => {
    filtersSP.value = {
        global: { value: null, matchMode: FilterMatchMode.CONTAINS }
    };
};
</script>
<template>
    <div class="Field col-12 md:col-12"
        style=" background: rgb(255, 255, 255); width: 100%; height: 120px;margin-bottom: 10px;display: flex;">
        <div class="Field col-12 md:col-2" style="margin-right: 10px;height: 30px; margin-left: -25px;">
            <Panel header="Tổng lợi nhuận" style=" width: 150px;  height: 40px; ">
                <div style="display: flex;">
                    <div style="margin-right: 20px; text-align: center;">
                        <p class="m-0" style="text-align: center; font-weight: 900; font-size: 1.4rem;">{{
                           formatCurrency(tongLoiNhuan) }}</p>
                    </div>
                    <div>

                    </div>

                </div>
            </Panel>

        </div>
        <div class="Field col-12 md:col-2" style="margin-right: 20px;">
                <Panel header="Hoàn tiền" style=" width: 170px; height: 50px;">
                    <p class="m-0" style="text-align: center; font-weight: 900; font-size: 1.4rem;">{{
                           formatCurrency(tongDonhangDangGiao) }}</p>
                </Panel>
            </div>
        <div class="Field col-12 md:col-2" style="margin-right: 10px;height: 30px;">
                <Panel header="Đơn hoàn thành" style=" width: 150px">
                    <div>
                        <p class="m-0" style="text-align: center; font-weight: 900; font-size: 1.4rem;">{{
                            tongDonhangHoanThanh }}</p>
                    </div>
                </Panel>
            </div>
          
            <div class="Field col-12 md:col-2" style="margin-right: 20px;">
                <Panel header="Đơn hủy" style=" width: 160px; height: 30px;">
                    <p class="m-0" style="text-align: center; font-weight: 900; font-size: 1.4rem;">{{
                            tongDonhangHuy }}</p>
                </Panel>
            </div>
        <div class="Field col-6 md:col-4" style="height: 40px;">
            <Panel header="Tìm kiếm" style=" width:300px; height: 40px;">
                <div class="flex flex-wrap gap-3" style="display: flex;">
                    <Button type="button" label="Năm/tháng" @click="toggle"
                        style="width: 105px; height: 40px;background: none;   color: black;" />
                    <OverlayPanel ref="op">

                        <H6>Hãy chọn năm</H6>
                        <div v-for="(o, index) in loadNam"
                            style="display: inline-block; margin-right: 10px; margin-bottom: 10px; margin-bottom: 20px;">
                            <div class="flex align-items-center">
                                <RadioButton v-model="vNam" type="radio" :inputId="'ingredient' + index" name="nam"
                                    :value="o.name" />
                                <label :for="'ingredient' + index" class="ml-2">{{ o.name }}</label>
                            </div>
                        </div>
                        <form @submit="onSubmit">
                            <div style="display: flex; height: 50px; margin-bottom: 20px;">

                                <div class="" style="height: 30px; margin-right: 20px;  display: block;">
                                    <label style="width: 100px;">Ngày bắt đầu</label>
                                    <span class="p-float-label">

                                        <InputText type="datetime-local" style="width: 160px;" v-model="startDate"
                                            :class="{ 'p-invalid': startDateError }" />
                                        <!-- <label  style="width: 100px;">start month</label> -->
                                    </span>
                                    <small class="p-error">{{ startDateError }}</small>
                                </div>
                                <div class="" style="height: 30px; margin-right: 20px;  display: block;">
                                    <label style="width: 100px;">Ngày kết thúc</label>
                                    <span class="p-float-label">

                                        <InputText type="datetime-local" style="width: 160px;" v-model="endDate"
                                            :class="{ 'p-invalid': endDateError }" />
                                        <!-- <label  style="width: 100px;">start month</label> -->
                                    </span>
                                    <small class="p-error">{{ endDateError }}</small>
                                </div>
                                <Button type="submit"
                                    style="background: none; height: 50px; border: none; margin-top: 10px;"><i
                                        class="pi pi-search" style="font-size: 1.8rem; color: blue"></i></Button>
                            </div>
                        </form>
                    </OverlayPanel>
                    <Button type="button" label="khác" @click="toggle1"
                        style="width: 80px; height: 40px;background: none;   color: black;" />
                    <Button type="button" label="Tháng" @click="load()"
                        style="width: 50px; height: 40px;background: none;    color: black;"> <i class="pi pi-replay"
                            style="font-size: 1.3rem; margin-left: -5px;"></i></Button>

                    <OverlayPanel ref="op1" style="height: 60px;">
                        <div style="display: flex; ">
                                <div class="flex align-items-center" style="margin-right: 20px;">
                                    <RadioButton v-model="vModelHinhThuc" type="radio" :inputId="'ingredient' + index"
                                        name="nam" value="1" />
                                    <label :for="'ingredient' + index" class="ml-2">tại quầy</label>
                                </div>
                                <div class="flex align-items-center">
                                    <RadioButton v-model="vModelHinhThuc" type="radio" :inputId="'ingredient' + index"
                                        name="nam" value="2" />
                                    <label :for="'ingredient' + index" class="ml-2">đặt hàng</label>
                                </div>
                            </div>
                    </OverlayPanel>


                </div>
            </Panel>
        </div>
    </div>
    <div class="Field col-12 md:col-12" style="margin-top: 50px;">
        <DataTable ref="dt" :value="lstHoaDon" v-model:selection="selectedProducts" dataKey="id" :paginator="true" :rows="5"
            :filters="filters"
            paginatorTemplate="FirstPageLink PrevPageLink PageLinks NextPageLink LastPageLink CurrentPageReport RowsPerPageDropdown"
            :rowsPerPageOptions="[5, 10, 25]"
            currentPageReportTemplate="Showing {first} to {last} of {totalRecords} products" responsiveLayout="scroll">
            <template #header>

                <div class="flex flex-column md:flex-row md:justify-content-between md:align-items-center">

                    <div style="display: flex;">
                        <h5 class="m-0" style="margin-right: 20px;"> Hóa đơn </h5>
                     
                    </div>
                    <span class="block mt-2 md:mt-0 p-input-icon-left">
                                <i class="pi pi-search" />
                                <InputText v-model="filters['global'].value" placeholder="Search..." />
                            
                            </span>
                </div>
            </template>
            <Column field="tenNhanVien" header="STT" :sortable="true" headerStyle="width:14%; min-width:5rem;">
                <template #body="slotProps">
                    <span class="p-column-title">Name</span>
                    {{ lstHoaDon.indexOf(slotProps.data) + 1 }}
                </template>
            </Column>
            <Column field="maHD" header="Mã" :sortable="true" headerStyle="width:14%; min-width:5rem;">
                <template #body="slotProps">
                    <span class="p-column-title">ma</span>
                    {{ slotProps.data.maHD }}
                </template>
            </Column>
            <Column field="ngayTao" header="Ngày Tạo" :sortable="true" headerStyle="width:14%; min-width:10rem;">
                <template #body="slotProps">
                    <span class="p-column-title">Name</span>
                    {{ slotProps.data.ngayTao }}
                </template>
            </Column>
            <Column field="tenPhuongThucThanhToan" header="Phương thức thanh toán" :sortable="true"
                headerStyle="width:14%; min-width:10rem;">
                <template #body="slotProps">
                    <span class="p-column-title">Name</span>
                    {{ slotProps.data.tenPhuongThucThanhToan }}
                  
                </template>
            </Column>
            <Column field="tongTien" header="Tổng tiền" :sortable="true" headerStyle="width:14%; min-width:9rem;">
                <template #body="slotProps">
                    <span class="p-column-title">Name</span>
                   
                    {{ formatCurrency(slotProps.data.tienSauKhiGiam==null?parseInt(slotProps.data.tongTien)+parseInt(slotProps.data.tienShip): slotProps.data.tienSauKhiGiam) }}
                </template>
            </Column>
            <Column field="loiNhuan" header="Lợi Nhuận" :sortable="true" headerStyle="width:14%; min-width:9rem;">
                <template #body="slotProps">
                    <span class="p-column-title">Name</span>
                    {{  formatCurrency(slotProps.data.loiNhuan) }}
                </template>
            </Column>
            <Column field="hoanTien" header="Hoàn tiền" :sortable="true" headerStyle="width:14%; min-width:9rem;">
                <template #body="slotProps">
                    <span class="p-column-title">Name</span>
                    {{  formatCurrency(slotProps.data.hoanTien) }}
                </template>
            </Column>

            <Column field="tenNhanVien" header="Action" headerStyle="width:14%; min-width:10rem;">
                <template #body="slotProps">
                    <span class="p-column-title">Name</span>
                    <DetailHoaDon :my-prop="slotProps.data"></DetailHoaDon>
                </template>
            </Column>
            <!-- <Column field="ngayGiao" header="Ngày giao" :sortable="true" headerStyle="width:14%; min-width:10rem;">
                <template #body="slotProps">
                    <span class="p-column-title">Status</span>
                    <span :class="'product-badge status-' + (slotProps.data.inventoryStatus ? slotProps.data.inventoryStatus.toLowerCase() : '')">{{ slotProps.data.inventoryStatus }}</span>
                </template>
            </Column> -->


        </DataTable>
        <DataTable ref="dt" :value="lstSanPham" v-model:selection="selectedProducts" dataKey="id" :paginator="true"
            :rows="5" :filters="filtersSP"
            paginatorTemplate="FirstPageLink PrevPageLink PageLinks NextPageLink LastPageLink CurrentPageReport RowsPerPageDropdown"
            :rowsPerPageOptions="[5, 10, 25]"
            currentPageReportTemplate="Showing {first} to {last} of {totalRecords} products" responsiveLayout="scroll">
            <template #header>

                <div class="flex flex-column md:flex-row md:justify-content-between md:align-items-center">

                    <div style="display: flex;">
                        <h5 class="m-0" style="margin-right: 20px;"> Sản Phẩm </h5>
                    </div>
                    <span class="block mt-2 md:mt-0 p-input-icon-left">
                                <i class="pi pi-search" />
                                <InputText v-model="filtersSP['global'].value" placeholder="Search..." />
                            
                            </span>
                </div>
            </template>
            <Column field="tenNhanVien" header="STT" :sortable="true" headerStyle="width:14%; min-width:2rem;">
                <template #body="slotProps">
                    <span class="p-column-title">Name</span>
                    {{ lstSanPham.indexOf(slotProps.data) + 1 }}
                </template>
            </Column>
            <Column field="tenNhanVien" header="Ảnh" :sortable="true" headerStyle="width:14%; min-width:7rem;">
                <template #body="slotProps">
                    <span class="p-column-title">Name</span>
                    <img :src="slotProps.data.anh" :alt="i" class="shadow-2" width="50" />
                </template>
            </Column>

            <Column field="ma" header="Mã" :sortable="true" headerStyle="width:14%; min-width:5rem;">
                <template #body="slotProps">
                    <span class="p-column-title">Name</span>
                    {{ slotProps.data.ma }}
                </template>
            </Column>

            <Column field="ten" header="Tên sản phẩm " :sortable="true" headerStyle="width:14%; min-width:10rem;">
                <template #body="slotProps">
                    <span class="p-column-title">Name</span>
                    {{ slotProps.data.ten }}
                </template>
            </Column>
            <Column field="giaNhap" header="Giá Nhập" :sortable="true" headerStyle="width:14%; min-width:10rem;">
                <template #body="slotProps">
                    <span class="p-column-title">Name</span>
                    {{  formatCurrency(slotProps.data.giaNhap) }}
                </template>
            </Column>
            <Column field="giaBan" header="Giá Bán" :sortable="true" headerStyle="width:14%; min-width:9rem;">
                <template #body="slotProps">
                    <span class="p-column-title">Name</span>
                    {{  formatCurrency(slotProps.data.giaBan) }}
                </template>
            </Column>
            <Column field="loiNhuan" header="Lợi Nhuận" :sortable="true" headerStyle="width:14%; min-width:10rem;">
                <template #body="slotProps">
                    <span class="p-column-title">Name</span>
                    {{  formatCurrency(slotProps.data.loiNhuan) }}
                </template>
            </Column>
            <Column field="trangThai" header="Trạng Thái" sortable headerStyle="width: 5%; min-width: 8rem;">
                            <template #body="slotProps">
                                <Tag :value="getStatusLabel(slotProps.data.trangThai).text"
                                    :severity="getStatusLabel(slotProps.data.trangThai).severity" />
                            </template>
                        </Column>

        </DataTable>


    </div>
</template>
