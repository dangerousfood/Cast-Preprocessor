package me.delong.Process.DataObjects;

import me.delong.Constants;
import me.delong.PathConstants;
import me.delong.Process.IrregularPolygon.IrregPolygonHelper;
import me.delong.Process.IrregularPolygon.Node;
import me.delong.SVGHelper;
import me.delong.Model.ModelAttributes;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by josephdelong on 12/26/16.
 */
public class Layer extends ArrayList<Node> {
    public String toSVG(ModelAttributes modelAtt) {
        //example data
        /*
        <svg width="120" height="120" viewPort="0 0 120 120" xmlns="http://www.w3.org/2000/svg">
            <polygon points="60,20 100,40 100,80 60,100 20,80 20,40"/>
        </svg>
         */
        StringBuilder stringBuilder = new StringBuilder();


        stringBuilder.append(SVGHelper.SVG_HALF_OPEN);
        stringBuilder.append(Constants.SPACE);

        stringBuilder.append(SVGHelper.widthMM(modelAtt.getxMax()));

        stringBuilder.append(Constants.SPACE);

        stringBuilder.append(SVGHelper.heightMM(modelAtt.getyMax()));

        stringBuilder.append(Constants.SPACE);

        stringBuilder.append(SVGHelper.strokeWidthMM(new BigDecimal("0.3")));

        stringBuilder.append(Constants.SPACE);

        stringBuilder.append(SVGHelper.fill("#dbc66a"));

        stringBuilder.append(Constants.SPACE);

        stringBuilder.append(SVGHelper.stroke("#89a1ef"));

        stringBuilder.append(Constants.SPACE);

        stringBuilder.append(SVGHelper.FILLRULE_NONZERO);

        stringBuilder.append(Constants.SPACE);

        stringBuilder.append(SVGHelper.SVG_HALF_CLOSE);

        stringBuilder.append(Constants.NEWLINE);


        stringBuilder.append(SVGHelper.OPEN_PATH);
        for(Node node: this){
            if(node.getOrder()%2!=0) IrregPolygonHelper.toCCW(node);
            else IrregPolygonHelper.toClockwise(node);

            stringBuilder.append(node.toCompoundPath());
        }
        stringBuilder.append(SVGHelper.CLOSE_PATH);
        stringBuilder.append(Constants.NEWLINE);
        stringBuilder.append(PathConstants.END_SVG);

        return stringBuilder.toString();
    }
}
