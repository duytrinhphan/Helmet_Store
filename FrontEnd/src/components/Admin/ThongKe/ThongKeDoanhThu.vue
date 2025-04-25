<template>
    <div class="p-fluid formgrid grid">
        <div class="Field col-12 md:col-12" style="background: rgb(255, 255, 255); width: 100%; height: 120px; margin-bottom: 10px; display: flex">
            <div class="Field col-12 md:col-2" style="height: 30px; margin-left: -25px">
                <Panel header="Tổng Doanh Thu" style="width: 100%; height: 40px">
                    <div style="display: flex">
                        <div style="margin-right: 20px; text-align: center">
                            <p class="m-0" style="text-align: center; font-weight: 750; font-size: 1.4rem">{{ formatCurrency(tongDoanhThu) }}</p>
                        </div>
                        <div></div>
                    </div>
                </Panel>
            </div>
            <div class="Field col-12 md:col-2" style="height: 30px">
                <Panel header="Tổng Hoàn tiền" style="width: 170px; height: 40px">
                    <div style="display: flex">
                        <div style="margin-right: 20px; text-align: center">
                            <p class="m-0" style="text-align: center; font-weight: 750; font-size: 1.4rem">{{ formatCurrency(tongHoanTien) }}</p>
                        </div>
                        <div></div>
                    </div>
                </Panel>
            </div>
            <div class="Field col-12 md:col-2" style="height: 30px">
                <Panel header="Tổng Chiết khấu" style="width: 170px; height: 40px">
                    <div style="display: flex">
                        <div style="margin-right: 20px; text-align: center">
                            <p class="m-0" style="text-align: center; font-weight: 750; font-size: 1.4rem">{{ formatCurrency(tongChietKhau) }}</p>
                        </div>
                        <div></div>
                    </div>
                </Panel>
            </div>

            <div class="Field col-12 md:col-2" style="height: 30px">
                <Panel header="Đơn hoàn thành" style="width: 150px; height: 40px">
                    <div style="display: flex">
                        <div style="margin-left: 40px; text-align: center">
                            <p class="m-0" style="text-align: center; font-weight: 750; font-size: 1.4rem">{{ tongDonHoanThanh }}</p>
                        </div>
                        <div></div>
                    </div>
                </Panel>
            </div>

            <div class="Field col-6 md:col-4" style="height: 40px">
                <Panel header="Tìm kiếm" style="width: 100%; height: 40px">
                    <div class="flex flex-wrap gap-3" style="display: flex">
                        <Button type="button" label="Năm/tháng" @click="toggle" style="width: 105px; height: 40px; background: none; color: black" />

                        <Button type="button" label="khác" @click="toggle1" style="width: 70px; height: 40px; background: none; color: black" />
                        <Button type="button" label="Tháng" @click="load()" style="width: 50px; height: 40px; background: none; color: black"> <i class="pi pi-replay" style="font-size: 1.8rem; margin-right: 00px; margin-left: -5px"></i></Button>
                        <OverlayPanel ref="op">
                            <H6>Hãy chọn năm</H6>
                            <div v-for="(o, index) in loadNam" style="display: inline-block; margin-right: 10px; margin-bottom: 20px">
                                <div class="flex align-items-center">
                                    <RadioButton v-model="vNam" type="radio" :inputId="'ingredient' + index" name="nam" :value="o.name" />
                                    <label :for="'ingredient' + index" class="ml-2">{{ o.name }}</label>
                                </div>
                            </div>
                            <form @submit="onSubmit">
                                <H6></H6>
                                <div style="display: flex; height: 50px; margin-bottom: 20px">
                                    <div class="" style="height: 30px; margin-right: 20px; display: block">
                                        <label style="width: 100px">Ngày bắt đầu</label>
                                        <span class="p-float-label">
                                            <InputText type="datetime-local" style="width: 160px" v-model="startDate" :class="{ 'p-invalid': startDateError }" />
                                            <!-- <label  style="width: 100px;">start month</label> -->
                                        </span>
                                        <small class="p-error">{{ startDateError }}</small>
                                    </div>
                                    <div class="" style="height: 30px; margin-right: 20px; display: block">
                                        <label style="width: 100px">Ngày kết thúc</label>
                                        <span class="p-float-label">
                                            <InputText type="datetime-local" style="width: 160px" v-model="endDate" :class="{ 'p-invalid': endDateError }" />
                                            <!-- <label  style="width: 100px;">start month</label> -->
                                        </span>
                                        <small class="p-error">{{ endDateError }}</small>
                                    </div>
                                    <Button type="submit" style="background: none; height: 50px; border: none; margin-top: 10px"><i class="pi pi-search" style="font-size: 1.8rem; color: blue"></i></Button>
                                </div>
                            </form>
                        </OverlayPanel>
                        <OverlayPanel ref="op1" style="height: 120px; width: 500px">
                            <div style="display: flex">
                                <p>hình thức:</p>
                                <div class="flex align-items-center" style="margin-right: 20px; margin-left: 20px; margin-top: -10px">
                                    <RadioButton v-model="vModelHinhThuc" type="radio" :inputId="'ingredient' + index" name="nam" value="1" />
                                    <label :for="'ingredient' + index" class="ml-2">tại quầy</label>
                                </div>
                                <div class="flex align-items-center" style="margin-top: -10px">
                                    <RadioButton v-model="vModelHinhThuc" type="radio" :inputId="'ingredient' + index" name="nam" value="2" />
                                    <label :for="'ingredient' + index" class="ml-2">đặt hàng</label>
                                </div>
                            </div>
                            <div style="display: flex; margin-top: 10px">
                                <p>Hoặc:</p>
                                <div class="Field col-12 md:col-2" style="margin-right: 50px; height: 30px; margin-top: -10px">
                                    <span class="p-float-label" style="height: 40px">
                                        <Dropdown id="dropdown" :options="dataLoai" v-model="selectedLoai" optionLabel="ten" :class="{ 'p-invalid': loaiError }" style="height: 40px; width: 120px"> </Dropdown>
                                        <label for="dropdown">Loại</label>
                                    </span>
                                </div>

                                <div class="Field col-12 md:col-2" style="margin-right: 70px; margin-top: -10px">
                                    <span class="p-float-label" style="height: 40px; width: 100px">
                                        <Dropdown id="dropdown" :options="dataThuongHieu" v-model="selectedCity" optionLabel="ten" @change="onCityChange" style="height: 40px; width: 140px"> </Dropdown>
                                        <label for="dropdown">Thương Hiệu</label>
                                    </span>
                                </div>
                                <div class="Field col-12 md:col-2" style="margin-right: 0px; margin-top: -10px">
                                    <span class="p-float-label" style="height: 40px; width: 100px">
                                        <Dropdown id="dropdown" :options="products" v-model="selectedProduct" optionLabel="ten" @change="onCityChange" style="height: 40px; width: 140px"> </Dropdown>
                                        <label for="dropdown">Sản phẩm</label>
                                    </span>
                                </div>
                            </div>
                        </OverlayPanel>
                    </div>
                </Panel>
            </div>
        </div>
        <div style="margin-top: 10px; font-weight: 700">Hôm nay</div>
        <div class="Field col-12 md:col-12" style="background: rgb(255, 255, 255); display: flex; margin-left: 3px; margin-top: 10px; width: 100%; height: 130px">
            <div class="flex Field col-12 md:col-6" style="margin-right: 10px; height: 30px; margin-left: -15px; height: 100%; border-radius: 20px; background-color: #f4f4f4; box-shadow: 5px 5px 5px grey">
                <div class="Field col-12 md:col-4" style="margin-top: 10px; margin-left: -12px; height: 80%; border-right: 1px solid">
                    <div style="font-weight: 700">Doanh thu:</div>
                    <div style="margin-right: 20px; margin-top: 20px; text-align: center">
                        <p class="m-0" style="text-align: center; font-weight: 700; font-size: 1.2rem">{{ formatCurrency(doanhThu) }}</p>
                    </div>
                </div>
                <div class="Field col-12 md:col-4" style="margin-top: 10px; margin-left: 12px; height: 80%; border-right: 1px solid">
                    <div style="font-weight: 700; margin-left: -10px">Doanh thu tại quầy:</div>
                    <div style="margin-right: 20px; margin-top: 20px; text-align: center">
                        <p class="m-0" style="text-align: center; font-weight: 700; font-size: 1.2rem">{{ formatCurrency(doanhThuTaiQuay) }}</p>
                    </div>
                </div>
                <div class="Field col-12 md:col-4" style="margin-top: 10px; margin-left: 12px; height: 80%">
                    <div style="font-weight: 700">Doanh thu online:</div>
                    <div style="margin-right: 20px; margin-top: 20px; text-align: center">
                        <p class="m-0" style="text-align: center; font-weight: 700; font-size: 1.2rem">{{ formatCurrency(doanhThuOnline) }}</p>
                    </div>
                </div>
            </div>
            <div class="flex Field col-12 md:col-6" style="margin-right: 10px; height: 30px; height: 100%; border-radius: 20px; background-color: #f4f4f4; box-shadow: 5px 5px 5px grey">
                <div class="Field col-12 md:col-4" style="margin-top: 10px; margin-left: -12px; height: 80%; border-right: 1px solid">
                    <div style="font-weight: 700">Đơn mua:</div>
                    <div style="margin-right: 20px; margin-top: 20px; text-align: center">
                        <p class="m-0" style="text-align: center; font-weight: 700; font-size: 1.2rem">{{ donMua }}</p>
                    </div>
                </div>
                <div class="Field col-12 md:col-4" style="margin-top: 10px; margin-left: 12px; height: 80%; border-right: 1px solid">
                    <div style="font-weight: 700">Đơn Trả:</div>
                    <div style="margin-right: 20px; margin-top: 20px; text-align: center">
                        <p class="m-0" style="text-align: center; font-weight: 700; font-size: 1.2rem">{{ donTra }}</p>
                    </div>
                </div>
                <div class="Field col-12 md:col-4" style="margin-top: 10px; margin-left: 12px; width: 150px; height: 80%">
                    <div style="font-weight: 700">Đơn hủy:</div>
                    <div style="margin-right: 20px; margin-top: 20px; text-align: center">
                        <p class="m-0" style="text-align: center; font-weight: 700; font-size: 1.2rem">{{ donHuy }}</p>
                    </div>
                </div>
            </div>
        </div>
        <div class="Field col-12 md:col-12" style="background: rgb(255, 255, 255); display: flex; margin-left: 0px; margin-top: 20px">
            <div>
                <div class="Field col-12 md:col-3" style="margin-bottom: 10px; background: rgb(255, 255, 255); width: 240px; height: 250px; border-radius: 20px; border: 1px solid black">
                    <Chart type="polarArea" :data="chartLoai" :options="chartOptionsLoai" class="w-full md:w-17rem" style="margin-left: -10px" />
                </div>
                <div class="Field col-12 md:col-3" style="margin-bottom: 10px; background: rgb(255, 255, 255); width: 240px; height: 260px; border-radius: 20px; border-radius: 20px; border: 1px solid black">
                    <Chart type="polarArea" :data="chartThuongHieu" :options="chartOptionsThuongHieu" class="w-full md:w-17rem" style="margin-left: -10px" />
                </div>
            </div>

            <div class="" style="margin-bottom: 10px; background: rgb(255, 255, 255); width: 1110px; margin-left: 10px">
                <div class="Field col-12 md:col-9" style="margin-bottom: 10px; background: rgb(255, 255, 255); width: 100%; height: 250px; border-radius: 20px; order-radius: 20px; border: 1px solid black">
                    <h5 style="margin-top: 2px">Doanh Thu Theo Tháng</h5>
                    <Chart type="line" :data="chartData" :options="chartOptions" class="h-15rem" />
                </div>
                <div class="Field col-12 md:col-9" style="margin-bottom: 10px; background: rgb(255, 255, 255); width: 100%; height: 260px; display: flex">
                    <div class="Field col-12 md:col-6" style="margin-left: -10px; margin-bottom: 10px; background: rgb(255, 255, 255); height: 260px; border-radius: 20px; margin-right: 20px; order-radius: 20px; border: 1px solid black">
                        <h5 style="margin-top: 2px">Top Sản phẩm doanh thu cao nhất</h5>
                        <Chart type="bar" :data="chartDataSPCao" :options="chartOptionsSPCao" class="h-15rem" style="margin-top: -10px; margin-left: -10px" />
                    </div>
                    <div class="Field col-12 md:col-6" style="margin-bottom: 10px; background: rgb(255, 255, 255); height: 260px; border-radius: 20px; order-radius: 20px; border: 1px solid black">
                        <h5 style="margin-top: 2px">Top Sản phẩm doanh thu thấp nhất</h5>
                        <Chart type="bar" :data="chartDataSPThap" :options="chartOptionsSPThap" class="h-15rem" style="margin-top: -10px; margin-left: -10px" />
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue';
import { ThongKeStore } from '@/service/Admin/ThongKe/ThongKe.api';
import { useLoaiService } from '@/service/Admin/Loai/LoaiService';
import { useCounterStore } from '@/service/Admin/ThuongHieu/ThuongHieuService.js';
import { ProductStore } from '@/service/Admin/product/product.api';
import { useForm, useField, defineRule } from 'vee-validate';
import * as yup from 'yup';
import OverlayPanel from 'primevue/overlaypanel';
import ExcelJS from 'exceljs';

const loaiStore = useLoaiService();
const thongKeStore = ThongKeStore();
const thuongHieuService = useCounterStore();
const productStore = ProductStore();

const vModelHinhThuc = ref();
const data = ref([]);
const tongDoanhThu = ref(0);
const tongHoanTien = ref(0);
const tongChietKhau = ref(0);
const tongDonHoanThanh = ref(0);
const lstAdminThongKeLoaiResponses = ref([]);
const lstAdminThongKeSanPhamCaoResponses = ref([]);
const lstAdminThongKeSanPhamThapResponses = ref([]);
const lstAdminThongKeThangResponses = ref([]);
const lstAdminThongKeThuongHieuResponses = ref([]);
const lstAdminThongKeThangNamResponses = ref([]);

const schema = yup.object().shape({
    startDate: yup.string().required('vui lòng chọn ngày bắt đầu'),
    endDate: yup.string().required('vui lòng chọn chọn ngày kết thúc')
});

const { handleSubmit, resetForm } = useForm({
    validationSchema: schema
});
const { value: startDate, errorMessage: startDateError } = useField('startDate');
const { value: endDate, errorMessage: endDateError } = useField('endDate');

const onSubmit = handleSubmit(async (values) => {
    await thongKeStore.fetchAllByMonth(values.startDate, values.endDate);
    tongDoanhThu.value = thongKeStore.tongDoanhThu;
    tongHoanTien.value = thongKeStore.tongHoanTien;
    tongChietKhau.value = thongKeStore.tongChietKhau;
    tongDonHoanThanh.value = thongKeStore.tongDonHoanThanh;
    lstAdminThongKeLoaiResponses.value = thongKeStore.lstAdminThongKeLoaiResponses;
    lstAdminThongKeSanPhamCaoResponses.value = thongKeStore.lstAdminThongKeSanPhamCaoResponses;
    lstAdminThongKeSanPhamThapResponses.value = thongKeStore.lstAdminThongKeSanPhamThapResponses;
    lstAdminThongKeThangResponses.value = thongKeStore.lstAdminThongKeThangResponses;
    lstAdminThongKeThuongHieuResponses.value = thongKeStore.lstAdminThongKeThuongHieuResponses;
    lstAdminThongKeThangNamResponses.value = thongKeStore.lstAdminThongKeThangNamResponses;

    chartData.value = setChartData();
    chartOptions.value = setChartOptions();
    chartLoai.value = setChartLoai();
    chartOptionsLoai.value = setChartOptionsLoai();
    chartThuongHieu.value = setChartThuongHieu();
    chartOptionsThuongHieu.value = setChartOptionsThuongHieu();
    chartDataSPCao.value = setChartDataSpCao();
    chartOptionsSPCao.value = setChartOptionsSpCao();
    chartDataSPThap.value = setChartDataSpThap();
    chartOptionsSPThap.value = setChartOptionsSpThap();
});

const vNam = ref(null);
const op = ref();
const toggle = (event) => {
    op.value.toggle(event);
};

const op1 = ref();
const toggle1 = (event) => {
    op1.value.toggle(event);
};

const formatCurrency = (value) => {
    return value ? value.toLocaleString('vi-VN', { style: 'currency', currency: 'VND' }) : 0;
};

const loadData = async () => {
    await thongKeStore.fetchAll();
    data.value = thongKeStore.data;
    tongDoanhThu.value = thongKeStore.tongDoanhThu;
    tongHoanTien.value = thongKeStore.tongHoanTien;
    tongChietKhau.value = thongKeStore.tongChietKhau;
    tongDonHoanThanh.value = thongKeStore.tongDonHoanThanh;
    lstAdminThongKeLoaiResponses.value = thongKeStore.lstAdminThongKeLoaiResponses;
    lstAdminThongKeSanPhamCaoResponses.value = thongKeStore.lstAdminThongKeSanPhamCaoResponses;
    lstAdminThongKeSanPhamThapResponses.value = thongKeStore.lstAdminThongKeSanPhamThapResponses;
    lstAdminThongKeThangResponses.value = thongKeStore.lstAdminThongKeThangResponses;
    lstAdminThongKeThuongHieuResponses.value = thongKeStore.lstAdminThongKeThuongHieuResponses;
    lstAdminThongKeThangNamResponses.value = thongKeStore.lstAdminThongKeThangNamResponses;

    chartData.value = setChartData();
    chartOptions.value = setChartOptions();
    chartLoai.value = setChartLoai();
    chartOptionsLoai.value = setChartOptionsLoai();
    chartThuongHieu.value = setChartThuongHieu();
    chartOptionsThuongHieu.value = setChartOptionsThuongHieu();
    chartDataSPCao.value = setChartDataSpCao();
    chartOptionsSPCao.value = setChartOptionsSpCao();
    chartDataSPThap.value = setChartDataSpThap();
    chartOptionsSPThap.value = setChartOptionsSpThap();
};

const doanhThu = ref(0);
const doanhThuTaiQuay = ref(0);
const doanhThuOnline = ref(0);
const donMua = ref(0);
const donTra = ref(0);
const donHuy = ref(0);
const loadDataDay = async () => {
    await thongKeStore.fetchAllByDay();
    doanhThu.value = thongKeStore.doanhThu;
    doanhThuTaiQuay.value = thongKeStore.doanhThuTaiQuay;
    doanhThuOnline.value = thongKeStore.doanhThuOnline;
    donMua.value = thongKeStore.donMua;
    donTra.value = thongKeStore.donTra;
    donHuy.value = thongKeStore.donHuy;
};

const load = () => {
    loadData();
    resetForm();
    selectedLoai.value = null;
    selectedCity.value = null;
    selectedProduct.value = null;
    vNam.value = null;
    vModelHinhThuc.value = null;
};

const dataLoai = ref([]);
const selectedLoai = ref(null);
const loadDataLoai = async () => {
    await loaiStore.fetchDataByStatus(1);
    dataLoai.value = loaiStore.dataByStatus1;
};

const dataThuongHieu = ref([]);
const selectedCity = ref(null);
const loadDataThuongHieu = async () => {
    await thuongHieuService.fetchDataByStatus(1);
    dataThuongHieu.value = thuongHieuService.dataByStatus1;
    // ThuongHieu.value =  dataThuongHieu.value.ten;
};

const products = ref([]);
const selectedProduct = ref(null);
const loadProducts = async () => {
    await productStore.fetchAll(); // Gọi hàm fetchAll từ Store
    products.value = productStore.products;
};
const onHinhThucGiaoHang = async (id) => {
    await thongKeStore.fetchAllByHinhThucGiaoHang(id);
    tongDoanhThu.value = thongKeStore.tongDoanhThu;
    tongHoanTien.value = thongKeStore.tongHoanTien;
    tongChietKhau.value = thongKeStore.tongChietKhau;
    tongDonHoanThanh.value = thongKeStore.tongDonHoanThanh;
    lstAdminThongKeLoaiResponses.value = thongKeStore.lstAdminThongKeLoaiResponses;
    lstAdminThongKeSanPhamCaoResponses.value = thongKeStore.lstAdminThongKeSanPhamCaoResponses;
    lstAdminThongKeSanPhamThapResponses.value = thongKeStore.lstAdminThongKeSanPhamThapResponses;
    lstAdminThongKeThangResponses.value = thongKeStore.lstAdminThongKeThangResponses;
    lstAdminThongKeThuongHieuResponses.value = thongKeStore.lstAdminThongKeThuongHieuResponses;
    lstAdminThongKeThangNamResponses.value = thongKeStore.lstAdminThongKeThangNamResponses;
};
watch(vModelHinhThuc, async (newVal) => {
    if (vModelHinhThuc.value == null || vModelHinhThuc.value == '') {
        return;
    }
    await onHinhThucGiaoHang(vModelHinhThuc.value);
    chartData.value = setChartData();
    chartOptions.value = setChartOptions();
    chartLoai.value = setChartLoai();
    chartOptionsLoai.value = setChartOptionsLoai();
    chartThuongHieu.value = setChartThuongHieu();
    chartOptionsThuongHieu.value = setChartOptionsThuongHieu();
    chartDataSPCao.value = setChartDataSpCao();
    chartOptionsSPCao.value = setChartOptionsSpCao();
    chartDataSPThap.value = setChartDataSpThap();
    chartOptionsSPThap.value = setChartOptionsSpThap();
});

const onloaiChangeLoai = async (id, year) => {
    await thongKeStore.fetchAllByLoai(id, year);
    tongDoanhThu.value = thongKeStore.tongDoanhThu;
    tongHoanTien.value = thongKeStore.tongHoanTien;
    tongChietKhau.value = thongKeStore.tongChietKhau;
    tongDonHoanThanh.value = thongKeStore.tongDonHoanThanh;
    lstAdminThongKeLoaiResponses.value = thongKeStore.lstAdminThongKeLoaiResponses;
    lstAdminThongKeSanPhamCaoResponses.value = thongKeStore.lstAdminThongKeSanPhamCaoResponses;
    lstAdminThongKeSanPhamThapResponses.value = thongKeStore.lstAdminThongKeSanPhamThapResponses;
    lstAdminThongKeThangResponses.value = thongKeStore.lstAdminThongKeThangResponses;
    lstAdminThongKeThuongHieuResponses.value = thongKeStore.lstAdminThongKeThuongHieuResponses;
    lstAdminThongKeThangNamResponses.value = thongKeStore.lstAdminThongKeThangNamResponses;
};
watch(selectedLoai, async (newVal) => {
    if (vNam.value === null) {
        vNam.value = new Date().getFullYear();
    }
    if (selectedLoai.value == null || selectedLoai.value == '') {
        return;
    }
    await onloaiChangeLoai(selectedLoai.value.id, vNam.value);
    chartData.value = setChartData();
    chartOptions.value = setChartOptions();
    chartLoai.value = setChartLoai();
    chartOptionsLoai.value = setChartOptionsLoai();
    chartThuongHieu.value = setChartThuongHieu();
    chartOptionsThuongHieu.value = setChartOptionsThuongHieu();
    chartDataSPCao.value = setChartDataSpCao();
    chartOptionsSPCao.value = setChartOptionsSpCao();
    chartDataSPThap.value = setChartDataSpThap();
    chartOptionsSPThap.value = setChartOptionsSpThap();
});

const onloaiChangeThuongHieu = async (id, year) => {
    await thongKeStore.fetchAllByThuongHieu(id, year);
    tongDoanhThu.value = thongKeStore.tongDoanhThu;
    tongHoanTien.value = thongKeStore.tongHoanTien;
    tongChietKhau.value = thongKeStore.tongChietKhau;
    tongDonHoanThanh.value = thongKeStore.tongDonHoanThanh;
    lstAdminThongKeLoaiResponses.value = thongKeStore.lstAdminThongKeLoaiResponses;
    lstAdminThongKeSanPhamCaoResponses.value = thongKeStore.lstAdminThongKeSanPhamCaoResponses;
    lstAdminThongKeSanPhamThapResponses.value = thongKeStore.lstAdminThongKeSanPhamThapResponses;
    lstAdminThongKeThangResponses.value = thongKeStore.lstAdminThongKeThangResponses;
    lstAdminThongKeThuongHieuResponses.value = thongKeStore.lstAdminThongKeThuongHieuResponses;
    lstAdminThongKeThangNamResponses.value = thongKeStore.lstAdminThongKeThangNamResponses;
};

watch(selectedCity, async (newVal) => {
    if (vNam.value === null) {
        vNam.value = new Date().getFullYear();
    }
    if (selectedCity.value == null || selectedCity.value == '') {
        return;
    }
    await onloaiChangeThuongHieu(selectedCity.value.id, vNam.value);
    chartData.value = setChartData();
    chartOptions.value = setChartOptions();
    chartLoai.value = setChartLoai();
    chartOptionsLoai.value = setChartOptionsLoai();
    chartThuongHieu.value = setChartThuongHieu();
    chartOptionsThuongHieu.value = setChartOptionsThuongHieu();
    chartDataSPCao.value = setChartDataSpCao();
    chartOptionsSPCao.value = setChartOptionsSpCao();
    chartDataSPThap.value = setChartDataSpThap();
    chartOptionsSPThap.value = setChartOptionsSpThap();
});

const onloaiChangeYear = async (year) => {
    await thongKeStore.fetchAllByYear(year);
    tongDoanhThu.value = thongKeStore.tongDoanhThu;
    tongHoanTien.value = thongKeStore.tongHoanTien;
    tongChietKhau.value = thongKeStore.tongChietKhau;
    tongDonHoanThanh.value = thongKeStore.tongDonHoanThanh;
    lstAdminThongKeLoaiResponses.value = thongKeStore.lstAdminThongKeLoaiResponses;
    lstAdminThongKeSanPhamCaoResponses.value = thongKeStore.lstAdminThongKeSanPhamCaoResponses;
    lstAdminThongKeSanPhamThapResponses.value = thongKeStore.lstAdminThongKeSanPhamThapResponses;
    lstAdminThongKeThangResponses.value = thongKeStore.lstAdminThongKeThangResponses;
    lstAdminThongKeThuongHieuResponses.value = thongKeStore.lstAdminThongKeThuongHieuResponses;
    lstAdminThongKeThangNamResponses.value = thongKeStore.lstAdminThongKeThangNamResponses;
};

const onloaiChangeSanPham = async (id, year) => {
    await thongKeStore.fetchAllBySanPham(id, year);
    tongDoanhThu.value = thongKeStore.tongDoanhThu;
    tongHoanTien.value = thongKeStore.tongHoanTien;
    tongChietKhau.value = thongKeStore.tongChietKhau;
    tongDonHoanThanh.value = thongKeStore.tongDonHoanThanh;
    lstAdminThongKeLoaiResponses.value = thongKeStore.lstAdminThongKeLoaiResponses;
    lstAdminThongKeSanPhamCaoResponses.value = thongKeStore.lstAdminThongKeSanPhamCaoResponses;
    lstAdminThongKeSanPhamThapResponses.value = thongKeStore.lstAdminThongKeSanPhamThapResponses;
    lstAdminThongKeThangResponses.value = thongKeStore.lstAdminThongKeThangResponses;
    lstAdminThongKeThuongHieuResponses.value = thongKeStore.lstAdminThongKeThuongHieuResponses;
    lstAdminThongKeThangNamResponses.value = thongKeStore.lstAdminThongKeThangNamResponses;
};

watch(selectedProduct, async (newVal) => {
    if (vNam.value === null) {
        vNam.value = new Date().getFullYear();
    }

    if (selectedProduct.value == null || selectedProduct.value == '') {
        return;
    }
    await onloaiChangeSanPham(selectedProduct.value.id, vNam.value);
    chartData.value = setChartData();
    chartOptions.value = setChartOptions();
    chartLoai.value = setChartLoai();
    chartOptionsLoai.value = setChartOptionsLoai();
    chartThuongHieu.value = setChartThuongHieu();
    chartOptionsThuongHieu.value = setChartOptionsThuongHieu();
    chartDataSPCao.value = setChartDataSpCao();
    chartOptionsSPCao.value = setChartOptionsSpCao();
    chartDataSPThap.value = setChartDataSpThap();
    chartOptionsSPThap.value = setChartOptionsSpThap();
});

watch(vNam, async (newVal) => {
    if (selectedProduct.value === null || selectedCity.value === null || selectedLoai.value === null) {
        await onloaiChangeYear(vNam.value);
        chartData.value = setChartData();
        chartOptions.value = setChartOptions();
        chartLoai.value = setChartLoai();
        chartOptionsLoai.value = setChartOptionsLoai();
        chartThuongHieu.value = setChartThuongHieu();
        chartOptionsThuongHieu.value = setChartOptionsThuongHieu();
        chartDataSPCao.value = setChartDataSpCao();
        chartOptionsSPCao.value = setChartOptionsSpCao();
        chartDataSPThap.value = setChartDataSpThap();
        chartOptionsSPThap.value = setChartOptionsSpThap();
    }
    if (selectedCity.value !== null) {
        await onloaiChangeThuongHieu(selectedCity.value.id, vNam.value);
        chartData.value = setChartData();
        chartOptions.value = setChartOptions();
        chartLoai.value = setChartLoai();
        chartOptionsLoai.value = setChartOptionsLoai();
        chartThuongHieu.value = setChartThuongHieu();
        chartOptionsThuongHieu.value = setChartOptionsThuongHieu();
        chartDataSPCao.value = setChartDataSpCao();
        chartOptionsSPCao.value = setChartOptionsSpCao();
        chartDataSPThap.value = setChartDataSpThap();
        chartOptionsSPThap.value = setChartOptionsSpThap();
    }
    if (selectedLoai.value !== null) {
        await onloaiChangeLoai(selectedLoai.value.id, vNam.value);
        chartData.value = setChartData();
        chartOptions.value = setChartOptions();
        chartLoai.value = setChartLoai();
        chartOptionsLoai.value = setChartOptionsLoai();
        chartThuongHieu.value = setChartThuongHieu();
        chartOptionsThuongHieu.value = setChartOptionsThuongHieu();
        chartDataSPCao.value = setChartDataSpCao();
        chartOptionsSPCao.value = setChartOptionsSpCao();
        chartDataSPThap.value = setChartDataSpThap();
        chartOptionsSPThap.value = setChartOptionsSpThap();
    }
    if (selectedProduct.value !== null) {
        await onloaiChangeSanPham(selectedProduct.value.id, vNam.value);
        chartData.value = setChartData();
        chartOptions.value = setChartOptions();
        chartLoai.value = setChartLoai();
        chartOptionsLoai.value = setChartOptionsLoai();
        chartThuongHieu.value = setChartThuongHieu();
        chartOptionsThuongHieu.value = setChartOptionsThuongHieu();
        chartDataSPCao.value = setChartDataSpCao();
        chartOptionsSPCao.value = setChartOptionsSpCao();
        chartDataSPThap.value = setChartDataSpThap();
        chartOptionsSPThap.value = setChartOptionsSpThap();
    }
});

onMounted(async () => {
    loadDataLoai();
    await loadData();
    loadDataThuongHieu();
    loadProducts();
    loadDataDay();
   // xuatBaoCao();
    loadNam.value = nam;
    loadThang.value = thang;
});

const modalTimKiem = ref(null);
const modal = ref(false);
const modal2 = ref(false);
const openModalTimKiem = () => {
    if (modalTimKiem.value === 'theoNam') {
        modal.value = true;
    } else if (modalTimKiem.value === 'theoThang') {
        modal2.value = true;
    }
};

const loadNam = ref([]);
const currentYear = new Date().getFullYear();
const startYear = 2018;
const nam = Array.from({ length: currentYear - startYear + 1 }, (_, i) => ({ name: (startYear + i).toString() }));

const loadThang = ref([]);
const thang = [{ name: '01' }, { name: '02' }, { name: '03' }, { name: '04' }, { name: '05' }, { name: '06' }, { name: '07' }, { name: '08' }, { name: '09' }, { name: '10' }, { name: '11' }, { name: '12' }];

const vThang = ref();

const chartLoai = ref();
const chartOptionsLoai = ref();
const chartThuongHieu = ref();
const chartOptionsThuongHieu = ref();

const chartData = ref();
const chartOptions = ref();

const chartDataSPCao = ref();
const chartOptionsSPCao = ref();

const chartDataSPThap = ref();
const chartOptionsSPThap = ref();

const setChartData = () => {
    const documentStyle = getComputedStyle(document.documentElement);
    const tongTienThang = {};
    const tongTienNam = {};
    const namHienTai = [];
    const namTruoc = [];

    if (lstAdminThongKeThangNamResponses.value !== null) {
        for (const o of lstAdminThongKeThangNamResponses.value) {
            tongTienNam[o.thang] = o.tongTien;
            namTruoc.push(o.nam);
        }
    }
    for (const o of lstAdminThongKeThangResponses.value) {
        tongTienThang[o.thang] = o.tongTien;
        namHienTai.push(o.nam);
    }

    const thang = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12];
    const dataTongTienThang = thang.map((t) => tongTienThang[t] || 0);
    const dataTongTienNam = thang.map((t) => tongTienNam[t] || 0);

    const viTri = [];
    for (let i = 0; i < dataTongTienThang.length; i++) {
        viTri.push(i);
    }
    return {
        labels: thang,
        datasets: [
            {
                label: namTruoc[0] ? namTruoc[0] : '',
                data: dataTongTienNam,
                fill: false,
                borderColor: documentStyle.getPropertyValue('--blue-500'),
                tension: 0.4
            },
            {
                label: namHienTai[0] ? namHienTai[0] : '',
                data: dataTongTienThang,
                fill: false,
                borderColor: documentStyle.getPropertyValue('--pink-500'),
                tension: 0.4
            }
        ]
    };
};

const setChartOptions = () => {
    const documentStyle = getComputedStyle(document.documentElement);
    const textColor = documentStyle.getPropertyValue('--text-color');
    const textColorSecondary = documentStyle.getPropertyValue('--text-color-secondary');
    const surfaceBorder = documentStyle.getPropertyValue('--surface-border');

    return {
        maintainAspectRatio: false,
        aspectRatio: 0.6,
        plugins: {
            legend: {
                labels: {
                    color: textColor
                }
            }
        },
        scales: {
            x: {
                ticks: {
                    color: textColorSecondary
                },
                grid: {
                    color: surfaceBorder
                }
            },
            y: {
                ticks: {
                    color: textColorSecondary
                },
                grid: {
                    color: surfaceBorder
                }
            }
        }
    };
};

const setChartLoai = () => {
    const documentStyle = getComputedStyle(document.documentElement);
    const ten = [];
    const tongTien = [];

    for (const o of lstAdminThongKeLoaiResponses.value) {
        ten.push(o.ten);
        tongTien.push(o.tongTien);
    }
    return {
        datasets: [
            {
                data: tongTien,
                backgroundColor: [documentStyle.getPropertyValue('--red-500'), documentStyle.getPropertyValue('--green-500'), documentStyle.getPropertyValue('--yellow-500'), documentStyle.getPropertyValue('--bluegray-500')],
                label: 'My dataset'
            }
        ],
        labels: ten
    };
};
const setChartOptionsLoai = () => {
    const documentStyle = getComputedStyle(document.documentElement);
    const textColor = documentStyle.getPropertyValue('--text-color');
    const surfaceBorder = documentStyle.getPropertyValue('--surface-border');

    return {
        plugins: {
            legend: {
                labels: {
                    color: textColor
                }
            }
        },
        scales: {
            r: {
                grid: {
                    color: surfaceBorder
                }
            }
        }
    };
};
const setChartThuongHieu = () => {
    const documentStyle = getComputedStyle(document.documentElement);
    const ten = [];
    const tongTien = [];
    for (const o of lstAdminThongKeThuongHieuResponses.value) {
        ten.push(o.ten);
        tongTien.push(o.tongTien);
    }
    return {
        datasets: [
            {
                data: tongTien,
                backgroundColor: [documentStyle.getPropertyValue('--red-500'), documentStyle.getPropertyValue('--green-500'), documentStyle.getPropertyValue('--yellow-500'), documentStyle.getPropertyValue('--bluegray-500')],
                label: 'My dataset'
            }
        ],
        labels: ten
    };
};
const setChartOptionsThuongHieu = () => {
    const documentStyle = getComputedStyle(document.documentElement);
    const textColor = documentStyle.getPropertyValue('--text-color');
    const surfaceBorder = documentStyle.getPropertyValue('--surface-border');

    return {
        plugins: {
            legend: {
                labels: {
                    color: textColor
                }
            }
        },
        scales: {
            r: {
                grid: {
                    color: surfaceBorder
                }
            }
        }
    };
};

const setChartDataSpCao = () => {
    const documentStyle = getComputedStyle(document.documentElement);
    const ten = [];
    const tongTien = [];
    for (const o of lstAdminThongKeSanPhamCaoResponses.value) {
        ten.push(o.ma);
        tongTien.push(o.tongTien);
    }
    return {
        labels: ten,
        datasets: [
            {
                label: 'doanh thu',
                backgroundColor: documentStyle.getPropertyValue('--blue-500'),
                borderColor: documentStyle.getPropertyValue('--blue-500'),
                data: tongTien
            }
        ]
    };
};
const setChartOptionsSpCao = () => {
    const documentStyle = getComputedStyle(document.documentElement);
    const textColor = documentStyle.getPropertyValue('--text-color');
    const textColorSecondary = documentStyle.getPropertyValue('--text-color-secondary');
    const surfaceBorder = documentStyle.getPropertyValue('--surface-border');

    return {
        indexAxis: 'y',
        maintainAspectRatio: false,
        aspectRatio: 0.8,
        plugins: {
            legend: {
                labels: {
                    color: textColor
                }
            }
        },
        scales: {
            x: {
                ticks: {
                    color: textColorSecondary,
                    font: {
                        weight: 500
                    }
                },
                grid: {
                    display: false,
                    drawBorder: false
                }
            },
            y: {
                ticks: {
                    color: textColorSecondary
                },
                grid: {
                    color: surfaceBorder,
                    drawBorder: false
                }
            }
        }
    };
};

const setChartDataSpThap = () => {
    const documentStyle = getComputedStyle(document.documentElement);
    const ten = [];
    const tongTien = [];
    for (const o of lstAdminThongKeSanPhamThapResponses.value) {
        ten.push(o.ma);
        tongTien.push(o.tongTien);
    }
    return {
        labels: ten,
        datasets: [
            {
                label: 'doanh thu',
                backgroundColor: documentStyle.getPropertyValue('--blue-500'),
                borderColor: documentStyle.getPropertyValue('--blue-500'),
                data: tongTien
            }
        ]
    };
};
const setChartOptionsSpThap = () => {
    const documentStyle = getComputedStyle(document.documentElement);
    const textColor = documentStyle.getPropertyValue('--text-color');
    const textColorSecondary = documentStyle.getPropertyValue('--text-color-secondary');
    const surfaceBorder = documentStyle.getPropertyValue('--surface-border');

    return {
        indexAxis: 'y',
        maintainAspectRatio: false,
        aspectRatio: 0.8,
        plugins: {
            legend: {
                labels: {
                    color: textColor
                }
            }
        },
        scales: {
            x: {
                ticks: {
                    color: textColorSecondary,
                    font: {
                        weight: 500
                    }
                },
                grid: {
                    display: false,
                    drawBorder: false
                }
            },
            y: {
                ticks: {
                    color: textColorSecondary
                },
                grid: {
                    color: surfaceBorder,
                    drawBorder: false
                }
            }
        }
    };
};


</script>
