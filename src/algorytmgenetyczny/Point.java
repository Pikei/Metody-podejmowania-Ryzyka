package algorytmgenetyczny;

public class Point {
    private double[] x;
    private double value;

    public Point(double[] x, double value) {
        this.x = x;
        this.value = value;
    }

    public double[] getX() {
        return x;
    }

    public void setX(double[] x) {
        this.x = x;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
