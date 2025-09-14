package task1;

public class Shape {
    private String name;
    private double param1;
    private double param2;
    private double area;

    public void setParameters(String name, int radius) {
        this.name = name;
        this.param1 = radius;
        this.param2 = 0;
        this.area = Math.PI * radius * radius;
    }

    public void setParameters(String name, int base, int height) {
        this.name = name;
        this.param1 = base;
        this.param2 = height;
        this.area = 0.5 * base * height;
    }

    public void setParameters(String name, double length, double width) {
        this.name = name;
        this.param1 = length;
        this.param2 = width;
        this.area = length * width;
    }

    public String details() {
        if ("Circle".equals(name)) {
            return "Shape: " + name + "\nArea: " + String.format("%.2f", area);
        } else if ("Triangle".equals(name)) {
            return "Shape: " + name + "\nArea: " + String.format("%.2f", area);
        } else if ("Rectangle".equals(name)) {
            return "Shape: " + name + "\nArea: " + String.format("%.2f", area);
        } else {
            return "Unknown Shape";
        }
    }
}
