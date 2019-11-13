package me.delong.Process.IrregularPolygon;

import me.delong.Constants;
import me.delong.DataObjects.Point;
import me.delong.SVGHelper;
import me.delong.Process.Node.NodeHelper;
import me.delong.Tree.Intercept;
import me.delong.Tree.Node;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by josephdelong on 8/25/16.
 */
public class IrregPolygon extends ArrayList<Point> implements Node{
    Point startPoint = null;
    boolean isClosed = false;

    Boolean isClockwise = null;

    Point min = null;
    Point max = null;


    public Point getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(Point startPoint) {
        this.startPoint = startPoint;
        add(startPoint);
    }

    public boolean isClosed() {
        return isClosed;
    }

    public void setClosed(boolean closed) {
        isClosed = closed;
    }

    @Override
    public Point getMin() {
        return min;
    }

    public void setMin(Point min) {
        this.min = min;
    }

    @Override
    public Point getMax() {
        return max;
    }

    public void setMax(Point max) {
        this.max = max;
    }


    public Boolean getClockwise() {
        return isClockwise;
    }

    public void setClockwise(Boolean clockwise) {
        isClockwise = clockwise;
    }

    @Override
    public boolean add(Point point) {
        if(!IrregPolygonHelper.addPoint(this, point)) return false;
        IrregPolygonHelper.calculateBounds(this, point);

        return super.add(point);
    }

    @Override
    public boolean remove(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        if(point.equals(startPoint))return false;

        return super.remove(o);
    }

    @Override
    public List<Node> next(Node node) {
        return null;
    }

    @Override
    public boolean isLeaf() {
        return true;
    }

    @Override
    public boolean isBoxOverlapping(Node node) {
        return Node.isBoxOverlapping(node, this);
    }

    @Override
    public Set<Intercept> tracer(BigDecimal ray) {
        Set<Intercept> intercepts = new LinkedHashSet<Intercept>();
        Point lastPoint = null;
        for(Point point: this){
            if(lastPoint!=null){
                if(NodeHelper.isIntersecting(ray, point, lastPoint)){
                    intercepts.add(NodeHelper.findIntercept(this ,ray, point, lastPoint));
                }
            }
            lastPoint = point;
        }
        return intercepts;
    }

    //Node node input isNested==true; if node is inside of this
    @Override
    public boolean isNested(Node node) throws Exception {
        if(node == null){
            throw new Exception("Comparing node is null and cannot be tested for nested property");
        }
        //calculate ideal midpoint of the two
        BigDecimal idealMidpoint = NodeHelper.findMidpoint(this, node);
        List<Intercept> collision = null;
        List<Intercept> nestedCollision = null;

        collision = new ArrayList<Intercept>(tracer(idealMidpoint));

        nestedCollision = new ArrayList<Intercept>(node.tracer(idealMidpoint));

        collision.addAll(nestedCollision);
        collision = NodeHelper.specialSort(collision);

        //collisions must not be null && collision.size() must be a multiple of two otherwise the object is not closed
        if(collision!=null && nestedCollision!=null && collision.size()>0 && nestedCollision.size()>0 && (collision.size()%2==0 && nestedCollision.size()%2==0)){

            int parentIndex = 0;
            int childIndex = 0;

            //values are for after loop evaluation
            boolean isNested = false;
            boolean isOutside = false;

            for (Intercept intercept: collision) {
                if(intercept.getRef().equals(this)){
                    parentIndex++;
                    parentIndex%=2;
                }
                else if(intercept.getRef().equals(node)){
                    childIndex++;

                    //nested case
                    if(childIndex%2==0 && parentIndex==1){
                        isNested = true;
                    }
                    //outside case
                    else if(childIndex%2==0 && parentIndex==0){
                        isOutside = true;
                    }

                    childIndex%=2;
                }
            }
            if(isNested && !isOutside) return true;
            else if(!isNested && isOutside) return false;
        }
        //node or this is are overlapping or malformed (uncommon edge case)
        throw new Exception("Overlapping objects case or other malformed object case");
    }

    public String toSVG(String thing){
        //example data
        /*
            <polygon points="60,20 100,40 100,80 60,100 20,80 20,40"/>
         */
        StringBuilder stringBuilder = new StringBuilder();

        Iterator<Point> itr = this.iterator();
        stringBuilder.append("<polygon points=\"");

        while (itr.hasNext()) {
            Point point = itr.next();
            stringBuilder.append(point.toString());
            if(itr.hasNext()){
                stringBuilder.append(" ");
            }
        }
        stringBuilder.append("\""+thing+"/>");

        return stringBuilder.toString();
    }

    public String toCompoundPath(){
        //example data
        /*
            <path d="M60,20 L100,40 L100,80 L60,100 L20,80 L20,40Z"/>
         */
        StringBuilder stringBuilder = new StringBuilder();

        Iterator<Point> itr = iterator();

        int i = 0;
        while (itr.hasNext()) {
            Point point = itr.next();
            if(i==0){
                stringBuilder.append(SVGHelper.MOVETO);
            }
            else{
                stringBuilder.append(SVGHelper.LINETO);
            }
            stringBuilder.append(point.toString());
            if(itr.hasNext()){
                stringBuilder.append(Constants.SPACE);
            }
            i++;
        }
        stringBuilder.append(SVGHelper.CLOSEPOLYGON);

        return stringBuilder.toString();
    }
}
