package me.delong.Slicer;

import java.math.BigDecimal;
import java.math.MathContext;

/**
 * Created by josephdelong on 12/17/16.
 */
public class SlicerConfig {
    BigDecimal zResolution;
    BigDecimal xyResolution;
    BigDecimal kerf;
    int threadCount;
    MathContext precision;

    public BigDecimal getzResolution() {
        return zResolution;
    }

    public void setzResolution(BigDecimal zResolution) {
        this.zResolution = zResolution;
    }

    public BigDecimal getXyResolution() {
        return xyResolution;
    }

    public void setXyResolution(BigDecimal xyResolution) {
        this.xyResolution = xyResolution;
    }

    public BigDecimal getKerf() {
        return kerf;
    }

    public void setKerf(BigDecimal kerf) {
        this.kerf = kerf;
    }

    public int getThreadCount() {
        return threadCount;
    }

    public void setThreadCount(int threadCount) {
        this.threadCount = threadCount;
    }

    public MathContext getPrecision() {
        return precision;
    }

    public void setPrecision(MathContext precision) {
        this.precision = precision;
    }
}
