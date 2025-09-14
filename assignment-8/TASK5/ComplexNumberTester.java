class RealNumber {
    public double realValue;
    public RealNumber() {
        this(0.0);
    }       
    public RealNumber(double realValue) {
        this.realValue = realValue;
    }
    public String toString(){
        return "RealPart: " + realValue;
    }
}

public class ComplexNumberTester {
    public static void main(String[] args) {
        ComplexNumber cn1 = new ComplexNumber();
        System.out.println(cn1);
        System.out.println("----------------");
        ComplexNumber cn2 = new ComplexNumber(5.0, 7.0);
        System.out.println(cn2);
    }
}

class ComplexNumber extends RealNumber {
    public double imaginaryValue;

    public ComplexNumber() {
        super(1.0);
        this.imaginaryValue = 1.0;
    }

    public ComplexNumber(double real, double imag) {
        super(real);
        this.imaginaryValue = imag;
    }  

    @Override
    public String toString() {
        return super.toString() + "\nImaginaryPart: " + imaginaryValue;
    }
}
