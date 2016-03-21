package Sketchy;
/**
 * This is the main panel.
 * It holds the canvas and
 * a panel of buttons and sliders
 * and labels. This class has many
 * private inner class mouse listeners.
 * 
 * @author <jsimwing>
 **/
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MainPanel extends JPanel{
	
	private Color _color;
	private Canvas _canvas;
	private JPanel _preview;

	public MainPanel(){
		_preview = new JPanel();
		JPanel sliders = new JPanel();
		JPanel topbar = new JPanel(); 
		JPanel sPanel = new JPanel(); //slider Panel
		JPanel bPanel1 = new JPanel();
		JPanel bPanel2 = new JPanel();
		JPanel bPanel = new JPanel(); //jbutton panel that holds bpanel 1 and 2
		JPanel rPanel = new JPanel(); //radio button panel
		JPanel filePanel = new JPanel(); //file button panel
		JPanel westPanel = new JPanel(); //panel that holds other panels, placed west in top panel
		JPanel labelPanel = new JPanel();
		JPanel copyPanel = new JPanel();
		_canvas = new Canvas();
		//default color
		int red = 127;
		int green = 127;
		int blue = 127;
		Color defaultColor = new Color(red, green, blue);
		_color = defaultColor;
		//JSliders
		JSlider redSlider = new JSlider(0, 255);
		JSlider greenSlider = new JSlider(0, 255);
		JSlider blueSlider = new JSlider(0, 255);
		//JButtons
		JButton lower = new JButton("Lower");
		JButton raise = new JButton("Raise");
		JButton fill = new JButton("Fill");
		JButton fillBG = new JButton("Fill BG");
		JButton delete = new JButton("Delete");
		JButton open = new JButton("Open"); 
		JButton save = new JButton("Save"); 
		JButton redo = new JButton("Redo"); 
		JButton undo = new JButton("Undo");
		JButton clear = new JButton("New");
		JButton getColor = new JButton("Get Fill");
		JButton help = new JButton("Help");
		JButton copy = new JButton("Copy");
		//RadioButtons
		JRadioButton select = new JRadioButton("Select Shape");
		JRadioButton drawL = new JRadioButton("Pen");
		JRadioButton drawFL = new JRadioButton("Fancy Pen");
		JRadioButton drawC = new JRadioButton("Draw Circle");
		JRadioButton drawS = new JRadioButton("Draw Square");
		ButtonGroup bg = new ButtonGroup();
		bg.add(select);
		bg.add(drawL);
		bg.add(drawFL);
		bg.add(drawC);
		bg.add(drawS);
		//Slider Listeners
		redSlider.addChangeListener(new Sliders(_preview, 0, redSlider));
		greenSlider.addChangeListener(new Sliders(_preview, 1, greenSlider));
		blueSlider.addChangeListener(new Sliders(_preview, 2, blueSlider));
		//J Listeners
		lower.addMouseListener(new Lower(this));
		raise.addMouseListener(new Raise(this));
		fill.addMouseListener(new Fill(this));
		fillBG.addMouseListener(new FillBG(this));
		delete.addMouseListener(new Delete(this));
		open.addMouseListener(new Open(this)); 
		save.addMouseListener(new Save(this));
		redo.addMouseListener(new Redo(this));
		undo.addMouseListener(new Undo(this));
		clear.addMouseListener(new Clear(this));
		getColor.addMouseListener(new getColor(this));
		help.addMouseListener(new Help(this));
		copy.addMouseListener(new Copy(this));
		//R Listeners
		drawC.addMouseListener((new Circle(this)));
		drawL.addMouseListener((new Line(this)));
		drawFL.addMouseListener((new FLine(this)));
		drawS.addMouseListener((new Square(this)));
		select.addMouseListener((new Select(this)));
		//Panel Size
		this.setPreferredSize(Constants.MPANEL_DIM);
		rPanel.setPreferredSize(Constants.RBUTTON_DIM);
		sliders.setPreferredSize(Constants.SLIDER_DIM);
		_preview.setPreferredSize(Constants.PREVIEW_DIM);
		topbar.setPreferredSize(Constants.TOOLBAR_DIM);
		sPanel.setPreferredSize(Constants.SPANEL_DIM);
		filePanel.setPreferredSize(Constants.FPANEL_DIM);
		westPanel.setPreferredSize(Constants.WPANEL_DIM);
		bPanel.setPreferredSize(Constants.JBUTTON_DIM);
		//button size
		lower.setPreferredSize(Constants.BUTTON_DIM);
		raise.setPreferredSize(Constants.BUTTON_DIM);
		undo.setPreferredSize(Constants.BUTTON_DIM);
		redo.setPreferredSize(Constants.BUTTON_DIM);
		delete.setPreferredSize(Constants.BUTTON_DIM);
		clear.setPreferredSize(Constants.BUTTON_DIM);
		save.setPreferredSize(Constants.BUTTON_DIM);
		delete.setPreferredSize(Constants.BUTTON_DIM);
		open.setPreferredSize(Constants.BUTTON_DIM);
		fill.setPreferredSize(Constants.BUTTON_DIM);
		fillBG.setPreferredSize(Constants.BUTTON_DIM);
		getColor.setPreferredSize(Constants.BUTTON_DIM);
		help.setPreferredSize(Constants.BUTTON_DIM);
		copy.setPreferredSize(Constants.BUTTON_DIM);
		//border
		Border border = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
		topbar.setBorder(border);
		labelPanel.setBorder(border);
		rPanel.setBorder(border);
		westPanel.setBorder(border);
		sPanel.setBorder(border);
		bPanel.setBorder(border);
		//color
		_preview.setBackground(_color);
		labelPanel.setBackground(new Color(248, 249, 250));
		//slider Panel
		sliders.add(redSlider);
		sliders.add(greenSlider);
		sliders.add(blueSlider);
		sPanel.setLayout(new BorderLayout());
		sPanel.add(_preview, BorderLayout.NORTH);
		sPanel.add(sliders, BorderLayout.CENTER);
		//button panel 1
		bPanel1.add(undo);
		bPanel1.add(redo);
		bPanel1.add(fill);
		bPanel1.add(fillBG);
		//button panel 2
		bPanel2.add(raise);
		bPanel2.add(lower);
		bPanel2.add(delete);
		bPanel2.add(getColor);
		//combined button panels
		bPanel.setLayout(new GridLayout(1, 2));
		bPanel.add(bPanel1);
		bPanel.add(bPanel2);
		//file Panel
		filePanel.add(clear);
		filePanel.add(save);
		filePanel.add(open);
		filePanel.add(help);
		//labelPanel
		labelPanel.setLayout(new GridLayout(1, 5));
		labelPanel.add(new JLabel("File", SwingConstants.CENTER));
		labelPanel.add(new JLabel()); //theses empty JLabels are purely for spacing
		labelPanel.add(new JLabel("Draw", SwingConstants.CENTER));
		labelPanel.add(new JLabel());
		labelPanel.add(new JLabel());
		labelPanel.add(new JLabel());
		labelPanel.add(new JLabel("Color", SwingConstants.CENTER));
		labelPanel.add(new JLabel());
		labelPanel.add(new JLabel());
		labelPanel.add(new JLabel("Edit", SwingConstants.CENTER));
		labelPanel.add(new JLabel());
		//Radio Button Panel
		copyPanel.add(copy);
		rPanel.setLayout(new GridLayout(3, 2));
		rPanel.add(select);
		rPanel.add(drawL);
		rPanel.add(drawS);
		rPanel.add(drawFL);
		rPanel.add(drawC);
		rPanel.add(copyPanel);
		//westPanel
		westPanel.setLayout(new BorderLayout());
		westPanel.add(filePanel, BorderLayout.WEST);
		westPanel.add(rPanel,  BorderLayout.EAST);
		//top panel
		topbar.setLayout(new BorderLayout());
		topbar.add(sPanel, BorderLayout.CENTER);
		topbar.add(bPanel, BorderLayout.EAST);
		topbar.add(westPanel, BorderLayout.WEST);
		topbar.add(labelPanel, BorderLayout.SOUTH);
		//MainPanel
		this.setLayout(new BorderLayout());
		this.add(_canvas, BorderLayout.CENTER);
		this.add(topbar, BorderLayout.NORTH);
	}
	public Color getColor(){
		return _color;
	}
	//Sliders Listener Inner Class
	private class Sliders implements ChangeListener{
		private JPanel _panel;
		private int _channel;
		private JSlider _slider;
		
		public Sliders(JPanel panel, int channel, JSlider slider){
			_panel = panel;
			_channel = channel;
			_slider = slider;		
		}
		public void stateChanged(ChangeEvent e){
			int val = _slider.getValue();
			Color bg = _panel.getBackground();
			Color newColor;
			switch(_channel){
			case 0:
				newColor = new Color(val, bg.getGreen(), bg.getBlue());
				break;
			case 1:
				newColor = new Color (bg.getRed(), val, bg.getBlue());
				break;
			default:
				newColor = new Color(bg.getRed(), bg.getGreen(), val);
			}
			_color = newColor;
			_panel.setBackground(newColor);
			_canvas.updateColor(newColor);
		}
	}
	//Lower Listener inner class
	private class Lower implements MouseListener{
		private Lower(MainPanel panel){
		}
		@Override
		public void mouseClicked(MouseEvent e){}
		@Override
		public void mousePressed(MouseEvent e){
			_canvas.lower();
		}
		@Override
		public void mouseReleased(MouseEvent e) {}
		@Override
		public void mouseEntered(MouseEvent e){}
		@Override
		public void mouseExited(MouseEvent e){}
	}
	//Raise Listener inner class
	private class Raise implements MouseListener{
		private Raise(MainPanel panel){}
		@Override
		public void mouseClicked(MouseEvent e){}
		@Override
		public void mousePressed(MouseEvent e){
			_canvas.raise();
		}
		@Override
		public void mouseReleased(MouseEvent e) {}
		@Override
		public void mouseEntered(MouseEvent e){}
		@Override
		public void mouseExited(MouseEvent e){}
	}
	//Fill Listener inner class
	private class Fill implements MouseListener{
		private Fill(MainPanel panel){
		}
		@Override
		public void mouseClicked(MouseEvent e){}
		@Override
		public void mousePressed(MouseEvent e){
			_canvas.fillShape();
		}
		@Override
		public void mouseReleased(MouseEvent e) {}
		@Override
		public void mouseEntered(MouseEvent e){}
		@Override
		public void mouseExited(MouseEvent e){}
	}
	//Fill Background Listener inner class
	private class FillBG implements MouseListener{
		private FillBG(MainPanel panel){
		}
		@Override
		public void mouseClicked(MouseEvent e){}
		@Override
		public void mousePressed(MouseEvent e){
			_canvas.addBG();
		}
		@Override
		public void mouseReleased(MouseEvent e) {}
		@Override
		public void mouseEntered(MouseEvent e){}
		@Override
		public void mouseExited(MouseEvent e){}
	}
	//Fill Background Listener inner class
	private class getColor implements MouseListener{
		private getColor(MainPanel panel){
		}
		@Override
		public void mouseClicked(MouseEvent e){}
		@Override
		public void mousePressed(MouseEvent e){
			_color = _canvas.getColor();
			_preview.setBackground(_color);
			_canvas.updateColor(_color);
		}
		@Override
		public void mouseReleased(MouseEvent e) {}
		@Override
		public void mouseEntered(MouseEvent e){}
		@Override
		public void mouseExited(MouseEvent e){}
	}
	//Delete Listener inner class
	private class Delete implements MouseListener{
		private Delete(MainPanel panel){
		}
		@Override
		public void mouseClicked(MouseEvent e){}
		@Override
		public void mousePressed(MouseEvent e){
			_canvas.deleteShape();
		}
		@Override
		public void mouseReleased(MouseEvent e) {}
		@Override
		public void mouseEntered(MouseEvent e){}
		@Override
		public void mouseExited(MouseEvent e){}
	}
	//open Listener inner class
	private class Open implements MouseListener{
		private Open(MainPanel panel){
		}
		@Override
		public void mouseClicked(MouseEvent e){}
		@Override
		public void mousePressed(MouseEvent e){
			_canvas.open();
		}
		@Override
		public void mouseReleased(MouseEvent e) {}
		@Override
		public void mouseEntered(MouseEvent e){}
		@Override
		public void mouseExited(MouseEvent e){}
	}
	//Save Listener inner class
	private class Save implements MouseListener{
		private Save(MainPanel panel){
		}
		@Override
		public void mouseClicked(MouseEvent e){}
		@Override
		public void mousePressed(MouseEvent e){
			_canvas.save();
		}
		@Override
		public void mouseReleased(MouseEvent e) {}
		@Override
		public void mouseEntered(MouseEvent e){}
		@Override
		public void mouseExited(MouseEvent e){}
	}
	//help Listener inner class
	private class Help implements MouseListener{
		private Help(MainPanel panel){
		}
		@Override
		public void mouseClicked(MouseEvent e){}
		@Override
		public void mousePressed(MouseEvent e){
			JOptionPane.showMessageDialog(null,
				    "Welcome to Sketchy.\n\n"
				    + "To create a shape click Draw Square/Circle and then\n"
				    + "click and drag on the canvas to set the shape's size.\n\n"
				    + "To clear the screen press New.\n Note: this will delete "
				    + "anything that has not been saved.\n\n"
				    + "KeyBoard Controls:\n"
				    + "Shift + Left Mouse to resize\n"
				    + "Note: for optimal resizing, release the mouse button \n"
				    + "before releasing the shift key\n"
				    + "Shift + Right Mouse to snap a shape to its original rotation\n"
				    + "Right Mouse to rotate a shape\n\n"
				    + "To change the background color press Fill BG\n\n"
				    + "To get a shape's color press Get Fill\n\n"
				    + "To make a copy of a shape select a shape and press Copy",
				    "About Sketchy",
				    JOptionPane.PLAIN_MESSAGE);
		}
		@Override
		public void mouseReleased(MouseEvent e) {}
		@Override
		public void mouseEntered(MouseEvent e){}
		@Override
		public void mouseExited(MouseEvent e){}
	}
	//Redo Listener inner class
	private class Redo implements MouseListener{
		private Redo(MainPanel panel){
		}
		@Override
		public void mouseClicked(MouseEvent e){}
		@Override
		public void mousePressed(MouseEvent e){
			_canvas.redo();
		}
		@Override
		public void mouseReleased(MouseEvent e) {}
		@Override
		public void mouseEntered(MouseEvent e){}
		@Override
		public void mouseExited(MouseEvent e){}
	}
	//Undo Listener inner class
	private class Undo implements MouseListener{
		private Undo(MainPanel panel){
		}
		@Override
		public void mouseClicked(MouseEvent e){}
		@Override
		public void mousePressed(MouseEvent e){
			_canvas.undo();;
		}
		@Override
		public void mouseReleased(MouseEvent e) {}
		@Override
		public void mouseEntered(MouseEvent e){}
		@Override
		public void mouseExited(MouseEvent e){}
	}
	//Clear Listener inner class
	private class Clear implements MouseListener{
		private Clear(MainPanel panel){
		}
		@Override
		public void mouseClicked(MouseEvent e){}
		@Override
		public void mousePressed(MouseEvent e){
			_canvas.clear();
		}
		@Override
		public void mouseReleased(MouseEvent e) {}
		@Override
		public void mouseEntered(MouseEvent e){}
		@Override
		public void mouseExited(MouseEvent e){}
	}
	//Copy listener private inner class
	private class Copy implements MouseListener{
		private Copy(MainPanel panel){
		}
		@Override
		public void mouseClicked(MouseEvent e){}
		@Override
		public void mousePressed(MouseEvent e){
			_canvas.copyShape();
		}
		@Override
		public void mouseReleased(MouseEvent e) {}
		@Override
		public void mouseEntered(MouseEvent e){}
		@Override
		public void mouseExited(MouseEvent e){}
	}
	//Select Listener inner class
	private class Select implements MouseListener{
		private Select(MainPanel panel){
		}
		@Override
		public void mouseClicked(MouseEvent e){}
		@Override
		public void mousePressed(MouseEvent e){
			_canvas.selectToggled();
		}
		@Override
		public void mouseReleased(MouseEvent e) {}
		@Override
		public void mouseEntered(MouseEvent e){}
		@Override
		public void mouseExited(MouseEvent e){}
	}
	//Line Listener inner class
	private class Line implements MouseListener{
		private Line(MainPanel panel){
		}
		@Override
		public void mouseClicked(MouseEvent e){}
		@Override
		public void mousePressed(MouseEvent e){
			_canvas.lineToggled();
		}
		@Override
		public void mouseReleased(MouseEvent e) {}
		@Override
		public void mouseEntered(MouseEvent e){}
		@Override
		public void mouseExited(MouseEvent e){}
	}
	//Featherd Line listener inner class
	private class FLine implements MouseListener{
		private FLine(MainPanel panel){
		}
		@Override
		public void mouseClicked(MouseEvent e){}
		@Override
		public void mousePressed(MouseEvent e){
			_canvas.fLineToggled();
		}
		@Override
		public void mouseReleased(MouseEvent e) {}
		@Override
		public void mouseEntered(MouseEvent e){}
		@Override
		public void mouseExited(MouseEvent e){}
	}
	//Circle Listener inner class
	private class Circle implements MouseListener{
		private Circle(MainPanel panel){
		}
		@Override
		public void mouseClicked(MouseEvent e){}
		@Override
		public void mousePressed(MouseEvent e){
			_canvas.circleToggled();
		}
		@Override
		public void mouseReleased(MouseEvent e) {}
		@Override
		public void mouseEntered(MouseEvent e){}
		@Override
		public void mouseExited(MouseEvent e){}
	}
	//Square Listener inner class
	private class Square implements MouseListener{
		private Square(MainPanel panel){
		}
		@Override
		public void mouseClicked(MouseEvent e){}
		@Override
		public void mousePressed(MouseEvent e){
			_canvas.squareToggled();
		}
		@Override
		public void mouseReleased(MouseEvent e) {}
		@Override
		public void mouseEntered(MouseEvent e){}
		@Override
		public void mouseExited(MouseEvent e){}
	}
}