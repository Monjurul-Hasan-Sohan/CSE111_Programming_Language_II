package TASK3;

public class Cargo {

    public static double int_capacity = 10.0;   
    public static int id_count = 0;

    private String contents;
    private double weight;
    private boolean loaded;
    private int ID;

    Cargo(String contents, double weight){
        this.contents = contents;
        this.weight = weight;
        id_count++;
        loaded = false;
        ID = id_count;
    }

    public void load(){
        if (!loaded) { 
            if (weight <= int_capacity) { 
                loaded = true;
                int_capacity -= weight;
                System.out.println("Cargo " + ID + " loaded for transport.");
            } else {
                System.out.println("Cannot load cargo, exceeds weight capacity.");
            }
        }
    }

    public void unload(){
        if (loaded) {  
            loaded = false;
            int_capacity += weight;
            System.out.println("Cargo " + ID + " unloaded.");
        }
    }

    public void details(){
        System.out.println("Cargo ID: " + ID + ", Contents: " + contents + ", Weight: " + weight + ", Loaded: " + loaded);
    }

    public static double capacity(){ 
        return int_capacity;
    }
}
