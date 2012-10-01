package code;

import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class TextFileChooser implements java.io.Serializable
{

	public static String saveFile()
	{
		JFileChooser fileopen = new JFileChooser();
		String answer = "?";

		FileFilter filter = new FileNameExtensionFilter("txt Files", "txt");
		fileopen.addChoosableFileFilter(filter);

		File f = null;

		try
		{
			f = new File(new File("iOFile").getCanonicalPath());
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		fileopen.setCurrentDirectory(f);

		int ret = fileopen.showSaveDialog(null);

		if (ret == JFileChooser.APPROVE_OPTION)
		{
			File file = fileopen.getSelectedFile();
			StringBuffer tempAddress = new StringBuffer(file.getAbsolutePath());

			if (!tempAddress.substring(tempAddress.length() - 4).equals(".txt"))
				tempAddress.append(".txt");

			answer = tempAddress.toString();
		}

		return answer;
	}

	public static String openFile()
	{
		JFileChooser fileopen = new JFileChooser();
		String answer = "?";

		FileFilter filter = new FileNameExtensionFilter("txt Files", "txt");
		fileopen.addChoosableFileFilter(filter);

		File f = null;

		try
		{
			f = new File(new File("iOFile").getCanonicalPath());
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		fileopen.setCurrentDirectory(f);

		int ret = fileopen.showOpenDialog(null);

		if (ret == JFileChooser.APPROVE_OPTION)
		{
			File file = fileopen.getSelectedFile();
			StringBuffer tempAddress = new StringBuffer(file.getAbsolutePath());

			if (!tempAddress.substring(tempAddress.length() - 4).equals(".txt"))
				tempAddress.append(".txt");

			answer = tempAddress.toString();
		}

		return answer;
	}
}