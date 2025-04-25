<script setup>
import { FilterMatchMode } from 'primevue/api';
import { ref, onMounted, onBeforeMount, watch } from 'vue';
import { useToast } from 'primevue/usetoast';
import ThemThuongHieu from './ThemThuongHieu.vue';
import DetailThuongHieu from './DetailThuongHieu.vue';
import UpdateThuongHieu from './UpdateThuongHieu.vue';
import { useCounterStore } from '@/service/Admin/ThuongHieu/ThuongHieuService';

const useThuongHieuService = useCounterStore();
const toast = useToast();
const deleteProductDialog = ref(false);
const product = ref({});
const selectedProducts = ref(null);
const dt = ref(null);
const filters = ref({});
const dataTrangThai = ref([
    { label: 'Tất cả', value: 'Tất cả' },
    { label: 'Đang sử dụng', value: '1' },
    { label: 'Ngưng sử dụng', value: '0' }
]);
const trangThai = ref();
const idDelete = ref();
const dataThuongHieu = ref([]);

//load data thương hiệu tất cả
const loadDataThuongHieu = async () => {
    await useThuongHieuService.fetchData();
    dataThuongHieu.value = useThuongHieuService.data;
};

//load data thương hiệu theo trạng thái
const loadDataThuongHieuByTrangThai = async () => {
    await useThuongHieuService.fetchDataByStatus(trangThai.value.value);
    dataThuongHieu.value = useThuongHieuService.data;
};

//thay đổi cbb
watch(trangThai, (newVal) => {
    if (trangThai.value.value != 'Tất cả') {
        loadDataThuongHieuByTrangThai();
    } else {
        loadDataThuongHieu();
    }
});

onBeforeMount(() => {
    initFilters();
});

//chạy cái hiện data luôn
onMounted(() => {
    loadDataThuongHieu();
});

//hiện confirm
const confirmDeleteProduct = (id) => {
    idDelete.value = id;
    deleteProductDialog.value = true;
};

//xoá
const deleteProduct = () => {
    const respone = useThuongHieuService.deleteSize(idDelete.value);
    toast.add({ severity: 'success', summary: 'Thông báo', detail: 'Xoá thành công', life: 3000 });
    deleteProductDialog.value = false;
};

const exportCSV = () => {
    dt.value.exportCSV();
};

const initFilters = () => {
    filters.value = {
        global: { value: null, matchMode: FilterMatchMode.CONTAINS }
    };
};
</script>
<template>
    <div class="grid">
        <div class="col-12">
            <div class="card">
                <Toast />
                <Toolbar class="mb-4">
                    <template v-slot:start>
                        <div class="my-2">
                            <!-- <Button label="New" icon="pi pi-plus" class="p-button-success mr-2" @click="openNew" /> -->
                            <ThemThuongHieu></ThemThuongHieu>
                        </div>
                    </template>

                    <!-- <template v-slot:end>
                        <FileUpload mode="basic" accept="image/*" :maxFileSize="1000000" label="Import" chooseLabel="Import" class="mr-2 inline-block" />
                        <Button label="Export" icon="pi pi-upload" class="p-button-help" @click="exportCSV($event)" />
                    </template> -->
                </Toolbar>

                <DataTable
                    ref="dt"
                    :value="dataThuongHieu"
                    v-model:selection="selectedProducts"
                    dataKey="id"
                    :paginator="true"
                    :rows="10"
                    :filters="filters"
                    paginatorTemplate="FirstPageLink PrevPageLink PageLinks NextPageLink LastPageLink CurrentPageReport RowsPerPageDropdown"
                    :rowsPerPageOptions="[5, 10, 25]"
                    currentPageReportTemplate="Showing {first} to {last} of {totalRecords} products"
                    responsiveLayout="scroll"
                >
                    <template #header>
                        <div class="flex flex-column md:flex-row md:justify-content-between md:align-items-center">
                            <h5 class="m-0">Quản lý thương hiệu</h5>
                            <span class="block mt-2 md:mt-0 p-input-icon-left">
                                <i class="pi pi-search" />
                                <InputText v-model="filters['global'].value" placeholder="Search..." />
                                <Dropdown v-model="trangThai" :options="dataTrangThai" optionLabel="label" placeholder="Tất cả" class="w-full md:w-14rem" style="margin-left: 20px" />
                            </span>
                        </div>
                    </template>
                    <Column field="ma" header="Mã" :sortable="true" headerStyle="width:14%; min-width:10rem;">
                        <template #body="slotProps">
                            <span class="p-column-title">ma</span>
                            {{ slotProps.data.ma }}
                        </template>
                    </Column>
                    <Column field="ten" header="Tên" :sortable="true" headerStyle="width:14%; min-width:10rem;">
                        <template #body="slotProps">
                            <span class="p-column-title">ten</span>
                            {{ slotProps.data.ten }}
                        </template>
                    </Column>

                    <Column field="trangThai" header="Trạng thái" :sortable="false" headerStyle="width:14%; min-width:10rem;">
                        <template #body="slotProps">
                            <span class="p-column-title">trangThai</span>
                            {{ slotProps.data.trangThai == 1 ? 'Đang sử dụng' : 'Ngưng sử dụng' }}
                        </template>
                    </Column>

                    <Column header="Hành động" headerStyle="min-width:10rem;">
                        <template #body="slotProps">
                            <DetailThuongHieu :my-prop="slotProps.data"></DetailThuongHieu>
                            <UpdateThuongHieu :my-prop="slotProps.data"></UpdateThuongHieu>
                            <!-- <Button icon="pi pi-pencil" class="p-button-rounded p-button-success mr-2" @click="editProduct(slotProps.data)" /> -->
                            <Button icon="pi pi-trash" class="p-button-rounded p-button-warning mt-2" @click="confirmDeleteProduct(slotProps.data.id)" />
                        </template>
                    </Column>
                </DataTable>
                <Dialog v-model:visible="deleteProductDialog" :style="{ width: '450px' }" header="Confirm" :modal="true">
                    <div class="flex align-items-center justify-content-center">
                        <i class="pi pi-exclamation-triangle mr-3" style="font-size: 2rem" />
                        <span v-if="product"
                            >Bạn có chắc chắn muốn xoá <b>{{ product.ten }}</b> không ?</span
                        >
                    </div>
                    <template #footer>
                        <Button label="No" icon="pi pi-times" class="p-button-text" @click="deleteProductDialog = false" />
                        <Button label="Yes" icon="pi pi-check" class="p-button-text" @click="deleteProduct" />
                    </template>
                </Dialog>

                <!-- <Dialog v-model:visible="deleteProductsDialog" :style="{ width: '450px' }" header="Confirm" :modal="true">
                    <div class="flex align-items-center justify-content-center">
                        <i class="pi pi-exclamation-triangle mr-3" style="font-size: 2rem" />
                        <span v-if="product">Are you sure you want to delete the selected products?</span>
                    </div>
                    <template #footer>
                        <Button label="No" icon="pi pi-times" class="p-button-text" @click="deleteProductsDialog = false" />
                        <Button label="Yes" icon="pi pi-check" class="p-button-text" @click="deleteSelectedProducts" />
                    </template>
                </Dialog> -->
            </div>
        </div>
    </div>
</template>
