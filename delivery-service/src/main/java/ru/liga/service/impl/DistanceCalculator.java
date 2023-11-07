package ru.liga.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
public class DistanceCalculator {
    @AllArgsConstructor
    static class Coordinates {
        private double latitude;
        private double longitude;
    }

    private final static double EARTH_RADIUS = 6371.0;

    public double calculateDistance(String firstAddress, String secondAddress) {

        Coordinates firstCoordinates = convertToCoordinates(firstAddress);
        Coordinates secondCoordinates = convertToCoordinates(secondAddress);

        double diffLatitude = Math.toRadians(firstCoordinates.latitude - secondCoordinates.latitude);
        double diffLongitude = Math.toRadians(firstCoordinates.longitude - secondCoordinates.longitude);

        double haversine = Math.sin(diffLatitude / 2) * Math.sin(diffLatitude / 2)
                + Math.cos(firstCoordinates.latitude)
                * Math.cos(secondCoordinates.latitude)
                * Math.sin(diffLongitude / 2)
                * Math.sin(diffLongitude / 2);

        double haversineFormula = 2 * Math.atan2(Math.sqrt(haversine), Math.sqrt(1 - haversine));

        return EARTH_RADIUS * haversineFormula;
    }

    private Coordinates convertToCoordinates(String address) {
        String[] parts = address.split(",");

        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid coordinate string format");
        }

        double latitude = Double.parseDouble(parts[0]);
        double longitude = Double.parseDouble(parts[1]);

        return new Coordinates(latitude, longitude);
    }
}
