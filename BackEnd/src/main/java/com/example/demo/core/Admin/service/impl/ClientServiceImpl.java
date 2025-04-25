package com.example.demo.core.Admin.service.impl;

import com.example.demo.core.Admin.model.request.AdminUserRequest;
import com.example.demo.core.Admin.model.request.OTPResquest;
import com.example.demo.core.Admin.service.ClientService;
import com.example.demo.core.Admin.service.MailService;
import com.example.demo.dto.DataMailDTO;
import com.example.demo.util.Const;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ClientServiceImpl implements ClientService {
    @Autowired
    private MailService mailService;

    @Override
    public Boolean create(AdminUserRequest sdi) {
        try {
            DataMailDTO dataMail = new DataMailDTO();

            dataMail.setTo(sdi.getEmail());
            dataMail.setSubject(Const.SEND_MAIL_SUBJECT.CLIENT_REGISTER);

            Map<String, Object> props = new HashMap<>();
            props.put("ten", sdi.getTen());
            props.put("userName", sdi.getUserName());
            props.put("password", sdi.getPassword());
            dataMail.setProps(props);

            mailService.sendHtmlMail(dataMail, Const.TEMPLATE_FILE_NAME.CLIENT_REGISTER);
            return true;
        } catch (MessagingException exp){
            exp.printStackTrace();
        }
        return false;
    }

    @Override
    public Boolean createOTP(OTPResquest otpRequest) {
        try {
            DataMailDTO dataMail = new DataMailDTO();

            dataMail.setTo( otpRequest.getEmail());
            dataMail.setSubject(Const.SEND_MAIL_OTP.CLIENT_REGISTER);

            Map<String, Object> props = new HashMap<>();
            props.put("title", otpRequest.getTitle());
            props.put("ten", otpRequest.getTen());
            dataMail.setProps(props);

            mailService.sendHtmlMail(dataMail, Const.TEMPLATE_OTP_NAME.CLIENT_REGISTER);
            return true;
        } catch (MessagingException exp){
            exp.printStackTrace();
        }
        return false;
    }
}
