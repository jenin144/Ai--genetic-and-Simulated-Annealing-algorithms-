
public class Operations {
    public static double probability(double f1, double f2, double temp) { // f1 and f2 cities 
        if (f2 < f1) return 1;
        return Math.exp((f1 - f2) / temp); // prob e^e/T
    }

    public static double distance(City city1, City city2) {
        int xDist = Math.abs(city1.getX() - city2.getX());
        int yDist = Math.abs(city1.getY() - city2.getY()); 
        return Math.sqrt(xDist * xDist + yDist * yDist); //Euclidean distance formula)   √((x2-x1)2 + (y2-y1)2)
    }
}
