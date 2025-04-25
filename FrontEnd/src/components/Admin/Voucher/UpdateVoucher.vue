<script setup>
import { ref, watch } from 'vue';
import { voucherStore } from '@/service/Admin/Voucher/VoucherService.js';
import { useToast } from 'primevue/usetoast';
import * as Yup from 'yup';
import { useField, useForm } from 'vee-validate';
const toast = useToast();
const VoucherService = voucherStore();
const check = ref(false);
const props = defineProps({
    myProp: {}
});
const updatevoucherDialog = ref(false);
const schema = Yup.object().shape({
    ten: Yup.string().required('Tên sản phẩm không được để trống').min(4, 'Tên sản phẩm phải có ít nhất 4 ký tự') .test('no-spaces', 'Tên không được chứa khoảng trắng', value => value && !/\s/.test(value)),
    moTa: Yup.string().required('Vui lòng điền mô tả voucher').min(10, 'Mô tả voucher phải có ít nhất 10 ký tự') .test('no-spaces', 'Tên không được chứa khoảng trắng', value => value && !/\s/.test(value)),
    giaTriGiam: Yup.number().required('Bạn cần nhập giá trị giảm của voucher').typeError('Giá trị giảm phải là số nguyên').min(1, 'Giá trị giảm phải lớn hơn hoặc bằng 1').max(100, 'Giá trị phải nhỏ hơn hoặc 100').nullable(),
    soLuong: Yup.number().required('Bạn cần nhập số lượng voucher').typeError('Số lượng voucher phải là một số').min(1, 'Số lượng phải lớn hơn hoặc bằng 1').nullable(),
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
const { value: moTa, errorMessage: moTaError } = useField('moTa');
const { value: giaTriGiam, errorMessage: giaTriGiamError } = useField('giaTriGiam');
const { value: soLuong, errorMessage: soLuongError } = useField('soLuong');
const { value: thoiGianBatDau, errorMessage: thoiGianBatDauError } = useField('thoiGianBatDau');
const { value: thoiGianKetThuc, errorMessage: thoiGianKetThucError } = useField('thoiGianKetThuc');
const { value: giamToiDa, errorMessage: giamToiDaError } = useField('giamToiDa');

watch(thoiGianBatDau, (newVal) => {
    // check.value = true;
    if (thoiGianBatDau.value != props.myProp.thoiGianBatDau) check.value = true;
});
const openUpdateDialog = () => {
    ten.value = props.myProp.ten;
    thoiGianBatDau.value = props.myProp.thoiGianBatDau;
    thoiGianKetThuc.value = props.myProp.thoiGianKetThuc;
    moTa.value = props.myProp.moTa;
    soLuong.value = props.myProp.soLuong;
    giamToiDa.value = props.myProp.giamToiDa;
    giaTriGiam.value = props.myProp.giaTriGiam;
    updatevoucherDialog.value = true;
};

const hideDialogVoucher = () => {
    updatevoucherDialog.value = false;
    resetForm();
};

const update = handleSubmit(async () => {

   
    const form = {
        ten: ten.value,
        thoiGianBatDau: thoiGianBatDau.value,
        giaTriGiam: giaTriGiam.value,
        thoiGianKetThuc: thoiGianKetThuc.value,
        moTa: moTa.value,
        soLuong: soLuong.value,
        giamToiDa: giamToiDa.value
    };
    
    
    VoucherService.updateVoucher(form, props.myProp.id);
    toast.add({ severity: 'success', summary: 'Successful', detail: 'Cập nhật thành công', life: 3000 });
    updatevoucherDialog.value = false;
});
</script>
<template>
    <Button icon="pi pi-pencil" class="p-button-rounded p-button-success mr-2" @click="openUpdateDialog()" />
    <Dialog v-model:visible="updatevoucherDialog" :style="{ width: '450px' }" header="Cập nhật Voucher" :modal="true" class="p-fluid">
        <div class="field">
            <label for="ten">Tên</label>
            <InputText id="ten" v-model="ten" :class="{ 'p-invalid': tenError }" required="true" />
            <small class="p-error">{{ tenError }}</small>
        </div>
        <div class="field">
            <label for="thoiGianBatDau">Ngày Bắt Đầu</label>
            <!-- <Calendar id="thoiGianBatDau" v-model="thoiGianBatDau" :class="{ 'p-invalid': thoiGianBatDauError }" dateFormat="yy/mm/dd" showIcon /> -->
            <div>
                <input id="thoiGianBatDau" type="datetime-local" v-model="thoiGianBatDau" style="height: 40px; width: 100%" />
            </div>
            <small class="p-error" v-if="check">{{ thoiGianBatDauError }}</small>
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
            <InputNumber id="price" v-model="giamToiDa" :class="{ 'p-invalid': giamToiDaError }" />
            <small class="p-error">{{ giamToiDaError }}</small>
        </div>

        <template #footer>
            <Button label="Huỷ" icon="pi pi-times" class="p-button-text" @click="hideDialogVoucher" />
            <Button type="submit" label="Cập nhật" icon="pi pi-check" class="p-button-text" @click="update" />
        </template>
    </Dialog>
</template>
