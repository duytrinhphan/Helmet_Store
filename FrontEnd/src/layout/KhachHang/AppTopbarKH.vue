<script setup>
import { ref, computed, onMounted, onBeforeUnmount, watch } from 'vue';
import { useLayout } from '@/layout/composables/layout';
import { useRouter } from 'vue-router';
import { userStore } from '@/service/Admin/User/UserService.js';
import { gioHangStore } from '@/service/KhachHang/Giohang/GiohangCTService.js';
import tokenService from '@/service/Authentication/TokenService.js';
import userKHService from '@/service/KhachHang/UserService.js';

import { HDKHStore } from '../../service/KhachHang/HoaDonKHService';
// import tokenService from '../../service/Authentication/TokenService.js';
import { KHThongBaoStore } from '../../service/KhachHang/ThongBaoService';
// import { HDStore } from from '../../../service/KhachHang/HoaDonKHService';

import { Client } from '@stomp/stompjs';
import SockJS from 'sockjs-client';

const useHD = HDKHStore();

const thongBaoStore = KHThongBaoStore();
const userService = userStore();
const { layoutConfig, onMenuToggle } = useLayout();
const outsideClickListener = ref(null);
const topbarMenuActive = ref(false);
const router = useRouter();
const selectedCustomer = ref(null);
const gioHangService = gioHangStore();
const maHD = ref(null);
const slGH = ref(localStorage.getItem('soLuongGH') || 0);
const dataHDCT = ref([]);

onMounted(() => {
    bindOutsideClickListener();
    getAllTB();
    getDem();
    soLuongGH();
    openSocketConnection();
});

// const cartItems = store.getCartItems;

const stompClient = ref(null);

const openSocketConnection = () => {
    stompClient.value = new Client({
        brokerURL: `${import.meta.env.VITE_BASE_WEBSOCKET_ENDPOINT}/ws`,
        onConnect: async () => {
            // console.log('Đã kết nối');
            // findUserByToken();
            const token = localStorage.getItem('token');
            if (token == '' || token == null) {
                return;
            } else {
                const respone = await tokenService.findByToken(token);
                stompClient.value.subscribe('/topic/hoa-don/' + respone.id, (message) => {
                    getAllTB();
                    getDem();
                    loadData();
                    loadDataByTrangThai(2);
                    loadDataByTrangThai(5);
                    loadDataByTrangThai(3);
                    loadDataByTrangThai(0);
                    loadDataByTrangThai(2);
                    loadDataTra();

                    loadDataHDCT(maHD.value);

                    // loadDataSpChiTiet();
                    // loadDataHD();

                });
            }
        }
    });

    stompClient.value.activate();
};

const loadDataHD = async () => {
    await useHD.findHdByIdHd();
    //   console.log(dataHD.value)
};

const loadDataSpChiTiet = async () => {
    await useHD.findHdctByIdHd();
};
const loadData = async () => {
    const token = localStorage.getItem('token');
    if (token.length > 0 || token != null) {
        await useHD.fetchData(token);
    }
};

const loadDataByTrangThai = async (status) => {
    const token = localStorage.getItem('token');
    if (token.length > 0 || token != null) {
        await useHD.fetchDataByStatus(token, status, '', '', '');
    }
};

const loadDataTra = async () => {
    const token = localStorage.getItem('token');
    if (token.length > 0 || token != null) {
        await useHD.findHdct(token);
    }
};

const data = ref([]);
const getAllTB = async () => {
    const token = localStorage.getItem('token');
    tokenCheck.value = token;
    if (token == '' || token == null) {
        return;
    } else {
        await thongBaoStore.fetchData(token);
        data.value = thongBaoStore.data;
    }
};

const tokenCheck = ref();

const dem = ref(0);
const getDem = async () => {
    const token = localStorage.getItem('token');
    if (token == '' || token == null) {
        return;
    } else {
        dem.value = await thongBaoStore.fetchdem(token);
        if (dem.value == undefined) {
            dem.value = 0;
            return;
        }
    }
};

const daXem = async (id) => {
    await thongBaoStore.daXem(id);
    getAllTB();
    getDem();
    router.push({ name: 'lich-su-san-pham' });
};

onBeforeUnmount(() => {
    unbindOutsideClickListener();
});

const logoUrl = computed(() => {
    return `https://nontrum.vn/wp-content/uploads/2021/12/NonTrum-logo-2022png.png`;
});

const onTopBarMenuButton = () => {
    topbarMenuActive.value = !topbarMenuActive.value;
};

const selectedUserId = computed(() => {
    return selectedCustomer.value ? selectedCustomer.value.id : null;
});

const onSettingsClick = (event) => {
    topbarMenuActive.value = false;
    if (event.item.label === 'Hồ sơ cá nhân') {
        router.push({ name: 'ho-so' });
    } else if (event.item.label === 'Lịch sử mua hàng') {
        router.push({ name: 'lich-su-san-pham' });
    } else if (event.item.label === 'Địa chỉ') {
        router.push({ name: 'dia-chi' });
    } else {
        // Xử lý trường hợp khác nếu cần
    }
};

const thongTinCaNhan = async () => {
    await router.push({ name: 'ho-so' });
};

const lichSuMuaHang = async () => {
    await router.push({ name: 'lich-su-san-pham' });
};

const diaChi = async () => {
    await router.push({ name: 'dia-chi' });
};

const dangXuat = async () => {
    localStorage.removeItem('token');
    localStorage.removeItem('currentUserInformation');


    const arraySPDaXem = JSON.parse(localStorage.getItem('spDaXem'));
    const arraySauKhiDangXuat = [];
    if (Array.isArray(arraySPDaXem)) {
        for (let i = 0; i < arraySPDaXem.length; i++) {
            if (arraySPDaXem[i].idUser != -1) {
                arraySauKhiDangXuat.push(arraySPDaXem[i]);
            }
        }
        localStorage.setItem('spDaXem', JSON.stringify(arraySauKhiDangXuat));
    }

    await router.push({ name: 'logout' });
};

const dangNhap = async () => {
    await router.push({ name: 'login' });
};

const topbarMenuClasses = computed(() => {
    return {
        'layout-topbar-menu-mobile-active': topbarMenuActive.value
    };
});

const bindOutsideClickListener = () => {
    if (!outsideClickListener.value) {
        outsideClickListener.value = (event) => {
            if (isOutsideClicked(event)) {
                topbarMenuActive.value = false;
            }
        };
        document.addEventListener('click', outsideClickListener.value);
    }
};
const unbindOutsideClickListener = () => {
    if (outsideClickListener.value) {
        document.removeEventListener('click', outsideClickListener);
        outsideClickListener.value = null;
    }
};
const isOutsideClicked = (event) => {
    if (!topbarMenuActive.value) return;

    const sidebarEl = document.querySelector('.layout-topbar-menu');
    const topbarEl = document.querySelector('.layout-topbar-menu-button');

    return !(sidebarEl.isSameNode(event.target) || sidebarEl.contains(event.target) || topbarEl.isSameNode(event.target) || topbarEl.contains(event.target));
};

const selectedKH = ref(null);
const khachHang = ref([]);

// const fetchData = async () => {
//     try {
//         if(token !== null || token !== "undefined"){
//             await userService.getAllUser();
//         khachHang.value = userService.data;
//         }

//         //   console.log(khachHang.value);
//     } catch (error) {
//         // Xử lý lỗi ở đây nếu cần
//     }
// };

const isTokenValid = async (token) => {
    if (token) {
        try {
            // Gọi API `/validate-token` để kiểm tra tính hợp lệ của token
            const response = await tokenService.validatetoken(token);

            if (response.status === 200) {
                // Nếu API trả về mã 200 (OK), token hợp lệ
                return true;
            }
        } catch (error) {
            // Xử lý lỗi nếu có lỗi khi gọi API
            console.error('Error while validating token:', error);
        }
    }
};

// dùng để lưu thông tin khách hàng khi được chọn CBB.
// nếu muốn dùng thông tin khách hàng khi đặt hàng thì dùng selectedCustomer.value

// hàm gọi sự thay đổi thông tin của khách hàng khi click vào CBB

const soLuong = ref(0);

const soLuongGH = async () => {
    const token = localStorage.getItem('token');
    await gioHangService.countGHCT(token);
    soLuong.value = gioHangService.soLuong;
};

const menu = ref();
const items = ref([
    {
        label: 'Hồ sơ cá nhân',
        command: onSettingsClick
    },
    {
        label: 'Địa chỉ',
        command: onSettingsClick
    },
    {
        label: 'Lịch sử mua hàng',
        command: onSettingsClick
    }
]);

const op2 = ref();
const toggle = (event) => {
    op2.value.toggle(event);
};

const op = ref();
const toggle2 = (event) => {
    op.value.toggle(event);
};


const loadDataHDCT = async (idHD) => {
    const respone = await useHD.findHdByIdHd(idHD);
    dataHDCT.value = respone;
};



</script>

<template>
    <div class="layout-topbar">
        <router-link :to="{ name: 'trang-chu' }" class="layout-topbar-logo" style="height: 60px; width: 120px">
            <img src="/src/assets/images/logo.png" alt="logo" style="height: 70px" />
        </router-link>

        <button class="p-link layout-topbar-menu-button layout-topbar-button" @click="onTopBarMenuButton()">
            <i class="pi pi-ellipsis-v"></i>
        </button>

        <Toast />
        <div class="layout-topbar-menu">
            <router-link :to="{ name: 'trang-chu' }" class="layout-topbar-logo" style="width: 100px; margin-left: 10px">
                <p style="font-size: 16px">Home</p>
            </router-link>
            <router-link :to="{ name: 'san-pham' }" class="layout-topbar-logo" style="width: 100%; margin-left: 10px">
                <p style="font-size: 16px">Sản phẩm</p>
            </router-link>
            <router-link :to="{ name: 'gioi-thieu' }" class="layout-topbar-logo" style="width: 140%; margin-left: 10px">
                <p style="font-size: 16px">Về chúng tôi</p>
            </router-link>
            <router-link :to="{ name: 'san-pham-da-xem' }" class="layout-topbar-logo" style="width: 150%; margin-right: 10px">
                <p style="font-size: 16px">Sản phẩm đã xem</p>
            </router-link>

            <router-link :to="{ name: 'gio-hang' }" class="layout-topbar-logo" style="width: 5%; margin-right: 3px">
                <i class="pi pi-shopping-cart p-text-secondary p-overlay-badge" style="font-size: 1.5rem" v-badge="gioHangService.soLuong"></i>
            </router-link>
            <div class="flex justify-content-center" style="margin-right: 10px; margin-left: 20px">
                <button class="p-link" @click="toggle" aria-haspopup="true" aria-controls="overlay_tmenu">
                    <i class="pi pi-user" style="font-size: 1.5rem" />
                </button>
                <OverlayPanel ref="op2" style="display: block; width: 150px">
                    <button v-if="tokenCheck != null" class="p-link a" aria-haspopup="true" aria-controls="overlay_tmenu" @click="thongTinCaNhan">
                        <div class="flex align-items-center" style="height: 20px; margin-bottom: 10px; width: 120px">Hồ sơ cá nhân</div>
                    </button>
                    <button v-if="tokenCheck != null" class="p-link a" aria-haspopup="true" aria-controls="overlay_tmenu" @click="diaChi">
                        <div class="flex align-items-center" style="height: 20px; margin-bottom: 10px; width: 120px">Địa chỉ</div>
                    </button>
                    <button v-if="tokenCheck != null" class="p-link a" aria-haspopup="true" aria-controls="overlay_tmenu" @click="lichSuMuaHang">
                        <div class="flex align-items-center" style="height: 20px; margin-bottom: 10px; width: 120px">Lịch sử mua hàng</div>
                    </button>
                    <button v-if="tokenCheck == null" class="p-link a" aria-haspopup="true" aria-controls="overlay_tmenu" @click="dangNhap">
                        <div class="flex align-items-center" style="height: 20px; margin-bottom: 10px; width: 120px">Đăng Nhập</div>
                    </button>

                    <button v-if="tokenCheck != null" class="p-link a" aria-haspopup="true" aria-controls="overlay_tmenu" @click="dangXuat">
                        <div class="flex align-items-center" style="height: 20px; margin-bottom: 10px; width: 120px">Đăng Xuất</div>
                    </button>
                </OverlayPanel>
            </div>
            <div class="flex justify-content-center gap-4">
                <button class="p-link" @click="toggle2" aria-haspopup="true" aria-controls="overlay_tmenu">
                    <i v-badge="dem" class="pi pi-bell p-overlay-badge" style="font-size: 1.5rem" />
                </button>

                <OverlayPanel ref="op" style="height: 300px; overflow: auto">
                    <H6>Thông báo </H6>

                    <div v-if="!data || data.length === 0" style="text-align: center; margin-top: 50px">
                        <svg width="50px" height="50px" viewBox="0 0 16 16" xmlns="http://www.w3.org/2000/svg" fill="#000000" class="bi bi-file-earmark-x">
                            <path d="M6.854 7.146a.5.5 0 1 0-.708.708L7.293 9l-1.147 1.146a.5.5 0 0 0 .708.708L8 9.707l1.146 1.147a.5.5 0 0 0 .708-.708L8.707 9l1.147-1.146a.5.5 0 0 0-.708-.708L8 8.293 6.854 7.146z" />
                            <path d="M14 14V4.5L9.5 0H4a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h8a2 2 0 0 0 2-2zM9.5 3A1.5 1.5 0 0 0 11 4.5h2V14a1 1 0 0 1-1 1H4a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1h5.5v2z" />
                        </svg>

                        <h5 style="text-align: center">Chưa có Thông báo !</h5>
                    </div>
                    <div v-for="(o, index) in data">
                        <button class="p-link" aria-haspopup="true" aria-controls="overlay_tmenu">
                            <div class="flex align-items-center" style="height: 50px; margin-bottom: 10px; width: 240px" @click="daXem(o.id)">
                                <div style="display: flex">
                                    <div style="margin-right: 10px; width: 180px; margin-bottom: -30px">
                                        <p style="margin-bottom: 30px">{{ o.content }}</p>
                                    </div>
                                    <div style="">
                                        <span style="font-size: 10px; margin-top: 0px">{{ o.trangThai == 0 ? 'đã xem' : 'chưa xem' }}</span>
                                    </div>
                                </div>
                            </div>
                        </button>
                    </div>
                </OverlayPanel>
            </div>
        </div>
    </div>
</template>

<style lang="scss" scoped>
button.p-link:hover {
    background-color: rgb(248, 239, 239);
    /* Thay #f00 bằng màu bạn muốn */
}
</style>
