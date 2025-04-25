package com.example.demo.core.Admin.model.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BHTQUserResponse {
    private Integer id;
    private String ma;
    private String ten;
    private String sdt;
    @JsonIgnore
    private String message;

    public BHTQUserResponse(String message) {
        this.message = message;
    }
}
