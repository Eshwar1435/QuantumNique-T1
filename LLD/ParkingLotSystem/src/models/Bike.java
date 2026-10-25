package models;

public class Bike extends Vehicle {
    public Bike(String licenseNumber) {
        super(VehicleType.BIKE, licenseNumber);
    }

    public Bike(String licenseNumber, String vehicleType) {
        this(licenseNumber);
        validateType(vehicleType);
    }

    private void validateType(String vehicleType) {
        if (!VehicleType.BIKE.name().equalsIgnoreCase(vehicleType)) {
            throw new IllegalArgumentException("Invalid vehicle type for bike.");
        }
    }
}
