package task4;

public class Player {
    private String name;
    private int age;
    private int totalMatches;

    public Player(String name, int age, int totalMatches) {
        this.name = name;
        this.age = age;
        this.totalMatches = totalMatches;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public int getTotalMatches() {
        return totalMatches;
    }
}
