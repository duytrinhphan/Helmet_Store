<template>
    <Button icon="pi pi-search" class="p-button-rounded p-button-info ml-2" rounded @click="showProduct()" />
    <Dialog v-model:visible="selectedDialog" header="Flex Scroll" :style="{ width: '75vw' }" maximizable modal :contentStyle="{ height: '300px' }" class="p-fluid">
        <template #header>
            <div class="flex flex-column md:flex-row md:justify-content-between md:align-items-center">
                <h5 class="m-0">Khuyến Mại</h5>
            </div>
        </template>
        <div class="card p-fluid">
            <DataTable
                :value="listSPCT"
                v-model:selection="selectedProduct"
                paginator
                :rows="5"
                :rowsPerPageOptions="[5, 10, 20, 50]"
                tableStyle="min-width: 50rem"
                paginatorTemplate="FirstPageLink PrevPageLink PageLinks NextPageLink LastPageLink CurrentPageReport RowsPerPageDropdown"
                currentPageReportTemplate="Showing {first} to {last} of {totalRecords} listSPCT"
                responsiveLayout="scroll"
            >
                <template #empty>
                    <div class="flex flex-column justify-content-center align-items-center" style="height: 300px">
                        <svg width="100px" height="100px" viewBox="0 0 16 16" xmlns="http://www.w3.org/2000/svg" fill="#000000" class="bi bi-file-earmark-x">
                            <path d="M6.854 7.146a.5.5 0 1 0-.708.708L7.293 9l-1.147 1.146a.5.5 0 0 0 .708.708L8 9.707l1.146 1.147a.5.5 0 0 0 .708-.708L8.707 9l1.147-1.146a.5.5 0 0 0-.708-.708L8 8.293 6.854 7.146z" />
                            <path d="M14 14V4.5L9.5 0H4a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h8a2 2 0 0 0 2-2zM9.5 3A1.5 1.5 0 0 0 11 4.5h2V14a1 1 0 0 1-1 1H4a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1h5.5v2z" />
                        </svg>
                        <h6>Không có dữ liệu.</h6>
                    </div>
                </template>
                <Column selectionMode="multiple" headerStyle="width: 3rem"></Column>
                <Column field="tenSP" header="Sản Phẩm"></Column>
                <Column field="tenMauSac" header="Màu Sắc"></Column>
                <Column field="tenSize" header="Size"></Column>
                <Column field="giaBan" header="Giá Bán"></Column>
            </DataTable>
        </div>

        <template #footer>
            <Button label="Huỷ" icon="pi pi-times" class="p-button-text" @click="hide" />
            <Button label="Áp dụng" icon="pi pi-check" @click="applyKhuyenMai" />
        </template>
    </Dialog>
</template>

<script setup>
import { ref, onMounted } from 'vue';
// import { productStore } from '@/service/Admin/Product/ProductService.js';
import { ctspStore } from '@/service/Admin/SanPhamChiTiet/sanPhamCTService';
import { useToast } from 'primevue/usetoast';
import { khuyenMaiStore } from '@/service/Admin/KhuyenMai/KhuyenMaiService.js';

const khuyenmaiService = khuyenMaiStore();
const toast = useToast();
const listSPCT = ref(null);
const selectedProduct = ref([]);
// const productService = productStore();
const selectedDialog = ref(false);
const ctspService = ctspStore();
const props = defineProps({
    newProp: {}
});

const showProduct = () => {
    selectedDialog.value = true;
};

const hide = () => {
    selectedDialog.value = false;
};

const idsp = props.newProp.id;
onMounted(() => {
    // console.log(idsp);
    loadDataProduct(idsp);
});

const loadDataProduct = async (idsp) => {
    await ctspService.fetchData(idsp);
    listSPCT.value = ctspService.data;
   
};

const applyKhuyenMai = () => {
    // đây là số lượng SPCT được chọn để áp dụng khuyến mại
    const sl = selectedProduct.value.length;
    // đây là IdKM dùng để áp dụng được truyền từ component cha
    const idkm = localStorage.getItem('idkm');

    // duyệt qua mảng danh sách các CTSP được chọn
    selectedProduct.value.forEach((product) => {
        const productId = product.id;
        // cập nhật lại giá tiền và id khuyến mại
        ctspService.updateCTSP(productId, idkm);
    });

    localStorage.removeItem('idkm');
    selectedProduct.value = [];
    selectedDialog.value = false;
    loadDataProduct(idsp);
    toast.add({ severity: 'success', summary: 'Successful', detail: 'Áp dụng khuyến mại thành công', life: 3000 });
};
</script>
