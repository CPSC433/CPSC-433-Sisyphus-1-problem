package cpsc433;

import java.util.ArrayList;

public class Room 
{
	private String name;
	private ArrayList<Person> occupants;
	private ArrayList<Room> close_to;
	private boolean isSmall;
	private int num_occupants;
	
	
	public Room(String roomName) 
	{
		name = roomName;
		occupants.add(null);
		occupants.add(null);
		num_occupants = 0;
	}
	
	public int getCloseRoomsize()
	{
		return close_to.size();
	}
	
	public Room getClose(int i)
	{
		return close_to.get(i);
	}
	
	public void setClose(Room room) {
		close_to.add(room);
	}
	
	public boolean getIsSmall()
	{
		return isSmall;
	}
	
	public void setIsSmall(boolean b)
	{
		isSmall = b;
	}
	
	public void setOccupant(Person person) {
		if (occupants.get(0) == null)
			occupants.set(0, person);
		else if (occupants.get (1) == null)
			occupants.set(1, person);
	}
	
	public Person getOccupant(int i) {
		Person person = occupants.get(i);
		return person;
	}
	
	// Maybe we should make a function that instead returns # of people in the room to replace the two functions below
	
	public boolean isEmpty() {
		if ((occupants.get(0) == null) && (occupants.get(1) == null))
			return true;
		else return false;
	}
	
	public boolean isShared() {
		if ((occupants.get(0) == null) || (occupants.get(1) == null))
			return false;
		else if (this.isEmpty())
			return false;
		else return true;
	}
	
	public Person getOther(Person person1) {
		if (num_occupants == 2) {
			if (occupants.get(0) != person1)
				return occupants.get(0);
			else
				return occupants.get(1);
		}
		return null;	
	}
}
