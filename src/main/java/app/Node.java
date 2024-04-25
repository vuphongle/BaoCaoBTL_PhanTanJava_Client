package app;

import java.util.ArrayList;
import java.util.List;

public class Node {
    private final String interfaceName;
    private final List<Node> children;

    public Node(String interfaceName) {
        this.interfaceName = interfaceName;
        this.children = new ArrayList<>();
    }

    public void addChild(Node child) {
        this.children.add(child);
    }

    public List<Node> getChildren() {
        return children;
    }

    public String getInterfaceName() {
        return interfaceName;
    }
}
