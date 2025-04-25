<script setup>
</script>

<template>
  <transition name="transitionName"
              :appear="true"
              enterActiveClass="fadeinleft">
    <router-view/>
  </transition>
</template>

<style scoped></style>
