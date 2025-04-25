<script setup>
import { FilterMatchMode } from 'primevue/api';
import { ref, onMounted, onBeforeMount } from 'vue';
import { useToast } from 'primevue/usetoast';
import ThemUser from './ThemAdmin.vue';
import TatCaVue from './TatCa.vue';
import NhanVien from './NhanVien.vue';
import KhachHang from './KhachHang.vue';
import Admin from './Admin.vue';
import { userStore } from '@/service/Admin/User/UserService';
const toast = new useToast();
const deleteProductDialog = ref(false);
const filters = ref({});
const dataUser = ref([]);
const userService = userStore();
const idDelete = ref();
onBeforeMount(() => {
    initFilters();
});
onMounted(() => {
    loadData();
});

const loadData = async () => {
    await userService.fetchData();
    // dataUser.value = userService.data;
    // console.log(userService.data);
    const productList = userService.data; // Lấy dữ liệu từ Store và gán vào biến products

    for (const [key, product] of productList.entries()) {
        // productList[key]['img'] = null;
        productList[key]['diaChi'] = null;
        const diaChi = await loadDiaChi(product.id);
        productList[key]['diaChi'] = diaChi;
    }
    dataUser.value = productList;
    // console.log(dataUser.value);
};

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
</script>
<template>
    <div class="grid">
        <div class="col-12">
            <div class="card">
                <h5>Quản lý người dùng</h5>
                <div class="card">
                    <TabView>
                        <TabPanel header="Tất cả">
                            <TatCaVue> </TatCaVue>
                        </TabPanel>
                        <TabPanel header="Admin">
                            <Admin> </Admin>
                        </TabPanel>
                        <TabPanel header="Nhân viên">
                            <NhanVien> </NhanVien>
                        </TabPanel>
                        <TabPanel header="Khách hàng">
                            <KhachHang> </KhachHang>
                        </TabPanel>
                    </TabView>
                </div>
            </div>
        </div>
    </div>
</template>
