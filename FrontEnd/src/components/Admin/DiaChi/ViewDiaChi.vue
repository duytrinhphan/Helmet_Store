<script setup>
import { ref, onMounted, onBeforeMount } from 'vue';
import { FilterMatchMode } from 'primevue/api';
import { useToast } from 'primevue/usetoast';
import ThemDiaChi from './ThemDiaChi.vue';

const product = ref({});
const submitted = ref(false);
const productDialog = ref(false);
const filters = ref({});
const dt = ref(null);

const valueChucVu = ref([
    { label: 'Nhân viên', value: 'Nhân viên' },
    { label: 'Quản lý', value: 'Quản lý' },
    { label: 'Khách hàng', value: 'Khách hàng' }
]);
// mở form
const openNew = () => {
    product.value = {};
    submitted.value = false;
    productDialog.value = true;
};

//đóng form
const hideDialog = () => {
    productDialog.value = false;
    submitted.value = false;
};

//save
const saveProduct = () => {
    submitted.value = true;
    if (product.value.name && product.value.name.trim() && product.value.price) {
        if (product.value.id) {
            product.value.inventoryStatus = product.value.inventoryStatus.value ? product.value.inventoryStatus.value : product.value.inventoryStatus;
            products.value[findIndexById(product.value.id)] = product.value;
            toast.add({ severity: 'success', summary: 'Successful', detail: 'Product Updated', life: 3000 });
        } else {
            product.value.id = createId();
            product.value.code = createId();
            product.value.image = 'product-placeholder.svg';
            product.value.inventoryStatus = product.value.inventoryStatus ? product.value.inventoryStatus.value : 'INSTOCK';
            products.value.push(product.value);
            toast.add({ severity: 'success', summary: 'Successful', detail: 'Product Created', life: 3000 });
        }
        productDialog.value = false;
        product.value = {};
    }
};
const exportCSV = () => {
    dt.value.exportCSV();
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
    <!-- <Button label="New" icon="pi pi-plus" class="p-button-success mr-2" @click="openNew" /> -->
    <Button icon="pi pi-plus" severity="success" rounded aria-label="Search" @click="openNew" />
    <!-- <div class="grid">
        <div class="col-12">
            <div class="card"> -->
    <Dialog v-model:visible="productDialog" :style="{ width: '500px' }" header="Địa chỉ" :modal="true" class="p-fluid">
        <Toolbar class="mb-4">
            <template v-slot:start>
                <div class="my-2">
                    <!-- <Button label="New" icon="pi pi-plus" class="p-button-success mr-2" @click="openNew" /> -->
                    <!-- <ThemUser></ThemUser> -->
                    <ThemDiaChi></ThemDiaChi>
                </div>
            </template>

            <template v-slot:end>
                <FileUpload mode="basic" accept="image/*" :maxFileSize="1000000" label="Import" chooseLabel="Import" class="mr-2 inline-block" style="width: 110px" />
                <Button label="Export" icon="pi pi-upload" class="p-button-help" @click="exportCSV($event)" style="width: 110px" />
            </template>
        </Toolbar>
        <DataTable
            ref="dt"
            :value="products"
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
                <div class="flex flex-column md:flex-row md:justify-content-between md:align-items-center ml-auto" style="width: 50%">
                    <span class="block mt-2 md:mt-0 p-input-icon-left">
                        <i class="pi pi-search" />
                        <InputText v-model="filters['global'].value" placeholder="Search..." />
                    </span>
                </div>
            </template>

            <Column field="code" header="Mã" :sortable="true" headerStyle="width:14%; min-width:10rem;">
                <template #body="slotProps">
                    <span class="p-column-title">Code</span>
                    {{ slotProps.data.code }}
                </template>
            </Column>
            <Column field="name" header="Tên" :sortable="true" headerStyle="width:14%; min-width:10rem;">
                <template #body="slotProps">
                    <span class="p-column-title">Name</span>
                    {{ slotProps.data.name }}
                </template>
            </Column>

            <Column header="Hành động" headerStyle="min-width:10rem;">
                <template #body="slotProps">
                    <DetailUser></DetailUser>
                    <UpdateUser></UpdateUser>
                    <!-- <Button icon="pi pi-pencil" class="p-button-rounded p-button-success mr-2" @click="editProduct(slotProps.data)" /> -->
                    <Button icon="pi pi-trash" class="p-button-rounded p-button-warning mt-2" @click="confirmDeleteProduct(slotProps.data)" />
                </template>
            </Column>
        </DataTable>
    </Dialog>
    <!-- </div>
        </div>
    </div> -->
</template>
