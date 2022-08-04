package model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Marwa
 */
class Graph {

    private final List<Node> graph = new ArrayList<>();

    Graph(String startStation) {
        graph.add(new Node(null, startStation));
    }

    void addNode(Node newNode) {
        graph.add(newNode);
    }

    void modifiedNode(Node newNode, int index) {
        graph.set(index, newNode);
    }

    void removeNode(int index) {
        graph.remove(index);
    }

    List<Node> getGraph() {
        return graph;
    }

    Node getNode(int index) {
        return graph.get(index);
    }

    boolean finish() {
        return graph.isEmpty();
    }
}
