package Sketchy;
/**
 * This is the rotate change command.
 * Every time a shape is raised or
 * lowered on the canvas, this command is
 * instantiated to save the previous 
 * and current index of the shape.
 * @author <jsimwing>
 **/
import java.util.ArrayList;

public class CSwap extends Command{
	private SketchyShape _shape, _swapShape;
	private ArrayList<SketchyShape> _array;
	private int _currIndex, _newIndex;
	public CSwap (SketchyShape shape, SketchyShape swapShape,
		ArrayList<SketchyShape> array, int currIndex, int newIndex){
		_shape = shape;
		_swapShape = swapShape;
		_array = array;
		_currIndex = currIndex;
		_newIndex = newIndex;	
	}
	@Override
	public void undo(){
		_array.set(_currIndex, _shape);
		_array.set(_newIndex, _swapShape);
	}
	@Override
	public void redo(){
		_array.set(_newIndex, _shape);
		_array.set(_currIndex, _swapShape);	
	}
}
