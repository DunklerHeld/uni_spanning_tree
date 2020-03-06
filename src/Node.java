import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Objects;

public class Node {

    private char name;
    private int id;

    private Node root;
    private Node hopToRoot;
    private int lengthToRoot;

    private ArrayList<Edge> edges;

    private ArrayList<Message> receivedMessages;

    public Node(char name, int id) {
        this.name = name;
        this.id = id;
        this.edges = new ArrayList<>();
        this.receivedMessages = new ArrayList<>();
        this.hopToRoot = this;
        this.root = this;
        this.lengthToRoot = 0;
    }

    //returns whether the routing info was changed
    public boolean computeMessages() {

        boolean changedInfo = false;

        for (Message message : receivedMessages) {

            int totalLengthToRoot = message.getLengthToRoot() + message.getLengthToSrc();

            if (this.root.id > message.getRoot().getId()) {
                this.root = message.getRoot();
                this.hopToRoot = message.getSrc();
                this.lengthToRoot = totalLengthToRoot;

                changedInfo = true;
                continue;
            }

            if (this.root.id == message.getRoot().getId() &&
                    this.lengthToRoot > totalLengthToRoot) {
                this.hopToRoot = message.getSrc();
                this.lengthToRoot = totalLengthToRoot;

                changedInfo = true;
                continue;
            }

        }

        receivedMessages = new ArrayList<>();

        return changedInfo;

    }

    public String getOutputString() {
        String ret = "";

        ret += this.name;
        ret += " -> ";
        ret += this.hopToRoot.getName();
        ret += ";";

        return ret;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return name == node.name &&
                id == node.id;
    }

    public void receiveMessage(Message m) {
        receivedMessages.add(m);
    }

    public void sendMessages() {

        Message message = new Message(this.root, this.lengthToRoot, this, 0);

        for (Edge e : edges) {
            Message m = message.copy();
            m.setLengthToSrc(e.getWeight());
            e.transmitMessage(m);
        }

    }

    public int getLengthToRoot() {
        return lengthToRoot;
    }

    public void setLengthToRoot(int lengthToRoot) {
        this.lengthToRoot = lengthToRoot;
    }

    public Node getRoot() {
        return root;
    }

    public void setRoot(Node root) {
        this.root = root;
    }

    public char getName() {
        return name;
    }

    public void setName(char name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Node getHopToRoot() {
        return hopToRoot;
    }

    public void setHopToRoot(Node hopToRoot) {
        this.hopToRoot = hopToRoot;
    }

    public Edge[] getEdges() {
        return (Edge[]) edges.toArray();
    }

    public void addEdge(Edge e) {
        this.edges.add(e);
    }

    public void removeEdge(Edge e) {
        this.edges.remove(e);
    }

    public void clearEdges() {
        this.edges = new ArrayList<>();
    }
}
