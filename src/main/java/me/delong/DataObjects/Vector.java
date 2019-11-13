package me.delong.DataObjects;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by josephdelong on 8/23/16.
 */
public class Vector {
    BigDecimal slopeXZ;
    BigDecimal slopeYZ;

    Vertex vertexA;
    Vertex vertexB;

    BigDecimal z1;


    public Vector(Vertex vertexA, Vertex vertexB){
        this.vertexA = vertexA;
        this.vertexB = vertexB;
        init();
    }
    private void init(){
        if(vertexA.getZ().compareTo(vertexB.getZ())==1){
            Vertex vertexSwap = vertexB;
            vertexB = vertexA;
            vertexA = vertexSwap;
        }
        z1 = vertexA.getZ();

    }

    //YZ slope is calculated with Z as the vertical axis
    private void calcSlopeYZ(Vertex vertexA, Vertex vertexB) {
        BigDecimal aZ = vertexA.getZ();
        BigDecimal aY = vertexA.getY();

        BigDecimal bZ = vertexB.getZ();
        BigDecimal bY = vertexB.getY();

        BigDecimal numerator = bZ.subtract(aZ);
        BigDecimal denominator = bY.subtract(aY);

        if(denominator.compareTo(BigDecimal.ZERO)!=0){
            slopeYZ = numerator.divide(denominator, MathContext.DECIMAL128);
        }
        else{
            slopeYZ = BigDecimal.ZERO;
        }

    }

    //XZ slope is calculated with X as the vertical axis
    private void calcSlopeXZ(Vertex vertexA, Vertex vertexB){
        BigDecimal aZ = vertexA.getZ();
        BigDecimal aX = vertexA.getX();

        BigDecimal bZ = vertexB.getZ();
        BigDecimal bX = vertexB.getX();

        BigDecimal numerator = bZ.subtract(aZ);
        BigDecimal denominator = bX.subtract(aX);

        if(denominator.compareTo(BigDecimal.ZERO)!=0){
            slopeXZ = numerator.divide(denominator, MathContext.DECIMAL128);
        }
        else{
            slopeXZ = BigDecimal.ZERO;
        }

    }
    public Point calcIntersectionXY(BigDecimal planeOfIntersection){
        Point point = new Point(BigDecimal.ZERO, BigDecimal.ZERO);

        if(slopeXZ==null || slopeYZ==null){
            calcSlopeXZ(vertexA, vertexB);
            calcSlopeYZ(vertexA, vertexB);
        }

        if(slopeXZ.compareTo(BigDecimal.ZERO)==0){
            point.setX(vertexA.getX());
        }
        else{
            BigDecimal x = (planeOfIntersection.subtract(z1).divide(slopeXZ, MathContext.DECIMAL128)).add(vertexA.getX());
            point.setX(x);
        }

        if(slopeYZ.compareTo(BigDecimal.ZERO)==0){
            point.setY(vertexA.getY());
        }
        else{
            //algebraic representation
            //y=mx+b
            //planeOfIntersection = y
            //b is not available so z1 stands in for b requires x to be added as an offset since z1 does not lie on the vertical axis origin
            BigDecimal y = (planeOfIntersection.subtract(z1).divide(slopeYZ, MathContext.DECIMAL128)).add(vertexA.getY());
            point.setY(y);
        }

        return point;
    }
    public boolean doesPlaneIntersect(BigDecimal planeOfIntersection){

        if(vertexA.getZ().compareTo(planeOfIntersection)>0 && vertexB.getZ().compareTo(planeOfIntersection)<0){
            return true;
        }
        if(vertexA.getZ().compareTo(planeOfIntersection)<0 && vertexB.getZ().compareTo(planeOfIntersection)>0){
            return true;
        }
        return false;
    }
    public boolean doesVectorRestOnPlane(BigDecimal planeOfIntersection){
        return (vertexA.getZ().compareTo(planeOfIntersection)==0 && vertexB.getZ().compareTo(planeOfIntersection)==0);
    }
    public List<Point> getEuchlidianPoints(){
        List<Point> points = new ArrayList<Point>();

        Point p1 = new Point(vertexA.getX(), vertexA.getY());
        //p1.setX(vertexA.getX());
        //p1.setY(vertexA.getY());


        Point p2 = new Point(vertexB.getX(), vertexB.getY());
        //p2.setX(vertexB.getX());
        //p2.setY(vertexB.getY());

        points.add(p1);
        points.add(p2);
        return points;
    }
}
