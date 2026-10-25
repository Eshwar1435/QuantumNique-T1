package models;

import java.time.LocalDateTime;

public class Ticket {
    public enum Status { ACTIVE, COMPLETED }

    private int ticketId;
    private Vehicle vehicle;
    private int slotNumber;
    private int floorNumber;
    private LocalDateTime entryTime;
    private Status status;

    public Ticket(int ticketId, Vehicle vehicle, int slotNumber, int floorNumber, LocalDateTime entryTime) {
        this.ticketId = ticketId;
        this.vehicle = vehicle;
        this.slotNumber = slotNumber;
        this.floorNumber = floorNumber;
        this.entryTime = entryTime;
        this.status = Status.ACTIVE;
    }

    public int getTicketId() { return ticketId; }
    public Vehicle getVehicle() { return vehicle; }
    public int getSlotNumber() { return slotNumber; }
    public int getFloorNumber() { return floorNumber; }
    public LocalDateTime getEntryTime() { return entryTime; }
    public Status getStatus() { return status; }
    public void complete() { status = Status.COMPLETED; }
}
