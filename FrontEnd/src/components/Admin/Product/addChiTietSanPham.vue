<script setup>
import * as yup from 'yup';
import { useForm, useField } from 'vee-validate';
import { ref, onMounted, onBeforeMount, defineEmits } from 'vue';
import { useToast } from 'primevue/usetoast';
import { SizeStore } from '@/service/Admin/Size/SizeService';
import { ProductStore } from '@/service/Admin/product/product.api';
import { TrongLuongStore } from '@/service/Admin/TrongLuong/TrongLuong.api';
import { useMauSacService } from '@/service/Admin/MauSac/MauSacService';
import { khuyenMaiStore } from '@/service/Admin/KhuyenMai/KhuyenMaiService.js';
import TableSize from '@/components/Admin/Product/DataTableSize.vue';
import TableMauSac from './DataTableMauSac.vue';
import TableTrongLuong from './DataTableTrongLuong.vue';
import TableKhuyenMai from './DataTableKhuyenMai.vue';


const trongLuongStore = TrongLuongStore();
const khuyenmaiService = khuyenMaiStore();
const mauSacStore = useMauSacService();
const useSizeService = SizeStore();
const productStore = ProductStore();
const toast = useToast();
const product = ref({});
const submitted = ref(false);
const productDialog = ref(false);
const props = defineProps({
    idProduct: Number
});

// confirm thêm
const addProductDialog = ref(false);

//hiện confirm
const confirmAddProduct = () => {
    addProductDialog.value = true;
};

const schema = yup.object().shape({
    anh: yup.string().required('bạn cần chọn ảnh'),
    soLuongTon: yup.number().required('số lượng không được để trống').typeError('Số lượng tồn phải là một số').min(1, 'Số lượng phải lớn hơn hoặc bằng 1').max(1000, 'số lượng quá lớn').nullable(),
    giaBan: yup
        .number()
        .required('Giá bán không được để trống')
        .min(50000, 'giá phải lớn hơn hoặc bằng 50.000 đ')
        .max(10000000, 'Giá bán không lớn hơn 10.000.000 đ'),
        // .when(['giaNhap'], (giaBan, schema) => {
        //     return schema.test({
        //         test: function (value) {
        //             const giaNhap = this.resolve(yup.ref('giaNhap'));
        //             if (value < giaNhap) {
        //                 toast.add({ severity: 'error', summary: 'Error ', detail: 'Giá Nhập không được nhỏ hơn giá bán', life: 3000 });
        //             }
        //             return true;
        //         },
        //         message: 'Giá bán phải nhỏ hơn giá nhập'
        //     });
        // }),
    // giaNhap: yup.number().required('Giá nhập không được để trống').min(50000, 'giá phải lớn hơn hoặc bằng 50.000 đ').max(10000000, 'Giá bán không lớn hơn  10.000.000 đ'),
    trongLuong: yup.string().required('vui lòng chọn trọng lượng sản phẩm'),
    size: yup.number().required('vui lòng chọn size cho sản phẩm'),
    mauSac: yup.number().required('vui lòng chọn màu sắc sản phẩm'),
    trangThai: yup.number().required('vui lòng chọn trạng thái '),
});

const { handleSubmit, resetForm } = useForm({
    validationSchema: schema
});

const { value: idSP, errorMessage: idError } = useField('idSP');
const { value: anh, errorMessage: anhError } = useField('anh');
const { value: soluong, errorMessage: soLuongError } = useField('soLuongTon');
const { value: GiaBan, errorMessage: giaBanError } = useField('giaBan');
// const { value: GiaNhap, errorMessage: giaNhapError } = useField('giaNhap');
const { value: TrongLuong, errorMessage: trongLuongError } = useField('trongLuong');
const { value: idKhuyenMai, errorMessage: idKhuyenMaiError } = useField('idKhuyenMai');
const { value: MauSac, errorMessage: mauSacError } = useField('mauSac');
const { value: size, errorMessage: sizeError } = useField('size');
const { value: TrangThai, errorMessage: TrangThaiSacError } = useField('trangThai');
//add

const emit = defineEmits(['add:myProp'])
const onSubmit = handleSubmit(async (values) => {
    submitted.value = true;
    if (values.idKhuyenMai == null || values.idKhuyenMai == '') {
        values.idKhuyenMai = '';
    }
    const form = {
        idSP: props.idProduct,
        anh: values.anh,
        soLuongTon: values.soLuongTon,
        giaBan: values.giaBan,
        // giaNhap: values.giaNhap,
        trongLuong: values.trongLuong,
        idKhuyenMai: values.idKhuyenMai,
        mauSac: values.mauSac,
        size: values.size,
        trangThai: values.trangThai,
    }
    const check = await productStore.checkDuplicateSPCT(form.size, form.mauSac, props.idProduct);
    if (check) {
        toast.add({ severity: 'error', summary: 'Error', detail: 'sản phẩm đã tồn tại', life: 3000 });
    } else {
        const data = await productStore.addSPCT(form);
        emit('add:myProp', data)
        toast.add({ severity: 'success', summary: 'Success Message', detail: 'update thành công', life: 3000 });
        reset();
        productDialog.value = false;
    }
});

const reset = () => {
    resetForm();
    selectedSize.value = null;
    selectedKhuyenMai.value = null;
    selectedMauSac.value = null;
    selectedTrongLuong.value = null;
}

// mở form
const openNew = () => {
    // console.log(props.idProduct)
    product.value = {};
    submitted.value = false;
    productDialog.value = true;
    anhs.value = 'https://cdn-icons-png.flaticon.com/512/2956/2956744.png';
};

const anhs = ref(null);
function onFileInputImage(event) {
    const files = event.target.files;
    // Lặp qua từng tệp trong mảng files
    for (const file of files) {
        const objectURL = URL.createObjectURL(file);
        anhs.value = objectURL;
        // Gán giá trị cho phần tử có id là 'imagesChinh' (thay đổi id nếu cần)
        const basePath = "D:\\imgDATN\\"; // Đường dẫn cố định
        const fileName = basePath + file.name;
        anh.value = fileName;
    }
}

//đóng form
const hideDialog = () => {
    productDialog.value = false;
    submitted.value = false;
};

const selectedMauSac = ref(null);
const selectedSize = ref(null);
const selectedKhuyenMai = ref(null);
//save
const saveProduct = () => {
    addProductDialog.value = true;
};
const dataSize = ref([]);
const loadDataSize = async () => {
    await useSizeService.fetchData();
    dataSize.value = useSizeService.dataByStatus1;
};
const dataMauSac = ref([]);
const loadDataMauSac = async () => {
    await mauSacStore.fetchDataByStatus(1);
    dataMauSac.value = mauSacStore.dataByStatus1;
    const lstMau = dataMauSac.value;
    dataMauSac.value = lstMau;
};

const onMauSacChange = () => {
    if (selectedMauSac.value) {
        MauSac.value = selectedMauSac.value.id;
    } else {
        MauSac.value = null;
    }
};

const onSizeChange = () => {
    if (selectedSize.value) {
        size.value = selectedSize.value.id;
    } else {
        size.value = null;
    }
};


onMounted(() => {
    loadDataSize();
    loadDataTrongLuong();
    loadDataMauSac();
    loadDataKhuyenmai();
});

const khuyenmais = ref([]);
const khuyenmai = ref([]);
const loadDataKhuyenmai = async () => {
    await khuyenmaiService.getKhuyenMai();
    khuyenmai.value = khuyenmaiService.data;
    khuyenmais.value = khuyenmai.value.filter(data => data.trangThai == 0 || data.trangThai == 2);

};

const selectedTrongLuong = ref(null);
const dataTrongLuong = ref([]);
const loadDataTrongLuong = async () => {
    await trongLuongStore.fetchDataByStatus(1);
    dataTrongLuong.value = trongLuongStore.dataByStatus1;
};

const onTrongLuongChange = () => {
    if (selectedTrongLuong.value) {
        TrongLuong.value = selectedTrongLuong.value.id;
        //    console.log(TrongLuong.value)
    } else {
        TrongLuong.value = null;
    }
};

const onTrongLuongKhuyenMai = () => {
    if (selectedKhuyenMai.value) {
        idKhuyenMai.value = selectedKhuyenMai.value.id;
        //    console.log(TrongLuong.value)
    } else {
        idKhuyenMai.value = null;
    }
};

const getStatusLabel = (trangThai) => {
    switch (trangThai) {
        case 0:
            return '- Còn hạn';

        case 1:
            return '- Hết hạn';

        case 2:
            return '- Chưa hoạt động';

        case 3:
            return '- Hết khuyến mại';

        default:
            return null;
    }
};
</script>
<template>
    <Button label="" icon="pi pi-plus" class="p-button-success mr-2" @click="openNew"
        style="width: 50px; height: 40px; margin-left: 20px" />

    <Dialog v-model:visible="productDialog" :style="{ width: '700px' }" header="Thêm sản phẩm chi tiết" :modal="true"
        class="p-fluid">
        <div class="card">
            <form @submit="onSubmit">
                <div class="p-fluid formgrid grid">

                    <div class="Field col-12 md:col-6" style="margin-bottom: 30px">
                        <div class="Field col-12 md:col-12" style="margin-bottom: 30px">
                            <span class="p-float-label">
                                <InputNumber id="soluong" v-model="soluong" :class="{ 'p-invalid': soLuongError }">
                                </InputNumber>
                                <label for="SoLuongTon">Số lượng tồn</label>
                            </span>
                            <small class="p-error">{{ soLuongError }}</small>
                        </div>
<!-- 
                        <div class="Field col-12 md:col-12" style="margin-bottom: 30px">
                            <span class="p-float-label">
                                <InputNumber id="Field" v-model="GiaNhap" :class="{ 'p-invalid': giaNhapError }">
                                </InputNumber>
                                <label for="Field">Giá Nhập</label>
                            </span>
                            <small class="p-error">{{ giaNhapError }}</small>
                        </div> -->
                        <div class="Field col-12 md:col-12" style="margin-bottom: 30px">
                            <span class="p-float-label">
                                <InputNumber id="number-input" name="GiaBan" v-model="GiaBan"
                                    :class="{ 'p-invalid': giaBanError }"></InputNumber>
                                <label for="Field">Giá bán</label>
                            </span>
                            <small class="p-error">{{ giaBanError }}</small>
                        </div>
                        <div class="field col-12 md:col-12" style="margin-bottom: 30px">
                                    <label for="address">Trạng thái</label>
                                    <div class="flex flex-wrap gap-3">
                                        <div class="flex align-items-center">
                                            <RadioButton v-model="TrangThai" inputId="ingredient1" name="pizza" value="1"
                                                :class="{ 'p-invalid': TrangThaiSacError }" />
                                            <label for="ingredient1" class="ml-2">Sẵn sàng để bán</label>
                                        </div>
                                        <div class="flex align-items-center">
                                            <RadioButton v-model="TrangThai" inputId="ingredient2" name="pizza" value="3"
                                                :class="{ 'p-invalid': TrangThaiSacError }" />
                                            <label for="ingredient2" class="ml-2">tồn kho</label>
                                        </div>
                                    </div>
                                    <small class="p-error">{{ TrangThaiSacError }}</small>
                                </div>
                    </div>
                    <div class="Field col-12 md:col-6" style="margin-bottom: 30px">
                        <div class="Field col-12 md:col-12"
                            style="margin-bottom: 30px;margin-left: 0px; height: 200px; margin-top: 60px; display: inline-flex; justify-content: center; align-items: center;display: block">
                            <div class="t"
                                style="border: 1px solid black; border-radius: 10px; width: 230px; height: 190px; margin-top: -60px">
                                <img :src="anhs" alt=""
                                    style="width: 200px; height: 170px; top: 50%; left: 50%; transform: translate(4%, 2%)" />
                            </div>
                            <div class="buton" style="margin-top: 10px">
                                <FileUpload mode="basic" name="demo[]" accept="image/*" :maxFileSize="1000000"
                                    @input="onFileInputImage" style="display: flex; width: 200px; margin-left: 20px;" />
                            </div>
                            <small class="p-error">{{ anhError }}</small>
                        </div>
                    </div>
                    <div class="Field col-12 md:col-12" style="margin-bottom: 30px; margin-top: 20px;">
                        <div class="p-fluid formgrid grid">
                            <div class="Field col-12 md:col-6" style="margin-bottom: 30px">
                                <div style="display: flex">
                                    <span class="p-float-label" style="width: 239px">
                                        <Dropdown id="dropdown" :options="dataTrongLuong" v-model="selectedTrongLuong"
                                            optionLabel="value" :class="{ 'p-invalid': trongLuongError }"
                                            @change="onTrongLuongChange">
                                        </Dropdown>
                                        <label for="dropdown">Trọng Lượng</label>
                                    </span>
                                    <TableTrongLuong :tableId="'TableTrongLuong'" :rightGhId="'right_ghTrongLuong'"
                                        :tableClass="'TableTrongLuong'" :rightGhClass="'right_ghTrongLuong'" />
                                </div>
                                <small class="p-error">{{ trongLuongError }}</small>
                            </div>
                            <div class="Field col-12 md:col-6" style="margin-bottom: 30px">
                                <div style="display: flex">
                                    <span class="p-float-label" style="width: 239px">
                                        <Dropdown id="dropdown" :options="dataMauSac" v-model="selectedMauSac"
                                            optionLabel="ten" :class="{ 'p-invalid': mauSacError }"
                                            @change=" onMauSacChange()">
                                        </Dropdown>
                                        <label for="dropdown">Màu Sắc</label>
                                    </span>
                                    <TableMauSac :tableId="'TableMauSac'" :rightGhId="'right_ghMauSac'"
                                        :tableClass="'TableMauSac'" :rightGhClass="'right_ghMauSac'" />
                                </div>
                                <small class="p-error">{{ mauSacError }}</small>
                            </div>

                            <div class="Field col-12 md:col-6" style="margin-bottom: 30px">
                                <div style="display: flex">
                                    <span class="p-float-label" style="width: 239px">
                                        <Dropdown id="dropdown" :options="dataSize" v-model="selectedSize" optionLabel="ten"
                                            :class="{ 'p-invalid': sizeError }" @change="onSizeChange">
                                        </Dropdown>
                                        <label for="dropdown">Size</label>
                                    </span>
                                    <TableSize :tableId="'TableMauSac'" :rightGhId="'right_ghMauSac'"
                                        :tableClass="'TableMauSac'" :rightGhClass="'right_ghMauSac'" />
                                </div>
                                <small class="p-error">{{ sizeError }}</small>
                            </div>
                            <div class="Field col-12 md:col-6" style="margin-bottom: 30px">
                                <div style="display: flex">
                                    <span class="p-float-label" style="width: 239px">
                                        <Dropdown id="dropdown" :options="khuyenmais" v-model="selectedKhuyenMai"
                                            :optionLabel="(option) => `${option.ten}  ${getStatusLabel(option.trangThai)}`"
                                            @change="onTrongLuongKhuyenMai">
                                        </Dropdown>
                                        <label for="dropdown">Khuyễn Mại</label>
                                    </span>
                                    <TableKhuyenMai :tableId="'TableTrongLuong'" :rightGhId="'right_ghTrongLuong'"
                                        :tableClass="'TableTrongLuong'" :rightGhClass="'right_ghTrongLuong'" />
                                </div>

                            </div>
                        </div>
                    </div>

                    <div style="width: 1000px; text-align: center;display: flex; margin-left: 170px;">

                        <Button class="p-button-outlined" outlined severity="secondary"
                            style="width: 100px; height: auto; margin: 10px" @click="reset()" label="clear"></Button>
                        <Button type="submit" class="p-button-outlined" style="width: 100px; height: auto; margin: 10px"
                            label="Lưu"></Button>
                    </div>
                </div>

            </form>
        </div>

    </Dialog>
    <!-- </div>
        </div>
    </div> -->
</template>
