package Sketchy;
/**
 * This is the sketchyCircle. It is 
 * an extension of the SketchyShape and
 * takes in an Ellipse2D.Double.
 * @author <jsimwing>
 **/
import java.awt.geom.Ellipse2D.Double;

public class SketchyCircle extends SketchyShape{
	public SketchyCircle(Double shape){
		super(shape);
	}
	@Override
	public String getType(){
		return new String("circle");
	}


}
