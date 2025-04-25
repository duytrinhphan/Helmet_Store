<script setup>
import { ref, computed, onMounted, onBeforeUnmount } from 'vue';
import { useLayout } from '@/layout/composables/layout';
import { useRouter } from 'vue-router';
import { userStore } from '@/service/Admin/User/UserService.js';
import tokenService from '@/service/Authentication/TokenService.js';
import { ThongBaoStore } from '@/service/Admin/thongBao/thongBao.api';
import { Stomp } from '@stomp/stompjs';
import SockJS from 'sockjs-client';
import { Client } from '@stomp/stompjs';
import { HDStore } from '@/service/Admin/HoaDon/HoaDonService';
import { useBanHangTaiQuayStore } from '@/service/Admin/BanHangTaiQuay/BanHangTaiQuayService';

const store = useBanHangTaiQuayStore();
const useHD = HDStore();
const thongBaoStore = ThongBaoStore();
const userService = userStore();
const { layoutConfig, onMenuToggle } = useLayout();
const outsideClickListener = ref(null);
const topbarMenuActive = ref(false);
const router = useRouter();

onMounted(() => {
    bindOutsideClickListener();
    getAllTB();
    fetchData();
    getDem();
    //  sockets();
    openSocketConnection();
});

const stompClient = ref(null);

const openSocketConnection = () => {
    stompClient.value = new Client({
        brokerURL: `${import.meta.env.VITE_BASE_WEBSOCKET_ENDPOINT}/ws`,
        onConnect: async () => {
            // findUserByToken();
            // const token = localStorage.getItem('token');
            // const respone = await tokenService.findByToken(token);
            stompClient.value.subscribe('/topic/admin/hoa-don', (message) => {
                getAllTB();
                getDem();
                loadHD();
                loadHDByTrangThai(2);
                loadHDByTrangThai(0);
                loadHDByTrangThai(4);
                loadHDByTrangThai(5);
                loadHDByTrangThai(3);
                loadHDByTrangThaiTraHang(7);
                loadHDByTrangThaiTraHang(8);
                loadHDByTrangThaiTraHang(9);
                loadSPBanHangTaiQuay();
            });
        }
    });

    stompClient.value.activate();
};

const tokenCheck = ref();
const data = ref([]);
const getAllTB = async () => {
    const token = localStorage.getItem('token');
    tokenCheck.value = token;
    await thongBaoStore.fetchData();
    data.value = thongBaoStore.data;
};

const loadSPBanHangTaiQuay = async()=>{
    await  store.loadSP();
}

const loadHD = async () => {
    await useHD.fetchData();
};

const loadHDByTrangThai = async (status) => {
    await useHD.fetchDataByStatus(status);
};

const loadHDByTrangThaiTraHang = async (status) => {
    await useHD.fetchDataHDCTByStatus(status);
};

const dem = ref([]);
const getDem = async () => {
    dem.value = await thongBaoStore.fetchdem();
};

const daXem = async (id) => {
    await thongBaoStore.daXem(id);
    getAllTB();
    getDem();
    router.push({ name: 'hoa-don' });
};

onBeforeUnmount(() => {
    unbindOutsideClickListener();
});

const logoUrl = computed(() => {
    return `layout/images/${layoutConfig.darkTheme.value ? 'logo-white' : 'logo-dark'}.svg`;
});

const onTopBarMenuButton = () => {
    topbarMenuActive.value = !topbarMenuActive.value;
};

const onSettingsClick = () => {
    topbarMenuActive.value = false;
    // router.push('/documentation');
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

const fetchData = async () => {
    try {
        await userService.fetchDataByStatus();
        khachHang.value = userService.data;
    } catch (error) {
        // Xử lý lỗi ở đây nếu cần
    }
};

onMounted(() => {
    fetchData();
});

// dùng để lưu thông tin khách hàng khi được chọn CBB.
// nếu muốn dùng thông tin khách hàng khi đặt hàng thì dùng selectedCustomer.value
const selectedCustomer = ref(null);

// hàm gọi sự thay đổi thông tin của khách hàng khi click vào CBB
const displayKH = async () => {
    selectedCustomer.value = khachHang.value.find((kh) => kh.ten === selectedKH.value.ten);
    const token = await tokenService.gentoken(selectedCustomer.value.username);
    localStorage.setItem('token', token);
};

const op = ref();
const toggle2 = (event) => {
    op.value.toggle(event);
};
const op2 = ref();
const toggle = (event) => {
    op2.value.toggle(event);
};

const dangXuat = () => {
    localStorage.removeItem('token');
    localStorage.removeItem('currentUserInformation');
  router.push({ name: 'logout-admin' });
};

const dangNhap = () => {
  router.push({ name: 'login-admin' });
};
const thongTinCaNhan = () => {
  router.push({ name: 'ho-so-admin' });
};
</script>

<template>
    <div class="layout-topbar">
        <!-- <router-link to="/" class="layout-topbar-logo">
                <img :src="logoUrl" alt="logo" />
                <span>SAKAI</span>
            </router-link> -->
        <router-link :to="{ name: 'trang-chu' }" class="layout-topbar-logo" style="height: 60px; width: 120px">
            <img src="/src/assets/images/logo.png" alt="logo" style="height: 70px" />
        </router-link>

        <button class="p-link layout-menu-button layout-topbar-button" @click="onMenuToggle()">
            <i class="pi pi-bars"></i>
        </button>

        <button class="p-link layout-topbar-menu-button layout-topbar-button" @click="onTopBarMenuButton()">
            <i class="pi pi-ellipsis-v"></i>
        </button>

        <div class="layout-topbar-menu" :class="topbarMenuClasses">
            <div class="flex justify-content-center" style="margin-right: 10px; margin-left: 0px; margin-right: 20px">
                <button class="p-link" @click="toggle" aria-haspopup="true" aria-controls="overlay_tmenu">
                    <i class="pi pi-user" style="font-size: 1.5rem" />
                </button>
                <OverlayPanel ref="op2" style="display: block; width: 150px">
                    <button v-if="tokenCheck != null" class="p-link a" aria-haspopup="true" aria-controls="overlay_tmenu"
                        @click="thongTinCaNhan">
                        <div class="flex align-items-center" style="height: 20px; margin-bottom: 10px; width: 120px">Hồ sơ
                            cá nhân</div>
                    </button>
                    <button v-if="tokenCheck == null" class="p-link a" aria-haspopup="true" aria-controls="overlay_tmenu"
                        @click="dangNhap">
                        <div class="flex align-items-center" style="height: 20px; margin-bottom: 10px; width: 120px">Đăng
                            Nhập</div>
                    </button>

                    <button v-if="tokenCheck != null" class="p-link a" aria-haspopup="true" aria-controls="overlay_tmenu"
                        @click="dangXuat">
                        <div class="flex align-items-center" style="height: 20px; margin-bottom: 10px; width: 120px">Đăng
                            Xuất</div>
                    </button>
                </OverlayPanel>
            </div>

            <div class="flex justify-content-center gap-4" style="">
                <button class="p-link" @click="toggle2" aria-haspopup="true" aria-controls="overlay_tmenu">
                    <i v-badge="dem" class="pi pi-bell p-overlay-badge" style="font-size: 1.5rem" />
                </button>

                <OverlayPanel ref="op" style="height: 300px; overflow: auto">
                    <H6>Thông báo </H6>
                    <div v-if="!data || data.length===0" style="text-align: center; margin-top: 50px; "  > 
                                  
                                  <svg  width="50px" height="50px" viewBox="0 0 16 16" xmlns="http://www.w3.org/2000/svg" fill="#000000" class="bi bi-file-earmark-x">
<path d="M6.854 7.146a.5.5 0 1 0-.708.708L7.293 9l-1.147 1.146a.5.5 0 0 0 .708.708L8 9.707l1.146 1.147a.5.5 0 0 0 .708-.708L8.707 9l1.147-1.146a.5.5 0 0 0-.708-.708L8 8.293 6.854 7.146z"/>
<path d="M14 14V4.5L9.5 0H4a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h8a2 2 0 0 0 2-2zM9.5 3A1.5 1.5 0 0 0 11 4.5h2V14a1 1 0 0 1-1 1H4a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1h5.5v2z"/>
</svg>
                       
                            
<h5  style="text-align: center;">Chưa có Thông báo !</h5>
                          </div>
                    <div v-for="(o, index) in data">
                        <button class="p-link" aria-haspopup="true" aria-controls="overlay_tmenu">
                            <div class="flex align-items-center" style="height: 50px; margin-bottom: 10px; width: 240px"
                                @click="daXem(o.id)">
                                <div style="display: flex">
                                    <div style="margin-right: 10px; width: 180px; margin-bottom: -30px">
                                        <p style="margin-bottom: 30px">{{ o.content }}</p>
                                    </div>
                                    <div style="">
                                        <span style="font-size: 10px; margin-top: 0px">{{ o.trangThai == 0 ? 'đã xem' :
                                            'chưa xem' }}</span>
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

<style lang="scss" scoped>button.p-link:hover {
    background-color: rgb(248, 239, 239);
    /* Thay #f00 bằng màu bạn muốn */
}</style>
