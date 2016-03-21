package Sketchy;

import javax.swing.JFrame;

/**
 * This is the main class, where your program will start.
 * Note that this is an application, where the main method calls 
 * the App constructor.
 *
 * Class comments here...
 * Program controls:
 * Shape functionality:
 * To draw a shape, toggle its corresponding button, click on the canvas
 * and then drag the mouse to create and size the shape.
 * To resize a shape, while the select button is toggled,
 * hold shift and the left mouse button down while dragging
 * the mouse (release the mouse before releasing the shift button)
 * To move an object while the select button is toggled, 
 * click on a shape and drag the mouse.
 * To rotate a shape while the select button is toggled,
 * right click on a shape and drag the mouse. Hold the
 * shift button down to snap the shape to its original rotation.
 * To make a copy of a selected shape press "copy"
 * A selected shape may also be raised, lowered, and deleted
 * Note: while resizing, if the mouse is released before the shift key,
 * the shape may move location after resizing
 * Color:
 * To modify the color, drag the sliders until the desired color appears.
 * The background color may be changed by pressing "Fill BG"
 * A selected shape may be filled with a color by pressing "fill"
 * A selected shape's fill may be gotten by pressing "get fill"
 * Lines:
 * To draw a line or a fancy line, toggle the line's button and 
 * click and drag on the canvas. The lines color will be whatever
 * color is visible in the color preview pane
 * Files:
 * To save a file press "Save"
 * To open a file press "open" (only sketchy files may be opened)
 * To clear the canvas or create a new file, press "new"
 * 
 * For more program controls press "help"
 * 
 *
 * @author <jsimwing>
 * Did you discuss your design with another student?
 * If so, list their login here:
 *
 */

public class APP {

	public APP() {
		// Constructor code goes here.
		JFrame frame = new JFrame();
		MainPanel panel = new MainPanel();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);
		frame.pack();
		frame.setVisible(true);
	}

	/* You don't need to touch this part. */
	public static void main(String[] argv) {
		new APP();
	}

}
