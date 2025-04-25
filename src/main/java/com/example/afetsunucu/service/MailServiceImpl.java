package com.example.afetsunucu.service;

import com.example.afetsunucu.entity.HelpMessage;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    private final JavaMailSender mailSender;

    @Override
    public void sendAliveNotification(HelpMessage message) {
        for (String to : message.getEmergencyContactEmails()) {
            try {
                MimeMessage mail = mailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(mail, true);
                helper.setTo(to);
                helper.setSubject("Hayattayım Bildirimi");
                helper.setText(
                        String.format("Merhaba,\n\n%s (%s) kişisi güvende olduğunu bildirdi.\n\nAdres:\n%s %s, %s, %s, %s",
                                message.getSender().getName(),
                                message.getSender().getEmail(),
                                message.getBuildingNumber(),
                                message.getStreet(),
                                message.getNeighborhood(),
                                message.getDistrict(),
                                message.getCity()
                        ),
                        false
                );
                mailSender.send(mail);
            } catch (MessagingException e) {
                System.err.println("Mail gönderilirken hata oluştu: " + e.getMessage());
            }
        }
    }
}
