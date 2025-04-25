package com.example.demo.core.khachHang.service;

import com.example.demo.core.khachHang.model.request.CommentRequest;
import com.example.demo.entity.Comment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface KHCommentService {

    Comment addComment(CommentRequest request, String token);

    Comment addPhanHoi(CommentRequest request, String token);

    List<Comment> getListComment( Integer idsp);

    List<Comment> getListCommentByIdPhanHoi( );

    void deleteComment(Integer id);
}
