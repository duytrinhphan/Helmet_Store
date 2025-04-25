<script setup>
import * as yup from 'yup';
import { useForm, useField } from 'vee-validate';
import { ref, onMounted, onBeforeMount } from 'vue';
import axios from 'axios';
import { useToast } from 'primevue/usetoast';
import { useCounterStore } from '@/service/Admin/ThuongHieu/ThuongHieuService';

const useThuongHieuService = useCounterStore();
const toast = useToast();
const product = ref({});
const submitted = ref(false);
const productDialog = ref(false);
// confirm thêm
const addProductDialog = ref(false);

//hiện confirm
const confirmAddProduct = () => {
    addProductDialog.value = true;
};

const schema = yup.object().shape({
    ten: yup
        .string()
        .required('Tên size không được để trống')
        .max(30, 'Tên size phải không được quá 30 ký tự')
        .matches(/^[a-zA-Z0-9đĐáÁàÀảẢãÃạẠăĂắẮằẰẳẲẵẴặẶâÂấẤầẦẩẨẫẪậẬêÊếẾềỀểỂễỄệỆôÔốỐồỒổỔỗỖộỘơƠớỚờỜởỞỡỠợỢùÙúÚụỤủỦũŨưỨỨửỬữỮựỰýÝỳỲỷỶỹỸỵỴ\s]*$/, 'Tên không được chứa kí tự đặc biệt!')
});

const { handleSubmit, resetForm } = useForm({
    validationSchema: schema
});

const containsSpecialCharacters = (ten) => {
    return /[!@#$%^&*(),.?":{}|<>]/.test(ten);
};
const isTenTooLong = (ten) => {
    return ten.length >= 31;
};

const { value: ten, errorMessage: tenError } = useField('ten');

const clearForm = () => {
    ten.value = '';
};

//add
const addProduct = () => {
    submitted.value = true;
    const form = {
        ten: ten.value
    };
    if (form.ten == null || form.ten.length == 0) {
        ten.value = '';
        toast.add({ severity: 'success', summary: 'Thông báo', detail: 'Thêm thất bại', life: 3000 });
    } else if (containsSpecialCharacters(form.ten)) {
        toast.add({ severity: 'success', summary: 'Thông báo', detail: 'Thêm thất bại', life: 3000 });
    } else if (isTenTooLong(form.ten)) {
        toast.add({ severity: 'success', summary: 'Thông báo', detail: 'Thêm thất bại', life: 3000 });
    } else {
        const respone = useThuongHieuService.createSize(form);
        productDialog.value = false;
        toast.add({ severity: 'success', summary: 'Thông báo', detail: 'Thêm thành công', life: 3000 });
        clearForm();
    }
    addProductDialog.value = false;
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
    <Toast />
    <!-- <div class="grid">
        <div class="col-12">
            <div class="card"> -->
    <Dialog v-model:visible="productDialog" :style="{ width: '600px' }" header="Thêm thương hiệu" :modal="true" class="p-fluid">
        <div class="card">
            <form @submit="onSubmit">
                <div class="p-fluid formgrid grid">
                    <div class="Field col-12" style="margin-bottom: 30px">
                        <span class="p-float-label">
                            <InputText id="ten" name="ten" type="text" v-model.trim="ten" :class="{ 'p-invalid': tenError }" required="true" autofocus />
                            <label for="username">Tên size</label>
                        </span>
                        <small class="p-error">{{ tenError }}</small>
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
