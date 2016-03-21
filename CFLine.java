package Sketchy;
/**
 * This is the fancy line change command.
 * Every time a fancy line is drawn on
 * canvas, this command is
 * instantiated to save the previous 
 * and current state of the line.
 * @author <jsimwing>
 **/
import java.util.ArrayList;

public class CFLine extends Command{
	private ArrayList<FancyLine> _array;
	private FancyLine _line;
	private int _index;
	public CFLine(ArrayList<FancyLine> array, FancyLine line, int index){
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
