package models;

public class Car extends Vehicle {
    public Car(String licenseNumber) {
        super(VehicleType.CAR, licenseNumber);
    }

    public Car(String licenseNumber, String vehicleType) {
        this(licenseNumber);
        if (!VehicleType.CAR.name().equalsIgnoreCase(vehicleType)) {
            throw new IllegalArgumentException("Invalid vehicle type for car.");
        }
    }
}
