package cpsc433;

import java.io.FileOutputStream;
import java.io.PrintStream;

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
		long startFindingSolution = System.currentTimeMillis();
		env.findSolution();
		int totalUtil = env.calcTotalUtility(true); // false for loop
		long finishFindingSolution = System.currentTimeMillis();
		long solutionTime = finishFindingSolution - startFindingSolution;

		long avgSolutionTime = solutionTime;
		
		/*while (avgSolutionTime < (maxtime - (System.currentTimeMillis() - startTime))/2) {
			startFindingSolution = System.currentTimeMillis();
			env.findSolution();
			totalUtil = env.calcTotalUtility(false);
			finishFindingSolution = System.currentTimeMillis();
			solutionTime = finishFindingSolution - startFindingSolution;
			avgSolutionTime = (avgSolutionTime + solutionTime)/2;
			// compare solution utility with previous solution utility
			// store current solution if better (with utility)
		}*/
		// retrieve (if applicable) best solution if stored.
		// run totalutil again on final solution with param true to find correct constraint nums
		
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
