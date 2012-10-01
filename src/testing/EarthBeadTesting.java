package testing;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Test;

import code.EarthBead;

public class EarthBeadTesting
{

	@Test
	public void test1()// should throw an exception
	{
			int beadDiameter = 40; // should be >= 44
			EarthBead bead = new EarthBead(beadDiameter, 44, new Color(255, 0, 0), new Color(125, 0, 0));	
	}
	
	@Test
	public void test2()
	{
			int beadDiameter = 44; 
			EarthBead bead = new EarthBead(beadDiameter, 44, new Color(255, 0, 0), new Color(125, 0, 0));
	}
	
	@Test
	public void test3()// should throw an exception
	{
			int translation = -1; // should be >= 0
			EarthBead bead = new EarthBead(44, translation, new Color(255, 0, 0), new Color(125, 0, 0));
	}
	
	@Test
	public void test4()
	{
			int translation = 0; // should be >= 0
			EarthBead bead = new EarthBead(44, translation, new Color(255, 0, 0), new Color(125, 0, 0));
	}
	
	@Test
	public void test5()
	{
			int translation = 44; // should be >= 0
			EarthBead bead = new EarthBead(44, translation, new Color(255, 0, 0), new Color(125, 0, 0));
	}
	
	@Test
	public void test6()// should not assert equals
	{
			EarthBead bead = new EarthBead(44, 44, new Color(255, 0, 0), new Color(125, 0, 0));
			Integer beadValue = bead.getValue();
			assertEquals("The value is not the one expected", beadValue, (Integer)1);
	}
	
	@Test
	public void test7()
	{
			EarthBead bead = new EarthBead(44, 44, new Color(255, 0, 0), new Color(125, 0, 0));
			Integer beadValue = bead.getValue();
			assertEquals("The value is not the one expected", beadValue, (Integer)0);
	}

	@Test
	public void test8()// should throw an exception
	{
			EarthBead bead = new EarthBead(44, 44, new Color(255, 0, 0), new Color(125, 0, 0));
			bead.setValue(5);
			Integer beadValue = bead.getValue();
			assertEquals("The value is not the one expected", beadValue, (Integer)5);
	}
	
	@Test
	public void test9()// should throw an exception
	{
			EarthBead bead = new EarthBead(44, 44, new Color(255, 0, 0), new Color(125, 0, 0));
			bead.setValue(-1);
			Integer beadValue = bead.getValue();
			assertEquals("The value is not the one expected", beadValue, (Integer)(-1));
	}
	
	@Test
	public void test10()
	{
			EarthBead bead = new EarthBead(44, 44, new Color(255, 0, 0), new Color(125, 0, 0));
			bead.setValue(1);
			Integer beadValue = bead.getValue();
			assertEquals("The value is not the one expected", beadValue, (Integer)1);
	}

	@Test
	public void test11()
	{
			EarthBead bead = new EarthBead(44, 44, new Color(255, 0, 0), new Color(125, 0, 0));
			bead.setValue(0);
			Integer beadValue = bead.getValue();
			assertEquals("The value is not the one expected", beadValue, (Integer)0);
	}
	
	@Test
	public void test12()
	{
			EarthBead bead = new EarthBead(44, 44, new Color(255, 0, 0), new Color(125, 0, 0));
			bead.setBackgroundColour(new Color(0, 250, 0));
			Color backgroundColour = bead.getBackgroundColour();
			assertEquals("The background colour is not the one expected", backgroundColour, new Color(0, 250, 0));
	}
	
	@Test
	public void test13()// should not assert equals
	{
			EarthBead bead = new EarthBead(44, 44, new Color(255, 0, 0), new Color(125, 0, 0));
			bead.setBackgroundColour(new Color(0, 245, 0));
			Color backgroundColour = bead.getBackgroundColour();
			assertEquals("The background colour is not the one expected", backgroundColour, new Color(0, 250, 0));
	}
	
	@Test
	public void test14()
	{
			EarthBead bead = new EarthBead(44, 44, new Color(255, 0, 0), new Color(125, 0, 0));
			bead.setBeadColour(new Color(0, 250, 0));
			Color beadColour = bead.getBeadColour();
			assertEquals("The bead colour is not the one expected", beadColour, new Color(0, 250, 0));
	}
	
	@Test
	public void test15()// should not assert equals
	{
			EarthBead bead = new EarthBead(44, 44, new Color(255, 0, 0), new Color(125, 0, 0));
			bead.setBeadColour(new Color(0, 245, 0));
			Color beadColour = bead.getBeadColour();
			assertEquals("The bead colour is not the one expected", beadColour, new Color(0, 250, 0));
	}
}
