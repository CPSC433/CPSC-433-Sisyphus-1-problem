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
		
		Environment env = new Environment("PredicateReader");
		String fromFile = null;
		
		if (args.length>0) {
			fromFile = args[0];
			env.fromFile(fromFile);
		}
		else {
			System.out.println("Synopsis: SisyphusI <env-file> [<solution-file>|<time-in-ms>]");
		}
		
		final String out = fromFile+".out";
		try {
			PrintStream outFile = new PrintStream(new FileOutputStream(out));
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
			env.findSolution();
			outFile.close();
		} catch (Exception ex) {}
		
		if( env.IsSolution() )
			System.out.println("There is a valid solution for this input");
		else
			System.out.println("There is not a valid solution for this input");
		/*

		Thread shutdownHookThread = new Thread("SisyphusIShutdownHook")
		{@Override public void run() {
			System.err.println("***Shutdown hook activated***");
			
			System.err.println(env.currentSolution==null
					?"no current solution"
							:env.currentSolution.toString());
			System.err.println("***Shutdown hook termniated***");
		}};
		Runtime.getRuntime().addShutdownHook(shutdownHookThread);

		if (args.length>1) {
			try {
				long timeLimit = new Long(args[1]).longValue();
				//timeLimit -= (System.currentTimeMillis()-startTime);
				System.out.println("Performing search for "+timeLimit+"ms");
				env.a_search("DepthFirstTreeSearch", "SmartControl", timeLimit);
			}
			catch (NumberFormatException ex) {
				env.currentSolution = new Solution(args[1]);
			}
		}

		if (env.currentSolution!=null) {
			//System.out.println(currentSolution.toString());
			System.out.println(env.currentSolution.getName()+": isSolved()    -> "+env.currentSolution.isSolved());
			System.out.println(env.currentSolution.getName()+": getGoodness() -> "+env.currentSolution.getGoodness());
		}

		if (args.length>1) {
			System.exit(1);
		}

		final int maxBuf = 200;
		byte[] buf = new byte[maxBuf];
		int length;
		try {
			System.out.print("\nSisyphus I: query using predicates, assert using \"!\" prefixing predicates;\n !exit() to quit; !help() for help.\n\n> ");
			while ((length=System.in.read(buf))!=-1) {
				String s = new String(buf,0,length);
				s = s.trim();
				if (s.equals("exit")) break;
				if (s.equals("?")||s.equals("help")) {
					s = "!help()";
					System.out.println("> !help()");
				}
				if (s.length()>0) {
					if (s.charAt(0)=='!') 
						env.assert_(s.substring(1));
					else 
						System.out.print(" --> "+env.eval(s));
				}
				System.out.print("\n> ");
			}
		} catch (Exception e) {
			System.err.println("exiting: "+e.toString());
		}
		try {
			Runtime.getRuntime().removeShutdownHook(shutdownHookThread);
		} catch (IllegalStateException e) {}; */
	}

}
