import java.awt.*;
import java.awt.geom.Arc2D;

public class Pan extends Arc2D.Double {

    private Color color;
    private GradientPaint gradient;

    double totalArea = 0;


    public Pan(Color c, double x, double y, double w, double h, double start, double extent, int type){
        super(x, y, w, h, start, extent, type);
        this.color = c;
    }

    public Pan(GradientPaint gradient, double x, double y, double w, double h, double start, double extent, int type){
        super(x, y, w, h, start, extent, type);
        this.gradient = gradient;
    }

    public void changeY(int changeBy) { y += changeBy;
    }

    public Color getColor(){
        return color;
    }

    public GradientPaint getGradient(){
        return gradient;
    }



}
