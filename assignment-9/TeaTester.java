class Tea {
    public String name;
    protected int price;
    protected boolean status;

    public Tea(String name, int price) {
        this.name = name;
        this.price = price;
        this.status = false;
    }

    public void productDetail() {
        System.out.println("Name: " + name + ", Price: " + price);
        System.out.println("Status: " + status);
    }
}

class KKTea extends Tea {
    protected int teaBags;
    private static int totalSales = 0;
    private static int regularSold = 0;
    protected static int flavouredSold = 0;

    public KKTea(int price, int teaBags) {
        super("KK Regular Tea", price);
        this.teaBags = teaBags;
    }

    public static void updateSoldStatusRegular(KKTea t){
        if (!t.status) {
            t.status = true;
            totalSales++;
            regularSold++;
        }
    }

    @Override
    public void productDetail() {
        super.productDetail();
        int weight = teaBags * 2;
        System.out.println("Weight: " + weight + ", Tea Bags: " + teaBags);
    }

    public static void totalSales(){
        System.out.println("Total Sales: " + totalSales);
        System.out.println("KK Regular Tea: " + regularSold);
        if (flavouredSold > 0) {
            System.out.println("KK Flavoured Tea: " + flavouredSold);
        }
    }

    protected static void addFlavouredSaleIfNew(KKFlavouredTea t){
        if (!t.status) {
            t.status = true;
            totalSales++;
            flavouredSold++;
        }
    }
}

class KKFlavouredTea extends KKTea {
    private String flavour;

    public KKFlavouredTea(String flavour, int price, int teaBags) {
        super(price, teaBags);
        this.flavour = flavour;
        this.name = "KK " + flavour + " Tea";
    }

    public static void updateSoldStatusFlavoured(KKFlavouredTea t){
        KKTea.addFlavouredSaleIfNew(t);
    }
}

public class TeaTester {
    public static void main(String[] args) {
        KKTea t1 = new KKTea(250, 50);
        System.out.println("--------1--------");
        t1.productDetail();

        System.out.println("--------2--------");
        KKTea.totalSales();

        System.out.println("--------3--------");
        KKTea t2 = new KKTea(470, 100);
        KKTea.updateSoldStatusRegular(t1);
        KKTea.updateSoldStatusRegular(t2);

        System.out.println("--------4--------");
        t2.productDetail();

        System.out.println("--------5--------");
        KKTea.totalSales();

        System.out.println("--------6--------");
        KKFlavouredTea t4 = new KKFlavouredTea("Jasmine", 260, 50);
        KKFlavouredTea t5 = new KKFlavouredTea("Honey Lemon", 270, 45);
        KKFlavouredTea t6 = new KKFlavouredTea("Honey Lemon", 270, 45);

        System.out.println("--------7--------");
        t4.productDetail();

        System.out.println("--------8--------");
        t6.productDetail();

        System.out.println("--------9--------");
        KKFlavouredTea.updateSoldStatusFlavoured(t4);
        KKFlavouredTea.updateSoldStatusFlavoured(t5);
        KKFlavouredTea.updateSoldStatusFlavoured(t6);

        System.out.println("--------10--------");
        KKTea.totalSales();
    }
}
