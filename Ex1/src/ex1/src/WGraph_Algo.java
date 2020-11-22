package ex1;

import java.io.*;
import java.util.*;

public class WGraph_Algo implements weighted_graph_algorithms {
    WGraph_DS g;

    public WGraph_Algo() {
        this.g = null;
    }

    @Override
    public void init(weighted_graph g) {
        this.g = (WGraph_DS) g;
    }

    @Override
    public weighted_graph getGraph() {
        return this.g;
    }

    @Override
    public weighted_graph copy() {
        return new WGraph_DS(this.g);
    }

    @Override
    public boolean isConnected() {
        // copy of nodes collection to prevent changes in the data of the graph
        ArrayList<node_info> nodes = new ArrayList<>((Collection) this.g.getV());

        nodes.removeIf(Objects::isNull);
        // a graph with 1/0 nodes is connected
        if (nodes.size() < 2) {
            return true;
        }
        // check if one node has a path to the rest (+1 to prev because source node is seen as null)
        HashMap<node_info, node_info> prev = dijkstra(0, -1);
        prev.values().removeIf(Objects::isNull);
        if (prev.values().size()+1 != nodes.size()) {
            return false;
        }
        // check for the opposite path
        prev = dijkstra(1, -1);
        if (prev.size() != nodes.size()) {
            return false;
        }
        return true;
    }

    @Override
    public double shortestPathDist(int src, int dest) {
        // check if source and destination exist
        if (this.g.getNode(src) == null || this.g.getNode(dest) == null) {
            return -1;
        }

        HashMap<node_info, node_info> prev = dijkstra(src, dest);
        node_info node = prev.get(this.g.getNode(dest));
        // check if the destination has been reached
        if (node == null) {
            return -1;
        }
        return this.g.getNode(dest).getTag();
    }

    @Override
    public List<node_info> shortestPath(int src, int dest) {
        ArrayList<node_info> path = new ArrayList<>();
        // check if source and destination exist
        if (this.g.getNode(src) == null || this.g.getNode(dest) == null) {
            return null;
        }

        HashMap<node_info, node_info> prev = dijkstra(src, dest);
//        ArrayList<node_info> prev = dijkstra(src);

        node_info node = prev.get(this.g.getNode(dest));
        // check if the destination has been reached
        if (node == null) {
            return null;
        }
        while (node != null) {
            path.add(node);
            node = prev.get(node);
        }

        Collections.reverse(path);
        path.add(this.g.getNode(dest));
        if (path.get(0).getKey() == src) {
            return path;
        }
        return null;
    }

    /**
     * implementation of dijkstra's search
     * @param src
     * @return
     */
    private HashMap<node_info, node_info> dijkstra(int src, int dest) {
        HashMap<node_info, node_info> prevDict = new HashMap<>(); //TODO Pqueue
        double w;
        for (node_info node : this.g.getV()) {
            node.setInfo("white"); // not visited
            node.setTag(-1); // no distance from src
            prevDict.put(node, null);
        }
        node_info node = this.g.getNode(src);
        if (node == null) {
            System.out.println("source node doesn't exist");
            return null;
        }

        PriorityQueue<node_info> pq = new PriorityQueue<>();
        pq.add(node);
        node.setTag(0);

        while (!pq.isEmpty()) {
            node = pq.poll();
            if (node.getKey() == dest) {
                return prevDict;
            }
            for (node_info neighbor : this.g.getV(node.getKey())) {
                if (neighbor.getInfo().equals("white")) {
                    w = this.g.getEdge(node.getKey(), neighbor.getKey());
                    // if never visited add first distance
                    if (neighbor.getTag() == -1) {
                        neighbor.setTag(node.getTag() + w);
                        prevDict.replace(neighbor, node);
                    }
                    // check if distance can improve
                    if (neighbor.getTag() > node.getTag()+w) {
                        neighbor.setTag(node.getTag() + w);
                        prevDict.replace(neighbor, node);
                    }
                    // add node to search queue
//                    q.add(neighbor);
                    pq.add(neighbor);
                }
            }
            // mark as visited
            node.setInfo("black");
        }

        return prevDict;
    }

    @Override
    public boolean save(String file) {
//        file = "C:/Users/mthee/Desktop/g0.obj"; //check full path
        try {
            FileOutputStream fOut = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fOut);
            oos.writeObject(this.g);
            oos.flush();
            oos.close();
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean load(String file) {
        WGraph_DS newG;
        try {
            FileInputStream fIn = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fIn);
            newG = (WGraph_DS) ois.readObject();
            ois.close();
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        this.g = newG;
//        this.g.nodeCount = newG.nodeCount;
//        this.g.edgeCount = newG.edgeCount;
//        this.g.nodes = newG.nodes;
//        this.g.nodesCollection = newG.nodesCollection;
        return true; //TODO
    }
}
