<template>
    <!-- không thể chọn CTSP áp dụng khuyến mại nếu trạng thái khuyến mại là hết hạn ( = 1) -->
    <Button icon="pi pi-search" class="p-button-rounded p-button-info ml-2" @click="showProducts()" :disabled="props.myProp.trangThai === 1" />
    <Dialog v-model:visible="selectedProductDialog" header="Flex Scroll" :style="{ width: '75vw' }" maximizable modal :contentStyle="{ height: '300px' }" class="p-fluid">
        <template #header>
            <div class="flex flex-column md:flex-row md:justify-content-between md:align-items-center">
                <h5 class="m-0 ">Khuyến Mại</h5>              
            </div>

            <Dropdown id="dropdown" :options="dataLoai" v-model="selectedLoai" optionLabel="ten"  placeholder="Tất cả" :class="{ 'p-invalid': loaiError }" style="height: 40px"> </Dropdown>
                <!-- <label for="dropdown" style="margin-left: 10px;">Loại</label> -->
            <Dropdown v-model="thuongHieu" :options="dataThuongHieu" optionLabel="label" placeholder="Tất cả" class="w-full md:w-14rem" style="margin-left: 20px" />


        </template>
        <div class="card p-fluid">
            <DataTable
                :value="products"
                paginator
                :rows="5"
                :rowsPerPageOptions="[5, 10, 20, 50]"
                tableStyle="min-width: 50rem"
                paginatorTemplate="FirstPageLink PrevPageLink PageLinks NextPageLink LastPageLink CurrentPageReport RowsPerPageDropdown"
                currentPageReportTemplate="Showing {first} to {last} of {totalRecords} products"
                responsiveLayout="scroll"
            >
                <Column field="code" header="STT" :sortable="true" style="width: 1px; padding: 5px">
                    <template #body="slotProps">
                        <span class="p-column-title">STT</span>
                        {{ products.indexOf(slotProps.data) + 1 }}
                    </template>
                </Column>
                <Column field="ma" header="Mã" :sortable="true" headerStyle="width:8%; min-width:3rem;">
                    <template #body="slotProps">
                        <span class="p-column-title">ma</span>
                        {{ slotProps.data.ma }}
                    </template>
                </Column>
                <Column header="Image" headerStyle="width:8%; min-width:5rem;">
                    <template #body="slotProps">
                        <span class="p-column-title">Image</span>

                        <img :src="slotProps.data.anh" :alt="i" class="shadow-2" width="100" />
                    </template>
                </Column>
                <Column field="ten" header="Tên" :sortable="true" headerStyle="width:14%; min-width:10rem;">
                    <template #body="slotProps">
                        <span class="p-column-title">Tên</span>
                        {{ slotProps.data.ten }}
                    </template>
                </Column>
                <Column field="loai" header="Loại" :sortable="true" headerStyle="width:8%; min-width:5rem;">
                    <template #body="slotProps">
                        <span class="p-column-title">Tên</span>
                        {{ slotProps.data.loai }}
                    </template>
                </Column>
                <Column field="thuongHieu" header="Thương Hiệu" :sortable="true" headerStyle="width:14%; min-width:5rem;">
                    <template #body="slotProps">
                        <span class="p-column-title">Tên</span>
                        {{ slotProps.data.thuongHieu }}
                    </template>
                </Column>
                <Column field="vatLieu" header="Vật Liệu" :sortable="true" headerStyle="width:14%; min-width:5rem;">
                    <template #body="slotProps">
                        <span class="p-column-title">Tên</span>
                        {{ slotProps.data.vatLieu }}
                    </template>
                </Column>

                <Column field="trangThai" header="Trạng Thái" sortable headerStyle="width: 14%; min-width: 8rem;">
                    <template #body="slotProps">
                        <Tag :value="getStatusLabel(slotProps.data.soLuongTon, slotProps.data.trangThai).text" :severity="getStatusLabel(slotProps.data.soLuongTon, slotProps.data.trangThai).severity" />
                    </template>
                </Column>

                <Column header="Action" headerStyle="min-width:10rem;">
                    <template #body="slotProps">                    
                        <!-- <SelectCTSP :my-prop="slotProps.data"></SelectCTSP>           -->
                        <Button label="Chọn sản phẩm" icon="pi pi-search" rounded  @click="showProduct(slotProps.data.id)"/>     
                    </template>
                </Column>
            </DataTable>
        </div>

        <template #footer>
            <Button label="Cancel" icon="pi pi-times" class="p-button-text" @click="hideDialog" />
            <!-- <Button label="Save" icon="pi pi-check" @click="applyKhuyenMai" /> -->
        </template>

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
                currentPageReportTemplate="Showing {first} to {last} of {totalRecords}  listSPCT"
                responsiveLayout="scroll"
            >
                <Column selectionMode="multiple" headerStyle="width: 3rem"></Column>
                <Column field="tenSP" header="Sản Phẩm"></Column>
                <Column field="tenMauSac" header="Màu Sắc"></Column>
                <Column field="tenSize" header="Size"></Column>
                <Column field="giaBan" header="Giá Bán"></Column>
            </DataTable>
        </div>

        <template #footer>
            <!-- <Button label="Cancel" icon="pi pi-times" class="p-button-text" @click="hideDialog" /> -->
            <Button label="Save" icon="pi pi-check" @click="applyKhuyenMai" />
        </template>
    </Dialog>

    </Dialog>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useLoaiService } from '@/service/Admin/Loai/LoaiService';
import { ProductStore } from '@/service/Admin/product/product.api';
import SelectCTSP from './SelectCTSP.vue';

import { ctspStore } from '@/service/Admin/SanPhamChiTiet/sanPhamCTService';
import { useToast } from 'primevue/usetoast';
import { khuyenMaiStore } from '@/service/Admin/KhuyenMai/KhuyenMaiService.js';

const khuyenmaiService = khuyenMaiStore();
const ctspService = ctspStore();
const productStore = ProductStore();
const loaiStore = useLoaiService();
const products = ref([]);
const  listSPCT = ref([])
// const productService = productStore();
const selectedProductDialog = ref(false);
const dataLoai = ref([]);
const props = defineProps({
    myProp: {}
});

const selectedDialog= ref(false);
onMounted(() => {
    loadProducts();
    loadDataLoai();
    loadDataProduct();
});

// const showProducts = () => {
//     selectedDialog.value = true;
// };

const idsp = ref()
const showProducts = (id) => {
    // localStorage.setItem("idkm",props.myProp.id )
    selectedProductDialog.value = true;
    idsp.value = id;
    cons
};

const showProduct = () => {
    selectedDialog.value = true;
};


const hideDialog = () => {
    selectedProductDialog.value = false;
};

const idkm = props.myProp.id;

const loadProducts = async () => {
    await productStore.fetchAll(); // Gọi hàm fetchAll từ Store
    products.value = productStore.products;

};

const selectedLoai = ref(null);
const loadDataLoai = async () => {
    await loaiStore.fetchDataByStatus(1);
    dataLoai.value = loaiStore.dataByStatus1;
};

// console.log("idsp", idsp.value);

const loadDataProduct = async () => {
    await ctspService.fetchData(products.value.id);
    listSPCT.value = ctspService.data;
    
};


const applyKhuyenMai = () => {
    // đây là số lượng SPCT được chọn để áp dụng khuyến mại
    const sl = selectedProduct.value.length;
    // đây là IdKM dùng để áp dụng được truyền từ component cha
    // const idkm = localStorage.getItem("idkm")

    // duyệt qua mảng danh sách các CTSP được chọn 
    selectedProduct.value.forEach((product) => {
        const productId = product.id;
        // cập nhật lại giá tiền và id khuyến mại
        khuyenmaiService.updateCTSP(productId, idkm);
       
      
      
    });
    selectedProduct.value = [];
    selectedDialog.value = false;
    loadDataProduct(idsp);
    toast.add({ severity: 'success', summary: 'Successful', detail: 'Áp dụng khuyến mại thành công', life: 3000 });
};

// const loadDataProduct = async () => {
//     await khuyenmaiService.getProduct();
//     products.value = khuyenmaiService.data;
// };

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


const getStatusLabel = (soLuong, trangThai) => {
    if (soLuong > 0 && trangThai == 1) {
        return { text: 'Còn Hàng', severity: 'success' };
    } else if (soLuong <= 0 || trangThai == 0) {
        return { text: 'Hết Hàng', severity: 'danger' };
    } else {
        return { text: 'Tồn Kho', severity: 'info' };
    }
};
</script>
