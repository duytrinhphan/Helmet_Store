<script setup>
import {useRoute, useRouter} from "vue-router";
import {onBeforeMount, ref} from "vue";
import {usePasswordStore} from "@/service/KhachHang/PasswordService";
import {useToast} from "primevue/usetoast";
import { useForm, useField, defineRule } from 'vee-validate';
import * as yup from 'yup';

const store = usePasswordStore();
const toast = useToast();
const route = useRoute();
const router = useRouter();
const email = ref();
// const password = ref();
let token = null;


const schema = yup.object().shape({
  
  matKhau: yup.string().required('Password không được để trống'),
  xacNhanMK: yup.string()
    .oneOf([yup.ref('matKhau'), null], 'Mật khẩu nhập lại phải trùng khớp với mật khẩu')
    .required('Nhập lại mật khẩu không được để trống')

});
const { handleSubmit, resetForm } = useForm({
  validationSchema: schema
});

const { value: matKhau, errorMessage: passError } = useField('matKhau');
const { value: xacNhanMK, errorMessage: xacNhanMKError } = useField('xacNhanMK');

onBeforeMount( async () => {
  const reqParams = route.query;
  email.value = reqParams.email;
  token = reqParams.token;
  if (!email.value || !token) await router.push({name: 'invalid-access'});
  try {
    await store.resetPassword(email.value, token, null);
  } catch (e) {
    await router.push({name: 'invalid-access'});
  }
});


const onSubmit = handleSubmit(async (val) => {
  try {
    await store.resetPassword(email.value, token, val.matKhau);
    await router.push({name: 'password-changed'});
  } catch (e) {
    await router.push({name: 'invalid-access'});
  }
});

</script>

<template>
  <Toast/>
  <div class="h-screen flex justify-content-center align-items-center">
    <div class="surface-card p-6 shadow-2 border-round-lg w-3">
      <div class="text-center mb-5">
        <img src="/src/assets/images/logo.png" alt="Image" height="80"/>
        <div class="text-900 text-3xl font-medium mb-3">Đặt lại mật khẩu</div>
      </div>
      <form  @submit="onSubmit">
      <div>
        <label for="email" class="block text-900 font-medium mb-2">Email</label>
        <InputText :modelValue="email" id="email" type="text" class="w-full mb-3 font-medium" disabled/>
        <label for="password" class="block text-900 font-medium mb-2">Mật khẩu mới</label>
        <InputText v-model="matKhau" id="password" type="password" placeholder="Nhập mật khẩu mới" class="w-full mb-3"  :class="{ 'p-invalid': passError }"/>
        <small class="p-error">{{ passError }}</small>
        <label for="re-password" class="block text-900 font-medium mb-2">Nhập lại mật khẩu</label>
        <InputText v-model="xacNhanMK" id="re-password" type="password" placeholder="Xác nhận lại mật khẩu" class="w-full mb-3"  :class="{ 'p-invalid': xacNhanMKError }"/>
        <small class="p-error">{{ xacNhanMKError }}</small>
        <div class="flex align-items-center justify-content-between mb-4">
          <router-link class="font-medium ml-2 text-right" :to="{name: 'trang-chu'}">Đi đến trang chủ</router-link>
          <router-link class="font-medium ml-2 text-right" :to="{name: 'login'}">Quay lại đăng nhập</router-link>
        </div>

        <Button type="submit" label="Đổi mật khẩu" icon="pi pi-key" class="w-full"></Button>
      </div></form>
    </div>
  </div>
</template>

<style scoped>

</style>