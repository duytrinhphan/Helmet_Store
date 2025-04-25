package com.example.demo.core.khachHang.controller;

import com.example.demo.core.Admin.repository.AdUserRepository;
import com.example.demo.core.khachHang.model.request.CommentRequest;
import com.example.demo.core.khachHang.service.KHCommentService;
import com.example.demo.core.token.service.TokenService;
import com.example.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("/api/khach-hang/comment")
public class CommentController {

    @Autowired
    KHCommentService khCommentService;

    @Autowired
    TokenService tokenService;

    @Autowired
    AdUserRepository userRepository;

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody CommentRequest request, @RequestParam String token) {
        return ResponseEntity.ok(khCommentService.addComment(request,token));
    }

    @PostMapping("/addPhanHoi")
    public ResponseEntity<?> addPhanHoi(@RequestBody CommentRequest request, @RequestParam String token) {
        return ResponseEntity.ok(khCommentService.addPhanHoi(request,token));
    }

    @GetMapping()
    public ResponseEntity<?> getList (@RequestParam("idsp") Integer idsp ) {

        return ResponseEntity.ok(khCommentService.getListComment(idsp));
    }

    @GetMapping("/getList")
    public ResponseEntity<?> getListById () {

        return ResponseEntity.ok(khCommentService.getListCommentByIdPhanHoi());
    }

    @DeleteMapping("/{idcomment}")
    public void deleteGHCT(@PathVariable(value = "idcomment") Integer idcomment) {

        khCommentService.deleteComment(idcomment);
    }

}
