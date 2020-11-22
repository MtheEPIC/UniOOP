package ex1;

import java.io.Serializable;
import java.util.ArrayList;

public class Node_DS implements node_info, Serializable, Comparable<node_info> {
    int key;
    double t;
    String color;

    public Node_DS(int key) {
        this.key = key;
    }

    @Override
    public int getKey() {
        return this.key;
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
    public double getTag() {
        return this.t;
    }

    @Override
    public void setTag(double t) {
        this.t = t;
    }

//    public ArrayList<node_info> getNeighbors() {
//        return this.neighbors;
//    }

    @Override
    public int compareTo(node_info o) {
        if (this.getTag() > o.getTag()) {
            return 1;
        } else if (this.getTag() < o.getTag()) {
            return -1;
        }
        return 0;
    }

//    public ArrayList<node_info> getNi() {
//        return this.neighbors;
//    }
//
//    public boolean hasNi(int key) {
//        if (this.neighbors == null) {
//            return false;
//        }
//        for (node_info neighbor : this.neighbors) {
//            if (neighbor.getKey() == key) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    public void addNi(node_info node) {
//        this.neighbors.add(node);
//    }
//
//    public void removeNode(node_info node) {
//        if (this == node) {
//            return;
//        }
//        this.neighbors.remove(node);
//    }
}
