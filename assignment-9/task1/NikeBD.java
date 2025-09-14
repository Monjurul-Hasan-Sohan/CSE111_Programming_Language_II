package task1;
public class NikeBD {

    private static int branchesOpened = 0;
    private static int totalJordan = 0, totalCortez = 0, totalKobe = 0;
    private static int totalSold = 0;

    private final String outletName;
    private int jordan = 0, cortez = 0, kobe = 0;
    private int sold = 0;

    public NikeBD(String outletName) {
        this.outletName = outletName;
        branchesOpened++;
    }

    public static void status() {
        System.out.println("Nike Bangladesh Status:");
        System.out.println("Branches Opened: " + branchesOpened);
        System.out.println("Currently Stocked: Jordan: " + totalJordan +
                           ", Cortez: " + totalCortez + ", Kobe: " + totalKobe);
        System.out.println("Sold: " + totalSold);
    }

    public void details() {
        System.out.println("Nike " + outletName + " outlet:");
        System.out.println("Products Currently Stocked: Jordan: " + jordan +
                           ", Cortez: " + cortez + ", Kobe: " + kobe);
        System.out.println("Sold: " + sold);
    }

    public void restockProducts(String product, int qty) {
        addStock(product, qty);
    }

    public void restockProducts(String[] products, int[] qty) {
        for (int i = 0; i < products.length && i < qty.length; i++) {
            addStock(products[i], qty[i]);
        }
    }

    public void productsSold(String prod1, int qty1, String prod2, int qty2) {
        sell(prod1, qty1);
        sell(prod2, qty2);
    }

    public void productSold(String prod1, int qty1, String prod2, int qty2) {
        productsSold(prod1, qty1, prod2, qty2);
    }

    private void addStock(String product, int qty) {
        if ("Jordan".equals(product)) {
            jordan += qty; totalJordan += qty;
        } else if ("Cortez".equals(product)) {
            cortez += qty; totalCortez += qty;
        } else if ("Kobe".equals(product)) {
            kobe += qty; totalKobe += qty;
        }
    }

    private void sell(String product, int qty) {
        if ("Jordan".equals(product)) {
            if (qty > jordan) qty = jordan;
            jordan -= qty; totalJordan -= qty;
        } else if ("Cortez".equals(product)) {
            if (qty > cortez) qty = cortez;
            cortez -= qty; totalCortez -= qty;
        } else if ("Kobe".equals(product)) {
            if (qty > kobe) qty = kobe;
            kobe -= qty; totalKobe -= qty;
        } else {
            return;
        }
        sold += qty;
        totalSold += qty;
    }
}
