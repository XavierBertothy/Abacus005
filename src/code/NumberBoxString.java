package code;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

/**
 * This component displays or not some text if its isValueDisplayed attribute
 * is true.
 */
public class NumberBoxString extends JPanel
{

	public final static Font VALUEFONT = new Font("Arial", Font.PLAIN, 15);

	private boolean isValueDisplayed;

	private int boxWidth;
	private int boxHeight;

	private String value;

	public NumberBoxString(int boxWidth, int boxHeight, boolean isValueDisplayed, String value)
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

	public String getValue()
	{
		return value;
	}

	public void setValue(String value)
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
			g.drawString(value, boxWidth / 2, boxHeight / 2);
		}
	}
}
