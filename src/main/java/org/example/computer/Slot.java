package org.example.computer;

public class Slot {
    private final String slotId;
    private final SlotType type;
    private final int levelNumber;
    private final int slotNumber;
    private boolean isAvailable;
    private Vehicle parkedVehicle;

    public Slot(String slotId, SlotType type, int levelNumber, int slotNumber) {
        this.slotId = slotId;
        this.type = type;
        this.levelNumber = levelNumber;
        this.slotNumber = slotNumber;
        this.isAvailable = true;
        this.parkedVehicle = null;
    }

    public boolean park(Vehicle vehicle) {
        if (!isAvailable) {
            return false;
        }
        this.parkedVehicle = vehicle;
        this.isAvailable = false;
        return true;
    }

    public Vehicle unpark() {
        if (isAvailable) {
            return null;
        }
        Vehicle vehicle = this.parkedVehicle;
        this.parkedVehicle = null;
        this.isAvailable = true;
        return vehicle;
    }

    // Getters
    public String getSlotId() { return slotId; }
    public SlotType getType() { return type; }
    public int getLevelNumber() { return levelNumber; }
    public int getSlotNumber() { return slotNumber; }
    public boolean isAvailable() { return isAvailable; }
    public Vehicle getParkedVehicle() { return parkedVehicle; }

    @Override
    public String toString() {
        return "Slot{" +
                "slotId='" + slotId + '\'' +
                ", type=" + type +
                ", level=" + levelNumber +
                ", slot=" + slotNumber +
                ", available=" + isAvailable +
                '}';
    }
}