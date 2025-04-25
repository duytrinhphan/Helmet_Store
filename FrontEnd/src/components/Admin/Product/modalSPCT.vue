
<script setup>
import { useConfirm } from "primevue/useconfirm";
import { useToast } from 'primevue/usetoast';
import AddSanPhamChiTiet from './addChiTietSanPham.vue';
import UpdateSanPhamChiTiet from './UpdateChiTietSanPham.vue';
import { ProductStore } from '@/service/Admin/product/product.api';
import { reactive, ref, computed, onMounted, onBeforeMount } from 'vue';
import { FilterMatchMode } from 'primevue/api';
import AddImage from '@/components/Admin/Image/AddImage.vue';
import UpdateImage from '@/components/Admin/Image/UpdateImage.vue';
import { format } from 'date-fns';
const props = defineProps({
    myProp: {}
});

const productStore = ProductStore();
const confirm = useConfirm();
const toast = useToast();
const productDialog = ref(false);
const lstChiTietSP = ref([]);
const idProduct = ref(null);
const arrayImage = ref([]);
const ImagesProduct = ref([]);
const editProduct = () => {
    idProduct.value = props.myProp.id;
    // ảnh của sản phẩm
    arrayImage.value = props.myProp.img;
    for (const img of arrayImage.value) {
        ImagesProduct.value.push(img.anh);
    }

    const list = props.myProp.sanPhamChiTiet;
    list.sort((a, b) => b.id - a.id);
    lstChiTietSP.value = list;
    //  product.value = { ...editProduct };

    productDialog.value = true;
};

const getStatusLabel = (soLuong) => {
    if (soLuong == 0) {
        return { text: 'hết Hàng', severity: 'danger' };
    } else if (soLuong == 1) {
        return { text: 'Còn hàng', severity: 'success' };
    } else {
        return { text: 'Tồn kho', severity: 'war' };
    }

};

const formatCurrency = (value) => {
    if (value == null || value.length <= 0) {
        return null;
    } else {
        return parseInt(value).toLocaleString('vi-VN', { style: 'currency', currency: 'VND' });
    }
};

const loadDataTrangThai = () => {
    lstChiTietSP.value = props.myProp.sanPhamChiTiet;
    if (trangThaiselect.value.value == -1) {
        lstChiTietSP.value = props.myProp.sanPhamChiTiet;
        return lstChiTietSP.value;
    } else if (trangThaiselect.value.value == 0) {
        lstChiTietSP.value = lstChiTietSP.value.filter(item => item.trangThai == 0);
        return lstChiTietSP.value;
    } else if (trangThaiselect.value.value == 1) {
        lstChiTietSP.value = lstChiTietSP.value.filter(item => item.trangThai == 1);
        return lstChiTietSP.value;
    } else if (trangThaiselect.value.value == 3) {
        lstChiTietSP.value = lstChiTietSP.value.filter(item => item.trangThai == 3);
        return lstChiTietSP.value;
    } else {
        lstChiTietSP.value = lstChiTietSP.value.filter(item => item.tenKM != null);
        return lstChiTietSP.value;
    }
}

const updateMyProp = (newData) => {
    const index = lstChiTietSP.value.findIndex(element => element.id === newData.id);
    if (index !== -1) {
        lstChiTietSP.value[index] = newData;
    }
}

const addMyProp = (newData) => {
    lstChiTietSP.value.push(newData);
}


onBeforeMount(() => {
    initFilters();
});

const filters = ref({});
const initFilters = () => {
    filters.value = {
        global: { value: null, matchMode: FilterMatchMode.CONTAINS }
    };
};

const trangThaiselect = ref(null);
const dataTrangThai = ref([
    { label: 'Tất cả', value: -1 },
    { label: 'Hết hàng', value: 0 },
    { label: 'Còn hàng', value: 1 },
    { label: 'khuyễn mại', value: 2 },
    { label: 'Tồn kho', value: 3 },
]);



const confirm2 = (event, id) => {
    confirm.require({
        target: event.currentTarget,
        message: 'Bạn có chắc muốn xóa không ?',
        icon: 'pi pi-info-circle',
        acceptClass: 'p-button-danger p-button-sm',
        accept: () => {
            deleteSPCT(id);
        },
        reject: () => {

        }
    });
};
const getStatusLabelKhuyenMai = (khuyenMai) => {
    return { text: 'Khuyễn Mại', severity: 'warn' };
};

const columns = ref([
    { field: 'giaSauGiam', header: 'Giá giảm giá' },
    { field: 'tenKM', header: 'Tên Khuyến Mãi' },
    { field: 'thoiGianBatDau', header: 'Thời gian bắt đầu' },
    { field: 'thoiGianKetThuc', header: 'Thời gian kết thúc' },
    { field: 'giaTriGiam', header: 'Giá Trị (%)' },
]);

// hàm để tắt/mở cột
const selectedColumns = ref(columns.value.soLuongTon);

const onToggle = (val) => {
    selectedColumns.value = columns.value.filter(col => val.includes(col));
};
const confirm3 = (event, id, soLuong) => {
    confirm.require({
        target: event.currentTarget,
        message: 'Bạn có chắc khôi lại không ?',
        icon: 'pi pi-info-circle',
        acceptClass: 'p-button-danger p-button-sm',
        accept: () => {
            khoiPhucSPCT(id, soLuong)
        },
        reject: () => {

        }
    });
};

const deleteSPCT = async (idSPCT) => {
    try {
        const repo = await productStore.deleteSPCT(idSPCT);
        const index = lstChiTietSP.value.findIndex(element => element.id === idSPCT);
        if (index !== -1) {
            lstChiTietSP.value[index] = repo;
        }
        toast.add({ severity: 'info', summary: 'Delete', detail: 'xóa thành công', life: 3000 });
    } catch (error) {
        console.error('Lỗi xóa màu sắc:', error);
    }
};

const khoiPhucSPCT = async (idSPCT, soLuong) => {
    try {
        if (soLuong <= 0) {
            toast.add({ severity: 'info', summary: 'Confirmed', detail: 'bạn không thể khôi phục vì số lượng <= 0', life: 3000 });
            return;
        }
        const repo = await productStore.khoiPhucSPCT(idSPCT);
        const index = lstChiTietSP.value.findIndex(element => element.id === idSPCT);
        if (index !== -1) {
            lstChiTietSP.value[index] = repo;
        }
        toast.add({ severity: 'info', summary: 'Khôi phục', detail: 'Khôi phục thành công', life: 3000 });
    } catch (error) {
        console.error('Lỗi xóa màu sắc:', error);
    }
};


const addimgMyProp = (newData) => {
    arrayImage.value.unshift(newData);
    // console.log(arrayImage.value)
}

const updateimgMyProp = (newData) => {
    const index = arrayImage.value.findIndex(element => element.id === newData.id);
    if (index !== -1) {
        arrayImage.value[index] = newData;
    }
}


const confirm1 = (event, id) => {
    confirm.require({
        target: event.currentTarget,
        message: 'Bạn có chắc muốn xóa không ?',
        icon: 'pi pi-info-circle',
        acceptClass: 'p-button-danger p-button-sm',
        accept: () => {
            deleteImg(id);
        },
        reject: () => {

        }
    });
};

const deleteImg = async (id) => {
    try {
        await productStore.deleteImg(id);
        arrayImage.value = arrayImage.value.filter(element => element.id !== id);
        toast.add({ severity: 'info', summary: 'Delete', detail: 'xóa thành công', life: 3000 });
    } catch (error) {
        console.error('Lỗi xóa màu sắc:', error);
    }
};

const formatDate = (dateTime) => {
    if (dateTime == null || dateTime.length <= 0) {
        return null;
    } else {
        return format(new Date(dateTime), 'yyyy/MM/dd HH:mm:ss');
    }
};
</script>
<template>
    <Button icon="pi pi-file-edit" severity="info" class="p-button-rounded mr-2" @click="editProduct()" />
    <Dialog v-model:visible="productDialog" :style="{ width: '1050px' }" header="Sản phẩm chi tiết - Ảnh" :modal="true"
        class="p-fluid">
        <div class="Field col-12 md:col-6" style="margin-bottom: 30px">
            <DataTable ref="dt" :value="arrayImage" v-model:selection="selectedProducts" dataKey="id" :paginator="true"
                :rows="2" 
                paginatorTemplate="FirstPageLink PrevPageLink PageLinks NextPageLink LastPageLink CurrentPageReport RowsPerPageDropdown"
                :rowsPerPageOptions="[2, 5, 10]" currentPageReportTemplate=" {first} to {last} of {totalRecords}"
                responsiveLayout="scroll" showGridlines>
                <template #header>

                    <div class="flex flex-column md:flex-row md:justify-content-between md:align-items-center">
                        <div style="display: flex;">
                            <h5 class="m-0" style="margin-right: 20px;"> Ảnh </h5>
                        </div>
                        <div style="bottom: 100;">
                            <AddImage @addImg:my-prop="addimgMyProp" :my-prop="idProduct"></AddImage>
                        </div>
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
                <Column field="code" header="STT" :sortable="true" style="width: 1px; padding: 5px;">
                    <template #body="slotProps">
                        <span class="p-column-title">STT</span>
                        {{ arrayImage.indexOf(slotProps.data) + 1 }}
                    </template>
                </Column>


                <Column field="code" header="Ảnh" :sortable="true" headerStyle="width:14%; min-width:8rem;">
                    <template #body="slotProps">
                        <span class="p-column-title">Tên Màu Sắc</span>
                        <img :src="slotProps.data.anh" :alt="i" class="shadow-2" width="50" />
                    </template>
                </Column>

                <Column header="Action" headerStyle="min-width:8rem;">
                    <template #body="slotProps">
                        <UpdateImage :my-prop="slotProps.data" @updateImg:my-prop="updateimgMyProp">
                        </UpdateImage>
                        <Button icon="pi pi-trash" class="p-button-rounded p-button-warning mt-2"
                            @click="confirm1($event, slotProps.data.id)" />
                    </template>
                </Column>

            </DataTable>
        </div>
        <div class="Field col-12 md:col-12" style="margin-top: 30px; margin-bottom: 30px;">
            <DataTable ref="dt" :value="lstChiTietSP" v-model:selection="selectedProducts" dataKey="id" :paginator="true"
                :rows="5" :filters="filters"
                paginatorTemplate="FirstPageLink PrevPageLink PageLinks NextPageLink LastPageLink CurrentPageReport RowsPerPageDropdown"
                :rowsPerPageOptions="[5, 10, 25]"
                currentPageReportTemplate="Showing {first} to {last} of {totalRecords} products" responsiveLayout="scroll"
                showGridlines>
                <template #header>

                    <div class="flex flex-column md:flex-row md:justify-content-between md:align-items-center">
                        <div class="flex flex-column md:flex-row md:justify-content-between md:align-items-center">
                            <MultiSelect icon="pi pi-plus" :modelValue="selectedColumns" :options="columns"
                                optionLabel="header" @update:modelValue="onToggle" display="chip"
                                placeholder="Select Columns" />
                        </div>
                        <div style="display: flex;">
                            <h5 class="m-0" style="margin-right: 20px;">Chi Tiết Sản Phẩm </h5>
                        </div>
                        <span class="block mt-2 md:mt-0 p-input-icon-left" style="width: 200px; left: 50px;">
                            <i class="pi pi-search" />
                            <InputText v-model="filters['global'].value" placeholder="Search..." />

                        </span>
                        <Dropdown v-model="trangThaiselect" :options="dataTrangThai" optionLabel="label"
                            :optionLabel="(option) => option.label" placeholder="Tất cả" class="w-full md:w-14rem"
                            style="margin-left: 20px" @change="loadDataTrangThai()">

                        </Dropdown>

                        <AddSanPhamChiTiet @add:my-prop="addMyProp" :idProduct="idProduct">
                        </AddSanPhamChiTiet>
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
                <Column field="code" header="STT" :sortable="true" style="width: 1px; padding: 5px;">
                    <template #body="slotProps">
                        <span class="p-column-title">STT</span>
                        {{ lstChiTietSP.indexOf(slotProps.data) + 1 }}
                    </template>
                </Column>
                <Column field="anh" header="Ảnh" :sortable="true" headerStyle="width:14%; min-width:5rem;">
                    <template #body="slotProps">
                        <span class="p-column-title">Ảnh</span>
                        <img :src="slotProps.data.anh" :alt="i" class="shadow-2" width="50" />
                    </template>
                </Column>
                <Column field="tenMauSac" header="Màu Sắc" :sortable="true" headerStyle="width:14%; min-width:6rem;">
                    <template #body="slotProps">
                        <span class="p-column-title">Màu Sắc</span>
                        {{ slotProps.data.tenMauSac }}
                    </template>
                </Column>
                <Column field="tenSize" header="Size" :sortable="true" headerStyle="width:14%; min-width:6rem;">
                    <template #body="slotProps">
                        <span class="p-column-title">Size</span>
                        {{ slotProps.data.tenSize === null ? "chưa có" : slotProps.data.tenSize }}
                    </template>
                </Column>
                <Column field="soLuongTon" header="Số Lượng" :sortable="true" headerStyle="width:14%; min-width:8rem;">
                    <template #body="slotProps">
                        <span class="p-column-title">Số Lượng</span>
                        {{ slotProps.data.soLuongTon }}
                    </template>
                </Column>
                <!-- <Column field="giaNhap" header="Giá Nhập" :sortable="true" headerStyle="width:14%; min-width:8rem;">
                    <template #body="slotProps">
                        <span class="p-column-title">Số Lượng</span>
                        {{ formatCurrency(slotProps.data.giaNhap) }}
                    </template>
                </Column> -->
                <Column field="giaBan" header="Giá Bán" :sortable="true" headerStyle="width:14%; min-width:8rem;">
                    <template #body="slotProps">
                        <span class="p-column-title">Số Lượng</span>
                        {{ formatCurrency(slotProps.data.giaBan) }}
                    </template>
                </Column>
                <Column v-for="(col, index) of selectedColumns" :field="col.field" :header="col.header"
                    :key="col.field + '_' + index" :sortable="true" headerStyle="width:8%; min-width:5rem;">
                    <template #body="slotProps">
                        <span class="p-column-title">{{ col.field }}</span>
                        {{
                            col.field === 'giaSauGiam'
                            ? formatCurrency(slotProps.data[col.field])
                            : ['thoiGianBatDau', 'thoiGianKetThuc'].includes(col.field)
                                ? formatDate(slotProps.data[col.field])
                                : slotProps.data[col.field]
                        }}
                    </template>
                </Column>
                <Column field="trongLuong" header="Trọng Lượng" :sortable="true" headerStyle="width:14%; min-width:8rem;">
                    <template #body="slotProps">
                        <span class="p-column-title">Số Lượng</span>
                        {{ slotProps.data.trongLuong }}
                    </template>
                </Column>
                <Column field="trangThai" header="Trạng Thái" sortable headerStyle="width: 4%; min-width: 5rem;">
                    <template #body="slotProps">

                        <Tag :value="getStatusLabel(slotProps.data.trangThai).text"
                            v-if="slotProps.data.tenKM === null || slotProps.data.tenKM == ''"
                            :severity="getStatusLabel(slotProps.data.trangThai).severity" />
                        <Tag :value="getStatusLabel(slotProps.data.trangThai).text"
                            v-else-if="slotProps.data.trangThai == 0"
                            :severity="getStatusLabel(slotProps.data.trangThai).severity" />
                        <div v-else>
                            <Tag :value="getStatusLabelKhuyenMai(slotProps.data.tenKM).text"
                                :severity="getStatusLabelKhuyenMai(slotProps.data.tenKM).severity" />
                        </div>

                    </template>
                </Column>
                <Column header="Action" headerStyle="min-width:10rem;">
                    <template #body="slotProps">
                        <UpdateSanPhamChiTiet :my-prop="slotProps.data" @update:myProp="updateMyProp"
                            :idProduct="idProduct"></UpdateSanPhamChiTiet>
                        <ConfirmPopup></ConfirmPopup>
                        <Button icon="pi pi-trash" class="p-button-rounded p-button-warning mt-2"
                            @click="confirm2($event, slotProps.data.id)" v-if="slotProps.data.trangThai !== 0" />


                        <Button icon="pi pi-refresh" class="p-button-rounded p-button-warning mt-2"
                            @click="confirm3($event, slotProps.data.id, slotProps.data.soLuongTon)"
                            v-if="slotProps.data.trangThai == 0 && slotProps.data.soLuongTon > 0" />
                    </template>
                </Column>

            </DataTable>
        </div>


    </Dialog>
</template>