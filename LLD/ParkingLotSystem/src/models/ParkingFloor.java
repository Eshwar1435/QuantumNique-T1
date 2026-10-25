package models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ParkingFloor {
    private int floorNumber;
    private List<ParkingSlot> parkingSlots;

    public ParkingFloor(int floorNumber) {
        if (floorNumber <= 0) {
            throw new IllegalArgumentException("Floor number must be positive.");
        }
        this.floorNumber = floorNumber;
        this.parkingSlots = new ArrayList<>();
    }

    public int getFloorNumber() { return floorNumber; }

    public void addSlot(ParkingSlot slot) {
        if (slot == null) {
            throw new IllegalArgumentException("Slot cannot be null.");
        }
        for (ParkingSlot existing : parkingSlots) {
            if (existing.getSlotNumber() == slot.getSlotNumber()) {
                throw new IllegalArgumentException("Duplicate slot number on floor.");
            }
        }
        parkingSlots.add(slot);
    }

    public List<ParkingSlot> getParkingSlots() {
        return Collections.unmodifiableList(parkingSlots);
    }

    public ParkingSlot findAvailableSlot(VehicleType vehicleType) {
        for (ParkingSlot slot : parkingSlots) {
            if (slot.getVehicleType() == vehicleType && slot.isAvailable()) {
                return slot;
            }
        }
        return null;
    }

    public List<ParkingSlot> getAvailableSlots(VehicleType vehicleType) {
        List<ParkingSlot> availableSlots = new ArrayList<>();
        for (ParkingSlot slot : parkingSlots) {
            if (slot.getVehicleType() == vehicleType && slot.isAvailable()) {
                availableSlots.add(slot);
            }
        }
        return availableSlots;
    }
}
