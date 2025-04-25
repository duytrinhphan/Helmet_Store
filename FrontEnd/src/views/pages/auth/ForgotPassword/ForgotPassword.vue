<script setup>
import {ref} from "vue";
import {useRouter} from "vue-router";
import {usePasswordStore} from "@/service/KhachHang/PasswordService";
import {useToast} from "primevue/usetoast";
import Loading from "vue-loading-overlay";
import 'vue-loading-overlay/dist/css/index.css';
import { useForm, useField, defineRule } from 'vee-validate';
import * as yup from 'yup';
const store = usePasswordStore();
const router = useRouter();
const toast = useToast();
// const email = ref();
const uiBlock = ref(false);


const schema = yup.object().shape({
  email: yup.string().required('Vui lòng điền email'),

});
const { handleSubmit, resetForm } = useForm({
    validationSchema: schema
});

const { value: email, errorMessage: emailError } = useField('email');

const onSubmit = handleSubmit(async (values) => {
  try {
    uiBlock.value = !uiBlock.value;
    await store.handleForgotPassword(values.email);
    uiBlock.value = !uiBlock.value;
    await router.push({name: 'mail-sent'});
  } catch (e) {
    uiBlock.value = !uiBlock.value;
    toast.add({severity: 'error', summary: 'Không thể thực hiện', detail: e.response.data, life: 3500});
  }
});
</script>

<template>
  <Toast/>
  <Loading v-model:active="uiBlock"
           :can-cancel="false"
           :is-full-page="true"
           color="#007bff"
           bgColor="#ffffff"
           height="80"
           width="80"
           loader="spinner"
           :lock-scroll="true"/>
  <div class="h-screen flex justify-content-center align-items-center">
    <div class="surface-card p-5 shadow-2 border-round w-3">
      <div class="text-center mb-5">
        <img src="/src/assets/images/logo.png" alt="Image" height="80"/>
        <div class="text-900 text-3xl font-medium mb-3">Quên mật khẩu</div>
        <span class="text-600 font-medium line-height-3">Chưa có tài khoản?</span>
        <a class="font-medium no-underline ml-2 text-blue-500 cursor-pointer">
          <router-link :to="{name: 'dang-ky'}">Tạo ngay!</router-link>
        </a>
      </div>
      <form  @submit="onSubmit">
        <div>
        <label for="email" class="block text-900 font-medium mb-2">Email</label>
        <InputText v-model="email" id="email" type="text" placeholder="Nhập email của bạn" class="w-full mb-3" :class="{ 'p-invalid': emailError }"/>
        <small class="p-error">{{ emailError }}</small>
        <div class="flex align-items-center justify-content-between mb-4">
          <router-link class="font-medium ml-2 text-right" :to="{name: 'trang-chu'}">Đi đến trang chủ</router-link>
          <router-link class="font-medium ml-2 text-right" :to="{name: 'login'}">Quay lại đăng nhập</router-link>
        </div>

        <Button  type="submit" label="Xác nhận" icon="pi pi-user" class="w-full"></Button>
      </div>

      </form>
    
    </div>
  </div>
</template>

<style scoped>

</style>