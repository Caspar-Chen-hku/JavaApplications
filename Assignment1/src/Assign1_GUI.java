import javax.swing.*;
import javax.swing.event.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

/**
 * This class provides a simple GUI for visualizing Square, Triangle and Circle
 * objects. Users can draw squares, triangles and circles by simply clicking and
 * dragging the mouse in the drawing canvas.
 * 
 * @author Kenneth Wong
 *
 */
public class Assign1_GUI {
	private ArrayList<Shape> list = new ArrayList<Shape>();	// list of shapes
	private JFrame frame;									// main frame of the App
	private JSlider rSlider;								// slider for the red channel
	private JSlider gSlider;								// slider for the green channel
	private JSlider bSlider;								// slider for the blue channel
	private boolean resizing = false;						// drawing status
	private int xm, ym;										// local coordinates of current mouse pointer
	private String shapeSelection = "square";				// shape selected
	private boolean filled = true;							// fill-style selected
	private boolean randomColor = true;						// random color flag

	/**
	 * Creates an instance of the Assign1_GUI class and calls its start() method
	 * to starting the ball rolling.
	 * 
	 * @param args
	 *            not being used in this application.
	 */
	public static void main(String[] args) {
		Assign1_GUI assign1 = new Assign1_GUI();
		assign1.start();
	}

	/**
	 * Constructor of the Assign1_GUI class.
	 */
	public Assign1_GUI() {
	}

	/**
	 * Builds the GUI and allows users to start drawing squares, triangles and circles.
	 */
	public void start() {
		frame = new JFrame();
		frame.setTitle("COMP2396 Assignment 1");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// create the drawing canvas
		MyDrawingPanel canvas = new MyDrawingPanel();
		// create the tools panel
		MyToolsPanel tools = new MyToolsPanel();

		frame.add(canvas, BorderLayout.CENTER);
		frame.add(tools, BorderLayout.WEST);
		frame.setSize(680, 400);
		frame.setVisible(true);
	} // start()

	class MyDrawingPanel extends JPanel implements MouseListener,
			MouseMotionListener {
		private static final long serialVersionUID = 3434052834963106098L;

		public MyDrawingPanel() {
			this.addMouseListener(this);
			this.addMouseMotionListener(this);
		}

		// draw the list of shapes
		public void paintComponent(Graphics g) {
			g.setColor(Color.white);
			g.fillRect(0, 0, this.getWidth(), this.getHeight());

			for (Shape shape : list) {
				// set the pen color
				g.setColor(new Color(shape.r, shape.g, shape.b));

				// retrieve the screen coordinates of the vertices
				int[] x = shape.getX();
				int[] y = shape.getY();

				if (shape instanceof Circle) {
					// draw a circle
					if (shape.filled) {
						g.fillOval(x[0], y[0], x[1] - x[0], y[1] - y[0]);
					} else {
						g.drawOval(x[0], y[0], x[1] - x[0], y[1] - y[0]);
					}
				} else {
					// draw a polygon
					if (shape.filled) {
						g.fillPolygon(x, y, x.length);
					} else {
						g.drawPolygon(x, y, x.length);
					}
				}
			}
			
			// draw the line while resizing a shape
			if (list.size() > 0 && resizing) {
				Shape shape = list.get(list.size() - 1);
				g.setColor(Color.BLACK);
				g.drawLine((int) Math.round(shape.xc),
						(int) Math.round(shape.yc), xm, ym);
			}
		}

		// implement MouseListener
		public void mouseClicked(MouseEvent e) {
		}

		public void mouseEntered(MouseEvent e) {
		}

		public void mouseExited(MouseEvent e) {
		}

		public void mousePressed(MouseEvent e) {
			resizing = true;
			Shape shape;
			
			// create a new shape
			switch (shapeSelection) {
				case "square":
					shape = new Square();
					break;
				case "triangle":
					shape = new Triangle();
					break;
				case "circle":
					shape = new Circle();
					break;
				default:
					shape = new Square();
			}

			if (randomColor) {
				// generate a random color
				rSlider.setValue((int) (Math.random() * 256));
				gSlider.setValue((int) (Math.random() * 256));
				bSlider.setValue((int) (Math.random() * 256));
				frame.repaint();
			}

			// set the attributes of the shape
			shape.r = rSlider.getValue();
			shape.g = gSlider.getValue();
			shape.b = bSlider.getValue();
			shape.filled = filled;
			shape.theta = 0;
			shape.xc = xm = e.getX();
			shape.yc = ym = e.getY();
			shape.setVertices(0);
			
			// add the shape to the list
			list.add(shape);
		}

		public void mouseReleased(MouseEvent e) {
			resizing = false;
			frame.repaint();
		}

		// implement MouseMotionListener
		public void mouseDragged(MouseEvent e) {
			if (list.size() > 0) {
				xm = e.getX();
				ym = e.getY();
				Shape shape = list.get(list.size() - 1);
				double dx = xm - shape.xc;
				double dy = ym - shape.yc;
				if (e.isShiftDown()) {
					if (Math.abs(dx) > Math.abs(dy)) {
						dy = 0.0;
					} else {
						dx = 0.0;
					}
				}
				// resize the shape
				shape.setVertices(Math.hypot(dx, dy));
				// rotate the shape
				if (dx >= 0) {
					shape.theta = Math.atan(dy / dx);
				} else {
					shape.theta = Math.atan(dy / dx) + Math.PI;
				}
			}
			frame.repaint();
		}

		public void mouseMoved(MouseEvent e) {
		}
	} // MyDrawingPanel class

	class MyToolsPanel extends JPanel implements ActionListener, ItemListener,
			ChangeListener {
		private static final long serialVersionUID = -5202781653257742262L;
		JCheckBox fillCheckBox;			// check box for fill-style
		JCheckBox randomColorCheckBox;	// check box for random color
		ColorPatch colorPatch;			// a patch for showing a selected color

		public MyToolsPanel() {
			// create the shape selection panel
			JRadioButton sqrButton = new JRadioButton("Square");
			JRadioButton triButton = new JRadioButton("Triangle");
			JRadioButton cirButton = new JRadioButton("Circle");
			sqrButton.setActionCommand("square");
			triButton.setActionCommand("triangle");
			cirButton.setActionCommand("circle");
			sqrButton.addActionListener(this);
			triButton.addActionListener(this);
			cirButton.addActionListener(this);
			sqrButton.setSelected(true);
			ButtonGroup shapeGroup = new ButtonGroup();
			shapeGroup.add(sqrButton);
			shapeGroup.add(triButton);
			shapeGroup.add(cirButton);
			JPanel shapePanel = new JPanel(new GridLayout(3, 1));
			shapePanel.add(sqrButton);
			shapePanel.add(triButton);
			shapePanel.add(cirButton);
			shapePanel.setBorder(BorderFactory.createTitledBorder("Shape"));

			// create the fill-style selection panel
			fillCheckBox = new JCheckBox("Fill");
			fillCheckBox.setSelected(true);
			fillCheckBox.addItemListener(this);
			JPanel stylePanel = new JPanel(new GridLayout(1, 1));
			stylePanel.add(fillCheckBox);
			stylePanel.setBorder(BorderFactory.createTitledBorder("Style"));

			// create the color selection panel
			randomColorCheckBox = new JCheckBox("Random");
			randomColorCheckBox.setSelected(true);
			randomColorCheckBox.addItemListener(this);
			rSlider = new JSlider(JSlider.HORIZONTAL, 0, 255,
					(int) (Math.random() * 256));
			gSlider = new JSlider(JSlider.HORIZONTAL, 0, 255,
					(int) (Math.random() * 256));
			bSlider = new JSlider(JSlider.HORIZONTAL, 0, 255,
					(int) (Math.random() * 256));
			rSlider.setEnabled(false);
			gSlider.setEnabled(false);
			bSlider.setEnabled(false);
			rSlider.setPreferredSize(new Dimension(80, 20));
			gSlider.setPreferredSize(new Dimension(80, 20));
			bSlider.setPreferredSize(new Dimension(80, 20));
			rSlider.addChangeListener(this);
			gSlider.addChangeListener(this);
			bSlider.addChangeListener(this);
			colorPatch = new ColorPatch();
			colorPatch.setPreferredSize(new Dimension(60, 60));
			JPanel colorPanel = new JPanel();
			colorPanel.setLayout(new GridBagLayout());
			GridBagConstraints c;
			c = new GridBagConstraints(0, 0, 2, 1, 0, 1,
					GridBagConstraints.WEST, GridBagConstraints.NONE,
					new Insets(0, 0, 0, 0), 0, 0);
			colorPanel.add(randomColorCheckBox, c);
			c = new GridBagConstraints(0, 1, 1, 3, 0.6, 0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 0), 0, 0);
			colorPanel.add(colorPatch, c);
			c = new GridBagConstraints(1, 1, 1, 1, 0.8, 1,
					GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
					new Insets(0, 0, 0, 0), 0, 0);
			colorPanel.add(rSlider, c);
			c = new GridBagConstraints(1, 2, 1, 1, 0.8, 1,
					GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
					new Insets(0, 0, 0, 0), 0, 0);
			colorPanel.add(gSlider, c);
			c = new GridBagConstraints(1, 3, 1, 1, 0.8, 1,
					GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
					new Insets(0, 0, 0, 0), 0, 0);
			colorPanel.add(bSlider, c);
			colorPanel.setBorder(BorderFactory.createTitledBorder("Color"));

			// create a dummy panel to fill up the remaining vertical space
			JPanel dummyPanel = new JPanel();
			dummyPanel.setPreferredSize(new Dimension(1, 100000));

			this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			this.add(shapePanel);
			this.add(stylePanel);
			this.add(colorPanel);
			this.add(dummyPanel);
		}

		// implement ActionListener
		public void actionPerformed(ActionEvent e) {
			String actionCommand = e.getActionCommand();
			switch (actionCommand) {
			case "square":
			case "triangle":
			case "circle":
				shapeSelection = actionCommand;	// update the shape selection
			default:
			}
		}

		// implement ItemListener
		public void itemStateChanged(ItemEvent e) {
			filled = fillCheckBox.isSelected();				// update the fill-style selection
			randomColor = randomColorCheckBox.isSelected();	// update the random color selection
			if (randomColor) {
				// disable the sliders for choosing color
				rSlider.setEnabled(false);
				gSlider.setEnabled(false);
				bSlider.setEnabled(false);
			} else {
				// enable the sliders for choosing color
				rSlider.setEnabled(true);
				gSlider.setEnabled(true);
				bSlider.setEnabled(true);
			}
		}

		// implement ChangeListener
		public void stateChanged(ChangeEvent e) {
			frame.repaint();
		}

		public class ColorPatch extends JPanel {
			private static final long serialVersionUID = 2981915065239520451L;

			public void paintComponent(Graphics g) {
				int width = this.getWidth();
				int height = this.getHeight();
				int length = width < height ? width : height;
				int arc = (int) Math.round(length / 4.0);
				g.setColor(new Color(rSlider.getValue(), gSlider.getValue(),
						bSlider.getValue()));
				g.fillRoundRect((int) Math.round((width - length) / 2.0),
						(int) Math.round((height - length) / 2.0), length,
						length, arc, arc);
			}
		}
	} // MyToolsPanel class
}
