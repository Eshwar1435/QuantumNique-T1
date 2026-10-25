package models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ParkingLot {
    private List<ParkingFloor> floors = new ArrayList<>();

    public void addFloor(ParkingFloor floor) {
        if (floor == null) {
            throw new IllegalArgumentException("Floor cannot be null.");
        }
        if (findFloor(floor.getFloorNumber()) != null) {
            throw new IllegalArgumentException("Duplicate floor number.");
        }
        floors.add(floor);
    }

    public List<ParkingFloor> getFloors() {
        return Collections.unmodifiableList(floors);
    }

    public ParkingFloor findFloor(int floorNumber) {
        for (ParkingFloor floor : floors) {
            if (floor.getFloorNumber() == floorNumber) return floor;
        }
        return null;
    }

    public List<ParkingSlot> getAvailableSlots(VehicleType vehicleType) {
        List<ParkingSlot> slots = new ArrayList<>();
        for (ParkingFloor floor : floors) slots.addAll(floor.getAvailableSlots(vehicleType));
        return slots;
    }

    public void displayOccupancy() {
        for (ParkingFloor floor : floors) {
            System.out.println("Floor " + floor.getFloorNumber());
            for (ParkingSlot slot : floor.getParkingSlots()) {
                String status = slot.isOccupied() ? "Occupied" : "Available";
                System.out.println("Slot " + slot.getSlotNumber() + " - " + slot.getVehicleType() + " - " + status);
            }
        }
    }
}
