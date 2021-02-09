public class PlayerAgent implements Agent{

    private Team team; // the name of this agent's role (white or black)
    private int playclock; // this is how much time (in seconds) we have before nextAction needs to return a move
    private boolean myTurn; // whether it is this agent's turn or not
    private int width, height; // dimensions of the board

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

        }
        return null;
    }

    @Override
    public void cleanup() {}
}
