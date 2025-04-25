package com.example.demo.infrastructure.constant;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

@Data
@AllArgsConstructor
public abstract class VNPayConstant {
   // @Value("${backend.base-endpoint}")
    private static String BASE_BACKEND_ENDPOINT = "https://vnk-backend-application.azuremicroservices.io";

    public static String vnp_Version = "2.1.0";
    public static String vnp_Command = "2.1.0";
    public static String vnp_TmnCode = "1FKUA6P0";

    public static String vnp_HashSecret = "FGBPHAVOMYHXVOJUZAVTOXVFBHELABND";

    public static String vnp_Url = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html";
    public static String vnp_BankCode = "NCB";
    public static String vnp_CurrCode = "VND";
    //    public static String vnp_IpAddr = "0:0:0:0:0:0:0:1";
    public static String vnp_Locale = "vn";
//    public static String vnp_ReturnUrl = "http://localhost:5173/payment/payment-success";
    public static String vnp_ReturnUrl = BASE_BACKEND_ENDPOINT + "/api/payment-callback";

}
