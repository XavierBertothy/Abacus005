package testing;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import code.Abacus;

public class AbacusTesting
{
	
	@Test
	public void test1()// should throw an exception 
	{
		int rodNumber = 0;// should be >= 0 and < 21
		Abacus abacus = new Abacus(44, 44, rodNumber, 0, 1, true, true);
	}
	
	@Test
	public void test2()// should throw an exception 
	{
		int rodNumber = 21; // should be >= 0 and < 21
		Abacus abacus = new Abacus(44, 44, rodNumber, 0, 1, true, true);
	}
	
	@Test
	public void test3()
	{
		int rodNumber = 1; 
		Abacus abacus = new Abacus(44, 44, rodNumber, 0, 1, true, true);
	}
	
	@Test
	public void test4()
	{
		int rodNumber = 20;
		Abacus abacus = new Abacus(44, 44, rodNumber, 0, 1, true, true);
	}
	
	@Test
	public void test5()
	{
		Abacus abacus = new Abacus(44, 44, 8, 0, 1, true, true);
		assertEquals("The value is not the one expected", abacus.getValue(), "0");
	}
	
	@Test
	public void test6()// should not assert equals
	{
		Abacus abacus = new Abacus(44, 44, 8, 0, 1, true, true);
		assertEquals("The value is not the one expected", abacus.getValue(), "1");
	}

	@Test
	public void test7()
	{
		Abacus abacus = new Abacus(44, 44, 8, 0, 1, true, true);
		assertEquals("The value is not the one expected", abacus.getValueWithZeros(), "00000000.");
	}
	
	@Test
	public void test8()// should not assert equals
	{
		Abacus abacus = new Abacus(44, 44, 8, 0, 1, true, true);
		assertEquals("The value is not the one expected", abacus.getValueWithZeros(), "0");
	}

	@Test
	public void test9()
	{
		Abacus abacus = new Abacus(44, 44, 8, 0, 1, true, true);
		abacus.setValue("1");
		assertEquals("The value is not the one expected", abacus.getValueWithZeros(), "00000001.");
	}

	@Test
	public void test10()
	{
		Abacus abacus = new Abacus(44, 44, 8, 0, 1, true, true);
		abacus.setValue("1.00");
		assertEquals("The value is not the one expected", abacus.getValueWithZeros(), "000001.00");
	}
	
	@Test
	public void test11()
	{
		Abacus abacus = new Abacus(44, 44, 8, 0, 1, true, true);
		abacus.setValue("12345678.");
		assertEquals("The value is not the one expected", abacus.getValueWithZeros(), "12345678.");
	}
	
	@Test
	public void test12()
	{
		Abacus abacus = new Abacus(44, 44, 8, 0, 1, true, true);
		abacus.setValue("1234.5678");
		assertEquals("The value is not the one expected", abacus.getValueWithZeros(), "1234.5678");
	}
	
	@Test
	public void test13()// should throw an exception
	{
		Abacus abacus = new Abacus(44, 44, 8, 0, 1, true, true);
		abacus.setValue("123456789."); // the number of characters should be > 8 in this case
		assertEquals("The value is not the one expected", abacus.getValueWithZeros(), "12345678.");
	}
	
	@Test
	public void test14()// should throw an exception
	{
		Abacus abacus = new Abacus(44, 44, 8, 0, 1, true, true);
		abacus.setValue("");// the value can not be empty
		assertEquals("The value is not the one expected", abacus.getValueWithZeros(), "");
	}
	
	@Test
	public void test15()// should throw an exception
	{
		Abacus abacus = new Abacus(44, 44, 8, 0, 1, true, true);
		abacus.setValue("three");// the value needs be with digits
		assertEquals("The value is not the one expected", abacus.getValueWithZeros(), "three");
	}
	
	@Test
	public void test16()
	{
		Abacus abacus = new Abacus(44, 44, 8, 0, 1, true, true);
		int rodWidth = abacus.getRodWidth();
		assertEquals("The value is not the one expected", rodWidth, 44);
	}
	
	@Test
	public void test17()
	{
		Abacus abacus = new Abacus(44, 44, 8, 0, 1, true, true);
		int translation = abacus.getTranslation();
		assertEquals("The value is not the one expected", translation, 44);
	}
	
	@Test
	public void test18()
	{
		Abacus abacus = new Abacus(44, 44, 8, 0, 1, true, true);
		int rodNumber = abacus.getRodNumber();
		assertEquals("The value is not the one expected", rodNumber, 8);
	}
	
	@Test
	public void test19()
	{
		Abacus abacus = new Abacus(44, 44, 8, 0, 1, true, true);
		int backgroundColourChoice = abacus.getBackgroundColour();
		assertEquals("The value is not the one expected", backgroundColourChoice, 0);
	}
	
	@Test
	public void test20()
	{
		Abacus abacus = new Abacus(44, 44, 8, 0, 1, true, true);
		abacus.setBackgroundColour(5);
		int backgroundColourChoice = abacus.getBackgroundColour();
		assertEquals("The value is not the one expected", backgroundColourChoice, 5);
	}
	
	@Test
	public void test21()// should throw an exception
	{
		Abacus abacus = new Abacus(44, 44, 8, 0, 1, true, true);
		abacus.setBackgroundColour(-1);// should be > 0 and < 9
		int backgroundColourChoice = abacus.getBackgroundColour();
		assertEquals("The value is not the one expected", backgroundColourChoice, -1);
	}
	
	@Test
	public void test22()// should throw an exception
	{
		Abacus abacus = new Abacus(44, 44, 8, 0, 1, true, true);
		abacus.setBackgroundColour(9);// should be > 0 and < 9
		int backgroundColourChoice = abacus.getBackgroundColour();
		assertEquals("The value is not the one expected", backgroundColourChoice, 9);
	}
	
	@Test
	public void test23()
	{
		Abacus abacus = new Abacus(44, 44, 8, 0, 1, true, true);
		abacus.setBackgroundColour(0);
		int backgroundColourChoice = abacus.getBackgroundColour();
		assertEquals("The value is not the one expected", backgroundColourChoice, 0);
	}
	
	@Test
	public void test24()
	{
		Abacus abacus = new Abacus(44, 44, 8, 0, 1, true, true);
		abacus.setBackgroundColour(8);
		int backgroundColourChoice = abacus.getBackgroundColour();
		assertEquals("The value is not the one expected", backgroundColourChoice, 8);
	}
	
	@Test
	public void test25()
	{
		Abacus abacus = new Abacus(44, 44, 8, 0, 1, true, true);
		int beadColourChoice = abacus.getBeadColour();
		assertEquals("The value is not the one expected", beadColourChoice, 1);
	}
	
	@Test
	public void test26()
	{
		Abacus abacus = new Abacus(44, 44, 8, 0, 1, true, true);
		abacus.setBeadColour(5);
		int beadColourChoice = abacus.getBeadColour();
		assertEquals("The value is not the one expected", beadColourChoice, 5);
	}
	
	@Test
	public void test27()// should throw an exception
	{
		Abacus abacus = new Abacus(44, 44, 8, 0, 1, true, true);
		abacus.setBeadColour(-1);// should be > 0 and < 9
		int beadColourChoice = abacus.getBeadColour();
		assertEquals("The value is not the one expected", beadColourChoice, -1);
	}
	
	@Test
	public void test28()// should throw an exception
	{
		Abacus abacus = new Abacus(44, 44, 8, 0, 1, true, true);
		abacus.setBeadColour(9);// should be > 0 and < 9
		int beadColourChoice = abacus.getBeadColour();
		assertEquals("The value is not the one expected", beadColourChoice, 9);
	}
	
	@Test
	public void test29()
	{
		Abacus abacus = new Abacus(44, 44, 8, 0, 1, true, true);
		abacus.setBeadColour(0);
		int beadColourChoice = abacus.getBeadColour();
		assertEquals("The value is not the one expected", beadColourChoice, 0);
	}
	
	@Test
	public void test30()
	{
		Abacus abacus = new Abacus(44, 44, 8, 0, 1, true, true);
		abacus.setBeadColour(8);
		int beadColourChoice = abacus.getBeadColour();
		assertEquals("The value is not the one expected", beadColourChoice, 8);
	}
	
	@Test
	public void test31()
	{
		Abacus abacus = new Abacus(44, 44, 8, 0, 1, true, true);
		assertEquals("The value is not the one expected", abacus.getIsRodValueDisplayed(), true);
	}
	
	@Test
	public void test32()
	{
		Abacus abacus = new Abacus(44, 44, 8, 0, 1, true, true);
		abacus.setIsRodValueDisplayed(false);
		assertEquals("The value is not the one expected", abacus.getIsRodValueDisplayed(), false);
	}
	
	@Test
	public void test33()
	{
		Abacus abacus = new Abacus(44, 44, 8, 0, 1, true, true);
		assertEquals("The value is not the one expected", abacus.getIsAbacusValueDisplayed(), true);
	}
	
	@Test
	public void test34()
	{
		Abacus abacus = new Abacus(44, 44, 8, 0, 1, true, true);
		abacus.setIsAbacusValueDisplayed(false);
		assertEquals("The value is not the one expected", abacus.getIsAbacusValueDisplayed(), false);
	}
}
