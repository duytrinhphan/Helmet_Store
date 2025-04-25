package com.example.demo.core.Admin.model.response;

import org.springframework.beans.factory.annotation.Value;

public interface DiaChiResponse {
    Integer getStt();

    @Value("#{target.id}")
    Integer getId();

    @Value("#{target.anh}")
    String getAnh();
}
