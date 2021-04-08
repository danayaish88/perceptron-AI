
public class DataLine {
    private int x;
    private int y;
    private int desiredY;

    public DataLine() {
    }

    public DataLine(int x, int y, int desiredY) {
        this.x = x;
        this.y = y;
        this.desiredY = desiredY;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

	public int getY() {
        return y;
    }
	
	public int getDesiredY() {
		return desiredY;
	}

	public void setDesiredY(int desiredY) {
		this.desiredY = desiredY;
	}
}
