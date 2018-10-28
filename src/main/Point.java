package main;

import java.util.Random;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Point {
	private GraphicsContext gc;
	private double x, y;
	private int label;
	private double bias = 1;
	
	public Point(GraphicsContext gc, double x, double y) {
		constructor(gc, x, y);
	}
	
	public Point(GraphicsContext gc) {
		Random r = new Random();
		double x = r.nextDouble() * 2 - 1;
		double y = r.nextDouble() * 2 - 1;
		constructor(gc, x, y);
	}
	
	public void constructor(GraphicsContext gc, double x, double y) {
		this.gc = gc;
		this.x = x;
		this.y = y;
		
		double lineY = MainPerceptron.f(x);
		if (y > lineY) {
			label = 1;
		} else {
			label = -1;
		}
	}
	
	
	
	public void show() {
		final int s = 20;
		if (label == 1) {
			gc.setFill(Color.BLUE);
		} else {
			gc.setFill(Color.YELLOW);
		}
		
		double px = pixelX();
		double py = pixelY();
		
		gc.fillOval(px - s * 0.5, py - s * 0.5, s, s);
	}
	
	public double pixelX() {
		return MainPerceptron.map(x, -1, 1, 0, gc.getCanvas().getWidth());
	}
	
	public double pixelY() {
		return MainPerceptron.map(y, -1, 1, gc.getCanvas().getHeight(), 0);
	}

	public int getLabel() {
		return label;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getBias() {
		return bias;
	}
}