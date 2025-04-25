<script setup>
import TatCa from './TatCa.vue';
import HoanThanh from './HoanThanh.vue';
import DaHuy from './DaHuy.vue';
import ChoThanhToan from './ChoThanhToan.vue';
import VanChuyen from './VanChuyen.vue';
import DangGiao from './DangGiao.vue';
import HoanTra from './TraHangHoanTien.vue';
</script>
<template>
    <div class="container">
        <div class="grid">
            <TabView class="tab-view">
                <div class="card">
                    <TabPanel header="Tất cả">
                        <TatCa></TatCa>
                    </TabPanel>

                    <TabPanel header="Đang chuẩn bị">
                        <VanChuyen></VanChuyen>
                    </TabPanel>
                    <TabPanel header="Đang giao">
                        <DangGiao></DangGiao>
                    </TabPanel>
                    <TabPanel header="Hoàn thành">
                        <HoanThanh></HoanThanh>
                    </TabPanel>
                    <TabPanel header="Đã huỷ">
                        <DaHuy></DaHuy>
                    </TabPanel>
                    <TabPanel header="Hoàn trả">
                        <HoanTra></HoanTra>
                    </TabPanel>
                </div>
            </TabView>
        </div>
    </div>
</template>

<style scoped>
.grid {
    /* margin-top: 70px; */
    /* position: absolute;
    top: 0;
    left: 0;
    right: 0; */
    margin: auto;
    margin-top: 80px;
    margin-bottom: 30px;
    width: 1100px;
    background: rgb(255, 255, 255);
    display: flex;
    justify-content: center;

    /* align-items: center; */
}

.tab-view {
    width: 100%;
    text-align: center;
}
</style>
