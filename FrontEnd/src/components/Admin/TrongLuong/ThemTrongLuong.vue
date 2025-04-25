<script setup>
import { useToast } from 'primevue/usetoast';
import { useForm, useField } from 'vee-validate';
import { ref, watch } from 'vue';
import * as yup from 'yup';
import { TrongLuongStore } from '@/service/Admin/TrongLuong/TrongLuong.api';

const TrongLuongService = TrongLuongStore();
const product = ref({});
const toast = useToast();
const submitted = ref(false);
const productDialog = ref(false);
// confirm thêm
const addProductDialog = ref(false);

const schema = yup.object().shape({
    donVi: yup
        .string()
        .required('Đơn vị không được để trống!')
        .max(30, 'Đơn vị giới hạn 30 ký tự')
        .matches(/^[a-zA-Z0-9đĐáÁàÀảẢãÃạẠăĂắẮằẰẳẲẵẴặẶâÂấẤầẦẩẨẫẪậẬêÊếẾềỀểỂễỄệỆôÔốỐồỒổỔỗỖộỘỏỎóÓòÒõÕọỌẻẺéÉèÈẽẼẹẸỉỈíÍìÌĩĨịỊơƠớỚờỜởỞỡỠợỢùÙúÚụỤủỦũŨưỨỨửỬữỮựỰýÝỳỲỷỶỹỸỵỴ\s]*$/, 'Tên không được chứa kí tự đặc biệt!'),
    giaTri: yup.number('Giá trị phải là số').required('Vui lòng nhập giá trị.').min(100, 'Giá trị phải lớn hơn hoặc bằng 100.').max(5000, 'Giá trị phải nhỏ hơn hoặc bằng 5000.')
});
const { handleSubmit, resetForm } = useForm({
    validationSchema: schema
});
const { value: donVi, errorMessage: donViError } = useField('donVi');

const { value: giaTri, errorMessage: giaTriError } = useField('giaTri');

const onSubmit = handleSubmit(async (values) => {
    try {
        // Sau khi xử lý, đặt lại biểu mẫu

        reset();
    } catch (error) {
        console.error('Lỗi xử lý dữ liệu:', error);
    }
});
watch(giaTri, (newVal) => {
    if (giaTri.value.length <= 0) {
        giaTri.value = 0;
        giaTriError.value = 'Giá trị không được để trống.';
    }
});
//add
const containsSpecialCharacters = (donVi) => {
    return /[!@#$%^&*(),.?":{}|<>]/.test(donVi);
};
const isDonViTooLong = (donVi) => {
    return donVi.length > 30;
};
const isGiaTriTooLong = (giaTri) => {
    return giaTri.length <= 0;
};
const addProduct = () => {
    submitted.value = true;
    const form = {
        donVi: donVi.value,
        value: giaTri.value
    };
    // Kiểm tra trường "ten" có trống (null hoặc chuỗi rỗng) hoặc có chứa ký tự đặc biệt không
    if (form.donVi == null || form.donVi.length <= 0) {
        donVi.value = '';
        toast.add({ severity: 'error', summary: 'Thông báo', detail: 'Thêm thất bại', life: 3000 });
    } else if (containsSpecialCharacters(form.donVi)) {
        toast.add({ severity: 'error', summary: 'Thông báo', detail: 'Thêm thất bại', life: 3000 });
    } else if (isDonViTooLong(form.donVi)) {
        toast.add({ severity: 'error', summary: 'Thông báo', detail: 'Thêm thất bại', life: 3000 });
    } else if (isGiaTriTooLong(form.value)) {
        toast.add({ severity: 'error', summary: 'Thông báo', detail: 'Thêm thất bại', life: 3000 });
    } else if (form.value == 0 || form.value < 100 || form.value > 5000) {
        toast.add({ severity: 'error', summary: 'Thông báo', detail: 'Thêm thất bại', life: 3000 });
    } else {
        giaTri.value = 0;
        const add = TrongLuongService.createTrongLuong(form);
        productDialog.value = false;
        toast.add({ severity: 'success', summary: 'Thông báo', detail: 'Thêm thành công', life: 3000 });
        clearForm();
    }
    addProductDialog.value = false;
};
const clearForm = () => {
    donVi.value = '';

    giaTri.value = 0;
};
const array = ref([]);
const reset = () => {
    resetForm();
    array.value = null;
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
</script>
<template>
    <Button label="Thêm mới" icon="pi pi-plus" class="p-button-success mr-2" @click="openNew" />
    <Dialog v-model:visible="productDialog" :style="{ width: '600px' }" header="Thêm trọng lượng" :modal="true" class="p-fluid">
        <div class="card">
            <form @submit="onSubmit">
                <div class="p-fluid formgrid grid">
                    <div class="Field col-12" style="margin-bottom: 30px">
                        <span class="p-float-label">
                            <InputText id="donVi" name="donVi" type="text" v-model.trim="donVi" :class="{ 'p-invalid': donViError }" required="true" autofocus />
                            <label for="username">Đơn vị</label>
                        </span>
                        <small class="p-error">{{ donViError }}</small>
                    </div>
                    <div class="Field col-12" style="margin-bottom: 30px">
                        <span class="p-float-label">
                            <InputText id="giaTri" name="giaTri" type="number" v-model.trim="giaTri" :class="{ 'p-invalid': giaTriError }" required="true" autofocus />
                            <label for="username">Giá trị</label>
                        </span>
                        <small class="p-error">{{ giaTriError }}</small>
                        <small class="p-error">{{ messGiaTri }}</small>
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
