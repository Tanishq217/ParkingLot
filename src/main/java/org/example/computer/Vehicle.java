package org.example.computer;


public class Vehicle {

    private final String registrationNumber;

    private final VehicleType type;

    private final String color;


    public Vehicle(String registrationNumber, VehicleType type, String color) {

        this.registrationNumber = registrationNumber;
        this.type = type;
        this.color = color;

    }

    public String getRegistrationNumber() {

        return registrationNumber;
    }

    public VehicleType getType() {

        return type;
    }

    public String getColor() {
        return color;

    }


    public String toString() {
        return type + " - " + registrationNumber + " (" + color + ")";
    }
}