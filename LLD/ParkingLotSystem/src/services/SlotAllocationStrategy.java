package services;

import models.ParkingLot;
import models.ParkingSlot;
import models.Vehicle;

public interface SlotAllocationStrategy {
    ParkingSlot findSlot(ParkingLot parkingLot, Vehicle vehicle);
}
