package code;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

import javax.swing.JComponent;
import javax.swing.JFrame;

public class Circles extends JComponent
{

	public void paintComponent(Graphics g)
	{
		g.setColor(new Color(200, 200, 200));
		g.drawOval(5, 5, 50, 50);
		g.fillOval(55, 5, 50, 50);

		Ellipse2D.Double circle1 = new Ellipse2D.Double(5, 105, 50, 50);
		Ellipse2D.Float circle2 = new Ellipse2D.Float(55, 105, 50, 50);
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(new Color(150, 150, 150));
		g2.draw(circle1);
		g2.fill(circle2);
	}

	public static void main(String[] args)
	{
		JFrame frame = new JFrame();

		Circles circles = new Circles();

		frame.add(circles);

		frame.setVisible(true);
		frame.setSize(115, 215);
		frame.setTitle("How to draw circles");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
