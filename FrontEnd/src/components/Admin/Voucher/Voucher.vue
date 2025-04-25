<script setup>
import { FilterMatchMode } from 'primevue/api';
import { ref, onMounted, computed, onBeforeMount } from 'vue';
import { useToast } from 'primevue/usetoast';
import { voucherStore } from '@/service/Admin/Voucher/VoucherService.js';
import AddVoucher from './AddVoucher.vue';
import UpdateVoucher from './UpdateVoucher.vue';
import DeleteVoucher from './DeleteVoucher.vue';
import { format } from 'date-fns';
import ApplyVoucher from './ApplyVoucher.vue';
import detailVoucher from './DetailVoucher.vue';
import ProgressSpinner from 'primevue/progressspinner';
import ExcelJS from 'exceljs';

const toast = useToast();
const vouchers = ref([]);
const deleteVoucherDialog = ref(false);
const selectedStatus = ref(null);
const VoucherService = voucherStore();

const voucher = ref({
    ten: '',
    thoiGianBatDau: '',
    thoiGianKetThuc: '',
    moTa: '',
    giamToiDa: 0,
    trangThai: 0
});

const selectedVoucher = ref([]);
const dt = ref(null);
const filters = ref({});
const statuses = ref([
    { label: 'Còn hạn', value: 0 },
    { label: 'Hết hạn', value: 1 },
    { label: 'Hết voucher', value: 2 },
    { label: 'Chưa bắt đầu', value: 3 }
]);

onBeforeMount(() => {
    initFilters();
});

onMounted(() => {
    loadDatavoucher();
});

const loadDatavoucher = async () => {
    await VoucherService.getVoucher();
    vouchers.value = VoucherService.data;
};

const initFilters = () => {
    filters.value = {
        global: { value: null, matchMode: FilterMatchMode.CONTAINS }
    };
};

const confirmDeleteVoucher = (editProduct) => {
    voucher.value = editProduct;
    deleteVoucherDialog.value = true;
};

const deleteVoucher = () => {
    VoucherService.deleteVoucher(voucher.value, voucher.value.id);
    toast.add({ severity: 'warn', summary: '', detail: 'Xoá thành công', life: 3000 });
    deleteVoucherDialog.value = false;
};

const exportCSV = () => {
    dt.value.exportCSV();
};

//hàm hiển thị trạng thái
const getStatusLabel = (trangThai) => {
    switch (trangThai) {
        case 0:
            return { text: 'Còn hạn', severity: 'success' };

        case 1:
            return { text: 'Hết hạn', severity: 'danger' };

        case 2:
            return { text: 'Hết voucher', severity: 'warning' };

        case 3:
            return { text: 'Chưa bắt đầu', severity: 'secondary' };

        case 4:
            return { text: 'Đã xoá', severity: 'info' };

        default:
            return null;
    }
};

const filteredVoucher = computed(() => {
    if (!selectedStatus.value) {
        return vouchers.value; // Trả về dữ liệu ban đầu nếu không có trạng thái nào được chọn
    }
    return vouchers.value.filter((customer) => customer.trangThai === selectedStatus.value.value);
});

const formatDate = (dateTime) => {
    return format(new Date(dateTime), 'yyyy/MM/dd HH:mm:ss');
};

const formatCurrency = (value) => {
    return parseInt(value).toLocaleString('vi-VN', { style: 'currency', currency: 'VND' });
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

const column = ['STT', 'Tên', 'Thời gian bắt đầu', 'Thời gian kết thúc', 'Giảm tối đa', 'Giá trị giảm(%)', 'Số lượng', 'Mô tả'];

const generateExcel = () => {
    const workbook = new ExcelJS.Workbook();
    const worksheet = workbook.addWorksheet('Sheet 1');

    const columnWidths = [5, 20, 20, 20, 15, 15, 10, 10];
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
        a.download = 'Voucher.xlsx'; // Tên file Excel khi tải về
        a.click();
        window.URL.revokeObjectURL(url);
    });
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
        await VoucherService.uploadFile(formData);
        excel.value = VoucherService.excels;
        console.log(excel.value);
        let hasError = false;
      //  for (const o of excel.value) {
            for (const data of excel.value.responseList) {
                if (data.importMessageTen !== null && data.importMessageTen !== 'SUCCESS') {
                    toast.add({ severity: 'error', summary: 'Error', detail: data.importMessageTen, life: 30000 });
                    hasError = true;
                    showProgressSpinner.value = false;
                    dis.value = true;
                    break;
                } else if (data.importMessageThoiGianBatDau !== null && data.importMessageThoiGianBatDau !== 'SUCCESS') {
                    toast.add({ severity: 'error', summary: 'Error', detail: data.importMessageThoiGianBatDau, life: 30000 });
                    hasError = true;
                    showProgressSpinner.value = false;
                    dis.value = true;
                    break;
                } else if (data.importMessageThoiGianKetThuc !== null && data.importMessageThoiGianKetThuc !== 'SUCCESS') {
                    toast.add({ severity: 'error', summary: 'Error', detail: data.importMessageThoiGianKetThuc, life: 30000 });
                    hasError = true;
                    showProgressSpinner.value = false;
                    dis.value = true;
                    break;
                } else if (data.importMessageMoTa !== null && data.importMessageMoTa !== 'SUCCESS') {
                    toast.add({ severity: 'error', summary: 'Error', detail: data.importMessageMoTa, life: 30000 });
                    hasError = true;
                    showProgressSpinner.value = false;
                    dis.value = true;
                    break;
                } else if (data.importMessageGiamToiDa !== null && data.importMessageGiamToiDa !== 'SUCCESS') {
                    toast.add({ severity: 'error', summary: 'Error', detail: data.importMessageGiamToiDa, life: 30000 });
                    hasError = true;
                    showProgressSpinner.value = false;
                    dis.value = true;
                    break;
                } else if (data.importMessageGiaTriGiam !== null && data.importMessageGiaTriGiam !== 'SUCCESS') {
                    toast.add({ severity: 'error', summary: 'Error', detail: data.importMessageGiaTriGiam, life: 30000 });
                    hasError = true;
                    showProgressSpinner.value = false;
                    dis.value = true;
                    break;
                } else if (data.importMessageSoLuong !== null && data.importMessageSoLuong !== 'SUCCESS') {
                    toast.add({ severity: 'error', summary: 'Error', detail: data.importMessageSoLuong, life: 30000 });
                    hasError = true;
                    showProgressSpinner.value = false;
                    dis.value = true;
                    break;
                }
                if (hasError) {
                break;
            }
            }
          
      //  }
        if (!hasError) {
            showProgressSpinner.value = false;
            dis.value = true;
            toast.add({ severity: 'success', summary: 'Success Message', detail: 'Import thành công', life: 3000 });
            loadDatavoucher();
        }
    } catch (error) {
        toast.add({ severity: 'error', summary: 'Error', detail: 'lỗi ', life: 10000 });
        showProgressSpinner.value = false;
        dis.value = true;
    }
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
                            <AddVoucher />
                            <DeleteVoucher :selectedVoucher="selectedVoucher" />
                        </div>
                    </template>

                    <template v-slot:end>
                        <Button label="Import excel" icon="pi pi-download" @click="openPosition('top')" style="min-width: 10rem" severity="secondary" rounded />
                    </template>
                </Toolbar>

                <DataTable
                    ref="dt"
                    :value="filteredVoucher"
                    v-model:selection="selectedVoucher"
                    dataKey="id"
                    :loading="loading"
                    :paginator="true"
                    :rows="5"
                    :filters="filters"
                    paginatorTemplate="FirstPageLink PrevPageLink PageLinks NextPageLink LastPageLink CurrentPageReport RowsPerPageDropdown"
                    :rowsPerPageOptions="[5, 10, 25]"
                    currentPageReportTemplate="Showing {first} to {last} of {totalRecords} products"
                    responsiveLayout="scroll"
                >
                    <template #header>
                        <div class="flex flex-column md:flex-row md:justify-content-between md:align-items-center">
                            <h5 class="m-0">Quản lý voucher</h5>

                            <Dropdown v-model="selectedStatus" :options="statuses" :optionLabel="(option) => option.label" placeholder="Trạng thái" class="p-column-filter" style="min-width: 12rem" :showClear="true">
                                <template #option="slotProps">
                                    <Tag :value="getStatusLabel(slotProps.option.value).text" :severity="getStatusLabel(slotProps.option.value).severity" />
                                </template>
                            </Dropdown>

                            <span class="block mt-2 md:mt-0 p-input-icon-left">
                                <i class="pi pi-search" />
                                <InputText v-model="filters['global'].value" placeholder="Search..." />
                            </span>
                        </div>
                    </template>

                    <Column selectionMode="multiple" headerStyle="width: 3rem"></Column>
                    <Column field="ten" header="Tên" :sortable="true" headerStyle="width:14%; min-width:8rem;">
                        <template #body="slotProps">
                            <span class="p-column-title">Tên</span>
                            {{ slotProps.data.ten }}
                        </template>
                    </Column>
                    <Column header="Ngày Bắt Đầu" filterField="date" dataType="date" style="min-width: 9rem">
                        <template #body="{ data }">
                            {{ formatDate(data.thoiGianBatDau) }}
                        </template>
                        <template #filter="{ filterModel }">
                            <Calendar v-model="filterModel.value" dateFormat="mm/dd/yy" placeholder="mm/dd/yyyy" />
                        </template>
                    </Column>

                    <Column header="Ngày Kết Thúc" filterField="date" dataType="date" style="min-width: 9rem">
                        <template #body="{ data }">
                            {{ formatDate(data.thoiGianKetThuc) }}
                        </template>
                        <template #filter="{ filterModel }">
                            <Calendar v-model="filterModel.value" dateFormat="mm/dd/yy" placeholder="mm/dd/yyyy" />
                        </template>
                    </Column>

                    <Column field="giaTriGiam" header="Giá trị giảm(%)" :sortable="true" headerStyle="width:14%; min-width:8rem;">
                        <template #body="slotProps">
                            <span class="p-column-title">Giá trị giảm</span>
                            {{ slotProps.data.giaTriGiam }}
                        </template>
                    </Column>

                    <Column field="giamToiDa" header="Giảm Tối Đa" :sortable="true" headerStyle="width:14%; min-width:8rem;">
                        <template #body="slotProps">
                            <span class="p-column-title">Giảm Tối Đa</span>
                            {{ formatCurrency(slotProps.data.giamToiDa) }}
                        </template>
                    </Column>

                    <Column field="soLuong" header="Số Lượng" :sortable="true" headerStyle="width:14%; min-width:6rem;">
                        <template #body="slotProps">
                            <span class="p-column-title">Số Lượng</span>
                            {{ slotProps.data.soLuong }}
                        </template>
                    </Column>

                    <Column field="trangThai" header="Trạng Thái" :showFilterMenu="false" :sortable="true" headerStyle="width:14%; min-width:10rem;">
                        <template #body="slotProps">
                            <Tag :value="getStatusLabel(slotProps.data.trangThai).text" :severity="getStatusLabel(slotProps.data.trangThai).severity" />
                            <!-- <span :class="'product-badge status-'">{{ slotProps.data.trangThai }}</span> -->
                        </template>
                    </Column>
                    <!-- 
                    <Column field="moTa" header="Mô Tả" :sortable="true" headerStyle="width:14%; min-width:8rem;">
                        <template #body="slotProps">
                            <span class="p-column-title">Mô Tả</span>
                            {{ slotProps.data.moTa }}
                        </template>
                    </Column> -->

                    <Column headerStyle="min-width:10rem;">
                        <template #body="slotProps">
                            <UpdateVoucher :my-prop="slotProps.data" />
                            <Button icon="pi pi-trash" class="p-button-rounded p-button-warning mt-2" @click="confirmDeleteVoucher(slotProps.data)" />
                            <!-- <ApplyVoucher :my-prop="slotProps.data" /> -->
                            <detailVoucher :my-prop="slotProps.data" />
                        </template>
                    </Column>
                </DataTable>

                <Dialog v-model:visible="deleteVoucherDialog" :style="{ width: '450px' }" header="Confirm" :modal="true">
                    <div class="flex align-items-center justify-content-center">
                        <i class="pi pi-exclamation-triangle mr-3" style="font-size: 2rem" />

                        <span v-if="voucher">Bạn có chắc chắn muốn xoá voucher <b>{{ voucher.ten }}</b>?</span>

                    </div>
                    <template #footer>
                        <Button label="Huỷ" icon="pi pi-times" class="p-button-text" @click="deleteVoucherDialog = false" />
                        <Button label="Xoá" icon="pi pi-check" class="p-button-text" @click="deleteVoucher" />
                    </template>
                </Dialog>
            </div>
        </div>
    </div>
    <Dialog v-model:visible="visible" header="Import excel" :style="{ width: '400px' }" :position="position" :modal="true" :draggable="false">
        <div class="flex align-items-center justify-content-center">
            <div v-if="dis">
                <div class="custom-file-upload">
                    <label class="upload-button">{{ setNameFile == '' ? 'Tải lên tệp Excel' : setNameFile }}<input type="file" name="excelFile" accept=".xls, .xlsx" @change="handImportExcel($event)" /></label>
                </div>
                <Button icon="pi pi-trash" class="p-button-warning mr-2" @click="handRemovefile()" style="width: 35px; height: 35px; margin: 0px 10px 10px 10px" />
            </div>

            <ProgressSpinner v-if="showProgressSpinner" />
        </div>

        <template #footer>
            <Button label="Export" icon="pi pi-upload" class="p-button" @click="generateExcel($event)" rounded style="height: 40px; margin-right: 150px" severity="secondary" />
            <Button label="Đóng" icon="pi pi-check" class="p-button" @click="closePosition()" severity="secondary" rounded style="height: 40px" />
        </template>
    </Dialog>
</template>

<style scoped lang="scss">
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
}
</style>
<!-- <style scoped lang="scss">
@import '@/assets/demo/styles/badges.scss';
</style> -->
