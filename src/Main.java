import algorytmgenetyczny.Genetyczny;
import pso.PSO;
import steffensen.Steffensen;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Ahh shit. Here we go again...");
        System.out.println("1. Monte Carlo");
        System.out.println("2. Hooke Jeeves");
        System.out.println("3. Spadek względem współrzędnych");
        System.out.println("4. Gauss-Seidel");
        System.out.println("5. Najszybszy spadek");
        System.out.println("6. Sumulowane wyżarzanie");
        System.out.println("7. Algorytm genetyczny");
        System.out.println("8. PSO");
        System.out.println("9. Algorytm Różnicowy");
        System.out.println("10. Steffensen");
        switch (input.next()) {
            case "1" -> new MonteCarlo();
            case "2" -> new HookeJeeves();
            case "3" -> new SpadekWWspolrzednych();
            case "4" -> new Gauss();
            case "5" -> new NajszybszySpadek();
            case "6" -> new SymulowaneWyzarzannie();
            case "7" -> new Genetyczny();
            case "8" -> new PSO();
            case "9" -> new Roznicowy();
            case "10" -> new Steffensen();
        }
    }
}