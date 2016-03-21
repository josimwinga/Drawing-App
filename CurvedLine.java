package Sketchy;
/**This is the Curved Line Class.
 * It takes in points and then uses
 * those point to draw lines which are 
 * saved in an array.
 * I chose to use an arrayList of 
 * points to keep track of the
 * points that are going to be 
 * added to the new line. Then those
 * new lines are saved in an arraylist
 * of Line2D.Double lines
 * When a curved line is opened 
 * after being saved, only the list
 * of Line2.DDoubles is instantiated
 * Two methods are used for reading a line:
 * Open color and Open . They are separate
 * in order to keep the code more organized
 * @author <jsimwing>
 */
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import cs015.fnl.SketchySupport.FileIO;
public class CurvedLine {
	private ArrayList<Point> _points;
	private ArrayList<Line2D.Double> _lines;
	private Color _color;
	
	public  CurvedLine(){
		_points = new ArrayList<Point>();
		_lines = new ArrayList<Line2D.Double>();
	}
	public void addLine(int x1, int y1, int x2, int y2){
		_lines.add(new Line2D.Double(x1, y1, x2, y2));
	}
	public void addPoint(Point point){
		_points.add(point);
		if(_points.indexOf(point) > 0){
			int p2Index = _points.indexOf(point) - 1;//index of the point 1 before the one that was just added
			Point point2 = _points.get(p2Index);
			this.addLine(point2.x, point2.y, point.x, point.y);
		}
	}
	public void setColor(Color color){
		_color = color;
	}
	public void paint(Graphics g){
		if(_lines != null){
			for(Line2D.Double line: _lines){
				g.setColor(_color);
				g.drawLine((int)line.x1, (int)line.y1, (int)line.x2, (int)line.y2);
			}
		}
	}
	//save file in this order: 
	//"cLine", [color(rgb), lines(x1, y1, x2, y2)]
	public void save(String filename, FileIO file){		
		for(Line2D.Double line: _lines){
			file.writeString(new String("cLine"));
			file.writeInt(_color.getRed());
			file.writeInt(_color.getGreen());
			file.writeInt(_color.getBlue());
			file.writeInt((int)line.x1);
			file.writeInt((int)line.y1);
			file.writeInt((int)line.x2);
			file.writeInt((int)line.y2);	
		}
	}
	public void openColor(int red, int green , int blue){
		_color = new Color(red, green, blue);
	}
	public void openLine(int x1, int y1, int x2, int y2){
		_lines.add(new Line2D.Double(x1, y1, x2, y2));
	}
}
