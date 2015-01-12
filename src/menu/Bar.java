package menu;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import javax.swing.*;
import field.GameState;

public class Bar extends JPanel{
	int total;
	int current;
	Color col = Color.blue;
	Color col2 = Color.black;
	int temp = 90;

	public Bar() {
		total = 100;
		current = 70;
		setPreferredSize(new Dimension(temp,10));
	}
	
	public Bar(int total, int current, Color col) {
		this.total = total;
		this.current = current;
		this.col = col;
		setPreferredSize(new Dimension(temp,10));
	}
	
	@Override
	public void paintComponent(Graphics g) {


		int w2 = ((temp*current)/total);
		
		g.setColor(col);
		g.fillRect(0, 0, w2, 10);
		g.setColor(Color.black);
		g.fillRect(temp-(temp-w2), 0, temp-w2, 10);
		g.setColor(Color.black);
		g.drawRect(0,0,temp,10);
	}

	static JFrame frame;

	public static void ShowScreen() throws Exception {
		frame = new JFrame("");// initializes frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// sets closing
																// upon click of
																// x
		frame.add(new Bar());
		frame.pack();
		frame.setSize(800, 600);
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		// Schedule a job for the event dispatch thread:
		// creating and showing this application's GUI.
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				// Turn off metal's use of bold fonts
				UIManager.put("swing.boldMetal", Boolean.FALSE);
				try {
					ShowScreen();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
