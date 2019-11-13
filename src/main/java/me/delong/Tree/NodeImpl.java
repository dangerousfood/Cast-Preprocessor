package me.delong.Tree;

import me.delong.DataObjects.Point;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by josephdelong on 1/5/17.
 */
public abstract class NodeImpl extends ArrayList<Node> implements Node {
    Point min;
    Point max;

    public NodeImpl(){
    }

    @Override
    public boolean add(Node node) {
        if(min == null || max == null){
            min = new Point(node.getMin().getX(), node.getMin().getY());
            max = new Point(node.getMin().getX(), node.getMin().getY());
            return super.add(node);
        }

        if(min.compareX(node.getMin())>=0){
            min.setX(node.getMin().getX());
        }

        if(min.compareY(node.getMin())>=0){
            min.setY(node.getMin().getY());
        }

        if(max.compareX(node.getMax())<=0){
            max.setX(node.getMax().getX());
        }

        if(max.compareY(node.getMax())<=0){
            max.setY(node.getMax().getY());
        }
        return super.add(node);
    }

    @Override
    public Point getMin() {
        return min;
    }

    @Override
    public Point getMax() {
        return max;
    }

    @Override
    public boolean isBoxOverlapping(Node node) {
        return Node.isBoxOverlapping(node, this);
    }

    @Override
    public List<Node> next(Node node) {
        List<Node> nextList = new ArrayList<Node>();
        Iterator<Node> itr = iterator();
        while(itr.hasNext()){
            Node nextNode = itr.next();
            if(Node.isBoxOverlapping(node, nextNode)){
                nextList.add(node);
            }
        }

        if(nextList.size()>0){
            return nextList;
        }
        return null;
    }

    @Override
    public boolean isLeaf() {
        return false;
    }
}
