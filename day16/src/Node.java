import java.util.ArrayList;

public class Node {
    public int x;
    public int y;
    public int cost = Integer.MAX_VALUE;
    public int bestCost = Integer.MAX_VALUE;
    public String currentDirection;
    public String nodeType;
    public ArrayList<Node> parent = new ArrayList<>();
    public Node lastParent;

    public int getAddCost(Node node) {
        if (currentDirection.equals(">")) {
            if (x + 1 == node.x) {
                return 1;
            } else if (x - 1 == node.x) {
                System.out.println("doubleturn1");
                return 2001;
            } else if (y + 1 == node.y) {
                return 1001;
            } else if (y - 1 == node.y) {
                return 1001;
            }

        } else if (currentDirection.equals("<")) {
            if (x + 1 == node.x) {
                System.out.println("doubleturn2");
                return 2001;
            } else if (x - 1 == node.x) {
                return 1;
            } else if (y + 1 == node.y) {
                return 1001;
            } else if (y - 1 == node.y) {
                return 1001;
            }

        } else if (currentDirection.equals("^")) {
            if (y - 1 == node.y) {
                return 1;
            } else if (y + 1 == node.y) {
                System.out.println("doubleturn3");
                return 2001;
            } else if (x + 1 == node.x) {
                return 1001;
            } else if (x - 1 == node.x) {
                return 1001;
            }

        } else if (currentDirection.equals("v")) {
            if (y + 1 == node.y) {
                return 1;
            } else if (y - 1 == node.y) {
                System.out.println("doubleturn4");
                return 2001;
            } else if (x + 1 == node.x) {
                return 1001;
            } else if (x - 1 == node.x) {
                return 1001;
            }
        }
        return 1;
    }

    public void changeDirection(Node node) {
        if (currentDirection.equals(">")) {
            if (x + 1 == node.x) {
                node.currentDirection = currentDirection;
            } else if (x - 1 == node.x) {
                node.currentDirection = "<";
            } else if (y - 1 == node.y) {
                node.currentDirection = "^";
            } else if (y + 1 == node.y) {
                node.currentDirection = "v";
            }

        } else if (currentDirection.equals("<")) {
            if (x + 1 == node.x) {
            } else if (x - 1 == node.x) {
                node.currentDirection = currentDirection;
            } else if (y - 1 == node.y) {
                node.currentDirection = "^";
            } else if (y + 1 == node.y) {
                node.currentDirection = "v";
            }

        } else if (currentDirection.equals("^")) {
            if (y - 1 == node.y) {
                node.currentDirection = currentDirection;
            } else if (y + 1 == node.y) {
                node.currentDirection = ("v");
            } else if (x + 1 == node.x) {
                node.currentDirection = ">";
            } else if (x - 1 == node.x) {
                node.currentDirection = "<";
            }

        } else if (currentDirection.equals("v")) {
            if (y + 1 == node.y) {
                node.currentDirection = currentDirection;
            } else if (y - 1 == node.y) {
                node.currentDirection = "^";
            } else if (x + 1 == node.x) {
                node.currentDirection = ">";
            } else if (x - 1 == node.x) {
                node.currentDirection = "<";
            }
        }
    }
    public String getNewDirection2(Node node) {
        if (x + 1 == node.x) return ">";
        if (x - 1 == node.x) return "<";
        if (y + 1 == node.y) return "v";
        if (y - 1 == node.y) return "^";
        return currentDirection; // Default to the current direction
    }

    public int getAddCost2(Node node) {
        if (x + 1 == node.x || x - 1 == node.x || y + 1 == node.y || y - 1 == node.y) {
            // Moving forward
            return 1;
        } else {
            // Any other case is a direction change
            return 1001; // Penalty for turning
        }
    }
}
