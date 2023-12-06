package gamification;

import java.awt.BorderLayout;
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

public class main {
	public static Float currentStats= 0F;
	public static ArrayList<JCheckBox> boxeslist;
	public static ArrayList<points> tasksList;
    public static Box tasksBox = Box.createVerticalBox();
    public static Box prizesBox = Box.createVerticalBox();
	public static screen mainFrame;

	public static void main(String[] args) {
		// values handling
		boxeslist = new ArrayList<JCheckBox>();
		tasksList = new ArrayList<points>();
		ArrayList<points> tasks = fileReadIn("tasks.txt");
		ArrayList<points> prizes = fileReadIn("prizes.txt");
		tasksList.addAll(prizes);
		tasksList.addAll(tasks);
		
		//running function
		currentStats = checkStats();
		createInterface(tasks, prizes);
		checkBoxPossible();
		

//		removingInterface(tasksList);
		
		

	}
public static void createInterface(ArrayList<points> tasks, ArrayList<points> prizes) {
		
		// - Basic JFrame Set up
		  mainFrame = new screen("Level Print");
	       
			// - Panel set up
	       JPanel submitPanel = new JPanel();

	       
	       // - points handling
	       JLabel label = new JLabel("Current Number of Points: " + currentStats);
	       mainFrame.topPanel.add(label);
	       
	       //task handling
	       JLabel taskLabel = new JLabel("Tasks");
	       checkboxHandling(tasksBox, tasks, taskLabel);
	       
	       // prize handling
	       JLabel prizeLabel = new JLabel ("Prizes");
	       checkboxHandling(prizesBox, prizes, prizeLabel);

	       // - submit button set up
	       JButton addButton = new JButton ("Add an Entry");
	       JButton removeButton = new JButton("Remove an Entry");
	       mainFrame.bottomPanel.add(addButton); mainFrame.bottomPanel.add(removeButton);
	       mainFrame.submitButton.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	            	printOut(tasks);
	            	updateStats(tasksList);
	            	label.setText("Current Number of Points: " + currentStats);
	            	checkBoxPossible();
	            	
	            }
	       });
	       
	       // - add handling
	      addButton.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	     	       addingInterface();
	     	       updateStats(tasksList);
	     	       

	     	       
	            }
	       });
	       
	       //-remove handling
	       removeButton.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	            	removingInterface( tasksList);
	            }
	       });

	       
	       
	       // -adding panels/end of frame set up
	        mainFrame.leftPanel.add(tasksBox, BorderLayout.WEST); mainFrame.rightPanel.add(prizesBox, BorderLayout.EAST);   
	       mainFrame.pack();
	        }

	public static void removingInterface(ArrayList<points> lst) {
		screen frame = new screen("Adjust Inputs");
		for (points x: lst) {
			x.cb.setEnabled(true);
			frame.centerBox.add(x.cb);
		}
		  frame.submitButton.setText("Remove entries");
	       frame.pack();
	}

	public static void addingInterface() {
		// - Basic JFrame Set up
		  screen frame = new screen("Adding Tasks and Rewards");
	      
	       // - General object set up
	       JTextField taskInput =new JTextField(10);
	       JTextField numInput  =new JTextField(10);
	       JLabel label = new JLabel("Please Enter the Task/Reward and # of Points");
	       
	       // Button creation/handling
	       ButtonGroup buttonGroup = new ButtonGroup();
	       JRadioButton addTaskButton = new JRadioButton("Task");
	       JRadioButton addRewardButton = new JRadioButton("Reward");
	       buttonGroup.add(addRewardButton); buttonGroup.add(addTaskButton);
	       
	       frame.submitButton.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	            	System.out.println("clicked");
	            	if (addTaskButton.isEnabled()) {
	            		addValue("tasks.txt", taskInput.getText(),numInput.getText()); 
	            	}
	            	else {
	            		addValue("prizes.txt",  taskInput.getText(),numInput.getText()) ;
	            		
	            	}

	            }
	       });
	       
	       // - adding section
	       frame.topPanel.add(label);
	       frame.leftPanel.add(taskInput); frame.leftPanel.add(numInput);
	       frame.rightPanel.add(addTaskButton); frame.rightPanel.add(addRewardButton);
	       
	       frame.pack();
	       
	}

		public static void checkBoxPossible() {
			for (points x: tasksList) {
				if (x.cost > currentStats && x.isPos == false) {
					x.cb.setEnabled(false);
				}
				else {
					x.cb.setEnabled(true);
				}
				x.cb.setSelected(false);
			}
		}
			
		public static void checkboxHandling(Box box, ArrayList<points>lst, JLabel label) {
			box.add(label);
			for (int x = 0; x < lst.size(); x++) {
				box.add(lst.get(x).cb);
			}
			
		}
	public static void updateBoxes(screen frame, ArrayList<points> lst) {
		Box newBox =  Box.createVerticalBox();
		for (points x: lst) {
			newBox.add(x.cb);
		}
		frame.centerBox = newBox;
	}
		

	public static void updateStats(ArrayList<points> lst) {
		for (int x = 0; x < lst.size(); x++) {
			points curVal = lst.get(x);
			
			if (curVal.cb.isSelected()) {
				if(curVal.isPos) {
					currentStats += curVal.cost;
				}
				else {
					currentStats -= curVal.cost;
				}
				 updateFile(currentStats.toString());
			}

		}
	}
	
	public static void addValue(String textFile, String task, String num) {
		String text =  task +"-"+num;
		Writer output;
		try {
			output = new BufferedWriter(new FileWriter(textFile, true));
			output.append(System.getProperty("line.separator"));
			output.append(text);
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (textFile == "curStats.txt"){
				
		}
		else {
			
		}
	     
		

		
	}
	
	public void removeValue() {
		
	}

	

	
	
	// convenience print function
	public static void printOut(ArrayList<points> lst) {
		for (int x = 0; x < lst.size(); x++) {
			System.out.println(lst.get(x));
			System.out.println(lst.get(x).cb.isSelected());
			System.out.println(lst.get(x).cost);
		}
	}
	
	public static void printOutBox(ArrayList<JCheckBox> lst) {
		for (int x = 0; x < lst.size(); x++) {
			System.out.println(lst.get(x).getText());
			Boolean isOn = lst.get(x).isSelected();
			System.out.println(isOn);
		}
	}
	
	// function to set new stats
	public static void updateFile(String newNum) {
		try {
			FileWriter myWriter = new FileWriter("curStats.txt");
			myWriter.write(newNum);
		    myWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	// - quick function to pull stats
	public static Float checkStats() {
		Float stats = 0f;
		try {
			File newFile = new File("curStats.txt");
			Scanner scanner = new Scanner(newFile);
			String curLine = scanner.nextLine();
			stats = Float.parseFloat(curLine);

			} catch (FileNotFoundException e) {
			e.printStackTrace();
			}
		
	return stats;
	}
	
	
	// - read in function for both tasks and prizes
	public static ArrayList<points> fileReadIn(String fileName){
		ArrayList<points> tasks = new ArrayList<points>();
		try {
			File newFile = new File(fileName);
			Scanner scanner = new Scanner(newFile);
			Boolean type = Boolean.parseBoolean(scanner.nextLine());
			

			while (scanner.hasNextLine()) {
				// - Going through Lines
				String curLine = scanner.nextLine();
				String[] split = curLine.split("-");
				points curTask = new points(split[0], Float.parseFloat(split[1]), type);
				tasks.add(curTask);
								
				}
			scanner.close();
			} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return tasks;
	}

}
