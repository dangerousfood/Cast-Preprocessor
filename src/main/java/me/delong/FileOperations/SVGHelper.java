package me.delong.FileOperations;

import me.delong.Exceptions.OpenOperationException;
import me.delong.Exceptions.SaveOperationException;
import me.delong.Model.ModelAttributes;
import me.delong.Process.DataObjects.Layer;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 * Created by josephdelong on 12/18/16.
 */
public class SVGHelper extends FileUtil {
    SaveHandler saveHandler;

    public static String PATH_DELIMITER = "/";
    public static String DASH = "-";
    public static String SVG_FILE_EXT = ".svg";

    public static Object open(String path) throws OpenOperationException {
        return null;
    }

    /*public static void save(Slices slices, String path, String md5, SaveHandler saveHandler) throws SaveOperationException {
        if(slices == null){
            String reason = SVGHelper.class.getName()+".save() method was passed Object o as a null";
            saveHandler.onFail(1,reason);
            throw new SaveOperationException(reason);
        }
        if(path == null){
            String reason = SVGHelper.class.getClass().getName()+".save() method was passed String path as a null";
            saveHandler.onFail(2,reason);
            throw new SaveOperationException(reason);
        }
        if(md5 == null){
            String reason = SVGHelper.class.getClass().getName()+".save() method was passed String md5 as a null";
            saveHandler.onFail(3,reason);
            throw new SaveOperationException(reason);
        }
        if(saveHandler == null){
            String reason = SVGHelper.class.getClass().getName()+".save() method was passed "+saveHandler.getClass().getName()+" as a null";
            saveHandler.onFail(3,reason);
            throw new SaveOperationException(reason);
        }

        //Making path for SVGs

        if(new File(path+md5).exists() || new File(path+md5).mkdir()) {

            int sliceIndex = 0;
            try {
                Iterator<Slice> itrSlice = slices.iterator();

                while (itrSlice.hasNext()) {
                    Slice slice = itrSlice.next();

                    String pathToSlice = path + md5 + PATH_DELIMITER + md5 + DASH + sliceIndex + SVG_FILE_EXT;
                    writeFile(pathToSlice, slice.toSVG());
                    sliceIndex++;
                }

            } catch (IOException e) {
                String reason = SVGHelper.class.getClass().getName() + ".save() generated an IOException @ sliceIndex:" + sliceIndex;
                saveHandler.onFail(5, reason);
            }
        }
        else{
            String reason = SVGHelper.class.getClass().getName() + " unable to create directory @:" + path+md5;
            saveHandler.onFail(6, reason);
            throw new SaveOperationException(reason);
        }
    }*/
    public static void save(List<Layer> layers, ModelAttributes modelAtt, String path, String md5, SaveHandler saveHandler) throws SaveOperationException {
        if(layers == null){
            String reason = SVGHelper.class.getName()+".save() method was passed Object o as a null";
            saveHandler.onFail(1,reason);
            throw new SaveOperationException(reason);
        }
        if(path == null){
            String reason = SVGHelper.class.getClass().getName()+".save() method was passed String path as a null";
            saveHandler.onFail(2,reason);
            throw new SaveOperationException(reason);
        }
        if(md5 == null){
            String reason = SVGHelper.class.getClass().getName()+".save() method was passed String md5 as a null";
            saveHandler.onFail(3,reason);
            throw new SaveOperationException(reason);
        }
        if(saveHandler == null){
            String reason = SVGHelper.class.getClass().getName()+".save() method was passed "+saveHandler.getClass().getName()+" as a null";
            saveHandler.onFail(3,reason);
            throw new SaveOperationException(reason);
        }

        //Making path for SVGs

        if(new File(path+md5).exists() || new File(path+md5).mkdir()) {

            int sliceIndex = 0;
            try {
                Iterator<Layer> itrLayer = layers.iterator();

                while (itrLayer.hasNext()) {
                    Layer layer = itrLayer.next();

                        String pathToSlice = path + md5 + PATH_DELIMITER + md5 + DASH + sliceIndex + SVG_FILE_EXT;
                        String layerString = layer.toSVG(modelAtt);
                        writeFile(pathToSlice, layerString);
                        sliceIndex++;

                }

            } catch (IOException e) {
                String reason = SVGHelper.class.getClass().getName() + ".save() generated an IOException @ sliceIndex:" + sliceIndex;
                saveHandler.onFail(5, reason);
            }
        }
        else{
            String reason = SVGHelper.class.getClass().getName() + " unable to create directory @:" + path+md5;
            saveHandler.onFail(6, reason);
            throw new SaveOperationException(reason);
        }
    }
}
