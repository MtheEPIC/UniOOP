import java.util.ArrayList;
import java.util.List;

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
        Graph_DS gCopy = new Graph_DS(this.g0.getV(), this.g0.edgeSize());
        return gCopy;
    }

    @Override
    public boolean isConnected() { //assume directional graph
        ArrayList<node_data> nodes = new ArrayList<>(this.g0.getV());
        for (node_data node : nodes) {
            for (node_data neighbor : this.g0.getV(node.getKey())) {
                if (!node.hasNi(neighbor.getKey())) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public int shortestPathDist(int src, int dest) {
        return this.shortestPath(src, dest).size(); //-1?

    }

    @Override
    public List<node_data> shortestPath(int src, int dest) { //only for fully conected
        ArrayList<node_data> path = new ArrayList<>();

        return _shortestPath(path, src, dest);
    }

    private List<node_data> _shortestPath(List<node_data> path, int src, int dest) {
        path.add(g0.getNode(src));
        if (src == dest) {
            return path;
        }
        for (node_data neighbor : g0.getV(src)) {
            if (!path.contains(neighbor)) {
                return _shortestPath(path, neighbor.getKey(), dest);
            }
        }
        return null; //path blocked
    }
}
