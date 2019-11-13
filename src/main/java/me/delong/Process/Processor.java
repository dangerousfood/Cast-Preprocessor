package me.delong.Process;

import me.delong.DataObjects.Point;
import me.delong.Process.IrregularPolygon.Node;
import me.delong.Process.DataObjects.Layer;
import me.delong.Slicer.DataObjects.Slice;
import me.delong.Slicer.DataObjects.Slices;
import me.delong.Slicer.SliceHelper;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by josephdelong on 12/26/16.
 */
public class Processor {
    List<Layer> layers;

    public List<Layer> getLayers() {
        return layers;
    }

    public Processor(Slices slices) {
        layers = convertSlicesToLayers(slices);
    }

    public static List<Layer> convertSlicesToLayers(Slices slices){
        List<Layer> layers = new ArrayList<Layer>();

        for(Slice slice: slices){
            Layer layer = SliceHelper.convertPointsToIrregPolygons(new ArrayList<Point>(slice));
            layers.add(layer);
        }

        return layers;
    }

    public void sortPolygonNesting(){
        Iterator<Layer> layerItr = layers.iterator();

        while(layerItr.hasNext()){
            Layer layer = layerItr.next();

            for(Node polygon: layer){
                for(Node treeNode: layer){

                    if(polygon!=treeNode && polygon.isBoxOverlapping(treeNode)){
                        try {
                            if(treeNode.isNested(polygon)){
                                treeNode.addChild(polygon);
                                polygon.increment();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            for(Node treeNode: layer){
                treeNode.dropChildren();
            }
        }
    }

    public void adjustPolygonKerf(BigDecimal kerf){

    }
}
