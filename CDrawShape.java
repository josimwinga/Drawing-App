package Sketchy;
/**
 * This is the shape drawn command.
 * Every time the a new shape is drawn
 * on the canvas,this command is
 * instantiated to save the previous 
 * and current state of the shape.
 * @author <jsimwing>
 **/
import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;

public class CDrawShape extends Command{
	private SketchyShape _shape;
	private Color _color;
	private double _width, _height, _x, _y, _rotation;
	private ArrayList<SketchyShape> _array;
	private int _index;
	
	public CDrawShape(SketchyShape shape, double width, double height, 
			Point location, ArrayList<SketchyShape> array, int index){
		_shape = shape;
		_width = width;
		_height = height;
		_x = location.x;
		_y = location.y;
		_array = array;
		_index = _array.indexOf(shape);
	}
	@Override
	//delete the shape
	public void undo(){
		_color = _shape.getFillColor();
		_rotation = _shape.getRotation();
		_array.remove(_index);	
	}
	//add the shape
	public void redo(){
		_shape.setSize(_width, _height);
		_shape.setFillColor(_color);
		_shape.setLocation(_x, _y);
		_shape.setRotation(_rotation);
		_array.add(_shape);
	}
}
