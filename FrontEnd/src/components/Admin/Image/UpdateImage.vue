<script setup>
import * as yup from 'yup';
import { useForm, useField } from 'vee-validate';
import { ref, onMounted, onBeforeMount, defineEmits } from 'vue';
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
    anh: yup.string().required('bạn cần chọn ảnh')
});

const { handleSubmit, resetForm } = useForm({
    validationSchema: schema
});

const { value: anh, errorMessage: anhError } = useField('anh');


//add
const emit = defineEmits(['updateImg:myProp'])
const onSubmit = handleSubmit(async (values) => {
    submitted.value = true;
    const form = {
        idSP: props.idProduct,
        anh: values.anh
    }
    const data = await productStore.editImg(form, props.myProp.id);
    emit('updateImg:myProp', data)
    toast.add({ severity: 'success', summary: 'Success Message', detail: 'update thành công', life: 3000 });
    productDialog.value = false;
});

const reset = () => {
    resetForm();
    anh.value = props.myProp.anh;
}


// mở form
const openNew = () => {
    product.value = {};
    submitted.value = false;
    productDialog.value = true;
    anh.value = props.myProp.anh;
};

const anhs = ref(null);
function onFileInputImage(event) {
    const files = event.target.files;
    // Lặp qua từng tệp trong mảng files
    for (const file of files) {
        const objectURL = URL.createObjectURL(file);
        anhs.value = objectURL;
        // Gán giá trị cho phần tử có id là 'imagesChinh' (thay đổi id nếu cần)
        const basePath = "D:\\imgDATN\\"; // Đường dẫn cố định
        const fileName = basePath + file.name;
        anh.value = fileName;
    }
}

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

    <Dialog v-model:visible="productDialog" :style="{ width: '400px' }" header="Sửa ảnh" :modal="true" class="p-fluid">
        <div class="card">
            <form @submit="onSubmit">
                <div class="p-fluid formgrid grid">

                    <div class="t"
                        style="border: 1px solid black; border-radius: 10px; width: 300px; height: 240px; margin-top: 10px; margin-left: 10px; margin-bottom: 20px;">
                        <img :src="anhs == null ? anh : anhs" alt=""
                            style="width: 275px; height: 230px; top: 50%; left: 50%; transform: translate(4%, 2%)" />
                    </div>
                    <div class="buton" style="margin-top: 10px; margin-bottom: 20px;">
                        <FileUpload mode="basic" name="demo[]" accept="image/*" :maxFileSize="1000000"
                            @input="onFileInputImage" style="display: flex; width: 200px; margin-left: 50px;" />
                    </div>
                    <small class="p-error">{{ anhError }}</small>


                    <div style="width: 1000px; text-align: center;display: flex; margin-left: 40px;">

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
