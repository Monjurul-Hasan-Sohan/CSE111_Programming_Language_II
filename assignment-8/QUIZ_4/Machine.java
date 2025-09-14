public class Machine{
    String name;
    boolean status;
    public Machine(String nm, boolean st){
        this.name = nm;
        this.status = st;
    }
    public void displayinfo(){
        System.out.println("Name: " + name + 
                            "\nStatus: " + status);
    }
    public void operate(){
        System.out.println("Machine is operating...");
    }
}
