import java.util.ArrayList;

public class Edge {

    private int weight;
    private ArrayList<Node> nodes;


    public Edge(int weight) {
        this.weight = weight;
        this.nodes = new ArrayList<>();
    }

    public void transmitMessage(Message message) {

        for (Node n : nodes) {
            if (!n.equals(message.getSrc())) {
                n.receiveMessage(message);
            }
        }

    }

    public void removeNode(Node n) {
        this.nodes.remove(n);
    }

    public void clearNodes() {
        this.nodes = new ArrayList<>();
    }

    public void addNode(Node n) {
        this.nodes.add(n);
    }

    public Node[] getNodes() {
        return (Node[]) nodes.toArray();
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
