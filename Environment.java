package cpsc433;

import java.util.TreeSet;
import java.util.Iterator;
import java.util.ArrayList;

import cpsc433.Predicate.ParamType;

public class Environment 
extends PredicateReader
implements SisyphusPredicates 
{
  public Environment(String name) {
		super(name);
	}
	
	private Predicate pred;
	private int numEmployees = 0;
	private int numManagers = 0;
	private int numRooms = 0;
	
	// Stores all predicates interpreted from input file
	public ArrayList<Predicate> arrPredicates = new ArrayList<Predicate>();
	
	public void a_person(String p)
	{
		String newPredicate = "person("+p+")";
		addNewPredicate( newPredicate );
	}
	public boolean e_person(String p){return false;}
	
	public void a_secretary(String p)
	{
		a_person(p);
		
		String newPredicate = "secretary("+p+")";
		int ret = addNewPredicate( newPredicate );
		
		if( ret == 1 )
			numEmployees++;
	}
	public boolean e_secretary(String p){return false;}
	
	public void a_researcher(String p)
	{
		a_person(p);
		
		String newPredicate = "researcher("+p+")";
		int ret = addNewPredicate( newPredicate );
		
		if( ret == 1 )
			numEmployees++;
	}
	public boolean e_researcher(String p){return false;}
	
	public void a_manager(String p)
	{
		a_person(p);
		
		String newPredicate = "manager("+p+")";
		int ret = addNewPredicate( newPredicate );
		
		if( ret == 1 )
			numManagers++;
	}
	public boolean e_manager(String p){return false;}
	
	public void a_smoker(String p)
	{
		a_person(p);
		
		String newPredicate = "smoker("+p+")";
		addNewPredicate( newPredicate );
	}
	public boolean e_smoker(String p){return false;}
	
	public void a_hacker(String p)
	{
		a_person(p);
		
		String newPredicate = "hacker("+p+")";
		addNewPredicate( newPredicate );
	}
	public boolean e_hacker(String p){return false;}
	
	public void a_in_group(String p, String grp)
	{
		a_person(p);
		
		a_group(grp);
		
		String newPredicate = "in-group("+p+", "+grp+")";
		addNewPredicate( newPredicate );
	}
	public boolean e_in_group(String p, String grp){return false;}
	public void a_group(String p, String grp)
	{
		a_person(p);
		
		a_group(grp);
		
		String newPredicate = "group("+p+", "+grp+")";
		addNewPredicate( newPredicate );
	}
	public boolean e_group(String p, String grp){return false;}
	
	public void a_in_project(String p, String prj)
	{
		a_person(p);
		
		a_project(prj);
		
		String newPredicate = "in-project("+p+", "+prj+")";
		addNewPredicate( newPredicate );
	}
	public boolean e_in_project(String p, String prj){return false;}
	public void a_project(String p, String prj)
	{
		a_person(p);
		
		a_project(prj);
		
		String newPredicate = "project("+p+", "+prj+")";
		addNewPredicate( newPredicate );
	}
	public boolean e_project(String p, String prj){return false;}
	
	public void a_heads_group(String p, String grp)
	{
		a_person(p);
		
		a_group(p, grp);
		
		String newPredicate = "heads-group("+p+", "+grp+")";
		addNewPredicate( newPredicate );
	}
	public boolean e_heads_group(String p, String grp){return false;}
	
	public void a_heads_project(String p, String prj)
	{
		a_person(p);
		
		a_project(p, prj);
		
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
		
		newPredicate = "works-with("+p2+", "+p+")";
		addNewPredicate( newPredicate );
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
		int ret = addNewPredicate( newPredicate );
		
		if( ret == 1 )
			numRooms++;
	}	
	public boolean e_room(String r){return false;}
	
	public void a_close(String room, String room2)
	{
		a_room(room);
		
		a_room(room2);
		
		String newPredicate = "close("+room+", "+room2+")";
		addNewPredicate( newPredicate );
		
		newPredicate = "close("+room2+", "+room+")";
		addNewPredicate( newPredicate );
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
		addNewPredicate( newPredicate );
	}
	public boolean e_large_room(String r){return false;}
	
	public void a_medium_room(String r)
	{
		a_room(r);
		
		String newPredicate = "medium-room("+r+")";
		addNewPredicate( newPredicate );
	}
	public boolean e_medium_room(String r){return false;}
	
	public void a_small_room(String r)
	{
		a_room(r);
		
		String newPredicate = "small-room("+r+")";
		addNewPredicate( newPredicate );
	}
	public boolean e_small_room(String r){return false;}
	
	// GROUPS
	public void a_group(String g)
	{		
		String newPredicate = "group("+g+")";
		addNewPredicate( newPredicate );
	}
	public boolean e_group(String g){return false;}
	
	// PROJECTS
	public void a_project(String p)
	{
		String newPredicate = "project("+p+")";
		addNewPredicate( newPredicate );
	}
	public boolean e_project(String p){return false;}
	
	public void a_large_project(String prj)
	{
		String newPredicate = "large-project("+prj+")";
		addNewPredicate( newPredicate );
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
	
	public boolean IsSolution()
	{
		if( (((numRooms - numManagers) * 2) - numEmployees) >= 0 )
			return true;
		else
			return false;
	}
}
