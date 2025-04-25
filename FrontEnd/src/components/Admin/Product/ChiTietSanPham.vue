<script setup>
import { FilterMatchMode } from 'primevue/api';
import { ref, onMounted, onBeforeMount, computed, reactive, watch } from 'vue';
import { ProductStore } from '@/service/Admin/product/product.api';
import AddProduct from './addProduct.vue';
import { useToast } from 'primevue/usetoast';
import UpdateProduct from './updateProduct.vue';
import Detail from './DetailProduct.vue';
import ModalSPCT from './modalSPCT.vue';
import ProgressSpinner from 'primevue/progressspinner';
import ExcelJS from 'exceljs';
import { format } from 'date-fns';

const column = ['STT', 'Sản phẩm', 'Vật liệu', 'Trọng lượng', 'Giá bán', 'Màu sắc', 'Size', 'Số lượng', 'Ảnh màu sắc 01 ', 'Ảnh chính', 'Ảnh 1', 'Quai đeo', 'Đệm lót', 'Mô tả sản phẩm', 'Loại sản phẩm', 'Thương hiệu'];

const generateExcel = () => {
    const workbook = new ExcelJS.Workbook();
    const worksheet = workbook.addWorksheet('Sheet 1');

    const columnWidths = [5, 20, 20, 15, 15, 20, 10, 10, 20, 20, 20, 20, 20, 30, 20, 20];
    worksheet.columns = column.map((col, index) => ({
        header: col,
        key: col,
        width: columnWidths[index]
    }));

    const headerRow = worksheet.getRow(1);
    headerRow.eachCell((cell) => {
        cell.fill = {
            type: 'pattern',
            pattern: 'solid',
            fgColor: { argb: 'FFFF00' }
        };
        cell.font = {
            bold: true
        };
    });

    // Tạo và tải file Excel
    workbook.xlsx.writeBuffer().then((buffer) => {
        const blob = new Blob([buffer], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });
        const url = window.URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = 'sanPham.xlsx'; // Tên file Excel khi tải về
        a.click();
        window.URL.revokeObjectURL(url);
    });
};

const productStore = ProductStore();
const toast = useToast();

const productDialog = ref(false);
const deleteProductDialog = ref(false);
const khoiPhucProductDialog = ref(false);
const deleteProductsDialog = ref(false);
const product = ref({});
const selectedProducts = ref(null);
const dt = ref(null);
const filters = ref({});
const submitted = ref(false);
// const selectedColors = ref([]);
const selectedIndices = ref([]);

const myDiv = ref(null);
const div = ref(null);

onBeforeMount(() => {
    initFilters();
});

const products = ref([]);
const showSpinner = ref(false);
const visibledatatable = ref(false);
const loadProducts = async () => {
    showSpinner.value = true;
    visibledatatable.value = false;
    await productStore.fetchAll(); // Gọi hàm fetchAll từ Store
    products.value = productStore.products;
    showSpinner.value = false;
    visibledatatable.value = true;
};

const url = ref([]);
let soLuongSP = ref(null);
onMounted(() => {
    myDiv.value = document.getElementById('right_gh');
    div.value = document.getElementById('table');
    loadProducts();
});

const formatCurrency = (value) => {
    return value.toLocaleString('vi-VN', { style: 'currency', currency: 'VND' });
};

const selectedCheckboxes = ref([]);

const initFilters = () => {
    filters.value = {
        global: { value: null, matchMode: FilterMatchMode.CONTAINS }
    };
};

const columns = ref([
    { field: 'quaiDeo', header: 'Quai đeo' },
    { field: 'demLot', header: 'Đệm lót' },
    { field: 'ngaySua', header: 'Ngày sửa' },
    { field: 'ngayTao', header: 'Ngày tạo' },
    { field: 'moTa', header: 'Mô Tả' }
    // { field: 'soLuongTon', header: 'Số lượng' },
    // { field: 'trongLuong', header: 'Trọng Lượng' },
    // { field: 'giaSauGiam', header: 'Giá giảm giá' },
    // { field: 'tenKM', header: 'Tên Khuyến Mãi' },
    // { field: 'thoiGianBatDau', header: 'Thời gian bắt đầu' },
    // { field: 'thoiGianKetThuc', header: 'Thời gian kết thúc' },
    // { field: 'giaTriGiam', header: 'Giá Trị (%)' },
]);

// hàm để tắt/mở cột
const selectedColumns = ref(columns.value.soLuongTon);

const onToggle = (val) => {
    selectedColumns.value = columns.value.filter((col) => val.includes(col));
};

const getStatusLabel = (soLuong, trangThai) => {
    if (soLuong > 0 && trangThai == 1) {
        return { text: 'Còn Hàng', severity: 'success' };
    } else if (soLuong <= 0 || trangThai == 0) {
        return { text: 'hết Hàng', severity: 'danger' };
    } else {
        return { text: 'tồn kho', severity: 'info' };
    }
};

const idDelete = ref();
const confirmDeleteProduct = (id) => {
    idDelete.value = id;
    deleteProductDialog.value = true;
};

const idKhoiPhuc = ref();
const confirmKhoiPhucProduct = (id) => {
    idKhoiPhuc.value = id;
    khoiPhucProductDialog.value = true;
};

const deleteProduct = (id) => {
    productStore.delete(idDelete.value);
    products.value = productStore.products;
    toast.add({ severity: 'success', summary: 'Thông báo', detail: 'Xoá thành công', life: 3000 });
    deleteProductDialog.value = false;
};

const khoiPhucProduct = (id) => {
    productStore.khoiPhuc(idKhoiPhuc.value);
    products.value = productStore.products;
    toast.add({ severity: 'success', summary: 'Thông báo', detail: 'khôi phục thành công', life: 3000 });
    khoiPhucProductDialog.value = false;
};
const position = ref('center');
const visible = ref(false);
const showProgressSpinner = ref(false);
const dis = ref(true);

const openPosition = (pos) => {
    position.value = pos;
    visible.value = true;
};

const closePosition = () => {
    visible.value = false;
    // loadProducts();
};

const setNameFile = ref('');
const handRemovefile = () => {
    setNameFile.value = '';
};

const excel = ref({});
const handImportExcel = async (event) => {
    showProgressSpinner.value = true;
    dis.value = false;
    const selectedFile = event.target.files[0];
    setNameFile.value = event.target.files[0].name;
    const formData = new FormData();
    formData.append('file', selectedFile);
   try {
        await productStore.uploadFile(formData);
        excel.value = productStore.excels;
        let hasError = false;
      //   console.log(excel.value.responseList)
            for (const data of excel.value.responseList) {
                if (data.importMessageGiaBan !== null && data.importMessageGiaBan !== 'SUCCESS') {
                    toast.add({ severity: 'error', summary: 'Error', detail: data.importMessageGiaBan, life: 30000 });
                    hasError = true;
                    showProgressSpinner.value = false;
                    dis.value = true;
                    break;
                } else if (data.importMessageDemLot !== null && data.importMessageDemLot !== 'SUCCESS') {
                    toast.add({ severity: 'error', summary: 'Error', detail: data.importMessageDemLot, life: 30000 });
                    hasError = true;
                    showProgressSpinner.value = false;
                    dis.value = true;
                    break;
                } else if (data.importMessageSanPham !== null && data.importMessageSanPham !== 'SUCCESS') {
                    toast.add({ severity: 'error', summary: 'Error', detail: data.importMessageSanPham, life: 30000 });
                    hasError = true;
                    showProgressSpinner.value = false;
                    dis.value = true;
                    break;
                } else if (data.importMessageTrongLuong !== null && data.importMessageTrongLuong !== 'SUCCESS') {
                    toast.add({ severity: 'error', summary: 'Error', detail: data.importMessageTrongLuong, life: 30000 });
                    hasError = true;
                    showProgressSpinner.value = false;
                    dis.value = true;
                    break;
                } else if (data.importMessageVatLieu !== null && data.importMessageVatLieu !== 'SUCCESS') {
                    toast.add({ severity: 'error', summary: 'Error', detail: data.importMessageVatLieu, life: 30000 });
                    hasError = true;
                    showProgressSpinner.value = false;
                    dis.value = true;
                    break;
                } else if (data.importMessageThuongHieu !== null && data.importMessageThuongHieu !== 'SUCCESS') {
                    toast.add({ severity: 'error', summary: 'Error', detail: data.importMessageThuongHieu, life: 30000 });
                    hasError = true;
                    showProgressSpinner.value = false;
                    dis.value = true;
                    break;
                } else if (data.importMessageSize !== null && data.importMessageSize !== 'SUCCESS') {
                    toast.add({ severity: 'error', summary: 'Error', detail: data.importMessageSize, life: 30000 });
                    hasError = true;
                    showProgressSpinner.value = false;
                    dis.value = true;
                    break;
                } else if (data.importMessageMauSac !== null && data.importMessageMauSac !== 'SUCCESS') {
                    toast.add({ severity: 'error', summary: 'Error', detail: data.importMessageMauSac, life: 30000 });
                    hasError = true;
                    showProgressSpinner.value = false;
                    dis.value = true;
                    break;
                } else if (data.importMessageLoai !== null && data.importMessageLoai !== 'SUCCESS') {
                    toast.add({ severity: 'error', summary: 'Error', detail: data.importMessageLoai, life: 30000 });
                    hasError = true;
                    showProgressSpinner.value = false;
                    dis.value = true;
                    break;
                } else if (data.importMessageImageMau !== null && data.importMessageImageMau !== 'SUCCESS') {
                    toast.add({ severity: 'error', summary: 'Error', detail: data.importMessageImageMau, life: 30000 });
                    hasError = true;
                    showProgressSpinner.value = false;
                    dis.value = true;
                    break;
                } else if (data.importMessageQuaiDeo !== null && data.importMessageQuaiDeo !== 'SUCCESS') {
                    toast.add({ severity: 'error', summary: 'Error', detail: data.importMessageQuaiDeo, life: 30000 });
                    hasError = true;
                    showProgressSpinner.value = false;
                    dis.value = true;
                    break;
                } else if (data.importMessageSoLuongSize !== null && data.importMessageSoLuongSize !== 'SUCCESS') {
                    toast.add({ severity: 'error', summary: 'Error', detail: data.importMessageSoLuongSize, life: 30000 });
                    hasError = true;
                    showProgressSpinner.value = false;
                    dis.value = true;
                    break;
                } else if (data.importMessageSoLuongMau !== null && data.importMessageSoLuongMau !== 'SUCCESS') {
                    toast.add({ severity: 'error', summary: 'Error', detail: data.importMessageSoLuongMau, life: 30000 });
                    hasError = true;
                    showProgressSpinner.value = false;
                    dis.value = true;
                    break;
                }
                 if (hasError) {
                break;
                 }
            }
           
        if (!hasError) {
            showProgressSpinner.value = false;
            dis.value = true;
            toast.add({ severity: 'success', summary: 'Success Message', detail: 'Import thành công', life: 3000 });
            loadProducts();
        }
    } catch (error) {
        toast.add({ severity: 'error', summary: 'Error', detail: 'lỗi ', life: 10000 });
        showProgressSpinner.value = false;
        dis.value = true;
    }
};
const trangThai = ref();
const dataTrangThai = ref([
    { label: 'Tất cả', value: 'tatCa' },
    { label: 'Còn Hàng', value: 'conHang' },
    { label: 'hết hàng', value: 'hetHang' },
    { label: 'Tồn kho', value: 'tonKho' },
    { label: 'Đang khuyến mại', value: 'dangKhuyenMai' }
]);

const loadDataByTrangThai = async () => {
    showSpinner.value = true;
    visibledatatable.value = false;
    await productStore.fetchDataByStatus(trangThai.value.value);
    products.value = productStore.products;
    const productList = productStore.products; // Lấy dữ liệu từ Store và gán vào biến products
    products.value = productList;

    showSpinner.value = false;
    visibledatatable.value = true;
};

watch(trangThai, (newVal) => {
    if (trangThai.value.value != 'tatCa') {
        loadDataByTrangThai();
    } else {
        loadProducts();
    }
});

const formatDate = (dateTime) => {
    if (dateTime == null || dateTime.length <= 0) {
        return null;
    } else {
        return format(new Date(dateTime), 'yyyy/MM/dd HH:mm:ss');
    }
};
</script>

<template>
    <div class="grid">
        <div class="col-12">
            <div class="card">
                <Toast />
                <section class="talbe" id="table">
                    <Toolbar class="mb-4">
                        <template v-slot:start>
                            <div class="my-2">
                                <AddProduct />
                            </div>
                        </template>

                        <template v-slot:end>
                            <Button label="Import excel" icon="pi pi-download" @click="openPosition('top')"
                                style="min-width: 10rem" severity="secondary" rounded />
                        </template>
                    </Toolbar>
                    <div style="margin-left: 500px">
                        <ProgressSpinner v-if="showSpinner" />
                    </div>
                    <DataTable ref="dt" :value="products" v-model:selection="selectedProducts" dataKey="id"
                        v-if="visibledatatable" :paginator="true" :rows="5" :filters="filters"
                        paginatorTemplate="FirstPageLink PrevPageLink PageLinks NextPageLink LastPageLink CurrentPageReport RowsPerPageDropdown"
                        :rowsPerPageOptions="[5, 10, 25]"
                        currentPageReportTemplate="Showing {first} to {last} of {totalRecords} products"
                        responsiveLayout="scroll" showGridlines>
                        <template #header>
                            <div class="flex flex-column md:flex-row md:justify-content-between md:align-items-center">
                                <div class="flex flex-column md:flex-row md:justify-content-between md:align-items-center">
                                    <MultiSelect icon="pi pi-plus" :modelValue="selectedColumns" :options="columns"
                                        optionLabel="header" @update:modelValue="onToggle" display="chip"
                                        placeholder="Select Columns" />
                                </div>
                                <div style="display: flex">
                                    <h5 class="m-0" style="margin-right: 20px">Sản Phẩm</h5>
                                </div>

                                <span class="block mt-2 md:mt-0 p-input-icon-left">
                                    <i class="pi pi-search" />
                                    <InputText v-model="filters['global'].value" placeholder="Search..." />
                                    <Dropdown v-model="trangThai" :options="dataTrangThai" optionLabel="label"
                                        placeholder="Tất cả" class="w-full md:w-14rem" style="margin-left: 20px" />
                                </span>
                            </div>
                        </template>
                        <template #empty>
                            <div class="flex flex-column justify-content-center align-items-center" style="height: 300px">
                                <svg width="100px" height="100px" viewBox="0 0 16 16" xmlns="http://www.w3.org/2000/svg"
                                    fill="#000000" class="bi bi-file-earmark-x">
                                    <path
                                        d="M6.854 7.146a.5.5 0 1 0-.708.708L7.293 9l-1.147 1.146a.5.5 0 0 0 .708.708L8 9.707l1.146 1.147a.5.5 0 0 0 .708-.708L8.707 9l1.147-1.146a.5.5 0 0 0-.708-.708L8 8.293 6.854 7.146z" />
                                    <path
                                        d="M14 14V4.5L9.5 0H4a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h8a2 2 0 0 0 2-2zM9.5 3A1.5 1.5 0 0 0 11 4.5h2V14a1 1 0 0 1-1 1H4a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1h5.5v2z" />
                                </svg>
                                <h6>Không có dữ liệu.</h6>
                            </div>
                        </template>
                        <Column field="code" header="STT" :sortable="true" style="width: 1px; padding: 5px">
                            <template #body="slotProps">
                                <span class="p-column-title">STT</span>
                                {{ products.indexOf(slotProps.data) + 1 }}
                            </template>
                        </Column>
                        <Column field="ma" header="Mã" :sortable="true" headerStyle="width:5%; min-width:5rem;">
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
                        <Column field="thuongHieu" header="Thương Hiệu" :sortable="true"
                            headerStyle="width:10%; min-width:5rem;">
                            <template #body="slotProps">
                                <span class="p-column-title">Tên</span>
                                {{ slotProps.data.thuongHieu }}
                            </template>
                        </Column>
                        <Column field="vatLieu" header="Vật Liệu" :sortable="true" headerStyle="width:8%; min-width:5rem;">
                            <template #body="slotProps">
                                <span class="p-column-title">Tên</span>
                                {{ slotProps.data.vatLieu }}
                            </template>
                        </Column>
                        <Column field="soLuongTon" header="Số Lượng tồn" :sortable="true"
                            headerStyle="width:10%; min-width:5rem;">
                            <template #body="slotProps">
                                <span class="p-column-title">Tên</span>
                                {{ slotProps.data.soLuongTon }}
                            </template>
                        </Column>
                        <Column v-for="(col, index) of selectedColumns" :field="col.field" :header="col.header"
                            :key="col.field + '_' + index" :sortable="true" headerStyle="width:8%; min-width:5rem;">
                            {{ ['ngayTao', 'ngaySua'].includes(col.field) ? formatDate(slotProps.data[col.field]) :
                                slotProps.data[col.field] }}
                        </Column>

                        <Column field="trangThai" header="Trạng Thái" sortable headerStyle="width: 10%; min-width: 8rem;">
                            <template #body="slotProps">
                                <Tag :value="getStatusLabel(slotProps.data.soLuongTon, slotProps.data.trangThai).text"
                                    :severity="getStatusLabel(slotProps.data.soLuongTon, slotProps.data.trangThai).severity" />
                            </template>
                        </Column>

                        <Column header="Action" headerStyle="min-width:10rem;">
                            <template #body="slotProps">
                                <Detail :my-prop="slotProps.data"></Detail>
                                <UpdateProduct :my-prop="slotProps.data"></UpdateProduct>
                                <ModalSPCT :my-prop="slotProps.data"></ModalSPCT>
                                <Button icon="pi pi-trash" class="p-button-rounded p-button-warning mt-2"
                                    @click="confirmDeleteProduct(slotProps.data.id)"
                                    v-if="slotProps.data.trangThai != 0 && slotProps.data.soLuongTon > 0" />
                                <Button icon="pi pi-refresh" class="p-button-rounded p-button-warning mt-2"
                                    @click="confirmKhoiPhucProduct(slotProps.data.id)"
                                    v-if="slotProps.data.trangThai == 0 && slotProps.data.soLuongTon > 0" />
                            </template>
                        </Column>
                    </DataTable>

                    <Dialog v-model:visible="khoiPhucProductDialog" :style="{ width: '450px' }" header="Khôi phục Sản phẩm"
                        :modal="true">
                        <div class="flex align-items-center justify-content-center">
                            <i class="pi pi-exclamation-triangle mr-3" style="font-size: 2rem" />
                            <span v-if="product">Bạn có chắc chắn khôi phục <b>{{ product.ten }}</b> không ?</span>
                        </div>
                        <template #footer>
                            <Button label="Không" icon="pi pi-times" class="p-button-text"
                                @click="deleteProductDialog = false" />
                            <Button label="Có" icon="pi pi-check" class="p-button-text"
                                @click="khoiPhucProduct(product.id)" />
                        </template>
                    </Dialog>

                    <Dialog v-model:visible="deleteProductDialog" :style="{ width: '450px' }" header="Xóa Sản phẩm"
                        :modal="true">
                        <div class="flex align-items-center justify-content-center">
                            <i class="pi pi-exclamation-triangle mr-3" style="font-size: 2rem" />
                            <span v-if="product">Bạn có chắc chắn muốn xoá sản phẩm <b>{{ product.ten }}</b> không ?</span>
                        </div>
                        <template #footer>
                            <Button label="Không" icon="pi pi-times" class="p-button-text"
                                @click="deleteProductDialog = false" />
                            <Button label="Có" icon="pi pi-check" class="p-button-text"
                                @click="deleteProduct(product.id)" />
                        </template>
                    </Dialog>

                    <Dialog v-model:visible="visible" header="Import excel" :style="{ width: '400px' }" :position="position"
                        :modal="true" :draggable="false">
                        <div class="flex align-items-center justify-content-center">
                            <div v-if="dis">
                                <div class="custom-file-upload">
                                    <label class="upload-button">{{ setNameFile == '' ? 'Tải lên tệp Excel' : setNameFile
                                    }}<input type="file" name="excelFile" accept=".xls, .xlsx"
                                            @change="handImportExcel($event)" /></label>
                                </div>
                                <Button icon="pi pi-trash" class="p-button-warning mr-2" @click="handRemovefile()"
                                    style="width: 35px; height: 35px; margin: 0px 10px 10px 10px" />
                            </div>

                            <ProgressSpinner v-if="showProgressSpinner" />
                        </div>

                        <template #footer>
                            <Button label="Export" icon="pi pi-upload" class="p-button" @click="generateExcel($event)"
                                rounded style="height: 40px; margin-right: 150px" severity="secondary" />
                            <Button label="Đóng" icon="pi pi-check" class="p-button" @click="closePosition()"
                                severity="secondary" rounded style="height: 40px" />
                        </template>
                    </Dialog>
                </section>
                <section class="right_gh" id="right_gh" style="display: none"></section>
            </div>
        </div>
    </div>
</template>

<style scoped lang="scss">
@import '@/assets/demo/styles/badges.scss';

.product-container {
    display: grid;
    grid-template-columns: repeat(3, 1fr);
    grid-gap: 20px;
}

.product-item {
    margin-top: 0px;
}

.product-name {
    margin-right: 20px;
    margin-left: 5px;
    margin-bottom: 1px;
}

.image-container {
    display: flex;
    flex-wrap: wrap;
}

.image-item {
    margin-bottom: 30px;
    margin-left: 10px;
    display: flex;
}

/* Tạo kiểu cho vùng chọn tệp và nút tải lên */
.custom-file-upload {
    position: relative;
    overflow: hidden;
    display: inline-block;
    border: 2px solid rgb(57, 68, 224);
    border-radius: 10px;
    width: 150px;
    height: 50px;
    text-align: center;
}

/* Tạo kiểu cho nút tải lên */
.upload-button {
    background-color: white;
    color: #fff;
    padding: 10px 20px;
    border: none;
    cursor: pointer;
    color: black;
    font-weight: 600;
    margin-top: 5px;
}

/* Tạo kiểu cho thẻ input */
.upload-button input[type='file'] {
    position: absolute;
    top: 0;
    left: 0;
    cursor: pointer;
    opacity: 0;
    z-index: 1;
    width: 100%;
    height: 100%;
}</style>
