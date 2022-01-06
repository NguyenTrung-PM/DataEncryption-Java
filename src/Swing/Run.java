package Swing;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.intellijthemes.FlatCyanLightIJTheme;

import javax.swing.*;

public class Run {
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(new FlatCyanLightIJTheme());
			LoadingGUI loadingGUI = new LoadingGUI();
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					GUI gui = new GUI();
					loadingGUI.dispose();
					gui.createAndShowGUI();
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
