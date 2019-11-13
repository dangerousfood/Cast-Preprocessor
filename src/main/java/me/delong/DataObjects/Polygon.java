package me.delong.DataObjects;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by josephdelong on 8/16/16.
 */
public class Polygon extends ArrayList<Vertex>{
    //List<Vertex> vertices = new ArrayList<Vertex>();
    Vertex normal;

    /*public List<Vertex> getVertices() {
        return vertices;
    }*/

    /*public void setVertices(List<Vertex> vertices) {
        this.vertices = vertices;
    }*/

    public Vertex getNormal() {
        return normal;
    }

    public void setNormal(Vertex normal) {
        this.normal = normal;
    }

    /*public void addPoint(Vertex vertex){
        vertices.add(vertex);
    }*/

    @Override
    public String toString() {
        Iterator<Vertex> itr = iterator();
        StringBuilder polygonVetices = new StringBuilder();
        while(itr.hasNext()){
            Vertex vertex = itr.next();
            polygonVetices.append(vertex.toString());
        }
        return polygonVetices.toString();
    }
}
