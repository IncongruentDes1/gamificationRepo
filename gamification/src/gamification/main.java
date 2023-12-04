package gamification;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class main {
	public static Float currentStats= 0F;

	public static void main(String[] args) {
		ArrayList<points> tasks = fileReadIn("tasks.txt");
		ArrayList<points> prizes = fileReadIn("prizes.txt");
		currentStats = checkStats();
		System.out.println(currentStats);
		updatestats("8");
		currentStats = checkStats();
		System.out.println(currentStats);
		
		createInterface();

	}
public static void createInterface() {
		
		// - Basic JFrame Set up
		   JFrame frame = new JFrame("Level Print");
	       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	       
	       JPanel pane = new JPanel();
	       JLabel label = new JLabel("Current Number of Points: " + currentStats);

	       frame.add(pane);
	       pane.add(label);
	       
	       
	       frame.pack();
	        frame.setVisible(true);
	        }
	
public static void updateTasks() {
	
}

public static void updatePrizes() {
	
}

	
	
	// convenience print function
	public static void printOut(ArrayList<points> lst) {
		for (int x = 0; x < lst.size(); x++) {
			System.out.println(lst.get(x));
		}
	}
	
	// function to set new stats
	public static void updatestats(String newNum) {
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

			while (scanner.hasNextLine()) {
				// - Going through Lines
				String curLine = scanner.nextLine();
				String[] split = curLine.split("-");
				points curTask = new points(split[0], Float.parseFloat(split[1]));
				tasks.add(curTask);
								
				}
			scanner.close();
			} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return tasks;
	}

}
