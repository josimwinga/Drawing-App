
/**
 * This is the deletion change command.
 * Every time a shape is deleted from the
 * canvas, this command is
 * instantiated to save the previous 
 * and current state of the shape.
 * @author <jsimwing>
 **/
import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;

public class CDelete extends Command{
	
	private SketchyShape _shape;
	private Color _color;
	private double _width, _height, _x, _y, _rotation;
	private ArrayList<SketchyShape> _array;
	private int _index;
	
	public CDelete(SketchyShape shape, double width, double height, double rotation,
			Point location, Color color, ArrayList<SketchyShape> array, int index){
		_shape = shape;
		_width = width;
		_height = height;
		_x = location.x;
		_y = location.y;
		_color = color;
		_array = array;
		_index = index;
		_rotation = rotation;
	}
	@Override
	//recreate the shape
	public void undo(){
		_shape.setSize(_width, _height);
		_shape.setColor(_color);
		_shape.setLocation(_x, _y);
		_shape.setRotation(_rotation);
		_array.add(_index, _shape);
	}
	@Override
	//delete the shape
	public void redo(){
		_array.remove(_shape);
	}
}
