package gamification;

public class points {
	String name;
	Float cost;
	Boolean isPos;
	
	public points(String Name, Float Cost, Boolean Type) {
		name = Name;
		cost = Cost;
		isPos = Type;
	}
	
	public String toString() {
		return name;
	}
}
