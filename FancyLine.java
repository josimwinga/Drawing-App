
/**
 * This is the fancy line class.
 * It takes in points to create lines
 * which are stored in an arrayList
 * @author <josimwing>
 **/
import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import cs015.fnl.SketchySupport.FileIO;
public class FancyLine {
	private ArrayList<Line2D.Double> _lines;
	private Color _color;
	public  FancyLine(){
		_lines = new ArrayList<Line2D.Double>();
	}
	public void addLine(Line2D.Double line, int x1, int y1, int x2, int y2){
		_lines.add(new Line2D.Double(x1, y1, x2, y2));
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
	//[color(rgb), lines(x1, y1, x2, y2)]
	public void save(String filename, FileIO file){		
		for(Line2D.Double line: _lines){
			file.writeString(new String("fLine"));
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
