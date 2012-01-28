import java.awt.Color;

import javax.swing.JFrame;

public class BezierMaker {

	private static MainView mainView;
    public static void main(String s[]) {
    	mainView = new MainView();
    	mainView.setSize(880,480);
    	mainView.setVisible(true);
    	mainView.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
}