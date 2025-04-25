package com.example.afetsunucu.service;

import com.example.afetsunucu.entity.HelpMessage;

public interface MailService {
    void sendAliveNotification(HelpMessage message);
}
