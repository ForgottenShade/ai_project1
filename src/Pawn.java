public class Pawn {
    public Coordinates position;
    public Team team;

    public Pawn(int x, int y, Team _team){
        position.x = x;
        position.y = y;
        team = _team;
    }

    public void Move(Action _move){
        if(_move == Action.MOVE && team == Team.WHITE){
            position.y += 1;
        }
    }
}
