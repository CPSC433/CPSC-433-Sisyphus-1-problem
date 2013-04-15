package cpsc433;

import java.util.ArrayList;

/* 
 * This class holds all properties of each person, and has all getters and setters to access those properties.
 */
public class Person 
{	
	//private int personUtil;
	private String name;
	private String group;
	private String project;
	private boolean isProjectHead;
	private boolean isGroupHead;
	private boolean isManager;
	private boolean isResearcher;
	private boolean isHacker;
	private boolean isSmoker;
	private boolean isSecretary;
	private boolean isProjectLarge;
	private ArrayList<String> arrWorksWith = new ArrayList<String>();
	

	
	public Person(String personName) 
	{
		name = personName;
		group = "";
		project = "";
		isProjectHead = false;
		isGroupHead = false;
		isHacker = false;
		isSmoker = false;
		isManager = false;
		isResearcher = false;
		isSecretary = false;
		isProjectLarge = false;
		//personUtil = -1000;
	}
	
	// Getters
	
	public Boolean worksWith(Person person)
	{
		for (int i = 0; i < arrWorksWith.size(); i++){
			if (arrWorksWith.get(i).equals(person.getName()))
				return true;
		} 
		return false;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getGroup()
	{
		return group;
	}
	
	public String getProject()
	{
		return project;
	}
	
	public boolean getIsGroupHead()
	{
		return isGroupHead;
	}
	
	public boolean getIsManager()
	{
		return isManager;
	}
	
	public boolean getIsSecretary()
	{
		return isSecretary;
	}
	
	public boolean getIsResearcher()
	{
		return isResearcher;
	}
	
	public boolean getIsProjectHead()
	{
		return isProjectHead;
	}
	
	public boolean getIsHacker()
	{
		return isHacker;
	}
	
/*	public int getPersonUtil()
	{
		return personUtil;
	}*/
	
	public boolean getIsSmoker()
	{
		return isSmoker;
	}
	
	public boolean getIsProjectLarge()
	{
		return isProjectLarge;
	}
	
	// Setters
	
/*	public setPersonUtil( int util){
		personUtil = util;
	}*/
	
	public void setGroup( String name )
	{
		group = name;
	}
	
	public void setProject( String name )
	{
		project = name;
	}
	
	public void setIsGroupHead( boolean b )
	{
		isGroupHead = b;
	}
	
	public void setIsProjectHead( boolean b )
	{
		isProjectHead = b;
	}
	
	public void setIsHacker( boolean b )
	{
		isHacker = b;
	}
	
	public void setIsSmoker( boolean b )
	{
		isSmoker = b;
	}
	
	public void setIsManager( boolean b )
	{
		isManager = b;
	}
	
	public void setIsSecretary( boolean b )
	{
		isSecretary = b;
	}	
	
	public void setIsResearcher( boolean b )
	{
		isResearcher = b;
	}
	
	public void setIsProjectLarge( boolean b )
	{
		isProjectLarge = b;
	}
	
	public void addWorksWith( String name )
	{
		arrWorksWith.add( name );
	}
}
