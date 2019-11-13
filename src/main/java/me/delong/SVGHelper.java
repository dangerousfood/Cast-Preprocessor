package me.delong;

import java.math.BigDecimal;

/**
 * Created by josephdelong on 1/20/17.
 */
public class SVGHelper {
    public static final String MOVETO = "M";
    public static final String LINETO = "L";
    public static final String CLOSEPOLYGON = "Z";
    public static final String OPEN_PATH = "<path d=\"";
    public static final String CLOSE_PATH = "\"/>";
    public static final String MM = "mm";
    public static final String PX = "px";
    private static final String IN = "in";
    public static final String SVG_HALF_OPEN = "<svg";
    public static final String SVG_HALF_CLOSE = "xmlns=\"http://www.w3.org/2000/svg\">";


    public static final String WIDTH = "width=\"";
    public static final String HEIGHT = "height=\"";
    public static final String STROKE_WIDTH = "stroke-width=\"";
    public static final String STROKE = "stroke=\"";
    public static final String FILL = "fill=\"";

    public static final String FILLRULE_NONZERO = "fill-rule=\"nonzero\"";
    public static final String FILLRULE_EVENODD = "fill-rule=\"evenodd\"";

    public static final String CLOSE_QUOTE = "\"";

    public enum Units {
        mm, px, in;



        @Override
        public String toString() {
            switch(this) {
                case mm: return MM;
                case px: return PX;
                case in: return IN;
                default: throw new IllegalArgumentException();
            }
        }
    }

    public static String heightMM(BigDecimal height){
        return height(height, Units.mm);
    }
    public static String height(BigDecimal height, Units unit){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(SVGHelper.HEIGHT);
        stringBuilder.append(height);
        stringBuilder.append(unit.toString());
        stringBuilder.append(SVGHelper.CLOSE_QUOTE);

        return stringBuilder.toString();
    }

    public static String widthMM(BigDecimal width){
        return width(width, Units.mm);
    }
    public static String width(BigDecimal width, Units unit){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(SVGHelper.WIDTH);
        stringBuilder.append(width);
        stringBuilder.append(unit.toString());
        stringBuilder.append(SVGHelper.CLOSE_QUOTE);

        return stringBuilder.toString();
    }

    public static String strokeWidthMM(BigDecimal strokeWidth){
        return strokeWidth(strokeWidth, Units.mm);
    }
    public static String strokeWidth(BigDecimal strokeWidth, Units unit){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(SVGHelper.STROKE_WIDTH);
        stringBuilder.append(strokeWidth);
        stringBuilder.append(unit.toString());
        stringBuilder.append(SVGHelper.CLOSE_QUOTE);

        return stringBuilder.toString();
    }

    public static String fill(String color){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(SVGHelper.FILL);
        stringBuilder.append(color);
        stringBuilder.append(SVGHelper.CLOSE_QUOTE);

        return stringBuilder.toString();
    }

    public static String stroke(String color){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(SVGHelper.STROKE);
        stringBuilder.append(color);
        stringBuilder.append(SVGHelper.CLOSE_QUOTE);

        return stringBuilder.toString();
    }
}
