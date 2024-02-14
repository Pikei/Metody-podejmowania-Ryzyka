package steffensen;

public class Steffensen {
    private final double[] x = new double[] {2, 2};

    public Steffensen() {
        System.out.println(f(x));
//        System.out.println(g(0));
//        System.out.println(g(1));
//        double fPrimX = ;
//        System.out.println(fPrimX);
//        double fPrimY = ;
//        System.out.println(fPrimY);
        x[0] = x[0] - pochodnaX1(x)/g(0);
        System.out.println(x[0]);
        x[1] = x[1] - pochodnaX2(x)/g(1);
//        System.out.println(x[0]);
        System.out.println(x[1]);
        System.out.println(f(x));

    }

    private double f(double[] x) {
        return 2*Math.pow(x[0]-1.5,2) + 2*x[0]*x[1] + Math.pow(x[1]-0.5,2) -3;
    }

    private double pochodnaX1(double[] x){
        return 4*x[0] - 6 + 2*x[1];
    }

    private double pochodnaX2(double[] x){
        return 2*x[0] + 2*x[1] - 1;
    }

    private double g(int index) {
        switch (index) {
            case 0 -> {
                double[] temp = new double[] {x[0]+pochodnaX1(x), x[1]};
                return (pochodnaX1(temp) - pochodnaX1(x)) / pochodnaX1(x);
            }
            case 1 -> {
                double[] temp = new double[] {x[0], x[1]+pochodnaX2(x)};
                double v = (pochodnaX2(temp) - pochodnaX2(x)) / pochodnaX2(x);
                return v;
            }

        }
        return 0;
    }

}

/*
Jest to metoda optymalizacji kroku
wyraża się za pomocą wzoru
Xi+1 = Xi - f(Xi)/g(Xi)
g(x) = ( f(x + f(x)) - f(x) / f(x) )
PRZYKŁAD:
    Mam moją znaną i lubianą funkcję f(x,y) = 2(x-1.5)^2 + 2xy + (y-0.5)^2 -3
    zakładam że mój punkt startowy = (2,2)
    aby policzyć krok dla płaszczyzny X muszę policzyć najpierw pochodną cząstkową w punkcie
        f'(x) = 4(x-1.5) + 2y = 4x - 6 + 2y = 4*2 - 6 + 2*2 = 8-6+4 = 6
    to będzie moja wyjściowa funkcja w poszukiwaniu optymalnego kroku
    teraz obliczam g(x)
        g(x) = (f(2+6)-6)/6 = ((4*8-6+2*2)-6)/6 = 4
    teraz mogę podstawić wartości do wzoru:
        X1 = X0 - f(X0)/g(X0) = 2 - 6/4 = 1/2
    zatem nowa współrzędna to (0.5 , 2)
    mogę przystąpić do obliczania wartości kroku dla y
        f'(y) = 2x + 2*(y-0.5) = 2*0.5 + 2*(2-0.5) = 1 + 4 -1.5 = 3.5
        g(y) = (f(2+3.5)-3.5)/3.5 = 15/7
        Y1 = Y0 - f(Y0)/g(Y0) = 2 - 8/2 = -2
    nowy punkt to (2.5 , -2)
    f(X0, Y0) = 2(2-1.5)^2 + 2*2*2 + (2-0.5)^2 -3 = 31/4 = 7.75
    f(X1, Y1) = 2(2.5-1.5)^2 + 2*2.5*0 + (0-0.5)^2 -3 = -19/4 = -0,75



    chyba nie można używać steffensena do funkcji 2 zmiennych
    zmieniam funkcję na 2(x-1.5)^2 + (y-0.5)^2 -3
    Podobna ale będzie bez 2 zmiennych pochodna
    więc tak:
        P0 = (2,2)
        f(x,y) = 2(x-1.5)^2 + (y-0.5)^2 -3 = 2(2-1.5)^2 + (2-0.5)^2 -3 = -0,25 v -1/4

        f'(x) =  4(x-1.5)
        f'(X0) = 4(2-1.5) = 2
        g(x) = (f(x + f(x)) - f(x)) / f(x)
        g(X0) = (f(2 + f(2)) - f(2)) / f(2) = (f(2+2) - f(2))/f(2)
        g(X0) = (4(4-1.5) - 4(2-1.5))/4(2-1.5) = (10-2)/2 = 8/2 = 4
        X1 = X0 - (f(X0)/g(X0)) = 2 - 2/4 = 1,5

        f'(y) =  2*(y-0.5)
        f'(Y0) = 2*(2-0.5) = 3
        g(Y0) = (f(2 + f(2)) - f(2)) / f(2) = (f(2+3) - f(2))/f(2) = (f(5) - f(2))/f(2)
        g(Y0) = (2*(5-0.5) - 2*(2-0.5)) / 2*(2-0.5) = (9-3)/3 = 6/3 = 2
        Y1 = Y0 - (f(Y0)/g(Y0)) = 2 - 3/2 =  0.5  v  1/2

        P1 = (1.5 , 0.5)
        f(1.5 , 0.5) = 2(1.5-1.5)^2 + (0.5-0.5)^2 -3 = 0-0-3 = -3


 */