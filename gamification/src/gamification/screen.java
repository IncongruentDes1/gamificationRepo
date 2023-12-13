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

import java.awt.*;
import javax.swing.*;

public class screen extends JFrame {
	// Parts
	public JPanel topPanel = new JPanel();
	public Box centerBox = Box.createVerticalBox();
	public JPanel leftPanel = new JPanel();
	public JPanel rightPanel = new JPanel();
	public JPanel bottomPanel = new JPanel();
	public JButton submitButton = new JButton("Submit");
	
	
	public screen(String Name) {
		this.setTitle(Name);

//		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(topPanel, BorderLayout.NORTH);
		this.add(centerBox, BorderLayout.CENTER);
		this.add(bottomPanel, BorderLayout.SOUTH);
		this.add(leftPanel, BorderLayout.WEST);
		this.add(rightPanel, BorderLayout.EAST);
		
		bottomPanel.add(submitButton);

	    this.pack();
	    this.setVisible(true);
	}
	
	
	
	
	// - CHECKBOX HANDLING  -------------------------------------------------------------------------------------------------------------------------
	public void addCheckBox(ArrayList<entries> lst) {
		for (entries x: lst) {
			centerBox.add(x.cb);
		}
		this.pack();
	}
	
	public void addCheckBox(entries x) {
		centerBox.add(x.cb);
	}
	
	public void removeCheckBox(entries x) {
		centerBox.remove(x.cb);
	}

}
