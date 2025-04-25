<!-- eslint-disable no-unused-vars -->
<script setup>
import { FilterMatchMode, FilterOperator } from 'primevue/api';
import { ref, onBeforeMount } from 'vue';
import ChoXacNhanVue from './ChoXacNhan.vue';
import TatCaVue from './TatCa.vue';
import DangChuanBiVue from './DangChuanBi.vue';
import DangGiaoVue from './DangGiao.vue';
import HoanThanhVue from './HoanThanh.vue';
import DaHuyVue from './DaHuy.vue';
import TraHangHoanTienVue from './TraHangHoanTien.vue';
import DaHoanTraVue from './DaHoanTra.vue';
import XacNhanTraHangHoanTien from './XacNhanTraHangHoanTien.vue';
import DaHuyTraHang from './DaHuyTraHang.vue';

const filters1 = ref(null);

const representatives = ref([
    { name: 'Amy Elsner', image: 'amyelsner.png' },
    { name: 'Anna Fali', image: 'annafali.png' },
    { name: 'Asiya Javayant', image: 'asiyajavayant.png' },
    { name: 'Bernardo Dominic', image: 'bernardodominic.png' },
    { name: 'Elwin Sharvill', image: 'elwinsharvill.png' },
    { name: 'Ioni Bowcher', image: 'ionibowcher.png' },
    { name: 'Ivan Magalhaes', image: 'ivanmagalhaes.png' },
    { name: 'Onyama Limba', image: 'onyamalimba.png' },
    { name: 'Stephen Shaw', image: 'stephenshaw.png' },
    { name: 'XuXue Feng', image: 'xuxuefeng.png' }
]);



onBeforeMount(() => {
  
    initFilters1();
});

const initFilters1 = () => {
    filters1.value = {
        global: { value: null, matchMode: FilterMatchMode.CONTAINS },
        name: { operator: FilterOperator.AND, constraints: [{ value: null, matchMode: FilterMatchMode.STARTS_WITH }] },
        'country.name': { operator: FilterOperator.AND, constraints: [{ value: null, matchMode: FilterMatchMode.STARTS_WITH }] },
        representative: { value: null, matchMode: FilterMatchMode.IN },
        date: { operator: FilterOperator.AND, constraints: [{ value: null, matchMode: FilterMatchMode.DATE_IS }] },
        balance: { operator: FilterOperator.AND, constraints: [{ value: null, matchMode: FilterMatchMode.EQUALS }] },
        status: { operator: FilterOperator.OR, constraints: [{ value: null, matchMode: FilterMatchMode.EQUALS }] },
        activity: { value: [0, 50], matchMode: FilterMatchMode.BETWEEN },
        verified: { value: null, matchMode: FilterMatchMode.EQUALS }
    };
};

const formatCurrency = (value) => {
    return value.toLocaleString('en-US', { style: 'currency', currency: 'USD' });
};

const formatDate = (value) => {
    return value.toLocaleDateString('en-US', {
        day: '2-digit',
        month: '2-digit',
        year: 'numeric'
    });
};
</script>

<template>
    <div class="grid">
        <div class="col-12">
            <div class="card">
                <h5>Quản lý hóa đơn</h5>
                <div class="card">
                    <TabView>
                        <TabPanel header="Tất cả">
                            <TatCaVue> </TatCaVue>
                        </TabPanel>
                        <TabPanel header="Chờ xác nhận">
                            <ChoXacNhanVue> </ChoXacNhanVue>
                        </TabPanel>
                        <TabPanel header="Đang giao">
                            <DangGiaoVue> </DangGiaoVue>
                        </TabPanel>
                        <TabPanel header="Hoàn thành">
                            <HoanThanhVue> </HoanThanhVue>
                        </TabPanel>
                        <TabPanel header="Đã hủy">
                            <DaHuyVue> </DaHuyVue>
                        </TabPanel>
                        <TabPanel header="Trả hàng">
                            <TraHangHoanTienVue> </TraHangHoanTienVue>
                        </TabPanel>
                        <TabPanel header="Xác nhận trả hàng">
                            <DaHoanTraVue> </DaHoanTraVue>
                        </TabPanel>
                        <TabPanel header="Huỷ trả hàng">
                            <DaHuyTraHang> </DaHuyTraHang>
                        </TabPanel>
                    </TabView>
                </div>
            </div>
        </div>
    </div>
</template>

<style scoped lang="scss">
@import '@/assets/demo/styles/badges.scss';

::v-deep(.p-datatable-frozen-tbody) {
    font-weight: bold;
}

::v-deep(.p-datatable-scrollable .p-frozen-column) {
    font-weight: bold;
}
</style>
