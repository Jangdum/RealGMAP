package Mapping;

import java.util.LinkedList;

public class NodeMath {
	private int nodeListSize;
	
	public NodeMath(int size, int nodeListSize) {
		this.nodeListSize = nodeListSize;
	}
	
	public boolean nodeOnEdge_Left(int nodePosition) {
		if(nodePosition % (int) Math.sqrt(nodeListSize) == 0) {
			return true;
		}
		else {
			return false;
		}
	}
	public boolean nodeOnEdge_Right(int nodePosition) {
		if(nodePosition % (int) Math.sqrt(nodeListSize) == ((int) Math.sqrt(nodeListSize) - 1)) {
			return true;
		}
		else {
			return false;
		}
	}

	
	public boolean willNodeExist_Right(int nodePosition) {
		if((nodePosition + 1) < nodeListSize) {
			return true;
		}else {
			return false;
		}
	}
	
	public boolean willNodeExist_Left(int nodePostion) {
		if((nodePostion - 1) >= 0) {
			return true;
		}else {
			return false;
		}
	}
	
	
	public boolean willNodeExist_Top(int nodePosition) {
		if(nodePosition - (int) Math.sqrt(nodeListSize) > 0) {
			return true;
		}else {
			return false;
		}
	}
	
	public boolean willNodeExist_Bottom(int nodePostion) {
		if(nodePostion + (int) Math.sqrt((nodeListSize)) < nodeListSize) {
			return true;
		}else {
			return false;
		}
	}
	
	
	private void QuickSort(LinkedList<Node> nodes, Node p, Node r) {
		if(p.getKey() < r.getKey()) {
			Node q = returnOrde
		}
	}
	
	
	public Node partition(LinkedList<Node> nodes, int p, int r) {
		Node x = nodes.get(r);
		int i = p - 1;
		
		for(int j = p; j <= (r - 1); j++) {
			if(nodes.get(j).getKey() <= x.getKey()) {
				
			}
		}
	}
}
