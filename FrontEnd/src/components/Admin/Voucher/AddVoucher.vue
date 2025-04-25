<script setup>
import { ref } from 'vue';
import * as Yup from 'yup';
import { useField, useForm } from 'vee-validate';
import { useToast } from 'primevue/usetoast';
import { voucherStore } from '@/service/Admin/Voucher/VoucherService.js';

const VoucherService = voucherStore();
const submitted = ref(false);
const toast = useToast();
const voucherDialog = ref(false);

const openNew = () => {
    voucherDialog.value = true;
    submitted.value = false;
    resetForm();
};

const hideDialog = () => {
    voucherDialog.value = false;
    submitted.value = false;
    resetForm();
};

const schema = Yup.object().shape({
    ten: Yup.string().required('Tên sản phẩm không được để trống').min(4, 'Tên sản phẩm phải có ít nhất 4 ký tự') .test('no-spaces', 'Tên không được chứa khoảng trắng', value => value && !/\s/.test(value)),
    moTa: Yup.string().required('Vui lòng điền mô tả voucher').min(10, 'Mô tả voucher phải có ít nhất 10 ký tự') .test('no-spaces', 'Tên không được chứa khoảng trắng', value => value && !/\s/.test(value)),
    soLuong: Yup.number().required('Bạn cần nhập số lượng voucher').typeError('Số lượng voucher phải là một số').min(1, 'Số lượng phải lớn hơn hoặc bằng 1').nullable(),
    giaTriGiam: Yup.number().required('Bạn cần nhập giá trị giảm của voucher').typeError('Giá trị giảm phải là số nguyên').min(1, 'Giá trị giảm phải lớn hơn hoặc bằng 1').max(100, 'Giá trị phải nhỏ hơn hoặc 100').nullable(),
    thoiGianBatDau: Yup.date()
        .nullable()
        .required('Thời gian bắt đầu là bắt buộc')
        .typeError('Vui lòng chọn ngày hợp lệ')
        .min(new Date(new Date().setHours(0, 0, 0, 0)), 'Thời gian bắt đầu không thể nhỏ hơn thời gian hiện tại'),
    thoiGianKetThuc: Yup.date().nullable().min(Yup.ref('thoiGianBatDau'), 'Thời gian kết thúc không thể nhỏ hơn thời gian bắt đầu').required('Thời gian kết thúc là bắt buộc').typeError('Vui lòng chọn ngày hợp lệ'),
    giamToiDa: Yup.number().required('Bạn cần nhập giá trị giảm').typeError('Giá trị giảm phải là một số').min(1, 'Giá trị giảm phải lớn hơn hoặc bằng 1').nullable()
});

const { handleSubmit, resetForm } = useForm({
    validationSchema: schema
});

const { value: ten, errorMessage: tenError } = useField('ten');
const { value: giaTriGiam, errorMessage: giaTriGiamError } = useField('giaTriGiam');
const { value: moTa, errorMessage: moTaError } = useField('moTa');
const { value: soLuong, errorMessage: soLuongError } = useField('soLuong');
const { value: thoiGianBatDau, errorMessage: thoiGianBatDauError } = useField('thoiGianBatDau');
const { value: thoiGianKetThuc, errorMessage: thoiGianKetThucError } = useField('thoiGianKetThuc');
const { value: giamToiDa, errorMessage: giamToiDaError } = useField('giamToiDa');

const clearForm = () => {
    ten.value = '';
    (thoiGianBatDau.value = ''), (thoiGianKetThuc.value = ''), (moTa.value = '');
    soLuong.value = '';
    giamToiDa.value = '';
};

const save = handleSubmit(async () => {
    submitted.value = true;
    const form = {
        ten: ten.value,
        thoiGianBatDau: thoiGianBatDau.value,
        thoiGianKetThuc: thoiGianKetThuc.value,
        moTa: moTa.value,
        soLuong: soLuong.value,
        giamToiDa: giamToiDa.value,
        giaTriGiam: giaTriGiam.value
    };
    const response = VoucherService.createVoucher(form);
    toast.add({ severity: 'success', summary: 'Successful', detail: 'Thêm mới thành công', life: 3000 });
    resetForm();
    voucherDialog.value = false;
});
</script>

<template>
    <Button label="Thêm mới" icon="pi pi-plus" class="p-button-success mr-2" @click="openNew" />
    <Dialog v-model:visible="voucherDialog" :style="{ width: '450px' }" header="Thêm Voucher" :modal="false" class="p-fluid">
        <div class="field">
            <label for="ten">Tên</label>
            <InputText id="ten" v-model.trim="ten" :class="{ 'p-invalid': tenError }" autofocus />
            <small class="p-error">{{ tenError }}</small>
        </div>
        <div class="field">
            <label for="thoiGianBatDau">Ngày Bắt Đầu</label>
            <!-- <Calendar id="thoiGianBatDau" v-model="thoiGianBatDau" :class="{ 'p-invalid': thoiGianBatDauError }" dateFormat="yy/mm/dd" showIcon /> -->
            <div>
                <input id="thoiGianBatDau" type="datetime-local" v-model="thoiGianBatDau" style="height: 40px; width: 100%" />
            </div>
            <small class="p-error">{{ thoiGianBatDauError }}</small>
        </div>
        <div class="field">
            <label for="thoiGianKetThuc">Ngày Kết Thúc</label>
            <!-- <Calendar id="thoiGianKetThuc" v-model="thoiGianKetThuc" :class="{ 'p-invalid': thoiGianKetThucError }" dateFormat="yy/mm/dd" showIcon /> -->
            <div>
                <input id="thoiGianKetThuc" type="datetime-local" v-model="thoiGianKetThuc" style="height: 40px; width: 100%" />
            </div>
            <small class="p-error">{{ thoiGianKetThucError }}</small>
        </div>
        <div class="field">
            <label for="moTa">Mô Tả</label>
            <InputText id="moTa" v-model.trim="moTa" :class="{ 'p-invalid': moTaError }" />
            <small class="p-error">{{ moTaError }}</small>
        </div>

        <div class="field">
            <label for="soLuong">Số Lượng</label>
            <InputText id="soLuong" v-model="soLuong" :class="{ 'p-invalid': soLuongError }" />
            <small class="p-error">{{ soLuongError }}</small>
        </div>

        <div class="field">
            <label for="giaTriGiam">Giá trị giảm(%)</label>
            <InputText id="soLuong" v-model="giaTriGiam" :class="{ 'p-invalid': giaTriGiamError }" />
            <small class="p-error">{{ giaTriGiamError }}</small>
        </div>

        <div class="field">
            <label for="price">Giảm Tối Đa</label>
            <InputNumber id="price" v-model="giamToiDa" :class="{ 'p-invalid': giamToiDa }" />
            <small class="p-error">{{ giamToiDaError }}</small>
        </div>

        <template #footer>
            <Button label="Huỷ" icon="pi pi-times" class="p-button-text" @click="hideDialog" />
            <Button type="submit" label="Lưu" icon="pi pi-check" class="p-button-text" @click="save" />
        </template>
    </Dialog>
</template>
