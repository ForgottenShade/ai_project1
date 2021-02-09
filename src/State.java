import java.util.HashSet;
import java.util.ArrayList;

/**
 * 
 * This class holds all information about the state of the board
 *
 */
public class State implements Cloneable {
		public ArrayList<Pawn> pawns_white;
		public ArrayList<Pawn> pawns_black;
		public int width, height;

		public State(int _width, int _height) {
			//initialize the starting state of the game
			this.init_white();
			this.init_black();
			this.width = _width;
			this.height = _height;
		}

		@SuppressWarnings("unchecked")
		public State clone() {
			State cloned;
			try {
				cloned = (State)super.clone();
				cloned.pawns_white = (ArrayList<Pawn>) pawns_white.clone();
				cloned.pawns_black = (ArrayList<Pawn>) pawns_black.clone();
			} catch (CloneNotSupportedException e) { e.printStackTrace(); System.exit(-1); cloned=null; }
			return cloned;
		}

		public boolean equals(Object o) {
			//TODO
			if (!(o instanceof State)) { 
				return false;
			}
			
			State s = (State) o;
			return s.pawns_black.equals(pawns_black) && s.pawns_white.equals(pawns_white);
		}

		private void init_white(){
			int id_counter = 0;
			for(int y = 0; y < 2; y++){
				for(int x = 0; x < this.width; x++){
					Pawn new_pawn = new Pawn(x, y, id_counter, Team.WHITE);
					pawns_white.add(new_pawn);
					id_counter++;
				}
			}
		}

		private void init_black(){
			int id_counter = 0;
			for(int y = 0; y < 2; y++){
				for(int x = 0; x < this.width; x++){
					Pawn new_pawn = new Pawn(x, height-y, id_counter, Team.BLACK);
					pawns_black.add(new_pawn);
					id_counter++;
				}
			}
		}

		// use this function to prevent duplicate states
		// but this hash functio does not guarantee unique states, we need to store the
		// states in a hash map
		public int hashCode() {
			//TODO
			return 0;
		}
	}