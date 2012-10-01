package code;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * This component creates the beam separating the earth beads from the heaven
 * bead. Its length is proportional to the number of rods in an abacus. If a
 * beam has a length of 1 the minimum and maximum exponent is 0. If the beam has
 * a length of x the minimum exponent is 0 and the maximum exponent is x-1. The
 * more the cursor go to the left the lowest the exponent gets.
 */

public class Beam extends JPanel implements Serializable
{

	public final static int MINWIDTH = 44;
	public final static int MINHEIGHT = 22;

	private ArrayList<ActionListener> actionListenerList;

	private Color backgroundColour;
	private Color beadColour;

	private int unitWidth;
	private int beamHeight;
	private int maxExponent;
	private int previousExponent;
	private int exponent;
	private int cursorPosition;
	private int cursorGoal;
	private int translation;
	private int translationUnit;

	private Timer timer;

	/**
	 * Shall not be used by programmer: it exists for the ActionListerner
	 * mechanism Instead use BeamTrial (int width, int height, int
	 * maximumExponant, Color backgroundColour, Color beadColour)
	 */
	public Beam()
	{}

	/**
	 * Builts a beam with a cursor to indicate the position of the decimal point.
	 * @param width integer setting the width of one unit and corresponding to the
	 *        rod width
	 * @param height integer setting the height of the beam
	 * @param backgroundColour Color defining the colour of the background
	 * @param beadColour Color defining the colour of the beads
	 * @exception if width is inferior to 44
	 * @exception if height is inferior to 22
	 * @exception if maximumExponent is inferior to 1
	 */
	public Beam(int width, int height, int maximumExponent, Color backgroundColour, Color beadColour)
			throws IllegalArgumentException
	{
		if (width < MINWIDTH || height < MINHEIGHT || maximumExponent <= 0)
			throw new IllegalArgumentException(
					"width should be superior or equal to 44. height should be superior or equal to 22.\n"
							+ "exponant should be superior to 0. width: " + width + "; height: " + height + "; exponent: "
							+ maximumExponent);

		else
		{
			unitWidth = width;
			beamHeight = height;
			this.maxExponent = maximumExponent;
			this.previousExponent = 0;
			this.exponent = 0;
			translationUnit = 15;
			translation = translationUnit;
			cursorPosition = unitWidth * (maxExponent - exponent) - (unitWidth / 2);
			cursorGoal = cursorPosition;
			this.backgroundColour = backgroundColour;
			this.beadColour = beadColour;

			addMouseListener(new MouseAdapter()
			{
				public void mouseClicked(MouseEvent e)
				{
					int totalWidth = unitWidth * maxExponent;
					int percentage = e.getX() * 100 / totalWidth;
					previousExponent = exponent;
					exponent = maxExponent - 1 - maxExponent * percentage / 100;

					if (exponent > previousExponent)
						translation = -1 * translationUnit;

					else if (exponent < previousExponent)
						translation = translationUnit;

					cursorPosition = cursorGoal;
					cursorGoal = unitWidth * (maxExponent - exponent) - (unitWidth / 2);

					repaint();

					timer = new Timer(10, new TimerListener());
					timer.start();
					processEvent(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
				}
			});
		}
	}

	public synchronized void addActionListener(ActionListener listener)
	{
		if (actionListenerList == null)
			actionListenerList = new ArrayList<ActionListener>(2);
		if (!actionListenerList.contains(listener))
			actionListenerList.add(listener);
	}

	public synchronized void removeActionListener(ActionListener listener)
	{
		if (actionListenerList != null && actionListenerList.contains(listener))
			actionListenerList.remove(listener);
	}

	public Dimension getPreferredSize()
	{
		return new Dimension(unitWidth * maxExponent, beamHeight);
	}

	/**
	 * Returns an integer which is the exponent shown on the beam.
	 * @return integer
	 */
	public int getExponent()
	{
		return exponent;
	}

	/**
	 * Displayed a new exponent on the beam.
	 * @param newExponent integer
	 * @exception if newExponent is superior or equal to the maximum exponent
	 *            entered in the constructor
	 * @exception if newExponent is inferior to 0
	 */
	public void setExponent(int newExponent) throws IllegalArgumentException
	{
		if (!(newExponent >= 0 && newExponent < maxExponent))
			throw new IllegalArgumentException("The new exponent needs to be superior or equal to 0 " + "and inferior to "
					+ maxExponent + ". The new exponent is " + newExponent);

		else
		{
			previousExponent = exponent;
			exponent = newExponent;

			cursorPosition = cursorGoal;
			cursorGoal = unitWidth * (maxExponent - exponent) - (unitWidth / 2);

			timer = new Timer(100, new TimerListener());
			timer.start();
		}
	}

	public Color getBackgroundColour()
	{
		return backgroundColour;
	}

	public void setBackgroundColour(Color backgroundColour)
	{
		this.backgroundColour = backgroundColour;
		repaint();
	}

	public Color getBeadColour()
	{
		return beadColour;
	}

	public void setBeadColour(Color beadColour)
	{
		this.beadColour = beadColour;
		repaint();
	}

	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		cursorPosition += translation;

		if ((translation == -1 * translationUnit && cursorPosition < cursorGoal)
				|| (translation == translationUnit && cursorPosition > cursorGoal))
			cursorPosition = cursorGoal;

		int rectPosition = 0;

		for (int x = 1; x <= maxExponent; x++)
		{
			g.setColor(backgroundColour);
			g.fillRect(rectPosition, 0, unitWidth, beamHeight);
			rectPosition += unitWidth;
		}

		int beadDiameter = unitWidth / 2;

		int beadY = (beamHeight * 2 > unitWidth ? (beamHeight - beadDiameter) / 2 : 0);

		g.setColor(beadColour);
		g.fillOval(cursorPosition, beadY, beadDiameter, beadDiameter);

	}

	private void processEvent(ActionEvent e)
	{
		ArrayList list;
		synchronized (this)
		{
			if (actionListenerList == null)
				return;
			list = (ArrayList) actionListenerList.clone();
		}
		for (int i = 0; i < list.size(); i++)
		{
			ActionListener listener = (ActionListener) list.get(i);
			listener.actionPerformed(e);
		}
	}

	class TimerListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			repaint();
			if ((translation == -1 * translationUnit && cursorPosition == cursorGoal)
					|| (translation == translationUnit && cursorPosition == cursorGoal))
				timer.stop();
		}
	}

}
