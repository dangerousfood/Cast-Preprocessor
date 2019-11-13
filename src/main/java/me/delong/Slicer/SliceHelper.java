package me.delong.Slicer;

import me.delong.DataObjects.Point;
import me.delong.Process.DataObjects.Layer;
import me.delong.Process.IrregularPolygon.IrregPolygon;
import me.delong.Process.IrregularPolygon.IrregPolygonHelper;
import me.delong.Process.IrregularPolygon.Node;

import java.util.Collections;
import java.util.List;

/**
 * Created by josephdelong on 1/2/17.
 */
public class SliceHelper {
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Portion of code dealing with ordering line segments following slicing
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //finding irregular polygons and adding them to a new layer
    public static Layer convertPointsToIrregPolygons(List<Point> points) {
        Collections.sort(points);
        copyReferences(points);

        Layer layer = new Layer();

        Node irregPolygon = null;
        int pointCounter = 0;
        while(true){
            if(irregPolygon == null){
                pointCounter = findNextStartPoint(pointCounter, points);
                if(points.size() == pointCounter){
                    break;
                }
                irregPolygon = new Node();
                irregPolygon.setStartPoint(points.get(pointCounter));
            }

            boolean didRetrieve = retrieveReference(irregPolygon, points);

            //irregPolygon has closed irregPolygon must be added to layer to maintain the state and irregPolygon object reference will be dereferenced
            if(irregPolygon.isClosed()){
                if(IrregPolygonHelper.cleanPolygon(irregPolygon)){
                    layer.add(irregPolygon);
                }

                irregPolygon = null;
            }
            //irregPolygon size has not grown with retrieveReference() and !isClosed therefore it is an open irregPolygon, single line which is a drop case
            if(!didRetrieve){
                System.err.println("Dropped case size: "+ irregPolygon.size());
                irregPolygon = null;
            }
        }

        return layer;
    }

    //searches in linear time for the next reference in the list
    private static int findNextStartPoint(int pointCounter, List<Point> points) {
        while(pointCounter<points.size()){
            if(!points.get(pointCounter).isRetrieved()) return pointCounter;
            pointCounter++;
        }
        return pointCounter;
    }
    //looks at points references and adds them
    private static boolean retrieveReference(IrregPolygon irregPolygon, List<Point> points) {
        boolean didRetrieve = false;
        Point point = irregPolygon.get(irregPolygon.size() - 1);
        if (point.getRef().size() >= 1 && !point.getRef().get(0).isRetrieved() && !(point.getRef().get(0).equals(irregPolygon.getStartPoint()) && (irregPolygon.size() < 2))) {
            Point next = irregPolygon.get(irregPolygon.size() - 1).getRef().get(0);
            irregPolygon.add(next);
            didRetrieve = true;
        } else if (point.getRef().size() >= 2 && !point.getRef().get(1).isRetrieved()) {
            Point next = irregPolygon.get(irregPolygon.size() - 1).getRef().get(1);
            irregPolygon.add(next);
            didRetrieve = true;
        } else if (point.getRef().size() >= 3 && !point.getRef().get(2).isRetrieved()) {
            Point next = irregPolygon.get(irregPolygon.size() - 1).getRef().get(2);
            irregPolygon.add(next);
            didRetrieve = true;
        }
        return didRetrieve;
    }

    //copies points from line with references to eachother
    private static List<Point> copyReferences(List<Point> points) {
        for(int i=0; i<points.size()-1; i++){

            Point point = points.get(i);
            Point nextPoint = points.get(i+1);

            if(point.equals(nextPoint)){
                point.addRef(nextPoint);
                nextPoint.addRef(point);
            }
        }
        return points;
    }


}
