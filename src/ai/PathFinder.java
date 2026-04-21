package ai;

import java.util.ArrayList;

import Main.GamePanel;
import entity.Entity;

public class PathFinder {
	
	GamePanel gp;
	public Node[][] node;
	ArrayList<Node> openList = new ArrayList<>();
	public ArrayList<Node> pathList = new ArrayList<>();
	Node startNode, goalNode, currentNode;
	boolean goalReached = false;
	int step = 0;
	
	public PathFinder(GamePanel gp) {
		this.gp = gp;
	}
	
	public void instantiateNodes() {

	    node = new Node[gp.maxScreenCol][gp.maxScreenRow];

	    for(int col = 0; col < gp.maxScreenCol; col++) {
	        for(int row = 0; row < gp.maxScreenRow; row++) {
	            node[col][row] = new Node(col, row);
	        }
	    }
	}
	
	public void resetNodes() {

	    for(int col = 0; col < gp.maxScreenCol; col++) {
	        for(int row = 0; row < gp.maxScreenRow; row++) {

	            node[col][row].open = false;
	            node[col][row].checked = false;
	            node[col][row].solid = false;
	            node[col][row].isStairs = false;

	        }
	    }

	    openList.clear();
	    pathList.clear();
	    goalReached = false;
	    step = 0;
	}
	
	public void setNodes(int startCol, int startRow, int goalCol, int goalRow) {

	    resetNodes();

	    startNode = node[startCol][startRow];
	    currentNode = startNode;
	    goalNode = node[goalCol][goalRow];
	    openList.add(currentNode);

	    int col = 0;
	    int row = 0;

	    while(col < gp.maxScreenCol && row < gp.maxScreenRow) {

	        int tileNum = gp.tileM.mapTileNum[gp.currentMap][col][row];
	        boolean blocked = false;

	        if(tileNum >= 0) {
	            if(gp.tileM.tile[tileNum].collision == 1) {
	                blocked = true;
	            }
	        }

	        node[col][row].solid = blocked;

	        node[col][row].isStairs = false;

	        if(tileNum == 2) {
	            node[col][row].isStairs = true;
	        }

	        getCost(node[col][row]);

	        col++;

	        if(col == gp.maxScreenCol) {
	            col = 0;
	            row++;
	        }
	        
	        
	    }
	    
	}
	
	public void getCost(Node node) {

	    // G cost
	    int xDistance = Math.abs(node.col - startNode.col);
	    int yDistance = Math.abs(node.row - startNode.row);
	    node.gCost = xDistance + yDistance;

	    // H cost
	    xDistance = Math.abs(node.col - goalNode.col);
	    yDistance = Math.abs(node.row - goalNode.row);
	    node.hCost = (xDistance + yDistance) * 10;

	    // F cost
	    node.fCost = node.gCost + node.hCost;
	}
	
	public boolean search() {

	    while(goalReached == false && step < 500) {

	        int col = currentNode.col;
	        int row = currentNode.row;

	        currentNode.checked = true;
	        openList.remove(currentNode);

	        // UP
	        if(row - 1 >= 0) {
	            openNode(node[col][row - 1]);
	        }

	        // LEFT
	        if(col - 1 >= 0) {
	            openNode(node[col - 1][row]);
	        }

	        // DOWN
	        if(row + 1 < gp.maxScreenRow) {
	            openNode(node[col][row + 1]);
	        }

	        // RIGHT
	        if(col + 1 < gp.maxScreenCol) {
	            openNode(node[col + 1][row]);
	        }

	        // choose best node
	        int bestIndex = 0;
	        int bestCost = 999;

	        for(int i = 0; i < openList.size(); i++) {

	            if(openList.get(i).fCost < bestCost) {
	                bestIndex = i;
	                bestCost = openList.get(i).fCost;
	            }
	            else if(openList.get(i).fCost == bestCost) {
	                if(openList.get(i).gCost < openList.get(bestIndex).gCost) {
	                    bestIndex = i;
	                }
	            }
	        }

	        if(openList.size() == 0) break;

	        currentNode = openList.get(bestIndex);

	        if(currentNode == goalNode) {
	            goalReached = true;
	            trackThePath();
	        }

	        step++;
	    }

	    return goalReached;
	}
	
	public boolean canMove(Node from, Node to) {

	    // WALLS BLOCK EVERYTHING
	    if(to.solid) return false;

	    // Check vertical movement
	    if (from.col == to.col && from.row != to.row) {
	        return from.isStairs || to.isStairs;
	    }
	    
	    // STAIRS RULES
	    if(to.isStairs || from.isStairs) {
	        return true; // allow vertical transitions
	    }

	    return true;
	}
	
	public void openNode(Node node) {

	    // HARD BLOCK WALLS
	    if(node.solid) return;

	    // already processed
	    if(node.checked) return;

	    // already in open list
	    if(node.open) return;
	    
	    if(!canMove(currentNode, node)) return;

	    node.open = true;
	    node.parent = currentNode;
	    openList.add(node);
	}
	
	
	public void trackThePath() {
		
		Node current = goalNode;
		
		while(current != startNode) {
			
			pathList.add(0,current);
			current = current.parent;
		}
	}
	
	
}
