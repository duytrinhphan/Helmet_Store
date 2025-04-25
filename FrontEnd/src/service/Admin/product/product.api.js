import { defineStore } from 'pinia';
import axios from '@/service/Authentication/http.js';

const apiSanPham = `${import.meta.env.VITE_BASE_API_ENDPOINT}/admin/san-pham`;
export const ProductStore = defineStore('product', {
  state: () => ({
    products: [],
    sanPhamCT: [],
    images: [],
    sizes: [],
    mauSacs: [],
    excels: [],
  }),
  actions: {
    async fetchAll() {
      try {
        const response = await axios.get(apiSanPham); // Thay đổi URL dựa trên API của bạn
        //this.products = response.data;
        const productList =response.data; // Lấy dữ liệu từ Store và gán vào biến products
        this.products = productList;

      } catch (error) {
        console.error('Lỗi khi lấy danh sách sản phẩm:', error);
      }
    },

    async fetchDataByLoai(idloai) {
      try {
        const response = await axios.get(apiSanPham+'/by-loai?idloai=' + idloai); // Thay đổi URL dựa trên API của bạn
        this.products = response.data;
        //  console.table(this.products.size);

      } catch (error) {
        console.error('Lỗi khi lấy danh sách sản phẩm:', error);
      }
    },
    async fetchDataByThuongHieu(idthuonghieu) {
      try {
        const response = await axios.get(apiSanPham+'/by-thuong-hieu?idthuonghieu=' + idthuonghieu); // Thay đổi URL dựa trên API của bạn
        this.products = response.data;
        //  console.table(this.products.size);

      } catch (error) {
        console.error('Lỗi khi lấy danh sách sản phẩm:', error);
      }
    },

 
    async fetchDataByStatus(status) {
      try {
        const response = await axios.get(apiSanPham+'/loc?comboBoxValue=' + status); // Thay đổi URL dựa trên API của bạn
        this.products = response.data;
        //  console.table(this.products.size);

      } catch (error) {
        console.error('Lỗi khi lấy danh sách sản phẩm:', error);
      }
    },


    async checkDuplicateName(data) {
      try {
        // Gửi yêu cầu GET để kiểm tra tên sản phẩm
        const response = await axios.put(apiSanPham+`/check`, data); // Thay đổi URL dựa trên API của bạn
        const isDuplicate = response.data; // API trả về một giá trị boolean cho trùng lặp
        return !isDuplicate; // Trả về true nếu không trùng lặp, ngược lại trả về false

      } catch (error) {
        console.error('Lỗi khi kiểm tra trùng lặp tên sản phẩm:', error);
        throw error; // Nếu có lỗi, ném ngoại lệ để xử lý ở một nơi khác
      }
    },

    async checkDuplicateSPCT(idSize,idMau,idSP) {
      try {
        // Gửi yêu cầu GET để kiểm tra tên sản phẩm
        const response = await axios.put(apiSanPham+`/check-spct/${idSize}?idMau=${idMau}&idSP=${idSP}`); // Thay đổi URL dựa trên API của bạn
        const isDuplicate = response.data; // API trả về một giá trị boolean cho trùng lặp
        return !isDuplicate; // Trả về true nếu không trùng lặp, ngược lại trả về false

      } catch (error) {
        console.error('Lỗi khi kiểm tra trùng lặp tên sản phẩm:', error);
        throw error; // Nếu có lỗi, ném ngoại lệ để xử lý ở một nơi khác
      }
    },
    


    async uploadFile(formData) {
        const response = await axios.post(apiSanPham+"/view-data", formData);
        const newProductData = response.data;
        this.excels= newProductData; 
    },

    async add(newProduct) {
      try {
        const response = await axios.post(apiSanPham, newProduct); // Thay đổi URL và dữ liệu newProduct tùy theo API của bạn
        const newProductData = response.data;
        this.products.unshift(newProductData); // Thêm sản phẩm mới vào danh sách
      } catch (error) {
        console.error('Lỗi khi thêm sản phẩm:', error);
      }
    },
    async edit(updatedProduct) {
      try {
        const response = await axios.put(apiSanPham+`/update-san-pham/${updatedProduct.id}`, updatedProduct); // Thay đổi URL và dữ liệu updatedProduct tùy theo API của bạn
        const index = this.products.findIndex(product => product.id === updatedProduct.id);
        if (index !== -1) {
          let newProductData = this.products[index];
          newProductData = response.data;
          this.products[index] = newProductData;
        }
      } catch (error) {
        console.error('Lỗi khi sửa sản phẩm:', error);
      }
    },
    async delete(productId) {
      try {
        //  this.products = this.products.filter(product => product.id !== productId); // Xóa sản phẩm khỏi danh sách
        const response = await axios.put(apiSanPham+`/delete/${productId}`);
        const index = this.products.findIndex(product => product.id === productId);
        if (index !== -1) {
          let newProductData = this.products[index];
          newProductData = response.data;
        

          this.products[index] = newProductData;
        }
      } catch (error) {
        console.error('Lỗi khi xóa sản phẩm:', error);
      }
    },

    async khoiPhuc(productId) {
      try {
        //  this.products = this.products.filter(product => product.id !== productId); // Xóa sản phẩm khỏi danh sách
        const response = await axios.put(apiSanPham+`/khoi-phuc/${productId}`);
        const index = this.products.findIndex(product => product.id === productId);
        if (index !== -1) {
          let newProductData = this.products[index];
          newProductData = response.data;
         

          this.products[index] = newProductData;
        }
      } catch (error) {
        console.error('Lỗi khi xóa sản phẩm:', error);
      }
    },

    // async deleteImg(productId, img) {
    //   try {
    //     await axios.delete(apiSanPham+`/api/products/deleteImg?idSP=${productId}&img=${img}`); // Thay đổi URL tùy theo API của bạn
    //   } catch (error) {
    //     console.error('Lỗi khi xóa sản phẩm:', error);
    //   }
    // },

    async addSPCT(newProduct) {
      try {
        const response = await axios.post(apiSanPham+'/add-spct', newProduct); // Thay đổi URL và dữ liệu newProduct tùy theo API của bạn
      //  await this.fetchAll();
        return response.data;
      } catch (error) {
        console.error('Lỗi khi thêm sản phẩm:', error);
      }
    },

    async editSPCT(updatedProduct,id) {
      try {
        const response = await axios.put(apiSanPham+`/update-san-pham-CT/${id}`, updatedProduct); // Thay đổi URL và dữ liệu updatedProduct tùy theo API của bạn
      //  await this.fetchAll();
        return response.data;
      } catch (error) {
        console.error('Lỗi khi sửa sản phẩm:', error);
      }
    },

    async deleteSPCT(id) {
      try {
        const response = await axios.put(apiSanPham+`/delete-spct/${id}`); // Thay đổi URL và dữ liệu updatedProduct tùy theo API của bạn
    //    await this.fetchAll();
        return response.data;
      } catch (error) {
        console.error('Lỗi khi sửa sản phẩm:', error);
      }
    },

    async khoiPhucSPCT(id) {
      try {
        const response = await axios.put(apiSanPham+`/khoi-phuc-spct/${id}`); // Thay đổi URL và dữ liệu updatedProduct tùy theo API của bạn
      //  await this.fetchAll();
        return response.data;
      } catch (error) {
        console.error('Lỗi khi sửa sản phẩm:', error);
      }
    },

    async addImg(newImg, id) {
      try {
        const response = await axios.post(apiSanPham+`/add-img/${id}`, newImg); // Thay đổi URL và dữ liệu newProduct tùy theo API của bạn
    //    await this.fetchAll();
        return response.data;
      } catch (error) {
        console.error('Lỗi khi thêm sản phẩm:', error);
      }
    },

    async editImg(updateImg,id) {
      try {
        const response = await axios.put(apiSanPham+`/update-img/${id}`, updateImg); // Thay đổi URL và dữ liệu updatedProduct tùy theo API của bạn
    //    await this.fetchAll();
        return response.data;
      } catch (error) {
        console.error('Lỗi khi sửa sản phẩm:', error);
      }
    },

    async deleteImg(id) {
      try {
        const response = await axios.delete(apiSanPham+`/delete-img/${id}`); // Thay đổi URL và dữ liệu updatedProduct tùy theo API của bạn
     //   await this.fetchAll();
        return response.data;
      } catch (error) {
        console.error('Lỗi khi sửa sản phẩm:', error);
      }
    },
  },
});
