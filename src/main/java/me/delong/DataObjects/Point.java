package me.delong.DataObjects;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by josephdelong on 8/20/16.
 */
public class Point implements Comparable {
    BigDecimal x;
    BigDecimal y;

    boolean retrieved = false;
    List<Point> ref = new ArrayList<Point>();

    public Point(BigDecimal x, BigDecimal y){
        this.x =x;
        this.y =y;
    }
    public boolean isRetrieved() {
        return retrieved;
    }

    public void setRetrieved(boolean retrieved) {
        this.retrieved = retrieved;
    }



    public BigDecimal getX() {
        return x;
    }

    public void setX(BigDecimal x) {
        this.x = x;
    }

    public BigDecimal getY() {
        return y;
    }

    public void setY(BigDecimal y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return x.toPlainString()+","+y.toPlainString();
    }

    @Override
    public int compareTo(Object o) {

        if (o == null || getClass() != o.getClass()) return 1;
        Point point = (Point)o;

        if(x.compareTo(point.getX())!=0){
            return x.compareTo(point.getX());
        }
        return y.compareTo(point.getY());
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Point point = (Point) o;

        return (x.compareTo(point.getX())==0 && y.compareTo(point.getY())==0);
    }

    @Override
    public int hashCode() {
        int result = 0;//super.hashCode();
        result = 31 * result + x.hashCode();
        result = 31 * result + y.hashCode();
        return result;
    }

    public int compareX(Point point){
        return getX().compareTo(point.getX());
    }

    public int compareY(Point point){
        return getY().compareTo(point.getY());
    }

    public Point subtract(Point point){
        return new Point(x.subtract(point.getX()), y.subtract(point.getY()));
    }
    public BigDecimal subtractX(Point point){
        return x.subtract(point.getX());
    }
    public BigDecimal subtractY(Point point){
        return y.subtract(point.getY());
    }

    public boolean addRef(Point point){
        return ref.add(point);
    }

    public List<Point> getRef() {
        return ref;
    }

    public void setRef(List<Point> ref) {
        this.ref = ref;
    }
}
