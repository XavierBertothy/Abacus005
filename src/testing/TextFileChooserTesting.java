package testing;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import code.TextFileChooser;

public class TextFileChooserTesting
{
	
	@Test
	public void test1() 
	{
		String textFileName = TextFileChooser.saveFile(); // the user will press cancel
		assertEquals("The value is not the one expected", textFileName, "?");
	}
	
	@Test
	public void test2()// should not assert and show the absolute path against the file name
	{
		String textFileName = TextFileChooser.saveFile(); // the user will type the file "exerciseWhichDoesNotExist"
		assertEquals("The value is not the one expected", textFileName, "DoesNotExist.txt");
	}
	
	@Test
	public void test3() 
	{
		String textFileName = TextFileChooser.openFile(); // the user will press cancel
		assertEquals("The value is not the one expected", textFileName, "?");
	}
	
	@Test
	public void test4()// should not assert and show the absolute path against the file name
	{
		String textFileName = TextFileChooser.openFile(); // the user will type the file "exercise1"
		assertEquals("The value is not the one expected", textFileName, "exercise1.txt");
	}
	
}
