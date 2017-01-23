
/**
 * This is the resize change command.
 * Every time a shape is resized on
 * canvas, this command is
 * instantiated to save the previous 
 * and current state of the shape.
 * @author <josimwing>
 **/
import java.awt.Point;
import java.util.ArrayList;
public class CResize extends Command{
	private double _prevW, _prevH, _currW, _currH;
	private SketchyShape _shape;
	private ArrayList<SketchyShape> _array;
	private Point _currLoc, _prevLoc;
	private int _index;
	
	public CResize(SketchyShape shape, ArrayList<SketchyShape> array, double prevW, double prevH, double currW, double currH,
			Point prevLoc, Point currLoc){
		_prevW = prevW;
		_prevH = prevH;
		_currW = currW;
		_currH = currH;
		_array = array;
		_currLoc = currLoc;
		_prevLoc = prevLoc;
		_index = array.indexOf(shape);
	}
	@Override
	public void undo(){
		_shape = _array.get(_index);
		_shape.setSize(_prevW, _prevH);
		_shape.setLocation(_prevLoc);
	}
	public void redo(){
		_shape = _array.get(_index);
		_shape.setSize(_currW, _currH);
		_shape.setLocation(_currLoc);	
	}
}
