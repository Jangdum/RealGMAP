package Mapping;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Window extends Canvas{

	private static final long serialVersionUID = 1L;
	
	static LinkedList<Node> nodes;
	
	public Window(int width, int height, LinkedList<Node> nodes, int size) {
		JFrame frame = new JFrame(); 
		
		Window.nodes = nodes;
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(width, height));
		frame.setMaximumSize(new Dimension(width, height));
		frame.setMinimumSize(new Dimension(width, height));
		frame.setLocationRelativeTo(null);
		//frame.setResizable(false);
		frame.setLayout(new GridLayout(size, size));
		
		for(int i = 0; i < Math.pow(size, 2); i++) {
			JPanel square = nodes.get(i).nodeSquare();
			frame.add(square);
		}
		
		frame.setVisible(true);
	}
}
