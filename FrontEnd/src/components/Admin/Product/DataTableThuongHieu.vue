<script setup>

import { ref, defineProps, onMounted } from 'vue';
import { useToast } from 'primevue/usetoast';
import View from '@/components/Admin/QuanLyThuongHieu/ViewThuongHieu.vue';
import ThemThuongHieu from '@/components/Admin/QuanLyThuongHieu/ThemThuongHieu.vue';
import DetailThuongHieu from '@/components/Admin/QuanLyThuongHieu/DetailThuongHieu.vue';
import UpdateThuongHieu from '@/components/Admin/QuanLyThuongHieu/UpdateThuongHieu.vue';
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

const loadDataThuongHieu = async () => {
    await useThuongHieuService.fetchData();
    dataThuongHieu.value = useThuongHieuService.data;
};
onMounted(() => {
    loadDataThuongHieu();
});



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
        <Dialog header="Thương hiệu" v-model:visible="display" :breakpoints="{ '960px': '75vw' }" :style="{ width: '800px',height:'800px' }"
            :modal="true">
             <View></View>

            <template #footer>

            </template>
        </Dialog>
        <Button type="button" icon="pi pi-pencil" @click="open" class="p-button-success"
            style="width: 50px; height: 40px; margin-left: 20px" />
    </div>
</template>
