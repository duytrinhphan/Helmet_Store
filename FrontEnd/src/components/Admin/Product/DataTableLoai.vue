<script setup>
import { ref, defineProps, onMounted } from 'vue';
import { useLoaiService } from '@/service/Admin/Loai/LoaiService';
import Index from '@/components/Admin/Loai/Index.vue';

const LoaiService = useLoaiService();
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


const dataLoai = ref([]);
const loadDataLoai = async () => {
    await LoaiService.fetchData();
    dataLoai.value = LoaiService.data;
};

onMounted(() => {
    loadDataLoai();
});

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

const getStatusLabel = (trangThai) => {
    switch (trangThai) {
        case 1:
            return { text: 'Còn Hàng', severity: 'success' };

        case 2:
            return { text: 'hết Hàng', severity: 'danger' };

        case 3:
            return { text: 'tồn kho', severity: 'danger' };;

        default:
            return null;
    }
};


const selectedProduct = ref(null);
const products = ref(null);



</script>

<template>
    <div>
        <Dialog header="Loại" v-model:visible="display" :breakpoints="{ '960px': '75vw' }" :style="{ width: '800px',height:'800px'  }"
            :modal="true">
              <Index></Index>
        

            <template #footer>

            </template>
        </Dialog>
        <Button type="button" icon="pi pi-pencil" @click="open" class="p-button-success"
            style="width: 50px; height: 40px; margin-left: 20px" />
    </div>
</template>
