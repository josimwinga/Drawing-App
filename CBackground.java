/**
 * This is the Background change command.
 * Every time the background color of the
 * canvas is changed, this command is
 * instantiated to save the previous 
 * and current state of the background.
 * @author <josimwing>
 **/
import java.awt.Color;

public class CBackground extends Command{
	private Color _prev, _curr;
	private Canvas _canvas;
	public CBackground(Canvas canvas, Color prev, Color curr){
		_canvas = canvas;
		_prev = prev;
		_curr = curr;
	}
	@Override
	public void undo(){
		_canvas.setBackground(_prev);
	}
	@Override
	public void redo(){
		_canvas.setBackground(_curr);
	}

}
