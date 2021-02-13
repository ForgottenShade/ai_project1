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

    //taking the legal moves, generate all possible states
    public List<Node> legalNodes(Node _node){
		List<Moves> _legal_moves = legalMoves(_node.state);
		List<Node> _legal_nodes = new LinkedList<Node>();

		for(int i = 0; i < _legal_moves.size(); i++){
			State _new_state = _node.state.clone();
			Node _new_node;
			//_new_state.applyMove(_legal_moves.get(i));
			_new_node = new Node(_node, getNextState(_node.state, _legal_moves.get(i)), _legal_moves.get(i), 0);
			_legal_nodes.add(_new_node);
		}

		return  _legal_nodes;
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
		if (moves.isEmpty()){
			state.isTerminal = true;
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
					if (s.myMap[x-1][y+1] == 2) {  //if we are not at the LEFT end of the map then we check if we can capture to the left
						moves.add(new Moves(x,y,x-1,y+1)); //if the top left square has black piece 
					}
				}
				//right diagonal
				if (x < s.myMap.length-1){  //if we are not at the RIGHT end of the map then we check if we can capture to the right
					if (s.myMap[x+1][y+1] == 2) { //if the top right square has black piece
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
				if (x > 0) {  //if we are not at the LEFT end of the map then we check if we can caputre to the left
					if (s.myMap[x-1][y-1] == 1) {  //if the bottom left square has white piece 
						moves.add(new Moves(x,y,x-1,y-1));
					}
				}
				//right diagonal
				if (x < s.myMap.length-1) { //if we are not at the RIGHT end of the map then we check if we can capture to the right
					if (s.myMap[x+1][y-1] == 1) { //if the bottom right square has white piece
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
		c.eval = eval(c);
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
	
	public State updateState(int x1, int y1, int x2, int y2){
		short moved_piece = currentState.myMap[x1][y1];
		currentState.myMap[x2][y2] = moved_piece;
		currentState.myMap[x1][y1] = 0;
		return currentState;
	}
}
