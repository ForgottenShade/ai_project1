import java.util.*;
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

    //taking the legal moves, generate all possible states
    public ArrayList<Node> legalNodes(Node _node, boolean turn){
		List<Moves> _legal_moves = legalMoves(_node.state, turn);
		ArrayList<Node> _legal_nodes = new ArrayList<>();

		for(int i = 0; i < _legal_moves.size(); i++){
			//State _new_state = _node.state.clone();
			Node _new_node;
			State _new_state = getNextState(_node.state, _legal_moves.get(i), turn);
			//_new_state.applyMove(_legal_moves.get(i));
			_new_node = new Node(_node, _new_state, _legal_moves.get(i), eval(_new_state));
			_legal_nodes.add(_new_node);
		}

		return  _legal_nodes;
	}

    // get all moves for current player
    public List<Moves> legalMoves(State state, boolean turn) {
        List<Moves> moves = new LinkedList<Moves>();
        
		for (int i=0; i<state.myMap.length; i++) { //column
			for (int j=0; j<state.myMap[0].length; j++) { //row
				// add moves for white piece
				if (turn && state.myMap[i][j] == 1) {
					moves.addAll(getMoves(state,i,j, turn));
				}
				// add moves for black piece
				else if (!turn && state.myMap[i][j] == 2) {  
					moves.addAll(getMoves(state,i,j, turn)); 
				}
			}
		}
		if (moves.isEmpty()){
			state.isTerminal = true;
		}
        return moves;
    }

    // find all moves
    public List<Moves> getMoves(State s, int x, int y, boolean turn) {
        List<Moves> moves = new LinkedList<Moves>();
        // white starts at the bottom of the board
		if (turn) {  
			// make sure we are inside the board
			if (y + 1 < s.myMap[0].length-1) { 
				//chek if left diagonal capture is possible
				if (x > 0) {
					if (s.myMap[x-1][y+1] == 2) {
						// add move is cell does contain a black piece  
						moves.add(new Moves(x,y,x-1,y+1)); 
					}
				}
				//check if right diagonal capture is possible
				if (x < s.myMap.length-1){  
					if (s.myMap[x+1][y+1] == 2) { 
						// add if move if cell does contain a white piece
						moves.add(new Moves(x,y,x+1,y+1));
					}
				}
				// check if forward move is possible
				if (s.myMap[x][y+1] == 0) {  
					// add move if cell is empty
					moves.add(new Moves(x,y,x,y+1));
				}
			}
		}
		else{
			// opposite logic for black player
			if (y>0) { 
				//left diagonal capture
				if (x > 0) { 
					if (s.myMap[x-1][y-1] == 1) {  
						moves.add(new Moves(x,y,x-1,y-1));
					}
				}
				//right diagonal capture
				if (x < s.myMap.length-1) { 
					if (s.myMap[x+1][y-1] == 1) {
						moves.add(new Moves(x,y,x+1,y-1));
					}
				}
				//check if forward move is possible (backwards)
				if(s.myMap[x][y-1] == 0) {  
					moves.add(new Moves(x,y,x,y-1));
				}
			}
		}
		
        return moves;
    }

	public State getNextState(State s, Moves m, boolean turn){
        State c = s.clone();
        // todo
		if (turn) {
			c.myMap[m.x][m.y] = 0;
			c.myMap[m.x2][m.y2] = 1;
			if (m.y2 == c.myMap[0].length - 1) { // If a white pawn has reached the top the state is terminal
				c.isTerminal = true; 
			}
		}
		else{
			c.myMap[m.x][m.y] = 0;
			c.myMap[m.x2][m.y2] = 2;
			if (m.y2 == 0){ // If a black pawn has reached the bottom the state is terminal
				c.isTerminal = true; 
			}
		}
		//c.eval = eval(c);
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
                if (s.myMap[i][j] == 1){
					whitePieces++;
					if (j > 1){ // The close a white pawn is to the top gives more points
						whitePieces += j;
					}
					if (j == s.myMap[0].length -1){ // 100 points given if a white pawn reaches the top
						whitePieces += 100; 
					}
				}
                else if (s.myMap[i][j] == 2){ // Opposite for black
					blackPieces++;
					if (j < s.myMap[0].length - 2){ 
						blackPieces += -(s.myMap[0].length - j);
					}
					if (j == 0){
						blackPieces += 100; 
					}
				}
            }
        }
        e = whitePieces - blackPieces;
        //if (s.isWhiteTurn){
        //    e = -e; // negate the score
        //}
        return e;
    }

    // print out current state for this environment. Good for testing.
    public String toString() {
        return currentState.toString();
    }
	
	public State updateState(int x1, int y1, int x2, int y2){
		short moved_piece = currentState.myMap[x1][y1];
		currentState.myMap[x2][y2] = moved_piece;
		currentState.myMap[x1][y1] = 0;
		return currentState;
	}
}
