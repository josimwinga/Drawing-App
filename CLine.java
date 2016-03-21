package Sketchy;
/**
 * This is the line change command.
 * Every time a line is drawn on
 * canvas, this command is
 * instantiated to save the previous 
 * and current state of the line.
 * @author <jsimwing>
 **/
import java.util.ArrayList;

public class CLine extends Command{
	private ArrayList<CurvedLine> _array;
	private CurvedLine _line;
	private int _index;
	
	public CLine(ArrayList<CurvedLine> array, CurvedLine line, int index){
		_array = array;
		_line = line;
		_index = index;
	}
	@Override
	public void undo(){
		_array.remove(_index);
	}
	@Override
	public void redo(){
		_array.add(_line);
	}

}
