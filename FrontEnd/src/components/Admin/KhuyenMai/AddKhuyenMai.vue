<template>
    <Button label="Thêm mới" icon="pi pi-plus" class="p-button-success mr-2" @click="openNew" />
    <Dialog v-model:visible="addKhuyenMaiDialog" header="Flex Scroll" :style="{ width: '50vw' }" maximizable modal :contentStyle="{ height: '400px' }" class="p-fluid">
        <template #header>
            <div class="flex flex-column md:flex-row md:justify-content-between md:align-items-center">
                <h5 class="m-0">Khuyến Mại</h5>
            
            </div>
        </template>

        <div class="card p-fluid">
            <div class="formgrid grid">
                <!-- <div class="field col">
                    <label for="name2">Mã</label>
                    <InputText id="ma" v-model="ma" required="true" autofocus :class="{ 'p-invalid': maError }" />
                    <small class="p-error">{{ maError }}</small>
                </div> -->
                <div class="field col">
                    <label for="email2">Tên</label>
                    <InputText id="ten" v-model="ten" required="true" autofocus :class="{ 'p-invalid': tenError }" />
                    <small class="p-error">{{ tenError }}</small>
                </div>

                <div class="field col">
                    <label for="email2">Giá Trị Giảm</label>
                    <InputNumber id="giatri" v-model="giaTriGiam" :class="{ 'p-invalid': giaTriGiamError }" />
                    <small class="p-error">{{ giaTriGiamError }}</small>
                </div>
            </div>
            <div class="formgrid grid">
                <div class="field col">
                    <label for="name2">Ngày Bắt Đầu</label>
                    <Calendar id="thoiGianBatDau" v-model="thoiGianBatDau" :class="{ 'p-invalid': thoiGianBatDauError }" dateFormat="yy/mm/dd" showIcon />
                    <small class="p-error">{{ thoiGianBatDauError }}</small>
                </div>
                <div class="field col">
                    <label for="email2">Ngày Kết Thúc</label>
                    <Calendar id="thoiGianKetThuc" v-model="thoiGianKetThuc" :class="{ 'p-invalid': thoiGianKetThucError }" dateFormat="yy/mm/dd" showIcon />
                    <small class="p-error">{{ thoiGianKetThucError }}</small>
                </div>
            </div>
            <div class="formgrid grid">
                <div class="field col">
                    <label for="name2">Mô Tả</label>
                    <Textarea v-model="moTa" required="true" :autoResize="true" rows="3" cols="30" :class="{ 'p-invalid': moTaError }" />
                    <small class="p-error">{{ moTaError }}</small>
                </div>
                <!-- <div class="field col">
                    <label for="email2">Giá Trị Giảm</label>
                    <InputNumber id="giatri" v-model="giaTriGiam" :class="{ 'p-invalid': giaTriGiamError }" />
                    <small class="p-error">{{ giaTriGiamError }}</small>
                </div> -->
            </div>
            <!-- <div class="formgrid grid">
                <div class="field col">
                    <label for="name2">Mô Tả</label>
                    <Textarea v-model="moTa" required="true" :autoResize="true" rows="3" cols="30" :class="{ 'p-invalid': moTaError }" />
                    <small class="p-error">{{ moTaError }}</small>
                </div>
            </div> -->
        </div>

        <template #footer>
            <Button label="Huỷ" icon="pi pi-times" class="p-button-text" @click="hideDialog" />
            <Button type="submit" label="Lưu" class="p-button-text" icon="pi pi-check" @click="save" />
        </template>
    </Dialog>
</template>

<script setup>
import { ref } from 'vue';
import * as Yup from 'yup';
import { useField, useForm } from 'vee-validate';
import { useToast } from 'primevue/usetoast';
import { khuyenMaiStore } from '@/service/Admin/KhuyenMai/KhuyenMaiService.js';

const khuyenmaiService = khuyenMaiStore();
const submitted = ref(false);
const toast = useToast();
const addKhuyenMaiDialog = ref(false);

const openNew = () => {
    addKhuyenMaiDialog.value = true;
    submitted.value = false;
    resetForm();
};

const hideDialog = () => {
    addKhuyenMaiDialog.value = false;
    submitted.value = false;
    resetForm();
};

const schema = Yup.object().shape({
    // ma: Yup.string().required('Mã khuyến mại không được để trống').min(4, 'Tên khuyến mại phải có ít nhất 4 ký tự'),
    ten: Yup.string().required('Tên khuyến mại không được để trống').min(4, 'Tên khuyến mại phải có ít nhất 4 ký tự').max(100, 'Tên giới hạn 100 ký tự')
    .test('no-spaces', 'Tên không được chứa khoảng trắng', value => value && !/\s/.test(value)),
    moTa: Yup.string().required('Vui lòng điền mô tả khuyến mại').min(10, 'Mô tả khuyến mại phải có ít nhất 10 ký tự')
    .test('no-spaces', 'Tên không được chứa khoảng trắng', value => value && !/\s/.test(value)),
    thoiGianBatDau: Yup.date().nullable().required('Thời gian bắt đầu là bắt buộc').typeError('Vui lòng chọn ngày hợp lệ').min(new Date(new Date().setHours(0, 0, 0, 0)), 'Thời gian bắt đầu không thể nhỏ hơn thời gian hiện tại'),
    thoiGianKetThuc: Yup.date().nullable().min(Yup.ref('thoiGianBatDau'), 'Ngày kết thúc phải sau ngày bắt đầu').required('Thời gian kết thúc là bắt buộc').typeError('Vui lòng chọn ngày hợp lệ'),
    giaTriGiam: Yup.number().required('Bạn cần nhập giá trị giảm').typeError('Giá trị giảm phải là một số').min(1, 'Giá trị giảm phải lớn hơn hoặc bằng 1').nullable()
});

const { handleSubmit, resetForm } = useForm({
    validationSchema: schema
});
// const { value: ma, errorMessage: maError } = useField('ma');
const { value: ten, errorMessage: tenError } = useField('ten');
const { value: moTa, errorMessage: moTaError } = useField('moTa');
const { value: thoiGianBatDau, errorMessage: thoiGianBatDauError } = useField('thoiGianBatDau');
const { value: thoiGianKetThuc, errorMessage: thoiGianKetThucError } = useField('thoiGianKetThuc');
const { value: giaTriGiam, errorMessage: giaTriGiamError } = useField('giaTriGiam');

const save = handleSubmit(async () => {
    submitted.value = true;
    const form = {
        // ma: ma.value,
        ten: ten.value,
        thoiGianBatDau: thoiGianBatDau.value,
        thoiGianKetThuc: thoiGianKetThuc.value,
        moTa: moTa.value,
        giaTriGiam: giaTriGiam.value
    };

    khuyenmaiService.createKhuyenMai(form);
    toast.add({ severity: 'success', summary: 'Successful', detail: 'Thêm mới thành công', life: 3000 });
    resetForm();
    addKhuyenMaiDialog.value = false;
});
</script>
