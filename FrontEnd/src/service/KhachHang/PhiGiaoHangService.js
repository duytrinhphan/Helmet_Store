import { defineStore } from 'pinia';
import axios from 'axios';

const api = `${import.meta.env.VITE_BASE_API_ENDPOINT}/khach-hang/dia-chi`;

export const phiShipStore = defineStore('phiShip', {
    state: () => ({
        money : 0
    }),
    actions: {
            //gửi cho giao hàng nhanh
        async phiShip(diaChi) {
            const form = {
                "from_district_id":1768,
                "from_ward_code":"190211",
                "service_type_id":2,
                "to_district_id":diaChi.idQuanHuyen,
                "to_ward_code": diaChi.idphuongXa,
                "height":50,
                "length":20,
                "weight":200,
                "width":20,
                "insurance_value":10000,
                "cod_failed_amount":2000,
                "coupon": null
                };
            
                try {
                    const response = await axios.post('https://online-gateway.ghn.vn/shiip/public-api/v2/shipping-order/fee', form, {
                        headers: {
                            // Thêm headers vào yêu cầu POST ở đây
                            token: '62a3cbdc-4e13-11ee-96dc-de6f804954c9',
                            ShopId: '4523827'
                        }
                    });
                  
                    this.money = response.data.data.total;
                } catch (error) {
                    console.error('Error fetching users:', error);
                }

        },
    }
})