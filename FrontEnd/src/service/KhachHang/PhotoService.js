export const PhotoService = {
    getData() {
        return [
            {
                itemImageSrc: 'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/hwT0YOIMG_5015.jpg',
                thumbnailImageSrc: 'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/hwT0YOIMG_5015.jpg',
                alt: 'Description for Image 1',
                price: 20,
                title: 'Title 1'
            },
            {
                itemImageSrc: 'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/dmzrOUIMG_5076.jpg',
                thumbnailImageSrc: 'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/dmzrOUIMG_5076.jpg',
                alt: 'Description for Image 2',
                price: 20,
                title: 'Title 2'
            },
            {
                itemImageSrc: 'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/GjL83QIMG_5067.jpg',
                thumbnailImageSrc: 'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/GjL83QIMG_5067.jpg',
                alt: 'Description for Image 3',
                price: 20,
                title: 'Title 3'
            },
            {
                itemImageSrc: 'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/qF37bUIMG_5058.jpg',
                thumbnailImageSrc: 'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/qF37bUIMG_5058.jpg',
                alt: 'Description for Image 4',
                price: 20,
                title: 'Title 4'
            },
            {
                itemImageSrc: 'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/asFeNUIMG_5050.jpg',
                thumbnailImageSrc: 'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/asFeNUIMG_5050.jpg',
                alt: 'Description for Image 5',
                price: 20,
                title: 'Title 5'
            },
            {
                itemImageSrc: 'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/Hv1g8FIMG_5029.jpg',
                thumbnailImageSrc: 'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/Hv1g8FIMG_5029.jpg',
                alt: 'Description for Image 6',
                price: 20,
                title: 'Title 6'
            },
            {
                itemImageSrc: 'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/qrIh2lIMG_4989.jpg',
                thumbnailImageSrc: 'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/qrIh2lIMG_4989.jpg',
                alt: 'Description for Image 7',
                price: 20,
                title: 'Title 7'
            },
            {
                itemImageSrc: 'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/zBIhPWIMG_4976.jpg',
                thumbnailImageSrc: 'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/zBIhPWIMG_4976.jpg',
                alt: 'Description for Image 8',
                price: 20,
                title: 'Title 8'
            },
            {
                itemImageSrc: 'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/1jgFG3IMG_4779.jpg',
                thumbnailImageSrc: 'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/1jgFG3IMG_4779.jpg',
                alt: 'Description for Image 9',
                price: 20,
                title: 'Title 9'
            },
            {
                itemImageSrc: 'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/t0NPKnIMG_4999.jpg',
                thumbnailImageSrc: 'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/t0NPKnIMG_4999.jpg',
                alt: 'Description for Image 10',
                price: 20,
                title: 'Title 10'
            },
            {
                itemImageSrc: 'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/sGyDewIMG_5038.jpg',
                thumbnailImageSrc: 'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/sGyDewIMG_5038.jpg',
                alt: 'Description for Image 11',
                price: 20,
                title: 'Title 11'
            },
            {
                itemImageSrc: 'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/4N5OaDIMG_2331.jpg',
                thumbnailImageSrc: 'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/4N5OaDIMG_2331.jpg',
                alt: 'Description for Image 12',
                price: 20,
                title: 'Title 12'
            },
            {
                itemImageSrc: 'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/j5aoFDIMG_4472.jpg',
                thumbnailImageSrc: 'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/j5aoFDIMG_4472.jpg',
                alt: 'Description for Image 13',
                price: 20,
                title: 'Title 13'
            },
        ];
    },

    getImages() {
        return Promise.resolve(this.getData());
    },
    getProductsSmall() {
        return Promise.resolve(this.getProductsData().slice(0, 10));
    }
};



