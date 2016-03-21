package Sketchy;
/**
 * This is the sketchyRect. It is 
 * the rectangular extension of the SketchyShape and
 * takes in a Rectangle2D.Double.
 * @author <jsimwing>
 **/
import java.awt.geom.Rectangle2D.Double;

public class SketchyRect extends SketchyShape{
	public SketchyRect(Double shape){
		super(shape);
	}
	@Override
	public String getType(){
		return new String("rect");
	}
}
