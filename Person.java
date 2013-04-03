package cpsc433;

import java.util.ArrayList;

public class Person 
{
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
	}
	
	// Getters
	
	public Boolean workswith(Person person){
		boolean works = false;
		for (int i = 0; i < arrWorksWith.size() && !works; i++){
			if (arrWorksWith[i].getName() == person.getName()) works = true;
		} 
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
	
	public void addWorksWith( String name )
	{
		arrWorksWith.add( name );
	}
}
