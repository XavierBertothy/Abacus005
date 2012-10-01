package code;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

/**
 * This component displays or not an integer if its isValueDisplayed attribute
 * is true.
 */

public class NumberBox extends JPanel
{

	public final static Font VALUEFONT = new Font("Arial", Font.PLAIN, 15);

	private boolean isValueDisplayed;

	private int boxWidth;
	private int boxHeight;
	private int value;

	public NumberBox(int boxWidth, int boxHeight, boolean isValueDisplayed, int value)
	{
		this.boxWidth = boxWidth;
		this.boxHeight = boxHeight;
		this.isValueDisplayed = isValueDisplayed;
		this.value = value;
	}

	public Dimension getPreferredSize()
	{
		return new Dimension(boxWidth, boxHeight);
	}

	public int getBoxHeight()
	{
		return boxHeight;
	}

	public int getValue()
	{
		return value;
	}

	public void setValue(int value)
	{
		this.value = value;
		repaint();
	}

	public boolean getIsValueDisplayed()
	{
		return isValueDisplayed;
	}

	public void setIsValueDisplayed(boolean isValueDisplayed)
	{
		this.isValueDisplayed = isValueDisplayed;
		repaint();
	}

	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		if (isValueDisplayed)
		{
			g.setFont(VALUEFONT);
			g.drawString(Integer.toString(value), boxWidth / 3, (int) (boxHeight / 1.5));
		}
	}

}
