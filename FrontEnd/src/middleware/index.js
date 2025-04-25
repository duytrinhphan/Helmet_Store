import {verifyJwt} from "@/service/common/JwtUtils";


export const authMiddleware = {
    isAuthSkippable: async (to, from, next) => {
        const token = localStorage.getItem('token');
        if (token) {
            const payloadData = await verifyJwt(token);
            if (payloadData) {
                if (payloadData.role === 'USER') next({name: 'trang-chu'});
                else next({name: 'admin'});
            }
            else next();
        } else next();
    },
    requireAuth: async (to, from, next) => {
        const token = localStorage.getItem('token');
        if (token) {
            const payloadData = await verifyJwt(token);
            if (payloadData) next();
            else next({name: 'login'});
        } else next({name: 'login'});
    },
    requireAdmin: async (to, from, next) => {
        const token = localStorage.getItem('token');
        if (token) {
            const payloadData = await verifyJwt(token);
            if (payloadData) {
                if (payloadData.role === "ADMIN") next();
                else next({name: 'unauthorized'});
            } else next({name: 'login-admin'});
        } else next({name: 'login-admin'});
    }

}