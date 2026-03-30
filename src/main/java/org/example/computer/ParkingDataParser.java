package org.example.computer;

import java.util.*;

public class ParkingDataParser {

    public static ParkingConfig parse(String data) {
        ParkingConfig config = new ParkingConfig();

        String[] sections = data.split("\\|");

        for (String section : sections) {
            String[] keyValue = section.split(":", 2);
            if (keyValue.length < 2) continue;

            String key = keyValue[0];
            String value = keyValue[1];

            switch (key) {
                case "levels":
                    config.numberOfLevels = Integer.parseInt(value);
                    break;
                case "slots":
                    parseSlots(value, config);
                    break;
                case "gates":
                    config.numberOfGates = Integer.parseInt(value);
                    break;
                case "distances":
                    parseDistances(value, config);
                    break;
            }
        }

        return config;
    }

    private static void parseSlots(String slotsData, ParkingConfig config) {
        String[] floorSlots = slotsData.split("\\|");
        config.slotsByFloor = new HashMap<>();

        for (String floor : floorSlots) {
            String[] parts = floor.split(":", 2);
            int floorNumber = Integer.parseInt(parts[0]);
            String[] slotConfigs = parts[1].split(",");

            Map<SlotType, Integer> slotCounts = new HashMap<>();
            for (String slotConfig : slotConfigs) {
                String[] typeCount = slotConfig.split(":");
                SlotType type = SlotType.valueOf(typeCount[0]);
                int count = Integer.parseInt(typeCount[1]);
                slotCounts.put(type, count);
            }
            config.slotsByFloor.put(floorNumber, slotCounts);
        }
    }

    private static void parseDistances(String distancesData, ParkingConfig config) {
        config.distances = new HashMap<>();
        String[] gateDistances = distancesData.split("\\|");

        for (String gateDistance : gateDistances) {
            String[] parts = gateDistance.split(":", 2);
            String gateId = parts[0];
            String[] slotDistances = parts[1].split(",");

            Map<String, Double> distanceMap = new HashMap<>();
            for (String sd : slotDistances) {
                String[] slotDist = sd.split(":");
                distanceMap.put(slotDist[0], Double.parseDouble(slotDist[1]));
            }
            config.distances.put(gateId, distanceMap);
        }
    }

    public static class ParkingConfig {
        int numberOfLevels;
        int numberOfGates;
        Map<Integer, Map<SlotType, Integer>> slotsByFloor;
        Map<String, Map<String, Double>> distances;
    }
}