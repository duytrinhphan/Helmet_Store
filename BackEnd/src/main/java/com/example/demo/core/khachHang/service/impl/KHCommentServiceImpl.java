package com.example.demo.core.khachHang.service.impl;

import com.example.demo.core.Admin.repository.AdUserRepository;
import com.example.demo.core.khachHang.model.request.CommentRequest;
import com.example.demo.core.khachHang.model.response.CommentResponse;
import com.example.demo.core.khachHang.repository.KHCommentRepository;
import com.example.demo.core.khachHang.service.KHCommentService;
import com.example.demo.core.token.service.TokenService;
import com.example.demo.entity.Comment;
import com.example.demo.entity.GioHangChiTiet;
import com.example.demo.entity.SanPham;
import com.example.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class KHCommentServiceImpl implements KHCommentService {

    @Autowired
    KHCommentRepository khCommentRepo;

    @Autowired
    TokenService tokenService;

    @Autowired
    AdUserRepository userRepository;


    @Override
    public Comment addComment(CommentRequest request, String token) {

        if (tokenService.getUserNameByToken(token) == null) {
            return null;
        }

        String userName = tokenService.getUserNameByToken(token);
        User user = userRepository.findByUserName(userName);

        Comment comment = new Comment();
        comment.setNoiDung(request.getNoiDung());
        comment.setUser(user);
        comment.setSanPham(SanPham.builder().id(request.getSanPham()).build());


        return khCommentRepo.save(comment);
    }

    @Override
    public Comment addPhanHoi(CommentRequest request, String token) {
        if (tokenService.getUserNameByToken(token) == null) {
            return null;
        }

        String userName = tokenService.getUserNameByToken(token);
        User user = userRepository.findByUserName(userName);

        Comment comment = new Comment();
        comment.setNoiDung(request.getNoiDung());
        comment.setIdPhanHoi(request.getIdPhanHoi());
        comment.setUser(user);
        comment.setSanPham(SanPham.builder().id(request.getSanPham()).build());

        return khCommentRepo.save(comment);
    }

    @Override
    public List<Comment> getListComment(Integer idsp) {
        return khCommentRepo.getListComment( idsp);
    }

    @Override
    public List<Comment> getListCommentByIdPhanHoi() {
        return khCommentRepo.getListCommentByIdPhanHoi();
    }

    @Override
    public void deleteComment(Integer id) {

            Comment comment = khCommentRepo.findById(id).get();

            List<Comment> listcomment = khCommentRepo.getListByIdPhanHoi(id);
                for (Comment cm : listcomment) {
                    khCommentRepo.deleteById(cm.getId());
                }
                khCommentRepo.deleteById(comment.getId());
           // }
        }

}
