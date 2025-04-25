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

    <Dialog v-model:visible="productDialog" :style="{ width: '450px' }" header="Địa chỉ" :modal="true" class="p-fluid">
        <div class="p-col-6">
            <div class="col-12">
                <label
                    >Tỉnh thành: <span>{{ myProp.tenTinhThanh }}</span></label
                >
            </div>
            <div class="col-12">
                <label
                    >Quận huyện: <span>{{ myProp.tenQuanHuyen }}</span></label
                >
            </div>
            <div class="col-12">
                <label
                    >Phường xã: <span>{{ myProp.tenphuongXa }}</span></label
                >
            </div>
            <div class="col-12">
                <label
                    >Địa chỉ cụ thể: <span>{{ myProp.diaChi }}</span></label
                >
            </div>
        </div>
    </Dialog>
    <!-- </div>
        </div>
    </div> -->
</template>
