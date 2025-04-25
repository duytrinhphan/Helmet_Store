<script setup>
import { ref } from 'vue';

import AppMenuItem from './AppMenuItem.vue';

const model = ref([
{
        label: 'Bán Hàng tại quầy',
        items: [{ label: 'Bán Hàng', icon: 'pi pi-fw pi-home', to: { name: 'ban-hang' } }]
    },
    {
        label: 'Thống Kê',
        items: [{ label: 'Thống Kê', icon: 'pi pi-fw pi-home', to: { name: 'thong-ke' } }]
    },
    {
        label: 'Sản Phẩm',
        items: [

            {
                label: 'Quản Lý Sản Phẩm',
                icon: '',
                items: [
                    {
                        label: 'Sản Phẩm',
                        icon: 'pi pi-fw pi-circle-off',
                        to: { name: 'quan-ly-san-pham' }
                    }, {
                        label: 'Thương Hiệu',
                        icon: 'pi pi-fw pi-circle-off',
                        to: { name: 'thuong-hieu' }
                    },
                    {
                        label: 'Màu sắc',
                        icon: 'pi pi-fw pi-circle-off',
                        to: { name: 'mau-sac' }
                    },
                    {
                        label: 'Size',
                        icon: 'pi pi-fw pi-circle-off',
                        to: { name: 'size' }
                    },
                    {
                        label: 'Loại',
                        icon: 'pi pi-fw pi-circle-off',
                        to: { name: 'loai' }
                    },
                    {
                        label: 'Vật liệu',
                        icon: 'pi pi-fw pi-circle-off',
                        to: { name: 'vat-lieu' }
                    },
                    {
                        label: 'Trọng lượng',
                        icon: 'pi pi-fw pi-circle-off',
                        to: { name: 'trong-luong' }
                    },
                ]
            },
        ]
    },
    {
        label: 'Vocher - Khuyễn Mại',
        items: [{
            label: 'Quản lý Khuyến Mại',
            icon: 'pi pi-fw pi-circle-off',
            to: { name: 'khuyen-mai' }
        },
        {
            label: 'Quản lý Voucher',
            icon: 'pi pi-fw pi-circle-off',
            to: { name: 'voucher' }
        },]
    },
    {
        label: 'User',
        items: [{
            label: 'Quản lý User',
            icon: 'pi pi-fw pi-circle-off',
            to: { name: 'user' }
        },]
    },
    {
        label: 'Quản lý Hóa Đơn',
        items: [{
            label: 'Hóa Đơn',
            icon: 'pi pi-fw pi-circle-off',
            to: { name: 'hoa-don' }
        },]
    },

    {
        label: 'Chat',
        items: [{
            label: 'Chat',
            icon: 'pi pi-fw pi-circle-off',
            to: { name: 'chat-admin' }
        },]
    },


]);
</script>

<template>
    <ul class="layout-menu">
        <template v-for="(item, i) in model" :key="item">
            <app-menu-item v-if="!item.separator" :item="item" :index="i"></app-menu-item>
            <li v-if="item.separator" class="menu-separator"></li>
        </template>
        <li>
            <a href="https://www.primefaces.org/primeblocks-vue/#/" target="_blank"> </a>
        </li>
    </ul>
</template>

<style lang="scss" scoped></style>
