import { defineStore } from 'pinia';
import axios from 'axios';

const apiDetail = `${import.meta.env.VITE_BASE_API_ENDPOINT}/khach-hang/detail`;


export const useDetailProductStore = defineStore('detail', {
    id: 'product',
    state: () => ({
        product: {},
        products: [],
        images: [],
        sizes: [],
        mauSacs: []
    }),
    actions: {
        async fetchSPCTByIdSP(idProduct) {
            try {
                const response = await axios.get(apiDetail + `/find-spct-by-idSP/` + idProduct);
                this.products = response.data;
            } catch (error) {
                console.error('Lỗi khi lấy chi tiết sản phẩm:', error);
                throw error;
            }
        },
        async fetchProductById(idProduct) {
            try {
                const response = await axios.get(apiDetail + `/find-by-id/` + idProduct);
                this.product = response.data;
            } catch (error) {
                console.error('Lỗi khi lấy chi tiết sản phẩm:', error);
                throw error;
            }
        },
        async fetchAllImage(idProduct) {
            try {
                const response = await axios.get(apiDetail + `/findByImage/` + idProduct); // Thay đổi URL dựa trên API của bạn
                this.images = response.data;
            } catch (error) {
                console.error('Lỗi khi lấy danh sách sản phẩm:', error);
            }
        },

        async fetchIdSPCT(idProduct,idSize,idMau) {
          try {
              const response = await axios.get(apiDetail + `/find-spct-id?idSize=${idSize}&idMau=${idMau}&idSP=${idProduct}` ); // Thay đổi URL dựa trên API của bạn
              this.products = response.data;
          } catch (error) {
              console.error('Lỗi khi lấy danh sách sản phẩm:', error);
          }
      },


        async fetchAllSize(idProduct) {
            try {
                const response = await axios.get(apiDetail + `/find-size/` + idProduct); // Thay đổi URL dựa trên API của bạn
                this.sizes = response.data;
                // console.log(response.data);
            } catch (error) {
                console.error('Lỗi khi lấy danh sách sản phẩm:', error);
            }
        },


    async fetchAllMauSac(idProduct) {
      try {
        const response = await axios.get(apiDetail + `/find-Mau-Sac/`+idProduct); // Thay đổi URL dựa trên API của bạn
        this.mauSacs = response.data;
        //  console.log('mau: ',   this.mauSacs  );
      } catch (error) {
        console.error('Lỗi khi lấy danh sách sản phẩm:', error);
      }
    },
    async fetchAll() {
      try {
        const response = await axios.get(apiDetail + '/getAll'); // Thay đổi URL dựa trên API của bạn
        this.products = response.data;
        //  console.table(this.products.size);

      } catch (error) {
        console.error('Lỗi khi lấy danh sách sản phẩm:', error);
      }
    },

    async getSizeByMauSac(idCTSP, idMS) {
      try {
        const response = await axios.get(`${import.meta.env.VITE_BASE_API_ENDPOINT}/khach-hang/detail/getSizeByMS/${idCTSP}?idms=${idMS}`); // Thay đổi URL dựa trên API của bạn
        this.sizes = response.data;
        return this.sizes;
      } catch (error) {
        console.error('Lỗi khi lấy danh sách sản phẩm:', error);
      }
    },
    async getMauSacBySize(idCTSP, idSizeCT) {
      try {
        const response = await axios.get(`${import.meta.env.VITE_BASE_API_ENDPOINT}/khach-hang/detail/getMauSacBySize/${idCTSP}?idsizect=${idSizeCT}`); // Thay đổi URL dựa trên API của bạn
        this.mauSacs = response.data;
        return this.mauSacs;
      } catch (error) {
        console.error('Lỗi khi lấy danh sách sản phẩm:', error);
      }
    },
    async getSanPhamSelected(idCTSP, idMS, idSizeCT) {
      try {
        const response = await axios.get(`${import.meta.env.VITE_BASE_API_ENDPOINT}/khach-hang/detail/getSanPhamSelected/${idCTSP}?idms=${idMS}&idsizect=${idSizeCT}`); // Thay đổi URL dựa trên API của bạn
        this.data = response.data;
        return this.data;
      } catch (error) {
        console.error('Lỗi khi lấy danh sách sản phẩm:', error);
      }
    },
    async getAllSLTon(idCTSP) {
      try {
        const response = await axios.get(apiDetail + '/getSLTon/'+idCTSP); // Thay đổi URL dựa trên API của bạn
        this.slTon = response.data;
        //  console.table(this.products.size)
      } catch (error) {
        console.error('Lỗi khi lấy danh sách sản phẩm:', error);
      }
    },
  }
});

