
public class Package extends City {
    private int weight;

    public Package(int x, int y, int weight) {
        super(x, y);
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }
}
