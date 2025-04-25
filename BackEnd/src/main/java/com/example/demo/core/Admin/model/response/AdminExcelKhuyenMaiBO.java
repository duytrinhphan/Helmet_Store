package com.example.demo.core.Admin.model.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AdminExcelKhuyenMaiBO {
    private Long total;
    private Long totalSuccess;
    private Long totalError;
    private List<AdminExcelKhuyenMaiResponse> responseList;
}
