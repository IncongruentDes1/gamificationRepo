package gamification;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JCheckBox;

public class main {
	public static Float currentStats= 0F;
	public static ArrayList<JCheckBox> boxeslist;
	public static ArrayList<points> tasksList;

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
		
		
		

	}
public static void createInterface(ArrayList<points> tasks, ArrayList<points> prizes) {
		
		// - Basic JFrame Set up
		   JFrame frame = new JFrame("Level Print");
	       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	       
			// - Panel set up
	       JPanel pointsPanel = new JPanel();	       
	       JPanel submitPanel = new JPanel();
	       Box tasksBox = Box.createVerticalBox();
	       Box prizesBox = Box.createVerticalBox();
	       
	       // - points handling
	       JLabel label = new JLabel("Current Number of Points: " + currentStats);
	       pointsPanel.add(label);
	       
	       //task handling
	       JLabel taskLabel = new JLabel("Tasks");
	       checkboxHandling(tasksBox, tasks, taskLabel);
	       
	       // prize handling
	       JLabel prizeLabel = new JLabel ("Prizes");
	       checkboxHandling(prizesBox, prizes, prizeLabel);

	       
	       
	       // - submit button set up
	       JButton submitButton = new JButton("Submit");
	       submitPanel.add(submitButton);
	       submitButton.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	            	printOut(tasks);
	            	updateStats(tasksList);
	            	label.setText("Current Number of Points: " + currentStats);
	            	checkBoxPossible();
	            }
	       });
	       
	       
	       // -adding panels/end of frame set up
	       frame.add(pointsPanel, BorderLayout.NORTH); frame.add(tasksBox, BorderLayout.WEST); frame.add(prizesBox, BorderLayout.EAST); frame.add(submitPanel, BorderLayout.SOUTH);
	       
	       frame.pack();
	        frame.setVisible(true);
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
