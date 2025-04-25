<template>
    <Button icon="pi pi-pencil" class="p-button-rounded p-button-success mr-2" @click="editKhuyenMai()" />
    <Dialog v-model:visible="updateKhuyenMaiDialog" header="Flex Scroll" :style="{ width: '50vw' }" maximizable modal :contentStyle="{ height: '400px' }" class="p-fluid">
        <template #header>
            <div class="flex flex-column md:flex-row md:justify-content-between md:align-items-center">
                <h5 class="m-0">Cập nhật Khuyến Mại</h5>
            </div>
        </template>
        <div class="card p-fluid">
            <div class="formgrid grid">
                <div class="field col">
                    <label for="name2">Mã</label>
                    <InputText id="ma" v-model.trim="ma" required="true" autofocus disabled />
                </div>
                <div class="field col">
                    <label for="email2">Tên</label>
                    <InputText id="ten" v-model.trim="ten" required="true" autofocus :class="{ 'p-invalid': tenError }" />
                    <small class="p-error">{{ tenError }}</small>
                </div>
            </div>
            <div class="formgrid grid">
                <div class="field col">
                    <label for="name2">Ngày Bắt Đầu</label>
                    <!-- <div>
                        <input id="thoiGianBatDau" type="datetime-local" v-model="thoiGianBatDau" style="height: 40px; width: 100%" />
                    </div> -->

                    <Calendar id="thoiGianBatDau" v-model="thoiGianBatDau" :class="{ 'p-invalid': thoiGianBatDauError }"  dateFormat="yyyy/MM/dd " showIcon />
                    <small class="p-error">{{ thoiGianBatDauError }}</small>
                </div>
                <div class="field col">
                    <label for="email2">Ngày Kết Thúc</label>
                    <!-- <div>
                        <input id="thoiGianKetThuc" type="datetime-local" v-model="thoiGianKetThuc" style="height: 40px; width: 100%" />
                    </div> -->
                    <Calendar id="thoiGianKetThuc" v-model="thoiGianKetThuc" :class="{ 'p-invalid': thoiGianKetThucError }"  dateFormat="yyyy/MM/dd " showIcon />
                    <small class="p-error">{{ thoiGianKetThucError }}</small>
                </div>
            </div>
            <div class="formgrid grid">
                <div class="field col">
                    <label for="name2">Mô Tả</label>
                    <Textarea v-model.trim="moTa" :autoResize="true" rows="3" cols="30" :class="{ 'p-invalid': moTaError }" />
                    <small class="p-error">{{ moTaError }}</small>
                </div>
                <div class="field col">
                    <label for="email2">Giá Trị Giảm</label>
                    <InputNumber id="giatri" v-model="giaTriGiam" :class="{ 'p-invalid': giaTriError }" />
                    <small class="p-error">{{ giaTriGiamError }}</small>
                </div>
            </div>
            <!-- <div class="formgrid grid">
                <div class="field col">
                    <label for="name2">Mô Tả</label>
                    <Textarea v-model.trim="moTa" :autoResize="true" rows="3" cols="30" :class="{ 'p-invalid': moTaError }" />
                    <small class="p-error">{{ moTaError }}</small>
                </div>
            </div> -->
        </div>

        <template #footer>
            <Button label="Huỷ" icon="pi pi-times" class="p-button-text" @click="hideDialog" />
            <Button label="Cập nhật" icon="pi pi-check" @click="update" />
        </template>
    </Dialog>
</template>

<script setup>
import { ref } from 'vue';
import { useToast } from 'primevue/usetoast';
import * as Yup from 'yup';
import { useField, useForm } from 'vee-validate';
import { khuyenMaiStore } from '@/service/Admin/KhuyenMai/KhuyenMaiService.js';

const khuyenmaiService = khuyenMaiStore();
const toast = useToast();
const khuyenmais = ref([]);
const updateKhuyenMaiDialog = ref(false);
const props = defineProps({
    myProp: {}
});

const schema = Yup.object().shape({
    ten: Yup.string().required('Tên sản phẩm không được để trống').min(4, 'Tên sản phẩm phải có ít nhất 4 ký tự') .test('no-spaces', 'Tên không được chứa khoảng trắng', value => value && !/\s/.test(value)),
    moTa: Yup.string().required('Vui lòng điền mô tả voucher').min(10, 'Mô tả voucher phải có ít nhất 10 ký tự'),
    thoiGianBatDau: Yup.date().nullable().required('Thời gian bắt đầu là bắt buộc').typeError('Vui lòng chọn ngày hợp lệ'),
    thoiGianKetThuc: Yup.date().nullable().min(Yup.ref('thoiGianBatDau'), 'Ngày kết thúc phải sau ngày bắt đầu').required('Thời gian kết thúc là bắt buộc').typeError('Vui lòng chọn ngày hợp lệ'),
    giaTriGiam: Yup.number().required('Bạn cần nhập giá trị khuyến mại').typeError('Giá trị phải là một số').min(1, 'Giá trị phải lớn hơn hoặc bằng 1').nullable()
});

const { handleSubmit, resetForm } = useForm({
    validationSchema: schema
});

const { value: ma } = useField('ma');
const { value: ten, errorMessage: tenError } = useField('ten');
const { value: moTa, errorMessage: moTaError } = useField('moTa');
const { value: thoiGianBatDau, errorMessage: thoiGianBatDauError } = useField('thoiGianBatDau');
const { value: thoiGianKetThuc, errorMessage: thoiGianKetThucError } = useField('thoiGianKetThuc');
const { value: giaTriGiam, errorMessage: giaTriGiamError } = useField('giaTriGiam');

const editKhuyenMai = () => {
    ma.value = props.myProp.ma;
    ten.value = props.myProp.ten;
    thoiGianBatDau.value = props.myProp.thoiGianBatDau;
    thoiGianKetThuc.value = props.myProp.thoiGianKetThuc;
    moTa.value = props.myProp.moTa;
    giaTriGiam.value = props.myProp.giaTriGiam;
    updateKhuyenMaiDialog.value = true;
};

const hideDialog = () => {
    updateKhuyenMaiDialog.value = false;
    resetForm();
};

const loadDataKhuyenmai = async () => {
    await khuyenmaiService.getKhuyenMai();
    khuyenmais.value = khuyenmaiService.data;
};

const update = handleSubmit(async () => {
    const form = {
        ten: ten.value,
        thoiGianBatDau: thoiGianBatDau.value,
        thoiGianKetThuc: thoiGianKetThuc.value,
        moTa: moTa.value,
        giaTriGiam: giaTriGiam.value
    };

    khuyenmaiService.updateKhuyenMai(form, props.myProp.id);
    // loadDataKhuyenmai();
    toast.add({ severity: 'success', summary: 'Successful', detail: 'Cập nhật thành công', life: 3000 });
    updateKhuyenMaiDialog.value = false;
});
</script>
