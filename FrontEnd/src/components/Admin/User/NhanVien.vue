<script setup>
import { FilterMatchMode } from 'primevue/api';
import { ref, onMounted, onBeforeMount, watch } from 'vue';
import { useToast } from 'primevue/usetoast';
import ThemUser from './ThemNhanVien.vue';
import UpdateUser from './UpdateUser.vue';
import DetailUser from './DetailUser.vue';
import { userStore } from '@/service/Admin/User/UserService';
const toast = new useToast();
const deleteProductDialog = ref(false);
const deleteProductsDialog = ref(false);
const product = ref({});
const selectedProducts = ref(null);
const dt = ref(null);
const filters = ref({});
const dataUser = ref([]);
const userService = userStore();
const idDelete = ref();
const dataTrangThai = ref([
    { label: 'Tất cả', value: 'Tất cả' },
    { label: 'Đang hoạt động', value: '0' },
    { label: 'Tài khoản bị khóa', value: '1' }
]);
const trangThai = ref();

const loadData = async () => {
    await userService.fetchDataRole('NHANVIEN');
    // dataUser.value = userService.data;
    // console.log(userService.data);
    const productList = userService.dataNhanVien; // Lấy dữ liệu từ Store và gán vào biến products

    for (const [key, product] of productList.entries()) {
        // productList[key]['img'] = null;
        productList[key]['diaChi'] = null;
        const diaChi = await loadDiaChi(product.id);
        productList[key]['diaChi'] = diaChi;
    }
    dataUser.value = productList;
    // console.log(dataUser.value);
};

const loadDatByTrangThai = async () => {
    await userService.fetchDataByStatuss(trangThai.value.value);
    const productList = userService.data.filter((user) => user.role === 'NHANVIEN');
    dataUser.value = productList;
};
//thay đổi cbb
watch(trangThai, (newVal) => {
    if (trangThai.value.value != 'Tất cả') {
        loadDatByTrangThai();
    } else {
        loadData();
    }
});

onBeforeMount(() => {
    initFilters();
});

onMounted(() => {
    loadData();
});

const diaChi = ref([]);

const loadDiaChi = async (idProduct) => {
    await userService.fetchAllDiaChi(idProduct); // Gọi hàm fetchAll từ Store
    diaChi.value = userService.diaChi;
    // console.log('mau: ',  loadMau.value );
    return diaChi.value;
};

const initFilters = () => {
    filters.value = {
        global: { value: null, matchMode: FilterMatchMode.CONTAINS }
    };
};

const confirmDeleteProduct = (id) => {
    idDelete.value = id;
    deleteProductDialog.value = true;
};

//xoá
const deleteProduct = (id) => {
    const remove = userService.deleteUser(idDelete.value);
    toast.add({ severity: 'success', summary: 'Thông báo', detail: 'Xoá thành công', life: 3000 });
    deleteProductDialog.value = false;
};
const truyeRole = 'NHANVIEN';

const columns = ref([
    { field: 'ngaySinh', header: 'Ngày sinh' },
    { field: 'email', header: 'Email' },
    { field: 'userName', header: 'User name' },
    { field: 'soLuongHoaDon', header: 'Số lượng đơn' },
    { field: 'role', header: 'Role' },
    { field: 'sdt', header: 'SĐT' }
]);

// hàm để tắt/mở cột
const selectedColumns = ref(null);

const onToggle = (val) => {
    selectedColumns.value = columns.value.filter((col) => val.includes(col));
};
</script>
<template>
    <div class="grid">
        <div class="col-12">
            <Toolbar class="mb-4">
                <template v-slot:start>
                    <div class="my-2">
                        <!-- <Button label="New" icon="pi pi-plus" class="p-button-success mr-2" @click="openNew" /> -->
                        <ThemUser :my-prop="truyeRole"></ThemUser>
                    </div>
                </template>
            </Toolbar>
            <DataTable
                ref="dt"
                :value="dataUser"
                v-model:selection="selectedProducts"
                dataKey="id"
                :paginator="true"
                :rows="10"
                :filters="filters"
                paginatorTemplate="FirstPageLink PrevPageLink PageLinks NextPageLink LastPageLink CurrentPageReport RowsPerPageDropdown"
                :rowsPerPageOptions="[5, 10, 25]"
                currentPageReportTemplate="Showing {first} to {last} of {totalRecords} products"
                responsiveLayout="scroll"
            >
                <template #header>
                    <div class="flex flex-column md:flex-row md:justify-content-between md:align-items-center">
                        <div class="flex flex-column md:flex-row md:justify-content-between md:align-items-center" style="width: 50px">
                            <MultiSelect icon="pi pi-plus" :modelValue="selectedColumns" :options="columns" optionLabel="header" @update:modelValue="onToggle" display="chip" placeholder="Select Columns" />
                        </div>
                        <span class="block mt-2 md:mt-0 p-input-icon-left">
                            <i class="pi pi-search" />
                            <InputText v-model="filters['global'].value" placeholder="Search..." />
                            <Dropdown v-model="trangThai" :options="dataTrangThai" optionLabel="label" placeholder="Tất cả" class="w-full md:w-14rem" style="margin-left: 20px" />
                        </span>
                    </div>
                </template>
                <Column header="STT" headerStyle="width:14%; min-width:10rem;">
                    <template #body="slotProps">
                        <span class="p-column-title">STT</span>
                        {{ slotProps.data.stt }}
                    </template>
                </Column>
                <Column header="Ảnh" headerStyle="width:14%; min-width:10rem;">
                    <template #body="slotProps">
                        <span class="p-column-title">anh</span>
                        <img :src="slotProps.data.anh" :alt="slotProps.data.anh" class="shadow-2" width="100" />
                    </template>
                </Column>
                <Column field="ma" header="Mã" headerStyle="width:14%; min-width:10rem;">
                    <template #body="slotProps">
                        <span class="p-column-title">ma</span>
                        {{ slotProps.data.ma }}
                    </template>
                </Column>
                <Column field="ten" header="Tên" headerStyle="width:14%; min-width:10rem;">
                    <template #body="slotProps">
                        <span class="p-column-title">ten</span>
                        {{ slotProps.data.ten }}
                    </template>
                </Column>
                <Column field="gioiTinh" header="Giới tính" headerStyle="width:14%; min-width:8rem;">
                    <template #body="slotProps">
                        <span class="p-column-title">gioiTinh</span>
                        {{ slotProps.data.gioiTinh == 1 ? 'Nam' : slotProps.data.gioiTinh == 0 ? 'Nữ' : 'Chưa xác định' }}
                    </template>
                </Column>
                <!-- <Column field="ngaySinh" header="Ngày sinh" headerStyle="width:14%; min-width:10rem;">
                    <template #body="slotProps">
                        <span class="p-column-title">ngaySinh</span>
                        {{ slotProps.data.ngaySinh }}
                    </template>
                </Column> -->
                <!-- <Column field="sdt" header="Sdt" headerStyle="width:14%; min-width:10rem;">
                    <template #body="slotProps">
                        <span class="p-column-title">sdt</span>
                        {{ slotProps.data.sdt }}
                    </template>
                </Column> -->
                <!-- <Column field="email" header="Email" headerStyle="width:14%; min-width:10rem;">
                    <template #body="slotProps">
                        <span class="p-column-title">email</span>
                        {{ slotProps.data.email }}
                    </template>
                </Column>
                <Column field="userName" header="User name" headerStyle="width:14%; min-width:10rem;">
                    <template #body="slotProps">
                        <span class="p-column-title">userName</span>
                        {{ slotProps.data.userName }}
                    </template>
                </Column>
                <Column field="soLuongHoaDon" header="Số lượng đơn" headerStyle="width:14%; min-width:10rem;">
                    <template #body="slotProps">
                        <span class="p-column-title">soLuongHoaDon</span>
                        {{ slotProps.data.soLuongHoaDon }}
                    </template>
                </Column>
                <Column field="role" header="Role" headerStyle="width:14%; min-width:10rem;">
                    <template #body="slotProps">
                        <span class="p-column-title">role</span>
                        {{ slotProps.data.role }}
                    </template>
                </Column>-->

                <Column v-for="(col, index) of selectedColumns" :field="col.field" :header="col.header" :key="col.field + '_' + index" :sortable="true" headerStyle="width:8%; min-width:9rem;"> </Column>

                <Column field="trangThai" header="Trạng thái" headerStyle="width:14%; min-width:10rem;">
                    <template #body="slotProps">
                        <span class="p-column-title">trangThai</span>
                        {{ slotProps.data.trangThai == 1 ? 'Tài khoản bị khóa' : 'Đang hoạt động' }}
                    </template>
                </Column>
                <Column header="Hành động" headerStyle="min-width:10rem;">
                    <template #body="slotProps">
                        <DetailUser :my-prop="slotProps.data"></DetailUser>
                        <UpdateUser :my-prop="slotProps.data"></UpdateUser>
                        <!-- <Button icon="pi pi-pencil" class="p-button-rounded p-button-success mr-2" @click="editProduct(slotProps.data)" /> -->
                        <Button icon="pi pi-trash" class="p-button-rounded p-button-warning mt-2" @click="confirmDeleteProduct(slotProps.data.id)" />
                    </template>
                </Column>
            </DataTable>
            <Dialog v-model:visible="deleteProductDialog" :style="{ width: '450px' }" header="Confirm" :modal="true">
                <div class="flex align-items-center justify-content-center">
                    <i class="pi pi-exclamation-triangle mr-3" style="font-size: 2rem" />
                    <span v-if="product"
                        >Bạn có muốn vô hiệu hóa tài khoản <b>{{ dataUser.ten }}</b
                        >?</span
                    >
                </div>
                <template #footer>
                    <Button label="No" icon="pi pi-times" class="p-button-text" @click="deleteProductDialog = false" />
                    <Button label="Yes" icon="pi pi-check" class="p-button-text" @click="deleteProduct" />
                </template>
            </Dialog>

            <Dialog v-model:visible="deleteProductsDialog" :style="{ width: '450px' }" header="Confirm" :modal="true">
                <div class="flex align-items-center justify-content-center">
                    <i class="pi pi-exclamation-triangle mr-3" style="font-size: 2rem" />
                    <span v-if="product">Bạn có muốn vô hiệu hóa tài khoản?</span>
                </div>
                <template #footer>
                    <Button label="No" icon="pi pi-times" class="p-button-text" @click="deleteProductsDialog = false" />
                    <Button label="Yes" icon="pi pi-check" class="p-button-text" @click="deleteSelectedProducts" />
                </template>
            </Dialog>
        </div>
    </div>
</template>
