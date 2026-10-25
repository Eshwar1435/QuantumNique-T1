package services;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import models.Ticket;
import models.VehicleType;

public class DefaultFeeStrategy implements FeeStrategy {
    private static final Map<VehicleType, Integer> HOURLY_RATES = new HashMap<>();

    static {
        registerRate(VehicleType.BIKE, 20);
        registerRate(VehicleType.CAR, 40);
        registerRate(VehicleType.TRUCK, 60);
    }

    public static void registerRate(VehicleType type, int hourlyRate) {
        if (type == null || hourlyRate < 0) {
            throw new IllegalArgumentException("Vehicle type and valid hourly rate are required.");
        }
        HOURLY_RATES.put(type, hourlyRate);
    }

    public double calculateFee(Ticket ticket) {
        if (ticket == null || ticket.getEntryTime() == null) {
            throw new IllegalArgumentException("Ticket is required.");
        }
        long minutes = Math.max(0, Duration.between(ticket.getEntryTime(), LocalDateTime.now()).toMinutes());
        long hours = Math.max(1, (minutes + 59) / 60);
        Integer hourlyRate = HOURLY_RATES.get(ticket.getVehicle().getVehicleType());
        if (hourlyRate == null) throw new IllegalArgumentException("Unsupported vehicle type.");
        return hours * hourlyRate;
    }
}
