package testing;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Test;

import code.Rod;

public class RodTesting
{

	@Test
	public void test1()// should throw an exception
	{
			int width = 40; // should be >= 44
			Rod rod = new Rod(width, 44, 44, new Color(255, 0, 0), new Color(125, 0, 0), true);	
	}
	
	@Test
	public void test2()
	{
			int width = 44; 
			Rod rod = new Rod(width, 44, 44, new Color(255, 0, 0), new Color(125, 0, 0), true);	
	}
	
	@Test
	public void test3()// should throw an exception
	{
			int translation = -1; // should be >= 0
			Rod rod = new Rod(44, translation, 44, new Color(255, 0, 0), new Color(125, 0, 0), true);	
	}
	
	@Test
	public void test4()
	{
			int translation = 0; // should be >= 0
			Rod rod = new Rod(44, translation, 44, new Color(255, 0, 0), new Color(125, 0, 0), true);
	}
	
	@Test
	public void test5()
	{
			int translation = 44; // should be >= 0
			Rod rod = new Rod(44, translation, 44, new Color(255, 0, 0), new Color(125, 0, 0), true);
	}
	
	@Test
	public void test6()// should not assert equals
	{
			Rod rod = new Rod(44, 44, 44, new Color(255, 0, 0), new Color(125, 0, 0), true);
			Integer rodValue = rod.getValue();
			assertEquals("The value is not the one expected", rod, (Integer)1);
	}
	
	@Test
	public void test7()
	{
		  Rod rod = new Rod(44, 44, 44, new Color(255, 0, 0), new Color(125, 0, 0), true);
			Integer rodValue = rod.getValue();
			assertEquals("The value is not the one expected", rodValue, (Integer)0);
	}

	@Test
	public void test8()// should throw an exception
	{
			Rod rod = new Rod(44, 44, 44, new Color(255, 0, 0), new Color(125, 0, 0), true);
			rod.setValue(10);
			Integer rodValue = rod.getValue();
			assertEquals("The value is not the one expected", rodValue, (Integer)10);
	}
	
	@Test
	public void test9()// should throw an exception
	{
			Rod rod = new Rod(44, 44, 44, new Color(255, 0, 0), new Color(125, 0, 0), true);
			rod.setValue(-1);
			Integer rodValue = rod.getValue();
			assertEquals("The value is not the one expected", rodValue, (Integer)(-1));
	}
	
	@Test
	public void test10()
	{
			Rod rod = new Rod(44, 44, 44, new Color(255, 0, 0), new Color(125, 0, 0), true);
			rod.setValue(9);
			Integer rodValue = rod.getValue();
			assertEquals("The value is not the one expected", rodValue, (Integer)9);
	}

	@Test
	public void test11()
	{
			Rod rod = new Rod(44, 44, 44, new Color(255, 0, 0), new Color(125, 0, 0), true);
			rod.setValue(0);
			Integer rodValue = rod.getValue();
			assertEquals("The value is not the one expected", rodValue, (Integer)0);
	}
	
}
