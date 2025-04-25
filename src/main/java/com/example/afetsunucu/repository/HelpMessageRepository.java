package com.example.afetsunucu.repository;

import com.example.afetsunucu.entity.HelpMessage;
import com.example.afetsunucu.entity.MessageType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HelpMessageRepository extends JpaRepository<HelpMessage, Long> {
    List<HelpMessage> findByType(MessageType type);
    List<HelpMessage> findBySender_Id(Long senderId);
    List<HelpMessage> findByFulfilled(Boolean fulfilled);
    void deleteById(Long id);
    long countByFulfilled(boolean fulfilled);
    long countByType(com.example.afetsunucu.entity.MessageType type);
}
