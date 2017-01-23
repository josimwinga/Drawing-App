
/**
 * This is the Canvas where all of the graphics will appear
 * and where files are saved, commands are made, and 
 * mouse interaction takes place.
 * There are 3 arrayLists of graphic elements,
 * a mouseListener/MouseMotionListener inner class,
 * and 2 stacks for the undo and redo methods. 
 * @author <josimwing>
 **/
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Stack;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Line2D.Double;
import javax.swing.JPanel;
import cs015.fnl.SketchySupport.FileIO;

public class Canvas extends JPanel{
	private SketchyShape _currShape;
	private SketchyCircle _circle;
	private SketchyRect _rect;
	private Color _color;
	private FancyLine _fLine;
	private CurvedLine _cLine;
	private Point _clickPoint, _prevLocation, _center;
	private double _prevRotation, _currRotation, _prevW, _prevH;
	private ArrayList<CurvedLine> _curvedLines;
	private ArrayList<FancyLine> _feathLines;
	private ArrayList<SketchyShape> _shapes;
	private Stack<Command> _undoStack, _redoStack;
	private boolean _selectToggled, _fLineToggled, 
			_lineToggled, _squareToggled, _circleToggled, _isResizing;
	
	public Canvas(){
		this.setPreferredSize(Constants.CANVAS_DIM);
		this.setBackground(Color.WHITE);
		//Radio Button Booleans
		_selectToggled = false;
		_lineToggled = false;
		_fLineToggled = false;
		_squareToggled = false;
		_circleToggled = false;
		//default color
		int red = 127;
		int green = 127;
		int blue = 127;
		Color defaultColor = new Color(red, green, blue);
		_color = defaultColor;
		//ArrayLists
		_shapes = new ArrayList<SketchyShape>();
		_curvedLines = new ArrayList<CurvedLine>();
		_feathLines = new ArrayList<FancyLine>();
		_redoStack = new Stack<Command>();
		_undoStack = new Stack<Command>();
		//Listeners
		ClickListener cListener = new ClickListener(this);
		this.addMouseListener(cListener);
		this.addMouseMotionListener(cListener);
	}
	public void circleToggled(){
		this.removeCurrShape();
		_circleToggled = true;
		_squareToggled = false;
		_fLineToggled = false;
		_selectToggled = false;	
		_lineToggled = false;
	}
	public void squareToggled(){
		this.removeCurrShape();
		_squareToggled = true;
		_fLineToggled = false;
		_selectToggled = false;
		_circleToggled = false;
		_lineToggled = false;
	}
	public void lineToggled(){
		this.removeCurrShape();
		_lineToggled = true;
		_fLineToggled = false;
		_selectToggled = false;
		_squareToggled = false;
		_circleToggled = false;
	}
	public void fLineToggled(){
		this.removeCurrShape();
		_fLineToggled = true;
		_selectToggled = false;
		_squareToggled = false;
		_circleToggled = false;
		_lineToggled = false;
	}
	public void selectToggled(){
		_selectToggled = true;
		_squareToggled = false;
		_circleToggled = false;
		_fLineToggled = false;
		_lineToggled = false;
	}
	public void addCommand(Command command){
		_undoStack.push(command);
		_redoStack.clear();
	}
	//remove any remaining borders and set _currShape to null
	public void removeCurrShape(){
		for(SketchyShape shape: _shapes){
			shape.setBorderColor(null);
			_currShape = null;
			this.repaint();
		}
	}
	public Color getColor(){
		if(isCurrShape() & _selectToggled){
			return _currShape.getFillColor();
		}
		else{
			return _color;
		}
	}
	//clear the canvas/create a "new" canvas
	public void clear(){
		//dialog box check for user
		int response = JOptionPane.showConfirmDialog(new JFrame(), 
				"Are you sure you want to open a new canvas?\n"
				+ "All unsaved changes will be lost.", "Sketchy", 
				JOptionPane.YES_NO_OPTION);
		if(response == JOptionPane.YES_OPTION){
			this.setBackground(Color.WHITE);
			if(_undoStack != null){
				_undoStack.clear();
			}
			if(_redoStack != null){
				_redoStack.clear();
			}
			if(shapesExist()){
				_shapes.clear();
			}
			if(_curvedLines != null){
				_curvedLines.clear();
			}
			if(_feathLines != null){
				_feathLines.clear();
			}
			this.repaint();
		}else{
			return;
		}
	}
	//returns true if _shapes is not empty
	public boolean shapesExist(){
		if(_shapes != null){
			return true;
		}else{
			return false;
		}
	}//returns true if there is a _currShape
	public boolean isCurrShape(){
		if(_currShape != null){
			return true;
		}else{
			return false;
		}
	}
	public void updateColor(Color color){
		_color = color;
	}
	public void fillShape(){
		if(_selectToggled & isCurrShape()){
			Color currColor = _currShape.getFillColor();
			_currShape.setColor(_color);
			_currShape.setBorderColor(Color.BLACK);
			_currShape.setBorderWidth(2);
			CFill command = new CFill(_currShape, currColor, _color, _shapes);
			this.addCommand(command);
			this.repaint();
		}	
	}
	//update the background color and add its command
	public void addBG(){
		Color color = this.getBackground();
		this.setBackground(_color);
		CBackground command = new CBackground(this, color, _color);
		this.addCommand(command);
		this.repaint();
	}
	public void copyShape(){
		if(isCurrShape() & _selectToggled){
			SketchyShape copy;
			if(_currShape.getType().equals("rect")){
				copy = new SketchyRect(new Rectangle2D.Double());
				
			}else{
				copy = new SketchyCircle(new Ellipse2D.Double());
			}
			copy.copyShape(_currShape.getLocation(), _currShape.getWidth(),
						_currShape.getHeight(), _currShape.getRotation(), _currShape.getFillColor());
			_shapes.add(copy);
			CDrawShape command = new CDrawShape(copy, copy.getWidth(), copy.getHeight(),
					copy.getLocation(), _shapes, _shapes.indexOf(copy));
			this.repaint();
			this.addCommand(command);
			}
	}
	//move the current shapes position down 1 spot in the array
	public void lower(){
		int currIndex = _shapes.indexOf(_currShape);
		if(shapesExist() & isCurrShape() & currIndex > 0){
			int newIndex = currIndex - 1;
			SketchyShape swapShape = _shapes.get(newIndex);
			_shapes.set(newIndex, _currShape);
			_shapes.set(currIndex, swapShape);
			CSwap command = new CSwap(_currShape, swapShape,
					_shapes, currIndex, newIndex);
			this.addCommand(command);
			this.repaint();
		}
	}
	//move the current shapes position up 1 spot in the array
	public void raise(){
		int currIndex = _shapes.indexOf(_currShape);
		if(shapesExist() & isCurrShape() &  currIndex < _shapes.size() - 1){
			int newIndex = currIndex + 1;
			SketchyShape swapShape = _shapes.get(newIndex);
			_shapes.set(newIndex, _currShape);
			_shapes.set(currIndex, swapShape);
			CSwap command = new CSwap(_currShape, swapShape,
					_shapes, currIndex, newIndex);
			this.addCommand(command);
			this.repaint();
		}
	}
	public void drawCircle(SketchyCircle circle, Point p, double dx, double dy){
		circle.setLocation(p.x, p.y);
		circle.setSize(dx, dy);
		circle.setColor(_color);
		this.repaint();
	}
	public void addCircleC(SketchyCircle circle){
		_shapes.add(circle);
		CDrawShape command = new CDrawShape(circle, circle.getWidth(), circle.getHeight(),
				circle.getLocation(), _shapes, _shapes.indexOf(circle));
		this.addCommand(command);
		_circle = null;
		this.repaint();
	}
	public void drawRect(SketchyRect rect, Point p, double dx, double dy){
		rect.setLocation(p.x, p.y);
		rect.setSize(dx, dy);
		rect.setColor(_color);
		this.repaint();
	}
	public void addRectC(SketchyRect rect){
		_shapes.add(rect);
		CDrawShape command = new CDrawShape(rect, rect.getWidth(), rect.getHeight(),
				rect.getLocation(), _shapes, _shapes.indexOf(rect));
		this.addCommand(command);
		_rect = null;
		this.repaint();
	}
	public void drawCLine(){
		int index = _curvedLines.indexOf(_cLine);
		CLine command = new CLine(_curvedLines, _cLine, index);
		this.addCommand(command);
		_cLine = null;
		this.repaint();
	}
	public void drawFLine(){
		int index = _feathLines.indexOf(_fLine);
		CFLine command = new CFLine(_feathLines, _fLine, index);
		this.addCommand(command);
		_fLine = null;
		this.repaint();
	}
	public void deleteShape(){
		if(isCurrShape()){
			int index = _shapes.indexOf(_currShape);
			SketchyShape shape = _currShape;
			_shapes.remove(shape);
			_currShape = null;
			CDelete command = new CDelete(shape, shape.getWidth(), shape.getHeight(),
					 shape.getRotation(), shape.getLocation(), shape.getFillColor(), _shapes, index);
			this.addCommand(command);
			this.repaint();
		}else{ 
			return;
		}
	}
	public void translate(Point currLocation){
		CTranslate command = new CTranslate(_currShape, _shapes, _prevLocation, currLocation);
		this.addCommand(command);
		this.repaint();
	}
	public void rotate(){
		CRotate command = new CRotate(_currShape, _shapes, _prevRotation, _currRotation);
		this.addCommand(command);
	}
	public void resize(double currW , double currH, Point currLoc){
		CResize command = new CResize(_currShape, _shapes, _prevW , _prevH, currW, currH, _prevLocation, currLoc);
		_isResizing = false;
		this.addCommand(command);
	}
	public void undo(){
		if(_undoStack.size() > 0){
			Command command = _undoStack.pop();
			_redoStack.push(command);
			command.undo();
			if(isCurrShape()){
				_currShape.setBorderColor(Color.BLACK);
				_currShape.setBorderWidth(2);
			}
			this.repaint();
		}
	}
	public void redo(){
		if(_redoStack.size() > 0){
			Command command = _redoStack.pop();
			_undoStack.push(command);
			command.redo();
			this.repaint();
		}
	}
	public void save(){
		FileIO file = new FileIO();
		String filename = FileIO.getFileName(true, this);
		//null check in case user clicks "cancel" 
		if(filename == null){
			return;
		}else{
		file.openWrite(filename);
		file.writeString(new String("background"));
		file.writeInt(this.getBackground().getRed());
		file.writeInt(this.getBackground().getGreen());
		file.writeInt(this.getBackground().getBlue());
		if(shapesExist()){
			for(SketchyShape shape: _shapes){
				shape.save(filename, file);
			}
		}
		if(_curvedLines != null){
			for(CurvedLine line: _curvedLines){
				line.save(filename, file);
			}
		}
		if(_feathLines != null){
			for(FancyLine fLine: _feathLines){
				fLine.save(filename, file);
			}
		}
		file.closeWrite();
		}
	}
	public void open(){
		FileIO file = new FileIO();
		String filename = FileIO.getFileName(false, this);
		//null check in case user clicks "cancel"
		if(filename == null){
			return;
		}else{
			this.setBackground(Color.WHITE);
			if(_undoStack != null){
				_undoStack.clear();
			}
			if(_redoStack != null){
				_redoStack.clear();
			}
			if(shapesExist()){
				_shapes.clear();
			}
			if(_curvedLines != null){
				_curvedLines.clear();
			}
			if(_feathLines != null){
				_feathLines.clear();
			}
		file.openRead(filename);
		CurvedLine line;
		FancyLine fLine;
		while(file.hasMoreData()){
			String type = file.readString();
			if(type.equals("background")){
				this.setBackground(new Color(file.readInt(), file.readInt(), file.readInt()));
			}
			if(type.equals("circle")){
				SketchyCircle circle = new SketchyCircle(new Ellipse2D.Double());
				circle.openShape(file.readInt(), file.readInt(), file.readInt(), file.readInt(), 
								file.readInt(), file.readInt(), file.readInt(), file.readInt());
				_shapes.add(circle);
			}
			if(type.equals("rect")){
				SketchyRect rect = new SketchyRect(new Rectangle2D.Double());
				rect.openShape(file.readInt(), file.readInt(), file.readInt(), file.readInt(), 
								file.readInt(), file.readInt(), file.readInt(), file.readInt());
				_shapes.add(rect);
			}
			if(type.equals("cLine")){
				line = new CurvedLine();
				line.openColor(file.readInt(), file.readInt(), file.readInt());
				line.openLine(file.readInt(), file.readInt(), file.readInt(), file.readInt());
				_curvedLines.add(line);
			}
			if(type.equals("fLine")){
				fLine = new FancyLine();
				fLine.openColor(file.readInt(), file.readInt(), file.readInt());
				fLine.openLine(file.readInt(), file.readInt(), file.readInt(), file.readInt());
				_feathLines.add(fLine);
			}
		}
		file.closeRead();
		this.repaint();
		}
	}
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		if(_cLine != null){
			_cLine.paint(g);
		}
		if(_fLine != null){
			_fLine.paint(g);
		}
		if(_circle != null){
			_circle.paint(g);
		}
		if(_rect != null){
			_rect.paint(g);
		}
		for(FancyLine line: _feathLines){
			if(line != null){
			line.paint(g);
			}		
		}
		for(CurvedLine line: _curvedLines){
			if(line != null){
			line.paint(g);
			}		
		}
		for(SketchyShape shape: _shapes){
			if(shapesExist()){
				shape.paint(g);
			}
		}
	}
	//private inner mouse listener
	private class ClickListener implements MouseListener, MouseMotionListener{
		private Canvas _canvas;
		public ClickListener(Canvas canvas){
			_canvas = canvas;
		}
		@Override
		public void mouseClicked(MouseEvent e){}
		@Override
		public void mousePressed(MouseEvent e){
			_clickPoint = e.getPoint();
			//select shape
			if (_selectToggled){
				for(SketchyShape shape: _shapes){
					if(shape.contains(e.getPoint())){
						//remove previous currShape
						if(isCurrShape()){
							_canvas.removeCurrShape();
						}//set _currShape
						_currShape= shape;
						_currShape.getLocation(_currShape.getX(), _currShape.getY());
						_prevW = _currShape.getWidth();
						_prevH = _currShape.getHeight();
						_center = _currShape.getCenter();
						_prevLocation = _currShape.getLocation();
						_prevRotation = _currShape.getRotation();
						_currShape.setBorderColor(Color.BLACK);
						_currShape.setBorderWidth(2);
						_canvas.repaint();
					}else if(isCurrShape()){//remove _currShape
						if(_currShape.contains(e.getPoint()) == false){
							_canvas.removeCurrShape();
						}
					}else{//remove borders
						shape.setBorderColor(null);
						_canvas.repaint();
					}
				}
			}
			//create feather line
			if (_fLineToggled){
				_fLine = new FancyLine();
			}
			//create curved line
			if(_lineToggled){
				_cLine = new CurvedLine();
			}
			//create new circle
			if(_circleToggled){
				_circle = new SketchyCircle(new Ellipse2D.Double());			
			}
			//create new square
			if (_squareToggled){
				_rect = new SketchyRect(new Rectangle2D.Double());
			}
		}
		@Override
		public void mouseDragged(MouseEvent e){
			//draw feathered line
			if(_fLineToggled){
				Double line = new Double();
				_fLine.addLine(line, _clickPoint.x, _clickPoint.y, 
						e.getPoint().x, e.getPoint().y);
				_fLine.setColor(_color);
				_canvas.repaint();
			}
			//draw curved line
			if(_lineToggled){
				_cLine.addPoint(e.getPoint());
				_cLine.setColor(_color);
				_canvas.repaint();
			}
			//draw circle
			if(_circleToggled){
				double dx = Math.abs(e.getPoint().x - _clickPoint.x);
				double dy = Math.abs(e.getPoint().y - _clickPoint.y);
				int newX = 0;
				int newY = 0;
				if(e.getPoint().x < _clickPoint.x){
					newX = _clickPoint.x - (int)dx;
				}
				if(e.getPoint().y < _clickPoint.y){
					newY = _clickPoint.y - (int)dy;
				}
				if(e.getPoint().y > _clickPoint.y){
					newY = _clickPoint.y;
				}
				if(e.getPoint().x > _clickPoint.x){
					newX = _clickPoint.x;
				}
				_canvas.drawCircle(_circle, new Point(newX, newY), dx, dy);
			}
			//draw rectangle
			if(_squareToggled){
				double dx = Math.abs(e.getPoint().x - _clickPoint.x);
				double dy = Math.abs(e.getPoint().y - _clickPoint.y);
				int newX = 0;
				int newY = 0;
				if(e.getPoint().x < _clickPoint.x){
					newX = _clickPoint.x - (int)dx;
					
				}
				if(e.getPoint().y < _clickPoint.y){
					newY = _clickPoint.y - (int)dy;
				}
				if(e.getPoint().y > _clickPoint.y){
					newY = _clickPoint.y;
				}
				if(e.getPoint().x > _clickPoint.x){
					newX = _clickPoint.x;
				}
				_canvas.drawRect(_rect, new Point(newX, newY), dx, dy);
			}
			//translate current shape
			if(_selectToggled & SwingUtilities.isLeftMouseButton(e) & isCurrShape()
					& (e.getModifiersEx() & InputEvent.SHIFT_DOWN_MASK ) == 0){
				_currShape.translate(_clickPoint, e.getPoint());	
				_canvas.repaint();
			}
			//rotate current shape
			if(_selectToggled & SwingUtilities.isRightMouseButton(e) & isCurrShape()){
				_currShape.rotate(_clickPoint, e.getPoint());
				_canvas.repaint();
			}
			//snap rotation with shift key
			if(_selectToggled & SwingUtilities.isRightMouseButton(e) & isCurrShape()
					& (e.getModifiersEx() & InputEvent.SHIFT_DOWN_MASK ) != 0){
				_currShape.setRotation(0);
				_canvas.repaint();
			}
			//resize current shape
			if(_selectToggled & (e.getModifiersEx() & InputEvent.SHIFT_DOWN_MASK )!= 0
					& SwingUtilities.isLeftMouseButton(e) & isCurrShape()){
				_currShape.resize(_clickPoint, e.getPoint(), _center, _prevLocation);
				_isResizing = true;
				_canvas.repaint();
			}
		}
		@Override
		public void mouseMoved(MouseEvent e){}
		@Override
		public void mouseReleased(MouseEvent e) {
			//get currShape's rotation
			if(_selectToggled & SwingUtilities.isRightMouseButton(e) & isCurrShape()){
				_currRotation = _currShape.getRotation();
				_canvas.rotate();
			}
			//get currShape's location
			if(_selectToggled & SwingUtilities.isLeftMouseButton(e) & isCurrShape()
					& (e.getModifiersEx() & InputEvent.SHIFT_DOWN_MASK ) == 0){
				Point currLocation = _currShape.getLocation();
				_canvas.translate(currLocation);
			}
			//get currShape's height and width for resize
			if(_isResizing){
				double currW =_currShape.getWidth();
				double currH = _currShape.getHeight();
				_currRotation = _currShape.getRotation();
				_canvas.resize(currW, currH, _currShape.getLocation());
			}
			if(_lineToggled){
				_curvedLines.add(_cLine);
				_canvas.drawCLine();
			}
			if(_fLineToggled){
				_feathLines.add(_fLine);
				_canvas.drawFLine();
			}
			if(_circleToggled){
				_canvas.addCircleC(_circle);
			}
			if(_squareToggled){
				_canvas.addRectC(_rect);
			}
		}
		@Override
		public void mouseEntered(MouseEvent e){}
		@Override
		public void mouseExited(MouseEvent e){}
	}	
}
