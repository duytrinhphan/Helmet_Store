<template>
      <Toast />
    <div class="form">
        <Toast />
        <div class="title">OTP</div>
        <div class="title">Mã xác nhận</div>
        <p class="message">Chúng tôi đã gửi mã xác minh tới email của bạn</p>
        <div class="inputs">
            <input id="input1" type="text" maxlength="1" v-model="input1" /> 
            <input id="input2" type="text" maxlength="1" v-model="input2"/> 
            <input id="input3" type="text" maxlength="1" v-model="input3"/> 
            <input id="input4" type="text" maxlength="1" v-model="input4"/>
        </div>
        <button class="action" @click="verify">Xác minh</button>
    </div>
</template>

<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import { useToast } from 'primevue/usetoast';
import { userRegisterStore} from '@/service/KhachHang/RegisterService.js';
import {useChatStore} from "@/service/Admin/Chat/ChatService";
const registerService =  userRegisterStore();
const chatStore = useChatStore();
const router = useRouter();
const toast = useToast();
const input1 = ref('');
const input2 = ref('');
const input3 = ref('');
const input4 = ref('');
const dataDangKy = ref([]);


const verify = async () => {
    const otp = localStorage.getItem("otp");

    const enteredOTP = input1.value + input2.value + input3.value + input4.value;

  if (enteredOTP === otp) {

    const form = localStorage.getItem("dangky");
    const storedForm = JSON.parse(form);

    await registerService.register(storedForm);
    dataDangKy.value = registerService.data;
  
    toast.add({ severity: 'success', summary: '', detail: 'Đăng ký thành công. Vui lòng đăng nhập', life: 6000 });
    localStorage.removeItem("otp");
    localStorage.removeItem("dangky");
    await chatStore.createChatkittyUser(dataDangKy.value.email, dataDangKy.value.ten);
    await router.push({ name: 'login' });
  } else {
   
    toast.add({ severity: 'warn', summary: '', detail: 'OTP không đúng. Vui lòng thử lại', life: 3000 });

  }


}
 
</script>

<style scoped>
.form {
    display: flex;
    position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
    align-items: center;
    flex-direction: column;
    justify-content: space-around;
    width: 800px;
    height: 400px;
    background-color: white;
    border-radius: 12px;
    padding: 20px;
}

.title {
    font-size: 20px;
    font-weight: bold;
    color: black;
}

.message {
    color: #a3a3a3;
    font-size: 14px;
    margin-top: 4px;
    text-align: center;
}

.inputs {
    margin-top: 10px;
}

.inputs input {
    width: 32px;
    height: 32px;
    text-align: center;
    border: none;
    border-bottom: 1.5px solid #d2d2d2;
    margin: 0 10px;
}

.inputs input:focus {
    border-bottom: 1.5px solid royalblue;
    outline: none;
}

.action {
    margin-top: 24px;
    padding: 12px 16px;
    border-radius: 8px;
    border: none;
    background-color: royalblue;
    color: white;
    cursor: pointer;
    align-self: end;
}
</style>
