
public class SimpleHeuristics implements Heuristics {
	/**
	 * reference to the environment to be able to figure out positions of obstacles
	 */
	private Environment env;

	/**
	 * @param solvingAgent
	 */
	SimpleHeuristics() {
	}
	
	public void init(Environment env) {
		this.env = env;
	}

	//TODO: Figure out how we want to do heuristics
	private int nbSteps(Coordinates a, Coordinates b) {
		int h = 0;
		h += Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
		return h;
	}

	public int eval(State s) {
		return 0;
	}
}