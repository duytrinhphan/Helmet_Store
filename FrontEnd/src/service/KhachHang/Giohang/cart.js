// import { ref } from 'vue';

// export const cartCount = ref(0);



// store.js
import { defineStore } from 'pinia';

export const soLuongGh = defineStore({
  id: 'slGH',
  state: () => ({
    cartItems: 0,
  }),
  getters: {
    getCartItems: state => state.cartItems,
  },
  actions: {
    updateCartItems(count) {
      this.cartItems = count;
      // console.log("testssssss", this.cartItems);
    },
  },
});

