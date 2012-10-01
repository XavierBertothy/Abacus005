package testing;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Test;

import code.Beam;

public class BeamTesting
{

	@Test
	public void test1()// should throw an exception
	{
		int width = 40; // should be >= 44
		Beam beam = new Beam( width, 44, 8, new Color(255, 0, 0), new Color(125, 0, 0));	
	}
	
	@Test
	public void test2()
	{
		int width = 44; 
		Beam beam = new Beam( width, 44, 8, new Color(255, 0, 0), new Color(125, 0, 0));	
	}
	
	@Test
	public void test3()// should throw an exception
	{
		int height = 21; // should be >= 22
		Beam beam = new Beam( 44, height, 8, new Color(255, 0, 0), new Color(125, 0, 0));	
	}
	
	@Test
	public void test4()
	{
		int height = 22;
		Beam beam = new Beam( 44, height, 8, new Color(255, 0, 0), new Color(125, 0, 0));	
	}
	
	@Test
	public void test5()// should throw an exception
	{
		int exponent = 0; // should be > 0
		Beam beam = new Beam( 44, 44, exponent, new Color(255, 0, 0), new Color(125, 0, 0));	
	}
	
	@Test
	public void test6()
	{
		int exponent = 1; 
		Beam beam = new Beam( 44, 44, exponent, new Color(255, 0, 0), new Color(125, 0, 0));	
	}
	
	@Test
	public void test7()
	{
		Beam beam = new Beam( 44, 44, 8, new Color(255, 0, 0), new Color(125, 0, 0));
		Integer beamValue = beam.getExponent();
		assertEquals("The value is not the one expected", beamValue, (Integer)0);
	}
	
	@Test
	public void test8()// should throw an exception
	{
		Beam beam = new Beam( 44, 44, 8, new Color(255, 0, 0), new Color(125, 0, 0));
		beam.setExponent(-1);// should be >= 0
		Integer beamValue = beam.getExponent();
		assertEquals("The value is not the one expected", beamValue, (Integer)(-1));
	}

	@Test
	public void test9()// should throw an exception
	{
		Beam beam = new Beam( 44, 44, 8, new Color(255, 0, 0), new Color(125, 0, 0));
		beam.setExponent(8);// should be < 8 in this case see constructor above.
		Integer beamValue = beam.getExponent();
		assertEquals("The value is not the one expected", beamValue, (Integer)8);
	}
	
	@Test
	public void test10()
	{
		Beam beam = new Beam( 44, 44, 8, new Color(255, 0, 0), new Color(125, 0, 0));
		beam.setExponent(7);// 7 is the highest exponent we can get in this case.
		Integer beamValue = beam.getExponent();
		assertEquals("The value is not the one expected", beamValue, (Integer)7);
	}
	
	@Test
	public void test11()
	{
		Beam beam = new Beam( 44, 44, 8, new Color(255, 0, 0), new Color(125, 0, 0));
		beam.setExponent(0);// 0 is always the minimum exponent we can get.
		Integer beamValue = beam.getExponent();
		assertEquals("The value is not the one expected", beamValue, (Integer)0);
	}
	
	@Test
	public void test12()
	{
		Beam beam = new Beam( 44, 44, 8, new Color(255, 0, 0), new Color(125, 0, 0));
		beam.setBackgroundColour(new Color(0, 250, 0));
		Color backgroundColour = beam.getBackgroundColour();
		assertEquals("The background colour is not the one expected", backgroundColour, new Color(0, 250, 0));
	}
	
	@Test
	public void test13()// should not assert equals
	{
		Beam beam = new Beam( 44, 44, 8, new Color(255, 0, 0), new Color(125, 0, 0));
		beam.setBackgroundColour(new Color(0, 245, 0));
		Color backgroundColour = beam.getBackgroundColour();
		assertEquals("The background colour is not the one expected", backgroundColour, new Color(0, 250, 0));
	}
	
	@Test
	public void test14()
	{
		Beam beam = new Beam( 44, 44, 8, new Color(255, 0, 0), new Color(125, 0, 0));
		beam.setBeadColour(new Color(0, 250, 0));
		Color beadColour = beam.getBeadColour();
		assertEquals("The bead colour is not the one expected", beadColour, new Color(0, 250, 0));
	}
	
	@Test
	public void test15()// should not assert equals
	{
		Beam beam = new Beam( 44, 44, 8, new Color(255, 0, 0), new Color(125, 0, 0));
		beam.setBeadColour(new Color(0, 245, 0));
		Color beadColour = beam.getBeadColour();
		assertEquals("The bead colour is not the one expected", beadColour, new Color(0, 250, 0));
	}
}
