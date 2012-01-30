import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

public class MainView extends JPanel implements MouseListener,
		MouseMotionListener, ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8680254835940841879L;

	private ArrayList<BezierCurve> curvesList = new ArrayList<BezierCurve>();
	private Marker activeMarker;
	private Image bcgImage = null;

	public MainView() throws HeadlessException {
		super(true);
		setLayout(null);

		addMouseListener(this);
		addMouseMotionListener(this);

	}

	private void setBackgroundImage(String path) {
		bcgImage = new ImageIcon(path).getImage();
		repaint();
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		Graphics2D graphic = (Graphics2D) g;
		// graphic.clearRect(0, 0, getWidth(), getHeight());
		graphic.setPaint(Color.BLACK);

		if (bcgImage != null) {
			graphic.drawImage(bcgImage, 0, 0, null);
		}

		for (BezierCurve curve : curvesList) {
			curve.Draw(graphic);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		for (BezierCurve curve : curvesList) {
			for (Marker marker : curve.getMarkersArray()) {
				if (marker.getFrame().contains(e.getPoint())) {
					activeMarker = marker;
					activeMarker.setPressed(true);
				}
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		activeMarker.setPressed(false);
		activeMarker = null;
		repaint();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (activeMarker != null) {
			activeMarker.PerformDrag(e.getPoint());
			repaint();
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void actionPerformed(ActionEvent event) {
		switch (event.getActionCommand()) {
		case "Add": {
			Marker arr[] = new Marker[4];
			if (curvesList.size() > 0) {
				BezierCurve curve = curvesList.get(curvesList.size() - 1);
				// System.out.println(String.format("Length = %i",
				// curve.getMarkersArray().length));
				arr[0] = curve.getMarkersArray()[curve.getMarkersArray().length - 1];
				Point2D previousMarkerCenter = curve.getMarkersArray()[curve
						.getMarkersArray().length - 2].Center();
				Point2D lastMarkerCenter = curve.getMarkersArray()[curve
						.getMarkersArray().length - 1].Center();
				Point2D direction = new Point2D.Float(
						(float) (lastMarkerCenter.getX() - previousMarkerCenter
								.getX()),
						(float) (lastMarkerCenter.getY() - previousMarkerCenter
								.getY()));
				arr[1] = new Marker(new Point2D.Float(
						(float) (lastMarkerCenter.getX() + direction.getX()),
						(float) (lastMarkerCenter.getY() + direction.getY())));
				arr[2] = new Marker(new Point2D.Float((float) arr[1].Center()
						.getX() + 50, (float) arr[1].Center().getY()));
				arr[3] = new Marker(new Point2D.Float(
						(float) lastMarkerCenter.getX() + 150,
						(float) lastMarkerCenter.getY()));
			} else {
				arr[0] = new Marker(new Point2D.Float(50, 50));
				arr[1] = new Marker(new Point2D.Float(120, 50));
				arr[2] = new Marker(new Point2D.Float(50, 120));
				arr[3] = new Marker(new Point2D.Float(120, 120));
			}
			BezierCurve curve = new BezierCurve(arr);
			curvesList.add(curve);
			repaint();
			break;
		}
		case "Image": {
			Properties properties = BezierMaker.getProperties();
			String defaultImagesFolder = properties.getProperty("DEFAULT_IMAGES_FOLDER");
			
			JFileChooser chooser = new JFileChooser(defaultImagesFolder);

			int result = chooser.showOpenDialog(MainView.this);
			if (result == JFileChooser.APPROVE_OPTION) {
				properties.setProperty("DEFAULT_IMAGES_FOLDER", chooser.getCurrentDirectory().toString());
				BezierMaker.saveProperties();
				setBackgroundImage(chooser.getSelectedFile().toString());
			}
			break;
		}
		case "Save": {
			Properties properties = BezierMaker.getProperties();
			String defaultImagesFolder = properties.getProperty("DEFAULT_RESULT_FOLDER");
			JFileChooser chooser = new JFileChooser(defaultImagesFolder);
			int result = chooser.showSaveDialog(MainView.this);
			if (result == JFileChooser.APPROVE_OPTION) {
				properties.setProperty("DEFAULT_RESULT_FOLDER", chooser.getCurrentDirectory().toString());
				BezierMaker.saveProperties();
				
				String stringToSave = "/";
				for(int i = 0; i < curvesList.size(); i++) {
					BezierCurve curve = curvesList.get(i);
					for(int j = 0; j < curve.getMarkersArray().length; j++) {
						Marker marker = curve.getMarkersArray()[j];
						String format = (i == curvesList.size()-1)?"%s%.0f,%.0f;":(j == curve.getMarkersArray().length-1)?"%s%.0f,%.0f;%n/":"%s%.0f,%.0f;";
						float x = (float)marker.Center().getX();
						float y = (float)marker.Center().getY();
						stringToSave = String.format(format,stringToSave, x, y);
					}
				}
				Writer writer = null;
				File fileToSave = new File(chooser.getSelectedFile().toString());
				try {
					writer = new BufferedWriter(new FileWriter(fileToSave));
					writer.write(stringToSave);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					try {
						if (writer != null) {
							writer.close();
						}
						
					} catch (IOException e2) {
						e2.printStackTrace();
					}
				}
			}
			break;
		}
		default:
			break;
		}
	}
}
