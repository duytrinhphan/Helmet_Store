<script setup>
import { FilterMatchMode } from 'primevue/api';
import { ref, onMounted, onBeforeMount, watch } from 'vue';
import Them from './ThemVatLieu.vue';
import Detail from './DetailVatLieu.vue';
import Update from './UpdateVatLieu.vue';
import { VatLieuStore } from '@/service/Admin/VatLieu/VatLieu.api';
import { useToast } from 'primevue/usetoast';

const vatLieuService = VatLieuStore();
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
const dataVatLieu = ref([]);

//load data màu sắc tất cả
// const loadDataMauSac = () => {
//     axios.get('http://localhost:8080/api/mau-sac').then((response) => {
//         dataMauSac.value = response.data.data;
//     });
// };

//load data màu sắc theo trạng thái
const loadDataVatLieu = async () => {
    await vatLieuService.fetchAll();
    dataVatLieu.value = vatLieuService.data;
};

const loadDataVatLieuByTrangThai = async () => {
    await vatLieuService.fetchDataByStatus(trangThai.value.value);
    dataVatLieu.value = vatLieuService.data;
};
//thay đổi cbb
watch(trangThai, (newVal) => {
    if (trangThai.value.value != 'Tất cả') {
        loadDataVatLieuByTrangThai();
    } else {
        loadDataVatLieu();
    }
});

onBeforeMount(() => {
    initFilters();
});

onMounted(() => {
    loadDataVatLieu();
});

//hiện confirm
const confirmDeleteProduct = (id) => {
    idDelete.value = id;
    deleteProductDialog.value = true;
};

//xoá
const deleteProduct = (id) => {
    // axios.put('http://localhost:8080/api/mau-sac/delete/' + idDelete.value).then((response) => {
    //     alert(response.data.data);
    //     deleteProductDialog.value = false;
    //     window.location.reload();
    // });
    const remove = vatLieuService.deleteVatLieu(idDelete.value);
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
                            <Them></Them>
                        </div>
                    </template>
                </Toolbar>
                <DataTable
                    ref="dt"
                    :value="dataVatLieu"
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
                            <h5 class="m-0">Quản lý vật liệu</h5>
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
                    <Column field="moTa" header="Mô tả" :sortable="true" headerStyle="width:14%; min-width:8rem;">
                        <template #body="slotProps">
                            <span class="p-column-title">moTa</span>
                            {{ slotProps.data.moTa }}
                        </template>
                    </Column>
                    <Column field="category" header="Trạng thái" :sortable="true" headerStyle="width:14%; min-width:10rem;">
                        <template #body="slotProps">
                            <span class="p-column-title">Category</span>
                            {{ slotProps.data.trangThai == 1 ? 'Đang sử dụng' : 'Đã xóa' }}
                        </template>
                    </Column>

                    <Column header="Hành động" headerStyle="min-width:10rem;">
                        <template #body="slotProps">
                            <Detail :my-prop="slotProps.data"></Detail>
                            <Update :my-prop="slotProps.data"></Update>
                            <!-- <Button icon="pi pi-pencil" class="p-button-rounded p-button-success mr-2" @click="editProduct(slotProps.data)" /> -->
                            <Button icon="pi pi-trash" class="p-button-rounded p-button-warning mt-2" @click="confirmDeleteProduct(slotProps.data.id)" />
                        </template>
                    </Column>
                </DataTable>
                <Dialog v-model:visible="deleteProductDialog" :style="{ width: '450px' }" header="Confirm" :modal="true">
                    <div class="flex align-items-center justify-content-center">
                        <i class="pi pi-exclamation-triangle mr-3" style="font-size: 2rem" />
                        <span v-if="product"
                            >Bạn có chắc chắn muốn xoá màu <b>{{ product.ten }}</b> không ?</span
                        >
                    </div>
                    <template #footer>
                        <Button label="No" icon="pi pi-times" class="p-button-text" @click="deleteProductDialog = false" />
                        <Button label="Yes" icon="pi pi-check" class="p-button-text" @click="deleteProduct(product.id)" />
                    </template>
                </Dialog>
            </div>
        </div>
    </div>
</template>
