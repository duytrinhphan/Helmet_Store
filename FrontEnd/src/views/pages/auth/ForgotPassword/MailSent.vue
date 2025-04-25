<script setup>
import MailSentAnimation from '@/assets/animation/mail-sent-animation.json';
import {LottieAnimation} from "lottie-web-vue";
</script>

<template>
  <div class="flex flex-column justify-content-center align-items-center h-screen">
    <LottieAnimation class="w-1 -ml-6 mb-5"  :animation-data="MailSentAnimation" :loop="true" :auto-play="true" :speed="0.8"/>
    <h2 class="font-bold text-green-600">Email đã được gửi đi!</h2>
    <p class="font-medium mb-0">Chúng tôi đã gửi email xử lý yêu cầu khôi phục mật khẩu, vui lòng kiểm tra hộp thư của mình.</p>
    <p class="font-medium">Rất mong sớm gặp lại quý khách!</p>
    <router-link :to="{name: 'trang-chu'}" class="">Đi đến trang chủ</router-link>
  </div>

</template>

<style scoped>
</style>