<script setup>
import { TrangChuStore } from '@/service/KhachHang/TrangChuService';
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { SPDaXemStore } from '@/service/KhachHang/SanPhamDaXem.js';
import ChatButton from './Chat/ChatButton.vue';
const spDaXemService = SPDaXemStore();
const router = useRouter();
const useTrangChuService = TrangChuStore();
const dataHangMoi = ref([]);
const dataFullrace = ref([]);
const dataTreEm = ref([]);
const dataSPBanChay = ref([]);

const goToProductDetail = (productId) => {
    themSPDaXem(productId);
    router.push({ name: 'chi-tiet-san-pham', params: { id: productId } });
};

//load data full
const loadData = async () => {
    await useTrangChuService.fetchDataByTenLoai(2);
    dataFullrace.value = useTrangChuService.dataFullface;
};

//load data hang moi
const loadDataHangMoi = async () => {
    await useTrangChuService.fetchDataHangMoi();
    dataHangMoi.value = useTrangChuService.dataHangMoi;
};

//load data sp bán chạy
const loadDataSPBanChay = async () => {
    await useTrangChuService.fetchDataBanChay();
    dataSPBanChay.value = useTrangChuService.dataSPBanChay;
};

//load data hang moi
const loadDataTreEm = async () => {
    await useTrangChuService.fetchDataByTenLoai(5);
    dataTreEm.value = useTrangChuService.dataTreEm;
};

const formatCurrency = (value) => {
    return parseInt(value).toLocaleString('vi-VN', { style: 'currency', currency: 'VND' });
};

const hienGiaGoc = (value) => {
    if (value != null) {
        return false;
    } else {
        return true;
    }
};
const hienGiaGiam = (value) => {
    if (value != null) {
        return true;
    } else {
        return false;
    }
};

onMounted(() => {
    loadData();
    loadDataSPBanChay();
    loadDataHangMoi();
    loadDataTreEm();
});

const banner1 = '/src/assets/images/banner.jpg';
const thumbnails = [
    {
        imageUrl: 'https://nontrum.vn/wp-content/uploads/2019/10/non-balder-vang-1-e1583121638578.jpg',
        name: 'NÓN 3/4'
    },
    {
        imageUrl: 'https://nontrum.vn/wp-content/uploads/2020/11/rona-luxury-den-bong.jpg',
        name: 'NÓN NỬA ĐẦU'
    },
    {
        imageUrl: 'https://nontrum.vn/wp-content/uploads/2021/11/XH02-den-4-1-e1637381735829-1.jpg',
        name: 'NÓN FULLFACE',
        price: '320,000 ₫'
    },
    {
        imageUrl: 'https://nontrum.vn/wp-content/uploads/2020/12/mu-xe-dap-Balder-superkid-khung-long-do-2.jpg',
        name: 'NÓN TRẺ EM',
        price: '320,000 ₫'
    }
    // Thêm các đối tượng khác nếu cần
];

const thumbnailsSP = [
    {
        imageUrl: 'https://nontrum.vn/wp-content/uploads/2019/10/non-balder-vang-1-e1583121638578.jpg',
        name: 'ROC R43 Frozen Xanh',
        price: '320,000 ₫'
    },
    {
        imageUrl: 'https://nontrum.vn/wp-content/uploads/2019/10/non-balder-vang-1-e1583121638578.jpg',
        name: 'ROC R43 Frozen Xanh',
        price: '320,000 ₫'
    },
    {
        imageUrl: 'https://nontrum.vn/wp-content/uploads/2019/10/non-balder-vang-1-e1583121638578.jpg',
        name: 'ROC R43 Frozen Xanh',
        price: '320,000 ₫'
    },
    {
        imageUrl: 'https://nontrum.vn/wp-content/uploads/2019/10/non-balder-vang-1-e1583121638578.jpg',
        name: 'ROC R43 Frozen Xanh',
        price: '320,000 ₫'
    },
    {
        imageUrl: 'https://nontrum.vn/wp-content/uploads/2019/10/non-balder-vang-1-e1583121638578.jpg',
        name: 'ROC R43 Frozen Xanh',
        price: '320,000 ₫'
    },
    {
        imageUrl: 'https://nontrum.vn/wp-content/uploads/2019/10/non-balder-vang-1-e1583121638578.jpg',
        name: 'ROC R43 Frozen Xanh',
        price: '320,000 ₫'
    },
    {
        imageUrl: 'https://nontrum.vn/wp-content/uploads/2019/10/non-balder-vang-1-e1583121638578.jpg',
        name: 'ROC R43 Frozen Xanh abc',
        price: '320,000 ₫'
    },

    {
        imageUrl: 'https://nontrum.vn/wp-content/uploads/2019/10/non-balder-vang-1-e1583121638578.jpg',
        name: 'ROC R43 Frozen Xanh',
        price: '320,000 ₫'
    },
    {
        imageUrl: 'https://nontrum.vn/wp-content/uploads/2019/10/non-balder-vang-1-e1583121638578.jpg',
        name: 'ROC R43 Frozen Xanh',
        price: '320,000 ₫'
    }
    // Thêm các đối tượng khác nếu cần
];
//sp hot
const banner2 =
    'https://scontent.xx.fbcdn.net/v/t1.15752-9/384494576_212944041799990_6536877871085600694_n.jpg?stp=dst-jpg_s1080x2048&_nc_cat=100&ccb=1-7&_nc_sid=510075&_nc_ohc=CBKQdqsgXOwAX8fYuuX&_nc_ad=z-m&_nc_cid=0&_nc_ht=scontent.xx&oh=03_AdREjH2b3LqpYhiC0dFqK-EDrJwPhnwR0pxyUQxipcou9w&oe=6572841E';
const banner3 =
    'https://scontent.xx.fbcdn.net/v/t1.15752-9/384495534_246446494627006_6844178457291330656_n.png?_nc_cat=107&ccb=1-7&_nc_sid=510075&_nc_ohc=cO2KzVhSOtgAX9CX4lV&_nc_ad=z-m&_nc_cid=0&_nc_ht=scontent.xx&oh=03_AdT-I5MJyt7UQ5wMxpWJf93pktpDOFy0SmdrK6cxujiHjQ&oe=6572A2D8';

//sp mới
const banner4 =
    'https://scontent.xx.fbcdn.net/v/t1.15752-9/384494576_212944041799990_6536877871085600694_n.jpg?stp=dst-jpg_s1080x2048&_nc_cat=100&ccb=1-7&_nc_sid=510075&_nc_ohc=CBKQdqsgXOwAX8fYuuX&_nc_ad=z-m&_nc_cid=0&_nc_ht=scontent.xx&oh=03_AdREjH2b3LqpYhiC0dFqK-EDrJwPhnwR0pxyUQxipcou9w&oe=6572841E';
const banner5 =
    'https://scontent.xx.fbcdn.net/v/t1.15752-9/384495534_246446494627006_6844178457291330656_n.png?_nc_cat=107&ccb=1-7&_nc_sid=510075&_nc_ohc=cO2KzVhSOtgAX9CX4lV&_nc_ad=z-m&_nc_cid=0&_nc_ht=scontent.xx&oh=03_AdT-I5MJyt7UQ5wMxpWJf93pktpDOFy0SmdrK6cxujiHjQ&oe=6572A2D8';

// nón full
const banner6 =
    'https://scontent.xx.fbcdn.net/v/t1.15752-9/384494576_212944041799990_6536877871085600694_n.jpg?stp=dst-jpg_s1080x2048&_nc_cat=100&ccb=1-7&_nc_sid=510075&_nc_ohc=CBKQdqsgXOwAX8fYuuX&_nc_ad=z-m&_nc_cid=0&_nc_ht=scontent.xx&oh=03_AdREjH2b3LqpYhiC0dFqK-EDrJwPhnwR0pxyUQxipcou9w&oe=6572841E';
const banner7 =
    'https://scontent.xx.fbcdn.net/v/t1.15752-9/384495534_246446494627006_6844178457291330656_n.png?_nc_cat=107&ccb=1-7&_nc_sid=510075&_nc_ohc=cO2KzVhSOtgAX9CX4lV&_nc_ad=z-m&_nc_cid=0&_nc_ht=scontent.xx&oh=03_AdT-I5MJyt7UQ5wMxpWJf93pktpDOFy0SmdrK6cxujiHjQ&oe=6572A2D8';
// nón trẻ em
const banner8 =
    'https://scontent.xx.fbcdn.net/v/t1.15752-9/384494576_212944041799990_6536877871085600694_n.jpg?stp=dst-jpg_s1080x2048&_nc_cat=100&ccb=1-7&_nc_sid=510075&_nc_ohc=CBKQdqsgXOwAX8fYuuX&_nc_ad=z-m&_nc_cid=0&_nc_ht=scontent.xx&oh=03_AdREjH2b3LqpYhiC0dFqK-EDrJwPhnwR0pxyUQxipcou9w&oe=6572841E';
const banner9 =
    'https://scontent.xx.fbcdn.net/v/t1.15752-9/384495534_246446494627006_6844178457291330656_n.png?_nc_cat=107&ccb=1-7&_nc_sid=510075&_nc_ohc=cO2KzVhSOtgAX9CX4lV&_nc_ad=z-m&_nc_cid=0&_nc_ht=scontent.xx&oh=03_AdT-I5MJyt7UQ5wMxpWJf93pktpDOFy0SmdrK6cxujiHjQ&oe=6572A2D8';

if (!localStorage.getItem('spDaXem')) {
    let array = [];
    localStorage.setItem('spDaXem', JSON.stringify(array));
}

const themSPDaXem = async (idSP) => {
    const token = localStorage.getItem('token');
    const respone = await spDaXemService.themSPDaXem(idSP);
    let array = JSON.parse(localStorage.getItem('spDaXem')); // Phân tích chuỗi JSON thành mảng
    if (token == null) {
        const form = {
            idUser: -1,
            anh: respone.anh,
            demLot: respone.demLot,
            giaBanMax: respone.giaBanMax,
            giaBanMin: respone.giaBanMin,
            giaSauGiamMax: respone.giaSauGiamMax,
            giaSauGiamMin: respone.giaSauGiamMin,
            idSP: respone.idSP,
            ma: respone.ma,
            moTa: respone.moTa,
            ngayTao: respone.ngayTao,
            quaiDeo: respone.quaiDeo,
            tenLoai: respone.tenLoai,
            tenSP: respone.tenSP,
            tenThuongHieu: respone.tenThuongHieu,
            trangThai: respone.trangThai
        };
        if (array.length <= 0) {
            array.unshift(form);
            localStorage.setItem('spDaXem', JSON.stringify(array));
        } else {
            // let check = ref(0);
            // for (let i = 0; i < array.length; i++) {
            //     if (array[i].idSP == idSP) {
            //         check.value = 1;
            //     }
            // }
            // if (check.value == 0) {
            //     array.unshift(form);
            //     localStorage.setItem('spDaXem', JSON.stringify(array));
            // }
            array.unshift(form);
            localStorage.setItem('spDaXem', JSON.stringify(array));
        }
    } else {
        const responeKH = await spDaXemService.findByToken(token);
        const form = {
            idUser: responeKH.id,
            anh: respone.anh,
            demLot: respone.demLot,
            giaBanMax: respone.giaBanMax,
            giaBanMin: respone.giaBanMin,
            giaSauGiamMax: respone.giaSauGiamMax,
            giaSauGiamMin: respone.giaSauGiamMin,
            idSP: respone.idSP,
            ma: respone.ma,
            moTa: respone.moTa,
            ngayTao: respone.ngayTao,
            quaiDeo: respone.quaiDeo,
            tenLoai: respone.tenLoai,
            tenSP: respone.tenSP,
            tenThuongHieu: respone.tenThuongHieu,
            trangThai: respone.trangThai
        };
        if (array.length <= 0) {
            array.unshift(form);
            localStorage.setItem('spDaXem', JSON.stringify(array));
        } else {
            let check = ref(0);
            for (let i = 0; i < array.length; i++) {
                if (array[i].idSP == idSP) {
                    check.value = 1;
                }
            }
            if (check.value == 0) {
                array.unshift(form);
                localStorage.setItem('spDaXem', JSON.stringify(array));
            }
        }
    }
};
</script>

<template>
    <div class="grid">
        <div class="image-container">
            <div class="nav-button left-button" @click="previousImage">&lt;</div>
            <img src="/src/assets/images/banner.jpg" class="centered-image" />
            <div class="nav-button right-button" @click="nextImage">&gt;</div>
        </div>
        <div class="main-sp">
            <div class="thumbnail-list">
                <div class="thumbnail" v-for="(thumbnail, index) in thumbnails" :key="index">
                    <a href="http://localhost:5173/#/trang-chu">
                        <img :src="thumbnail.imageUrl" alt="Thumbnail" />
                        <h5>{{ thumbnail.name }}</h5>
                    </a>
                </div>
            </div>
            <!-- SP hot -->
            <div><img src="/src/assets/images/bannerSPBC.jpg" alt="Thumbnail" class="banner2" /></div>
            <div><img src="/src/assets/images/tenSP_BanChay.png" alt="Thumbnail" class="banner3" /></div>
            <div class="flex-container">
                <div class="flex-item" v-for="(spct, index) in dataSPBanChay" :key="index">
                    <div class="product-top">
                        <a href="" class="product-thumb">
                            <img :src="spct.anh" alt="Thumbnail" class="product-image" />
                        </a>
                        <a class="xct" @click="goToProductDetail(spct.idSP)">Xem chi tiết</a>
                    </div>
                    <p class="ten-sp">{{ spct.tenSP }}</p>
                    <br />
                    <p class="gia-sp" style="color: black; text-align: center" v-if="spct.giaBanMin == spct.giaBanMax">{{ formatCurrency(spct.giaBanMax) }}</p>
                    <p class="gia-sp" style="color: black; text-align: center" v-else-if="spct.giaSauGiamMax != null && spct.giaSauGiamMin != null && spct.giaSauGiamMax != spct.giaSauGiamMin">
                        {{ formatCurrency(spct.giaSauGiamMin) }} - {{ formatCurrency(spct.giaSauGiamMax) }}
                    </p>
                    <p class="gia-sp" style="color: black; text-align: center" v-else-if="spct.giaSauGiamMax == null && spct.giaSauGiamMin == null">{{ formatCurrency(spct.giaBanMin) }} - {{ formatCurrency(spct.giaBanMax) }}</p>
                    <p class="gia-sp" style="color: black; text-align: center" v-else-if="spct.giaSauGiamMax == spct.giaSauGiamMin">{{ formatCurrency(spct.giaSauGiamMax) }}</p>
                    <p class="gia-sp" style="color: black; text-align: center" v-else>{{ formatCurrency(spct.giaBanMin) }} - {{ formatCurrency(spct.giaBanMax) }}</p>
                </div>
            </div>
            <!-- SP mới -->
            <div><img src="/src/assets/images/HANG-MOI.jpg" alt="Thumbnail" class="banner2" /></div>
            <div><img src="/src/assets/images/hangMoi.png" alt="Thumbnail" class="banner3" /></div>
            <div class="flex-container">
                <div class="flex-item" v-for="(spct, index) in dataHangMoi" :key="index">
                    <div class="product-top">
                        <a href="" class="product-thumb">
                            <img :src="spct.anh" alt="Thumbnail" class="product-image" />
                        </a>
                        <a class="xct" @click="goToProductDetail(spct.idSP)">Xem chi tiết</a>
                    </div>
                    <p class="ten-sp">{{ spct.tenSP }}</p>
                    <br />
                    <p class="gia-sp" style="color: black; text-align: center" v-if="spct.giaBanMin == spct.giaBanMax">{{ formatCurrency(spct.giaBanMax) }}</p>
                    <p class="gia-sp" style="color: black; text-align: center" v-else-if="spct.giaSauGiamMax != null && spct.giaSauGiamMin != null && spct.giaSauGiamMax != spct.giaSauGiamMin">
                        {{ formatCurrency(spct.giaSauGiamMin) }} - {{ formatCurrency(spct.giaSauGiamMax) }}
                    </p>
                    <p class="gia-sp" style="color: black; text-align: center" v-else-if="spct.giaSauGiamMax == null && spct.giaSauGiamMin == null">{{ formatCurrency(spct.giaBanMin) }} - {{ formatCurrency(spct.giaBanMax) }}</p>
                    <p class="gia-sp" style="color: black; text-align: center" v-else-if="spct.giaSauGiamMax == spct.giaSauGiamMin">{{ formatCurrency(spct.giaSauGiamMax) }}</p>
                    <p class="gia-sp" style="color: black; text-align: center" v-else>{{ formatCurrency(spct.giaBanMin) }} - {{ formatCurrency(spct.giaBanMax) }}</p>
                </div>
            </div>
            <!-- Nón full -->
            <div><img src="/src/assets/images/fullFace.jpg" alt="Thumbnail" class="banner2" /></div>
            <div><img src="/src/assets/images/full.png" alt="Thumbnail" class="banner3" /></div>
            <div class="flex-container">
                <div class="flex-item" v-for="(spct, index) in dataFullrace" :key="index">
                    <div class="product-top">
                        <a href="" class="product-thumb">
                            <img :src="spct.anh" alt="Thumbnail" class="product-image" />
                        </a>
                        <a class="xct" @click="goToProductDetail(spct.idSP)">Xem chi tiết</a>
                    </div>
                    <p class="ten-sp">{{ spct.tenSP }}</p>
                    <br />
                    <p class="gia-sp" style="color: black; text-align: center" v-if="spct.giaBanMin == spct.giaBanMax">{{ formatCurrency(spct.giaBanMax) }}</p>
                    <p class="gia-sp" style="color: black; text-align: center" v-else-if="spct.giaSauGiamMax != null && spct.giaSauGiamMin != null && spct.giaSauGiamMax != spct.giaSauGiamMin">
                        {{ formatCurrency(spct.giaSauGiamMin) }} - {{ formatCurrency(spct.giaSauGiamMax) }}
                    </p>
                    <p class="gia-sp" style="color: black; text-align: center" v-else-if="spct.giaSauGiamMax == null && spct.giaSauGiamMin == null">{{ formatCurrency(spct.giaBanMin) }} - {{ formatCurrency(spct.giaBanMax) }}</p>
                    <p class="gia-sp" style="color: black; text-align: center" v-else-if="spct.giaSauGiamMax == spct.giaSauGiamMin">{{ formatCurrency(spct.giaSauGiamMax) }}</p>
                    <p class="gia-sp" style="color: black; text-align: center" v-else>{{ formatCurrency(spct.giaBanMin) }} - {{ formatCurrency(spct.giaBanMax) }}</p>
                </div>
            </div>

            <!-- Nón trẻ em -->
            <div><img src="/src/assets/images/non-tre-em-scaled.jpg" alt="Thumbnail" class="banner2" /></div>
            <div><img src="/src/assets/images/Banner-non-tre-em.png" alt="Thumbnail" class="banner3" /></div>
            <div class="flex-container">
                <div class="flex-item" v-for="(spct, index) in dataTreEm" :key="index">
                    <div class="product-top">
                        <a href="" class="product-thumb">
                            <img :src="spct.anh" alt="Thumbnail" class="product-image" />
                        </a>
                        <a class="xct" @click="goToProductDetail(spct.idSP)">Xem chi tiết</a>
                    </div>
                    <p class="ten-sp">{{ spct.tenSP }}</p>
                    <br />

                    <p class="gia-sp" style="color: black; text-align: center" v-if="spct.giaBanMin == spct.giaBanMax">{{ formatCurrency(spct.giaBanMax) }}</p>
                    <p class="gia-sp" style="color: black; text-align: center" v-else-if="spct.giaSauGiamMax != null && spct.giaSauGiamMin != null && spct.giaSauGiamMax != spct.giaSauGiamMin">
                        {{ formatCurrency(spct.giaSauGiamMin) }} - {{ formatCurrency(spct.giaSauGiamMax) }}
                    </p>
                    <p class="gia-sp" style="color: black; text-align: center" v-else-if="spct.giaSauGiamMax == null && spct.giaSauGiamMin == null">{{ formatCurrency(spct.giaBanMin) }} - {{ formatCurrency(spct.giaBanMax) }}</p>
                    <p class="gia-sp" style="color: black; text-align: center" v-else-if="spct.giaSauGiamMax == spct.giaSauGiamMin">{{ formatCurrency(spct.giaSauGiamMax) }}</p>
                    <p class="gia-sp" style="color: black; text-align: center" v-else>{{ formatCurrency(spct.giaBanMin) }} - {{ formatCurrency(spct.giaBanMax) }}</p>
                </div>
            </div>
            <div class="thong-tin">
                <p class="tieu-de-to">TẠI SAO NÊN CHỌN NÓN TRÙM?</p>
                <div class="flex col-12">
                    <div class="colum-gioi-thieu cot1">
                        <p class="tieu-de">SẢN PHẨM CHÍNH HÃNG</p>
                        <p class="content">100% sản phẩm bán tại Nón Trùm chính hãng và đạt chuẩn. Sản phẩm đa dạng và đầy đủ để khách hàng lựa chọn.</p>
                    </div>
                    <div class="colum-gioi-thieu cot2">
                        <p class="tieu-de">GIẶT NÓN MIỄN PHÍ</p>
                        <p class="content">Tặng 6 tháng giặt nón miễn phí trị giá đến 420,000đ bằng máy chuyên dụng do Nón Trùm nghiên cứu và phát triển đầu tiên và duy nhất Việt Nam.</p>
                    </div>
                    <div class="colum-gioi-thieu cot3">
                        <p class="tieu-de">GIAO HÀNG MIỄN PHÍ</p>
                        <p class="content">Với đơn hàng từ 450,000đ trở lên, Nón Trùm giao hàng tận nơi toàn quốc miễn phí. Ngoài ra, Nón Trùm có giao nhanh trong 2-3 giờ nội thành phố Hà Nội.</p>
                    </div>
                </div>
                <div class="flex col-12">
                    <div class="colum-gioi-thieu cot1">
                        <p class="tieu-de">MUA NHIỀU GIẢM SÂU</p>
                        <p class="content">Với đơn hàng từ 450,000đ trở lên, Nón Trùm giao hàng tận nơi toàn quốc miễn phí. Ngoài ra, Nón Trùm có giao nhanh trong 2-3 giờ nội thành phố Hà Nội.</p>
                    </div>
                    <div class="colum-gioi-thieu cot2">
                        <p class="tieu-de">BẢO HÀNH 365 NGÀY</p>
                        <p class="content">Nón được bảo hành 365 ngày chính hãng nên bạn có thể an tâm mua hàng.</p>
                    </div>
                    <div class="colum-gioi-thieu cot3">
                        <p class="tieu-de">ĐỔI MỚI 7 NGÀY</p>
                        <p class="content">Đổi ngay nón mới trong vòng 7 ngày nếu có lỗi kỹ thuật. Ngoài ra, Nón Trùm hỗ trợ đổi size nón nếu bạn đội không vừa.</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<style scoped>
.grid {
    margin-top: 70px;
    display: flex;
    justify-content: center;
    align-items: center;
    width: 100vw;
    background: rgb(253, 253, 254);
}

.image-container {
    position: relative;
    width: 100%;
    height: 100%;
}

.centered-image {
    width: 100%;
    object-fit: contain;
}

.nav-button {
    position: absolute;
    top: 50%;
    font-size: 24px;
    color: white;
    cursor: pointer;
    user-select: none;
    background-color: rgba(0, 0, 0, 0.5);
    padding: 10px 20px;
    border: none;
    outline: none;
    transition: background-color 0.3s, transform 0.3s;
}

.nav-button:hover {
    background-color: rgba(0, 0, 0, 0.7);
}

.nav-button.left-button {
    left: 10px;
    border-top-right-radius: 25px;
    border-bottom-right-radius: 25px;
}

.nav-button.right-button {
    right: 0px;
    border-top-left-radius: 25px;
    border-bottom-left-radius: 25px;
}

.thumbnail-list {
    display: flex;
    justify-content: flex-start;
    flex-wrap: wrap;
}

.main-sp {
    /* background: black; */
    display: flex;
    justify-content: center;
    align-items: center;
    flex-direction: column;
    /* Để căn giữa theo chiều dọc */
    width: 1200px;
}

.thumbnail {
    margin-top: 50px;
    /* margin-left: 50px;
    margin-right: 50px; */
    text-align: center;
    width: 300px;
}

.thumbnail img {
    width: 200px;
    height: 200px;
    object-fit: cover;
}

.banner2 {
    width: 100%;
    max-width: 1100px;
}

.banner3 {
    width: 100%;
    max-width: 1100px;
}

.thumbnail-list-sp {
    margin-bottom: 20px;
    display: flex;
    justify-content: flex-start;
    flex-wrap: wrap;
}

.thumbnail-sp {
    margin-top: 50px;
    margin-left: 12px;
    margin-right: 12px;
    text-align: center;
    width: 200px;
}

.thumbnail-sp img {
    width: 200px;
    height: 200px;
    object-fit: cover;
    border: 1px solid #ccc;
}

.sp {
    margin-top: 40px;
    display: flex;
    justify-content: center;
    align-items: center;
    flex-wrap: wrap;
    padding: 0 40px 0 40px;
}

.ten-sp {
    text-align: left;
    font-size: 17px;
    margin-left: 10px;
    margin-top: 10px;
    margin-bottom: -5px;
    color: black;
    height: 35px;
}

.gia-sp {
    text-align: left;
    font-size: 13px;
    margin-left: 10px;
    font-weight: bold;
    color: red;
}

.thong-tin {
    margin-top: 20px;
    padding-bottom: 60px;
    color: white;
    text-align: center;
    width: 100%;
    max-width: 1100px;
    background: rgb(74, 83, 74);
}

.tieu-de {
    font-weight: bold;
    font-size: 18px;
}

.content {
    font-size: 16px;
}

.colum-gioi-thieu {
    width: 33.3%;
    padding: 0 10px 0 10px;
}

.tieu-de-to {
    margin-top: 20px;
    font-size: 22px;
    font-weight: bold;
}

.cot1 {
    /* margin-right: 50px; */
    border-right: 1px solid white;
}

.cot2 {
    /* margin-right: 50; */
    border-right: 1px solid white;
}

.cot3 {
}

.flex-container {
    display: flex;
    flex-wrap: wrap;
    justify-content: flex-start;
    width: 1100px;
}

.flex-item {
    flex: 0 0 calc(20% - 10px);
    /* 20% độ rộng cho mỗi cột và 10px là khoảng cách giữa các cột */
    border: 1px solid #ccc;
    padding: 10px;
    box-sizing: border-box;
    /* Đảm bảo rằng padding và border không làm tăng kích thước của các cột */
    margin-bottom: 20px;
    /* Khoảng cách giữa các dòng */
    margin-right: 12px;
}

/* Đảm bảo chỉ 5 cột trên mỗi dòng */
.flex-item:nth-child(5n) {
    margin-right: 0;
}

.flex-item img {
    max-width: 100%;
    /* Đảm bảo ảnh không vượt quá kích thước của .flex-item */
    height: auto;
    /* Đảm bảo tỷ lệ hình ảnh được giữ nguyên khi giảm kích thước theo chiều rộng */
    display: block;
    /* Loại bỏ khoảng trắng dư thừa dưới ảnh */
}

.xct {
    text-transform: uppercase;
    text-decoration: none;
    text-align: center;
    display: block;
    background-color: #446084;
    color: #fff;
    padding: 10px 0px;
    position: absolute;
    width: 100%;
    bottom: -45px;
}

.product-top {
    position: relative;
    overflow: hidden;
}

.product-top .product-thumb {
    display: block;
}

.product-top .product-thumb .product-image {
    display: block;
}

.flex-item:hover .xct {
    bottom: 0px;
}
</style>
