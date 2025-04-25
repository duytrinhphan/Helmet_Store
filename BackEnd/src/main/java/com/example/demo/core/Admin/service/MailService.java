package com.example.demo.core.Admin.service;

import com.example.demo.dto.DataMailDTO;
import jakarta.mail.MessagingException;

public interface MailService {
    void sendHtmlMail(DataMailDTO dataMail, String templateName) throws MessagingException;

    void sendHtmlMailOTP(DataMailDTO dataMail, String templateName) throws MessagingException;
}
