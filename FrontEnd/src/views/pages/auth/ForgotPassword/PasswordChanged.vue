<script setup>
import PasswordChangedAnimation from '@/assets/animation/password-changed-animation.json';
import {LottieAnimation} from "lottie-web-vue";
import {onMounted, ref} from "vue";
import {useRouter} from "vue-router";

const router = useRouter();
const count = ref(10);

onMounted(() => {
  setInterval(() => {
    count.value--;
    if (count.value === 0) router.push({name: 'login'});
  }, 1000);
});
</script>

<template>
  <div class="flex flex-column justify-content-center align-items-center h-screen">
    <LottieAnimation class="w-1" :animation-data="PasswordChangedAnimation" :loop="true" :auto-play="true" :speed="0.8"/>
    <h2 class="font-bold text-green-600">Mật khẩu đã được thay đổi!</h2>
    <p class="font-medium">Quý khách đã đặt lại thành công mật khẩu tài khoản tại VNK.</p>
    <p class="font-bold">Hệ thống sẽ tự động chuyển đến trang đăng nhập sau <span class="text-red-600">{{count}}</span> giây...</p>
    <router-link :to="{name: 'login'}" class="">Chuyển ngay và luôn!!!</router-link>
  </div>

</template>

<style scoped>
</style>