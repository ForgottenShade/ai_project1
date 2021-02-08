import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Environment {

	protected int sizeX, sizeY;
	protected State currentState;

	public Environment(Collection<String> percepts) {
		initFromPercepts(percepts);
	}
	
    public void initFromPercepts(Collection<String> percepts) {

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
	}
	
    /**
     * 
     * @return the current state of the environment
     */
	public State getCurrentState() {
		return currentState;
	}
	
	/**
	 * updates the current state of the environment based on the given action
	 * @param a
	 */
	public void doAction(Action a) {
		currentState = getNextState(currentState, a);
	}
	
	/**
	 * 
	 * @param state
	 * @return a list of actions that are possible in the given state
	 */
	public List<Action> legalMoves(State state) {

		//TODO: evaluate legal moves by board state
		/*
		List<Action> moves = new LinkedList<Action>();
		if (!state.turned_on) {
			moves.add(Action.TURN_ON);
		} else {
			if (state.position.equals(home) && state.dirt.size() == 0) {
				moves.add(Action.TURN_OFF);
			}
			if (state.dirt.contains(state.position)) {
				moves.add(Action.SUCK);
			}
			Coordinates facingPosition = state.facingPosition();
			if (facingPosition.x>0 && facingPosition.y>0 && facingPosition.x<=sizeX && facingPosition.y<=sizeY && !obstacles.contains(facingPosition)) {
				moves.add(Action.GO);
			}
			moves.add(Action.TURN_RIGHT);
			moves.add(Action.TURN_LEFT);
		}
		return moves;*/
		return null;
	}

	/**
	 * 
	 * @param s state
	 * @param a action
	 * @return the state resulting from doing a in s
	 */
	public State getNextState(State s, Action a) {
		// TODO: fill out this function

		System.out.println("Failed to interpret command.");
		return s;
	}

	/**
	 * 
	 * @param s
	 * @param a
	 * @return the cost of doing action a in state s
	 */
	public int getCost(State s, Action a) {
		return 0;
	}

}
