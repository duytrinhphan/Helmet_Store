
import { ref } from "vue";
import axios from 'axios';
import router from '@/router/index.js'
import Toast from 'primevue/toast';

const instance = axios.create({
  // baseURL: "http://localhost:8080/api",
});

instance.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('token');
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

instance.interceptors.response.use(
  function (response) {
    return response;
  },
  async function (error) {
    const prevRequest = error?.config;
    const { status } = error?.response;

      //nếu token hết hạn  => xoá token trong localstorage và chuyển về trang chủ 
    if ((status === 401 || status === 403) && !prevRequest?.sent) {
      prevRequest.sent = true;
    //  localStorage.removeItem('token');
        router.push({ name: 'unauthorized' });

      return instance(prevRequest);
    }
  
    return Promise.reject(error);
  }
);




export default instance;
