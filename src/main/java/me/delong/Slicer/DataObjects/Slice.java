package me.delong.Slicer.DataObjects;

import me.delong.DataObjects.Point;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by josephdelong on 12/18/16.
 */
public class Slice extends ArrayList<Point> {
    BigDecimal zResolution;
    BigDecimal xyResolution;
    BigDecimal kerf;

    public BigDecimal getzResolution() {
        return zResolution;
    }

    public void setzResolution(BigDecimal zResolution) {
        this.zResolution = zResolution;
    }

    public BigDecimal getXyResolution() {
        return xyResolution;
    }

    public void setXyResolution(BigDecimal xyResolution) {
        this.xyResolution = xyResolution;
    }

    public BigDecimal getKerf() {
        return kerf;
    }

    public void setKerf(BigDecimal kerf) {
        this.kerf = kerf;
    }

    //Format lines of slice into an SVG file
    /*public String toSVG(){
        //<svg xmlns="http://www.w3.org/2000/svg" version="1.1">
        //  <line x1="50" y1="50" x2="200" y2="200" stroke="blue" stroke-width="4"/>
        //</svg>
        StringBuilder stringBuilder = null;

        Iterator<Line> itr = this.iterator();

        stringBuilder = new StringBuilder();
        stringBuilder.append("<svg xmlns=\"http://www.w3.org/2000/svg\" version=\"1.1\">");
        while (itr.hasNext()) {
            Line line = itr.next();
            stringBuilder.append("<line x1=\"" + line.getStart().getX() + "\"" + " y1=\"" + line.getStart().getY() + "\"" + " x2=\"" + line.getEnd().getX() + "\" y2=\"" + line.getEnd().getY() + "\" stroke=\"blue\" stroke-width=\"" + kerf + "\"/>");

        }
        stringBuilder.append("</svg>");

        return stringBuilder.toString();
    }*/
}
