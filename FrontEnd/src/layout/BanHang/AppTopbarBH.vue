<script setup>
import { ref, computed, onMounted, onBeforeUnmount } from 'vue';
import { useLayout } from '@/layout/composables/layout';
import { useRouter } from 'vue-router';

const { layoutConfig, onMenuToggle } = useLayout();
const outsideClickListener = ref(null);
const topbarMenuActive = ref(false);
const router = useRouter();
onMounted(() => {
    bindOutsideClickListener();
});
onBeforeUnmount(() => {
    unbindOutsideClickListener();
});
const logoUrl = computed(() => {
    return `https://nontrum.vn/wp-content/uploads/2021/12/NonTrum-logo-2022png.png`;
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
</script>

<template>
    <div class="layout-topbar">
        <router-link :to="{ name: 'trang-chu' }" class="layout-topbar-logo" style="height: 60px; width: 120px">
            <img src="/src/assets/images/logo.png" alt="logo" style="height: 70px" />
        </router-link>
        <!-- <span class="p-input-icon-left">
            <i class="pi pi-search" />
            <InputText v-model="value1" placeholder="Search" />
        </span> -->

        <div class="layout-topbar-menu">
            <label for="" style="margin-right: 50px">Nhân viên: <span>Lê Văn B</span></label>
<!--            <span style="font-weight: bold">9:25:00</span>-->
        </div>
    </div>
</template>

<style lang="scss" scoped></style>
