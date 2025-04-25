<script setup>
import { ref } from 'vue';
import { userStore } from '@/service/Admin/User/UserService';
import * as yup from 'yup';
import { useForm, useField } from 'vee-validate';
import { useToast } from 'primevue/usetoast';

const toast = useToast();
const userService = userStore();
const product = ref({});
const submitted = ref(false);
const productDialog = ref(false);

const schema = yup.object().shape({
    ten: yup
        .string()
        .required('Tên người dùng không được để trống')
        .min(4, 'Tên người dùng phải có ít nhất 4 ký tự')
        .matches(/^[a-zA-Z0-9đĐáÁàÀảẢãÃạẠăĂắẮằẰẳẲẵẴặẶâÂấẤầẦẩẨẫẪậẬêÊếẾềỀểỂễỄệỆôÔốỐồỒổỔỗỖộỘơƠớỚờỜởỞỡỠợỢùÙúÚụỤủỦũŨưỨỨửỬữỮựỰýÝỳỲỷỶỹỸỵỴ\s]*$/, 'Tên không được chứa kí tự đặc biệt!'),
    sdt: yup
        .string()
        .required('Số điện thoại không được để trống')
        .matches(/^[0-9]{10}$/, 'Số điện thoại phải chứa đúng 10 số')
        .typeError('Số điện thoại phải là một số')
        .nullable(),
    userName: yup
        .string()
        .required('User name không được để trống')
        .matches(/^[a-zA-Z0-9đĐáÁàÀảẢãÃạẠăĂắẮằẰẳẲẵẴặẶâÂấẤầẦẩẨẫẪậẬêÊếẾềỀểỂễỄệỆôÔốỐồỒổỔỗỖộỘơƠớỚờỜởỞỡỠợỢùÙúÚụỤủỦũŨưỨỨửỬữỮựỰýÝỳỲỷỶỹỸỵỴ\s]*$/, 'User name không được chứa kí tự đặc biệt!'),
    gioiTinh: yup.string().required('Vui lòng chọn giới tính '),
    ngaySinh: yup.date().required(' vui lòng chọn ngày sinh '),
    email: yup
        .string()
        .matches(/^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Z|a-z]{2,}$/, 'Email không hợp lệ')
        .required(' Email không được để trống'),
    image: yup.string().required('vui lòng chọn ảnh'),
});
const { handleSubmit, resetForm } = useForm({
    validationSchema: schema
});
const { value: ten, errorMessage: tenError } = useField('ten');
const { value: userName, errorMessage: userNameError } = useField('userName');
const { value: role, errorMessage: roleError } = useField('role');
const { value: email, errorMessage: emailError } = useField('email');
const { value: sdt, errorMessage: sdtError } = useField('sdt');
const { value: ngaySinh, errorMessage: ngaySinhError } = useField('ngaySinh');
const { value: gioiTinh, errorMessage: gioiTinhError } = useField('gioiTinh');
const { value: image, errorMessage: imageError } = useField('image');
const { value: diaChi, errorMessage: diaChiError } = useField('diaChi');
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
// confirm sửa
const updateProductDialog = ref(false);
//nhận dữ liệu từ cha
const props = defineProps({
    myProp: {}
});

const confirmUpdateProduct = () => {
    updateProductDialog.value = true;
};

// mở form
const editProduct = () => {
    ten.value = props.myProp.ten;
    userName.value = props.myProp.userName;
    role.value = props.myProp.role;
    email.value = props.myProp.email;
    sdt.value = props.myProp.sdt;
    ngaySinh.value = props.myProp.ngaySinh;
    gioiTinh.value = props.myProp.gioiTinh.toString();
    images.value = props.myProp.anh;
    // Lấy giá trị đầu tiên của diaChi nếu mảng diaChi không rỗng
    diaChi.value = props.myProp.diaChi.length > 0 ? props.myProp.diaChi[0].diaChi : '';
    // Hiển thị dialog
    productDialog.value = true;
};

//đóng form
const hideDialog = () => {
    ten.value = props.myProp.ten;
    userName.value = props.myProp.userName;
    role.value = props.myProp.role;
    email.value = props.myProp.email;
    sdt.value = props.myProp.sdt;
    ngaySinh.value = props.myProp.ngaySinh;
    gioiTinh.value = props.myProp.gioiTinh.toString();
    image.value = props.myProp.anh;
    diaChi.value = props.myProp.diaChi;
    productDialog.value = false;
    submitted.value = false;
};

//save
const saveProduct = () => {
    confirmUpdateProduct();
};

const updateProduct = () => {
    submitted.value = true;
    const form = {
        ten: ten.value,
        userName: userName.value,
        email: email.value,
        role: role.value,
        sdt: sdt.value,
        ngaySinh: ngaySinh.value,
        gioiTinh: gioiTinh.value,
        diaChi: diaChi.value,
        image: image.value,
    };
    schema
        .validate(form)
        .then(() => {
            // Dữ liệu hợp lệ, thực hiện thêm sản phẩm
            const update = userService.updateUser(props.myProp.id, form);
            productDialog.value = false;
            toast.add({ severity: 'success', summary: 'Thông báo', detail: 'Sửa thành công', life: 3000 });
            reset();
            // Reload trang web sau khi cập nhật thành công
            // window.location.reload();
        })
        .catch((error) => {
            // Dữ liệu không hợp lệ, hiển thị thông báo lỗi bằng Toast
            toast.add({ severity: 'error', summary: 'Lỗi', detail: error.message, life: 3000 });
        });
    updateProductDialog.value = false;
};

// Hàm chuyển đổi tệp thành dạng Base64
// function convertFileToBase64(file) {
//     return new Promise((resolve, reject) => {
//         const reader = new FileReader();
//         reader.onloadend = () => {
//             resolve(reader.result);
//         };
//         reader.onerror = reject;
//         reader.readAsDataURL(file);
//     });
// }

const images = ref(null);
function onFileInputImage(event) {
    const files = event.target.files;
    // Lặp qua từng tệp trong mảng files
    for (const file of files) {
        const objectURL = URL.createObjectURL(file);
        images.value = objectURL;
        // Gán giá trị cho phần tử có id là 'imagesChinh' (thay đổi id nếu cần)
        const basePath = 'D:\\imgDATN\\'; // Đường dẫn cố định
        const fileName = basePath + file.name;
        image.value = fileName;
    }
}
</script>
<template>
    <Button icon="pi pi-pencil" class="p-button-rounded p-button-success mr-2" @click="editProduct()" />
    <Dialog v-model:visible="productDialog" :style="{ width: '800px' }" header="Sửa user" :modal="true" class="p-fluid">
        <div class="card">
            <div class="flex">
                <!-- Cột phải -->
                <div class="p-col-6" style="width: 400px">
                    <form @submit="onSubmit">
                        <div class="p-fluid formgrid grid">
                            <div class="field col-12" style="margin-bottom: 30px">
                                <span class="p-float-label">
                                    <InputText id="userName" v-model.trim="userName" :class="{ 'p-invalid': userNameError }" required="true" autofocus disabled />
                                    <label for="userName">Tài khoản</label>
                                </span>
                                <small class="p-error">{{ userNameError }}</small>
                            </div>
                            <div class="Field col-12" style="margin-bottom: 30px">
                                <span class="p-float-label">
                                    <InputText id="ten" name="ten" type="text" v-model.trim="ten" :class="{ 'p-invalid': tenError }" required="true" autofocus />
                                    <label for="ten">Tên Người dùng</label>
                                </span>
                                <small class="p-error">{{ tenError }}</small>
                            </div>
                            <div class="field col-12" style="margin-bottom: 30px">
                                <label for="ngaySinh">Ngày sinh</label>
                                <InputText type="date" v-model.trim="ngaySinh" :class="{ 'p-invalid': ngaySinhError }" />
                                <small class="p-error">{{ ngaySinhError }}</small>
                            </div>
                            <div class="field col-12" style="margin-bottom: 30px">
                                <span class="p-float-label">
                                    <InputText id="sdt" v-model.trim="sdt" required="true" autofocus :class="{ 'p-invalid': sdtError }" />
                                    <label for="sdt">SĐT</label>
                                    <small class="p-error">{{ sdtError }}</small>
                                </span>
                            </div>
                            <div class="field col-12" style="margin-bottom: 30px">
                                <span class="p-float-label">
                                    <InputText id="email" v-model.trim="email" required="true" autofocus :class="{ 'p-invalid': emailError }" />
                                    <label for="email">Email</label>
                                    <small class="p-error">{{ emailError }}</small>
                                </span>
                            </div>
                            <div class="field col-12" style="margin-bottom: 30px">
                                <span class="p-float-label">
                                    <InputText id="diaChi" v-model.trim="diaChi" required="true" autofocus />
                                    <label for="diaChi">Địa chỉ</label>
                                </span>
                            </div>
                        </div>
                    </form>
                </div>
                <!-- cột trái -->
                <div class="p-col-6" style="margin-left: 70px">
                    <div style="display: block; margin-top: 60px">
                        <div class="t" style="border: 1px solid black; border-radius: 10px; width: 200px; height: 180px; margin-top: -60px">
                            <img :src="images" alt="image" style="width: 180px; height: 170px; top: 50%; left: 50%; transform: translate(4%, 2%)" />
                        </div>
                        <div class="buton" style="margin-top: 10px">
                            <FileUpload mode="basic" name="demo[]" accept="image/*" :maxFileSize="1000000" @input="onFileInputImage" style="display: flex" />
                        </div>
                        <small class="p-error">{{ imageError }}</small>
                    </div>
                    <p style="margin-top: 20px">Giới tính</p>
                    <div class="formgrid grid">
                        <div class="field-radiobutton col-6">
                            <RadioButton id="category1" name="gioiTinh" value="1" v-model.trim="gioiTinh" :class="{ 'p-invalid': gioiTinhError }" />
                            <label for="category1">Nam</label>
                        </div>
                        <div class="field-radiobutton col-6">
                            <RadioButton id="category2" name="gioiTinh" value="0" v-model.trim="gioiTinh" :class="{ 'p-invalid': gioiTinhError }" />
                            <label for="category2">Nữ</label>
                        </div>
                        <small class="p-error">{{ gioiTinhError }}</small>
                    </div>
                </div>
            </div>
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
</template>
