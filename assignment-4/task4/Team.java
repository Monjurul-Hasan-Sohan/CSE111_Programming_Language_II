package task4;

public class Team {
    private String name;
    private Player[] roster;
    private int count;


    public Team() {
        this.name = "Unnamed Team";
        this.roster = new Player[10];
        this.count = 0;
    }


    public Team(String name) {
        this();
        this.name = name;
    }

    public void updateName(String newName) {
        this.name = newName;
    }


    public void addPlayer(Player p) {
        if (count < roster.length) {
            roster[count++] = p;
        } else {
            System.out.println("Roster is full; cannot add " + p.getName());
        }
    }

    public void printDetail() {
        System.out.println("Team: " + name);
        System.out.println("List of players:");
        for (int i = 0; i < count; i++) {
            Player p = roster[i];
            System.out.println("Name: " + p.getName());
            System.out.println("Age: " + p.getAge() + ", Total Matches: " + p.getTotalMatches());
        }
    }
}
