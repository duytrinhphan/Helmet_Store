import { defineStore } from 'pinia';
import axios from 'axios';

const api = `${import.meta.env.VITE_BASE_API_ENDPOINT}/payment-vnpay`;

export const vnpayStore = defineStore('vnpay', {
    state: () => ({
        vnpay : ""
    }),
    actions: {
         async thanhToanVnPay(form){
            const response = await axios.post(api, form)
            this.vnpay =  response.data;
        
         }
       
    }
})