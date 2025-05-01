package com.example.afetsunucu.service;

import com.example.afetsunucu.dto.GeocodingResult;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class LocationIqGeocodingService implements GeocodingService {

    private static final Logger logger = LoggerFactory.getLogger(LocationIqGeocodingService.class);

    @Value("${locationiq.api.key}")
    private String apiKey;

    private final WebClient webClient = WebClient.builder()
            .baseUrl("https://eu1.locationiq.com")
            .defaultHeader("User-Agent", "MyApp/1.0 (your@email.com)")
            .build();

    @Override
    public GeocodingResult geocode(String fullAddress) {
        try {
            logger.info("📍 LocationIQ geocoding istek yapılıyor: {}", fullAddress);

            JsonNode response = webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/v1/search")
                            .queryParam("key", apiKey)
                            .queryParam("q", fullAddress)
                            .queryParam("format", "json")
                            .build())
                    .retrieve()
                    .bodyToMono(JsonNode.class)
                    .block();

            if (!response.isArray() || response.size() == 0) {
                logger.warn("⚠️ LocationIQ sonucu bulunamadı: {}", fullAddress);
                throw new RuntimeException("No geocoding results for: " + fullAddress);
            }

            JsonNode bestMatch = response.get(0);

            double lat = bestMatch.path("lat").asDouble();
            double lon = bestMatch.path("lon").asDouble();

            logger.info("📌 Koordinatlar bulundu: lat={}, lon={}", lat, lon);

            return new GeocodingResult(lat, lon);

        } catch (Exception e) {
            logger.error("❌ LocationIQ geocoding hatası: {}", fullAddress, e);
            throw new RuntimeException("LocationIQ failed for address: " + fullAddress, e);
        }
    }

}
