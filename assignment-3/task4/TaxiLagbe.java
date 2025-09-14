package task4;

class TaxiLagbe {
    private String taxiID;
    private String area;
    private String[] passengerNames;
    private int[] fares;
    private int count;

    TaxiLagbe() {
        passengerNames = new String[4];
        fares = new int[4];
        count = 0;
    }

    void storeInfo(String id, String areaName) {
        this.taxiID = id;
        this.area = areaName;
    }

    void addPassenger(String name, int fare) {
        if (count < 4) {
            passengerNames[count] = name;
            fares[count] = fare;
            count++;
            System.out.println("Dear " + name + "! Welcome to TaxiLagbe");
        } else {
            System.out.println("Taxi Full! No more passengers can be added");
        }
    }

    void printDetails() {
        System.out.println("Taxi number: " + taxiID);
        System.out.println("This taxi can cover " + area + " area");
        System.out.println("Total Passenger: " + count);
        System.out.println("Passenger Lists:");
        for (int i = 0; i < count; i++) {
            System.out.println(passengerNames[i]);
        }

        int totalFare = 0;
        for (int i = 0; i < count; i++) {
            totalFare += fares[i];
        }

        System.out.println("Total collected fare: " + totalFare + " Taka");
    }
}