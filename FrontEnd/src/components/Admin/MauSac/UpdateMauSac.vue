<script setup>
import { ref } from 'vue';
import { useForm, useField } from 'vee-validate';
import * as yup from 'yup';
import { useMauSacService } from '@/service/Admin/MauSac/MauSacService';
import { useToast } from 'primevue/usetoast';

const mauSacService = useMauSacService();
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

const schema = yup.object().shape({
    ten: yup
        .string()
        .required('Tên không được để trống!')
        .max(30, 'Tên giới hạn 30 ký tự')
        .matches(/^[a-zA-Z0-9đĐáÁàÀảẢãÃạẠăĂắẮằẰẳẲẵẴặẶâÂấẤầẦẩẨẫẪậẬêÊếẾềỀểỂễỄệỆôÔốỐồỒổỔỗỖộỘỏỎơƠớỚờỜởỞỡỠợỢỏỎóÓòÒõÕọỌẻẺéÉèÈẽẼẹẸỉỈíÍìÌĩĨịỊùÙúÚụỤủỦũŨưỨỨửỬữỮựỰýÝỳỲỷỶỹỸỵỴ\s]*$/, 'Tên không được chứa kí tự đặc biệt!'),
    moTa: yup.string().max(100, 'Mô tả giới hạn 100 ký tự').nullable()
});
const { handleSubmit, resetForm } = useForm({
    validationSchema: schema
});
const { value: ten, errorMessage: tenError } = useField('ten');
const { value: MoTa, errorMessage: MoTaSacError } = useField('moTa');
const onSubmit = handleSubmit(async (values) => {
    try {
        // console.log('Dữ liệu đã gửi:', values);

        // Sau khi xử lý, đặt lại biểu mẫu

        reset();
    } catch (error) {
        console.error('Lỗi xử lý dữ liệu:', error);
    }
});
const array = ref([]);
const reset = () => {
    resetForm();
    array.value = null;
};
//hiện confirm
const confirmUpdateProduct = () => {
    updateProductDialog.value = true;
};

// mở form
const editProduct = () => {
    ten.value = props.myProp.ten;
    MoTa.value = props.myProp.moTa;
    product.value = { ...editProduct };
    productDialog.value = true;
};

//đóng form
const hideDialog = () => {
    ten.value = props.myProp.ten;
    MoTa.value = props.myProp.moTa;
    productDialog.value = false;
    submitted.value = false;
};

//save
const saveProduct = () => {
    confirmUpdateProduct();
};

// sửa
const containsSpecialCharacters = (ten) => {
    return /[!@#$%^&*(),.?":{}|<>]/.test(ten);
};
const isTenTooLong = (ten) => {
    return ten.length > 30;
};
const isMoTaTooLong = (moTa) => {
    if(moTa == null) return false;
    return moTa.length > 100;
};
const updateProduct = () => {
    submitted.value = true;
    const form = {
        ten: ten.value,
        moTa: MoTa.value
    };
    if (form.ten === null || form.ten.trim() === '') {
        toast.add({ severity: 'error', summary: 'Thông báo', detail: 'Thêm thất bại', life: 3000 });
    } else if (containsSpecialCharacters(form.ten)) {
        toast.add({ severity: 'error', summary: 'Thông báo', detail: 'Thêm thất bại', life: 3000 });
    } else if (isTenTooLong(form.ten)) {
        toast.add({ severity: 'error', summary: 'Thông báo', detail: 'Thêm thất bại', life: 3000 });
    } else if (isMoTaTooLong(form.moTa)) {
        toast.add({ severity: 'error', summary: 'Thông báo', detail: 'Thêm thất bại', life: 3000 });
    } else {
        const update = mauSacService.updateMauSac(props.myProp.id, form);
        toast.add({ severity: 'success', summary: 'Thông báo', detail: 'Sửa thành công', life: 3000 });
        productDialog.value = false;
    }
    updateProductDialog.value = false;
};
</script>
<template>
    <Button icon="pi pi-pencil" class="p-button-rounded p-button-success mr-2" @click="editProduct()" />

    <Dialog v-model:visible="productDialog" :style="{ width: '600px' }" header="Sửa màu sắc" :modal="true" class="p-fluid">
        <div class="card">
            <form @submit="onSubmit">
                <div class="p-fluid formgrid grid">
                    <div class="Field col-12" style="margin-bottom: 30px">
                        <span class="p-float-label">
                            <InputText id="ten" name="ten" type="text" v-model="ten" :class="{ 'p-invalid': tenError }" v-model.trim="product.ten" required="true" autofocus />
                            <label for="username">Tên màu sắc</label>
                        </span>
                        <small class="p-error">{{ tenError }}</small>
                    </div>
                    <div class="field col-12" style="margin-bottom: 30px">
                        <label for="address">Mô tả</label>
                        <Textarea id="MoTa" rows="4" v-model="MoTa" :class="{ 'p-invalid': MoTaSacError }" v-model.trim="product.ten" required="true" autofocus></Textarea>
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
