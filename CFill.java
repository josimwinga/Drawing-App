package Sketchy;
/**
 * This is the fill change command.
 * Every time a shape's is
 * changed, this command is
 * instantiated to save the previous 
 * and current state of the shape.
 * @author <jsimwing>
 **/
import java.awt.Color;
import java.util.ArrayList;

public class CFill extends Command{
	private Color _prevColor, _currColor;
	private SketchyShape _shape;
	private int _index;
	private ArrayList<SketchyShape> _array;
	public CFill(SketchyShape shape, Color oldColor, Color currColor,
			ArrayList<SketchyShape> array){
		_prevColor = oldColor;
		_currColor = currColor;
		_shape = shape;
		_array = array;
		_index = array.indexOf(shape);
	}
	@Override
	public void undo(){
		_shape = _array.get(_index);
		_shape.setColor(_prevColor);
	}
	public void redo(){
		_shape = _array.get(_index);
		_shape.setColor(_currColor);
	}
}
