import axios from 'axios';

class TokenService {
    async gentoken(username) {
        const response = await axios.post(`${import.meta.env.VITE_BASE_API_ENDPOINT}/genToken?username=${username}`);
        return response.data;
    }

    async validatetoken(token) {
        const response = await axios.get(`${import.meta.env.VITE_BASE_API_ENDPOINT}/validate-token?token=${token}`);
        return response.data;
    }

    async getUserNameByToken(token) {
        const response = await axios.get(`${import.meta.env.VITE_BASE_API_ENDPOINT}/getUseNameByToken?token=${token}`);
        return response.data;
    }

    async checkGoogle(email, name, anh) {
        const response = await axios.get(`${import.meta.env.VITE_BASE_API_ENDPOINT}/khach-hang/user/find-user-by-email/` + email + '?username=' + name + '&anh=' + anh);
        return response.data;
    }

    async createAccountGoogle(email, name, anh) {
        const response = await axios.post(`${import.meta.env.VITE_BASE_API_ENDPOINT}/khach-hang/user/createAccountGG/` + email + '?username=' + name + '&anh=' + anh);
        return response.data;
    }

    async findByToken(token) {
        const response = await axios.get(`${import.meta.env.VITE_BASE_API_ENDPOINT}/khach-hang/user/find-by-token?token=` + token);
        return response.data;
    }
}
export default new TokenService();
