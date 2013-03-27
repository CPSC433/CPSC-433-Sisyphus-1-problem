package cpsc433;

import java.util.ArrayList;

public class Room 
{
	private String name;
	private int num_occupants;
	private ArrayList<Person> occupants;
	private ArrayList<Room> close_to;
	
	public Room(String roomName) 
	{
		name = roomName;
	}
	
	
}
