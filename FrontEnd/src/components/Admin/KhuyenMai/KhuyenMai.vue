<script setup>
import { FilterMatchMode } from 'primevue/api';
import { format } from 'date-fns';
import { ref, onMounted, onBeforeMount, computed } from 'vue';

import { khuyenMaiStore } from '@/service/Admin/KhuyenMai/KhuyenMaiService.js';
import { useToast } from 'primevue/usetoast';
import AddKhuyenMai from './AddKhuyenMai.vue';
import UpdateKhuyenMai from './UpdateKhuyenMai.vue';
import DeleteKhuyenMai from './DeleteKhuyenMai.vue';
import ApplyKM from './ApplyKM.vue';
import ProgressSpinner from 'primevue/progressspinner';
import ExcelJS from 'exceljs';

const toast = useToast();
const filters = ref({});
const khuyenmais = ref([]);
const khuyenmai = ref({});
const selectedKhuyenMai = ref([]);
const deleteKhuyenMaiDialog = ref(false);
const selectedStatus = ref(null);
const expandedRows = ref([]);
const khuyenmaiService = khuyenMaiStore();

onBeforeMount(() => {
    initFilters();
});

onMounted(() => {
    loadDataKhuyenmai();
});

const initFilters = () => {
    filters.value = {
        global: { value: null, matchMode: FilterMatchMode.CONTAINS }
    };
};

const loadDataKhuyenmai = async () => {
    await khuyenmaiService.getKhuyenMai();
    khuyenmais.value = khuyenmaiService.data;
  
};

// const initFilters1 = () => {
//     filters.value = {
//         date: { operator: FilterOperator.AND, constraints: [{ value: null, matchMode: FilterMatchMode.DATE_IS }] },
//         trangThai: { operator: FilterOperator.OR, constraints: [{ value: null, matchMode: FilterMatchMode.EQUALS }] }
//     };
// };

const statuses = ref([
    { label: 'Còn hạn', value: 0 },
    { label: 'Hết hạn', value: 1 },
    { label: 'Chưa hoạt động', value: 2 },
    { label: 'Hết khuyến mại', value: 3 }
]);

const columns = ref([
    { field: 'ma', header: 'Mã' },
    { field: 'ten', header: 'Tên' },
   // { field: 'thoiGianBatDau', header: 'Ngày Bắt Đầu' },
    //{ field: 'thoiGianKetThuc', header: 'Ngày Kết Thúc' },
    { field: 'moTa', header: 'Mô Tả' },
    { field: 'giaTriGiam', header: 'Giá Trị (%)' },
    // { field: 'giamToiDa', header: 'Giảm tối đa' },
    // { field: 'ngaySua', header: 'Ngày Sửa' },
    // { field: 'ngayTao', header: 'Ngày Tạo' }
]);

// hàm để tắt/mở cột
const selectedColumns = ref(columns.value);

const onToggle = (val) => {
    selectedColumns.value = columns.value.filter((col) => val.includes(col));
};

const confirmDeleteKhuyenMai = (editKhuyenMai) => {
    khuyenmai.value = editKhuyenMai;
    deleteKhuyenMaiDialog.value = true;
};

const deleteKhuyenMai = () => {
    khuyenmaiService.deleteKhuyenMai(khuyenmai.value, khuyenmai.value.id);
    toast.add({ severity: 'warn', summary: '', detail: 'Khuyến mại đã được xoá', life: 3000 });
    deleteKhuyenMaiDialog.value = false;
};

// dùng để lọc khuyến mại theo trạng thái trên CBB
const filteredVoucher = computed(() => {
    if (!selectedStatus.value) {
        return khuyenmais.value; // Trả về dữ liệu ban đầu nếu không có trạng thái nào được chọn
    }
    return khuyenmais.value.filter((customer) => customer.trangThai === selectedStatus.value.value);
});

const getStatusLabel = (trangThai) => {
    switch (trangThai) {
        case 0:
            return { text: 'Còn hạn', severity: 'success' };

        case 1:
            return { text: 'Hết hạn', severity: 'danger' };

        case 2:
            return { text: 'Chưa hoạt động', severity: 'warning' };

        case 3:
            return { text: 'Hết khuyến mại  ', severity: 'info' };

        default:
            return null;
    }
};

// onBeforeMount(() => {
//     initFilters();
// });

const formatDate = (dateTime) => {
    if (dateTime == null || dateTime.length <= 0) {
        return null;
    } else {
        return format(new Date(dateTime), 'yyyy/MM/dd');
    }
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

const column = ['STT', 'Tên', 'Thời gian bắt đầu', 'Thời gian kết thúc', 'Giảm tối đa', 'Giá trị giảm', 'Mô tả'];

const generateExcel = () => {
    const workbook = new ExcelJS.Workbook();
    const worksheet = workbook.addWorksheet('Sheet 1');

    const columnWidths = [5, 20, 20, 20, 15, 15, 30];
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

    workbook.xlsx.writeBuffer().then((buffer) => {
        const blob = new Blob([buffer], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });
        const url = window.URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = 'Khuyến mại.xlsx';
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
    //  console.log(selectedFile)
    const formData = new FormData();
    formData.append('file', selectedFile);

    try {
        await khuyenmaiService.uploadFile(formData);
        excel.value = khuyenmaiService.excels;
     //   console.log(excel.value)
        let hasError = false;
     //   for (const o of excel.value) {
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
                } else if (data.importMessageKieuGiamGia !== null && data.importMessageKieuGiamGia !== 'SUCCESS') {
                    toast.add({ severity: 'error', summary: 'Error', detail: data.importMessageKieuGiamGia, life: 30000 });
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
            loadDataKhuyenmai();
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
                            <AddKhuyenMai />
                            <DeleteKhuyenMai :selectedKhuyenMai="selectedKhuyenMai" />
                        </div>
                    </template>

                    <template v-slot:end>
                        <Button label="Import excel" icon="pi pi-download" @click="openPosition('top')" style="min-width: 10rem" severity="secondary" rounded />
                    </template>
                </Toolbar>

                <DataTable
                    :value="filteredVoucher"
                    v-model:selection="selectedKhuyenMai"
                    v-model:expandedRows="expandedRows"
                    :columns="visibleColumns"
                    :paginator="true"
                    :rows="5"
                    filterDisplay="menu"
                    paginatorTemplate="FirstPageLink PrevPageLink PageLinks NextPageLink LastPageLink CurrentPageReport RowsPerPageDropdown"
                    :rowsPerPageOptions="[5, 10, 25]"
                    :filters="filters"
                    currentPageReportTemplate="Showing {first} to {last} of {totalRecords} products"
                    responsiveLayout="scroll"
                >
                    <template #header>
                        <div class="flex flex-column md:flex-row md:justify-content-between md:align-items-center">
                            <MultiSelect icon="pi pi-plus" placeholder="Select Columns" :modelValue="selectedColumns" :options="columns" optionLabel="header" @update:modelValue="onToggle" display="tag" />
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

                    <Column v-for="(col, index) of selectedColumns" :field="col.field" :header="col.header" :key="col.field + '_' + index" :sortable="true" headerStyle="width:14%; min-width:10rem;"></Column>

                    <Column header="Ngày Bắt Đầu" filterField="date" dataType="date" style="min-width: 9rem">
                        <template #body="{ data }">
                            {{ formatDate(data.thoiGianBatDau) }}
                        </template>
                       
                    </Column>

                    <Column header="Ngày Kết Thúc" filterField="date" dataType="date" style="min-width: 9rem">
                        <template #body="{ data }">
                            {{ formatDate(data.thoiGianKetThuc) }}
                        </template>
                       
                    </Column>

                    <Column field="trangThai" header="Trạng Thái" sortable style="min-width: 12rem">
                        <template #body="slotProps">
                            <Tag :value="getStatusLabel(slotProps.data.trangThai).text" :severity="getStatusLabel(slotProps.data.trangThai).severity" />
                        </template>
                    </Column>
                    <Column headerStyle="min-width:15rem;">
                        <template #body="slotProps">
                            <UpdateKhuyenMai :my-prop="slotProps.data" />
                            <Button icon="pi pi-trash" class="p-button-rounded p-button-warning mt-2" @click="confirmDeleteKhuyenMai(slotProps.data)" />
                            <ApplyKM :my-prop="slotProps.data" />
                        </template>
                    </Column>
                </DataTable>
                <Dialog v-model:visible="deleteKhuyenMaiDialog" :style="{ width: '450px' }" header="Confirm" :modal="true">
                    <div class="flex align-items-center justify-content-center">
                        <i class="pi pi-exclamation-triangle mr-3" style="font-size: 2rem" />
                        <span v-if="khuyenmai"
                            >Bạn có chắc chắn muốn xoá khuyến mại <b>{{ khuyenmai.ten }}</b
                            >?</span
                        >
                    </div>
                    <template #footer>
                        <Button label="No" icon="pi pi-times" class="p-button-text" @click="deleteKhuyenMaiDialog = false" />
                        <Button label="Yes" icon="pi pi-check" class="p-button-text" @click="deleteKhuyenMai" />
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
@import '@/assets/demo/styles/badges.scss';
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
