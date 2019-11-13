package me.delong.DataObjects;

import org.nevec.rjm.BigDecimalMath;

import java.math.BigDecimal;
import java.math.MathContext;

/**
 * Created by josephdelong on 1/19/17.
 */
public class Polar implements Comparable{
    BigDecimal radius;
    //in radians
    BigDecimal theta;

    Point point;

    public BigDecimal getRadius() {
        return radius;
    }

    public BigDecimal getTheta() {
        return theta;
    }

    public Polar(Point point, Point center) throws Exception {
        this.point = recenter(point, center);

        radius = calcRadius(this.point);
        theta = calcTheta(this.point);
    }

    private static BigDecimal calcTheta(Point point) throws Exception {
        if(point.getX().compareTo(BigDecimal.ZERO)==0 && point.getY().compareTo(BigDecimal.ZERO)==0 ){
            return BigDecimal.ZERO;
        }
        else if(point.getX().compareTo(BigDecimal.ZERO)==0 && point.getY().compareTo(BigDecimal.ZERO)>0){
            return BigDecimalMath.pi(MathContext.DECIMAL128).divide(new BigDecimal(2), MathContext.DECIMAL128);
        }
        else if(point.getX().compareTo(BigDecimal.ZERO)==0 && point.getY().compareTo(BigDecimal.ZERO)<0){
            return BigDecimalMath.pi(MathContext.DECIMAL128).divide(new BigDecimal(2), MathContext.DECIMAL128).add(BigDecimalMath.PI);
        }

        BigDecimal result = point.getY().divide(point.getX(), MathContext.DECIMAL128);
        result = BigDecimalMath.atan(result);

        //quadrant 1
        if(point.getX().compareTo(BigDecimal.ZERO)>0 && point.getY().compareTo(BigDecimal.ZERO)>0){

        }
        //quadrant 2
        else if(point.getX().compareTo(BigDecimal.ZERO)>0 && point.getY().compareTo(BigDecimal.ZERO)<0){
            result = BigDecimalMath.pi(MathContext.DECIMAL128).divide(new BigDecimal(2), MathContext.DECIMAL128).add(result);
        }
        //quadrant 3
        else if(point.getX().compareTo(BigDecimal.ZERO)<0 && point.getY().compareTo(BigDecimal.ZERO)<0){
            result = BigDecimalMath.pi(MathContext.DECIMAL128).divide(new BigDecimal(2), MathContext.DECIMAL128).add(result);
        }
        //quadrant 4
        else if(point.getX().compareTo(BigDecimal.ZERO)<0 && point.getY().compareTo(BigDecimal.ZERO)>0){
            result = BigDecimalMath.pi(MathContext.DECIMAL128).add(result);
        }

        //sanity check
        if(result.compareTo(BigDecimalMath.pi(MathContext.DECIMAL128).multiply(new BigDecimal(2), MathContext.DECIMAL128))>0) throw new Exception("Calculation exceeds 2 radians");

        return result;
    }

    public static BigDecimal calcRadius(Point point) {
        return BigDecimalMath.sqrt((point.getX().pow(2)).add(point.getY().pow(2)), MathContext.DECIMAL128);
    }

    private static Point recenter(Point point, Point centeroid){
        return new Point(point.getX().subtract(centeroid.getX()), point.getY().subtract(centeroid.getY()));
    }

    @Override
    public int compareTo(Object o) {
        if (o == null || getClass() != o.getClass()) return 1;
        Polar point = (Polar)o;
        return theta.compareTo(point.getTheta());
    }
}
