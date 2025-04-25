package com.example.afetsunucu.dto;

import lombok.Data;

import java.util.Map;

@Data
public class StatisticsResponse {
    private long totalMessages;
    private long fulfilledMessages;
    private long unfulfilledMessages;
    private long totalUsers;
    private Map<String, Long> messageTypeDistribution;
}
