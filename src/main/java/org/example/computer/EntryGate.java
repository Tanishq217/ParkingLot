package org.example.computer;


import java.util.HashMap;
import java.util.Map;

public class EntryGate {

    private final String gateId;

    private final int gateNumber;

    private final Map<String, Double> distanceToSlots; // slotId -> distance

    public EntryGate(String gateId, int gateNumber) {

        this.gateId = gateId;

        this.gateNumber = gateNumber;


        this.distanceToSlots = new HashMap<>();
    }


    public void setDistanceToSlot(String slotId, double distance) {

        distanceToSlots.put(slotId, distance);

    }


    public double getDistanceToSlot(String slotId) {

        return distanceToSlots.getOrDefault(slotId, Double.MAX_VALUE);
    }


    public String getGateId() {
        return gateId;
    }
    public int getGateNumber() {
        return gateNumber;
    }

    @Override
    public String toString() {
        return "EntryGate{" +
                "gateId='" + gateId + '\'' +
                ", gateNumber=" + gateNumber +
                '}';
    }

}
