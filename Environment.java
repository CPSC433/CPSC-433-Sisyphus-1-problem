package cpsc433;

import java.util.TreeSet;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Random;

import cpsc433.Predicate.ParamType;

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
		a_person(p);
		
		a_group(p, grp);
		
		int personIndex = findPersonIndex( p );
		arrPeople.get(personIndex).setIsGroupHead(true);
		
		String newPredicate = "heads-group("+p+", "+grp+")";
		addNewPredicate( newPredicate );
	}
	public boolean e_heads_group(String p, String grp){return false;}
	
	public void a_heads_project(String p, String prj)
	{
		a_person(p);
		
		a_project(p, prj);
		
		int personIndex = findPersonIndex( p );
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
		if (room.isShared())
		{
			util -= 4;
			con[13]++;
			if (person.getIsSmoker() && !(room.getOther(person).getIsSmoker())) {util -= 50; if (end_util) con[10]++;}
			if (room.getIsSmall()) {util -= 25; if (end_util) con[15]++;}
			if (!person.getProject().equals("") && person.getProject().equals(room.getOther(person).getProject())) {util -= 7; if (end_util) con[11]++;}  
			if (!person.worksWith(room.getOther(person))) {util -= 3; if (end_util) con[14]++;}
			if (!person.getIsSecretary())
			{
				if (person.getIsHacker() && !room.getOther(person).getIsHacker()) {util -= 2; if (end_util) con[12]++;}
				if (!person.getIsHacker() && room.getOther(person).getIsHacker()) {util -= 2; if (end_util) con[12]++;}
			}
		}
		if (person.getIsSecretary() && room.isShared() && !room.getOther(person).getIsSecretary()) {util -= 5; if (end_util) con[3]++;}
		if (!person.getIsManager())
		{
			boolean closeToManager = false;
			Room temp;
			for (int i = 0; i < room.getCloseRoomsize() && !closeToManager; i++)
			{
				temp = room.getClose(i);
				if (!temp.isShared() && !temp.isEmpty())
				{
					if (temp.getOccupant(0).getIsManager()) closeToManager = true;
				}
			}
			if (!closeToManager) {util -= 2; if (end_util) con[1]++;}
		}
		if (!person.getIsGroupHead())
		{
			boolean closeToHead = false;
			Room temp;
			for (int i = 0; i < room.getCloseRoomsize() && !closeToHead; i++)
			{
				temp = room.getClose(i);
				if (!temp.isShared() && !temp.isEmpty())
				{
					if (temp.getOccupant(0).getIsGroupHead() && person.getGroup().equals(temp.getOccupant(0).getGroup())) closeToHead = true;
				}
			}
			if (!closeToHead) {util -= 2; if (end_util) con[6]++;}
			if (!closeToHead && person.getIsManager()) {util -= 20; if (end_util) con[5]++;}
			if (!closeToHead && person.getIsProjectHead() && person.getIsProjectLarge() ) {util -= 10; if (end_util) con[9]++;}
		}
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
			if (!closeToHead && isProjectHead) {util -= 5; if (end_util) con[7]++;}
		}	
		if (person.getIsGroupHead() && !room.getIsLarge()) {util -= 40; if (end_util) con[0]++;}
		if (person.getIsGroupHead() || person.getIsProjectHead() || person.getIsManager())
		{
			boolean closeToSecretary = false;
			Room temp;
			for (int i = 0; i < room.getCloseRoomsize() && !closeToSecretary; i++)
			{
				temp = room.getClose(i);
				if (!temp.isShared() && !temp.isEmpty()) {
					if (temp.getOccupant(0).getIsSecretary() && person.getGroup().equals(temp.getOccupant(0).getGroup())) closeToSecretary = true;
				}
				else if (temp.isShared()) {
					if (temp.getOccupant(0).getIsSecretary() && person.getGroup().equals(temp.getOccupant(0).getGroup())) closeToSecretary = true;
					if (temp.getOccupant(1).getIsSecretary() && person.getGroup().equals(temp.getOccupant(0).getGroup())) closeToSecretary = true;
				}
			}
			if (!closeToSecretary && person.getIsGroupHead()) {util -= 30; if (end_util) con[2]++;}
			if (!closeToSecretary && person.getIsManager()) {util -= 20; if (end_util) con[4]++;}
			if (!closeToSecretary && person.getIsProjectHead() && person.getIsProjectLarge()) {util -=10; if (end_util) con[8]++;}
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
				if( currentPerson.getIsManager() && !arrRooms.get(j).isEmpty() )
					arrUtil.add(-1000);
				
				else
					arrUtil.add(utility( currentPerson, arrRooms.get(j), false ));
			}
			int highestUtil = -1000;
			int index = 0;
			for( int j = 0; j < arrUtil.size(); j++ )
			{
				int util = arrUtil.get(j);
				if( util > highestUtil )
				{
					highestUtil = util;
					index = j;
					// clear array because new highest util has been found
					rooms_w_same_util.clear();
					// set first element in array to be index of room
					rooms_w_same_util.add(j);
				}
				// if utility of room is the same as highest utility, we will randomly choose one at the end
				else if (util == highestUtil){
					rooms_w_same_util.add(j); // add room to array
				}
			}
			
			// If there is more than one room with the same utility to choose from, choose randomly 
			if (rooms_w_same_util.size() > 1) {
				Random gen = new Random(1001);	// seeded for testing -- REMOVE SEED BEFORE FULL IMPLEMENTATION
				index = gen.nextInt(rooms_w_same_util.size());
			}
			
			assignmentMap.put(currentPerson, arrRooms.get(index));
			arrRooms.get(index).setOccupant( currentPerson );
			
			System.out.println( currentPerson.getName() + "  " + arrRooms.get(index).getName() );
			
			if( currentPerson.getIsManager() || arrRooms.get(index).isShared() )
				arrRooms.remove(index);
			
		}
	}
	
	public int calcTotalUtility()
	{
		int totalUtil = 0;
		Iterator it = assignmentMap.entrySet().iterator();
		while (it.hasNext()) 
		{
			Map.Entry pairs = (Map.Entry)it.next();
			Person person = (Person)pairs.getKey();
			Room room = (Room)pairs.getValue();
			int util = utility( person, room, true );
			totalUtil += util;
		}
		return totalUtil;
	}
	
	public void makeSolution()
	{
		int i = findPersonIndex( "Andy" );
		int j = findRoomIndex( "C5113" );
		
		assignmentMap2.put(arrPeople.get(i), arrRooms.get(j));
		arrRooms.get(j).setOccupant( arrPeople.get(i) );
		
		i = findPersonIndex( "Angi" );
		j = findRoomIndex( "C5113" );
		
		assignmentMap2.put(arrPeople.get(i), arrRooms.get(j));
		arrRooms.get(j).setOccupant( arrPeople.get(i) );
		
		i = findPersonIndex( "Eva" );
		j = findRoomIndex( "C5114" );
		
		assignmentMap2.put(arrPeople.get(i), arrRooms.get(j));
		arrRooms.get(j).setOccupant( arrPeople.get(i) );
		
		i = findPersonIndex( "Hans" );
		j = findRoomIndex( "C5115" );
		
		assignmentMap2.put(arrPeople.get(i), arrRooms.get(j));
		arrRooms.get(j).setOccupant( arrPeople.get(i) );
		
		i = findPersonIndex( "Harry" );
		j = findRoomIndex( "C5116" );
		
		assignmentMap2.put(arrPeople.get(i), arrRooms.get(j));
		arrRooms.get(j).setOccupant( arrPeople.get(i) );
		
		i = findPersonIndex( "Joachim" );
		j = findRoomIndex( "C5117" );
		
		assignmentMap2.put(arrPeople.get(i), arrRooms.get(j));
		arrRooms.get(j).setOccupant( arrPeople.get(i) );
		
		i = findPersonIndex( "Jurgen" );
		j = findRoomIndex( "C5116" );
		
		assignmentMap2.put(arrPeople.get(i), arrRooms.get(j));
		arrRooms.get(j).setOccupant( arrPeople.get(i) );
		
		i = findPersonIndex( "Katharina" );
		j = findRoomIndex( "C5119" );
		
		assignmentMap2.put(arrPeople.get(i), arrRooms.get(j));
		arrRooms.get(j).setOccupant( arrPeople.get(i) );
		
		i = findPersonIndex( "Marc" );
		j = findRoomIndex( "C5120" );
		
		assignmentMap2.put(arrPeople.get(i), arrRooms.get(j));
		arrRooms.get(j).setOccupant( arrPeople.get(i) );
		
		i = findPersonIndex( "Michael" );
		j = findRoomIndex( "C5123" );
		
		assignmentMap2.put(arrPeople.get(i), arrRooms.get(j));
		arrRooms.get(j).setOccupant( arrPeople.get(i) );
		
		i = findPersonIndex( "Monika" );
		j = findRoomIndex( "C5121" );
		
		assignmentMap2.put(arrPeople.get(i), arrRooms.get(j));
		arrRooms.get(j).setOccupant( arrPeople.get(i) );
		
		i = findPersonIndex( "Thomas" );
		j = findRoomIndex( "C5122" );
		
		assignmentMap2.put(arrPeople.get(i), arrRooms.get(j));
		arrRooms.get(j).setOccupant( arrPeople.get(i) );
		
		i = findPersonIndex( "Ulrike" );
		j = findRoomIndex( "C5121" );
		
		assignmentMap2.put(arrPeople.get(i), arrRooms.get(j));
		arrRooms.get(j).setOccupant( arrPeople.get(i) );
		
		i = findPersonIndex( "Uwe" );
		j = findRoomIndex( "C5123" );
		
		assignmentMap2.put(arrPeople.get(i), arrRooms.get(j));
		arrRooms.get(j).setOccupant( arrPeople.get(i) );
		
		i = findPersonIndex( "Werner" );
		j = findRoomIndex( "C5120" );
		
		assignmentMap2.put(arrPeople.get(i), arrRooms.get(j));
		arrRooms.get(j).setOccupant( arrPeople.get(i) );
	}
	
	public void printConstriants(){
		for (int i = 0; i < 16; i++)
		{
			System.out.println(i + 1 + ":  " + con[i]);
		}
	}
	
}
