package ai;

import java.util.ArrayList;

public class PathFinder {
    public Node[][] nodes;
    public ArrayList<Node> openList = new ArrayList<>();
    public ArrayList<Node> pathList = new ArrayList<>();
    Node startNode, goalNode, currentNode;
    boolean goalReached = false;
    int step = 0;
    int[][] navGrid;
    int maxRow, maxCol;

    public PathFinder(int[][] navGrid) {
        this.navGrid = navGrid;
        maxRow = navGrid.length;
        maxCol = navGrid[0].length;
        instantiateNodes();
    }

    public void instantiateNodes() {
        nodes = new Node[maxCol][maxRow];
        for (int r = 0; r < maxRow; r++) {
            for (int c = 0; c < maxCol; c++) {
                nodes[c][r] = new Node(c, r); // utilise ai.Node
            }
        }
    }

    public void resetNodes() {
        for (int r = 0; r < maxRow; r++) {
            for (int c = 0; c < maxCol; c++) {
                nodes[c][r].open = false;
                nodes[c][r].checked = false;
                nodes[c][r].solid = navGrid[r][c] == 0;
                nodes[c][r].parent = null;
                nodes[c][r].gCost = 0;
                nodes[c][r].hCost = 0;
                nodes[c][r].fCost = 0;
            }
        }
        openList.clear();
        pathList.clear();
        goalReached = false;
        step = 0;
    }

    public void setNodes(int startCol, int startRow, int goalCol, int goalRow) {
        resetNodes();
        startNode = nodes[startCol][startRow];
        currentNode = startNode;
        goalNode = nodes[goalCol][goalRow];
        openList.add(currentNode);

        for (int r = 0; r < maxRow; r++) {
            for (int c = 0; c < maxCol; c++) {
                getCost(nodes[c][r]);
            }
        }
    }

    public void getCost(Node node) {
        node.gCost = Math.abs(node.col - startNode.col) + Math.abs(node.row - startNode.row);
        node.hCost = Math.abs(node.col - goalNode.col) + Math.abs(node.row - goalNode.row);
        node.fCost = node.gCost + node.hCost;
    }

    public boolean search() {
        while (!goalReached && step < 500) {
            currentNode.checked = true;
            openList.remove(currentNode);

            // ajouter les voisins
            if (currentNode.row - 1 >= 0) openNode(nodes[currentNode.col][currentNode.row - 1]);
            if (currentNode.row + 1 < maxRow) openNode(nodes[currentNode.col][currentNode.row + 1]);
            if (currentNode.col - 1 >= 0) openNode(nodes[currentNode.col - 1][currentNode.row]);
            if (currentNode.col + 1 < maxCol) openNode(nodes[currentNode.col + 1][currentNode.row]);

            if (openList.size() == 0) break;

            // trouver le Node avec le meilleur fCost
            int bestNodeIndex = 0;
            int bestFCost = 999;
            for (int i = 0; i < openList.size(); i++) {
                Node n = openList.get(i);
                if (n.fCost < bestFCost || (n.fCost == bestFCost && n.gCost < openList.get(bestNodeIndex).gCost)) {
                    bestNodeIndex = i;
                    bestFCost = n.fCost;
                }
            }

            currentNode = openList.get(bestNodeIndex);

            if (currentNode == goalNode) {
                goalReached = true;
                trackPath();
            }

            step++;
        }

        return goalReached;
    }

    public void openNode(Node node) {
        if (!node.open && !node.checked && !node.solid) {
            node.open = true;
            node.parent = currentNode;
            openList.add(node);
        }
    }

    public void trackPath() {
        Node current = goalNode;
        while (current != startNode) {
            pathList.add(0, current);
            current = current.parent;
        }
    }
}