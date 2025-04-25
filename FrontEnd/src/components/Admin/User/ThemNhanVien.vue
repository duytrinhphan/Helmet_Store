<script setup>
import { onMounted, ref } from 'vue';
import { useForm, useField } from 'vee-validate';
import { userStore } from '@/service/Admin/User/UserService';
import { useToast } from 'primevue/usetoast';
import * as yup from 'yup';
import { QrcodeStream } from 'vue3-qrcode-reader';
const product = ref({});
const submitted = ref(false);
const productDialog = ref(false);
const userService = userStore();
const toast = useToast();
const addProductDialog = ref(false);
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
    password: yup.string().required('Password không được để trống'),
    gioiTinh: yup.string().required('Vui lòng chọn giới tính '),
    ngaySinh: yup.date().required(' vui lòng chọn ngày sinh '),
    email: yup
        .string()
        .matches(/^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Z|a-z]{2,}$/, 'Email không hợp lệ')
        .required(' Email không được để trống'),
    image: yup.string().required('vui lòng chọn ảnh')
});
const { handleSubmit, resetForm } = useForm({
    validationSchema: schema
});
const { value: ten, errorMessage: tenError } = useField('ten');
const { value: userName, errorMessage: userNameError } = useField('userName');
const { value: password, errorMessage: passError } = useField('password');
const { value: role, errorMessage: roleError } = useField('role');
const { value: email, errorMessage: emailError } = useField('email');
const { value: sdt, errorMessage: sdtError } = useField('sdt');
const { value: ngaySinh, errorMessage: ngaySinhError } = useField('ngaySinh');
const { value: gioiTinh, errorMessage: gioiTinhError } = useField('gioiTinh');
const { value: diaChi, errorMessage: diaChiError } = useField('diaChi');
const { value: image, errorMessage: imageError } = useField('image');
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

const props = defineProps({
    myProp: ''
});

// mở form
const openNew = () => {
    product.value = {};
    submitted.value = false;
    productDialog.value = true;
    images.value = 'https://cdn-icons-png.flaticon.com/512/2956/2956744.png';
};

//đóng form
const hideDialog = () => {
    productDialog.value = false;
    submitted.value = false;
};


const addProduct = async () => {
    submitted.value = true;
    const form = {
        ten: ten.value,
        userName: userName.value,
        password: password.value,
        email: email.value,
        role: props.myProp,
        sdt: sdt.value,
        ngaySinh: ngaySinh.value,
        gioiTinh: gioiTinh.value,
        diaChi: diaChi.value,
        image: image.value,
        trangThai: 0
    };

    try {
        await schema.validate(form);

        // Dữ liệu hợp lệ, thực hiện thêm sản phẩm
        const addedUser = await userService.createUser(form);

        // Gửi email với userName và password
        const mailData = {
            ten: form.ten,
            userName: form.userName,
            password: form.password,
            email: form.email,
        };

        await userService.sendMail(mailData); 

        productDialog.value = false;
        toast.add({ severity: 'success', summary: 'Thông báo', detail: 'Thêm thành công', life: 3000 });
        reset();

    } catch (error) {
        // Dữ liệu không hợp lệ, hiển thị thông báo lỗi bằng Toast
        toast.add({ severity: 'error', summary: 'Lỗi', detail: error.message, life: 3000 });
    }
    addProductDialog.value = false;
};


//save
const saveProduct = () => {
    addProductDialog.value = true;
};

const error = ref('');
const torch = ref(false);

const onDecode = (decodeString) => {
    const data = decodeString.split('|'); // Phân tách chuỗi bằng dấu pipe (|)
    if (data.length >= 5) {
        ten.value = data[2]; // Gán tên từ chuỗi mã QR code vào biến ten
        const rawDate = data[3]; // Lấy chuỗi ngày sinh từ dữ liệu mã QR code
        const year = parseInt(rawDate.substr(4, 4), 10);
        const month = parseInt(rawDate.substr(2, 2), 10) - 1; // Tháng trong JavaScript bắt đầu từ 0
        const day = parseInt(rawDate.substr(0, 2), 10) + 1;
        ngaySinh.value = new Date(year, month, day).toISOString().substr(0, 10); // Chuyển đổi ngày và gán giá trị vào ngaySinh
        const gioiTinhValue = data[4] === 'Nam' ? 1 : 0; // Chuyển đổi giới tính thành 1 nếu là 'Nam', ngược lại là 0
        gioiTinh.value = gioiTinhValue.toString(); // Gán giá trị cho gioiTinh, giá trị này sẽ là '1' hoặc '0'
        diaChi.value = data[5]; // Gán địa chỉ từ chuỗi mã QR code vào biến diaChi
        // console.log('Giới tính:', gioiTinh.value);
    } else {
        error.value = 'Dữ liệu không đủ hoặc không hợp lệ từ mã QR code.';
    }
};

const onError = (error) => {
    console.error('Lỗi quét mã QR:', error);
    // Xử lý lỗi ở đây (nếu cần)
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
    <Button label="Thêm mới" icon="pi pi-plus" class="p-button-success mr-2" @click="openNew" />
    <Dialog v-model:visible="productDialog" :style="{ width: '800px' }" header="Thêm nhân viên" :modal="true" class="p-fluid">
        <div class="card">
            <!-- <Button label="Toggle Camera" icon="pi pi-camera" class="p-button-success" @click="toggleCamera" /> -->
            <div class="flex">
                <!-- Cột phải -->
                <div class="p-col-6" style="width: 400px">
                    <form @submit="onSubmit">
                        <div class="p-fluid formgrid grid">
                            <div class="field col-12" style="margin-bottom: 30px">
                                <span class="p-float-label">
                                    <InputText id="userName" v-model.trim="userName" :class="{ 'p-invalid': userNameError }" required="true" autofocus />
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
                                <span class="p-float-label">
                                    <Password id="password" v-model.trim="password" :class="{ 'p-invalid': passError }" toggleMask />
                                    <label for="password">Mật khẩu</label>
                                </span>
                                <small class="p-error">{{ passError }}</small>
                            </div>
                            <div class="field col-12" style="margin-bottom: 30px">
                                <label for="ngaySinh">Ngày sinh</label>
                                <InputText type="date" v-model.trim="ngaySinh"   />
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
                    <div class="formgrid grid">
                        <div style="width: 200px; height: 170px; margin-left: 10px">
                            <qrcode-stream @decode="onDecode" @error="onError" :torch="torch"></qrcode-stream>
                            <p>{{ error }}</p>
                        </div>
                        <!-- <button @click="toggleTorch">{{ torch ? 'Tắt' : 'Bật' }}</button> -->
                    </div>
                    <div style="display: block; margin-top: 100px">
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
                    <!-- <p>Role</p>
                    <div class="formgrid grid">
                        <div class="field-radiobutton col-6">
                            <RadioButton id="nv" name="role" value="NHANVIEN" v-model.trim="role" :class="{ 'p-invalid': roleError }" />
                            <label for="nv">Nhân viên</label>
                        </div>
                        <div class="field-radiobutton col-6">
                            <RadioButton id="us" name="role" value="USER" v-model.trim="role" :class="{ 'p-invalid': roleError }" />
                            <label for="us">Khách hàng</label>
                        </div>
                        <small class="p-error">{{ roleError }}</small>
                    </div> -->
                </div>
            </div>
        </div>
        <Dialog v-model:visible="addProductDialog" :style="{ width: '450px' }" header="Confirm" :modal="true">
            <div class="flex align-items-center justify-content-center">
                <i class="pi pi-exclamation-triangle mr-3" style="font-size: 2rem" />
                <span v-if="product">Bạn có chắc chắn muốn thêm không ?</span>
            </div>
            <template #footer>
                <Button label="No" icon="pi pi-times" class="p-button-text" @click="addProductDialog = false" />
                <Button label="Yes" icon="pi pi-check" class="p-button-text" @click="addProduct" />
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

