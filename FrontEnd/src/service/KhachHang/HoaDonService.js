import { defineStore } from 'pinia';
import axios from 'axios';

const api = `${import.meta.env.VITE_BASE_API_ENDPOINT}/khach-hang/checkout`;

export const checkoutStore = defineStore('checkOut', {
    state: () => ({
        checkOut : []
    }),
    actions: {
         async checkout(form){
            const response = await axios.post(api, form)
            this.checkOut =  response.data;
         }
       
    }
})