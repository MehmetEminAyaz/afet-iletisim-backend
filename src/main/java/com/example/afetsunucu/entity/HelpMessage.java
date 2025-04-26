package com.example.afetsunucu.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "help_messages")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HelpMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private MessageType type; // ARAMA, SAGLIK, MALZEME, HAYATTAYIM

    private String description;  // Genel açıklama
    private LocalDateTime timestamp;

    // Adres
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

    private Boolean fulfilled = false;

    private Double latitude;
    private Double longitude;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User sender;


    private String healthType; // Örn: "heartAttack", "minorInjury"

    @ElementCollection
    private List<String> victimNames; // Arama mesajı için isimler

    @ElementCollection
    private List<String> emergencyContactEmails; // HAYATTAYIM mesajı için

    @ElementCollection
    @CollectionTable(name = "help_message_aid_quantities", joinColumns = @JoinColumn(name = "message_id"))
    @MapKeyColumn(name = "aid_type")
    @Column(name = "quantity")
    private java.util.Map<String, Integer> aidQuantities; // Örn: "food": 3, "shelter": 2
}
