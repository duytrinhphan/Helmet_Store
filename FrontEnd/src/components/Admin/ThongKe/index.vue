<script setup>
import { onMounted, reactive, ref, watch } from 'vue';

import { useLayout } from '@/layout/composables/layout';
// import ThongKeHoaDon from './ThongKeHoaDon.vue';
import ThongKeSanPham from './ThongKeLoiNhuan.vue';
import ThongKeTheoNgay from './ThongKeDoanhThu.vue';

const { isDarkTheme } = useLayout();



const products = ref(null);

const lineOptions = ref(null);


onMounted(() => {
   // productService.getProductsSmall().then((data) => (products.value = data));
});

const formatCurrency = (value) => {
    return value.toLocaleString('en-US', { style: 'currency', currency: 'USD' });
};
const applyLightTheme = () => {
    lineOptions.value = {
        plugins: {
            legend: {
                labels: {
                    color: '#495057'
                }
            }
        },
        scales: {
            x: {
                ticks: {
                    color: '#495057'
                },
                grid: {
                    color: '#ebedef'
                }
            },
            y: {
                ticks: {
                    color: '#495057'
                },
                grid: {
                    color: '#ebedef'
                }
            }
        }
    };
};

const applyDarkTheme = () => {
    lineOptions.value = {
        plugins: {
            legend: {
                labels: {
                    color: '#ebedef'
                }
            }
        },
        scales: {
            x: {
                ticks: {
                    color: '#ebedef'
                },
                grid: {
                    color: 'rgba(160, 167, 181, .3)'
                }
            },
            y: {
                ticks: {
                    color: '#ebedef'
                },
                grid: {
                    color: 'rgba(160, 167, 181, .3)'
                }
            }
        }
    };
};

watch(
    isDarkTheme,
    (val) => {
        if (val) {
            applyDarkTheme();
        } else {
            applyLightTheme();
        }
    },
    { immediate: true }
);
</script>

<template>

    <div class="card">
        <div class="card">
            <ThongKeTheoNgay></ThongKeTheoNgay>
            <!-- <TabView>
                <TabPanel header="Thống kê doanh thu">
                    
                </TabPanel>
                <TabPanel header="Thống kê Lợi nhuận">
                    <ThongKeSanPham></ThongKeSanPham>
                </TabPanel>
              
                <TabPanel header="Thống kê hoá đơn">
                    <ThongKeHoaDon></ThongKeHoaDon>
                </TabPanel>
            </TabView> -->

        </div>
    </div>
</template>

<style scoped>
.grid {
    margin-top: 1px;
}
</style>
