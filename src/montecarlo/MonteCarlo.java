package montecarlo;

import java.util.Arrays;
import java.util.Random;

public class MonteCarlo {
    private static final int numberOfPoints = 20; //liczba punktów losowanych
    private final int dimensions = 4; //przestrzeń n-wymiarowa
    private final double[][] x = new double[numberOfPoints][dimensions]; //wszystkie punkty
    private final double[] oldBest = new double[dimensions]; //współrzędne najlepszego punktu
    private final double[] newBest = new double[dimensions]; //współrzędne najlepszego punktu
    private final double[] results = new double[numberOfPoints]; //tablica z wynikami dla punktów
    private final int iterations = 1000; //warunek stopu po liczbie iteracji całego algorytmu
    private int k = 30; //warunek stopu po liczbie iteracji, jeśli najlepszy punkt się nie zmienił
    private final Random rand = new Random();

    public MonteCarlo() {
        printResult(monteCarlo());
    }

    private double f(double[] x) {
        return Math.pow(x[2],3) + 3*Math.pow(x[3],2) - 2*x[0]*Math.pow(x[1],4);
    }

    private double randomize(double bound) {
        return rand.nextDouble(bound);
    }

    private void drawNewPointPosition() {
//      dla współrzędnych punktów w różnym zakresie:
        for (int i = 0; i < numberOfPoints; i++) {
            x[i][0] = randomize(500);
            x[i][1] = randomize(500);
            x[i][2] = randomize(500);
            x[i][3] = randomize(100);
//            dla współrzędnych punktów w takim samym zakresie:
//            for (int j = 0; j < dimensions; j++) {
//                x[i][j] = randomize(500);
//            }
        }
    }
    private void calculateResult() {
        for (int i = 0; i < results.length; i++) {
            results[i] = f(x[i]);
        }
    }
    private int monteCarlo() {
        Arrays.fill(oldBest, 0);
        for (int i = 0; i < iterations; i++) {
            drawNewPointPosition(); //wylosowanie nowych pozycji punktów
            calculateResult(); //obliczenie wartości funkcji w tych punktach
            double min = Arrays.stream(results).min().getAsDouble(); //znalezienie minimum
            int index = Arrays.binarySearch(results, min); //znalezienie indesku dla minimum
            if (index == -1) {index = indexException(min);} //na wypadek błędu metody binarySearch(), która czasem zwraca wardość -1
            System.arraycopy(x[index], 0, newBest, 0, newBest.length); //zapamiętanie najlepszej pozycji
            if(!Arrays.equals(oldBest, newBest)) { //jeżeli stary najlepszy punkt i nowy najlepszy punkt są od siebie różne
                k = 30; //przywraca licznik "k" do orginalnej wartości
                if (isNewBetter()) { //porównanie który punkt jest lepszy i zastąpienie gorszego tym lepszym
                    System.arraycopy(newBest, 0, oldBest, 0, oldBest.length);
                } else {
                    System.arraycopy(oldBest, 0, newBest, 0, newBest.length);
                }
            } else k--; //jeśli nie program nie wylosował nowego punktu to zmniejsza licznik "k"
            if(k==0) { //jeśli licznik "k" dojdzie do 0 kończy się algorytm i zwracana jest ilość iteracji po której program zakończył działanie
                return i;
            }
        }
        return iterations;
    }

    private void printResult(int iter) {
        System.out.println("Wartość znalezionego minimum wynosi: " + f(oldBest));
        System.out.println("Znajduje się w punkcie o współrzędnych: ");
        for (double v : oldBest) {
            System.out.println(v);
        }
        if (iter != iterations){
            System.out.println("Program zatrzymał się po wykonaniu " + iter + " iteracji.\n" +
                    "Przez 30 ostatnich iteracji wartość najlepszego punktu nie uległa zmianie.");
        } else {
            System.out.println("Program zatrzymał się po wykonaniu " + iter + " iteracji.");        }
    }
    private int indexException(double min) {
        int index;
        for (index = 0; index < results.length; index++) {
            if(results[index]==min)
                return index;
        }
        return 0;
    }

    private boolean isNewBetter() {
        return f(newBest) < f(oldBest);
    }

    public int getDimensions() {
        return dimensions;
    }

    public double[][] getX() {
        return x;
    }

    public double[] getOldBest() {
        return oldBest;
    }

    public double[] getNewBest() {
        return newBest;
    }

    public double[] getResults() {
        return results;
    }

    public int getIterations() {
        return iterations;
    }

    public int getK() {
        return k;
    }

    public void setK(int k) {
        this.k = k;
    }

    public Random getRand() {
        return rand;
    }
}