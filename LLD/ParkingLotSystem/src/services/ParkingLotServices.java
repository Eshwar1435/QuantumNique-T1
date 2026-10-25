package services;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import models.ParkingFloor;
import models.ParkingLot;
import models.ParkingSlot;
import models.Ticket;
import models.Vehicle;

public class ParkingLotServices {
    private ParkingLot parkingLot;
    private FeeStrategy feeStrategy;
    private SlotAllocationStrategy allocationStrategy;
    private Map<Integer, Ticket> activeTickets = new HashMap<>();
    private Map<String, Vehicle> activeVehicles = new HashMap<>();
    private int ticketCounter = 1;

    public ParkingLotServices() {
        this(createDefaultLot(), new DefaultFeeStrategy(), new FirstAvailableSlotStrategy());
    }

    public ParkingLotServices(ParkingLot parkingLot, FeeStrategy feeStrategy,
            SlotAllocationStrategy allocationStrategy) {
        if (parkingLot == null || feeStrategy == null || allocationStrategy == null) {
            throw new IllegalArgumentException("Parking lot and strategies are required.");
        }
        this.parkingLot = parkingLot;
        this.feeStrategy = feeStrategy;
        this.allocationStrategy = allocationStrategy;
    }

    private static ParkingLot createDefaultLot() {
        ParkingLot lot = new ParkingLot();
        for (int floorNumber = 1; floorNumber <= 3; floorNumber++) {
            ParkingFloor floor = new ParkingFloor(floorNumber);
            floor.addSlot(new ParkingSlot(1, models.VehicleType.BIKE));
            floor.addSlot(new ParkingSlot(2, models.VehicleType.BIKE));
            floor.addSlot(new ParkingSlot(3, models.VehicleType.CAR));
            floor.addSlot(new ParkingSlot(4, models.VehicleType.CAR));
            floor.addSlot(new ParkingSlot(5, models.VehicleType.TRUCK));
            floor.addSlot(new ParkingSlot(6, models.VehicleType.TRUCK));
            lot.addFloor(floor);
        }
        return lot;
    }

    public Ticket parkVehicle(Vehicle vehicle) {
        if (vehicle == null) throw new IllegalArgumentException("Vehicle cannot be null.");
        if (activeVehicles.containsKey(vehicle.getLicenseNumber())) {
            throw new IllegalStateException("Vehicle is already parked.");
        }
        ParkingSlot slot = allocationStrategy.findSlot(parkingLot, vehicle);
        if (slot == null) throw new IllegalStateException("No suitable parking slot is available.");
        slot.parkVehicle(vehicle);
        int floorNumber = findFloorNumber(slot);
        Ticket ticket = new Ticket(ticketCounter++, vehicle, slot.getSlotNumber(), floorNumber, LocalDateTime.now());
        activeTickets.put(ticket.getTicketId(), ticket);
        activeVehicles.put(vehicle.getLicenseNumber(), vehicle);
        return ticket;
    }

    private int findFloorNumber(ParkingSlot target) {
        for (ParkingFloor floor : parkingLot.getFloors()) {
            if (floor.getParkingSlots().contains(target)) return floor.getFloorNumber();
        }
        throw new IllegalStateException("Allocated slot does not belong to the parking lot.");
    }

    public double exitVehicle(Ticket ticket) {
        if (ticket == null) throw new IllegalArgumentException("Ticket cannot be null.");
        if (activeTickets.get(ticket.getTicketId()) != ticket || ticket.getStatus() != Ticket.Status.ACTIVE) {
            throw new IllegalArgumentException("Invalid or already completed ticket.");
        }
        ParkingFloor floor = parkingLot.findFloor(ticket.getFloorNumber());
        if (floor == null) throw new IllegalArgumentException("Ticket refers to an invalid floor.");
        ParkingSlot slot = null;
        for (ParkingSlot candidate : floor.getParkingSlots()) {
            if (candidate.getSlotNumber() == ticket.getSlotNumber()) slot = candidate;
        }
        if (slot == null || slot.getVehicle() != ticket.getVehicle()) {
            throw new IllegalArgumentException("Ticket does not match the parked vehicle.");
        }
        double fee = feeStrategy.calculateFee(ticket);
        slot.removeVehicle();
        ticket.complete();
        activeTickets.remove(ticket.getTicketId());
        activeVehicles.remove(ticket.getVehicle().getLicenseNumber());
        return fee;
    }

    public void displayStatus() { parkingLot.displayOccupancy(); }
    public java.util.List<ParkingSlot> getAvailableSlots(models.VehicleType type) {
        return parkingLot.getAvailableSlots(type);
    }
    public double calculateFee(Ticket ticket) { return feeStrategy.calculateFee(ticket); }
    public ParkingLot getParkingLot() { return parkingLot; }
}
