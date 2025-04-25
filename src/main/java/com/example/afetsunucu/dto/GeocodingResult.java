package com.example.afetsunucu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeocodingResult {
    private double latitude;
    private double longitude;
}
