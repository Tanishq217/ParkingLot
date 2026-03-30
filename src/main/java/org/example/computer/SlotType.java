package org.example.computer;

public enum SlotType {

    SMALL(20.0),
    // $20 per hour for bikes
    MEDIUM(50.0),
    // $50 per hour for cars
    LARGE(100.0);
    // $100 per hour for buses

    private final double hourlyRate;

    SlotType(double hourlyRate) {

        this.hourlyRate = hourlyRate;

    }

    public double getHourlyRate() {

        return hourlyRate;

    }


    public static SlotType fromVehicleType(VehicleType vehicleType) {

        switch (vehicleType) {

            case BIKE:
                return SMALL;

            case CAR:

                return MEDIUM;
            case BUS:
                return LARGE;
            default:

                throw new IllegalArgumentException("Unknown vehicle type");
        }
    }
}
