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
 * This component creates the upper part of a rod containing one heaven bead. If
 * the state attribute is false the bead is up and grey and has the value 0. If
 * the state attribute is false the bead is down touching the beam, has the
 * beadColour attribute and has the value 5. The translation attribute
 * determines the distance between the bead when up and the beam.
 */

public class HeavenBead extends JPanel implements java.io.Serializable
{

	public final static int MINBEADDIAMETER = 44;

	private ArrayList<ActionListener> actionListenerList;

	private boolean state;

	private Color backgroundColour;
	private Color beadColour;

	private int value = 0;
	private int diameter;
	private int translation;
	private int initialPosition;

	private Timer timer;

	/**
	 * Shall not be used by programmer: it exists for the ActionListerner
	 * mechanism Instead use HeavenBead(int diameter, int translation, Color
	 * backgroundColour, Color beadColour)
	 */
	public HeavenBead()
	{}

	/**
	 * Builts one heaven bead.
	 * @param diameter integer setting the bead diameter
	 * @param translation integer setting the gap between the beads and the beam
	 * @param backgroundColour Color defining the colour of the background
	 * @param beadColour Color defining the colour of the beads
	 * @exception if diameter is inferior to 44
	 * @exception if translation is inferior to 0
	 */
	public HeavenBead(int diameter, int translation, Color backgroundColour, Color beadColour)
			throws IllegalArgumentException
	{
		if (diameter < MINBEADDIAMETER)
			throw new IllegalArgumentException("The bead diameter should be superior to 44. " + "Diameter: " + diameter);

		else if (translation < 0)
			throw new IllegalArgumentException("The translation of the bead should be superior "
					+ "or egal to 0. Translation: " + translation);
		else
		{
			initialPosition = 0;
			state = false;
			this.diameter = diameter;
			this.translation = translation;
			this.backgroundColour = backgroundColour;
			this.beadColour = beadColour;
			setBackground(backgroundColour);

			addMouseListener(new MouseAdapter()
			{
				public void mouseClicked(MouseEvent e)
				{
					if (state)
					{
						state = false;
						value = 0;
					}
					else
					{
						state = true;
						value = 5;
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
		return new Dimension(diameter, diameter + translation);
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
	 * Displayed a value of the heaven bead.
	 * @param value integer
	 * @exception if value is not 0 or 5
	 */
	public void setValue(int value) throws IllegalArgumentException
	{
		if (value != 0 && value != 5)
			throw new IllegalArgumentException("The new value of the heaven bead should be either 0 or 5. New value: "
					+ value);
		else
		{
			this.state = ((value == 0) ? false : true);
			this.value = value;
			timer = new Timer(10, new TimerListener());
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

		if (!(state))
		{
			initialPosition -= translation / 20;
			if (initialPosition < 0)
				initialPosition = 0;
			g.setColor(Color.LIGHT_GRAY);
			g.fillOval(0, initialPosition, diameter, diameter);
		}
		else if (state)
		{
			initialPosition += translation / 20;
			if (initialPosition > (translation))
				initialPosition = translation;
			g.setColor(beadColour);
			g.fillOval(0, initialPosition, diameter, diameter);
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
			if (((state) && initialPosition == translation) || (!state && initialPosition == 0))
				timer.stop();
		}
	}
}
