package com.neobis.g4g.girls_for_girls.service;

import org.springframework.mail.SimpleMailMessage;


public interface EmailService {
    void sendEmail(SimpleMailMessage email);
}
