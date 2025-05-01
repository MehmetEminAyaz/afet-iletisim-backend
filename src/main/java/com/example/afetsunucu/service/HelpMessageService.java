package com.example.afetsunucu.service;

import com.example.afetsunucu.dto.*;
import com.example.afetsunucu.entity.HelpMessage;
import com.example.afetsunucu.entity.MessageType;
import com.example.afetsunucu.entity.User;
import com.example.afetsunucu.repository.HelpMessageRepository;
import com.example.afetsunucu.repository.UserRepository;
import com.example.afetsunucu.security.UserDetailsImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HelpMessageService {

    private final HelpMessageRepository helpMessageRepository;
    private final UserRepository userRepository;
    private final GeocodingService geocodingService;
    private final MailService mailService; // 🔹 Eksik servis eklendi

    @Transactional
    public HelpMessageResponse createHelpMessage(HelpMessageRequest request) {
        User currentUser = getCurrentUser();

        String fullAddress = request.getCity()+ ","+request.getDistrict()+ ","+request.getNeighborhood() +","+request.getStreet()+"," + request.getBuildingNumber();



        GeocodingResult geo = geocodingService.geocode(fullAddress);

        HelpMessage message = HelpMessage.builder()
                .type(request.getType())
                .description(request.getDescription())
                .timestamp(LocalDateTime.now())
                .city(request.getCity())
                .district(request.getDistrict())
                .neighborhood(request.getNeighborhood())
                .street(request.getStreet())
                .buildingNumber(request.getBuildingNumber())
                .personCount(request.getPersonCount())
                .foodNeeded(request.getFoodNeeded())
                .waterNeeded(request.getWaterNeeded())
                .shelterNeeded(request.getShelterNeeded())
                .healthNeeded(request.getHealthNeeded())
                .fulfilled(false)
                .latitude(geo.getLatitude())
                .longitude(geo.getLongitude())
                .sender(currentUser)
                .healthType(request.getHealthType())
                .victimNames(request.getVictimNames())
                .emergencyContactEmails(request.getEmergencyContactEmails())
                .aidQuantities(request.getAidQuantities())
                .build();

        helpMessageRepository.save(message);

        if (message.getType() == MessageType.HAYATTAYIM) {
            mailService.sendAliveNotification(message);
        }

        return mapToResponse(message);
    }

    public List<HelpMessageResponse> getAllMessages(Boolean fulfilled) {
        List<HelpMessage> messages = (fulfilled == null)
                ? helpMessageRepository.findAll()
                : helpMessageRepository.findByFulfilled(fulfilled);

        return messages.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<HelpMessageResponse> getMyMessages() {
        User currentUser = getCurrentUser();
        return helpMessageRepository.findBySender_Id(currentUser.getId()).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteById(Long id) {
        if (!helpMessageRepository.existsById(id)) {
            throw new RuntimeException("Mesaj bulunamadı");
        }
        helpMessageRepository.deleteById(id);
    }

    @Transactional
    public HelpMessageResponse updateMessage(Long id, HelpMessageUpdateRequest request) {
        HelpMessage message = helpMessageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mesaj bulunamadı"));

        if (request.getDescription() != null)
            message.setDescription(request.getDescription());
        if (request.getFulfilled() != null)
            message.setFulfilled(request.getFulfilled());
        if (request.getHealthType() != null)
            message.setHealthType(request.getHealthType());
        if (request.getVictimNames() != null)
            message.setVictimNames(request.getVictimNames());
        if (request.getEmergencyContactEmails() != null)
            message.setEmergencyContactEmails(request.getEmergencyContactEmails());
        if (request.getAidQuantities() != null)
            message.setAidQuantities(request.getAidQuantities());

        helpMessageRepository.save(message);
        return mapToResponse(message);
    }

    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));
    }

    private HelpMessageResponse mapToResponse(HelpMessage m) {
        return HelpMessageResponse.builder()
                .id(m.getId())
                .type(m.getType())
                .description(m.getDescription())
                .timestamp(m.getTimestamp())
                .city(m.getCity())
                .district(m.getDistrict())
                .neighborhood(m.getNeighborhood())
                .street(m.getStreet())
                .buildingNumber(m.getBuildingNumber())
                .personCount(m.getPersonCount())
                .foodNeeded(m.getFoodNeeded())
                .waterNeeded(m.getWaterNeeded())
                .shelterNeeded(m.getShelterNeeded())
                .healthNeeded(m.getHealthNeeded())
                .fulfilled(m.getFulfilled())
                .latitude(m.getLatitude())
                .longitude(m.getLongitude())
                .senderName(m.getSender().getName())
                .senderEmail(m.getSender().getEmail())
                .healthType(m.getHealthType())
                .victimNames(m.getVictimNames())
                .emergencyContactEmails(m.getEmergencyContactEmails())
                .aidQuantities(m.getAidQuantities())
                .build();
    }

    @Transactional
    public HelpMessageResponse updateFulfilled(Long id, HelpMessageFulfillRequest request) {
        HelpMessage message = helpMessageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mesaj bulunamadı"));

        message.setFulfilled(request.getFulfilled());
        helpMessageRepository.save(message);

        return mapToResponse(message);
    }
}
