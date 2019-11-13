package me.delong;

import me.delong.Exceptions.SaveOperationException;
import me.delong.FileOperations.SVGHelper;
import me.delong.FileOperations.TemporarySaveHandler;
import me.delong.Model.Model;
import me.delong.Model.ModelAttributes;
import me.delong.Process.DataObjects.Layer;
import me.delong.Process.Processor;
import me.delong.Slicer.DataObjects.Slices;
import me.delong.Slicer.Slicer;
import me.delong.Slicer.SlicerConfig;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws SaveOperationException, InterruptedException, UnsupportedEncodingException, NoSuchAlgorithmException {

        SlicerConfig slicerConfig = new SlicerConfig();
        slicerConfig.setKerf(new BigDecimal("0.3"));
        slicerConfig.setXyResolution(new BigDecimal("0.03"));
        slicerConfig.setzResolution(new BigDecimal("0.3"));
        slicerConfig.setPrecision(MathContext.DECIMAL128);
        slicerConfig.setThreadCount(7);

        Timer modelTimer = new Timer();
        URL url = ClassLoader.getSystemResource("3DBenchy.stl");
        Model model = new Model(url.getPath());
        ModelAttributes modelAtt = model.getModelAttributes();
        System.err.println("Model open time: "+modelTimer.stop());

        Timer sliceTimer = new Timer();
        Slicer slicer = null;
        try {
            slicer = new Slicer(model, slicerConfig);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Slices slices = slicer.getSlices();
        System.err.println("Slicing time: "+sliceTimer.stop());

        Timer processTimer = new Timer();
        Processor processor = new Processor(slices);
        processor.sortPolygonNesting();
        processor.adjustPolygonKerf(slicerConfig.getKerf());


        System.err.println("Process time: "+processTimer.stop());

        Timer bezTimer = new Timer();
        System.err.println("Bez time: "+bezTimer.stop());


        List<Layer> layers = processor.getLayers();
        SVGHelper.save(layers, modelAtt, "sliced-model/", model.getMD5(slicerConfig), new TemporarySaveHandler());
    }
}
