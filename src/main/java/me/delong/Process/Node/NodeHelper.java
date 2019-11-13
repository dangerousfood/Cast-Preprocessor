package me.delong.Process.Node;

import me.delong.DataObjects.Point;
import me.delong.Tree.Intercept;
import me.delong.Tree.Node;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by josephdelong on 1/21/17.
 */
public class NodeHelper {
    public static Intercept findIntercept(Node polygon, BigDecimal ray, Point p1, Point p2){
        //ensuring that p2 has the lower x value to ensure consistent slope calculation
        if(p1.compareX(p2)<0){
            Point swap = p2;
            p2 = p1;
            p1 = swap;
        }

        BigDecimal rise = p2.getY().subtract(p1.getY());
        BigDecimal run = p2.getX().subtract(p1.getX());

        //vertical line
        if(rise.compareTo(BigDecimal.ZERO)==0){
            return new Intercept(polygon, ray, p1.getX());
        }
        //normal case (inverse slope * distance)+p2x
        else{
            BigDecimal inverseSlope = run.divide(rise, MathContext.DECIMAL128);
            BigDecimal distance = (p2.getY().subtract(ray));
            BigDecimal x = distance.multiply(inverseSlope);

            //case where p2 is lower than the ray
            if(ray.compareTo(p2.getY())>0){
                x = x.multiply(new BigDecimal(-1));
            }
            x = x.add(p2.getX());
            return new Intercept(polygon, ray, x);
        }
    }

    public static boolean isIntersecting(BigDecimal ray, Point p1, Point p2){
        if((p1.getY().compareTo(ray)>0 && p2.getY().compareTo(ray)<=0) || (p2.getY().compareTo(ray)>=0 && p1.getY().compareTo(ray)<0)) return true;

        return false;
    }

    //sorts the two different collisons together and assures that the order is correct for ray tracing
    public static List<Intercept> specialSort(List<Intercept> intercepts){
        Collections.sort(intercepts);

        int i = 0;
        Intercept intercept1 = null;
        Intercept intercept2 = null;
        int size = intercepts.size();
        for (i = 0; i<size ; i++){
            Intercept intercept3 = intercepts.get(i);
            //ordered intercept1, intercept2, and intercept3 if(1&3 have matching reference and the intercept value of 2&3 are the same then: 2&3 swap places
            if(intercept2 != null && intercept1 != null && intercept2.getIntercept().compareTo(intercept3.getIntercept())==0 && !intercept2.getRef().equals(intercept3.getRef()) && !intercept1.getRef().equals(intercept2.getRef())){
                System.err.println("Is this ever called?");
                Collections.swap(intercepts, i, i-1);
            }
            intercept1 = intercept2;
            intercept2 = intercept3;
        }

        return intercepts;
    }
    public static BigDecimal findMidpoint(Node polygon, Node node) {

        List<BigDecimal> extentValues = new ArrayList<BigDecimal>();
        extentValues.add(polygon.getMin().getY());
        extentValues.add(polygon.getMax().getY());
        extentValues.add(node.getMin().getY());
        extentValues.add(node.getMax().getY());

        Collections.sort(extentValues);

        BigDecimal idealMidpoint = extentValues.get(2).subtract(extentValues.get(1));
        idealMidpoint = idealMidpoint.divide(new BigDecimal(2), MathContext.DECIMAL128);
        idealMidpoint = idealMidpoint.add(extentValues.get(1));

        return idealMidpoint;
    }
}
