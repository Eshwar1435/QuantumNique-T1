package models;

public class Vehicle {
    private VehicleType vehicleType;
    private String licenseNumber;

    public Vehicle(VehicleType vehicleType, String licenseNumber) {
        if (vehicleType == null || licenseNumber == null || licenseNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Vehicle type and license number are required.");
        }
        this.vehicleType = vehicleType;
        this.licenseNumber = licenseNumber.trim();
    }

    public Vehicle(String vehicleType, String licenseNumber) {
        this(VehicleType.valueOf(vehicleType.trim().toUpperCase()), licenseNumber);
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }
}
