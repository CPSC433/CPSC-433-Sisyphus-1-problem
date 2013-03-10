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

		final Environment env = Environment.get();
		Solution.verbosity = Solution.Verbosity.SUMMARY;

		String fromFile = null;

		if (args.length>0) {
			fromFile = args[0];
			env.fromFile(fromFile);
		}
		else {
			System.out.println("Synopsis: SisyphusI <env-file> [<solution-file>|<time-in-ms>]");
		}

		final String out = fromFile+".out";

		Thread shutdownHookThread = new Thread("SisyphusIShutdownHook")
		{@Override public void run() {
			System.err.println("***Shutdown hook activated***");
			try {
				PrintStream outFile = new PrintStream(new FileOutputStream(out));
				outFile.println(env.currentSolution.toString());
				outFile.close();
			}
			catch (Exception ex) {}
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
		} catch (IllegalStateException e) {};
	}

}
