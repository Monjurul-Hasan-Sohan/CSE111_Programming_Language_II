public class main {
 public static void main(String[] args){
    Machine m1 = new Machine("ground", true);
    CoffeeMachine c1 = new CoffeeMachine("Nescafe", true, 4);
    System.out.println("==========================================");
    System.out.println("============DISPLAY INFO============");
    m1.displayinfo();
    System.out.println("============DISPLAY INFO============");
    c1.displayinfo();
    System.out.println("============OPERATE============");
    m1.operate();
    System.out.println("============OPERATE============");
    c1.operate();
    System.out.println("============MULTIPLE COFFEE============");
    c1.multiplecoffee(5);
    System.out.println("============OPERATE============");
    c1.operate();
 }   
}
