package com.example.demo.core.khachHang.service.impl.KHDetailServiceImpl;

import com.example.demo.core.Admin.repository.AdImageReponsitory;
import com.example.demo.core.khachHang.service.KHDetailService.ImageServie;
import com.example.demo.entity.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageServiceImpl implements ImageServie {

    @Autowired
    private AdImageReponsitory repo;

    @Override
    public List<Image> findByIdCTSP(Integer id) {
        return repo.findBySanPhamId(id);
    }
}
