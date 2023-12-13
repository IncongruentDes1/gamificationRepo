package gamification;

import javax.swing.JCheckBox;

public class  entries {
	String name;
	Float cost;
	Boolean isPos;
	JCheckBox cb;
	
	public entries(String Name, Float Cost, Boolean Type) {
		name = Name;
		isPos = Type;
		
		if (isPos == false) {
			cost = Cost - (Cost *2);
		}
		else {
			cost = Cost;
		}
		cb = new JCheckBox(name + " points: " + cost);
	}
	
	public String toString() {
		return name;
	}
}
