package me.delong.Process.IrregularPolygon;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jdelong on 5/29/17.
 */
public class Node extends IrregPolygon implements Comparable{

    int order = 0;

    List<Node> children = new ArrayList<Node>();

    public List<Node> getChildren() {
        return children;
    }

    public void setChildren(List<Node> children) {
        this.children = children;
    }

    public void addChild(Node child){
        children.add(child);
    }

    //removes all children that are != order+1
    public void dropChildren(){
        List<Node> removeList = new ArrayList<Node>();
        for(Node child: children){
            if(child.getOrder()!=(order+1)) removeList.add(child);
        }
        children.remove(removeList);
    }

    public void increment(){
        order++;
    }
    public int getOrder() {
        return order;
    }

    @Override
    public int compareTo(Object o) {

        if (o == null || getClass() != o.getClass()) return 1;
        Node node = (Node)o;

        if(node.getOrder()>order) return 1;
        else if((node.getOrder()<order))return -1;

        return 0;
    }
}
