<script setup>
import { format } from 'date-fns';
import { ref, onBeforeMount } from 'vue';
import { FilterMatchMode } from 'primevue/api';
import { voucherStore } from '@/service/Admin/Voucher/VoucherService.js';

const userVoucherService = voucherStore();
const filters = ref({});
const product = ref({});
const productDialog = ref(false);
const dataUser = ref([]);

const props = defineProps({
    myProp: {}
});

const editProduct = () => {
    //loadDataUser();
    product.value = { ...editProduct };
    productDialog.value = true;
};

const loadDataUser = async () => {
    const respone = await userVoucherService.getUserByVoucher(props.myProp.id);
    dataUser.value = respone;
    console.log(dataUser.value);
};
onBeforeMount(() => {
    initFilters();
});

const initFilters = () => {
    filters.value = {
        global: { value: null, matchMode: FilterMatchMode.CONTAINS }
    };
};
const formatCurrency = (value) => {
    return parseInt(value).toLocaleString('vi-VN', { style: 'currency', currency: 'VND' });
};

const formatDate = (dateTime) => {
    return format(new Date(dateTime), 'yyyy/MM/dd HH:mm:ss');
};
</script>
<template>
    <Button icon="pi pi-eye" class="p-button-rounded p-button-success mr-2" @click="editProduct()" />

    <Dialog v-model:visible="productDialog" :style="{ width: '1000px' }" header="Detail user" :modal="true" class="p-fluid">
        <div class="flex">
            <!-- Cột phải -->
            <div class="p-col-6">
                <div class="col-12">
                    <label
                        >Tên: <span>{{ myProp.ten }}</span></label
                    >
                </div>
                <div class="col-12">
                    <label
                        >Giá trị giảm: <span>{{ myProp.giaTriGiam }}%</span></label
                    >
                </div>
                <div class="col-12">
                    <label
                        >Giảm tối đa: <span>{{ myProp.giamToiDa }}</span></label
                    >
                </div>
                <div class="col-12">
                    <label
                        >Ngày bắt đầu: <span>{{ formatDate(myProp.thoiGianBatDau) }}</span></label
                    >
                </div>
            </div>
            <!-- cột trái -->
            <div class="p-col-6">
                <div class="col-12">
                    <label
                        >Ngày kết thúc: <span>{{ formatDate(myProp.thoiGianKetThuc) }}</span></label
                    >
                </div>
                <div class="col-12">
                    <label
                        >Số lượng: <span>{{ myProp.soLuong }}</span></label
                    >
                </div>
                <div class="col-12">
                    <label
                        >Mô tả: <span>{{ myProp.moTa }}</span></label
                    >
                </div>
                <div class="col-12">
                    <label
                        >Trạng thái: <span>{{ myProp.trangThai == 0 ? 'Đang sử dụng' : 'Ngưng sử dụng' }}</span></label
                    >
                </div>
            </div>
        </div>
        <!-- <DataTable
            :value="dataUser"
            v-model:selection="selectedProduct"
            paginator
            :filters="filters"
            :rows="5"
            :rowsPerPageOptions="[5, 10, 20, 50]"
            tableStyle="min-width: 50rem"
            paginatorTemplate="FirstPageLink PrevPageLink PageLinks NextPageLink LastPageLink CurrentPageReport RowsPerPageDropdown"
            currentPageReportTemplate="Showing {first} to {last} of {totalRecords} products"
            responsiveLayout="scroll"
        >
            <div class="flex flex-column md:flex-row md:justify-content-between md:align-items-center">
                <span class="block mt-2 md:mt-0 p-input-icon-left">
                    <i class="pi pi-search" style="margin-bottom: 10px" />
                    <InputText v-model="filters['global'].value" placeholder="Search..." style="width: 300px; margin-bottom: 10px" />
                </span>
                <div class="md:ml-auto">
                    <Dropdown v-model="cbbValue" :options="dataSearch" optionLabel="label" placeholder="Dưới 1 triệu" class="p-column-filter" style="margin-bottom: 10px; width: 200px" :showClear="true"> </Dropdown>
                </div>
            </div>
            
            <Column field="ma" header="Mã" :sortable="true"></Column>
            <Column field="ten" header="Tên" :sortable="true"></Column>
            <Column field="sdt" header="Sđt" :sortable="true"></Column>
            <Column field="email" header="Email" :sortable="true"></Column>
            <Column field="tongTien" header="Tổng tiền đã mua" :sortable="true" headerStyle="width:14%; min-width:10rem;">
                <template #body="slotProps">
                    <span class="p-column-title">tongTien</span>
                    {{ formatCurrency(slotProps.data.tongTien) }}
                </template>
            </Column>
        </DataTable> -->
    </Dialog>
    <!-- </div>
        </div>
    </div> -->
</template>
