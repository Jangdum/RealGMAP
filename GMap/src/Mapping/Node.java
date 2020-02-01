package Mapping;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class Node extends JPanel{
	
	

	private static final long serialVersionUID = 1L;
	private int key;
	private Node p;
	private Node left;
	private Node right;
	private TYPE type;
	private ID id;
	private int positionOnMap;
	
	private Colors BFSColor;
	private Node BFSParent;
	private int BFSDistance;
	private JLabel BFSPathID;
	private boolean BFSInPath;
	
	private int distra_distance;
	private Node distra_parent;
	
	private JLabel idDisplay;
	private JLabel BFSvisitLabel;
	
	public Node() {
		this.key = 0; 
		this.id = null;
		
		p = left = right = null;
	}
	
	public JPanel nodeSquare() {
		JPanel panel = new JPanel();
		
		panel.setSize(new Dimension(WIDTH, HEIGHT));
		
		if(id == ID.source) {
			panel.setBackground(Color.BLUE);
			idDisplay = new JLabel("S");
			idDisplay.setFont(new Font("Arial", Font.BOLD, 24));
			panel.add(idDisplay);
		}else if(id == ID.target) {
			panel.setBackground(Color.MAGENTA);
			idDisplay = new JLabel("T");
			idDisplay.setFont(new Font("Arial", Font.BOLD, 24));
			panel.add(idDisplay);
		}else {
			if(type == TYPE.nonSolid) {
				/*
				if(BFSColor == Colors.GRAY) {
					panel.setBackground(Color.LIGHT_GRAY);
				}else if(BFSColor == Colors.BLACK) {
					panel.setBackground(Color.BLACK);
				}else {
					panel.setBackground(Color.WHITE);
				}
				*/
				panel.setBackground(Color.BLACK);
			}else if(id == ID.edit){
				panel.setBackground(Color.GREEN);
			}else if(type == TYPE.Solid){
				panel.setBackground(Color.darkGray);
			}
		}
		
		BFSPathID = new JLabel();
		if(BFSInPath) {
			BFSPathID.setText("*");
			BFSPathID.setFont(new Font("Arial", Font.BOLD, 24));
			BFSPathID.setForeground(Color.GREEN);
			panel.add(BFSPathID);
		}
		
		return panel;
	}
	
	
	
	
	
	
	
	
	

	
	/******************************************
	 * 										  *
	 * 										  *
	 *  ENUM SETTERS						  *
	 * 										  *
	 * 										  *
	 * ****************************************
	 */
	
	
	public void setID(ID id) {
		this.id = id;
	}
	
	public void setType(TYPE type) {
		this.type = type;
	}
	
	public void setBFSColor(Colors BFSColor) {
		this.BFSColor = BFSColor;
	}
	
	
	
	
	/******************************************
	 * 										  *
	 * 										  *
	 *  INT SETTERS						      *
	 * 										  *
	 * 										  *
	 * ****************************************
	 */
	
	
	public void setKey(int key) {
		this.key = key;
	}
	
	public void setBFSDistance(int BFSDistance) {
		this.BFSDistance = BFSDistance;
	}
	
	public void setNodePositionOnMap(int positionOnMap) {
		this.positionOnMap = positionOnMap;
	}
	
	
	
	
	
	
	
	
	/******************************************
	 * 										  *
	 * 										  *
	 *  NODE SETTERS						  *
	 * 										  *
	 * 										  *
	 * ****************************************
	 */
	

	
	public void setParent(Node p) {
		this.p = p;
	}
	
	
	public void setLeft(Node left) {
		this.left = left;
	}
	
	
	public void setRight(Node right) {
		this.right = right;
	}
	
	public void setBFSParent(Node BFSParent) {
		this.BFSParent = BFSParent;
	}
	
	public void setDDistance(int d) {
		this.distra_distance = d;
	}
	
	public void setDParent(Node p) {
		this.distra_parent = p;
	}
	
	
	
	
	
	
	/******************************************
	 * 										  *
	 * 										  *
	 *  Label Setting						  *
	 * 										  *
	 * 										  *
	 * ****************************************
	 */
	
	
	
	
	
	public void setBFSVisitLabel(String text) {
		this.BFSvisitLabel.setText(text);
	}
	
	
	
	
	/******************************************
	 * 										  *
	 * 										  *
	 *  Label Setting						  *
	 * 										  *
	 * 										  *
	 * ****************************************
	 */
	
	
	public void setBFSInPath(boolean BFSInPath) {
		this.BFSInPath = BFSInPath;
	}
	
	
	
	
	
	
	
	/******************************************
	 * 										  *
	 * 										  *
	 *  BOOLEAN GETTING						  *
	 * 										  *
	 * 										  *
	 * ****************************************
	 */
	
	
	public boolean getBFSInPath() {
		return BFSInPath;
	}
	
	
	
	
	
	
	
	/******************************************
	 * 										  *
	 * 										  *
	 *  NODE GETTERS						  *
	 * 										  *
	 * 										  *
	 * ****************************************
	 */
	
	
	
	
	public Node getBFSParent() {
		return BFSParent;
	}
	
	
	public Node getParent() {
		return p;
	}
	
	
	public Node getRight() {
		return right;
	}
	
	
	public Node getLeft() {
		return left;
	}
	
	public Node getDistraParent() {
		return distra_parent;
	}
	

	
	
	
	
	
	/******************************************
	 * 										  *
	 * 										  *
	 *  ENUM GETTERS						  *
	 * 										  *
	 * 										  *
	 * ****************************************
	 */
	
	
	public ID getID() {
		return id;
	}
	
	
	public TYPE getType() {
		return type;
	}
	
	public Colors getBFSColor() {
		return BFSColor;
	}
	
	
	
	
	
	/******************************************
	 * 										  *
	 * 										  *
	 *  INT GETTERS						      *
	 * 										  *
	 * 										  *
	 * ****************************************
	 */
	
	
	
	public int getBFSDistance() {
		return BFSDistance;
	}
	
	public int getKey() {
		return key;
	}
	
	public int getPositionOnMap() {
		return positionOnMap;
	}
	
	public int getDistraDistance() {
		return distra_distance;
	}
}
