package org.example.computer;

import java.time.Duration;
import java.time.LocalDateTime;

public class PricingServicec {

    public double calculateFee(SlotType slotType, LocalDateTime entryTime, LocalDateTime exitTime) {
        long hoursParked = Duration.between(entryTime, exitTime).toHours();

        if (hoursParked == 0) {
            hoursParked = 1;
        }

        double hourlyRate = slotType.getHourlyRate();
        return hoursParked * hourlyRate;
    }
}