import { defineStore } from 'pinia';
//  import axios from 'axios';
import axios from '@/service/Authentication/http.js';
const apiCTSP = `${import.meta.env.VITE_BASE_API_ENDPOINT}/admin/ctsp`;



export const ctspStore = defineStore('ctsp', {
    state: () => ({
        data: [],
    }),
    actions: {
        //load tất cả data
        async fetchData(idsp) {
           
            try {
                const response = await axios.get(apiCTSP+`?idsp=${idsp}`);
                this.data = response.data;            
            } catch (error) {
                console.error('Error fetching users:', error);
            }
        },

        updateCTSP(idctsp, idkm) {
            axios.put(`${import.meta.env.VITE_BASE_API_ENDPOINT}/admin/khuyenMai/applyKM/${idctsp}?idkm=${idkm}`).then((response) => {
                let index = -1;
                for (let i = 0; i < this.data.length; i++) {
                    if (idctsp == this.data[i].id) {
                        index = i;
                    }
                }
                this.data.splice(index, 1);
            });
        }
        
    }
})


