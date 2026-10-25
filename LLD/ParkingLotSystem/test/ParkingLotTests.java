import models.Bike;
import models.Car;
import models.ParkingFloor;
import models.ParkingLot;
import models.ParkingSlot;
import models.Ticket;
import models.Truck;
import models.VehicleType;
import services.DefaultFeeStrategy;
import services.FirstAvailableSlotStrategy;
import services.ParkingLotServices;

public class ParkingLotTests {
    private static ParkingLotServices service() {
        ParkingLot lot = new ParkingLot();
        ParkingFloor floor = new ParkingFloor(1);
        floor.addSlot(new ParkingSlot(1, VehicleType.BIKE));
        floor.addSlot(new ParkingSlot(2, VehicleType.CAR));
        floor.addSlot(new ParkingSlot(3, VehicleType.TRUCK));
        lot.addFloor(floor);
        return new ParkingLotServices(lot, new DefaultFeeStrategy(), new FirstAvailableSlotStrategy());
    }

    private static void check(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }

    public static void main(String[] args) {
        ParkingLotServices service = service();
        Ticket bike = service.parkVehicle(new Bike("B1"));
        check(bike != null, "bike parking");
        check(service.parkVehicle(new Car("C1")) != null, "car parking");
        check(service.parkVehicle(new Truck("T1")) != null, "truck parking");
        check(service.getAvailableSlots(VehicleType.BIKE).isEmpty(), "full bike slot");
        try { service.parkVehicle(new Bike("B1")); throw new AssertionError("duplicate"); }
        catch (IllegalStateException expected) { }
        try { service.exitVehicle(new Ticket(88, new Bike("X"), 1, 1, java.time.LocalDateTime.now())); throw new AssertionError("invalid ticket"); }
        catch (IllegalArgumentException expected) { }
        check(service.exitVehicle(bike) >= 20, "fee and exit");
        check(service.getAvailableSlots(VehicleType.BIKE).size() == 1, "slot release");
        try { service.exitVehicle(bike); throw new AssertionError("repeated exit"); }
        catch (IllegalArgumentException expected) { }
        System.out.println("10 tests passed.");
    }
}
