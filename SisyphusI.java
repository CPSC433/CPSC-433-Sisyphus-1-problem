package cpsc433;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/*
 * SisyphusI Final Program created by Kristi Mikkelsen, Garrick van der Lee, Kyle McLenahan (Group 2)
 * for CPSC 433 W13 - Artificial Intelligence
 */

public class SisyphusI {
	
	/*
	 * main function, start point of program. This function creates the environment, tracks the time, 
	 * outputs the solution to the output file, and calls functions from the enviroment class such as 
	 * reading in the input file, checking if a solution can exist, and finding a solution. 
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
			} catch (Exception ex) { // if invalid max time is entered, exit program
				System.out.println("Synopsis: SisyphusI <env-file> [<solution-file>|<time-in-ms>]");
				System.exit(0);
			}
		}
		else { // if incorrect input parameters are entered, exit program
			System.out.println("Synopsis: SisyphusI <env-file> [<solution-file>|<time-in-ms>]");
			System.exit(0);
		}
		
		if( !env.IsSolution() ) { // if no solution can exist, exit program
			System.out.println("There is not a valid solution for this input");
			System.exit(0);
		}
		
		final String out = fromFile+".out";
		
		// create copy ArrayList to hold initial value of rooms, to reinitialize env.arrRooms in loop
		ArrayList<Room> arrRooms = new ArrayList<Room>();
		arrRooms.addAll(env.arrRooms); 							// init
		
		// ordering employees ensures all managers/group heads/project heads have their own room
		env.orderEmployees();
		
		// find first solution
		long startFindingSolution = System.currentTimeMillis();
		env.findSolution();
		int totalUtil = env.calcTotalUtility(false); // false it is not the final run of calcTotalUtility
		long finishFindingSolution = System.currentTimeMillis();
		long solutionTime = finishFindingSolution - startFindingSolution;
		long avgSolutionTime = solutionTime; 		// used in loop
		
		// create copy Map to hold the solution just found and to compare to next solution found in the loop
		Map<Person,Room> assignmentMap_oldSolution = new HashMap<Person, Room>();
		assignmentMap_oldSolution.putAll(env.assignmentMap); 	// init
		int oldUtil = totalUtil;								// init
		
		/* 	loop to run env.findSolution as many times as possible, each time comparing the solution to 
			the best solution found so far */
		while (avgSolutionTime < (maxtime - (System.currentTimeMillis() - startTime))/10) {
			// reset arrRooms and occupants of room, and assignmentMap
			env.arrRooms.clear();
			for (int i = 0; i < arrRooms.size(); i++) {
				env.arrRooms.add(arrRooms.get(i));
				env.arrRooms.get(i).clearOccupants();
			}
			env.assignmentMap.clear();
			
			// find the next solution
			startFindingSolution = System.currentTimeMillis();
			env.findSolution();	
			totalUtil = env.calcTotalUtility(false);
			finishFindingSolution = System.currentTimeMillis();
			
			solutionTime = finishFindingSolution - startFindingSolution;
			avgSolutionTime = (avgSolutionTime + solutionTime)/2; // update the average solution time
			
			/* 	If the current solution is better than the best (so far) solution, replace it with the 
				current solution assignmentMap and utility value for comparison */
			if (totalUtil > oldUtil) {
				assignmentMap_oldSolution.clear();
				assignmentMap_oldSolution.putAll(env.assignmentMap);
				oldUtil = totalUtil;
			}
		}
		
		// clear assignmentMap and replace with best solution that was found
		env.assignmentMap.clear();
		env.assignmentMap.putAll(assignmentMap_oldSolution);
		env.calcTotalUtility(true);
		totalUtil = oldUtil;
		/* 	Since we already have the lowest utility, we shouldn't need to recalculate it to 
			get the value; the reason we will still need to run it is to calculate the number of
			times each soft constraints was broken. */
		
		// write the solution out to the file
		try {
			PrintStream outFile = new PrintStream(new FileOutputStream(out));
			// Form: assigned-to(name,room-name)
			for (int i = 0; i < env.arrPeople.size(); i++) {
				Person currentPerson = env.arrPeople.get(i);
				Room room = env.assignmentMap.get(currentPerson);
				outFile.println("assigned-to(" + currentPerson.getName() + "," + room.getName() + ")");
			}
			System.out.println( totalUtil );
			//env.printConstriants();
			outFile.close();
		} catch (Exception ex) {}
	}
	
}
