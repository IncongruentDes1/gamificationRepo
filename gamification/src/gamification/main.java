package gamification;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.*;

public class main {
	public static Float currentStats= 0F;
	
	public static screen mainPanel;
	public static screen removePanel;
	public static screen addPanel;
	public static ArrayList<points> tasks;
	

	public static void main(String[] args) throws FileNotFoundException {
		mainPanelSetUp();

	}
	
	
	// - main panel work
	public static void mainPanelSetUp() throws FileNotFoundException {
		mainPanel = new screen("Main Interface");
		tasks = fileReadIn("tasks.txt");
		mainPanel.addCheckBox(tasks);
	}
	
	
	// - File Handling
	public static ArrayList<points> fileReadIn(String fileName) throws FileNotFoundException {
		tasks = new ArrayList<points>();
		
		// - File  Read in 
		File file = new File(fileName);
		Scanner scanner = new Scanner(file);
		
		while(scanner.hasNextLine()) {
			String curLine = scanner.nextLine();
			String[] curLineSplit = curLine.split("-");
			tasks.add(new points(curLineSplit[0], Float.parseFloat(curLineSplit[1]), Boolean.parseBoolean(curLineSplit[2])));
		}
		return tasks;
	}
	
	public static void fileWrite() {
		
	}
	
	

}
