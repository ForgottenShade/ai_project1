import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class PlayerAgent implements Agent{

    private Team team; // the name of this agent's role (white or black)
    private int playclock; // this is how much time (in seconds) we have before nextAction needs to return a move
    private boolean myTurn; // whether it is this agent's turn or not
    private int width, height; // dimensions of the board
    private boolean isWhiteTurn;
    private boolean isTerminalState;
    private Environment env;
    private ArrayList<Node> frontierList;
    private int sizeOfTable = 1000;

    @Override
    public void init(String role, int width, int height, int playclock) {
        if(role == "white"){
            team = Team.WHITE;
            myTurn = true;
        }else{
            team = Team.BLACK;
            myTurn = false;
        }

        this.width = width;
        this.height = height;
        this.playclock = playclock;
        isWhiteTurn = true; 
        isTerminalState = false;
        // Initalize the environment and frontier list
        env = new Environment(width, height); 
        frontierList = new ArrayList<Node>();
    }

    @Override
    public String nextAction(int[] lastMove) {
        if (lastMove != null) {
            int x1 = lastMove[0], y1 = lastMove[1], x2 = lastMove[2], y2 = lastMove[3];
            String roleOfLastPlayer;
            if (myTurn && team.equals(Team.WHITE) || !myTurn && team.equals(Team.BLACK)) {
                roleOfLastPlayer = "white";
            } else {
                roleOfLastPlayer = "black";
            }
            System.out.println(roleOfLastPlayer + " moved from " + x1 + "," + y1 + " to " + x2 + "," + y2);
            // TODO: 1. update your internal world model according to the action that was just executed
            env.updateState(x1, y1, x2, y2);

        }

        myTurn = !myTurn;
        if (myTurn){
            Node c_node = new Node(env.currentState, env.currentState.eval);
            doSearch(c_node, 1);
            }

             //int alpha = Integer.MAX_VALUE;
            //int beta = -Integer.MAX_VALUE; // you cannot do Integer.Min_Value since if you do -MinValue you will overflow the buffer.
            // TODO: 2. run alpha-beta search to determine the best move
            // look at RandomAgent to understand what to return
            // You should start with something "simple" Like DFS
            // Then go add on it more, For example: DFS -> DFS with iterative deepening
            // -> Minimax with iterative deepening -> Add pruning (alpha-beta search)
            // Remember to always test everything you do as soon as you can do it!!


            // The format of what has to be returned. 
            // return "(move " + x1 + " " + y1 + " " + x2 + " " + y2 + ")";

        }
        else {
            return "noop";
        }
    }

    public int doSearch(Node _parent_node,int depth){
        int value;
        int bestVal;

        while (true) { // some loop that goes on until a RuntimeException is thrown.
            try {
                // do your search here
                if (_parent_node.isTerminal) {
                    return _parent_node.value;
                }

                expandNode(_parent_node);

                for(int i = 0; i < depth; i++) {
                    for (int j = 0; j < frontierList.size(); j++) {
                        value = doSearch(frontierList.get(j), depth);
                        bestVal = max(value, bestVal);
                    }
                }

                return bestVal;
            } catch (RuntimeException e) {
                return bestVal;
            }
            depth++;
        }
    }

    public Node findNextNodeToExpand() {
        if (frontierList.isEmpty()){
            System.out.println("MyAgent : findNextNodeToExpand() -> frontierList is empty");
            return null;
        }
        else{
            // todo
            // this presently gets the node with the best evaluation as in a* before using dfs and iterative deepening upon that node
            int best_val = 0;
            Node best_node = null;
            for(int i = 0; i < frontierList.size(); i++){
                Node _node = frontierList.get(i);
                if(best_node == null){
                    best_node = _node;
                    best_val = _node.evaluation;
                }else if(_node.evaluation < best_val){
                    best_node = _node;
                    best_val = _node.evaluation;
                }
            }
            return best_node;
        }
    }

    public void expandNode(Node _node) {
        boolean isTimeUp = true; // of course change this
        if (isTimeUp){
            throw new RuntimeException();
        }
        // for each available move...
        // update frontier list
        List<State> _legal_states = env.legalStates(_node);
        for(int i = 0; i < _legal_states.size(); i++){
            Node newNode = new Node(_node, _legal_states.get(i), 0);
            frontierList.add(newNode);
        }
        frontierList.remove(_node);
        // ...

    }

    @Override
    public void cleanup() {
        // TODO: cleanup so that he agent is ready for the next match
        this.team = null;
        this.playclock = -1;
        this.height = -1;
        this.width = -1;
        this.myTurn = false;
        this.env = null;
        this.frontierList = null;
        this.isWhiteTurn = false;
        this.sizeOfTable = -1;

    }
}
