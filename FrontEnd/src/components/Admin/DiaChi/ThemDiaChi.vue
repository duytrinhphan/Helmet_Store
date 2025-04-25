<script setup>
import { useToast } from 'primevue/usetoast';
import { useForm, useField } from 'vee-validate';
import { onMounted, ref, watch } from 'vue';
import * as yup from 'yup';
import { useDiaChi } from '@/service/KhachHang/DiaChiService';
import { useRoute } from 'vue-router';
import { userStore } from '@/service/Admin/User/UserService';

const props = defineProps({
    idUser: Number
});

const route = useRoute();
const diaChiService = useDiaChi();
const userService = userStore();
const product = ref({});
const toast = useToast();
const submitted = ref(false);
const productDialog = ref(false);
// confirm thêm
const addProductDialog = ref(false);

const diaChi = ref('');
const addProduct = async () => {
    submitted.value = true;
   
    if (!selectedTinhThanh.value || !selectedQuanHuyen.value || !selectedPhuongXa.value || !diaChi.value) {
        toast.add({ severity: 'error', summary: 'Thông báo', detail: 'Vui lòng nhập đầy đủ thông tin', life: 3000 });
        return;
    } else {
        const form = {
            idUser: props.idUser,
        idTinhThanh: selectedTinhThanh.value.ProvinceID,
        tinhThanh: selectedTinhThanh.value.ProvinceName,
        idQuanHuyen: selectedQuanHuyen.value.DistrictID,
        quanHuyen: selectedQuanHuyen.value.DistrictName,
        idPhuongXa: selectedPhuongXa.value.WardCode,
        phuongXa: selectedPhuongXa.value.WardName,
        diaChi: diaChi.value
    };

       await userService.createDiaChi(form);
      //  window.location.reload();
       productDialog.value = false;
       toast.add({ severity: 'success', summary: 'Thông báo', detail: 'Thêm địa chỉ thành công', life: 3000 });
       clearForm();
    }
    addProductDialog.value = false;
};

const clearForm = () => {
    selectedTinhThanh.value = null;
    selectedQuanHuyen.value = null;
    selectedPhuongXa.value = null;
    diaChi.value = null;
};
// mở form
const openNew = () => {
    product.value = {};
    submitted.value = false;
    productDialog.value = true;
};

//đóng form
const hideDialog = () => {
    productDialog.value = false;
    submitted.value = false;
};

//save
const saveProduct = () => {
    addProductDialog.value = true;
};

const selectedTinhThanh = ref(null);
const selectedQuanHuyen = ref(null);
const selectedPhuongXa = ref(null);
const tinhThanhList = ref([]);
const quanHuyenList = ref([]);
const phuongXaList = ref([]);
// Fetch data when component is mounted
onMounted(async () => {
    try {
        const response = await diaChiService.fetchTinhThanh();
        tinhThanhList.value = response;
        // console.log(tinhThanhList.value);
    } catch (error) {
        console.error('Error fetching tinhThanhList:', error);
    }
});

// X? lý s? ki?n khi t?nh/thành ph? thay d?i
const onTinhThanhChange = async () => {
    // console.log('Tinh Thanh ID:', selectedTinhThanh.value); // Ki?m tra giá tr? c?a tinhThanhId
    if (selectedTinhThanh.value) {
        try {
            // G?i hàm fetchQuanHuyen v?i giá tr? tinhThanhId
            const quanHuyenData = await diaChiService.fetchQuanHuyen(selectedTinhThanh.value.ProvinceID);
            quanHuyenList.value = quanHuyenData;
            // console.log('Danh sách qu?n/huy?n:', quanHuyenData); // Ki?m tra d? li?u tr? v? t? API
            // ...
        } catch (error) {
            console.error('L?i khi l?y danh sách qu?n/huy?n:', error);
        }
    }
};

// Function to fetch Phu?ng/Xã based on the selected Qu?n/Huy?n
const onQuanHuyenChange = async () => {
    if (selectedQuanHuyen.value) {
        try {
            // Fetch data for the third dropdown (Phu?ng/Xã) based on the selected Qu?n/Huy?n
            const phuongXaData = await diaChiService.fetchPhuongXa(selectedQuanHuyen.value.DistrictID);
            phuongXaList.value = phuongXaData;
            // console.log('Danh sách xã: ', phuongXaData);
        } catch (error) {
            console.error('L?i khi l?y danh sách phu?ng/xã:', error);
        }
    }
};
</script>
<template>
    <a label="thêm mới"   @click="openNew" style="color: blue;" >Thêm mới</a>
    <!-- <div class="grid">
        <div class="col-12">
            <div class="card"> -->
    <Dialog v-model:visible="productDialog" :style="{ width: '600px' }" header="Thêm địa chỉ" :modal="true" class="p-fluid">
        <div class="card">
            <form @submit="onSubmit">
                <div class="p-fluid formgrid grid">
                    <div class="field col-12" style="margin-bottom: 30px">
                        <label for="tinhThanh">Tỉnh/Thành phố</label>
                        <Dropdown v-model="selectedTinhThanh" :options="tinhThanhList" optionLabel="ProvinceName" placeholder="Chọn tỉnh/thành phố" @change="onTinhThanhChange"></Dropdown>
                    </div>

                    <!-- Quận/Huyện combobox -->
                    <div class="field col-12" style="margin-bottom: 30px">
                        <label for="quanHuyen">Quận/Huyện</label>
                        <Dropdown v-model="selectedQuanHuyen" :options="quanHuyenList" optionLabel="DistrictName" placeholder="Chọn quận/huyện" @change="onQuanHuyenChange"></Dropdown>
                    </div>

                    <!-- Phường/Xã combobox -->
                    <div class="field col-12" style="margin-bottom: 30px">
                        <label for="phuongXa">Phường/Xã</label>
                        <Dropdown v-model="selectedPhuongXa" :options="phuongXaList" optionLabel="WardName" placeholder="Chọn phường/xã"></Dropdown>
                    </div>
                    <div class="field col-12" style="margin-bottom: 30px">
                        <label for="address">Địa chỉ cụ thể</label>
                        <InputText id="diaChi" rows="4" v-model.trim="diaChi" :class="{ 'p-invalid': MoTaSacError }" required="false" autofocus></InputText>
                    </div>
                </div>
            </form>
        </div>
        <Dialog v-model:visible="addProductDialog" :style="{ width: '450px' }" header="Confirm" :modal="true">
            <div class="flex align-items-center justify-content-center">
                <i class="pi pi-exclamation-triangle mr-3" style="font-size: 2rem" />
                <span v-if="product">Bạn có chắc chắn muốn thêm không ?</span>
            </div>
            <template #footer>
                <Button label="No" icon="pi pi-times" class="p-button-text" @click="addProductDialog = false" />
                <Button label="Yes" icon="pi pi-check" class="p-button-text" @click="addProduct" />
            </template>
        </Dialog>
        <template #footer>
            <Button label="Cancel" icon="pi pi-times" class="p-button-text" @click="hideDialog" />
            <Button label="Save" icon="pi pi-check" class="p-button-text" @click="saveProduct" />
        </template>
    </Dialog>
    <!-- </div>
        </div>
    </div> -->
</template>
