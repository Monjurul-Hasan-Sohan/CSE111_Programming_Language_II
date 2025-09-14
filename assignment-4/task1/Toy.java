package task1;

class Toy {
    int price;
    String name;

    Toy(String toyName, int toyPrice) {
        name = toyName;
        price = toyPrice;
        System.out.println("A new toy has been made!");
    }

    void showPrice() {
        System.out.println("price: " + price + " Taka");
    }

    void updateName(String newName) {
        System.out.println("Changing old name: " + name);
        name = newName;
        System.out.println("new name: " + name);
    }

    void updatePrice(int newPrice) {
        price = newPrice;
    }
}
