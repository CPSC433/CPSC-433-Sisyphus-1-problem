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
	
	
	public Room(String roomName) 
	{
		name = roomName;
		occupants = new ArrayList<Person>();
		close_to = new ArrayList<Room>();
		occupants.add(null);
		occupants.add(null);
		isSmall = false;
		isMedium = false;
		isLarge = false;
	}
	
	//get room name
	public String getName()
	{
		return name;
	}
	
	//gets the array size of the close room array
	public int getCloseRoomsize()
	{
		return close_to.size();
	}
	
	//gets the specified close room
	public Room getClose(int i)
	{
		return close_to.get(i);
	}
	
	//add a close room to the close room array
	public void setClose(Room room) {
		close_to.add(room);
	}
	
	//getter for isSmall boolean
	public boolean getIsSmall()
	{
		return isSmall;
	}
	
	//setter for is small boolean
	public void setIsSmall(boolean b)
	{
		isSmall = b;
	}
	
	//getter for isMediuam boolean
	public boolean getIsMedium()
	{
		return isMedium;
	}
	
	//setter for isMedium boolean
	public void setIsMedium(boolean b)
	{
		isMedium = b;
	}
	
	//getter of isLarge boolean
	public boolean getIsLarge()
	{
		return isLarge;
	}
	
	//setter for is Large boolean
	public void setIsLarge(boolean b)
	{
		isLarge = b;
	}
	
	//sets the person into the first available slot
	public void setOccupant(Person person) {
		if (occupants.get(0) == null)
			occupants.set(0, person);
		else if (occupants.get (1) == null)
			occupants.set(1, person);
	}
	
	//gets the occupant in the specified slot 
	public Person getOccupant(int i) {
		Person person = occupants.get(i);
		return person;
	}
	
	//clears the occupants in a room
	public void clearOccupants() {
		occupants.set(0, null);
		occupants.set(1, null);
	}
	

	//returns true is a room is empty otherwise false
	public boolean isEmpty() {
		if ((occupants.get(0) == null) && (occupants.get(1) == null))
			return true;
		else return false;
	}
	
	//returns true is room is shared otherwise false
	public boolean isShared() {
		if ((occupants.get(0) != null) && (occupants.get(1) != null))
			return true;
		
		return false;
	}
	
	//returns the other person in a room
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
