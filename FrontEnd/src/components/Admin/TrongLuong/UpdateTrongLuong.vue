<script setup>
import { ref, watch } from 'vue';
import { useForm, useField } from 'vee-validate';
import * as yup from 'yup';
import { TrongLuongStore } from '@/service/Admin/TrongLuong/TrongLuong.api';
import { useToast } from 'primevue/usetoast';

const toast = useToast();
const TrongLuongService = TrongLuongStore();
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
    donVi: yup
        .string()

        .required('Tên không được để trống!')
        .max(30, 'Tên giới hạn 30 ký tự')
        .matches(/^[a-zA-Z0-9đĐáÁàÀảẢãÃạẠăĂắẮằẰẳẲẵẴặẶâÂấẤầẦẩẨẫẪậẬêÊếẾềỀểỂễỄệỆôÔốỐồỒổỔỗỖộỘỏỎóÓòÒõÕọỌẻẺéÉèÈẽẼẹẸỉỈíÍìÌĩĨịỊơƠớỚờỜởỞỡỠợỢùÙúÚụỤủỦũŨưỨỨửỬữỮựỰýÝỳỲỷỶỹỸỵỴ\s]*$/, 'Tên không được chứa kí tự đặc biệt!'),
    giaTri: yup.number().required('Vui lòng nhập giá trị.').min(100, 'Giá trị phải lớn hơn hoặc bằng 100.').max(5000, 'Giá trị phải nhỏ hơn hoặc bằng 5000.')
});
const { handleSubmit, resetForm } = useForm({
    validationSchema: schema
});
const { value: donVi, errorMessage: donViError } = useField('donVi');

const { value: giaTri, errorMessage: giaTriError } = useField('giaTri');

const onSubmit = handleSubmit(async (values) => {
    try {
        console.log('Dữ liệu đã gửi:', values);

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
    donVi.value = props.myProp.donVi;

    giaTri.value = props.myProp.value;

    product.value = { ...editProduct };
    productDialog.value = true;
};
//đóng form
const hideDialog = () => {
    donVi.value = props.myProp.donVi;
    value.value = props.myProp.value;
    productDialog.value = false;
    submitted.value = false;
};
watch(giaTri, (newVal) => {
    if (giaTri.value.length <= 0) {
        giaTri.value = 0;
        giaTriError.value = 'Giá trị không được để trống.';
    }
});
//save
const saveProduct = () => {
    confirmUpdateProduct();
};

// sửa
const containsSpecialCharacters = (donVi) => {
    return /[!@#$%^&*(),.?":{}|<>]/.test(donVi);
};
const isDonViTooLong = (donVi) => {
    return donVi.length > 30;
};

const updateProduct = () => {
    submitted.value = true;
    const form = {
        donVi: donVi.value,

        value: giaTri.value
    };
    if (form.donVi == null || form.donVi.length <= 0) {
        donVi.value = '';
        toast.add({ severity: 'error', summary: 'Thông báo', detail: 'Sửa thất bại', life: 3000 });
    } else if (containsSpecialCharacters(form.donVi)) {
        toast.add({ severity: 'error', summary: 'Thông báo', detail: 'Sửa thất bại', life: 3000 });
    } else if (isDonViTooLong(form.donVi)) {
        toast.add({ severity: 'error', summary: 'Thông báo', detail: 'Sửa thất bại', life: 3000 });
    } else if (form.value == 0 || form.value < 100 || form.value > 5000) {
        toast.add({ severity: 'error', summary: 'Thông báo', detail: 'Sửa thất bại', life: 3000 });
    } else {
        const update = TrongLuongService.updateTrongLuong(props.myProp.id, form);
        toast.add({ severity: 'success', summary: 'Thông báo', detail: 'Sửa thành công', life: 3000 });
        productDialog.value = false;
    }
    updateProductDialog.value = false;
};
</script>
<template>
    <Button icon="pi pi-pencil" class="p-button-rounded p-button-success mr-2" @click="editProduct()" />

    <Dialog v-model:visible="productDialog" :style="{ width: '600px' }" header="Update trọng lượng" :modal="true" class="p-fluid">
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
