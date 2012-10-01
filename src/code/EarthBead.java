package code;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * This component creates the lower part of a rod containing four earth beads.
 * According to its present present value and where the user will click the
 * earthBead component will have the value of 0, 1, 2, 3, 4 and an equivalent
 * number of beads which will be up and of the colour of the beadColour
 * attribute. The rest of the beads will be down and grey. The translation
 * attribute determines the distance separating earth beads when down from the
 * beam.
 */

public class EarthBead extends JPanel implements java.io.Serializable
{

	public final static int MINBEADDIAMETER = 44;

	private ArrayList<ActionListener> actionListenerList;

	private Color backgroundColour;
	private Color beadColour;

	private int value;
	private int previousValue;
	private int diameter;
	private int translation;
	private int initialPosition;
	private int movingPosition;
	private int cursorPosition;

	private Timer timer;

	/**
	 * Shall not be used by programmer: it exists for the ActionListerner
	 * mechanism Instead use EarthBead(final int diameter, final int translation,
	 * Color backgroundColour, Color beadColour)
	 */
	public EarthBead()
	{}

	/**
	 * Builts four earth beads.
	 * @param diameter integer setting the bead diameter
	 * @param translation integer setting the gap between the beads and the beam
	 * @param backgroundColour Color defining the colour of the background
	 * @param beadColour Color defining the colour of the beads
	 * @exception if diameter is inferior to 44
	 * @exception if translation is inferior to 0
	 */
	public EarthBead(final int diameter, final int translation, Color backgroundColour, Color beadColour)
			throws IllegalArgumentException
	{
		if (diameter < MINBEADDIAMETER)
			throw new IllegalArgumentException("The bead diameter should be superior to 44." + "Diameter: " + diameter);

		else if (translation < 0)
			throw new IllegalArgumentException("The translation of the bead should be superior or egal to 0. Translation: "
					+ translation);

		else
		{
			value = 0;
			previousValue = 0;
			this.diameter = diameter;
			this.translation = translation;
			this.backgroundColour = backgroundColour;
			this.beadColour = beadColour;
			setBackground(backgroundColour);

			addMouseListener(new MouseAdapter()
			{
				public void mouseClicked(MouseEvent e)
				{
					cursorPosition = e.getY();
					switch (value)
					// Determine what the new value is
					{
						case 0:
						{
							previousValue = 0;
							if (cursorPosition >= translation)
							{
								if (cursorPosition < (translation + diameter))
									value = 1;

								else if (cursorPosition < (translation + diameter * 2))
									value = 2;

								else if (cursorPosition < (translation + diameter * 3))
									value = 3;

								else value = 4;
							}
							movingPosition = translation;
							break;
						}
						case 1:
						{
							previousValue = 1;
							if (cursorPosition <= diameter)
								value = 0;

							else if (cursorPosition > (translation + diameter))
							{
								if (cursorPosition < (translation + diameter * 2))
									value = 2;

								else if (cursorPosition < (translation + diameter * 3))
									value = 3;

								else value = 4;
							}
							if (previousValue > value)
								movingPosition = diameter * value;

							else movingPosition = translation + diameter;
							break;
						}
						case 2:
						{
							previousValue = 2;
							if (cursorPosition <= diameter)
								value = 0;

							else if (cursorPosition <= diameter * 2)
								value = 1;

							else if (cursorPosition > (translation + diameter * 2))
							{
								if (cursorPosition < (translation + diameter * 3))
									value = 3;

								else if (cursorPosition < (translation + diameter * 4))
									value = 4;
							}
							if (previousValue > value)
								movingPosition = diameter * value;

							else movingPosition = translation + diameter * 2;
							break;
						}
						case 3:
						{
							previousValue = 3;
							if (cursorPosition <= diameter)
								value = 0;

							else if (cursorPosition <= diameter * 2)
								value = 1;

							else if (cursorPosition <= diameter * 3)
								value = 2;

							else if (cursorPosition > (translation + diameter * 3))
								value = 4;

							if (previousValue > value)
								movingPosition = diameter * value;

							else movingPosition = translation + diameter * 3;
							break;
						}
						case 4:
						{
							previousValue = 4;
							if (cursorPosition <= diameter)
								value = 0;

							else if (cursorPosition <= diameter * 2)
								value = 1;

							else if (cursorPosition <= diameter * 3)
								value = 2;

							else if (cursorPosition <= diameter * 4)
								value = 3;
							movingPosition = diameter * value;
						}
					}
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
		return new Dimension(diameter, diameter * 4 + translation);
	}

	/**
	 * Returns an integer which is the value shown by the four earth beads.
	 * @return integer
	 */

	public int getValue()
	{
		return value;
	}

	/**
	 * Displayed a value of the four earth beads.
	 * @param value integer
	 * @exception if value is superior to 4 or inferior to 0
	 */
	public void setValue(int value) throws IllegalArgumentException
	{
		if (value < 0 || value >= 5)
			throw new IllegalArgumentException("The new value of the earth bead should between 0 and 4. New value: " + value);

		else
		{
			this.value = value;
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
		setBackground(backgroundColour);
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

		if (value >= previousValue)
		{
			g.setColor(beadColour);
			initialPosition = 0;

			for (int x = 0; x < previousValue; x++)// Draw the beads left up
			{
				g.fillOval(0, initialPosition, diameter, diameter);
				initialPosition += diameter;
			}
			movingPosition -= translation / 20;
			if (movingPosition < diameter * previousValue)
				movingPosition = diameter * previousValue;
			initialPosition = movingPosition;

			for (int x = 0; x < (value - previousValue); x++)// Draw the moving beads
			{
				g.fillOval(0, initialPosition, diameter, diameter);
				initialPosition += diameter;
			}
			g.setColor(Color.LIGHT_GRAY);
			initialPosition = translation + diameter * value;

			for (int x = 0; x < 4 - value; x++)// Draw the beads left down
			{
				g.fillOval(0, initialPosition, diameter, diameter);
				initialPosition += diameter;
			}
		}
		else
		{
			g.setColor(beadColour);
			initialPosition = 0;

			for (int x = 0; x < value; x++)// Draw the beads left up
			{
				g.fillOval(0, initialPosition, diameter, diameter);
				initialPosition += diameter;
			}
			g.setColor(Color.LIGHT_GRAY);
			movingPosition += translation / 20;

			if (movingPosition > (translation + diameter * value))
				movingPosition = translation + diameter * value;
			initialPosition = movingPosition;

			for (int x = 0; x < previousValue - value; x++)// Draw the moving beads
			{
				g.fillOval(0, initialPosition, diameter, diameter);
				initialPosition += diameter;
			}
			initialPosition = translation + diameter * previousValue;

			for (int x = 0; x < 4 - previousValue; x++)// Draw the beads left down
			{
				g.fillOval(0, initialPosition, diameter, diameter);
				initialPosition += diameter;
			}
		}
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

	private class TimerListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			repaint();
			if ((value >= previousValue && movingPosition == diameter * previousValue)
					|| (value < previousValue && movingPosition == translation + diameter * value))
				timer.stop();
		}
	}

}
