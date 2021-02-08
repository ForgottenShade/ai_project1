import java.util.HashSet;

/**
 * 
 * This class holds all information about the state of the board
 *
 */
public class State implements Cloneable {
		public Coordinates position;

		public State() {
			position = new Coordinates(0,0);
		}

		@SuppressWarnings("unchecked")
		public State clone() {
			State cloned;
			try {
				cloned = (State)super.clone();
				cloned.position = (Coordinates)position.clone();
			} catch (CloneNotSupportedException e) { e.printStackTrace(); System.exit(-1); cloned=null; }
			return cloned;
		}

		public boolean equals(Object o) {
			//TODO
			return false;
		}

		// use this function to prevent duplicate states
		// but this hash functio does not guarantee unique states, we need to store the
		// states in a hash map
		public int hashCode() {
			//TODO
			return 0;
		}
	}