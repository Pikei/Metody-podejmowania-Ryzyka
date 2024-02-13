import java.util.Arrays;
import java.util.Random;

public class Roznicowy {
    private final int dimensions = 2;
    private final int populationSize = 100;

    private final double[][] x = new double[populationSize][dimensions];
    private final double[][] u = new double[populationSize][dimensions];
    private final double[][] o = new double[populationSize][dimensions];
    private final double F = 0.6;
    private final double C = 0.4;
    private final double e = 0.002;
    private final Random rand = new Random();

    public Roznicowy() {
        generatePopulation();
        double[] best = new double[dimensions];
        do {
            for (int i = 0; i < populationSize; i++) {
                u[i] = mutate();
                if (rand.nextDouble(1) <= C)
                    System.arraycopy(u[i], 0, o[i], 0, dimensions);
                else
                    System.arraycopy(x[i], 0, o[i], 0, dimensions);
                if (f(o[i]) <= f(x[i]))
                    System.arraycopy(o[i], 0, x[i], 0, dimensions);
            }
            best = findBest();
        } while (!exit(best));
        printResult(best);
    }

    private double f(double[] x) {
        //(x-3.14)^2+(y-2.72)^2+sin(3*x+1.41)+sin(4*y-1.73) | funkcja ze strony https://machinelearningmastery.com/a-gentle-introduction-to-particle-swarm-optimization/
        return Math.pow(x[0] - 3.14, 2) + Math.pow(x[1] -2.72, 2) + Math.sin(3*x[0]+1.41) + Math.sin(4*x[1]-1.73);
//        return 2*Math.pow(x[0]-1.5,2) + 2*x[0]*x[1] + Math.pow(x[1]-0.5,2) -3;

    }

    private void generatePopulation() {
        for (int i = 0; i < x.length; i++) {
            Arrays.setAll(x[i], j -> randomDouble(-500, 500));
        }
    }

    private double[] mutate() {
        int i1, i2, i3;
        do {
            i1 = rand.nextInt(populationSize);
            i2 = rand.nextInt(populationSize);
            i3 = rand.nextInt(populationSize);
        } while (i1==i2 && i2==i3);
        double[] temp = new double[dimensions];
        for (int i = 0; i < temp.length; i++) {
            temp[i] = x[i1][i] + F * (x[i2][i] - x[i3][i]);
        }
        return temp;
    }

    private double[] findBest() {
        double[] best = new double[dimensions];
        Arrays.setAll(best, i -> x[0][i]);
        for (int i = 0; i < x.length; i++) {
            if (f(x[i]) < f(best))
                System.arraycopy(x[i], 0, best, 0, dimensions);
        }
        return best;
    }

    private boolean exit(double[] best) {
        for (int i = 0; i < populationSize; i++) {
            for (int j = 0; j < best.length; j++) {
                if (Math.abs(Math.abs(best[j])) - Math.abs(x[i][j]) > e ) return false;
            }
            if (Math.abs(Math.abs(f(best)) - Math.abs(f(x[i]))) > e ) return false;
        }
        return true;
    }

    private void printResult(double[] best) {
        System.out.println("Znaleziono minimum w punkcie X(" + roundResult(best[0] , 6) + ", " + roundResult(best[1] , 6) + ")");
        System.out.println("Wartość funkcji f(X) = " + roundResult(f(best) , 6));
    }

    private double randomDouble(double min, double max) {
        return rand.nextDouble() * (max - min) + min;
    }

    private double roundResult(double value, int position) {
        int number = (int) value;
        double result = value - number;
        double temp = (int)((value - number) * Math.pow(10,position +1));
        double temp2 = (int)(temp/ 10);
        temp2 *=10;
        result *= Math.pow(10,position);
        result = (int) result;
        if (Math.abs(temp - temp2)>=5){
            if (result > 0) {
                result++;
            } else {
                result--;
            }
        }
        result /=  Math.pow(10,position);
        result += number;
        return result;
    }
}

/*
OZNACZENIA:
C - prawdopodobieństwo przejścia elementu z etapu mutacji do etapu próbnego (C <0,1>)
F - czynnik skalujący wykorzystywany w mutacji (zazwyczaj od 0.4 do 0.9)
Xi - osobnik w populacji
Ui - osobnik w procesie mutacji
Oi - osobnik w procesie krzyżowania
DZIAŁANIE ALGORYTMU:
1. Generowanie populacji
    Generowana jest nowa populacja osobników poprzez wybór losowy współrzędnych
2. REPRODUKCJA:
    Reprodukcja polega na ustaleniu wektora bazowego wymaganego do przeprowadzenia mutacji.
    Wyznacza sięgo na różne sposoby, przeważnie losowo lub wybiera się najlepszego osobnika z populacji.
    W skrócie wybór 3 losowych osobników różnych od siebie
3. MUTACJA:
    Ui = Xi1 + F * (Xi2 - Xi3)
    !OSOBNIKI Xi MUSZĄ BYĆ RÓŻNE OD SIEBIE!
4. KRZYŻOWANIE:
    jeżeli rand(0,1) <= C
        Oi = Ui
    w przeciwnym razie
        Oi = Xi
5. SELEKCJA
    jeżeli f(Oi) <= f(Xi)
        Xi = Oi
6. jeżeli warunek stopu nie został spełniony idź do punktu 2.
7. zwróć wartość najlepszego osobnika

 */