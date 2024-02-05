import java.util.Arrays;

public class SpadekWWspolrzednych {
    private final double[] x = new double[2];
    private final double k = 0.01;

    public SpadekWWspolrzednych() {
        Arrays.fill(x,1);       //punkt startowy (1,1,1,...)
        algorytm();
    }

    private double f(double[] x) {
        //f(x) = 2(x-1.5)^2 + (y-0.5)^2 -3
        // sprawdziłem na geogebra.org wyznacza poprawnie, tylko algorytm jest słaby dla bardiej skomplikowanych obliczeń
        return 2*Math.pow(x[0]+1.5,2) + Math.pow(x[1]-0.5,2) -3;
    }

    private void algorytm() {
        double[] temp = new double[x.length];
        int count = 0;
        do {
            // algorytm będzie się wykonywać tak długo dopóki licznik będzie mniejszy od ilości współrzędnych punktu
            for (int i = 0; i < x.length; i++) {
                Arrays.setAll(temp, j -> x[j]);
                x[i] += k;                      //krok w jedną stronę na osi
                if (f(x) < f(temp)) {
                    count = 0;                  //jeśli po kroku wartość funkcji jest lepsza ustaw licznik na 0 i przejdź do kolejnej współrzędnej
                    continue;
                }
                x[i] -= 2 * k;                  //krok w drugą stronę na osi
                if (f(x) < f(temp)) {
                    count = 0;                  //jeśli po kroku wartość funkcji jest lepsza ustaw licznik na 0 i przejdź do kolejnej współrzędnej
                    continue;
                }
                x[i] = temp[i];                 //jeśli nie wykonano kroku w żadną stronę na osi jednej współrzędnej, to
                count++;                        //zwiększ licznik i przejdź do kolejnej współrzędnej
            }
        } while (count < x.length);             //jeśli nie wykonano kroku w żadną ze stron na żadnej osi współrzędnych (licznik równy ilości współrzędnych)
        print();                                //wypisz wynik i zakończ program
    }

    private void print() {
        System.out.println("Znaleziono minimum w punkcie: (" + x[0] +", "+ x[1]+")");
        System.out.println("Wartość funkcji w punkcie: f(x) = " + f(x));
        System.out.println("Wynik z dokładnością do " + k);
    }
}
