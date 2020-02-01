package Mapping;

import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

public class Map {
	
	static Scanner input = new Scanner(System.in);
	static Random rand = new Random();
	static LinkedList<Node> nodes = new LinkedList<Node>();
	static LinkedList<Node> changedNodes = new LinkedList<Node>();
	static Node root;
	private int WIDTH = 640, HEIGHT = 480;
	static Node source;
	static Node target;
	static NodeMath NMath;
	static boolean large = false;
	int[][] A;
	int tracker = 0;
	
	
	
	/******************************************
	 * 										  *
	 * 										  *
	 * MAP GENORATION						  *
	 * 										  *
	 * 										  *
	 * ****************************************
	 */
	
	
	
	
	/*
	 * This will add all the keys to the nodes
	 * of the map
	 */
	
	public void MapGenerator(int size) {
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				A[i][j] = rand.nextInt(20);	
				
				System.out.print(A[i][j] + ", ");
			}
			System.out.println();
		}
	}
	
	
	/*
	 * This will identify if the node will be a solid or not
	 */
	
	public void caveCreator(int matrixSize) {
		for(int i = 0; i < matrixSize; i++) {
			Node n = nodes.get(i);
			
			if(n.getKey() > 10 && n != target) {
				n.setType(TYPE.Solid);
			}else {
				n.setType(TYPE.nonSolid);
			}
			
			n.setNodePositionOnMap(i);
			
			if(tracker == 4) {
				tracker = 0;
			}else {
				tracker++;
			}
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	/******************************************
	 * 										  *
	 * 										  *
	 * BINARY TREE							  *
	 * 										  *
	 * 										  *
	 * ****************************************
	 */
	
	
	
	
	/*
	 * This is setting the keys to the nodes and putting them into 
	 * a binary search tree for easy search		O(N)
	 */
	
	public void InsertNodesintoBinaryTree(int k, Node z) {
		Node y = null;
		Node x = Map.root;
		
		z.setKey(k);
		
		while(x != null) {
			y = x;
			
			if(z.getKey() < x.getKey()) {
				x = x.getLeft();
			}else {
				x = x.getRight();
			}
		}
		
		z.setParent(y);
		
		if(y == null) {
			Map.root = z;
		}else if(z.getKey() < y.getKey()) {
			y.setLeft(z);
		}else {
			y.setRight(z);
		}
		
		nodes.add(z);
	}
	
	
	/*
	 * Searching for the source node which is the minimum 
	 * on the tree
	 */
	
	public void searchForSoucre() {
		Node node = root;
		
		while(node.getLeft() != null) {
			node = node.getLeft();
		}
		
		node.setID(ID.source);
		node.setType(TYPE.nonSolid);
		source = node;
	}
	
	
	/*
	 * This is searching for the target node which is the 
	 * max on the tree
	 */
	
	public void searchForTarget() {
		Node node = root;
		
		while(node.getRight() != null) {
			node = node.getRight();
		}
		
		node.setID(ID.target);
		node.setType(TYPE.nonSolid);
		target = node;
	}
	
	
	/*
	 * Gives min node
	 */
	
	public Node minNode() {
		Node node = root;
		
		while(node.getLeft() != null) {
			node = node.getLeft();
		}
		
		return node;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/******************************************
	 * 										  *
	 * 										  *
	 *  NODE EXCLUDED						  *
	 * 										  *
	 * 										  *
	 * ****************************************
	 */
	
	
	
	
	
	
	/*
	 * This will check to see if the source node is 
	 * excluded from other non solid
	 */
	
	public void excluded(int matrixSize, ID id, ID search) {
		boolean[] sides = new boolean[4];
		
		for(Node currentNode: nodes) {
			if(currentNode.getID() == id) {
				Node rightNode = null;
				Node bottomNode = null;
				Node leftNode = null;
				Node topNode = null;
				
				if(NMath.willNodeExist_Right(currentNode.getPositionOnMap())) {
					
					rightNode = nodes.get(currentNode.getPositionOnMap() + 1);
					if(rightNode.getType() == TYPE.Solid) {
						sides[0] = true;
					}
				} else {
					sides[0] = true;
				} if(NMath.willNodeExist_Bottom(currentNode.getPositionOnMap())) {
					bottomNode = nodes.get(currentNode.getPositionOnMap() + (int) Math.sqrt(matrixSize));
					if(bottomNode.getType() == TYPE.Solid) {
						sides[1] = true;
					}
				} else {
					sides[1] = true;
				} if(NMath.willNodeExist_Left(currentNode.getPositionOnMap())) {
					leftNode = nodes.get(currentNode.getPositionOnMap() - 1);
					if(leftNode.getType() == TYPE.Solid) {
						sides[2] = true;
					}
				} else {
					sides[2] = true;
				} if(NMath.willNodeExist_Top(currentNode.getPositionOnMap())) {
					topNode = nodes.get(currentNode.getPositionOnMap() - (int) Math.sqrt(matrixSize));
					if(topNode.getType() == TYPE.Solid) {
						sides[3] = true;
					}
				} else {
					sides[3] = true;
				}
			}
		}
		
		// if the node is excluded go to the connect function to eliminate nodes around 
		if(sides[0] == true && sides[1] == true && sides[2] == true &&
				sides[3] == true) {
			connect(matrixSize, id, search);
		}
	}
	
	
	/*
	 * Test to see where the target node is on the map
	 */
	
	public static boolean NodeOnBottom(int matrexSize, int nodePos, ID nodeID) {
		for(int i = nodePos; i < matrexSize; i++) {
			if(nodes.get(i).getID() == nodeID) {
				return true;
			}
		}
		return false;
	}
	
	
	
	
	
	
	
	
	
	/*
	 * If any important node is surrounded by solid nodes
	 * this will fix the problem	O(N)
	 */ 	
	
	public void connect(int matrixSize, ID id, ID search) {
		Node leftNode = null;
		Node rightNode = null;
		
		
		for(Node currentNode: nodes) {
			
			//Test the node to see if it equals to the id of what you are looking for
			if(currentNode.getID() == id) {
				boolean isBelow = NodeOnBottom(matrixSize, currentNode.getPositionOnMap(), search);
		
				if(isBelow) {
					Node bottomNode = null;
					Node bottomRightNode = null;
					Node bottomLeftNode = null;
					
					//Testing to see if the node exists on the bottom and destroy if it does
					if(NMath.willNodeExist_Bottom(currentNode.getPositionOnMap())) {
						bottomNode = nodes.get(currentNode.getPositionOnMap() + (int) Math.sqrt(matrixSize));
						bottomNode.setKey(bottomNode.getKey() - 10);
						bottomNode.setType(TYPE.nonSolid);
					} if(NMath.willNodeExist_Bottom(currentNode.getPositionOnMap() + 1)) {
						bottomRightNode = nodes.get((currentNode.getPositionOnMap() + (int) Math.sqrt(matrixSize)) + 1);
						if(bottomRightNode.getType() == TYPE.Solid && bottomRightNode.getID() != ID.target && 
								!NMath.nodeOnEdge_Right(bottomNode.getPositionOnMap())) {
							bottomRightNode.setKey(bottomRightNode.getKey() - 10);
							bottomRightNode.setType(TYPE.nonSolid);
						}
					} if(NMath.willNodeExist_Bottom(currentNode.getPositionOnMap() - 1)) {
						bottomLeftNode = nodes.get((currentNode.getPositionOnMap() + (int) Math.sqrt(matrixSize)) - 1);
						if(bottomLeftNode.getType() == TYPE.Solid &&
								bottomLeftNode.getID() != ID.target && 
								!NMath.nodeOnEdge_Left(bottomNode.getPositionOnMap())) {
							
							bottomLeftNode.setKey(bottomLeftNode.getKey() - 10);
							bottomLeftNode.setType(TYPE.nonSolid);
						}
					}		
				} else {
					Node topNode = null;
					Node topRightNode = null;
					Node topLeftNode = null;
	
					if(NMath.willNodeExist_Top(currentNode.getPositionOnMap())) {
						topNode = nodes.get(currentNode.getPositionOnMap() - (int) Math.sqrt(matrixSize));
						topNode.setKey(topNode.getKey() - 10);
						topNode.setType(TYPE.nonSolid);
					} if(NMath.willNodeExist_Top(currentNode.getPositionOnMap() - 1)) {
						topLeftNode = nodes.get((currentNode.getPositionOnMap() - (int) Math.sqrt(matrixSize)) - 1);
						if(topLeftNode.getType() == TYPE.Solid &&
								topLeftNode.getID() != ID.target && 
								!NMath.nodeOnEdge_Left(topNode.getPositionOnMap())) {
							topLeftNode.setKey(topLeftNode.getKey() - 10);
							topLeftNode.setType(TYPE.nonSolid);
						}
					} if(NMath.willNodeExist_Top(currentNode.getPositionOnMap() + 1)) {
						topRightNode = nodes.get((currentNode.getPositionOnMap() - (int) Math.sqrt(matrixSize)) + 1);
						if(topRightNode.getType() == TYPE.Solid &&
								topRightNode.getID() != ID.target && 
								!NMath.nodeOnEdge_Right(topNode.getPositionOnMap())) {
							topRightNode.setKey(topRightNode.getKey() - 10);
							topRightNode.setType(TYPE.nonSolid);
						}
					}
				} if(NMath.willNodeExist_Right(currentNode.getPositionOnMap()) && 
						!NMath.nodeOnEdge_Right(currentNode.getPositionOnMap())) {
					rightNode = nodes.get(currentNode.getPositionOnMap() + 1);
					rightNode.setKey(rightNode.getKey() - 10);
					rightNode.setType(TYPE.nonSolid);
				} if(NMath.willNodeExist_Left(currentNode.getPositionOnMap()) && 
						!NMath.nodeOnEdge_Left(currentNode.getPositionOnMap())) {
					leftNode = nodes.get(currentNode.getPositionOnMap() - 1);
					leftNode.setKey(leftNode.getKey() - 10);
					leftNode.setType(TYPE.nonSolid);
				}
			}
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/******************************************
	 * 										  *
	 * 										  *
	 *  MAZE RUNNER						      *
	 * 										  *
	 * 										  *
	 * ****************************************
	 */
	
	
	
	

	public boolean BFS(int matrixSize, Node source, Node target, boolean target_Below) {
		LinkedList<Node> Q = new LinkedList<Node>();
		boolean target_found = false;
		boolean followTarget = false;
		
		
		for(Node currentNode: nodes) {
			if(currentNode != source) {
				if(currentNode.getType() == TYPE.nonSolid) {
					currentNode.setBFSColor(Colors.WHITE);
					currentNode.setBFSDistance(Integer.MAX_VALUE);
					currentNode.setBFSParent(null);
				}
			}
		}
		
		source.setBFSColor(Colors.GRAY);
		source.setBFSDistance(0);
		source.setBFSParent(null);
		
		target.setBFSColor(Colors.WHITE);
		target.setBFSDistance(Integer.MAX_VALUE);
		target.setBFSParent(null);
		
		Q.add(source);
		
		while(Q.size() != 0) {
			Node topNode = null;
			Node leftNode = null;
			Node bottomNode = null;
			Node rightNode = null;
			
			
			Node u = Q.remove();
			
			followTarget = false;
			target_Below = NodeOnBottom(matrixSize, u.getPositionOnMap(), target.getID());
			
			/*
			 * Checking nodes around the current node to see if there is an opening that
			 * might interest the function too go to
			 */
			
			if(NMath.willNodeExist_Top(u.getPositionOnMap())) {
				topNode = nodes.get(u.getPositionOnMap() - (int) Math.sqrt(matrixSize));
			} if(NMath.willNodeExist_Left(u.getPositionOnMap())) {
				leftNode = nodes.get(u.getPositionOnMap() - 1);
				if(NMath.nodeOnEdge_Left(u.getPositionOnMap())) {
					leftNode = null;
				}
			} if(NMath.willNodeExist_Bottom(u.getPositionOnMap())) {
				bottomNode = nodes.get(u.getPositionOnMap() + (int) Math.sqrt(matrixSize));
			} if(NMath.willNodeExist_Right(u.getPositionOnMap())) {
				rightNode = nodes.get(u.getPositionOnMap() + 1);
				if(NMath.nodeOnEdge_Right(u.getPositionOnMap())) {
					rightNode = null;
				}
			}
			
			 //If the target is above the source node go to this function test the 
			// top and the left node to see if it is open first
			if(target_Below) {
		
				if(bottomNode != null && bottomNode.getType() == TYPE.nonSolid && 
						bottomNode.getBFSColor() == Colors.WHITE) {
					if(bottomNode.getID() == ID.target) {
						bottomNode.setBFSColor(Colors.GRAY);
						bottomNode.setBFSDistance(u.getBFSDistance() + 1);
						bottomNode.setBFSParent(u);
						target_found = true;
						break;
					}else {
						followTarget = true;
						bottomNode.setBFSColor(Colors.GRAY);
						bottomNode.setBFSDistance(u.getBFSDistance() + 1);
						bottomNode.setBFSParent(u);
						Q.add(bottomNode);
					}
				} if(rightNode != null && rightNode.getType() == TYPE.nonSolid && 
						rightNode.getBFSColor() == Colors.WHITE) {
					if(rightNode.getID() == ID.target) {
						rightNode.setBFSColor(Colors.GRAY);
						rightNode.setBFSDistance(u.getBFSDistance() + 1);
						rightNode.setBFSParent(u);
						target_found = true;
						break;
						
					}else {
						followTarget = true;
						rightNode.setBFSColor(Colors.GRAY);
						rightNode.setBFSDistance(u.getBFSDistance() + 1);
						rightNode.setBFSParent(u);
						Q.add(rightNode);
					}
				}
			} else {
				//If the target is below the source node then it will check the bottom and the right node first to see 
				// if it is open
			if(topNode != null && topNode.getType() == TYPE.nonSolid && 
					topNode.getBFSColor() == Colors.WHITE) {
				if(topNode.getID() == ID.target) {
					topNode.setBFSColor(Colors.GRAY);
					topNode.setBFSDistance(u.getBFSDistance() + 1);
					topNode.setBFSParent(u);
					target_found = true;
					break;
				}else {
					followTarget = true;
					topNode.setBFSColor(Colors.GRAY);
					topNode.setBFSDistance(u.getBFSDistance() + 1);
					topNode.setBFSParent(u);
					Q.add(topNode);
				}
			} if(leftNode != null && leftNode.getType() == TYPE.nonSolid && 
					leftNode.getBFSColor() == Colors.WHITE) {
				if(leftNode.getID() == ID.target) {
					leftNode.setBFSColor(Colors.GRAY);
					leftNode.setBFSDistance(u.getBFSDistance() + 1);
					leftNode.setBFSParent(u);
					target_found = true;
					break;
				}else {
					followTarget = true;
					leftNode.setBFSColor(Colors.GRAY);
					leftNode.setBFSDistance(u.getBFSDistance() + 1);
					leftNode.setBFSParent(u);
					Q.add(leftNode);
				}
			}
			
		}
		
			
		if(!followTarget) {
			if(topNode != null && topNode.getType() == TYPE.nonSolid && 
					topNode.getBFSColor() == Colors.WHITE) {
				if(topNode.getID() == ID.target) {
					topNode.setBFSColor(Colors.GRAY);
					topNode.setBFSDistance(u.getBFSDistance() + 1);
					topNode.setBFSParent(u);
					target_found = true;
					break;
				}else {
					topNode.setBFSColor(Colors.GRAY);
					topNode.setBFSDistance(u.getBFSDistance() + 1);
					topNode.setBFSParent(u);
					Q.add(topNode);
				}
			} if(leftNode != null && leftNode.getType() == TYPE.nonSolid && 
					leftNode.getBFSColor() == Colors.WHITE) {
				if(leftNode.getID() == ID.target) {
					leftNode.setBFSColor(Colors.GRAY);
					leftNode.setBFSDistance(u.getBFSDistance() + 1);
					leftNode.setBFSParent(u);
					target_found = true;
					break;
				} else {
					leftNode.setBFSColor(Colors.GRAY);
					leftNode.setBFSDistance(u.getBFSDistance() + 1);
					leftNode.setBFSParent(u);
					Q.add(leftNode);
				}
			} if(bottomNode != null && bottomNode.getType() == TYPE.nonSolid && 
					bottomNode.getBFSColor() == Colors.WHITE) {
				if(bottomNode.getID() == ID.target) {
					bottomNode.setBFSColor(Colors.GRAY);
					bottomNode.setBFSDistance(u.getBFSDistance() + 1);
					bottomNode.setBFSParent(u);
					target_found = true;
					break;
				} else {
					bottomNode.setBFSColor(Colors.GRAY);
					bottomNode.setBFSDistance(u.getBFSDistance() + 1);
					bottomNode.setBFSParent(u);
					Q.add(bottomNode);
				}
			} if(rightNode != null && rightNode.getType() == TYPE.nonSolid && 
					rightNode.getBFSColor() == Colors.WHITE) {
				if(rightNode.getID() == ID.target) {
					rightNode.setBFSColor(Colors.GRAY);
					rightNode.setBFSDistance(u.getBFSDistance() + 1);
					rightNode.setBFSParent(u);
					target_found = true;
					break;
				} else {
					rightNode.setBFSColor(Colors.GRAY);
					rightNode.setBFSDistance(u.getBFSDistance() + 1);
					rightNode.setBFSParent(u);
					Q.add(rightNode);
				}
			}
		}
			
		u.setBFSColor(Colors.BLACK);

	}	
			
		if(target_found) 
			return true;
		else 
			return false;
	}
	
	
	public void initialize_single_source(int mazeSize, Node source) {
		for(Node node: nodes) {
			if(node.getType() == TYPE.nonSolid) {
				node.setDDistance(Integer.MAX_VALUE);
				node.setDParent(null);
			}
		}
		source.setDDistance(0);
	}
	
	
	public boolean Dijkstra(int mazeSize, Node source, Node target, boolean targetBelow) {
		initialize_single_source(mazeSize, source);
		LinkedList<Node> S = new LinkedList<Node>();
		LinkedList<Node> Q = nodes;
		
		while(Q.size() != 0) {
			//Node u = 
		}
		
		return true;
	}
	
	
	
	
	/******************************************
	 * 										  *
	 * 										  *
	 *  Reconstruct after destory			  *
	 * 										  *
	 * 										  *
	 * ****************************************
	 */

	//O(n)
	public void reconstruction() {
		for(Node currentNode: changedNodes) {
			if(!currentNode.getBFSInPath()) {
				currentNode.setKey(currentNode.getKey() + 10);
				currentNode.setType(TYPE.Solid);
			}
		}
	}
	
	
	
	
	
	
	
	
	
	
	/******************************************
	 * 										  *
	 * 										  *
	 *  Shortest Path To Target				  *
	 * 										  *
	 * 										  *
	 * ****************************************
	 */
	
	
	

	/*
	 * This will display the BFS shortest path and
	 * Display it on the GUI using Green Nodes	O(1)
	 */
	
	
	
	
	public void path(int matrixSize, Node node) {
		if(node == source) {
			node.setBFSInPath(true);
		}else {
			path(matrixSize, node.getBFSParent());
			node.setBFSInPath(true);
			System.out.println("<" + node.getBFSParent().getPositionOnMap() + ", " + node.getPositionOnMap()  + ">");
		}	
	}
	
	
	
	
	
	
	
	
	
	
	
	
	/******************************************
	 * 										  *
	 * 										  *
	 *  PATH CREATION            			  *
	 * 										  *
	 * 										  *
	 * ****************************************
	 */
	
	
	//O(1)
	public static boolean NodeTester(int mazeSize, Node testNode, Node currentNode) {
		Node topNode = null;
		Node leftNode = null;
		Node bottomNode = null;
		Node rightNode = null;
		
		//This will set the variables testing to see if each variable exists on the 
		//List.
		if(NMath.willNodeExist_Top(testNode.getPositionOnMap())) {
			topNode = nodes.get(testNode.getPositionOnMap() - (int) Math.sqrt(mazeSize));
		} if(NMath.willNodeExist_Left(testNode.getPositionOnMap()) && 
				!NMath.nodeOnEdge_Left(testNode.getPositionOnMap())) {
			leftNode = nodes.get(testNode.getPositionOnMap() - 1);
		} if(NMath.willNodeExist_Bottom(testNode.getPositionOnMap())) {
			bottomNode = nodes.get(testNode.getPositionOnMap() + (int) Math.sqrt(mazeSize));
		} if(NMath.willNodeExist_Right(testNode.getPositionOnMap()) && 
				!NMath.nodeOnEdge_Right(testNode.getPositionOnMap())) {
			rightNode = nodes.get(testNode.getPositionOnMap() + 1);
		}
		
		//This will return true if there is an open node next to the testing node will also
		//check to see if the current node is being tested so it doesn't use that as an opening
		if(rightNode != null && rightNode != currentNode && rightNode.getType() == TYPE.nonSolid) {
			return true;
		} else if(leftNode != null && leftNode != currentNode && leftNode.getType() == TYPE.nonSolid) {
			return true;
		} else if(topNode != null && topNode != currentNode && topNode.getType() == TYPE.nonSolid) {
			return true;
		} else if(bottomNode != null && bottomNode != currentNode && bottomNode.getType() == TYPE.nonSolid) {
			return true;
		} else {
			return false;
		}
	}
	
	//O(N)
	public static boolean visitCheck(LinkedList<Node> visited, Node block) {
		//Go threw the list of visited nodes if the node is found in the
		//list then return true
		for(Node n: visited) {
			if(n == block) 
				return true;
		}
		return false;
	}
	
	public static void PathFinder(int mazeSize, boolean nodeOnBottom, Node source, Node target) {
		LinkedList<Node> maze = new LinkedList<Node>();
		LinkedList<Node> visited = new LinkedList<Node>();
		
		maze.add(source);
				
		while(maze.size() != 0) {
			Node u = maze.remove();
			Node[] surroundingNodes = new Node[4];
			
			nodeOnBottom = NodeOnBottom(mazeSize, u.getPositionOnMap(), target.getID());
					
			//Gathering all Existing Nodes
			if(NMath.willNodeExist_Top(u.getPositionOnMap())) {
				surroundingNodes[0] = nodes.get(u.getPositionOnMap() - (int) Math.sqrt(mazeSize));
			} if(NMath.willNodeExist_Right(u.getPositionOnMap()) && 
					!NMath.nodeOnEdge_Right(u.getPositionOnMap())) {
				surroundingNodes[1] = nodes.get(u.getPositionOnMap() + 1);
			} if(NMath.willNodeExist_Bottom(u.getPositionOnMap())) {
				surroundingNodes[2] = nodes.get(u.getPositionOnMap() + (int) Math.sqrt(mazeSize));
			} if(NMath.willNodeExist_Left(u.getPositionOnMap()) && 
					!NMath.nodeOnEdge_Left(u.getPositionOnMap())) {
				surroundingNodes[3] = nodes.get(u.getPositionOnMap() - 1);
			}			
			
			//if the target is below the current node that is is looking at 
			if(nodeOnBottom) {
				if(surroundingNodes[2] != null && surroundingNodes[2].getType() == TYPE.nonSolid &&
						!visitCheck(visited, surroundingNodes[2])) {
					maze.add(surroundingNodes[2]);
					visited.add(surroundingNodes[2]);
				} else if(surroundingNodes[3] != null && surroundingNodes[3].getType() == TYPE.nonSolid &&
						!visitCheck(visited, surroundingNodes[3])) {
					maze.add(surroundingNodes[3]);
					visited.add(surroundingNodes[3]);
				} else {
					if(surroundingNodes[2] != null && surroundingNodes[2].getType() == TYPE.Solid &&
							NodeTester(mazeSize, surroundingNodes[2], u)) {
						surroundingNodes[2].setType(TYPE.nonSolid);
						surroundingNodes[2].setKey(surroundingNodes[2].getKey() - 10);
						changedNodes.add(surroundingNodes[2]);
						break;
					} if(surroundingNodes[3] != null && surroundingNodes[3].getType() == TYPE.Solid &&
							NodeTester(mazeSize, surroundingNodes[3], u)) {
						System.out.println("\t-> CHANGED " + surroundingNodes[3].getPositionOnMap());
						surroundingNodes[3].setType(TYPE.nonSolid);
						surroundingNodes[3].setKey(surroundingNodes[3].getKey() - 10);
						changedNodes.add(surroundingNodes[3]);
						break;
					}
				}
			} else {	//If the target is above the current Node
				if(surroundingNodes[0] != null  && surroundingNodes[0].getType() == TYPE.nonSolid && 
						!visitCheck(visited, surroundingNodes[0])) {	//
					maze.add(surroundingNodes[0]);
					visited.add(surroundingNodes[0]);
				} else if(surroundingNodes[1] != null && surroundingNodes[1].getType() == TYPE.nonSolid && 
						!visitCheck(visited, surroundingNodes[1])) {
					maze.add(surroundingNodes[1]);
					visited.add(surroundingNodes[1]);
				} else {
					if(surroundingNodes[0] != null && surroundingNodes[0].getType() == TYPE.Solid &&
							NodeTester(mazeSize, surroundingNodes[0], u)) {
						surroundingNodes[0].setType(TYPE.nonSolid);
						surroundingNodes[0].setKey(surroundingNodes[0].getKey() - 10);
						changedNodes.add(surroundingNodes[0]);
						break;
					} if(surroundingNodes[1] != null && surroundingNodes[1].getType() == TYPE.Solid &&
							NodeTester(mazeSize, surroundingNodes[1], u)) {
						surroundingNodes[1].setType(TYPE.nonSolid);
						surroundingNodes[1].setKey(surroundingNodes[1].getKey() - 10);
						changedNodes.add(surroundingNodes[1]);
						break;
					}
				}
			}

			if(surroundingNodes[2] != null && surroundingNodes[2].getType() == TYPE.nonSolid &&
					!visitCheck(visited, surroundingNodes[2])) {
				maze.add(surroundingNodes[2]);
				visited.add(surroundingNodes[2]);
			} else if(surroundingNodes[3] != null && surroundingNodes[3].getType() == TYPE.nonSolid &&
					!visitCheck(visited, surroundingNodes[3])) {
				maze.add(surroundingNodes[3]);
				visited.add(surroundingNodes[3]);
			} else {
				if(surroundingNodes[2] != null && surroundingNodes[2].getType() == TYPE.Solid &&
						NodeTester(mazeSize, surroundingNodes[2], u)) {
					surroundingNodes[2].setType(TYPE.nonSolid);
					surroundingNodes[2].setKey(surroundingNodes[2].getKey() - 10);
					changedNodes.add(surroundingNodes[2]);
					break;
				} if(surroundingNodes[3] != null && surroundingNodes[3].getType() == TYPE.Solid &&
						NodeTester(mazeSize, surroundingNodes[3], u)) {
					surroundingNodes[3].setType(TYPE.nonSolid);
					surroundingNodes[3].setKey(surroundingNodes[3].getKey() - 10);
					changedNodes.add(surroundingNodes[3]);
					break;
				}
			} 
			if(surroundingNodes[0] != null  && surroundingNodes[0].getType() == TYPE.nonSolid && 
					!visitCheck(visited, surroundingNodes[0])) {
				maze.add(surroundingNodes[0]);
				visited.add(surroundingNodes[0]);
			} else if(surroundingNodes[1] != null && surroundingNodes[1].getType() == TYPE.nonSolid && 
					!visitCheck(visited, surroundingNodes[1])) {
				maze.add(surroundingNodes[1]);
				visited.add(surroundingNodes[1]);
			} else {
				if(surroundingNodes[0] != null && surroundingNodes[0].getType() == TYPE.Solid &&
						NodeTester(mazeSize, surroundingNodes[0], u)) {
					surroundingNodes[0].setType(TYPE.nonSolid);
					surroundingNodes[0].setKey(surroundingNodes[0].getKey() - 10);
					changedNodes.add(surroundingNodes[0]);
					break;
				} if(surroundingNodes[1] != null && surroundingNodes[1].getType() == TYPE.Solid &&
						NodeTester(mazeSize, surroundingNodes[1], u)) {
					surroundingNodes[1].setType(TYPE.nonSolid);
					surroundingNodes[1].setKey(surroundingNodes[1].getKey() - 10);
					changedNodes.add(surroundingNodes[1]);
					break;
				}
			}
		}
	}
	
	
	
	
	
	/*
	
	/******************************************
	 * 										  *
	 * 										  *
	 *  CONSTRUCTOR						      *
	 * 										  *
	 * 										  *
	 * ****************************************
	 */
	
	
	
	
	
	
	
	
	public Map(int size) {
		boolean pathFind = false;
		System.out.println(" - Size of " + size);
		A = new int[size][size];
		size = A.length;
		MapGenerator(A.length);
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) { 
				InsertNodesintoBinaryTree(A[i][j], new Node()); 
			} 
		}
		searchForSoucre();
		searchForTarget();
		NMath = new NodeMath(size, nodes.size());
		caveCreator(size * size);
		System.out.println("\n\t-- testing excluded source");
		excluded(size * size, ID.source, ID.target);
		System.out.println("\n\t-- testing excluded target");
		excluded(size * size, ID.target, ID.source);
		while(!BFS((size * size), source, target, 
				NodeOnBottom((size * size), source.getPositionOnMap(), target.getID()))) { 
			PathFinder((size * size), NodeOnBottom((size * size), source.getPositionOnMap(), target.getID()), source, target);
			pathFind = true;
		}
		new Window(WIDTH, HEIGHT, nodes, size);
		path(size * size, target);
		
		if(pathFind) {
			reconstruction();
		}
		System.out.println("Creating Window...");
		new Window(WIDTH, HEIGHT, nodes, size);
	}
	
	public static void main(String[] args) {
		int x = 11;
		
		System.out.println("1) Computer Picks Size");
		System.out.println("2) User Picks Size");
		System.out.print("> ");
		int y = input.nextInt();
		
		while(true) {
			if(y == 1) {
				x = rand.nextInt((20 - 4) + 1) + 4;
				break;
			}else if(y == 2){
				System.out.print("Size: ");
				x = input.nextInt();
				System.out.println();
				break;
			}else {
				System.out.println("Not a option");
			}
		}
		
		new Map(x);
	}
}
	
//1374 lines
	
	
	
	
	
	
	
	
	