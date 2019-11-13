package me.delong.Tree;

import java.math.BigDecimal;

/**
 * Created by josephdelong on 1/6/17.
 */
public class Intercept  implements Comparable{
    private Node ref;
    private BigDecimal ray;
    private BigDecimal intercept;

    private boolean dirtyBit = false;

    public Intercept(Node ref, BigDecimal ray, BigDecimal intercept){
        this.ref = ref;
        this.ray = ray;
        this.intercept = intercept;
    }
    public Node getRef() {
        return ref;
    }

    public BigDecimal getRay() {
        return ray;
    }

    public BigDecimal getIntercept() {
        return intercept;
    }

    @Override
    public int compareTo(Object o) {
        if (o == null || getClass() != o.getClass()) return 1;
        Intercept intercept = (Intercept)o;

        return getIntercept().compareTo(intercept.getIntercept());
    }

    public boolean isDirtyBit() {
        return dirtyBit;
    }

    public void setDirtyBit(boolean dirtyBit) {
        this.dirtyBit = dirtyBit;
    }
}
