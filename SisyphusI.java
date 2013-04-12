package cpsc433;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
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
		
		ArrayList<Room> arrRooms = new ArrayList<Room>();
		//ArrayList<Room> best_arrRooms = new ArrayList<Room>();
		arrRooms.addAll(env.arrRooms); // save arrRooms for next use
		
		env.orderEmployees();
		
		long startFindingSolution = System.currentTimeMillis();
		env.findSolution();
		int totalUtil = env.calcTotalUtility(false); // false for loop
		//best_arrRooms.addAll(env.arrRooms);
		long finishFindingSolution = System.currentTimeMillis();
		long solutionTime = finishFindingSolution - startFindingSolution;

		long avgSolutionTime = solutionTime;
		Map<Person,Room> assignmentMap_oldSolution = new HashMap<Person, Room>();
		
		assignmentMap_oldSolution.putAll(env.assignmentMap);
		
		int oldUtil = totalUtil;
		
		while (avgSolutionTime < (maxtime - (System.currentTimeMillis() - startTime))/10) {
			// reset arrRooms and occupants of room
			env.arrRooms.clear();
			for (int i = 0; i < arrRooms.size(); i++) {
				env.arrRooms.add(arrRooms.get(i));
				env.arrRooms.get(i).clearOccupants();
			}
			
			env.assignmentMap.clear();
			
			startFindingSolution = System.currentTimeMillis();
			env.findSolution();	
			totalUtil = env.calcTotalUtility(false);
			finishFindingSolution = System.currentTimeMillis();
			
			solutionTime = finishFindingSolution - startFindingSolution;
			
			avgSolutionTime = (avgSolutionTime + solutionTime)/2;
			
			if (totalUtil > oldUtil) {
				assignmentMap_oldSolution.clear();
				assignmentMap_oldSolution.putAll(env.assignmentMap);
				oldUtil = totalUtil;
			}
		}
		//System.out.println("Average time to find solution: " + avgSolutionTime);
		//System.out.println("Number of solutions found: " + count);
		env.assignmentMap.clear();
		env.assignmentMap.putAll(assignmentMap_oldSolution);
		//totalUtil = env.calcTotalUtility(true);
		/* 
		 * Since we already have the lowest utility, we shouldn't need to recalculate it to 
		 * get the value; the reason we will still need to run it though (if we want) is for
		 * number of constraints broken.
		 */
		env.calcTotalUtility(true);
		totalUtil = oldUtil;
		
		
		//System.out.println("Time to find solution: " + (System.currentTimeMillis() - startTime));
		//env.makeSolution();		
		
		long startWritingTime = System.currentTimeMillis();
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
		
		if( !env.IsSolution() )
			System.out.println("There is not a valid solution for this input");
		//System.out.println("Total time: " + (System.currentTimeMillis() - startTime) + "ms");
	}
	
}
