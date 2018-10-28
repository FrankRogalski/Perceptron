package main;

import java.util.Random;

public class Perceptron {
	private double[] weights;
	
	public Perceptron(int n) {
		weights = new double[n];
		Random r = new Random();
		for (int i = 0; i < weights.length; i++) {
			weights[i] = r.nextDouble() * 2.0 - 1.0;
		}
	}
	
	public int guess(double inputs[]) {
		double sum = 0;
		for (int i = 0; i < weights.length; i++) {
			sum += inputs[i] * weights[i];
		}
		return (int) Math.signum(sum);
	}
	
	public void train(double inputs[], int target) {
		final int guess = guess(inputs);
		final int error = target - guess;
		final double learningRate = 0.1;
		for (int i = 0; i < weights.length; i++) {
			weights[i] += error * inputs[i] * learningRate;
		}
	}
	
	public double guessY(double x) {
		double w0 = weights[0];
		double w1 = weights[1];
		double w2 = weights[2];
		return -(w2 / w1) - (w0 / w1) * x;
	}
}