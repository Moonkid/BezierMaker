import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JFrame;

public class BezierMaker {

	private static MainView mainView;
	private static JButton addButton;
	private static JButton bcgPicker;
	private static JButton saveButton;
	private static Properties properties = new Properties();
	
    public static Properties getProperties() {
		return properties;
	}

	public static void main(String s[]) {
    	
    	try {
			properties.load(new FileInputStream("options.properties"));
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Error ocured while loading properties");
			e.printStackTrace();
		}
    	
    	
		JFrame frame = new JFrame("Maker");
		frame.setSize(920, 520);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    	frame.setLayout(null);
    	
    	mainView = new MainView();
    	mainView.setSize(800,480);
    	mainView.setVisible(true);
    	frame.add(mainView);
    	
    	addButton = new JButton("Add");
		addButton.setLocation(810, 10);
		addButton.setSize(80, 30);
		addButton.addActionListener(mainView);

		frame.add(addButton);
		
		bcgPicker = new JButton("Image");
		bcgPicker.setLocation(810, 50);
		bcgPicker.setSize(80, 30);
		bcgPicker.addActionListener(mainView);
		
		frame.add(bcgPicker);

		saveButton = new JButton("Save");
		saveButton.setLocation(810, 90);
		saveButton.setSize(80, 30);
		saveButton.addActionListener(mainView);
		frame.add(saveButton);
    }

	public static void saveProperties() {
		try {
			properties.store(new FileOutputStream("options.properties"), null);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}