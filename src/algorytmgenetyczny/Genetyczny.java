package algorytmgenetyczny;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

public class Genetyczny {
    private final int dimentions = 2;               //liczba wymiarów
    private final int populationNumber = 100;       //liczebność populacji
    private final double e = 0.02;                 //tolerancja błędu
    private final Point[] population = new Point[populationNumber];
    private final Point[] newPopulation = new Point[populationNumber];

    private final Random rand = new Random();

    public Genetyczny() {
        generatePopulation();
        IntStream.range(0, newPopulation.length).forEach(i -> newPopulation[i] = new Point(population[i].getX(), population[i].getValue()));
        int bestOldIndex, bestNewIndex, counter = 0;
        do {
            bestOldIndex = findBest(population);
            newGeneration();
            crossover();
            IntStream.range(0, populationNumber).forEach(i -> newPopulation[i].setValue(f(newPopulation[i].getX())));
            bestNewIndex = findBest(newPopulation);
            compare(bestOldIndex, bestNewIndex);
            for (int i = 0; i < population.length; i++) {
                population[i].setValue(newPopulation[i].getValue());
                population[i].setX(newPopulation[i].getX());
            }
//            printStep(bestNewIndex);
            counter ++;
        } while (!exit(bestNewIndex, counter));
        printResult(bestNewIndex);
    }


    private void generatePopulation() {
        for (int i = 0; i < populationNumber; i++) {
            double[] temp = new double[dimentions];
            Arrays.setAll(temp, j -> rabdomDouble(-500, 500));
            population[i] = new Point(temp, f(temp));
        }
    }

    private int findBest(Point[] population) {
        double best = population[0].getValue();
        int index = 0;
        for (int i = 0; i < population.length; i++) {
            if(population[i].getValue() < best) {
                best = population[i].getValue();
                index = i;
            }
        }
        return index;
    }
    private int tournament(int contestantsNumber) {
        int[] temp = new int[contestantsNumber];
        Arrays.setAll(temp, i -> rand.nextInt(100));
        int best = temp[0];
        for (int i : temp) {
            if (population[best].getValue() < population[i].getValue()) {
                best = i;
            }
        }
        return best;
    }
    private void newGeneration() {
        int index;
        for (int i = 0; i < populationNumber; i++) {
            index = tournament(4);
            newPopulation[i] = new Point(population[index].getX(),population[index].getValue());
        }
    }
    private void compare(int oldIndex, int newIndex) {
        if (population[oldIndex].getValue() < newPopulation[newIndex].getValue()) {
            newPopulation[newIndex].setX(population[oldIndex].getX());
            newPopulation[newIndex].setValue(population[oldIndex].getValue());
        }
    }
    private void crossover(){
        for (int i = 0; i < newPopulation.length; i+=2) {
            if (rand.nextDouble() <= 0.4) {
                double[] temp = new double[dimentions];
                double[] temp2 = new double[dimentions];
                for (int j = 0; j <= dimentions/2-1; j++) {
                    temp[j] = newPopulation[i].getX()[j];
                    temp2[j] = newPopulation[i+1].getX()[j];
                }
                for (int j = dimentions/2; j<dimentions; j++) {
                    temp[j] = newPopulation[i+1].getX()[j];
                    temp2[j] = newPopulation[i].getX()[j];
                }
                if (rand.nextDouble() < 0.2) {
                    Arrays.setAll(temp, j -> temp[j]*=rabdomDouble(-1,1));
                    Arrays.setAll(temp2, j -> temp2[j]*=rabdomDouble(-1,1));
                }
                newPopulation[i].setX(temp);
                newPopulation[i+1].setX(temp2);
            }
        }
    }

    private boolean exit(int index, int counter) {
        if (counter == 1000000){
            System.out.println("Program zakończył się po 1mln iteracji");
            System.out.println("Znalezione rozwiązanie jest bliskie prawdy ale wykracza poza zakres +/- " + e + " od rzeczywistego rozwiązania.");
            System.out.println("Rzeczywiste rozwiązanie to -4.75, w punkcie (2.5, -2)");
            return true;
        }
        //chrzanię to inaczej niż iteracyjnie się nie da albo jeśli się realnie zna wartość
        double realValue = -4.75;
        if (Math.abs(Math.abs(newPopulation[index].getValue()) - Math.abs(realValue)) <= e){
            System.out.println("Program zakończył po " + counter + " iteracjach znajdując rozwiązanie w granicach +/- " + e + " od rzeczywistego rozwiązania.");
            System.out.println("Rzeczywiste rozwiązanie to -4.75, w punkcie (2.5, -2)");
            return true;
        }
        return false;
    }

    private double f(double[] x) {
        //f(x) = 2(x-1.5)^2 + 2xy + (y-0.5)^2 -3
        return 2*Math.pow(x[0]-1.5,2) + 2*x[0]*x[1] + Math.pow(x[1]-0.5,2) -3;
    }

    private double rabdomDouble(double min, double max) {
        return rand.nextDouble() * (max - min) + min;
    }
    private void printResult(int best) {
        System.out.println("Znaleziono minimum funkcji o wartości: " + newPopulation[best].getValue() + " w punkcie (" +  newPopulation[best].getX()[0] + ", " + newPopulation[best].getX()[1] + ")");
    }
    private void printStep(int best) {
        System.out.println("Aktualna wartość funkcji : " + newPopulation[best].getValue() + " w punkcie (" +  newPopulation[best].getX()[0] + ", " + newPopulation[best].getX()[1] + ")");
    }

    /*
    działanie algorytmu genetycznego:
    1. Generowanie populacji:
        losowe współrzędne dla określonej liczby punktów,
        przykładowo dla punktów o współrzędnych (X,Y) i liczebności popoulacji = 100:
            wygeneruj 100 punktów o współrzędnych (rand, rand)
    2. Ocena poppulacji:
        określ wartości f(P), gdzie P to punkt w populacji, dla wszystkich osobników
        zapamiętaj najlepszego osobnika z populacji
    3. selekcja wybranie osobników do reprodukcji, na zasadzie turnieju:
        wybierz kilka losowych osobników z populacji,
            wybierz spośród nich najlepszego osobnika i dodaj do nowej populacji
    4. krzyżowanie:
        określ warunki krzyżowania, przykładowo jeśli rand<0.4 krzyżuj ze sobą pary osobników:
            przykładowe krzyżowanie:
                P1 = (X1, Y1)   P2 = (X2, Y2)
                P1new = (X1, Y2)    P2new = (X2, Y1)
    5. mutacja:
        określ warunki mutacji, przykładowo:
            if rand <= 0.2
                P = (rand(-0.2, 0.2) * X, rand(-0.2, 0.2) * Y)
    6. Ocena nowej populacji
        określ wartości f(Pnew), gdzie Pnew to punkt w nowej populacji, dla wszystkich osobników
        porównaj najlepszego osobnika z nowej populacji z najlepszym osobnikiem z nowej populacji
            jeśli stary osobnik jest lepszy niż nowy, zamień nowego najlepszego na starego najlepszego
    7. Warunek stopu:
        np ocena wszystkich osobników jest w granicach błędu, przykładowo:
            (for i from 0 to populacja.length) {if ||f(Pi)| - |f(Pi+1)|| > 0.002 return false}return true;
    8. Zwrócenie najlepszego osobnika z nowej populacji jako wynik;
     */
}
