<script setup>

import { ref, defineProps, onMounted } from 'vue';
import View from '@/components/Admin/TrongLuong/Index.vue';

const props = defineProps({
    tableId: String,
    rightGhId: String,
    tableClass: String,
    rightGhClass: String
});

const display = ref(false);
const isRightGhLoaiVisible = ref(true);

const tableClass = ref(props.tableClass);
const rightGhClass = ref(props.rightGhClass);


const formatCurrency = (value) => {
    return value.toLocaleString('en-US', { style: 'currency', currency: 'USD' });
};

const chuyenPhanTus = (id) => {
    isRightGhLoaiVisible.value = false;
    const element = document.getElementById(id);
    window.scroll({
        top: element.offsetTop,
        behavior: 'smooth'
    });
};

const vePhanTus = (id) => {
    isRightGhLoaiVisible.value = true;
    const element = document.getElementById(id);
    if (element) {
        window.scroll({
            top: element.offsetTop,
            behavior: 'smooth'
        });
    }
};

const open = () => {
    display.value = true;
};

const close = () => {
    display.value = false;
};

const selectedProduct = ref(null);
const products = ref(null);
</script>

<template>
    <div>
        <Dialog header="Trọng lượng" v-model:visible="display" :breakpoints="{ '960px': '75vw' }" :style="{ width: '1000px' }" :modal="true">
            <!-- <section :class="tableClass" :id="tableId">
                <div :class="{ hidden: !isRightGhLoaiVisible }">
                    <Button label="New" icon="pi pi-plus" class="p-button-success mr-2" @click="chuyenPhanTus(rightGhId)" style="margin-bottom: 10px" />
                    <DataTable :value="products" v-model:selection="selectedProduct" selectionMode="single" :paginator="true" :rows="5" @row-select="onProductSelect" responsiveLayout="scroll">
                        <Column field="name" header="Name" :sortable="true" headerStyle="min-width:12rem;"></Column>
                        <Column header="Image" headerStyle="min-width:5rem;">
                            <template #body="slotProps">
                                <img :src="'demo/images/product/' + slotProps.data.image" :alt="slotProps.data.image" width="50" class="shadow-2" />
                            </template>
                        </Column>
                        <Column field="price" header="Price" :sortable="true" headerStyle="min-width:8rem;">
                            <template #body="slotProps">
                                {{ formatCurrency(slotProps.data.price) }}
                            </template>
                        </Column>
                        <Column>
                            <template #body="slotProps">
                                <Button icon="pi pi-pencil" class="p-button-rounded p-button-success mr-2" />
                                <Button icon="pi pi-trash" class="p-button-rounded p-button-warning mt-2" />
                            </template>
                        </Column>
                    </DataTable>
                </div>
            </section>
            <section :class="rightGhClass" :id="rightGhId">
                <div :class="{ hidden: isRightGhLoaiVisible }">
                    <Button icon="pi pi-angle-double-left" label="  " class="p-button-outlined" @click="vePhanTus(tableId)" />
                    <div class="tong" style="position: relative; top: 50%; left: 50%; transform: translate(-50%, 0%); width: 500px; height: 270px">
                        <div class="ten" style="margin: 20px 0px 20px 0px">
                            <span class="p-float-label">
                                <InputNumber id="inputnumber"></InputNumber>
                                <label for="inputnumber">Tên</label>
                            </span>
                        </div>
                        <div class="trangThai" style="margin: 20px 0px 20px 0px">
                            <label for="address">Trạng thái</label>
                            <div class="flex flex-wrap gap-3" style="margin-top: 10px">
                                <div class="flex align-items-center">
                                    <RadioButton v-model="ingredient" inputId="ingredient1" name="pizza" value="Cheese" />
                                    <label for="ingredient1" class="ml-2">Cheese</label>
                                </div>
                                <div class="flex align-items-center">
                                    <RadioButton v-model="ingredient" inputId="ingredient2" name="pizza" value="Mushroom" />
                                    <label for="ingredient2" class="ml-2">Mushroom</label>
                                </div>
                            </div>
                        </div>

                        <div class="moTa" style="margin: 20px 0px 20px 0px">
                            <label for="address">Mô tả</label>
                            <Textarea id="address" rows="4" style="width: 500px" />
                        </div>
                    </div>
                    <Button label="Lưu" @click="close" icon="pi pi-check" class="p-button-outlined" style="margin-left: 400px" />
                </div>
            </section> -->
            <View></View>
            <template #footer> </template>
        </Dialog>
        <Button type="button" icon="pi pi-pencil" @click="open" class="p-button-success" style="width: 50px; height: 40px; margin-left: 20px" />
    </div>
</template>
