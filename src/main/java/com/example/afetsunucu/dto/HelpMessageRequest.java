package com.example.afetsunucu.dto;

import com.example.afetsunucu.entity.MessageType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class HelpMessageRequest {

    @NotNull
    private MessageType type;

    private String description;

    @NotBlank
    private String city;
    @NotBlank
    private String district;
    @NotBlank
    private String neighborhood;
    @NotBlank
    private String street;
    @NotBlank
    private String buildingNumber;

    private Integer personCount;

    private Boolean foodNeeded;
    private Boolean waterNeeded;
    private Boolean shelterNeeded;
    private Boolean healthNeeded;

    // Yeni alanlar:
    private String healthType;                      // sağlık durumu türü
    private List<String> victimNames;               // kişi isimleri
    private List<String> emergencyContactEmails;    // hayattayım bildirimi için
    private Map<String, Integer> aidQuantities;     // yardım türleri ve miktarları
}
