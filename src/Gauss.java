
import java.util.Arrays;

public class Gauss {
    private final double[] x = new double[2];
    private final double[] xNew = new double[x.length];
    private double k = 0.5;             //początkowa wartość kroku
    private final double e = 0.0002;    //dokładość dla gradientu w zakresie <-e,e> | -e < 0 < e

    public Gauss() {
        Arrays.fill(x,2);       // punkt startowy
        Arrays.fill(xNew,2);    //punkt który będzie porównywany ze zmieniającym się punktem startowym
        algorytm();
    }

    private double f(double[] x) {
        //f(x) = 2(x-1.5)^2 + 2xy + (y-0.5)^2 -3
        // sprawdziłem na geogebra.org i wolframalpha.com
        // minimum jest w punkcie (2.5,-2) a wartość wynosi -4.75
        return 2*Math.pow(x[0]-1.5,2) + 2*x[0]*x[1] + Math.pow(x[1]-0.5,2) -3;
    }

    private double pochodnaX1(double[] x){
        return 4*x[0] - 6 + 2*x[1];
    }

    private double pochodnaX2(double[] x){
        return 2*x[0] + 2*x[1] - 1;
    }

    private void algorytm() {
        double temp = k;
        do {
            k = temp;                                                               //przywróć krok do pierwotnej wartości
            while (!(pochodnaX1(xNew) > -e && pochodnaX1(xNew) < e)) {              //zmiana x dopóki f'(x) nie będzie w zakresie <-e,e>
                if (pochodnaX1(xNew) > -e && pochodnaX1(xNew) > e) {                //sprawdzenie czy pochodna po x jest > 0 +/- e (w zakresie (-e,e))
                    xNew[0] -= k;                                                   //przesunięcie na osi jeśli warunek jest spełniony
                    if (xNew[0]==x[0]) k = k / 2;                                   //jeśli punkt się nie poruszył to następuje zmniejszenie kroku
                } else if (pochodnaX1(xNew) < -e && pochodnaX1(xNew) < e) {         //sprawdzenie czy pochodna po x jest < 0 +/- e (w zakresie (-e,e))
                    xNew[0] += k;
                    if (xNew[0]==x[0]) k = k / 2;
                }//w innym wypadku, czyli pochodna znajduje się w zakresie <-e,e> (0 +/- e) zostań w punkcie
            }
            if (f(xNew)<f(x)) {
                x[0] = xNew[0];     //przypisz nową współrzędną do srtarego punktu
            }
            k = temp;                                                               //przywróć krok do pierwotnej wartości
            while (!(pochodnaX2(xNew) > -e && pochodnaX2(xNew) < e)) {              //zmiana y dopóki f'(y) nie będzie w zakresie <-e,e>
                if (pochodnaX2(xNew) > -e && pochodnaX2(xNew) > e) {                //sprawdzenie czy pochodna po y jest > 0 +/- e (w zakresie (-e,e))
                    xNew[1] -= k;                                                   //przesunięcie na osi jeśli warunek jest spełniony
                    if (xNew[1]==x[1]) k = k / 2;                                   //jeśli punkt się nie poruszył to następuje zmniejszenie kroku
                } else if (pochodnaX2(xNew) < -e && pochodnaX2(xNew) < e) {         //sprawdzenie czy pochodna po y jest < 0 +/- e (w zakresie (-e,e))
                    xNew[1] += k;
                    if (xNew[1]==x[1]) k = k / 2;
                }//w innym wypadku, czyli pochodna znajduje się w zakresie <-e,e> (0 +/- e)
            }
            if (f(xNew)<f(x)) {
                x[1] = xNew[1];     //przypisz nową współrzędną do srtarego punktu
            }
        } while (k > e);            //wykonuj dopóki krok jest większy od e
        print();
    }

    private void print() {
        System.out.println("Znaleziono minimum w punkcie X(" + xNew[0] + ", " + xNew[1] + ")");
        System.out.println("Wartość funkcji f(X) = " + f(xNew));
        System.out.println("Przybliżenie wyniku +/- " + (k+e));
    }
}