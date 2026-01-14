import java.util.*;

abstract class Vehicle {
    private String vehicleName;
    private double distance;
    private double fuelUsed;

    public Vehicle(String name, double dist, double fuel) {
        this.vehicleName = name;
        this.distance = dist;
        this.fuelUsed = fuel;
    }

    public String getVehicleName() { return vehicleName; }
    public double getDistance() { return distance; }
    public double getFuelUsed() { return fuelUsed; }

    public double calculateMileage() {
        return distance / fuelUsed;
    }

    public abstract double calculateEmission(); // Abstract - depends on fuel type

    public void displayDetails() {
        double mileage = calculateMileage();
        System.out.println("\n--- Vehicle Efficiency Report ---");
        System.out.println("Vehicle Name: " + vehicleName);
        System.out.println("Distance Travelled: " + distance + " km");
        System.out.println("Fuel Used: " + fuelUsed + " liters");
        System.out.printf("Mileage: %.2f km/l\n", mileage);
        System.out.printf("CO₂ Emission: %.2f kg\n", calculateEmission());
        if (mileage > 20)
            System.out.println("Efficiency: Excellent");
        else if (mileage >= 15)
            System.out.println("Efficiency: Good");
        else
            System.out.println("Efficiency: Needs Improvement");
            System.out.printf("EcoScore: %.2f / 100%n", calculateEcoScore());
            System.out.println("Remark: " + getEcoRemark());

    }
    public double calculateEcoScore() {
    double mileage = calculateMileage();
    double emission = calculateEmission();

    // Simple eco logic: high mileage & low emission = better score
    double mileageScore = Math.min(60, mileage * 3);
    double emissionPenalty = Math.min(40, emission);
    double ecoScore = mileageScore - emissionPenalty;

    // Clamp to 0–100
    if (ecoScore < 0) ecoScore = 0;
    if (ecoScore > 100) ecoScore = 100;
    return ecoScore;
}

public String getEcoRemark() {
    double score = calculateEcoScore();
    if (score >= 75)
        return "Excellent – Very Eco-Friendly 🚗💨";
    else if (score >= 50)
        return "Good – Efficient Performance ✅";
    else if (score >= 30)
        return "Moderate – Can Improve ♻️";
    else
        return "Poor – High Emission, Low Efficiency ⚠️";
}

}

class PetrolVehicle extends Vehicle {
    public PetrolVehicle(String name, double dist, double fuel) {
        super(name, dist, fuel);
    }
    public double calculateEmission() {
        // 1 liter petrol ≈ 2.31 kg CO2
        return getFuelUsed() * 2.31;
    }
}

class DieselVehicle extends Vehicle {
    public DieselVehicle(String name, double dist, double fuel) {
        super(name, dist, fuel);
    }
    public double calculateEmission() {
        // 1 liter diesel ≈ 2.68 kg CO2
        return getFuelUsed() * 2.68;
    }
}

class ElectricVehicle extends Vehicle {
    public ElectricVehicle(String name, double dist, double fuel) {
        super(name, dist, fuel);
    }
    public double calculateEmission() {
        // Assume negligible direct CO2 emission
        return 0.0;
    }
}

public class FuelTracker {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Vehicle> vehicles = new ArrayList<>();

        System.out.print("Enter number of vehicles to track: ");
        int n = sc.nextInt();
        sc.nextLine();

        for (int i = 0; i < n; i++) {
            System.out.println("\n--- Vehicle " + (i + 1) + " ---");
            System.out.print("Enter Vehicle Name: ");
            String name = sc.nextLine();
            System.out.print("Enter Vehicle Type (Petrol/Diesel/Electric): ");
            String type = sc.nextLine().toLowerCase();
            System.out.print("Enter Distance Travelled (in km): ");
            double distance = sc.nextDouble();
            System.out.print("Enter Fuel Used (in liters or kWh): ");
            double fuel = sc.nextDouble();
            sc.nextLine();

            Vehicle v;
            switch (type) {
                case "diesel":
                    v = new DieselVehicle(name, distance, fuel);
                    break;
                case "electric":
                    v = new ElectricVehicle(name, distance, fuel);
                    break;
                default:
                    v = new PetrolVehicle(name, distance, fuel);
            }
            vehicles.add(v);
        }

        System.out.println("\n===== Efficiency Reports =====");
        for (Vehicle v : vehicles) {
            v.displayDetails();
        }

        sc.close();
    }
}


