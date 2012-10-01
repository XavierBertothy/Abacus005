package code;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JPanel;

/**
 * This component creates a rod composed of 1 heaven bead and 4 earth beads
 * separated by a chunk of a beam. Below the earth beads the value of the rod
 * can displayed.
 */

public class Rod extends JPanel implements java.io.Serializable
{

	private ArrayList<ActionListener> actionListenerList;

	private EarthBead earthBead;

	private int value;
	private int rodWidth;
	private int rodHeight;

	private HeavenBead heavenBead;

	private NumberBox numberBox;

	/**
	 * Shall not be used by programmer: it exists for the ActionListerner
	 * mechanism Instead use Rod(int diameter, int translation, Color
	 * backgroundColour, Color beadColour, Boolean isValueDisplayed)
	 */
	public Rod()
	{}

	/**
	 * Builts four earth beads.
	 * @param width integer setting the bead diameter
	 * @param translation integer setting the gap between the beads and the beam
	 * @param beamHeight integer setting the height of the beam
	 * @param backgroundColour Color defining the colour of the background
	 * @param beadColour Color defining the colour of the beads
	 * @param isValueDisplayed Boolean if true the value of the rod will displayed
	 *        at the bottom. If false the value will not be displayed
	 * @exception if diameter is inferior to 44
	 * @exception if translation is inferior to 0
	 */
	public Rod(int width, int translation, int beamHeight, Color backgroundColour, Color beadColour,
			Boolean isValueDisplayed)
	{
		try
		{
			this.rodWidth = width;

			heavenBead = new HeavenBead(rodWidth, translation, backgroundColour, beadColour);
			ActionListener heavenBeadListener = new Listener();
			heavenBead.addActionListener(heavenBeadListener);

			earthBead = new EarthBead(rodWidth, translation, backgroundColour, beadColour);
			ActionListener earthBeadListener = new Listener();
			earthBead.addActionListener(earthBeadListener);

			numberBox = new NumberBox(rodWidth, rodWidth / 2, isValueDisplayed, 0);

			setLayout(null);
			add(heavenBead);
			add(earthBead);
			add(numberBox);

			int componentWidth = rodWidth;

			Dimension heavenBeadDimension = heavenBead.getPreferredSize();
			int heavenBeadHeight = heavenBeadDimension.height;

			Dimension earthBeadDimension = earthBead.getPreferredSize();
			int earthBeadHeight = earthBeadDimension.height;
			int earthBeadY = heavenBeadHeight + beamHeight;

			Dimension numberBoxDimension = numberBox.getPreferredSize();
			int numberBoxHeight = numberBoxDimension.height;
			int numberBoxY = heavenBeadHeight + beamHeight + earthBeadHeight;

			heavenBead.setBounds(0, 0, componentWidth, heavenBeadHeight);
			earthBead.setBounds(0, earthBeadY, componentWidth, earthBeadHeight);
			numberBox.setBounds(0, numberBoxY, componentWidth, numberBoxHeight);

			rodHeight = heavenBeadHeight + beamHeight + earthBeadHeight + numberBoxHeight;
		}
		catch (IllegalArgumentException ex)
		{
			ex.printStackTrace();
		}
	}

	public int getValue()
	{
		return value;
	}

	public void setValue(int value) throws IllegalArgumentException
	{
		if (value > 9 || value < 0)
			throw new IllegalArgumentException("The value of the rod needs to be superior or equal to 0 and inferior to 10: "
					+ value);

		else
		{
			try
			{
				this.value = value;
				numberBox.setValue(value);

				if (value >= 5)
				{
					heavenBead.setValue(5);
					earthBead.setValue(value - 5);
				}
				else
				{
					heavenBead.setValue(0);
					earthBead.setValue(value);
				}
			}
			catch (IllegalArgumentException ex)
			{
				ex.printStackTrace();
			}
		}
	}

	public void setBackgroundColour(Color backgroundColour)
	{
		heavenBead.setBackgroundColour(backgroundColour);
		earthBead.setBackgroundColour(backgroundColour);
	}

	public void setBeadColour(Color beadColour)
	{
		heavenBead.setBeadColour(beadColour);
		earthBead.setBeadColour(beadColour);
	}

	public boolean getIsValueDisplayed()
	{
		return numberBox.getIsValueDisplayed();
	}

	public void setIsValueDisplayed(boolean isValueDisplayed)
	{
		numberBox.setIsValueDisplayed(isValueDisplayed);
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
		return new Dimension(rodWidth, rodHeight);
	}

	private class Listener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			value = heavenBead.getValue() + earthBead.getValue();
			numberBox.setValue(value);
			processEvent(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
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
}