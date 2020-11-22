import java.util.ArrayList;
import java.util.Collection;

public class NodeData implements node_data {
    ArrayList<node_data> neighbors;
    String color;
    int tag;
    int key;
    static int keyCounter = 0;

    public NodeData() {
        this.neighbors = new ArrayList<node_data>();
        this.color = null; //tmp
        this.tag = -1;
        this.key = keyCounter++;
    }

    @Override
    public int getKey() {
        return this.key;
    }

    @Override
    public Collection<node_data> getNi() {
        return this.neighbors;
    }

    @Override
    public boolean hasNi(int key) {
        if (this.neighbors == null) {
            return false;
        }
        for (node_data neighbor : this.neighbors) { //TODO foreach
            if (neighbor.getKey() == key) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void addNi(node_data t) {
        this.neighbors.add(t);
    }

    @Override
    public void removeNode(node_data node) {
        if (this == node) {
            return;
        }
        this.neighbors.remove(node);
    }

    @Override
    public String getInfo() {
        return this.color;
    }

    @Override
    public void setInfo(String s) {
        this.color = s;
    }

    @Override
    public int getTag() {
        return this.tag;
    }

    @Override
    public void setTag(int t) {
        this.tag = t;
    }
}
