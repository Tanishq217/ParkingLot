package org.example.computer;
import java.util.*;

public class ParkingLotFactory {

    public static ParkingManager createParkingLot(String configurationData) {
        ParkingManager manager = new ParkingManager();
        ParkingDataParser.ParkingConfig config = ParkingDataParser.parse(configurationData);

        // Create levels and slots
        for (int i = 1; i <= config.numberOfLevels; i++) {
            Level level = new Level(i);

            Map<SlotType, Integer> slotCounts = config.slotsByFloor.get(i);
            if (slotCounts != null) {
                int slotCounter = 1;
                for (Map.Entry<SlotType, Integer> entry : slotCounts.entrySet()) {
                    SlotType type = entry.getKey();
                    int count = entry.getValue();

                    for (int j = 1; j <= count; j++) {
                        String slotId = "L" + i + "_S" + slotCounter++;
                        Slot slot = new Slot(slotId, type, i, j);
                        level.addSlot(slot);
                    }
                }
            }

            manager.addLevel(level);
        }

        // Create entry gates and set distances
        for (int i = 1; i <= config.numberOfGates; i++) {
            String gateId = "G" + i;
            EntryGate gate = new EntryGate(gateId, i);

            // Set distances for this gate
            Map<String, Double> gateDistances = config.distances.get(gateId);
            if (gateDistances != null) {
                for (Map.Entry<String, Double> distanceEntry : gateDistances.entrySet()) {
                    gate.setDistanceToSlot(distanceEntry.getKey(), distanceEntry.getValue());
                }
            }

            manager.addEntryGate(gate);
        }

        return manager;
    }

    // For creating empty parking lot
    public static ParkingManager createEmptyParkingLot() {
        return new ParkingManager();
    }
}