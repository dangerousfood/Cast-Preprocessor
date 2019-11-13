package me.delong.Tree;

import me.delong.DataObjects.Point;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Created by josephdelong on 1/11/17.
 */
public interface Node {
    public Point getMin();
    public Point getMax();

    //tests main node then tests children and returns a list of children
    public List<Node> next(Node node);
    public boolean isLeaf();

    public boolean isBoxOverlapping(Node node);
    //true means node1 bounding box and node2 bounding box overlap
    //false means they do not overlap
    public static boolean isBoxOverlapping(Node node1, Node node2){
        if(isPointInside(node1.getMin(),node2.getMin(),node2.getMax()) || isPointInside(node1.getMax(),node2.getMin(),node2.getMax()) || isPointInside(node2.getMin(),node1.getMin(),node1.getMax()) || isPointInside(node2.getMax(),node1.getMin(),node1.getMax())) return true;
        return false;
    }
    public static boolean isPointInside(Point evaluate, Point min, Point max){
        //if((evaluate.compareX(min)>=0 && evaluate.compareX(max)<=0) && (evaluate.compareY(min)>=0 && evaluate.compareY(max)<=0)) return true;
        boolean x = false;
        boolean y = false;

        List<BigDecimal> extentValuesX = new ArrayList<BigDecimal>();
        extentValuesX.add(evaluate.getX());
        extentValuesX.add(min.getX());
        extentValuesX.add(max.getX());
        Collections.sort(extentValuesX);

        if(evaluate.getX().equals(extentValuesX.get(1))) x = true;

        List<BigDecimal> extentValuesY = new ArrayList<BigDecimal>();
        extentValuesY.add(evaluate.getY());
        extentValuesY.add(min.getY());
        extentValuesY.add(max.getY());
        Collections.sort(extentValuesY);

        if(evaluate.getY().equals(extentValuesY.get(1))) y = true;


        if(x && y) return true;
        return false;
    }

    //both classes should be implemented by the object that is being checked for collision
    public abstract Set<Intercept> tracer(BigDecimal ray);
    public abstract boolean isNested(Node node) throws Exception;
}
