package me.delong.DataObjects;

import java.math.BigDecimal;

/**
 * Created by josephdelong on 1/24/15.
 */
public class Vertex implements Comparable{
    BigDecimal x;
    BigDecimal y;
    BigDecimal z;

    public BigDecimal getZ() {
        return z;
    }

    public void setZ(BigDecimal z) {
        this.z = z;
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
    public int compareTo(Object o) {
        return z.compareTo(((Vertex)o).getZ());
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString(){
        return "<"+x+", "+y+","+z+">";
    }

    public Point toEuchlidian(){
        Point point = new Point(x,y);
        //point.setX(x);
        //point.setY(y);
        return point;
    }
}
