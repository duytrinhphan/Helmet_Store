package com.example.demo.core.khachHang.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDTO {
    private String accessToken;
    private String tokenType = "Bearer";
    private String usernameOrEmail;
    private Integer userID;

    public LoginResponseDTO (String accessToken,String usernameOrEmail) {
        this.accessToken = accessToken;
        this.usernameOrEmail = usernameOrEmail;
    }
}
