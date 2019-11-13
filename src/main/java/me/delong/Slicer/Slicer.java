package me.delong.Slicer;

import me.delong.DataObjects.Polygon;
import me.delong.Model.Model;
import me.delong.Slicer.DataObjects.Slices;

import java.util.List;

/**
 * Created by josephdelong on 8/20/16.
 */
public class Slicer {
    Slices slices;
    Model model;
    SlicerConfig slicerConfig;
    SlicerHandler slicerHandler;

    public Slicer(Model model, SlicerConfig slicerConfig) throws InterruptedException {
        this.model = model;
        this.slicerConfig = slicerConfig;

        sliceModel();
    }

    //xyResolution is the size of the precision
    //kerf is the extrusion width or the cutting width
    //if kerf is positive process is additive (FFF 3D Printing, SLA, SLS printing)
    //if kerf is negative process is subtractive (CNC, Lasercut)

    public void sliceModel() throws InterruptedException {
        slicerHandler = new SlicerHandler(model, slicerConfig);

        int polygonsPerList = model.size()/slicerConfig.getThreadCount();
        //Splitting polygonlist into individual threads
        for(int i=0; i<=slicerConfig.getThreadCount(); i++){
            List<Polygon> subList;
            int start = i*polygonsPerList;
            int end = 0;

            if(i==slicerConfig.getThreadCount()){
                end = model.size();
            }else{
                end = start+polygonsPerList;
            }
            new Thread(new SlicerThread(model.subList(start, end), slicerConfig, slicerHandler)).start();
        }
        //TODO
        //Log.debug("Slicer has completed all dispatched threads");
        slices = slicerHandler.getSlices();
    }
    public Slices getSlices() throws InterruptedException{
        if(slices!=null){
            //throw slicing exception
        }
        while(!slicerHandler.isCompleted()){
            Thread.sleep(100);
        }
        return slices;
    }
}
