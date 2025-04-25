package com.example.demo.core.khachHang.repository;

import com.example.demo.entity.Comment;
import com.example.demo.reponsitory.CommentRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface KHCommentRepository extends CommentRepository {

    @Query("select a from Comment a join User u on a.user.id = u.id join SanPham sp on sp.id = a.sanPham.id where sp.id =:idsp and a.idPhanHoi is null")
    List<Comment> getListComment( Integer idsp);


    @Query("select a from Comment a where a.idPhanHoi is not null ")
    List<Comment> getListCommentByIdPhanHoi();

    @Query("select a from Comment a where a.idPhanHoi =:idphanhoi ")
    List<Comment> getListByIdPhanHoi(Integer idphanhoi);

//    @Query(value = """
//                select a.id, a.noi_dung as noiDung, u.ten as tenUser, sp.ten as tenSP from comment a join user u on a.id_user = u.id join san_pham sp on a.id_san_pham = sp.id where u.id =:iduser and sp.id =:idsp
//                """, nativeQuery = true)
//    List<CommentResponse> getListComment(Integer iduser, Integer idsp);
}
