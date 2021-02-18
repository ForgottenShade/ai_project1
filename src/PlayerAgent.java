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
    private Node current_solution = null;
    private int maxEval = -1000;
    private int minEval = 1000;

    @Override
    public void init(String role, int width, int height, int playclock) {
        if(role.equals("white")){
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
        long startTime = System.currentTimeMillis();

        if (lastMove != null) {
            int x1 = lastMove[0], y1 = lastMove[1], x2 = lastMove[2], y2 = lastMove[3];
            String roleOfLastPlayer;
            if (myTurn && team.equals(Team.WHITE) || !myTurn && team.equals(Team.BLACK)) {
                roleOfLastPlayer = "black";
            } else {
                roleOfLastPlayer = "white";
            }
            System.out.println(roleOfLastPlayer + " moved from " + x1 + "," + y1 + " to " + x2 + "," + y2);
            // TODO: 1. update your internal world model according to the action that was just executed
            env.updateState(x1 - 1, y1 - 1, x2 - 1, y2 - 1);
        }
        System.out.println(env.currentState);

        if (myTurn){
            Node c_node = new Node(env.currentState, env.eval(env.currentState));
            Node best_node = null;
            int depth = 1;
            while(System.currentTimeMillis() - startTime < (playclock * 1000) - 10 && depth < 4){
                minimax(c_node, depth, -1000, 1000, isWhiteTurn, isWhiteTurn); // minimax(c_node, 3, -1000, 1000, true)
                frontierList = new ArrayList<Node>();
                if (best_node == null){
                    best_node = current_solution;
                }
                if (isWhiteTurn){
                    if (current_solution.evaluation > best_node.evaluation){
                        best_node = current_solution;
                    }
                }
                else {
                    if (current_solution.evaluation < best_node.evaluation){
                        best_node = current_solution;
                    }
                }
                
                System.out.println(best_node.evaluation);
                depth++;
            }
    
            isWhiteTurn = !isWhiteTurn;
            myTurn = !myTurn;
            return best_node.move.toString();
        }
        myTurn = !myTurn;
        isWhiteTurn = !isWhiteTurn;

        return "noop";
    }

    public int minimax(Node position, int depth, int alpha, int beta, boolean maxPlayer, boolean actPlayer){ // 
        if (depth == 0 || position.state.isTerminal){
            return env.eval(position.state);
        }
        expandNode(position, maxPlayer);
        ArrayList<Node> present_list =  (ArrayList<Node>) frontierList.clone();
        if (present_list.size() > 0){
            if (maxPlayer){
                maxEval = -1000;
                for (int i = 0; i < present_list.size(); i++){
                    Node next_node = present_list.get(i);
                    int eval = minimax(next_node, depth - 1, alpha, beta, false, actPlayer); // (next_node, depth - 1, alpha, beta, false)
                    if (eval > maxEval){
                        maxEval = eval;
                        if(actPlayer){
                          current_solution = present_list.get(i);
                        }
                        System.out.println("In minimax - maxPlayer. current_solution: " + current_solution.move);
                    }
                    if (eval > alpha){
                        alpha = eval;
                    }
                    if (beta <= alpha){
                        break;
                    }
                }
                return maxEval;
            }
            else {
                minEval = 1000;
                for (int j = 0; j < present_list.size(); j++){
                    Node next_node = present_list.get(j);
                    int eval = minimax(next_node, depth - 1, alpha, beta, true, actPlayer); // (next_node, depth - 1, alpha, beta, true)
                    if (eval < minEval){
                        minEval = eval;
                        if (!actPlayer){
                            current_solution = present_list.get(j);
                        }
                        System.out.println("In minimax - minPlayer. current_solution: " + current_solution.move);
                    }
                    if (eval < beta){
                        beta = eval;
                    }
                    if (beta <= alpha){
                        break;
                    }
                }
                return minEval;
            }
        }
        return env.eval(position.state);
    }

    public void expandNode(Node _node, boolean turn) {
        boolean isTimeUp = false; // of course change this
        if (isTimeUp){
            throw new RuntimeException();
        }
        // for each available move...
        // update frontier list
        ArrayList<Node> _legal_nodes = env.legalNodes(_node, turn);
        frontierList = _legal_nodes;
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
    }
}
