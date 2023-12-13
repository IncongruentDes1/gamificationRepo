package gamification;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.*;
import java.lang.Math;

public class main {
	public static Float currentStats= 0F;
	
	public static screen mainPanel;
	public static screen addPanel;
	public static ArrayList<entries> tasks;
	public static JLabel curentries;
	

	public static void main(String[] args) throws FileNotFoundException {
		mainPanelSetUp();

	}
	
	// - PANEL WORK -------------------------------------------------------------------------------------------------------------------------
	
	// - MAIN Panel Work - functional
	public static void mainPanelSetUp() throws FileNotFoundException {
		mainPanel = new screen("Main Interface");
		tasks = fileReadIn("tasks.txt");
		mainPanel.addCheckBox(tasks);
		
		//Label
		curentries = new JLabel("Current Number of entries are: 0");
		statsReadIn("curStats.txt", curentries);
		
		//Buttons
		JButton addButton = new JButton("Add an Entry");
		JButton removeButton = new JButton("Remove an Entry");
		
		// - General Submit
		mainPanel.submitButton.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) { 
				  System.out.println("submit clicked");
				  try {
					statsUpdate("curStats.txt", curentries);
				} catch (IOException e1) {
					e1.printStackTrace();
				}		  } 	} );
		
		// -adding
		addButton.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) { 
					addPanelSetUp();
			  } 	} );
		
		// - Removal
		removeButton.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) { 
				  	for (int x = 0; x < tasks.size(); x++) {
				  		if (tasks.get(x).cb.isSelected()) {
				  			try {
								removeEntry(tasks.get(x));
							} catch (IOException e1) {
								e1.printStackTrace();
							}	}	}  } 
			} );
		mainPanel.bottomPanel.add(addButton); mainPanel.bottomPanel.add(removeButton); mainPanel.topPanel.add(curentries);
		mainPanel.pack();
	}
	
	// - Add panel work, functional
	public static void addPanelSetUp() {
		addPanel = new screen("Adding Entries");
		
		// - label
		JLabel title = new JLabel("Please Enter the name of the Task and Associated entries");
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
		
		// - listener
		addPanel.submitButton.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) { 
				  entries newEntry;
				  if (taskButton.isSelected()) {
					  newEntry = new entries(taskName.getText(), Float.parseFloat(amount.getText()), true);
				  }
				  else {
					  newEntry = new entries(taskName.getText(), Float.parseFloat(amount.getText()), false);
				  }
				  try {
					newEntry(newEntry);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			  } 
			} );
		
		addPanel.pack();
	}
	
	// - ENTRY MANAGEMENT WORK -------------------------------------------------------------------------------------------------------------------------
	
	//-adding/updating 
	public static void newEntry(entries entry) throws IOException {
		tasks.add(entry);
		mainPanel.addCheckBox(entry);
		mainPanel.pack();
		refreshSaveData();
	}
	
	public static void removeEntry(entries entry) throws IOException {
		tasks.remove(entry);
		for (entries task: tasks) {
			System.out.println(task);
		}
		refreshSaveData();
		mainPanel.removeCheckBox(entry);
		mainPanel.addCheckBox(tasks);
	}
	
	

	
	
	// - FILE  MANAGEMENT WORK -------------------------------------------------------------------------------------------------------------------------	

	
	// -  Task Sheet Handling: Read in
	public static ArrayList<entries> fileReadIn(String fileName) throws FileNotFoundException {
		tasks = new ArrayList<entries>();
		
		// - File  Read in 
		File file = new File(fileName);
		Scanner scanner = new Scanner(file);
		
		while(scanner.hasNextLine()) {
			String curLine = scanner.nextLine();
			String[] curLineSplit = curLine.split("-");
			tasks.add(new entries(curLineSplit[0], Float.parseFloat(curLineSplit[1]), Boolean.parseBoolean(curLineSplit[2])));
		}
		scanner.close();
		return tasks;
	}
	
	
	// - Stat Sheet Handling: Read in and Write 
	public static Float statsReadIn(String fileName, JLabel label) throws FileNotFoundException {
		File file = new File(fileName);
		Scanner scanner = new Scanner(file);
		currentStats = Float.parseFloat(scanner.nextLine());
		scanner.close();
		label.setText("Current Number of entries are: " + currentStats);
		return currentStats;
	}
	
	// stat update
	public static void statsUpdate(String fileName,  JLabel label) throws IOException {
		File file = new File(fileName);
		BufferedWriter out = new BufferedWriter(new FileWriter(file));
		
		for (entries task: tasks) {
			if (task.cb.isSelected()) {
				currentStats += task.cost;
				task.cb.setSelected(false);
			}
		}
		
		out.write(currentStats.toString());
		out.close();
		label.setText("Current Number of entries are: " + currentStats);
		mainPanel.pack();
	}
	
	// - resetting files
	public static void refreshSaveData() throws IOException {
		File file =new File("tasks.txt");
		Writer fileWriter = new FileWriter(file);
		for (entries task: tasks) {
			String tempFormat = (task.name + "-" + Math.abs(task.cost) + "-" + task.isPos);
			fileWriter.write(tempFormat);
			fileWriter.write(System.getProperty("line.separator"));
		}
		fileWriter.close();
	}
	
	
	
	

}
