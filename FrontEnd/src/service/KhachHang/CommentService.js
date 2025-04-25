import { defineStore } from 'pinia';
import axios from 'axios';

const apiComment = `${import.meta.env.VITE_BASE_API_ENDPOINT}/khach-hang/comment`;

export const commentStore = defineStore('comment',{
    state: () => ({     
        data: [],
        dataPhanHoi: [],
        user:[]
    }),
    actions: {
        async addComment(form,token) {
            try {
                const response = await axios.post(apiComment+ `/add?token=${token}`,form);
                this.data.unshift(response.data);
            } catch (error) {
                console.error('Error fetching :', error);
            }
        },

        async addPhanHoi(form,token) {
            try {
                const response = await axios.post(apiComment+ `/addPhanHoi?token=${token}`,form);
                this.dataPhanHoi.unshift(response.data);
            } catch (error) {
                console.error('Error fetching :', error);
            }
        },
       
        async getListComment(idsp) {
            try {
                const response = await axios.get(apiComment + `?idsp=${idsp}`);
                this.data = response.data;
            } catch (error) {
                console.error('Error fetching :', error);
            }
        },
        async getListCommentByIdPhanHoi() {
            try {
                const response = await axios.get(apiComment + `/getList`);
                this.dataPhanHoi = response.data;
                
            } catch (error) {
                console.error('Error fetching :', error);
            }
        },
        async xoaComment(id) {
            try {
                const response = await axios.delete(apiComment+'/'+id);              
                this.data = response.data;
            } catch (error) {
                console.error('Error fetching :', error);
            }
        },
        async findByToken(token) {
            try {
                const response = await axios.get(`${import.meta.env.VITE_BASE_API_ENDPOINT}/khach-hang/user/find-by-token?token=` + token);         
                this.user = response.data;
             
            } catch (error) {
            }
          
        }
    }
})