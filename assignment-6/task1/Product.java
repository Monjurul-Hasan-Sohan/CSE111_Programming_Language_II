package task1;

public class Product {
    // Private attributes
    private String name;
    private double price;
    private int quantity;

    // Default constructor
    public Product() {
        this.name = "Unknown";
        this.price = 0.0;
        this.quantity = 0;
    }

    // Parameterized constructor
    public Product(String name, double price) {
        this.name = name;
        this.price = price;
        this.quantity = 0; // default
    }


    // Getter & Setter for price
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }

    // Getter & Setter for quantity
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // Display info (without quantity)
    public void displayInfo() {
        System.out.println("Product Name: " + name);
        System.out.println("Price: $" + price);
    }

    // Overloaded displayInfo with quantity
    public void displayInfo(boolean showQuantity) {
        System.out.println("Product Name: " + name);
        System.out.println("Price: $" + price);
        if (showQuantity) {
            System.out.println("Quantity: " + quantity);
        }
    }
}
