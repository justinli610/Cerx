package fieldCreator;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import field.Map;

public class Main {
	public static void main(String[] args) {
		JFrame f = new JFrame();
		
		JScrollPane scrollPane = new JScrollPane();
		
		NewMapPanel nmp = new NewMapPanel(new Map(20, 20, Map.GRASS), scrollPane);
		
		scrollPane.setViewportView(nmp);
		scrollPane.setPreferredSize(new Dimension(800, 600));
		scrollPane.getHorizontalScrollBar().setUnitIncrement(60);
		scrollPane.getVerticalScrollBar().setUnitIncrement(25);
		f.add(scrollPane);
		f.setJMenuBar(new NewMapMenuBar(nmp));
		f.setTitle("Map Editor 3.5");
		
		f.pack();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}
}
