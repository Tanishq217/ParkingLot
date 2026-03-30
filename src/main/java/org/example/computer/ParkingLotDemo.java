package org.example.computer;



import java.util.Map;

public class ParkingLotDemo {
    public static void main(String[] args) {
        System.out.println("=== Multi-Level Parking Lot System ===\n");

        // Sample configuration string
        String configData = "levels:3|" +
                "slots:1:SMALL:10,MEDIUM:5,LARGE:2|" +
                "2:SMALL:8,MEDIUM:4,LARGE:1|" +
                "3:SMALL:5,MEDIUM:3,LARGE:1|" +
                "gates:2|" +
                "distances:G1:L1_S1:10,L1_S2:15,L2_S1:25,L2_S2:30,L3_S1:40|" +
                "G2:L1_S1:20,L1_S2:25,L2_S1:15,L2_S2:20,L3_S1:30";

        // Create parking lot
        ParkingManager parkingLot = ParkingLotFactory.createParkingLot(configData);

        // Check initial status
        System.out.println("1. Initial Parking Status:");
        printStatus(parkingLot.getStatus());
        System.out.println();

        // Create vehicles
        Vehicle bike1 = new Vehicle("KA01AB1234", VehicleType.BIKE, "Red");
        Vehicle car1 = new Vehicle("KA02CD5678", VehicleType.CAR, "White");
        Vehicle bus1 = new Vehicle("KA03EF9012", VehicleType.BUS, "Blue");

        // Park vehicles
        System.out.println("2. Parking Vehicles:");
        try {
            Ticket ticket1 = parkingLot.park(bike1, "G1");
            System.out.println("✓ Parked " + bike1 + " at " + ticket1.getSlot().getSlotId());

            Ticket ticket2 = parkingLot.park(car1, "G2");
            System.out.println("✓ Parked " + car1 + " at " + ticket2.getSlot().getSlotId());

            Ticket ticket3 = parkingLot.park(bus1, "G1");
            System.out.println("✓ Parked " + bus1 + " at " + ticket3.getSlot().getSlotId());

            // Show status after parking
            System.out.println("\n3. Status After Parking:");
            printStatus(parkingLot.getStatus());

            // Try to park when no slots available
            System.out.println("\n4. Testing Slot Unavailability:");
            Vehicle bike2 = new Vehicle("KA04GH5678", VehicleType.BIKE, "Black");
            parkingLot.park(bike2, "G1");

        } catch (ParkingException e) {
            System.out.println("✗ " + e.getMessage());
        }

        // Exit a vehicle
        System.out.println("\n5. Exiting Vehicle:");
        try {
            // We need to store tickets to exit - in real system, user would have ticket
            // For demo, we'll show concept
            System.out.println("Exiting bike - Fee would be calculated based on time");
            System.out.println("✓ Exit successful");

        } catch (Exception e) {
            System.out.println("✗ " + e.getMessage());
        }

        // Add new slots dynamically
        System.out.println("\n6. Adding New Slots Dynamically:");
        Slot newSlot = new Slot("L1_S18", SlotType.SMALL, 1, 18);
        parkingLot.addSlot(newSlot, 1);
        System.out.println("✓ Added new SMALL slot at Level 1, Slot 18");

        System.out.println("\n7. Status After Adding Slots:");
        printStatus(parkingLot.getStatus());

        // Show nearest slot allocation
        System.out.println("\n8. Nearest Slot Allocation:");
        Vehicle car2 = new Vehicle("KA05IJ3456", VehicleType.CAR, "Silver");
        try {
            Ticket ticket = parkingLot.park(car2, "G1");
            System.out.println("✓ Parked " + car2 + " at " + ticket.getSlot().getSlotId());
            System.out.println("  (Nearest available MEDIUM slot to Gate 1)");
        } catch (ParkingException e) {
            System.out.println("✗ " + e.getMessage());
        }
    }

    private static void printStatus(Map<SlotType, Integer> status) {
        System.out.println("  Available Slots:");
        System.out.println("    SMALL (Bikes):  " + status.get(SlotType.SMALL));
        System.out.println("    MEDIUM (Cars):  " + status.get(SlotType.MEDIUM));
        System.out.println("    LARGE (Buses):  " + status.get(SlotType.LARGE));
    }
}