package Sketchy;
/**
 * This is the rotate change command.
 * Every time a shape is rotated on
 * canvas, this command is
 * instantiated to save the previous 
 * and current state of the shape.
 * @author <jsimwing>
 **/
import java.util.ArrayList;

public class CRotate extends Command{
	private double _prev, _curr;
	private SketchyShape _shape;
	private int _index;
	private ArrayList<SketchyShape> _array;
	public CRotate(SketchyShape shape, ArrayList<SketchyShape> array, double prevRotation, double currRotation){
		_prev = prevRotation;
		_curr = currRotation;
		_array = array;
		_index = array.indexOf(shape);
	}
	@Override
	public void undo(){
		_shape = _array.get(_index);
		_shape.setRotation(_prev);
	}
	@Override
	public void redo(){
		_shape = _array.get(_index);
		_shape.setRotation(_curr);
	}
}
