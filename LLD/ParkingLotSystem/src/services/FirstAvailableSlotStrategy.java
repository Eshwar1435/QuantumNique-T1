package services;

import models.ParkingFloor;
import models.ParkingLot;
import models.ParkingSlot;
import models.Vehicle;

public class FirstAvailableSlotStrategy implements SlotAllocationStrategy {
    public ParkingSlot findSlot(ParkingLot parkingLot, Vehicle vehicle) {
        for (ParkingFloor floor : parkingLot.getFloors()) {
            ParkingSlot slot = floor.findAvailableSlot(vehicle.getVehicleType());
            if (slot != null) return slot;
        }
        return null;
    }
}
