<script setup>
import { useLayout } from '@/layout/composables/layout';
import { computed } from 'vue';

const { layoutConfig } = useLayout();

const logoUrl = computed(() => {
    return `layout/images/${layoutConfig.darkTheme.value ? 'logo-white' : 'logo-dark'}.svg`;
});
</script>

<template>
    <!-- Remove the container if you want to extend the Footer to full width. -->
    <!-- Footer -->
    <footer class="text-lg-start text-white" style="background-color: #3e4551">
        <!-- Grid container -->
        <div class="p-4 pb-0">
            <!-- Section: Links -->
            <section class="">
                <!--Grid row-->
                <div class="row">
                    <!--Grid column-->
                    <div class="col-md-2 col-lg-2 col-xl-2 mx-auto mb-4">
                        <h6 class="text-uppercase fw-bold">Công ty TNHH siêu to khổng lồ 1 thành viên</h6>
                        <hr class="mb-4 mt-0 d-inline-block mx-auto" style="width: 60px; background-color: #7c4dff; height: 2px" />
                        <p>Các thương hiệu mũ bảo hiểm của công ty như Royal, ROC, ROYCE, KIO... đã in đậm trong tâm trí khách hàng và khẳng định được chỗ đứng vững chắc tại thị trường trong nước cũng như quốc tế.</p>
                    </div>
                    <!--Grid column-->

                    <!--Grid column-->
                    <div class="col-md-2 col-lg-2 col-xl-2 mx-auto mb-4">
                        <h6 class="text-uppercase fw-bold">THÔNG TIN</h6>
                        <hr class="mb-4 mt-0 d-inline-block mx-auto" style="width: 60px; background-color: #7c4dff; height: 2px" />
                        <p>
                            <a href="#!" class="text-white">Giới thiệu chuỗi nón trùm</a>
                        </p>
                        <p>
                            <a href="#!" class="text-white">Chính sách bán hàng</a>
                        </p>
                        <p>
                            <a href="#!" class="text-white">Chính sách bảo hành, đổi mới</a>
                        </p>
                        <p>
                            <a href="#!" class="text-white">Giao hàng, thanh toán</a>
                        </p>
                        <p>
                            <a href="#!" class="text-white">Chính sách bảo vệ thông tin cá nhân</a>
                        </p>
                        <p>
                            <a href="#!" class="text-white">Hợp tác với Nón Trùm</a>
                        </p>
                        <p>
                            <a href="#!" class="text-white">Tuyển dụng</a>
                        </p>
                    </div>
                    <!--Grid column-->

                    <!--Grid column-->
                    <div class="col-md-2 col-lg-2 col-xl-2 mx-auto mb-4">
                        <!-- Links -->
                        <h6 class="text-uppercase fw-bold">Liên hệ</h6>
                        <hr class="mb-4 mt-0 d-inline-block mx-auto" style="width: 60px; background-color: #7c4dff; height: 2px" />
                        <p><i class="pi pi-home mr-3"></i> 342 đường Hồ Tùng Mậu, Bắc Từ Liêm, Hà Nội</p>
                        <p><i class="pi pi-envelope mr-3"></i> vunlph28399@fpt.edu.vn</p>
                        <p><i class="pi pi-phone mr-3"></i> + 01 234 567 88</p>
                        <p><i class="pi pi-print mr-3"></i> + 01 234 567 89</p>
                    </div>
                    <!--Grid column-->

                    <!--Grid column-->

                    <!--Grid column-->
                    <div class="col-md-2 col-lg-2 col-xl-2 mx-auto mb-4">
                        <h6 class="text-uppercase fw-bold">Kênh truyền thông & sàn thương mại điển tử</h6>
                        <hr class="mb-4 mt-0 d-inline-block mx-auto" style="width: 60px; background-color: #7c4dff; height: 2px" />
                        <p>
                            <a href="#!" class="text-white">Tiktok Nón Trùm</a>
                        </p>
                        <p>
                            <a href="#!" class="text-white">Shopee Nón Trùm</a>
                        </p>
                        <p>
                            <a href="#!" class="text-white">Tiki Nón Trùm</a>
                        </p>
                        <p>
                            <a href="#!" class="text-white">Tiki Rona</a>
                        </p>
                        <p>
                            <a href="#!" class="text-white">Lazada Nón Trùm</a>
                        </p>
                        <p>
                            <a href="#!" class="text-white">Lazada Rona</a>
                        </p>
                    </div>
                </div>
                <!--Grid row-->
            </section>
            <!-- Section: Links -->

            <hr class="mb-4" />

            <!-- Section: CTA -->
            <section class="">
                <p class="d-flex justify-content-center align-items-center">
                    <span class="me-3">Register for free</span>
                    <button type="button" class="btn btn-outline-light btn-rounded">Sign up!</button>
                </p>
            </section>
            <!-- Section: CTA -->

            <hr class="mb-4" />

            <!-- Section: Social media -->
            <section class="mb-4 text-center">
                <!-- Facebook -->
                <a class="btn btn-outline-light btn-floating m-1" href="#!" role="button"><i class="pi pi-facebook"></i></a>

                <!-- Twitter -->
                <a class="btn btn-outline-light btn-floating m-1" href="#!" role="button"><i class="pi pi-twitter"></i></a>

                <!-- Google -->
                <a class="btn btn-outline-light btn-floating m-1" href="#!" role="button"><i class="pi pi-google"></i></a>

                <!-- Instagram -->
                <a class="btn btn-outline-light btn-floating m-1" href="#!" role="button"><i class="pi pi-instagram"></i></a>

                <!-- Linkedin -->
                <a class="btn btn-outline-light btn-floating m-1" href="#!" role="button"><i class="pi pi-linkedin"></i></a>

                <!-- Github -->
                <a class="btn btn-outline-light btn-floating m-1" href="#!" role="button"><i class="pi pi-github"></i></a>
            </section>
            <!-- Section: Social media -->
        </div>
        <!-- Grid container -->

        <!-- Copyright -->
        <div class="text-center p-3" style="background-color: rgba(0, 0, 0, 0.2)">
            © 2023 VNK:
            <a class="text-white" href="https://mdbootstrap.com/">Dự án tốt nghiệp</a>
        </div>
        <!-- Copyright -->
    </footer>
    <!-- Footer -->
    <!-- End of .container -->
</template>
<style lang="scss" scoped>
</style>

