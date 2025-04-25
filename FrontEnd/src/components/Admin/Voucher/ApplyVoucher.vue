<script setup>
import { FilterMatchMode } from 'primevue/api';
import { ref, onMounted, defineEmits, watch, onBeforeMount } from 'vue';
// import { productStore } from '@/service/Admin/Product/ProductService.js';
import { voucherStore } from '@/service/Admin/Voucher/VoucherService.js';
import { useToast } from 'primevue/usetoast';

const toast = useToast();
const dataUser = ref([]);
const selectedProduct = ref([]);
// const productService = productStore();
const selectedProductDialog = ref(false);
const userVoucherService = voucherStore();
const khuyenmais = ref([]);
const filters = ref({});
const props = defineProps({
    myProp: {}
});
// đây là số lượng khuyến mại còn lại theo IdKM khi được chọn
const soLuong = props.myProp.soLuong;

const showProducts = () => {
    selectedProductDialog.value = true;
};
// dùng emit để cập nhật lại số lượng khuyến mại ở component cha
const emits = defineEmits(['update']);

const hideDialog = () => {
    selectedProductDialog.value = false;
};

onMounted(() => {
    loadDataUser(dataSearch.value[0].value);
});

const loadDataUser = async (cbbValue) => {
    await userVoucherService.getUserByTongTien(cbbValue);
    dataUser.value = userVoucherService.dataUser;
};

// const loadDataKhuyenmai = async () => {
//     await khuyenmaiService.getKhuyenMai();
//     khuyenmais.value = khuyenmaiService.data;
// };

// const applyKhuyenMai = () => {
//     // đây là số lượng SPCT được chọn để áp dụng khuyến mại
//     const sl = selectedProduct.value.length;
//     // đây là IdKM dùng để áp dụng được truyền từ component cha
//     const idkm = props.myProp.id;

//     // duyệt qua mảng danh sách các CTSP được chọn
//     selectedProduct.value.forEach((product) => {
//         const productId = product.id;
//         // cập nhật lại giá tiền và id khuyến mại
//         khuyenmaiService.updateCTSP(productId, idkm);
//         loadDataProduct();
//         selectedProduct.value = [];
//         selectedProductDialog.value = false;
//     });
//     loadDataProduct();
//     toast.add({ severity: 'success', summary: 'Successful', detail: 'Áp dụng khuyến mại thành công', life: 3000 });
// };
const cbbValue = ref();
const dataSearch = ref([
    { label: 'Dưới 1 triệu', value: 'duoi1Trieu' },
    { label: '1 đên 3 triệu', value: '1den3' },
    { label: '3 đến 6 triệu', value: '3den6' },
    { label: '6 đến 10 triệu', value: '6den10' },
    { label: 'Trên 10 triệu', value: 'tren10trieu' }
]);

watch(cbbValue, (newVal) => {
    if (cbbValue.value.value == 'duoi1Trieu') {
        loadDataUser('duoi1Trieu');
    } else if (cbbValue.value.value == '1den3') {
        loadDataUser('1den3');
    } else if (cbbValue.value.value == '3den6') {
        loadDataUser('3den6');
    } else if (cbbValue.value.value == '6den10') {
        loadDataUser('6den10');
    } else if (cbbValue.value.value == 'tren10trieu') {
        loadDataUser('6den10');
    } else {
        loadDataUser('duoi1Trieu');
    }
});

watch(selectedProductDialog, (newVal) => {
    if (selectedProductDialog.value == false) {
        selectedProduct.value = [];
    }
});

const formatCurrency = (value) => {
    return parseInt(value).toLocaleString('vi-VN', { style: 'currency', currency: 'VND' });
};

// confirm xác nhận
const addProductDialog = ref(false);

//hiện confirm
const confirmAddProduct = () => {
    addProductDialog.value = true;
};

const btnXacNhan = () => {
    if (selectedProduct.value.length <= 0) {
        toast.add({ severity: 'error', summary: 'error', detail: 'Bạn chưa chọn', life: 3000 });
        addProductDialog.value = false;
    } else {
        // useHD.huyHoaDon(idHD.value, 'Người dùng không nhận hàng', 5);
        selectedProduct.value.forEach((user) => {
            const form = {
                idVoucher: props.myProp.id,
                idUser: user.id
            };
            userVoucherService.addUserWithVoucher(form);
            // console.log(form);
        });
        toast.add({ severity: 'success', summary: 'Thông báo', detail: 'Lưu thành công', life: 3000 });
        addProductDialog.value = false;
        selectedProductDialog.value = false;
    }
};
onBeforeMount(() => {
    initFilters();
});

const initFilters = () => {
    filters.value = {
        global: { value: null, matchMode: FilterMatchMode.CONTAINS }
    };
};
</script>

<template>
    <!-- không thể chọn CTSP áp dụng khuyến mại nếu trạng thái khuyến mại là hết hạn ( = 1) -->
    <Button icon="pi pi-search"  class="p-button-rounded p-button-info mr-2" @click="showProducts()" :disabled="props.myProp.trangThai === 1" />
    <Dialog v-model:visible="selectedProductDialog" header="Flex Scroll" :style="{ width: '75vw' }" maximizable modal :contentStyle="{ height: '300px' }" class="p-fluid">
        <template #header>
            <div class="flex flex-column md:flex-row md:justify-content-between md:align-items-center">
                <h5 class="m-0">Voucher</h5>
            </div>
        </template>

        <div class="card p-fluid">
            <DataTable
                :value="dataUser"
                v-model:selection="selectedProduct"
                paginator
                :filters="filters"
                :rows="10"
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
                <Column selectionMode="multiple" headerStyle="width: 3rem"></Column>
                <Column field="ma" header="Mã" :sortable="true"></Column>
                <Column field="ten" header="Tên" :sortable="true"></Column>
                <Column field="sdt" header="Sđt" :sortable="true"></Column>
                <Column field="email" header="Email" :sortable="true"></Column>
                <Column field="tongTienDaMua" header="Tổng tiền đã mua" :sortable="true" headerStyle="width:14%; min-width:10rem;">
                    <template #body="slotProps">
                        <span class="p-column-title">tongTienDaMua</span>
                        {{ formatCurrency(slotProps.data.tongTienDaMua) }}
                    </template>
                </Column>
            </DataTable>
        </div>

        <template #footer>
            <Button label="Cancel" icon="pi pi-times" class="p-button-text" @click="hideDialog" />
            <Button label="Save" icon="pi pi-check" @click="confirmAddProduct" />
        </template>
    </Dialog>
    <Dialog v-model:visible="addProductDialog" :style="{ width: '450px' }" header="Confirm" :modal="true">
        <div class="flex align-items-center justify-content-center">
            <i class="pi pi-exclamation-triangle mr-3" style="font-size: 2rem" />
            <span>Bạn có chắc chắn muốn lưu không ?</span>
        </div>
        <template #footer>
            <Button label="No" icon="pi pi-times" class="p-button-text" @click="addProductDialog = false" />
            <Button label="Yes" icon="pi pi-check" class="p-button-text" @click="btnXacNhan()" />
        </template>
    </Dialog>
</template>
