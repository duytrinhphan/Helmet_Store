<template>
    <Button label="Delete" icon="pi pi-trash" class="p-button-danger" @click="confirmDeleteSelected" :disabled="!selectedKhuyenMai || !selectedKhuyenMai.length" />
    <Dialog v-model:visible="deleteSelectedKhuyenMaiDialog" :style="{ width: '450px' }" header="Confirm" :modal="true">
        <div class="flex align-items-center justify-content-center">
            <i class="pi pi-exclamation-triangle mr-3" style="font-size: 2rem" />
            <span>Bạn có chắc chắn muốn xoá những khuyến mại được chọn</span>
        </div>
        <template #footer>
            <Button label="No" icon="pi pi-times" class="p-button-text" @click="hideDialogDelete" />
            <Button label="Yes" icon="pi pi-check" class="p-button-text" @click="deleteKhuyenMai" />
        </template>
    </Dialog>
</template>

<script setup>
import { ref,  defineProps } from 'vue';
import { useToast } from 'primevue/usetoast';
import { khuyenMaiStore } from '@/service/Admin/KhuyenMai/KhuyenMaiService.js';

const khuyenMaiService = khuyenMaiStore();
const deleteSelectedKhuyenMaiDialog = ref(false);
const toast = useToast();
const props = defineProps({
    selectedKhuyenMai: Array
});

const confirmDeleteSelected = () => {
    deleteSelectedKhuyenMaiDialog.value = true;
};

const hideDialogDelete = () => {
    deleteSelectedKhuyenMaiDialog.value = false;
};

const deleteKhuyenMai = () => {
    props.selectedKhuyenMai.forEach((khuyenmai) => {
        const khuyenmaiId = khuyenmai.id;
        const updatedKhuyenMai = { ...khuyenmai, status: 'newStatus' };
        khuyenMaiService.deleteKhuyenMai(updatedKhuyenMai, khuyenmaiId);      
        deleteSelectedKhuyenMaiDialog.value = false;
    });
    toast.add({ severity: 'warn', summary: '', detail: 'Xoá thành công', life: 3000 });
};


</script>
