package com.example.afetsunucu.service;

import com.example.afetsunucu.dto.GeocodingResult;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class OpenCageGeocodingService implements GeocodingService {

    @Value("${opencage.api.key}")
    private String apiKey;

    private final WebClient webClient = WebClient.create("https://api.opencagedata.com");

    @Override
    public GeocodingResult geocode(String fullAddress) {
        String url = "/geocode/v1/json?q=" + URLEncoder.encode(fullAddress, StandardCharsets.UTF_8)
                + "&key=" + apiKey;

        JsonNode response = webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block();

        JsonNode location = response.path("results").get(0).path("geometry");

        double lat = location.path("lat").asDouble();
        double lng = location.path("lng").asDouble();

        return new GeocodingResult(lat, lng);
    }
}
