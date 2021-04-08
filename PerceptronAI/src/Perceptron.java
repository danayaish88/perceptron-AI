import java.awt.Graphics;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Random;

public class Perceptron {

	public  static Double threshold = 5000.0;
	public Double learningRate = 0.1;
	public Integer iterations = 100;
	public ArrayList<DataLine> linesOfData;
	public double w1, w2;
	public double error ;
	public int tN, tP = 0;
	
	
	public Perceptron(ArrayList<DataLine> linesOfData, Double learningRate, Integer iterations) {
		this.learningRate = learningRate;
		this.iterations = iterations;
		this.linesOfData = linesOfData;
		learn();
	}
	
	private void learn() {
		Random r = new Random();
		w1 =( r.nextDouble() - 0.5) * 200;							//range from -0.5, 0.5
		w2 = (r.nextDouble() - 0.5) * 200;									//range from -0.5, 0.5
		for(int j = 0; j < iterations / linesOfData.size(); j++) {
			for(int i = 0; i < linesOfData.size(); i++) {
				int x = linesOfData.get(i).getX() ;
				int y = linesOfData.get(i).getY();
				int yDesired = linesOfData.get(i).getDesiredY();
				double yActual = activationFunc(bigX(x, w1, y, w2));
				error = yDesired - yActual;
				w1 += deltaWeight(x, error);	
				w2 += deltaWeight(y, error);
				threshold += deltaThreshold();
			}
			
		}
		System.out.print(w1 + " " + w2);
	}

	private Double deltaThreshold() {
		return learningRate * -1 * error * 250;
	}

	public void drawLine(Graphics g, int width) {
		int x1 = width;
		int y1 = (int) ((threshold - (width * w1)) / w2);
		int x2 = 10;
		int y2 = (int) ((threshold - (10 * w1 )) / w2);
		System.out.println("(" + x1 + "," + y1 + ") , (" + x2 + "," + y2 + ")");
		g.drawLine(x1, y1, x2, y2);
		
	}

	public Double bigX(int x, Double w1, int y, Double w2) {
		return (x * w1) + (y * w2) - threshold;
	}
	
	public int activationFunc(double x) {
		return x < 0 ? -1 : 1 ;
	}
	
	public Double deltaWeight(int x, Double error) {
		return learningRate * x * error;
	}

	public int testNode(int x2, int y2) {
		return activationFunc(bigX(x2, w1, y2, w2));
	}
	
	public double performance() {
		for(int i = 0; i < linesOfData.size(); i++) {
			int desiredY = linesOfData.get(i).getDesiredY();
			int x = linesOfData.get(i).getX();
			int y = linesOfData.get(i).getY();
			if(desiredY == -1) {
				if (activationFunc(bigX(x, w1, y, w2)) == -1) 
					tN++;
			}else if(desiredY == 1) {
				if (activationFunc(bigX(x, w1, y, w2)) == 1) 
					tP++;
			}
		}
		//NumberFormat defaultFormat = NumberFormat.getPercentInstance();
		//defaultFormat.setMinimumFractionDigits(1);

		return (((double)(tN + tP)) / linesOfData.size());
	}
}
