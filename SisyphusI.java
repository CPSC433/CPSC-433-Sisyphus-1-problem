package cpsc433;

import java.io.FileOutputStream;
import java.io.PrintStream;

/**
 * 
 * <p>Copyright: Copyright (c) 2003-2013, Department of Computer Science, University 
 * of Calgary.  Permission to use, copy, modify, distribute and sell this 
 * software and its documentation for any purpose is hereby granted without 
 * fee, provided that the above copyright notice appear in all copies and that
 * both that copyright notice and this permission notice appear in supporting 
 * documentation.  The Department of Computer Science makes no representations
 * about the suitability of this software for any purpose.  It is provided
 * "as is" without express or implied warranty.</p>
 *
 * @author <a href="http://www.cpsc.ucalgary.ca/~kremer/">Rob Kremer</a>
 *
 */
public class SisyphusI {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		long startTime = System.currentTimeMillis();
		
		Environment env = new Environment("PredicateReader");
		
		String fromFile = null;
		long maxtime = 0;
		
		if (args.length == 2) {
			fromFile = args[0];
			env.fromFile(fromFile);
			try {
				maxtime = Long.valueOf(args[1]);
			} catch (Exception ex) {
				System.out.println("Synopsis: SisyphusI <env-file> [<solution-file>|<time-in-ms>]");
				System.exit(0);
			}
		}
		else {
			System.out.println("Synopsis: SisyphusI <env-file> [<solution-file>|<time-in-ms>]");
			System.exit(0);
		}
		
		if( !env.IsSolution() ) {
			System.out.println("There is not a valid solution for this input");
			System.exit(0);
		}
		
		final String out = fromFile+".out";
		long startWritingTime = System.currentTimeMillis();
		env.findSolution();
		//env.makeSolution();
		try {
			PrintStream outFile = new PrintStream(new FileOutputStream(out));
			// Form: assigned-to(name,room-name)
			for (int i = 0; i < env.arrPeople.size(); i++) {
				Person currentPerson = env.arrPeople.get(i);
				Room room = env.assignmentMap.get(currentPerson);
				outFile.println("assigned-to(" + currentPerson.getName() + "," + room.getName() + ")");
			}
			/*
			outFile.println( "***Employees***" );
			for( int i = 0; i < env.arrEmployees.size(); i++ )
			{
				outFile.println(env.arrEmployees.get(i).toString());
			}
			outFile.println( "***Rooms***" );
			for( int i = 0; i < env.arrRooms.size(); i++ )
			{
				outFile.println(env.arrRooms.get(i).toString());
			}
			outFile.println( "***Groups***" );
			for( int i = 0; i < env.arrGroups.size(); i++ )
			{
				outFile.println(env.arrGroups.get(i).toString());
			}
			outFile.println( "***Projects***" );
			for( int i = 0; i < env.arrProjects.size(); i++ )
			{
				outFile.println(env.arrProjects.get(i).toString());
			}
			outFile.println( "***Room Sizes***" );
			for( int i = 0; i < env.arrRoomSizes.size(); i++ )
			{
				outFile.println(env.arrRoomSizes.get(i).toString());
			}
			outFile.println( "***Room Proximity***" );
			for( int i = 0; i < env.arrRoomProximity.size(); i++ )
			{
				outFile.println(env.arrRoomProximity.get(i).toString());
			}
			outFile.println( "***Project Descriptions***" );
			for( int i = 0; i < env.arrProjectDescriptions.size(); i++ )
			{
				outFile.println(env.arrProjectDescriptions.get(i).toString());
			}
			outFile.println( "***People Predicates***" );
			for( int i = 0; i < env.arrPeople.size(); i++ )
			{
				outFile.println(env.arrPeople.get(i).getName());
				outFile.println(env.arrPeople.get(i).getGroup());
				outFile.println(env.arrPeople.get(i).getProject());
				//outFile.println(env.arrPeople.get(i).getName());
				//outFile.println(env.arrPeople.get(i).getName());
				//outFile.println(env.arrPeople.get(i).getName());
			}
			outFile.println( "***Remaining Predicates***" );
			for( int i = 0; i < env.arrPredicates.size(); i++ )
			{
				outFile.println(env.arrPredicates.get(i).toString());
			}
			*/
			int totalUtil = env.calcTotalUtility();
			System.out.println( totalUtil );
			env.printConstriants();
			outFile.close();
		} catch (Exception ex) {}
		System.out.println("Time to read to file: " + (System.currentTimeMillis() - startWritingTime) + "ms");
		/*
		 if( env.IsSolution() )
		 System.out.println("There is a valid solution for this input");
		 else
		 System.out.println("There is not a valid solution for this input");
		 */
		long endtime = System.currentTimeMillis() - startTime;
		System.out.println("Total time: " + endtime + "ms");
		System.out.println("Under time constraint? " + (endtime < maxtime));
	}
	
}
