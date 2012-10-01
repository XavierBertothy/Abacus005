package code;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;

public class CirclesAwt extends JFrame
{
	Canvas c;
	Graphics g;
	Circle ci1;
	Circle ci2;

	public CirclesAwt()
	{
		c = new Canvas();
		c.setSize(115, 215);
		add(c);
		g = c.getGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, 115, 215);
		g.setColor(Color.DARK_GRAY);
		g.drawOval(5, 5, 50, 50);
		g.fillOval(55, 5, 50, 50);
		/*
		 * g.setColor(new Color (150, 150, 150));
		 * ci1 = new Circle(5, 55, 50, 50, g);
		 * ci1.draw();
		 * ci2 = new Circle(55, 55, 50, 50, g);
		 * ci2.fill();
		 */
	}

	public class Circle
	{
		protected int hx, hy, hwidth, hheight;
		protected Graphics hg;

		public Circle(int hx, int hy, int hwidth, int hheight, Graphics hg)
		{
			this.hx = hx;
			this.hy = hy;
			this.hwidth = hwidth;
			this.hheight = hheight;
			this.hg = hg;
		}

		public void draw()
		{
			hg.drawOval(hx, hy, hwidth, hheight);
		}

		public void fill()
		{
			hg.fillOval(hx, hy, hwidth, hheight);
		}
	}

	public static void main(String[] args)
	{
		CirclesAwt circle = new CirclesAwt();
		circle.setVisible(true);
		circle.setSize(115, 215);
		circle.setTitle("How to draw circles");
		circle.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
