import java.awt.*;
import java.awt.geom.*;
import java.util.*;

public class BezierCurve {
	private Marker markersArray[] = new Marker[4];
	public Marker[] getMarkersArray() {
		return markersArray;
	}

	private ArrayList<Point2D> bezierPoints = new ArrayList<Point2D>();
	
	public BezierCurve(Marker[] markersArray) {
		this.markersArray = markersArray;
		for (Marker marker : markersArray) {
			marker.addBezierCurve(this);
		}
		drawCurve();
	}
	
	public void drawCurve() {
		bezierPoints.clear();
		for (float i = 0; i < 1; i+=0.01) {
			bezierPoints.add(BezierFunction(i));
		}
	}

	private Point2D BezierFunction(float t) {
        float c0 = (1 - t) * (1 - t) * (1 - t);
        float c1 = (1 - t) * (1 - t) * 3 * t;
        float c2 = (1 - t) * t * 3 * t;
        float c3 = t * t * t;
        float x = c0 * (float)markersArray[0].Center().getX() + c1 * (float)markersArray[1].Center().getX() + c2 * (float)markersArray[2].Center().getX() + c3 * (float)markersArray[3].Center().getX();
        float y = c0 * (float)markersArray[0].Center().getY() + c1 * (float)markersArray[1].Center().getY() + c2 * (float)markersArray[2].Center().getY() + c3 * (float)markersArray[3].Center().getY();
        return new Point2D.Float(x,y);
    }

	public void Draw(Graphics2D graphic) {
		graphic.setRenderingHint(
		        RenderingHints.KEY_ANTIALIASING,
		        RenderingHints.VALUE_ANTIALIAS_ON);
		
		graphic.setPaint(Color.GRAY);
		graphic.setStroke(new BasicStroke(1));
		for (int i = 0; i < markersArray.length-1;i++ ) {
			graphic.draw(new Line2D.Float(markersArray[i].Center(), markersArray[i+1].Center()));
		}
		
		graphic.setPaint(Color.BLACK);
		graphic.setStroke(new BasicStroke(3));
		for(int i = 0;i < bezierPoints.size()-1; i++) {
			graphic.draw(new Line2D.Float(bezierPoints.get(i), bezierPoints.get(i+1)));
		}
		
		for (Marker marker : markersArray) {
			marker.Draw(graphic);
		}
	}

}
