
package unity;

import javafx.geometry.BoundingBox;
import javafx.geometry.Point2D;

public class VisualNav {
    
    public String visualID;
    public Point2D position;
    public Point2D velocity;
    public double course;
    public BoundingBox box;
    
    public VisualNav(String visualID, double x, double y, Point2D velocity, BoundingBox box){
        this.visualID = visualID;
        this.position = new Point2D(x, y);
        this.velocity = velocity;
        
        this.box = box;
    }
    
    public double getX(){
        return this.position.getX();
    }
    public double getY(){
        return this.position.getY();
    }
    
    public double getCourse(){
        double theta = Math.atan2(this.velocity.getY(), this.velocity.getX());
        double angle = Math.toDegrees(theta);
        if (angle < 0) {
            angle += 360;
        }
        return angle;  //返回航向
    }
    
    public boolean isColliding(VisualNav other){
        return box.intersects(other.box);
    }
}





