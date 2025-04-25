<script setup>
import AppConfig from '@/layout/AppConfig.vue';
import NotFoundAnimation from '@/assets/animation/not-found-animation.json'
import {LottieAnimation} from "lottie-web-vue";
</script>

<template>
  <div class="surface-ground flex align-items-center justify-content-center min-h-screen min-w-screen overflow-hidden">
    <div class="flex flex-column align-items-center justify-content-center">
      <img src="/src/assets/images/logo.png" alt="Sakai logo" class="mb-5 w-6rem flex-shrink-0" />
      <div
          style="border-radius: 56px; padding: 0.3rem; background: linear-gradient(180deg, rgba(33, 150, 243, 0.4) 10%, rgba(33, 150, 243, 0) 30%)">
        <div class="w-full surface-card py-8 px-5 sm:px-8 flex flex-column align-items-center"
             style="border-radius: 53px">
          <h1 class="text-900 font-bold text-3xl lg:text-4xl mb-2">Có vẻ bạn đã đi lạc...</h1>
          <div class="text-600 text-2xs mb-5">Không tìm thấy tài nguyên!</div>
          <LottieAnimation class="w-18rem" :animation-data="NotFoundAnimation" :loop="true" :auto-play="true"
                           :speed="1"/>
          <router-link :to="{ name: 'trang-chu' }" class="w-full flex align-items-center py-5 border-300 border-bottom-1">
                        <span class="flex justify-content-center align-items-center bg-orange-400 border-round"
                              style="height: 3.5rem; width: 3.5rem">
                            <i class="pi pi-fw pi-home text-50 text-2xl"></i>
                        </span>
            <span class="ml-4 flex flex-column">
                            <span class="text-900 lg:text-lg font-medium mb-0">Đi về trang chủ</span>
                        </span>
          </router-link>
          <keep-alive>
          <router-link to="" @click="$router.go(-1)" class="w-full flex align-items-center py-5 border-300 border-bottom-1">
                        <span class="flex justify-content-center align-items-center bg-indigo-400 border-round"
                              style="height: 3.5rem; width: 3.5rem">
                            <i class="pi pi-fw pi-history text-50 text-2xl"></i>
                        </span>
            <span class="ml-4 flex flex-column">
                            <span class="text-900 lg:text-lg font-medium mb-0">Quay lại trang trước</span>
                        </span>
          </router-link>
          </keep-alive>
        </div>
      </div>
    </div>
  </div>
  <AppConfig simple/>
</template>
