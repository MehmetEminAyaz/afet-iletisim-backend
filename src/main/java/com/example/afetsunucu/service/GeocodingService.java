package com.example.afetsunucu.service;

import com.example.afetsunucu.dto.GeocodingResult;

public interface GeocodingService {
    GeocodingResult geocode(String fullAddress);
}
