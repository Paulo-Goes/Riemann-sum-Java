public class Dados {
    private int intervalo;
    private double x1, area, soma;

    public Dados(int n, double x1, double area, double soma){
        intervalo = n;
        this.x1 = x1;
        this.area = area;
        this.soma = soma;
    }

    public int getIntervalo() {
        return intervalo;
    }

    public double getX1() {
        return x1;
    }

    public double getArea() {
        return area;
    }

    public double getSoma() {
        return soma;
    }
}