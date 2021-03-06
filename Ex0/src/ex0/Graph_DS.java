import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Graph_DS implements graph {
    ArrayList<node_data> nodes;
    int edgeCount;

    public Graph_DS() {
        this(new ArrayList<>(), 0);
//        this.nodes = new ArrayList();
//        this.edgeCount = 0;
    }

    public Graph_DS(Collection<node_data> nodes, int edgeCount) {
        this.nodes = new ArrayList<>(nodes);
        this.edgeCount = edgeCount;
    }

    public Graph_DS(Graph_DS g) {
        this.nodes = new ArrayList<>(g.getV());
        this.edgeCount = g.edgeSize();
    }

    @Override
    public node_data getNode(int key) {
        try {
            return this.nodes.get(key);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("ERROR: the given node key isn't in an acceptable range");
            return null;
        }
    }

    @Override
    public boolean hasEdge(int node1, int node2) {
        node_data node1DS = getNode(node1);
        return node1DS.hasNi(node2);
    }

    @Override
    public void addNode(node_data n) {
        this.nodes.add(n);
    }

    @Override
    public void connect(int node1, int node2) {
        node_data node1Pointer = getNode(node1);
        node_data node2Pointer = getNode(node2);
        if(node1==node2) { //Assume you can't connect a node to itself
            return;
        }
        if(!node1Pointer.hasNi(node2)) {
            node1Pointer.addNi(node2Pointer);
            node2Pointer.addNi(node1Pointer);
            this.edgeCount++;
        }
    }

    @Override
    public Collection<node_data> getV() { //shallow copy
        return this.nodes;
    }

    @Override
    public Collection<node_data> getV(int node_id) {
        return this.getNode(node_id).getNi();
    }

    @Override
    public node_data removeNode(int key) {
        /**
         * Delete the node (with the given ID) from the graph -
         * and removes all edges which starts or ends at this node.
         * This method should run in O(n), |V|=n, as all the edges should be removed.
         * @return the data of the removed node (null if none).
         * @param key
         */
        node_data nodePointer = this.getNode(key);
        if (nodePointer == null) {
            return null;
        }
        Collection<node_data> neighbors = nodePointer.getNi();
        for (node_data node : neighbors) {
            node.removeNode(nodePointer);
        }
        return nodePointer;
    }

    @Override
    public void removeEdge(int node1, int node2) {
        node_data node1Pointer = getNode(node1);
        node_data node2Pointer = getNode(node2);
//        System.out.println(node1Pointer.hasNi(node2));
        if(node1Pointer.hasNi(node2)) {
            node1Pointer.removeNode(node2Pointer);
            node2Pointer.removeNode(node1Pointer);
            this.edgeCount--;
        }
        // TODO @return the data of the removed edge (null if none).
    }

    @Override
    public int nodeSize() {
        return this.nodes.size();
    }

    @Override
    public int edgeSize() {
        return this.edgeCount;
    }

    @Override
    public int getMC() { //TODO implement
        return 0;
    }
}
