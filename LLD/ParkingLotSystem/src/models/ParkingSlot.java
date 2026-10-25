package models;

public class ParkingSlot {
    private int slotNumber;
    private Vehicle vehicle;
    private VehicleType vehicleType;

    public ParkingSlot(int slotNumber, VehicleType vehicleType) {
        if (slotNumber <= 0 || vehicleType == null) {
            throw new IllegalArgumentException("Slot number and vehicle type are required.");
        }
        this.slotNumber = slotNumber;
        this.vehicleType = vehicleType;
    }

    public ParkingSlot(int slotNumber, String vehicleType) {
        this(slotNumber, VehicleType.valueOf(vehicleType.trim().toUpperCase()));
    }

    public int getSlotNumber() { return slotNumber; }
    public Vehicle getVehicle() { return vehicle; }
    public VehicleType getVehicleType() { return vehicleType; }
    public boolean isOccupied() { return vehicle != null; }
    public boolean isAvailable() { return !isOccupied(); }

    public boolean canPark(Vehicle vehicle) {
        return vehicle != null && isAvailable() && vehicle.getVehicleType() == vehicleType;
    }

    public void parkVehicle(Vehicle vehicle) {
        if (!canPark(vehicle)) {
            throw new IllegalStateException("Parking slot is occupied or unsuitable for the vehicle.");
        }
        this.vehicle = vehicle;
    }

    public Vehicle removeVehicle() {
        if (isAvailable()) {
            throw new IllegalStateException("Parking slot is already empty.");
        }
        Vehicle removedVehicle = vehicle;
        vehicle = null;
        return removedVehicle;
    }

    public void exitVehicle() { removeVehicle(); }
}
