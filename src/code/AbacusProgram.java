package code;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class AbacusProgram
{

	public static void main(String[] args)
	{
		try
		{
			JPanel abacus = new AbacusInterface();

			JFrame frame = new JFrame();
			frame.add(abacus);
			frame.setTitle("Abacus");
			frame.setSize(800, 610);
			frame.setLocationRelativeTo(null);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setVisible(true);
		}
		catch (IllegalArgumentException ex)
		{
			ex.printStackTrace();
		}
		
	}

}
