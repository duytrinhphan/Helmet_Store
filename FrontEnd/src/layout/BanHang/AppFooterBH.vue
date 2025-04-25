<script setup>
import { useLayout } from '@/layout/composables/layout';
import { computed } from 'vue';


const { layoutConfig } = useLayout();


const logoUrl = computed(() => {
    return `layout/images/${layoutConfig.darkTheme.value ? 'logo-white' : 'logo-dark'}.svg`;
});
</script>

<template>
    <!-- Remove the container if you want to extend the Footer to full width. -->
    <!-- Footer -->
    <div class="layout-footer">
        <img :src="logoUrl" alt="Logo" height="20" class="mr-2" />
        by
        <span class="font-medium ml-2">PrimeVue</span>
    </div>
    <!-- Footer -->
    <!-- End of .container -->
</template>
<style lang="scss" scoped>
</style>

