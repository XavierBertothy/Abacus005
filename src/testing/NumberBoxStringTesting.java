package testing;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Test;

import code.NumberBoxString;

public class NumberBoxStringTesting
{
	
	@Test
	public void test1()
	{
		NumberBoxString numberBox = new NumberBoxString( 44, 44, true, "1234567");	
		assertEquals("The value is not the one expected", numberBox.getBoxHeight(), 44);	
	}
	
	@Test
	public void test2()
	{
		NumberBoxString numberBox = new NumberBoxString( 44, 44, true, "1234567");	
		assertEquals("The value is not the one expected", numberBox.getValue(), "1234567");
	}
	
	@Test
	public void test3()
	{
		NumberBoxString numberBox = new NumberBoxString( 44, 44, true, "1234567");		
		numberBox.setValue("666");
		assertEquals("The value is not the one expected", numberBox.getValue(), "666");
	}
	
	@Test
	public void test4()
	{
		NumberBoxString numberBox = new NumberBoxString( 44, 44, true, "1234567");		
		assertEquals("The state is not the one expected", numberBox.getIsValueDisplayed(), true);
	}
	
	@Test
	public void test5()
	{
		NumberBoxString numberBox = new NumberBoxString( 44, 44, true, "1234567");	
		numberBox.setIsValueDisplayed(false);
		assertEquals("The state is not the one expected", numberBox.getIsValueDisplayed(), false);
	}
}
