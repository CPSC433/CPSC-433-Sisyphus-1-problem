package cpsc433;

import java.util.TreeSet;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Random;

import cpsc433.Predicate.ParamType;

// Every thing within this class sets up our search environment
public class Environment 
extends PredicateReader
implements SisyphusPredicates 
{
	public Environment(String name) {
		super(name);
		for(int i = 0; i < 16; i++)
		{
			con[i] = 0;
		}
	}
	
	enum Role
	{
		eResearcher,
		eSecretary,
		eManager,
		eNone
	}
	
	private Predicate pred;
	private int numEmployees = 0;
	private int numManagers = 0;
	private int numRooms = 0;
	private int[] con = new int[16];
	
	// Stores all remaining predicates interpreted from input file
	public ArrayList<Predicate> arrPredicates = new ArrayList<Predicate>();
	
	// Stores all employee predicates interpreted from input file
	public ArrayList<Predicate> arrEmployees = new ArrayList<Predicate>();
	
	// Stores all room predicates interpreted from input file
	public ArrayList<Predicate> arrRoomPreds = new ArrayList<Predicate>();
	
	// Stores all group predicates interpreted from input file
	public ArrayList<Predicate> arrGroups = new ArrayList<Predicate>();
	
	// Stores all project predicates interpreted from input file
	public ArrayList<Predicate> arrProjects = new ArrayList<Predicate>();
	
	// Stores all room size predicates interpreted from input file
	public ArrayList<Predicate> arrRoomSizes = new ArrayList<Predicate>();
	
	// Stores all room proximity predicates interpreted from input file
	public ArrayList<Predicate> arrRoomProximity = new ArrayList<Predicate>();
	
	// Stores all project description predicates interpreted from input file
	public ArrayList<Predicate> arrProjectDescriptions = new ArrayList<Predicate>();
	
	// Stores all people in our problem
	public ArrayList<Person> arrPeople = new ArrayList<Person>();
	public ArrayList<Person> arrResearchers = new ArrayList<Person>();
	public ArrayList<Person> arrSmokers = new ArrayList<Person>();
	public ArrayList<Person> arrManagers = new ArrayList<Person>();
	
	public ArrayList<Room> arrRooms = new ArrayList<Room>();
	
	public Map<Person,Room> assignmentMap = new HashMap<Person, Room>();
	public Map<Person,Room> assignmentMap2 = new HashMap<Person, Room>();
	
	public void a_person(String p)
	{
		String newPredicate = "person("+p+")";
		addNewPredicate( newPredicate );
		
		addPerson( p );
	}
	public boolean e_person(String p){return false;}
	
	public void a_secretary(String p)
	{
		a_person(p);
		
		String newPredicate = "secretary("+p+")";
		int ret = addNewEmployee( newPredicate );
		
		int personIndex = findPersonIndex( p );
		arrPeople.get(personIndex).setIsSecretary(true);
		
		if( ret == 1 )
			numEmployees++;
	}
	public boolean e_secretary(String p){return false;}
	
	public void a_researcher(String p)
	{
		a_person(p);
		
		String newPredicate = "researcher("+p+")";
		int ret = addNewEmployee( newPredicate );
		
		int personIndex = findPersonIndex( p );
		arrPeople.get(personIndex).setIsResearcher(true);
		
		if( ret == 1 )
			numEmployees++;
	}
	public boolean e_researcher(String p){return false;}
	
	public void a_manager(String p)
	{
		a_person(p);
		
		String newPredicate = "manager("+p+")";
		int ret = addNewEmployee( newPredicate );
		
		int personIndex = findPersonIndex( p );
		arrPeople.get(personIndex).setIsManager(true);
		
		if( ret == 1 )
			numManagers++;
	}
	public boolean e_manager(String p){return false;}
	
	public void a_smoker(String p)
	{
		a_person(p);
		
		String newPredicate = "smoker("+p+")";
		addNewPredicate( newPredicate );
		
		int personIndex = findPersonIndex( p );
		arrPeople.get(personIndex).setIsSmoker(true);
	}
	public boolean e_smoker(String p){return false;}
	
	public void a_hacker(String p)
	{
		a_person(p);
		
		String newPredicate = "hacker("+p+")";
		addNewPredicate( newPredicate );
		
		int personIndex = findPersonIndex( p );
		arrPeople.get(personIndex).setIsHacker(true);
	}
	public boolean e_hacker(String p){return false;}
	
	public void a_in_group(String p, String grp)
	{
		a_person(p);
		
		a_group(grp);
		
		int personIndex = findPersonIndex( p );
		arrPeople.get(personIndex).setGroup(p);
		
		String newPredicate = "in-group("+p+", "+grp+")";
		addNewPredicate( newPredicate );
	}
	public boolean e_in_group(String p, String grp){return false;}
	public void a_group(String p, String grp)
	{
		a_person(p);
		
		a_group(grp);
		
		int personIndex = findPersonIndex( p );
		arrPeople.get(personIndex).setGroup(grp);
		
		String newPredicate = "group("+p+", "+grp+")";
		addNewPredicate( newPredicate );
	}
	public boolean e_group(String p, String grp){return false;}
	
	public void a_in_project(String p, String prj)
	{
		a_person(p);
		
		a_project(prj);
		
		int personIndex = findPersonIndex( p );
		arrPeople.get(personIndex).setProject(prj);
		
		String newPredicate = "in-project("+p+", "+prj+")";
		addNewPredicate( newPredicate );
	}
	public boolean e_in_project(String p, String prj){return false;}
	public void a_project(String p, String prj)
	{
		a_person(p);
		
		a_project(prj);
		
		int personIndex = findPersonIndex( p );
		arrPeople.get(personIndex).setProject(prj);
		
		String newPredicate = "project("+p+", "+prj+")";
		addNewPredicate( newPredicate );
	}
	public boolean e_project(String p, String prj){return false;}
	
	public void a_heads_group(String p, String grp)
	{
		int personIndex = findPersonIndex( p );
		if( personIndex == -1 )
			numManagers++;
		
		a_person(p);
		
		a_group(p, grp);
		
		personIndex = findPersonIndex( p );
		arrPeople.get(personIndex).setIsGroupHead(true);
		
		String newPredicate = "heads-group("+p+", "+grp+")";
		addNewPredicate( newPredicate );
	}
	public boolean e_heads_group(String p, String grp){return false;}
	
	public void a_heads_project(String p, String prj)
	{
		int personIndex = findPersonIndex( p );
		if( personIndex == -1 )
			numManagers++;
		
		a_person(p);
		
		a_project(p, prj);
		
		personIndex = findPersonIndex( p );
		arrPeople.get(personIndex).setIsProjectHead(true);
		
		String newPredicate = "heads-project("+p+", "+prj+")";
		addNewPredicate( newPredicate );
	}
	public boolean e_heads_project(String p, String prj){return false;}
	
	public void a_works_with(String p, TreeSet<Pair<ParamType,Object>> p2s)
	{
		a_person(p);
		
		Iterator iter = p2s.iterator();
		
		String p3 = "works-with("+p+", {";
		
		boolean bFirst = true;
		
		while( iter.hasNext() )
		{
			if( !bFirst )
				p3 += ", ";
			else
				bFirst = false;
			
			Pair pair = (Pair)iter.next();
			String p2 = (String)pair.getValue();
			
			a_person(p2);
			
			a_works_with(p, p2);
			
			p3 += p2;
		}
		p3 += "})";
		
		addNewPredicate( p3 );
	}
	public boolean e_works_with(String p, TreeSet<Pair<ParamType,Object>> p2s){return false;}
	
	public void a_works_with(String p, String p2)
	{
		a_person(p);
		
		a_person(p2);
		
		String newPredicate = "works-with("+p+", "+p2+")";
		addNewPredicate( newPredicate );
		
		int personIndex = findPersonIndex( p );
		arrPeople.get(personIndex).addWorksWith(p2);
		
		newPredicate = "works-with("+p2+", "+p+")";
		addNewPredicate( newPredicate );
		
		personIndex = findPersonIndex( p2 );
		arrPeople.get(personIndex).addWorksWith(p);
	}
	public boolean e_works_with(String p, String p2){return false;}
	
	public void a_assign_to(String p, String room) throws Exception
	{
		a_person(p);
		
		a_room(room);
		
		String newPredicate = "assigned-to("+p+", "+room+")";
		addNewPredicate( newPredicate );
	}
	public boolean e_assign_to(String p, String room){return false;}
	
	// ROOMS
	public void a_room(String r)
	{
		String newPredicate = "room("+r+")";
		int ret = addNewRoom( newPredicate );
		
		if( ret == 1 )
		{
			numRooms++;
			addRoom( r );
		}
	}	
	public boolean e_room(String r){return false;}
	
	public void a_close(String room, String room2)
	{
		a_room(room);
		
		a_room(room2);
		
		String newPredicate = "close("+room+", "+room2+")";
		addNewRoomProximity( newPredicate );
		
		int roomIndex = findRoomIndex( room );
		Room newRoom = findRoom( room2 );
		arrRooms.get(roomIndex).setClose(newRoom);
		
		newPredicate = "close("+room2+", "+room+")";
		addNewRoomProximity( newPredicate );
		
		roomIndex = findRoomIndex( room2 );
		newRoom = findRoom( room );
		arrRooms.get(roomIndex).setClose(newRoom);
	}	
	public boolean e_close(String room, String room2){return false;}
	
	public void a_close(String room, TreeSet<Pair<ParamType,Object>> set)
	{
		a_room(room);
		
		Iterator iter = set.iterator();
		
		String r3 = "close("+room+", {";
		
		boolean bFirst = true;
		
		while( iter.hasNext() )
		{
			if( !bFirst )
				r3 += ", ";
			else
				bFirst = false;
			
			Pair pair = (Pair)iter.next();
			String r2 = (String)pair.getValue();
			
			a_room(r2);
			
			a_close(room, r2);
			
			r3 += r2;
		}
		r3 += "})";
		
		addNewPredicate( r3 );
	}
	public boolean e_close(String room, TreeSet<Pair<ParamType,Object>> set){return false;}
	
	public void a_large_room(String r)
	{
		a_room(r);
		
		String newPredicate = "large-room("+r+")";
		addNewRoomSize( newPredicate );
		
		int roomIndex = findRoomIndex( r );
		arrRooms.get(roomIndex).setIsLarge(true);
	}
	public boolean e_large_room(String r){return false;}
	
	public void a_medium_room(String r)
	{
		a_room(r);
		
		String newPredicate = "medium-room("+r+")";
		addNewRoomSize( newPredicate );
		
		int roomIndex = findRoomIndex( r );
		arrRooms.get(roomIndex).setIsMedium(true);
	}
	public boolean e_medium_room(String r){return false;}
	
	public void a_small_room(String r)
	{
		a_room(r);
		
		String newPredicate = "small-room("+r+")";
		addNewRoomSize( newPredicate );
		
		int roomIndex = findRoomIndex( r );
		arrRooms.get(roomIndex).setIsSmall(true);
	}
	public boolean e_small_room(String r){return false;}
	
	// GROUPS
	public void a_group(String g)
	{		
		String newPredicate = "group("+g+")";
		addNewGroup( newPredicate );
	}
	public boolean e_group(String g){return false;}
	
	// PROJECTS
	public void a_project(String p)
	{
		String newPredicate = "project("+p+")";
		addNewProject( newPredicate );
	}
	public boolean e_project(String p){return false;}
	
	public void a_large_project(String prj)
	{
		String newPredicate = "large-project("+prj+")";
		addNewProjectDescription( newPredicate );
		
		for( int i = 0; i < arrPeople.size(); i++ )
		{
			if( prj.equals(arrPeople.get(i).getProject()) )
				arrPeople.get(i).setIsProjectLarge( true );
		}
	}
	public boolean e_large_project(String prj){return false;}
	
	// Utility function for adding a new predicate to the array of predicates
	private int addNewPredicate( String newPredicate )
	{
		pred = makePredicate(newPredicate);
		for( int i = 0; i < arrPredicates.size(); i++ )
		{
			if( pred.toString().compareTo(arrPredicates.get(i).toString()) == 0 )
				return 0;
		}
		arrPredicates.add(pred);
		
		return 1;
	}
	
	private int addNewEmployee( String newPredicate )
	{
		pred = makePredicate(newPredicate);
		for( int i = 0; i < arrEmployees.size(); i++ )
		{
			if( pred.toString().compareTo(arrEmployees.get(i).toString()) == 0 )
				return 0;
		}
		arrEmployees.add(pred);
		
		return 1;
	}
	
	private int addNewRoom( String newPredicate )
	{
		pred = makePredicate(newPredicate);
		for( int i = 0; i < arrRoomPreds.size(); i++ )
		{
			if( pred.toString().compareTo(arrRoomPreds.get(i).toString()) == 0 )
				return 0;
		}
		arrRoomPreds.add(pred);
		
		return 1;
	}
	
	private int addNewGroup( String newPredicate )
	{
		pred = makePredicate(newPredicate);
		for( int i = 0; i < arrGroups.size(); i++ )
		{
			if( pred.toString().compareTo(arrGroups.get(i).toString()) == 0 )
				return 0;
		}
		arrGroups.add(pred);
		
		return 1;
	}
	
	private int addNewProject( String newPredicate )
	{
		pred = makePredicate(newPredicate);
		for( int i = 0; i < arrProjects.size(); i++ )
		{
			if( pred.toString().compareTo(arrProjects.get(i).toString()) == 0 )
				return 0;
		}
		arrProjects.add(pred);
		
		return 1;
	}
	
	private int addNewRoomSize( String newPredicate )
	{
		pred = makePredicate(newPredicate);
		for( int i = 0; i < arrRoomSizes.size(); i++ )
		{
			if( pred.toString().compareTo(arrRoomSizes.get(i).toString()) == 0 )
				return 0;
		}
		arrRoomSizes.add(pred);
		
		return 1;
	}
	
	private int addNewRoomProximity( String newPredicate )
	{
		pred = makePredicate(newPredicate);
		for( int i = 0; i < arrRoomProximity.size(); i++ )
		{
			if( pred.toString().compareTo(arrRoomProximity.get(i).toString()) == 0 )
				return 0;
		}
		arrRoomProximity.add(pred);
		
		return 1;
	}
	
	private int addNewProjectDescription( String newPredicate )
	{
		pred = makePredicate(newPredicate);
		for( int i = 0; i < arrProjectDescriptions.size(); i++ )
		{
			if( pred.toString().compareTo(arrProjectDescriptions.get(i).toString()) == 0 )
				return 0;
		}
		arrProjectDescriptions.add(pred);
		
		return 1;
	}
	
	public boolean IsSolution()
	{
		if( (((numRooms - numManagers) * 2) - numEmployees) >= 0 )
			return true;
		else
			return false;
	}
	
	public Person findPerson( String name )
	{
		Person newPerson = null;
		for( int i = 0; i < arrPeople.size(); i++ )
		{
			newPerson = arrPeople.get(i);
			if( newPerson.getName().equals(name) )
				return newPerson;
		}
		return null;
	}
	
	public int findPersonIndex( String name )
	{
		Person newPerson = null;
		for( int i = 0; i < arrPeople.size(); i++ )
		{
			newPerson = arrPeople.get(i);
			if( newPerson.getName().equals(name) )
				return i;
		}
		return -1;
	}
	
	public void addPerson( String name )
	{
		if( findPerson( name ) == null )
		{
			Person newPerson = new Person( name );
			arrPeople.add( newPerson );
		}
	}	
	
	public Room findRoom( String name )
	{
		Room newRoom = null;
		for( int i = 0; i < arrRooms.size(); i++ )
		{
			newRoom = arrRooms.get(i);
			if( newRoom.getName().equals(name) )
				return newRoom;
		}
		return null;
	}
	
	public int findRoomIndex( String name )
	{
		Room newRoom = null;
		for( int i = 0; i < arrRooms.size(); i++ )
		{
			newRoom = arrRooms.get(i);
			if( newRoom.getName().equals(name) )
				return i;
		}
		return -1;
	}
	
	public void addRoom( String name )
	{
		if( findRoom( name ) == null )
		{
			Room newRoom = new Room( name );
			arrRooms.add( newRoom );
		}
	}	
	
	
	/**Utility Function for environment
	 
	 */
	
	
	public int utility (Person person, Room room, boolean end_util)
	{
		int util = 0;
		//all constraints that require two people in a room are within this if statement
		if (room.isShared())
		{
			util -= 4; //since room is shared add constriant 14
			if (end_util) con[13]++; 
			if (person.getIsSmoker() && !(room.getOther(person).getIsSmoker())) {util -= 50; if (end_util) con[10]++;} //if a person is a smoker and is in a room with a non smoker add constraint 11
			if (room.getIsSmall()) {util -= 25; if (end_util) con[15]++;} //if a person shares a small room, add constraint 16
			if (!person.getProject().equals("") && person.getProject().equals(room.getOther(person).getProject())) {util -= 7; if (end_util) con[11]++;} //if a person is in a project and shares a room with another person in the same project add constraint 12
			if (!person.worksWith(room.getOther(person))) {util -= 3; if (end_util) con[14]++;} //if a person does not share a room with a person they work with add constraint 15
			if (!person.getIsSecretary())
			{
				if (person.getIsHacker() && !room.getOther(person).getIsHacker()) {util -= 2; if (end_util) con[12]++;} //add constraint 13 if a person is a hacker/non-hacker
				if (!person.getIsHacker() && room.getOther(person).getIsHacker()) {util -= 2; if (end_util) con[12]++;} //and does not share a room with a hacker/non-hacker
			}
		}
		
		//add constraint 4 if a a Secretary does not share a room with a secrectary
		if (person.getIsSecretary() && room.isShared() && !room.getOther(person).getIsSecretary()) {util -= 5; if (end_util) con[3]++;} 
		
		//if a person is not a manager check to see if employee is cloes to the manager
		if (!person.getIsManager()) 
		{
			boolean closeToManager = false;
			Room temp;
			for (int i = 0; i < room.getCloseRoomsize() && !closeToManager; i++) //check all close rooms for a manager, stop if a manager is found or if all rooms have been checked
			{
				temp = room.getClose(i);
				if (!temp.isShared() && !temp.isEmpty())
				{
					if (temp.getOccupant(0).getIsManager()) closeToManager = true; //check only the first person element in the room since manager do not share rooms
				}
			}
			if (!closeToManager) {util -= 2; if (end_util) con[1]++;} //if no manager is in a close room add constraint 2
		}
		
		 //if a person is not a group head check to see if the employee is close to a group head
		if (!person.getIsGroupHead()) 
		{
			boolean closeToHead = false;
			Room temp;
			for (int i = 0; i < room.getCloseRoomsize() && !closeToHead; i++) //check all close rooms for a group head, stop their group head is not found or if all rooms have been checked
			{
				temp = room.getClose(i);
				if (!temp.isShared() && !temp.isEmpty())
				{
					if (temp.getOccupant(0).getIsGroupHead() && person.getGroup().equals(temp.getOccupant(0).getGroup())) closeToHead = true; //check only the first person element in the room since group heads do not share rooms
				}
			}
			if (!closeToHead) {util -= 2; if (end_util) con[6]++;} // add constraint 7 if the person is not close to their group head
			if (!closeToHead && person.getIsManager()) {util -= 20; if (end_util) con[5]++;} // add constriant 6 if the person is a manager and is not close to their group head
			if (!closeToHead && person.getIsProjectHead() && person.getIsProjectLarge() ) {util -= 10; if (end_util) con[9]++;} // add constriant 10 if the user is a project lead and not close to a group head
		}
		
		//this is essentially the same code as for the manager loop
		if (!person.getIsProjectHead()) 
		{
			boolean closeToHead = false;
			boolean isProjectHead = false;
			Room temp;
			for( int i = 0; i < arrPeople.size(); i++ )
			{
				if( arrPeople.get(i).getIsProjectHead() && arrPeople.get(i).getProject().equals(person.getProject()) )
					isProjectHead = true;	
			}
			for (int i = 0; i < room.getCloseRoomsize() && !closeToHead; i++)
			{
				temp = room.getClose(i);
				if (!temp.isEmpty() && temp.getOccupant(0).getIsProjectHead() && person.getProject().equals(temp.getOccupant(0).getProject())) closeToHead = true;
			}
			if (!closeToHead && isProjectHead) {util -= 5; if (end_util) con[7]++;} // add constraint 8 if the person is not close to the project head
		}	
		
		//if a group head does not have a large room add constraint 1
		if (person.getIsGroupHead() && !room.getIsLarge()) {util -= 40; if (end_util) con[0]++;} 
		
		//for a manager, project head and group head check to see if they are close to a secretary
		if (person.getIsGroupHead() || person.getIsProjectHead() || person.getIsManager()) 
		{
			boolean closeToSecretary = false;
			Room temp;
			//loop through all close room and exit loop if a secretary is found or if all close rooms have been searched
			for (int i = 0; i < room.getCloseRoomsize() && !closeToSecretary; i++)
			{
				temp = room.getClose(i);
				if (!temp.isShared() && !temp.isEmpty()) { //if there is only one person check only the first person element
					if (temp.getOccupant(0).getIsSecretary() && person.getGroup().equals(temp.getOccupant(0).getGroup())) closeToSecretary = true;
				}
				else if (temp.isShared()) { //if there are two people in the close room check both person elements
					if (temp.getOccupant(0).getIsSecretary() && person.getGroup().equals(temp.getOccupant(0).getGroup())) closeToSecretary = true;
					if (temp.getOccupant(1).getIsSecretary() && person.getGroup().equals(temp.getOccupant(0).getGroup())) closeToSecretary = true;
				}
			}
			if (!closeToSecretary && person.getIsGroupHead()) {util -= 30; if (end_util) con[2]++;} //add constriant 3 if a group head is not close to a secretary
			if (!closeToSecretary && person.getIsManager()) {util -= 20; if (end_util) con[4]++;} //add constriant 5 if a manager ...
			if (!closeToSecretary && person.getIsProjectHead() && person.getIsProjectLarge()) {util -=10; if (end_util) con[8]++;} //add constriant 9 if a project head ...
		}
		return util;
	}
	
	public void findSolution()
	{
		for( int i = 0; i < arrPeople.size(); i++ )
		{
			// create array to hold indices of rooms with the same highest util value, to randomly choose from at the end
			ArrayList<Integer> rooms_w_same_util = new ArrayList<Integer>();
			
			Person currentPerson = arrPeople.get(i);
			ArrayList<Integer> arrUtil = new ArrayList<Integer>();
			for( int j = 0; j < arrRooms.size(); j++ )
			{
				assignmentMap.put(currentPerson, arrRooms.get(j));
				if( (currentPerson.getIsManager() || currentPerson.getIsGroupHead() || currentPerson.getIsProjectHead()) && !arrRooms.get(j).isEmpty() ){ // also for project heads and group heads?
					arrUtil.add(-2147483648); // essentially -infinity (lowest value int can take)
				}
				else {
					arrUtil.add(calcTotalUtility(false));	
				}
			}
			//this is the control for the path we wish to take
			int highestUtil = -2147483648; // highestUtil is -infinity
			int index = 0;
			for( int j = 0; j < arrUtil.size(); j++ )
			{
				int util = arrUtil.get(j);
				if( util > highestUtil )  //our first control is to take the room configuration with the highest utility
				{
					highestUtil = util;
					index = j;
					// clear array because new highest util has been found
					rooms_w_same_util.clear();
					// set first element in array to be index of room
					rooms_w_same_util.add(j);
				}
				else if (util == highestUtil /*&& newRoom == newRoomFlag*/){ //if the Utility is the same then add the
					rooms_w_same_util.add(j);			//index to the array of possible room assignments
				}
			}
			
			// If there is more than one room with the same utility to choose from, choose randomly from that list
			if (rooms_w_same_util.size() > 1) {
				Random gen = new Random();
				index = gen.nextInt(rooms_w_same_util.size());
			}
			assignmentMap.put(currentPerson, arrRooms.get(index));
			arrRooms.get(index).setOccupant( currentPerson );
			
			//System.out.println( currentPerson.getName() + "  " + arrRooms.get(index).getName());
			
			//when a room is full remove it from the available rooms array
			if( currentPerson.getIsManager() || currentPerson.getIsGroupHead() || currentPerson.getIsProjectHead() || arrRooms.get(index).isShared() ) {
				arrRooms.remove(index);
			}
			
		}
	}
	
	//calculates the total utility of the current room arrangement
	public int calcTotalUtility(boolean bCountConstraints)
	{
		int totalUtil = 0;
		//takes our assignment map and iteratively calculates the utility of each person
		Iterator it = assignmentMap.entrySet().iterator();
		while (it.hasNext()) 
		{
			Map.Entry pairs = (Map.Entry)it.next();
			Person person = (Person)pairs.getKey();
			Room room = (Room)pairs.getValue();
			int util = utility( person, room, bCountConstraints );
			totalUtil += util;
		}
		return totalUtil;
	}
	
	//originally here to print the total amount of times a soft constraint has been broken for the final solutions
	public void printConstriants(){
		for (int i = 0; i < 16; i++)
		{
			System.out.println(i + 1 + ":  " + con[i]);
		}
	}
	
	//orders our employees based on what they are
	public void orderEmployees()
	{
		//we order all people initial by their posistion
		for( int i = 0; i < arrPeople.size(); i++ )
		{
			Person currentPerson = arrPeople.get(i);
			if( currentPerson.getIsManager() ||  currentPerson.getIsManager() ||  currentPerson.getIsManager() )
				arrManagers.add( currentPerson ); //we sparate people is they are a manager
			else if( currentPerson.getIsSmoker() )
				arrSmokers.add( currentPerson ); // if not a manager we separate them is they are a smoker
			else
				arrResearchers.add( currentPerson ); // then finally other people
		}
		arrPeople.clear();
		// for our people array we put the managers at the beginning of the array 
		for( int i = 0; i < arrManagers.size(); i++ )
		{
			arrPeople.add(arrManagers.get(i));
		}
		// then smokers
		for( int i = 0; i < arrSmokers.size(); i++ )
		{
			arrPeople.add(arrSmokers.get(i));
		}
		// and finally everyone else
		for( int i = 0; i < arrResearchers.size(); i++ )
		{
			arrPeople.add(arrResearchers.get(i));
		}
		//we do this ordering to try to avoid some of the heavier soft constriants
	}
	
}
