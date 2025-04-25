package com.example.demo.reponsitory;

import com.example.demo.entity.Size;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SizeReponsitory extends JpaRepository<Size, Integer> {

    List<Size> getAllByTrangThai(Integer trangThai, Sort sort);

}
