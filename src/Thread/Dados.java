package Thread;

public class Dados {
    private final double x1, area, soma;

    public Dados(double x1, double area, double soma){
        this.x1 = x1;
        this.area = area;
        this.soma = soma;
    }

    public synchronized double getX1() {
        return x1;
    }

    public synchronized double getArea() {
        return area;
    }

    public synchronized double getSoma() {
        return soma;
    }
}