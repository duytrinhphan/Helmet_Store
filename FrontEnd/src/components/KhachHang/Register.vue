

<script setup>
import { ref, onMounted, watchEffect } from 'vue';
import { userRegisterStore } from '@/service/KhachHang/RegisterService.js';
import { useForm, useField } from 'vee-validate';
import { useToast } from 'primevue/usetoast';
import { userStore } from '@/service/Admin/User/UserService.js';
import khUserService from '@/service/KhachHang/UserService.js';
import { useRouter } from 'vue-router';
import * as yup from 'yup';
const userService = userStore();
const router = useRouter();
const registerService = userRegisterStore();
const schema = yup.object().shape({
  ten: yup
    .string()
    .required('Tên không được để trống')
    .min(4, 'Tên  phải có ít nhất 4 ký tự')
    .matches(/^[a-zA-Z0-9đĐáÁàÀảẢãÃạẠăĂắẮằẰẳẲẵẴặẶâÂấẤầẦẩẨẫẪậẬêÊếẾềỀểỂễỄệỆôÔốỐồỒổỔỗỖộỘơƠớỚờỜởỞỡỠợỢùÙúÚụỤủỦũŨưỨỨửỬữỮựỰýÝỳỲỷỶỹỸỵỴ\s]*$/, 'Tên không được chứa kí tự đặc biệt!'),
  sdt: yup
    .string()
    .required('Số điện thoại không được để trống')
    .matches(/^[0-9]{10}$/, 'Số điện thoại phải chứa đúng 10 số')
    .typeError('Số điện thoại phải là một số')
    .nullable(),
  matKhau: yup.string().required('Password không được để trống'),
  ngaySinh: yup.date().required(' vui lòng chọn ngày sinh '),
  email: yup
    .string()
    .matches(/^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Z|a-z]{2,}$/, 'Email không hợp lệ')
    .required(' Email không được để trống'),
  xacNhanMK: yup.string()
    .oneOf([yup.ref('matKhau'), null], 'Mật khẩu nhập lại phải trùng khớp với mật khẩu')
    .required('Nhập lại mật khẩu không được để trống')

});
const { handleSubmit, resetForm } = useForm({
  validationSchema: schema
});
const { value: ten, errorMessage: tenError } = useField('ten');
const { value: matKhau, errorMessage: passError } = useField('matKhau');
const { value: email, errorMessage: emailError } = useField('email');
const { value: sdt, errorMessage: sdtError } = useField('sdt');
const { value: ngaySinh, errorMessage: ngaySinhError } = useField('ngaySinh');
const { value: xacNhanMK, errorMessage: xacNhanMKError } = useField('xacNhanMK');
const gioiTinh = ref(2);
const toast = useToast();
const otp = ref(null);

const khachHang = ref(null);

const onSubmit = handleSubmit(async () => {

  const form = {
    ten: ten.value,
    email: email.value,
    sdt: sdt.value,
    matKhau: matKhau.value,
    ngaySinh: ngaySinh.value,
    gioiTinh: parseInt(gioiTinh.value)
  }

  await registerService.getUserByEmail(email.value)
  khachHang.value = registerService.data;
 
  if (khachHang.value != null) {
    toast.add({ severity: 'warn', summary: '', detail: 'Email đã tồn tại', life: 3000 });
    return;
  }
  const formString = JSON.stringify(form);

  const randomOTP = Math.floor(1000 + Math.random() * 9000);
  otp.value = randomOTP.toString();

  localStorage.setItem("dangky", formString);
  localStorage.setItem("otp", otp.value);

  const mailData = {
    ten: ten.value,
    email: email.value,
    title: otp.value
  }

  await registerService.sendOTP(mailData);
  toast.add({ severity: 'success', summary: '', detail: 'OTP được gửi trong mail của bạn', life: 7000 });
  await router.push({ name: 'xac-thuc' });
});

</script>
<template>
  <div class="surface-ground flex align-items-center justify-content-center min-h-screen min-w-screen overflow-hidden">
    <Toast />
    <div class="flex flex-column align-items-center justify-content-center ">
      <img src="/src/assets/images/logo.png" alt="Sakai logo" class="mb-5 w-6rem flex-shrink-0" />
      <div
        style="border-radius: 56px; padding: 0.3rem; background: linear-gradient(180deg, var(--primary-color) 10%, rgba(33, 150, 243, 0) 30%)">
        <div class="w-full surface-card py-8 px-5 sm:px-8" style="border-radius: 53px; margin-bottom: 20px">
          <div class="text-center mb-5">
            <div class="text-900 text-3xl font-medium mb-3">Xin chào!</div>
            <span class="text-600 font-medium">Đăng ký để tiếp tục</span>
          </div>

          <div class="col-12" style="width: 800px;">
           
              <form class="form" action="#" @submit="onSubmit" >
                <div class="p-fluid formgrid grid">
                <div class="Field col-12 md:col-6" style="margin-bottom: 20px;" >
                  <div style="height: 90px">
                    <label for="email1" class="block text-900 text-xl font-medium mb-2">Họ và tên: </label>
                    <InputText placeholder="Nhập họ và tên" type="text" v-model="ten" :class="{ 'p-invalid': tenError }"
                      class="w-full md:w-25rem mb-5" style="padding: 1rem" />
                  </div>
                  <small class="p-error">{{ tenError }}</small>
                </div>

                <div class="Field col-12 md:col-6" style="margin-bottom: 20px;">
                  <div style="height: 90px">
                    <label for="email1" class="block text-900 text-xl font-medium mb-2">Email: </label>
                    <InputText placeholder="Nhập email" type="email" v-model="email" autofocus
                      :class="{ 'p-invalid': emailError }" class="w-full md:w-30rem mb-5" style="padding: 1rem" />
                  </div>
                  <small class="p-error">{{ emailError }}</small>
                </div>

                <div class="Field col-12 md:col-6" style="margin-bottom: 20px;">
                  <div style="height: 90px">
                    <label for="email1" class="block text-900 text-xl font-medium mb-2">Số điện thoại: </label>
                    <InputText placeholder="Nhập số điện thoại" type="text" v-model="sdt" autofocus
                      :class="{ 'p-invalid': sdtError }"  class="w-full md:w-25rem mb-5"  style="padding: 1rem" />
                  </div>
                  <small class="p-error">{{ sdtError }}</small>
                </div>
                <div class="Field col-12 md:col-6" style="display: flex;">
                  <div>
                    <label for="email1" class="block text-900 text-xl font-medium mb-2">Ngày sinh: </label>
                  <Calendar dateFormat="mm/dd/yy" placeholder="Enter birth date" v-model="ngaySinh"
                    :class="{ 'p-invalid': ngaySinhError }" class="w-full md:w-10rem mb-5" />

                  <small class="p-error">{{ ngaySinhError }}</small>
                  </div>
                  

                 
                </div>
                <div class="Field col-12 md:col-12 flex flex-wrap gap-3" style="margin-bottom: 20px;">
                  <label for="email1" class="block text-900 text-xl font-medium mb-2">Giới tính: </label>
                  <div class="flex align-items-center">
                    <RadioButton value="1" v-model="gioiTinh" inputId="ingredient1" name="pizza" />
                    <label for="ingredient1" class="ml-2">nam</label>
                  </div>
                  <div class="flex align-items-center">
                    <RadioButton value="0" v-model="gioiTinh" inputId="ingredient2" name="pizza" />
                    <label for="ingredient2" class="ml-2">nữ</label>
                  </div>

                </div>

                <div class="Field col-12 md:col-6" style="margin-bottom: 10px">
                  <div style="height: 90px">
                    <label for="password1" class="block text-900 font-medium text-xl mb-2">Mật khẩu: </label>
                    <Password id="password1" type="password" v-model="matKhau" autofocus
                      :class="{ 'p-invalid': passError }" placeholder="Nhập mật khẩu" :toggleMask="true"
                      class="w-full mb-3" inputClass="w-full" inputStyle="padding:1rem"></Password>
                  </div>
                  <small class="p-error">{{ passwordError }}</small>
                </div>

                <div class="Field col-12 md:col-6" style="margin-bottom: 10px">
                  <div style="height: 90px">
                    <label for="password1" class="block text-900 font-medium text-xl mb-2">Nhập lại mật khẩu: </label>
                    <Password id="password1" placeholder="Nhập lại mật khẩu" type="password" v-model="xacNhanMK" autofocus
                      :class="{ 'p-invalid': xacNhanMKError }" :toggleMask="true" class="w-full mb-3" inputClass="w-full"
                      inputStyle="padding:1rem"></Password>
                  </div>
                  <small class="p-error">{{ xacNhanMKError }}</small>
                </div>
              </div>
              <Button type="submit" label="Đăng Ký" class="w-5 p-3 text-xl" severity="secondary"  style="margin-left: 200px; margin-top: 20px;"></Button>
              </form>
          </div>
        </div>
      </div>
    </div>
  </div>
  <AppConfig simple />
</template>


<style scoped>
/*  */
</style>