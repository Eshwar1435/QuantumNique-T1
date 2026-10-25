package models;

public class Truck extends Vehicle {
    public Truck(String licenseNumber) {
        super(VehicleType.TRUCK, licenseNumber);
    }

    public Truck(String licenseNumber, String vehicleType) {
        this(licenseNumber);
        if (!VehicleType.TRUCK.name().equalsIgnoreCase(vehicleType)) {
            throw new IllegalArgumentException("Invalid vehicle type for truck.");
        }
    }
}
