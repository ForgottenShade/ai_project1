import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Environment {

	protected int sizeX, sizeY;
	protected State currentState;
	protected short[][] map;

	public Environment(int w, int h) {
        initFromInput(w, h);
    } // w = column, h = row


	//CODE FROM SKELETON
	// this does not need to be fast code since it is only run once
    public void initFromInput(int w, int h){
        currentState = new State(w, h);
        map = new short[w][h];
        // initialize the map
        for (int i = 0; i < w; i++){ // for each column
            for (int j = 0; j < h; j++){ // for each row
                if (j < 2) { // if first 2 rows we place white
                    map[i][j] = 1; // 1 is for white pieces
                }
                else if (j > (h - 3)) {
                    map[i][j] = 2; // 2 is for black pieces
                }
                else {
                    map[i][j] = 0; // else empty
                }
            }
        }
        currentState.myMap = map;
    }

	//CODE FROM TA VIDEO/////////////////////////////////

    // get all moves for current player
    public List<Moves> legalMoves(State state) {
        List<Moves> moves = new LinkedList<Moves>();
        
		for (int i=0; i<map.length; i++) { //column
			for (int j=0; j<map[0].length; j++) { //row
				if (state.isWhiteTurn && map[i][j] == 1) {  //if we find a white piece when it is white´s turn
					moves.addAll(getMoves(state,i,j));  //add all moves if any for that piece
				}
				else if (!state.isWhiteTurn && map[i][j] == 2) {  //if it is black´s turn and we found a black piece
					moves.addAll(getMoves(state,i,j));  //add all moves if any for that piece
				}
			}
		}
        return moves;
    }

    // find all moves for piece located at x, y
    public List<Moves> getMoves(State s, int x, int y) {
        List<Moves> moves = new LinkedList<Moves>();
        
		if (s.isWhiteTurn) {  //if it is white we check the row above the piece
			if (y + 1 < s.myMap[0].length-1) {  //see if we are not at black´s end of the map to avoid null pointers
				//left diagonal
				if (x > 0) {
					if (s.myMap[x-1][y+1] != 1) {  //if we are not at the LEFT end of the map then we check if we can go diagonal left
						moves.add(new Moves(x,y,x-1,y+1)); //if the top left square is either empty or has black piece (not white piece)
					}
				}
				//right diagonal
				if (x < s.myMap.length-1){  //if we are not at the RIGHT end of the map then we check if we can go diagonal right
					if (s.myMap[x+1][y+1] != 1) { //if the top right square is either empty or has black piece (not white piece)
						moves.add(new Moves(x,y,x+1,y+1));
					}
				}
				//forward
				if (s.myMap[x][y+1] == 0) {  //if the square in front of us is empty
					moves.add(new Moves(x,y,x,y+1));
				}
			}
		}
		else{
			if (y>0) {  //see if we are not at white´s end of the map to avoid null poointers
				//left diagonal
				if (x > 0) {  //if we are not at the LEFT end of the map then we check if we can go diagonal left
					if (s.myMap[x-1][y-1] != 2) {  //if the bottom left square is either empty or has white piece (not black piece)
						moves.add(new Moves(x,y,x-1,y-1));
					}
				}
				//right diagonal
				if (x < s.myMap.length-1) { //if we are not at the RIGHT end of the map then we check if we can go diagonal right
					if (s.myMap[x+1][y-1] != 2) { //if the bottom right square is either empty or has white piece (not black piece)
						moves.add(new Moves(x,y,x+1,y-1));
					}
				}
				//forward
				if(s.myMap[x][y-1] == 0) {  //if the square below is empty
					moves.add(new Moves(x,y,x,y-1));
				}
			}
		}
		
        return moves;
    }

    public State getNextState(State s, Moves m){
        State c = s.clone();
        // todo
		if (c.isWhiteTurn) {
			c.myMap[m.x][m.y] = 0;
			c.myMap[m.x2][m.y2] = 1;
		}
		else{
			c.myMap[m.x][m.y] = 0;
			c.myMap[m.x2][m.y2] = 2;
		}
        return c;
    }

    public int eval(State s) {
        // Set your own evaluation here
        // this should not be done prior to State and Environment
        int e = 0;
        // example evaluation
        int blackPieces = 0;
        int whitePieces = 0;

        for (int i = 0; i < s.myMap.length; i++){ // for each column
            for (int j = 0; j < s.myMap[0].length; j++){ // for each row
                if (s.myMap[i][j] == 1){whitePieces++;}
                else if (s.myMap[i][j] == 2){blackPieces++;}
            }
        }
        e = whitePieces - blackPieces;
        if (!s.isWhiteTurn){
            e = -e; // negate the score
        }
        return e;
    }

    // print out current state for this environment. Good for testing.
    public String toString() {
        return currentState.toString();
    }
	





	////////////////////////////////////////////////////////////
	//CODE FROM GITHUB
	// public Environment(Collection<String> percepts) {
	// 	initFromPercepts(percepts);
	// }


    // public void initFromPercepts(Collection<String> percepts) {

		//TODO: Update as I have no idea what the incoming looks like
		/*
		currentState = new State();
		obstacles = new HashSet<Coordinates>();
		Pattern perceptNamePattern = Pattern.compile("\\(\\s*([^\\s]+).*");
		for (String percept:percepts) {
			Matcher perceptNameMatcher = perceptNamePattern.matcher(percept);
			if (perceptNameMatcher.matches()) {
				String perceptName = perceptNameMatcher.group(1);
				if (perceptName.equals("HOME")) {
					Matcher m = Pattern.compile("\\(\\s*HOME\\s+([0-9]+)\\s+([0-9]+)\\s*\\)").matcher(percept);
					if (m.matches()) {
						System.out.println("robot is at " + m.group(1) + "," + m.group(2));
						home = new Coordinates(Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2)));
						currentState.position = (Coordinates)home.clone();
					}
				} else if (perceptName.equals("SIZE")) {
					Matcher m = Pattern.compile("\\(\\s*SIZE\\s+([0-9]+)\\s+([0-9]+)\\s*\\)").matcher(percept);
					if (m.matches()) {
						System.out.println("size is " + m.group(1) + "," + m.group(2));
						sizeX = Integer.parseInt(m.group(1));
						sizeY = Integer.parseInt(m.group(2));
					}
				} else if (perceptName.equals("AT")) {
					Matcher m = Pattern.compile("\\(\\s*AT\\s+([^\\s]+)\\s+([0-9]+)\\s+([0-9]+)\\s*\\)").matcher(percept);
					if (m.matches()) {
						System.out.println(m.group(1) + " is at " + m.group(2) + "," + m.group(3));
						Coordinates c = new Coordinates(Integer.parseInt(m.group(2)), Integer.parseInt(m.group(3)));
						if (m.group(1).equals("DIRT")) {
							currentState.dirt.add(c);
						} else {
							obstacles.add(c);
						}
					}
				} else if (perceptName.equals("ORIENTATION")) {
					Matcher m = Pattern.compile("\\(\\s*ORIENTATION\\s+([^\\s]+)\\s*\\)").matcher(percept);
					if (m.matches()) {
						System.out.println("orientation is " + m.group(1));
						if (m.group(1).equals("NORTH")) {
							currentState.orientation=0;
						}else if (m.group(1).equals("EAST")) {
							currentState.orientation=1;
						}else if (m.group(1).equals("SOUTH")) {
							currentState.orientation=2;
						}else if (m.group(1).equals("WEST")) {
							currentState.orientation=3;
						}
					}
				} else {
					System.out.println("other percept:" + percept);
				}
			} else {
				System.err.println("strange percept that does not match pattern: " + percept);
			}
		}*/
	// }
	
//     /**
//      * 
//      * @return the current state of the environment
//      */
// 	public State getCurrentState() {
// 		return currentState;
// 	}
	
// 	/**
// 	 * updates the current state of the environment based on the given action
// 	 * @param a
// 	 */
// 	public void doAction(Action a) {
// 		currentState = getNextState(currentState, a);
// 	}
	
// 	/**
// 	 * 
// 	 * @param state
// 	 * @return a list of actions that are possible in the given state
// 	 */
// 	public List<Action> legalMoves(State state) {

// 		//TODO: evaluate legal moves by board state
// 		/*
// 		List<Action> moves = new LinkedList<Action>();
// 		if (!state.turned_on) {
// 			moves.add(Action.TURN_ON);
// 		} else {
// 			if (state.position.equals(home) && state.dirt.size() == 0) {
// 				moves.add(Action.TURN_OFF);
// 			}
// 			if (state.dirt.contains(state.position)) {
// 				moves.add(Action.SUCK);
// 			}
// 			Coordinates facingPosition = state.facingPosition();
// 			if (facingPosition.x>0 && facingPosition.y>0 && facingPosition.x<=sizeX && facingPosition.y<=sizeY && !obstacles.contains(facingPosition)) {
// 				moves.add(Action.GO);
// 			}
// 			moves.add(Action.TURN_RIGHT);
// 			moves.add(Action.TURN_LEFT);
// 		}
// 		return moves;*/
// 		return null;
// 	}

// 	/**
// 	 * 
// 	 * @param s state
// 	 * @param a action
// 	 * @return the state resulting from doing a in s
// 	 */
// 	public State getNextState(State s, Action a) {
// 		// TODO: fill out this function

// 		System.out.println("Failed to interpret command.");
// 		return s;
// 	}

// 	/**
// 	 * 
// 	 * @param s
// 	 * @param a
// 	 * @return the cost of doing action a in state s
// 	 */
// 	public int getCost(State s, Action a) {
// 		return 0;
// 	}

}
