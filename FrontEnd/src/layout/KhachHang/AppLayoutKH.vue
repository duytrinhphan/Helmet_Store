<script setup>
import {computed, watch, ref, onBeforeUnmount, onBeforeMount} from 'vue';
import AppTopbar from '@/layout/KhachHang/AppTopbarKH.vue';
import AppFooter from '@/layout/KhachHang/AppFooterKH.vue';
import AppSidebar from '@/layout/AppSidebar.vue';
import AppConfig from '@/layout/AppConfig.vue';
import { useLayout } from '@/layout/composables/layout';
import ChatButton from "@/components/KhachHang/Chat/ChatButton.vue";
import {useChatStore} from "@/service/Admin/Chat/ChatService";
import {verifyJwt} from "@/service/common/JwtUtils";
import * as chatService from "@/service/chatkitty";

const chatStore = useChatStore();
const { layoutConfig, layoutState, isSidebarActive } = useLayout();
const outsideClickListener = ref(null);

watch(isSidebarActive, (newVal) => {
    if (newVal) {
        bindOutsideClickListener();
    } else {
        unbindOutsideClickListener();
    }
});

const containerClass = computed(() => {
    return {
        'layout-theme-light': layoutConfig.darkTheme.value === 'light',
        'layout-theme-dark': layoutConfig.darkTheme.value === 'dark',
        'layout-overlay': layoutConfig.menuMode.value === 'overlay',
        'layout-static': layoutConfig.menuMode.value === 'static',
        'layout-static-inactive': layoutState.staticMenuDesktopInactive.value && layoutConfig.menuMode.value === 'static',
        'layout-overlay-active': layoutState.overlayMenuActive.value,
        'layout-mobile-active': layoutState.staticMenuMobileActive.value,
        'p-input-filled': layoutConfig.inputStyle.value === 'filled',
        'p-ripple-disabled': !layoutConfig.ripple.value
    };
});
const bindOutsideClickListener = () => {
    if (!outsideClickListener.value) {
        outsideClickListener.value = (event) => {
            if (isOutsideClicked(event)) {
                layoutState.overlayMenuActive.value = false;
                layoutState.staticMenuMobileActive.value = false;
                layoutState.menuHoverActive.value = false;
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
    const sidebarEl = document.querySelector('.layout-sidebar');
    const topbarEl = document.querySelector('.layout-menu-button');

    return !(sidebarEl.isSameNode(event.target) || sidebarEl.contains(event.target) || topbarEl.isSameNode(event.target) || topbarEl.contains(event.target));
};

onBeforeMount(async () => {
  const token = localStorage.getItem('token');
  if (token) {
    const payloadData = await verifyJwt(token);
    await chatService.login(payloadData.sub, payloadData.id);
    if (payloadData.role === 'USER') await chatStore.createDirectChannel(payloadData.sub);
  }
});

onBeforeUnmount(async () => {
  await chatStore.tearDown();
});
</script>

<template>
    <div class="layout-wrapper" :class="containerClass">
        <app-topbar></app-topbar>
        <!-- <div class="layout-sidebar">
            <app-sidebar></app-sidebar>
        </div> -->
        <div>
            <div class="layout-main">
                <router-view></router-view>
            </div>

            <div class="footer">
                <app-footer></app-footer>
                <!-- <h1>haha</h1> -->
            </div>

        </div>
        <!-- <app-config></app-config> -->
        <div class="layout-mask"></div>
    </div>
  <ChatButton/>
</template>

<style lang="scss" scoped>
.layout-wrapper {
    display: flex;
    flex-direction: column;
    min-height: 100vh;
}

.layout-main {
    flex: 1;
}

.layout-footer {
    background-color: #f8f9fa;
    text-align: center;
    padding: 10px;
}
</style>

