<script setup>
import { FilterMatchMode } from 'primevue/api';
import { useConfirm } from "primevue/useconfirm";
import { useForm, useField, defineRule } from 'vee-validate';
import * as yup from 'yup';
import { reactive, ref, computed, onMounted, onBeforeMount } from 'vue';
import TableLoai from './DataTableLoai.vue';
import TableThuongHieu from './DataTableThuongHieu.vue';

import TablevatLieu from './DataTableVatLieu.vue';
import { ProductStore } from '@/service/Admin/product/product.api';
import { useToast } from 'primevue/usetoast';
import { useCounterStore } from '@/service/Admin/ThuongHieu/ThuongHieuService.js';
import { SizeStore } from '@/service/Admin/Size/SizeService';
import { useLoaiService } from '@/service/Admin/Loai/LoaiService';
import { useMauSacService } from '@/service/Admin/MauSac/MauSacService';
import { TrongLuongStore } from '@/service/Admin/TrongLuong/TrongLuong.api';
import { VatLieuStore } from '@/service/Admin/VatLieu/VatLieu.api';

const confirm = useConfirm();
const toast = useToast();
const productStore = ProductStore();
const thuongHieuService = useCounterStore();
const sizeStore = SizeStore();
const mauSacStore = useMauSacService();
const loaiStore = useLoaiService();
const trongLuongStore = TrongLuongStore();
const vatLieuStore = VatLieuStore();
const props = defineProps({
    myProp: {}
});

const schema = yup.object().shape({
    ten: yup
        .string()
        .required('Tên sản phẩm không được để trống')
        .min(4, 'Tên sản phẩm phải có ít nhất 4 ký tự')
        .matches(/^[a-zA-Z0-9đĐáÁàÀảẢãÃạẠăĂắẮằẰẳẲẵẴặẶâÂấẤầẦẩẨẫẪậẬêÊếẾềỀểỂễỄệỆôÔốỐồỒổỔỗỖộỘơƠớỚờỜởỞỡỠợỢùÙúÚụỤủỦũŨưỨỨửỬữỮựỰýÝỳỲỷỶỹỸỵỴ\s\-]*$/, 'Tên không được chứa kí tự đặc biệt!')
        .test('no-spaces', 'Tên không được chứa khoảng trắng', value => value && !/\s/.test(value)),
    quaiDeo: yup.string().required('Bạn cần chọn quai đeo cho sản phẩm'),
    loai: yup.number().required('loại sản phẩm không được để trống'),
    thuongHieu: yup.number().required('vui lòng chọn Thương hiệu sản phẩm '),
    vatLieu: yup.number().required(' vui lòng chọn Vật liệu sản phẩm '),
    demLot: yup.string().required(' vui lòng chọn đệm lót sản phẩm '),
    trangThai: yup.number().required('vui lòng chọn trạng thái của sản phẩm'),
    moTa: yup.string().required('Vui lòng điền mô tả sản phẩm').min(10, 'Mô tả sản phẩm phải có ít nhất 10 ký tự'),
    anh: yup.string().required('vui lòng chọn ảnh chính cho sản phẩm')
});

const { handleSubmit, resetForm } = useForm({
    validationSchema: schema
});

const { value: id, errorMessage: idError } = useField('id');
const { value: name, errorMessage: nameError } = useField('ten');
const { value: QuaiDeo, errorMessage: quaiDeoError } = useField('quaiDeo');
const { value: Loai, errorMessage: loaiError } = useField('loai');
const { value: ThuongHieu, errorMessage: thuongHieuError } = useField('thuongHieu');
const { value: vatLieu, errorMessage: vatLieuError } = useField('vatLieu');
const { value: demLot, errorMessage: demLotError } = useField('demLot');
const { value: TrangThai, errorMessage: TrangThaiSacError } = useField('trangThai');
const { value: MoTa, errorMessage: MoTaSacError } = useField('moTa');
// const { value: imagesProduct, errorMessage: imagesProductError } = useField('imagesProduct');
const { value: imagesChinh, errorMessage: imagestError } = useField('anh');

const isOpen = ref(true);

const productDialog = ref(false);
const product = ref({});
// mở form

//đóng form
const hideDialog = () => {

    productDialog.value = false;
    // submitted.value = false;
};

const onSubmit = handleSubmit(async (values) => {
    try {
        // console.log(values)
        await productStore.edit(values);
        toast.add({ severity: 'success', summary: 'Success Message', detail: 'update thành công', life: 3000 });
        productDialog.value = false;
        reset();
    } catch (error) {
        console.error('Lỗi xử lý dữ liệu:', error);
    }
});

const formatPrice = (value) => {
    return new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(value);
};

const sizes = ref([
    { value: 'M', label: 'M', id: 1 },
    { value: 'L', label: 'L', id: 2 },
    { value: 'XL', label: 'XL', id: 3 },
    { value: 'LL', label: 'LL', id: 4 }
    // Thêm các Size khác tùy ý
]);

const colors = ref([
    { value: 'M', label: 'M', id: 1 },
    { value: 'L', label: 'L', id: 2 },
    { value: 'XL', label: 'XL', id: 3 }
    // Thêm các item khác tùy ý
]);

const selectedCity = ref(null);
const selectedLoai = ref(null);
const selectedMauSac = ref(null);
const selectedvatLieu = ref(null);
const selectedTrongLuong = ref(null);
const selectedSizes = ref(null);

const array = ref([]);

const check = async () => {
    try {
        const isDuplicate = await productStore.checkDuplicateName(name.value);
    } catch (error) {
        console.error('Lỗi khi kiểm tra trùng lặp tên sản phẩm:', error);
        // Xử lý lỗi nếu cần thiết
    }
};

const reset = () => {
    resetForm();
    array.value = [];
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
};

const handleInputChange = () => {
    if (array.value.length > 0) {
        SoLuongSize.value = array.value.join(',').replace(/^,/, '').split(',').map(Number);
    } else {
        SoLuongSize.value = null;
    }
};

const arrayMauSac = ref([]);
const handleInputChangeMau = (sizeId) => {
    if (arrayMauSac.value.length > 0) {
        soLuongMauSac.value = arrayMauSac.value.join(',').replace(/^,/, '').split(',').map(Number);
    } else {
        soLuongMauSac.value = null;
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

const onMauSacChange = () => {
    if (selectedMauSac.value.length > 0) {
        const selectedIds = selectedMauSac.value.map((item) => item.id);
        idMauSac.value = selectedIds.join(',').split(',').map(Number);
    } else {
        idMauSac.value = null;
    }
};

const onSizeChange = () => {
    if (selectedSizes.value.length > 0) {
        const selectedIds = selectedSizes.value.map((item) => item.id);
        Size.value = selectedIds.join(',').split(',').map(Number);
    } else {
        Size.value = null;
    }
};

const onvatLieuChange = () => {
    if (selectedvatLieu.value) {
        vatLieu.value = selectedvatLieu.value.id;
    } else {
        vatLieu.value = null;
    }
};

// const selectedItems = computed(() => {
//     return sizes.value.filter(item => item.isChecked);

// });
sizes.value.forEach((item) => {
    item.showInput = false; // Initialize the showInput property for each item
});

const arrayImgMauSac = ref([]);
function onFileInputImageMauSac(id) {
    const index = selectedMauSac.value.findIndex((s) => s.id === id);
    const files = event.target.files;

    for (let i = 0; i < files.length; i++) {
        const file = files[i];
        const basePath = "D:\\imgDATN\\"; // Đường dẫn cố định
        const fileName = basePath + file.name;

        // Kiểm tra nếu index hợp lệ, sau đó cập nhật giá trị tại index đó
        if (index >= 0 && index < arrayImgMauSac.value.length) {
            arrayImgMauSac.value[index] = fileName;
        } else {
            // Nếu index không hợp lệ, hãy thêm objectURL vào cuối mảng
            arrayImgMauSac.value.push(fileName);
        }
    }

    // Cập nhật giá trị imgMauSac.value sau khi đã duyệt qua tất cả các tệp
    imgMauSac.value = arrayImgMauSac.value.join(',').replace(/^,/, '').split(',');
}

const anh = ref(null);
const ImagesProduct = ref([]);
const imageUrls = ref([]);
function onFileInputImageProduct(event) {
    const files = event.target.files;


    for (let i = 0; i < files.length; i++) {
        const file = files[i];
        const basePath = "D:\\imgDATN\\"; // Đường dẫn cố định
        const fileName = basePath + file.name;
        imageUrls.value.push(fileName);

    }
    ImagesProduct.value = imageUrls.value;
    imagesProduct.value = ImagesProduct.value.join(',').replace(/^,/, '').split(',');
}

const dataThuongHieu = ref([]);
//load data thương hiệu tất cả
const loadDataThuongHieu = async () => {
    await thuongHieuService.fetchData();
    dataThuongHieu.value = thuongHieuService.dataByStatus1;
    // ThuongHieu.value =  dataThuongHieu.value.ten;

    const selectedThuongHieu = dataThuongHieu.value.find((item) => item.ten === props.myProp.thuongHieu);
    selectedCity.value = selectedThuongHieu;
    if (selectedCity.value) {
        ThuongHieu.value = selectedCity.value.id;
    } else {
        ThuongHieu.value = null;
    }
};

const dataSize = ref([]);

//load data size tất cả
const loadDataSize = async () => {
    await sizeStore.fetchData();
    dataSize.value = sizeStore.dataByStatus1;
};

const dataMauSac = ref([]);
const loadDataMauSac = async () => {
    await mauSacStore.fetchData();
    dataMauSac.value = mauSacStore.dataByStatus1;
};

const dataLoai = ref([]);
const loadDataLoai = async () => {
    await loaiStore.fetchData();
    dataLoai.value = loaiStore.dataByStatus1;

    const selectedLoais = dataLoai.value.find((item) => item.ten === props.myProp.loai);
    selectedLoai.value = selectedLoais;

    if (selectedLoai.value) {
        Loai.value = selectedLoai.value.id;
    } else {
        Loai.value = null;
    }
};

const dataTrongLuong = ref([]);
const loadDataTrongLuong = async () => {
    await trongLuongStore.fetchAll();
    dataTrongLuong.value = trongLuongStore.dataByStatus1;
};

const dataVatLieu = ref([]);
const loadDataVatLieu = async () => {
    await vatLieuStore.fetchAll();
    dataVatLieu.value = vatLieuStore.dataByStatus1;
    const selectedVatLieus = dataVatLieu.value.find((item) => item.ten.localeCompare(props.myProp.vatLieu));
    selectedvatLieu.value = selectedVatLieus;
    if (selectedvatLieu.value) {
        vatLieu.value = selectedvatLieu.value.id;
    } else {
        vatLieu.value = null;
    }
};

onBeforeMount(() => {
    loadDataThuongHieu();
    loadDataLoai();
    loadDataVatLieu();
});





const lstChiTietSP = ref([]);
const idProduct = ref(null);
const editProduct = async () => {


    id.value = props.myProp.id;
    idProduct.value = props.myProp.id;
    name.value = props.myProp.ten;
    QuaiDeo.value = props.myProp.quaiDeo;
    demLot.value = props.myProp.demLot;
    TrangThai.value = props.myProp.trangThai.toString();
    MoTa.value = props.myProp.moTa;
    imagesChinh.value = props.myProp.anh;
    // selectedLoai.value = props.myProp.loai;



    product.value = { ...editProduct };


    productDialog.value = true;
};

function onFileInputImage(event) {
    const files = event.target.files;
    // Lặp qua từng tệp trong mảng files
    for (const file of files) {
        const objectURL = URL.createObjectURL(file);
        anh.value = objectURL;
        // Gán giá trị cho phần tử có id là 'imagesChinh' (thay đổi id nếu cần)
        const basePath = "D:\\imgDATN\\"; // Đường dẫn cố định
        const fileName = basePath + file.name;
        imagesChinh.value = fileName;
    }
}



const selectedProducts = ref(null);
const filters = ref({});

const getStatusLabel = (soLuong) => {
    if (soLuong == 0) {
        return { text: 'hết Hàng', severity: 'danger' };
    } else if (soLuong == 1) {
        return { text: 'Còn hàng', severity: 'success' };
    } else {
        return { text: 'Tồn kho', severity: 'war' };
    }

};
const formatCurrency = (value) => {
    return value.toLocaleString('vi-VN', { style: 'currency', currency: 'VND' });
};

</script>

<template>
    <Button icon="pi pi-pencil" class="p-button-rounded p-button-success mr-2" @click="editProduct()" />
    <Dialog v-model:visible="productDialog" :style="{ width: '1050px' }" header="Sửa sản phẩm" :modal="true"
        class="p-fluid">
        <form @submit="onSubmit" style="margin-top: 30px">
            <div class="p-fluid formgrid grid">
                <div class="Field col-12 md:col-6" style="margin-bottom: 30px">
                    <div class="Field col-12 md:col-12" style="margin-bottom: 30px">
                        <span class="p-float-label">
                            <InputText id="name" name="name" type="text" v-model="name" :class="{ 'p-invalid': nameError }">
                            </InputText>
                            <label for="username">Tên sản phẩm</label>
                        </span>
                        <small class="p-error">{{ nameError }}</small>
                    </div>
                    <div class="Field col-12 md:col-12" style="margin-bottom: 30px">
                        <label for="address">Quai Đeo</label>
                        <div class="flex flex-wrap gap-3">
                            <div class="flex align-items-center">
                                <RadioButton v-model="QuaiDeo" type="radio" inputId="ingredient1" name="QuaiDeo" value="Vải"
                                    :class="{ 'p-invalid': quaiDeoError }" />
                                <label for="ingredient1" class="ml-2">Vải</label>
                            </div>
                            <div class="flex align-items-center">
                                <RadioButton v-model="QuaiDeo" type="radio" inputId="ingredient2" name="QuaiDeo"
                                    value="Quai đeo đặc biệt" :class="{ 'p-invalid': quaiDeoError }" />
                                <label for="ingredient2" class="ml-2">Quai đeo đặc biệt</label>
                            </div>
                            <div class="flex align-items-center">
                                <RadioButton v-model="QuaiDeo" type="radio" inputId="ingredient3" name="QuaiDeo" value="Da"
                                    :class="{ 'p-invalid': quaiDeoError }" />
                                <label for="ingredient3" class="ml-2" :class="{ 'p-invalid': equaiDeoError }">Da</label>
                            </div>
                            <div class="flex align-items-center">
                                <RadioButton v-model="QuaiDeo" type="radio" inputId="ingredient3" name="QuaiDeo"
                                    value="Polycarbonate" :class="{ 'p-invalid': quaiDeoError }" />
                                <label for="ingredient3" class="ml-2"
                                    :class="{ 'p-invalid': equaiDeoError }">Polycarbonate</label>
                            </div>
                        </div>
                        <small class="p-error">{{ quaiDeoError }}</small>
                    </div>
                    <div class="Field col-12 md:col-12" style="margin-bottom: 30px">
                        <label for="address">Đệm lót</label>
                        <div class="flex flex-wrap gap-3">
                            <div class="flex align-items-center">
                                <RadioButton v-model="demLot" inputId="ingredient1" name="pizza" value="Polycarbonate"
                                    :class="{ 'p-invalid': demLotError }" />
                                <label for="ingredient1" class="ml-2">Polycarbonate</label>
                            </div>
                            <div class="flex align-items-center">
                                <RadioButton v-model="demLot" inputId="ingredient2" name="pizza" value="Vải"
                                    :class="{ 'p-invalid': demLotError }" />
                                <label for="ingredient2" class="ml-2">Vải</label>
                            </div>
                            <div class="flex align-items-center">
                                <RadioButton v-model="demLot" inputId="ingredient4" name="pizza" value="Da"
                                    :class="{ 'p-invalid': demLotError }" />
                                <label for="ingredient4" class="ml-2">Da</label>
                            </div>
                        </div>
                        <small class="p-error">{{ demLotError }}</small>
                    </div>
                    <div class="field col-12 md:col-8" style="margin-bottom: 30px">
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
                    <div class="p-fluid formgrid grid">
                        <div class="Field col-6 md:col-6" style="margin-bottom: 30px">
                            <div style="display: flex">
                                <span class="p-float-label" style="width: 239px">
                                    <Dropdown id="dropdown" :options="dataLoai" v-model="selectedLoai" optionLabel="ten"
                                        :class="{ 'p-invalid': loaiError }" @change="onloaiChange"> </Dropdown>
                                    <label for="dropdown">Loại</label>
                                </span>
                                <TableLoai :tableId="'tableLoai'" :rightGhId="'right_ghLoai'" :tableClass="'tableLoai'"
                                    :rightGhClass="'right_ghLoai'" />
                            </div>

                            <small class="p-error">{{ loaiError }}</small>
                        </div>

                        <div class="Field col-12 md:col-6" style="margin-bottom: 30px">
                            <div style="display: flex">
                                <span class="p-float-label" style="width: 239px">
                                    <Dropdown id="dropdown" :options="dataVatLieu" v-model="selectedvatLieu"
                                        :class="{ 'p-invalid': vatLieuError }" optionLabel="ten" @change="onvatLieuChange">
                                    </Dropdown>
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
                                    <Dropdown id="dropdown" :options="dataThuongHieu" v-model="selectedCity"
                                        optionLabel="ten" @change="onCityChange" :class="{ 'p-invalid': thuongHieuError }">
                                    </Dropdown>
                                    <label for="dropdown">Thương Hiệu</label>
                                </span>
                                <TableThuongHieu :tableId="'TableThuongHieu'" :rightGhId="'right_ghThuongHieu'"
                                    :tableClass="'TableThuongHieu'" :rightGhClass="'right_ghThuongHieu'" />
                            </div>

                            <small class="p-error">{{ thuongHieuError }}</small>
                        </div>

                    </div>
                </div>
                <div class="Field col-12 md:col-6" style="margin-bottom: 30px">
                    <div class="p-fluid formgrid grid">
                        <div class="Field col-12 md:col-6"
                            style="margin-bottom: 30px; height: 300px; margin-top: 10px; display: inline-flex; justify-content: center; align-items: center">
                            <div style="display: block; margin-left: 200px">
                                <div class="t"
                                    style="border: 1px solid black; border-radius: 10px; width: 300px; height: 240px; margin-top: -60px">
                                    <img :src="anh == null ? imagesChinh : anh" alt=""
                                        style="width: 275px; height: 230px; top: 50%; left: 50%; transform: translate(4%, 2%)" />
                                </div>
                                <div class="buton" style="margin-top: 10px">
                                    <FileUpload mode="basic" name="demo[]" accept="image/*" :maxFileSize="1000000"
                                        @input="onFileInputImage" style="display: flex" />
                                </div>
                                <small class="p-error">{{ imagestError }}</small>
                            </div>
                        </div>

                        <div class="field col-12 md:col-12" style="margin-top: -50px">
                            <label for="address">Mô tả</label>
                            <Textarea id="address" rows="4" v-model="MoTa"
                                :class="{ 'p-invalid': MoTaSacError }"></Textarea>
                            <small class="p-error">{{ MoTaSacError }}</small>
                        </div>
                    </div>


                </div>

                <div style="width: 1000px; text-align: center">

                    <Button class="p-button-outlined" outlined severity="secondary"
                        style="width: 200px; height: auto; margin: 10px" @click="reset()" label="clear"></Button>
                    <Button type="submit" class="p-button-outlined" style="width: 200px; height: auto; margin: 10px"
                        label="Lưu"></Button>
                </div>
            </div>
        </form>
    </Dialog>
</template>

<style scoped lang="scss">
.img-product {
    /* width: 100px;
  height: 90px; */
    position: relative;
    /* Thiết lập vị trí tương đối để dễ dàng định vị nút */
}

/* Định dạng cho nút */
// .p-button-warning {
//     position: absolute;
//     /* Thiết lập vị trí tuyệt đối để đặt nút trên hình ảnh */
//     top: 0;
//     /* Đặt nút ở trên cùng của hình ảnh */
//     left: 10;
//     /* Đặt nút ở bên trái của hình ảnh */
//     width: 25px;
//     height: 25px;
//     margin: 10px 0 0 10px;
// }
</style>
