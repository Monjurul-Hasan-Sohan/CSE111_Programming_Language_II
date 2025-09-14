package task3;

class Triangle {
    int second, third, first;

    Triangle(int a, int b, int c) {
        first = a;
        second = b;
        third = c;
    }

    int getPerimeter() {
        return first + second + third;
    }

    void triangleDetails() {
        System.out.println("Three sides of the triangle are: " + first + ", " + second + ", " + third);
        System.out.println("Perimeter: " + getPerimeter());
    }

    String printTriangleType() {
        if (first == second && second == third) {
            return "This is an Equilateral Triangle.";
        } else if (first == second || second == third || first == third) {
            return "This is an Isosceles Triangle.";
        } else {
            return "This is a Scalene Triangle.";
        }
    }

    void compareTriangles(Triangle other) {
        if (this == other) {
            System.out.println("These two triangle objects have the same address.");
        } else if (first == other.first && second == other.second && third == other.third) {
            System.out.println("Addresses are different but the sides of the triangles are equal.");
        } else if (getPerimeter() == other.getPerimeter()) {
            System.out.println("Only the perimeter of both triangles is equal.");
        } else {
            System.out.println("Addresses, length of the sides and perimeter all are different.");
        }
    }
}
