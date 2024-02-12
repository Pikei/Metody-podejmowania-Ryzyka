package pso;

public class Particle {
    private double[] x;
    private double[] personalBest;
    private double[] globalBest;
    private double[] vector;
    private double value;

//    public Particle(double[] x, double[] personalBest, double[] globalBest, double[] vector, double value) {
//        this.x = x;
//        this.personalBest = personalBest;
//        this.globalBest = globalBest;
//        this.vector = vector;
//        this.value = value;
//    }

    public Particle(double[] x, double[] personalBest, double value) {
        this.x = x;
        this.personalBest = personalBest;
        this.value = value;
    }

    public double[] getX() {
        return x;
    }

    public void setX(double[] x) {
        this.x = x;
    }

    public double[] getPersonalBest() {
        return personalBest;
    }

    public void setPersonalBest(double[] personalBest) {
        this.personalBest = personalBest;
    }

    public double[] getGlobalBest() {
        return globalBest;
    }

    public void setGlobalBest(double[] globalBest) {
        this.globalBest = globalBest;
    }

    public double[] getVector() {
        return vector;
    }

    public void setVector(double[] vector) {
        this.vector = vector;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
