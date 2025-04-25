package com.example.afetsunucu.service;

import com.example.afetsunucu.dto.StatisticsResponse;
import com.example.afetsunucu.entity.MessageType;
import com.example.afetsunucu.repository.HelpMessageRepository;
import com.example.afetsunucu.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatisticsService {

    private final HelpMessageRepository helpMessageRepository;
    private final UserRepository userRepository;

    public StatisticsResponse getStatistics() {
        long totalMessages = helpMessageRepository.count();
        long fulfilledMessages = helpMessageRepository.countByFulfilled(true);
        long unfulfilledMessages = helpMessageRepository.countByFulfilled(false);
        long totalUsers = userRepository.count();

        Map<String, Long> messageTypeDistribution = Arrays.stream(MessageType.values())
                .collect(Collectors.toMap(
                        Enum::name,
                        type -> helpMessageRepository.countByType(type)
                ));

        StatisticsResponse response = new StatisticsResponse();
        response.setTotalMessages(totalMessages);
        response.setFulfilledMessages(fulfilledMessages);
        response.setUnfulfilledMessages(unfulfilledMessages);
        response.setTotalUsers(totalUsers);
        response.setMessageTypeDistribution(messageTypeDistribution);
        return response;
    }
}
