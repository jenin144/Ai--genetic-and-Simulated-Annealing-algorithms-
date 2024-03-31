
 //      try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\HP ProBook A9\\eclipse-workspace\\Ai-test\\src\\pack.txt"))) {
public class City {
	
    private int x;
    private int y;

    public City(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "City [x=" + x + ", y=" + y + "]";
    }

	public int getWeight() {
		// TODO Auto-generated method stub
		return 0;
	}
}