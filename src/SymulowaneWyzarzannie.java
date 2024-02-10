import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

public class SymulowaneWyzarzannie {

    private final double[] x = new double[2];
    private final double[] xNew = new double[2];
    private double t = 1000;
    private final double a = 0.03;
    private final double e = 0.00002;
    private double delta;
    private final Random rand = new Random();

    public SymulowaneWyzarzannie() {
        Arrays.fill(x,2);
        do {
            for (int i = 0; i < 10000; i++) {
                //wybór sąsiada w zakresie x-t,x+t x jest tutaj medianą (poniżej)
                IntStream.range(0, xNew.length).forEach(j -> xNew[j] = randomize(x[j] - t, x[j] + t));
                delta = f(xNew) - f(x);
                if (delta < 0) {
                    Arrays.setAll(x, j -> xNew[j]);
//                    printStep();
                } else if(rand.nextDouble(1) < Math.exp(-delta/t)){
                    Arrays.setAll(x, j -> xNew[j]);
//                    printStep();
                }
            }
            t *= 1-a;   //zmniejszanie temperatury


        } while (Math.abs(0-t)>e);
        printResult();
    }

    private double f(double[] x) {
        //znana i lubiana funkcja z poprzednich zadań
        return 2*Math.pow(x[0]-1.5,2) + 2*x[0]*x[1] + Math.pow(x[1]-0.5,2) -3;
    }

    private double randomize(double min, double max) {
        return rand.nextDouble() * (max - min) + min;
    }

    private void printResult() {
        System.out.println("Znaleziono minimum funkcji o wartości: " + f(x) + " w punkcie (" + x[0] + ", " + x[1] + ")");
        System.out.println("Zaokrąglenie wyniku " + roundResult(f(x), 2));
        System.out.println("W punkcie (" + roundResult(x[0], 2) + ", " + roundResult(x[1], 2) + ")");
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

    private void printStep() {
        System.out.println();
        System.out.println("Aktualne położenie (" + x[0] + ", " + x[1] + ")");
        System.out.println("Aktualne przybliżenie wyniku: " + f(x));
        System.out.println("Aktualna temperatura = " + t);
        System.out.println();
    }
    /*
Wszystkie algorytmy w necie są do dupy
jak ja to rozumiem:
do{
    for (int i=0; i<L; i++) {
        losuję nowy punkt xNew sąsiedni (x jest medianą) dla punktu startowego x, w moim przypadku losuję liczbę z zakresu (x-t,x+t)
        δ (delta) = f(xNew) - f(x)
        if δ < 0 {
            x=xNew
        } else if (random(0,1) < e^(-δ/t)) {
            x=xNew
        }
    t = t*1-alfa
}while (warunek stopu) ja se wymyśliłem że ma być |0-t|< wcześniej ustalonej tolerancji
 */
}