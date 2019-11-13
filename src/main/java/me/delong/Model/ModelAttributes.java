package me.delong.Model;

import java.math.BigDecimal;

/**
 * Created by josephdelong on 12/28/16.
 */
public class ModelAttributes {
    BigDecimal xMin;
    BigDecimal xMax;

    BigDecimal yMin;
    BigDecimal yMax;

    BigDecimal zMin;
    BigDecimal zMax;

    public BigDecimal getxMin() {
        return xMin;
    }

    public void setxMin(BigDecimal xMin) {
        this.xMin = xMin;
    }

    public BigDecimal getxMax() {
        return xMax;
    }

    public void setxMax(BigDecimal xMax) {
        this.xMax = xMax;
    }

    public BigDecimal getyMin() {
        return yMin;
    }

    public void setyMin(BigDecimal yMin) {
        this.yMin = yMin;
    }

    public BigDecimal getyMax() {
        return yMax;
    }

    public void setyMax(BigDecimal yMax) {
        this.yMax = yMax;
    }

    public BigDecimal getzMin() {
        return zMin;
    }

    public void setzMin(BigDecimal zMin) {
        this.zMin = zMin;
    }

    public BigDecimal getzMax() {
        return zMax;
    }

    public void setzMax(BigDecimal zMax) {
        this.zMax = zMax;
    }
}
