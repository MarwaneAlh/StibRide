package model;

/**
 *
 * @author Marwa
 */
class Node {

    private final Node nodeParent;
    private final String station;
    private final int order;

    Node(Node nodeParent, String station) {
        this.nodeParent = nodeParent;
        this.station = station;
        if (nodeParent == null) {
            order = 0;
        } else {
            order = nodeParent.getOrder() + 1;
        }
    }

    Node getNodeParent() {
        return nodeParent;
    }

    String getStation() {
        return station;
    }

    int getOrder() {
        return order;
    }
}
