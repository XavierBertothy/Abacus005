package code;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * This class creates a dialog JPanel allowing to select one colour for the
 * beads and another ofr the background of the abacus. The user has the option
 * to save the colours as the default one so that next time the application is
 * open these colour will be used.
 * @see Abacus class.
 */

public class AbacusParameterDialog extends JDialog
{

	public final static int COLOURNUMBER = 3;

	private boolean isSavedAsParameter = false;

	private int backgroundColourChoice;
	private int beadColourChoice;

	private JButton okButton;
	private JButton cancelButton;

	private JCheckBox saveAsParameterButton;

	private JComboBox backgroundColourButton;
	private JComboBox beadColourButton;

	/**
	 * Builds a dialog box to choose the colour of an abacus.
	 * @param backgroundColourChoice integer which is the colour position in an
	 *        array of colours
	 * @param beadColourChoice integer which is the colour position in an array of
	 *        colours
	 */
	public AbacusParameterDialog(int backgroundColourChoice, int beadColourChoice)
	{
		this(null, true, backgroundColourChoice, beadColourChoice);
	}

	/**
	 * Builds a dialog box to choose the colour of an abacus.
	 * @param parent Frame in which the dialog box is inserted
	 * @param modal Boolean
	 * @param backgroundColourChoice integer which is the colour position in an
	 *        array of colours in the Abacus class
	 * @param beadColourChoice integer which is the colour position in an array of
	 *        colours in the Abacus class
	 */
	public AbacusParameterDialog(java.awt.Frame parent, Boolean modal, int backgroundColour,
			int beadColour)
	{
		super(parent, modal);
		setTitle("Choose abacus parameters");
		backgroundColourChoice = backgroundColour;
		beadColourChoice = beadColour;

		Color[] colourArray = new Color[COLOURNUMBER];

		Icon[] backgroundColourArray = new Icon[Abacus.BACKGROUNDCOLOUR.length];
		for (int x = 0; x < Abacus.BACKGROUNDCOLOUR.length; x++)
		{
			for (int y = 0; y < colourArray.length; y++)
				colourArray[y] = Abacus.BACKGROUNDCOLOUR[x][y];

			backgroundColourArray[x] = new ColouredRectangle(colourArray);
		}

		Icon[] beadColourArray = new Icon[Abacus.BEADCOLOUR.length];
		for (int x = 0; x < Abacus.BEADCOLOUR.length; x++)
		{
			for (int y = 0; y < colourArray.length; y++)
				colourArray[y] = Abacus.BEADCOLOUR[x];

			beadColourArray[x] = new ColouredRectangle(colourArray);
		}

		backgroundColourButton = new JComboBox(backgroundColourArray);
		backgroundColourButton.setSelectedIndex(backgroundColourChoice);
		backgroundColourButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				backgroundColourChoice = backgroundColourButton.getSelectedIndex();
			}
		});

		beadColourButton = new JComboBox(beadColourArray);
		beadColourButton.setSelectedIndex(beadColourChoice);
		beadColourButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				beadColourChoice = beadColourButton.getSelectedIndex();
			}
		});

		saveAsParameterButton = new JCheckBox("Save as your parameters", false);
		saveAsParameterButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (saveAsParameterButton.isSelected())
					isSavedAsParameter = true;
			}
		});

		JPanel parameterPanel = new JPanel();
		parameterPanel.setLayout(new GridLayout(5, 0));
		parameterPanel.add(new JLabel("Background colour:"));
		parameterPanel.add(backgroundColourButton);
		parameterPanel.add(new JLabel("Bead colour:"));
		parameterPanel.add(beadColourButton);
		parameterPanel.add(saveAsParameterButton);

		JPanel intermediaryParameterPanel = new JPanel();
		intermediaryParameterPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		intermediaryParameterPanel.add(parameterPanel);

		okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				setVisible(false);
			}
		});

		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				backgroundColourChoice = -1;
				beadColourChoice = -1;
				setVisible(false);
			}
		});

		JPanel okCancelPanel = new JPanel();
		okCancelPanel.add(okButton);
		okCancelPanel.add(cancelButton);

		add(intermediaryParameterPanel, BorderLayout.CENTER);
		add(okCancelPanel, BorderLayout.SOUTH);

	}

	public Dimension getPreferredSize()
	{
		return new java.awt.Dimension(200, 200);
	}

	public int getBackgroundColourChoice()
	{
		return backgroundColourChoice;
	}

	public int getBeadColourChoice()
	{
		return beadColourChoice;
	}

	public boolean getIsSavedAsParameter()
	{
		return isSavedAsParameter;
	}

	public void setIsSavedAsParameter(boolean b)
	{
		isSavedAsParameter = b;
		saveAsParameterButton.setSelected(b);
	}

	protected class ColouredRectangle implements Icon
	{
		private Color[] colour;
		private final int STANDARDSIZE = 44;

		public ColouredRectangle(Color[] colour)
		{
			this.colour = new Color[colour.length];

			for (int x = 0; x < colour.length; x++)
				this.colour[x] = colour[x];

		}

		public void paintIcon(Component c, Graphics g, int x, int y)
		{
			Graphics2D g2d = (Graphics2D) g.create();

			for (int z = 0; z < colour.length; z++)
			{
				g2d.setColor(colour[z]);
				g2d.fillRect(z * STANDARDSIZE, 0, STANDARDSIZE, STANDARDSIZE);
			}

			g2d.dispose();
		}

		public int getIconWidth()
		{
			return STANDARDSIZE * colour.length;
		}

		public int getIconHeight()
		{
			return STANDARDSIZE;
		}
	}
}
