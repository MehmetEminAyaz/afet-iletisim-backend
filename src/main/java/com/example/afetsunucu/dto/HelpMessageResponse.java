package com.example.afetsunucu.dto;

import com.example.afetsunucu.entity.MessageType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@Builder
public class HelpMessageResponse {
    private Long id;
    private MessageType type;
    private String description;
    private LocalDateTime timestamp;

    private String city;
    private String district;
    private String neighborhood;
    private String street;
    private String buildingNumber;

    private Integer personCount;
    private Boolean foodNeeded;
    private Boolean waterNeeded;
    private Boolean shelterNeeded;
    private Boolean healthNeeded;

    private Boolean fulfilled;
    private Double latitude;
    private Double longitude;

    private String senderName;
    private String senderEmail;

    // Yeni alanlar:
    private String healthType;
    private List<String> victimNames;
    private List<String> emergencyContactEmails;
    private Map<String, Integer> aidQuantities;
}
