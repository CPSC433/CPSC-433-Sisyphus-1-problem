package cpsc433;

import java.util.ArrayList;

public class Room 
{
	private String name;
	private ArrayList<Person> occupants;
	private ArrayList<Room> close_to;
	private boolean isSmall;
	
	
	public Room(String roomName) 
	{
		name = roomName;
		occupants.add(null);
		occupants.add(null);
	}
	
	public boolean getIsSmall()
	{
		return isSmall;
	}
	
	public void setIsSmall(boolean b)
	{
		isSmall = b;
	}
	
	public boolean isShared() {
		if ((occupants.get(0) == null) || (occupants.get(1) == null))
			return false;
		else
			return true;
	}
	
	public Person getOther(Person person1) {
		if (num_occupants == 2) {
			if (occupants.get(0) != person1)
				return occupants.get(0);
			else
				return occupants.get(1);
		}
		else
			
	}
}
