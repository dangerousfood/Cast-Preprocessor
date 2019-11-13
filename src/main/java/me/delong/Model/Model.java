package me.delong.Model;

import me.delong.DataObjects.Vertex;
import me.delong.FileOperations.ModelHelper;
import me.delong.DataObjects.Polygon;
import me.delong.Slicer.SlicerConfig;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by josephdelong on 11/23/14.
 */
public class Model extends ArrayList<Polygon>{

    String fileName;
    String MD5;
    String path;

    BigDecimal xMin;
    BigDecimal xMax;

    BigDecimal yMin;
    BigDecimal yMax;

    BigDecimal zMin;
    BigDecimal zMax;

    public Model (String path){
        this.path = path;
        ModelHelper.readBinarySTL(path, this);
        //uniformScale(new BigDecimal(1));
        calcMaxMin();
        baselineModel();
        calcMaxMin();
    }


    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }


    public BigDecimal getxMin() {
        return xMin;
    }

    public void setxMin(BigDecimal xMin) {
        this.xMin = xMin;
    }

    public BigDecimal getxMax() {
        return xMax;
    }

    public void setxMax(BigDecimal xMax) {
        this.xMax = xMax;
    }

    public BigDecimal getyMin() {
        return yMin;
    }

    public void setyMin(BigDecimal yMin) {
        this.yMin = yMin;
    }

    public BigDecimal getyMax() {
        return yMax;
    }

    public void setyMax(BigDecimal yMax) {
        this.yMax = yMax;
    }

    public BigDecimal getzMin() {
        return zMin;
    }

    public void setzMin(BigDecimal zMin) {
        this.zMin = zMin;
    }

    public BigDecimal getzMax() {
        return zMax;
    }

    public void setzMax(BigDecimal zMax) {
        this.zMax = zMax;
    }


    @Override
    public String toString() {
        Iterator<Polygon> itr = iterator();
        StringBuilder modelPolygons = new StringBuilder();
        while(itr.hasNext()){
            Polygon polygon = itr.next();
            modelPolygons.append(polygon.toString());
        }
        return modelPolygons.toString();
    }
    private void uniformScale(BigDecimal scaleFactor){
        scale(scaleFactor, scaleFactor, scaleFactor);
    }
    private void scale(BigDecimal scaleX, BigDecimal scaleY, BigDecimal scaleZ){

        Iterator<Polygon> polygonItr = iterator();
        while(polygonItr.hasNext()){
            Polygon polygon = polygonItr.next();
            //List<Vertex> vertices = polygon.getVertices();

            Iterator<Vertex> pointItr = polygon.iterator();

            while(pointItr.hasNext()){
                scalePolygon(pointItr.next(), scaleX, scaleY, scaleZ);
            }
            //if mins and maxes == null throw exception
        }
    }
    private void scalePolygon(Vertex vertex, BigDecimal scaleX, BigDecimal scaleY, BigDecimal scaleZ){
        vertex.setX(vertex.getX().multiply(scaleX));
        vertex.setY(vertex.getY().multiply(scaleY));
        vertex.setZ(vertex.getZ().multiply(scaleZ));
    }
    private void calcMaxMin(){

        Iterator<Polygon> polygonItr = iterator();
        while(polygonItr.hasNext()){
            Polygon polygon = polygonItr.next();
            ///List<Vertex> vertices = polygon.getVertices();

            Iterator<Vertex> pointItr = polygon.iterator();

            while(pointItr.hasNext()){
                Vertex vertex = pointItr.next();
                if(xMin == null || xMax == null || yMin == null || yMax == null || zMin == null || zMax == null){
                    xMin = vertex.getX();
                    xMax = vertex.getX();
                    yMin = vertex.getY();
                    yMax = vertex.getY();
                    zMin = vertex.getZ();
                    zMax = vertex.getZ();
                }
                else{
                    if(vertex.getX().compareTo(xMin)==-1){
                        xMin = vertex.getX();
                    }
                    else if(vertex.getX().compareTo(xMax)==1){
                        xMax = vertex.getX();
                    }

                    if(vertex.getY().compareTo(yMin)==-1){
                        yMin = vertex.getY();
                    }
                    else if(vertex.getY().compareTo(yMax)==1){
                        yMax = vertex.getY();
                    }

                    if(vertex.getZ().compareTo(zMin)==-1){
                        zMin = vertex.getZ();
                    }
                    else if(vertex.getZ().compareTo(zMax)==1){
                        zMax = vertex.getZ();
                    }
                }
            }
            //if mins and maxes == null throw exception
        }
    }
    public String getMD5(SlicerConfig SlicerConfig) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        byte[] modelBytes = toString().getBytes("UTF-8");

        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] digest = md.digest(modelBytes);

        MD5 =  toHexString(digest);
        return MD5;
    }
    public static String toHexString(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();

        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }

        return hexString.toString();
    }
    //takes a model and zeroes the Model base coordinates to (0,0,0)
    private void baselineModel(){
        Iterator<Polygon> polygonItr = iterator();
        while(polygonItr.hasNext()){
            Polygon polygon = polygonItr.next();
            //List<Vertex> vertices = polygon.getVertices();

            Iterator<Vertex> pointItr = polygon.iterator();

            if(xMin == null || xMax == null || yMin == null || yMax == null || zMin == null || zMax == null){
                //throw exception
            }
            while(pointItr.hasNext()){
                Vertex vertex = pointItr.next();
                vertex.setX(vertex.getX().subtract(xMin, MathContext.DECIMAL128));
                vertex.setY(vertex.getY().subtract(yMin, MathContext.DECIMAL128));
                vertex.setZ(vertex.getZ().subtract(zMin, MathContext.DECIMAL128));
            }
        }
    }

    public ModelAttributes getModelAttributes(){
        ModelAttributes modelAtt = new ModelAttributes();
        modelAtt.setxMax(xMax);
        modelAtt.setyMax(yMax);
        modelAtt.setzMax(zMax);

        modelAtt.setxMin(xMin);
        modelAtt.setyMin(yMin);
        modelAtt.setzMin(zMin);

        return modelAtt;
    }
}
