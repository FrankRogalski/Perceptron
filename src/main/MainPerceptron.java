package main;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MainPerceptron extends Application{
	private Canvas can;
	private GraphicsContext gc;
	private Timeline tl_draw;
	
	private Perceptron brain = new Perceptron(3);
	private Point[] points = new Point[100];
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void init() throws Exception {
		tl_draw = new Timeline(new KeyFrame(Duration.millis(1000.0 / 60.0), e -> {
			draw();
		}));
		tl_draw.setCycleCount(Timeline.INDEFINITE);
		tl_draw.play();
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		Pane root = new Pane();
		Scene scene = new Scene(root, 800, 800);
		
		stage.setTitle("Perceptron");
		
		can = new Canvas(scene.getWidth(), scene.getHeight());
		gc = can.getGraphicsContext2D();
		
		root.getChildren().add(can);
		
		scene.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				for (int i = 0; i < points.length; i++) {
					final double[] inputs = {points[i].getX(), points[i].getY(), points[i].getBias()};
					brain.train(inputs, points[i].getLabel());
				}
			}
		});
		
		stage.setScene(scene);
		stage.show();
		
		setup();
	}
	
	public static double f(double x) {
		return 20 * x + 0.2;
	}
	
	private void setup() {
		for (int i = 0; i < points.length; i++) {
			points[i] = new Point(gc);
		}
	}
	
	private void draw() {
		final int s = 10;
		gc.clearRect(0, 0, can.getWidth(), can.getHeight());

		Point p1 = new Point(gc, -1, f(-1));
		Point p2 = new Point(gc, 1, f(1));
		gc.strokeLine(p1.pixelX(), p1.pixelY(), p2.pixelX(), p2.pixelY());
		
		Point p3 = new Point(gc, -1, brain.guessY(-1));
		Point p4 = new Point(gc, 1, brain.guessY(1));
		gc.strokeLine(p3.pixelX(), p3.pixelY(), p4.pixelX(), p4.pixelY());
		
		for (int i = 0; i < points.length; i++) {
			points[i].show();
			
			final double[] inputs = {points[i].getX(), points[i].getY(), points[i].getBias()};
			
			int guess = brain.guess(inputs);
			if (guess == points[i].getLabel()) {
				gc.setFill(Color.GREEN);
			} else {
				gc.setFill(Color.RED);
			}
			gc.fillOval(points[i].pixelX() - s * 0.5, points[i].pixelY() - s * 0.5, s, s);
		}
	}
	
	public final static double map(double value, double min, double max, double nMin, double nMax) {
		return ((value - min) / (max - min)) * (nMax - nMin) + nMin;
	}
}