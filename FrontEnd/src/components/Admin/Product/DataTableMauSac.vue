<script setup>

import { ref, defineProps, onMounted } from 'vue';

import Index from '@/components/Admin/MauSac/Index.vue';

import { useMauSacService } from '@/service/Admin/MauSac/MauSacService';
import { useToast } from 'primevue/usetoast';

const toast = useToast();
const mauSacService = useMauSacService();
const dataMauSac = ref([]);
const props = defineProps({
    tableId: String,
    rightGhId: String,
    tableClass: String,
    rightGhClass: String
});

const display = ref(false);
const isRightGhLoaiVisible = ref(true);

const tableClass = ref(props.tableClass);
const rightGhClass = ref(props.rightGhClass);


const formatCurrency = (value) => {
    return value.toLocaleString('en-US', { style: 'currency', currency: 'USD' });
};

const loadDataMauSac = async () => {
    await mauSacService.fetchData();
    dataMauSac.value = mauSacService.data;
};

onMounted(() => {
    loadDataMauSac();
});
const deleteProductDialog = ref(false);
const idDelete = ref();
const confirmDeleteProduct = (id) => {
    idDelete.value = id;
    deleteProductDialog.value = true;
};

//xoá
const deleteProduct = () => {
    const remove = mauSacService.deleteMauSac(idDelete.value);
    toast.add({ severity: 'success', summary: 'Thông báo', detail: 'Xoá thành công', life: 3000 });
    deleteProductDialog.value = false;
};

const chuyenPhanTus = (id) => {
    isRightGhLoaiVisible.value = false;
    const element = document.getElementById(id);
    window.scroll({
        top: element.offsetTop,
        behavior: 'smooth'
    });
};

const vePhanTus = (id) => {
    isRightGhLoaiVisible.value = true;
    const element = document.getElementById(id);
    if (element) {
        window.scroll({
            top: element.offsetTop,
            behavior: 'smooth'
        });
    }
};

const open = () => {
    display.value = true;
};

const close = () => {
    display.value = false;
};

const selectedProduct = ref(null);
const products = ref(null);
</script>

<template>
    <div>
        <Dialog header="Màu sắc" v-model:visible="display" :breakpoints="{ '960px': '75vw' }" :style="{ width: '1000px' }"
            :modal="true">
            <index></index>

            <template #footer>

            </template>
        </Dialog>
        <Button type="button" icon="pi pi-pencil" @click="open" class="p-button-success"
            style="width: 50px; height: 40px; margin-left: 20px" />
    </div>
</template>
