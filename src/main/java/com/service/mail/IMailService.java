package com.service.mail;

import com.model.dto.Mail;

public interface IMailService {
    void sendEmail(Mail mail);
}
