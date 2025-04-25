<script setup>
import { useForm, useField, defineRule } from 'vee-validate';
import * as yup from 'yup';
import { reactive, ref, computed, onMounted, watch } from 'vue';
import TableLoai from './DataTableLoai.vue';
import TableSize from './DataTableSize.vue';
import TableThuongHieu from './DataTableThuongHieu.vue';
import TableMauSac from './DataTableMauSac.vue';
import TablevatLieu from './DataTableVatLieu.vue';
import TableTrongLuong from './DataTableTrongLuong.vue';
import { ProductStore } from '@/service/Admin/product/product.api';
import { useToast } from 'primevue/usetoast';
import { useCounterStore } from '@/service/Admin/ThuongHieu/ThuongHieuService.js';
import { SizeStore } from '@/service/Admin/Size/SizeService';
import { useLoaiService } from '@/service/Admin/Loai/LoaiService';
import { useMauSacService } from '@/service/Admin/MauSac/MauSacService';
import { TrongLuongStore } from '@/service/Admin/TrongLuong/TrongLuong.api';
import { VatLieuStore } from '@/service/Admin/VatLieu/VatLieu.api';

const toast = useToast();
const productStore = ProductStore();
const thuongHieuService = useCounterStore();
const sizeStore = SizeStore();
const mauSacStore = useMauSacService();
const loaiStore = useLoaiService();
const trongLuongStore = TrongLuongStore();
const vatLieuStore = VatLieuStore();

const schema = yup.object().shape({
    ten: yup
        .string()
        .required('Tên sản phẩm không được để trống')
        .min(4, 'Tên sản phẩm phải có ít nhất 4 ký tự')
        .matches(/^[a-zA-Z0-9đĐáÁàÀảẢãÃạẠăĂắẮằẰẳẲẵẴặẶâÂấẤầẦẩẨẫẪậẬêÊếẾềỀểỂễỄệỆôÔốỐồỒổỔỗỖộỘơƠớỚờỜởỞỡỠợỢùÙúÚụỤủỦũŨưỨỨửỬữỮựỰýÝỳỲỷỶỹỸỵỴ\s\-]*$/, 'Tên không được chứa kí tự đặc biệt!')
        .test('no-spaces', 'Tên không được chứa khoảng trắng', value => value && !/\s/.test(value)),
    //  soLuongTon: yup.number().required('số lượng không được để trống').typeError('Số lượng tồn phải là một số').min(1, 'Số lượng phải lớn hơn hoặc bằng 1').max(1000, 'số lượng quá lớn').nullable(),
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
    quaiDeo: yup.string().required('Bạn cần chọn quai đeo cho sản phẩm'),
    loai: yup.number().required('loại sản phẩm không được để trống'),
    thuongHieu: yup.number().required('vui lòng chọn Thương hiệu sản phẩm '),
    idMauSac: yup.array().required('vui lòng chọn màu sắc sản phẩm '),
    vatLieu: yup.number().required(' vui lòng chọn Vật liệu sản phẩm '),
    demLot: yup.string().required(' vui lòng chọn đệm lót sản phẩm '),
    soLuongSize: yup.number().required(' vui lòng nhập số lượng').typeError('Số lượng size phải là một số').min(1, 'Số lượng phải lớn hơn hoặc bằng 1').nullable(),
    trongLuong: yup.string().required('vui lòng chọn trọng lượng sản phẩm'),
    imgMauSac: yup.array().required('vui lòng chọn ảnh màu sắc sản phẩm'),
    moTa: yup.string().required('Vui lòng điền mô tả sản phẩm').min(10, 'Mô tả sản phẩm phải có ít nhất 10 ký tự')
    .test('no-spaces', 'Tên không được chứa khoảng trắng', value => value && !/\s/.test(value)),
    anh: yup.string().required('vui lòng chọn ảnh chính cho sản phẩm')
});

const { handleSubmit, resetForm } = useForm({
    validationSchema: schema
});

const { value: name, errorMessage: nameError } = useField('ten');
const { value: GiaBan, errorMessage: giaBanError } = useField('giaBan');
// const { value: GiaNhap, errorMessage: giaNhapError } = useField('giaNhap');
const { value: QuaiDeo, errorMessage: quaiDeoError } = useField('quaiDeo');
const { value: Loai, errorMessage: loaiError } = useField('loai');
const { value: ThuongHieu, errorMessage: thuongHieuError } = useField('thuongHieu');
const { value: vatLieu, errorMessage: vatLieuError } = useField('vatLieu');
const { value: idMauSac, errorMessage: mauSacError } = useField('idMauSac');
const { value: DemLot, errorMessage: demLotError } = useField('demLot');
const { value: Size, errorMessage: SizeError } = useField('idSize');
const { value: SoLuongSize, errorMessage: soLuongSizeError } = useField('soLuongSize');
const { value: TrongLuong, errorMessage: trongLuongError } = useField('trongLuong');
const { value: imgMauSac, errorMessage: ImgMauSacError } = useField('imgMauSac');
const { value: TrangThai, errorMessage: TrangThaiSacError } = useField('trangThai');
const { value: MoTa, errorMessage: MoTaSacError } = useField('moTa');
const { value: imagesProduct, errorMessage: imagesProductError } = useField('imagesProduct');
const { value: imagesChinh, errorMessage: imagestError } = useField('anh');
// const { value: soLuongMauSac, errorMessage: soLuongMauError } = useField('soLuongMauSac');

const isOpen = ref(true);

const onSubmit = handleSubmit(async (values) => {
    try {
        // Kiểm tra trùng lặp trước khi thêm sản phẩm
        const isDuplicate = await productStore.checkDuplicateName(values); // Sử dụng `values.name` thay vì `name.value`
        if (isDuplicate) {
            // Hiển thị thông báo lỗi hoặc xử lý theo nhu cầu của bạn
            toast.add({ severity: 'error', summary: 'Error', detail: 'sản phẩm đã tồn tại', life: 3000 });
        } else {

            for (let i = 0; i < values.giaBan.length; i++) {
                let giaBan = values.giaBan[i];
                //   let giaNhap = values.giaNhap[i];
                let soLuong = values.soLuongSize[i];
                if (soLuong <= 0) {
                    toast.add({ severity: 'error', summary: 'Error', detail: 'Số lượng sản phẩm phải lớn hơn 0', life: 3000 });
                    return;
                }
                if (isNaN(soLuong)) {
                    toast.add({ severity: 'error', summary: 'Error', detail: 'Giá trị phải là một số', life: 3000 });
                    return;
                }
                if (giaBan <= 0) {
                    toast.add({ severity: 'error', summary: 'Error', detail: 'Giá bán sản phẩm phải lớn hơn 0', life: 3000 });
                    return;
                }
                if (isNaN(giaBan)) {
                    toast.add({ severity: 'error', summary: 'Error', detail: 'Giá bán phải là một số', life: 3000 });
                    return;
                }
                if (Number(giaBan) < 50000) {
                    toast.add({ severity: 'error', summary: 'Error', detail: 'giá bán phải lớn hơn 50.000 đ' + giaBan, life: 3000 });
                    return;
                }
                if (giaBan > 10000000) {
                    toast.add({ severity: 'error', summary: 'Error', detail: 'Giá bán không lớn hơn 10.000.000 đ', life: 3000 });
                    return;
                }
                // if (giaNhap <= 0) {
                //     toast.add({ severity: 'error', summary: 'Error', detail: 'Giá nhập sản phẩm phải lớn hơn 0', life: 3000 });
                //     return;
                // }
                // if (isNaN(giaNhap)) {
                //     toast.add({ severity: 'error', summary: 'Error', detail: 'Giá nhập phải là một số', life: 3000 });
                //     return;
                // }
                // if (giaNhap < 50000) {
                //     toast.add({ severity: 'error', summary: 'Error', detail: 'giá nhập phải lớn hơn 50.000 đ', life: 3000 });
                //     return;
                // }
                // if (giaNhap > 10000000) {
                //     toast.add({ severity: 'error', summary: 'Error', detail: 'Giá nhập không lớn hơn 10.000.000 đ', life: 3000 });
                //     return;
                // }
                // if (giaNhap > giaBan) {
                //     toast.add({ severity: 'error', summary: 'Error', detail: 'Giá nhập phải lớn hơn giá bán', life: 3000 });
                //     return;
                // }

            }
            await productStore.add(values);
            toast.add({ severity: 'success', summary: 'Success Message', detail: 'Thêm thành công', life: 3000 });
            productDialog.value = false;
            reset();
        }
    } catch (error) {
        console.error('Lỗi xử lý dữ liệu:', error);
    }
});


const error = ref(false);

const formatPrice = (value) => {
    return new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(value);
};

const selectedCity = ref(null);
const selectedLoai = ref(null);
const selectedMauSac = ref(null);
const selectedvatLieu = ref(null);
const selectedTrongLuong = ref(null);
const selectedSizes = ref(null);

const array = ref([]);
const arrayGiaNhap = ref([]);
const arrayGiaBan = ref([]);
const arrayMauSac = ref([]);

const dataThuongHieu = ref([]);
//load data thương hiệu tất cả
const loadDataThuongHieu = async () => {
    await thuongHieuService.fetchDataByStatus(1);
    dataThuongHieu.value = thuongHieuService.dataByStatus1;
    // ThuongHieu.value =  dataThuongHieu.value.ten;
};

const dataSize = ref([]);

//load data size tất cả
const loadDataSize = async () => {
    await sizeStore.fetchDataByStatus(1);
    dataSize.value = sizeStore.dataByStatus1;
};

const dataMauSac = ref([]);
const loadDataMauSac = async () => {
    await mauSacStore.fetchDataByStatus(1);
    dataMauSac.value = mauSacStore.dataByStatus1;
    const lstMau = dataMauSac.value;
    // Tạo mảng mới với thông tin về size và màu sắc
    for (const [key, product] of lstMau.entries()) {
        lstMau[key]['size'] = null;
    }
    dataMauSac.value = lstMau;
};

const dataLoai = ref([]);
const loadDataLoai = async () => {
    await loaiStore.fetchDataByStatus(1);
    dataLoai.value = loaiStore.dataByStatus1;
    //  console.log( loaiStore.data)
};

const dataTrongLuong = ref([]);
const loadDataTrongLuong = async () => {
    await trongLuongStore.fetchDataByStatus(1);
    dataTrongLuong.value = trongLuongStore.dataByStatus1;
};

const dataVatLieu = ref([]);
const loadDataVatLieu = async () => {
    await vatLieuStore.fetchDataByStatus(1);
    dataVatLieu.value = vatLieuStore.dataByStatus1;
};

onMounted(() => {
    loadDataThuongHieu();
    loadDataSize();
    loadDataMauSac();
    loadDataLoai();
    loadDataTrongLuong();
    loadDataVatLieu();
});

const reset = () => {
    resetForm();
    imagesProduct.value = [];
    array.value = [];
    arrayGiaBan.value = [];
    arrayGiaNhap.value = [];
    arrayMauSac.value = [];
    selectedSizes.value = null;
    selectedMauSac.value = null;
    selectedLoai.value = null;
    selectedCity.value = null;
    selectedTrongLuong.value = null;
    selectedvatLieu.value = null;
    ImagesProduct.value = [];
    arrayImgMauSac.value = [];
    imageUrls.value = [];
    anh.value = 'https://cdn-icons-png.flaticon.com/512/2956/2956744.png';
};

const handleInputChange = (sizeId) => {
    if (array.value.length > 0) {
        SoLuongSize.value = array.value.join(',').replace(/^,/, '').split(',').map(Number);
    } else {
        SoLuongSize.value = null;
    }
};

const handleInputChangeGiaBan = (sizeId) => {
    if (arrayGiaBan.value.length > 0) {
        GiaBan.value = arrayGiaBan.value.join(',').replace(/^,/, '').split(',').map(Number);
        //  console.log(GiaBan.value);
    } else {
        GiaBan.value = null;
    }
};

const handleInputChangeGiaNhap = (sizeId) => {
    if (arrayGiaNhap.value.length > 0) {
        GiaNhap.value = arrayGiaNhap.value.join(',').replace(/^,/, '').split(',').map(Number);
        //    console.log(GiaNhap.value);
    } else {
        GiaNhap.value = null;
    }
};


const onCityChange = () => {
    if (selectedCity.value) {
        ThuongHieu.value = selectedCity.value.id;
    } else {
        ThuongHieu.value = null;
    }
};

const onTrongLuongChange = () => {
    if (selectedTrongLuong.value) {
        TrongLuong.value = selectedTrongLuong.value.id;
        //    console.log(TrongLuong.value)
    } else {
        TrongLuong.value = null;
    }
};

const onloaiChange = () => {
    if (selectedLoai.value) {
        Loai.value = selectedLoai.value.id;
    } else {
        Loai.value = null;
    }
};
let oldMauLength = ref(null);
let oldMauSacLength = oldMauLength.value;
const onMauSacChange = () => {
    if (selectedMauSac.value.length > 0) {
        const selectedIds = selectedMauSac.value.map((item) => item.id);
        idMauSac.value = selectedIds.join(',').split(',').map(Number);
        selectedMauSac.value = [...new Set(selectedMauSac.value)];
        oldMauLength.value = selectedMauSac.value.length;
        // console.log(selectedMauSac.value)
    } else {
        idMauSac.value = null;
    }
};

let oldSizeLength = ref(null);
const onSizeChange = () => {
    if (selectedSizes.value.length > 0) {
        const selectedIds = selectedSizes.value.map((item) => item.id);
        Size.value = selectedIds.join(',').split(',').map(Number);
        oldSizeLength.value = selectedSizes.value.length;

    } else {
        Size.value = null;
    }
};

let old = oldMauLength.value * oldSizeLength.value;


watch([selectedSizes, oldMauLength], ([newDataSize, newDataMau]) => {
    //  console.log(oldMauLength)
    if (selectedSizes.value == null || selectedSizes.value == 0 || selectedSizes.value == '' || selectedMauSac.value == null || selectedMauSac.value == '') {

    } else if (selectedSizes.value && selectedMauSac.value && oldMauLength.value) {
        let mauSacLength = oldMauLength.value;
        let sizeLength = selectedSizes.value.length;
        let check = sizeLength * mauSacLength;

        // Lưu giá trị ban đầu của selectedMauSac.value
        let originalMauSac = selectedMauSac.value.slice(0, mauSacLength);

        // Nối mảng với chính nó cho đến khi đạt đủ số lượng phần tử
        while (selectedMauSac.value.length < check) {

            selectedMauSac.value = selectedMauSac.value.concat(selectedMauSac.value);

        }

        // Cắt mảng để chỉ giữ lại số lượng phần tử theo check
        selectedMauSac.value = selectedMauSac.value.slice(0, check);

        //  console.log(' sele  ', selectedMauSac.value);
        // console.log(' selectedSizesWithColors  ', combinedArray);

    } else {
        console.log('One of the values is null or undefined');
    }
});



const onvatLieuChange = () => {
    if (selectedvatLieu.value) {
        vatLieu.value = selectedvatLieu.value.id;
    } else {
        vatLieu.value = null;
    }
};


const arrayImgMauSac = ref([]);
function onFileInputImageMauSac(event) {
    const files = event.target.files;
    for (let i = 0; i < files.length; i++) {
    
        const file = files[i];
        const objectURL = URL.createObjectURL(file);
        const basePath = "D:\\imgDATN\\"; // Đường dẫn cố định
        const fileName = basePath + file.name;
        arrayImgMauSac.value.push(fileName);
    }
    imgMauSac.value = arrayImgMauSac.value.join(',').replace(/^,/, '').split(',');
}


const anh = ref('https://cdn-icons-png.flaticon.com/512/2956/2956744.png');

function onFileInputImage(event) {
    const files = event.target.files;
    // Lặp qua từng tệp trong mảng files
    for (const file of files) {
        const objectURL = URL.createObjectURL(file);
        // Gán giá trị cho phần tử có id là 'imagesChinh' (thay đổi id nếu cần)
        const basePath = "D:\\imgDATN\\"; // Đường dẫn cố định
        const fileName = basePath + file.name;
        imagesChinh.value = fileName;
        anh.value = objectURL;
        // console.log('anh chinh: ', imagesChinh.value)
    }
}

const ImagesProduct = ref([]);
const imageUrls = ref([]);
function onFileInputImageProduct(event) {
    const files = event.target.files;
    for (let i = 0; i < files.length; i++) {
        const file = files[i];
        const objectURL = URL.createObjectURL(file);
        const basePath = "D:\\imgDATN\\"; // Đường dẫn cố định
        const fileName = basePath + file.name;
        imageUrls.value.push(fileName);

    }
    ImagesProduct.value = imageUrls.value;
    // console.log(ImagesProduct.value)
    imagesProduct.value = ImagesProduct.value.join(',').replace(/^,/, '').split(',');
    //  console.log('anh phu: ', imagesProduct.value)
}

const product = ref({});
const submitted = ref(false);
const productDialog = ref(false);
const openNew = () => {
    product.value = {};
    submitted.value = false;
    productDialog.value = true;
};
</script>

<template>
    <Button label="Thêm mới" icon="pi pi-plus" class="p-button-success mr-2" @click="openNew" />
    <div>
        <Dialog v-model:visible="productDialog" :style="{ width: '1100px' }" header="Thêm Mới Sản Phẩm" :modal="true"
            class="p-fluid">
            <div class="col-12">

                <div class="card">
                    <form @submit="onSubmit">
                        <div class="p-fluid formgrid grid">
                            <div class="Field col-12 md:col-6" style="margin-bottom: 30px">
                                <div class="Field col-12 md:col-12" style="margin-bottom: 30px">
                                    <span class="p-float-label">
                                        <InputText id="name" name="name" type="text" v-model="name"
                                            :class="{ 'p-invalid': nameError }"></InputText>
                                        <label for="username">Tên sản phẩm</label>
                                    </span>
                                    <small class="p-error">{{ nameError }}</small>
                                </div>
                                <div class="Field col-12 md:col-12" style="margin-bottom: 30px">
                                    <label for="address">Quai Đeo</label>
                                    <div class="flex flex-wrap gap-3">
                                        <div class="flex align-items-center">
                                            <RadioButton v-model="QuaiDeo" type="radio" inputId="ingredient1" name="QuaiDeo"
                                                value="Quai đeo cố định" :class="{ 'p-invalid': quaiDeoError }" checked />
                                            <label for="ingredient1" class="ml-2">Quai đeo cố định</label>
                                        </div>
                                        <div class="flex align-items-center">
                                            <RadioButton v-model="QuaiDeo" type="radio" inputId="ingredient2" name="QuaiDeo"
                                                value="Quai đeo dạng Y" :class="{ 'p-invalid': quaiDeoError }" />
                                            <label for="ingredient2" class="ml-2">Quai đeo dạng Y</label>
                                        </div>
                                        <div class="flex align-items-center">
                                            <RadioButton v-model="QuaiDeo" type="radio" inputId="ingredient3" name="QuaiDeo"
                                                value="Quai đeo đặc biệt" :class="{ 'p-invalid': quaiDeoError }" />
                                            <label for="ingredient3" class="ml-2"
                                                :class="{ 'p-invalid': equaiDeoError }">Quai
                                                đeo đặc biệt</label>
                                        </div>
                                    </div>
                                    <small class="p-error">{{ quaiDeoError }}</small>
                                </div>

                                <div class="Field col-12 md:col-12" style="margin-bottom: 30px">
                                    <label for="address">Đệm lót</label>
                                    <div class="flex flex-wrap gap-3">
                                        <div class="flex align-items-center">
                                            <RadioButton v-model="DemLot" inputId="ingredient1" name="pizza"
                                                value="Bọt biển " :class="{ 'p-invalid': demLotError }" checked />
                                            <label for="ingredient1" class="ml-2">Bọt biển </label>
                                        </div>
                                        <div class="flex align-items-center">
                                            <RadioButton v-model="DemLot" inputId="ingredient2" name="pizza"
                                                value="Vật liệu mềm" :class="{ 'p-invalid': demLotError }" />
                                            <label for="ingredient2" class="ml-2">Vật liệu mềm</label>
                                        </div>
                                        <div class="flex align-items-center">
                                            <RadioButton v-model="DemLot" inputId="ingredient4" name="pizza"
                                                value="Đệm lót chống xốp nhiễu" :class="{ 'p-invalid': demLotError }" />
                                            <label for="ingredient4" class="ml-2">Đệm lót chống xốp nhiễu</label>
                                        </div>
                                    </div>
                                    <small class="p-error">{{ demLotError }}</small>
                                </div>
                                <!-- <div class="field col-12 md:col-8" style="margin-bottom: 30px">
                                        <label for="address">Trạng thái</label>
                                        <div class="flex flex-wrap gap-3" >
                                            <div class="flex align-items-center">
                                                <RadioButton v-model="TrangThai" inputId="ingredient1" name="pizza" value="1"
                                                    :class="{ 'p-invalid': TrangThaiSacError }" />
                                                <label for="ingredient1" class="ml-2">Sẵn sàng để bán</label>
                                            </div>
                                            <div class="flex align-items-center" >
                                                <RadioButton v-model="TrangThai" inputId="ingredient2" name="pizza" value="3"
                                              :class="{ 'p-invalid': TrangThaiSacError }" />
                                                <label for="ingredient2" class="ml-2">tồn kho</label>
                                            </div>
                                        </div>
                                        <small class="p-error">{{ TrangThaiSacError }}</small>
                                    </div> -->
                                <div class="p-fluid formgrid grid">
                                    <div class="Field col-12 md:col-6" style="margin-bottom: 30px">
                                        <div style="display: flex">
                                            <span class="p-float-label" style="width: 239px">
                                                <Dropdown id="dropdown" :options="dataLoai" v-model="selectedLoai"
                                                    optionLabel="ten" :class="{ 'p-invalid': loaiError }"
                                                    @change="onloaiChange"> </Dropdown>
                                                <label for="dropdown">Loại</label>
                                            </span>
                                            <TableLoai :tableId="'tableLoai'" :rightGhId="'right_ghLoai'"
                                                :tableClass="'tableLoai'" :rightGhClass="'right_ghLoai'" />
                                        </div>

                                        <small class="p-error">{{ loaiError }}</small>
                                    </div>
                                    <div class="Field col-12 md:col-6" style="margin-bottom: 30px">
                                        <div style="display: flex">
                                            <span class="p-float-label" style="width: 239px">
                                                <Dropdown id="dropdown" :options="dataThuongHieu" v-model="selectedCity"
                                                    optionLabel="ten" @change="onCityChange"
                                                    :class="{ 'p-invalid': thuongHieuError }"></Dropdown>
                                                <label for="dropdown">Thương Hiệu</label>
                                            </span>
                                            <TableThuongHieu :tableId="'TableThuongHieu'" :rightGhId="'right_ghThuongHieu'"
                                                :tableClass="'TableThuongHieu'" :rightGhClass="'right_ghThuongHieu'" />
                                        </div>

                                        <small class="p-error">{{ thuongHieuError }}</small>
                                    </div>
                                    <div class="Field col-12 md:col-6" style="margin-bottom: 30px">
                                        <div style="display: flex">
                                            <span class="p-float-label" style="width: 239px">
                                                <MultiSelect v-model="selectedMauSac" :options="dataMauSac"
                                                    optionLabel="ten" :filter="false" :maxSelectedLabels="3"
                                                    :class="{ 'p-invalid': mauSacError }" @change="onMauSacChange">
                                                </MultiSelect>
                                                <label for="multiselect">Màu sắc</label>
                                            </span>

                                            <TableMauSac :tableId="'TableMauSac'" :rightGhId="'right_ghMauSac'"
                                                :tableClass="'TableMauSac'" :rightGhClass="'right_ghMauSac'" />
                                        </div>
                                        <small class="p-error">{{ mauSacError }}</small>
                                    </div>
                                    <div class="Field col-12 md:col-6" style="margin-bottom: 30px">
                                        <div style="display: flex">
                                            <span class="p-float-label" style="width: 239px">
                                                <Dropdown id="dropdown" :options="dataVatLieu" v-model="selectedvatLieu"
                                                    :class="{ 'p-invalid': vatLieuError }" optionLabel="ten"
                                                    @change="onvatLieuChange"> </Dropdown>
                                                <label for="dropdown">Vật liệu</label>
                                            </span>
                                            <TablevatLieu :tableId="'TablevatLieu'" :rightGhId="'right_ghvatLieu'"
                                                :tableClass="'TablevatLieu'" :rightGhClass="'right_ghvatLieu'" />
                                        </div>
                                        <small class="p-error">{{ vatLieuError }}</small>
                                    </div>


                                    <div class="Field col-12 md:col-6" style="margin-bottom: 30px">
                                        <div style="display: flex">
                                            <span class="p-float-label" style="width: 239px">
                                                <Dropdown id="dropdown" :options="dataTrongLuong"
                                                    v-model="selectedTrongLuong" optionLabel="value"
                                                    :class="{ 'p-invalid': trongLuongError }" @change="onTrongLuongChange">
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
                                            <span class="p-float-label" style="width: 150px">
                                                <MultiSelect v-model="selectedSizes" :options="dataSize" optionLabel="ten"
                                                    :filter="false" :maxSelectedLabels="3"
                                                    :class="{ 'p-invalid': SizeError }" @change="onSizeChange">
                                                </MultiSelect>
                                                <label for="multiselect">Size</label>
                                            </span>
                                            <TableSize :tableId="'TableMauSac'" :rightGhId="'right_ghMauSac'"
                                                :tableClass="'TableMauSac'" :rightGhClass="'right_ghMauSac'" />
                                        </div>
                                        <small class="p-error">{{ SizeError }}</small>
                                    </div>
                                </div>
                                <div class="field col-12 md:col-12" style="margin-bottom: 30px">
                                    <label for="address">Mô tả</label>
                                    <Textarea id="address" rows="4" v-model="MoTa"
                                        :class="{ 'p-invalid': MoTaSacError }"></Textarea>
                                    <small class="p-error">{{ MoTaSacError }}</small>
                                </div>
                                <div class="field col-12 md:col-12">
                                    <file-upload :upload-url="uploadUrl" :pt="{buttonbar: {class: 'flex-nowrap'}}" :multiple="true" :maxFileSize="2000000"
                                        @input="onFileInputImageProduct"
                                        :class="{ 'p-invalid': imagesProductError }"></file-upload>
                                    <small class="p-error">{{ imagesProductError }}</small>
                                    
                                </div>

                            </div>

                            <div class="Field col-12 md:col-6">
                                <div class="Field col-12 md:col-12"
                                    style="margin-bottom: 30px;margin-left: 60px; height: 300px; margin-top: 60px; display: inline-flex; justify-content: center; align-items: center;display: block">
                                    <div class="t"
                                        style="border: 1px solid black; border-radius: 10px; width: 300px; height: 240px; margin-top: -60px">
                                        <img :src="anh" alt=""
                                            style="width: 275px; height: 230px; top: 50%; left: 50%; transform: translate(4%, 2%)" />
                                    </div>
                                    <div class="buton" style="margin-top: 10px">
                                        <FileUpload mode="basic" name="demo[]" accept="image/*" :maxFileSize="1000000"
                                            @input="onFileInputImage"
                                            style="display: flex; width: 200px; margin-left: 50px;" />
                                    </div>
                                    <small class="p-error">{{ imagestError }}</small>
                                </div>
                                <div class="field col-12 md:col-12">

                                    <div style="background: rgb(255, 255, 255); width: 450px ;  display: flex;">
                                        <div style="background: rgb(255, 255, 255); width: 20% ; height: 100%;">
                                            <div v-for="(mau, index) in selectedSizes" :key="index"
                                                style="margin-top: 40px; margin-bottom: 50px; margin-left: 70px;">
                                                <label :for="`input-${mau.id}`"
                                                    style="margin-right: 5px; margin-left: 0px; margin-top: 0px; margin-bottom: 0;">{{
                                                        mau.ten }}</label>
                                            </div>
                                        </div>
                                        <div style="background: rgb(255, 255, 255); width: 80% ; height: 100%;">
                                            <div v-for="(color, index) in selectedMauSac" :key="index"
                                                class="mausac-container"
                                                style="display: inline-block; margin-bottom: 15px; height: 90x; width: 150px; margin-left: 10px;">
                                                <div>
                                                    Màu :
                                                    <span class="product-name">{{ color.ten }}</span>
                                                </div>
                                                <FileUpload mode="basic" name="demo[]" accept="image/*"
                                                    :maxFileSize="1000000" @input="onFileInputImageMauSac" />
                                            </div>
                                        </div>
                                    </div>

                                    <small class="p-error">{{ ImgMauSacError }}</small>
                                </div>

                                <div class="field col-12 md:col-12">

                                    <div style="background: rgb(255, 255, 255); width: 450px ;  display: flex;">
                                        <div style="width:75px">
                                            <p>Số lượng :</p>
                                        </div>
                                        <div style="width: 100%; height: 100%; display: flex; flex-wrap: wrap">
                                            <div style=" width: 20% ; height: 100%;">

                                                <div style="display: flex; flex-wrap: wrap">
                                                    <div v-for="(mau, index) in selectedSizes" :key="index"
                                                        style="margin-top: 5px; margin-left: 30px;">
                                                        <label :for="`input-${mau.id}`"
                                                            style="margin-right: 5px; margin-left: 0px">{{
                                                                mau.ten }}</label>
                                                    </div>

                                                </div>
                                            </div>
                                            <div style=" width: 80% ; height: 100%;">
                                                <div style="display: flex; flex-wrap: wrap">
                                                    <div v-for="(mau, index) in selectedMauSac" :key="index"
                                                        style="margin-top: 5px">
                                                        <label :for="`input-${mau.id}`"
                                                            style="margin-right: 10px; margin-left: 10px">{{
                                                                mau.ten }} <span v-if="mau.size !== null"> - {{ mau.size.ten
    }}</span></label>
                                                        <input type="number" :id="`input-${mau.id}`" v-model="array[index]"
                                                            @change="handleInputChange(mau.id)"
                                                            :class="{ 'p-invalid': soLuongSizeError }"
                                                            style="height: 20px; width: 80px;border-radius: 5px ; border: 1px solid;" />

                                                    </div>
                                                </div>
                                            </div>
                                        </div>

                                    </div>
                                    <small class="p-error">{{ soLuongSizeError }}</small>
                                </div>



                                <div class="field col-12 md:col-12">

                                    <div style="background: rgb(255, 255, 255); width: 450px ;  display: flex;">
                                        <div style="width: 75px">
                                            <p>Giá Bán:</p>
                                        </div>
                                        <div style="background: rgb(255, 255, 255); width: 20% ; height: 100%;">

                                            <div style="display: flex; flex-wrap: wrap">
                                                <div v-for="(mau, index) in selectedSizes" :key="index"
                                                    style="margin-top: 5px; margin-left: 30px;">
                                                    <label :for="`input-${mau.id}`"
                                                        style="margin-right: 5px; margin-left: 0px">{{
                                                            mau.ten }}</label>
                                                </div>

                                            </div>
                                        </div>
                                        <div style="background: rgb(255, 255, 255); width: 80% ; height: 100%; display: ;">

                                            <div style="display: flex; flex-wrap: wrap">

                                                <div v-for="(mau, index) in selectedMauSac" :key="index"
                                                    style="margin-top: 5px; ">
                                                    <label :for="`input-${mau.id}`"
                                                        style="margin-right: 10px; margin-left: 10px">{{ mau.ten }}</label>
                                                    <input type="number" :id="`input-${mau.id}`"
                                                        v-model="arrayGiaBan[index]"
                                                        @change="handleInputChangeGiaBan(mau.id)"
                                                        :class="{ 'p-invalid': giaBanError }"
                                                        style="height: 20px; width: 80px;border-radius: 5px ; border: 1px solid;" />
                                                </div>
                                            </div>

                                        </div>
                                    </div>

                                    <small class="p-error">{{ giaBanError }}</small>
                                </div>
                            </div>

                            <div style="width: 1000px; text-align: center">

                                <Button class="p-button-outlined" outlined severity="secondary"
                                    style="width: 200px; height: auto; margin: 10px" @click="reset()"
                                    label="clear"></Button>
                                <Button type="submit" class="p-button-outlined"
                                    style="width: 200px; height: auto; margin: 10px" label="Lưu"></Button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </Dialog>
    </div>
</template>

<style scoped></style>
