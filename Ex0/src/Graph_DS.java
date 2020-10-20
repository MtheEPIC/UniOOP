import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Graph_DS implements graph {
    ArrayList<node_data> nodes;
    int edgeCount;

    public Graph_DS() {
         this.nodes = new ArrayList();
         this.edgeCount = 0;
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
//        return false; //TODO
    }

    @Override
    public void addNode(node_data n) {
        this.nodes.add(n);
    }

    @Override
    public void connect(int node1, int node2) {
        // O1
        node_data node1Pointer = getNode(node1);
        node_data node2Pointer = getNode(node2);
        if(!node1Pointer.hasNi(node2)) {
            node1Pointer.addNi(node2Pointer);
            node2Pointer.addNi(node1Pointer);
            this.edgeCount++;
        }
    }

    @Override
    public Collection<node_data> getV() {
        return null; //TODO
    }

    @Override
    public Collection<node_data> getV(int node_id) {
        return null; //TODO
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
        node_data nodePointer = getNode(key);
        Collection<node_data> neighbors = nodePointer.getNi();
        for (node_data node : neighbors) {
            node.removeNode(nodePointer);
        }
        return null; //TODO @return the data of the removed node (null if none).
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
        return 0;
    }

    @Override
    public int edgeSize() {
        return this.edgeCount; //tmp
    }

    @Override
    public int getMC() {
        return 0;
    }
}
