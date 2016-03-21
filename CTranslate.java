package Sketchy;
/**
 * This is the Translate change command.
 * Every time a shape is translated on
 * canvas, this command is
 * instantiated to save the previous 
 * and current state of the shape.
 * @author <jsimwing>
 **/
import java.awt.Point;
import java.util.ArrayList;

public class CTranslate extends Command{
	private Point _prev, _curr;
	private SketchyShape _shape;
	private ArrayList<SketchyShape> _array;
	private int _index;
	public CTranslate(SketchyShape shape, ArrayList<SketchyShape> array, Point prev, Point curr){
		_shape = shape;
		_array = array;
		_prev = prev;
		_curr = curr;
		_index = array.indexOf(shape);
	}
	@Override
	public void undo(){
		_shape = _array.get(_index);
		_shape.setLocation(_prev);
	}
	@Override
	public void redo(){
		_shape = _array.get(_index);
		_shape.setLocation(_curr);
	}
}
