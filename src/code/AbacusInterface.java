package code;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.Timer;

/**
 * This class creates an interface containing an abacus. The program in two
 * modes: a normal mode where the user can use the abacus as he/she wishes. The
 * user has the possibility to load a video previously recorded, record some
 * calculations, change the colours of the abacus, reset the abacus to zero. The
 * value of the abacus and its rods are shown at the botom of it. In the
 * exercise mode the user can record his/her answer and get a final feedback.
 * When the user has one or more exercise he/she is free to switch back and
 * forth between the tasks. The program will reinstate the colour, value and
 * instruction linked to the exercise as it was before. The value of the abacus
 * and its rods are not shown in the exercise mode.
 * @see Abacus class, AbacusParameterDialog class, Record class, TextFileChooser
 *      class.
 */

public class AbacusInterface extends JPanel implements java.io.Serializable
{
	public static final int NORMALMODE = 100;
	public static final int EXERCISEMODE = 200;
	public static final int EXERCISEMAXIMUMNUMBER = 10;

	public static final int BUTTONHEIGHT = 44;
	public static final int BUTTONLENGTH = 44;

	public static final String LOADNEWABACUS = "200";
	public static final String CHANGEABACUSVALUE = "201";
	public static final String CHANGEABACUSCOLOUR = "202";

	public static final String PARAMETERFILE = "parameters/abacusParameters.txt";
	public static final String INSTRUCTIONBACKICONFILE = "parameters/Rewind16.gif";
	public static final String STEPBACKICONFILE = "parameters/StepBack16.gif";
	public static final String RECORDICONFILE = "parameters/Record16.gif";
	public static final String PLAYICONFILE = "parameters/Play16.gif";
	public static final String STEPFORWARDICONFILE = "parameters/StepForward16.gif";
	public static final String INSTRUCTIONFORWARDICONFILE = "parameters/FastForward16.gif";
	public static final String LOADICONFILE = "parameters/Load16.gif";
	public static final String SAVEICONFILE = "parameters/Save.gif";
	public static final String STOPICONFILE = "parameters/Stop16.gif";
	public static final String RESETICONFILE = "parameters/Reset16.gif";
	public static final String ERASEICONFILE = "parameters/Erase16.gif";
	public static final String PARAMETERICONFILE = "parameters/Parameters16.gif";

	public static final String[][] EXERCISE = { { "300", "Display", "" }, { "301", "Calculate", "" },
			{ "302", "Add", " + " }, { "303", "Subtract", " - " }, { "304", "Multiply by", " * " },
			{ "305", "Divided by", " / " }, { "306", "", "" } };

	public static final String[] TYPICALCOMMENT = { "Welldone", "False" };

	private Abacus abacus;

	private AbacusParameterDialog abacusParameterDialog;

	private ArrayList<JRadioButton> exerciseButtonList;
	private ArrayList<String> parameterList;
	private ArrayList<Record> recordList;

	private boolean isRecording;
	private boolean isPlaying;

	private ButtonGroup group;

	private int presentPosition;
	private int state;

	private JButton instructionBackButton;
	private JButton moveBackButton;
	private JButton recordButton;
	private JButton playButton;
	private JButton moveForwardButton;
	private JButton instructionForwardButton;
	private JButton loadButton;
	private JButton saveButton;
	private JButton stopButton;
	private JButton resetButton;
	private JButton eraseButton;
	private JButton parameterButton;
	private JButton resultButton;

	private JLabel exerciseField;
	private JLabel recordField;

	private JPanel abacusPanel;
	private JPanel normalExerciseListPanel;
	private JPanel commandButtonPanel;

	private Record record;

	private Timer timer;

	/**
	 * Builds an interface containing an abacus with added functions
	 */
	public AbacusInterface()
	{
		try
		{

			isRecording = false;
			isPlaying = false;

			record = new Record();
			record.load(PARAMETERFILE);

			if (!record.getInstructionCode().equals(LOADNEWABACUS))
				throw new IllegalArgumentException("The instruction of the parameter file should "
						+ "start with the integer " + LOADNEWABACUS + " instead of "
						+ record.getInstructionCode());

			else abacus = getAbacusFromString(record.getInstructionData());

			abacus.setValue("000000.00");
			record.clear();
			state = NORMALMODE;

			abacusParameterDialog =
					new AbacusParameterDialog(abacus.getBackgroundColour(), abacus.getBeadColour());
			abacusParameterDialog.setSize(200, 400);

			AbacusListener abacusListener = new AbacusListener();
			ActionListener instructionBackListener = new InstructionBackListener();
			ActionListener moveBackListener = new MoveBackListener();
			ActionListener recordListener = new RecordListener();
			ActionListener playListener = new PlayListener();
			ActionListener moveForwardListener = new MoveForwardListener();
			ActionListener instructionForwardListener = new InstructionForwardListener();
			ActionListener loadListener = new LoadListener();
			ActionListener saveListener = new SaveListener();
			ActionListener stopListener = new StopListener();
			ActionListener eraseListener = new EraseListener();
			ActionListener parameterListener = new ParameterListener();

			abacus.addActionListener(abacusListener);

			abacusPanel = new JPanel();
			abacusPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
			abacusPanel.add(abacus);

			Icon instructionBackIcon = new ImageIcon(INSTRUCTIONBACKICONFILE);
			instructionBackButton = new JButton(instructionBackIcon);
			instructionBackButton.setToolTipText("For going to the previous instruction");
			instructionBackButton.setPreferredSize(new Dimension(BUTTONLENGTH, BUTTONHEIGHT));
			instructionBackButton.addActionListener(instructionBackListener);

			Icon backIcon = new ImageIcon(STEPBACKICONFILE);
			moveBackButton = new JButton(backIcon);
			moveBackButton.setToolTipText("For stepping back in a record");
			moveBackButton.setPreferredSize(new Dimension(BUTTONLENGTH, BUTTONHEIGHT));
			moveBackButton.addActionListener(moveBackListener);

			Icon recordIcon = new ImageIcon(RECORDICONFILE);
			recordButton = new JButton(recordIcon);
			recordButton.setToolTipText("For recording the operations");
			recordButton.setPreferredSize(new Dimension(BUTTONLENGTH, BUTTONHEIGHT));
			recordButton.addActionListener(recordListener);

			Icon playIcon = new ImageIcon(PLAYICONFILE);
			playButton = new JButton(playIcon);
			playButton.setToolTipText("For reading a record");
			playButton.setPreferredSize(new Dimension(BUTTONLENGTH, BUTTONHEIGHT));
			playButton.addActionListener(playListener);

			Icon forwardIcon = new ImageIcon(STEPFORWARDICONFILE);
			moveForwardButton = new JButton(forwardIcon);
			moveForwardButton.setToolTipText("For stepping forward in a record");
			moveForwardButton.setPreferredSize(new Dimension(BUTTONLENGTH, BUTTONHEIGHT));
			moveForwardButton.addActionListener(moveForwardListener);

			Icon instructionForwardIcon = new ImageIcon(INSTRUCTIONFORWARDICONFILE);
			instructionForwardButton = new JButton(instructionForwardIcon);
			instructionForwardButton.setToolTipText("For going to the next instruction");
			instructionForwardButton.setPreferredSize(new Dimension(BUTTONLENGTH, BUTTONHEIGHT));
			instructionForwardButton.addActionListener(instructionForwardListener);

			Icon loadIcon = new ImageIcon(LOADICONFILE);
			loadButton = new JButton(loadIcon);
			loadButton.setToolTipText("For loading an exercise, a video or an exam");
			loadButton.setPreferredSize(new Dimension(BUTTONLENGTH, BUTTONHEIGHT));
			loadButton.addActionListener(loadListener);

			Icon saveIcon = new ImageIcon(SAVEICONFILE);
			saveButton = new JButton(saveIcon);
			saveButton.setToolTipText("For saving a record");
			saveButton.setPreferredSize(new Dimension(BUTTONLENGTH, BUTTONHEIGHT));
			saveButton.addActionListener(saveListener);

			Icon stopIcon = new ImageIcon(STOPICONFILE);
			stopButton = new JButton(stopIcon);
			stopButton.setToolTipText("For stopping a record");
			stopButton.setPreferredSize(new Dimension(BUTTONLENGTH, BUTTONHEIGHT));
			stopButton.addActionListener(stopListener);

			Icon resetIcon = new ImageIcon(RESETICONFILE);
			resetButton = new JButton(resetIcon);
			resetButton.setToolTipText("For resetting the abacus");
			resetButton.setPreferredSize(new Dimension(BUTTONLENGTH, BUTTONHEIGHT));
			resetButton.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					abacus.setValue("000000.00");
				}
			});

			Icon eraseIcon = new ImageIcon(ERASEICONFILE);
			eraseButton = new JButton(eraseIcon);
			eraseButton.setToolTipText("For erasing the present record");
			eraseButton.setPreferredSize(new Dimension(BUTTONLENGTH, BUTTONHEIGHT));
			eraseButton.addActionListener(eraseListener);

			Icon parameterIcon = new ImageIcon(PARAMETERICONFILE);
			parameterButton = new JButton(parameterIcon);
			parameterButton.setToolTipText("For setting parameters");
			parameterButton.setPreferredSize(new Dimension(BUTTONLENGTH, BUTTONHEIGHT));
			parameterButton.addActionListener(parameterListener);

			resultButton = new JButton("R");
			resultButton.setToolTipText("For getting the result of an exercise");
			resultButton.setPreferredSize(new Dimension(BUTTONLENGTH, BUTTONHEIGHT));
			resultButton.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					getResult(record);
				}
			});

			commandButtonPanel = new JPanel();
			getCommandPanel();

			JPanel commandPanel = new JPanel();
			commandPanel.setLayout(new FlowLayout());
			commandPanel.add(commandButtonPanel);

			exerciseField = new JLabel();
			recordField = new JLabel();

			JPanel intermediaryCommandPanel = new JPanel();
			intermediaryCommandPanel.setLayout(new BorderLayout());
			intermediaryCommandPanel.add(exerciseField, BorderLayout.NORTH);
			intermediaryCommandPanel.add(recordField, BorderLayout.CENTER);
			intermediaryCommandPanel.add(commandPanel, BorderLayout.SOUTH);

			JPanel finalCommandPanel = new JPanel();
			finalCommandPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 30));
			finalCommandPanel.add(intermediaryCommandPanel);

			exerciseButtonList = new ArrayList<JRadioButton>(EXERCISEMAXIMUMNUMBER);
			parameterList = new ArrayList<String>();
			recordList = new ArrayList<Record>();
			presentPosition = 0;

			parameterList.add(getAbacusParameters());
			recordList.add(record);

			normalExerciseListPanel = new JPanel();
			normalExerciseListPanel.setLayout(new GridLayout(EXERCISEMAXIMUMNUMBER, 1));
			group = new ButtonGroup();

			JRadioButton temp = new JRadioButton("regular");
			temp.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					parameterList.set(presentPosition, getAbacusParameters());
					recordList.set(presentPosition, record);

					presentPosition = 0;
					record = recordList.get(0);
					setAbacusParameters(parameterList.get(0));
					getCommandPanel();
					exerciseField.setText("");
				}
			});
			temp.setSelected(true);

			exerciseButtonList.add(temp);
			normalExerciseListPanel.add(temp);
			group.add(temp);

			JPanel commandAndAbacus = new JPanel();
			commandAndAbacus.setLayout(new BorderLayout(0, 0));
			commandAndAbacus.add(abacusPanel, BorderLayout.CENTER);
			commandAndAbacus.add(finalCommandPanel, BorderLayout.SOUTH);

			setLayout(new BorderLayout(0, 0));
			add(normalExerciseListPanel, BorderLayout.WEST);
			add(commandAndAbacus, BorderLayout.CENTER);
		}
		catch (IllegalArgumentException ex)
		{
			ex.printStackTrace();
		}
		catch (IOException e1)
		{
			e1.printStackTrace();
		}
	}

	private String eraseUnnecessaryZeros(String abacusValueWithZeros)
	{
		StringBuffer returnedValue = new StringBuffer();
		String tempValue = abacusValueWithZeros;
		boolean leadingZero = true;

		for (int x = 0; x < tempValue.length(); x++)
		{
			if (tempValue.charAt(x) != '0')
			{
				leadingZero = false;
				returnedValue.append(tempValue.charAt(x));
			}

			else if (tempValue.charAt(x) == '0' && !leadingZero)
			{
				returnedValue.append(tempValue.charAt(x));
			}
		}

		while (returnedValue.charAt(returnedValue.length() - 1) == '0' && isDecimal(tempValue))
		{
			returnedValue.deleteCharAt(returnedValue.length() - 1);
		}

		if (returnedValue.charAt(returnedValue.length() - 1) == '.')
			returnedValue.deleteCharAt(returnedValue.length() - 1);

		if (returnedValue.charAt(0) == '.' && returnedValue.length() >= 2)
			returnedValue.insert(0, "0");

		if (returnedValue.length() == 0)
			returnedValue.append("0");

		return returnedValue.toString();
	}

	private void getCommandPanel()
	{
		if (state == NORMALMODE)
		{
			commandButtonPanel.removeAll();
			commandButtonPanel.setLayout(new GridLayout(2, 5, 0, 0));
			commandButtonPanel.add(moveBackButton);
			commandButtonPanel.add(recordButton);
			commandButtonPanel.add(playButton);
			commandButtonPanel.add(moveForwardButton);
			commandButtonPanel.add(parameterButton);
			commandButtonPanel.add(loadButton);
			commandButtonPanel.add(saveButton);
			commandButtonPanel.add(stopButton);
			commandButtonPanel.add(eraseButton);
			commandButtonPanel.add(resetButton);
			commandButtonPanel.validate();
		}
		else if (state == EXERCISEMODE)
		{
			commandButtonPanel.removeAll();
			commandButtonPanel.setLayout(new GridLayout(2, 4, 0, 0));
			commandButtonPanel.add(instructionBackButton);
			commandButtonPanel.add(recordButton);
			commandButtonPanel.add(moveForwardButton);
			commandButtonPanel.add(instructionForwardButton);
			commandButtonPanel.add(loadButton);
			commandButtonPanel.add(resultButton);
			commandButtonPanel.add(saveButton);
			commandButtonPanel.add(resetButton);
			commandButtonPanel.validate();
		}
	}

	private void setEnabledButton()
	{
		if (state == NORMALMODE)
		{
			if (isRecording)
			{
				moveBackButton.setEnabled(false);
				playButton.setEnabled(false);
				moveForwardButton.setEnabled(false);
				loadButton.setEnabled(false);
				saveButton.setEnabled(false);
				stopButton.setEnabled(false);
				eraseButton.setEnabled(false);
			}
			else
			{
				moveBackButton.setEnabled(true);
				playButton.setEnabled(true);
				moveForwardButton.setEnabled(true);
				loadButton.setEnabled(true);
				saveButton.setEnabled(true);
				stopButton.setEnabled(true);
				eraseButton.setEnabled(true);
			}
		}
		else if (state == EXERCISEMODE)
		{
			if (isRecording)
			{
				instructionBackButton.setEnabled(false);
				moveForwardButton.setEnabled(false);
				instructionForwardButton.setEnabled(false);
				loadButton.setEnabled(false);
				resultButton.setEnabled(false);
				saveButton.setEnabled(false);
			}
			else
			{
				instructionBackButton.setEnabled(true);
				moveForwardButton.setEnabled(true);
				instructionForwardButton.setEnabled(true);
				loadButton.setEnabled(true);
				resultButton.setEnabled(true);
				saveButton.setEnabled(true);
			}
		}
	}

	private Abacus getAbacusFromString(String abacusParameters) throws IllegalArgumentException
	{
		if (!isIntegers(abacusParameters))
			throw new IllegalArgumentException("All the parameters should be integers. Parameters: "
					+ abacusParameters);

		else
		{
			Scanner in = new Scanner(abacusParameters);

			int tempBackgroundColourChoice = Integer.parseInt(in.next());
			int tempBeadColourChoice = Integer.parseInt(in.next());

			in.close();

			return new Abacus(tempBackgroundColourChoice, tempBeadColourChoice);
		}
	}

	private boolean isIntegers(String value)
	{
		for (int x = 0; x < value.length(); x++)
			if ((value.charAt(x) < '0' || value.charAt(x) > '9') && (!(value.charAt(x) == ' ')))
				return false;

		return true;
	}

	private void changeAbacusColour(String abacusParameters)
	{
		Scanner in = new Scanner(abacusParameters);

		int tempBackgroundColour = Integer.parseInt(in.next());
		int tempBeadColour = Integer.parseInt(in.next());
		abacus.setBackgroundColour(tempBackgroundColour);
		abacus.setBeadColour(tempBeadColour);

		in.close();
	}

	private String getAbacusParameters()
	{
		return state + " " + abacus.getValueWithZeros() + " "
				+ (abacus.getIsRodValueDisplayed() ? 1 : 0) + " "
				+ (abacus.getIsAbacusValueDisplayed() ? 1 : 0) + " " + abacus.getBackgroundColour() + " "
				+ abacus.getBeadColour() + " "
				+ (recordField.getText().isEmpty() ? "X" : recordField.getText());
	}

	private void setAbacusParameters(String abacusParameters)
	{
		Scanner in = new Scanner(abacusParameters);

		state = in.nextInt();
		abacus.setValue(in.next());
		abacus.setIsRodValueDisplayed((in.next().equals("1") ? true : false));
		abacus.setIsAbacusValueDisplayed((in.next().equals("1") ? true : false));
		changeAbacusColour(in.next() + " " + in.next());
		String recordFieldText = in.nextLine().substring(1);
		recordField.setText((recordFieldText.equals("X") ? "" : recordFieldText));

		in.close();
	}

	private void createButton(String exerciseAddress) throws ArrayIndexOutOfBoundsException
	{
		if (exerciseButtonList.size() >= EXERCISEMAXIMUMNUMBER)
			throw new ArrayIndexOutOfBoundsException(
					"The maximum number of displayed exercises has been reached: " + EXERCISEMAXIMUMNUMBER);

		else
		{
			JRadioButton temp = new JRadioButton(getExerciseName(exerciseAddress));
			final int buttonPosition = exerciseButtonList.size();
			temp.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					if (isPlaying == true)
					{
						isPlaying = false;
						timer.stop();
					}

					parameterList.set(presentPosition, getAbacusParameters());
					recordList.set(presentPosition, record);

					presentPosition = buttonPosition;
					record = recordList.get(buttonPosition);
					setAbacusParameters(parameterList.get(buttonPosition));
					getCommandPanel();

					if (record.getInstructionCode().charAt(0) == EXERCISE[0][0].charAt(0))
						displayInstruction(record.getInstructionCode(), record.getInstructionData());

				}
			});
			temp.setSelected(true);

			exerciseButtonList.add(temp);
			normalExerciseListPanel.add(temp);
			normalExerciseListPanel.validate();
			group.add(temp);
		}
	}

	private String getExerciseName(String exerciseAddress)
	{
		StringBuffer temp = new StringBuffer(exerciseAddress);
		temp.delete(temp.length() - 4, temp.length());
		int startExerciseName = temp.length() - 1;

		while (temp.charAt(startExerciseName) != '\\' && startExerciseName >= 0)
			startExerciseName--;

		temp.delete(0, startExerciseName + 1);
		return temp.toString();
	}

	private void displayInstruction(String code, String data)
	{
		int exerciseType = 0;

		for (int x = 0; x < EXERCISE.length; x++)
			if (code.equals(EXERCISE[x][0]))
				exerciseType = x;

		String displayedData = "The question is empty";

		if (code.equals(EXERCISE[6][0]))
		{
			Scanner in = new Scanner(data);
			in.next();
			displayedData = in.nextLine().substring(1);

			in.close();
		}
		else displayedData = data;

		exerciseField.setText(EXERCISE[exerciseType][1] + " " + displayedData);
	}

	private void getResult(Record record)
	{
		record.setToStart();
		record.next();

		String instruction = null;
		String expectedAnswer = null;
		String givenAnswer = null;
		ArrayList<String[]> answerList = new ArrayList<String[]>();
		boolean finished = false;

		while (!finished)
		{
			if (record.getInstructionCode().equals(EXERCISE[0][0]))
			{
				expectedAnswer = record.getInstructionData();
				instruction = EXERCISE[0][1] + " " + record.getInstructionData();
			}

			else if (record.getInstructionCode().equals(EXERCISE[1][0]))
			{
				expectedAnswer = doOperation(record.getInstructionData());
				instruction = EXERCISE[1][1] + " " + record.getInstructionData();
			}

			else if (record.getInstructionCode().equals(EXERCISE[6][0]))
			{
				Scanner in = new Scanner(record.getInstructionData());
				expectedAnswer = in.next();
				instruction = in.nextLine().substring(1);
				in.close();
			}

			else
			{
				for (int x = 2; x < EXERCISE.length - 1; x++)
				{
					if (record.getInstructionCode().equals(EXERCISE[x][0]))
					{
						expectedAnswer =
								doOperation(expectedAnswer + EXERCISE[x][2] + record.getInstructionData());
						instruction = EXERCISE[x][1] + " " + record.getInstructionData();
					}
				}
			}

			if (!record.isLastPosition())
			{
				record.next();

				if (record.getInstructionCode().charAt(0) == EXERCISE[0][0].charAt(0))
				{
					String[] answerLine = { instruction, expectedAnswer, "No answer", TYPICALCOMMENT[1] };
					answerList.add(answerLine);
				}
				else
				{
					while (record.getInstructionCode().charAt(0) != EXERCISE[0][0].charAt(0)
							&& !record.isLastPosition())
						record.next();

					if (!record.isLastPosition()
							|| record.getInstructionCode().charAt(0) == EXERCISE[0][0].charAt(0))
						record.previous();

					else finished = true;

					givenAnswer = eraseUnnecessaryZeros(record.getInstructionData());

					String wellDone =
							(givenAnswer.equals(expectedAnswer) ? TYPICALCOMMENT[0] : TYPICALCOMMENT[1]);

					String[] answerLine = { instruction, expectedAnswer, givenAnswer, wellDone };
					answerList.add(answerLine);

					if (!record.isLastPosition())
						record.next();

					else finished = true;
				}
			}
			else
			{
				String[] answerLine = { instruction, expectedAnswer, "No answer", TYPICALCOMMENT[1] };
				answerList.add(answerLine);
				finished = true;
			}
		}

		displayResult(answerList);
	}

	private void displayResult(ArrayList<String[]> answerList)
	{
		int totalWellDone = 0;
		int totalQuestion = 0;

		String[] legend = { "Question", "Expected answer", "Your answer", "" };

		String[][] result = new String[20][4];

		int x = 0;

		for (; x < answerList.size(); x++)
		{
			totalQuestion++;

			if (answerList.get(x)[3].equals(TYPICALCOMMENT[0]))
				totalWellDone++;

			for (int y = 0; y < answerList.get(x).length; y++)
				result[x][y] = answerList.get(x)[y];
		}

		int percentage = totalWellDone * 100 / totalQuestion;
		String[] mainMark = new String[2];
		mainMark[0] = "Your score is " + totalWellDone + " / " + totalQuestion;

		if (percentage >= 80)
			mainMark[1] = "Excellent!";
		else if (percentage >= 50)
			mainMark[1] = "Good!";
		else mainMark[1] = "Try again.";

		x++;
		for (int y = 0; y < mainMark.length; y++)
			result[x][y] = mainMark[y];

		JTable resultTable = new JTable(result, legend);
		resultTable.setShowGrid(false);
		resultTable.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);

		JScrollPane scrollPane = new JScrollPane(resultTable);
		scrollPane.setPreferredSize(new java.awt.Dimension(600, 200));
		JOptionPane.showMessageDialog(null, scrollPane, "Your results",
				JOptionPane.INFORMATION_MESSAGE, null);
	}

	private String doOperation(String operation)
	{
		Scanner in = new Scanner(operation);

		BigDecimal operand1 = new BigDecimal(in.next());
		String operator = in.next();
		BigDecimal operand2 = new BigDecimal(in.next());
		BigDecimal result;

		if (operator.equals("+"))
			result = operand1.add(operand2);

		else if (operator.equals("-"))
			result = operand1.subtract(operand2);

		else if (operator.equals("*"))
			result = operand1.multiply(operand2);

		else result = operand1.divide(operand2);

		in.close();

		return result.toString();
	}

	private void save()
	{
		try
		{
			String saveAddress = TextFileChooser.saveFile();
			if (!saveAddress.equals("?"))
				record.save(saveAddress);
		}
		catch (IOException e1)
		{
			e1.printStackTrace();
		}
	}

	private void saveParameter() throws IOException
	{
		FileWriter outFile = new FileWriter(PARAMETERFILE);
		PrintWriter output = new PrintWriter(outFile);
		try
		{
			String instruction =
					LOADNEWABACUS + " " + abacusParameterDialog.getBackgroundColourChoice() + " "
							+ abacusParameterDialog.getBeadColourChoice();
			output.print(instruction);
		}
		finally
		{
			output.close();
		}
	}

	private void savePreviousRecord()
	{
		if (!record.isEmpty())
		{
			int answer =
					JOptionPane.showConfirmDialog(null, "Would you like to save the existing record?"
							+ " The record is about to be erased", "Save existing record?",
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

			if (answer == 0)
				save();

			if (isPlaying == true)
			{
				isPlaying = false;
				timer.stop();
			}
			recordField.setText("");

			setAbacusParameters(parameterList.get(0));

			record.clear();
		}
	}

	private void read()
	{
		if (!record.isLastPosition())
		{
			record.next();

			if (record.getInstructionCode().equals(CHANGEABACUSVALUE))
				abacus.setValue(record.getInstructionData());

			else if (record.getInstructionCode().equals(CHANGEABACUSCOLOUR))
				changeAbacusColour(record.getInstructionData());

			else recordField.setText("The instruction code is not known: " + record.getInstructionCode());
		}
		else recordField.setText("End of record");
	}

	private boolean isDecimal(String value)
	{
		for (int x = 0; x < value.length(); x++)
		{
			if (value.charAt(x) == '.')
				return true;
		}
		return false;
	}

	private class AbacusListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if (state == EXERCISEMODE && isRecording)
			{
				record.addInTheList(CHANGEABACUSVALUE, abacus.getValueWithZeros());
			}

			if (state == NORMALMODE && isRecording)
			{
				if (record.isEmpty())
				{
					String instruction = abacus.getBackgroundColour() + " " + abacus.getBeadColour();
					record.add(CHANGEABACUSCOLOUR, instruction);
				}
				record.add(CHANGEABACUSVALUE, abacus.getValueWithZeros());
			}
		}
	}

	private class InstructionBackListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if (!record.isEmpty() && state == EXERCISEMODE)
			{
				if (!record.isFirstPosition())
				{
					record.previous();

					while (record.getInstructionCode().charAt(0) != EXERCISE[0][0].charAt(0)
							&& !record.isFirstPosition())
						record.previous();

					if (record.getInstructionCode().equals(CHANGEABACUSCOLOUR))
					{
						record.next();
						displayInstruction(record.getInstructionCode(), record.getInstructionData());
						recordField.setText("Start of exercise");
					}
					else
					{
						displayInstruction(record.getInstructionCode(), record.getInstructionData());

						recordField.setText("");

						if (record.getInstructionCode().equals(EXERCISE[0][0])
								|| record.getInstructionCode().equals(EXERCISE[1][0])
								|| record.getInstructionCode().equals(EXERCISE[6][0]))
							abacus.setValue("000000.00");
					}
				}
			}
		}
	}

	private class MoveBackListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if (!record.isEmpty() && state == NORMALMODE)
			{
				isRecording = false;

				if (isPlaying == true)
				{
					isPlaying = false;
					timer.stop();
				}

				recordField.setText("");

				if (!record.isFirstPosition())
				{
					record.previous();

					if (record.getInstructionCode().equals(CHANGEABACUSVALUE))
						abacus.setValue(record.getInstructionData());

					if (record.getInstructionCode().equals(CHANGEABACUSCOLOUR))
						changeAbacusColour(record.getInstructionData());
				}
				else recordField.setText("Start");
			}
		}
	}

	private class RecordListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if (!isRecording)
			{
				recordField.setText("Recording");
				isRecording = true;
				setEnabledButton();

				if (state == EXERCISEMODE)
				{

					if (record.getInstructionCode().equals(CHANGEABACUSVALUE))
					{
						record.previous();

						while (record.getInstructionCode().charAt(0) != EXERCISE[0][0].charAt(0)
								&& !record.isFirstPosition())
							record.previous();
					}

					if (record.getInstructionCode().charAt(0) == EXERCISE[0][0].charAt(0)
							&& !record.isLastPosition())
					{
						record.next();

						while (record.getInstructionCode().equals(CHANGEABACUSVALUE)
								&& !record.isLastPosition())
							record.eraseInstructionLine();

						if (record.getInstructionCode().equals(CHANGEABACUSVALUE) && !record.isLastPosition())
							record.eraseInstructionLine();

						else if (record.getInstructionCode().equals(CHANGEABACUSVALUE)
								&& record.isLastPosition())
						{
							record.eraseInstructionLine();
						}

						else record.previous();
					}
				}

				if (state == NORMALMODE)
				{
					savePreviousRecord();

					if (isPlaying == true)
					{
						isPlaying = false;
						timer.stop();
					}
				}
			}
			else
			{
				recordField.setText("");
				isRecording = false;
				setEnabledButton();
			}
		}
	}

	private class PlayListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if (!record.isEmpty() && state == NORMALMODE)
			{
				isRecording = false;

				if (isPlaying == false)
				{
					recordField.setText("Playing");
					isPlaying = true;
					timer = new Timer(1000, new TimerListener());
					timer.start();
				}
				else
				{
					recordField.setText("Pause");
					isPlaying = false;
					timer.stop();
				}
			}
		}
	}

	private class MoveForwardListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if (!record.isEmpty() && state == EXERCISEMODE)
			{
				if (!record.isLastPosition())
				{
					record.next();
					recordField.setText("");

					if (record.getInstructionCode().equals(CHANGEABACUSVALUE))
						abacus.setValue(record.getInstructionData());

					else if (record.getInstructionCode().charAt(0) == EXERCISE[0][0].charAt(0))
					{
						displayInstruction(record.getInstructionCode(), record.getInstructionData());

						if (record.getInstructionCode().equals(EXERCISE[0][0])
								|| record.getInstructionCode().equals(EXERCISE[1][0])
								|| record.getInstructionCode().equals(EXERCISE[6][0]))
							abacus.setValue("000000.00");
					}
					else
					{
						record.previous();

						while (record.getInstructionCode().charAt(0) != EXERCISE[0][0].charAt(0)
								&& !record.isFirstPosition())
							record.previous();
					}
				}
				else
				{
					recordField.setText("Save your exercise. End of record");
				}
			}

			if (!record.isEmpty() && state == NORMALMODE)
			{
				isRecording = false;

				if (isPlaying == true)
				{
					isPlaying = false;
					timer.stop();
				}

				recordField.setText("");
				read();
			}
		}
	}

	private class InstructionForwardListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if (!record.isEmpty() && state == EXERCISEMODE)
			{
				if (!record.isLastPosition())
				{
					record.next();

					while (record.getInstructionCode().charAt(0) != EXERCISE[0][0].charAt(0)
							&& !record.isLastPosition())
						record.next();

					if (record.getInstructionCode().charAt(0) != EXERCISE[0][0].charAt(0))
						recordField.setText("Save your exercise. End of record");

					else
					{
						displayInstruction(record.getInstructionCode(), record.getInstructionData());

						if (record.getInstructionCode().equals(EXERCISE[0][0])
								|| record.getInstructionCode().equals(EXERCISE[1][0])
								|| record.getInstructionCode().equals(EXERCISE[6][0]))
							abacus.setValue("000000.00");
					}
				}
				else recordField.setText("Save your exercise. End of record");
			}
		}
	}

	private class LoadListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			try
			{
				String openAddress = TextFileChooser.openFile();
				if (!openAddress.equals("?"))
				{
					Record tempRecord = new Record();

					tempRecord.load(openAddress);
					tempRecord.next();

					if (tempRecord.getInstructionCode().charAt(0) == EXERCISE[0][0].charAt(0))
					{
						parameterList.set(presentPosition, getAbacusParameters());
						recordList.set(presentPosition, record);

						record = new Record();

						record.load(openAddress);

						if (record.getInstructionCode().equals(CHANGEABACUSCOLOUR))
							changeAbacusColour(record.getInstructionData());

						else recordField.setText("The instruction code is not known: "
								+ record.getInstructionCode());

						if (!record.isLastPosition())
						{
							record.next();

							if (record.getInstructionCode().charAt(0) == EXERCISE[0][0].charAt(0))
							{
								abacus.setIsRodValueDisplayed(false);
								abacus.setIsAbacusValueDisplayed(false);
								abacus.setValue("000000.00");

								createButton(openAddress);

								presentPosition = parameterList.size();
								state = EXERCISEMODE;
								getCommandPanel();
								parameterList.add(getAbacusParameters());
								recordList.add(record);
								exerciseButtonList.get(exerciseButtonList.size() - 1).setSelected(true);

								displayInstruction(record.getInstructionCode(), record.getInstructionData());

								recordField.setText("");
							}
						}
					}
					else if (state == NORMALMODE)
					{
						savePreviousRecord();

						record = tempRecord;

						record.previous();

						if (record.getInstructionCode().equals(CHANGEABACUSCOLOUR))
							changeAbacusColour(record.getInstructionData());

						else recordField.setText("The instruction code is not known: "
								+ record.getInstructionCode());
					}
				}
			}
			catch (ArrayIndexOutOfBoundsException e1)
			{
				e1.printStackTrace();
			}
			catch (IOException e1)
			{
				e1.printStackTrace();
			}
		}
	}

	private class SaveListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if (state == EXERCISEMODE && !record.isEmpty())
				save();

			else if (state == NORMALMODE && !record.isEmpty())
			{
				save();

				if (isPlaying == true)
				{
					isPlaying = false;
					timer.stop();
				}
				recordField.setText("");

				setAbacusParameters(parameterList.get(0));

				record.clear();
			}
		}
	}

	private class StopListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if (!record.isEmpty() && state == NORMALMODE)
			{
				isRecording = false;

				if (isPlaying == true)
				{
					isPlaying = false;
					timer.stop();
				}

				recordField.setText("");

				record.setToStart();

				if (record.getInstructionCode().equals(CHANGEABACUSCOLOUR))
					changeAbacusColour(record.getInstructionData());

				abacus.setValue("000000.00");
			}
		}
	}

	private class EraseListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if (!record.isEmpty() && state == NORMALMODE)
			{
				isRecording = false;

				if (isPlaying == true)
				{
					isPlaying = false;
					timer.stop();
				}

				recordField.setText("");

				setAbacusParameters(parameterList.get(0));

				record.clear();
			}
		}
	}

	private class ParameterListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			abacusParameterDialog.setVisible(true);

			if (abacusParameterDialog.getBackgroundColourChoice() != -1)
				abacus.setBackgroundColour(abacusParameterDialog.getBackgroundColourChoice());

			if (abacusParameterDialog.getBeadColourChoice() != -1)
				abacus.setBeadColour(abacusParameterDialog.getBeadColourChoice());

			if (abacusParameterDialog.getIsSavedAsParameter())
			{
				try
				{
					saveParameter();
					abacusParameterDialog.setIsSavedAsParameter(false);
				}
				catch (IOException e1)
				{
					e1.printStackTrace();
				}
			}

			String instructionData = abacus.getBackgroundColour() + " " + abacus.getBeadColour();

			if (state == EXERCISEMODE && isRecording)
				record.addInTheList(CHANGEABACUSCOLOUR, instructionData);

			if (state == NORMALMODE && isRecording)
				record.add(CHANGEABACUSCOLOUR, instructionData);
		}
	}

	private class TimerListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			read();
		}
	}

}
