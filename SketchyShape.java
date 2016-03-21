package Sketchy;
/**This is the Sketchy Shape class. It is
 * an extension of the shape class
 * and has many methods unique to the class
 * and its subclasses.
 * @author jsimwing
 */

import cs015.fnl.SketchySupport.FileIO;

import java.lang.Math;
import java.awt.Color;
import java.awt.Point;
import java.awt.geom.RectangularShape;

import cs015.prj.Shape.Shape;

public class SketchyShape extends Shape{
	private double _shapeX, _shapeY;
	public SketchyShape(RectangularShape shape){
		super(shape);
	}
	//save file in this order: 
	//[type, location(x,y), dimension(w,h), rotation, color(rgb)]
	public void save(String filename, FileIO file){
		String type = new String(this.getType());
		Color color = this.getFillColor();
		file.writeString(type);
		file.writeInt((int)this.getX());
		file.writeInt((int)this.getY());
		file.writeInt((int)this.getWidth());
		file.writeInt((int)this.getHeight());
		file.writeInt((int)this.getRotation());
		file.writeInt((int)color.getRed());
		file.writeInt((int)color.getGreen());
		file.writeInt((int)color.getBlue());
	}
	public SketchyShape openShape(int x, int y, int width, int height, int rotation, int red, int green, int blue){
		this.setLocation(x, y);
		this.setSize(width, height);
		this.setRotation(rotation);
		this.setColor(new Color(red, green, blue));
		return this;
	}
	public SketchyShape copyShape(Point p, double width, double height, double rotation, Color color){
		this.setLocation(p.x +10, p.y + 10);
		this.setSize(width, height);
		this.setRotation(rotation);
		this.setColor(color);
		return this;
	}
	public void getLocation(double x, double y){
		_shapeX = x;
		_shapeY = y;
	}
	public void setLocation(Point point){
		this.setLocation(point.x, point.y);
	}
	public Point getLocation(){
		double x = this.getX();
		double y = this.getY();
		return new Point((int)x, (int)y);
	}
	public void translate(Point previous, Point current){
		int dx = current.x - previous.x;
		int dy = current.y - previous.y;
		this.setLocation(_shapeX + dx, _shapeY + dy);
	}
	public void rotate(Point previous, Point current){
		Point center = this.getCenter();
		double dA = Math.atan2(current.y - center.y, current.x - center.x)
				-Math.atan2(previous.y - center.y, previous.x - center.x);
		this.setRotation(Math.toDegrees(dA));
	}
	public Point rotatePoint(Point pointToRotate, Point rotateAround, Double angle){
		double sine = Math.sin(Math.toRadians(angle));
		double cosine = Math.cos(Math.toRadians(angle));
		Point point = new Point((pointToRotate.x - rotateAround.x), (pointToRotate.y - rotateAround.y));
		point = new Point((int)(point.x * cosine + point.y * sine), (int)(-point.x * sine + point.y * cosine));
		point = new Point(point.x + rotateAround.x, point.y + rotateAround.y);
		return point;
	}
	public void resize(Point prev, Point curr, Point center, Point prevLoc){
		double shapeRotation = this.getRotation();
		this.setRotation(-shapeRotation);
		Point rotatedPrev = this.rotatePoint(prev, center, shapeRotation);
		Point rotatedCurr = this.rotatePoint(curr, center, shapeRotation);
		double dx = Math.abs(rotatedPrev.x - rotatedCurr.x)/2;
		double dy = Math.abs(rotatedPrev.y - rotatedCurr.y)/4;
		double width = this.getWidth();
		double height = 0;
		double newY = 0;
		double newX = 0;
		//x movement
		if(this.getWidth() < 1){
			//reduce width
			width = Math.abs((center.x - rotatedCurr.x));
			newX = center.x - (width/2);
		}
		else if(rotatedCurr.x >= center.x & rotatedPrev.x < center.x){
			//reduce height
			width = Math.abs((center.x - rotatedCurr.x)/2);
			newX = center.x - (width/2);
		}
		else if(Math.abs(rotatedPrev.x - center.x) > Math.abs(rotatedCurr.x - center.x)){
			//reduce width
			width = this.getWidth() - dx/2;
			newX = this.getX() + (dx/4);
		}
		else{
			//increase width
			width = this.getWidth() + dx/2;	
			newX = this.getX() - (dx/4);	
		}
		// Y movement
		if((rotatedCurr.y <= center.y & rotatedPrev.y > center.y)||
				(rotatedCurr.y >= center.y & rotatedPrev.y < center.y) || this.getHeight() < 1 ){
			System.out.println("yUP");
			//increase height
			height = Math.abs((center.y - rotatedCurr.y));
			newY = center.y - (height/2);
		}
//		else if((rotatedCurr.y >= center.y & rotatedPrev.y < center.y)|| this.getHeight() < 1){
//			//increase height
//			height = Math.abs((center.y - rotatedCurr.y)/2);
//			newY = center.y - (height/2);
//		}
		else if(Math.abs(rotatedPrev.y - center.y) > Math.abs(rotatedCurr.y - center.y)){
			//reduce height
			height = this.getHeight() - dy/2;
			newY = this.getY() + (dy/4);
		}else{
			//increase height
			height = this.getHeight() + dy/2;
			newY = this.getY() - (dy/4);
		}
		this.setRotation(shapeRotation);
		this.setSize(width, height);
		this.setLocation(newX, newY);
	}
	public String getType(){
		return new String("SketchyShape");
	}
}
