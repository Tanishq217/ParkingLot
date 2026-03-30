package org.example.computer;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class ParkingManager {
    private final List<Level> levels;
    private final Map<String, EntryGate> entryGates;
    private final Map<String, Ticket> activeTickets;
    private final PricingServicec pricingServicec;
    private final AtomicInteger ticketIdCounter;

    public ParkingManager() {
        this.levels = new ArrayList<>();
        this.entryGates = new HashMap<>();
        this.activeTickets = new ConcurrentHashMap<>();
        this.pricingServicec = new PricingServicec();
        this.ticketIdCounter = new AtomicInteger(1);
    }

    public void addLevel(Level level) {
        levels.add(level);
    }

    public void addEntryGate(EntryGate gate) {
        entryGates.put(gate.getGateId(), gate);
    }

    public Ticket park(Vehicle vehicle, String entryGateId) {
        // Validate entry gate exists
        EntryGate entryGate = entryGates.get(entryGateId);
        if (entryGate == null) {
            throw new ParkingException("Invalid entry gate: " + entryGateId);
        }

        // Get required slot type for vehicle
        SlotType requiredType = SlotType.fromVehicleType(vehicle.getType());

        // Find nearest available slot
        Slot nearestSlot = findNearestAvailableSlot(requiredType, entryGate);

        if (nearestSlot == null) {
            throw new ParkingException("No available slot for vehicle type: " + vehicle.getType());
        }

        // Park the vehicle
        boolean parked = nearestSlot.park(vehicle);
        if (!parked) {
            throw new ParkingException("Failed to park vehicle in slot: " + nearestSlot.getSlotId());
        }

        // Create and return ticket
        String ticketId = generateTicketId();
        Ticket ticket = new Ticket(ticketId, vehicle, nearestSlot, entryGate);
        activeTickets.put(ticketId, ticket);

        return ticket;
    }

    public double exit(Ticket ticket) {
        // Validate ticket exists
        if (!activeTickets.containsKey(ticket.getTicketId())) {
            throw new ParkingException("Invalid ticket: " + ticket.getTicketId());
        }

        Slot slot = ticket.getSlot();

        // Unpark vehicle
        Vehicle vehicle = slot.unpark();
        if (vehicle == null) {
            throw new ParkingException("No vehicle found in slot: " + slot.getSlotId());
        }

        // Calculate fee
        double fee = pricingServicec.calculateFee(
                slot.getType(),
                ticket.getEntryTime(),
                LocalDateTime.now()
        );

        // Remove active ticket
        activeTickets.remove(ticket.getTicketId());

        return fee;
    }

    public Map<SlotType, Integer> getStatus() {
        Map<SlotType, Integer> totalStatus = new HashMap<>();
        for (SlotType type : SlotType.values()) {
            totalStatus.put(type, 0);
        }

        for (Level level : levels) {
            Map<SlotType, Integer> levelStatus = level.getStatus();
            for (Map.Entry<SlotType, Integer> entry : levelStatus.entrySet()) {
                totalStatus.put(entry.getKey(),
                        totalStatus.get(entry.getKey()) + entry.getValue());
            }
        }

        return totalStatus;
    }

    private Slot findNearestAvailableSlot(SlotType requiredType, EntryGate entryGate) {
        List<Slot> availableSlots = new ArrayList<>();

        // Collect all available slots of required type
        for (Level level : levels) {
            Slot slot = level.findAvailableSlot(requiredType);
            if (slot != null) {
                availableSlots.add(slot);
            }
        }

        if (availableSlots.isEmpty()) {
            return null;
        }

        // Find slot with minimum distance from entry gate
        return availableSlots.stream()
                .min(Comparator.comparingDouble(slot ->
                        entryGate.getDistanceToSlot(slot.getSlotId())))
                .orElse(null);
    }

    private String generateTicketId() {
        return "TKT" + System.currentTimeMillis() + ticketIdCounter.getAndIncrement();
    }

    // For adding slots dynamically
    public void addSlot(Slot slot, int levelNumber) {
        Level level = levels.stream()
                .filter(l -> l.getLevelNumber() == levelNumber)
                .findFirst()
                .orElseThrow(() -> new ParkingException("Level not found: " + levelNumber));
        level.addSlot(slot);
    }
}