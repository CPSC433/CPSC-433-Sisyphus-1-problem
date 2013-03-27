package cpsc433;

import java.util.ArrayList;

import cpsc433.Environment.Role;

public class Person 
{
	private String name;
	private String group;
	private String project;
	private boolean isProjectHead;
	private boolean isGroupHead;
	private boolean isHacker;
	private boolean isSmoker;
	private ArrayList<String> arrWorksWith = new ArrayList<String>();
	Role role;
	
	public Person(String personName) 
	{
		name = personName;
		group = "";
		project = "";
		isProjectHead = false;
		isGroupHead = false;
		isHacker = false;
		isSmoker = false;
		role = Role.eNone;
	}
	
	// Getters
	
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
	
	public boolean getIsProjectHead()
	{
		return isProjectHead;
	}
	
	public boolean getIsHacker()
	{
		return isHacker;
	}
	
	public boolean getIsSmoker()
	{
		return isSmoker;
	}
	
	// Setters
	
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
	
	public void addWorksWith( String name )
	{
		arrWorksWith.add( name );
	}
}
