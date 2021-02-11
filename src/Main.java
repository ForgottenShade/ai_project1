import java.util.List;

public class Main {
	
	/**
	 * starts the game player and waits for messages from the game master <br>
	 * Command line options: [port]
	 */
	public static void main(String[] args){
		try{
			
			// my recommended todos in order:
			// 1. Create an Environment and State using Lab3 as a guide. It should have Environment.initialize() and State.clone()
			Environment env5_10 = new Environment(5, 10); // create a new map with x = 6 and h = 10 (6 columns and 10 rows)
			Environment env3_5 = new Environment(3, 5); // smallest environment with empty squares in front
			Environment env3_4 = new Environment(3, 4); // example environment with black and white pieces that can be captured

			// 2. Create a printing functions State.toString() which is important for debugging
			// 3. Test your implementation for the initialization, cloning of the new environment and print it out to make sure it is correct
			System.out.println(env5_10.currentState.toString()); // print out the map and see if it looks correct
			System.out.println(env3_5.currentState.toString());
			System.out.println(env3_4.currentState.toString());
			
			// 4. Create  Implement the available or legal moves inside Environment.java and test it
			// first: check for moves with empty squares in front of the pieces
			List<Moves> m = env3_5.legalMoves(env3_5.currentState);
			System.out.println(env3_5.currentState.toString()); // print map also to look compare
			System.out.println(m); // for white
			env3_5.currentState.isWhiteTurn = false; // see also for black's turn if it is correct
			m = env3_5.legalMoves(env3_5.currentState);
			System.out.println(env3_5.currentState.toString()); // print map also to look compare
			System.out.println(m); // for black
			// second: check for moves with enemy pieces in front of the them
			m = env3_4.legalMoves(env3_4.currentState);
			System.out.println(env3_4.currentState.toString()); // print map also to look compare
			System.out.println(m); // for white
			env3_4.currentState.isWhiteTurn = false; // see also for black's turn if it is correct
			m = env3_4.legalMoves(env3_4.currentState);
			System.out.println(env3_4.currentState.toString()); // print map also to look compare
			System.out.println(m); // for black


			// 5. Create and test getNextState function
			// first check for white, both in a map where you can and cannot capture
			env3_4.currentState.isWhiteTurn = true;
			env3_5.currentState.isWhiteTurn = true;
			List<Moves> availMoves3_4 = env3_4.legalMoves(env3_4.currentState);
			List<Moves> availMoves3_5 = env3_5.legalMoves(env3_5.currentState);
			System.out.println("Checking getNextState in a 3x4 map where it is white's turn:\n");
			for (Moves thisMove : availMoves3_4){ // for each move
				System.out.println("\nWith move " + thisMove + " we get a state that looks like:\n");
				System.out.println(env3_4.getNextState(env3_4.currentState, thisMove));
			}
			System.out.println("Checking getNextState in a 3x5 map where it is white's turn:\n");
			for (Moves thisMove : availMoves3_5){ // for each move
				System.out.println("\nWith move " + thisMove + " we get a state that looks like:\n");
				System.out.println(env3_5.getNextState(env3_5.currentState, thisMove));
			}
			// now we check for black, both in a map where you can and cannot capture
			env3_4.currentState.isWhiteTurn = false;
			env3_5.currentState.isWhiteTurn = false;
			availMoves3_4 = env3_4.legalMoves(env3_4.currentState);
			availMoves3_5 = env3_5.legalMoves(env3_5.currentState);
			for (Moves thisMove : availMoves3_4){ // for each move
				System.out.println("\nWith move " + thisMove + " we get a state that looks like:\n");
				System.out.println(env3_4.getNextState(env3_4.currentState, thisMove));
			}
			System.out.println("Checking getNextState in a 3x5 map where it is white's turn:\n");
			for (Moves thisMove : availMoves3_5){ // for each move
				System.out.println("\nWith move " + thisMove + " we get a state that looks like:\n");
				System.out.println(env3_5.getNextState(env3_5.currentState, thisMove));
			}
			// 6. Create and test the evaluation function
			System.out.println("\nChecking evaluation function\n");
			env3_4.currentState.isWhiteTurn = true;
			env3_5.currentState.isWhiteTurn = true;
			System.out.println("");
			List<Moves> availMoves3_4_eval = env3_4.legalMoves(env3_4.currentState);
			List<Moves> availMoves3_5_eval = env3_5.legalMoves(env3_5.currentState);
			System.out.println("Checking evaluation() in a 3x4 map where it is white's turn:\n");
			for (Moves thisMove : availMoves3_4_eval){ // for each move
				State tempStateForPrint = env3_4.getNextState(env3_4.currentState, thisMove);
				System.out.println("\nWith move " + thisMove + " we get a new score of " + tempStateForPrint.eval +
						" and we get a map that looks like:\n" + tempStateForPrint);
			}
			System.out.println("Checking evaluation() in a 3x5 map where it is white's turn:\n");
			for (Moves thisMove : availMoves3_5_eval){ // for each move
				State tempStateForPrint = env3_5.getNextState(env3_5.currentState, thisMove);
				System.out.println("\nWith move " + thisMove + " we get a new score of " + tempStateForPrint.eval +
						" and we get a map that looks like:\n" + tempStateForPrint);
			}
			// now we check for black, both in a map where you can and cannot capture
			env3_4.currentState.isWhiteTurn = false;
			env3_5.currentState.isWhiteTurn = false;
			availMoves3_4_eval = env3_4.legalMoves(env3_4.currentState);
			availMoves3_5_eval = env3_5.legalMoves(env3_5.currentState);
			for (Moves thisMove : availMoves3_4_eval){ // for each move
				State tempStateForPrint = env3_4.getNextState(env3_4.currentState, thisMove);
				System.out.println("\nWith move " + thisMove + " we get a new score of " + tempStateForPrint.eval +
						" and we get a map that looks like:\n" + tempStateForPrint);
			}
			System.out.println("Checking evaluation() in a 3x5 map where it is white's turn:\n");
			for (Moves thisMove : availMoves3_5_eval){ // for each move
				State tempStateForPrint = env3_5.getNextState(env3_5.currentState, thisMove);
				System.out.println("\nWith move " + thisMove + " we get a new score of " + tempStateForPrint.eval +
						" and we get a map that looks like:\n" + tempStateForPrint);
			}
			
			// 7. Update the environment after each move inside MyAgent.java
				// That is the TODO: 1. update your internal world model according to the action that was just executed
				// make your own test, for example printing inside myAgent to make sure it works.

			// 8. Make an environment that exists without

			// 9. Create and test some search algorithm inside MyAgent.java, don't go right into creating fully implemented alpha-beta search
				// you should start with something simple that can be tested, then add one and one functionality onto it. For example
				// a) start with DFS with  implementing MiniMax
				// b) Then make it iterative deepening
				// c) and then finally add pruning.
				//


			/*
			// TODO: put in your agent here
			//Agent agent = new PlayerAgent();
			Agent agent = new RandomAgent();

			int port=4001;
			if(args.length>=1){
				port=Integer.parseInt(args[0]);
			}
			GamePlayer gp=new GamePlayer(port, agent);
			gp.waitForExit();
			*/
		}catch(Exception ex){
			ex.printStackTrace();
			System.exit(-1);
		}
	}
}
