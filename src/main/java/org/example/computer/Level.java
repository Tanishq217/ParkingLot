package org.example.computer;
import java.util.*;
import java.util.stream.Collectors;

public class Level {

    private final int levelNumber;

    private final Map<String, Slot> slots;

    private final Map<SlotType, List<Slot>> slotsByType;

    public Level(int levelNumber) {

        this.levelNumber = levelNumber;

        this.slots = new HashMap<>();
        this.slotsByType = new HashMap<>();

        for (SlotType type : SlotType.values()) {

            slotsByType.put(type, new ArrayList<>());
        }

    }

    public void addSlot(Slot slot) {

        slots.put(slot.getSlotId(), slot);

        slotsByType.get(slot.getType()).add(slot);
    }

    public Slot findAvailableSlot(SlotType requiredType) {
        return slotsByType.get(requiredType).stream()
                .filter(Slot::isAvailable)
                .findFirst()
                .orElse(null);
    }

    public int getAvailableSlotsCount(SlotType type) {
        return (int) slotsByType.get(type).stream()
                .filter(Slot::isAvailable)
                .count();
    }

    public Map<SlotType, Integer> getStatus() {
        Map<SlotType, Integer> status = new HashMap<>();
        for (SlotType type : SlotType.values()) {
            status.put(type, getAvailableSlotsCount(type));
        }
        return status;
    }

    public int getLevelNumber() { return levelNumber; }
    public Collection<Slot> getAllSlots() { return slots.values(); }
}
