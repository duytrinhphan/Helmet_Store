<script setup>
import { ref } from 'vue';

const product = ref({});
const productDialog = ref(false);

//nhận dữ liệu từ cha
const props = defineProps({
    myProp: {}
});

// mở form
const editProduct = () => {
    product.value = { ...editProduct };
    productDialog.value = true;
};
</script>
<template>
    <Button icon="pi pi-eye" class="p-button-rounded p-button-success mr-2" @click="editProduct()" />

    <Dialog v-model:visible="productDialog" :style="{ width: '450px' }" header="Detail trọng lượng" :modal="true" class="p-fluid">
        <div class="p-col-6">
            <div class="col-12">
                <label
                    >Mã: <span>{{ myProp.ma }}</span></label
                >
            </div>
            <div class="col-12">
                <label
                    >Value: <span>{{ myProp.value }}</span></label
                >
            </div>
            <div class="col-12">
                <label
                    >Đơn vị: <span>{{ myProp.donVi }}</span></label
                >
            </div>
            <div class="col-12">
                <label
                    >Ngày tạo: <span>{{ myProp.ngayTao }}</span></label
                >
            </div>
            <div class="col-12">
                <label
                    >Ngày sửa: <span>{{ myProp.ngaySua }}</span></label
                >
            </div>
            <div class="col-12">
                <label
                    >Trạng thái: <span>{{ myProp.trangThai == 1 ? 'Đang sử dụng' : 'Ngừng sử dụng' }}</span></label
                >
            </div>
        </div>
    </Dialog>
    <!-- </div>
        </div>
    </div> -->
</template>
