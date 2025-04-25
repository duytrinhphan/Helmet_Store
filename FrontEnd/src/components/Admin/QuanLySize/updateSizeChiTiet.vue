<script setup>
import * as yup from 'yup';
import { useForm, useField } from 'vee-validate';
import { ref, onMounted, onBeforeMount } from 'vue';
import { useToast } from 'primevue/usetoast';
import { SizeStore } from '@/service/Admin/Size/SizeService';
import { ProductStore } from '@/service/Admin/product/product.api';
import TableSize from '@/components/Admin/Product/DataTableSize.vue';

const productStore = ProductStore();
const useSizeService = SizeStore();
const toast = useToast();
const product = ref({});
const submitted = ref(false);
const productDialog = ref(false);
const props = defineProps({
    myProp: {},
    idProduct: Number
});

// confirm thêm
const addProductDialog = ref(false);

//hiện confirm
const confirmAddProduct = () => {
    addProductDialog.value = true;
};

const schema = yup.object().shape({
    idSize: yup.string().required('bạn cần chọn size')
});

const { handleSubmit, resetForm } = useForm({
    validationSchema: schema
});

const { value: idSize, errorMessage: sizeError } = useField('idSize');


//add
const onSubmit = handleSubmit(async (values) => {
    submitted.value = true;

    const form = {
        idSize: values.idSize,
        idSanPhamChiTiet: props.idProduct
    };
    const isDuplicate = await productStore.checkSize(props.idProduct, values.idSize);
    if (isDuplicate) {
        // Hiển thị thông báo lỗi hoặc xử lý theo nhu cầu của bạn
        toast.add({ severity: 'error', summary: 'Error', detail: 'Size đã tồn tại', life: 3000 });
    } else {
        await productStore.editSize(form, props.myProp.id);
    }
    productDialog.value = false;
});

const reset = () => {
    resetForm();
    selectedSize.value = null;
}

const selectedSize = ref(null);
const onSizeChange = () => {
    if (selectedSize.value) {
        idSize.value = selectedSize.value.id;
    } else {
        idSize.value = null;
    }
};

// mở form
const openNew = () => {
    product.value = {};
    submitted.value = false;
    productDialog.value = true;

    selectedSize.value = props.myProp;

    const selectedSizes = dataSize.value.find((item) => item.ten === props.myProp.ten);
    selectedSize.value = selectedSizes;
    if (selectedSize.value) {
        idSize.value = selectedSize.value.id;
    } else {
        idSize.value = null;
    }
};

//đóng form
const hideDialog = () => {
    productDialog.value = false;
    submitted.value = false;
};

//save
const saveProduct = () => {
    addProductDialog.value = true;
};

const dataSize = ref([]);
const loadDataSize = async () => {
    await useSizeService.fetchData();
    dataSize.value = useSizeService.dataByStatus1;
};


onMounted(() => {
    loadDataSize();
});

</script>
<template>
    <Button icon="pi pi-pencil" class="p-button-rounded p-button-success mr-2" @click="openNew" />

    <Dialog v-model:visible="productDialog" :style="{ width: '300px' }" header="Thêm size" :modal="true" class="p-fluid">
        <div class="card">
            <form @submit="onSubmit">
                <div class="p-fluid formgrid grid">
                    <div class="Field col-12 md:col-6" style="margin-bottom: 30px">
                        <div style="display: flex">
                            <span class="p-float-label" style="width: 350px">
                                <Dropdown id="dropdown" :options="dataSize" v-model="selectedSize" optionLabel="ten"
                                    :class="{ 'p-invalid': sizeError }" @change="onSizeChange" style="width: 140px;">
                                </Dropdown>
                                <label for="dropdown">Size</label>
                            </span>
                            <TableSize :tableId="'TableMauSac'" :rightGhId="'right_ghMauSac'" :tableClass="'TableMauSac'"
                                :rightGhClass="'right_ghMauSac'" />
                        </div>
                        <small class="p-error" style="width: 550px;">{{ sizeError }}</small>
                    </div>


                    <div style="width: 1000px; text-align: center;display: flex;">

                        <Button class="p-button-outlined" outlined severity="secondary"
                            style="width: 100px; height: auto; margin: 10px" @click="reset()" label="clear"></Button>
                        <Button type="submit" class="p-button-outlined" style="width: 100px; height: auto; margin: 10px"
                            label="Lưu"></Button>
                    </div>
                </div>

            </form>
        </div>

    </Dialog>
    <!-- </div>
        </div>
    </div> -->
</template>
