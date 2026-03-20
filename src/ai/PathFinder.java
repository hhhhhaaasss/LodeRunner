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
                nodes[c][r] = new Node(c, r);
            }
        }
    }

    public void resetNodes() {
        for (int r = 0; r < maxRow; r++) {
            for (int c = 0; c < maxCol; c++) {
                Node n = nodes[c][r];
                n.open = false;
                n.checked = false;
                n.solid = navGrid[r][c] == 0;
                n.parent = null;
                n.gCost = 0;
                n.hCost = 0;
                n.fCost = 0;
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
        goalNode = nodes[goalCol][goalRow];

        currentNode = startNode;

        startNode.gCost = 0;
        startNode.hCost = getHeuristic(startNode);
        startNode.fCost = startNode.hCost;

        openList.add(startNode);
    }

    // Heuristique Manhattan
    public int getHeuristic(Node node) {
        return Math.abs(node.col - goalNode.col) + Math.abs(node.row - goalNode.row);
    }

    public boolean search() {

        while (!goalReached && step < 500) {

            currentNode.checked = true;
            openList.remove(currentNode);

            int col = currentNode.col;
            int row = currentNode.row;

            // Haut
            if (row - 1 >= 0) openNode(nodes[col][row - 1]);

            // Bas
            if (row + 1 < maxRow) openNode(nodes[col][row + 1]);

            // Gauche
            if (col - 1 >= 0) openNode(nodes[col - 1][row]);

            // Droite
            if (col + 1 < maxCol) openNode(nodes[col + 1][row]);

            if (openList.isEmpty()) break;

            // Trouver le meilleur node
            int bestNodeIndex = 0;
            int bestFCost = Integer.MAX_VALUE;

            for (int i = 0; i < openList.size(); i++) {
                Node n = openList.get(i);

                if (n.fCost < bestFCost ||
                        (n.fCost == bestFCost &&
                                n.gCost < openList.get(bestNodeIndex).gCost)) {

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

        if (node.solid || node.checked) return;

        int newGCost = currentNode.gCost + 1;

        if (!node.open || newGCost < node.gCost) {

            node.gCost = newGCost;
            node.hCost = getHeuristic(node);
            node.fCost = node.gCost + node.hCost;

            node.parent = currentNode;

            if (!node.open) {
                node.open = true;
                openList.add(node);
            }
        }
    }

    public void trackPath() {

        Node current = goalNode;

        while (current != startNode) {
            pathList.add(0, current);
            current = current.parent;
        }
    }

    public static class Node {

        public int col, row;

        public boolean open = false;
        public boolean checked = false;
        public boolean solid = false;

        public int gCost, hCost, fCost;

        public Node parent = null;

        public Node(int col, int row) {
            this.col = col;
            this.row = row;
        }
    }
}