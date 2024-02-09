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
        switch (input.next()) {
            case "1" -> new MonteCarlo();
            case "2" -> new HookeJeeves();
            case "3" -> new SpadekWWspolrzednych();
            case "4" -> new Gauss();
            case "5" -> new NajszybszySpadek();
        }
    }
}