import { defineStore } from 'pinia';

export const useCartStore = defineStore({
    id: 'cart',
    state: () => ({
      selectedProducts: [],
      totalAmount: 0,
    }),
    actions: {
      updateSelectedProducts(products) {
        this.selectedProducts = products;
      }
    
    },
  });