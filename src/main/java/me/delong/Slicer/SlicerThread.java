package me.delong.Slicer;

import me.delong.DataObjects.Point;
import me.delong.DataObjects.Polygon;
import me.delong.DataObjects.Vector;
import me.delong.DataObjects.Vertex;
import me.delong.Slicer.DataObjects.Slice;
import me.delong.Slicer.DataObjects.Slices;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Created by josephdelong on 12/17/16.
 */
public class SlicerThread implements Runnable{
    List<Polygon> polygons;
    SlicerConfig slicerConfig;
    SlicerHandler slicerHandler;

    public SlicerThread(List<Polygon> polygons, SlicerConfig slicerConfig, SlicerHandler slicerHandler){
        this.polygons = polygons;
        this.slicerConfig = slicerConfig;
        this.slicerHandler = slicerHandler;
    }


    public List<Polygon> getPolygons() {
        return polygons;
    }

    public void setPolygons(List<Polygon> polygons) {
        this.polygons = polygons;
    }

    @Override
    public void run() {
        Iterator<Polygon> polygonItr = polygons.iterator();
        while(polygonItr.hasNext()){
            Polygon polygon = polygonItr.next();

            SlicePolygon slicePolygon = new SlicePolygon(polygon);
            slicePolygon.run();
        }
    }

    public class SlicePolygon{
        Vertex vertexA;
        Vertex vertexB;
        Vertex vertexC;

        Polygon polygon;

        BigDecimal layerSize;
        BigDecimal startingLayer;

        Vector ab;
        Vector bc;
        Vector ac;

        //Changes to original
        public SlicePolygon(Polygon polygon){
            this.polygon = polygon;
        }
        public void run() {
            Collections.sort(polygon);
            this.vertexA = polygon.get(0);
            this.vertexB = polygon.get(1);
            this.vertexC = polygon.get(2);

            calcLayerSize(polygon.get(0).getZ(), polygon.get(2).getZ());

            //Calculates slopes of each vector
            this.ab = new Vector(vertexA, vertexB);
            this.bc = new Vector(vertexB, vertexC);
            this.ac = new Vector(vertexA, vertexC);

            slicePolygon();
        }

        private void calcLayerSize(BigDecimal aZ, BigDecimal cZ) {
            //find first resolution line for polygon
            //Starting and ending resolution line in Z of the polygon being worked
            startingLayer = aZ.divide(slicerConfig.getzResolution(), MathContext.DECIMAL128);
            //Fixed rounding trouble regarding polygon layer size
            startingLayer = startingLayer.setScale(0, RoundingMode.DOWN);
            BigDecimal endingLayer = cZ.divide(slicerConfig.getzResolution(), MathContext.DECIMAL128);
            //Fixed rounding trouble regarding polygon layer size
            endingLayer = endingLayer.setScale(0, RoundingMode.UP);

            this.layerSize = endingLayer.subtract(startingLayer);
        }
        private Slices initPolygonSlices(){
            Slices slices = new Slices();
            for(int i=0; layerSize.compareTo(new BigDecimal(i))==1; i++){
                Slice slice = new Slice();
                slice.setzResolution(slicerConfig.getzResolution());
                slice.setXyResolution(slicerConfig.getXyResolution());
                slice.setKerf(slicerConfig.getKerf());
                slices.add(slice);
            }
            return slices;
        }

        public void slicePolygon(){
            //IrregPolygon First Slicing increases slicing speed
            //because it increases the amount of data processed
            //since each polygon will likely have multiple intersections

            //Math is all done using positive integers
            //There should never be a negative result

            //iteration through z resolution lines
            Slices slices = initPolygonSlices();
            for(int i=0; layerSize.compareTo(new BigDecimal(i))==1; i++){
                BigDecimal planeOfIntersection = startingLayer.add(new BigDecimal(i)).multiply(slicerConfig.getzResolution());
                //Line line = new Line();
                List<Point> points = new ArrayList<Point>();

                int count = 0;
                if(ab.doesVectorRestOnPlane(planeOfIntersection) && bc.doesVectorRestOnPlane(planeOfIntersection) && ac.doesVectorRestOnPlane(planeOfIntersection)){
                    //drop case polygon is euchlidean
                }
                else if(ab.doesVectorRestOnPlane(planeOfIntersection) || bc.doesVectorRestOnPlane(planeOfIntersection) || ac.doesVectorRestOnPlane(planeOfIntersection)){
                    if(ac.doesVectorRestOnPlane(planeOfIntersection)){
                        points.addAll(ac.getEuchlidianPoints());
                    }
                    else if(ab.doesVectorRestOnPlane(planeOfIntersection)){
                        points.addAll(ab.getEuchlidianPoints());
                    }
                    else if(bc.doesVectorRestOnPlane(planeOfIntersection)){
                        points.addAll(bc.getEuchlidianPoints());
                    }
                }
                else if(ac.doesPlaneIntersect(planeOfIntersection) || ab.doesPlaneIntersect(planeOfIntersection) || bc.doesPlaneIntersect(planeOfIntersection)){
                    if(vertexA.getZ().compareTo(planeOfIntersection)==0){
                        points.add(vertexA.toEuchlidian());
                    }
                    if(vertexB.getZ().compareTo(planeOfIntersection)==0){
                        points.add(vertexB.toEuchlidian());
                    }
                    if(vertexC.getZ().compareTo(planeOfIntersection)==0){
                        points.add(vertexC.toEuchlidian());
                    }
                    if(ac.doesPlaneIntersect(planeOfIntersection)){
                        points.add(ac.calcIntersectionXY(planeOfIntersection));
                    }
                    if(ab.doesPlaneIntersect(planeOfIntersection)){
                        points.add(ab.calcIntersectionXY(planeOfIntersection));
                    }
                    if(bc.doesPlaneIntersect(planeOfIntersection)){
                        points.add(bc.calcIntersectionXY(planeOfIntersection));
                    }
                }

                slices.get(i).addAll(isLine(points));
            }
            slicerHandler.success(startingLayer, slices);

        }
    }
    public static List<Point> isLine(List<Point> points){
        if(points!=null && points.size()>1 && !points.get(0).equals(points.get(1))){
            return addReference(points);
        }
        return new ArrayList<Point>();
    }
    public static List<Point> addReference(List<Point> points) {
        points.get(0).addRef(points.get(1));
        points.get(1).addRef(points.get(0));
        return points;
    }
}
