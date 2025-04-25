<script setup>
import {useThongTin} from '@/service/KhachHang/ThongTinCaNhanService';
import {inject, ref} from "vue";
import {useToast} from "primevue/usetoast";

const store = useThongTin();
const toast = useToast();
const oldPassword = ref('');
const newPassword = ref('');
const rePassword = ref('');
const dialogRef = inject('dialogRef');

const doiMatKhau = async () => {
  if (oldPassword.value.length === 0 || newPassword.value.length === 0 || rePassword.value.length === 0) {
    toast.add({ severity: 'error', summary: 'Không thể thực hiện', detail: 'Vui lòng nhập đầy đủ', life: 3000 });
    return;
  }
  if (newPassword.value !== rePassword.value) {
    toast.add({ severity: 'error', summary: 'Không thể thực hiện', detail: 'Nhập lại mật khẩu không khớp', life: 3000 });
    return;
  }
  try {
    const currentUser = JSON.parse(localStorage.getItem("currentUserInformation"));
    await store.doiMatKhau(currentUser.id, oldPassword.value, newPassword.value);
    toast.add({ severity: 'success', summary: 'Thành công', detail: 'Mật khẩu đã được thay đổi', life: 3000 });
    dialogRef.value.close();
  } catch (e) {
    toast.add({ severity: 'error', summary: 'Thất bại', detail: e.response.data, life: 3000 });
  }
}
</script>

<template>
  <Toast/>
  <div>
    <label for="old-password" class="block text-900 font-medium mb-2">Mật khẩu cũ</label>
    <InputText v-model.trim="oldPassword" id="old-password" type="password" class="w-full mb-3"/>
    <label for="new-password" class="block text-900 font-medium mb-2">Mật khẩu mới</label>
    <InputText v-model.trim="newPassword" id="new-password" type="password" class="w-full mb-3"/>
    <label for="re-password" class="block text-900 font-medium mb-2">Xác nhận mật khẩu mới</label>
    <InputText v-model.trim="rePassword" id="re-password" type="password" class="w-full mb-3"/>
    <Button @click="doiMatKhau" label="Xác nhận" icon="pi pi-key" class="w-full"></Button>
  </div>
</template>

<style scoped>

</style>