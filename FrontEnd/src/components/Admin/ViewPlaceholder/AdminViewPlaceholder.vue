<script setup>
import AnimatedLogo from '@/assets/animation/animated-logo-1.json';
import {LottieAnimation} from "lottie-web-vue";
</script>

<template>
  <LottieAnimation class="w-7 m-auto" :animation-data="AnimatedLogo" :loop="true" :auto-play="true" :speed="0.8"/>
</template>

<style scoped>
</style>