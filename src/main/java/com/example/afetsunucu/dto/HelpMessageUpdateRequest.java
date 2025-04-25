package com.example.afetsunucu.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class HelpMessageUpdateRequest {
    private String description;
    private Boolean fulfilled;
    private String healthType;
    private List<String> victimNames;
    private List<String> emergencyContactEmails;
    private Map<String, Integer> aidQuantities;
}
