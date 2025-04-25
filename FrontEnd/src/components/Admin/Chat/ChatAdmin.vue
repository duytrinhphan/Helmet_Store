<script setup>
import ChatComponent from "@/components/Admin/Chat/ChatComponent.vue";

</script>

<template> 
  <div class="h-full">
<!--    <Button label="Search" severity="success" icon="pi pi-search" />-->
    <!--    <span class="user-logged" :class="{ 'user-logged-dark': theme === 'dark' }">-->
    <!--      Logged in as-->
    <!--    </span>-->
    <!--    <select v-model="username">-->
    <!--      <option v-for="user in users" :key="user.username" :value="user.username">-->
    <!--        {{ user.name }}-->
    <!--      </option>-->
    <!--    </select>-->

    <!--    <div class="button-theme">-->
    <!--      <button class="button-light" @click="theme = 'light'">Light</button>-->
    <!--      <button class="button-dark" @click="theme = 'dark'">Dark</button>-->
    <!--    </div>-->

    <ChatComponent height="890px" :single-room="false"/>
  </div>
</template>

<style scoped>
</style>