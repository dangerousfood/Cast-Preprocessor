package me.delong.Process.IrregularPolygon;

import me.delong.DataObjects.Point;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.*;

/**
 * Created by josephdelong on 1/20/17.
 */
public class IrregPolygonHelper {

    public enum ExtentValue {
        xmin, xmax, ymin, ymax
    }

    public static void toCCW(IrregPolygon polygon){
        try {
            if(isClockwise(polygon)){
                Collections.reverse(polygon);
                polygon.setClockwise(new Boolean(false));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void toClockwise(IrregPolygon polygon){
        try {
            if(!isClockwise(polygon)){
                Collections.reverse(polygon);
                polygon.setClockwise(new Boolean(true));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static boolean isClockwise(IrregPolygon polygon) throws Exception {

        if(polygon.getClockwise() != null && polygon.getClockwise()) return true;
        else if(polygon.getClockwise() != null && !polygon.getClockwise()) return false;

        List<ExtentValue> s1 = new ArrayList<ExtentValue>();
        List<ExtentValue> s2 = new ArrayList<ExtentValue>();
        //sequence 1
        int i = 0;
        for(Point point: polygon){
            if(s1.size()==0) {
                if (point.compareX(polygon.getMax()) == 0) {
                    s1.add(ExtentValue.xmax);
                }
                if (point.compareX(polygon.getMin()) == 0) {
                    s1.add(ExtentValue.xmin);
                }
                if (point.compareY(polygon.getMax()) == 0) {
                    s1.add(ExtentValue.ymax);
                }
                if (point.compareY(polygon.getMin()) == 0) {
                    s1.add(ExtentValue.ymin);
                }
                i++;
            }
        }

        for(;i<polygon.size(); i++){
            Point point = polygon.get(i);
            if(s2.size()==0) {
                if (point.compareX(polygon.getMax()) == 0) {
                    s2.add(ExtentValue.xmax);
                }
                if (point.compareX(polygon.getMin()) == 0) {
                    s2.add(ExtentValue.xmin);
                }
                if (point.compareY(polygon.getMax()) == 0) {
                    s2.add(ExtentValue.ymax);
                }
                if (point.compareY(polygon.getMin()) == 0) {
                    s2.add(ExtentValue.ymin);
                }
                List<ExtentValue> removeList = new ArrayList<ExtentValue>();
                for(ExtentValue value: s2){
                    if(s1.contains(value)){
                        removeList.add(value);
                    }
                }
                s2.removeAll(removeList);
            }
        }

        if(s1.size()>1 && s2.size()>1){
            polygon.setClockwise(doubleDoubleExtent(polygon));
            return polygon.getClockwise();
        }
        else if(s1.size()==0 || s2.size()==0) throw new Exception("No trouble");
        else{
            polygon.setClockwise(new Boolean(sequenceLookupTable(s1, s2)));
            return polygon.getClockwise();
        }
    }

    private static boolean sequenceLookupTable(List<ExtentValue> s1, List<ExtentValue> s2) throws Exception {
        for(ExtentValue value: s1){
            for(ExtentValue key: s2) {
                if (value == ExtentValue.xmin) {
                    if(key==ExtentValue.ymin)return false;
                    if(key==ExtentValue.ymax)return true;
                }
                else if (value == ExtentValue.xmax) {
                    if(key==ExtentValue.ymin)return true;
                    if(key==ExtentValue.ymax)return false;
                }
                else if (value == ExtentValue.ymin) {
                    if(key==ExtentValue.xmin)return true;
                    if(key==ExtentValue.xmax)return false;
                }
                else if (value == ExtentValue.ymax) {
                    if(key==ExtentValue.xmin)return false;
                    if(key==ExtentValue.xmax)return true;
                }
            }
        }
        throw new Exception("Failed to determine CW or CCW using lookup table");
    }

    private static boolean doubleDoubleExtent(IrregPolygon polygon){

        int i = 0;

        Point yMax;
        int indexYMax = 0;

        Point yMin;
        int indexYMin = 0;

        for(Point point: polygon){
            if(point.getY().compareTo(polygon.getMax().getY())==0){

            }
            i++;
        }


        return true;
    }

    public static void combineSlopes(IrregPolygon polygon) {
        List<Point> redundantPoints = new ArrayList<Point>();
        Point p1 = null;
        Point pivot = null;

        for(Point p2: polygon){
            if(p1!=null && pivot!=null){
                if((pivot.compareX(p1)==0 && pivot.compareX(p2)==0) || (pivot.compareY(p1)==0 && pivot.compareY(p2)==0))
                {
                    redundantPoints.add(pivot);
                }
                else{
                    BigDecimal rise = p1.getY().subtract(pivot.getY());
                    BigDecimal run = p1.getX().subtract(pivot.getX());

                    BigDecimal rise1 = pivot.getY().subtract(p2.getY());
                    BigDecimal run1 = pivot.getX().subtract(p2.getX());

                    if(rise.compareTo(BigDecimal.ZERO)!=0 && run.compareTo(BigDecimal.ZERO)!=0 && rise1.compareTo(BigDecimal.ZERO)!=0 && run1.compareTo(BigDecimal.ZERO)!=0){
                        BigDecimal slope1 = rise.divide(run, MathContext.DECIMAL128);
                        BigDecimal slope2 = rise1.divide(run1, MathContext.DECIMAL128);
                        if(slope1.compareTo(slope2)==0){
                            redundantPoints.add(pivot);
                        }
                    }
                }
            }
            p1 = pivot;
            pivot = p2;
        }

        polygon.removeAll(redundantPoints);
    }

    public static void removeDuplicates(IrregPolygon polygon) {
        Set<Point> s = new LinkedHashSet<Point>(polygon);
        polygon.clear();
        polygon.addAll(s);
        polygon.setClosed(false);
        polygon.add(polygon.getStartPoint());
    }

    public static boolean integrityCheck(IrregPolygon polygon) {
        return (polygon.size()>2 && polygon.getStartPoint()!=null && polygon.getMin()!=null && polygon.getMax()!=null && polygon.isClosed());
    }

    public static void calculateBounds(IrregPolygon polygon, Point point){
        Point min = polygon.getMin();
        Point max = polygon.getMax();

        if((min == null || max == null) && point != null){
            min = new Point(new BigDecimal(point.getX().toString()), new BigDecimal(point.getY().toString()));
            max = new Point(new BigDecimal(point.getX().toString()), new BigDecimal(point.getY().toString()));
        }

        if(point.getX().compareTo(min.getX())==-1){
            min.setX(new BigDecimal(point.getX().toString()));
        }
        else if(point.getX().compareTo(max.getX())==1){
            max.setX(new BigDecimal(point.getX().toString()));
        }

        if(point.getY().compareTo(min.getY())==-1){
            min.setY(new BigDecimal(point.getY().toString()));
        }
        else if(point.getY().compareTo(max.getY())==1){
            max.setY(new BigDecimal(point.getY().toString()));
        }

        polygon.setMin(min);
        polygon.setMax(max);
    }

    public static boolean cleanPolygon(IrregPolygon polygon){
        if(integrityCheck(polygon)){
            removeDuplicates(polygon);
            toClockwise(polygon);
            combineSlopes(polygon);
            return true;
        }
        return false;
    }

    public static boolean addPoint(IrregPolygon polygon, Point point){
        if(point.equals(polygon.getStartPoint()) && polygon.size()>2 && !polygon.isClosed()){
            polygon.setClosed(true);
        }
        else if(polygon.isClosed() || point.isRetrieved()){
            return false;
        }
        point.setRetrieved(true);

        return true;
    }
}
