import {createRouter, createWebHistory} from 'vue-router';
import {authMiddleware} from "@/middleware";

const router = createRouter({
    history: createWebHistory(),
    routes: [
        {
            path: '',
            component: () => import('@/layout/KhachHang/AppLayoutKH.vue'),
            children: [
                {
                    path: '',
                    name: 'trang-chu',
                    component: () => import('@/components/KhachHang/TrangChu.vue')
                },
                {
                    path: 'gioi-thieu',
                    name: 'gioi-thieu',
                    component: () => import('@/components/KhachHang/GioiThieu.vue'),
                },
                {
                    path: 'san-pham-da-xem',
                    name: 'san-pham-da-xem',
                    component: () => import('@/components/KhachHang/SanPhamDaXem.vue'),
                },
                {
                    path: 'tra-cuu/:id',
                    name: 'tra-cuu',
                    component: () => import('@/components/KhachHang/TraCuuDonHang.vue'),
                },
                {
                    path: 'lich-su-san-pham',
                    name: 'lich-su-san-pham',
                    beforeEnter: authMiddleware.requireAuth,
                    component: () => import('@/components/KhachHang/LichSuSP/LichSuSP.vue'),
                },
                {
                    path: 'ho-so',
                    name: 'ho-so',
                    beforeEnter: authMiddleware.requireAuth,
                    component: () => import('@/components/KhachHang/ThongTinKhachHang.vue'),
                },
                {
                    path: 'san-pham',
                    name: 'san-pham',
                    component: () => import('@/components/KhachHang/SanPham.vue'),
                },
                {
                    path: 'san-pham/:id',
                    name: 'chi-tiet-san-pham',
                    component: () => import('@/components/KhachHang/DetailSanPham.vue'),
                    props: true
                },
                {
                    path: 'gio-hang',
                    name: 'gio-hang',
                    component: () => import('@/components/KhachHang/GioHang/GioHang.vue')
                },
                {
                    path: 'gio-hang/thanh-toan',
                    name: 'thanh-toan',
                    component: () => import('@/components/KhachHang/ThanhToan.vue'),
                    beforeEnter: authMiddleware.requireAuth
                },
                {
                    path: 'gio-hang/thanh-toan/thanh-cong',
                    name: 'thanh-cong',
                    component: () => import('@/components/KhachHang/Success.vue'),
                    beforeEnter: authMiddleware.requireAuth
                },
                {
                    path: 'gio-hang/thanh-toan/that-bai',
                    name: 'that-bai',
                    component: () => import('@/components/KhachHang/PaymentFailled.vue'),
                    beforeEnter: authMiddleware.requireAuth
                },
                {
                    path: 'trang-thai-don-hang/:id',
                    name: 'trang-thai-don-hang',
                    props: true,
                    beforeEnter: authMiddleware.requireAuth,
                    component: () => import('@/components/KhachHang/LichSuSP/TrangThaiDonHang.vue'),
                },
                {
                    path: 'dia-chi',
                    name: 'dia-chi',
                    beforeEnter: authMiddleware.requireAuth,
                    component: () => import('@/components/KhachHang/DiaChiKhachHang/Index.vue')
                }
            ]
        },
        {
            path: '/dang-ky',
            name: 'dang-ky',
            component: () => import('@/components/KhachHang/Register.vue'),
        },
        {
            path: '/dang-ky/xac-thuc',
            name: 'xac-thuc',
            component: () => import('@/components/KhachHang/OTP.vue')
        },
        {
            path: '/login',
            name: 'login',
            beforeEnter: authMiddleware.isAuthSkippable,
            component: () => import('@/views/pages/auth/Login.vue')
        },
        {
            path: '/admin/login',
            name: 'login-admin',
            beforeEnter: authMiddleware.isAuthSkippable,
            component: () => import('@/views/pages/auth/loginAdmin.vue')
        },
        {
            path: '/admin',
            component: () => import('@/layout/AppLayout.vue'),
             beforeEnter: authMiddleware.requireAdmin,
            children: [
                {
                    path: '',
                    name: 'admin',
                    component: () => import('@/components/Admin/ViewPlaceholder/AdminViewPlaceholder.vue')
                },
                {
                    path: 'san-pham',
                    component: () => import('@/layout/LayoutSP.vue'),
                    children: [
                        {
                            path: '',
                            name: 'quan-ly-san-pham',
                            component: () => import('@/components/Admin/Product/ChiTietSanPham.vue')
                        },
                        {
                            path: 'mau-sac',
                            name: 'mau-sac',
                            component: () => import('@/components/Admin/MauSac/Index.vue')
                        },
                        {
                            path: 'loai',
                            name: 'loai',
                            component: () => import('@/components/Admin/Loai/Index.vue')
                        },
                        {
                            path: 'trong-luong',
                            name: 'trong-luong',
                            component: () => import('@/components/Admin/TrongLuong/Index.vue')
                        },
                        {
                            path: 'vat-lieu',
                            name: 'vat-lieu',
                            component: () => import('@/components/Admin/VatLieu/Index.vue')
                        },
                        {
                            path: 'size',
                            name: 'size',
                            component: () => import('@/components/Admin/QuanLySize/ViewSize.vue')
                        },
                        {
                            path: 'thuong-hieu',
                            name: 'thuong-hieu',
                            component: () => import('@/components/Admin/QuanLyThuongHieu/ViewThuongHieu.vue')
                        }
                    ]
                },
                {
                    path: 'khuyen-mai',
                    name: 'khuyen-mai',
                    component: () => import('@/components/Admin/KhuyenMai/KhuyenMai.vue')
                },
                {
                    path: 'hoa-don',
                    name: 'hoa-don',
                    component: () => import('@/components/Admin/HoaDon/index.vue')
                },
                {
                    path: 'ho-so',
                    name: 'ho-so-admin',
                    component: () => import('@/components/Admin/ThongTinCaNhan/thongTinCaNhan.vue')
                },
                {
                    path: 'thong-ke',
                    name: 'thong-ke',
                    component: () => import('@/components/Admin/ThongKe/index.vue')
                },
                {
                    path: 'ban-hang',
                    name: 'ban-hang',
                    component: () => import('@/components/Admin/BanHang/BanHangTaiQuay.vue')
                },
                {
                    path: 'user',
                    name: 'user',
                    component: () => import('@/components/Admin/User/index.vue')
                },
                {
                    path: 'voucher',
                    name: 'voucher',
                    component: () => import('@/components/Admin/Voucher/Voucher.vue')
                },
                {
                    path: 'chat',
                    name: 'chat-admin',
                    component: () => import('@/components/Admin/Chat/ChatAdmin.vue')
                }
            ]
        },
        {
            path: '/forgot-password',
            name: 'forgot-password',
            component: () => import('@/views/pages/auth/ForgotPassword/ForgotPassword.vue')
        },
        {
            path: '/forgot-password/mail-sent',
            name: 'mail-sent',
            component: () => import('@/views/pages/auth/ForgotPassword/MailSent.vue')
        },
        {
            path: '/reset-password',
            name: 'reset-password',
            component: () => import('@/views/pages/auth/ForgotPassword/ResetPassword.vue')
        },
        {
            path: '/reset-password/password-changed',
            name: 'password-changed',
            component: () => import('@/views/pages/auth/ForgotPassword/PasswordChanged.vue')
        },
        {
            path: '/logout',
            name: 'logout',
            redirect: {name: 'login'}
        },
        {
            path: '/admin/logout',
            name: 'logout-admin',
            redirect: {name: 'login-admin'}
        },
        {
            path: '/unauthorized',
            name: 'unauthorized',
            component: () => import('@/views/pages/auth/Unauthorized.vue')
        },
        {
            path: '/invalid-access',
            name: 'invalid-access',
            component: () => import('@/views/pages/auth/InvalidAccess.vue')
        },
        {
            path: '/:pathMatch(.*)*',
            name: 'not-found',
            component: () => import('@/views/pages/NotFound.vue')
        }
    ],
    scrollBehavior (to, from, savedPosition) {
        return { top: 0 }
    }
});

export default router;