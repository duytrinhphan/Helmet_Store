import {defineStore} from "pinia";
import axios from '@/service/Authentication/http.js';

const API_ENDPOINT = `${import.meta.env.VITE_BASE_API_ENDPOINT}/khach-hang/reset-password`;
export const usePasswordStore = defineStore('passwordStore', () => {
    const handleForgotPassword = async (email) => {
        await axios.get(`${API_ENDPOINT}`, {
            params: {
                email
            }
        });
    }

    const resetPassword = async (email, token, password) => {
        await axios.post(`${API_ENDPOINT}`, null, {
            headers: {
                password
            },
            params: {
                email, token
            }
        });
    }

    return {
        handleForgotPassword,
        resetPassword
    }
})