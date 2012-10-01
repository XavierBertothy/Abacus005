package code;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class allows to create a Record which a container to store instructions
 * composed of two parts: a code and some data. The code gives indications about
 * what has been done or what needs to be done. Data are complementary. Both
 * code and data re strings. A Record instance is in fact an ArrayList which is
 * modified through the use of methods. A variable allows to access the list at
 * a precise position to read its content or to add more instructions.
 * Each instance can contain a maximum of 200 instructions.
 */

public class Record implements java.io.Serializable
{

	public static final int INSTRUCTIONMAXIMUM = 200;

	private ArrayList<InstructionLine> instructionList;

	private int position;

	/**
	 * Creates a Record instance which is an ArrayList customised
	 */
	public Record()
	{
		instructionList = new ArrayList<InstructionLine>(INSTRUCTIONMAXIMUM);
		position = -1;
	}

	/**
	 * Adds an instruction at the end of a list of instructions
	 * @param code String 
	 * @param data String
	 * @exception if the list has reach its maximum capacity of 200 instructions
	 */
	public void add(String code, String data) throws ArrayIndexOutOfBoundsException
	{
		if (instructionList.size() == INSTRUCTIONMAXIMUM)
			throw new ArrayIndexOutOfBoundsException("The record has reached its maximum capacity: " + INSTRUCTIONMAXIMUM);

		else
		{
			if (instructionList.isEmpty())
				position = 0;

			instructionList.add(new InstructionLine(code, data));
		}
	}

	/**
	 * Adds an instruction in a list of instructions at the present position.
	 * @param code String 
	 * @param data String
	 * @exception if the list has reach its maximum capacity of 200 instructions
	 */
	public void addInTheList(String code, String data) throws ArrayIndexOutOfBoundsException
	{
		if (instructionList.size() == INSTRUCTIONMAXIMUM)
			throw new ArrayIndexOutOfBoundsException("The record has reached its maximum capacity: " + INSTRUCTIONMAXIMUM);

		else
		{
			if (instructionList.isEmpty())
			{
				position = 0;
				instructionList.add(new InstructionLine(code, data));
			}
			else
			{
				instructionList.add(position + 1, new InstructionLine(code, data));
				position++;
			}
		}
	}

	/**
	 * Returns the code of an instruction
	 * @return String which is the code of the instruction
	 * @exception if the list is empty
	 */
	public String getInstructionCode() throws ArrayIndexOutOfBoundsException
	{
		if (instructionList.isEmpty())
			throw new ArrayIndexOutOfBoundsException("Nothing has been recorded yet");

		else return instructionList.get(position).code;
	}

	/**
	 * Returns the data of an instruction
	 * @return String which is the data of the instruction
	 * @exception if the list is empty
	 */
	public String getInstructionData() throws ArrayIndexOutOfBoundsException
	{
		if (instructionList.isEmpty())
			throw new ArrayIndexOutOfBoundsException("Nothing has been recorded yet");

		else return instructionList.get(position).data;
	}

	public void changeData(String parameter) throws ArrayIndexOutOfBoundsException
	{
		if (instructionList.isEmpty())
			throw new ArrayIndexOutOfBoundsException("Nothing has been recorded yet");

		else
		{
			StringBuffer tempData = new StringBuffer(instructionList.get(position).data);
			while (!(tempData.length() == 0) && tempData.charAt(tempData.length() - 1) != ' ')
				tempData.deleteCharAt(tempData.length() - 1);
			tempData.append(parameter);
			instructionList.get(position).data = tempData.toString();
		}
	}

	/**
	 * Goes to the next instruction in the list
	 * @exception if the list is empty and if the last instruction has been passed
	 */
	public void next() throws ArrayIndexOutOfBoundsException
	{
		if (instructionList.isEmpty())
			throw new ArrayIndexOutOfBoundsException("Nothing has been recorded yet");

		else if (position + 1 >= instructionList.size())
			throw new ArrayIndexOutOfBoundsException("It is the last line of the instructions recorded");

		else position += 1;
	}

	/**
	 * Goes to the previous instruction in the list
	 * @exception if the list is empty and if the first instruction has been passed
	 */
	public void previous() throws ArrayIndexOutOfBoundsException
	{
		if (instructionList.isEmpty())
			throw new ArrayIndexOutOfBoundsException("Nothing has been recorded yet");

		else if (position - 1 < 0)
			throw new ArrayIndexOutOfBoundsException("It is the first line of the instructions recorded");

		else position -= 1;
	}

	/**
	 * Removes the last instruction in the list
	 * @exception if the list is empty
	 */
	public void removeLastInstrunctions() throws ArrayIndexOutOfBoundsException
	{
		if (instructionList.isEmpty())
			throw new ArrayIndexOutOfBoundsException("Nothing has been recorded yet");

		else
		{
			while (position + 1 != instructionList.size())
				instructionList.remove(instructionList.size() - 1);
		}
	}

	/**
	 * Saves a list of instructions into a txt file
	 * @exception if there is a problem with the outputStream
	 */
	public void save(String outputFile) throws IOException
	{
		setToStart();
		FileWriter outFile = new FileWriter(outputFile);
		PrintWriter output = new PrintWriter(outFile);
		try
		{
			boolean finished = false;
			String instruction;

			while (!finished)
			{
				instruction = getInstructionCode() + " " + this.getInstructionData() + "\n";
				output.print(instruction);

				if (!isLastPosition())
					next();

				else finished = true;
			}
		}
		finally
		{
			output.close();
		}
	}

	/**
	 * Load the content of a txt file into a Record instance
	 * @exception if the file is empty or if the file does not exist or if the instruction is not complete
	 */
	public void load(String inputFile) throws IllegalArgumentException, IOException
	{
		Scanner in = new Scanner(new File(inputFile));

		if (!in.hasNext())
			throw new IllegalArgumentException(inputFile + " is empty");

		else
		{
			String instruction;
			String code;
			StringBuffer data = new StringBuffer();

			while (in.hasNextLine())
			{
				instruction = in.nextLine();

				Scanner decompose = new Scanner(instruction);

				code = decompose.next();

				if (!decompose.hasNext())
					throw new IllegalArgumentException(inputFile + " has incomplete data");

				else
				{
					data.append(decompose.nextLine());
					if (data.charAt(0) == ' ')
						data.delete(0, 1);
					add(code, data.toString());
					data.delete(0, data.length());
				}
			}
			in.close();
		}
	}

	/**
	 * Erase an instruction in a list at a certain position
	 * @exception if the list is empty
	 */
	public void eraseInstructionLine() throws ArrayIndexOutOfBoundsException
	{
		if (instructionList.isEmpty())
			throw new IllegalArgumentException("Nothing has been recorded yet");

		else
		{
			if (isLastPosition() && isFirstPosition())
			{
				instructionList.remove(position);
				position = -1;
			}
			else if (isLastPosition())
			{
				instructionList.remove(position);
				position--;
			}
			else instructionList.remove(position);
		}
	}

	/**
	 * Clears the list
	 * @exception if the list is empty
	 */
	public void clear() throws ArrayIndexOutOfBoundsException
	{
		if (instructionList.isEmpty())
			throw new ArrayIndexOutOfBoundsException("Nothing has been recorded yet");

		else
		{
			instructionList.clear();
			position = -1;
		}
	}

	/**
	 * Tells the position of the instruction presently read in the list
	 * @exception if the list is empty
	 */
	public int getPosition() throws ArrayIndexOutOfBoundsException
	{
		if (instructionList.isEmpty())
			throw new ArrayIndexOutOfBoundsException("Nothing has been recorded yet");

		else return position;
	}

	/**
	 * Restarts at the beginning of the list
	 * @exception if the list is empty
	 */
	public void setToStart() throws ArrayIndexOutOfBoundsException
	{
		if (instructionList.isEmpty())
			throw new ArrayIndexOutOfBoundsException("Nothing has been recorded yet");

		else position = 0;
	}

	/**
	 * Checks if the list is empty
	 * @return boolean if true the list has some content if false the list is empty
	 */
	public boolean isEmpty()
	{
		return instructionList.isEmpty();
	}

	/**
	 * Checks if the last instruction of the list is read 
	 * @return boolean 
	 */
	public boolean isLastPosition()
	{
		return position + 1 == instructionList.size();
	}

	/**
	 * Checks if the first instruction of the list is read 
	 * @return boolean 
	 */
	public boolean isFirstPosition()
	{
		return position == 0;
	}

	/**
	 * Returns the size of the list
	 * @return int 
	 */
	public int size()
	{
		return instructionList.size();
	}

	private class InstructionLine
	{
		private String code;
		private String data;

		public InstructionLine(String code, String data)
		{
			this.code = code;
			this.data = data;
		}
	}

}