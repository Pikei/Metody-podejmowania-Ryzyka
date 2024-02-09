import java.util.Arrays;

public class NajszybszySpadek {
    private final double[] x = new double[2];
    private final double[] xNew = new double[x.length];
    private final double[] g = new double[x.length];
    private double k = 0.5;
    private final double e = 0.002;

    public NajszybszySpadek() {
            Arrays.fill(x, 2);
        do {
            Arrays.setAll(xNew, i -> x[i]);
            Arrays.setAll(g, i -> fPrim(x, i));
            Arrays.setAll(xNew, i-> (x[i] - k*g[i]));
            if (f(x) < f(xNew)) {
                k /= 2;
                continue;
            }
            Arrays.setAll(x, i-> xNew[i]);
        } while (!stop());
        print();
    }

    private double f(double[] x) {
        //f(x) = 2(x-1.5)^2 + (y-0.5)^2 -3
        // sprawdzona funkcja ze znanym minimum i współrzędnymi
        return 2*Math.pow(x[0]-1.5,2) + 2*x[0]*x[1] + Math.pow(x[1]-0.5,2) -3;
    }

    private double fPrim(double[] x, int index) {
        // z założenia pod więcej wymiarów, przykładowo jakby było dla 100 wymiarów.
        // Aktualna funkcja operuje na 2 współrzędnych, ale odpowiednio modyfikując zwracane wartości metoda zwróci pochodną zarówno po x1, x50, czy x100
        if (index == 0) {
            double v = 4 * x[0] - 6 + 2 * x[1];
            return v;
        } else if (index == x.length-1) {
            double v = 2 * x[x.length - 2] + 2 * x[x.length - 1] - 1;
            return v;
        } else return 0;
    }

    private boolean stop() {
//        for (double v : g) {
//            if (Math.pow(Math.abs(v), 2) > e) return false;
//        }
//        return true;
        return Arrays.stream(g).noneMatch(v -> Math.pow(Math.abs(v), 2) > e);
    }

    private void print() {
        System.out.println("Znaleziono minimum funkcji o wartości: " + f(x) + " w punkcie (" + x[0] + ", " + x[1] + ")");
        System.out.println("Tolerancja błędu: " + e);
    }

    /*
    założenia algorytmu:
    wybierz punkt startowy, np (1,1)
    oblicz gradient funkcji w tym punkcie
    od obecnego punktu odejmij gradient*krok
    jeśli f(x)<f(xNew) zmniejsz krok (nie ma tutaj optymalizacji kroku więc dlatego jest on zmniejszany)
    w przeciwnym razie x = zNew
    jeśli |Gradient(Xi)|^2 <= e to zakończ
    w przeciwnym razie zacznij od nowa
     */

}