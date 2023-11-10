package idiotoodporne;

import java.util.Random;

public class MC {
    //Wersja algorytmu napisana po najmniejszej lini oporu.
    // Dosłownie zapamiętanie najlepszej pozycji jednego punktu po n-iteracjach.
    //Prościej się nie da :)
    public MC() {
        Random random = new Random();
        double x = random.nextDouble(500);
        double y = random.nextDouble(500);
        double z = random.nextDouble(500);
        double j = random.nextDouble(100);
        double best = f(x, y, z,j);
        for (int i = 0; i < 100000; i++) {
            x = random.nextDouble(500);
            y = random.nextDouble(500);
            z = random.nextDouble(500);
            j = random.nextDouble(100);
            if (f(x, y, z,j)<best){
                best = f(x, y, z,j);
            }
        }
        System.out.println("wartość: " + best);
        System.out.println("współrzędne ("+x+", "+y+", "+z+", "+j+")");
    }

    private double f(double x, double y, double z, double j) {
        return Math.pow(z,3) + 3*Math.pow(j,2) - 2*x*Math.pow(y,4);
    }
}