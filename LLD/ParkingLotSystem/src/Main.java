import models.Bike;
import models.Car;
import models.Ticket;
import models.Truck;
import services.ParkingLotServices;

public class Main {
    public static void main(String[] args) {
        ParkingLotServices pls = new ParkingLotServices();

        Bike b1 = new Bike("TN-01-AK-0007", "BIKE");
        Bike b2 = new Bike("TN-04-JV-0001", "BIKE");
        Bike b3 = new Bike("TN-07-SR-0003", "BIKE");

        Car c1 = new Car("TS-03-AA-0001", "CAR");
        Car c2 = new Car("TS-07-PK-0002", "CAR");
        Car c3 = new Car("TS-12-NN-0003", "CAR");

        Truck t1 = new Truck("AP-01-AB-1234", "TRUCK");
        Truck t2 = new Truck("AP-02-CD-5678", "TRUCK");
        Truck t3 = new Truck("AP-03-EF-8901", "TRUCK");

        Ticket b1t = pls.parkVehicle(b1);
        pls.parkVehicle(b2);
        pls.parkVehicle(b3);

        pls.parkVehicle(c1);
        pls.parkVehicle(c2);
        pls.parkVehicle(c3);

        pls.parkVehicle(t1);
        pls.parkVehicle(t2);
        pls.parkVehicle(t3);

        pls.displayStatus();

        pls.exitVehicle(b1t);

        pls.displayStatus();
    }
}
