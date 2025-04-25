import { defineStore } from 'pinia';
import axios from 'axios';


const api = `${import.meta.env.VITE_BASE_API_ENDPOINT}/khach-hang/register`;

export const userRegisterStore = defineStore('register',{
    id: 'register',
    state: () => ({
        data: [],
    }),
    actions: {
        async register(form) {
            try {
                const response = await axios.post(api, form);
                this.data = response.data;
            } catch (error) {
                console.error('Error fetching users:', error);
            }
        },
        async getUserByEmail(email) {
            try {
            const response = await axios.get(`${import.meta.env.VITE_BASE_API_ENDPOINT}/khach-hang/user/find-email?email=${email}`);
            this.data  =  response.data;
        } catch (error) {
            console.error('Error fetching users:', error);
        }
        },
        async sendOTP(form) {
            try {
                const response = await axios.post(`${import.meta.env.VITE_BASE_API_ENDPOINT}/khach-hang/mail/sendOTP`, form);
                return response.data;
            } catch (error) {
                console.error('Error sending mail:', error);
                throw error; // You can handle the error in the component that calls this action
            }
        },
    }
})