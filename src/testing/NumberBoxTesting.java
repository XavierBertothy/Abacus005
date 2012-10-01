package testing;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Test;

import code.NumberBox;

public class NumberBoxTesting
{
	
	@Test
	public void test1()
	{
		NumberBox numberBox = new NumberBox( 44, 44, true, 1);	
		assertEquals("The value is not the one expected", numberBox.getBoxHeight(), 44);	
	}
	
	@Test
	public void test2()
	{
		NumberBox numberBox = new NumberBox( 44, 44, true, 1);	
		assertEquals("The value is not the one expected", numberBox.getValue(), 1);
	}
	
	@Test
	public void test3()
	{
		NumberBox numberBox = new NumberBox( 44, 44, true, 1);		
		numberBox.setValue(9);
		assertEquals("The value is not the one expected", numberBox.getValue(), 9);
	}
	
	@Test
	public void test4()
	{
		NumberBox numberBox = new NumberBox( 44, 44, true, 1);		
		assertEquals("The state is not the one expected", numberBox.getIsValueDisplayed(), true);
	}
	
	@Test
	public void test5()
	{
		NumberBox numberBox = new NumberBox( 44, 44, true, 1);	
		numberBox.setIsValueDisplayed(false);
		assertEquals("The state is not the one expected", numberBox.getIsValueDisplayed(), false);
	}
}
