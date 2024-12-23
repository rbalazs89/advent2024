import java.util.ArrayList;

public class Node {
    public String value;
    public ArrayList<Node> connectedNodes;
    public void connectNode(Node node){
        if(!connectedNodes.contains(node)){
            connectedNodes.add(node);
        }
        if(!node.connectedNodes.contains(this)){
            node.connectedNodes.add(this);
        }
    }
}
