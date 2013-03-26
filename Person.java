package cpsc433;

import java.util.ArrayList;

public class Person 
{
  private String name;
	private String group;
	private String project;
	private boolean isProjectHead;
	private boolean isHacker;
	private boolean isSmoker;
	
	private ArrayList<String> arrWorksWith = new ArrayList<String>();
	
	public Person(String personName) 
	{
		name = personName;
	}
	
	
}
