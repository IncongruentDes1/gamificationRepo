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
	
	public static JLabel curPoints;
	

	public static void main(String[] args) throws FileNotFoundException {
		mainPanelSetUp();

	}
	// - Adding Panel work
	public static void addPanelSetUp() {
		addPanel = new screen("Adding Entries");
		
		// - label
		JLabel title = new JLabel("Please Enter the name of the Task and Associated Points");
		addPanel.topPanel.add(title);


		
		// -text boxes
		JTextField taskName = new JTextField(10);
		JTextField amount = new JTextField(10);
		addPanel.leftPanel.add(taskName); addPanel.leftPanel.add(amount);
		
		// JRadio
		JRadioButton taskButton = new JRadioButton("task");
		JRadioButton prizeButton = new JRadioButton("prize");
		ButtonGroup group = new ButtonGroup(); group.add(prizeButton); group.add(taskButton);
		addPanel.centerBox.add(taskButton); addPanel.centerBox. add(prizeButton);
		
		addPanel.pack();
	}
	
	// - Remove Panel work
	public static void removePanelSetUp() {
		removePanel = new screen ("Removing Entries");
		
		JLabel title = new JLabel("Please Select the Entries you would like to remove from the Task List");
		removePanel.topPanel.add(title);
		
		for (points task: tasks) {
			removePanel.centerBox.add(task.cb);
		}
		
		
		
		removePanel.pack();
	}
	
	
	// - main panel work
	public static void mainPanelSetUp() throws FileNotFoundException {
		mainPanel = new screen("Main Interface");
		tasks = fileReadIn("tasks.txt");
		mainPanel.addCheckBox(tasks);
		
		//Label
		curPoints = new JLabel("Current Number of Points are: 0");
		statsReadIn("curStats.txt", curPoints);
		
		//Buttons
		JButton addButton = new JButton("Add an Entry");
		JButton removeButton = new JButton("Remove an Entry");
		
		// - General Submit
		mainPanel.submitButton.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) { 
				  System.out.println("submit clicked");
				  try {
					statsUpdate("curStats.txt", curPoints);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			  } 
			} );
		
		// -adding
		addButton.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) { 
					addPanelSetUp();
			  } 
			} );
		
		// - Removal
		removeButton.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) { 
					removePanelSetUp();
			  } 
			} );
		
		
		
		mainPanel.bottomPanel.add(addButton); mainPanel.bottomPanel.add(removeButton); mainPanel.topPanel.add(curPoints);
		mainPanel.pack();
	}
	
	

	

	
	
	
	// -  Task Sheet Handling: Read in
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
	
	
	// - Stat Sheet Handling: Read in and Write 
	
	
	public static Float statsReadIn(String fileName, JLabel label) throws FileNotFoundException {
		File file = new File(fileName);
		Scanner scanner = new Scanner(file);
		currentStats = Float.parseFloat(scanner.nextLine());
		scanner.close();
		label.setText("Current Number of Points are: " + currentStats);
		return currentStats;
	}
	
	// stat update
	public static void statsUpdate(String fileName,  JLabel label) throws IOException {
		File file = new File(fileName);
		BufferedWriter out = new BufferedWriter(new FileWriter(file));
		
		for (points task: tasks) {
			if (task.cb.isSelected()) {
				currentStats += task.cost;
				task.cb.setSelected(false);
			}
		}
		
		out.write(currentStats.toString());
		out.close();
		label.setText("Current Number of Points are: " + currentStats);
		mainPanel.pack();
	}
	
	
	
	

}
