class Sue {
    void method1() 
        {
            System.out.println("sue 1");
        }
    void method3() 
        {
            System.out.println("sue 3"); 
        }
}
class Blue {
    void method1() {
        System.out.println("blue 1");
        method3();
    }
    void method3() 
        {
             System.out.println("blue 3");
        }
}
class Moo extends Blue {
    void method2() {
        super.method3();
        System.out.println("moo 2");
        this.method3();       
    }
    void method3()
        {
             System.out.println("moo 3");
        }
}
class Crew extends Moo {
    void method1() 
        {
             System.out.println("crew 1"); 
    }
    void method3()
        {
             System.out.println("crew 3");
        }
}

public class Main {
    public static void main(String[] args) {

        Moo    var1 = new Crew();
        Blue   var2 = new Moo();
        Object var3 = new Sue();
        Sue    var4 = new Sue();
        Blue   var5 = new Crew();
        Blue   var6 = new Blue();

        System.out.println("1)");  var1.method1();
        System.out.println("2)");  var2.method1();

        System.out.println("3) compiler error");

        System.out.println("4)");  var4.method1();
        System.out.println("5)");  var5.method1();
        System.out.println("6)");  var6.method1();

        System.out.println("7)");  var1.method3();
        System.out.println("8)");  var2.method3();

        System.out.println("9) compiler error");

        System.out.println("10)"); ((Blue)var1).method1();
        System.out.println("11)"); ((Crew)var1).method2();

        System.out.println("12) compiler error (inconvertible types)"); 

        System.out.println("13)");
        try { ((Blue)var3).method1(); } catch (ClassCastException e) { System.out.println("runtime error"); }

        System.out.println("14)");
        try { ((Crew)var3).method1(); } catch (ClassCastException e) { System.out.println("runtime error"); }

        System.out.println("15)"); ((Sue)var3).method3();

        System.out.println("16)"); ((Moo)var2).method2();

        System.out.println("17)");
        try { ((Crew)var3).method2(); } catch (ClassCastException e) { System.out.println("runtime error"); }

        System.out.println("18)"); ((Moo)var5).method2();

        System.out.println("19)");
        try { ((Moo)var6).method2(); } catch (ClassCastException e) { System.out.println("runtime error"); }

        System.out.println("20)"); ((Moo)var2).method1();
    }
}
