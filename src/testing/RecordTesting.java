package testing;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import code.Record;

public class RecordTesting
{
	
	@Test
	public void test1() 
	{
		Record record = new Record();
		record.add("200", "0 2");
		record.add("300", "123");
		record.add("301", "5 + 6");
	
		assertEquals("The value is not the one expected", record.getInstructionCode(), "200");
		assertEquals("The value is not the one expected", record.getInstructionData(), "0 2");
		record.next();
		assertEquals("The value is not the one expected", record.getInstructionCode(), "300");
		assertEquals("The value is not the one expected", record.getInstructionData(), "123");
		record.next();
		assertEquals("The value is not the one expected", record.getInstructionCode(), "301");
		assertEquals("The value is not the one expected", record.getInstructionData(), "5 + 6");
	}
	
	@Test
	public void test2()// should throw an exception
	{
		Record record = new Record();
		String code = record.getInstructionCode();// record is empty
	}
	
	@Test
	public void test3()// should throw an exception
	{
		Record record = new Record();
		String data = record.getInstructionData();// record is empty
	}
	
	@Test
	public void test4()// should throw an exception
	{
		Record record = new Record();
		for (int x = 0; x < Record.INSTRUCTIONMAXIMUM + 1; x++) // x should be < Record.INSTRUCTIONMAXIMUM
			record.add("200", "0 2");
	}
	
	@Test
	public void test5() throws IllegalArgumentException, IOException
	{
		Record record = new Record();
		record.load("testingIOFile/completeFile.txt");
		
		record.next();
		record.changeData("11 * 11");

		assertEquals("The data is not the one expected", record.getInstructionData(), "11 * 11");
	}
	
	@Test
	public void test6() throws IllegalArgumentException, IOException// should throw an exception
	{
		Record record = new Record();
		record.load("testingIOFile/completeFile.txt");
		
		System.out.println("Should throw the exception now");
		record.previous();
	}
	
	@Test
	public void test7()// should throw an exception
	{
		Record record = new Record();
		record.previous();// empty file
	}
	
	@Test
	public void test8() throws IllegalArgumentException, IOException// should throw an exception
	{
		Record record = new Record();
		record.load("testingIOFile/completeFile.txt");
		
		record.next();
		record.next();
		
		System.out.println("Should throw the exception now");
		record.next();
	}
	
	@Test
	public void test9()// should throw an exception
	{
		Record record = new Record();
		record.next();// empty file
	}
	
	@Test
	public void test10()
	{
		Record record = new Record();
		record.add("200", "0 2");
		record.add("300", "123");
		record.add("301", "5 + 6");
		record.add("301", "3 - 2");
		record.add("301", "4 * 3");
		record.add("301", "6 / 3");
		
		record.next();
		record.next();// position is at the third instruction
		record.removeLastInstrunctions();// remove all instruction after the current instruction "301 5 + 6"
		
		assertEquals("The size is not the one expected", record.size(), 3);
	}
	
	@Test
	public void test11() throws IllegalArgumentException, IOException
	{
		Record record = new Record();
		String firstCode1, secondCode1, firstCode2, secondCode2;
		record.load("testingIOFile/completeFile.txt");
		firstCode1 = record.getInstructionCode();
		record.next();
		secondCode1 = record.getInstructionCode();
		record.save("testingIOFile/checkingFile.txt");
		record.clear();
		record.load("testingIOFile/checkingFile.txt");
		firstCode2 = record.getInstructionCode();
		record.next();
		secondCode2 = record.getInstructionCode();
		assertEquals("The instruction has not been saved correctly", firstCode1, firstCode2);
		assertEquals("The instruction has not been saved correctly", secondCode1, secondCode2);
	}
	
	@Test
	public void test12() throws IllegalArgumentException, IOException
	{
		Record record = new Record();
		record.load("testingIOFile/completeFile.txt");// the file has three instructions
		assertEquals("The size of the record is not the one expected", 3, record.size());
	}
	
	@Test
	public void test13() throws IllegalArgumentException, IOException// should throw an exception
	{
		Record record = new Record();
		record.load("testingIOFile/incompleteFile.txt");// incomplete file
	}
	
	@Test
	public void test14() throws IllegalArgumentException, IOException// should throw an exception
	{
		Record record = new Record();
		record.load("testingIOFile/nonExistingFile.txt");// non existing file
	}
	
	@Test
	public void test15() throws IllegalArgumentException, IOException// should throw an exception
	{
		Record record = new Record();
		record.load("testingIOFile/emptyFile.txt");// empty file
	}
	
	@Test
	public void test16()
	{
		Record record = new Record();
		record.add("200", "0 2");
		record.add("300", "123");
		record.add("301", "5 + 6");
		
		while(!record.isEmpty())
			record.eraseInstructionLine();// all instructions are erased
		
		assertEquals("The file is not empty", record.isEmpty(), true);
	}
	
	@Test
	public void test17()
	{
		Record record = new Record();
		record.add("200", "0 2");
		record.add("300", "123");
		record.add("301", "5 + 6");
		
		record.next();
		record.next();
		record.eraseInstructionLine();// the instruction 301 5 + 6 is erased
		
		record.setToStart();
		assertEquals("This is not the expected code", record.getInstructionCode(), "200");
		record.next();
		assertEquals("This is not the expected code", record.getInstructionCode(), "300");
		assertEquals("The position is not the one expected", record.isLastPosition(), true);
	}
	
	@Test
	public void test18()
	{
		Record record = new Record();
		record.add("200", "0 2");
		record.add("300", "123");
		record.add("301", "5 + 6");
		
		record.next();
		record.eraseInstructionLine();// the instruction 300 123 is erased
		
		record.setToStart();
		assertEquals("This is not the expected code", record.getInstructionCode(), "200");
		record.next();
		assertEquals("This is not the expected code", record.getInstructionCode(), "301");
	}
	
	@Test
	public void test19()
	{
		Record record = new Record();
		record.add("200", "0 2");
		record.add("300", "123");
		record.add("301", "5 + 6");
		
		record.eraseInstructionLine();// the instruction 200 0 2 is erased
		
		assertEquals("This is not the expected code", record.getInstructionCode(), "300");
	}

	@Test
	public void test20()// should throw an exception
	{
		Record record = new Record();
		record.eraseInstructionLine();// record is empty
	}

	@Test
	public void test21()
	{
		Record record = new Record();
		record.add("200", "0 2");
		record.add("300", "123");
		record.add("301", "5 + 6");
		
		record.clear();
		
		assertEquals("The file is not empty", record.isEmpty(), true);
	}
	
	@Test
	public void test22()// should throw an exception
	{
		Record record = new Record();
		record.clear();// record is empty
	}
	
	@Test
	public void test23()
	{
		Record record = new Record();
		
		for (int x = 0; x < Record.INSTRUCTIONMAXIMUM; x++)
			record.add("200", "0 2");
		
		for (int x = 0; x < 10; x++)
			record.next();
		
		assertEquals("The position is not the one expected", record.getPosition(), 10);
	}
	
	@Test
	public void test24()// should throw an exception
	{
		Record record = new Record();
		int position = record.getPosition();// record is empty
	}
	
	@Test
	public void test25()
	{
		Record record = new Record();
		record.add("200", "0 2");
		record.add("300", "123");
		record.add("301", "5 + 6");
		
		while (!record.isLastPosition())
			record.next();
		record.setToStart();
		
		assertEquals("The position is not the one expected", record.isFirstPosition(), true);
	}
	
	@Test
	public void test26()// should throw an exception
	{
		Record record = new Record();
		record.setToStart();// record is empty
	}
	
	@Test
	public void test27()
	{
		Record record = new Record();
		assertEquals("The record is not empty", record.isEmpty(), true);
	}
	
	@Test
	public void test28()// should not assert equals
	{
		Record record = new Record();
		record.add("200", "0 2");
		record.add("300", "123");
		record.add("301", "5 + 6");
		assertEquals("The record is not empty", record.isEmpty(), true);
	}
	
	@Test
	public void test29()
	{
		Record record = new Record();
		
		for (int x = 0; x < Record.INSTRUCTIONMAXIMUM; x++)
			record.add("200", "0 2");
		
		while (!record.isLastPosition())
			record.next();
		
		assertEquals("The position is not the one expected", record.isLastPosition(), true);
	}
	
	@Test
	public void test30()// should not assert equals
	{
		Record record = new Record();
		
		for (int x = 0; x < Record.INSTRUCTIONMAXIMUM; x++)
			record.add("200", "0 2");
		
		while (!record.isLastPosition())
			record.next();
		
		record.previous();// The record is just at the instruction before the last one
		
		assertEquals("The position is not the one expected", record.isLastPosition(), true);
	}
	
	@Test
	public void test31()
	{
		Record record = new Record();
		
		for (int x = 0; x < Record.INSTRUCTIONMAXIMUM; x++)
			record.add("200", "0 2");
		
		assertEquals("The position is not the one expected", record.isFirstPosition(), true);
	}
	
	@Test
	public void test32()// should not assert equals
	{
		Record record = new Record();
		
		for (int x = 0; x < Record.INSTRUCTIONMAXIMUM; x++)
			record.add("200", "0 2");
		
		record.next(); // The record is just at the second instruction
		
		assertEquals("The position is not the one expected", record.isFirstPosition(), true);
	}
	
	@Test
	public void test33()
	{
		Record record = new Record();
		
		for (int x = 0; x < Record.INSTRUCTIONMAXIMUM; x++)
			record.add("200", "0 2");
		
		assertEquals("The size of the record is not the one expected", Record.INSTRUCTIONMAXIMUM, record.size());
	}
	
	@Test
	public void test34()
	{
		Record record = new Record();
		assertEquals("The size is not the one expected", record.size(), 0);
	}
}
