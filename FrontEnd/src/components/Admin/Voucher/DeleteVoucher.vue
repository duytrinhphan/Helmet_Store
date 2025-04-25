<template>
    <Button label="Delete" icon="pi pi-trash" class="p-button-danger" @click="confirmDeleteSelected" :disabled="!selectedVoucher || !selectedVoucher.length" />
    <Dialog v-model:visible="deleteSelectedVoucherDialog" :style="{ width: '450px' }" header="Confirm" :modal="true">
        <div class="flex align-items-center justify-content-center">
            <i class="pi pi-exclamation-triangle mr-3" style="font-size: 2rem" />
            <span >Bạn có chắc chắn muốn xoá những voucher được chọn ?</span>
        </div>
        <template #footer>
            <Button label="No" icon="pi pi-times" class="p-button-text" @click="hideDialogDelete" />
            <Button label="Yes" icon="pi pi-check" class="p-button-text" @click="deleteVoucher" />
        </template>
    </Dialog>
</template>

<script setup>
import { ref, defineProps } from 'vue';
import { voucherStore } from '@/service/Admin/Voucher/VoucherService.js';
const VoucherService = voucherStore();
const deleteSelectedVoucherDialog = ref(false);
const props = defineProps({
    selectedVoucher: Array
});

const confirmDeleteSelected = () => {
    deleteSelectedVoucherDialog.value = true;
};

const hideDialogDelete = () => {
    deleteSelectedVoucherDialog.value = false;
};

const deleteVoucher = () => {
    props.selectedVoucher.forEach((voucher) => {
        const voucherId = voucher.id;
        const updatedVoucher = { ...voucher, status: 'newStatus' };
        VoucherService.deleteVoucher(updatedVoucher, voucherId);     
        deleteSelectedVoucherDialog.value = false;       
    });
    toast.add({ severity: 'warn', summary: '', detail: 'Xoá thành công', life: 3000 });
};

</script>
