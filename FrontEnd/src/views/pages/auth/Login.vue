<script setup>
import * as Yup from 'yup';
import { useLayout } from '@/layout/composables/layout';
import { ref, computed } from 'vue';
import { useField, useForm } from 'vee-validate';
import AppConfig from '@/layout/AppConfig.vue';
import { decodeCredential } from 'vue3-google-login';
import tokenService from '@/service/Authentication/TokenService.js';
import { dangNhapStore } from '@/service/KhachHang/DangNhapService';
import { useToast } from 'primevue/usetoast';
import { useRouter } from 'vue-router';
import { gioHangStore } from '@/service/KhachHang/Giohang/GiohangCTService.js';
import { verifyJwt } from '@/service/common/JwtUtils';
import { useChatStore } from '@/service/Admin/Chat/ChatService';
import * as chatService from '@/service/chatkitty';

const router = useRouter();
const gioHangService = gioHangStore();
const dnService = dangNhapStore();
const chatStore = useChatStore();
const { layoutConfig } = useLayout();
const checked = ref(false);
const toast = useToast();

const gotoTrangChu = () => {
    router.push({ name: 'trang-chu' });
};

const callback = async (response) => {
    const userData = decodeCredential(response.credential);
    const user = await tokenService.checkGoogle(userData.email, userData.name, userData.picture);

    //add sản phẩm đã xem
    const arraySPDaXem = JSON.parse(localStorage.getItem('spDaXem'));

    if (Array.isArray(arraySPDaXem)) {
        for (let i = 0; i < arraySPDaXem.length; i++) {
            if (arraySPDaXem[i].idUser == -1) {
                let check = 0;
                for (let j = 0; j < arraySPDaXem.length; j++) {
                    if (arraySPDaXem[i].idSP == arraySPDaXem[j].idSP && arraySPDaXem[i].idUser == user.id) {
                        check = 1;
                    }
                }
                if (check == 0) arraySPDaXem[i].idUser = user.id;
            }
        }
    }
    localStorage.setItem('spDaXem', JSON.stringify(arraySPDaXem));

    if (user === null || user === '') {
        const user1 = await tokenService.createAccountGoogle(userData.email, userData.name, userData.picture);
        const token = await tokenService.gentoken(user1.userName);
        await chatStore.createChatkittyUser(user1.email, user1.ten);
        localStorage.setItem('token', token);
        localStorage.setItem(
            'currentUserInformation',
            JSON.stringify({
                id: user1.id,
                username: user1.userName
            })
        );
    } else {
        const token = await tokenService.gentoken(user.userName);
        localStorage.setItem('token', token);

        localStorage.setItem(
            'currentUserInformation',
            JSON.stringify({
                id: user.id,
                username: user.userName
            })
        );
    }
    if (localStorage.getItem('cart')) {
        let array = JSON.parse(localStorage.getItem('cart'));
        await gioHangService.addToCartWhenLogin(array, localStorage.getItem('token'));
        localStorage.removeItem('cart');
    }

    gotoTrangChu();
};

const schema = Yup.object().shape({
    email: Yup.string().required('Email không được để trống').email('Email không đúng định dạng'),
    password: Yup.string().required('Mật khẩu không được để trống').min(5, 'Mật khẩu phải trên 5 ký tự')
});

const { handleSubmit, resetForm } = useForm({
    validationSchema: schema
});

const { value: email, errorMessage: emailError } = useField('email');
const { value: password, errorMessage: passwordError } = useField('password');
const dangNhapa = handleSubmit(async () => {
    const login = {
        usernameOrEmail: email.value,
        password: password.value
    };
    const data = await dnService.dangNhap(login);

    if (data == null || data === undefined || data.length <= 0) {
        toast.add({ severity: 'error', summary: 'Thông báo', detail: 'Sai tài khoản hoặc mật khẩu', life: 3000 });
    } else {
        localStorage.setItem('token', data.accessToken);
        localStorage.setItem(
            'currentUserInformation',
            JSON.stringify({
                id: data.userID,
                username: data.usernameOrEmail
            })
        );

        //add sản phẩm đã xem
        const arraySPDaXem = JSON.parse(localStorage.getItem('spDaXem'));
        if (Array.isArray(arraySPDaXem)) {
            for (let i = 0; i < arraySPDaXem.length; i++) {
                if (arraySPDaXem[i].idUser == -1) {
                    let check = 0;
                    for (let j = 0; j < arraySPDaXem.length; j++) {
                        if (arraySPDaXem[i].idSP == arraySPDaXem[j].idSP && arraySPDaXem[i].idUser == parseInt(data.userID)) {
                            check = 1;
                        }
                    }
                    if (check == 0) arraySPDaXem[i].idUser = parseInt(data.userID);
                }
            }
        }
        localStorage.setItem('spDaXem', JSON.stringify(arraySPDaXem));
        gotoTrangChu();
        // khi dang nhap thanh cong thi add sp gio hang vao db
        if (localStorage.getItem('cart')) {
            let array = JSON.parse(localStorage.getItem('cart'));
            await gioHangService.addToCartWhenLogin(array, data.accessToken);
            localStorage.removeItem('cart');
        }
    }
});

const logoUrl = computed(() => {
    return `layout/images/${layoutConfig.darkTheme.value ? 'logo-white' : 'logo-dark'}.svg`;
});

const dangKy = () => {
    router.push({ name: 'dang-ky' });
};
</script>

<template>
    <div class="surface-ground flex align-items-center justify-content-center min-h-screen min-w-screen overflow-hidden">
        <Toast />
        <div class="flex flex-column align-items-center justify-content-center">
            <img src="/src/assets/images/logo.png" alt="Sakai logo" class="mb-5 w-6rem flex-shrink-0" />
            <div style="border-radius: 56px; padding: 0.3rem; background: linear-gradient(180deg, var(--primary-color) 10%, rgba(33, 150, 243, 0) 30%)">
                <div class="w-full surface-card py-8 px-5 sm:px-8" style="border-radius: 53px; margin-bottom: 20px">
                    <div class="text-center mb-5">
                        <div class="text-900 text-3xl font-medium mb-3">Xin chào!</div>
                        <span class="text-600 font-medium">Đăng nhập để tiếp tục</span>
                    </div>

                    <div>
                        <div class="field">
                            <div style="height: 90px">
                                <label for="email1" class="block text-900 text-xl font-medium mb-2">Email</label>
                                <InputText id="email1" type="text" placeholder="Địa chỉ email" class="w-full md:w-30rem mb-5" style="padding: 1rem" v-model="email" />
                            </div>
                            <small class="p-error">{{ emailError }}</small>
                        </div>
                        <div style="margin-bottom: 10px">
                            <div style="height: 90px">
                                <label for="password1" class="block text-900 font-medium text-xl mb-2">Mật khẩu</label>
                                <Password id="password1" v-model="password" placeholder="Mật khẩu" :toggleMask="true" class="w-full mb-3" inputClass="w-full" inputStyle="padding:1rem"></Password>
                            </div>
                            <small class="p-error">{{ passwordError }}</small>
                        </div>
                        <div class="flex align-items-center justify-content-between mb-5 gap-5">
                            <div class="flex align-items-center">
                                <Checkbox v-model="checked" id="rememberme1" binary class="mr-2"></Checkbox>
                                <label for="rememberme1">Ghi nhớ đăng nhập</label>
                            </div>

                            <a @click="router.push({ name: 'forgot-password' })" class="font-medium no-underline ml-2 text-right cursor-pointer" style="color: var(--primary-color)">Quên mật khẩu?</a>
                        </div>
                        <div class="w-full p-3">
                            <Button label="Đăng ký" class="w-5 p-3 text-xl" severity="secondary" @click="dangKy" style="margin-right: 50px"></Button>
                            <Button label="Đăng nhập" class="w-5 p-3 text-xl" @click="dangNhapa"></Button>
                        </div>

                        <div style="margin-top: 20px; margin-left: 50px">
                            <GoogleLogin :callback="callback" style="opacity: 00; width: 20px"> </GoogleLogin>
                            <button class="btn google" style="width: 290px; margin-left: -20px; margin-top: -30px">
                                <svg
                                    version="1.1"
                                    width="20"
                                    id="Layer_1"
                                    xmlns="http://www.w3.org/2000/svg"
                                    xmlns:xlink="http://www.w3.org/1999/xlink"
                                    x="0px"
                                    y="0px"
                                    viewBox="0 0 512 512"
                                    style="enable-background: new 0 0 512 512"
                                    xml:space="preserve"
                                >
                                    <path
                                        style="fill: #fbbb00"
                                        d="M113.47,309.408L95.648,375.94l-65.139,1.378C11.042,341.211,0,299.9,0,256
      	c0-42.451,10.324-82.483,28.624-117.732h0.014l57.992,10.632l25.404,57.644c-5.317,15.501-8.215,32.141-8.215,49.456
      	C103.821,274.792,107.225,292.797,113.47,309.408z"
                                    ></path>
                                    <path
                                        style="fill: #518ef8"
                                        d="M507.527,208.176C510.467,223.662,512,239.655,512,256c0,18.328-1.927,36.206-5.598,53.451
      	c-12.462,58.683-45.025,109.925-90.134,146.187l-0.014-0.014l-73.044-3.727l-10.338-64.535
      	c29.932-17.554,53.324-45.025,65.646-77.911h-136.89V208.176h138.887L507.527,208.176L507.527,208.176z"
                                    ></path>
                                    <path
                                        style="fill: #28b446"
                                        d="M416.253,455.624l0.014,0.014C372.396,490.901,316.666,512,256,512
      	c-97.491,0-182.252-54.491-225.491-134.681l82.961-67.91c21.619,57.698,77.278,98.771,142.53,98.771
      	c28.047,0,54.323-7.582,76.87-20.818L416.253,455.624z"
                                    ></path>
                                    <path
                                        style="fill: #f14336"
                                        d="M419.404,58.936l-82.933,67.896c-23.335-14.586-50.919-23.012-80.471-23.012
      	c-66.729,0-123.429,42.957-143.965,102.724l-83.397-68.276h-0.014C71.23,56.123,157.06,0,256,0
      	C318.115,0,375.068,22.126,419.404,58.936z"
                                    ></path>
                                </svg>

                                Google
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <AppConfig simple />
</template>

<style scoped>
.pi-eye {
    transform: scale(1.6);
    margin-right: 1rem;
}

.pi-eye-slash {
    transform: scale(1.6);
    margin-right: 1rem;
}
</style>
