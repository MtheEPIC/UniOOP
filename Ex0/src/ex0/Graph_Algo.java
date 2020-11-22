import java.util.*;

public class Graph_Algo implements graph_algorithms {
    Graph_DS g0;

    public Graph_Algo(graph g0) {
        this.g0 = new Graph_DS((Graph_DS) g0);
    }

    @Override
    public void init(graph g) {

    }

    @Override
    public graph copy() {
        Graph_DS gCopy = new Graph_DS(this.g0.getV(), this.g0.edgeSize()); // TODO combine with constructor
        return gCopy;
    }

    @Override
    public boolean isConnected() {
        if (this.g0.nodeSize() < 2) {
            return true;
        }
        ArrayList<node_data> nodes = (ArrayList<node_data>) this.g0.getV();
        node_data node = nodes.get(0);
        if (mapGraph(node.getKey()).length + 1 < this.g0.nodeSize()) {
            return false;
        }
        // check in the other direction
        node = nodes.get(1);
        if (mapGraph(node.getKey()).length + 1 < this.g0.nodeSize()) {
            return false;
        }
        return true;
    }

    @Override
    public int shortestPathDist(int src, int dest) {
        try {
            return this.shortestPath(src, dest).size() -1;
        } catch (NullPointerException e) {
            System.out.printf("ERROR: there is no path between the two nodes (%d,%d)\n", src, dest);
            return -1;
        }
    }

    @Override
    public List<node_data> shortestPath(int src, int dest) {
        ArrayList<node_data> path = new ArrayList<>();
        // check in nodes a neighbors
        if (this.g0.getV(src).contains(this.g0.getNode(dest))) {
            path.add(this.g0.getNode(src));
            path.add(this.g0.getNode(dest));
            return path;
        }

        node_data[] prev = mapGraph(src);
        return getPath(src, dest, prev);
    }
    private node_data[] mapGraph(int src) {
        Queue<node_data> q = new LinkedList<>();
        q.add(this.g0.getNode(src));
        boolean[] visited = new boolean[this.g0.nodeSize()];
        visited[src] = true;

        node_data[] prev = new node_data[this.g0.nodeSize()];
        while (!q.isEmpty()) {
            node_data node = q.poll();
            for (node_data next : this.g0.getV(node.getKey())) {
                if (!visited[next.getKey()]) {
                    q.add(next);
                    visited[next.getKey()] = true;
                    prev[next.getKey()] = node;
                }
            }
        }
        return prev;
    }
    private ArrayList<node_data> getPath(int src, int dest, node_data[] prev) {
        ArrayList<node_data> path = new ArrayList<>();
        Stack<node_data> reversPath = new Stack<>();

        node_data node = prev[dest];
        while (node != null) {
            reversPath.add(node);
            node = prev[node.getKey()];
        }

        while (!reversPath.isEmpty()) {
            path.add(reversPath.pop());
        }
        path.add(this.g0.getNode(dest));
        if (path.get(0).getKey() == src) {
            return path;
        }
        return null;
    }
}
