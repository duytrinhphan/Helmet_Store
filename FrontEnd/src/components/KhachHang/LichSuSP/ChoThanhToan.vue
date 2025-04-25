<!-- eslint-disable no-unused-vars -->
<script setup>
import * as yup from 'yup';
import { useForm, useField } from 'vee-validate';
import { FilterMatchMode, FilterOperator } from 'primevue/api';
import { ref, onBeforeMount, onMounted, watch } from 'vue';
import { useToast } from 'primevue/usetoast';
import { HDKHStore } from '@/service/KhachHang/HoaDonKHService';
import DetailHoaDon from './TrangThaiDonHang.vue';
import { useRouter } from 'vue-router';

const router = useRouter();

const redirectToTrangThaiDonHang = (id) => {
    // Chuyển hướng đến trang trang-thai-don-hang và truyền ID của hóa đơn qua URL
    router.push({ name: 'trang-thai-don-hang', params: { id: id } });
};
const toast = useToast();
const useHD = HDKHStore();
const filters1 = ref(null);
const idHD = ref(null);
const data = ref([]);

//show dialog lý do
const lyDoDialog = ref(false);
// confirm xác nhận
const addProductDialog = ref(false);
// confirm huy
const huyDialog = ref(false);

//hiện dialog lý do
const showDialogLyDo = (id) => {
    idHD.value = id;
    lyDoDialog.value = true;
};

//hiện confirm
const confirmAddProduct = (id) => {
    idHD.value = id;
    addProductDialog.value = true;
};

//hiện confirm huy
const confirmHuy = () => {
    huyDialog.value = true;
};

watch(addProductDialog, (newVal) => {
    if (addProductDialog.value == false) {
        idHD.value = null;
    }
});

watch(lyDoDialog, (newVal) => {
    if (lyDoDialog.value == false) lyDo.value = '';
});

const loadData = async () => {
    const token = localStorage.getItem('token');
    if (token.length > 0 || token != null) {
        await useHD.fetchDataByStatus(token, 1, "", "","");
        data.value = useHD.dataChoThanhToan;
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
        return { text: 'Giao cho đơn vị vận chuyển', severity: 'help' };
    } else if (trangThai == 6) {
        return { text: 'Yêu cầu đổi trả', severity: 'warning' };
    } else {
        return { text: 'Xác nhận đổi trả', severity: 'success' };
    }
};

const btnXacNhan = () => {
    useHD.choXacNhan(idHD.value);
    toast.add({ severity: 'success', summary: 'Thông báo', detail: 'Xác nhận thành công', life: 3000 });
    addProductDialog.value = false;
};

const schema = yup.object().shape({
    lyDo: yup.string().required('Lý do không được để trống')
});
const { handleSubmit, resetForm } = useForm({
    validationSchema: schema
});
const { value: lyDo, errorMessage: LyDoError } = useField('lyDo');
const btnXacNhanHuy = () => {
    if (lyDo.value == null || lyDo.value.length <= 0) {
        toast.add({ severity: 'success', summary: 'Thông báo', detail: 'Huỷ thất bại', life: 3000 });
        lyDo.value = '';
        huyDialog.value = false;
    } else {
        useHD.huyHoaDon(idHD.value, lyDo.value);
        toast.add({ severity: 'success', summary: 'Thông báo', detail: 'Huỷ thành công', life: 3000 });
        lyDo.value = '';
        huyDialog.value = false;
        lyDoDialog.value = false;
    }
};

const columns = ref([
    { field: 'maHD', header: 'Mã hoá đơn' },
    { field: 'tenNguoiNhan', header: 'Tên người nhận' },
    { field: 'tongTien', header: 'Tổng tiền' },
    { field: 'tienSauKhiGiam', header: 'Tiền sau giảm' }
]);
const dataSearchDate = ref([
    { label: 'Ngày tạo', value: 'ngayTao' },
    { label: 'Ngày sửa', value: 'ngaySua' },
    { label: 'Ngày thanh toán', value: 'ngayThanhToan' },
    { label: 'Ngày ship', value: 'ngayShip' },
    { label: 'Ngày nhận', value: 'ngayNhan' }
]);
const startDate = ref(null);
const endDate = ref([null]);
const typeSearchDate = ref(null);

const searchDate = async () => {
    if (typeSearchDate.value == null) {
        const respone = await useHD.searchDateByTrangThai(startDate.value, endDate.value, 'ngayTao', 2);
        data.value = respone;
    } else {
        const respone = await useHD.searchDateByTrangThai(startDate.value, endDate.value, typeSearchDate.value.value, 2);
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
    return value.toLocaleString('en-US', { style: 'currency', currency: 'USD' });
};

const formatDate = (value) => {
    return value.toLocaleDateString('en-US', {
        day: '2-digit',
        month: '2-digit',
        year: 'numeric'
    });
};
</script>
<template>
    <!-- <Toast />
    <div class="col-12 flex" style="margin-right: 10px; padding-left: 0">
        <Dropdown v-model="typeSearchDate" :options="dataSearchDate" optionLabel="label" placeholder="Ngày tạo" class="w-full md:w-14rem" style="height: 40px" />
        <div class="p-inputgroup flex-1" style="margin-left: 20px">
            <span class="p-inputgroup-addon" style="height: 40px">Ngày bắt đầu</span>
            <input type="datetime-local" v-model="startDate" style="min-width: 13rem; height: 40px" />
        </div>
        <div class="p-inputgroup flex-1">
            <span class="p-inputgroup-addon" style="height: 40px">Ngày kết thúc</span>
            <input type="datetime-local" v-model="endDate" style="min-width: 13rem; height: 40px" />
        </div>
        <div style="margin-left: 5px">
            <Button label="Seach" @click="searchDate()" icon="pi pi-search" class="p-button-rounded p-button-primary mr-2 mb-2" />
        </div>
    </div> -->
    <DataTable ref="dt" :value="data" v-model:selection="selectedProducts" dataKey="id" :paginator="true" :rows="5"
        :filters="filters1"
        paginatorTemplate="FirstPageLink PrevPageLink PageLinks NextPageLink LastPageLink CurrentPageReport RowsPerPageDropdown"
        :rowsPerPageOptions="[5, 10, 25]" currentPageReportTemplate="Showing {first} to {last} of {totalRecords} products"
        responsiveLayout="scroll">
        <template #header>
            <div class="col-12 flex">
                <div class="flex flex-column md:flex-row md:justify-content-between md:align-items-center">
                    <MultiSelect icon="pi pi-plus" placeholder="Select Columns" :modelValue="selectedColumns"
                        :options="columns" optionLabel="header" @update:modelValue="onToggle" display="tag" />
                </div>
                <span class="p-input-icon-left" style="margin-left: 20px">
                    <i class="pi pi-search" />
                    <InputText v-model="filters1['global'].value" placeholder="Keyword Search"
                        style="min-width: 13rem; height: 40px" />
                </span>
            </div>
        </template>
        <Column field="stt" header="STT" :sortable="true" headerStyle="width:14%; min-width:1rem;">
            <template #body="slotProps">
                <span class="p-column-title">stt</span>
                {{ slotProps.data.stt }}
            </template>
        </Column>
        <Column v-for="(col, index) of selectedColumns" :field="col.field" :header="col.header"
            :key="col.field + '_' + index" :sortable="true" headerStyle="width:14%; min-width:10rem;"></Column>
        <Column field="diaChi" header="Địa chỉ" :sortable="false" headerStyle="width:14%; min-width:10rem;">
            <template #body="slotProps">
                <span class="p-column-title">diaChi</span>
                {{ slotProps.data.diaChiCuThe }}, {{ slotProps.data.tenPhuongXa }}, {{ slotProps.data.tenQuanHuyen }}, {{
                    slotProps.data.tenTinhThanh }}
            </template>
        </Column>
        <Column field="trangThai" header="Trạng thái" :sortable="false" headerStyle="width:14%; min-width:10rem;">
            <template #body="slotProps">
                <span class="p-column-title">trangThai</span>
                <Tag :value="hienThiTrangThai(slotProps.data.trangThai).text"
                    :severity="hienThiTrangThai(slotProps.data.trangThai).severity" />
            </template>
        </Column>
        <Column header="Hành động" headerStyle="min-width:10rem;">
            <template #body="slotProps">
                <Button :my-prop="slotProps.data" @click="redirectToTrangThaiDonHang(slotProps.data.idHD)" label="Xem"
                    class="p-button-outlined p-button-info mr-2 mb-2" />
                <Button label="Thanh toán" class="p-button-outlined p-button-info mr-2 mb-2"
                    @click="confirmAddProduct(slotProps.data.idHD)" />
                <Button label="Hủy" class="p-button-outlined p-button-info mr-2 mb-2"
                    @click="showDialogLyDo(slotProps.data.idHD)" />
            </template>
        </Column>
    </DataTable>
    <!-- lý do -->
    <Dialog v-model:visible="lyDoDialog" :style="{ width: '450px' }" header="Huỷ hoá đơn" :modal="true">
        <div class="card">
            <form @submit="onSubmit">
                <div class="p-fluid formgrid grid">
                    <div class="field col-12" style="margin-bottom: 30px">
                        <label for="address">Lý do</label>
                        <Textarea id="lyDo" rows="4" v-model.trim="lyDo" :class="{ 'p-invalid': LyDoError }" required="true"
                            autofocus></Textarea>
                        <small class="p-error">{{ LyDoError }}</small>
                    </div>
                </div>
            </form>
        </div>
        <template #footer>
            <Button label="No" icon="pi pi-times" class="p-button-text" @click="lyDoDialog = false" />
            <Button label="Yes" icon="pi pi-check" class="p-button-text" @click="confirmHuy" />
        </template>
    </Dialog>
    <Dialog v-model:visible="addProductDialog" :style="{ width: '450px' }" header="Confirm" :modal="true">
        <div class="flex align-items-center justify-content-center">
            <i class="pi pi-exclamation-triangle mr-3" style="font-size: 2rem" />
            <span>Bạn có chắc chắn muốn nhận không ?</span>
        </div>
        <template #footer>
            <Button label="No" icon="pi pi-times" class="p-button-text" @click="addProductDialog = false" />
            <Button label="Yes" icon="pi pi-check" class="p-button-text" @click="btnXacNhan()" />
        </template>
    </Dialog>
    <!-- comfirm huỷ -->
    <Dialog v-model:visible="huyDialog" :style="{ width: '450px' }" header="Confirm" :modal="true">
        <div class="flex align-items-center justify-content-center">
            <i class="pi pi-exclamation-triangle mr-3" style="font-size: 2rem" />
            <span>Bạn có chắc chắn muốn huỷ không ?</span>
        </div>
        <template #footer>
            <Button label="No" icon="pi pi-times" class="p-button-text" @click="huyDialog = false" />
            <Button label="Yes" icon="pi pi-check" class="p-button-text" @click="btnXacNhanHuy()" />
        </template>
    </Dialog>
</template>
