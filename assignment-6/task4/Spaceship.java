package task4;

public class Spaceship {
  private String name;
  private int capacity;
  private Cargo[] cargos = new Cargo[100];
  private int count = 0;
  private int current = 0;

  public Spaceship(String n, int c) {
    name = n;
    capacity = c;
  }

  public void loadCargo(Cargo c) {
    if (c == null) return;
    int after = current + c.getWeight();
    if (after > capacity) {
      System.out.println("Warning: Unable to load " + c.getName() + " inside " + name + ". Exceeds capacity by " + (after - capacity) + ".");
      return;
    }
    if (count >= cargos.length) return;
    cargos[count] = c;
    count++;
    current = after;
  }

  public void displayDetails() {
    System.out.println("Spaceship Name: " + name);
    System.out.println("Capacity: " + capacity);
    System.out.println("Current Cargo Weight: " + current);
    String s = "";
    int i;
    for (i = 0; i < count; i++) {
      if (i > 0) s = s + " ";
      s = s + cargos[i].getName();
    }
    System.out.println("Cargo:" + s);
  }
}
