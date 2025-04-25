<script setup>
import { FilterMatchMode } from 'primevue/api';
import { ref, onMounted, onBeforeMount, watch } from 'vue';
import Them from './Add.vue';
import Detail from './Detail.vue';
import Update from './Update.vue';
import { useDiaChi } from '@/service/KhachHang/DiaChiService';
import { useToast } from 'primevue/usetoast';
import { useRoute } from 'vue-router';

const route = useRoute();
const idProduct = parseInt(route.params.id);
const diaChiService = useDiaChi();
const toast = useToast();
const deleteProductDialog = ref(false);
const product = ref({});
const selectedProducts = ref(null);
const dt = ref(null);
const filters = ref({});
const trangThai = ref();
const idDelete = ref();
// const dataDiaChi = ref([]);

const diaChi = ref([]);

const loadDiaChi = async () => {
    const token = localStorage.getItem('token');
    if (token.length > 0 || token != null) {
        await diaChiService.fetchData(token);
        diaChi.value = diaChiService.diaChi;
    }
    // await diaChiService.fetchAllDiaChi(idProduct); // Gọi hàm fetchAll từ Store
    // diaChi.value = diaChiService.diaChi;
    // console.log(diaChiService.diaChi);
    // console.log('mau: ',  loadMau.value );
    // return diaChi.value;
};

onBeforeMount(() => {
    initFilters();
});

onMounted(() => {
    loadDiaChi();
});

//hiện confirm
const confirmDeleteProduct = (id) => {
    idDelete.value = id;
    deleteProductDialog.value = true;
};

//xoá
const deleteProduct = (id) => {
    diaChiService.deleteDiaChi(idDelete.value); // Delete the item from the service
    diaChi.value = diaChi.value.filter((item) => item.id !== idDelete.value); // Remove the item from the diaChi array
    toast.add({ severity: 'success', summary: 'Thông báo', detail: 'Xoá thành công', life: 3000 });
    deleteProductDialog.value = false; // Close the delete confirmation dialog
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
    <div class="container">
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
                    :value="diaChi"
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
                            <h5 class="m-0">Địa chỉ của tôi</h5>
                            <span class="block mt-2 md:mt-0 p-input-icon-left">
                                <i class="pi pi-search" />
                                <InputText v-model="filters['global'].value" placeholder="Search..." />
                                <!-- <Dropdown v-model="trangThai" :options="dataTrangThai" optionLabel="label" placeholder="Tất cả" class="w-full md:w-14rem" style="margin-left: 20px" /> -->
                            </span>
                        </div>
                    </template>
                    <Column field="tenTinhThanh" header="Tỉnh thành" :sortable="true" headerStyle="width:14%; min-width:10rem;">
                        <template #body="slotProps">
                            <span class="p-column-title">tenTinhThanh</span>
                            {{ slotProps.data.tenTinhThanh }}
                        </template>
                    </Column>
                    <Column field="tenQuanHuyen" header="Quận huyện" :sortable="true" headerStyle="width:14%; min-width:10rem;">
                        <template #body="slotProps">
                            <span class="p-column-title">tenQuanHuyen</span>
                            {{ slotProps.data.tenQuanHuyen }}
                        </template>
                    </Column>
                    <Column field="tenphuongXa" header="Phường xã" :sortable="true" headerStyle="width:14%; min-width:8rem;">
                        <template #body="slotProps">
                            <span class="p-column-title">tenphuongXa</span>
                            {{ slotProps.data.tenphuongXa }}
                        </template>
                    </Column>
                    <Column field="diaChi" header="Địa chỉ cụ thể" :sortable="true" headerStyle="width:14%; min-width:10rem;">
                        <template #body="slotProps">
                            <span class="p-column-title">diaChi</span>
                            {{ slotProps.data.diaChi }}
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
                            >Bạn có chắc chắn muốn xoá địa chỉ <b>{{ product.ten }}</b> không ?</span
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

<style scoped>
.container {
    margin-top: 70px;
}
</style>
