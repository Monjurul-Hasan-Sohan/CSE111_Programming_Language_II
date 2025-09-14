public class CoffeeTester {
    public static void main(String[] args) {
        Machine m1 = new Machine("Generic Brand", true);
        CoffeeMachine c1 = new CoffeeMachine("Nespresso", true, 4);

        System.out.println("===== Machine Info =====");
        m1.displayInfo();
        System.out.println("------------------------");
        m1.operate();

        System.out.println("\n===== Coffee Machine Info =====");
        c1.displayInfo();
        System.out.println("------------------------");
        c1.operate();
        System.out.println("------------------------");
        c1.brewMultipleCups(5);
        System.out.println("------------------------");
        c1.operate();
    }
}

class Machine {
public String brand;
public boolean status;

Machine(String brand, boolean status) {
    this.brand = brand;
    this.status = status;
}

void displayInfo() {
    System.out.println("Brand: " + brand);
    System.out.println("Status: " + status);
}

public void operate() {
    System.out.println("Machine is now operating....");
}

}
class CoffeeMachine extends Machine {
    public int bean;
    CoffeeMachine(String nm, boolean st, int n) {
    super(nm, st);
    this.bean = n;
}
    @Override
    public void displayInfo() {
    System.out.println("Brand: " + brand);
    System.out.println("Status: " + status);
    System.out.println("Beans: " + bean);
}


    public void call() {
        if (bean > 0) {
            this.bean--;
            System.out.println("Brewing a cup of coffee... Beans left: " + this.bean);
        } else {
            System.out.println("No more beans");
        }
    }

    @Override
    public void operate() {
        System.out.println("!cannot brew coffee");
    }


    public void brewMultipleCups(int nofc) {
        if (this.bean > 0) {
            System.out.println("Starting to brew multiple cups of coffee...");
            for (int i = bean; i > 0; i--) {
                this.call();
            }
        }
    }
}