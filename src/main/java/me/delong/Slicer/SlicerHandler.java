package me.delong.Slicer;

import me.delong.Model.Model;
import me.delong.Slicer.DataObjects.Slice;
import me.delong.Slicer.DataObjects.Slices;

import java.math.BigDecimal;

/**
 * Created by josephdelong on 12/17/16.
 */
//Thread Safe
public class SlicerHandler {
    Slices slices = new Slices();
    int polygonsReturned = 0;
    int polygonsSize = 0;

    public Slices getSlices() {
        return slices;
    }

    public SlicerHandler(Model model, SlicerConfig slicerConfig){
        init(model, slicerConfig);
        this.polygonsSize = model.size();
    }
    private synchronized  void init(Model model, SlicerConfig slicerConfig){
        int sliceCount = 0;
        sliceCount = calcSliceCount(model, slicerConfig.getzResolution());
        //create layers
        for(int i=0; i<sliceCount; i++){
            Slice slice = new Slice();
            slice.setKerf(slicerConfig.getKerf());
            slices.add(slice);
        }
    }
    private int calcSliceCount(Model model, BigDecimal zResolution){
        return model.getzMax().subtract(model.getzMin()).divide(zResolution, 0, BigDecimal.ROUND_CEILING).intValue();
    }

    //success method should be synchronized as it will be updating the object List<Slice> layers
    public synchronized void success(BigDecimal startingLayer, Slices polygonSlices){
        for(int i=0; i<polygonSlices.size(); i++){
            Slice slice = polygonSlices.get(i);
            slices.get(startingLayer.intValue()+i).addAll(slice);
        }
        polygonsReturned++;
    }

    public synchronized void fail(String reason){
        polygonsReturned++;
        //TODO
        //Log.debug(reason);
    }

    public boolean isCompleted(){
        return polygonsReturned == polygonsSize;
    }
}
