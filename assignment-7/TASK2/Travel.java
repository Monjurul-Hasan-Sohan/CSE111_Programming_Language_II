package TASK2;

public class Travel {
    static private int no_of_traveller;

    private String source;
    private String destination;
    private int time;

    public Travel(String source, String destination){
        this.source = source;
        this.destination = destination;
        no_of_traveller++;
    }

    public void setTime(int time){
        this.time = time;
    }

    public void setSource(String source){
        this.source = source;
    }

    public void setDestination(String destination){
        this.destination = destination;
    }

    public String displayTravelInfo(){  
        return "Source: " + source  + "\nDestination: " + destination + "\nTime: " + time + ":00";
    }

    public static int getCount(){
        return no_of_traveller;
    }
}
