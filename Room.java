package cpsc433;

import java.util.ArrayList;

public class Room 
{
	private String name;
	private ArrayList<Person> occupants;
	private ArrayList<Room> close_to;
	private boolean isSmall;
	private boolean isMedium;
	private boolean isLarge;
	private int num_occupants;
	
	
	public Room(String roomName) 
	{
		name = roomName;
		occupants = new ArrayList<Person>();
		close_to = new ArrayList<Room>();
		occupants.add(null);
		occupants.add(null);
		num_occupants = 0;
		isSmall = false;
		isMedium = false;
		isLarge = false;
	}
	
	public String getName()
	{
		return name;
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
	
	public boolean getIsMedium()
	{
		return isMedium;
	}
	
	public void setIsMedium(boolean b)
	{
		isMedium = b;
	}
	
	public boolean getIsLarge()
	{
		return isLarge;
	}
	
	public void setIsLarge(boolean b)
	{
		isLarge = b;
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
		if ((occupants.get(0) != null) && (occupants.get(1) != null))
			return true;
		
		return false;
	}
	
	public Person getOther(Person person1) {
		if (occupants.size() == 2) {
			if (occupants.get(0) != person1)
				return occupants.get(0);
			else
				return occupants.get(1);
		}
		return null;	
	}
}
