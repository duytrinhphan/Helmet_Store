<script setup>
import { onMounted, ref } from 'vue';
import { useForm, useField } from 'vee-validate';
import * as yup from 'yup';
import { useDiaChi } from '@/service/KhachHang/DiaChiService';
import { useRoute } from 'vue-router';
import { useToast } from 'primevue/usetoast';

const route = useRoute();
const idUser = parseInt(route.params.id);
const diaChiService = useDiaChi();
const toast = useToast();
const product = ref({});
const submitted = ref(false);
const productDialog = ref(false);
const diaChi = ref('');
// confirm sửa
const updateProductDialog = ref(false);
//nhận dữ liệu từ cha
const props = defineProps({
    myProp: {}
});

//hiện confirm
const confirmUpdateProduct = () => {
    updateProductDialog.value = true;
};

const selectedTinhThanh = ref(null);
const selectedQuanHuyen = ref(null);
const selectedPhuongXa = ref(null);
const tinhThanhList = ref([]);
const quanHuyenList = ref([]);
const phuongXaList = ref([]);

const editProduct = async (rowData) => {
    // Populate the comboboxes with data from the selected row
    selectedTinhThanh.value = tinhThanhList.value.find((tinhThanh) => tinhThanh.ProvinceID === rowData.idTinhThanh);
    await onTinhThanhChange(); // Fetch quanHuyenList based on selectedTinhThanh

    selectedQuanHuyen.value = quanHuyenList.value.find((quanHuyen) => quanHuyen.DistrictID === rowData.idQuanHuyen);
    await onQuanHuyenChange(); // Fetch phuongXaList based on selectedQuanHuyen

    selectedPhuongXa.value = phuongXaList.value.find((phuongXa) => phuongXa.WardCode === rowData.idphuongXa);
    // Set the diaChi value
    diaChi.value = rowData.diaChi;

    productDialog.value = true;
};

//đóng form
const hideDialog = () => {
    diaChi.value = props.myProp.diaChi;
    productDialog.value = false;
    submitted.value = false;
};

//save
const saveProduct = () => {
    confirmUpdateProduct();
};

// sửa
const updateProduct = async () => {
    submitted.value = true;

    if (!selectedTinhThanh.value || !selectedQuanHuyen.value || !selectedPhuongXa.value || !diaChi.value) {
        toast.add({ severity: 'error', summary: 'Thông báo', detail: 'Vui lòng nhập đầy đủ thông tin', life: 3000 });
        return;
    }

    const form = {
        idTinhThanh: selectedTinhThanh.value.ProvinceID,
        tinhThanh: selectedTinhThanh.value.ProvinceName,
        idQuanHuyen: selectedQuanHuyen.value.DistrictID,
        quanHuyen: selectedQuanHuyen.value.DistrictName,
        idPhuongXa: selectedPhuongXa.value.WardCode,
        phuongXa: selectedPhuongXa.value.WardName,
        diaChi: diaChi.value,
        user: idUser
    };

    try {
        const token = localStorage.getItem('token');
        await diaChiService.updateDiaChi(props.myProp.id, form, token);
        // console.log(form);
        // window.location.reload();
        toast.add({ severity: 'success', summary: 'Thông báo', detail: 'Sửa địa chỉ thành công', life: 3000 });
        updateProductDialog.value = false;
        clearForm();
        productDialog.value = false;
    } catch (error) {
        console.error('Lỗi khi thêm địa chỉ:', error);
        toast.add({ severity: 'error', summary: 'Thông báo', detail: 'Sửa địa chỉ thất bại', life: 3000 });
    }
    productDialog.value = false;
    updateProductDialog.value = false;
};

const clearForm = () => {
    selectedTinhThanh.value = null;
    selectedQuanHuyen.value = null;
    selectedPhuongXa.value = null;
    diaChi.value = null;
};

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
    <Button icon="pi pi-pencil" class="p-button-rounded p-button-success mr-2" @click="editProduct(props.myProp)" />

    <Dialog v-model:visible="productDialog" :style="{ width: '600px' }" header="Sửa màu sắc" :modal="true" class="p-fluid">
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
        <Dialog v-model:visible="updateProductDialog" :style="{ width: '450px' }" header="Confirm" :modal="true">
            <div class="flex align-items-center justify-content-center">
                <i class="pi pi-exclamation-triangle mr-3" style="font-size: 2rem" />
                <span v-if="product">Bạn có chắc chắn muốn sửa không ?</span>
            </div>
            <template #footer>
                <Button label="No" icon="pi pi-times" class="p-button-text" @click="updateProductDialog = false" />
                <Button label="Yes" icon="pi pi-check" class="p-button-text" @click="updateProduct" />
            </template>
        </Dialog>
        <template #footer>
            <Button label="Thoát" icon="pi pi-times" class="p-button-text" @click="hideDialog" />
            <Button label="Lưu" icon="pi pi-check" class="p-button-text" @click="saveProduct" />
        </template>
    </Dialog>
    <!-- </div>
        </div>
    </div> -->
</template>
