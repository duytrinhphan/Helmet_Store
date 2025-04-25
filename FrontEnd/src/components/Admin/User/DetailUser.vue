<script setup>
import { ref } from 'vue';

const product = ref({});
const productDialog = ref(false);

const props = defineProps({
    myProp: {}
});

const editProduct = () => {
    product.value = { ...editProduct };
    productDialog.value = true;
};
</script>
<template>
    <Button icon="pi pi-eye" class="p-button-rounded p-button-success mr-2" @click="editProduct()" />

    <Dialog v-model:visible="productDialog" :style="{ width: '500px' }" header="Detail user" :modal="true" class="p-fluid">
        <div class="flex">
            <!-- Cột phải -->
            <div class="p-col-6">
                <div class="col-12">
                    <label
                        >Mã: <span>{{ myProp.ma }}</span></label
                    >
                </div>
                <div class="col-12">
                    <label
                        >Role: <span>{{ myProp.role }}</span></label
                    >
                </div>
                <div class="col-12">
                    <label
                        >Tên: <span>{{ myProp.ten }}</span></label
                    >
                </div>
                <div class="col-12">
                    <label
                        >User name: <span>{{ myProp.userName }}</span></label
                    >
                </div>
                <div class="col-12">
                    <label
                        >Giới tính: <span>{{ myProp.gioiTinh == 1 ? 'Nam' : 'Nữ' }}</span></label
                    >
                </div>
                <div class="col-12">
                    <label
                        >Ngày sinh: <span>{{ myProp.ngaySinh }}</span></label
                    >
                </div>
                <div class="col-12">
                    <label
                        >Sdt: <span>{{ myProp.sdt }}</span></label
                    >
                </div>
                <div class="col-12">
                    <label
                        >Email: <span>{{ myProp.email }}</span></label
                    >
                </div>
                <div class="col-12">
                    <label
                        >Số lượng hóa đơn: <span>{{ myProp.soLuongHoaDon }}</span></label
                    >
                </div>
                <div class="col-12">
                    <label
                        >Địa chỉ:
                        <div v-for="i in myProp.diaChi">
                            <div class="col-6" style="width: 100%">
                                <p>{{ i.diaChi }}</p>
                            </div>
                        </div></label
                    >
                </div>
                <!-- <div class="col-12">
                    <label>Địa chỉ: <span>Thôn Đông Mai, xã Trung Nghĩa, huyện Yên Phong, tỉnh Bắc Ninh.</span></label>
                </div> -->
                <div class="col-12">
                    <label
                        >Trạng thái: <span>{{ myProp.trangThai == 1 ? 'Tài khoản bị khóa' : 'Đang hoạt động' }}</span></label
                    >
                </div>
            </div>
            <!-- cột trái -->
            <div class="p-col-6">
                <img :src="myProp.anh" alt="User Image" style="width: 100%" />
            </div>
        </div>
    </Dialog>
    <!-- </div>
        </div>
    </div> -->
</template>
