package pso;

import java.util.Arrays;
import java.util.Random;

public class PSO {
    private final int dimensions = 2;
    private final int swarmSize = 10000;
    private final Particle[] swarm = new Particle[swarmSize];
    private final double[] c = new double[dimensions];
    private final double w = 0.8;
    private final double e = 0.002;
    private final Random rand = new Random();

    public PSO() {
        Arrays.fill(c, 0.1);
        generateParticles();
        do {
                findGlobalBest();
                findPersonalBest();
                moveParticle();
//                printStep();
        } while (!exit());
        printResult();
    }

    private double f(double[] x) {
        //(x-3.14)^2+(y-2.72)^2+sin(3*x+1.41)+sin(4*y-1.73) | funkcja ze strony https://machinelearningmastery.com/a-gentle-introduction-to-particle-swarm-optimization/
//        return Math.pow(x[0] - 3.14, 2) + Math.pow(x[1] -2.72, 2) + Math.sin(3*x[0]+1.41) + Math.sin(4*x[1]-1.73);
        return 2*Math.pow(x[0]-1.5,2) + 2*x[0]*x[1] + Math.pow(x[1]-0.5,2) -3;

    }

    private void generateParticles() {
        for (int i = 0; i < swarmSize; i++) {
            double[] initialPosition = new double[dimensions];
            Arrays.setAll(initialPosition, j -> randomDouble(-100, 100));
            swarm[i] = new Particle(initialPosition, initialPosition, f(initialPosition));
        }
    }

    private void findPersonalBest() {
        double[] best = new double[dimensions];
        for (int i = 0; i < swarmSize; i++) {
            for (int j = 0; j < best.length; j++) {
                best[j] = swarm[i].getPersonalBest()[j];
            }
            if (swarm[i].getValue() < f(best)) {
                for (int j = 0; j < best.length; j++) {
                    best[j] = swarm[i].getX()[j];
                }
                swarm[i].setPersonalBest(best);
            }
        }

    }private void findGlobalBest() {
        double[] best = new double[dimensions];
        if (swarm[0].getGlobalBest() == null) {
            for (int i = 0; i < best.length; i++) {
                best[i] = swarm[0].getX()[i];
            }
        } else {
            for (int i = 0; i < best.length; i++) {
                best[i] = swarm[0].getGlobalBest()[i];
            }
        }
        for (int i = 0; i < swarmSize; i++) {
            if (swarm[i].getValue() < f(best)) {
                for (int j = 0; j < best.length; j++) {
                    best[j] = swarm[i].getX()[j];
                }
                for (int j = 0; j < swarm.length; j++) {
                    swarm[j].setGlobalBest(best);
                }
            }
        }

    }

    private void moveParticle() {
        for (int i = 0; i < swarmSize; i++) {
            double[] newPosition = new double[dimensions];
            swarm[i].setVector(v(i));
            for (int j = 0; j < newPosition.length; j++) {
                newPosition[j] = swarm[i].getX()[j] + swarm[i].getVector()[j];
            }
            swarm[i].setX(newPosition);
            swarm[i].setValue(f(newPosition));
        }
    }

    private double[] v(int index) {
        double[] v = new double[dimensions];
        if (swarm[index].getVector() == null){
            for (int i = 0; i < v.length; i++) {
                v[i] = w*2 + c[i] * rand.nextDouble(1) * (swarm[index].getGlobalBest()[i] - swarm[index].getX()[i]) + c[i] * rand.nextDouble(1) * (swarm[index].getGlobalBest()[i] - swarm[index].getX()[i]);
            }
            return v;
        }
        for (int i = 0; i < v.length; i++) {
            v[i] = w * swarm[index].getVector()[i] + c[i] * rand.nextDouble(1) * (swarm[index].getGlobalBest()[i] - swarm[index].getX()[i]) + c[i] * rand.nextDouble(1) * (swarm[index].getGlobalBest()[i] - swarm[index].getX()[i]);
        }
        return v;
    }

    private boolean exit() {
        for (int i = 0; i < swarm.length; i++) {
            for (int j = 0; j < dimensions; j++) {
                if (Math.abs(Math.abs(swarm[i].getX()[j]) - Math.abs(swarm[i].getGlobalBest()[j])) > e) return false;
            }
        }
        return true;
    }
    private double randomDouble(double min, double max) {
        return rand.nextDouble() * (max - min) + min;
    }

    private void printStep() {
        if (f(swarm[0].getGlobalBest()) < 0)
            System.out.println();
        System.out.println("Aktualna przybliżenie wyniku : " + f(swarm[0].getGlobalBest()) + " w punkcie (" +  swarm[0].getGlobalBest()[0] + ", " + swarm[0].getGlobalBest()[1] + ")");
    }

    private void printResult() {
        System.out.println("Znaleziono minimum w punkcie X(" + roundResult(swarm[0].getGlobalBest()[0], 6) + ", " + roundResult(swarm[0].getGlobalBest()[1], 6) + ")");
        System.out.println("Wartość funkcji f(X) = " + roundResult(f(swarm[0].getGlobalBest()),6));
    }

    private double roundResult(double value, int position) {        //metoda ta zaokrągla podaną liczbę ułamkową do podanego miejsca po przecinku (position)
        int number = (int) value;                                   //może i wygląda paskudnie ale działa jeszcze gorzej (wsm to działa poprawnie)
        double result = value - number;                             //zgodnie z zasadą w matematyce jeśli ostatnia liczba >= 5 to zaokrąglamy w górę
        double temp = (int)((value - number) * Math.pow(10,position +1));       //jest to jednorazowa wariacja takiego zaokrąglania więcej nie robię :)
        double temp2 = (int)(temp/ 10);                                         //niepotrzebnie utrudniam sobie życie :'(
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
DZIAŁANIE ALGORYTMU:
1. wylosowanie początkowych współrzędnych dla N cząsteczek
2. obliczenie wartości funkcji dla każdej cząsteczki i zapamiętanie jej jako najlepszą do tej pory dla danej cząsteczki
3. wybór spóśród najlepszych personalnych wartości najlepszej globalnej wartości
4. obliczenie wekrora dla każdej cząsteczki ze wzoru: V(i+1) = wV(i) + c1r1(pbest(X(i)) - X(i)) + c2r2(gbest - X(i)), gdzie:
    V(i+1) = wV(i) + c1r1(pbest(X(i)) - X(i)) + c2r2(gbest(X(i)) - X(i)), gdzie:
        X(i) - aktualne położenie cząsteczki
        V(i) - aktualny wektor cząsteczki
        V(i+1) - wektor dla cząsteczki, który po dodaniu do X(i) zwróci kolejne położenie cząsteczki
        pbest() - dotychczasowe najlepsze rozwiązanie znalezione przez cząsteczkę
        gbest - dotychczasowe najlepsze rozwiązanie znalezione przez wszystkie cząsteczki
        r1 , r2 - liczby losowe z zakresu <0,1>
        w, c1, c2 - stałe liczby określone w ramach algorytmu (tutaj kolejno w=0.8, c1=c2=0.1)
5. obliczenie wartości funkcji, znalezienie nowej najlepszej pozycji personalenej dla każdej cząsteczki i nowej najlepszej pozycji globalnej
6. sprawdzenie warunku stopu:
    jeśli odległość między wszystkimi cząsteczkami jest mniejsza niż zakładana tolerancja błędu
        zwróć gbest jako rozwiązanie
    w przeciwnym razie idź do punktu 3.
 */
