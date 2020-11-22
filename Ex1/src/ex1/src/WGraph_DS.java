package ex1;

import java.io.Serializable;
import java.util.*;

public class WGraph_DS implements weighted_graph, Serializable {
    ArrayList<node_info> nodesCollection;
    ArrayList<HashMap<node_info, HashMap<node_info, Double>>> nodes;

    int edgeCount;
    int nodeCount;

    public WGraph_DS() {
        this.nodesCollection = new ArrayList<>();
        this.nodes = new ArrayList<>();
        this.edgeCount = 0;
        this.nodeCount = 0;
    }

    public WGraph_DS(WGraph_DS copy) {
        this.nodesCollection = new ArrayList<>((Collection) copy.nodesCollection);
        this.nodes = new ArrayList<>((Collection) copy.nodes);
        this.edgeCount = copy.edgeCount;
        this.nodeCount = copy.nodeCount;
    }

    @Override
    public node_info getNode(int key) {
        if (nodeExists(key)) {
            return this.nodesCollection.get(key);
        }
        return null;
    }

    @Override
    public boolean hasEdge(int node1, int node2) {
        if (!this.nodeExists(node1) || !this.nodeExists(node2)) {
            return false;
        }
        return this.getConnections(node1).containsKey(this.getNode(node2)) &&
            this.getConnections(node1).containsKey(this.getNode(node2));
    }

    @Override
    public double getEdge(int node1, int node2) {
        if (!this.hasEdge(node1, node2)) {
            return -1;
        }
        return this.getConnections(node1).get(this.getNode(node2));
    }

    @Override
    public void addNode(int key) {
        // i assumed it will always be 0,1,2..
        // check if node already exists
        if (this.nodeExists(key)) {
            return;
        }

        HashMap<node_info, HashMap<node_info, Double>> nodeInfo = new HashMap<>();
        HashMap<node_info, Double> connections = new HashMap<>();

        Node_DS node = new Node_DS(key);
        nodeInfo.put(node, connections);

        this.nodes.add(nodeInfo);
        this.nodesCollection.add(node);
        this.nodeCount++;
    }

    /**
     * checks if node exists and returns true if it does
     * @param key
     * @return
     */
    private boolean nodeExists(int key) {
        if (this.nodes.size() > key) {
            if (this.nodes.get(key) != null) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void connect(int node1, int node2, double w) {
        //O1, if exists update weight
        // check if nodes are same
        if (node1 == node2) {
            return;
        }
        // check if nodes exist
        node_info nodeDS1 = this.getNode(node1);
        node_info nodeDS2 = this.getNode(node2);
        if (nodeDS1 == null || nodeDS2 == null) {
            return;
        }
        // connect if not connected, else only update weight
        if (!this.hasEdge(node1, node2)) {
            this.connectEstablish(node1, node2);
            this.connectEstablish(node2, node1);
            this.edgeCount++;
        }
        connectWeight(node1, nodeDS2, w);
        connectWeight(node2, nodeDS1, w);
    }

    private HashMap<node_info, Double> getConnections(int key) {
        return this.nodes.get(key).get(this.getNode(key));
    }

    private void connectEstablish(int node1, int node2) {
        HashMap<node_info, Double> connections = getConnections(node1);
        connections.put(this.getNode(node2), -1.0);
    }

    private void connectWeight(int node1, node_info nodeDS2, double weight) {
        this.getConnections(node1).replace(nodeDS2, weight);
    }

    @Override
    public Collection<node_info> getV() {
        return this.nodesCollection;
    }

    /** loop code from https://stackoverflow.com/questions/4234985/how-to-for-each-the-hashmap **/
    @Override
    public Collection<node_info> getV(int node_id) { //O[k] k=degree
        if (!nodeExists(node_id)) {
            return null;
        }
        ArrayList<node_info> v = new ArrayList<>();
        for (Map.Entry<node_info, Double> connection : this.getConnections(node_id).entrySet()) {
            v.add(connection.getKey());
        }
        return v;
    }

    /** loop code from https://stackoverflow.com/questions/4234985/how-to-for-each-the-hashmap **/
    @Override
    public node_info removeNode(int key) {
        // check if node exists
        if (!this.nodeExists(key)) {
            return null;
        }
        // remove node for neighbors 'memory'
        for (Map.Entry<node_info, Double> connection : this.getConnections(key).entrySet()) {
            node_info neighbor = connection.getKey();
            this.getConnections(neighbor.getKey()).remove(this.getNode(key));
            this.edgeCount--;
        }
        // remove node from memory
        this.nodesCollection.set(key, null);
        this.nodes.set(key, null);

        this.nodeCount--;
        return null; //TODO return the data of the removed node (null if none).
    }

    @Override
    public void removeEdge(int node1, int node2) {
        this.getConnections(node1).remove(this.getNode(node2));
        this.getConnections(node2).remove(this.getNode(node1));
        this.edgeCount--;
    }

    @Override
    public int nodeSize() {
        return this.nodeCount;
    }

    @Override
    public int edgeSize() {
        return this.edgeCount;
    }

    @Override
    public int getMC() {
        return 0; //TODO
    }
}
