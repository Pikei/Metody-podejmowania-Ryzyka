package hookejeeves;

import java.util.Arrays;
import java.util.stream.IntStream;

public class HookeJeeves {
    public HookeJeeves() {
        algorytm();
    }

    private final double[] xStart = new double[4];          //punkt startowy, deklaracja wymiarów (x,y,z,...)
    private final double[] x = new double[xStart.length];   //punkt, który będzie się poruszać, tyle samo współrzędnych co punkt startowy
    private double k = 1.5;                                 //krok
    private final double e = 0.02;                          //dokładność rozwiązania

    private double f(double[] x) {                          //funkcja
        return IntStream.range(0, x.length).mapToDouble(i -> (i + 2) * Math.pow(x[i], (i))).sum();
    }

    private void algorytm() {
        Arrays.fill(xStart, 1);                 //początek z punktu (1,1,1,...)
        Arrays.setAll(x, i -> xStart[i]);
        do {
            krokProbny();                           //wykonanie kroku próbnego
            krokRoboczy();                          //wykonanie kroku roboczego
            if (f(x) < f(xStart))
                Arrays.setAll(xStart, i -> x[i]);   //jeśli po wykonaniu kroku punkt jest lepszy to zapisz jego wartość jako aktualną
            print();                                //wypisanie aktualnego położenia
        } while (k > e);                            //pętla będzie się wykonywać tak długo, dopóki krok będzie większy od e
        printResult();                              //wypisanie wyniku końcowego
    }

    private void krokProbny() {
        double v = k;                               //zapisz aktualną wartość kroku
        for (int i = 0; i < x.length; i++) {        //dla wszystkich wymiarów
            x[i] += k;                              //zmień współrzędną osi o krok
            if (f(x) < f(xStart)) {                 //jeśli nowy punkt jest lepszty niż poprzedni
                k = v;                              //przywróć poprzednią wartość kroku
                continue;                           //przejdź do kolejnej współrzędnej na osi
            }
            x[i] -= 2 * k;                          //jeśli zwiększenie współrzędnej nie było lepsze to zmniejsz współrzędną o krok
            if (f(x) < f(xStart)) {                 //jeśli nowy punkt jest lepszty niż poprzedni
                k = v;                              //przywróć poprzednią wartość kroku
                continue;                           //przejdź do kolejnej współrzędnej na osi
            }
            x[i] = xStart[i];                       //jeśli krok w żadną ze stron nie był pomyślny przywróć poprzednią wartość współrzędnej
            if (k > e) {                            //jeśli krok jest większy niż e
                k *= 0.2;                           //zmniejsz krok
                i--;                                //ponów dla tej samej współrzędnej powyższe działania ze zmniejszonym krokiem
            } else k = v;                           //jeśli krok jest mniejszy niż e, przywróć początkową wartość kroku i przejdź do następnej współrzędnej
        }
    }

    private void krokRoboczy() {
        double v = k;                                                   //zapisz aktualną wartość kroku
        double[] tempArray = new double[x.length];                      //utwórz tymczasowy punkt, o współrzędnych x
        Arrays.setAll(tempArray, i -> x[i]);
        Arrays.setAll(x, i -> xStart[i] + v * (x[i] - xStart[i]));      //wykonaj krok po wektorze o długości kroku
        if (f(x) < f(tempArray)) k *= 1.25;                             //jeśli krok był udany zwiększ wartość kroku
        else {
            Arrays.setAll(x, i -> tempArray[i]);                        //jeśli nie przywróć poprzednie współrzędne
            k *= 0.2;                                                   //zmniejsz wartość kroku
        }
    }

    private void print() {
        System.out.println("==========================");
        System.out.print("aktualny punkt: [");
        for (double v : x) {
            System.out.print(v + " ");
        }
        System.out.print("]\n");
        System.out.println("aktualne przybliżenie wyniku: " + f(x));
        System.out.println("aktualny krok: " + k);
        System.out.println("==========================");
    }

    private void printResult() {
        System.out.println("==========WYNIK===========");
        System.out.print("Znaleziono minimum funkcji w punkcie: [");
        for (double v : x) {
            System.out.print(v + " ");
        }
        System.out.print("]\n");
        System.out.println("Wartość funkcji wynosi: " + f(x));
        System.out.println("==========================");
    }
}