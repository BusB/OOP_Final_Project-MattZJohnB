import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

public class GameShape extends Area {
    private static final double ELLIPSE_AREA = 0.78540;
    private static final double TRIANGLE_AREA = 0.5;
    private static final double RECTANGLE_AREA = 1.0;
    private static final double STAR_AREA = 0.32632;
    private static final int STARTING_ROW = 0;

    private Color gsColor;
    private double gsArea;
    private double xScale;
    private double yScale;
    private String shapeName;
    private int xPosition;
    private int yPosition;
    private boolean isSelected;
    private int location;


    /**creates random shape of the specified type*/
    public GameShape(Color color, String shapeName){
        super();
        this.gsColor = color;
        this.xScale = 0.2 + 0.8*Math.random();
        this.yScale = 0.2 + 0.8*Math.random();
        isSelected = false;
        yPosition = 500;
        xPosition = 225;
        location = STARTING_ROW;
        Area a;
        AffineTransform atScale = new AffineTransform();
        AffineTransform atPosition = new AffineTransform();

        if(shapeName.equalsIgnoreCase("ellipse")){
            this.gsArea = ELLIPSE_AREA * xScale * yScale;
            this.shapeName = "Ellipse";
            Ellipse2D ellipse = new Ellipse2D.Double(0,0,50,50);
            a = new Area(ellipse);
        } else if(shapeName.equalsIgnoreCase("triangle")){
            this.gsArea = TRIANGLE_AREA * xScale * yScale;
            this.shapeName = "Triangle";
            this.xPosition+=100;
            int[] xListTriangle = {0,25,50};
            int[] yListTriangle = {50,0,50};
            Polygon triangle = new Polygon(xListTriangle,yListTriangle,3);
            a = new Area(triangle);
        } else if(shapeName.equalsIgnoreCase("star")){
            this.gsArea = STAR_AREA * xScale * yScale;
            this.shapeName = "Star";
            this.xPosition+=200;
            int[] xList = {0,18,25,32,50,35,40,25,10,15};
            int[] yList = {19,19,0,19,19,32,50,40,50,32};
            Polygon star = new Polygon(xList, yList, 10);
            a = new Area(star);


        } else {
            this.gsArea = RECTANGLE_AREA * xScale * yScale;
            this.shapeName = "Rectangle";
            this.xPosition+=300;
            Rectangle2D rectangle = new Rectangle2D.Double(0,0,50,50);
            a = new Area(rectangle);
        }
        yPosition += (50.*(1.-yScale));
        this.add(a);
        atScale.setToScale(xScale,yScale);
        atPosition.setToTranslation(xPosition,yPosition);
        this.transform(atScale);
        this.transform(atPosition);
    }


    /**creates a rectangle that makes sure shapes can balance*/
    public GameShape(Color color, GameShape ellipse, GameShape triangle, GameShape star){
        super();
        this.gsColor = color;
        int randomShape = (int) (Math.random()*3);
        double scale = 0.2 + 0.8*Math.random();
        AffineTransform atScale = new AffineTransform();
        AffineTransform atPosition = new AffineTransform();

        //try to balance the ellipse
        if(randomShape==0){
            //if the ellipse is small, put the rectangle on the ellipse side
            if(ellipse.getGsArea()<triangle.getGsArea()+star.getGsArea()-4){
                double area = (triangle.getGsArea()+star.getGsArea()-ellipse.getGsArea())/100.;
                while (area/scale < 0.2 || area/scale > 1.0){
                    scale = 0.2 + 0.8*Math.random();
                }
                xScale = scale;
                yScale = area/scale;
            //if the ellipse is big, put the rectangle on the other side
            } else if(ellipse.getGsArea()>triangle.getGsArea()+star.getGsArea()+4){
                double area = (ellipse.getGsArea()-triangle.getGsArea()-star.getGsArea())/100.;
                while (area/scale < 0.2 || area/scale > 1.0){
                    scale = 0.2 + 0.8*Math.random();
                }
                xScale = scale;
                yScale = area/scale;
            //if it's too well balanced, try the next shape
            }else{
                randomShape = 1;
            }
        }
        //try to balance the triangle
        if(randomShape==1){
            if(triangle.getGsArea()<ellipse.getGsArea()+star.getGsArea()-4.){
                double area = (ellipse.getGsArea()+star.getGsArea()-triangle.getGsArea())/100.;
                while (area/scale < 0.2 || area/scale > 1.0){
                    scale = 0.2 + 0.8*Math.random();
                }
                xScale = scale;
                yScale = area/scale;
            } else if(triangle.getGsArea()>ellipse.getGsArea()+star.getGsArea()+4.){
                double area = (triangle.getGsArea()-ellipse.getGsArea()-star.getGsArea())/100.;
                while (area/scale < 0.2 || area/scale > 1.0){
                    scale = 0.2 + 0.8*Math.random();
                }
                xScale = scale;
                yScale = area/scale;
            } else {
                randomShape = 2;
            }
        }
        //try to balance the star
        if(randomShape==2){
            if(star.getGsArea()<ellipse.getGsArea()+triangle.getGsArea()-4.){
                double area = (ellipse.getGsArea()+triangle.getGsArea()-star.getGsArea())/100.;
                while (area/scale < 0.2 || area/scale > 1.0){
                    scale = 0.2 + 0.8*Math.random();
                }
                xScale = scale;
                yScale = area/scale;
            } else if(star.getGsArea()>ellipse.getGsArea()+triangle.getGsArea()+4.){
                double area = (star.getGsArea()-ellipse.getGsArea()-triangle.getGsArea())/100.;
                while (area/scale < 0.2 || area/scale > 1.0){
                    scale = 0.2 + 0.8*Math.random();
                }
                xScale = scale;
                yScale = area/scale;
            } else {
                randomShape = 3;
            }
        }
        //nothing else worked, so try to balance the rectangle.
        if(randomShape==3){
            //if the other shapes are small, generate a rectangle to balance them all
            if (star.getGsArea()+triangle.getGsArea()+ellipse.getGsArea()<100.){
                double area = (star.getGsArea()+triangle.getGsArea()+ellipse.getGsArea())/100.;
                while (area/scale < 0.2 || area/scale > 1.0){
                    scale = 0.2 + 0.8*Math.random();
                }
                xScale = scale;
                yScale = area/scale;
            //if the other shapes are too big, shrink them and make a big square to balance them.
            } else {
                double area = (star.getGsArea()+triangle.getGsArea()+ellipse.getGsArea())/100.;
                xScale = 1.0;
                yScale = 1.0;
                star.rescale(area);
                triangle.rescale(area);
                ellipse.rescale(area);
            }
        }

        this.shapeName = "Rectangle";
        this.gsArea = RECTANGLE_AREA* xScale * yScale;
        this.isSelected = false;
        this.yPosition = 500;
        this.xPosition = 525;
        this.location = STARTING_ROW;
        Rectangle2D rectangle = new Rectangle2D.Double(0,0,50,50);
        Area a = new Area(rectangle);
        this.add(a);
        yPosition += (50.*(1.-yScale));
        atScale.setToScale(xScale,yScale);
        atPosition.setToTranslation(xPosition,yPosition);
        this.transform(atScale);
        this.transform(atPosition);

    }

    public double getGsArea(){
        return Math.round(this.gsArea*100);
    }

    public double getxScale() {
        return xScale;
    }

    public boolean getIsSelected(){
        return isSelected;
    }

    public int getLocation() {
        return location;
    }

    public int getxPosition() {
        return xPosition;
    }

    public int getyPosition() {
        return yPosition;
    }

    public double getyScale() {
        return yScale;
    }
    public String getShapeName() {
        return shapeName;
    }

    public Color getGsColor(){
        return gsColor;
    }

    public void setGsColor(Color color){
        this.gsColor = color;
    }

    public void setxScale(double xScale) {
        this.gsArea=this.gsArea*xScale/this.xScale;
        this.xScale = xScale;
    }

    public void setyScale(double yScale){
        this.gsArea=this.gsArea*yScale/this.yScale;
        this.yScale = yScale;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    public void setPosition(double xPosition, double yPosition) {
        double yMovement = yPosition-this.yPosition;
        double xMovement = xPosition-this.xPosition;
        AffineTransform at = new AffineTransform();
        at.setToTranslation(xMovement,yMovement);
        this.xPosition = (int) xPosition+1;
        this.yPosition = (int) yPosition+1;
        this.transform(at);
    }

    public void movePosition(double deltaX, double deltaY){
        AffineTransform at = new AffineTransform();
        at.setToTranslation(deltaX,deltaY);
        this.xPosition+=deltaX;
        this.yPosition+=deltaY;
        this.transform(at);
    }

    public void hybridMove(double deltaX, double yPosition){
        double deltaY = Math.round(yPosition)-this.yPosition;
        AffineTransform at = new AffineTransform();
        at.setToTranslation(deltaX,deltaY);
        this.yPosition = (int) Math.round(yPosition);
        this.xPosition+=deltaX;
        this.transform(at);
    }

    public void selectShape(){
        this.isSelected = !isSelected;
    }

    public void rescale(double area){
        this.xScale = xScale/Math.sqrt(area);
        this.yScale = yScale/Math.sqrt(area);
        AffineTransform at1 = new AffineTransform();
        at1.setToScale(1/Math.sqrt(area), 1/Math.sqrt(area));
        this.transform(at1);
        AffineTransform at2 = new AffineTransform();
        at2.setToTranslation(0,50.*(1.-yScale));
        this.transform(at2);
        this.gsArea = this.gsArea/area;
    }

    public String toString(){
        return shapeName + "; x " + xScale + "; y " + yScale + "; Area " + this.getGsArea();
    }


}

