import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;

import javax.swing.*;

public class Marker extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1535969740922535931L;
	private Rectangle frame;
	private static int width = 12;
	private static int height = 12;
	private Point2D position;
	private ArrayList<BezierCurve> bezierCurves = new ArrayList<BezierCurve>();
	
	private boolean isPressed = false;
	
	public Marker(Point2D position) {
		super(true);
		setPosition(position);
	}

	public Rectangle getFrame() {
		return frame;
	}

	private void setFrame(Rectangle frame) {
		this.frame = frame;
	}
	
	public Point2D Center() {
		return new Point2D.Float((float)getFrame().getCenterX(), (float)getFrame().getCenterY());
	}

	public void Draw(Graphics2D graphic) {
		Color color = isPressed?Color.GRAY:Color.BLACK;
		graphic.setPaint(color);
		graphic.fill(new Ellipse2D.Double(getFrame().x, getFrame().y, getFrame().width, getFrame().height));
	}

	public Point2D getPosition() {
		return position;
	}

	public void setPosition(Point2D position) {
		this.position = position;
		setFrame(new Rectangle((int)position.getX(), (int)position.getY(),width, height));
	}
	
	public boolean isPressed() {
		return isPressed;
	}

	public void setPressed(boolean isPressed) {
		this.isPressed = isPressed;
	}

	public void PerformDrag(Point point) {
		Point2D newPosition = new Point2D.Float(point.x-width/2, point.y-height/2);
		setPosition(newPosition);
		for (BezierCurve curve : bezierCurves) {
			curve.drawCurve();
		}
	}

	public void addBezierCurve(BezierCurve bezierCurve) {
		bezierCurves.add(bezierCurve);
	}
}
