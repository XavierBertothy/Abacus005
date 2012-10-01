package testing;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import code.AbacusParameterDialog;


public class AbacusParameterDialogTesting
{

	@Test
	public void test1()
	{
		AbacusParameterDialog dialog = new AbacusParameterDialog (0, 1);
		assertEquals("The parameter is not the one expected", dialog.getBackgroundColourChoice(), 0);
	}
	
	@Test
	public void test2()
	{
		AbacusParameterDialog dialog = new AbacusParameterDialog (0, 1);
		assertEquals("The parameter is not the one expected", dialog.getBeadColourChoice(), 1);
	}
	
	@Test
	public void test3()
	{
		AbacusParameterDialog dialog = new AbacusParameterDialog (0, 1);
		assertEquals("The parameter is not the one expected", dialog.getIsSavedAsParameter(), false);
	}
	
	@Test
	public void test4() 
	{
		AbacusParameterDialog dialog = new AbacusParameterDialog (0, 1); 
		dialog.setSize(200, 300);
		dialog.setVisible(true);// the user will type cancel
		assertEquals("The parameter is not the one expected", dialog.getBackgroundColourChoice(), -1);
	}
	
	@Test
	public void test5() 
	{
		AbacusParameterDialog dialog = new AbacusParameterDialog (0, 1); 
		dialog.setSize(200, 300);
		dialog.setVisible(true);// the user will type cancel
		assertEquals("The parameter is not the one expected", dialog.getBeadColourChoice(), -1);
	}
	
	@Test
	public void test6() 
	{
		AbacusParameterDialog dialog = new AbacusParameterDialog (0, 1); 
		dialog.setIsSavedAsParameter(true);
		assertEquals("The parameter is not the one expected", dialog.getIsSavedAsParameter(), true);
	}
	

	@Test
	public void test7() 
	{
		AbacusParameterDialog dialog = new AbacusParameterDialog (0, 1); 
		dialog.setSize(200, 300);
		dialog.setVisible(true);// the user will select the third background colour and press OK
		assertEquals("The parameter is not the one expected", dialog.getBackgroundColourChoice(), 2);
	}
	
	@Test
	public void test8() 
	{
		AbacusParameterDialog dialog = new AbacusParameterDialog (0, 1); 
		dialog.setSize(200, 300);
		dialog.setVisible(true);// the user will Select the third bead colour and press OK
		assertEquals("The parameter is not the one expected", dialog.getBeadColourChoice(), 2);
	}
	
}
