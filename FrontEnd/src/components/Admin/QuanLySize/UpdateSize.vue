<script setup>
import * as yup from 'yup';
import { useForm, useField } from 'vee-validate';
import { ref, onMounted, onBeforeMount } from 'vue';
import axios from 'axios';
import { useToast } from 'primevue/usetoast';
import { SizeStore } from '@/service/Admin/Size/SizeService';

const useSizeService = SizeStore();
const toast = useToast();
const product = ref({});
const submitted = ref(false);
const productDialog = ref(false);
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

const schema = yup.object().shape({
    ten: yup
        .string()
        .required('Tên size không được để trống')
        .max(30, 'Tên size phải không được quá 30 ký tự')
        .matches(/^[a-zA-Z0-9đĐáÁàÀảẢãÃạẠăĂắẮằẰẳẲẵẴặẶâÂấẤầẦẩẨẫẪậẬêÊếẾềỀểỂễỄệỆôÔốỐồỒổỔỗỖộỘơƠớỚờỜởỞỡỠợỢùÙúÚụỤủỦũŨưỨỨửỬữỮựỰýÝỳỲỷỶỹỸỵỴ\s]*$/, 'Tên không được chứa kí tự đặc biệt!'),
    moTa: yup.string().max(255, 'Mô tả size không quá 255 ký tự').nullable()
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
const isMoTaTooLong = (moTa) => {
    if (moTa == null) return false;
    return moTa.length >= 256;
};

const { value: ten, errorMessage: tenError } = useField('ten');
const { value: moTa, errorMessage: MoTaSacError } = useField('moTa');

// mở form
const editProduct = () => {
    ten.value = props.myProp.ten;
    moTa.value = props.myProp.moTa;
    product.value = { ...editProduct };
    productDialog.value = true;
};

//đóng form
const hideDialog = () => {
    ten.value = props.myProp.ten;
    moTa.value = props.myProp.moTa;
    productDialog.value = false;
    submitted.value = false;
};

//save
const saveProduct = () => {
    confirmUpdateProduct();
};

// sửa
const updateProduct = () => {
    submitted.value = true;
    const form = {
        ten: ten.value,
        moTa: moTa.value
    };
    if (form.ten == null || form.ten <= 0) {
        toast.add({ severity: 'success', summary: 'Thông báo', detail: 'Sửa thất bại', life: 3000 });
    } else if (form.ten.length == 0) {
        toast.add({ severity: 'success', summary: 'Thông báo', detail: 'Sửa thất bại', life: 3000 });
    } else if (containsSpecialCharacters(form.ten)) {
        toast.add({ severity: 'success', summary: 'Thông báo', detail: 'Sửa thất bại', life: 3000 });
    } else if (isTenTooLong(form.ten)) {
        toast.add({ severity: 'success', summary: 'Thông báo', detail: 'Sửa thất bại', life: 3000 });
    } else if (isMoTaTooLong(form.moTa)) {
        toast.add({ severity: 'success', summary: 'Thông báo', detail: 'Sửa thất bại', life: 3000 });
    } else {
        const respone = useSizeService.updateSize(props.myProp.id, form);
        toast.add({ severity: 'success', summary: 'Thông báo', detail: 'Sửa thành công', life: 3000 });
        productDialog.value = false;
    }
    updateProductDialog.value = false;
};
</script>
<template>
    <Button icon="pi pi-pencil" class="p-button-rounded p-button-success mr-2" @click="editProduct()" />

    <Dialog v-model:visible="productDialog" :style="{ width: '600px' }" header="Update size" :modal="true" class="p-fluid">
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
                    <div class="field col-12" style="margin-bottom: 30px">
                        <label for="address">Mô tả</label>
                        <Textarea id="moTa" rows="4" v-model.trim="moTa" :class="{ 'p-invalid': MoTaSacError }" required="true" autofocus></Textarea>
                        <small class="p-error">{{ MoTaSacError }}</small>
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
            <Button label="Cancel" icon="pi pi-times" class="p-button-text" @click="hideDialog" />
            <Button label="Save" icon="pi pi-check" class="p-button-text" @click="saveProduct" />
        </template>
    </Dialog>
    <!-- </div>
        </div>
    </div> -->
</template>
