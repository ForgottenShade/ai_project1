import javax.swing.text.html.HTMLDocument;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * 
 * This class holds all information about the state of the board
 *
 */
public class State implements Cloneable {
		//public ArrayList<Pawn> pawns_white;
		//public ArrayList<Pawn> pawns_black;
		public int width, height;
		protected short[][] myMap;
		protected boolean isWhiteTurn;
		protected int eval;
		public boolean isTerminal;


		public State(int _width, int _height) {
			//initialize the starting state of the game
			this.height = _height;
			this.width = _width;
			myMap = new short[width][height]; // the TA has [2][2] instead of width and height... I have no idea why.
			isWhiteTurn = true;
			eval = 0; 
			isTerminal = false;
		}

		public String toString() {
			System.out.println("myMapLength: " + myMap.length + " , " + myMap[0].length);
			String toRet = "isWhiteTurn: " + isWhiteTurn;
			if (myMap != null && myMap.length > 0 && myMap[0].length > 0){
				toRet += "\nMap: \n";
				for (int j = myMap[0].length - 1; j >= 0 ; j--){ // for each row, we have to go from up to down
	
					for (int i = 0; i < myMap.length; i++){ // for each column
						if (myMap[i][j] == 0){
							toRet += "- "; // represents empty space
						}
						else if (myMap[i][j] == 1){
							toRet += "W "; // represents white piece
						}
						else if (myMap[i][j] == 2){
							toRet += "B "; // represents black piece
						}
						else { // this should never happen, but good to have just in case
							System.out.println("--Error-- State : toString() -> myMap[i][j] is not 0, 1 or 2");
						}
					}
					toRet += "\n";
				}
			}
			else {
				toRet += "--Error-- State : toString() -> could not print out map";
			}
			return toRet;
		}

		
		@SuppressWarnings("unchecked")
		public State clone() {
			State cloned;
			try {
				cloned = (State)super.clone();
				short[][] newMap = new short[myMap.length][]; // we cannot directly clone 2D arrays so we need to iterate
				for (int i = 0; i < myMap.length; i++){newMap[i] = myMap[i].clone();};
				cloned.myMap = newMap;
				cloned.isWhiteTurn = isWhiteTurn; // make sure to change the current player somewhere
			} catch (CloneNotSupportedException e) { e.printStackTrace(); System.exit(-1); cloned=null; }
			return cloned;
		}
	
		public State applyMove(Moves _move){
			short moved_piece = myMap[_move.x][_move.y];
			myMap[_move.x2][_move.y2] = moved_piece;
			myMap[_move.x][_move.y] = 0;
			return this;
		}
	
		// @SuppressWarnings("unchecked")
		// public State clone() {
		// 	State cloned;
		// 	try {
		// 		cloned = (State)super.clone();
		// 		cloned.pawns_white = (ArrayList<Pawn>) pawns_white.clone();
		// 		cloned.pawns_black = (ArrayList<Pawn>) pawns_black.clone();
		// 	} catch (CloneNotSupportedException e) { e.printStackTrace(); System.exit(-1); cloned=null; }
		// 	return cloned;
		// }

		public boolean equals(Object o) {
			//TODO
			if (!(o instanceof State)) { 
				return false;
			}
			
			State s = (State) o;
			return s.myMap.equals(myMap);
		}

		// private void init_white(){
		// 	int id_counter = 0;
		// 	for(int y = 0; y < 2; y++){
		// 		for(int x = 0; x < this.width; x++){
		// 			Pawn new_pawn = new Pawn(x, y, id_counter, Team.WHITE);
		// 			pawns_white.add(new_pawn);
		// 			id_counter++;
		// 		}
		// 	}
		// }
	
		// private void init_black(){
		// 	int id_counter = 0;
		// 	for(int y = 0; y < 2; y++){
		// 		for(int x = 0; x < this.width; x++){
		// 			Pawn new_pawn = new Pawn(x, height-y, id_counter, Team.BLACK);
		// 			pawns_black.add(new_pawn);
		// 			id_counter++;
		// 		}
		// 	}
		// }
	


		// //check if a pawn can move forward
		// public boolean can_move(Pawn _pawn, int x, int y){
		// 	//find the position after the move
		// 	Coordinates new_pos;
		// 	if(_pawn.team == Team.WHITE){
		// 		new_pos = new Coordinates(_pawn.position.x, _pawn.position.y + 1);
		// 	}else {
		// 		new_pos = new Coordinates(_pawn.position.x, _pawn.position.y - 1);
		// 	}

		// 	//check against white pawns
		// 	for(int i = 0; i < pawns_white.size(); i++){
		// 		Pawn other_pawn = pawns_white.get(i);
		// 		if(other_pawn.position == new_pos){
		// 			return false;
		// 		}
		// 	}

		// 	//check against black pawns
		// 	for(int i = 0; i < pawns_black.size(); i++){
		// 		Pawn other_pawn = pawns_black.get(i);
		// 		if(other_pawn.position == new_pos){
		// 			return false;
		// 		}
		// 	}

		// 	return true;
		// }


		//check if a pawn can capture another and return the action required to do so, null if not
		//TODO: Handle the case when can capture on both sides
	/*
		public Action can_capture(Pawn _pawn){
			//find the new position after the move
			Coordinates cap_left;
			Coordinates cap_right;

			if(_pawn.team == Team.WHITE){
				cap_left = new Coordinates(_pawn.position.x - 1, _pawn.position.y + 1);
				cap_right = new Coordinates(_pawn.position.x + 1, _pawn.position.y + 1);
			}else{
				cap_left = new Coordinates(_pawn.position.x + 1, _pawn.position.y - 1);
				cap_right = new Coordinates(_pawn.position.x - 1, _pawn.position.y - 1);
			}

			//check cap_left against the opposing team
			if(_pawn.team == Team.WHITE){
				for(int i = 0; i < pawns_black.size(); i++){
					Pawn other_pawn = pawns_black.get(i);
					if(other_pawn.position == cap_left){
						return Action.CAP_LEFT;
					}else if(other_pawn.position == cap_right){
						return Action.CAP_RIGHT;
					}
				}
			}

			//check cap_left against the opposing team
			if(_pawn.team == Team.BLACK){
				for(int i = 0; i < pawns_white.size(); i++){
					Pawn other_pawn = pawns_white.get(i);
					if(other_pawn.position == cap_left){
						return Action.CAP_LEFT;
					}else if(other_pawn.position == cap_right){
						return Action.CAP_RIGHT;
					}
				}
			}

			return null;
		}
	*/

		// use this function to prevent duplicate states
		// but this hash functio does not guarantee unique states, we need to store the
		// states in a hash map
		public int hashCode() {
			//TODO
			return 0;
		}
	}