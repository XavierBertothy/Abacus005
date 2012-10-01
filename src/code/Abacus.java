package code;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JPanel;

/**
 * This class creates an abacus of 1:4 bead format. It can vary in size and
 * colour. An abacus is composed of heaven beads at the top and earth beads at
 * the bottom.The abacus has a value depending of the position of its earth
 * beads, heaven beads and bead on the beam. Its value can be displayed if
 * needed. The bead on the beam changes the position of the decimal point.This
 * abacus can have from 1 to 20 rods. The programmer can choose between 9
 * colours for the beads and their background. The background colour of the rods
 * is an array of 3 shades of the same colour to make the reading of each rod
 * more legible next to each another.
 * @see HeavebBead class, EarthBead class, Beam class, NumberBox class,
 *      NumberBoxString class.
 */

public class Abacus extends JPanel implements java.io.Serializable
{
	public final static Color BEADCOLOUR[] = new Color[] { Color.RED, Color.MAGENTA, Color.ORANGE,
			Color.YELLOW, Color.GREEN, Color.CYAN, Color.BLUE, new Color(120, 60, 120), Color.WHITE };
	public final static Color BACKGROUNDCOLOUR[][] = new Color[][] {
			{ new Color(245, 100, 60), new Color(245, 80, 55), new Color(245, 60, 50) }, 			// red
			{ new Color(230, 130, 135), new Color(230, 80, 135), new Color(230, 30, 135) },		// magenta
			{ new Color(240, 195, 80), new Color(240, 170, 55), new Color(245, 145, 30) },		// orange
			{ new Color(230, 230, 190), new Color(230, 230, 140), new Color(230, 230, 90) },	// yellow
			{ new Color(140, 220, 140), new Color(115, 220, 115), new Color(90, 220, 90) },		// green
			{ new Color(50, 200, 200), new Color(50, 175, 175), new Color(50, 150, 150) },		// cyan
			{ new Color(100, 100, 250), new Color(85, 85, 250), new Color(70, 70, 250) },			// blue
			{ new Color(170, 105, 175), new Color(160, 95, 165), new Color(150, 85, 155) },		// purple
			{ new Color(230, 230, 230), new Color(225, 225, 225), new Color(220, 220, 220) },	// white
	};

	private final int BACKGROUNDCOLOURNUMBER = 3;
	private final int MAXRODNUMBER = 20;

	private ArrayList<Rod> array;
	private ArrayList<ActionListener> actionListenerList;
	private Beam beam;

	private int rodNumber;
	private int translation;
	private int abacusHeight;
	private int totalAbacusHeight;
	private int abacusWidth;
	private int abacusMaximumWidth;
	private int backgroundColourChoice;
	private int beadColourChoice;

	private NumberBoxString abacusValueDisplay;

	private StringBuffer value;

	/**
	 * Shall not be used by programmer: it exists for the ActionListerner.
	 * mechanism. Instead use Abacus(int rodWidth, int translation, int rodNumber,
	 * int backgroundColourChoice,int beadColourChoice, boolean isValueDisplayed)
	 */
	Abacus()
	{}

	/**
	 * Builds an abacus.
	 * @param rodWidth integer setting the rod width and the bead diameter
	 * @param translation integer setting the gap between the beads and the beam
	 * @param rodNumber integer setting the number of rods
	 * @param backgroundColourChoice integer defining the colour of the background
	 *        in the array BACKGROUNDCOLOUR
	 * @param beadColourChoice integer defining the colour of the beads in an
	 *        array BEADCOLOUR
	 * @param isRodValueDisplayed boolean if true the value of each rod will be
	 *        displayed at the bottom of the abacus. If false the value will not
	 *        be displayed
	 * @param isAbacusValueDisplayed boolean, if true the value of the abacus will
	 *        be displayed at the very bottom of the abacus. If false the value
	 *        will not be displayed
	 * @exception if rodWidth is inferior to 44
	 * @exception if rodNumber is inferior to 1 or superior to 20
	 */
	public Abacus(int rodWidth, int translation, int rodNumber, int backgroundColourChoice,
			int beadColourChoice, boolean isRodValueDisplayed, boolean isAbacusValueDisplayed)
			throws IllegalArgumentException
	{
		if (rodNumber <= 0 || rodNumber > MAXRODNUMBER)
			throw new IllegalArgumentException(
					"The number of rods should be superior to 0 and inferior or equal to 20.\n"
							+ "rodNumber is " + rodNumber);
		else
		{
			try
			{
				value = new StringBuffer(rodNumber);
				for (int x = 0; x < rodNumber; x++)
					value.append('0');
				this.rodNumber = rodNumber;
				this.translation = translation;
				this.abacusHeight = 6 * rodWidth + rodWidth / 2 + 2 * translation;
				this.totalAbacusHeight = abacusHeight + rodWidth / 2;
				this.abacusWidth = rodNumber * rodWidth;
				this.abacusMaximumWidth = MAXRODNUMBER * rodWidth;
				this.backgroundColourChoice = backgroundColourChoice;
				this.beadColourChoice = beadColourChoice;
				array = new ArrayList<Rod>(MAXRODNUMBER);

				int beamHeight = rodWidth;
				JPanel rods = new JPanel();
				rods.setLayout(new GridLayout(1, rodNumber, 0, 0));
				ActionListener rodListener = new Listener();
				for (int x = 0; x < rodNumber; x++)
				{
					array.add(new Rod(rodWidth, translation, beamHeight,
							BACKGROUNDCOLOUR[backgroundColourChoice][x % BACKGROUNDCOLOURNUMBER],
							BEADCOLOUR[beadColourChoice], isRodValueDisplayed));
					array.get(x).addActionListener(rodListener);
					rods.add(array.get(x));
				}

				beam =
						new Beam(rodWidth, rodWidth, rodNumber,
								BACKGROUNDCOLOUR[backgroundColourChoice][BACKGROUNDCOLOURNUMBER - 1],
								BEADCOLOUR[beadColourChoice]);
				beam.addActionListener(rodListener);

				abacusValueDisplay =
						new NumberBoxString(abacusWidth, rodWidth / 2, isAbacusValueDisplayed, getValue());

				setLayout(null);
				add(beam);
				add(rods);
				add(abacusValueDisplay);

				int abacusComponentX = (abacusMaximumWidth - abacusWidth) / 2;

				rods.setBounds(abacusComponentX, 0, abacusWidth, abacusHeight);
				beam.setBounds(abacusComponentX, rodWidth + translation, abacusWidth, rodWidth);
				abacusValueDisplay.setBounds(abacusComponentX, abacusHeight, abacusWidth, rodWidth / 2);
			}
			catch (IllegalArgumentException ex)
			{
				ex.printStackTrace();
			}
		}
	}

	/**
	 * Builds an abacus.
	 * @param backgroundColourChoice integer defining the colour of the background
	 *        in the array BACKGROUNDCOLOUR
	 * @param beadColourChoice integer defining the colour of the beads in an
	 *        array BEADCOLOUR
	 */
	public Abacus(int backgroundColourChoice, int beadColourChoice)
	{
		this(44, 44, 8, backgroundColourChoice, beadColourChoice, true, true);
	}

	/**
	 * Returns a String which is the value of the abacus. It eliminates any
	 * leading zeros and zero at the end of the fractional part of a number for
	 * example for an abacus of 8 rods the value will be changed for 123.4 instead
	 * of returning 00123.40.
	 * @return String
	 */
	public String getValue()
	{
		StringBuffer returnedValue = new StringBuffer();
		String tempValue = getValueWithZeros();
		boolean leadingZero = true;

		for (int x = 0; x < tempValue.length(); x++)
		{
			if (tempValue.charAt(x) != '0')
			{
				leadingZero = false;
				returnedValue.append(tempValue.charAt(x));
			}
			else if (tempValue.charAt(x) == '0' && !leadingZero)
				returnedValue.append(tempValue.charAt(x));
		}

		while (returnedValue.charAt(returnedValue.length() - 1) == '0' && isDecimal(tempValue))
			returnedValue.deleteCharAt(returnedValue.length() - 1);

		if (returnedValue.charAt(returnedValue.length() - 1) == '.')
			returnedValue.deleteCharAt(returnedValue.length() - 1);

		if (returnedValue.length() == 0)
			returnedValue.append("0");

		return returnedValue.toString();
	}

	/**
	 * Returns a String which is the value of the abacus with its leading zeros
	 * and final zeros if it is the case.
	 * @return String
	 */
	public String getValueWithZeros()
	{
		StringBuffer returnedValue = new StringBuffer(rodNumber + 1);

		for (int x = 0; x < value.length(); x++)
			returnedValue.append(value.charAt(x));

		int exponent = beam.getExponent();

		returnedValue.append('.');

		for (int x = 0; x < exponent; x++)
		{
			char character = returnedValue.charAt(returnedValue.length() - 2 - x);
			returnedValue.setCharAt(returnedValue.length() - 1 - x, character);
		}

		returnedValue.setCharAt(returnedValue.length() - 1 - exponent, '.');

		return returnedValue.toString();
	}

	/**
	 * Displayed a value on the abacus from a number in a String form.
	 * @param newValue String which represents the new value of the abacus.
	 * @exception if newValue is neither an integer or a real number in the String
	 *            form
	 * @exception if newValue is an integer or a real number with more digits than
	 *            the number of rods in the abacus
	 */
	public void setValue(String newValue) throws IllegalArgumentException
	{
		if (newValue.isEmpty())
			throw new IllegalArgumentException("The new value is empty");

		else if ((newValue.length() > rodNumber && !isDecimal(newValue))
				|| (newValue.length() > rodNumber + 1 && isDecimal(newValue)))
			throw new IllegalArgumentException(
					"The new value can not be bigger than the number of rods.\n" + "number of rods: "
							+ rodNumber + ", newvalue: " + newValue + ", newvalue length: " + newValue.length());

		else if (!allNumbers(newValue))
			throw new IllegalArgumentException("The new value should only contain numbers: " + newValue);

		else
		{
			int exponent = 0;
			boolean afterdecimal = false;
			StringBuffer internalValue = new StringBuffer(rodNumber);

			for (int x = 0; x < newValue.length(); x++)
			{
				if (newValue.charAt(x) != '.')
				{
					internalValue.append(newValue.charAt(x));
					if (afterdecimal)
						exponent++;
				}
				else afterdecimal = true;
			}

			beam.setExponent(exponent);

			int difference = rodNumber - internalValue.length();

			for (int x = 0; x < difference; x++)
				value.setCharAt(x, '0');

			for (int x = difference; x < value.length(); x++)
				value.setCharAt(x, internalValue.charAt(x - difference));

			for (int x = value.length() - 1; x >= 0; x--)
			{
				int y = value.charAt(x) - '0';
				array.get(x).setValue(y);
			}

			abacusValueDisplay.setValue(getValue());
			processEvent(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
		}
	}

	/**
	 * Returns an integer which is the width of a rod in the abacus.
	 * @return integer
	 */
	public int getRodWidth()
	{
		return abacusWidth / rodNumber;
	}

	/**
	 * Returns an integer which is the space between the beads and the beam in the
	 * abacus.
	 * @return integer
	 */
	public int getTranslation()
	{
		return translation;
	}

	/**
	 * Returns an integer which is the number of rods in the abacus.
	 * @return integer
	 */
	public int getRodNumber()
	{
		return rodNumber;
	}

	/**
	 * Returns an integer which represents the present colour of the background in
	 * the array BACKGROUNCOLOUR.
	 * @return integer which is the position of the array of colours in the
	 *         general array of background colour
	 * @see public final static Color BACKGROUNDCOLOUR[][]
	 */
	public int getBackgroundColour()
	{
		return backgroundColourChoice;
	}

	/**
	 * Sets the colour for the background. Each colour choice is composed of three
	 * shades of the same colour.
	 * @param backgroundColourChoice integer which is the position of the array of
	 *        colours in the general array of background colours
	 * @see public final static Color BACKGROUNDCOLOUR[][]
	 */
	public void setBackgroundColour(int backgroundColourChoice) throws IllegalArgumentException
	{
		if (backgroundColourChoice < 0 || backgroundColourChoice >= BACKGROUNDCOLOUR.length)
			throw new IllegalArgumentException(
					"The colour choice is not in the range. The range is from 0 and "
							+ BACKGROUNDCOLOUR.length + " both included. The choice is number"
							+ backgroundColourChoice);
		else
		{
			this.backgroundColourChoice = backgroundColourChoice;

			for (int x = 0; x < rodNumber; x++)
				array.get(x).setBackgroundColour(BACKGROUNDCOLOUR[backgroundColourChoice][x % 3]);

			beam.setBackgroundColour(BACKGROUNDCOLOUR[backgroundColourChoice][BACKGROUNDCOLOURNUMBER - 1]);
		}
	}

	/**
	 * Returns an integer which represents the present colour of the beads in the
	 * array BEADCOLOUR.
	 * @return integer which is the position of the colour in the array of bead
	 *         colours
	 * @see public final static Color BEADCOLOUR[]
	 */
	public int getBeadColour()
	{
		return beadColourChoice;
	}

	/**
	 * Sets the array of the colours for the background of the rods. Each array is
	 * composed of three shades of the same colour
	 * @param beadColourChoice integer which is the position of the array of
	 *        colours in the general array of background colours
	 * @see public final static Color BEADCOLOUR[]
	 */
	public void setBeadColour(int beadColourChoice) throws IllegalArgumentException
	{
		if (beadColourChoice < 0 || beadColourChoice >= BEADCOLOUR.length)
			throw new IllegalArgumentException(
					"The colour choice is not in the range. The range is from 0 and " + BEADCOLOUR.length
							+ " both included. The choice is number" + beadColourChoice);
		else
		{
			this.beadColourChoice = beadColourChoice;

			for (int x = 0; x < rodNumber; x++)
				array.get(x).setBeadColour(BEADCOLOUR[beadColourChoice]);

			beam.setBeadColour(BEADCOLOUR[beadColourChoice]);
		}
	}

	/**
	 * Returns a boolean which tells if the value of the rods is displayed. If
	 * true the values will appear at the bottom of the abacus; if false the
	 * program will not display the values.
	 * @return boolean
	 */
	public boolean getIsRodValueDisplayed()
	{
		return array.get(0).getIsValueDisplayed();
	}

	/**
	 * Displays the value of the rods at the bottom of the abacus.
	 * @param isRodValueDisplayed boolean, if true the value of each rod will be
	 *        displayed at the bottom of the abacus. If false the value will not
	 *        be displayed
	 */
	public void setIsRodValueDisplayed(boolean isRodValueDisplayed)
	{
		for (int x = 0; x < rodNumber; x++)
			array.get(x).setIsValueDisplayed(isRodValueDisplayed);
	}

	/**
	 * Returns a boolean which tells if the value of the abacus is displayed. If
	 * true the value will appear at the bottom; if false the program will not
	 * display the value.
	 * @return boolean
	 */
	public boolean getIsAbacusValueDisplayed()
	{
		return abacusValueDisplay.getIsValueDisplayed();
	}

	/**
	 * Displays the value of the abacus at the bottom.
	 * @param isAbacusValueDisplayed boolean, if true the value of the abacus will
	 *        be displayed at the bottom. If false the value will not be displayed
	 */
	public void setIsAbacusValueDisplayed(boolean isAbacusValueDisplayed)
	{
		abacusValueDisplay.setIsValueDisplayed(isAbacusValueDisplayed);
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

	/**
	 * Returns a Dimension object which will suit the abacus in size. Normally the
	 * width will be the number of rods times the rod width and the height the six
	 * times the bead diameter plus two times the translation. To clarify the rod
	 * width and the bead diameter are always of the same dimension. Also the
	 * height of an abacus or a rod is composed in the top down order of one
	 * heaven bead, a space (translation), a beam (1/2 bead height), space
	 * (translation), four earth beads and a space for the rod value and the
	 * abacus value to be displayed (one bead height)
	 * @return Dimension
	 */
	public Dimension getPreferredSize()
	{
		return new Dimension(abacusMaximumWidth, totalAbacusHeight);
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

	private boolean allNumbers(String value)
	{
		boolean uniqueDot = true;
		for (int x = 0; x < value.length(); x++)
		{
			if (value.charAt(x) != '.' && value.charAt(x) < '0' || value.charAt(x) > '9')
				return false;

			else if (value.charAt(x) == '.' && uniqueDot)
				uniqueDot = false;

			else if (value.charAt(x) == '.' && !uniqueDot)
				return false;
		}
		return true;
	}

	private boolean isDecimal(String value)
	{
		for (int x = 0; x < value.length(); x++)
			if (value.charAt(x) == '.')
				return true;

		return false;
	}

	private class Listener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			for (int x = 0; x < rodNumber; x++)
				value.setCharAt(x, ((char) ('0' + (array.get(x).getValue()))));

			abacusValueDisplay.setValue(getValue());
			processEvent(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
		}
	}

}