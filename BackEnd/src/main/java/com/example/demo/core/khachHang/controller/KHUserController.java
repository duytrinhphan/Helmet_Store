package com.example.demo.core.khachHang.controller;


import com.example.demo.core.khachHang.model.request.LoginPayLoad;
import com.example.demo.core.khachHang.model.response.LoginResponseDTO;
import com.example.demo.core.khachHang.repository.KHUserRepository;
import com.example.demo.core.khachHang.service.KHUserService;
import com.example.demo.core.khachHang.service.LoginService;
import com.example.demo.entity.User;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/khach-hang/user")
@CrossOrigin(origins = {"*"})
public class KHUserController {

    @Autowired
    KHUserRepository khUserRepo;

    @Autowired
    private KHUserService khUserService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private LoginService loginService;

    @GetMapping()
    public User getUserByUsername(@RequestParam("username") String username) {
        User user = khUserRepo.findAllByUserName(username);
        return user;
    }

    @GetMapping("/find-user-by-email/{email}")
    public ResponseEntity<?> getUserByEmail(@PathVariable String email, @RequestParam("username") String username, @RequestParam("anh") String anh) {
        User user = khUserService.dangNhapGoogle(email, username, anh);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/createAccountGG/{email}")
    public ResponseEntity<?> createAccountGG(@PathVariable String email, @RequestParam("username") String username, @RequestParam("anh") String anh) {
        User user = khUserService.createAccountGG(email,username,anh);
        return ResponseEntity.ok(user);
    }

    @PatchMapping("/updateSDT/{id}")
    public ResponseEntity<?> updateSDT( @PathVariable(value = "id") Integer id, @RequestParam("sdt") String sdt) {
        User user = khUserService.updateSDT(id,sdt);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginPayLoad loginPayload) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginPayload.getUsernameOrEmail(), loginPayload.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = loginService.login(loginPayload);
            String usernameOrEmail = loginPayload.getUsernameOrEmail();

            LoginResponseDTO jwtAuthResponse = new LoginResponseDTO();
            jwtAuthResponse.setAccessToken(token);
            jwtAuthResponse.setUsernameOrEmail(usernameOrEmail);
//            jwtAuthResponse.setUserID(khUserService.findByToken(token) != null ? khUserService.findByToken(token).getId() : null);
            jwtAuthResponse.setUserID(khUserService.findByToken(token).getId());

//            ResponseDTO response = new ResponseDTO(true, "Logged In Successfully!", jwtAuthResponse, null, HttpStatus.OK.value());
            return ResponseEntity.ok(jwtAuthResponse);
        } catch (AuthenticationException ex) {
            // Lỗi xác thực, đăng nhập không thành công
//            ResponseDTO response = new ResponseDTO(false, "Invalid Username or Password", null, ex.getMessage(), HttpStatus.UNAUTHORIZED.value());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex);
        }
    }

    @GetMapping("/check-validate")
    public ResponseEntity<?> checkValidate(@RequestBody LoginPayLoad loginPayload) {
        return ResponseEntity.ok(khUserService.checkValiDate(loginPayload));
    }

    @GetMapping("/find-by-token")
    public ResponseEntity<?> validate(@RequestParam("token") String token) {
        return ResponseEntity.ok(khUserService.findByToken(token));
    }


    @GetMapping("/find-email")
    public ResponseEntity<?> findUserByEmail(@RequestParam("email") String email) {
        return ResponseEntity.ok(khUserService.findByEmail(email));
    }

    @PutMapping("/{id}/doi-mat-khau")
    public ResponseEntity<?> doiMatKhau(@PathVariable("id") Integer userId, @RequestBody Map<String, String> reqBody) {
        return khUserService.doiMatKhau(userId, reqBody.get("oldPassword"), reqBody.get("newPassword")) ? ResponseEntity.ok().build() : ResponseEntity.badRequest().body("Mật khẩu cũ chưa chính xác") ;
    }
}
