package algorytmgenetyczny;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;
import java.util.stream.IntStream;

public class Genetyczny {
    private final int dimentions = 2;               //liczba wymiarów
    private final int populationNumber = 100;       //liczebność populacji
    private final double e = 0.02;                 //tolerancja błędu
    private final double[] bestValue = new double[populationNumber];
    private final HashMap<Integer, Point> population = new HashMap<>();
    private final HashMap<Integer, Point> newPopulation = new HashMap<>();
    private final Random rand = new Random();

    public Genetyczny() {
        generatePopulation();
        int bestOldIndex, bestNewIndex = 0;
//        Arrays.setAll(bestValue, i -> rabdomDouble(-1000, 1000));
//        do {
        for (int j = 0; j < 100000; j++) {
            if (j == 90000)
                System.out.println();
            newPopulation.putAll(population);
            bestOldIndex = findBest(population);
            newGeneration();
            crossover();
            IntStream.range(0, populationNumber).forEach(i -> newPopulation.get(i).setValue(f(newPopulation.get(i).getX())));
            bestNewIndex = findBest(newPopulation);
            compareBest(bestOldIndex, bestNewIndex);
            bestValue[0] = newPopulation.get(bestNewIndex).getValue();
            printStep(bestNewIndex);

        }
//            bestValue[j] = newPopulation.get(bestNewIndex).getValue();
//            j++;
//            if (j >= populationNumber)
//                j=0;
//        } while (!exit(j));
        printResult(bestNewIndex);
    }

    private void generatePopulation() {
        for (int i = 0; i < populationNumber; i++) {
            double[] temp = new double[dimentions];
            Arrays.setAll(temp, j -> rabdomDouble(-500, 500));
            population.put(i, new Point(temp, f(temp)));
        }
    }

    private int findBest(HashMap<Integer, Point> population) {
        double best = population.get(0).getValue();
        int index = 0;
        for (int i = 0; i < population.size(); i++) {
            if(population.get(i).getValue() < best) {
                best = population.get(i).getValue();
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
            if (population.get(best).getValue() < population.get(i).getValue()) {
                best = i;
            }
        }
        return best;
    }
    private void newGeneration() {
        for (int i = 0; i < populationNumber; i++) {
            newPopulation.put(i, population.get(tournament(10)));
        }
    }
    private void compareBest(int oldIndex, int newIndex) {
        if (population.get(oldIndex).getValue() < newPopulation.get(newIndex).getValue()) {
            newPopulation.get(newIndex).setX(population.get(oldIndex).getX());
            newPopulation.get(newIndex).setValue(population.get(oldIndex).getValue());
        }
    }
    private void crossover(){
        for (int i = 0; i < newPopulation.size(); i+=2) {
            if (rand.nextDouble() <= 0.4) {
                double[] temp = new double[dimentions];
                double[] temp2 = new double[dimentions];
                for (int j = 0; j <= dimentions/2-1; j++) {
                    temp[j] = newPopulation.get(i).getX()[j];
                    temp2[j] = newPopulation.get(i+1).getX()[j];
                }
                for (int j = dimentions/2; j<dimentions; j++) {
                    temp[j] = newPopulation.get(i+1).getX()[j];
                    temp2[j] = newPopulation.get(i).getX()[j];
                }
                if (rand.nextDouble() < 0.2) {
                    Arrays.setAll(temp, j -> temp[j]+rabdomDouble(-0.2,0.2));
                    Arrays.setAll(temp2, j -> temp2[j]+rabdomDouble(-0.2,0.2));
                }
                newPopulation.get(i).setX(temp);
                newPopulation.get(i+1).setX(temp2);
            }
        }
    }

    private boolean exit(int i) {
        if(Math.abs(Arrays.stream(bestValue).average().getAsDouble()) - Math.abs(bestValue[i]) < e)
            return true;
//        if (population.get(oldIndex).getValue() < newPopulation.get(newIndex).getValue()) {
//            newPopulation.get(newIndex).setX(population.get(oldIndex).getX());
//            newPopulation.get(newIndex).setValue(population.get(oldIndex).getValue());
//        }
//        if (Math.sqrt(Math.pow(temp,2)-Math.pow(bestValue, 2)) < e) {
//            System.out.println("CHUUUUUUUUUUUUUUJ");
//            return true;
//        } return false;
//        for (int i = 0; i < newPopulation.size(); i++) {
//            if (Math.abs(Math.abs(bestValue)-Math.abs(newPopulation.get(i).getValue())) < e ) {
//                count ++;
//            }
//        }
//        return count == newPopulation.size();
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
        System.out.println("Znaleziono minimum funkcji o wartości: " + newPopulation.get(best).getValue() + " w punkcie (" +  newPopulation.get(best).getX()[0] + ", " + newPopulation.get(best).getX()[1] + ")");
    }
    private void printStep(int best) {
        System.out.println("Aktualna wartość funkcji : " + newPopulation.get(best).getValue() + " w punkcie (" +  newPopulation.get(best).getX()[0] + ", " + newPopulation.get(best).getX()[1] + ")");
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
