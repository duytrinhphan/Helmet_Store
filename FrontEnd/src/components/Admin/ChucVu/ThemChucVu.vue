<script setup>
import { ref, onMounted, onBeforeMount } from 'vue';
import ViewChucVu from '@/components/Admin/ChucVu/ViewChucVu.vue';

const product = ref({});
const submitted = ref(false);
const productDialog = ref(false);

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
</script>
<template>
    <Button label="New" icon="pi pi-plus" class="p-button-success mr-2" @click="openNew" />
    <!-- <div class="grid">
        <div class="col-12">
            <div class="card"> -->
    <Dialog v-model:visible="productDialog" :style="{ width: '450px' }" header="Thêm chức vụ" :modal="true" class="p-fluid">
        
        <div class="field">
            <label for="name">Tên chức vụ</label>
            <InputText id="name" v-model.trim="product.name" required="true" autofocus :class="{ 'p-invalid': submitted && !product.name }" />
            <small class="p-invalid" v-if="submitted && !product.name">Name is required.</small>
        </div>
        
        <template #footer>
            <Button label="Cancel" icon="pi pi-times" class="p-button-text" @click="hideDialog" />
            <Button label="Save" icon="pi pi-check" class="p-button-text" @click="saveProduct" />
        </template>
    </Dialog>
    <!-- </div>
        </div>
    </div> -->
</template>
