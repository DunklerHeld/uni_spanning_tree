public class Message {

    private Node root;
    private int lengthToRoot;

    private Node src;
    private int lengthToSrc;

    public Message(Node root, int lengthToRoot, Node src, int lengthToSrc) {
        this.root = root;
        this.lengthToRoot = lengthToRoot;
        this.src = src;
        this.lengthToSrc = lengthToSrc;
    }

    public Message copy() {
        return new Message(this.root, this.lengthToRoot, this.src, this.lengthToSrc);
    }

    public void setLengthToSrc(int lengthToSrc) {
        this.lengthToSrc = lengthToSrc;
    }

    public Node getRoot() {
        return root;
    }

    public int getLengthToRoot() {
        return lengthToRoot;
    }

    public Node getSrc() {
        return src;
    }

    public int getLengthToSrc() {
        return lengthToSrc;
    }
}
