<script setup>
import { ref, onMounted } from 'vue';



const home = ref({
    icon: 'pi pi-home',
    to: { name: 'trang-chu' }
});
const items = ref([{ label: 'Computer' }, { label: 'Notebook' }, { label: 'Accessories' }, { label: 'Backpacks' }, { label: 'Item' }]);
import Divider from 'primevue/divider';
</script>

<template>
    <div class="grid">
      
        <div class="container">
            <br />
            <h2 style="color: #e8bd72">Về chúng tôi</h2>
            <Divider />
            <label for=""
                >Với khát khao tạo ra những chiếc mũ bảo hiểm đẳng cấp, sang trọng, an toàn cho người Việt Nam, công ty TNHH SX TM MAFA VN (Công ty MAFA VN) được thành lập. Vượt qua muôn vàn khó khăn ban đầu, với chặng đường gần 20 năm liên tục phát
                triển, Công ty MAFA VN đã trở thành nhà sản xuất mũ bảo hiểm hàng đầu Việt Nam. Các thương hiệu mũ bảo hiểm của công ty như Royal, ROC, ROYCE, KIO... đã in đậm trong tâm trí khách hàng và khẳng định được chỗ đứng vững chắc tại thị
                trường trong nước cũng như quốc tế.</label
            >
            <div class="text-center">
                <br />
                <img src="https://royalhelmet.com.vn/ckfinder/userfiles/images/ve%20chung%20toi%202(2).jpg" alt="" />
            </div>
            <label for=""
                >Ngoài sự nỗ lực của ban lãnh đạo, toàn thể cán bộ công nhân viên, sức mạnh chính tạo nên sự thành công ấy chính là tiêu chí lấy khách hàng làm trung tâm, luôn lắng nghe, thấu hiểu khách hàng để tạo ra những sản phẩm hoàn hảo, mang
                lại sự hài lòng cao nhất cho người sử dụng. <span class="quote-text">“Niềm tin khách hàng là sự thành công lớn nhất của chúng tôi”.</span></label
            >
            <br />
            <br />
            <label for="">
                Với tiêu chí ấy, các thương hiệu mũ bảo hiểm <span class="quote-text">Royal, ROC, ROYCE, KIO</span>...liên tục đạt được các chứng chỉ cao nhất về chất lượng trong nước cũng như quốc tế. Mũ bảo hiểm Royal, ROC, JC nhiều năm liền được
                người tiêu dùng bình chọn là Hàng Việt Nam Chất Lượng Cao...Đây là thước đo cao nhất của người tiêu dùng dành cho một thương hiệu Việt.
            </label>
            <div class="text-center">
                <br />
                <img src="https://royalhelmet.com.vn/ckfinder/userfiles/images/acg-chung%20nhan%20mafa-01.jpg" alt="" />
            </div>
            <br />
            <label for=""
                >Cũng với tiêu chí “Niềm tin khách hàng là sự thành công của chúng tôi”, công ty MAFA VN là công ty tiên phong trong cuộc cách mạng biến mũ bảo hiểm trở thành sản phẩm thời trang. Để chiếc mũ bảo hiểm của ngày hôm nay không chỉ đảm
                bảo sự an toàn mà còn tôn vinh nét đẹp, mang đến niềm tự hào, sự thoải mái, tiện lợi cao nhất cho người sử dụng. Chính vì vậy, những sản phẩm mũ bảo hiểm của công ty đã nhận được sự tưởng và ủng hộ mạnh mẽ của khách hàng trong và
                ngoài nước, tạo ra cuộc cách mạng trong nhận thức về mũ bảo hiểm của người tiêu dùng.</label
            >
            <br />
            <br />
            <label for=""> Chính Niềm Tin và Sự ủng hộ của Khách hàng đã giúp công ty xây dựng được:</label>
            <br />
            <br />
            <label for=""> -Hệ thống hơn <span class="quote-text">2.000 </span>đại lý, nhà phân phối mũ bảo hiểm trên khắp cả nước.</label>
            <br />
            <br />
            <label for=""> -Sản phẩm của công ty cũng xuất hiện tại hàng chục hệ thống siêu thị, chuỗi bán lẻ hàng đầu Việt Nam như <span class="quote-text">BigC, Aeon, Điện Máy Xanh, FM Style...</span></label>
            <br />
            <br />
            <label for="">
                -Với chất lượng và mẫu mã vượt trội, đạt tiêu chuẩn quốc tế, Royal, ROC, ROYCE, KIO còn vươn mình chinh phục khách hàng toàn cầu với hàng loạt đơn hàng xuất khẩu lớn từ các quốc gia như
                <span class="quote-text">Mỹ, EU, Nhật Bản, Australia, Ấn Độ...</span></label
            >
            <br />
            <br />
            <label for="">
                -Tận dụng sức mạnh của cuộc cách mạng 4.0, công ty cũng đầu tư mạnh mẽ vào các kênh bán hàng và chăm sóc khách hàng online thông qua các trang web:
                <span class="quote-text">Royalhelmet.com.vn, Rochelmet.vn, Nonxedap.com, Roycehelmet.com, Kiohelmet.com</span> cùng hệ thống fanpage, các kênh thương mại điện tử khác.</label
            >
            <br />
            <br />
            <label for="">
                -Đặc biệt, <span class="quote-text">MAFA VN</span> đã trở thành nhà sản xuất mũ bảo hiểm đầu tiên của Việt Nam xây dựng gian hàng chính thức trên <span class="quote-text">Amazon.com</span>-trang thương mại điện tử hàng đầu thế giới.
                Bước đi chiến lược này không những đã khẳng định được chất lượng mang tầm quốc tế của mũ bảo hiểm <span class="quote-text">Royal, ROC, ROYCE, KIO...</span>mà còn góp thêm một thương hiệu Việt vươn ra chinh phục toàn cầu, nâng cao niềm
                tự hào Việt Nam.</label
            >
            <br />
            <br />
            <label for="">
                Những hệ thống bán hàng và chăm sóc khách hàng ấy không những giúp khách hàng ở bất cứ nơi đâu có thể dễ dàng tiếp cận và sở hữu các sản phẩm của công ty mà còn là kênh lắng nghe, thấu hiểu và phản hồi những ý kiến của khách hàng một
                cách nhanh chóng, hiệu quả nhằm đẩy mạnh hơn nữa việc tạo ra những sản phẩm tốt nhất, hoàn hảo nhất, chinh phục niềm tin của quý khách hàng!
            </label>
            <br />
            <br />
            <label for=""> <span class="quote-text">“Niềm Tin Khách Hàng Là Niềm Tự Hào của Chúng Tôi”</span></label>
            <br />
            <br />
        </div>
    </div>
</template>

<style scoped>
body,
html {
    height: 100%;
    margin: 0;
    padding: 0;
    overflow: hidden;
}

.image-container {
    position: relative;
    width: 1535px; /* Chiều rộng cố định của .image-container */
    height: 250px; /* Chiều cao cố định của .image-container */
    overflow: hidden;
}

.full-width-image {
    width: 100%; /* Căn full viền theo chiều rộng của .image-container */
    height: 100%; /* Căn full viền theo chiều cao của .image-container */
    object-fit: cover;
}

.image-overlay {
    position: absolute;
    top: 65%;
    left: 50%;
    transform: translate(-50%, -50%);
    text-align: center;
    color: #fff;
    font-family: 'Arial', sans-serif;
}

.quote-container {
    text-align: center;
    margin-top: 50px;
}

.quote-text {
    font-size: 16px;
    font-weight: bold;
    color: black; /* Đặt màu chữ thành đen */
}

.grid {
    margin-top: 70px;
    display: flex;
    justify-content: center;
}

.container {
    width: 1100px;
    background: white;
}
</style>
